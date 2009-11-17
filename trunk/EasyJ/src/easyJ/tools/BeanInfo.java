package easyJ.tools;

import java.util.ArrayList;

public class BeanInfo implements java.io.Serializable {
    private String tableName;

    private String viewName;

    private String primaryKey;

    private ArrayList tableProperties;

    private ArrayList viewProperties;

    public BeanInfo() {}

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getViewName() {
        return viewName;
    }

    public void setViewName(String viewName) {
        this.viewName = viewName;
    }

    public ArrayList getTableProperties() {
        return tableProperties;
    }

    public void setTableProperties(ArrayList tableProperties) {
        this.tableProperties = tableProperties;
    }

    public ArrayList getViewProperties() {
        return viewProperties;
    }

    public void setViewProperties(ArrayList viewProperties) {
        this.viewProperties = viewProperties;
    }

    public String getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(String primaryKey) {
        this.primaryKey = primaryKey;
    }
}
