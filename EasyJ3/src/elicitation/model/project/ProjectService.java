package elicitation.model.project;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import com.ibatis.sqlmap.client.SqlMapClient;

import com.opensymphony.xwork2.ActionSupport;

import elicitation.model.user.SysUser;
import elicitation.utils.Utils;

public class ProjectService {
	static SqlMapClient client = Utils.getMapClient();

	public static String addProject(Project project) throws SQLException {
		Object primaryKey = client.insert("project.insertProject", project);
		if (primaryKey instanceof Integer) {
			int pk = (Integer) primaryKey;
			if (pk >= 0) {
				project.setProjectId(pk);
				return ActionSupport.SUCCESS;
			}
		}
		return ActionSupport.ERROR;
	}

	public static String deleteProject(Project project) throws SQLException {
		int rowsAffected = client.delete("project.deleteProject", project);
		if (rowsAffected > 0)
			return ActionSupport.SUCCESS;
		return ActionSupport.ERROR;
	}

	public static String updateProject(Project project) throws SQLException {
		int rowsAffected = client.update("project.updateProject", project);
		if (rowsAffected > 0)
			return ActionSupport.SUCCESS;
		return ActionSupport.ERROR;
	}

	public static Project selectProject(Project project)  {
		Project res = new Project();
		try{
			res = (Project) (client.queryForObject("project.queryProject",
				project));
		}catch(SQLException e){
			e.printStackTrace();
		}
		return res;
	}

	// TODO Segment
	/** Data Segment * */
	public static String addData(Data data) throws SQLException {
		Object primaryKey = client.insert("project.insertData", data);
		if (primaryKey instanceof Integer) {
			int pk = (Integer) primaryKey;
			if (pk >= 0) {
				data.setDataId(pk);
				return ActionSupport.SUCCESS;
			}
		}
		return ActionSupport.ERROR;
	}

	public static String deleteData(Data data) throws SQLException {
		int rows = client.delete("project.deleteData", data);
		if (rows > 0)
			return ActionSupport.SUCCESS;
		return ActionSupport.ERROR;
	}

	public static Data selectData(Data para) throws SQLException {
		Data data = (Data) (client.queryForObject("project.selectData", para));
		return data;
	}

	public static List<Data> selectDataList(Project project)
			throws SQLException {
		List<Data> ds = client.queryForList("project.selectDataList", project);
		return ds;

	}

	public static String updateData(Data data) throws SQLException {
		int rows = client.update("project.updateData", data);
		if (rows > 0)
			return ActionSupport.SUCCESS;
		return ActionSupport.ERROR;
	}

	/** Role Segment * */
	public static String addRole(Role role) throws SQLException {
		Object primaryKey = client.insert("project.insertRole", role);
		if (primaryKey instanceof Integer) {
			int pk = (Integer) primaryKey;
			if (pk >= 0) {
				role.setRoleId(pk);
				return ActionSupport.SUCCESS;
			}
		}
		return ActionSupport.ERROR;
	}
	public static String pickRole(SysUser user,Role role,Project pro,int isCreator) throws SQLException{
		HashMap para = new HashMap();
		para.put("userId",user.getUserId());
		para.put("roleId", role.getRoleId());
		para.put("projectId", pro.getProjectId());		
		para.put("isCreator", isCreator);
		client.insert("project.insertUserProjectRole",para);
		return ActionSupport.SUCCESS;
	}
	public static String deleteRole(Role role) throws SQLException {
		int rows = client.delete("project.deleteRole", role);
		if (rows > 0)
			return ActionSupport.SUCCESS;
		return ActionSupport.ERROR;
	}

	/**
	 * SQL-select * from role where role_id = #roleId#
	 * 
	 * @param param
	 * @return
	 * @throws SQLException
	 */
	public static Role selectRole(Role param) throws SQLException {
		Role role = (Role) (client.queryForObject("project.selectRole", param));
		return role;
	}

	public static List<Role> selectRoleList(Project project)
			throws SQLException {
		List<Role> roleList = client.queryForList("project.selectRoleList",
				project);
		return roleList;
	}
	
	public static List<Role> selectRoleListOfUser(Project project, SysUser user) throws SQLException{
		int pid = project.getProjectId();
		int uid = user.getUserId();		
		HashMap para = new HashMap();
		para.put("projectId", pid);
		para.put("userId", uid);
		List<Role> roleList = client.queryForList("project.selectRoleListOfUser",para);
		return roleList;
	}
	public static String updateRole(Role role) throws SQLException {
		int rows = client.update("project.updateRole", role);
		if (rows > 0)
			return ActionSupport.SUCCESS;
		return ActionSupport.ERROR;
	}

