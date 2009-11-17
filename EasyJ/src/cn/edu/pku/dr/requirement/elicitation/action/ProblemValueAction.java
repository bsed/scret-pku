package cn.edu.pku.dr.requirement.elicitation.action;

import easyJ.common.EasyJException;
import java.io.IOException;

import cn.edu.pku.dr.requirement.elicitation.data.Problemreason;
import cn.edu.pku.dr.requirement.elicitation.data.Problemvalue;
import cn.edu.pku.dr.requirement.elicitation.data.ProblemvalueTypeValue;
import cn.edu.pku.dr.requirement.elicitation.data.ProblemvalueEvaluation;
import cn.edu.pku.dr.requirement.elicitation.business.proxy.ProblemReasonProxyFactory;
import cn.edu.pku.dr.requirement.elicitation.business.proxy.ProblemValueProxyFactory;

public class ProblemValueAction extends easyJ.http.servlet.SingleDataAction {
    public ProblemValueAction() {}

    public void query() throws EasyJException {
        super.query();
        this.returnPath = "/WEB-INF/template/AjaxNewWindowViewAll.jsp";

    }

    public void getProblemvalue() throws EasyJException, IOException {
        ProblemValueImpl targetObject = new ProblemValueImpl();
        ProblemValueInterface problemvalueInterface = null;
        Object proxy = ProblemValueProxyFactory.getProxy(targetObject);
        problemvalueInterface = (ProblemValueInterface) proxy;
        Problemvalue problemvalue = new Problemvalue();
        problemvalue = (Problemvalue) object;
        try {
            response.getWriter().println(
                    problemvalueInterface.getProblemvalue(problemvalue,
                            this.request, false).toString());
        } catch (EasyJException ex) {
            ex.getStackTrace();
        }

    }

    public void creatingProblemValue() throws EasyJException, IOException {
        ProblemValueImpl targetObject = new ProblemValueImpl();
        ProblemValueInterface problemvalueInterface = null;
        Object proxy = ProblemValueProxyFactory.getProxy(targetObject);
        problemvalueInterface = (ProblemValueInterface) proxy;
        Problemvalue problemvalue = new Problemvalue();
        problemvalue = (Problemvalue) object;
        returnMessage = problemvalueInterface.creatingProblemValue(
                problemvalue, this.request, response);

    }

    public void problemvalueTypeEvaluation() throws EasyJException, IOException {
        ProblemValueImpl targetObject = new ProblemValueImpl();
        ProblemValueInterface problemvalueInterface = null;
        Object proxy = ProblemValueProxyFactory.getProxy(targetObject);
        problemvalueInterface = (ProblemValueInterface) proxy;
        ProblemvalueTypeValue pvtv = new ProblemvalueTypeValue();
        pvtv = (ProblemvalueTypeValue) object;
        returnMessage = problemvalueInterface.problemvalueTypeEvaluation(pvtv);

    }

    public void problemvalueUpdate() throws EasyJException, IOException {
        ProblemValueImpl targetObject = new ProblemValueImpl();
        ProblemValueInterface problemvalueInterface = null;
        Object proxy = ProblemValueProxyFactory.getProxy(targetObject);
        problemvalueInterface = (ProblemValueInterface) proxy;
        ProblemvalueEvaluation pve = new ProblemvalueEvaluation();
        pve = (ProblemvalueEvaluation) object;
        response.setContentType("text/xml");
        response.getWriter().write(
                problemvalueInterface.problemvalueUpdate(pve, this.request,
                        response).toString());

    }

    public void viewAllProblemvalue() throws EasyJException, IOException {
        ProblemValueImpl targetObject = new ProblemValueImpl();
        ProblemValueInterface problemvalueInterface = null;
        Object proxy = ProblemValueProxyFactory.getProxy(targetObject);
        problemvalueInterface = (ProblemValueInterface) proxy;
        Problemvalue problemvalue = new Problemvalue();
        problemvalue = (Problemvalue) object;
        try {
            response.getWriter().println(
                    problemvalueInterface.viewAllProblemvalue(problemvalue,
                            this.request).toString());
        } catch (EasyJException ex) {
            ex.getStackTrace();
        }

    }

}
