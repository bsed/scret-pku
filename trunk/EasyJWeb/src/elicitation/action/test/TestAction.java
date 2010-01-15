package elicitation.action.test;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.opensymphony.xwork2.ActionSupport;

import elicitation.utils.Utils;

public class TestAction extends ActionSupport {
	private String userName;
	public void setUserName(String name){
		this.userName = name;
	}
	public String execute() throws Exception{
		System.out.println(userName);
		return ActionSupport.SUCCESS;
	}
	public static void main(String []args)throws Exception{
		SqlMapClient client = Utils.getMapClient();
		Object user = client.queryForObject("user.getUserByUserName","admin");
		
	}

}
