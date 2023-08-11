package cn.ruihusoft.mybatis.utils;

import cn.ruihusoft.mybatis.HelperConfig;
import cn.ruihusoft.mybatis.entity.ColumnProperties;
import cn.ruihusoft.mybatis.entity.TableProperties;
import cn.ruihusoft.mybatis.generator.MybatisColumnType;
import cn.ruihusoft.mybatis.generator.MysqlColumnType;
import com.google.common.base.CaseFormat;
import org.springframework.util.StringUtils;

import java.sql.*;
import java.util.*;
import java.util.regex.Pattern;

public class DatabaseInfoUtil {

    private Pattern pattern = Pattern.compile("jdbc:(?<db>\\\\w+):.*((//)|@)(?<host>.+):(?<port>\\\\d+)(/|(;DatabaseName=)|:)(?<dbName>\\\\w+)\\\\??.*");

    private Map<String, HelperConfig.ColumnInf> columnCache = new HashMap<>();
    private Map<String, Map<String, String>> tableOthersCache = new HashMap<>();
    private Map<String, String> tableCommentCache = new HashMap<>();
    private Set<String> dealTableCache = new HashSet<>();

    private Connection connection;
    private String databaseName = null;

    private interface SqlQueryCallback {
        boolean callback(ResultSet resultSet) throws SQLException;
    }

    private void sqlQuery(String sql, SqlQueryCallback callback) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        while (resultSet.next()) {
            boolean flag = callback.callback(resultSet);
            if (!flag) {
                break;
            }
        }

