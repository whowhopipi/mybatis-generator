package cn.ruihusoft.mybatis.core;

import cn.ruihusoft.mybatis.utils.Generator;
import org.junit.Test;

public class ConfigGenerator {

    @Test
    public void generatorAll() throws Exception {
        Generator.create();
    }

    @Test
    public void generatorAlarm() throws Exception {
        Generator.create("application-alarm.yml");
    }

    @Test
    public void generatorOneTableFirst() throws Exception {
        String tableName = "hc_uicustomization_inf";

        doGenerator("application-first.yml", tableName);
    }

    @Test
    public void generatorOneTableUpdate() throws Exception {
        String tableName = "hc_uicustomization_inf";

        doGenerator("application-update.yml", tableName);
    }

    private void doGenerator(String baseConfig, String tableName) throws Exception {
        Generator.create(baseConfig, "tables/"+tableName.toLowerCase() + ".yml");
    }
}
