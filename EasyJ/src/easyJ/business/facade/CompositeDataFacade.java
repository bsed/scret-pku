package easyJ.business.facade;

import easyJ.common.EasyJException;
import java.util.ArrayList;
import easyJ.database.dao.OrderRule;
import easyJ.database.dao.Page;
import easyJ.common.BeanUtil;
import easyJ.database.session.SessionFactory;
import easyJ.database.session.Session;
import easyJ.database.dao.OrderDirection;

public class CompositeDataFacade extends SingleDataFacade {
    private static SingleDataFacade sdf = (SingleDataFacade) SingleDataFacade
            .getInstance();

    private static CompositeDataFacade cdf;

    // orderRule用来对明细排序的。这是默认的顺序。
    private static OrderRule[] detailOrderRule = {
        new OrderRule("sequence", OrderDirection.ASC)
    };

    public static CommonFacade getInstance() {
        if (cdf == null)
            cdf = new CompositeDataFacade();
        return cdf;
    }

    public CompositeDataFacade() {}

    public Object create(Object o) throws easyJ.common.EasyJException {
        Session session = null;
        try {
            session = SessionFactory.openSession();
            Class clazz = o.getClass();
            o = session.create(o);
            String primaryKeyName = BeanUtil.getPrimaryKeyName(o.getClass());
            Object primaryKeyValue = BeanUtil.getPrimaryKeyValue(o);
            String[] subClassProperties = BeanUtil.getSubClassProperties(clazz);
            for (int i = 0; i < subClassProperties.length; i++) {
                String property = subClassProperties[i];
                ArrayList propertyValues = (ArrayList) BeanUtil.getFieldValue(
                        o, property);

                for (int j = 0; j < propertyValues.size(); j++) {
                    Object detail = propertyValues.get(j);
                    BeanUtil.setFieldValue(detail, primaryKeyName,
                            primaryKeyValue);
                    session.create(detail);
                }
            }
        } finally {
            if (session != null)
                session.close();
        }
        return o;
    }

    public void update(Object o) throws easyJ.common.EasyJException {
        Session session = null;
        try {
            session = SessionFactory.openSession();
            Class clazz = o.getClass();
            session.update(o);
            String[] subClassProperties = BeanUtil.getSubClassProperties(clazz);
            for (int i = 0; i < subClassProperties.length; i++) {
                String property = subClassProperties[i];
                ArrayList propertyValues = (ArrayList) BeanUtil.getFieldValue(
                        o, property);
                for (int j = 0; j < propertyValues.size(); j++) {
                    Object primaryKeyValue = BeanUtil
                            .getPrimaryKeyValue(propertyValues.get(j));
                    if (primaryKeyValue != null)
                        session.update(propertyValues.get(j));
                    else
                        session.create(propertyValues.get(j));
                }
            }
        } finally {
            if (session != null)
                session.close();
        }
    }

    public void delete(Object o) throws easyJ.common.EasyJException {
        Session session = null;
        try {
            session = SessionFactory.openSession();
            Class clazz = o.getClass();
            session.delete(o);
            String[] subClassProperties = BeanUtil.getSubClassProperties(clazz);
            for (int i = 0; i < subClassProperties.length; i++) {
                String property = subClassProperties[i];
                ArrayList propertyValues = (ArrayList) BeanUtil.getFieldValue(
                        o, property);
                for (int j = 0; j < propertyValues.size(); j++)
                    session.delete(propertyValues.get(j));
            }
        } finally {
            if (session != null)
                session.close();
        }
    }

    public Object get(Object o) throws EasyJException {
        /* 在此首先要得到主表的数据，然后得到所有从表的数据 */
        Class clazz = o.getClass();
        o = sdf.get(o);
        String primaryKeyName = BeanUtil.getPrimaryKeyName(clazz);
        Object primaryKeyValue = BeanUtil.getPrimaryKeyValue(o);
        String[] subClassProperties = BeanUtil.getSubClassProperties(clazz);
        for (int i = 0; i < subClassProperties.length; i++) {
            String property = subClassProperties[i];
            String className = BeanUtil.getSubClass(clazz, property);
            Object obj = BeanUtil.getEmptyObject(className);
            /* 设置子表中对应主表主键的属性值：例如主表的主键是invoiceId，那么在子表中也必须要有这样的一个属性焦作invoiceId */
            BeanUtil.setFieldValue(obj, primaryKeyName, primaryKeyValue);
            String[] subClassesProperties = null;
            try {
                subClassesProperties = BeanUtil.getSubClassProperties(obj
                        .getClass());
            } catch (Exception e) {}
            ArrayList subClassList = null;
            try {
                BeanUtil.getField(obj.getClass(), "sequence");
                subClassList = sdf.query(obj, detailOrderRule);
            } catch (Exception e) {
                subClassList = sdf.query(obj);
            }

            if (subClassesProperties != null) {
                for (int j = 0; j < subClassList.size(); j++) {
                    Object object = get(subClassList.get(j));
                    subClassList.set(j, object);
                }
            }
            /* 将得到的列表赋值给此属性 */
            BeanUtil.setFieldValue(o, property, subClassList);
        }
        return o;
    }

}
