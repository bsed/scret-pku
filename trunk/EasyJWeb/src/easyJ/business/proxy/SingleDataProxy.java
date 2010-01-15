package easyJ.business.proxy;

import easyJ.common.BeanUtil;
import easyJ.common.EasyJException;
import easyJ.business.facade.SingleDataFacade;
import java.util.ArrayList;

import cn.edu.pku.dr.requirement.elicitation.system.Context;

import easyJ.database.dao.DAOFactory;
import easyJ.database.dao.Filter;
import easyJ.database.dao.OrderRule;
import easyJ.database.dao.Page;
import easyJ.database.dao.command.SelectCommand;
import easyJ.database.dao.command.UpdateCommand;
import easyJ.database.dao.command.UpdateItem;
import easyJ.logging.EasyJLog;

public class SingleDataProxy implements DataProxy {
    protected static Context context; // 用来保存整个项目的上下文环境

    private static SingleDataProxy sdp = new SingleDataProxy();

    private static SingleDataFacade singleDataFacade = (SingleDataFacade) SingleDataFacade
            .getInstance();

    public static SingleDataProxy getInstance() {
        return sdp;
    }

    private String[] accurateProperties;

    protected SingleDataProxy() {

    }

    public void add(Class clazz, Object object) throws EasyJException {
		// TODO Auto-generated method stub
		
	}
    
    public Object create(Object o) throws EasyJException {
        BeanUtil.transferObject(context, o, true, false);
        singleDataFacade.create(o);
        return o;
    }

    public void delete(Object o) throws EasyJException {
        singleDataFacade.delete(o);
    }

    public void delete(Object primaryKeys[], Class clazz) throws EasyJException {
        singleDataFacade.delete(primaryKeys, clazz);
    }

    public void deleteBatch(Class clazz, String[] primaryKeys)
            throws easyJ.common.EasyJException {
        singleDataFacade.deleteBatch(clazz, primaryKeys);
    }

    /**
     * 这个是真正的数据库删除，只在进行多数据选择的时候使用，慎重，慎重。。。。
     * 参见SingleDataAction中的multiSelectConfirm。
     * 
     * @param condition
     *                要删除的条件
     * @throws easyJ.common.EasyJException
     */
    public void deleteBatch(Object condition)
            throws easyJ.common.EasyJException {
        BeanUtil.transferObject(context, condition, true, false);
        singleDataFacade.deleteBatch(condition);
    }

    public Object get(Object o) throws EasyJException {
        return singleDataFacade.get(o);
    }

    
    public Context getContext() {
        return context;
    }

    public Long getCount(Object object) throws EasyJException {
        BeanUtil.transferObject(context, object, true, false);
        return singleDataFacade.getCount(object);
    }

    public Long getCount(Object lower, Object upper) throws EasyJException {
        BeanUtil.transferObject(context, lower, true, false);
        BeanUtil.transferObject(context, upper, true, false);
        return singleDataFacade.getCount(lower, upper);
    }

    public Long getCount(SelectCommand scmd) throws EasyJException {
        return singleDataFacade.getCount(scmd);
    }

    public ArrayList query(Object o) throws EasyJException {
        BeanUtil.transferObject(context, o, true, false);
        return singleDataFacade.query(o);
    }

    public Page query(Object o, Integer page) throws EasyJException {
        BeanUtil.transferObject(context, o, true, false);
        return singleDataFacade.query(o, page.intValue());
    }

    public Page query(Object o, Integer page, OrderRule[] orderRules)
            throws EasyJException {
        BeanUtil.transferObject(context, o, true, false);
        return singleDataFacade.query(o, page.intValue(), orderRules);
    }

    public ArrayList query(Object lower, Object upper) throws EasyJException {
        BeanUtil.transferObject(context, lower, true, false);
        BeanUtil.transferObject(context, upper, true, false);
        return singleDataFacade.query(lower, upper);
    }

    public Page query(Object lower, Object upper, Integer currentPage)
            throws EasyJException {
        BeanUtil.transferObject(context, lower, true, false);
        BeanUtil.transferObject(context, upper, true, false);
        return singleDataFacade.query(lower, upper, currentPage.intValue());
    }

    public Page query(Object lower, Object upper, Integer currentPage,
            OrderRule[] orderRules) throws EasyJException {
        BeanUtil.transferObject(context, lower, true, false);
        BeanUtil.transferObject(context, upper, true, false);
        return singleDataFacade.query(lower, upper, currentPage.intValue(),
                orderRules);
    }

    public ArrayList query(Object lower, Object upper, OrderRule[] orderRules)
            throws EasyJException {
        BeanUtil.transferObject(context, lower, true, false);
        BeanUtil.transferObject(context, upper, true, false);
        return singleDataFacade.query(lower, upper, orderRules);
    }

    public ArrayList query(Object o, OrderRule[] orderRules)
            throws EasyJException {
        BeanUtil.transferObject(context, o, true, false);
        return singleDataFacade.query(o, orderRules);
    }

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
            throws easyJ.common.EasyJException {
        Object obj=BeanUtil.getEmptyObject(scmd.getSelectClass());
        
        BeanUtil.transferObject(context, obj, true, false);
        
        Filter filter = DAOFactory.getFilter();
        
        filter =filter.buildFilter(obj,true);
        
        Filter aFilter=scmd.getFilter();
        
        aFilter.addFilter(filter);
        
        scmd.setFilter(aFilter);
        return singleDataFacade.query(scmd);
    }

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
            throws easyJ.common.EasyJException {
        return singleDataFacade.query(scmd);
    }

    public ArrayList query(String className, String conditions,
            String orderbyClauses) throws EasyJException {
        return singleDataFacade.query(className, conditions, orderbyClauses);
    }

    public Page query(String className, String condition,
            String orderbyClauses, int currentPageNo) throws EasyJException {
        return singleDataFacade.query(className, condition, orderbyClauses,
                currentPageNo);
    }

    public void setAccurateProperties(String[] properties) {
        accurateProperties = properties;
        singleDataFacade.setAccurateProperties(accurateProperties);
    }

    public void setContext(Context ctx) {
        this.context = ctx;
    }

	public void update(Object o) throws EasyJException {
        BeanUtil.transferObject(context, o, true, false);
        singleDataFacade.update(o);
    }

}
