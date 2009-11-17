package easyJ.database.dao.command;

import java.util.ArrayList;
import easyJ.common.EasyJException;
import easyJ.common.BeanUtil;
import easyJ.common.Const;
import easyJ.database.ColumnPropertyMapping;
import java.lang.reflect.Field;
import java.sql.Connection;
import easyJ.database.dao.FilterSQLServerImpl;
import easyJ.database.dao.Filter;
import easyJ.database.dao.ExecuteSQL;
import easyJ.database.dao.SQLOperator;
import easyJ.database.dao.DAOFactory;

public class UpdateCommandSQLServerImpl implements UpdateCommand {
    public UpdateCommandSQLServerImpl() {}

    private String tableName;

    private Class updateClass;

    private ArrayList updateItems;

    private Filter filter;

//    ArrayList paramNameList = new ArrayList();

    ArrayList paramValueList = new ArrayList();

    ArrayList paramTypeList = new ArrayList();

    public UpdateCommandSQLServerImpl(Class c) throws EasyJException {
        this.tableName = (String) BeanUtil.getPubStaticFieldValue(c,
                Const.TABLE_NAME);
        this.updateClass = c;
        updateItems = new ArrayList();
    }

    public UpdateCommandSQLServerImpl(Object o) throws EasyJException {
        this.tableName = BeanUtil.getTableName(o);
        this.updateClass = o.getClass();
        updateItems = new ArrayList();
        if (o == null
                || BeanUtil.getFieldValue(o, BeanUtil
                        .getPrimaryKeyName(updateClass)) == null) {
            String clientMessage = "服务器忙";
            String location = "SQLServerDAOImpl.executeUpdate(Object o)";
            String logMessage = "参数错误，更新的数据不能为空，主键值也不能为空";
            EasyJException ee = new EasyJException(null, location, logMessage,
                    clientMessage);
            throw ee;
        }
        Field fields[] = updateClass.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            String fieldName = field.getName();
            if (!fieldName.equals(BeanUtil.getPrimaryKeyName(updateClass))
                    && BeanUtil.isUpdatable(o.getClass(), fieldName)
                    && !java.lang.reflect.Modifier.isStatic(field
                            .getModifiers())
                    && BeanUtil.getSubClass(updateClass, fieldName) == null) {
//                paramNameList.add(ColumnPropertyMapping
//                        .getColumnName(fieldName));
                paramValueList.add(BeanUtil.getFieldValue(o, fieldName));
                paramTypeList.add(field.getType());
                UpdateItem ui = new UpdateItem();
                ui.setProperty(fieldName);
                ui.setValue(BeanUtil.getFieldValue(o, fieldName));
                updateItems.add(ui);
            }
        }
        String primaryKeyName = BeanUtil.getPrimaryKeyName(updateClass);
        Object promaryKeyValue = BeanUtil.getPrimaryKeyValue(o);
        filter = DAOFactory.getFilter(primaryKeyName,
                SQLOperator.EQUAL, promaryKeyValue);
    }

    // 得到执行update语句时preparedStatement所用到的参数。包括update以及where子句中的参数
    public ArrayList getParameters() {
        ArrayList parameters = new ArrayList();
        for (int i = 0; i < updateItems.size(); i++)
            parameters.add(((UpdateItem) updateItems.get(i)).getValue());
        parameters.addAll(filter.getParameters());
        return parameters;
    }

    public Filter getFilter() {
        return filter;
    }

    public void setFilter(Filter filter) {
        this.filter = (FilterSQLServerImpl) filter;
    }

    public void addUpdateItem(UpdateItem updateItem) {
        this.paramValueList.add(updateItem.getValue());
        this.updateItems.add(updateItem);
    }

    public String getSQL()throws EasyJException // 带select
    {
        StringBuffer sql = new StringBuffer();
        sql.append("update ");
        sql.append(tableName);
        sql.append(" set ");
        for (int i = 0; i < updateItems.size() - 1; i++) {
            UpdateItem ui = (UpdateItem) updateItems.get(i);
            String column = ColumnPropertyMapping.getColumnName(ui
                    .getProperty());
            sql.append(column);
            sql.append("=?,");
        }
        UpdateItem ui = (UpdateItem) updateItems.get(updateItems.size() - 1);
        String column = ColumnPropertyMapping.getColumnName(ui.getProperty());
        sql.append(column);
        sql.append("=? ");
        if (filter != null) {
            sql.append(" where ");
            sql.append(filter.getSQL());
        }
        return sql.toString();
    }

    public String getExecutableSQL() {
        StringBuffer sql = new StringBuffer();
        sql.append("update ");
        sql.append(tableName);
        sql.append(" set ");
        for (int i = 0; i < updateItems.size() - 1; i++) {
            UpdateItem ui = (UpdateItem) updateItems.get(i);
            String column = ColumnPropertyMapping.getColumnName(ui
                    .getProperty());
            sql.append(column);
            if (ui.getValue() instanceof String
                    || ui.getValue() instanceof java.sql.Date) {
                sql.append("='" + ui.getValue() + "'");
            } else {
                sql.append("=" + ui.getValue() + ",");
            }
        }
        UpdateItem ui = (UpdateItem) updateItems.get(updateItems.size() - 1);
        String column = ColumnPropertyMapping.getColumnName(ui.getProperty());
        sql.append(column);
        if (ui.getValue() instanceof String
                || ui.getValue() instanceof java.sql.Date) {
            sql.append("='" + ui.getValue() + "'");
        } else {
            sql.append("=" + ui.getValue() + ",");
        }

        if (filter != null) {
            sql.append(" where ");
            sql.append(filter.getExecutableSQL());
        }
        return sql.toString();
    }

    public Object execute(Connection conn) throws EasyJException {
        // get the updateSQL and get the property values.
        String updateSQL = getSQL();
        // EasyJLog.debug(updateSQL);
        paramValueList.addAll(filter.getParameters());
        //因为update的filter里面不可能有为空的param，所以用不到add fileter的paramTypeList。
        // execute the updateSQL
        ExecuteSQL
                .executeUpdate(updateSQL, paramValueList, paramTypeList, conn);
        return null;
    }
}
