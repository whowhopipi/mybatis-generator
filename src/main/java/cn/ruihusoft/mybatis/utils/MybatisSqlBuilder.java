package cn.ruihusoft.mybatis.utils;

public class MybatisSqlBuilder {

    private boolean hasScript = false;

    private StringBuilder sqlTemp = new StringBuilder();

    public static String buildIfTag(String condition, String content) {
        StringBuilder ifTagSql = new StringBuilder();

        ifTagSql.append("<if test=\"").append(condition).append("\">");
        ifTagSql.append(content);
        ifTagSql.append("</if>");

        return ifTagSql.toString();
    }

    public static String buildSimpleForeachTag(String collectionName) {
        return buildForeachTag(
                collectionName,
                collectionName + "Item",
                collectionName + "Index",
                "(",
                ")",
                ",",
                "#{" + collectionName + "Item}"
        );
    }

    public static String buildForeachTag(String collectionName, String itemName, String indexName, String openTag, String closeTag, String separatorTag, String content) {
        StringBuilder foreachTagSql = new StringBuilder();

        foreachTagSql
                .append("<foreach ")
                .append("collection=\"" + collectionName + "\" ")
                .append("item=\"" + itemName + "\" ")
                .append("index=\"" + indexName + "\" ")
                .append("open=\"" + openTag + "\" ")
                .append("close=\"" + closeTag + "\" ")
                .append("separator=\"" + separatorTag + "\">");
        foreachTagSql.append(content);
        foreachTagSql.append("</foreach>");

        return foreachTagSql.toString();
    }

    public static String buildBindTag(String name, String value) {
        return "<bind name=\"" + name + "\" value=\"" + value + "\" />";
    }

    public MybatisSqlBuilder newBuilder() {
        return new MybatisSqlBuilder();
    }

    public MybatisSqlBuilder script() {
        hasScript = true;
        return this;
    }

    public MybatisSqlBuilder and(String sql) {
        sqlTemp.append(sql);
        return this;
    }

    public MybatisSqlBuilder ifTag(String condition, String content) {
        sqlTemp.append(buildIfTag(condition, content));
        return this;
    }

    public MybatisSqlBuilder foreachSimpleTag(String collectionName) {
        sqlTemp.append(buildSimpleForeachTag(collectionName));
        return this;
    }

}
