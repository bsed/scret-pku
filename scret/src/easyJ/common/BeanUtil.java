package easyJ.common;

import java.lang.reflect.*;
import java.util.Collection;
import java.util.Iterator;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.ArrayList;
import easyJ.logging.EasyJLog;
import easyJ.common.validate.GenericValidator;
import easyJ.common.validate.ValidateErrors;
import easyJ.common.validate.Validate;
import easyJ.system.data.UserPropertyRight;
import easyJ.system.data.SystemDataCache;
import java.util.StringTokenizer;

public class BeanUtil {
    public BeanUtil() {}

    /**
     * 从一个对象中得到此对象所属类对应的数据库表（table）
     * 
     * @param o
     *                Object 用来获取数据库表的对象
     * @throws EasyJException
     * @return String 此对象所属类对应的数据库表的名字
     */
    public static String getTableName(Object o) throws EasyJException {
        return (String) getPubFieldValue(o, easyJ.common.Const.TABLE_NAME);
    }

    public static Field getField(Class clazz, String fieldName)
            throws EasyJException {
        try {
            Field field = clazz.getDeclaredField(fieldName);
            return field;
        } catch (SecurityException ex) {
            String location = "BeanUtil.getField(Class,String)";
            String logMessage = "Class " + clazz + "'s field: " + fieldName
                    + "() can't be visited";
            EasyJException ee = new EasyJException(ex, location, logMessage);
            throw ee;

        } catch (NoSuchFieldException ex) {
            String location = "BeanUtil.getField(Class,String)";
            String logMessage = "Class " + clazz + "'s field: " + fieldName
                    + "() doesn't exist";
            EasyJException ee = new EasyJException(ex, location, logMessage);
            throw ee;
        }
    }

    /**
     * 从一个对象中得到此对象所属类对应的数据库试图（view）
     * 
     * @param o
     *                Object 用来获取数据库试图的对象
     * @throws EasyJException
     * @return String 此对象所属类对应数据库试图的名字
     */

    public static String getViewName(Object o) throws EasyJException {
        return (String) getPubFieldValue(o, easyJ.common.Const.VIEW_NAME);
    }

    /**
     * <p>
     * 用来判断某个类的属性是否可以被更新。一个类的属性是和视图的字段对应的，如果字段属于视图，但是不属于视图对应的表，那么此属性就是不可更新的
     * </p>
     * <p>
     * 此方法其实是调用了每个对象的isUpdatable方法，例如一个学生类Student，所属班级的名称class_name是视图字段，其它属性是属于学生本身的，那么isUpdatable方法应该这样写
     * </p>
     * <p>
     * public boolean isUpdatable(String property){
     * </p>
     * <p>
     * if("className".equals(property)) return false;
     * </p>
     * <p>
     * return true;
     * </p>
     * <p>}
     * </p>
     * 
     * @param clazz
     *                Class 属性所属的类
     * @param property
     *                String 属性名
     * @throws EasyJException
     * @return boolean 如果可以更新则返回true，不可以更新则返回false
     * @see <a
     *      href="../database/dao/command/UpdateCommandSQLServerImpl.html">UpdateCommandSQLServerImpl</a>
     */
    public static boolean isUpdatable(Class clazz, String property)
            throws EasyJException {
        String[] args = {
            property
        };
        Boolean result = (Boolean) invokeStaticMethod(clazz, "isUpdatable",
                args);
        return result.booleanValue();
    }

    /**
     * <p>
     * 依据属性名得到一个对象中此属性对应的值。例如Student student=new Student();
     * student.setStudentName("Liu");
     * </p>
     * <p>
     * 调用getFieldValue(student,"studentName")之后，便可以得到"Liu"这样属性对应的值
     * </p>
     * 
     * @param valueObject
     *                Object 从中取值的对象
     * @param property
     *                String 属性名
     * @throws EasyJException
     * @return Object 属性对应的值。
     */
    public static Object getFieldValue(Object valueObject, String property)
            throws EasyJException {
        Object result = null;
        Method getter = null;
        String getterName = "get" + property.substring(0, 1).toUpperCase()
                + property.substring(1);
        try {
            getter = valueObject.getClass().getDeclaredMethod(getterName, null);
            result = getter.invoke(valueObject, null);
            return result;
        } catch (SecurityException ex) {
            String location = "BeanUtil.getFieldValue(Object,String)";
            String logMessage = "Bean " + valueObject.getClass()
                    + "'s method: " + getterName
                    + "() is private, while it shall be public";
            EasyJException ee = new EasyJException(ex, location, logMessage);
            throw ee;
        } catch (NoSuchMethodException ex) {
            String location = "BeanUtil.getFieldValue(Object,String)";
            String logMessage = "Bean " + valueObject.getClass()
                    + "'s method: " + getterName
                    + " doesn't exist, please add this method to bean";
            EasyJException ee = new EasyJException(ex, location, logMessage);
            throw ee;
        } catch (InvocationTargetException ex1) {
            String location = "BeanUtil.getFieldValue(Object,String)";
            String logMessage = "Exception occurs at Bean "
                    + valueObject.getClass() + "'s method: " + getterName
                    + ", please check the method";
            EasyJException ee = new EasyJException(ex1, location, logMessage);
            throw ee;
        } catch (IllegalArgumentException ex1) {
            String location = "BeanUtil.getFieldValue(Object,String)";
            String logMessage = "Bean " + valueObject.getClass()
                    + "'s method: " + getterName
                    + " arguments is wrong, please check the arguments";
            EasyJException ee = new EasyJException(ex1, location, logMessage);
            throw ee;
        } catch (IllegalAccessException ex1) {
            String location = "BeanUtil.getFieldValue(Object,String)";
            String logMessage = "Bean " + valueObject.getClass()
                    + "'s method: " + getterName
                    + " is private, while it shall be public";
            EasyJException ee = new EasyJException(ex1, location, logMessage);
            throw ee;
        }
    }

