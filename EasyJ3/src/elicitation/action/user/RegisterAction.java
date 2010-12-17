package elicitation.action.user;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import elicitation.model.user.SysUser;
import elicitation.service.user.UserService;

public class RegisterAction extends ActionSupport implements ModelDriven<SysUser>{

	private SysUser user;

	
	
	public SysUser getModel(){
		if(user == null)
			user= new SysUser();
		return user;
	}
	public String register() throws Exception {
		System.out.println("开始注册");
		String sr = UserService.register(user);
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
