package easyJ.database.session;

import java.sql.Connection;
import easyJ.database.connection.*;
import easyJ.database.dao.*;
import java.sql.SQLException;
import easyJ.common.EasyJException;
import java.util.ArrayList;
import easyJ.common.BeanUtil;
import easyJ.logging.EasyJLog;
import easyJ.database.dao.command.UpdateCommand;
import easyJ.database.dao.command.UpdateItem;
import easyJ.database.dao.command.SelectCommand;

public class SessionImpl implements Session {
    private Connection conn;

    private static DAO dao;

    private String[] accurateProperties;

    public void setAccurateProperties(String[] properties) {
        accurateProperties = properties;
        dao.setAccurateProperties(accurateProperties);
    }

    public SessionImpl() throws easyJ.common.EasyJException {
        conn = ConnectionControllerFactory.getConnection();
        dao = DAOFactory.getDAO();
    }

    public Object get(Object o) throws easyJ.common.EasyJException {
        return dao.get(o, conn);
    }

    public Object create(Object o) throws easyJ.common.EasyJException {
        return dao.create(o, conn);
    }

    public void update(Object o) throws easyJ.common.EasyJException {
        dao.update(o, conn);
    }

    public void update(UpdateCommand ucmd) throws easyJ.common.EasyJException {
        dao.update(ucmd, conn);
    }

    /* 在这里删除只是改变状态，并不是真正的删除，所以调用的是update */
    public void delete(Object o) throws easyJ.common.EasyJException {
        UpdateCommand ucmd = DAOFactory.getUpdateCommand(o.getClass());
        UpdateItem ui = new UpdateItem("useState", "N");
        ucmd.addUpdateItem(ui);
        String primaryKey = (String) BeanUtil.getPubFieldValue(o,
                easyJ.common.Const.PRIMARY_KEY);
        Filter filter = DAOFactory.getFilter(primaryKey, SQLOperator.EQUAL,
                BeanUtil.getFieldValue(o, primaryKey));
        ucmd.setFilter(filter);
        EasyJLog.debug(ucmd.getExecutableSQL());
        dao.update(ucmd, conn);
    }

    /* 根据主键批量删除 */
    public void deleteBatch(Class clazz, Object[] primaryKeys)
            throws easyJ.common.EasyJException {
        UpdateCommand ucmd = DAOFactory.getUpdateCommand(clazz);
        UpdateItem ui = new UpdateItem("useState", "N");
        ucmd.addUpdateItem(ui);
        Long[] primaryKeysLong = new Long[primaryKeys.length];
        for (int i = 0; i < primaryKeys.length; i++)
            primaryKeysLong[i] = new Long(primaryKeys[i].toString());
        String primaryKey = (String) BeanUtil.getPubStaticFieldValue(clazz,
                easyJ.common.Const.PRIMARY_KEY);
        Filter filter = DAOFactory.getFilter(primaryKey, SQLOperator.IN,
                primaryKeysLong);
        ucmd.setFilter(filter);
        EasyJLog.debug(ucmd.getExecutableSQL());
        dao.update(ucmd, conn);
    }

    /* 根据主键批量删除 */
    public void deleteBatch(Object condition)
            throws easyJ.common.EasyJException {
        // UpdateCommand ucmd=DAOFactory.getUpdateCommand(condition);
        // UpdateItem ui=new UpdateItem("useState","N");
        // ucmd.addUpdateItem(ui);
        // EasyJLog.debug(ucmd.getExecutableSQL());
        dao.deleteBatch(condition, conn);
    }

    public ArrayList query(Object o) throws EasyJException {
        return dao.query(o, conn);
    }

    public ArrayList query(Object o, OrderRule[] orderRules)
            throws EasyJException {
        return dao.query(o, orderRules, conn);
    }

    public Page query(Object o, int page, OrderRule[] orderRules)
            throws EasyJException {
        return dao.query(o, page, orderRules, conn);
    }