	public static String freezeScenario(Scenario sce) throws SQLException{
		client.startTransaction();
		client.update("project.freezeScenario",sce);
		client.commitTransaction();
		client.endTransaction();
		return ActionSupport.SUCCESS;
			
		
	}
	/** Scenario Segment * */
	/**
	 * 在更新场景时，要把相关的数据/角色关系也存入到数据库中. 这个地方要多测试几次.
	 */
	public static String addScenario(Scenario sce) throws SQLException {
		try {
			client.startTransaction();
			Object primaryKey = client.insert("project.insertScenario", sce);
			HashMap para = new HashMap();
			para.put("scenarioId", sce.getScenarioId());
			for(Data data:sce.getDataList()){
				para.put("dataId", data.getDataId());// replace the old value.
				client.insert("project.insertScenarioData",para);
			}
			for(Role role:sce.getRoleList()){
				para.put("roleId", role.getRoleId());
				client.insert("project.insertScenarioRole",para);
			}
			/**
			 * 如果有相关的角色描述，则一并插入.
			 * 执行路径：确定解决方案，升级场景.
			 */
			int scenarioId = sce.getScenarioId();
			for(RoleMap rm:sce.getRoleMap()){
				int roleId = rm.getRole().getRoleId();
				String des = rm.getDescription();
				updateScenarioRoleDes(scenarioId, roleId, des);
			}
			client.commitTransaction();
			if (primaryKey instanceof Integer) {
				int pk = (Integer) primaryKey;
				if (pk >= 0) {
					sce.setScenarioId(pk);
					return ActionSupport.SUCCESS;
				}
			}
		} finally {
			client.endTransaction();
		}
		return ActionSupport.ERROR;
	}

	public static String deleteScenario(Scenario sce) throws SQLException {
		int rows = client.delete("project.deleteScenario", sce);
		if (rows > 0)
			return ActionSupport.SUCCESS;
		return ActionSupport.ERROR;
	}
	/**
	 * 没有关联的问题和解决方案信息.
	 * @param scenario
	 * @return
	 * @throws SQLException
	 */
	public static Scenario selectScenarioAllInfo(Scenario scenario) throws SQLException{
		scenario = ProjectService.selectScenario(scenario);
		List<Data> ds = ProjectService.selectDataList(scenario);
		
		scenario.addData(ds);
		
		List<Role> rs = ProjectService.selectRoleList(scenario);
	
		scenario.addRole(rs);
		//select role's description. 
		ProjectService.selectScenarioRoleDes(scenario);
		return scenario;
	}
	public static Scenario selectScenario(Scenario param) throws SQLException {
		Scenario sce = (Scenario) (client.queryForObject(
				"project.selectScenario", param));
		/*Project project= new Project();
		project.setProjectId(sce.getProjectId());
		project = selectProject(project);
		*/
		return sce;
	}
	/**
	 * 	select * from scenario where project_id =#projectId# 
		order by scenario_id desc
		Don't change the scenario_id desc. Affect other version-service.
	 * @param project
	 * @return
	 * @throws SQLException
	 */
	public static List<Scenario> selectScenarioList(Project project)
			throws SQLException {
		List<Scenario> sl = client.queryForList("project.selectScenarioList",
				project);
		return sl;
	}

	public static String updateScenario(Scenario param) throws SQLException {
		int rows = client.update("project.updateScenario", param);
		if (rows > 0)
			return ActionSupport.SUCCESS;
		return ActionSupport.ERROR;
	}

	public static List<Data> selectDataList(Scenario scenario)
			throws SQLException {
		List<Data> ds = client.queryForList("project.selectScenarioData",
				scenario);
		return ds;
	}

	public static List<Role> selectRoleList(Scenario scenario)
			 {
		
		List<Role> rs  = null;
		try{
			rs = client.queryForList("project.selectScenarioRole",
					scenario);
		}catch(Exception e){
			e.printStackTrace();
		}
		return rs;
	}
	/**
	 * 
	 * @param scenario
	 * @throws SQLException
	 */
	public static void selectScenarioRoleDes(Scenario scenario)
			throws SQLException {
		for (Role role : scenario.roleList) {
			HashMap para = new HashMap();
			para.put("scenarioId", scenario.getScenarioId());
			para.put("roleId", role.getRoleId());
			String des = (String) client.queryForObject(
					"project.selectScenarioRoleDes", para);
			
			scenario.mapRole2Des(role, des);
		//	System.out.println("("+role.getRoleId()+","+role.getRoleName()+")="+des);
			
		}		
		

	}

	public static boolean isFreeScenarioRoleDes(int scenarioId,int roleId) throws SQLException{
		HashMap para = new HashMap();
		para.put("scenarioId", scenarioId);
		para.put("roleId", roleId);
		String state = (String)client.queryForObject("project.selectScenarioRoleDesState",para);
		if("free".equals(state) || "".equals(state) || state == null ){
			return true;
		}
		return false;
		
	}
	public static void lockScenarioRoleDes(int scenarioId,int roleId) throws SQLException{
		HashMap para = new HashMap();
		para.put("scenarioId", scenarioId);
		para.put("roleId", roleId);
		client.update("project.lockScenarioRoleDes",para);		
	}
	/**
	 * 单纯的选出角色的描述.
	 * 将锁定单独列为一个service
	 * @param scenarioId
	 * @param roleId
	 * @return
	 * @throws SQLException
	 */
	public static String selectScenarioRoleDes(int scenarioId, int roleId)
			throws SQLException {
		HashMap para = new HashMap();
		para.put("scenarioId", scenarioId);
		para.put("roleId", roleId);
		String old = (String) client.queryForObject(
				"project.selectScenarioRoleDes", para);
		if (old == null)
			old = "";
		return old;
	}