    /**
     * 获得一个对象中被public修饰的属性对应的值。
     * 
     * @param valueObject
     *                Object 从中取值的对象
     * @param property
     *                String 属性名
     * @throws EasyJException
     * @return Object 属性对应的值
     */
    public static Object getPubFieldValue(Object valueObject, String property)
            throws EasyJException {
        try {
            Class c = valueObject.getClass();
            Field field = c.getDeclaredField(property);
            Object result = (String) field.get(valueObject);
            return result;
        } catch (SecurityException ex) {
            String location = "BeanUtil.getPubFieldValue(Object,String)";
            String logMessage = "Bean " + valueObject.getClass()
                    + "'s property: " + property
                    + " is private, please make it public";
            EasyJException ee = new EasyJException(ex, location, logMessage);
            throw ee;
        } catch (NoSuchFieldException ex) {
            String location = "BeanUtil.getPubFieldValue(Object,String)";
            String logMessage = "Bean " + valueObject.getClass()
                    + "'s property: " + property
                    + " doesn't exist, please add this property as public";
            EasyJException ee = new EasyJException(ex, location, logMessage);
            throw ee;
        } catch (IllegalAccessException ex) {
            String location = "BeanUtil.getPubFieldValue(Object,String)";
            String logMessage = "Bean " + valueObject.getClass()
                    + "'s property: " + property
                    + " is private, please make it public";
            EasyJException ee = new EasyJException(ex, location, logMessage);
            throw ee;
        }
    }

    /**
     * 用来得到类定义的public static的属性值。
     * 
     * @param c
     *                Class 从中得到值的类
     * @param property
     *                String 属性名称
     * @throws EasyJException
     * @return Object 属性值
     */
    public static Object getPubStaticFieldValue(Class c, String property)
            throws EasyJException {
        try {
            Field field = c.getDeclaredField(property);
            Object result = field.get(null);
            return result;
        } catch (SecurityException ex) {
            String location = "BeanUtil.getPubStaticFieldValue(Object,String)";
            String logMessage = "Bean " + c + "'s property: " + property
                    + " is private, please make it public";
            EasyJException ee = new EasyJException(ex, location, logMessage);
            throw ee;
        } catch (NoSuchFieldException ex) {
            String location = "BeanUtil.getPubStaticFieldValue(Object,String)";
            String logMessage = "Bean " + c + "'s property: " + property
                    + " doesn't exist, please add this property as public";
            EasyJException ee = new EasyJException(ex, location, logMessage);
            throw ee;
        } catch (IllegalAccessException ex) {
            String location = "BeanUtil.getPubStaticFieldValue(Object,String)";
            String logMessage = "Bean " + c + "'s property: " + property
                    + " is private, please make it public";
            EasyJException ee = new EasyJException(ex, location, logMessage);
            throw ee;
        }
    }

    /**
     * 对对象的某个属性进行赋值
     * 
     * @param valueObject
     *                Object 要赋值的对象
     * @param property
     *                String 要赋值的属性
     * @param value
     *                Object 属性值
     * @throws EasyJException
     */
    public static void setFieldValue(Object valueObject, String property,
            Object value) throws EasyJException {

        Method setter = null;
        String setterName = "set" + property.substring(0, 1).toUpperCase()
                + property.substring(1);
        Object[] arg = {
            value
        };
        try {
            Class[] args = {
                getFieldType(valueObject.getClass(), property)
            };
            setter = valueObject.getClass().getDeclaredMethod(setterName, args);
            setter.invoke(valueObject, arg);
        } catch (InvocationTargetException ite) {
            String location = "BeanUtil.setFieldValue(Object,String,Object)";
            String logMessage = "Exception occurs at Bean "
                    + valueObject.getClass() + "'s method: " + setterName
                    + ", please check the method";
            EasyJException ee = new EasyJException(ite, location, logMessage);
            throw ee;

        } catch (NoSuchMethodException ex) {
            String location = "BeanUtil.setFieldValue(Object,String,Object)";
            String logMessage = "Bean " + valueObject.getClass()
                    + "'s method: " + setterName
                    + " doesn't exist, please add this method to bean";
            EasyJException ee = new EasyJException(ex, location, logMessage);
            throw ee;
        } catch (IllegalAccessException ex) {
            String location = "BeanUtil.setFieldValue(Object,String,Object)";
            String logMessage = "Bean " + valueObject.getClass()
                    + "'s method: " + setterName
                    + " is private, please make it public";
            EasyJException ee = new EasyJException(ex, location, logMessage);
            throw ee;
        }

    }

