package easyJ.database.dao;

public class AggregateType implements java.io.Serializable {
    public static final AggregateType MAX = new AggregateType("MAX");

    public static final AggregateType MIN = new AggregateType("MIN");

    public static final AggregateType AVG = new AggregateType("AVG");

    public static final AggregateType SUM = new AggregateType("SUM");

    public static final AggregateType COUNT = new AggregateType("COUNT");

    public static final AggregateType DISTINCT = new AggregateType("DISTINCT");

    private String aggregatesType;

    protected AggregateType() {}

    protected AggregateType(String s) {
        this.aggregatesType = s;
    }

    public String getType() {
        return aggregatesType;
    }

    public String toString() {
        return aggregatesType;
    }
}