	public static boolean freeScenarioRoleDes(int scenarioId, int roleId) throws SQLException{
		HashMap para = new HashMap();
		para.put("scenarioId", scenarioId);
		para.put("roleId", roleId);
		int rows = client.update("project.freeScenarioRoleDes",para);
		if(rows == 0 ){
			System.err.println("freeScenarioRoleDes failed");
			return false;
		}
		return true;
	}
	public static boolean updateScenarioRoleDes(int scenarioId, int roleId,
			String des) throws SQLException {
		HashMap para = new HashMap();
		para.put("scenarioId", scenarioId);
		para.put("roleId", roleId);
		para.put("description", des);
		String old = (String) client.queryForObject(
				"project.selectScenarioRoleDes", para);
		// 如果不存在，则先添加
		if (old == null || "".equals(old)) {
			Object record = client
					.insert("project.insertScenarioRoleDes", para);

			if (record == null) {
				System.err.println("updateScenarioRoleDes#insert FAIL!");
				return false;
			}
			return true;
		}
		// 如果已经存在，则更新内容;
		int rows = client.update("project.updateScenarioRoleDes", para);
		if (rows == 0) {
			System.err.println("updateScenarioRoleDes#update FAIL!");
			return false;
		}
		return true;

	}
	/** * Scenario - Data -Role Segment ** */

	public static String sendJoinRequest(int projectId, int roleId,
			SysUser user,String req_des) throws SQLException {
		HashMap para = new HashMap();
		para.put("projectId", projectId);
		para.put("roleId", roleId);
		para.put("userId", user.getUserId());
		para.put("reqDes", req_des);
		
		Object key = client.insert("project.insertJoinReq", para);
		if(key == null){
			return ActionSupport.ERROR;
		}else{
			return ActionSupport.SUCCESS;
		}
	}
	/**
	 * 多分支版本功能.
	 * @param string
	 * @param scenario
	 * @return
	 */
	public static List<Scenario> selectScenarioChildren(
			Scenario scenario) throws Exception{
		client.startTransaction();
		List<Scenario> children = client.queryForList("project.selectScenarioChildren",scenario);		
		client.commitTransaction();
		client.endTransaction();
		return children;
	}

	public static Scenario selectScenarioParent(Scenario scenario) throws Exception{
		client.startTransaction();
		Scenario parent = (Scenario) client.queryForObject("project.selectScenarioParent",scenario);		
		client.commitTransaction();
		client.endTransaction();
		return parent;
	}

	
	/**
	 * 空置，采用java语言 更合适.SQL 需要子过程辅助，不适合写在这里
	 */
	@Deprecated
	public static List<Scenario> selectScenarioLeafs(Scenario scenario) throws SQLException{
		client.startTransaction();
		List<Scenario> leafs = client.queryForList("project.selectScenarioLeafs",scenario);		
		client.commitTransaction();
		client.endTransaction();
		return leafs;
	}

	public static boolean isLeaf(Scenario node) throws SQLException{
		client.startTransaction();
		//1. 查看table scenario_version
		Object ro = client.queryForObject("project.isLeaf",node); //table scenario_version.		
		client.commitTransaction();
		client.endTransaction();
		if(ro == null){
			//2. 查看是否是初创的草稿.
			String name = node.getScenarioName();
			if(name == null || "".equals(name) ) {
				//首先抽取全部的scenario信息
				node = ProjectService.selectScenario(node);
			}
			if(node.getUseState().equals(Scenario.DRAFT)) 
				return true; // 说明是初创的草稿，也属于叶子节点.
			return false; //不是叶子节点.
		}
		return true;
	}
	/**
	 * 直接按照名字在 scenario中找 id 最小的，也能找到根.
	 * @param node
	 * @return
	 * @throws SQLException
	 */
	public static boolean isRoot(Scenario node) throws SQLException{
		//1. 查看table scenario_version
		Object parent = client.queryForObject("project.isRoot",node); //table scenario_version.
		if(parent == null)		return true;
		//System.out.println("parent id = "+parent);
		return false;
		
	}
	
	public static String buildTreeJson(Scenario root) throws Exception{
		String tree = "";
		List<Scenario> children = root.getChildren();
		for(Scenario node:children){
			int sid = node.getScenarioId();
			if(node.isLeaf()){
				tree += "{id:'"+ sid +"',text:'"+ sid +"',leaf:true},";
			}else{
				String childrenTree = buildTreeJson(node);
				tree += "{id:'"+ sid +"',text:'"+ sid +"',children:"+ childrenTree + "},";
			}
		}
		if(tree.length() > 0) 
			tree = tree.substring(0,tree.length()-1);
		tree = '['+tree+']';
		return tree;
	}

	public static Scenario selectRootScenario(Scenario scenario)  {
		
		try{
		Scenario root = (Scenario) client.queryForObject("project.selectRootScenario",scenario);
		return root;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	/** * Scenario - Role - Scenario Description ** */
}
