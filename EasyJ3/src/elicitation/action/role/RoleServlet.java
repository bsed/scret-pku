package elicitation.action.role;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.opensymphony.xwork2.ActionSupport;

import elicitation.model.project.Project;
import elicitation.model.project.ProjectService;
import elicitation.model.project.Role;
import elicitation.model.user.SysUser;

public class RoleServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest request,HttpServletResponse response){
		String name = (String) request.getParameter("roleName");
		String des  = (String) request.getParameter("roleDescription");
		SysUser user = (SysUser) request.getSession().getAttribute("user");
		//Debug 
		if(user == null) {
			user = new SysUser();
			user.setUserId(98);
		}
		int projectId = Integer.valueOf(request.getParameter("projectId")); 
		Role role = new Role();
		role.setRoleName(name);
		role.setRoleDes(des);
		role.setProjectId(projectId);
		role.setCreatorId(user.getUserId());
		//Debug end.
		String ans = "";
		try {
			 //ans = DomainService.addDomain(new Domain(name,des,user.getUserId()));
			ans = ProjectService.addRole(role);
		} catch (SQLException e) {		
			e.printStackTrace();
		}
		
		String res = "";
		if(ans.equals(ActionSupport.ERROR)){
			res ="{success:false}";
		}else {
			res = "{success:true}";
		}

		try {
			response.getWriter().write(res);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
