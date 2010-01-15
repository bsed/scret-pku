package easyJ.database.dao;

public class OrderDirection {
    public static final OrderDirection DESC = new OrderDirection("DESC");

    public static final OrderDirection ASC = new OrderDirection("ASC");

    private String type = "DESC";

    protected OrderDirection() {}

    protected OrderDirection(String s) {
        this.type = s;
    }

    public String getType() {
        return type;
    }

    public boolean equals(OrderDirection other) {
        if (this.type.equals(other.type))
            return true;
        return false;
    }

    public String toString() {
        return type;
    }

    public static OrderDirection parse(String value) {
        if ("ASC".equalsIgnoreCase(value))
            return ASC;
        else
            return DESC;
    }
}
