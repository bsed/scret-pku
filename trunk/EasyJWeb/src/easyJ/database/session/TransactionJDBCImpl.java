package easyJ.database.session;

import java.sql.*;

import easyJ.common.EasyJException;
public class TransactionJDBCImpl extends Transaction {
    private Connection conn;
    // 提交次数
    private int commitCount;

    // 事务嵌套层次
    private int transDeep;
    
    public TransactionJDBCImpl() {}
    
    public TransactionJDBCImpl(Connection conn) throws EasyJException{
        try {
            this.conn = conn;
            conn.setAutoCommit(false);
            commitCount = 0;
            transDeep = 0;
        } catch (SQLException ex) {
            String clientMessage = "服务器忙";
            String location = "easyJ.database.transaction.TransactionJDBCImpl.begin()";
            String logMessage = "数据库事务启动失败！信息：" + ex.getMessage();
            EasyJException ee = new EasyJException(ex, location, logMessage,
                    clientMessage);
            throw ee;
        }
    }

    protected void begin() {
        transDeep++;
    }
    
    public Connection getConnection() {
        return conn;
    }
    
    public void commit() throws EasyJException {
        try {
            if (transDeep > 1) {
                transDeep--;
                commitCount++;
                return;
            } else if (transDeep == 1) {
                conn.commit();
                conn.setAutoCommit(true);
                conn.close();
                conn = null;
            } else {
                String clientMessage = "服务器忙";
                String location = "easyJ.database.transaction.TransactionJDBCImpl.commit()";
                String logMessage = "数据库事务提交失败！信息：" + "不能没有启动事务就提交";
                throw new EasyJException(null, location, logMessage,
                        clientMessage);
            }
        } catch (SQLException ex) {
            String clientMessage = "服务器忙";
            String location = "easyJ.database.transaction.TransactionJDBCImpl.commit()";
            String logMessage = "数据库事务提交失败！信息：" + ex.getMessage();
            EasyJException ee = new EasyJException(ex, location, logMessage,
                    clientMessage);
            throw ee;

        }
    }

    /**
     * 结束一个事务，如果事务本身不是第一层，则只是简单的把当前的层次减1，
     * 只有当到达第一层之后才真正的执行rollback。
     */
    public void rollback() throws EasyJException {
      
        if (transDeep > 1) {
            transDeep--;
            return;
        }

        // 如果不是所有的事务都正常提交了，则需要回滚
        try {
            if (transDeep == 1) {
                conn.rollback();
            }
        } catch (SQLException ex) {
            String clientMessage = "服务器忙";
            String location = "easyJ.database.transaction.TransactionJDBCImpl.end()";
            String logMessage = "数据库事务回滚失败！信息：" + ex.getMessage();
            EasyJException ee = new EasyJException(ex, location, logMessage,
                    clientMessage);
            throw ee;
        } finally {
            try {
                conn.close();
                conn = null;
            } catch (SQLException se) {}
        }

    }
}
