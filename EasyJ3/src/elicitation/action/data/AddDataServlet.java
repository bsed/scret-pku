package elicitation.action.data;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.opensymphony.xwork2.ActionSupport;

import elicitation.model.project.Data;
import elicitation.model.project.ProjectService;

public class AddDataServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String dataName = request.getParameter("dataName");
			String dataDes = request.getParameter("dataDescription");			
			int projectId = Integer.valueOf(request.getParameter("projectId"));
			Data data = new Data();
			data.setDataName(dataName);
			data.setDataDes(dataDes);
			data.setProjectId(projectId);
			
			String ans = "";
			String res = ProjectService.addData(data);
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
