package elicitation.model.version;

import java.util.List;

import elicitation.model.project.Scenario;

/**
 * 
 * @author John
 * 场景演化树 . 因为这棵树的叶子可能是 草稿，因而叫 版本树，可能不太合适.
 */
public class EvolutionTree implements ITree<Scenario>{
	private TreeNode root;
	
	/**
	 * 这里的 List<Scenario>是一个场景的List. 
	 * 并且按照先后顺序排列的.
	 */
	@Override
	public void buildTree(List<Scenario> es) {
		Scenario root = es.get(0);
		createRoot(root);
		int size = es.size();
		Scenario node = null;
		for(int i = 0 ;i<size;i++){
			node = es.get(i);
			//判断子节点是谁. table scenario_version
			
		}
		
	}

	@Override
	public List<Scenario> getChildren(Scenario e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Scenario> getLeafs() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Scenario> getPath(Scenario e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Scenario getRoot() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Scenario> traverse(Scenario e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void createRoot(Scenario e) {
		// TODO Auto-generated method stub
		
	}

}
