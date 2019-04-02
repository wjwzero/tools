package com.tools.auto.domain.bo;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by yangkaile on 2017/4/10.
 */
public class DataBaseFieldsBO {
    public static final String INT = "int";
    private String name;
    // 驼峰命名
    private String camelCaseName;
    private String type;
    private boolean index;
    private boolean nullable;
    private String description;
    private Object defaultValue;
    private boolean autoIncrement;


    public DataBaseFieldsBO(String name, String type, String index, String nullable, String camelCaseName, String description) {
        this.name = name;
        this.type = type;
        if(type.contains("bigint")){
            this.type = "Long";
        }else if(type.equals(INT)){
            this.type = "Integer";
        }else if(type.contains("varchar")){
            this.type = "String";
        }else if(type.contains("timestamp")){
            this.type = "Date";
        }
        this.camelCaseName = camelCaseName;
        this.description = description;
        this.index = !StringUtils.isBlank(index);
        this.nullable = "YES".equals(nullable);
    }

    public boolean isAutoIncrement() {
        return autoIncrement;
    }

    public void setAutoIncrement(boolean autoIncrement) {
        this.autoIncrement = autoIncrement;
    }

    public String getCamelCaseName() {
        return camelCaseName;
    }

    public void setCamelCaseName(String camelCaseName) {
        this.camelCaseName = camelCaseName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isIndex() {
        return index;
    }

    public void setIndex(boolean index) {
        this.index = index;
    }

    public boolean isNullable() {
        return nullable;
    }

    public void setNullable(boolean nullable) {
        this.nullable = nullable;
    }

    public Object getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(Object defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "DataBaseFields{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", index=" + index +
                ", nullable=" + nullable +
                ", defaultValue=" + defaultValue +
                '}';
    }
}
