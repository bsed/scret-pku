package easyJ.system.service;

import java.util.List;
import java.util.Hashtable;
import java.util.ArrayList;
import easyJ.common.BeanUtil;
import easyJ.common.EasyJException;
import easyJ.database.session.Session;
import easyJ.database.session.SessionFactory;

public class TreeWholeImpl implements Tree {
    private String className; // 这颗树是对哪个类的，也就是针对哪个数据库表的

    private Class clazz;

    private String idProperty; // 主键对应的属性名

    private String parentIdProperty; // 父亲对应的属性名

    private String displayProperty; // 显示内容对用的属性名

    private String functionProperty;// 每个节点所执行功能对应的property

    private java.util.Hashtable hashData; // 所有数据所组成的父子hash

    private java.util.List listData; // 最终的按照深度优先排列成的数据

    private Object root;

    private int size;

    public TreeWholeImpl() {}

    public TreeWholeImpl(String className) throws EasyJException {
        this.className = className;
        this.clazz = BeanUtil.getClass(className);
        this.idProperty = "id";
        this.parentIdProperty = "parentId";
    }

    public TreeWholeImpl(String className, String idProperty,
            String parentIdProperty, String displayProperty,
            String functionProperty) throws EasyJException {
        this.className = className;
        this.clazz = BeanUtil.getClass(className);
        this.size = 0;
        this.idProperty = idProperty;
        this.parentIdProperty = parentIdProperty;
        this.displayProperty = displayProperty;
        this.functionProperty = functionProperty;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public java.util.Hashtable getHashData() {
        return hashData;
    }

    public void setHashData(java.util.Hashtable hashData) {
        this.hashData = hashData;
    }

    public java.util.List getListData() {
        return listData;
    }

    public void setListData(java.util.List listData) {
        this.listData = listData;
    }

    public void createTree() throws EasyJException {
        Session session = null;
        try {
            session = SessionFactory.openSession();
            List valueList = session.query(BeanUtil.getEmptyObject(className));
            if (valueList.size() == 0)
                return;
            createTree(valueList);
        } finally {
            if (session != null)
                session.close();
        }
    }

    /**
     * 此方法只适用于一个根节点。
     * 
     * @param tree
     *                List 需要组建成树的数据
     * @throws EasyJException
     * @return List 组织成树结构之后的数据，其中的节点变成了TreeNode
     */
    public void createTree(List valueList) throws EasyJException {
        if (valueList == null)
            return;
        size = valueList.size();
        Hashtable ht = new Hashtable();
        /* 构建hashtable,key为父Id，value为其子的集合 */
        for (int i = 0; i < size; i++) {
            Object node = (Object) valueList.get(i);
            Long parentId = (Long) BeanUtil.getFieldValue(node,
                    parentIdProperty);
            // 如果是顶层节点，则不做处理。
            if (parentId == null) {
                root = node;
                continue;
            }
            List sons = (List) ht.get(parentId);
            if (sons != null) {
                sons.add(node);
                ht.put(parentId, sons);
            } else {
                sons = new ArrayList();
                sons.add(node);
                ht.put(parentId, sons);
            }
        }
        hashData = ht;
        /* 得到根的所有子 */
        listData = createSubTree(root, ht, 0);
    }

    public List createSubTree(Object parent, Hashtable ht, int level)
            throws EasyJException {
        List result = new ArrayList();

        TreeNode tn = new TreeNode();
        tn.setValue(parent);
        tn.setLevel(level);

        Long id = (Long) BeanUtil.getFieldValue(parent, idProperty);
        Long parentId = (Long) BeanUtil.getFieldValue(parent, parentIdProperty);
        String displayValue = (String) BeanUtil.getFieldValue(parent,
                displayProperty);

        tn.setId(id);
        tn.setParentId(parentId);
        tn.setDisplayValue(displayValue);

        List sons = (List) ht.get(id);

        if (sons == null)
            tn.setIsLeaf(true);
        else
            tn.setIsLeaf(false);

        result.add(tn);

        if (sons == null)
            return result;

        int size = sons.size();
        for (int i = 0; i < size; i++) {
            Object son = (Object) sons.get(i);
            List subTree = createSubTree(son, ht, level + 1);
            if (subTree != null)
                result.addAll(subTree);
        }
        return result;
    }

    public String getIdProperty() {
        return idProperty;
    }

    public void setIdProperty(String idProperty) {
        this.idProperty = idProperty;
    }

    public String getParentIdProperty() {
        return parentIdProperty;
    }

    public void setParentIdProperty(String parentIdProperty) {
        this.parentIdProperty = parentIdProperty;
    }

    public String getDisplayProperty() {
        return displayProperty;
    }

    public void setDisplayProperty(String displayProperty) {
        this.displayProperty = displayProperty;
    }

    public Object getRoot() {
        return root;
    }

    public void setRoot(Object root) {
        this.root = root;
    }

    public String getFunctionProperty() {
        return functionProperty;
    }

    public void setFunctionProperty(String functionProperty) {
        this.functionProperty = functionProperty;
    }

    public int getSize() {
        return size;
    }
}
