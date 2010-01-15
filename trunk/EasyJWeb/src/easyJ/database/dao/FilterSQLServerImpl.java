package easyJ.database.dao;

import java.lang.reflect.Field;
import java.util.ArrayList;

import easyJ.common.BeanUtil;
import easyJ.common.EasyJException;
import easyJ.database.ColumnPropertyMapping;

public class FilterSQLServerImpl implements Filter, java.io.Serializable {
    private java.util.ArrayList filters = new ArrayList();

    private String property;

    private SQLOperator operator;

    private Object[] values;

    private java.util.ArrayList logicOperators = new ArrayList();

    private String[] accurateProperties;

    public void setAccurateProperties(String[] properties) {
        this.accurateProperties = properties;
    }

    public FilterSQLServerImpl() {}

    public FilterSQLServerImpl(String property, SQLOperator operator,
            Object value) {
        this.property = property;
        this.operator = operator;
        this.values = new Object[1];
        this.values[0] = value;
    }

    public FilterSQLServerImpl(String property, SQLOperator operator,
            Object value, Object value1) {
        this.property = property;
        this.operator = operator;
        this.values = new Object[2];
        this.values[0] = value;
        this.values[1] = value1;
    }

    public ArrayList getFilters() {
        return filters;
    }
    
    /*
     * 增加一个filter和当前已有的Filter一起，其关系为AND的关系
     */
    public void addFilter(Filter f) throws EasyJException {
        //如果增加的是一个带有子filter的，则需要增加子filter。
        if(f.getFilters()!=null && f.getFilters().size()!=0) {
            ArrayList list=f.getFilters();
            for(int i=0;i<list.size();i++) {
                addFilter((Filter)list.get(i));
            }
        } else
            filters.add(f);
        // 第一个加入的filter不需要逻辑运算符
        if (filters.size() != 1)
            logicOperators.add(LogicOperator.AND);
    }

    // 新加的LogicOperator代表此LogicOperator与之前的filter之间的关系
    // 例如：原来已经有了 A and B。那么加入addFilter(C,or)之后变成A and B or C
    public void addFilter(Filter f, LogicOperator op) throws EasyJException {
        //如果增加的是一个带有子filter的，则需要增加子filter。
        if(f.getFilters()!=null && f.getFilters().size()!=0) {
            ArrayList list=f.getFilters();
            for(int i=0;i<list.size();i++) {
                addFilter((Filter)list.get(i));
            }
        } else
            filters.add(f);
        if (filters.size() != 1)
            logicOperators.add(op);
    }

    public Object clone() {
        return null;
    }

    public String getSQL(){

        StringBuffer sql = new StringBuffer();
        // 如果有子filter则处理子filter，本filter为一个底层的filter，处理filter本身的属性。
        if (filters.size() != 0) {
            for (int i = 0; i < filters.size() - 1; i++) {
                Filter filter = (Filter) filters.get(i);
                LogicOperator logicOperator = (LogicOperator) logicOperators
                        .get(i);
                sql.append(filter.getSQL());
                sql.append(" ");
                sql.append(logicOperator.toString());
                sql.append(" ");
            }
            Filter filter = (Filter) filters.get(filters.size() - 1);
            sql.append(filter.getSQL());
        } else if (property != null) {
            String column = ColumnPropertyMapping.getColumnName(property);

            sql.append(column);
            sql.append(" ");
            sql.append(operator.toString());
            sql.append(" ");
            // 只有between才有两个参数
            if (operator.getDimension() == 2) {
                sql.append("? and ? ");
            } else if (operator.getDimension() == 1) {
                if (operator.equals(SQLOperator.IN)) {
                    sql.append("(");
                    Object[] inParameters = (Object[]) values[0];
                    /* 最后一个没有“,”注意循环的边界 */
                    for (int i = 0; i < inParameters.length - 1; i++) {
                        sql.append("?,");
                    }
                    sql.append("?");
                    sql.append(")");
                } else
                    sql.append("?");
            }
        } else if (property == null) {
            return "";
        }

        return sql.toString();
    }

