package elicitation.action.user;

import java.util.Enumeration;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sun.jmx.snmp.EnumRowStatus;

public class LogOutServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request,HttpServletResponse response){
		try{
		/*Enumeration<String> e = getServletContext().getAttributeNames();
		while(e.hasMoreElements()){
			String name = e.nextElement();
			getServletContext().removeAttribute(name);			
		}*/
		HttpSession session = request.getSession();
		session.removeAttribute("user");
		session.invalidate();
		String path = request.getContextPath();
		response.setHeader("Pragma","No-cache");  
		response.setHeader("Cache-Control","no-cache"); 
		response.setHeader("Cache-Control", "no-store"); 
		response.setDateHeader("Expires", 0); 
		/*
		 * 加入随机数，保证及时更新.
		 * */
		response.sendRedirect(path+"/action_domainList.do?start="+Math.random());
		
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
