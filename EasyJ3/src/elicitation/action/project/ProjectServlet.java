package elicitation.action.project;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.cglib.transform.impl.AddDelegateTransformer;

import com.opensymphony.xwork2.ActionSupport;

import elicitation.model.domain.Domain;
import elicitation.model.project.Project;
import elicitation.model.project.ProjectService;
import elicitation.model.user.SysUser;
import elicitation.model.user.CompositeUser.UserProjectRole;
import elicitation.service.user.UserRoleRelationService;

public class ProjectServlet extends HttpServlet{
	/**
	 * Add project Servlet.
	 */
	protected void doPost(HttpServletRequest request,HttpServletResponse response)  {
		String projectName = request.getParameter("projectName");
		String projectDescription = request.getParameter("projectDescription");
		SysUser user = (SysUser) request.getSession().getAttribute("user");
		//Debug
		if(user == null){
			user = new SysUser();
			user.setUserId(98);
		}
//		Domain domain  = (Domain)getServletContext().getAttribute("domain");
//		if(domain ==null){
//			
//			domain = new Domain();
//			domain.setDomainId(-1);//PUBLIC DOMAIN
//		}
		int domainId = -1 ;  //Default Domain:其他领域
			domainId = Integer.valueOf(request.getParameter("domainId"));
		
		//Debug
		Project project = new Project();
		project.setCreatorId(user.getUserId());
		project.setDomainId(domainId);
		project.setProjectName(projectName);
		project.setProjectDescription(projectDescription);
		String ans = "";
		String res = "";
		try{
			ans = ProjectService.addProject(project);
		}catch(Exception e){
			e.printStackTrace();
		}
		if(ans.equals(ActionSupport.SUCCESS)){
			res = "{success:true}";
		}else{
			res = "{success:false}";
		}
		try{
		//同时需要添加记录到table user_project_role中
			ans = UserRoleRelationService.insertProjectUser(user, project);
		}catch(Exception e){
			e.printStackTrace();
		}
		if(res.equals("{success:true}") && ans.equals(ActionSupport.SUCCESS)){
		
			res = "{success:true}";
		}else{
			res = "{success:false}";
		}
		try{
			response.getWriter().write(res);
		}catch(IOException ioe){
			ioe.printStackTrace();
		}
		
		
	}

}
