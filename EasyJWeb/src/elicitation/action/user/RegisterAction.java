package elicitation.action.user;

import com.opensymphony.xwork2.ActionSupport;

import elicitation.service.user.RegisterService;

public class RegisterAction extends ActionSupport {

	private String userName;
	private String passwd;
	private String confirmpw;
	private String email;
	

	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public String getConfirmpw() {
		return confirmpw;
	}

	public void setConfirmpw(String confirmpw) {
		this.confirmpw = confirmpw;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}


	public String register() throws Exception {
		System.out.println("开始注册");
		String sr = RegisterService.register(userName, passwd, email);
		if (sr != null && sr.equals("error")) {
			addActionError("注册失败,请仔细检查各项,并重新输入! ");
		}
		System.out.println("Register = " + sr);
		return sr;
	}
	public String execute() throws Exception{
		System.out.println("registerAction");
		return ActionSupport.SUCCESS;
	}

}
