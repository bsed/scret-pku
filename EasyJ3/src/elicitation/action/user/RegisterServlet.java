package elicitation.action.user;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.opensymphony.xwork2.ActionSupport;

import elicitation.model.user.SysUser;
import elicitation.service.user.LoginService;
import elicitation.service.user.UserService;

public class RegisterServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String name = request.getParameter("userName");
		String password = request.getParameter("password");
		SysUser user = new SysUser();
		user.setUserName(name);
		user.setPassword(password);
		String msg = "";
		try {
			String rcode = UserService.register(user);
			if(rcode.equals(ActionSupport.ERROR)){
				msg = "{success:false}";
			}else{
				msg = "{success:true}";
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		response.getWriter().write(msg);
	}
}
