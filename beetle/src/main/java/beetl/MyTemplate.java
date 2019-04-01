package beetl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangkaile on 2017/4/6.
 */
public class MyTemplate {
    /**
     * 文件路径
     */
    private String  rootPath;
    /**
     * 包名（模块名）
     */
    private String packageName;
    /**
     * 实体类名
     */
    private String entityClassName;
    /**
     * VO类名
     */
    private String voClassName;
    /**
     * BO类名
     */
    private String boClassName;
    /**
     * 实体对象名
     */
    private String entityName;
    /**
     * 模板名称
     *
     */
    private String templateName;
    /**
     * Controller RequestMapping
     */
    private String packageMapPath;

    private String resourcesPath;

    private String collectionName;
    private List<DataBaseFields> list = new ArrayList<DataBaseFields>();

    public String getRootPath() {
        return rootPath;
    }

    public void setRootPath(String rootPath) {
        this.rootPath = rootPath;
    }


    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getEntityClassName() {
        return entityClassName;
    }

    public void setEntityClassName(String entityClassName) {
        this.entityClassName = entityClassName;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }
    public String getPackagePath(){
        String packagePath = "";
        String[] array = packageName.split("\\.");
        for(String str : array){
            packagePath += str + File.separator;
        }
        return packagePath;
    }
    public void setPackageMapPath(){
        String mapPath = "";

        String[] array = packageName.split("\\.");
        for(String str : array){
            mapPath += "/" + str;
        }
        this.packageMapPath = mapPath;
    }
    public String getPackageMapPath(){
        return packageMapPath;
    }

    public String getCollectionName() {
        return collectionName;
    }

    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }

    public List<DataBaseFields> getList() {
        return list;
    }

    public void setList(List<DataBaseFields> list) {
        this.list = list;
    }

    public String getVoClassName() {
        return voClassName;
    }

    public void setVoClassName(String voClassName) {
        this.voClassName = voClassName;
    }

    public String getBoClassName() {
        return boClassName;
    }

    public void setBoClassName(String boClassName) {
        this.boClassName = boClassName;
    }

    public String getResourcesPath() {
        return resourcesPath;
    }

    public void setResourcesPath(String resourcesPath) {
        this.resourcesPath = resourcesPath;
    }

}
