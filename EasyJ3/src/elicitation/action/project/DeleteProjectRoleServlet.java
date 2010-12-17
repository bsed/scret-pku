package elicitation.action.project;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.opensymphony.xwork2.ActionSupport;

import elicitation.model.project.ProjectService;
import elicitation.model.project.Role;

public class DeleteProjectRoleServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			int rid = Integer.valueOf(request.getParameter("roleId"));
			Role role = new Role();
			role.setRoleId(rid);
			String a = ProjectService.deleteRole(role);
			if (!ActionSupport.SUCCESS.equals(a)) {
				System.out.println("Delete ROLE -" + rid + " FAILED@@");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
