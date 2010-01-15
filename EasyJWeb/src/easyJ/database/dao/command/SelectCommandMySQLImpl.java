package easyJ.database.dao.command;

import easyJ.common.EasyJException;
import easyJ.database.dao.OrderRule;

public class SelectCommandMySQLImpl extends SelectCommandSQLServerImpl {
    public SelectCommandMySQLImpl(Class c) throws EasyJException {
        super(c);
    }

    public String getSQL(String condition) {
        StringBuffer orderRule = new StringBuffer();
        int ruleSize = orderRules.size();
        if (ruleSize > 0) {
            orderRule.append(" order by ");
            for (int i = 0; i < ruleSize - 1; i++) {
                OrderRule rule = (OrderRule) orderRules.get(i);
                orderRule.append(rule.getOrderColumn());
                orderRule.append(" ");
                orderRule.append(rule.getOrderDirection().toString());
                orderRule.append(",");
            }
            OrderRule rule = (OrderRule) orderRules.get(ruleSize - 1);
            orderRule.append(rule.getOrderColumn());
            orderRule.append(" ");
            orderRule.append(rule.getOrderDirection().toString());
        }

        StringBuffer group = new StringBuffer();
        if (groupItems.size() > 0)
            group.append(" group by ");
        for (int i = 0; i < groupItems.size(); i++)
            group.append((String) groupItems.get(i));

        StringBuffer select = new StringBuffer();
        if (selectItems.size() > 0) {
            for (int i = 0; i < selectItems.size() - 1; i++) {
                select.append((String) selectItems.get(i));
                select.append(", ");
            }
            select.append((String) selectItems.get(selectItems.size() - 1));
        } else {
            select.append(" * ");
        }

        StringBuffer sql = new StringBuffer();
        sql.append("select ");
        sql.append(select);
        sql.append(" from ");
        // sql.append(select);
        sql.append(viewName);
        if (!"".equals(condition)) {
            sql.append(" where ");
            sql.append(condition);
        }
        if (group.length() != 0)
            sql.append(group);
        if (orderRule.length() != 0)
            sql.append(orderRule);
        if (size != -1) {
            sql.append(" LIMIT ");
            sql.append(start);
            sql.append(",");
            sql.append(size);
        }
        return sql.toString();
    }

}
