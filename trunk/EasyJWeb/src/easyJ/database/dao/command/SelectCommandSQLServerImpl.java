package easyJ.database.dao.command;

import easyJ.common.EasyJException;
import java.util.ArrayList;
import easyJ.database.ColumnPropertyMapping;
import easyJ.common.Const;
import easyJ.common.BeanUtil;
import java.sql.Connection;
import easyJ.database.dao.FilterSQLServerImpl;
import easyJ.database.dao.AggregateType;
import easyJ.database.dao.OrderRule;
import easyJ.database.dao.Filter;
import easyJ.database.dao.ExecuteSQL;

public class SelectCommandSQLServerImpl implements SelectCommand,
        java.io.Serializable {

    public SelectCommandSQLServerImpl(Class c) throws EasyJException {
        this.primaryKey = ColumnPropertyMapping.getColumnName((String) BeanUtil
                .getPubStaticFieldValue(c, Const.PRIMARY_KEY));
        this.viewName = (String) BeanUtil.getPubStaticFieldValue(c,
                Const.VIEW_NAME);
        this.selectClass = c;
        groupItems = new ArrayList();
        selectItems = new ArrayList();
        orderRules = new ArrayList();
    }

    protected Class selectClass;

    protected String viewName;

    protected String primaryKey;

    protected ArrayList selectItems;

    protected ArrayList groupItems;

    /* 条件以两种形式存在，一种是以filter的形式存在，另外一种是以condition的形式存在。这两种形式不可能同时存在，如果同时存在则先考虑filter */
    protected Filter filter;

    protected String condition;

    protected ArrayList orderRules;

    long start = -1;

    long size = -1;

    long end = -1;

    public void addSelectItem(String property) throws EasyJException {
        String column = ColumnPropertyMapping.getColumnName(property);
        // 如果是* 或者别的，那么就不会有对应的数据
        if (column == null)
            column = property;
        selectItems.add(column);
    }

    public void setLimits(long start, long end) {
        this.start = start;
        this.size = end - start;
        this.end = end;
    }

    public void addSelectItem(String property, AggregateType type)
            throws EasyJException {
        String column = ColumnPropertyMapping.getColumnName(property);
        // 如果是* 或者别的，那么就不会有对应的数据
        if (column == null)
            column = property;
        selectItems.add(type.toString() + "(" + column + ")");
    }

    public void addGroupItem(String property) throws EasyJException {
        String column = ColumnPropertyMapping.getColumnName(property);
        // 如果是* 或者别的，那么就不会有对应的数据
        if (column == null)
            column = property;
        groupItems.add(column);
    }

    public void addOrderRule(OrderRule orderRule) throws EasyJException {
        orderRules.add(orderRule);
    }

    public Filter getFilter() {
        return filter;
    }

    public void setFilter(Filter filter) {
        this.filter = (FilterSQLServerImpl) filter;
    }

    public String getSQL() throws EasyJException{
        if (filter != null)
            return getSQL(filter.getSQL());
        else if (condition != null)
            return getSQL(condition);
        else
            return getSQL("");
    }

    public String getExecutableSQL() {
        if (filter != null)
            return getSQL(filter.getExecutableSQL());
        else
            return getSQL("");

    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getSQL(String condition) {
        StringBuffer orderRule = new StringBuffer();

        int ruleSize = orderRules.size();
        if (ruleSize > 0) {
            orderRule.append(" order by ");
            for (int i = 0; i < ruleSize - 1; i++) {
                OrderRule rule = (OrderRule) orderRules.get(i);
                orderRule.append(rule.getOrderColumn());
                orderRule.append(" ");
                orderRule.append(rule.getOrderDirection().toString());
                orderRule.append(",");
            }
            OrderRule rule = (OrderRule) orderRules.get(ruleSize - 1);
            orderRule.append(rule.getOrderColumn());
            orderRule.append(" ");
            orderRule.append(rule.getOrderDirection().toString());
        }

        StringBuffer group = new StringBuffer();
        if (groupItems.size() > 0)
            group.append(" group by ");
        for (int i = 0; i < groupItems.size(); i++)
            group.append((String) groupItems.get(i));

        StringBuffer select = new StringBuffer();
        if (selectItems.size() > 0) {
            for (int i = 0; i < selectItems.size() - 1; i++) {
                select.append((String) selectItems.get(i));
                select.append(", ");
            }
            select.append((String) selectItems.get(selectItems.size() - 1));
        } else {
            select.append(" * ");
        }

        StringBuffer sql = new StringBuffer();
        if (size == -1) {
            sql.append("select ");
            sql.append(select);
            sql.append(" from ");
            // sql.append(select);
            sql.append(viewName);
            if (!"".equals(condition)) {
                sql.append(" where ");
                sql.append(condition);
                sql.append(group);
                sql.append(orderRule);
            }

        } else {

            sql.append("select top " + size + " ");
            sql.append(select);
            sql.append(" from ");
            sql.append(viewName);
            sql.append(" where " + primaryKey + " not in (select top " + start
                    + " " + primaryKey + " from ");
            sql.append(viewName);
            if (!"".equals(condition)) {
                sql.append(" where ");
                sql.append(condition);
            }
            sql.append(group);
            sql.append(orderRule);
            sql.append(" ) ");
            if (!"".equals(condition)) {
                sql.append(" and ");
                sql.append(condition);
            }
            sql.append(group);
            sql.append(orderRule);
        }
        return sql.toString();
    }

    public String getViewName() {
        return viewName;
    }

    public Class getSelectClass() {
        return selectClass;
    }

    public void setSelectClass(Class selectClass) {
        this.selectClass = selectClass;
    }

    public Object execute(Connection conn) throws EasyJException {
        if (filter != null)
            return ExecuteSQL.executeQuery(getSQL(), filter.getParameters(),
                    selectClass, conn);
        else
            return ExecuteSQL.executeQuery(getSQL(), null, selectClass, conn);
    }

}
