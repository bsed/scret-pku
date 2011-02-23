package elicitation.model.project;

import java.io.Serializable;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;



import elicitation.action.scenario.ScenarioServlet;
import elicitation.model.question.Question;
import elicitation.model.question.QuestionService;
import elicitation.model.review.Review;
import elicitation.model.review.ReviewService;
import elicitation.model.solution.Solution;
import elicitation.model.solution.SolutionService;
import elicitation.model.user.SysUser;
import elicitation.service.user.UserRoleRelationService;
import elicitation.service.user.UserService;
/**
 * 
 * @author John
 * 不同的角色由不同的场景描述
 * 去除观察者、外部参与者等场景属性.
 * 
 *  保持模型的稳定；如果在页面中需要用到其他信息，则直接实现 getXXX()函数即可。
 *  
 *  scenario.jsp ->ScenarioAction 's Model = Scenario.
 */
public class Scenario implements Serializable , Comparable<Scenario>{
	public static String DRAFT = "draft";
	public static String VERSION = "version";
	public static String FREEZE= "freeze";
	private int scenarioId;
	private String scenarioName;
	private int creatorId;
	private int projectId;
	
	private Timestamp buildTime;
	private Timestamp updateTime;
	//private String scenarioDes;//不同角色有不同的场景描述
	private HashMap<Role, String> role2des = new HashMap<Role, String>();
	
	private String useState = "draft"; //{draft,version}
	
	/**
	 * 操作接口放在Class Project中.
	 * 由Project分配数据和角色.
	 */
	protected List<Data> dataList = new ArrayList<Data>();
	protected List<Role> roleList = new ArrayList<Role>();
	/**
	 * 权限部分.
	 */
	
	private boolean joinPermission = false;
	private boolean makeVersionPermission = false;
	private boolean questionPermission = false;
	private boolean defineQTypePermission = false;
	private boolean solutionPermission = false;
	private boolean chooseSolutionPermission = false;
	private boolean voteQuestionPermission = false;
	public Scenario(){
		// Avoid java.lang.InstantiationException in Ibatis-instantiation. 
	}
	public Scenario(int scenarioId){
		this.scenarioId = scenarioId;
	}
	public Scenario(String name, int cid, int pid) {
		this.scenarioName = name;
		this.creatorId = cid;
		this.projectId = pid;
	}
	public void addData(Data data){
		if(dataList.contains(data)) return ;
		dataList.add(data);
	}
	public List<Data> getDataList(){
		return dataList;
	}
	public void addData(List<Data> dataList){
		for(Data data:dataList){
			addData(data);
		}
	}
	public void addRole(Role role){
		if(roleList.contains(role)) return;
		roleList.add(role);
	}
	public void addRole(List<Role> roleList){
		for(Role role:roleList){
			addRole(role);
		}
	}
	public List<Role> getRoleList(){
		return roleList;
	}
	/**
	 * 方便在project.jsp页面中显示 .
	 * @return
	 */
	public String getRoleListStr(){
		String res = "";
		for(Role role:roleList){
			res += ","+role.getRoleName();
		}
		if(res.length()>1){
			res = res.substring(1,res.length());
		}
		return res;
	}
	public String getDesFromRole(Role role){
		return role2des.get(role);
	}
	public void mapRole2Des(Role role,String des){
		if(roleList.contains(role)){
			role2des.put(role,des);
		}
	}
	/**
	 * 转成 List<RoleMap>便于融入 struts2.0 框架.
	 * @return
	 */
	public List<RoleMap> getRoleMap(){
		Role []keys = role2des.keySet().toArray(new Role[0]);
		List<RoleMap> rolemap = new ArrayList<RoleMap>();
		Arrays.sort(keys,new Comparator<Role>(){

			@Override
			public int compare(Role o1, Role o2) {
				// TODO Auto-generated method stub
				return o1.getRoleId() - o2.getRoleId();
			}
			
		});
		for(int i = 0 ;i<keys.length ;i++){
			RoleMap rm = new RoleMap(this);
			rm.role = keys[i];
			float vr = VersionService.getVoteRate(this.scenarioId,rm.role.getRoleId());
			rm.role.setVoteRate(vr);
			rm.description = role2des.get(keys[i]);
			rolemap.add(rm);
		}
		return  rolemap;
	}
	public String getDescription(Role role){
		return  role2des.get(role);
	}
	
