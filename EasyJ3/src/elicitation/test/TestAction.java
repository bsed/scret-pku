package elicitation.test;

import com.opensymphony.xwork2.ActionSupport;

public class TestAction extends ActionSupport {
	private String userName;
	public void setUserName(String name){
		this.userName = name;
	}
	public String execute() throws Exception{
		System.out.println(userName);
		return ActionSupport.SUCCESS;
	}

}
