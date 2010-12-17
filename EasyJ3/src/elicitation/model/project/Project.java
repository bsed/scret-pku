package elicitation.model.project;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import elicitation.model.domain.Domain;
import elicitation.model.domain.DomainService;
import elicitation.model.request.Request;
import elicitation.model.user.SysUser;
import elicitation.service.request.RequestService;
import elicitation.service.user.UserService;

public class Project implements Serializable{
	private int projectId;
	private int domainId;
	private String state="Y";
	private Date buildTime;
	private Date updateTime;
	private String projectName;
	private String projectDescription;
	private int creatorId;
	private List<Data> datas = new ArrayList<Data>();
	private List<Role> roles = new ArrayList<Role>();
	private List<Scenario> scenarios = new ArrayList<Scenario>();
	private boolean writePermission = false;
	
	public Project(){		
	}
	public Project(int projectId){
		this.projectId = projectId;
	}
	public Project(int cid,int did,String name,String des){
		creatorId = cid;
		domainId = did;
		projectName = name;
		projectDescription = des;
	}
	public void addData(List<Data> ds){
		for(Data data:ds){
			addData(data);
		}
	}
	public void addData(Data data){
		if(datas.contains(data)) return;
		data.setProjectId(this.projectId);
		datas.add(data);
	}
	public List<Data> getDataList(){
		return datas;
	}
	public Data findData(int did){
		Iterator<Data> ite = datas.iterator();
		while(ite.hasNext()){
			Data n = ite.next();
			if(n.getDataId() == did)
				return n;
		}
		return null;
	}
	public void addRole(List<Role> rs){
		for(Role role:rs){
			addRole(role);
		}
	}
	public void addRole(Role role){
		if(roles.contains(role)) return ;
		role.setProjectId(this.projectId);
		roles.add(role);
	}
	public List<Role> getRoleList(){
		return roles;
	}
	public Role findRole(int rid){
		Iterator<Role> ite = roles.iterator();
		while(ite.hasNext()){
			Role n = ite.next();
			if(n.getRoleId() == rid){
				return n;
			}
		}
		return null;
	}
	
