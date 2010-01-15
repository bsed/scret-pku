package easyJ.logging;

public interface Log {
    public void debug(Object o);

    public void log(Object o);

    public void error(Object o);

    public void error(Object o, Exception e);

    public void warn(Object o);
}
