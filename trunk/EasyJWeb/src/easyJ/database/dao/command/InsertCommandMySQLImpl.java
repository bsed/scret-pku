package easyJ.database.dao.command;

import easyJ.common.EasyJException;
import java.sql.Connection;
import easyJ.database.dao.ExecuteSQL;

public class InsertCommandMySQLImpl extends InsertCommandSQLServerImpl {
    public InsertCommandMySQLImpl() {}

    public InsertCommandMySQLImpl(Object o) throws EasyJException {
        super(o);
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
                + ") values (" + value + ")";
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
                + ") values (" + value + ")";
        return insertSQL;

    }

    public Object execute(Connection conn) throws EasyJException {
        return ExecuteSQL.executeMySQLInsert(getSQL(), paramValueList,
                paramTypeList, conn);
    }

}
