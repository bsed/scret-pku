package easyJ.database.dao;

import easyJ.database.ColumnPropertyMapping;

public class OrderRule {
    private String orderColumn;

    private OrderDirection orderDirection;

    public OrderRule() {}

    public OrderRule(String orderProperty, OrderDirection orderDirection) {
        setOrderColumn(orderProperty);
        setOrderDirection(orderDirection);
    }

    public String getOrderColumn() {
        return orderColumn;
    }

    public void setOrderColumn(String orderProperty) {
        String column = ColumnPropertyMapping.getColumnName(orderProperty);
        // 如果是* 或者别的，那么就不会有对应的数据
        if (column == null)
            column = orderProperty;
        this.orderColumn = column;
    }

    public OrderDirection getOrderDirection() {
        return orderDirection;
    }

    public void setOrderDirection(OrderDirection orderDirection) {
        this.orderDirection = orderDirection;
    }
}
