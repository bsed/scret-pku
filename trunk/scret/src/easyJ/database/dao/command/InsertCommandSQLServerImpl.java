package easyJ.database.dao.command;

import easyJ.common.validate.GenericValidator;
import easyJ.common.EasyJException;
import java.util.ArrayList;
import easyJ.common.BeanUtil;
import easyJ.common.Const;
import java.lang.reflect.Field;
import easyJ.database.ColumnPropertyMapping;
import java.sql.Connection;
import easyJ.database.dao.ExecuteSQL;

public class InsertCommandSQLServerImpl implements InsertCommand {
    public InsertCommandSQLServerImpl() {}

    protected String tableName;

    protected ArrayList paramNameList = new ArrayList();

    protected ArrayList paramValueList = new ArrayList();

    protected ArrayList paramTypeList = new ArrayList();

    public InsertCommandSQLServerImpl(String tableName) throws EasyJException {
        if (GenericValidator.isBlankOrNull(tableName)
                || tableName.indexOf(",") >= 0) {
            throw new EasyJException();
        }
        this.tableName = tableName;
    }

    public InsertCommandSQLServerImpl(Object o) throws EasyJException {
        String tableName = (String) BeanUtil.getPubFieldValue(o,
                Const.TABLE_NAME);
        this.tableName = tableName;
        Class oclass = o.getClass();
        Field fields[] = oclass.getDeclaredFields();

        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            String fieldName = field.getName();
            if (!fieldName.equals(BeanUtil.getPrimaryKeyName(oclass))
                    && BeanUtil.isUpdatable(o.getClass(), fieldName)
                    && !java.lang.reflect.Modifier.isStatic(field
                            .getModifiers())
                    && BeanUtil.getSubClass(oclass, fieldName) == null) {
                paramNameList.add(ColumnPropertyMapping
                        .getColumnName(fieldName));
                paramValueList.add(BeanUtil.getFieldValue(o, fieldName));
                paramTypeList.add(field.getType());
            }
        }
    }

    public void addInsertItem(String property, Object value)
            throws EasyJException {
        if (GenericValidator.isBlankOrNull(property)) {
            throw new EasyJException();
        }
        paramNameList.add(property);
        paramValueList.add(value);
        paramTypeList.add(value.getClass());
    }

    public String getSQL() {
        StringBuffer name = new StringBuffer(), value = new StringBuffer();
        for (int i = 0; i < paramNameList.size(); i++) {
            if (i == 0) {
                name.append(paramNameList.get(i));
                value.append("?");
            } else {
                name.append(",");
                name.append(paramNameList.get(i));
                value.append(",?");
            }
        }
        String insertSQL = "insert into " + tableName + " (" + name
                + ") values (" + value + ") select @@identity ";
        return insertSQL;
    }

    public String getExecutableSQL() {
        StringBuffer name = new StringBuffer(), value = new StringBuffer();
        for (int i = 0; i < paramNameList.size(); i++) {
            Object param = paramValueList.get(i);
            if (i == 0) {
                name.append(paramNameList.get(i));
                if (param instanceof String || param instanceof java.sql.Date)
                    value.append("'" + param + "'");
                else
                    value.append(param);
            } else {
                name.append(",");
                name.append(paramNameList.get(i));
                if (param instanceof String || param instanceof java.sql.Date)
                    value.append("'" + param + "'");
                else
                    value.append(param);
            }
        }
        String insertSQL = "insert into " + tableName + " (" + name
                + ") values (" + value + ") select @@identity ";
        return insertSQL;

    }

    public Object execute(Connection conn) throws EasyJException {
        return ExecuteSQL.executeInsert(getSQL(), paramValueList,
                paramTypeList, conn);
    }

}
