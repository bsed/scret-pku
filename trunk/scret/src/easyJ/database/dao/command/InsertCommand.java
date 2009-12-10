package easyJ.database.dao.command;

import easyJ.common.EasyJException;

public interface InsertCommand extends Command {
    /**
     * 增加一个插入语句的字段以及此字段对应的值
     * 
     * @param columnName
     *                String 字段名
     * @param value
     *                Object 字段对应的值
     * @throws EasyJException
     */
    public void addInsertItem(String columnName, Object value)
            throws EasyJException;
}
