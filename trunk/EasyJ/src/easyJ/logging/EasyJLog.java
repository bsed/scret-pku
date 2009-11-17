package easyJ.logging;

public class EasyJLog {
    public EasyJLog() {}

    public static void debug(Object o) {
        System.out.println(o.toString());
    }

    public static void log(Object o) {
        System.out.println(o.toString());
    }

    public static void error(Object o) {

    }

    public static void error(Object o, Exception e) {

    }

    public static void warn(Object o) {

    }
}
