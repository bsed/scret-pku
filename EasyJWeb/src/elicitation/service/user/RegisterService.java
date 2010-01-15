package elicitation.service.user;

import java.sql.SQLException;
import java.util.Random;

import elicitation.utils.*;
import com.ibatis.sqlmap.client.SqlMapClient;
import elicitation.model.*;
import elicitation.model.user.SysUser;

/**
 * version 1.0 liudecheng.
 * 
 * @author baipeng Apr 13, 2009 暂时不引入 邮件激活 的功能.
 */
public class RegisterService {

	private static SqlMapClient client = null;

	public static String register(String userName, String passwd, String email) throws Exception
	{
		
			client = Utils.getMapClient();
			SysUser user = null;
			
			user = (SysUser)client.queryForObject("user.getUserByUserName", userName);		
			// 在jsp中,利用Ajax做了用户名验证了.
			if(user==null)
			{
				user = new SysUser();
				user.setUserName(userName);
				user.setPassword(passwd);
				user.setEmail(email);
				
				// String activeCode = activeCode();
				// user.setActiveCode(activeCode);
				//user.setActive(false);
				Object key = client.insert("user.registerUser", user);
				if(key==null)
					return "error";
				return "success";
			}else{
				System.out.println("该用户已经存在！");
				return "error";
			}
				// //TODO 发送 激活邮件到 email中
				//MailService.send(email,passwd,activeCode);
			
			
	}

	/**
	 * length(activecode) = 30 digital ;[0-9]
	 * 
	 * @return
	 */
	private static String activeCode() {
		String activeCode = "";
		Random rand = new Random();
		for (int i = 0; i < 30; i++) {
			activeCode += rand.nextInt(10);
		}
		return activeCode;
	}

	public static void main(String[] args) {
		try{
			RegisterService rs = new RegisterService();
			rs.register("1234tt", "1234", "1234@gmail.com");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
