package easyJ.tools;

public interface Generator {
    public BeanInfo generateBeanInfo(String tableName, String viewName)
            throws easyJ.common.EasyJException;

    public void generateXML(String tableName, String viewName)
            throws easyJ.common.EasyJException;

    public String generateBean(String tableName, String viewName)
            throws easyJ.common.EasyJException;
}
