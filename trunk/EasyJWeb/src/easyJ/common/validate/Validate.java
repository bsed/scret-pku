package easyJ.common.validate;

import easyJ.system.data.Property;
import java.util.StringTokenizer;
import easyJ.common.BeanUtil;
import easyJ.common.EasyJException;
import easyJ.system.data.SystemDataCache;
import easyJ.system.data.UserPropertyRight;

public class Validate {
    public Validate() {}

    public static String getClientValidate(String classPropertyName)
            throws EasyJException {
        UserPropertyRight property = (UserPropertyRight) SystemDataCache
                .getPropertyHT(false).get(classPropertyName);
        if (property == null)
            return null;
        /*
         * 校验规则的格式是：不同的校验规则用分号(;)隔开。一个校验规则所用到的参数用逗号(,)隔开。比如一个字段需要进行float校验，
         * 而且不能大于100，不能小于0（学生的成绩）。表示规则应该是：isFloat;maxValue,100;minValue,0 。
         */
        String propertyChiName = property.getPropertyChiName();
        String validateRules = property.getValidateRule();
        if (GenericValidator.isBlankOrNull(validateRules))
            return null;
        // 得到校验的规则
        StringTokenizer ruleToken = new StringTokenizer(validateRules, ";");
        StringBuffer buffer = new StringBuffer();
        // buffer.append("javascript:");
        while (ruleToken.hasMoreElements()) {
            String validateRule = ruleToken.nextToken();
            StringTokenizer methodParamToken = new StringTokenizer(
                    validateRule, ",");
            String method = methodParamToken.nextToken();
            buffer.append(method);
            buffer.append("(this.value,");
            String errorMsg = ErrorMsg.getErrorMsg(method);
            while (methodParamToken.hasMoreTokens()) {
                String parameter = methodParamToken.nextToken();
                buffer.append(parameter);
                buffer.append(",");
                errorMsg = errorMsg.replaceFirst("\\?", parameter);
            }
            buffer.append("'" + propertyChiName + errorMsg + "'");
            buffer.append(",this");
            buffer.append(");");
        }
        return buffer.toString();
        // return "alert(this.value)";
    }

    public static String validateProperty(String classPropertyName, String value)
            throws EasyJException {
        /*
         * 校验规则的格式是：不同的校验规则用分号(;)隔开。一个校验规则所用到的参数用逗号(,)隔开。比如一个字段需要进行float校验，
         * 而且不能大于100，不能小于0（学生的成绩）。表示规则应该是：isFloat;maxValue,100;minValue,0 。
         */
        UserPropertyRight property = (UserPropertyRight) SystemDataCache
                .getPropertyHT(false).get(classPropertyName);
        if (property == null)
            return null;
        String propertyChiName = property.getPropertyChiName();
        String validateRules = property.getValidateRule();
        if (GenericValidator.isBlankOrNull(validateRules))
            return null;

        // 得到校验的规则
        StringTokenizer ruleToken = new StringTokenizer(validateRules, ";");
        while (ruleToken.hasMoreElements()) {
            String validateRule = ruleToken.nextToken();
            StringTokenizer methodParamToken = new StringTokenizer(
                    validateRule, ",");
            String method = methodParamToken.nextToken();
            String[] parameters = new String[3]; // 当前情况校验的参数最多为2个，加上被校验的值，总共三个参数。
            int i = 1;
            parameters[0] = value;
            String errorMsg = ErrorMsg.getErrorMsg(method);
            while (methodParamToken.hasMoreTokens()) {
                parameters[i] = methodParamToken.nextToken();
                errorMsg = errorMsg.replaceFirst("\\?", parameters[i]);
                i++;
            }
            Boolean result = null;
            /* i=0说明没有参数，i=1说明有1个参数，i=2说明有两个参数 */
            switch (i) {
                case 1:
                    String[] a = {
                        parameters[0]
                    };
                    result = (Boolean) BeanUtil.invokeStaticMethod(
                            GenericValidator.class, method, a);
                    break;
                case 2:
                    String[] b = {
                        parameters[0], parameters[1]
                    };
                    result = (Boolean) BeanUtil.invokeStaticMethod(
                            GenericValidator.class, method, b);
                    break;
                case 3:
                    result = (Boolean) BeanUtil.invokeStaticMethod(
                            GenericValidator.class, method, parameters);
                    break;
            }
            /* 如果有一个规则被违反了，就不用管其他的规则了。比如：如果违反了不是数字，那么就不用比较最大最小值了。 */
            if (!result.booleanValue())
                return propertyChiName + errorMsg;
        }
        return null;
    }
}
