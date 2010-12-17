package elicitation.model.project;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.opensymphony.xwork2.ActionSupport;

import elicitation.utils.Utils;

public class CompositeScenarioRoleDataService {
	static SqlMapClient client = Utils.getMapClient();
	public static List selectScenarioData(Scenario sce) throws Exception{
		List sd = client.queryForList("project.selectScenarioData",sce);
		return sd;
	}
	public static String insertScenarioData(Scenario sce,Data data) throws Exception{
		HashMap para = new HashMap();
		para.put("scenarioId", sce.getScenarioId());
		para.put("dataId", data.getDataId());		
		Object primaryKey = client.insert("project.insertScenarioData",para);
		if(primaryKey == null) return ActionSupport.ERROR;
		return ActionSupport.SUCCESS;
	}
	public static String deleteScenarioData(Scenario sce,Data data) throws Exception{
		HashMap para = new HashMap();
		para.put("scenarioId", sce.getScenarioId());
		para.put("dataId", data.getDataId());
		int rows = client.delete("project.deleteScenarioData",para);
		if(rows == 0) return ActionSupport.ERROR;
		return ActionSupport.SUCCESS;
	}
	public static List selectScenarioRole(Scenario sce) throws SQLException{
		List res = client.queryForList("project.selectScenarioRole",sce);
		return res;
	}
	public static String insertScenarioRole(Scenario sce,Role role) throws SQLException{
		HashMap para = new HashMap();
		para.put("scenarioId", sce.getScenarioId());
		para.put("roleId",role.getRoleId());
		Object primary = client.insert("project.insertScenarioRole",para);
		if(primary == null) return ActionSupport.ERROR;
		return ActionSupport.SUCCESS;
	}
	public static String deleteScenarioRole(Scenario sce,Role role) throws SQLException{
		HashMap para = new HashMap();
		para.put("scenarioId", sce.getScenarioId());
		para.put("roleId",role.getRoleId());
		int rows = client.delete("project.deleteScenarioRole",para);
		if(rows == 0) return ActionSupport.ERROR;
		return ActionSupport.SUCCESS;
	}
	public static String selectScenarioRoleDes(Scenario sce,Role role) throws SQLException{
		HashMap para = new HashMap();
		para.put("scenarioId", sce.getScenarioId());
		para.put("roleId",role.getRoleId());
		String des = (String)client.queryForObject("project.selectScenarioRoleDes",para);
		return des;
	}
	public static String insertScenarioRoleDes(Scenario sce, Role role,String description) throws SQLException{
		HashMap para = new HashMap();
		para.put("scenarioId", sce.getScenarioId());
		para.put("roleId",role.getRoleId());
		para.put("description", description);
		Object primaryKey = client.insert("project.insertScenarioRoleDes",para);
		if(primaryKey == null) return ActionSupport.ERROR;
		return ActionSupport.SUCCESS;
		}
	public static String deleteScenarioRoleDes(Scenario sce,Role role) throws SQLException{
		HashMap para = new HashMap();
		para.put("scenarioId", sce.getScenarioId());
		para.put("roleId",role.getRoleId());
		int rows = client.delete("project.deleteScenarioRoleDes",para);
		if(rows == 0) return ActionSupport.ERROR;
		return ActionSupport.SUCCESS;
	}
		
}
