package easyJ.database.dao.command;

import java.sql.Connection;
import easyJ.common.EasyJException;

public interface Command {
    /**
     * 得到改命令对应的PreparedStatement需要的带?的sql语句
     * 
     * @return String 返回sql语句
     */
    public String getSQL() throws EasyJException; // 带select

    /**
     * 得到可以直接执行的sql语句。
     * 
     * @return String sql语句
     */
    public String getExecutableSQL();

    /**
     * 执行命令
     * 
     * @param conn
     *                Connection 执行命令所需要用到的链接。
     * @throws EasyJException
     * @return Object
     */
    public Object execute(Connection conn) throws EasyJException;
}