    /**
     * 用来得到类的某个属性的类型
     * 
     * @param clazz
     *                Class 属性所属的类
     * @param property
     *                String 属性名
     * @throws EasyJException
     * @return Class 返回属性的类型
     */
    public static Class getFieldType(Class clazz, String property)
            throws EasyJException {
        Field f = null;

        try {
            f = clazz.getDeclaredField(property);
            return f.getType();
        } catch (SecurityException ex) {
            String location = "BeanUtil.getFieldType(Object,String)";
            String logMessage = "Bean " + clazz.getClass() + "'s property: "
                    + property + " is private, please make it public";
            EasyJException ee = new EasyJException(ex, location, logMessage);
            throw ee;
        } catch (NoSuchFieldException ex) {
            String location = "BeanUtil.getFieldType(Object,String)";
            String logMessage = "Bean " + clazz.getClass() + "'s property: "
                    + property
                    + " doesn't exist, please add this property as public";
            EasyJException ee = new EasyJException(ex, location, logMessage);
            throw ee;
        }
    }

    /**
     * 依据一个类型得到一个此类型的对象
     * 
     * @param clazz
     *                Class 要得到对象的类
     * @throws EasyJException
     * @return Object 此类的一个实例对象。
     */
    public static Object getEmptyObject(Class clazz) throws EasyJException {
        Object obj = null;
        try {
            obj = clazz.newInstance();
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
            throw new EasyJException(ex,
                    "easyJ.common.BeanUtil.getObject(String className)", clazz
                            .getName()
                            + "'s constructor method shall be public", "服务器忙");
        } catch (InstantiationException ex) {
            ex.printStackTrace();
            throw new EasyJException(ex,
                    "easyJ.common.BeanUtil.getObject(String className)", clazz
                            .getName()
                            + "的实例创建失败", "服务器忙");
        }
        return obj;
    }

    /**
     * 依据一个类名来得到此类名对应类的一个实例
     * 
     * @param className
     *                String 类名的字符串表示
     * @throws EasyJException
     *                 如果类名不存在，就抛出异常。
     * @return Object 返回此类对应的实例对象
     */
    public static Object getEmptyObject(String className) throws EasyJException {
        Object obj = null;
        Class clazz = null;
        try {
            clazz = Class.forName(className);
            obj = clazz.newInstance();
        } catch (ClassNotFoundException ex) {
            // ex.printStackTrace();
            throw new EasyJException(ex,
                    "easyJ.common.BeanUtil.getObject(String className)",
                    "从客户端得到对象时，类‘" + className + "’不存在，需要看看配置文件，看是否是类名写错",
                    "服务器忙");
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
            throw new EasyJException(ex,
                    "easyJ.common.BeanUtil.getObject(String className)",
                    className + "'s constructor method shall be public", "服务器忙");
        } catch (InstantiationException ex) {
            ex.printStackTrace();
            throw new EasyJException(ex,
                    "easyJ.common.BeanUtil.getObject(String className)",
                    className + "的实例创建失败", "服务器忙");
        }
        return obj;
    }

    /**
     * 根据字符串表示的类得到一个类对象。
     * 
     * @param className
     *                String 类名对应的字符串
     * @throws EasyJException
     *                 如果类名对应的字符串不能被加载，就要抛出异常。
     * @return Class 返回类名对应的类对象。
     */
    public static Class getClass(String className) throws EasyJException {
        Class clazz = null;
        try {
            clazz = Class.forName(className);
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            throw new EasyJException(ex,
                    "easyJ.common.BeanUtil.getClass(String className)",
                    "从客户端得到对象时，类‘" + className + "’不存在，需要看看配置文件，看是否是类名写错",
                    "服务器忙");
        }
        return clazz;
    }

