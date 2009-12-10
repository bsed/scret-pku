package easyJ.database.dao;

public class SQLOperator implements java.io.Serializable {
    public static final SQLOperator IS_NULL = new SQLOperator("IS NULL", 0);

    public static final SQLOperator IS_NOT_NULL = new SQLOperator(
            "IS NOT NULL", 0);

    public static final SQLOperator EQUAL = new SQLOperator("=", 1);

    public static final SQLOperator NOT_EQUAL = new SQLOperator("<>", 1);

    public static final SQLOperator LESS_EQUAL = new SQLOperator("<=", 1);

    public static final SQLOperator LARGER_EQUAL = new SQLOperator(">=", 1);

    public static final SQLOperator LESS = new SQLOperator("<", 1);

    public static final SQLOperator LARGER = new SQLOperator(">", 1);

    public static final SQLOperator IN = new SQLOperator("IN", 1);

    public static final SQLOperator LIKE = new SQLOperator("LIKE", 1);

    public static final SQLOperator NOT_LIKE = new SQLOperator("NOT LIKE", 1);

    public static final SQLOperator BETWEEN = new SQLOperator("BETWEEN", 1);

    private String operator;

    private int dimension;

    private SQLOperator() {}

    private SQLOperator(String operator, int dimension) {
        this.operator = operator;
        this.dimension = dimension;
    }

    public String getOperator() {
        return operator;
    }

    public int getDimension() {
        return dimension;
    }

    public String toString() {
        return operator;
    }
}
