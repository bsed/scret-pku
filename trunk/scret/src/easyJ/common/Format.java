package easyJ.common;

import java.text.DateFormat;
import java.util.Locale;
import java.text.NumberFormat;
import java.util.Currency;

/**
 * 这个类用来控制输出用的，主要使用在向客户端的格式化输入，将来所有的输出格式的控制都应该在这里。
 */

public class Format {
    public Format() {}

    public static String format(Object o) {
        if (o == null)
            return "";
        return o.toString();
    }

    public static String formatDisplay(Object o) {
        /* 这里会根据客户的需求决定当值为空的时候，到底显示什么，这里默认的显示一个空字符串 */
        if (o == null)
            return "";
        if (o instanceof java.sql.Date) {
            /* 这两个变量DEFAULT以及CHINA在将来应该是允许用户自己修改的 */
            DateFormat df = DateFormat.getDateInstance(DateFormat.DEFAULT,
                    Locale.CHINA);
            return df.format((java.sql.Date) o);
        }
        if ((o instanceof Integer)) {
            NumberFormat nf = NumberFormat.getIntegerInstance(Locale.CHINA);
            nf.setMinimumFractionDigits(0);
            nf.setMaximumFractionDigits(0);
            return nf.format((Integer) o);
        }
        if ((o instanceof Long)) {
            NumberFormat nf = NumberFormat.getIntegerInstance(Locale.CHINA);
            nf.setMinimumFractionDigits(0);
            nf.setMaximumFractionDigits(0);
            return nf.format((Long) o);
        }
        if ((o instanceof java.math.BigDecimal)) {
            NumberFormat nf = NumberFormat.getIntegerInstance(Locale.CHINA);
            nf.setCurrency(Currency.getInstance(Locale.CHINA));
            nf.setMinimumFractionDigits(2);
            nf.setMaximumFractionDigits(4);
            return nf.format((java.math.BigDecimal) o);
        }
        return o.toString();
    }
}
