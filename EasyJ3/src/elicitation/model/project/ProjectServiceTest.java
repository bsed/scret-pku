package elicitation.model.project;

import java.sql.SQLException;
import java.util.List;

import com.opensymphony.xwork2.ActionSupport;

import elicitation.model.user.SysUser;

import junit.framework.TestCase;

public class ProjectServiceTest extends TestCase {
	Project pro ;
	Data data ;
	Role role ; 
	Scenario sce;
	protected void setUp(){
		pro = new Project(1,2,"软件","软件领域的描述");
		data = new Data("测试数据","测试数据描述",1,1);//cid = 1 and pid = 1.
		role = new Role("测试角色","测试角色描述",1,1);
		sce = new Scenario("测试场景",1,1);
		sce.addData(data);
		sce.addRole(role);
	}
	public void testAddProject() throws SQLException {
		String res = ProjectService.addProject(pro);
		
		
	}
	/**
	 * 关于数据的增删查改
	 * 1) relation between  data and project .
	 * 2) relation between data and scenario.
	 * 3) add/edit/remove/select data's properties.
	 * @throws SQLException
	 */
	public void testDataService() throws SQLException{
		String res = ProjectService.addData(data);
		ProjectService.addData(data);
		assertEquals(ActionSupport.SUCCESS, res);
		System.out.println("dataId="+data.getDataId());
		Data sd = ProjectService.selectData(data);
		assertEquals(data.getDataId(),sd.getDataId());
		sd.setDataDes("update description");
		res = ProjectService.updateData(sd);
		assertEquals(ActionSupport.SUCCESS,res);
		res = ProjectService.deleteData(sd);
		assertEquals(ActionSupport.SUCCESS,res);
	}
	/**
	 * 关于角色的增删查改
	 * 1) relation between role and project.
	 * 2) relation between role and scenario.
	 * 3) add/edit/remove/select role's properties.
	 * @throws SQLException
	 */
	public void testRoleService() throws SQLException{
		String res = ProjectService.addRole(role);
		ProjectService.addRole(role);
		assertEquals(ActionSupport.SUCCESS, res);
		System.out.println("roleId="+role.getRoleId());
		Role sd = ProjectService.selectRole(role);
		assertEquals(role.getRoleId(),sd.getRoleId());
		sd.setRoleDes("update description");
		res = ProjectService.updateRole(sd);
		assertEquals(ActionSupport.SUCCESS,res);
		res = ProjectService.deleteRole(sd);
		assertEquals(ActionSupport.SUCCESS,res);
	}
	public void testPickRole() throws SQLException{
		SysUser user = new SysUser();
		user.setUserId(1);
		Role role = new Role();
		role.setRoleId(1);
		Project pro = new Project();
		pro.setProjectId(1);
		ProjectService.pickRole(user, role, pro, 1);
	}
	public void testRoleProjectUser() throws SQLException{
		Project project = new Project(122);
		SysUser user = new SysUser(12);
		List<Role> rl = ProjectService.selectRoleListOfUser(project, user);
		for(Role role:rl){
			System.out.println(role);
		}
	}
	/**
	 * 关于场景的增删查改 exclude relations with role and data . Just test atomic scenario.
	 * 1) relation between scenario and project.
	 * 2）add/edit/remove/select scenario's properties.
	 * @throws SQLException
	 */
	public void testScenarioService() throws SQLException{
		String res = ProjectService.addScenario(sce);
		ProjectService.addScenario(sce);
		assertEquals(ActionSupport.SUCCESS, res);
		System.out.println("sceId="+sce.getScenarioId());
		Scenario sd = ProjectService.selectScenario(sce);
		assertEquals(sce.getScenarioId(),sd.getScenarioId());
		res = ProjectService.updateScenario(sd);
		assertEquals(ActionSupport.SUCCESS,res);
		res = ProjectService.deleteScenario(sd);
		assertEquals(ActionSupport.SUCCESS,res);
	}
	public void testFreezeScenario() throws SQLException{
		Scenario sce = new Scenario();
		sce.setScenarioId(127);
		ProjectService.freezeScenario(sce);
	}
	public void testAddScenarioWithDataANDRole()throws SQLException{
		String res = ProjectService.addScenario(sce);
	}
	/**
	 * ### The Main Function of the System
	 * Assign roles and data to scenario. 
	 * Assign roles to user in a project.
	 * Check the authority through role.
	 * @throws Exception 
	 */
	public void testCompositeScenarioService() throws Exception{
		sce.setScenarioId(1);
		data.setDataId(1);
		role.setRoleId(1);
		String description = "场景角色描述";
//		String res = CompositeScenarioRoleDataService.insertScenarioData(sce, data);
//		CompositeScenarioRoleDataService.insertScenarioRole(sce, role);
//		CompositeScenarioRoleDataService.insertScenarioRoleDes(sce, role, description);
//		CompositeScenarioRoleDataService.deleteScenarioData(sce, data);
//		CompositeScenarioRoleDataService.deleteScenarioRole(sce, role);
//		CompositeScenarioRoleDataService.deleteScenarioRoleDes(sce, role);
		
	}
	
