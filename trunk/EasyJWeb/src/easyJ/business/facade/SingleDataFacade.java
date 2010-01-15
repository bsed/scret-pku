package easyJ.business.facade;

import easyJ.common.EasyJException;
import java.util.ArrayList;
import easyJ.database.dao.OrderRule;
import easyJ.database.dao.command.SelectCommand;
import easyJ.database.dao.Page;
import easyJ.database.session.SessionFactory;
import easyJ.database.session.Session;
import easyJ.database.dao.command.UpdateCommand;
import easyJ.database.dao.command.UpdateItem;
import easyJ.common.BeanUtil;
import easyJ.database.dao.SQLOperator;
import easyJ.database.dao.DAOFactory;
import easyJ.database.dao.Filter;

public class SingleDataFacade implements CommonFacade {
    protected SingleDataFacade() {}

    private static SingleDataFacade singleDataFacade;

    private String[] accurateProperties;

    public void setAccurateProperties(String[] properties) {
        accurateProperties = properties;
    }

    public static CommonFacade getInstance() {
        if (singleDataFacade == null)
            singleDataFacade = new SingleDataFacade();
        return singleDataFacade;
    }

    public Object create(Object o) throws EasyJException {
        Session session = null;
        try {
            session = SessionFactory.openSession();
            o = session.create(o);
            return o;
        } finally {
            if (session != null)
                session.close();
        }
    }

    public void update(Object o) throws EasyJException {
        Session session = null;
        try {

            session = SessionFactory.openSession();
            session.update(o);
        } finally {
            if (session != null)
                session.close();
        }

    }

    public void delete(Object o) throws EasyJException {
        Session session = null;
        try {
            session = SessionFactory.openSession();
            // change the o's use_state
            // Class clazz=null;
            // SelectCommand scmd=DAOFactory.getSelectCommand(clazz);
            // Filter filter=DAOFactory.getFilter("dataId",SQLOperator.IN,"");
            session.delete(o);
        } finally {
            if (session != null)
                session.close();
        }
    }

    public void deleteBatch(Class clazz, String[] primaryKeys)
            throws easyJ.common.EasyJException {
        Session session = null;
        try {
            session = SessionFactory.openSession();
            // change the o's use_state
            session.deleteBatch(clazz, primaryKeys);
        } finally {
            if (session != null)
                session.close();
        }
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
        Session session = null;
        try {
            session = SessionFactory.openSession();
            // change the o's use_state
            session.deleteBatch(condition);
        } finally {
            if (session != null)
                session.close();
        }
    }

    /* 多主键的删除 */
    public void delete(Object primaryKeys[], Class clazz) throws EasyJException {
        Session session = null;
        try {
            session = SessionFactory.openSession();
            UpdateCommand ucmd = session.getUpdateCommand(clazz);
            UpdateItem ui = new UpdateItem("useState", "N");
            ucmd.addUpdateItem(ui);
            for (int i = 0; i < primaryKeys.length; i++) {
                String primaryKey = (String) BeanUtil.getPubStaticFieldValue(
                        clazz, easyJ.common.Const.PRIMARY_KEY);
                Filter filter = DAOFactory.getFilter(primaryKey,
                        SQLOperator.EQUAL, primaryKeys[i]);
                ucmd.setFilter(filter);
                // EasyJLog.debug(ucmd.getExecutableSQL());
                session.update(ucmd);
            }
        } finally {
            if (session != null)
                session.close();
        }
    }

    public ArrayList query(Object o) throws EasyJException {
        Session session = null;
        try {
            session = SessionFactory.openSession();
            session.setAccurateProperties(accurateProperties);
            // change the o's use_state
            return session.query(o);
        } finally {
            if (session != null)
                session.close();
        }

    }

    public ArrayList query(Object o, OrderRule[] orderRules)
            throws EasyJException {
        Session session = null;
        try {
            session = SessionFactory.openSession();
            session.setAccurateProperties(accurateProperties);
            // change the o's use_state
            return session.query(o, orderRules);
        } finally {
            if (session != null)
                session.close();
        }

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
        Session session = null;
        try {
            session = SessionFactory.openSession();
            session.setAccurateProperties(accurateProperties);
            // change the o's use_state
            return session.query(scmd);
        } finally {
            if (session != null)
                session.close();
        }
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
        Session session = null;
        try {
            session = SessionFactory.openSession();
            session.setAccurateProperties(accurateProperties);
            // change the o's use_state
            return session.query(scmd, orderRules);
        } finally {
            if (session != null)
                session.close();
        }
    }

