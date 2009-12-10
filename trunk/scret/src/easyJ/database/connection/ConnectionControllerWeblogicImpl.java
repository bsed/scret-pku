package easyJ.database.connection;

import easyJ.common.*;
import java.sql.Connection;
import javax.naming.*;
import java.util.Properties;
import javax.sql.DataSource;
import java.sql.SQLException;

public class ConnectionControllerWeblogicImpl implements ConnectionController,
        java.io.Serializable {
    private ConnectionControllerWeblogicImpl() {}

    private static ConnectionControllerWeblogicImpl instance;

    private static final String dsName = "DS";// ErpDB

    private static Context ic = null;

    private static DataSource ds = null;

    public static Context getContext() throws EasyJException {
        initialContext();
        return ic;
    }

    public static ConnectionController getInstance() {
        if (instance == null)
            instance = new ConnectionControllerWeblogicImpl();
        return instance;
    }

    public Connection getConnection() throws EasyJException {
        lookupDataSource();
        Connection con = null;
        try {
            con = ds.getConnection();
        } catch (SQLException se) {
            se.printStackTrace();
            String clientMessage = "服务器忙";
            String location = "easyJ.database.connection.ConnectionControllerWeblogicImpl.getConnection()";
            String logMessage = "can't get connection because of SQLException: "
                    + se.getMessage();
            EasyJException ee = new EasyJException(se, location, logMessage,
                    clientMessage);
            throw ee;
        } catch (Exception e) {
            if (e instanceof EasyJException) {
                throw (EasyJException) e;
            }
            e.printStackTrace();
            String clientMessage = "服务器忙";
            String location = "easyJ.database.connection.ConnectionControllerWeblogicImpl.getConnection()";
            String logMessage = "can't get connection because of exception:"
                    + e.getMessage();
            EasyJException ee = new EasyJException(e, location, logMessage,
                    clientMessage);
            throw ee;
        }

        return con;
    }

    // InitialContext for Weblogic Server
    private static void initialContext() throws EasyJException {
        if (ic == null) {
            String url = "t3://localhost:80";//
            String user = null;
            String password = null;
            Properties properties = null;
            try {
                properties = new Properties();
                properties.put(Context.INITIAL_CONTEXT_FACTORY,
                        "weblogic.jndi.WLInitialContextFactory");
                properties.put(Context.PROVIDER_URL, url);
                if (user != null) {
                    properties.put(Context.SECURITY_PRINCIPAL, user);
                    properties.put(Context.SECURITY_CREDENTIALS,
                            password == null ? "" : password);
                }
                ic = new InitialContext(properties);
            } catch (Exception e) {
                if (e instanceof EasyJException) {
                    throw (EasyJException) e;
                }
                e.printStackTrace();
                String clientMessage = "服务器忙";
                String location = "easyJ.database.connection.ConnectionControllerWeblogicImpl.initialContext()";
                String logMessage = "can't initial context because of exception:"
                        + e.getMessage()
                        + ". Please make sure the IP and Port is correct"
                        + " and make sure the sever is running.";
                EasyJException ee = new EasyJException(e, location, logMessage,
                        clientMessage);
                throw ee;
            }
        }
    }

    // 取得JDBC数据源
    private static void lookupDataSource() throws EasyJException {
        if (ds == null) {
            initialContext();
            try {
                ds = (DataSource) ic.lookup(dsName);
            } catch (NamingException ne) {
                ne.printStackTrace();
                String clientMessage = "服务器忙";
                String location = "easyJ.database.connection.ConnectionControllerWeblogicImpl.lookupDataSource()";
                String logMessage = "can't find the datasource with JNDI name "
                        + dsName
                        + ". Please make sure the datasource is configured with the JNDI name.";
                EasyJException ee = new EasyJException(ne, location,
                        logMessage, clientMessage);
                throw ee;
            } catch (Exception e) {
                if (e instanceof EasyJException) {
                    throw (EasyJException) e;
                }
                e.printStackTrace();
                String clientMessage = "服务器忙";
                String location = "easyJ.database.connection.ConnectionControllerWeblogicImpl.lookupDataSource()";
                String logMessage = "Unexcepted exception occurs, can't initial context because of exception:"
                        + e.getMessage();
                EasyJException ee = new EasyJException(e, location, logMessage,
                        clientMessage);
                throw ee;
            }
        }
    }

}
