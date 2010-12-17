package elicitation.model.domain;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.opensymphony.xwork2.ActionSupport;

import elicitation.model.project.Project;
import elicitation.model.user.SysUser;
import elicitation.utils.Utils;

public class DomainService {
	static SqlMapClient client = Utils.getMapClient();
	public static String addDomain(Domain dom) throws SQLException{
		Object primaryKey = client.insert("domain.insertDomain",dom);
		if(primaryKey instanceof Integer){
			int pk = (Integer)primaryKey;
			dom.setDomainId(pk);
			return ActionSupport.SUCCESS;
		}
		return ActionSupport.ERROR;
	}
	public static String hasDomain(Domain dom) throws SQLException{
		Object ob =  client.queryForObject("domain.selectDomain",dom);
		if(ob instanceof Domain){
			Domain res  = (Domain)ob;
			if(res.getDomainId() == dom.getDomainId())
				return ActionSupport.SUCCESS;
		}
		return ActionSupport.ERROR;
	}
	public static List<Domain> selectDomainList(SysUser user) throws SQLException{
		return client.queryForList("domain.selectUserDomainList",user);
	}
	public static List<Domain> selectDomainList() {
		List dms = null;
		try{
			dms = client.queryForList("domain.selectDomainList");
		}catch(SQLException e){
			e.printStackTrace();
		}
		return dms;
	}
	public static String editDomain(Domain dom) throws SQLException{
		int rows = client.update("domain.updateDomain",dom);
		if(rows == 1){
			return ActionSupport.SUCCESS;
		}else{
			return ActionSupport.ERROR;
		}
	}
	public static String deleteDomain(Domain dom) throws SQLException{
		int rows = client.delete("domain.deleteDomain",dom);
		if(rows == 1){
			return ActionSupport.SUCCESS;
		}
		return ActionSupport.ERROR;
	}
	public static String getProjects(Domain dom)throws SQLException{
		List projects  = client.queryForList("domain.selectProjects",dom);
		Iterator<Project> ite = projects.iterator();
		while(ite.hasNext()){
			Project pro = ite.next();
			dom.addProject(pro);
		}
		return ActionSupport.SUCCESS;		
	}
	public static Domain selectDomain(Domain dom) {

	
		try {
			return (Domain)client.queryForObject("domain.selectDomain",dom);
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return null;
	}
}
