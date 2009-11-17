package easyJ.database;

import easyJ.logging.EasyJLog;

public class ColumnPropertyMapping {
    public ColumnPropertyMapping() {}

    public static final char upperLower = 'a' - 'A';

    public static String getColumnName(String propertyName) {
        // EasyJLog.debug("propertyName is:"+propertyName);
        char[] property = propertyName.toCharArray();
        char[] column = new char[property.length + 10];
        int j = 0;
        for (int i = 0; i < property.length; i++) {
            if (property[i] < 'a') {
                column[j] = '_';
                column[++j] = (char) (property[i] + upperLower);
            } else {
                column[j] = property[i];
            }
            j++;
        }
        String columnName = new String(column, 0, j);
        return columnName;
    }

    public static String getPropertyName(String columnName) {
        char[] col = columnName.toCharArray();
        char[] property = new char[col.length];
        int j = 0;
        for (int i = 0; i < col.length; i++) {
            if (col[i] <= 'Z' && col[i] != '_')
                property[j] = (char) (col[i] + upperLower);
            else
                property[j] = col[i];
            if (col[i] == '_') {
                i++;
                if (col[i] >= 'a')
                    property[j] = (char) (col[i] - upperLower);
                else
                    property[j] = col[i];
            }
            j++;
        }
        String propertyName = new String(property, 0, j);
        return propertyName;
    }

}
