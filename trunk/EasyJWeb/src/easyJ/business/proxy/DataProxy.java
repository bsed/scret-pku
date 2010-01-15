package easyJ.business.proxy;

import java.util.ArrayList;

import cn.edu.pku.dr.requirement.elicitation.system.Context;
import easyJ.common.EasyJException;
import easyJ.database.dao.OrderRule;
import easyJ.database.dao.Page;
import easyJ.database.dao.command.SelectCommand;

public interface DataProxy {
    public Object create(Object o) throws EasyJException;

    public Object get(Object o) throws EasyJException;

    public void setAccurateProperties(String[] properties);

    public void update(Object o) throws EasyJException;

    public void deleteBatch(Class clazz, String[] primaryKeys)
            throws easyJ.common.EasyJException;

    public void delete(Object o) throws EasyJException;
    
    public void add(Class clazz, Object object) throws EasyJException;
    public void delete(Object primaryKeys[], Class clazz) throws EasyJException;

    public ArrayList query(Object o) throws EasyJException;

    public ArrayList query(Object o, OrderRule[] orderRules)
            throws EasyJException;

    public ArrayList query(Object lower, Object upper) throws EasyJException;

    public ArrayList query(Object lower, Object upper, OrderRule[] orderRules)
            throws EasyJException;

    public Page query(Object o, Integer page) throws EasyJException;

    public Page query(Object o, Integer page, OrderRule[] orderRules)
            throws EasyJException;

    public Page query(Object lower, Object upper, Integer currentPage)
            throws EasyJException;

    public Page query(Object lower, Object upper, Integer currentPage,
            OrderRule[] orderRules) throws EasyJException;

    public Long getCount(SelectCommand scmd) throws EasyJException;

    public Long getCount(Object lower, Object upper) throws EasyJException;

    public Long getCount(Object object) throws EasyJException;

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

    public void setContext(Context context);

	

}
