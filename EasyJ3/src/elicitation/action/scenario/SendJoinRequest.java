package elicitation.action.scenario;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.opensymphony.xwork2.ActionSupport;

import elicitation.model.project.ProjectService;
import elicitation.model.user.SysUser;

public class SendJoinRequest extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			int projectId = Integer
					.valueOf(request.getParameter("projectId"));
			int roleId = Integer.valueOf(request.getParameter("roleId"));
			
			String req_des = request.getParameter("req_description");
			SysUser user = (SysUser) request.getSession().getAttribute("user");
			if (user == null) {
				user = new SysUser();
				user.setUserId(92);
			}
			String res = ProjectService.sendJoinRequest(projectId,roleId,user,req_des);
			String ans = "";
			if(ActionSupport.SUCCESS.equals(res)){
				ans = "{success:true}";
			}else{
				ans = "{success:false}";
			}
			response.getWriter().write(ans);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
