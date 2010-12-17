package elicitation.action.role;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import elicitation.model.project.Project;
import elicitation.model.project.ProjectService;
import elicitation.model.project.Role;
import elicitation.model.user.SysUser;

public class PickRole extends HttpServlet {
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) {
		int roleId = Integer.valueOf(request.getParameter("roleId"));
		Role role = new Role();
		role.setRoleId(roleId);
		
		int projectId = Integer.valueOf(request.getParameter("projectId"));
		Project pro  =new Project();
		pro.setProjectId(projectId);
		
		int isCreator = Integer.valueOf(request.getParameter("isCreator"));
		SysUser user = (SysUser)request.getSession().getAttribute("user");
		try{
			ProjectService.pickRole(user, role, pro, isCreator);
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().write("{success:true}");
		}catch (Exception e){
			e.printStackTrace();
		}
		
	}

}
