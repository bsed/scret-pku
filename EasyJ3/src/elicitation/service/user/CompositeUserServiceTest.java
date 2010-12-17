package elicitation.service.user;

import java.util.HashMap;
import java.util.List;

import com.opensymphony.xwork2.ActionSupport;

import elicitation.model.project.Project;
import elicitation.model.project.Role;
import elicitation.model.user.SysUser;
import junit.framework.TestCase;

public class CompositeUserServiceTest extends TestCase {
	SysUser user = null;;
	Project pro = null;
	Role role = null;
	protected void setUp(){
		user = new SysUser(1,"流散","1234");
		pro = new Project();
		pro.setProjectId(1);
		role = new Role();
		role.setRoleId(2);
		
	}
	public void testRegister() throws Exception{		
		UserService.register(user);
	}
	public void testQuery() throws Exception{
		SysUser rus = null;
		rus = UserService.query(user);
		assertEquals(user,rus);
	}
	public void testEdit() throws Exception{
		
		String res = UserService.edit(user);
		System.err.println(res);
		assertEquals(res, ActionSupport.SUCCESS);
	}
	public void testDelete() throws Exception{
		String res = UserService.delete(user);
		assertEquals(res, ActionSupport.SUCCESS);
	}
	
	public void testUserProjectRole() throws Exception{
		user.setUserId(92);
		List myprojects = UserRoleRelationService.selectProjects(user);
		UserRoleRelationService.insertProjectRole(user, pro, role);
		role.setRoleId(3);
		UserRoleRelationService.insertProjectRole(user, pro, role);
		role.setRoleId(2);
		UserRoleRelationService.deleteProjectRole(user, pro, role);
		System.out.println(myprojects.size());
	}
	protected void tearDown(){
		
	}
}
