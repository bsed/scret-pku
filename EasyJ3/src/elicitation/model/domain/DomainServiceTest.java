package elicitation.model.domain;

import java.sql.SQLException;
import java.util.List;

import com.opensymphony.xwork2.ActionSupport;

import junit.framework.TestCase;

public class DomainServiceTest extends TestCase {

	static Domain dom = new Domain("电子政务","电子政务描述",1);
	protected void setUp(){
				
	}
	public void testAddDomain() throws SQLException {
		String res = DomainService.addDomain(dom);
		assertEquals(ActionSupport.SUCCESS,res);
		System.out.println("Id="+dom.getDomainId());
	}

	public void testSelectDomain() throws SQLException {
		
		System.out.println("Select ID="+dom.getDomainId());
		String actual  = DomainService.hasDomain(dom);
		String expected  =ActionSupport.SUCCESS;
		//assertEquals(expected, actual);
		
		List doms = DomainService.selectDomainList();
		System.out.println("Domain nums = "+doms.size());
		
	}

	public void testEditDomain() throws SQLException {		
		String actual  =DomainService.editDomain(dom);
		String expected = ActionSupport.SUCCESS;
		assertEquals(expected, actual);
	}

	public void testDeleteDomain() throws SQLException {
		String actual  =DomainService.deleteDomain(dom);
		String expected = ActionSupport.SUCCESS;
		assertEquals(expected, actual);
	}
	
	public void testSelectProjects() throws SQLException{
		dom.setDomainId(8);
		String actual = DomainService.getProjects(dom);
		String expected = ActionSupport.SUCCESS;
		assertEquals(expected, actual);
		System.out.println("Num="+dom.getProjects().size());
	}

}
