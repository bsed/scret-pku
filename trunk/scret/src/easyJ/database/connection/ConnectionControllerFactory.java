package easyJ.database.connection;

import java.sql.Connection;

public class ConnectionControllerFactory {
    public ConnectionControllerFactory() {}

    public static ConnectionController getConnectionController() {
        return ConnectionControllerHardImpl.getInstance();
    }

    public static Connection getConnection() throws easyJ.common.EasyJException {
        ConnectionController cc = getConnectionController();
        return cc.getConnection();
    }
}
