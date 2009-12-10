package easyJ.database.dao.command;

import java.sql.Connection;

import easyJ.common.BeanUtil;
import easyJ.common.Const;
import easyJ.common.EasyJException;
import easyJ.common.validate.GenericValidator;
import easyJ.database.dao.ExecuteSQL;
import easyJ.database.dao.Filter;

public class DeleteCommandMySQLImpl implements DeleteCommand {
    protected String tableName;

    protected Filter filter;

    protected String condition;

    public DeleteCommandMySQLImpl(String tableName) throws EasyJException {
        if (GenericValidator.isBlankOrNull(tableName)
                || tableName.indexOf(",") >= 0) {
            throw new EasyJException();
        }
        this.tableName = tableName;
    }

    public DeleteCommandMySQLImpl(Object o) throws EasyJException {
        String tableName = (String) BeanUtil.getPubFieldValue(o,
                Const.TABLE_NAME);
        this.tableName = tableName;
    }

    public Object execute(Connection conn) throws EasyJException {
        ExecuteSQL.executeUpdate(getSQL(), filter.getParameters(), conn);
        return null;
    }

    public String getSQL() throws EasyJException{
        if (filter != null)
            return getSQL(filter.getSQL());
        else if (condition != null)
            return getSQL(condition);
        else
            return getSQL("");
    }

    public String getExecutableSQL() {
        if (filter != null)
            return getSQL(filter.getExecutableSQL());
        else
            return getSQL("");

    }

    public String getSQL(String condition) {
        return "delete from " + tableName + " where " + condition;
    }

    public Filter getFilter() {
        return filter;
    }

    public void setFilter(Filter filter) {
        this.filter = filter;
    }

}
