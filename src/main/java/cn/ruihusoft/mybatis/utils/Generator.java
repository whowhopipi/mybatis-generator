package cn.ruihusoft.mybatis.utils;

import cn.ruihusoft.mybatis.HelperConfig;
import cn.ruihusoft.mybatis.entity.FreemarkerModel;
import cn.ruihusoft.mybatis.entity.TableProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
public class Generator {

    private HelperConfig helperConfig;

    private List<FreemarkerModel> models;

    public static Generator create() throws Exception {
        return create("application.yml");
    }

    public static Generator create(String... applicationName) throws Exception {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        HelperConfig helperConfig = new HelperConfig();
        ObjectReader objectReader = mapper.readerForUpdating(helperConfig);

        for (String file : applicationName) {
            objectReader.readValue(Generator.class.getClassLoader().getResourceAsStream(file));
        }

//        Yaml yaml = new Yaml();
//        HelperConfig helperConfig = yaml.loadAs(Generator.class.getClassLoader().getResourceAsStream(applicationName), HelperConfig.class);

        return create(helperConfig);
    }

    public static Generator create(HelperConfig helperConfig) throws SQLException {
        Generator generator = new Generator();
        generator.helperConfig = helperConfig;

        // 如果没有设置根目录，则设置当前目录为根目录
        if (!StringUtils.hasText(helperConfig.getPath())) {
            String currentProjectDir = System.getProperty("user.dir");
            helperConfig.setPath(currentProjectDir);
        }

        generator.models = new ArrayList<>();

        DatabaseInfoUtil infoUtil = new DatabaseInfoUtil();
        List<TableProperties> tables = infoUtil.getInfo(helperConfig);

        for (TableProperties table : tables) {
            FreemarkerModel model = new FreemarkerModel();

            //
            model.setEntityPackage(helperConfig.getEntity().getPkg());
            model.setEntityName(table.getName() + helperConfig.getEntity().getSuffix());

            model.setDaoPackage(helperConfig.getDao().getPkg());
            model.setDaoName(table.getName() + helperConfig.getDao().getSuffix());
            model.setBaseMapper(helperConfig.getDao().getBaseMapper());

            model.setXmlName(table.getName() + helperConfig.getXml().getSuffix());

            model.setServicePackage(helperConfig.getService().getSuffix());
            model.setServiceName(table.getName() + helperConfig.getService().getSuffix());
            model.setServiceParentClass(helperConfig.getService().getParent());

            model.setServiceImplPackage(helperConfig.getServiceImpl().getPkg());
            model.setServiceImplName(table.getName() + helperConfig.getServiceImpl().getSuffix());
            model.setServiceImplParentClass(helperConfig.getServiceImpl().getParent());

            model.setExamplePackage(helperConfig.getExample().getPkg());
            model.setExampleName(table.getName() + helperConfig.getExample().getSuffix());

            model.setConfPackage(helperConfig.getConf().getPkg());
            model.setConfName(table.getName() + helperConfig.getConf().getSuffix());

            model.setTable(table);

            generator.models.add(model);
        }

        if (helperConfig.getEntity().isEnable()) {
            generator.createEntity();
        }

        if (helperConfig.getConf().isEnable()) {
            generator.createConf();
        }

        if (helperConfig.getDao().isEnable()) {
            generator.createDao();
        }

        if (helperConfig.getExample().isEnable()) {
            generator.createExample();
        }

        if (helperConfig.getXml().isEnable()) {
            generator.createXml();
        }

        if (helperConfig.getService().isEnable()) {
            generator.createService();
        }

        if (helperConfig.getServiceImpl().isEnable()) {
            generator.createServiceImpl();
        }

        return generator;
    }

    private boolean isAbsolutePath(String path) {
        if (path.startsWith("/") || path.indexOf(":") > 0) {
            return true;
        }
        return false;
    }

    private String getPath(String basePath, String path, String packageName) {
        String filePath = "";
        // 判断path是否为根目录
        if (isAbsolutePath(path)) {
            filePath = path;
        } else {
            filePath = basePath + "/" + path;
        }
        String packagePath = packageName.replaceAll("\\.", "/");
        filePath = filePath + "/" + packagePath + "/";
        return filePath;
    }

