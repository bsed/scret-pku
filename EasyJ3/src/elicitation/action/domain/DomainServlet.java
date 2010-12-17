package elicitation.action.domain;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.opensymphony.xwork2.ActionSupport;

import elicitation.model.domain.Domain;
import elicitation.model.domain.DomainService;
import elicitation.model.user.SysUser;
/*
 * Add Domain servlet.
 */
public class DomainServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	@Override
	protected void doPost(HttpServletRequest request,HttpServletResponse response){
		String name = (String) request.getParameter("domainName");
		String des  = (String) request.getParameter("domainDescription");
		SysUser user = (SysUser) request.getSession().getAttribute("user");
		//Debug 
		if(user == null) {
			user = new SysUser();
			user.setUserId(98);
		}
		//Debug end.
		String ans = "";
		try {
			 ans = DomainService.addDomain(new Domain(name,des,user.getUserId()));
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
