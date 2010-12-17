package elicitation.action.user;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import elicitation.model.user.SysUser;
import elicitation.service.user.LoginService;
import elicitation.service.user.UserService;

public class LoginServlet extends HttpServlet {
	@Override
	protected void	doGet(HttpServletRequest request,HttpServletResponse response) {
		
		String name = request.getParameter("userName");
		String password = request.getParameter("password");
		int userId = -100;
		try {
			userId = LoginService.login(name, password);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String msg="";
		if(userId  == LoginService.LOGIN_ERROR){
			msg ="{success:false}";
		}else{
			msg = "{success:true}";
		}
		SysUser user = new SysUser(userId,name,password);
		HttpSession session = request.getSession();
		session.setAttribute("user", user);
		try {
			response.getWriter().write(msg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
