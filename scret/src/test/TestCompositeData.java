package test;

import cn.edu.pku.dr.requirement.elicitation.data.Scenario;
import easyJ.business.facade.CompositeDataFacade;

public class TestCompositeData {
    public TestCompositeData() {}

    public static void main(String[] args) {
        try {
            CompositeDataFacade cdf = new CompositeDataFacade();
            Scenario scenario = new Scenario();
            scenario.setScenarioId(new Long(1));
            scenario = (Scenario) cdf.get(scenario);
            int a = 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
