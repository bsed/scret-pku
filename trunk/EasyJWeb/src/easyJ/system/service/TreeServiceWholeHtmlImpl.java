package easyJ.system.service;

import java.util.Hashtable;
import easyJ.common.BeanUtil;
import easyJ.common.EasyJException;
import easyJ.common.Const;
import java.util.List;
import easyJ.common.validate.Validate;
import easyJ.common.Format;
import easyJ.common.validate.GenericValidator;
import java.util.ArrayList;
import easyJ.common.StringUtil;

/**
 * 因为直接生成整个Three的创建会消耗比较长的时间，所以TreeService是用来缓冲所有的tree以及对tree进行操作的
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2007
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */
public class TreeServiceWholeHtmlImpl implements TreeService {
	private java.util.Hashtable trees = new Hashtable();

	private static TreeServiceWholeHtmlImpl instance = null;

	private int iniSpreadLevel = 1; // 这棵树初始展开的层数。初始化为1，如果值为Integer.MAX_VALUE则意味着要默认展开所有的层次。

	public static TreeService getInstance() {
		if (instance == null)
			instance = new TreeServiceWholeHtmlImpl();
		return instance;
	}

	/* 这个方法获得整个表的树 */
	public Tree getTree(String className) throws easyJ.common.EasyJException {
		if (trees.get(className) != null) {
			return (Tree) trees.get(className);
		} else {
			Class clazz = BeanUtil.getClass(className);
			String idProperty = (String) BeanUtil.getPubStaticFieldValue(clazz,
					Const.TREE_ID_PROPERTY);
			String parentIdProperty = (String) BeanUtil.getPubStaticFieldValue(
					clazz, Const.TREE_PARENT_ID_PROPERTY);
			String displayProperty = (String) BeanUtil.getPubStaticFieldValue(
					clazz, Const.TREE_DISPLAY_PROPERTY);
			String functionProperty = (String) BeanUtil.getPubStaticFieldValue(
					clazz, Const.TREE_FUNCTION_PROPERTY);
			Tree tree = new TreeWholeImpl(className, idProperty,
					parentIdProperty, displayProperty, functionProperty);
			tree.createTree();
			trees.put(className, tree);
			return tree;
		}
	}

	/* 这个方法获得按照权限定义某个用户所拥有数据的树，下面的user将来要换做User类型 */
	public Tree getTree(String className, ArrayList list)
			throws easyJ.common.EasyJException {
		Class clazz = BeanUtil.getClass(className);
		String idProperty = (String) BeanUtil.getPubStaticFieldValue(clazz,
				Const.TREE_ID_PROPERTY);
		String parentIdProperty = (String) BeanUtil.getPubStaticFieldValue(
				clazz, Const.TREE_PARENT_ID_PROPERTY);
		String displayProperty = (String) BeanUtil.getPubStaticFieldValue(
				clazz, Const.TREE_DISPLAY_PROPERTY);
		String functionProperty = (String) BeanUtil.getPubStaticFieldValue(
				clazz, Const.TREE_FUNCTION_PROPERTY);
		Tree tree = new TreeWholeImpl(className, idProperty, parentIdProperty,
				displayProperty, functionProperty);
		tree.createTree(list);
		return tree;
	}

	public void generateWholeTree(Tree tree, StringBuffer buffer,
			int selectMode, String functionUrl) throws EasyJException {
		if (tree == null || tree.getSize() == 0)
			return;
		Object root = tree.getRoot();
		// buffer.append("<table class=\"border\"><tr><td>");

		buffer.append("<div id=\"node\" style=\"display:block\">\n");
		/* 从第一层开始输出,即不输出根节点. */
		generateWholeSubTree(root, tree, 1, 1, buffer, selectMode, functionUrl);
		buffer.append("</div>");
		// buffer.append("</td></tr></table>");
	}

	/**
	 * 此方法得到一颗html树的代码，调用的时候需要得到一棵树，以及这棵树的根节点root。
	 * 采用generateWholeTreeHtml(root,tree,1);来得到
	 * 
	 * @param node
	 *            Object 要处理的当前节点
	 * @param tree
	 *            Tree 要处理的当前节点所属的树
	 * @param level
	 *            int 当前处理节点所处的层次 根节点为第一层
	 * @param iniLevel
	 *            int 是指从第几层的节点开始输出的.初始的输出层数.在输出层次路径的时候有用.
	 * @param functionUrl
	 *            String 节点所对应的功能地址
	 * @throws EasyJException
	 */
	public void generateWholeSubTree(Object node, Tree tree, int level,
			int iniLevel, StringBuffer buffer, int selectMode,
			String functionUrl) throws EasyJException {
		generateSingleNode(node, tree, level, iniLevel, buffer, selectMode,
				functionUrl);
		Long parentId = (Long) BeanUtil.getFieldValue(node, tree
				.getIdProperty());
		List sons = (List) tree.getHashData().get(parentId);
		if (sons == null) {
			buffer.append("</div>\n");
			// System.out.println("</div>");
			return;
		}
		for (int i = 0; i < sons.size(); i++) {
			generateWholeSubTree(sons.get(i), tree, level + 1, 1, buffer,
					selectMode, functionUrl);
		}
		if (level > iniLevel)
			buffer.append("</div>\n");
	}

