package easyJ.database.connection;

import easyJ.common.*;
import java.sql.Connection;

public interface ConnectionController {
    public Connection getConnection() throws EasyJException;
}
