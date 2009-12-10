package easyJ.business.proxy;

import java.util.ArrayList;

import cn.edu.pku.dr.requirement.elicitation.system.Context;
import easyJ.business.facade.CompositeDataFacade;
import easyJ.common.BeanUtil;
import easyJ.common.EasyJException;
import easyJ.database.dao.OrderRule;
import easyJ.database.dao.Page;

public class CompositeDataProxy extends SingleDataProxy {
    private static CompositeDataFacade cdf = (CompositeDataFacade) CompositeDataFacade
            .getInstance();

    private static CompositeDataProxy cdp = new CompositeDataProxy();

    public static CompositeDataProxy getInstance() {
        return cdp;
    }

    private CompositeDataProxy() {

    }

    public Object create(Object o) throws EasyJException {
        BeanUtil.transferObject(context, o, true, false);
        cdf.create(o);
        return o;
    }

    public Object get(Object o) throws EasyJException {
        return cdf.get(o);
    }

    public void update(Object o) throws EasyJException {
        BeanUtil.transferObject(context, o, true, false);
        cdf.update(o);
    }

    public void deleteBatch(Class clazz, String[] primaryKeys)
            throws easyJ.common.EasyJException {
        cdf.deleteBatch(clazz, primaryKeys);
    }

    public void delete(Object o) throws EasyJException {
        cdf.delete(o);
    }

    public void delete(Object primaryKeys[], Class clazz) throws EasyJException {
        cdf.delete(primaryKeys, clazz);
    }

    public ArrayList query(Object o) throws EasyJException {
        BeanUtil.transferObject(context, o, true, false);
        return cdf.query(o);
    }

    public ArrayList query(Object o, OrderRule[] orderRules)
            throws EasyJException {
        BeanUtil.transferObject(context, o, true, false);
        return cdf.query(o, orderRules);
    }

    public ArrayList query(Object lower, Object upper) throws EasyJException {
        BeanUtil.transferObject(context, lower, true, false);
        BeanUtil.transferObject(context, upper, true, false);
        return cdf.query(lower, upper);
    }

    public ArrayList query(Object lower, Object upper, OrderRule[] orderRules)
            throws EasyJException {
        BeanUtil.transferObject(context, lower, true, false);
        BeanUtil.transferObject(context, upper, true, false);
        return cdf.query(lower, upper, orderRules);
    }

    public Page query(Object o, Integer page) throws EasyJException {
        BeanUtil.transferObject(context, o, true, false);
        return cdf.query(o, page.intValue());
    }

    public Page query(Object o, Integer page, OrderRule[] orderRules)
            throws EasyJException {
        BeanUtil.transferObject(context, o, true, false);
        return cdf.query(o, page.intValue(), orderRules);
    }

    public Page query(Object lower, Object upper, Integer currentPage)
            throws EasyJException {
        BeanUtil.transferObject(context, lower, true, false);
        BeanUtil.transferObject(context, upper, true, false);
        return cdf.query(lower, upper, currentPage.intValue());
    }

    public Page query(Object lower, Object upper, Integer currentPage,
            OrderRule[] orderRules) throws EasyJException {
        BeanUtil.transferObject(context, lower, true, false);
        BeanUtil.transferObject(context, upper, true, false);
        return cdf.query(lower, upper, currentPage.intValue(), orderRules);
    }

}
