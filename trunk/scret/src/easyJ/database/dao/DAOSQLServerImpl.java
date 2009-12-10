package easyJ.database.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.StringTokenizer;

import easyJ.common.BeanUtil;
import easyJ.common.Const;
import easyJ.common.EasyJException;
import easyJ.common.validate.GenericValidator;
import easyJ.database.ColumnPropertyMapping;
import easyJ.database.dao.command.DeleteCommand;
import easyJ.database.dao.command.InsertCommand;
import easyJ.database.dao.command.SelectCommand;
import easyJ.database.dao.command.UpdateCommand;
import easyJ.database.dao.command.UpdateItem;

public class DAOSQLServerImpl implements DAO, java.io.Serializable {
    private static Hashtable SQLScriptPool = new Hashtable();

    private static DAOSQLServerImpl instance;

    private String[] accurateProperties;

    private DAOSQLServerImpl() {

    }

    public static DAO getInstance() {
        if (instance == null)
            instance = new DAOSQLServerImpl();
        return instance;
    }

    public void setAccurateProperties(String[] properties) {
        accurateProperties = properties;
    }

    public static Long getTotalNum(String viewName, String condition,
            ArrayList paramList, Connection conn) throws EasyJException {
        ResultSet rs = null;
        try {
            String sql = "select count(*) as count_num from " + viewName;
            if (!"".equals(condition))
                sql = sql + " where " + condition;
            rs = ExecuteSQL.executeQueryResultSet(sql, paramList, conn);
            rs.next();
            long count = rs.getLong("count_num");
            return new Long(count);
        } catch (SQLException e) {
            // TODO 自动生成 catch 块
            e.printStackTrace();
            String clientMessage = "服务器忙";
            String location = "easyJ.database.dao.SQLServerDAOImpl.executeQuery()";
            String logMessage = "SQL语句执行错误,请查看生成的SQL是否正确";
            EasyJException ee = new EasyJException(e, location, logMessage,
                    clientMessage);
            throw ee;
        } finally {
            try {
                if (rs != null) {
                    Statement st = rs.getStatement();
                    rs.close();
                    rs = null;
                    st.close();
                    st = null;
                }
            } catch (Exception e) {

            }

        }

    }

    public Long getCount(Object lower, Object upper, Connection conn)
            throws EasyJException {
        try {
            BeanUtil.setFieldValue(lower, "useState", "Y");
            BeanUtil.setFieldValue(upper, "useState", "Y");
        } catch (Exception e) {

        }
        Filter filter = DAOFactory.getFilter();
        filter.setAccurateProperties(accurateProperties);
        filter = filter.buildFilter(lower, upper,true);
        SelectCommand scmd = DAOFactory.getSelectCommand(lower.getClass());
        scmd.setFilter(filter);
        // 得到总的满足条件的数据量
        Long count = null;
        ArrayList paramList = new ArrayList();
        if (filter != null) {
            String totalNumStr = filter.getSQL();
            paramList = filter.getParameters();
            count = getTotalNum(scmd.getViewName(), totalNumStr, paramList,
                    conn);
        } else {
            count = getTotalNum(scmd.getViewName(), "", paramList, conn);
        }
        accurateProperties = null;
        return count;

    }

    public Long getCount(SelectCommand scmd, Connection conn)
            throws EasyJException {
        Filter filter = scmd.getFilter();
        Long count = null;
        ArrayList paramList = new ArrayList();
        if (filter != null) {
            String totalNumStr = filter.getSQL();
            paramList = filter.getParameters();
            count = getTotalNum(scmd.getViewName(), totalNumStr, paramList,
                    conn);
        } else {
            count = getTotalNum(scmd.getViewName(), "", paramList, conn);
        }
        return count;
    }

    public Long getCount(Object o, Connection conn) throws EasyJException {
        return getCount(o, o, conn);
    }

    public void delete(Object o, Connection conn) throws EasyJException {
        Class oclass = o.getClass();
        String sql = "delete from "
                + BeanUtil.getTableName(o)
                + " where "
                + ColumnPropertyMapping.getColumnName(BeanUtil
                        .getPrimaryKeyName(oclass)) + " =?";
        ArrayList paramValueList = new ArrayList();
        paramValueList.add(BeanUtil.getFieldValue(o, BeanUtil
                .getPrimaryKeyName(oclass)));
        ExecuteSQL.executeUpdate(sql, paramValueList, conn);
    }

