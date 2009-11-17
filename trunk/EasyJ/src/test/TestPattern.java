package test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class TestPattern {
    public static void main(String args[]) {
        String patterStr = ".*+(\"(.*)\")*.*+";
        String content = "a\"密码错误\"";
        Pattern pa = Pattern.compile(patterStr);
        Matcher ma = pa.matcher(content);
        System.out.println(ma.matches());
        System.out.println(ma.group(1));
        System.out.println(ma.group(2));
    }
}

