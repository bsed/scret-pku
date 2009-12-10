package easyJ.business.facade;

import easyJ.common.EasyJException;
import java.util.ArrayList;
import easyJ.database.dao.OrderRule;
import easyJ.database.dao.command.SelectCommand;
import easyJ.database.dao.Page;
import easyJ.database.dao.command.UpdateCommand;

public interface CommonFacade {

    public void setAccurateProperties(String[] properties);

    public Object create(Object o) throws easyJ.common.EasyJException;

    public void update(Object o) throws easyJ.common.EasyJException;

    public void delete(Object o) throws easyJ.common.EasyJException;

    public ArrayList query(Object o) throws EasyJException;

    public void deleteBatch(Class clazz, String[] primaryKeys)
            throws easyJ.common.EasyJException;

    public ArrayList query(Object o, OrderRule[] orderRules)
            throws EasyJException;

    public ArrayList query(Object lower, Object upper) throws EasyJException;

    public Page query(String className, String condition,
            String orderbyClauses, int currentPageNo) throws EasyJException;

    public ArrayList query(String className, String conditions,
            String orderbyClauses) throws EasyJException;

    public ArrayList query(Object lower, Object upper, OrderRule[] orderRules)
            throws EasyJException;

    public Page query(Object o, int page) throws EasyJException;

    public Page query(Object o, int page, OrderRule[] orderRules)
            throws EasyJException;

    public Page query(Object lower, Object upper, int page)
            throws EasyJException;

    public Page query(Object lower, Object upper, int currentPageNo,
            OrderRule[] orderRules) throws EasyJException;

    /**
     * 用来查询数据，用于一些特殊条件的查询，如果指示简单的查询，推荐使用query(Object)
     * 
     * @param scmd
     *                SelectCommand 进行查询的查询条件。
     * @see <a href="../dao/command/SelectCommand.html">SelectCommand</a>
     * @throws EasyJException
     * @return ArrayList 返回符合条件的所有数据集合
     */
    public ArrayList query(SelectCommand scmd)
            throws easyJ.common.EasyJException;

    /**
     * 用来查询数据，用于一些特殊条件的查询，如果指示简单的查询，推荐使用query(Object)
     * 
     * @param scmd
     *                SelectCommand 进行查询的查询条件。
     * @see <a href="../dao/command/SelectCommand.html">SelectCommand</a>
     * @see <a href="../dao/command/Filter.html">Filter</a>
     * @param orderRules
     *                OrderRule[] 用来进行排序的OrderRule数组
     * @see <a href="../dao/OrderRule.html">OrderRule</a>
     * @throws EasyJException
     * @return ArrayList 返回符合条件的所有数据集合
     */
    public ArrayList query(SelectCommand scmd, OrderRule[] orderRules)
            throws easyJ.common.EasyJException;

    public Object get(Object o) throws EasyJException;

    public Long getCount(SelectCommand scmd) throws EasyJException;

    public Long getCount(Object lower, Object upper) throws EasyJException;

    public Long getCount(Object object) throws EasyJException;
}
