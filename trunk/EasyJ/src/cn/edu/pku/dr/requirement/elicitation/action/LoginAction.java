package cn.edu.pku.dr.requirement.elicitation.action;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.Cookie;

import easyJ.common.EasyJException;
import easyJ.database.dao.DAOFactory;
import easyJ.database.dao.Filter;
import easyJ.database.dao.SQLOperator;
import easyJ.database.dao.command.SelectCommand;
import easyJ.http.Globals;
import easyJ.system.data.SysUser;
import easyJ.system.data.SysUserCache;

public class LoginAction extends easyJ.http.servlet.SingleDataAction {
	public LoginAction() {
	}

	public boolean login(SysUser user) throws EasyJException {
		// 这里从LDAP取得用户信息，看是否合法用户。
		SysUser ldpUser = new SysUser();
		// 如果LDAP服务器没有数据，就暂时不做处理，因为可能确实是没有的，但是将来可能根据我们数据库中的数据写入LDAP数据库
		// 本来应该直接抛出异常的，但是现在为了能够继续执行，暂时不抛异常。
		// try {
		// ldpUser = LDAPService.getUserFromLDAP(user.getUserName());
		// } catch (Exception e) {
		//
		// }
		// if (!user.getUserName().equals(ldpUser.getUserName())) {
		// // throw new EasyJException(null,
		// // "cn.dr.requirement.elicitation.action.LoginAction",
		// // user.getUserName() + "的用户名密码错", "用户名密码错");
		// // 暂时不抛异常，给一个admin的用户名和密码
		// // user.setUserName("admin");
		// // user.setPassword("123");
		// }

		// 这里从数据库查询看看用户是否存在。
		String[] accurateProperties = { "userName", "password" };
		dp.setAccurateProperties(accurateProperties);

		ArrayList users = dp.query(user);
		if (users.size() == 0) {
			throw new EasyJException(null,
					"cn.dr.requirement.elicitation.action.LoginAction", user
							.getUserName()
							+ "的用户名密码错", "用户名密码错");
		} else if (!((SysUser) users.get(0)).getPassword().equals(
				user.getPassword())) {

			throw new EasyJException(null,
					"cn.dr.requirement.elicitation.action.LoginAction", user
							.getUserName()
							+ "的用户名密码错", "用户名密码错");
		}

		user = (SysUser) users.get(0);

		/* 将数据写入cookie，用来传给其他的服务。暂时将liufeng06写入cookie */
		String cookie_val = new String("liufeng06:8888");
		// String cookie_val = new String(user.getUserName() + ":" +
		// user.getPassword());
		// cookie_val = (new
		// sun.misc.BASE64Encoder()).encode(cookie_val.getBytes());
		Cookie cookie = new Cookie("_seforge_", cookie_val);
		cookie.setDomain(".seforge.org");
		cookie.setPath("/");
		cookie.setMaxAge(60 * 60 * 24 * 365);
		response.addCookie(cookie);

		// 得到用户拥有权限数据的缓存，并放入session当中。
		userCache = new SysUserCache(user);
		userCache.setAjax(request.getParameter("ajax"));
		request.getSession().setAttribute(Globals.SYS_USER_CACHE, userCache);

		// 用户登陆进来之后进入个人主页。
		// returnPath = "/WEB-INF/AjaxMain.jsp";
		returnPath = "/WEB-INF/AjaxProject.jsp";
		return true;
	}

	public void loginFromEclipse() throws EasyJException {
		user = (SysUser) object;
		SysUser ldpUser = new SysUser();
		String[] accurateProperties = { "userName", "password" };
		dp.setAccurateProperties(accurateProperties);
		ArrayList users = dp.query(user);
		if (users.size() == 0) {
			returnMessage = "failure";
		} else if (!((SysUser) users.get(0)).getPassword().equals(
				user.getPassword())) {
			returnMessage = "failure";
		}
		else {
			returnMessage = "success";
		}

		// System.out.print(request.getParameterValues("userName").toString());

	}

	public boolean login() throws EasyJException {
		user = (SysUser) object;
		if (user.getUserName() == null) {
			user.setUserName("guest");
		}

		return login(user);
	}

	public void logout() throws EasyJException {
		request.getSession().invalidate();
		try {
			response.sendRedirect("/Login.jsp");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void register() throws EasyJException {
		SysUser user = new SysUser();
		user.setUserId(new Long(-1));
		user = (SysUser) dp.get(user);
		userCache = new SysUserCache(user);
		userCache.setAjax(request.getParameter("ajax"));
		request.getSession().setAttribute(Globals.SYS_USER_CACHE, userCache);
		returnPath = "/WEB-INF/AjaxRegister.jsp";
		return;
	}

	public void registerSave() throws EasyJException {
		SelectCommand scmd = DAOFactory.getSelectCommand(object.getClass());
		SysUser registerUser = (SysUser) object;
		registerUser.setUserGroupIds(",-1,");
		registerUser.setUserGroupNames("访客");
		Filter filter = DAOFactory.getFilter("userName", SQLOperator.EQUAL,
				registerUser.getUserName());
		scmd.setFilter(filter);
		ArrayList result = dp.query(scmd);
		if (result.size() != 0) {
			returnMessage = "用户名已存在";
			returnPath = null;
			return;
		} else {
			super.newObject();
			returnMessage = "注册成功";
			returnPath = null;
			return;
		}
	}
}
