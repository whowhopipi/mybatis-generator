package cn.ruihusoft.mybatis.utils;

import cn.ruihusoft.mybatis.entity.FreemarkerModel;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileWriter;

@Slf4j
public class FreemarkerUtil {

    public static void createFile(String templateName, String filePath, String fileName, FreemarkerModel dataModel, boolean override) {
        FileWriter out = null;
        try {
            // 通过FreeMarker的Confuguration读取相应的模板文件
            Configuration configuration = new Configuration(Configuration.VERSION_2_3_28);
            // 设置模板路径
            configuration.setClassForTemplateLoading(FreemarkerUtil.class, "/");
            // 设置默认字体
            configuration.setDefaultEncoding("utf-8");
            // 获取模板
            Template template = configuration.getTemplate(templateName);
            File file = new File(filePath + fileName);

            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }

            if(file.exists() && !override) {
                log.warn("文件{}{}已存在", filePath, fileName);
                return;
            }

            //设置输出流
            out = new FileWriter(file);
            //模板输出静态文件
            template.process(dataModel, out);
            out.flush();
            out.close();

            log.info("生成文件成功：{}{}", filePath, fileName);
            return;
        } catch (Exception e) {
            log.error("生成文件时出错：{}{}", filePath, fileName, e);
        }
    }

}