    /**
     * 克隆一个对象，因为在java语言中所有的赋值都是引用赋值，所以要进行深层赋值的时候就需要使用clone这个方法。
     * 
     * @param valueObject
     *                Object 需要克隆的对象
     * @throws EasyJException
     * @return Object 克隆之后的克隆对象。
     */
    public static Object cloneObject(Object valueObject) throws EasyJException {
        if (valueObject == null) {
            return null;
        }
        Class colneClass = valueObject.getClass();
        Object col = null;
        // 基本类型
        if (valueObject instanceof String) {
            return valueObject.toString();
        } else if (valueObject instanceof Long) {
            Long lObject = (Long) valueObject;
            return new Long(lObject.longValue());
        } else if (valueObject instanceof Integer) {
            Integer iObject = (Integer) valueObject;
            return new Integer(iObject.intValue());
        } else if (valueObject instanceof Boolean) {
            Boolean iObject = (Boolean) valueObject;
            return new Boolean(iObject.booleanValue());
        } else if (valueObject instanceof Short) {
            Short iObject = (Short) valueObject;
            return new Short(iObject.shortValue());
        } else if (valueObject instanceof BigDecimal) {
            BigDecimal bObject = (BigDecimal) valueObject;
            return bObject.add(new BigDecimal(0));
        } else if (valueObject instanceof java.sql.Date) {
            java.sql.Date dObject = (java.sql.Date) valueObject;
            return dObject.clone();
        } else if (valueObject instanceof java.sql.Time) {
            java.sql.Time tObject = (java.sql.Time) valueObject;
            return tObject.clone();
        } else if (valueObject instanceof java.sql.Timestamp) {
            java.sql.Timestamp tsObject = (java.sql.Timestamp) valueObject;
            return tsObject.clone();
        } else if (valueObject instanceof Collection) { // 集合类型的对象
            try {
                col = colneClass.newInstance();
                Collection valueObjectCol = (Collection) valueObject;
                Iterator iterator = valueObjectCol.iterator();
                while (iterator.hasNext()) {
                    Object nextObject = iterator.next();
                    Object cloneObject = cloneObject(nextObject);
                    Class[] args = {
                        Object.class
                    };
                    Method colAdd = colneClass.getDeclaredMethod("add", args);
                    Object[] colObject = {
                        cloneObject
                    };
                    colAdd.invoke(col, colObject);
                }
            } catch (Exception e) {

            }
        } else { // 单个对象
            Object result = null;
            Method getter = null;
            Method setter = null;
            String fieldName = null;
            Field[] fields = colneClass.getDeclaredFields();
            try {
                col = colneClass.newInstance();
                // 1.设置ValueObject的属性
                for (int i = 0; i < fields.length; i++) {
                    fieldName = fields[i].getName();
                    // 1.1 取得属性
                    String getterName = "get"
                            + fieldName.substring(0, 1).toUpperCase()
                            + fieldName.substring(1);
                    try {
                        getter = colneClass.getDeclaredMethod(getterName, null);
                    } catch (NoSuchMethodException me) {
                        continue;
                    }
                    result = getter.invoke(valueObject, null);
                    Object colneResult = cloneObject(result);
                    if (result == null) {
                        continue;
                    }
                    Class[] args = {
                        result.getClass()
                    };
                    /*
                     * if (result instanceof Collection) { args = new Class[] {
                     * java.util.ArrayList.class}; }
                     */
                    Object[] colObject = {
                        colneResult
                    };
                    // 1.2 设置属性值
                    String setterName = "set"
                            + fieldName.substring(0, 1).toUpperCase()
                            + fieldName.substring(1);
                    setter = colneClass.getDeclaredMethod(setterName, args);
                    setter.invoke(col, colObject);
                }
            } catch (NoSuchMethodException me) {
                throw new EasyJException(me,
                        "easyJ.common.BeanUtil.clone(Object valueObject)",
                        "ValueObject <" + valueObject.getClass()
                                + "> must define method " + setter);
            } catch (InvocationTargetException ite) {
                throw new EasyJException(ite,
                        "easyJ.common.BeanUtil.clone(Object valueObject)",
                        "exception occur when invoking "
                                + valueObject.getClass() + setter + " method ");

            } catch (IllegalAccessException ie) {
                throw new EasyJException(ie,
                        "easyJ.common.BeanUtil.clone(Object valueObject)",
                        "ValueObject <" + valueObject.getClass() + "> method "
                                + setter + " should define as PUBLIC.");
            } catch (InstantiationException ine) {
                throw new EasyJException(ine,
                        "easyJ.common.BeanUtil.clone(Object valueObject)",
                        "Exception occur when New Instance of "
                                + valueObject.getClass()
                                + ". please make the constructor public");
            }

        }
        return col;
    }

    /**
     * 根据方法名来调用一个static方法。
     * 
     * @param class1
     *                Class 方法所属的类对象
     * @param methodName
     *                String 方法名
     * @param args
     *                Object[] 方法调用需要的参数
     * @throws EasyJException
     * @return Object 返回被调用方法的返回值。
     */
    public static Object invokeStaticMethod(Class class1, String methodName,
            Object[] args) throws EasyJException {
        try {
            Object obj1 = null;
            if (args != null) {
                Class argsClass[] = new Class[args.length];
                for (int i = 0; i < args.length; i++) {
                    argsClass[i] = args[i].getClass();
                }
                Method method = class1.getDeclaredMethod(methodName, argsClass);
                obj1 = method.invoke(null, args);
            } else {
                Method method = class1.getDeclaredMethod(methodName, null);
                obj1 = method.invoke(null, null);
            }
            return obj1;
        } catch (SecurityException ex1) {
            String location = "BeanUtil.invokeStaticMethod(Class class1, String methodName,Object[] args)";
            String logMessage = "Bean " + class1 + "'s method: " + methodName
                    + " is private, please make it public";
            EasyJException ee = new EasyJException(ex1, location, logMessage);
            throw ee;
        } catch (NoSuchMethodException ex1) {
            String location = "BeanUtil.invokeStaticMethod(Class class1, String methodName,Object[] args)";
            String logMessage = "Bean "
                    + class1
                    + "'s method: "
                    + methodName
                    + " doesn't exist, please add it. Or the method is not static, please add static to decorate";
            EasyJException ee = new EasyJException(ex1, location, logMessage);
            throw ee;
        } catch (InvocationTargetException ex2) {
            String location = "BeanUtil.invokeStaticMethod(Class class1, String methodName,Object[] args)";
            String logMessage = "Exception occurs at Bean " + class1
                    + "'s method: " + methodName + ", please check the method";
            EasyJException ee = new EasyJException(ex2, location, logMessage);
            throw ee;
        } catch (IllegalArgumentException ex2) {
            String location = "BeanUtil.invokeStaticMethod(Class class1, String methodName,Object[] args)";
            String logMessage = "The arguments of of Bean " + class1
                    + "'s method: " + methodName
                    + " are illegal, please check the arguments";
            EasyJException ee = new EasyJException(ex2, location, logMessage);
            throw ee;
        } catch (IllegalAccessException ex2) {
            String location = "BeanUtil.invokeStaticMethod(Class class1, String methodName,Object[] args)";
            String logMessage = "Bean " + class1 + "'s method: " + methodName
                    + " is private, please make it public";
            EasyJException ee = new EasyJException(ex2, location, logMessage);
            throw ee;
        }
    }