    public ArrayList query(Object lower, Object upper) throws EasyJException {
        Session session = null;
        try {
            session = SessionFactory.openSession();
            session.setAccurateProperties(accurateProperties);
            // change the o's use_state
            return session.query(lower, upper);
        } finally {
            if (session != null)
                session.close();
        }

    }

    public ArrayList query(Object lower, Object upper, OrderRule[] orderRules)
            throws EasyJException {
        Session session = null;
        try {
            session = SessionFactory.openSession();
            session.setAccurateProperties(accurateProperties);
            // change the o's use_state
            return session.query(lower, upper, orderRules);
        } finally {
            if (session != null)
                session.close();
        }

    }

    public Page query(Object o, int currentPageNo) throws EasyJException {
        Session session = null;
        try {
            session = SessionFactory.openSession();
            session.setAccurateProperties(accurateProperties);
            // change the o's use_state
            return session.query(o, currentPageNo);
        } finally {
            if (session != null)
                session.close();
        }

    }

    public Page query(Object o, int currentPageNo, OrderRule[] orderRules)
            throws EasyJException {
        Session session = null;
        try {
            session = SessionFactory.openSession();
            session.setAccurateProperties(accurateProperties);
            // change the o's use_state
            return session.query(o, currentPageNo, orderRules);
        } finally {
            if (session != null)
                session.close();
        }

    }

    public Page query(Object lower, Object upper, int currentPageNo)
            throws EasyJException {
        Session session = null;
        try {
            session = SessionFactory.openSession();
            session.setAccurateProperties(accurateProperties);
            // change the o's use_state
            return session.query(lower, upper, currentPageNo);
        } finally {
            if (session != null)
                session.close();
        }

    }

    public Page query(Object lower, Object upper, int currentPageNo,
            OrderRule[] orderRules) throws EasyJException {
        Session session = null;
        try {
            session = SessionFactory.openSession();
            session.setAccurateProperties(accurateProperties);
            // change the o's use_state
            return session.query(lower, upper, currentPageNo, orderRules);
        } finally {
            if (session != null)
                session.close();
        }
    }

    public Object get(Object o) throws EasyJException {
        Session session = null;
        try {
            session = SessionFactory.openSession();
            session.setAccurateProperties(accurateProperties);
            // change the o's use_state
            return session.get(o);
        } finally {
            if (session != null)
                session.close();
        }

    }

    /* 此方法可以支持用户自定义的条件和排序字段 */
    public Page query(String className, String condition,
            String orderbyClauses, int currentPageNo) throws EasyJException {
        Session session = null;
        try {
            session = SessionFactory.openSession();
            session.setAccurateProperties(accurateProperties);
            // change the o's use_state
            return session.query(className, condition, orderbyClauses,
                    currentPageNo);
        } finally {
            if (session != null)
                session.close();
        }

    }

    public ArrayList query(String className, String conditions,
            String orderbyClauses) throws EasyJException {
        Session session = null;
        try {
            session = SessionFactory.openSession();
            session.setAccurateProperties(accurateProperties);
            // change the o's use_state
            return session.query(className, conditions, orderbyClauses);
        } finally {
            if (session != null)
                session.close();
        }
    }

    public Long getCount(SelectCommand scmd) throws EasyJException {
        Session session = null;
        try {
            session = SessionFactory.openSession();
            return session.getCount(scmd);
        } finally {
            if (session != null)
                session.close();
        }
    }

    public Long getCount(Object lower, Object upper) throws EasyJException {
        Session session = null;
        try {
            session = SessionFactory.openSession();
            session.setAccurateProperties(accurateProperties);
            return session.getCount(lower, upper);
        } finally {
            if (session != null)
                session.close();
        }

    }

    public Long getCount(Object object) throws EasyJException {
        Session session = null;
        try {
            session = SessionFactory.openSession();
            session.setAccurateProperties(accurateProperties);
            return session.getCount(object);
        } finally {
            if (session != null)
                session.close();
        }
    }

}
