package elicitation.action.scenario;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.opensymphony.xwork2.ActionSupport;

import elicitation.model.project.VersionService;

public class MakeVersionServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request,HttpServletResponse response){
		try{
		int scenarioId = Integer.valueOf(request.getParameter("scenarioId"));
		String ans = VersionService.makeVersion(scenarioId);
		String res = "{success:true}";
		if(ActionSupport.ERROR.equals(ans)){
			res = "{success:false}";
		}
		response.getWriter().write(res);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