    /**
     * 依据方法名调用某个对象的方法
     * 
     * @param obj
     *                Object 被调用方法的对象
     * @param methodName
     *                String 方法名
     * @param args
     *                Object[] 方法需要的参数
     * @throws EasyJException
     * @return Object 返回方法执行后的返回值
     */
    public static Object invokeMethod(Object obj, String methodName,
            Object[] args) throws EasyJException {
        Class class1 = obj.getClass();
        try {
            Method method = null;
            if (args == null) {
                method = class1.getMethod(methodName, null);
            } else {
                Class argsClass[] = new Class[args.length];
                for (int i = 0; i < args.length; i++) {
                    argsClass[i] = args[i].getClass();
                }
                method = class1.getDeclaredMethod(methodName, argsClass);
            }
            Object obj1 = method.invoke(obj, args);
            return obj1;
        } catch (SecurityException ex1) {
            String location = "BeanUtil.invokeMethod(Object obj, String methodName,Object[] args)";
            String logMessage = "Bean " + class1 + "'s method: " + methodName
                    + " is private, please make it public";
            EasyJException ee = new EasyJException(ex1, location, logMessage);
            throw ee;
        } catch (NoSuchMethodException ex1) {
            String location = "BeanUtil.invokeMethod(Object obj, String methodName,Object[] args)";
            String logMessage = "Bean "
                    + class1
                    + "'s method: "
                    + methodName
                    + " doesn't exist, please add it. Or the method is not static, please add static to decorate";
            EasyJException ee = new EasyJException(ex1, location, logMessage);
            throw ee;
        } catch (InvocationTargetException ex2) {
            String location = "BeanUtil.invokeMethod(Object obj, String methodName,Object[] args)";
            String logMessage = "Exception occurs at Bean " + class1
                    + "'s method: " + methodName
                    + ", please check the method. 被调用方法发生异常，检测那个方法";
            Exception cause = (Exception) ex2.getCause();
            EasyJException ee = new EasyJException(cause, location, logMessage);
            throw ee;
        } catch (IllegalArgumentException ex2) {
            String location = "BeanUtil.invokeMethod(Object obj, String methodName,Object[] args)";
            String logMessage = "The arguments of of Bean " + class1
                    + "'s method: " + methodName
                    + " are illegal, please check the arguments";
            EasyJException ee = new EasyJException(ex2, location, logMessage);
            throw ee;
        } catch (IllegalAccessException ex2) {
            String location = "BeanUtil.invokeMethod(Object obj, String methodName,Object[] args)";
            String logMessage = "Bean " + class1 + "'s method: " + methodName
                    + " is private, please make it public";
            EasyJException ee = new EasyJException(ex2, location, logMessage);
            throw ee;
        }
    }

    /**
     * 功能：用来将从客户端传来的数据的封装到一个用className类创建的对象中。需要保证的是properties当中的key的值和对象属性名的值是一样的
     * 
     * @param className
     *                String 用来表示需要将客户端传来的数据封装到一个什么类型的对象当中。
     * @param HashMap
     *                properties 从request中得到的各种数据，包括文件数据。
     * @param errors
     *                ValidateErrors 用来封装在对客户端数据进行校验的时候所得到的不符合校验规范的错误。
     *                校验规范可以参考Property表的validate_rule字段
     * @return 得到相应的对象。
     */
    public static Object getObject(String className, HashMap properties,
            ValidateErrors errors) throws EasyJException {
        Object obj = getEmptyObject(className);
        obj = getObject(obj, properties, errors);
        return obj;
    }

    /**
     * 功能：用来将从客户端传来的数据的封装到一个用className类创建的对象中，此对象为主子表对象。
     * 需要保证的是主表的properties当中的key的值和对象属性名的值是一样的；子表字段的属性都一样，所以就会存到数组当中
     * 
     * @param className
     *                String 用来表示需要将客户端传来的数据封装到一个什么类型的对象当中。
     * @param HashMap
     *                properties 从request中得到的各种数据，包括文件数据。
     * @param errors
     *                ValidateErrors 用来封装在对客户端数据进行校验的时候所得到的不符合校验规范的错误。
     *                校验规范可以参考Property表的validate_rule字段
     * @return 得到相应的对象。
     */
    public static Object getCompositObject(String className,
            HashMap properties, ValidateErrors errors) throws EasyJException {
        Object obj = getEmptyObject(className);
        /* 获得主表的内容 */
        obj = getCompositObject(obj, properties, errors);

        return obj;
    }

