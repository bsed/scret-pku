package easyJ.tools;

import easyJ.database.session.*;
import easyJ.database.*;
import java.util.ArrayList;
import easyJ.database.ColumnPropertyMapping;
import easyJ.database.dao.*;
import easyJ.common.StringUtil;
import easyJ.database.dao.command.SelectCommand;

public class GeneratorSQLServerImpl implements Generator {
    private GeneratorSQLServerImpl() {}

    private static Generator instance = null;

    public static Generator getInstance() {
        if (instance == null)
            instance = new GeneratorSQLServerImpl();
        return instance;
    }

    public BeanInfo generateBeanInfo(String tableName, String viewName)
            throws easyJ.common.EasyJException {
        BeanInfo bi = new BeanInfo();
        bi.setTableName(tableName);
        bi.setViewName(viewName);
        Column column = new Column();
        column.setTableName(tableName);
        Session session = SessionFactory.openSession();
        SelectCommand scmd = DAOFactory.getSelectCommand(Column.class);
        scmd.setFilter(DAOFactory.getFilter("tableName", SQLOperator.EQUAL,
                tableName));
        ArrayList tableProperties = session.query(scmd);
        bi.setTableProperties(tableProperties);
        for (int i = 0; i < tableProperties.size(); i++) {
            column = (Column) tableProperties.get(i);
            if ("Y".equals(column.getIsPrimaryKey()))
                bi.setPrimaryKey(column.getColumnName());
        }
        scmd = DAOFactory.getSelectCommand(Column.class);
        scmd.setFilter(DAOFactory.getFilter("tableName", SQLOperator.EQUAL,
                viewName));
        ArrayList viewProperties = session.query(scmd);
        viewProperties.removeAll(tableProperties);
        bi.setViewProperties(viewProperties);
        return bi;
    }

    public String generateBean(String tableName, String viewName)
            throws easyJ.common.EasyJException {
        BeanInfo bi = generateBeanInfo(tableName, viewName);
        StringBuffer beanStr = new StringBuffer();
        beanStr.append("public class ");
        String className = ColumnPropertyMapping.getPropertyName(tableName);
        className = (char) (className.charAt(0) - 'a' + 'A')
                + className.substring(1, className.length());
        beanStr.append(className);
        beanStr.append(" implements  java.io.Serializable{\n");
        beanStr.append("  public static final String TABLE_NAME=\""
                + bi.getTableName() + "\";\n");
        beanStr.append("  public static final String VIEW_NAME=\""
                + bi.getViewName() + "\";\n");
        beanStr.append("  public static final String PRIMARY_KEY=\""
                + ColumnPropertyMapping.getPropertyName(bi.getPrimaryKey())
                + "\";\n");
        beanStr.append(generateProperty(bi.getTableProperties()));
        beanStr.append(generateProperty(bi.getViewProperties()));
        beanStr.append(generateIsUpdatable(bi.getViewProperties()));
        beanStr.append(generateGetSubClass());
        beanStr.append(generateGetSet(bi.getTableProperties()));
        beanStr.append(generateGetSet(bi.getViewProperties()));
        beanStr.append(generateClone());
        beanStr.append(generateEquals(bi));
        beanStr.append(generateHashCode(bi));
        bi.getTableProperties().addAll(bi.getViewProperties());
        beanStr.append(generateToString(bi.getTableProperties()));
        beanStr.append("\n}");
        return beanStr.toString();
    }

    public StringBuffer generateGetSubClass() {
        StringBuffer getSubClass = new StringBuffer();
        getSubClass
                .append("  public static String getSubClass(String property){\n");
        getSubClass.append("    return null;\n");
        getSubClass.append("  }\n");
        return getSubClass;
    }

    public StringBuffer generateIsUpdatable(ArrayList viewProperties) {
        StringBuffer isUpdatable = new StringBuffer();
        isUpdatable
                .append("  public static Boolean isUpdatable(String property){\n");
        for (int i = 0; i < viewProperties.size(); i++) {
            Column column = (Column) viewProperties.get(i);
            isUpdatable.append("      if(\"");
            isUpdatable.append(ColumnPropertyMapping.getPropertyName(column
                    .getColumnName()));
            isUpdatable.append("\".equals(");
            isUpdatable.append("property))");
            isUpdatable.append("    return new Boolean(false);\n");
        }
        isUpdatable.append("    return new Boolean(true);");
        isUpdatable.append("\n  }\n");
        return isUpdatable;
    }

    public StringBuffer generateProperty(ArrayList properties) {
        TypeMapping tm = TypeMappingFactory.getTypeMapping();
        StringBuffer propertyStr = new StringBuffer();
        for (int i = 0; i < properties.size(); i++) {
            Column column = (Column) properties.get(i);
            propertyStr.append("  private ");
            propertyStr.append(tm.getJavaTypeFromSqlType(column.getType()));
            propertyStr.append(" ");
            propertyStr.append(ColumnPropertyMapping.getPropertyName(column
                    .getColumnName()));
            propertyStr.append(";\n");
        }

        return propertyStr;
    }

