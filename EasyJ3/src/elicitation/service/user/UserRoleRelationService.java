package elicitation.service.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.opensymphony.xwork2.ActionSupport;

import elicitation.model.project.Project;
import elicitation.model.project.Role;
import elicitation.model.user.SysUser;
import elicitation.utils.Utils;
/**
 * 
 * @author John
 * TODO 利用 role join user_project_role 可以直接生成 role.
 * 可以帮助改进下列几个 查询的效率。
 */
public class UserRoleRelationService {
	public static List<Role> selectUserRoleInProject(SysUser user,Project pro){
		try{
			List<Role> rs = new ArrayList<Role>();
			SqlMapClient client = Utils.getMapClient();
			List<HashMap<String, Integer>> plist = client.queryForList("user.selectProjects",user);// List<HashMap>
			int p_id = pro.getProjectId();
			for(HashMap<String, Integer> map :plist){
				int pid= map.get("project_id");
				int isCreator = map.get("is_creator");
				int rid = map.get("role_id");
				if(pid== p_id ){
					Role role = new Role();
					role.setRoleId(rid);
					rs.add(role);
				}
			}
			return rs;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	public static List<Project> selectUserProjects(SysUser user){
		try{
			List<Project> ps = new ArrayList<Project>();
			SqlMapClient client = Utils.getMapClient();
			List<HashMap<String, Integer>> plist = client.queryForList("user.selectProjects",user);// List<HashMap>
			for(HashMap<String, Integer> map :plist){
				int pid= map.get("project_id");
				int isCreator = map.get("is_creator");
				int rid = map.get("role_id");
				Project pro = new Project();
				pro.setProjectId(pid);
				ps.add(pro);
			}
		return ps;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 取得与user关联的工程.
	 * Table:user_project_role
	 * SQL:select * from user_project_role where user_id = #userId#
	 * @param user
	 */
	public static List selectProjects(SysUser user){
		try{
			SqlMapClient client = Utils.getMapClient();
			List myProjects = client.queryForList("user.selectProjects",user);// List<HashMap>
		return myProjects;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 
	 * @param user -- creator of the project.
	 * @param pro
	 * @return
	 */
	public static String insertProjectUser(SysUser user,Project pro) throws Exception{
		SqlMapClient client = Utils.getMapClient();
		HashMap params = new HashMap();
		params.put("userId", user.getUserId());
		params.put("projectId", pro.getProjectId());
		params.put("isCreator", 1);
		Object primaryKey = client.insert("user.insertProjectUser",params);
		if(primaryKey == null){
			return ActionSupport.ERROR;
		}
		return ActionSupport.SUCCESS;
	}
	public static String insertProjectRole(SysUser user,Project pro,Role role) throws Exception{
		SqlMapClient client= Utils.getMapClient();
		HashMap params = new HashMap();
		params.put("userId", user.getUserId());
		params.put("projectId", pro.getProjectId());
		params.put("roleId", role.getRoleId());
		params.put("isCreator", 0);
		Object primaryKey = client.insert("user.insertUserProjectRole",params);
		if(primaryKey == null){
			return ActionSupport.ERROR;			
		}		
		return ActionSupport.SUCCESS;
	}
	public static String deleteProjectRole(SysUser user,Project pro,Role role) throws Exception{
		SqlMapClient client = Utils.getMapClient();
		HashMap params = new HashMap();
		params.put("userId", user.getUserId());
		params.put("projectId", pro.getProjectId());
		params.put("roleId", role.getRoleId());
		params.put("isCreator", 0);
		int rows = client.delete("user.deleteUserProjectRole",params);
		return ActionSupport.SUCCESS;
	}
	/**
	 * 没有直接更新关系的操作；
	 * 只允许用户查询、增加、删除.
	 */
}
