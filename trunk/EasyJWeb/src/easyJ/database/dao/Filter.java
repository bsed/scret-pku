package easyJ.database.dao;

import easyJ.common.EasyJException;
import java.util.ArrayList;

public interface Filter extends java.lang.Cloneable, java.io.Serializable {
    public void setAccurateProperties(String[] properties);
    /*
    * @param view  指示是否操作的是视图，如果操作的是视图的话，在where中可以有视图的内容，否则，不能有视图的字段。
    *              删除操作，更新操作就不是操作的视图，所以需要指明false
    */
    public Filter buildFilter(Object o, boolean isView) throws EasyJException;

    public Filter buildFilter(Object lower, Object upper, boolean isView) throws EasyJException;

    public ArrayList getParameters();

    public void addFilter(Filter f) throws EasyJException;

    public void addFilter(Filter f, LogicOperator op) throws EasyJException;

    public String getProperty();

    public void setProperty(String property);

    public SQLOperator getOperator();

    public void setOperator(SQLOperator operator);

    public Object[] getValues();

    public void setValues(Object value);

    public void setValues(Object value, Object value1);

    public Object clone();


    public String getSQL();

    
    public ArrayList getParameter();

    public String getExecutableSQL();
    
    public ArrayList getFilters();
}
