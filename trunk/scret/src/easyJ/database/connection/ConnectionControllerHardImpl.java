package easyJ.database.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.commons.configuration.Configuration;

import easyJ.common.EasyJConfiguration;
import easyJ.common.EasyJException;

public class ConnectionControllerHardImpl implements ConnectionController,
        java.io.Serializable {
	private boolean configured = false;
    private String driver;
    private String url;
    private String user;
    private String password;
    private ConnectionControllerHardImpl() {}

    private static ConnectionControllerHardImpl instance = null;

    public static ConnectionController getInstance() {
        if (instance == null)
            instance = new ConnectionControllerHardImpl();
        return instance;
    }

    public Connection getConnection() throws EasyJException {
    	
//      String driver = "com.microsoft.jdbc.sqlserver.SQLServerDriver";
//      String url = "jdbc:microsoft:sqlserver://192.168.4.133;DatabaseName=RequirementElicitation;SelectMethod=Cursor";
//      String user = "sa";
//      String password = "123";
  	if ( !configured ) {
  		configured = true;
	    	Configuration config = EasyJConfiguration.getConfig();
	        driver = config.getString("connection.driver");
	        url = config.getString("connection.url");
	        user = config.getString("connection.user");
	        password = config.getString("connection.password");
  	}
      try {
      	//如果不在web环境下使用，则无法读到配置文件，所以需要设一个默认值，也就是本地数据库
      	if (driver == null) {
      		driver = "com.mysql.jdbc.Driver";
      		url = "jdbc:mysql://192.168.4.133:3306/RequirementElicitation?characterEncoding=utf8";
      		user = "root";
      		password = ""; 
      	}
           Class.forName(driver);
           Connection conn=DriverManager.getConnection(url, user, password);
          return conn;
      } catch (ClassNotFoundException ex) {
          ex.printStackTrace();
          String clientMessage = "服务器忙";
          String location = "easyJ.database.connection.ConnectionControllerHardImpl.getConnection()";
          String logMessage = "can't get connection because of ClassNotFoundException: "
                  + ex.getMessage()
                  + ". Please put the JDBC driver to the suitable path. Or the name of the driver is wrong.";
          EasyJException ee = new EasyJException(ex, location, logMessage,
                  clientMessage);
          throw ee;
      } catch (SQLException ex) {
          ex.printStackTrace();
          String clientMessage = "服务器忙";
          String location = "easyJ.database.connection.ConnectionControllerHardImpl.getConnection()";
          String logMessage = "can't get connection because of SQLException: "
                  + ex.getMessage()
                  + ". Please check the url and user and password";
          EasyJException ee = new EasyJException(ex, location, logMessage,
                  clientMessage);
          throw ee;
      }
  }
}
