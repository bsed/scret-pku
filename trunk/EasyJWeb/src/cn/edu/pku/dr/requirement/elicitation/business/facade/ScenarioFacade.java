package cn.edu.pku.dr.requirement.elicitation.business.facade;

import cn.edu.pku.dr.requirement.elicitation.data.*;
import easyJ.business.proxy.SingleDataProxy;
import easyJ.common.EasyJException;

public class ScenarioFacade {
    private static SingleDataProxy sdp = SingleDataProxy.getInstance();

    public ScenarioFacade() {}

    public void saveDescription(Description description) throws EasyJException {
        if (description.getDescriptionId() == null) {
            // 在dp中除了要
            sdp.create(description);
        } else {
            sdp.update(description);
        }
    }
}
