package elicitation.service.user;

import java.sql.SQLException;
import java.util.Random;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.opensymphony.xwork2.ActionSupport;

import elicitation.model.user.SysUser;
import elicitation.utils.Utils;

/**
 * version 1.0 liudecheng.
 * 
 * @author baipeng Apr 13, 2009 暂时不引入 邮件激活 的功能.
 */
public class UserService {

	private static SqlMapClient client = null;

	public static String edit(SysUser user) throws Exception{
		client = Utils.getMapClient();
		int rowsAffected = client.update("user.updateUser",user);
		if(rowsAffected>0) return ActionSupport.SUCCESS;
		else return ActionSupport.ERROR;
	}
	public static SysUser selectUserByID(SysUser user) {
		client = Utils.getMapClient();
		try{
			user = (SysUser)client.queryForObject("user.getUserByUserID",user.getUserId());
		}catch(SQLException e){
			e.printStackTrace();
		}
		return user;
	}
	public static SysUser query(SysUser user) throws Exception{
		client = Utils.getMapClient();
		SysUser user2 = (SysUser)client.queryForObject("user.getUserByUserName",user.getUserName());
		return user2;
	}
	public static String delete(SysUser user) throws Exception{
		client = Utils.getMapClient();
		int rowsA = client.delete("user.deleteUser",user);
		if(rowsA>0) return ActionSupport.SUCCESS;
		return ActionSupport.ERROR;
	}
	public static String register(SysUser user) throws Exception{

		client = Utils.getMapClient();
		
		SysUser tmp = (SysUser)client.queryForObject("user.getUserByUserName", user.getUserName());		
		// 在jsp中,利用Ajax做了用户名验证了.
		if(tmp==null)
		{			
			Object key = client.insert("user.registerUser", user);
			if(key==null)
				return ActionSupport.ERROR;
			if(key instanceof Integer){
				int pk = (Integer)key;
				user.setUserId(pk);
				return ActionSupport.SUCCESS;
			}
			if(key instanceof Long){
				int pk = ((Long)key).intValue();
				user.setUserId(pk);
				return ActionSupport.SUCCESS;
			}
			return ActionSupport.ERROR;
		}else{
			System.out.println("该用户已经存在！");
			return ActionSupport.ERROR;
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
}
