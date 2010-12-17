package elicitation.action.project;

import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.validation.JSONValidationInterceptor;

import elicitation.model.project.Project;
import elicitation.model.project.ProjectService;
import elicitation.model.project.Role;

public class ProjectRoleServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) {
		try{
		 
			Project project = new Project();
			int projectId = Integer.valueOf(request.getParameter("projectId"));
			project.setProjectId(projectId);
			List<Role> rolelist = ProjectService.selectRoleList(project);
			String ans = "";
			int i = 0;
			for (Role role : rolelist) {
				ans += "['" +(i++)+"','"+ role.getRoleName() + "','" + role.getRoleDes()
						+"',"+role.getRoleId()+ "],";
			}
			if(ans.length()>2)
				ans = ans.substring(0,ans.length()-1);
			ans = "[" + ans + "]";
			System.out.println("ans=" + ans);
			
			response.setContentType("application/x-javascript; charset=UTF-8");
			ans = ans.replace('\n', ' ');
			response.getWriter().write(ans);
			response.getWriter().close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