    /**
     * <p>
     * 功能：用来将从客户端传来的数据的封装到一个用className类创建的对象中，此对象为主子表对象。
     * </p>
     * <p>
     * 需要保证的是主表的properties当中的key的值和对象属性名的值是一样的；子表字段的属性都一样，所以就会存到数组当中
     * </p>
     * <p>
     * 对于主子表中删除子表的记录，也是要传到服务器的，只是使用状态变为了不使用。对于新增的记录
     * </p>
     * 
     * @param obj
     *                Object 用来封装客户端数据的对象。
     * @param HashMap
     *                properties 从request中得到的各种数据，包括文件数据。
     * @param errors
     *                ValidateErrors 用来封装在对客户端数据进行校验的时候所得到的不符合校验规范的错误。
     *                校验规范可以参考Property表的validate_rule字段
     * @return 得到相应的对象。
     */
    public static Object getCompositObject(Object obj, HashMap properties,
            ValidateErrors errors) throws EasyJException {
        /* 获得主表的内容 */
        obj = getObject(obj, properties, errors);
        String primaryKeyName = getPrimaryKeyName(obj.getClass());
        Object primaryKeyValue = getPrimaryKeyValue(obj);
        /* 下面获得更新的子表数据 */
        String[] subClassProperties = getSubClassProperties(obj.getClass());
        /* 对所有的子表属性进行循环 */
        for (int j = 0; j < subClassProperties.length; j++) {
            String subClassProperty = subClassProperties[j];
            ArrayList details = (ArrayList) BeanUtil.getFieldValue(obj,
                    subClassProperty);

            /* 对每一条子表记录进行赋值,detailsize代表从服务器送给客户端的明细条数，clientValueSize代表从客户端传来的明细条数 */
            int detailSize = 0;
            if (details != null)
                detailSize = details.size();
            else
                details = new ArrayList();
            // int clientValueSize=0;
            // String a[]=(String[])properties.get("detailSize");
            // if(a!=null)
            // clientValueSize=Integer.parseInt(a[0])-1;

            String subClass = getSubClass(obj.getClass(), "details");
            String detailPrimaryKeyName = getPrimaryKeyName(getClass(subClass));
            Object value = properties.get(detailPrimaryKeyName);
            if (value != null) {
                String[] primaryKeyValues = (String[]) value;
                for (int i = 0; i < primaryKeyValues.length; i++) {
                    // 说明是新增的
                    if (GenericValidator.isBlankOrNull(primaryKeyValues[i])) {
                        Object subObj = getEmptyObject(BeanUtil.getSubClass(obj
                                .getClass(), subClassProperty));
                        subObj = getObject(subObj, properties, null, errors, i);
                        setFieldValue(subObj, "sequence", new Short((short) i));
                        /* 如果新增明细的时候，这里要设置新增明细的主表外键。其他情况主表主键为空的时候则不处理 */
                        if (primaryKeyValue != null)
                            setFieldValue(subObj, primaryKeyName,
                                    primaryKeyValue);
                        details.add(subObj);
                    } else {
                        for (int k = 0; k < detailSize; k++) {
                            Object subObj = details.get(k);
                            Long subPrimaryKeyValue = (Long) getPrimaryKeyValue(subObj);
                            if (subPrimaryKeyValue.longValue() == Long
                                    .parseLong(primaryKeyValues[i])) {
                                getObject(subObj, properties, null, errors, i);
                                setFieldValue(subObj, "sequence", new Short(
                                        (short) i));
                                break;
                            }
                        }
                    }
                }
            }
            // for (int k = 0; k < detailSize; k++) {
            // //得到此类的所有的column。
            // Object subObj=details.get(k);
            // getObject(subObj,properties,null,errors,k);
            // }
            // /*如果用户有新增明细数据，则在这里处理*/
            // for(int l=detailSize;l<clientValueSize;l++)
            // {
            // Object subObj =
            // getEmptyObject(BeanUtil.getSubClass(obj.getClass()
            // ,subClassProperty));
            // subObj=getObject(subObj,properties,null,errors,l);
            // setFieldValue(subObj,"sequence",new Short((short)l));
            // /*如果新增明细的时候，这里要设置新增明细的主表外键。其他情况主表主键为空的时候则不处理*/
            // if(primaryKeyValue!=null)
            // setFieldValue(subObj,primaryKeyName,primaryKeyValue);
            // details.add(subObj);
            // }

            setFieldValue(obj, subClassProperty, details);
        }

        return obj;
    }

    /**
     * <p>
     * 用来获得主子表中某个字段对应子表的类
     * </p>
     * 
     * @param clazz
     *                Class 主表的类对象
     * @param propertyName
     *                String 主表的属性名
     * @throws EasyJException
     * @return String 返回此属性对应的子表所对应的类名。
     */
    public static String getSubClass(Class clazz, String propertyName)
            throws EasyJException {
        String[] parameters = {
            propertyName
        };
        return (String) BeanUtil.invokeStaticMethod(clazz, "getSubClass",
                parameters);
    }

    /**
     * <p>
     * 在主子表对应的类中，有些属性是用来代表子表数据的。本方法是用来获得主子表类中所有对应了子表信息的字段。其实是调用了相应的类的getSubClassProperties方法
     * </p>
     * 
     * @param clazz
     *                Class 主子表对应的类
     * @throws EasyJException
     * @return String[] 返回此类中所有对应子表的属性。
     */
    public static String[] getSubClassProperties(Class clazz)
            throws EasyJException {
        return (String[]) BeanUtil.invokeStaticMethod(clazz,
                "getSubClassProperties", null);
    }

    /**
     * 用来将某对象的主键值设为NULL。用在删除之后，再此进行查询的时候需要将主键设为NULL。
     * 
     * @param o
     *                Object 需要将主键置空的对象
     * @throws EasyJException
     */
    public static void setPrimaryKeyNull(Object o) throws EasyJException {
        String primaryKey = (String) getPubStaticFieldValue(o.getClass(),
                Const.PRIMARY_KEY);
        setFieldValue(o, primaryKey, null);
    }