	public void addScenario(List<Scenario> ss){
		for(Scenario s:ss){
			addScenario(s);
		}
	}
	public void addScenario(Scenario sce){
		if(scenarios.contains(sce))return ;
		sce.setProjectId(this.projectId);
		scenarios.add(sce);
	}
	public  List<Scenario> getScenarioList(){
		
		return scenarios;
	}
	public Scenario findScenario(int sid){
		Iterator<Scenario> ite = scenarios.iterator();
		while(ite.hasNext()){
			Scenario n = ite.next();
			if(n.getScenarioId()==sid)
				return n;
		}
		return null;
	}
	public int getProjectId() {
		return projectId;
	}
	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}
	public int getDomainId() {
		return domainId;
	}
	public void setDomainId(int domainId) {
		this.domainId = domainId;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Date getBuildTime() {
		return buildTime;
	}
	public void setBuildTime(Date buildTime) {
		this.buildTime = buildTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getProjectDescription() {
		return projectDescription;
	}
	public void setProjectDescription(String projectDescription) {
		this.projectDescription = projectDescription;
	}
	public int getCreatorId() {
		return creatorId;
	}
	
	public void setCreatorId(int creatorId) {
		this.creatorId = creatorId;
	}
	public void removeData2Scenario(Data data,Scenario sce){
		if(sce.dataList.contains(data)){
			sce.dataList.remove(data);
		}
	}
	/**
	 * 删除场景中的某个角色
	 * @param role
	 * @param sce
	 */
	public void removeRole2Scenario(Role role,Scenario sce){
		if(sce.roleList.contains(role)){
			sce.roleList.remove(role);
		}
	}
	
	public List<Scenario> getScenarioFromRole(Role role){
		List<Scenario> res = new ArrayList<Scenario>();
		Iterator<Scenario> ite = scenarios.iterator();
		while(ite.hasNext()){
			Scenario sce  = ite.next();
			if(sce.roleList.contains(role)){
				res.add(sce);
			}
		}
		return res;
	}
	public List<Scenario> getScenarioFromData(Data data){
		List<Scenario> res = new ArrayList<Scenario>();
		Iterator<Scenario> ite = scenarios.iterator();
		while(ite.hasNext()){
			Scenario sce  = ite.next();
			if(sce.dataList.contains(data)){
				res.add(sce);
			}
		}
		return res;
	}
	public List<Data> getDataListofScenario(Scenario sce){
		return sce.dataList;
	}
	public List<Role> getRoleListofScenario(Scenario sce){
		return sce.roleList;
	}
	
	public void addData2Scenario(Data data,Scenario sce){
		if(datas.contains(data) && scenarios.contains(sce)){
			if(!sce.dataList.contains(data))
				sce.dataList.add(data); 
		}
	}
	public void addRole2Scenario(Role role,Scenario sce){
		if(roles.contains(role) && scenarios.contains(sce)){
			if(!sce.roleList.contains(role)){
				sce.roleList.add(role);
			}
		}
	}
	public boolean equals(Object o){
		if(o == null ) return false;
		if(o instanceof Project){
			return ((Project)o).projectName.equals(this.projectName);
		}else
			return false;
	}
	public int hashCode(){
		return projectName.hashCode();
	}
	/***********************For struts-jsp use**************************/
	public List<Request> getRequests(){
		return RequestService.selectRequests(this);
	}
	public int getRequestNum(){
		return RequestService.selectRequests(this).size();
	}
	public Domain getDomain(){
		Domain dom =new Domain();
		dom.setDomainId(domainId);
		return DomainService.selectDomain(dom);
	}
	public SysUser getCreator(){
		SysUser user = new SysUser();
		user.setUserId(creatorId);
		return UserService.selectUserByID(user);
		
	}
	public List<Scenario> getScenarioDraftList(){
		if(this == null) return null;
		List<Scenario> sl = new ArrayList<Scenario>();
		for(Scenario sc : scenarios){
			if(sc.getUseState().equals(Scenario.DRAFT)){
				sl.add(sc);
			}
		}
		return sl;
	}
	/**
	 * project.jsp
	 *   用户登录以后要在工程页 我的角色，不然一头雾水，不知道是哪个角色.
	 */
	private List<Role> userRoleList = null;
	private boolean joinPermission = false;
	private boolean pickRolePermission = false;
	public void solveUserRoleList(SysUser user){
		try{
			userRoleList = ProjectService.selectRoleListOfUser(this, user);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public List<Role> getUserRoleList(){
		return userRoleList;
	}
	public String getUserRoleListStr(){
		String  res = "";
		for(Role role:userRoleList){
			res += ","+role.getRoleName();
		}
		if(res.length() >0){
			res = res.substring(1,res.length());
		}
		return res;
	}
	/**
	 * 加入多版本以后的数据改正.
	 * @return
	 */
	public List<Scenario> getScenarioVersionList(){
		if(this == null) return null;
		List<Scenario> scenarios = getScenarioList();
		List<Scenario> sl = new ArrayList<Scenario>();
		HashSet<String> nset = new HashSet<String>();
		for(Scenario sc : scenarios){
			if(sc.getUseState().equals(Scenario.VERSION)){
				String name = sc.getScenarioName();
				if(nset.contains(name)) continue;
				nset.add(name);
				sl.add(sc);
			}
		}
		return sl;
		//Sort by scenario names.
/*		Scenario []arrs = scenarios.toArray(new Scenario[0]);
		Arrays.sort(arrs,new Comparator<Scenario>(){

			@Override
			public int compare(Scenario o1, Scenario o2) {
				
				return o1.getScenarioName().compareTo(o2.getScenarioName());
			}
			
		});		
		List<List<Scenario>> llist = new ArrayList<List<Scenario>>();
		for(int i =  0; i<arrs.length ;i++){
			Scenario sce =arrs[i];
			String name = sce.getScenarioName();
			int j = i>0 ? (i-1):0;
			String prev = arrs[i].getScenarioName();
			if(prev == name) {
				if(llist.size()==0){
					List<Scenario> list =new ArrayList<Scenario>();
					list.add(arrs[i]);
					llist.add(list);
				}else{
					llist.get(llist.size()-1).add(arrs[i]);
				}	
			}else{
				List<Scenario> list = new ArrayList<Scenario>();
				list.add(arrs[i]);
				llist.add(list);
			}
		}
		return llist;*/
	}
	/**
	 * 用于在ProjectAction 中获取SysUser，判断是否对该Project 具有Write权限.
	 * @param b
	 */
	public void setWritePermission(boolean b) {		
		writePermission  = b;
	}
	public boolean getWritePermission(){
		return writePermission;
	}
	
	
	public void solvePermission(SysUser user){
		if(user!=null && user.getUserId() == getCreatorId()){
			setWritePermission(true);
			setPickRolePermission(true);
		}else{
			setWritePermission(false);
			setPickRolePermission(false);
		}
		
		if(user!=null && user.getUserId() != getCreatorId()){
			setJoinPermission(true);
		}else{
			setJoinPermission(false);
		}
		
	}
	public void setPickRolePermission(boolean b){
		pickRolePermission = b;
	}
	public boolean isPickRolePermission(){
		return pickRolePermission;
	}
	public void setJoinPermission(boolean b) {
		joinPermission  = b;
		
	}
	public boolean isJoinPermission(){
		return joinPermission;
	}
	/**
	 * project.jsp页面要显示每个场景的角色，方便用户使用
	 * 问题：
	 *     project.jsp和scenario.jsp页面重复查找 场景与角色关系！！！怎么样保留下前次的结果呢？？ 
	 */
	public void solveScenarioRole() {
		
		for(Scenario scenario:scenarios){
			List<Role> rs  = ProjectService.selectRoleList(scenario);
			scenario.addRole(rs);
		}
	}
	
	public double getRand(){
		return Math.random();
	}
	
}
