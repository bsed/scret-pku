package easyJ.system.service;

import java.util.*;
import javax.servlet.http.*;
import easyJ.common.EasyJException;
import easyJ.system.data.UserPropertyRight;
import easyJ.database.dao.OrderDirection;
import easyJ.database.session.Session;
import easyJ.database.session.SessionFactory;
import easyJ.database.dao.OrderRule;
import easyJ.database.dao.Filter;
import easyJ.database.dao.DAOFactory;
import easyJ.database.dao.SQLOperator;
import easyJ.database.dao.command.SelectCommand;
import easyJ.database.dao.LogicOperator;
import easyJ.system.data.SysUserCache;

/**
 * 这个类已经没用了。
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2007
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */
public class UserColumnService {
    public UserColumnService() {}

    public static ArrayList getAllColumns(Long userId, String className)
            throws EasyJException {
        try {
            // 用来得到用户对各字段的权限。
            Filter filter = DAOFactory.getFilter();
            Filter filter1 = DAOFactory.getFilter("userId", SQLOperator.EQUAL,
                    userId);
            Filter filter2 = DAOFactory.getFilter("className",
                    SQLOperator.EQUAL, className);
            SelectCommand scmd = DAOFactory
                    .getSelectCommand(UserPropertyRight.class);
            filter.addFilter(filter1);
            filter.addFilter(filter2, LogicOperator.AND);
            scmd.setFilter(filter);
            // 得到拥有权限的所有的ColumnsList
            Session session = null;
            try {
                session = SessionFactory.openSession();
                ArrayList allColumnsList = session.query(scmd);
                return allColumnsList;
            } finally {
                if (session != null)
                    session.close();
            }
        } catch (EasyJException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public static ArrayList getEditProperties(Long userId, String className)
            throws EasyJException {
        try {
            Filter filter = DAOFactory.getFilter();
            Filter filter1 = DAOFactory.getFilter("userId", SQLOperator.EQUAL,
                    userId);
            Filter filter2 = DAOFactory.getFilter("className",
                    SQLOperator.EQUAL, className);
            Filter filter3 = DAOFactory.getFilter("whetherEdit",
                    SQLOperator.EQUAL, "Y");
            SelectCommand scmd = DAOFactory
                    .getSelectCommand(UserPropertyRight.class);
            filter.addFilter(filter1);
            filter.addFilter(filter2, LogicOperator.AND);
            filter.addFilter(filter3, LogicOperator.AND);
            scmd.setFilter(filter);

            // 得到用来编辑的PropertiesList
            OrderRule or = new OrderRule();
            or.setOrderColumn("editSequence");
            or.setOrderDirection(OrderDirection.ASC);
            OrderRule[] orderRules = {
                or
            };
            Session session = null;
            try {
                session = SessionFactory.openSession();
                ArrayList allPropertiesList = session.query(scmd, orderRules);
                return allPropertiesList;
            } finally {
                if (session != null)
                    session.close();
            }

        } catch (EasyJException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public static ArrayList getDisplayProperties(Long userId, String className)
            throws EasyJException {
        try {
            Filter filter = DAOFactory.getFilter();
            Filter filter1 = DAOFactory.getFilter("userId", SQLOperator.EQUAL,
                    userId);
            Filter filter2 = DAOFactory.getFilter("className",
                    SQLOperator.EQUAL, className);
            Filter filter3 = DAOFactory.getFilter("whetherDisplay",
                    SQLOperator.EQUAL, "Y");
            SelectCommand scmd = DAOFactory
                    .getSelectCommand(UserPropertyRight.class);
            filter.addFilter(filter1);
            filter.addFilter(filter2, LogicOperator.AND);
            filter.addFilter(filter3, LogicOperator.AND);
            scmd.setFilter(filter);

            // 得到用来编辑的PropertiesList
            OrderRule or = new OrderRule();
            or.setOrderColumn("displaySequence");
            or.setOrderDirection(OrderDirection.ASC);
            OrderRule[] orderRules = {
                or
            };
            Session session = null;
            try {
                session = SessionFactory.openSession();
                ArrayList allPropertiesList = session.query(scmd, orderRules);
                return allPropertiesList;
            } finally {
                if (session != null)
                    session.close();
            }

        } catch (EasyJException e) {
            e.printStackTrace();
            throw e;
        }

    }

    public static ArrayList getQueryProperties(Long userId, String className,
            SysUserCache userCache) throws EasyJException {
        try {
            Filter filter = DAOFactory.getFilter();
            Filter filter1 = DAOFactory.getFilter("userId", SQLOperator.EQUAL,
                    userId);
            Filter filter2 = DAOFactory.getFilter("className",
                    SQLOperator.EQUAL, className);
            Filter filter3 = DAOFactory.getFilter("whetherQuery",
                    SQLOperator.EQUAL, "Y");
            SelectCommand scmd = DAOFactory
                    .getSelectCommand(UserPropertyRight.class);
            filter.addFilter(filter1);
            filter.addFilter(filter2, LogicOperator.AND);
            filter.addFilter(filter3, LogicOperator.AND);
            scmd.setFilter(filter);

            // 得到用来编辑的PropertiesList
            OrderRule or = new OrderRule();
            or.setOrderColumn("querySequence");
            or.setOrderDirection(OrderDirection.ASC);
            OrderRule[] orderRules = {
                or
            };
            Session session = null;
            try {
                session = SessionFactory.openSession();
                ArrayList allPropertiesList = session.query(scmd, orderRules);
                return allPropertiesList;
            } finally {
                if (session != null)
                    session.close();
            }

        } catch (EasyJException e) {
            e.printStackTrace();
            throw e;
        }

    }

}