    public Page query(Object o, int page) throws EasyJException {
        return dao.query(o, page, conn);
    }

    public ArrayList query(Object lower, Object upper, OrderRule[] orderRules)
            throws EasyJException {
        return dao.query(lower, upper, orderRules, conn);
    }

    public ArrayList query(Object lower, Object upper) throws EasyJException {
        return dao.query(lower, upper, conn);
    }

    public Page query(Object lower, Object upper, int page,
            OrderRule[] orderRules) throws EasyJException {
        return dao.query(lower, upper, page, orderRules, conn);
    }

    public Page query(String className, String condition,
            String orderbyClauses, int currentPageNo) throws EasyJException {
        return dao.query(className, condition, orderbyClauses, currentPageNo,
                conn);
    }

    public ArrayList query(String className, String conditions,
            String orderbyClauses) throws EasyJException {
        return dao.query(className, conditions, orderbyClauses, conn);
    }

    public Page query(Object lower, Object upper, int page)
            throws EasyJException {
        return dao.query(lower, upper, page, conn);
    }

    public ArrayList query(SelectCommand scmd)
            throws easyJ.common.EasyJException {
        return dao.query(scmd, conn);
    }

    /**
     * 根据查询命令看符合查询条件的数据有多少个。
     * 
     * @param scmd
     *                SelectCommand 要执行的查询命令
     * @throws EasyJException
     * @return Long 返回的条数
     */
    public Long getCount(SelectCommand scmd) throws EasyJException {
        return dao.getCount(scmd, conn);
    }

    /**
     * 根据查询的上下界看符合查询条件的数据有多少个。
     * 
     * @param lower
     *                Object 用来封装查询条件的下界
     *                例如：如果查询年龄在30到40岁之间的人，那么在lower的age属性中存放值为30
     * @param upper
     *                Object 用来封装查询条件的上界
     *                例如：如果查询年龄在30到40岁之间的人，那么在lower的age属性中存放值为40
     * @throws EasyJException
     * @return Long 返回的条数
     */
    public Long getCount(Object lower, Object upper) throws EasyJException {
        return dao.getCount(lower, upper, conn);
    }

    /**
     * 根据查询条件所处的对象查看符合查询条件的数据有多少个。
     * 
     * @param object
     *                Object 用来封装查询条件的下界
     *                例如：如果查询年龄在30到40岁之间的人，那么在lower的age属性中存放值为30
     * @throws EasyJException
     * @return Long 返回的条数
     */
    public Long getCount(Object object) throws EasyJException {
        return dao.getCount(object, conn);
    }

    public ArrayList query(SelectCommand scmd, OrderRule[] orderRules)
            throws easyJ.common.EasyJException {
        return dao.query(scmd, conn);
    }

    public UpdateCommand getUpdateCommand(Class clazz)
            throws easyJ.common.EasyJException {
        return DAOFactory.getUpdateCommand(clazz);
    }

    public SelectCommand getSelectCommand(Class clazz)
            throws easyJ.common.EasyJException {
        return DAOFactory.getSelectCommand(clazz);
    }

    public Transaction beginTransaction() throws easyJ.common.EasyJException {
        try {
            conn.setAutoCommit(false);
        } catch (SQLException ex) {
            ex.printStackTrace();
            String clientMessage = "服务器忙";
            String location = "easyJ.database.session.SessionImpl.beginTransaction()";
            String logMessage = "设置事务非自动提交出错,错误信息：" + ex.getMessage();
            EasyJException ee = new EasyJException(ex, location, logMessage,
                    clientMessage);
            throw ee;
        }

        Transaction transaction = TransactionFactory.getTransaction();
        return transaction;
    }

    public void close() throws easyJ.common.EasyJException {
        try {
            if (conn != null)
                conn.close();
            conn = null;
        } catch (SQLException se) {

        }
    }

}
