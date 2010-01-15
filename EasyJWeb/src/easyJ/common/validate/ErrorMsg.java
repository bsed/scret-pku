package easyJ.common.validate;

import java.util.Locale;
import java.text.DateFormat;
import java.util.Hashtable;

/* 如果将来要国际化，可以从资源文件中取。message中的问号是要被替换的变量 */
public class ErrorMsg {
    private static Hashtable ht = new Hashtable();

    public static final String isDouble = "必须为数字";

    public static final String isEmail = "必须为有效的EMAIL地址";

    public static final String isInt = "必须为整数";

    public static final String isFloat = "必须为浮点数";

    public static final String isLong = "必须为长整数";

    public static final String isShort = "必须为短整数，范围不可超过。。。";

    public static final String isUrl = "必须为有效的Url";

    public static final String isDate = "必须为有效的日期，格式为：yyyy/mm/dd";

    public static final String isCredit = "必须为有效的信用卡号";

    public static final String minLen = "的长度至少为?";

    public static final String maxLen = "的长度至多为?";

    public static final String minValue = "至少为?";

    public static final String maxValue = "至多为?";

    public static final String isInRange = "应该在?与?之间";

    public static final String matchRegexp = "的内容应该符合\"?\"";
    /* 放入hashtable的key要和校验方法名完全一样 */
    static {
        ht.put("isDouble", isDouble);
        ht.put("isEmail", isEmail);
        ht.put("isInt", isInt);
        ht.put("isFloat", isFloat);
        ht.put("isLong", isLong);
        ht.put("isShort", isShort);
        ht.put("isUrl", isUrl);
        ht.put("isDate", isDate);
        ht.put("isCredit", isCredit);
        ht.put("minLen", minLen);
        ht.put("maxLen", maxLen);
        ht.put("minValue", minValue);
        ht.put("maxValue", maxValue);
        ht.put("isInRange", isInRange);
        ht.put("matchRegexp", matchRegexp);
    }

    public ErrorMsg() {}

    public static String getErrorMsg(String method) {
        return (String) ht.get(method);
    }
}
