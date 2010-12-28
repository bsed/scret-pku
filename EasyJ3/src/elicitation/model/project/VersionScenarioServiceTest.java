package elicitation.model.project;

import java.util.List;

import junit.framework.TestCase;

public class VersionScenarioServiceTest extends TestCase {

	public void testVoteConfirm() {
		VersionService.voteConfirm(1, 1,1);
	}
	public void testVoteRate(){
		float a = VersionService.getVoteRate(116, 59);		
		System.out.println(a);
	}
	public void testMakeVersion(){
		String s = VersionService.makeVersion(95);
		System.out.println(s);
	}
	public void testSelectScenarioVersion(){
		
		List<Scenario> scenarios = VersionService.selectScenarioVersion("设计调查问卷");
		for(Scenario sce:scenarios){
			System.out.println(sce.getScenarioName()+" Id="+sce.getScenarioId()+" buildTime="+sce.getBuildTime());
		}
	}
	public void testSelectPreVersion(){
		System.out.println("=================select pre version begins");
		Scenario sce = new Scenario();
		sce.setScenarioId(127);
		sce = VersionService.selectPreVersion(sce);
		System.out.println(sce.getScenarioName()+" Id="+sce.getScenarioId()+" buildTime="+sce.getBuildTime());
		System.out.println("=================select pre version ends");
	}
	public void testSelectNextVersion(){
		System.out.println("=================select next version begins");
		Scenario sce = new Scenario();
		sce.setScenarioId(116);
		sce = VersionService.selectNextVersion(sce);
		System.out.println(sce.getScenarioName()+" Id="+sce.getScenarioId()+" buildTime="+sce.getBuildTime());
		System.out.println("=================select next version ends");
	}

}