    public ArrayList getParameters() {

        ArrayList list = new ArrayList();
        if (filters.size() == 0) {
            if (values == null)
                return list;
            if (operator.equals(SQLOperator.IN)) {
                Object[] inParameters = (Object[]) values[0];
                for (int i = 0; i < inParameters.length; i++) {
                    list.add(inParameters[i]);
                }
            } else
                list.add(values[0]);
        } else {
            for (int i = 0; i < filters.size(); i++) {
                Filter filter = (Filter) filters.get(i);
                list.addAll((filter.getParameters()));
            }
        }
        return list;
    }

    
    public Filter buildFilter(Object o ,boolean isView) throws EasyJException {
        Class oclass = o.getClass();

        Field fields[] = oclass.getDeclaredFields();
        ArrayList paramNameList = new ArrayList();
        ArrayList paramValueList = new ArrayList();
        Filter filter = DAOFactory.getFilter();
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            String fieldName = field.getName();
            /* 当字段不是static，内容不为空，而且不是集合类型的(这里用了ArrayList来代替) */
            if (!java.lang.reflect.Modifier.isStatic(field.getModifiers())
                    && BeanUtil.getFieldValue(o, fieldName) != null
                    && BeanUtil.getSubClass(oclass, fieldName) == null) {
                //如果是对表操作，则在where语句中不能出现视图的字段。
                if(!isView)
                  if(!BeanUtil.isUpdatable(oclass, fieldName)) continue;
                paramNameList.add(fieldName);
                paramValueList.add(BeanUtil.getFieldValue(o, fieldName));
            }
        }
        for (int i = 0; i < paramValueList.size(); i++) {
            if (paramValueList.get(i) instanceof String) {
                boolean accurate = false;
                String property = (String) paramNameList.get(i);
                if (accurateProperties != null)
                    for (int j = 0; j < accurateProperties.length; j++) {
                        if (property.equals(accurateProperties[j]))
                            accurate = true;
                        break;
                    }
                Filter filter1 = null;
                if (!accurate)
                    filter1 = DAOFactory.getFilter((String) paramNameList
                            .get(i), SQLOperator.LIKE, "%"
                            + paramValueList.get(i) + "%");
                else
                    filter1 = DAOFactory.getFilter((String) paramNameList
                            .get(i), SQLOperator.EQUAL, paramValueList.get(i));
                filter.addFilter(filter1);
            } else {
                Filter filter1 = DAOFactory.getFilter((String) paramNameList
                        .get(i), SQLOperator.EQUAL, paramValueList.get(i));
                filter.addFilter(filter1);
            }
        }
        return filter;
    }

    public Filter buildFilter(Object lower, Object upper, boolean isView) throws EasyJException {
        // 如果是一个对象，则调用一个对象的就可以了。
        if (lower == upper)
            return buildFilter(lower,isView);
        Filter filter = DAOFactory.getFilter();
        Class oclass = lower.getClass();
        Field fields[] = oclass.getDeclaredFields();
        ArrayList paramNameList = new ArrayList();
        ArrayList paramLowerValueList = new ArrayList();
        ArrayList paramUpperValueList = new ArrayList();
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            String fieldName = field.getName();
            if (!java.lang.reflect.Modifier.isStatic(field.getModifiers())
                    && (BeanUtil.getFieldValue(lower, fieldName) != null || BeanUtil
                            .getFieldValue(upper, fieldName) != null)
                    && BeanUtil.getSubClass(oclass, fieldName) == null) {
                //如果是对表操作，则在where语句中不能出现视图的字段。
                if(!isView)
                  if(!BeanUtil.isUpdatable(oclass, fieldName)) continue;
                paramNameList.add(fieldName);
                paramLowerValueList.add(BeanUtil
                        .getFieldValue(lower, fieldName));
                paramUpperValueList.add(BeanUtil
                        .getFieldValue(upper, fieldName));
            }
        }
        for (int i = 0; i < paramLowerValueList.size(); i++) {
            if (paramLowerValueList.get(i) instanceof String) {
                Filter filter1 = DAOFactory.getFilter((String) paramNameList
                        .get(i), SQLOperator.LIKE, "%"
                        + paramLowerValueList.get(i) + "%");
                filter.addFilter(filter1);
            } else {
                if (paramLowerValueList.get(i) == null) {
                    Filter filter1 = DAOFactory.getFilter(
                            (String) paramNameList.get(i),
                            SQLOperator.LESS_EQUAL, paramUpperValueList.get(i));
                    filter.addFilter(filter1);
                } else if (paramUpperValueList.get(i) == null) {
                    Filter filter1 = DAOFactory.getFilter(
                            (String) paramNameList.get(i),
                            SQLOperator.LARGER_EQUAL, paramLowerValueList
                                    .get(i));
                    filter.addFilter(filter1);
                } else {
                    if (paramLowerValueList.get(i).equals(
                            paramUpperValueList.get(i))) {
                        Filter filter1 = DAOFactory.getFilter(
                                (String) paramNameList.get(i),
                                SQLOperator.EQUAL, paramUpperValueList.get(i));
                        filter.addFilter(filter1);
                    } else {
                        Filter filter1 = DAOFactory.getFilter(
                                (String) paramNameList.get(i),
                                SQLOperator.LESS_EQUAL, paramUpperValueList
                                        .get(i));
                        Filter filter2 = DAOFactory.getFilter(
                                (String) paramNameList.get(i),
                                SQLOperator.LARGER_EQUAL, paramLowerValueList
                                        .get(i));
                        filter.addFilter(filter1);
                        filter.addFilter(filter2);
                    }
                }
            }
        }
        return filter;
    }

    public String getExecutableSQL() {
        StringBuffer sql = new StringBuffer();
        // 如果有子filter则处理filter，本filter为一个底层的filter，处理filter本身的属性。
        // 两个filter之间会有一个LogicOperation。LogicOperator比filter的数量少一个
        if (filters.size() != 0) {
            for (int i = 0; i < filters.size() - 1; i++) {
                Filter filter = (Filter) filters.get(i);
                LogicOperator logicOperator = (LogicOperator) logicOperators
                        .get(i);
                sql.append(filter.getExecutableSQL());
                sql.append(logicOperator.toString());
                sql.append(" ");
            }
            Filter filter = (Filter) filters.get(filters.size() - 1);
            sql.append(filter.getExecutableSQL());
        } else if (property != null) {
            String column = ColumnPropertyMapping.getColumnName(property);
            sql.append(column);
            sql.append(" ");
            sql.append(operator.toString());
            sql.append(" ");
            // 只有between才有两个参数
            if (operator.getDimension() == 2) {
                if (values[0] instanceof String
                        || values[0] instanceof java.sql.Date)
                    sql.append("'" + values[0] + "' and '" + values[1] + "' ");
                else
                    sql.append(values[0] + " and " + values[1] + " ");
            } else if (operator.getDimension() == 1) {
                if (values[0] instanceof String
                        || values[0] instanceof java.sql.Date)
                    sql.append("'" + values[0] + "' ");
                else if (values[0] instanceof Object[]) {
                    Object[] inParameters = (Object[]) values[0];
                    for (int i = 0; i < inParameters.length - 1; i++) {
                        if (inParameters[0] instanceof String)
                            sql.append("'" + inParameters[i] + "', ");
                        else
                            sql.append(inParameters[i] + ", ");
                    }
                    if (inParameters[0] instanceof String)
                        sql.append("'" + inParameters[inParameters.length - 1]
                                + "'");
                    else
                        sql.append(inParameters[inParameters.length - 1]);
                } else
                    sql.append(values[0] + " ");
            }
        } else {
            return "";
        }

        return sql.toString();
    }

    public ArrayList getParameter() {
        return null;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public SQLOperator getOperator() {
        return operator;
    }

    public void setOperator(SQLOperator operator) {
        this.operator = operator;
    }

    public Object[] getValues() {
        return values;
    }

    public void setValues(Object value) {
        this.values = new Object[1];
        this.values[0] = value;
    }

    public void setValues(Object value, Object value1) {
        this.values = new Object[2];
        this.values[0] = value;
        this.values[0] = value1;
    }

}
