package beetl;

/**
 * Created by yangkaile on 2017/4/10.
 */
public class DataBaseFields {
    /**
     * 字段名 映射数据库字段
     */
    private String name;
    /**
     * 驼峰命名 首字母小写
     */
    private String camelCaseName;
    /**
     * 驼峰命名 首字母大写
     */
    private String upperCamelCaseName;
    private String type;
    private boolean index;
    private boolean nullable;
    private String description;
    private Object defaultValue;
    private boolean autoIncrement;

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

    public String getUpperCamelCaseName() {
        return upperCamelCaseName;
    }

    public void setUpperCamelCaseName(String upperCamelCaseName) {
        this.upperCamelCaseName = upperCamelCaseName;
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
