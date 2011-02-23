package elicitation.model.version;

import java.util.List;

/**
 * 
 * @author John
 * 单个场景形成的版本树
 * 1. 找到所有的叶子节点 --> 也就是各个分支的最新状态.
 * 2. 任意给定一个节点，能找出(节点-根)和(节点-叶子)的路径 ---> 用于找历史路径.
 * 备用功能:
 * 3. 遍历整个的树.
 * 
 */
public interface ITree <E> {
	void buildTree(List<E> es);
	void createRoot(E e);
	E getRoot();
	List<E> getChildren(E e);
	List<E> getLeafs();	
	List<E> getPath(E e);//返回路径 按照id数排序；在这里，id数正好代表建立的先后次序
	List<E> traverse(E e);// 遍历以e为根的子树.
}
