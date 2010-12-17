package elicitation.action.project;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.opensymphony.xwork2.ActionSupport;

import elicitation.model.project.Data;
import elicitation.model.project.Project;
import elicitation.model.project.ProjectService;
import elicitation.model.user.SysUser;

public class DataServlet extends HttpServlet{
	protected void doGet(HttpServletRequest request,HttpServletResponse response){
		String dataName= request.getParameter("dataName");
		String dataDescription = request.getParameter("dataDescription");
		SysUser user = (SysUser)request.getSession().getAttribute("user");
		if(user == null){
			user = new SysUser();
			user.setUserId(98);
		}
		Project project = (Project)getServletContext().getAttribute("project");
		if(project ==null){
			project = new Project();
			project.setProjectId(98);
		}
		Data data = new Data();
		data.setDataName(dataName);
		data.setDataDes(dataDescription);
		data.setCreatorId(user.getUserId());
		data.setProjectId(project.getProjectId());
		String ans = "";
		try{
			ans = ProjectService.addData(data);
		}catch(Exception e){
			e.printStackTrace();
		}
		String res = "";
		if(ans.equals(ActionSupport.SUCCESS)){
			res = "{success:true}"; 
		}else{
			res = "{success:false}";
		}
		try{
			response.getWriter().write(res);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