    /**
     * 获得某对象的主键值，只对应单主键的情况
     * 
     * @param o
     *                Object 要获得主键值的对象
     * @throws EasyJException
     * @return Object 主键值
     */
    public static Object getPrimaryKeyValue(Object o) throws EasyJException {
        String primaryKey = (String) getPubStaticFieldValue(o.getClass(),
                Const.PRIMARY_KEY);
        // EasyJLog.debug("o is:" + o);
        return getFieldValue(o, primaryKey);
    }

    /**
     * 获得某个类的主键名称
     * 
     * @param clazz
     *                Class 需要获得主键名称的类对象
     * @throws EasyJException
     * @return String 返回主键名称
     */
    public static String getPrimaryKeyName(Class clazz) throws EasyJException {
        String primaryKey = (String) getPubStaticFieldValue(clazz,
                Const.PRIMARY_KEY);
        return primaryKey;
    }

    /**
     * 获得某个类的主键名称
     * 
     * @param className
     *                String 需要获得主键的类的名称
     * @throws EasyJException
     * @return String 返回主键名称
     */
    public static String getPrimaryKeyName(String className)
            throws EasyJException {
        Class clazz = getClass(className);
        String primaryKey = (String) getPubStaticFieldValue(clazz,
                Const.PRIMARY_KEY);
        return primaryKey;
    }

    /**
     * 功能：用来将从客户端传来的数据的封装到某个已经有数据的对象中。需要保证的是properties当中的key的值和对象属性名的值是一样的
     * 之所以要这个方法是因为当用户要更新数据的时候，需要首先从数据库得到数据，否则如果客户端传过来的数据不全的话
     * 就会造成数据丢失，所以当用户更新的时候首先从数据库得到对象，然后再将客户端的数据封装入对象当中，这样来避免数据丢失
     * 
     * @param HashMap
     *                properties 从request中得到的各种数据，包括文件数据。
     * @return 得到相应的对象。
     */
    public static Object getObject(Object obj, HashMap properties,
            ValidateErrors errors) throws EasyJException {
        return getObject(obj, properties, "", errors);
    }

    public static Object getObject(String className, HashMap properties,
            String prefix, ValidateErrors errors) throws EasyJException {
        // 首先将没有上下限的数据得到，否则这些数据用有前缀的属性值是得不到的。
        Object obj = getObject(className, properties, errors);
        return getObject(obj, properties, prefix, errors);
    }

    public static Object getObject(Object obj, HashMap properties,
            String prefix, ValidateErrors errors) throws EasyJException {
        return getObject(obj, properties, prefix, errors, 0);
    }

    /**
     * @param className
     *                String
     * @param properties
     *                HashMap
     * @param prefix
     *                String 其值为upper or lower代表查询的上下界
     * @param position
     *                代表从第客户端传来的数组中取第几个数据。主要用在主子表的子表获取，对于单表来说position默认为0
     * @throws EasyJException
     * @return Object
     */

