package elicitation.action.user;


import com.opensymphony.xwork2.ActionSupport;

import elicitation.service.user.LoginService;
/**
 * 
 * @author baipeng
 * Apr 13, 2009
 */
public class ValidateNameAction extends ActionSupport{
	private String userName;
	private String tip;
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getTip() {
		return tip;
	}
	public void setTip(String tip) {
		this.tip = tip;
	}
	public String execute() throws Exception{
		int iv = LoginService.validateName(userName);
		if(iv == LoginService.VALIDATE_FAIL){
			tip = "对不起,该用户名已被注册!";
		}else{
			tip = "恭喜,用户名可用!";
		}
		
		return super.SUCCESS;
	}
}
