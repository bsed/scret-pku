package easyJ.database.dao;

import java.sql.Connection;
import easyJ.common.EasyJException;
import java.util.ArrayList;
import easyJ.database.dao.command.UpdateCommand;
import easyJ.database.dao.command.SelectCommand;

public interface DAO {
    public Object create(Object o, Connection conn) throws EasyJException;

    public Object update(Object o, Connection conn) throws EasyJException;

    public void update(UpdateCommand ucmd, Connection conn)
            throws EasyJException;

    public void setAccurateProperties(String[] properties);

    public Object get(Object o, Connection conn) throws EasyJException;

    public ArrayList query(Object o, Connection conn) throws EasyJException;

    public ArrayList query(Object o, OrderRule[] orderRules, Connection conn)
            throws EasyJException;

    public ArrayList query(Object lower, Object upper, Connection conn)
            throws EasyJException;

    /* 此方法可以支持用户自定义的条件和排序字段 */
    public Page query(String className, String condition,
            String orderbyClauses, int currentPageNo, Connection conn)
            throws EasyJException;

    public ArrayList query(String className, String conditions,
            String orderbyClauses, Connection conn) throws EasyJException;

    public ArrayList query(Object lower, Object upper, OrderRule[] orderRules,
            Connection conn) throws EasyJException;

    public Page query(Object o, int page, Connection conn)
            throws EasyJException;

    public Page query(Object o, int page, OrderRule[] orderRules,
            Connection conn) throws EasyJException;

    public Page query(Object lower, Object upper, int page, Connection conn)
            throws EasyJException;

    public Page query(Object lower, Object upper, int currentPageNo,
            OrderRule[] orderRules, Connection conn) throws EasyJException;

    public void delete(Object o, Connection conn) throws EasyJException;

    /**
     * 按照条件批量删除，一定要慎重使用。
     * 
     * @param o
     * @param conn
     * @throws EasyJException
     */
    public void deleteBatch(Object o, Connection conn) throws EasyJException;

    public Long getCount(SelectCommand scmd, Connection conn)
            throws EasyJException;

    public Long getCount(Object lower, Object upper, Connection conn)
            throws EasyJException;

    public Long getCount(Object o, Connection conn) throws EasyJException;

    public ArrayList query(SelectCommand scmd, Connection conn)
            throws easyJ.common.EasyJException;

    public ArrayList query(SelectCommand scmd, OrderRule[] orderRules,
            Connection conn) throws easyJ.common.EasyJException;
}
