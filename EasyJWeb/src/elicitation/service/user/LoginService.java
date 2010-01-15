package elicitation.service.user;



import com.opensymphony.xwork2.ActionContext;

import elicitation.model.user.SysUser;
import elicitation.utils.*;
import com.ibatis.sqlmap.client.SqlMapClient;
/**
 * version 1.0 liudecheng. method: login.
 * @author baipeng
 * Apr 13, 2009
 */
public class LoginService {
	public static int LOGIN_ERROR = -1 ;
	public static int VALIDATE_FAIL = -2 ;
	public static int VALIDATE_OK  =  0 ;
	public static int login(String username, String passwd) throws Exception
	{
		SqlMapClient client = Utils.getMapClient();
		SysUser user = null;		
		user = (SysUser)client.queryForObject("user.getUserByUserName", username);		
		if(user == null) return LOGIN_ERROR;		
		if(user.getPassword().equals(passwd))
		{
			ActionContext.getContext().getSession().put("user", user);
			return user.getUserId().intValue();
		}
		else return LOGIN_ERROR;
	}
	public static int validateName(String username)throws Exception{
		SqlMapClient client = Utils.getMapClient();
		SysUser user = null; 
		user = (SysUser)client.queryForObject("user.getUserByUserName",username);
		if(user != null) return VALIDATE_FAIL;
		return VALIDATE_OK;
	}
	public static void main(String []args){
		try{
			int r = login("baipeng","baipeng");
			if(r == LOGIN_ERROR){
				System.out.println("Login Failed");
			}else{
				System.out.println("OK");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
