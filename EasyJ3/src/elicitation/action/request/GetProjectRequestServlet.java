package elicitation.action.request;

import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.opensymphony.xwork2.ActionSupport;

import elicitation.model.project.Project;
import elicitation.model.request.Request;
import elicitation.service.request.RequestService;

public class GetProjectRequestServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) {
		try{
			int projectId = Integer.valueOf(request.getParameter("projectId"));
			Project pro = new Project();
			pro.setProjectId(projectId);
			List<Request> reqs = RequestService.selectRequests(pro);
			int i=  0 ;
			String ans = "";
			for(Request req:reqs){
				int id = req.getRequestId();
				String name = req.getRequestUser().getUserName();
				String des = req.getRequestDescription();
				
				String role = req.getRequestRoles().get(0).getRoleName();
				ans += "[" + (i++)	+ ",'"+name+"','"+des+"','"+role+"',"+id+"],";
			}
			if(ans.length() > 2) ans = ans.substring(0,ans.length() -1 );
			ans  = "["+ans+"]";
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().write(ans);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
}
