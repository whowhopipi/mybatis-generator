package cn.ruihusoft.mybatis.core;


import cn.ruihusoft.mybatis.HelperConfig;
import cn.ruihusoft.mybatis.utils.Generator;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DoGenerator {

    public static void main(String[] args) throws SQLException {
        HelperConfig helperConfig = new HelperConfig();

        helperConfig.setPath("d:/temp");

        HelperConfig.DatabaseInfo databaseInfo = new HelperConfig.DatabaseInfo();
        databaseInfo.setUrl("jdbc:mysql://192.168.20.185:3306/verifydir?useSSL=false&useUnicode=true&characterEncoding=utf8&serverTimezone=GMT%2B8");
        databaseInfo.setUser("root");
        databaseInfo.setPassword("Pwdis123");
        databaseInfo.setDriver("com.mysql.jdbc.Driver");
        databaseInfo.setDialect("MYSQL");
        helperConfig.setDatabase(databaseInfo);

        HelperConfig.EntityInf entityInf = new HelperConfig.EntityInf();
        entityInf.setEnable(true);
        entityInf.setPkg("cn.ruihusoft.entity");
        helperConfig.setEntity(entityInf);

        HelperConfig.ConfInf confInf = new HelperConfig.ConfInf();
        confInf.setEnable(true);
        confInf.setPkg("cn.ruihusoft.entity");
        helperConfig.setConf(confInf);

        HelperConfig.DaoInf daoInf = new HelperConfig.DaoInf();
        daoInf.setEnable(true);
        daoInf.setPkg("cn.ruihusoft.dao");
        helperConfig.setDao(daoInf);

        HelperConfig.ExampleInf exampleInf = new HelperConfig.ExampleInf();
        exampleInf.setEnable(true);
        exampleInf.setPkg("cn.ruihusoft.entity");
        helperConfig.setExample(exampleInf);

        HelperConfig.XmlInf xmlInf = new HelperConfig.XmlInf();
        xmlInf.setEnable(true);
        xmlInf.setPkg("mapper");
        helperConfig.setXml(xmlInf);

        HelperConfig.ServiceInf serviceInf = new HelperConfig.ServiceInf();
        serviceInf.setEnable(true);
        serviceInf.setPkg("cn.ruihusoft.service");
        helperConfig.setService(serviceInf);

        HelperConfig.ServiceImplInf serviceImplInf = new HelperConfig.ServiceImplInf();
        serviceImplInf.setEnable(true);
        serviceImplInf.setPkg("cn.ruihusoft.service.impl");
        helperConfig.setServiceImpl(serviceImplInf);

        List<HelperConfig.TableInf> tableInfs = new ArrayList<>();

        HelperConfig.TableInf allTable = new HelperConfig.TableInf();
        allTable.setName("%");
//        tableInfs.add(allTable);

        HelperConfig.TableInf directoryInf = new HelperConfig.TableInf();
        directoryInf.setName("HC_DIRECTORY_INF");
        tableInfs.add(directoryInf);

        HelperConfig.ColumnInf type = new HelperConfig.ColumnInf();
        type.setName("type");
        type.setJavaType("cn.ruihusoft.enums.TypeEnum");
        directoryInf.addColumnInf(type);

        helperConfig.setTables(tableInfs);

        Generator.create(helperConfig);
    }
}
