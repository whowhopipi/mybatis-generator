package cn.ruihusoft.mybatis.generator;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MybatisColumnType {

    CHAR("CHAR", "String"),

    VARCHAR("VARCHAR", "String"),

    LONGVARCHAR("LONGVARCHAR", "String"),

    NUMERIC("NUMERIC", "java.math.BigDecimal"),

    DECIMAL("DECIMAL", "java.math.BigDecimal"),

    BIT("BIT", "Boolean"),

    BOOLEAN("BOOLEAN", "Boolean"),

    TINYINT("TINYINT", "Byte"),

    SMALLINT("SMALLINT", "Short"),

    INTEGER("INTEGER", "Integer"),

    BIGINT("BIGINT", "Long"),

    REAL("REAL", "Float"),

    FLOAT("FLOAT", "Double"),

    BINARY("BINARY", "byte[]"),

    VARBINARY("VARBINARY", "byte[]"),

    LONGVARBINARY("LONGVARBINARY", "byte[]"),

    DATE("DATE", "java.util.Date"),

    TIME("TIME", "java.sql.Time"),

    TIMESTAMP("TIMESTAMP", "java.sql.Timestamp"),

    CLOB("CLOB", "java.sql.Clob"),

    BLOB("BLOB", "java.sql.Blob"),
    ;

    private String mybatisType;

    private String javaType;

}
