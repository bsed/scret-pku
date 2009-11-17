package easyJ.system.data;

import java.util.Hashtable;
import java.util.ArrayList;
import easyJ.business.proxy.SingleDataProxy;
import easyJ.common.BeanUtil;
import easyJ.common.EasyJException;
import easyJ.database.dao.OrderDirection;
import easyJ.database.dao.OrderRule;

/*
 * 因为所有的基础数据都会被经常用到，所以这些数据应该被缓冲起来，当对这些数据进行操作的时候，
 * 除了操作数据库外还要操作这些缓冲的数据。在这里缓冲的是所有的基础数据，如果用户需要用到自己有权限的基础数据的话应该来这里
 */
public class SystemDataCache {
    /* propertiesHT 的key是 className+propertyName，value是Property的对象 */
    private static Hashtable propertiesHT;

    private static ArrayList propertyList;

    private static ArrayList groupPropertyList;

    private static ArrayList pageFunctionsList;

    private static ArrayList modulesList;

    private static ArrayList interestList;

    private static ArrayList dictionaryList;

    private static SingleDataProxy sdp = SingleDataProxy.getInstance();

    public SystemDataCache() {}

    /* 获得所有的兴趣 */
    public static ArrayList getInterests() throws EasyJException {
        if (interestList == null) {
            Interest module = new Interest();
            interestList = sdp.query(module);
        }
        return interestList;
    }

    /* 获得所有的数据字典数据 */
    public static ArrayList getDictinaries() throws EasyJException {
        if (dictionaryList == null) {
            Dictionary dict = new Dictionary();
            dictionaryList = sdp.query(dict);
        }
        return dictionaryList;
    }

    /* 获得所有的模块 */
    public static ArrayList getModules() throws EasyJException {
        if (modulesList == null) {
            Module module = new Module();
            modulesList = sdp.query(module);
        }
        return modulesList;
    }

    /**
     * 获得所有的页面功能
     * @param refresh         此参数指明是否需要从数据库中重新载入
     * @return                返回所有的功能集合
     * @throws EasyJException
     */
    public static ArrayList getPageFunctionsList(boolean refresh) throws EasyJException {
        if (pageFunctionsList == null || refresh) {
            PageFunction function = new PageFunction();
            pageFunctionsList = sdp.query(function);
        }
        return pageFunctionsList;
    }
    /**
     * 获得数据库中所有用户的属性 
     * @param refresh         此参数指明是否需要从数据库中重新载入
     * @return                返回数据库中所有用户的属性 
     * @throws EasyJException
     */
    public static ArrayList getProperties(boolean refresh) throws EasyJException {
        if (propertyList == null  || refresh) {
            UserPropertyRight property = new UserPropertyRight();
            OrderRule sequenceRule = new OrderRule();
            sequenceRule.setOrderColumn("sequence");
            sequenceRule.setOrderDirection(OrderDirection.ASC);
            OrderRule tableRule = new OrderRule();
            tableRule.setOrderColumn("className");
            tableRule.setOrderDirection(OrderDirection.ASC);
            OrderRule[] rules = {
                tableRule, sequenceRule
            };
            propertyList = sdp.query(property, rules);
        }
        return propertyList;
    }

    /* 获得数据库中所有的用户组属性 */
    public static ArrayList getGroupProperties(boolean refresh) throws EasyJException {
        if (groupPropertyList == null || refresh) {
            GroupPropertyRight property = new GroupPropertyRight();
            groupPropertyList = sdp.query(property);
        }
        return groupPropertyList;
    }

    public static Hashtable getPropertyHT(boolean refresh) throws EasyJException {
        getProperties(refresh);
        getGroupProperties(refresh);
        if (propertiesHT == null || refresh) {
        	if (propertiesHT == null)
        		propertiesHT = new Hashtable();
        	else
        		propertiesHT.clear();
            
            for (int i = 0; i < propertyList.size(); i++) {
                UserPropertyRight property = (UserPropertyRight) propertyList
                        .get(i);
                propertiesHT.put(property.getClassName()
                        + property.getPropertyName(), property);
                // System.out.println(property.getClassName()+property.getPropertyName());
            }
            
            for (int i = 0; i < groupPropertyList.size(); i++) {
            	GroupPropertyRight groupProperty = (GroupPropertyRight) groupPropertyList
                        .get(i);
            	UserPropertyRight property = new UserPropertyRight();
            	BeanUtil.transferObject(groupProperty, property, true, true);
                propertiesHT.put(property.getClassName()
                        + property.getPropertyName(), property);
                // System.out.println(property.getClassName()+property.getPropertyName());
            }
        }
        return propertiesHT;
    }
}
