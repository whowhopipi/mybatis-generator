package cn.ruihusoft.mybatis.entity;

import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class TableProperties {

    /**
     * 相关文件名
     */
    private String name;

    /**
     * 表名
     */
    private String code;

    /**
     * 表描述
     */
    private String common;

    /**
     * 列
     */
    private String columnList;

    /**
     * 各个ID列
     */
    private List<ColumnProperties> ids;

    /**
     * 各个列
     */
    private List<ColumnProperties> columns;

    /**
     * 所有列
     */
    private List<ColumnProperties> allColumns;

    /**
     * 其它自定义参数
     */
    private Map<String, String> others = new HashMap<>();
}
