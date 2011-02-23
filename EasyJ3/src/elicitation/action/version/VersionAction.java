package elicitation.action.version;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

import elicitation.model.project.ProjectService;
import elicitation.model.project.Scenario;
import elicitation.model.project.VersionService;

public class VersionAction implements Action {
	private Scenario scenario = new Scenario();
	private List<Scenario> historyVersion = null;
	public void setScenarioName(String name){
		/*try{
		byte[] bs = name.getBytes("iso-8859-1");
		name = new String(bs,"utf-8");
		}catch(Exception e){
			e.printStackTrace();			
		}*/
		scenario.setScenarioName(name);
	}
	public void setScenarioId(String sid){
		int id = Integer.valueOf(sid);
		scenario.setScenarioId(id);
	}
	public String exe_historyVersion() throws Exception {
		System.out.println("---------------come into VersionAction#exe_historyVersion");
		System.out.println(scenario.getScenarioName() + scenario.getScenarioId());
		historyVersion = VersionService.selectScenarioVersion(scenario.getScenarioName());
		return ActionSupport.SUCCESS;	
		
	}
	
	public List<Scenario> getHistoryVersion(){
		System.out.println("getHistoryVersion");
		for(Scenario sce:historyVersion){
			System.out.println(sce.getScenarioId());
		}
		return historyVersion;
	}
	public Scenario getRootScenario() {
		return ProjectService.selectRootScenario(scenario);
	}
	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
