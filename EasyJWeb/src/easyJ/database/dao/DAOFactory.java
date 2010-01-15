package easyJ.database.dao;

import easyJ.common.EasyJConfiguration;
import easyJ.database.DatabaseType;
import easyJ.database.dao.command.DeleteCommand;
import easyJ.database.dao.command.DeleteCommandMySQLImpl;
import easyJ.database.dao.command.InsertCommand;
import easyJ.database.dao.command.InsertCommandMySQLImpl;
import easyJ.database.dao.command.InsertCommandSQLServerImpl;
import easyJ.database.dao.command.SelectCommand;
import easyJ.database.dao.command.SelectCommandMySQLImpl;
import easyJ.database.dao.command.SelectCommandSQLServerImpl;
import easyJ.database.dao.command.UpdateCommand;
import easyJ.database.dao.command.UpdateCommandSQLServerImpl;
/**
 * 此方法根据不同的数据库选择不同的对象返回
 * @author liufeng
 *
 */
public class DAOFactory {
    public DAOFactory() {}
    private static String databaseType = DatabaseType.MY_SQL;

    static {
    	databaseType = EasyJConfiguration.getConfig().getString("database_type");
    }
    
    public static DAO getDAO() {
        return DAOSQLServerImpl.getInstance();
    }

    public static SelectCommand getSelectCommand(Class c)
            throws easyJ.common.EasyJException {
    	if (DatabaseType.SQL_SERVER.equals(databaseType))
    		return new SelectCommandSQLServerImpl(c);
    	return new SelectCommandMySQLImpl(c);
    }

    public static InsertCommand getInsertCommand(Class c)
            throws easyJ.common.EasyJException {
        return new InsertCommandSQLServerImpl(c);
    }

    public static InsertCommand getInsertCommand(Object o)
            throws easyJ.common.EasyJException {
    	if (DatabaseType.SQL_SERVER.equals(databaseType))
    		return new InsertCommandSQLServerImpl(o);
        return new InsertCommandMySQLImpl(o);
    }

    public static UpdateCommand getUpdateCommand(Class c)
            throws easyJ.common.EasyJException {
        return new UpdateCommandSQLServerImpl(c);
    }

    public static UpdateCommand getUpdateCommand(Object o)
            throws easyJ.common.EasyJException {
        return new UpdateCommandSQLServerImpl(o);
    }

    public static DeleteCommand getDeleteCommand(Object o)
            throws easyJ.common.EasyJException {
        return new DeleteCommandMySQLImpl(o);
    }

    public static Filter getFilter() {
        return new FilterSQLServerImpl();
    }

    public static Filter getFilter(String property, SQLOperator operator,
            Object value) {
        return new FilterSQLServerImpl(property, operator, value);
    }

    public static Filter getFilter(String property, SQLOperator operator,
            Object value, Object value1) {
        return new FilterSQLServerImpl(property, operator, value, value1);
    }

}
