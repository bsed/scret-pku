package easyJ.common;

public class Const {
    public Const() {}

    public static final String INSERT_SQL = "INSERT_SQL";

    public static final String DELETE_SQL = "DELETE_SQL";

    public static final String UPDATE_SQL = "UPDATE_SQL";

    public static final String PRIMARY_KEY = "PRIMARY_KEY";

    public static final String TABLE_NAME = "TABLE_NAME";

    public static final String VIEW_NAME = "VIEW_NAME";

    public static final String UPDATE_TIME = "updateTime";

    /* 下面三个属性表示一个树状表中id，parentId以及name对应的属性名，这三个应该出现在具体的java bean中 */
    public static final String TREE_ID_PROPERTY = "TREE_ID_PROPERTY";

    public static final String TREE_PARENT_ID_PROPERTY = "TREE_PARENT_ID_PROPERTY";

    public static final String TREE_DISPLAY_PROPERTY = "TREE_DISPLAY_PROPERTY";

    public static final String TREE_FUNCTION_PROPERTY = "TREE_FUNCTION_PROPERTY";

    /* 下面两个属性表示主键id对应的属性，name对应的属性名，这三个应该出现在具体的java bean中，一般用在下拉列表当中 */
    public static final String ID_PROPERTY = "ID_PROPERTY";

    public static final String DISPLAY_PROPERTY = "DISPLAY_PROPERTY";

    /* 下面的属性表示记录在context当中的权限问题，包括owner,group,other */
    public static final String CONTEXT_ROLE_PROPERTY = "CONTEXT_ROLE_PROPERTY";

    /* 选择树的节点时仅将树节点展开 */
    public static final int TREE_SELECT_SPREAD = 1;

    /* 选择树的节点时将树节点展开，并且在右面的页面显示此节点所对应功能的界面 */
    public static final int TREE_SELECT_SPREAD_FUNCTION = 2;

    /* 选择树的节点时将选择的叶节点数据返回给调用页面 */
    public static final int TREE_SELECT_LEAF_RETURN = 3;

    /* 选择树的节点时将选择的节点数据返回给调用页面 */
    public static final int TREE_SELECT_NODE_RETURN = 4;

    public static final int MAX_INT_VALUE = Integer.MAX_VALUE;

    public static final long MAX_LONG_VALUE = Long.MAX_VALUE;

}
