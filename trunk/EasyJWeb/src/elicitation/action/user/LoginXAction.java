package elicitation.action.user;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.opensymphony.xwork2.ActionSupport;

import elicitation.utils.Utils;

public class LoginXAction extends ActionSupport {
	private String userName;
	private String password;
	private String url = "cn.edu.pku.dr.requirement.elicitation.action.LoginAction.do?ACTION=login&easyJ.http.Globals.CLASS_NAME=easyJ.system.data.SysUser";
	public String getUrl(){
		return url;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String execute() throws Exception {
		//TODO  检测username and password，如果出错的话，返回error，交由struts2.0进行处理;
		// 如果正确的话，就会redirect到 EasyJ的原框架进行处理.
		SqlMapClient client = Utils.getMapClient();
		Object user = client.queryForObject("user.getUserByUserName",userName);
		if(user == null){
			System.out.println(userName+" 不存在");
			return ActionSupport.ERROR;
		}else{
			System.out.println(userName+"正确");
		}
		url += "&userName="+userName+"&password="+password;
		System.out.println(url);
		return ActionSupport.SUCCESS;
	}
}
