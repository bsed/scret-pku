package elicitation.service.request;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.opensymphony.xwork2.ActionSupport;

import elicitation.model.project.Project;
import elicitation.model.project.Role;
import elicitation.model.project.Scenario;
import elicitation.model.request.Request;
import elicitation.model.user.SysUser;
import elicitation.utils.Utils;

/**
 * 
 * @author John
 * 与数据库交互获取相关请求信息
 */
public class RequestService {
	public static SqlMapClient client = Utils.getMapClient();
	public static List<Request> selectRequests(Project pro){
		List<Request> reqs = new ArrayList<Request>();
		try{
			reqs = client.queryForList("request.selectProjectRequests",pro);
			for(int i = 0 ;i<reqs.size() ;i++){
				Request req = reqs.get(i);
				SysUser user = (SysUser) client.queryForObject("user.getUserByUserID",req.getRequestUser().getUserId());
				req.setRequestUser(user);
				req.setRequestProject(pro);
				List<Role> roles = new ArrayList<Role>();
				for(Role role:req.getRequestRoles()){					
					role =(Role)client.queryForObject("project.selectRole",role);
					roles.add(role);
				}
				req.setRequestRoleList(roles);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return reqs;
	}
	
	public static String approveRequest(Request req) {
		try{
			//update table req_join_scenario
			client.update("request.approveRequest",req);
			//insert record to table user_project_role
			client.insert("request.insertUserProjectRole",req);
		}catch(Exception e){
			e.printStackTrace();
			return ActionSupport.ERROR;
		}
		return ActionSupport.SUCCESS;
	}	
	public static String rejectRequest(Request req){
		try{
			client.update("request.rejectRequest",req);
		}catch(Exception e){
			e.printStackTrace();
			return ActionSupport.ERROR;
		}
		return ActionSupport.SUCCESS;
	}
}