        resultSet.close();
        statement.close();
    }

    private TableProperties dealTable(String tableName) throws SQLException {
        if (dealTableCache.contains(tableName)) {
            return null;
        }

        TableProperties table = new TableProperties();
        table.setCode(tableName);
        table.setName(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, tableName));
        Map<String, String> tableOthers = tableOthersCache.get(tableName);
        if (tableOthers != null) {
            table.getOthers().putAll(tableOthers);
        }
        String tableComment = tableCommentCache.get(tableName);
        if (StringUtils.hasText(tableComment)) {
            table.setCommon(tableComment);
        }

        String searchIdSql = "show index from " + tableName + " where Key_name = 'PRIMARY'";
        String searchColumnSql = "select * from information_schema.columns where TABLE_SCHEMA = '" + databaseName + "' and table_name = '" + tableName + "' order by COLUMN_NAME";

        Set<String> ids = new HashSet<>();
        sqlQuery(searchIdSql, idResultSet -> {
            String columnName = idResultSet.getString("column_name");
            ids.add(columnName);
            return true;
        });

        StringBuilder columnList = new StringBuilder();
        List<ColumnProperties> idColumns = new ArrayList<>();
        List<ColumnProperties> columns = new ArrayList<>();
        sqlQuery(searchColumnSql, resultSet -> {
            String column = resultSet.getString("column_name");
            String jdbcType = resultSet.getString("data_type");
            String comment = resultSet.getString("column_comment");
            String columnType = resultSet.getString("column_type");

            String columnCacheKey = columnCacheKey(tableName, column);
            HelperConfig.ColumnInf columnInf = columnCache.get(columnCacheKey);

            if (columnInf == null) {
                columnInf = new HelperConfig.ColumnInf();
            }

            if (columnInf.isIgnore()) {
                return true;
            }

            ColumnProperties info = new ColumnProperties();

            info.setCode(column);
            // 转驼峰
            info.setName(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, column));
            info.setName2(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, column));

            MybatisColumnType mybatisColumnType = null;
            if (columnType.equalsIgnoreCase("tinyint(1)")) {
                mybatisColumnType = MybatisColumnType.BOOLEAN;
            } else if (columnType.equalsIgnoreCase("bit(1)")) {
                mybatisColumnType = MybatisColumnType.BOOLEAN;
            } else {
                MysqlColumnType dbType = MysqlColumnType.parse(jdbcType);
                if (dbType == null) {
                    throw new RuntimeException("未知的数据库类型：" + jdbcType);
                }
                mybatisColumnType = dbType.getMybatis();
            }

            info.setJdbcType(mybatisColumnType.getMybatisType());

            if (StringUtils.hasText(columnInf.getJavaType())) {
                info.setJavaType(columnInf.getJavaType());
            } else {
                info.setJavaType(mybatisColumnType.getJavaType());
            }

            if (StringUtils.hasText(columnInf.getComment())) {
                info.setComment(columnInf.getComment());
            } else {
                info.setComment(dealComment(comment));
            }

            // 判断是否ID
            if (ids.contains(column)) {
                info.setId(true);
                idColumns.add(info);
            } else {
                columns.add(info);
            }

            columnList.append(" ,").append(column);

            return true;
        });

        // 将其它列加到id列后面
        idColumns.addAll(columns);

        table.setColumns(idColumns);
        table.setColumnList(columnList.substring(2));

        dealTableCache.add(tableName);

        return table;
    }

    private String columnCacheKey(String tableName, String columnName) {
        return tableName.toLowerCase() + "_" + columnName.toLowerCase();
    }

    public List<TableProperties> getInfo(HelperConfig helperConfig) throws SQLException {
        List<TableProperties> retList = new ArrayList<>();

        // 创建连接
        HelperConfig.DatabaseInfo db = helperConfig.getDatabase();
        connection = DriverManager.getConnection(db.getUrl(), db.getUser(), db.getPassword());

        databaseName = JDBCUtil.getMysqlDbName(db.getUrl());

        //sql
        List<HelperConfig.TableInf> tableInfs = helperConfig.getTables();
        if (tableInfs == null) {
            tableInfs = new ArrayList<>();

            HelperConfig.TableInf tableInf = new HelperConfig.TableInf();
            tableInf.setName("%");
            tableInfs.add(tableInf);
        }

        Set<String> tables = new HashSet<>();
        Set<String> ignoreTables = new HashSet<>();

        for (HelperConfig.TableInf tableInf : tableInfs) {

            String searchTableSql = "select * from information_schema.`TABLES` a where a.TABLE_SCHEMA = '" + databaseName + "' and a.TABLE_NAME like '" + tableInf.getName() + "'";

            sqlQuery(searchTableSql, resultSet -> {
                String tableName = resultSet.getString("TABLE_NAME");
                String tableComment = resultSet.getString("TABLE_COMMENT");

                if(ignoreTables.contains(tableName)) {
                    return true;
                }

                if(tableInf.isIgnore()) {
                    ignoreTables.add(tableName);
                    return true;
                }

                // 判断表是否有处理过
                if(tables.contains(tableName)) {
                    return true;
                }

                // 将column缓存
                List<HelperConfig.ColumnInf> columnInfs = tableInf.getColumns();
                if (columnInfs != null) {
                    for (HelperConfig.ColumnInf columnInf : columnInfs) {
                        String columnCacheKey = columnCacheKey(tableName, columnInf.getName());
                        columnCache.put(columnCacheKey, columnInf);
                    }
                }

                if (tableInf.getOthers() != null) {
                    Map<String, String> oldCache = tableOthersCache.get(tableName);
                    if (oldCache != null) {
                        oldCache.putAll(tableInf.getOthers());
                    } else {
                        oldCache = tableInf.getOthers();
                    }
                    tableOthersCache.put(tableName, oldCache);
                }

                if (StringUtils.hasText(tableInf.getComment())) {
                    tableCommentCache.put(tableName, tableInf.getComment());
                } else if(StringUtils.hasText(tableComment)) {
                    tableCommentCache.put(tableName, tableComment);
                }

                tables.add(tableName);

                return true;
            });
        }

        for (String tableName : tables) {
            TableProperties tableProperties = dealTable(tableName);
            if (tableProperties != null) {
                retList.add(tableProperties);
            }
        }

        connection.close();

        return retList;
    }

    private String dealComment(String comment) {
        if (StringUtils.isEmpty(comment)) {
            return comment;
        }

        return comment.replaceAll("\n", "").replaceAll("\r", "").replaceAll("\"", "\\\\\"");
    }


}
