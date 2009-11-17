package cn.edu.pku.dr.requirement.elicitation.action;

import easyJ.common.EasyJException;
import java.io.IOException;
import cn.edu.pku.dr.requirement.elicitation.data.Ambiguity;
import cn.edu.pku.dr.requirement.elicitation.business.proxy.AmbiguityProxyFactory;
import cn.edu.pku.dr.requirement.elicitation.data.AmbiguityTypeValue;
import cn.edu.pku.dr.requirement.elicitation.data.AmbiguityEvaluation;

public class AmbiguityAction extends easyJ.http.servlet.SingleDataAction {
    public AmbiguityAction() {}

    public void query() throws EasyJException {
        super.query();
        this.returnPath = "/WEB-INF/template/AjaxNewWindowViewAll.jsp";

    }

    public void getAmbiguity() throws EasyJException, IOException {
        AmbiguityImpl targetObject = new AmbiguityImpl();
        AmbiguityInterface ambiguityInterface = null;
        Object proxy = AmbiguityProxyFactory.getProxy(targetObject);
        ambiguityInterface = (AmbiguityInterface) proxy;
        Ambiguity ambiguity = new Ambiguity();
        ambiguity = (Ambiguity) object;
        try {
            response.getWriter().println(
                    ambiguityInterface.getAmbiguity(ambiguity, this.request,
                            false).toString());
        } catch (EasyJException ex) {
            ex.getStackTrace();
        }

    }

    public void ambiguityTypeEvaluation() throws EasyJException, IOException {
        AmbiguityImpl targetObject = new AmbiguityImpl();
        AmbiguityInterface ambiguityInterface = null;
        Object proxy = AmbiguityProxyFactory.getProxy(targetObject);
        ambiguityInterface = (AmbiguityInterface) proxy;
        AmbiguityTypeValue atv = new AmbiguityTypeValue();
        atv = (AmbiguityTypeValue) object;
        returnMessage = ambiguityInterface.ambiguityTypeEvaluation(atv);

    }

    public void ambiguityUpdate() throws EasyJException, IOException {
        AmbiguityImpl targetObject = new AmbiguityImpl();
        AmbiguityInterface ambiguityInterface = null;
        Object proxy = AmbiguityProxyFactory.getProxy(targetObject);
        ambiguityInterface = (AmbiguityInterface) proxy;
        AmbiguityEvaluation ae = new AmbiguityEvaluation();
        ae = (AmbiguityEvaluation) object;
        response.setContentType("text/xml");
        response.getWriter().write(
                ambiguityInterface.ambiguityUpdate(ae, this.request, response)
                        .toString());

    }

    public void creatingAmbiguity() throws EasyJException, IOException {
        AmbiguityImpl targetObject = new AmbiguityImpl();
        AmbiguityInterface ambiguityInterface = null;
        Object proxy = AmbiguityProxyFactory.getProxy(targetObject);
        ambiguityInterface = (AmbiguityInterface) proxy;
        Ambiguity ab = new Ambiguity();
        ab = (Ambiguity) object;
        returnMessage = ambiguityInterface.creatingAmbiguity(ab, this.request,
                response);
    }

    public void viewAllAmbiguity() throws EasyJException, IOException {
        AmbiguityImpl targetObject = new AmbiguityImpl();
        AmbiguityInterface ambiguityInterface = null;
        Object proxy = AmbiguityProxyFactory.getProxy(targetObject);
        ambiguityInterface = (AmbiguityInterface) proxy;
        Ambiguity ambiguity = new Ambiguity();
        ambiguity = (Ambiguity) object;
        try {
            response.getWriter().println(
                    ambiguityInterface
                            .viewAllAmbiguity(ambiguity, this.request)
                            .toString());
        } catch (EasyJException ex) {
            ex.getStackTrace();
        }

    }

}
