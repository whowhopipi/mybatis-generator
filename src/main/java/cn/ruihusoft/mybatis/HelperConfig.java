package cn.ruihusoft.mybatis;

import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 代码生成器配置类
 *
 * @author hujh
 */
@Data
public class HelperConfig {

    /**
     *
     */
    private String name;

    private String path;

    private String templates = "template";

    private String author;

    private String version;

    private DatabaseInfo database = new DatabaseInfo();

    private EntityInf entity = new EntityInf();

    private DaoInf dao = new DaoInf();

    private XmlInf xml = new XmlInf();

    private ConfInf conf = new ConfInf();

    private ExampleInf example = new ExampleInf();

    private ServiceInf service = new ServiceInf();

    private ServiceImplInf serviceImpl = new ServiceImplInf();

    private List<TableInf> tables;

    @Data
    public static class DatabaseInfo {
        private String dialect;
        private String driver;
        private String url;
        private String user;
        private String password;
    }

    @Data
    public static class ModelInf {
        private boolean enable = false;
        private String path = "src/main/java";

        private String pkg;
        private String suffix = "";
        private String template;

        private Map<String, String> others;
    }

    @Data
    @ToString(callSuper = true)
    public static class EntityInf extends ModelInf {
        private String template = "entity.ftl";
        private boolean keyFile = false;
    }

    @Data
    @ToString(callSuper = true)
    public static class DaoInf extends ModelInf {
        private String template = "dao.ftl";
        private String baseMapper = "tk.mybatis.mapper.common.Mapper";
        private String suffix = "Mapper";
    }

    @Data
    @ToString(callSuper = true)
    public static class XmlInf extends ModelInf {
        private String suffix = "Mapper";
        private String path = "src/main/resources";
        private String template = "xml.ftl";
    }

    @Data
    @ToString(callSuper = true)
    public static class ExampleInf extends ModelInf {
        private String template = "example.ftl";
        private String suffix = "Example";
    }

    @Data
    @ToString(callSuper = true)
    public static class ConfInf extends ModelInf {
        private String template = "entityConst.ftl";
        private String suffix = "Conf";
    }

    @Data
    @ToString(callSuper = true)
    public static class ServiceInf extends ModelInf {
        private String parent = "cn.ruihusoft.mybatis.base.BaseService";
        private String template = "service.ftl";
        private String suffix = "Service";
    }

    @Data
    @ToString(callSuper = true)
    public static class ServiceImplInf extends ServiceInf {
        private String parent = "cn.ruihusoft.mybatis.base.BaseServiceImpl";
        private String template = "serviceImpl.ftl";
        private String suffix = "ServiceImpl";
    }

    @Data
    public static class TableInf {
        private String name;
        private String comment;
        private boolean ignore = false;
        private EntityInf entity;
        private DaoInf dao;
        private XmlInf xml;
        private ExampleInf example;
        private ConfInf conf;
        private ServiceInf service;
        private ServiceImplInf serviceImpl;
        private List<ColumnInf> columns = new ArrayList<>();
        private Map<String, String> others;

        public void addColumnInf(ColumnInf columnInf) {
            this.columns.add(columnInf);
        }
    }

    @Data
    public static class ColumnInf {
        private String name;
        private String comment;
        private boolean ignore = false;
        private String javaType;
        private Map<String, String> others;
    }
}
