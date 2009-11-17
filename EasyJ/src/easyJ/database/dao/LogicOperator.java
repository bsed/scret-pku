package easyJ.database.dao;

public class LogicOperator implements java.io.Serializable {

    public static final LogicOperator AND = new LogicOperator("AND");

    public static final LogicOperator OR = new LogicOperator("OR");

    private String operator;

    private LogicOperator() {}

    private LogicOperator(String operator) {
        this.operator = operator;
    }

    public String getOperator() {
        return operator;
    }

    public String toString() {
        return operator;
    }
}
