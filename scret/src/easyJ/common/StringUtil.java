package easyJ.common;

import easyJ.common.validate.Validate;
import easyJ.common.validate.GenericValidator;

public class StringUtil {
    public StringUtil() {}

    public static final char upperLower = 'a' - 'A';

    /**
     * 此方法将字符串转换为clazz对应的类型
     * 
     * @param clazz
     *                Class 需要转换的目标类型
     * @param value
     *                String 需要被转换的字符串
     * @return Object 转换完之后的对象。
     */
    public static Object changeType(Class clazz, String value)
            throws EasyJException {
        if (GenericValidator.isBlankOrNull(value))
            return null;
        try {
            if (clazz.equals(Long.class))
                return new Long(value);
            else if (clazz.equals(java.math.BigDecimal.class))
                return new java.math.BigDecimal(value);
            else if (clazz.equals(java.sql.Date.class))
                return java.sql.Date.valueOf(value);
            else if (clazz.equals(Integer.class))
                return new Integer(value);
            else if (clazz.equals(Short.class))
                return new Short(value);
            else if (clazz.equals(Boolean.class))
                return new Boolean(value);
            else if (clazz.equals(Byte.class))
                return new Byte(value);
            else if (clazz.equals(Float.class))
                return new Float(value);
            else if (clazz.equals(Double.class))
                return new Double(value);
            else if (clazz.equals(java.sql.Time.class))
                return java.sql.Time.valueOf(value);
            else if (clazz.equals(java.sql.Timestamp.class))
                return java.sql.Timestamp.valueOf(value);
        } catch (Exception e) {
            e.printStackTrace();
            String clientMessage = "服务器忙";
            String location = "easyJ.common.StringUtil.changeType(Class clazz,String value)";
            String logMessage = "在将\"" + value + "\"转化为\"" + clazz + "\"时出现错误";
            EasyJException ee = new EasyJException(e, location, logMessage,
                    clientMessage);
            throw ee;

        }
        return value;
    }

    /**
     * 此方法用来将各种特殊符号转化为html的转意字符。主要是将数据库中的内容进行HTML显示的格式化
     * 
     * @param str
     *                String 需要转意的字符串
     * @return String 转意完之后的字符串
     */
    public static String fixString(String str) {
        if (str == null)
            return "";
        str = str.replaceAll("\'", "&sq;");
        return str;
    }

    public static String upperCaseFirstLetter(String value) {
        if (GenericValidator.isBlankOrNull(value))
            return value;
        char[] newValue = value.toCharArray();
        if (newValue[0] <= 'z' && newValue[0] >= 'a')
            newValue[0] = (char) (newValue[0] - upperLower);
        return new String(newValue);
    }

    public static String fixEmpty(Object obj) {
        if (obj == null)
            return "";
        else
            return obj.toString();
    }
}
