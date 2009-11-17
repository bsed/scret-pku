package easyJ.database;

import java.sql.Types;

public class TypeMappingSQLServerImpl extends TypeMapping {
    private static TypeMapping instance = null;

    private TypeMappingSQLServerImpl() {}

    public static TypeMapping getInstance() {
        if (instance == null)
            instance = new TypeMappingSQLServerImpl();
        return instance;
    }

    public int getJavaSqlType(String sqlType) {
        if (sqlType.equalsIgnoreCase("bigint"))
            return Types.BIGINT;
        if (sqlType.equalsIgnoreCase("binary"))
            return Types.BINARY;
        if (sqlType.equalsIgnoreCase("bit"))
            return Types.BIT;
        if (sqlType.equalsIgnoreCase("char"))
            return Types.CHAR;
        if (sqlType.equalsIgnoreCase("datetime"))
            return Types.DATE;
        if (sqlType.equalsIgnoreCase("decimal"))
            return Types.DECIMAL;
        if (sqlType.equalsIgnoreCase("float"))
            return Types.FLOAT;
        if (sqlType.equalsIgnoreCase("image"))
            return Types.LONGVARBINARY;
        if (sqlType.equalsIgnoreCase("int"))
            return Types.INTEGER;
        if (sqlType.equalsIgnoreCase("money"))
            return Types.DECIMAL;
        if (sqlType.equalsIgnoreCase("nchar"))
            return Types.CHAR;
        if (sqlType.equalsIgnoreCase("ntext"))
            return Types.LONGVARCHAR;
        if (sqlType.equalsIgnoreCase("numeric"))
            return Types.NUMERIC;
        if (sqlType.equalsIgnoreCase("nvarchar"))
            return Types.VARCHAR;
        if (sqlType.equalsIgnoreCase("real"))
            return Types.REAL;
        if (sqlType.equalsIgnoreCase("smalldatetime"))
            return Types.DATE;
        if (sqlType.equalsIgnoreCase("smallint"))
            return Types.SMALLINT;
        if (sqlType.equalsIgnoreCase("smallmoney"))
            return Types.DECIMAL;
        if (sqlType.equalsIgnoreCase("sql_variant"))
            return Types.VARCHAR;
        if (sqlType.equalsIgnoreCase("sysname"))
            return Types.VARCHAR;
        if (sqlType.equalsIgnoreCase("text"))
            return Types.LONGVARCHAR;
        if (sqlType.equalsIgnoreCase("timestamp"))
            return Types.BINARY;
        if (sqlType.equalsIgnoreCase("tinyint"))
            return Types.TINYINT;
        if (sqlType.equalsIgnoreCase("uniqueidentifier"))
            return Types.CHAR;
        if (sqlType.equalsIgnoreCase("varbinary"))
            return Types.VARBINARY;
        if (sqlType.equalsIgnoreCase("varchar"))
            return Types.VARCHAR;
        return -1;
    }

    public String getSqlType() {
        return null;
    }
}
