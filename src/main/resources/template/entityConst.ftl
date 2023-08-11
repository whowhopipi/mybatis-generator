package ${entityPackage};

public interface ${confName} {
<#list table.columns as cl>
    /**
    * 数据库中对应字段：${cl.code}
    */
    String DB_${cl.code?upper_case} = "${cl.code}";

    /**
    * 实体中对应的字段：${cl.name}
    */
    String ${cl.code?upper_case} = "${cl.name}";

</#list>
}