    public static Object getObject(Object obj, HashMap properties,
            String prefix, ValidateErrors errors, int position)
            throws EasyJException {
        String className = obj.getClass().getName();
        // 得到此类的所有的column。
        Field fields[] = obj.getClass().getDeclaredFields();
        // 将客户端数据封装到obj里面去。将obj的属性设值
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            // 如果是静态的属性则不需要进行设置
            if (java.lang.reflect.Modifier.isStatic(field.getModifiers())) {
                continue;
            }
            Class type = field.getType();
            String fieldName = field.getName();
            /* 这里得到property是为了得到property的type信息，如果此property的type是checkboxs，那么就需要对客户端传过来的信息进行处理 */
            UserPropertyRight property = (UserPropertyRight) SystemDataCache
                    .getPropertyHT(false).get(obj.getClass().getName() + fieldName);
            Object value = null;
            boolean detailCheckBox = false;
            if (!GenericValidator.isBlankOrNull(prefix)) {
                value = properties.get(prefix + fieldName);
            } else {
                value = properties.get(fieldName);
                //当明细当中有checkbox的时候，而且checkbox可能数目不一致，就一定需要知道是第几行的。
                if ("columns".equals(fieldName)) {
                    System.out.println("columns".equals(fieldName));
                }
                if (value == null) {
                    value = properties.get(fieldName + position);
                    if (value != null) {
                        detailCheckBox = true;
                    }
                }
            }
            //明细中含有checkbox的时候的做法，todo:这里将来如何处理要分情况，一种是现在这种直接填值的，另外一种是和下面的checkbox类似的。
            if (detailCheckBox) {
                String propertyValue = ",";
                String[] valueArr = (String[]) value;
                for (String v : valueArr) {
                    propertyValue += (v + ",");
                }
                BeanUtil.setFieldValue(obj, fieldName, propertyValue);
                continue;
            }

            
            /*
             * value==null的时候不应该直接continue，因为如果是checkbox的话，取消之后虽然没有数据传过来，但是那是意味着取消操作
             * property==null意味着用户没有权限看到，所以就不做任何处理。如果prefix存在说明是查询，则不做处理。
             */
            
            if (value == null) {
                if (property == null || !"checkboxs".equals(property.getType())
                        || !GenericValidator.isBlankOrNull(prefix))
                    continue;
                else {
                    BeanUtil.setFieldValue(obj, fieldName, null);
                    BeanUtil.setFieldValue(obj, property.getShowProperty(),
                            null);
                }
            }
            /* property中的数据除了FormFile之外，都是String[]。 */
            if (value instanceof String[]) {
                String[] valueArray = (String[]) value;
                /*
                 * 如果是checkboxs类型的，则需要将用户客户端传过来的数据进行处理。在此情况下，客户端传过来的数据格式是
                 * id,name，需要取出id，和name并分别付给ids和names属性
                 */
                if (property != null && "checkboxs".equals(property.getType())) {
                    String ids = ",", names = "";
                    for (int j = 0; j < valueArray.length; j++) {
                        StringTokenizer st = new StringTokenizer(valueArray[j],
                                ",");
                        ids = ids + st.nextToken() + ",";
                        if (j != valueArray.length - 1)
                            names = names + st.nextToken() + "、";
                        else
                            names = names + st.nextToken();
                    }
                    BeanUtil.setFieldValue(obj, fieldName, ids);
                    BeanUtil.setFieldValue(obj, property.getShowProperty(),
                            names);
                } else {
                    /*
                     * 有些情况比如子表的外键，也就是主表的主键是不在字表明细当中列出的，而且也是不可能被改变的，所以传过来的就只能有一个值，
                     * 在这种情况下valueArray.length<=position，就不应该执行
                     */
                    if (valueArray.length > position) {
                        String errorMsg = Validate.validateProperty(className
                                + fieldName, valueArray[position]);
                        if (!GenericValidator.isBlankOrNull(errorMsg)) {
                            errors.addErrorMsg(fieldName, errorMsg);
                        }
                        /* 如果传过来的数据不存在，说明不需要更新，如果是空字符串说明数据需要更新为NULL */
                        if ("".equals(valueArray[position])) {
                            setFieldValue(obj, fieldName, null);
                        } else {
                            setFieldValue(obj, fieldName, StringUtil
                                    .changeType(type, valueArray[position]));
                        }
                    }
                }
            } else {
                /* 在这里处理当有文件传过来的时候 */
            }

        }
        // EasyJLog.debug("obj is:"+obj);
        return obj;
    }

    /**
     * 用来将一个对象的数据传递给另外一个对象，这在数据之间的转换经常会用到。
     * 需要注意的是暂时是按照数据之间属性名相同这个规则来进行转换，但是将来应该支持通过配置文件来进行两个数据的转换。
     * 此方法支持值拷贝和引用拷贝，支持对主子表的处理（暂时没做，将来应该补上）。
     * 
     * @param source
     *                Object 被拷贝的对象
     * @param dest
     *                Object 目标对象
     * @param valueCopy
     *                boolean 是否是值拷贝
     * @param withNullValue
     *                boolean 是否拷贝null值，即是否将源对象中的null拷贝到目标对象。如果拷贝会造成目标对象数据丢失。
     * @throws EasyJException
     */
    public static void transferObject(Object source, Object dest,
            boolean valueCopy, boolean withNullValue) throws EasyJException {
        if (source == null)
            return;
        Field fields[] = source.getClass().getDeclaredFields();
        int length = fields.length;
        for (int i = 0; i < length; i++) {
            Field field = fields[i];
            String fieldName = field.getName();
            // 暂时只处理非静态属性，以及非子表属性。将来需要处理子表数据。
            if (!java.lang.reflect.Modifier.isStatic(fields[i].getModifiers())
                    && BeanUtil.getSubClass(source.getClass(), fieldName) == null) {
                Object value = getFieldValue(source, field.getName());
                Object cpValue = null;
                if (valueCopy && value != null)
                    cpValue = cloneObject(value);

                try {
                    if (value != null)
                        setFieldValue(dest, field.getName(), cpValue);
                    else if (withNullValue)
                        setFieldValue(dest, field.getName(), null);
                } catch (Exception e) {
                    // 如果需要设置的对象没有，则什么都不做，接着进行循环。
                }
            }
        }
    }

    /**
     * 这个方法是用来将一个对象序列化到客户端，参见Ajax.js文件中的Ajax.submit方法，在此方法中对序列化的文本进行反序列化得到对象。
     * 具体的规则为"属性名=值" 例如："studentId=1&studentName=liu";
     * 
     * @param obj
     * @return
     * @throws EasyJException
     */
    public static StringBuffer serializeObjectToClient(Object obj)
            throws EasyJException {
        StringBuffer buffer = new StringBuffer();
        Class clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();
        String fieldName;
        Object value;
        Method getter;
        // 1.设置ValueObject的属性
        int size = fields.length;
        for (int i = 0; i < size; i++) {
            fieldName = fields[i].getName();
            // 1.1 取得属性
            String getterName = "get" + fieldName.substring(0, 1).toUpperCase()
                    + fieldName.substring(1);
            try {
                getter = clazz.getDeclaredMethod(getterName, null);
            } catch (NoSuchMethodException me) {
                continue;
            }
            try {
                value = getter.invoke(obj, null);
            } catch (Exception e) {
                continue;
            }
            if (value != null)
                buffer.append(fieldName + "=" + value);
            if (i != size - 1)
                buffer.append("&");
        }
        return buffer;
    }
}
