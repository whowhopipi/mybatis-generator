package cn.ruihusoft.mybatis.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Data
public class ColumnProperties implements Serializable {

    /**
     * 实体中的名称
     */
    private String name;

    /**
     * 首字母大写
     */
    private String name2;

    /**
     * 列名
     */
    private String code;

    private String jdbcType;

    private String comment;

    private String javaType;

    /**
     * 是否ID
     */
    private boolean id;

    private Map<String,String> others = new HashMap<>();
}