    public static StringBuffer generateGetSet(ArrayList properties) {
        TypeMapping tm = TypeMappingFactory.getTypeMapping();
        StringBuffer getSetStr = new StringBuffer();
        for (int i = 0; i < properties.size(); i++) {
            Column column = (Column) properties.get(i);
            String propertyName = ColumnPropertyMapping.getPropertyName(column
                    .getColumnName());
            getSetStr.append("  public ");
            getSetStr.append(tm.getJavaTypeFromSqlType(column.getType()));
            getSetStr.append(" get");
            getSetStr.append((char) (propertyName.charAt(0) - 'a' + 'A'));
            getSetStr.append(propertyName.substring(1, propertyName.length()));
            getSetStr.append("() ");
            getSetStr.append("    {");
            getSetStr.append(" return this.");
            getSetStr.append(propertyName);
            getSetStr.append(";}");
            getSetStr.append("\n");

            getSetStr.append("  public void");
            getSetStr.append(" set");
            getSetStr.append((char) (propertyName.charAt(0) - 'a' + 'A'));
            getSetStr.append(propertyName.substring(1, propertyName.length()));
            getSetStr.append("(");
            getSetStr.append(tm.getJavaTypeFromSqlType(column.getType()));
            getSetStr.append(" ");
            getSetStr.append(propertyName);
            getSetStr.append(") ");
            getSetStr.append("  {");
            getSetStr.append(" this.");
            getSetStr.append(propertyName);
            getSetStr.append("=");
            getSetStr.append(propertyName);
            getSetStr.append(";");
            getSetStr.append("}");
            getSetStr.append("\n");
        }
        return getSetStr;
    }

    public StringBuffer generateClone() {
        StringBuffer cloneStr = new StringBuffer();
        cloneStr.append("  public Object clone(){\n");
        cloneStr.append("    Object object=null;\n");
        cloneStr
                .append("    try{object = easyJ.common.BeanUtil.cloneObject(this);}catch (easyJ.common.EasyJException ee){return null;}\n");
        cloneStr.append("    return object;\n  }\n");
        return cloneStr;
    }

    public StringBuffer generateToString(ArrayList properties) {
        StringBuffer toStringStr = new StringBuffer();
        toStringStr.append("  public String toString(){\n");
        toStringStr.append("    StringBuffer buffer=new StringBuffer();\n");
        toStringStr.append("    buffer.append(\"[\");\n");
        for (int i = 0; i < properties.size(); i++) {
            Column column = (Column) properties.get(i);
            String propertyName = ColumnPropertyMapping.getPropertyName(column
                    .getColumnName());

            if (i != properties.size() - 1)
                toStringStr.append("    buffer.append(\"" + propertyName
                        + "=\"+" + propertyName + "+\",\");\n");
            if (i == properties.size() - 1)
                toStringStr.append("    buffer.append(\"" + propertyName
                        + "=\"+" + propertyName + ");\n");
        }
        toStringStr.append("    buffer.append(\"]\");\n");
        toStringStr.append("    return buffer.toString();\n");
        toStringStr.append("  }");
        return toStringStr;
    }

    public StringBuffer generateEquals(BeanInfo bi) {
        StringBuffer equalsStr = new StringBuffer();
        String primaryKey = ColumnPropertyMapping.getPropertyName(bi
                .getPrimaryKey());
        String className = StringUtil
                .upperCaseFirstLetter(ColumnPropertyMapping.getPropertyName(bi
                        .getTableName()));
        equalsStr.append("  public boolean equals(Object o){\n");
        equalsStr.append("    if(!(o instanceof " + className + "))\n");
        equalsStr.append("        return false;\n");
        equalsStr.append("    " + className + " bean=(" + className + ")o;\n");
        equalsStr.append("    if(" + primaryKey + ".equals(bean.get"
                + StringUtil.upperCaseFirstLetter(primaryKey) + "()))\n");
        equalsStr.append("        return true;\n");
        equalsStr.append("    else\n            return false;\n");
        equalsStr.append("  }\n");
        return equalsStr;
    }

    public StringBuffer generateHashCode(BeanInfo bi) {
        StringBuffer hashCodeStr = new StringBuffer();
        String primaryKey = ColumnPropertyMapping.getPropertyName(bi
                .getPrimaryKey());
        hashCodeStr.append("  public int hashCode()\n");
        hashCodeStr.append("  {\n");
        hashCodeStr.append("    return " + primaryKey + ".hashCode();\n");
        hashCodeStr.append("  }\n");
        return hashCodeStr;
    }

    public void generateXML(String tableName, String viewName)
            throws easyJ.common.EasyJException {

    }

}