	public int getScenarioId() {
		return scenarioId;
	}
	public void setScenarioId(int scenarioId) {
		this.scenarioId = scenarioId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	public int getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(int creatorId) {
		this.creatorId = creatorId;
	}

	public String getScenarioName() {
		return scenarioName;
	}

	public void setScenarioName(String scenarioName) {
		this.scenarioName = scenarioName;
	}

	public int getProjectId() {
		return projectId;
	}
	public Timestamp getBuildTime() {
		return buildTime;
	}
	public void setBuildTime(Timestamp buildTime) {
		this.buildTime = buildTime;
	}
	public Timestamp getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}
	/*****for struts display info****/
	/**
	 * 评论部分.
	 */
	public List<Review> getReviews(){
		List<Review> revlist = null;
		revlist = ReviewService.selectScenarioReview(scenarioId);
		return revlist;
	}
	/**
	 * 返回该版本最终采纳的 Solution
	 * 采用多分支版本演化机制，这里的bestsolution 就是一个集合。
	 * reference： history_version.jsp.
	 */
	public List<Solution> getBestSolution(){
		List<Solution> solutions = SolutionService.selectSolutionFromPrevScenario(this);
		return solutions;
	}
	public List<Question> getQuestion(){
		return QuestionService.selectQuestions(this);
	}
	public List<Solution> getSolutions(){
		return SolutionService.selectSolutionList(this);
	}
	/**
	 * 
	 * @return parent project.
	 */
	public Project getProject(){
		Project pro = new Project();
		pro.setProjectId(projectId);		
		return ProjectService.selectProject(pro);
	}
	public SysUser getSysUser(){
		SysUser user = new SysUser();
		user.setUserId(creatorId);
		return UserService.selectUserByID(user);
	}
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Scenario){
			Scenario para = (Scenario)obj;
			return para.getScenarioId() == this.scenarioId;
		}
		return false;
	}
	public int hashCode(){
		return Integer.valueOf(scenarioId).hashCode();
	}
	@Override
    public String toString() {
		return this.scenarioId+" "+this.scenarioName;
	}
	public String getUseState() {
		return useState;
	}
	/**
	 * 方便在 project.jsp页面中显示.
	 * @return
	 */
	public String getViewUseState(){
		if(useState.equals(Scenario.DRAFT))
			return "草稿";
		return "版本";
		
	}
	/**
	 * 冻结版本
	 *   挑选好解决方案后，冻结原有版本，不能进行提问以及提新的解决方案.
	 * @return
	 */
	public boolean isFreeze(){
		if(Scenario.FREEZE.equals(useState))
			return true;
		return false;
	}
	public boolean isDraft(){
		if(Scenario.DRAFT.equals(useState))
			return true;
		return false;
	}
	public boolean isVersion(){
		if(Scenario.VERSION.equals(useState)){
			return true;
		}
		return false;
	}
	
	public void setUseState(String useState) {
		this.useState = useState;
	}
	/**
	 * 		权限部分
	 * private boolean joinPermission = false;
	private boolean makeVersionPermission = false;
	private boolean editPermission  =false;
	private boolean questionPermission = false;
	private boolean defineQTypePermission = false;
	private boolean solutionPermission = false;
	private boolean chooseSolutionPermission = false;
	 */
	public void solvePermission(SysUser user){
		/**
		 * 1. 找到user在工程中的所有角色; //包括 admin信息
		 * 2. 找到场景在工程中的所有角色;
		 * 3. 根据以上信息进行权限控制.		 * 
		 * */
		if(user!=null){
			Project project = getProject();
			int uid = user.getUserId();
			
			/**
			 * 表示用户在项目中具有哪些角色.
			 */
			List<Role> p_roles = UserRoleRelationService.selectUserRoleInProject(user, project);
			List<Role> s_roles = roleList;
			if(uid == project.getCreatorId()){ //Maybe bug.
				setProjectAdminPermission(p_roles);
			}else{
				makeVersionPermission = false;		
				solutionPermission = false; //提解决方案;
				chooseSolutionPermission = false;//选择解决方案.
			}
			if(user.isMemberOfScenario(p_roles,s_roles)){ // TODO 这个地方效率有损失，后期再调整效率吧
				setScenarioUserPermission(p_roles);
			}else if(user.isMemberOfProject(p_roles)){
				setProjectUserPermission();
			}else{
				setGeneralUserPermission();
			}
		}else{
			setGuestPermission();
		}
	}
	public void setGuestPermission(){
		joinPermission = false;
		makeVersionPermission = false;
		
		questionPermission = false;
		defineQTypePermission =false;
		solutionPermission = false;
		 
		
	}
	public void setGeneralUserPermission(){
		joinPermission = true;
		
		questionPermission = true;
		defineQTypePermission = true;
		solutionPermission = false;
		
		voteQuestionPermission = false;
	}
	public void setScenarioUserPermission(List<Role> roles){
		joinPermission = true;
		
		for(Role role :roleList){
			if(roles.contains(role)){
				role.setEditPermission(true);
			}
		}
		questionPermission = true;
		defineQTypePermission = true;
		solutionPermission = true;
		
		voteQuestionPermission = true;
	}
	public void setProjectUserPermission(){
		joinPermission = true;		
		questionPermission = true;
		defineQTypePermission = true;
		
		solutionPermission = true;				
		voteQuestionPermission = true;
	}
	/**
	 * 
	 * @param roles  
	 * 管理员处理的问题： 
	 *  1. 确定版本
	 *  2. 提解决方案 
	 *  3. 确定解决方案 
	 *  
	 *  如编辑描述等，是由角色控制的，不属于管理员的处理范畴。
	 *  管理员可以直接为自己选择角色.
	 * 
	 */
	public void setProjectAdminPermission(List<Role> roles){		
		makeVersionPermission = true;		
		solutionPermission = true; //提解决方案;
		chooseSolutionPermission =true;//选择解决方案.
	}
	public boolean isVoteQuestionPermission(){
		return voteQuestionPermission;
	}
	@Deprecated
	public boolean isJoinPermission() {
		return joinPermission;
	}
	public boolean isMakeVersionPermission() {
		return makeVersionPermission;
	}

	public boolean isQuestionPermission() {
		return questionPermission;
	}
	public boolean isDefineQTypePermission() {
		return defineQTypePermission;
	}
	public boolean isSolutionPermission() {
		return solutionPermission;
	}
	public boolean isChooseSolutionPermission() {
		return chooseSolutionPermission;
	}
	
	public double getRand(){
		return Math.random();
	}
	/***
	 * 以下是多分支版本的功能.
	 * 初次实现，全部由数据库直接读取;
	 * 后续有功夫的话，需要代码重构，一次读取，在内存中操作，减少数据库的压力.
	 */
	public List<Scenario> getChildren() throws Exception{
		List<Scenario> children = 
			ProjectService.selectScenarioChildren(this);
		return children;
	}
	public Scenario getParent() throws Exception{
		Scenario parent = ProjectService.selectScenarioParent(this);
		return parent;
	}
	/**
	 * 以当前场景为末端，返回 一条历史路径 。
	 * 用于[历史版本]链接,产生一个历史版本路径.
	 * @return
	 * @throws Exception
	 */
	public List<Scenario> getHistoryPath() throws Exception {
		List<Scenario> path = new ArrayList<Scenario>();		
		Scenario parent = getParent();
		while(parent != null){
			path.add(parent);
			parent = parent.getParent();
		}
		return path;
	}
	// 以当前版本为根的树的叶子.
	public List<Scenario> getLeafs() throws Exception{
		List<Scenario> leafs = new ArrayList<Scenario>();
		
		
		List<Scenario> children =getChildren();
		if(children == null || children.size() ==  0) {
			leafs.add(this);
			return leafs;
		}
		for(Scenario node:children){
			//1. 如果当前节点是叶子，就加入到 leafs.
			if(node.isLeaf())
				leafs.add(node);
			//2. 如果当前节点是中间节点，递归调用 getLeafs .
			else {
				leafs.addAll(node.getLeafs());
			}
		}
		//按照 id 进行降序排列.
		Collections.sort(leafs);
		return leafs;
	}
	public boolean isLeaf() throws SQLException{
		return ProjectService.isLeaf(this);
	}
	public boolean isRoot() throws SQLException{
		return ProjectService.isRoot(this);
	}
	@Override
	public int compareTo(Scenario o) {
		
		return -(scenarioId - o.scenarioId);
	}
	/**
	 * scenario.jsp
	 * 用于得到上一版本的ID.
	 */
	public int getPrevId() throws SQLException{
		Scenario pre = VersionService.selectPreVersion(this);
		if(pre != null)
			return pre.scenarioId;
		return -1;
	}
	// 得到下一版本的Scenarios 
	// 多分支结构. => List<Scenario>
	public List<Scenario> getNextScenarios() throws SQLException {
		List<Scenario> ns = VersionService.selectNextVersions(this);
		return ns;
	}
	/**
	 * <PreScenario,PreSolutions{chooosed}, CurScenario>
	 * 可以选择多个解决方案，进化到下一个版本中. => List<Solution> 
	 * @return
	 */
	public List<Solution> getPreSolutions(){
		return SolutionService.selectSolutionFromNextScenario(this);
	}
}
