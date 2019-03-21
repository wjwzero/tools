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
    private boolean isIndex;
    private boolean isNull;
    private String description;
    private Object defaultValue;
    private boolean autoIncrement;


    public DataBaseFieldsBO(String name, String type, String isIndex, String isNull, String description) {
        this.name = name;
        this.type = type;
        if(type.contains(INT)){
            this.type = "Integer";
        }else if(type.contains("varchar")){
            this.type = "String";
        }
        this.camelCaseName =
        this.description = description;
        this.isIndex = StringUtils.isBlank(isIndex);
        this.isNull = "YES".equals(isNull);
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
        return isIndex;
    }

    public void setIndex(boolean index) {
        isIndex = index;
    }

    public boolean isNull() {
        return isNull;
    }

    public void setNull(boolean aNull) {
        isNull = aNull;
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
                ", isIndex=" + isIndex +
                ", isNull=" + isNull +
                ", defaultValue=" + defaultValue +
                '}';
    }
}
