package easyJ.system.service;

import java.util.ArrayList;

public interface TreeService {
    /**
     * 根据类名来获得此类对应的数据库表中内容的树。要求此树只能有一个根，根的父ID为NULL
     * 
     * @param className
     *                String 类的名字
     * @throws EasyJException
     * @return Tree 得到的树。
     */
    public Tree getTree(String className) throws easyJ.common.EasyJException;

    /**
     * 根据得到的树tree来生成客户端显示的树。
     * 
     * @param tree
     *                Tree 得到的树在内存中的表示Tree
     * @param buffer
     *                StringBuffer 用来输出到客户端的树的字符串内容
     * @param selectMode
     *                int 选择模式。包括只能选择叶节点，还是所有节点都可以选择，具体参见Const类。
     * @param functionUrl
     *                String 当点击每个节点的时候所要执行的javascript方法，或者是url
     * @throws EasyJException
     */
    public void generateWholeTree(Tree tree, StringBuffer buffer,
            int selectMode, String functionUrl)
            throws easyJ.common.EasyJException;

    /**
     * 生成一个单一的节点的HTML代码
     * 
     * @param node
     *                Object 生成节点的对象
     * @param tree
     *                Tree 节点所属的树
     * @param level
     *                int 节点所处的层次
     * @param iniLevel
     *                int 代表从第几层开始输入
     * @param buffer
     *                StringBuffer HTML代码存放的buffer
     * @param selectMode
     *                int 选择模式。包括只能选择叶节点，还是所有节点都可以选择，具体参见Const类。
     * @param functionUrl
     *                String 当点击每个节点的时候所要执行的javascript方法，或者是url
     * @throws EasyJException
     */
    public void generateSingleNode(Object node, Tree tree, int level,
            int iniLevel, StringBuffer buffer, int selectMode,
            String functionUrl) throws easyJ.common.EasyJException;

    /**
     * 输出以root为根的子树的HTML代码
     * 
     * @param root
     *                Object 子树的根
     * @param tree
     *                Tree 整棵树
     * @param level
     *                int root所处的层次
     * @param iniLevel
     *                int 代表从第几层开始输入
     * @param buffer
     *                StringBuffer HTML代码存放的buffer
     * @param selectMode
     *                int 选择模式。包括只能选择叶节点，还是所有节点都可以选择，具体参见Const类。
     * @param functionUrl
     *                String 当点击每个节点的时候所要执行的javascript方法，或者是url
     * @throws EasyJException
     */
    public void generateWholeSubTree(Object root, Tree tree, int level,
            int iniLevel, StringBuffer buffer, int selectMode,
            String functionUrl) throws easyJ.common.EasyJException;

    /**
     * 设置显示树的时候，有基层是默认展开的。如果是0，则都不展开，就只能看到最顶层的数据；如果设置为1，则自动第一层如果有子节点就会自动展开
     * 
     * @param iniSpreadLevel
     *                int
     */
    public void setIniSpreadLevel(int iniSpreadLevel);

    /**
     * 根据
     * 
     * @param className
     *                String
     * @param list
     *                ArrayList
     * @throws EasyJException
     * @return Tree
     */
    public Tree getTree(String className, ArrayList list)
            throws easyJ.common.EasyJException;
}