    public void deleteBatch(Object o, Connection conn) throws EasyJException {
        DeleteCommand dcmd = DAOFactory.getDeleteCommand(o);
        Filter filter = DAOFactory.getFilter();
        filter.setAccurateProperties(accurateProperties);
        filter = filter.buildFilter(o,false);
        dcmd.setFilter(filter);
        dcmd.execute(conn);
        accurateProperties = null;
    }

    public Object get(Object o, Connection conn) throws EasyJException {
        ArrayList list = query(o, conn);
        if (list.size() == 0) {
            EasyJException ee = new EasyJException();
            String location = "SQLServerDAOImpl.getObject(Object o,Connection conn)";
            String clientMessage = "您查找的数据不存在";
            throw ee;
        }
        return list.get(0);
    }

    public Object update(Object o, Connection conn) throws EasyJException {
        UpdateCommand ucmd = DAOFactory.getUpdateCommand(o);
        /* 将updateTime,buildTime设为当前时间。所有的表都应该有这两个字段。useState设为Y */
        try {
            BeanUtil.setFieldValue(o, "updateTime", new java.sql.Date(System
                    .currentTimeMillis()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            BeanUtil.setFieldValue(o, "useState", "Y");
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 在返回之前是不是需要对从数据库中读取o，这里简略了，应该是从数据库按照主键再读一次的。
        ucmd.execute(conn);
        return o;
    }

    public void update(UpdateCommand ucmd, Connection conn)
            throws EasyJException {
        /* 下面用来默认的设置更新时间 */
        UpdateItem ui = new UpdateItem();
        ui.setProperty("updateTime");
        ui.setValue(new java.sql.Date(System.currentTimeMillis()));
        ucmd.addUpdateItem(ui);

        ucmd.execute(conn);
    }

    public Object create(Object o, Connection conn) throws EasyJException {
        try {
            BeanUtil.setFieldValue(o, "useState", "Y");
        } catch (Exception e) {
            // e.printStackTrace();
        }
        /* 将updateTime,buildTime设为当前时间。所有的表都应该有这两个字段。useState设为Y */
        try {
            BeanUtil.setFieldValue(o, "updateTime", new java.sql.Date(System
                    .currentTimeMillis()));
            BeanUtil.setFieldValue(o, "buildTime", new java.sql.Date(System
                    .currentTimeMillis()));
        } catch (Exception e) {}
        InsertCommand icmd = DAOFactory.getInsertCommand(o);
        Long no = (Long) icmd.execute(conn);
        BeanUtil.setFieldValue(o, BeanUtil.getPrimaryKeyName(o.getClass()), no);
        return o;
    }

    public ArrayList query(Object o, Connection conn) throws EasyJException {

        return query(o, getDefaultOrderRule(o.getClass()), conn);
    }

    public ArrayList query(Object o, OrderRule[] orderRules, Connection conn)
            throws EasyJException {
        try {
            BeanUtil.setFieldValue(o, "useState", "Y");
        } catch (Exception e) {

        }
        Filter filter = DAOFactory.getFilter();
        filter.setAccurateProperties(accurateProperties);
        filter = filter.buildFilter(o,true);
        SelectCommand scmd = DAOFactory.getSelectCommand(o.getClass());
        /* 如果没有排序，则默认为按照主键排序 */
        if (orderRules == null)
            orderRules = getDefaultOrderRule(o.getClass());
        if (orderRules != null) {
            for (int i = 0; i < orderRules.length; i++)
                scmd.addOrderRule(orderRules[i]);
        }
        scmd.setFilter(filter);
        String sql = scmd.getSQL();
        // EasyJLog.debug(scmd.getExecutableSQL());

        ArrayList result = (ArrayList) scmd.execute(conn);
        accurateProperties = null;
        return result;
    }

    public ArrayList query(SelectCommand scmd, Connection conn)
            throws easyJ.common.EasyJException {
        return query(scmd, null, conn);
    }

    public ArrayList query(SelectCommand scmd, OrderRule[] orderRules,
            Connection conn) throws easyJ.common.EasyJException {
        String sql = scmd.getSQL();
        if (orderRules != null) {
            for (int i = 0; i < orderRules.length; i++)
                scmd.addOrderRule(orderRules[i]);
        }
        // EasyJLog.debug(scmd.getExecutableSQL());
        return (ArrayList) scmd.execute(conn);
    }

    public ArrayList query(Object lower, Object upper, Connection conn)
            throws EasyJException {

        return query(lower, upper, getDefaultOrderRule(lower.getClass()), conn);
    }

    public ArrayList query(Object lower, Object upper, OrderRule[] orderRules,
            Connection conn) throws EasyJException {
        try {
            BeanUtil.setFieldValue(lower, "useState", "Y");
            BeanUtil.setFieldValue(upper, "useState", "Y");
        } catch (Exception e) {

        }
        Filter filter = DAOFactory.getFilter();
        filter.setAccurateProperties(accurateProperties);
        filter = filter.buildFilter(lower, upper,true);
        SelectCommand scmd = DAOFactory.getSelectCommand(lower.getClass());
        /* 如果没有排序，则默认为按照主键排序 */
        if (orderRules == null)
            orderRules = getDefaultOrderRule(lower.getClass());
        if (orderRules != null) {
            for (int i = 0; i < orderRules.length; i++)
                scmd.addOrderRule(orderRules[i]);
        }
        scmd.setFilter(filter);
        // EasyJLog.debug(scmd.getExecutableSQL());
        ArrayList result = (ArrayList) scmd.execute(conn);
        accurateProperties = null;
        return result;
    }

    /*
     * 此方法可以支持用户自定义的条件和排序字段,conditions 的结构为 (propertyName = value and
     * propertyName >= value), condition可以作为查询条件直接执行。 orderbyColumn的结构为
     * propertyName asc, propertyName desc
     */
    public ArrayList query(String className, String conditions,
            String orderbyClauses, Connection conn) throws EasyJException {
        Class clazz = BeanUtil.getClass(className);
        String viewName = (String) BeanUtil.getPubStaticFieldValue(clazz,
                Const.VIEW_NAME);
        String sql = "";
        if (GenericValidator.isBlankOrNull(orderbyClauses))
            sql = "select * from " + viewName + " where " + conditions;
        else
            sql = "select * from " + viewName + " where " + conditions
                    + " order by " + orderbyClauses;
        return ExecuteSQL.executeQuery(sql, null, clazz, conn);
    }

    public Page query(String className, String condition,
            String orderbyClauses, int currentPageNo, Connection conn)
            throws EasyJException {
        Class clazz = BeanUtil.getClass(className);
        Page page = new Page();
        page.setCurrentPageNo(currentPageNo);
        SelectCommand scmd = DAOFactory.getSelectCommand(clazz);
        /* 设置查询条件，代替filter */
        scmd.setCondition(condition);
        scmd.setLimits((currentPageNo - 1) * Page.pageSize, currentPageNo
                * Page.pageSize);
        /* 如果没有排序，则默认为按照主键排序 */
        if (GenericValidator.isBlankOrNull(orderbyClauses)) {
            OrderRule rule = getDefaultOrderRule(clazz)[0];
            scmd.addOrderRule(rule);
        } else {
            StringTokenizer orderbyClausesToken = new StringTokenizer(
                    orderbyClauses, ",");
            while (orderbyClausesToken.hasMoreElements()) {
                String orderbyClause = orderbyClausesToken.nextToken();
                StringTokenizer conditionToken = new StringTokenizer(
                        orderbyClause, " ");
                String propertyName = conditionToken.nextToken();
                OrderDirection direction = new OrderDirection(conditionToken
                        .nextToken());
                OrderRule orderRule = new OrderRule(propertyName, direction);
                scmd.addOrderRule(orderRule);
            }
        }
        ArrayList list = (ArrayList) scmd.execute(conn);
        page.setPageData(list);
        return page;
    }

    public Page query(Object o, int currentPageNo, Connection conn)
            throws EasyJException {
        return query(o, currentPageNo, getDefaultOrderRule(o.getClass()), conn);
    }

    public Page query(Object o, int currentPageNo, OrderRule[] orderRules,
            Connection conn) throws EasyJException {
        try {
            BeanUtil.setFieldValue(o, "useState", "Y");
        } catch (Exception e) {

        }
        Filter filter = DAOFactory.getFilter();
        filter.setAccurateProperties(accurateProperties);
        filter = filter.buildFilter(o,true);
        SelectCommand scmd = DAOFactory.getSelectCommand(o.getClass());
        scmd.setFilter(filter);
        // 得到总的满足条件的数据量
        String totalNumStr = filter.getSQL();
        ArrayList paramList = filter.getParameters();
        Long totalNum = getTotalNum(scmd.getViewName(), totalNumStr, paramList,
                conn);

        Page page = new Page();
        page.setTotalNum(totalNum);
        page.setCurrentPageNo(currentPageNo);
        /* 如果没有排序，则默认为按照主键排序 */
        if (orderRules == null)
            orderRules = getDefaultOrderRule(o.getClass());
        if (orderRules != null) {
            for (int i = 0; i < orderRules.length; i++)
                scmd.addOrderRule(orderRules[i]);
        }
        scmd.setLimits((currentPageNo - 1) * Page.pageSize, currentPageNo
                * Page.pageSize);
        // 因为sql的语句中条件出现了两次，所以参数也要两次。
        paramList.addAll(paramList);
        String sql = scmd.getSQL();
        // EasyJLog.debug(scmd.getExecutableSQL());
        ArrayList list = (ArrayList) scmd.execute(conn);
        page.setPageData(list);
        accurateProperties = null;
        return page;
    }

    public Page query(Object lower, Object upper, int currentPageNo,
            Connection conn) throws EasyJException {
        return query(lower, upper, currentPageNo, getDefaultOrderRule(lower
                .getClass()), conn);
    }

    public Page query(Object lower, Object upper, int currentPageNo,
            OrderRule[] orderRules, Connection conn) throws EasyJException {
        try {
            BeanUtil.setFieldValue(lower, "useState", "Y");
            BeanUtil.setFieldValue(upper, "useState", "Y");
        } catch (Exception e) {

        }
        Filter filter = DAOFactory.getFilter();
        filter.setAccurateProperties(accurateProperties);
        filter = filter.buildFilter(lower, upper,true);
        SelectCommand scmd = DAOFactory.getSelectCommand(lower.getClass());
        scmd.setFilter(filter);
        // 得到总的满足条件的数据量
        Long totalNum = null;
        ArrayList paramList = new ArrayList();
        if (filter != null) {
            String totalNumStr = filter.getSQL();
            paramList = filter.getParameters();
            totalNum = getTotalNum(scmd.getViewName(), totalNumStr, paramList,
                    conn);
        } else {
            totalNum = getTotalNum(scmd.getViewName(), "", paramList, conn);
        }

        Page page = new Page();
        page.setTotalNum(totalNum);
        page.setCurrentPageNo(currentPageNo);
        /* 如果没有排序，则默认为按照主键排序 */
        if (orderRules == null)
            orderRules = getDefaultOrderRule(lower.getClass());
        if (orderRules != null) {
            for (int i = 0; i < orderRules.length; i++)
                scmd.addOrderRule(orderRules[i]);
        }
        scmd.setLimits((currentPageNo - 1) * Page.pageSize, currentPageNo
                * Page.pageSize);
        // 因为sql的语句中条件出现了两次，所以参数也要两次。
        paramList.addAll(paramList);
        // EasyJLog.debug(scmd.getExecutableSQL());
        ArrayList list = (ArrayList) scmd.execute(conn);
        page.setPageData(list);
        accurateProperties = null;
        return page;
    }

    /**
     * 如果用户没有指定按照什么来排序，那么就找到默认的排序 首先如果有sequence字段，则默认按照sequence来排序， 如果没有按照主键来排序。
     * 
     * @param clazz
     * @return
     * @throws EasyJException
     */
    private OrderRule[] getDefaultOrderRule(Class clazz) throws EasyJException {
        String orderByColumn = null;
        OrderDirection direction = null;
        OrderRule orderRule = new OrderRule();
        try {
            // 如果存在sequence，则按照sequence来排序
            BeanUtil.getField(clazz, "sequence");
            orderByColumn = "sequence";
            direction = OrderDirection.ASC;
        } catch (Exception e) {

        }
        String primaryKey = BeanUtil.getPrimaryKeyName(clazz);
        if (orderByColumn == null) {
            orderByColumn = primaryKey;
            direction = OrderDirection.DESC;
        }
        orderRule.setOrderColumn(primaryKey);
        orderRule.setOrderDirection(OrderDirection.DESC);
        OrderRule[] orderRules = {
            orderRule
        };
        return orderRules;
    }

}