    private void createEntity() {
        String template = helperConfig.getTemplates() + "/" + helperConfig.getEntity().getTemplate();
        createBase("生成Entity",
                helperConfig.getEntity().getPath(),
                helperConfig.getEntity().getPkg(),
                helperConfig.getEntity().getSuffix() + ".java",
                template,
                true
        );
    }

    private void createDao() {
        String template = helperConfig.getTemplates() + "/" + helperConfig.getDao().getTemplate();
        createBase("生成Mapper",
                helperConfig.getDao().getPath(),
                helperConfig.getDao().getPkg(),
                helperConfig.getDao().getSuffix() + ".java",
                template,
                true
        );
    }

    private void createXml() {
        String template = helperConfig.getTemplates() + "/" + helperConfig.getXml().getTemplate();
        createBase("生成XMLMapper",
                helperConfig.getXml().getPath(),
                helperConfig.getXml().getPkg(),
                helperConfig.getXml().getSuffix() + ".xml",
                template,
                true
        );
    }

    private void createService() {
        String template = helperConfig.getTemplates() + "/" + helperConfig.getService().getTemplate();
        createBase("生成Service",
                helperConfig.getService().getPath(),
                helperConfig.getService().getPkg(),
                helperConfig.getService().getSuffix() + ".java",
                template,
                true
        );
    }

    private void createServiceImpl() {
        String template = helperConfig.getTemplates() + "/" + helperConfig.getServiceImpl().getTemplate();
        createBase("生成ServiceImpl",
                helperConfig.getServiceImpl().getPath(),
                helperConfig.getServiceImpl().getPkg(),
                helperConfig.getServiceImpl().getSuffix() + ".java",
                template,
                true
        );
    }

    private void createExample() {
        String template = helperConfig.getTemplates() + "/" + helperConfig.getExample().getTemplate();
        createBase("生成Sample",
                helperConfig.getExample().getPath(),
                helperConfig.getExample().getPkg(),
                helperConfig.getExample().getSuffix() + ".java",
                template,
                true
        );
    }

    private void createConf() {
        String template = helperConfig.getTemplates() + "/" + helperConfig.getConf().getTemplate();
        createBase("生成Conf",
                helperConfig.getConf().getPath(),
                helperConfig.getConf().getPkg(),
                helperConfig.getConf().getSuffix() + ".java",
                template,
                true
        );
    }

    private void createBase(String logTags, String path, String pkg, String suffix, String template, boolean override) {
        if (Strings.isNullOrEmpty(path)) {
            log.error("{}:path不能为空", logTags);
            return;
        }

        if (Strings.isNullOrEmpty(pkg)) {
            log.error("{}:package不能为空", logTags);
            return;
        }

        String filePath = getPath(helperConfig.getPath(), path, pkg);
        createOther(template, override, filePath, suffix, null);
    }

    /**
     * 生成其它类型文件
     *
     * @param template       模板
     * @param override       是否覆盖
     * @param filePath       生成后的文件路径，如果是相对路径，则和根目录拼在一起组成最后目录，支持包目录
     * @param fileNameSuffix 生成文件的后缀，该后缀包含后缀名，如.java，生成后的文件名规则是：表名+后缀
     * @param params         其它参数，模板里面用other.xxx来引用
     */
    private void createOther(String template, boolean override, String filePath, String fileNameSuffix, Map<String, Object> params) {
        for (FreemarkerModel model : models) {
            if (model.getOthers() == null) {
                model.setOthers(params);
            } else if (params != null) {
                model.getOthers().putAll(params);
            }

            String endPath = null;
            if (isAbsolutePath(filePath)) {
                endPath = filePath;
            } else {
                endPath = helperConfig.getPath() + "/" + filePath.replaceAll("\\.", "/");
            }

            String fileName = model.getTable().getName() + fileNameSuffix;
            FreemarkerUtil.createFile(template, endPath, fileName, model, override);
        }

    }
}
