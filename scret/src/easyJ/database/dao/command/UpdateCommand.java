package easyJ.database.dao.command;

import java.util.ArrayList;

public interface UpdateCommand extends FilterableCommand {
    /**
     * 为更新命令增加一个item。也就是在更新的sql语句中增加一个更新字段以及对应的值。
     * 
     * @param updateItem
     *                UpdateItem
     */
    public void addUpdateItem(UpdateItem updateItem);

    /**
     * 一般不会用到，内部使用，用来保存PreparedState中“？”对应的值。
     * 
     * @return ArrayList
     */
    public ArrayList getParameters();
}