	/**
	 * 此方法将单个节点node的信息输出
	 * 
	 * @param node
	 *            Object 要输出信息的节点。
	 * @param tree
	 *            Tree 此node所属tree，因为要用到id以及显示的名称。
	 * @param level
	 *            int 此节点所处的层次
	 * @param selectMode
	 *            int 这棵树的选择模式：中间节点单纯展开，中间节点除了展开也作为功能，包括选择返回。
	 * @throws EasyJException
	 */
	public void generateSingleNode(Object node, Tree tree, int level,
			int iniLevel, StringBuffer buffer, int selectMode,
			String functionUrl) throws EasyJException {
		if (level <= iniLevel)
			return; // 这样不显示根节点数据。
		
		Long id = (Long) BeanUtil.getFieldValue(node, tree.getIdProperty());
		if(id.equals(new Long(11))){
			System.out.println("领域管理");
		}
		String displayValue = (String) BeanUtil.getFieldValue(node, tree.getDisplayProperty());
		buffer.append("<p class=\"treeNode\">");
		// System.out.print("<p>");
		boolean isLeaf = false;
		/* 这里的id为空意味着是增加的一个“空”节点，其他的id都应该是有值的，因为是主键 */
		if (id == null || tree.getHashData().get(id) == null) {
			isLeaf = true;
		}
		System.out.println("Single Node- Tree id = "+id +" isLeaf = " +isLeaf);
		String[] path = getPathWithoutPic(level, isLeaf);

		for (int i = 0; i < level - iniLevel; i++) {
			// if(i!=level-1)
			buffer.append(path[i]);// System.out.print(path[i]);
			// else buffer.append("<img src=\"Images/" + path[i] + "\"
			// name=\"logo"+id+"\" onclick=\"tclick("+id+")\"/>");
		}
		/*
		 * 如果传入的functionUrl为空，说明每个节点都有自己的functionUrl，这个时候从节点中取出functionUrl
		 * 否则，这个functionUrl中应该有一个问号，这个时候应该将问号替换为node的id。
		 */
		if (GenericValidator.isBlankOrNull(functionUrl)
				&& !GenericValidator.isBlankOrNull(tree.getFunctionProperty()))
			functionUrl = (String) BeanUtil.getFieldValue(node, tree.getFunctionProperty());
		else
			;// functionUrl=functionUrl.replaceAll("?",id.toString());
		setFunction(buffer, isLeaf, id, displayValue, selectMode, functionUrl);

		buffer.append(displayValue);
		buffer.append("</a>");
		buffer.append("</p>\n");

		/* 如果展开层次为最大值，那么就默认展开所有的层次 */
		if (iniSpreadLevel != Const.MAX_INT_VALUE) {
			if (level <= iniSpreadLevel)
				buffer.append("<div  id=\"node" + id
						+ "\" style=\"display:block\">\n");
			else
				buffer.append("<div id=\"node" + id
						+ "\" style=\"display:none\">\n");
		} else {
			buffer.append("<div id=\"node" + id
					+ "\" style=\"display:block\">\n");
		}
	}

	public String[] getPathWithoutPic(int level, boolean isLeaf) {
		String[] path = new String[level];
		for (int j = 0; j < level; j++) {
			path[j] = "　";
		}

		return path;
	}

	public String[] getPath(int level, boolean isLeaf) {
		String[] path = new String[level];
		if (isLeaf)
			path[level - 1] = TreeNode.LEAF;
		else {
			if (iniSpreadLevel != Const.MAX_INT_VALUE) {
				if (level <= iniSpreadLevel)
					path[level - 1] = TreeNode.UNZIPROOT;
				else
					path[level - 1] = TreeNode.ZIPROOT;
			} else
				path[level - 1] = TreeNode.UNZIPROOT;
		}
		for (int j = 0; j < level - 1; j++) {
			path[j] = "　";
		}

		return path;
	}

	public void setFunction(StringBuffer buffer, boolean isLeaf, Long id,
			String displayValue, int selectMode, String functionUrl) {
		String functionPrefix = "";
		if (isLeaf)
			functionPrefix = "leaf";
		else
			functionPrefix = "node";

		String functionPostfix = "";

		switch (selectMode) {
		case Const.TREE_SELECT_SPREAD_FUNCTION:
			functionPostfix = "Function";
			break;
		case Const.TREE_SELECT_LEAF_RETURN:
			functionPostfix = "Select";
			break;
		case Const.TREE_SELECT_NODE_RETURN:
			functionPostfix = "Select";
			break;
		}

		String functionName = functionPrefix + functionPostfix;

		// 如果仅仅只是展开
		if (selectMode == Const.TREE_SELECT_SPREAD)
			if (!isLeaf)
				functionName = "tclick";
			else
				functionName = "";

		// 如果方法本身就是javascript方法，则直接用javascript方法。否则调用其他方法
		if (functionUrl != null && functionUrl.indexOf("javascript") >= 0) {
			buffer.append("<a href=\"" + Format.format(functionUrl) + "\">");
		} else if (!"".equals(functionName))
			if (id == null)
				buffer.append("<a href=\"javascript:" + functionName + "("
								+ "''" + ",'','" + Format.format(functionUrl)
								+ "')\">");
			else
				buffer.append("<a href=\"javascript:" + functionName + "(" + id
						+ ",'" + displayValue + "','"
						+ Format.format(functionUrl) + "')\">");
	}

	public int getIniSpreadLevel() {
		return iniSpreadLevel;
	}

	public synchronized void setIniSpreadLevel(int iniSpreadLevel) {
		this.iniSpreadLevel = iniSpreadLevel;
	}

	/*
	 * 暂时还没有考虑树节点的添加删除，将来应该加上去。
	 */
}
