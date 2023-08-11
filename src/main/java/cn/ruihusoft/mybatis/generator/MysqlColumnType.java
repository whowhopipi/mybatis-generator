package cn.ruihusoft.mybatis.generator;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MysqlColumnType implements DbType {

    TINYINT("TINYINT",MybatisColumnType.TINYINT),
    SMALLINT("SMALLINT",MybatisColumnType.SMALLINT),
    MEDIUMINT("MEDIUMINT",MybatisColumnType.INTEGER),
    INT("INT",MybatisColumnType.INTEGER),
    INTEGER("INTEGER",MybatisColumnType.INTEGER),
    BIGINT("BIGINT",MybatisColumnType.BIGINT),

    FLOAT("FLOAT",MybatisColumnType.FLOAT),
    DOUBLE("DOUBLE",MybatisColumnType.FLOAT),
    DECIMAL("DECIMAL",MybatisColumnType.DECIMAL),
    DEC("DEC",MybatisColumnType.DECIMAL),

    DATE("DATE",MybatisColumnType.DATE),
    TIME("TIME",MybatisColumnType.TIME),
    YEAR("YEAR",MybatisColumnType.DATE),
    DATETIME("DATETIME",MybatisColumnType.DATE),
    TIMESTAMP("TIMESTAMP",MybatisColumnType.TIMESTAMP),

    CHAR("CHAR",MybatisColumnType.CHAR),
    VARCHAR("VARCHAR",MybatisColumnType.VARCHAR),
    TINYBLOB("TINYBLOB",MybatisColumnType.BINARY),
    TINYTEXT("TINYTEXT",MybatisColumnType.VARCHAR),
    BLOB("BLOB",MybatisColumnType.BLOB),
    TEXT("TEXT",MybatisColumnType.VARCHAR),
    MEDIUMBLOB("MEDIUMBLOB",MybatisColumnType.BLOB),
    MEDIUMTEXT("MEDIUMTEXT",MybatisColumnType.LONGVARCHAR),
    LONGBLOB("LONGBLOB",MybatisColumnType.LONGVARBINARY),
    LONGTEXT("LONGTEXT",MybatisColumnType.LONGVARCHAR),

    BOOLEAN("TINYINT(1)",MybatisColumnType.BOOLEAN),
    BIT("BIT(1)",MybatisColumnType.BIT),
    ;

    private String type;

    private MybatisColumnType mybatis;

    public static MysqlColumnType parse(String dbType) {
        for(MysqlColumnType one : MysqlColumnType.values()) {
            if(one.type.equalsIgnoreCase(dbType)) {
                return one;
            }
        }

        return null;
    }
}
