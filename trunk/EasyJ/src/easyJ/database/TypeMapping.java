package easyJ.database;

import java.sql.Types;

public abstract class TypeMapping {
    public abstract int getJavaSqlType(String sqlType);

    public abstract String getSqlType();

    public String getJavaTypeFromSqlType(String sqlType) {
        return getJavaType(getJavaSqlType(sqlType));
    }

    public static String getJavaType(int javaSqlType) {
        if (javaSqlType == Types.CHAR)
            return "String";
        if (javaSqlType == Types.VARCHAR)
            return "String";
        if (javaSqlType == Types.LONGVARCHAR)
            return "String";
        if (javaSqlType == Types.NUMERIC)
            return "java.math.BigDecimal";
        if (javaSqlType == Types.DECIMAL)
            return "java.math.BigDecimal";
        if (javaSqlType == Types.BIT)
            return "Boolean";
        if (javaSqlType == Types.BOOLEAN)
            return "Boolean";
        if (javaSqlType == Types.TINYINT)
            return "Byte";
        if (javaSqlType == Types.SMALLINT)
            return "Short";
        if (javaSqlType == Types.INTEGER)
            return "Integer";
        if (javaSqlType == Types.BIGINT)
            return "Long";
        if (javaSqlType == Types.REAL)
            return "Float";
        if (javaSqlType == Types.FLOAT)
            return "Double";
        if (javaSqlType == Types.DOUBLE)
            return "Double";
        if (javaSqlType == Types.BINARY)
            return "byte[]";
        if (javaSqlType == Types.VARBINARY)
            return "byte[]";
        if (javaSqlType == Types.LONGVARBINARY)
            return "byte[]";
        if (javaSqlType == Types.DATE)
            return "java.sql.Date";
        if (javaSqlType == Types.TIME)
            return "java.sql.Time";
        if (javaSqlType == Types.TIMESTAMP)
            return "java.sql.Date";
        if (javaSqlType == Types.CLOB)
            return "Clob";
        if (javaSqlType == Types.BLOB)
            return "Blob";
        if (javaSqlType == Types.ARRAY)
            return "Array";
        if (javaSqlType == Types.DATALINK)
            return "java.net.URL";
        return null;
    }

    public static int getJavaSQLType(Class javaType) {
        if (javaType.equals(String.class))
            return java.sql.Types.VARCHAR;
        if (javaType.equals(java.math.BigDecimal.class))
            return java.sql.Types.DECIMAL;
        if (javaType.equals(Boolean.class))
            return java.sql.Types.BOOLEAN;
        if (javaType.equals(Byte.class))
            return java.sql.Types.TINYINT;
        if (javaType.equals(Short.class))
            return java.sql.Types.SMALLINT;
        if (javaType.equals(Integer.class))
            return java.sql.Types.INTEGER;
        if (javaType.equals(Long.class))
            return java.sql.Types.BIGINT;
        if (javaType.equals(Float.class))
            return java.sql.Types.REAL;
        if (javaType.equals(Double.class))
            return java.sql.Types.DOUBLE;
        if (javaType.equals(byte[].class))
            return java.sql.Types.VARBINARY;
        if (javaType.equals(java.sql.Date.class))
            return java.sql.Types.DATE;
        if (javaType.equals(java.sql.Time.class))
            return java.sql.Types.TIME;
        if (javaType.equals(java.sql.Timestamp.class))
            return java.sql.Types.TIMESTAMP;
        if (javaType.equals(java.sql.Clob.class))
            return java.sql.Types.CLOB;
        if (javaType.equals(java.sql.Blob.class))
            return java.sql.Types.BLOB;
        if (javaType.equals(java.sql.Array.class))
            return java.sql.Types.ARRAY;
        if (javaType.equals(java.net.URL.class))
            return java.sql.Types.DATALINK;
        return -1;
    }

}
