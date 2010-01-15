package easyJ.database.session;

import java.sql.Connection;

import easyJ.common.EasyJException;
import easyJ.database.connection.ConnectionControllerFactory;

public class TransactionFactory {
    public TransactionFactory() {}

    private final static ThreadLocal<Transaction> local = new ThreadLocal<Transaction>();

    /**
     * 得到一个当前线程正在使用的transaction。
     * todo:将来支持新开transaction。
     * @return
     * @throws EasyJException
     */
    public static Transaction getTransaction() throws EasyJException {
        Transaction transaction = local.get();
        if (transaction == null) {
            // 设置本地线程的connection
            Connection conn = ConnectionControllerFactory.getConnection();
            transaction = new TransactionJDBCImpl(conn);
            local.set(transaction);
        }
        //说明当前的transaction已经完成了，应该创建一个新的
        if (transaction.getConnection() == null) {
            Connection conn = ConnectionControllerFactory.getConnection();
            transaction = new TransactionJDBCImpl(conn);
            local.set(transaction);
        }
        transaction.begin();
        return transaction;
    }
}