	public void testUpdateProject() throws SQLException {
		pro.setProjectDescription("Version2");
		ProjectService.updateProject(pro);
	}

	public void testQueryProject() throws SQLException {
		//fail("Not yet implemented"); // TODO
		
		Project res = ProjectService.selectProject(new Project(98));
		System.out.println("Name="+res.getProjectName());
		System.out.println("Des="+res.getProjectDescription());
		System.out.println("ID="+res.getCreatorId());
		
		//assertEquals(pro.getProjectName(), res.getProjectName());
	}
	
	public void testDeleteProject() throws SQLException {
		//fail("Not yet implemented"); // TODO
		String mess = ProjectService.deleteProject(pro);
		assertEquals(ActionSupport.SUCCESS, mess);
	}
	public void testScenarioRoleDes()throws SQLException{
		boolean r = ProjectService.updateScenarioRoleDes(95, 45, "Description");
		assertEquals(true, r);
	}
	public void testIsFreeRoleDes() throws SQLException{
		boolean state = ProjectService.isFreeScenarioRoleDes(127,63);
		System.out.println(state);
	}
	public void testSelectScenario() throws SQLException{
		Scenario  sc = new Scenario();
		sc.setScenarioId(127);
		sc = ProjectService.selectScenario(sc);
		System.out.println(sc.getUseState());
	}
	/**
	 * 这个测试没有做好.
	 * @throws SQLException
	 */
	public void testScenarioSolvePermission() throws SQLException{
		Scenario sc = new Scenario();
		sc.setScenarioId(127);
		sc=ProjectService.selectScenario(sc);
		
		SysUser user = new SysUser();
		user.setUserId(92);
		sc.solvePermission(user);
		System.out.println(sc);
	}
	/**
	 * 以下是多分支版本的测试函数 
	 */
	public void testGetRoots() throws SQLException{
		Project project = new Project(122);
		List<Scenario> ss = ProjectService.selectScenarioList(project);
		project.addScenario(ss);
		List<Scenario> roots = project.getRoots();
		for(Scenario node:roots){
			System.out.println(node);
		}
	}
	public void testIsRoot() throws SQLException{
		Scenario scenario = new Scenario(145);
		System.out.println(scenario.isRoot());
	}
	public void testIsLeaf() throws SQLException{
		Scenario scenario = new Scenario(184);
		System.out.println(scenario.isLeaf());
	}
	public void testGetLeafs() throws Exception {
		Scenario scenario = new Scenario(149);
		List<Scenario> leafs = scenario.getLeafs();
		for(Scenario node:leafs){
			System.out.println(node);
		}
	}
	public void testGetParent() throws Exception {
		Scenario scenario  =new Scenario(146);
		Scenario parent = scenario.getParent();
		System.out.println(parent);
	}
	public void testGetHistoryPath() throws Exception{
		Scenario node = new Scenario(189);
		List<Scenario> path = node.getHistoryPath();
		for(Scenario sce:path){
			System.out.println(sce);
		}
	}
	public void testBuildTreeJson() throws Exception{
		Scenario root = new Scenario(146);
		String treeJson = ProjectService.buildTreeJson(root);
		System.out.println(treeJson);
		
	}
	public void testSelectRootScenario() throws Exception{
		Scenario root = new Scenario(184);
		root.setScenarioName("求职者填写简历");
		root = ProjectService.selectRootScenario(root);
		System.out.println(root);
	}
	protected void tearDown(){
		
	}

}
