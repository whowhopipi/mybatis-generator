package cn.ruihusoft.mybatis.entity;

import lombok.Data;

import java.util.Map;

@Data
public class FreemarkerModel {

    /**
     * 数据库列
     */
    private TableProperties table;

    private String date;

    private String author;

    /**
     * 实体类包名
     */
    private String entityPackage;

    private String entityName;

    /**
     * dao类包名
     */
    private String daoPackage;

    private String daoName;

    private String baseMapper;

    /**
     * xml
     */
    private String xmlName;

    /**
     * service接口包名
     */
    private String servicePackage;

    private String serviceName;

    private String serviceParentClass;

    /**
     * service实现包名
     */
    private String serviceImplPackage;

    private String serviceImplName;

    private String serviceImplParentClass;

    /**
     * Sample包
     */
    private String examplePackage;

    private String exampleName;

    /**
     * conf包
     */
    private String confPackage;

    private String confName;

    private Map<String, Object> others;
}
