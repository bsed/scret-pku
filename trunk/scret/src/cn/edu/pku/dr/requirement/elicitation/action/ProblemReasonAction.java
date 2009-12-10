package cn.edu.pku.dr.requirement.elicitation.action;

import easyJ.common.EasyJException;
import java.io.IOException;
import cn.edu.pku.dr.requirement.elicitation.data.Problemvalue;
import cn.edu.pku.dr.requirement.elicitation.data.ProblemvalueTypeValue;
import cn.edu.pku.dr.requirement.elicitation.business.proxy.ProblemValueProxyFactory;
import cn.edu.pku.dr.requirement.elicitation.business.proxy.ProblemReasonProxyFactory;
import cn.edu.pku.dr.requirement.elicitation.data.Problemreason;
import cn.edu.pku.dr.requirement.elicitation.data.ProblemreasonEvaluation;

public class ProblemReasonAction extends easyJ.http.servlet.SingleDataAction {
    public ProblemReasonAction() {}

    public void query() throws EasyJException {
        super.query();
        this.returnPath = "/WEB-INF/template/AjaxNewWindowViewAll.jsp";

    }

    public void getProblemreason() throws EasyJException, IOException {
        ProblemReasonImpl targetObject = new ProblemReasonImpl();
        ProblemReasonInterface problemreasonInterface = null;
        Object proxy = ProblemReasonProxyFactory.getProxy(targetObject);
        problemreasonInterface = (ProblemReasonInterface) proxy;
        Problemreason problemreason = new Problemreason();
        problemreason = (Problemreason) object;
        try {
            response.getWriter().println(
                    problemreasonInterface.getProblemreason(problemreason,
                            this.request, false).toString());
        } catch (EasyJException ex) {
            ex.getStackTrace();
        }

    }

    public void creatingProblemReason() throws EasyJException, IOException {
        ProblemReasonImpl targetObject = new ProblemReasonImpl();
        ProblemReasonInterface problemreasonInterface = null;
        Object proxy = ProblemReasonProxyFactory.getProxy(targetObject);
        problemreasonInterface = (ProblemReasonInterface) proxy;
        Problemreason problemreason = new Problemreason();
        problemreason = (Problemreason) object;
        returnMessage = problemreasonInterface.creatingProblemReason(
                problemreason, this.request, response);

    }

    public void problemreasonUpdate() throws EasyJException, IOException {
        ProblemReasonImpl targetObject = new ProblemReasonImpl();
        ProblemReasonInterface problemreasonInterface = null;
        Object proxy = ProblemReasonProxyFactory.getProxy(targetObject);
        problemreasonInterface = (ProblemReasonInterface) proxy;
        ProblemreasonEvaluation pre = new ProblemreasonEvaluation();
        pre = (ProblemreasonEvaluation) object;
        response.setContentType("text/xml");
        response.getWriter().write(
                problemreasonInterface.problemreasonUpdate(pre, this.request,
                        response).toString());

    }

    public void viewAllProblemreason() throws EasyJException, IOException {
        ProblemReasonImpl targetObject = new ProblemReasonImpl();
        ProblemReasonInterface problemreasonInterface = null;
        Object proxy = ProblemReasonProxyFactory.getProxy(targetObject);
        problemreasonInterface = (ProblemReasonInterface) proxy;
        Problemreason problemreason = new Problemreason();
        problemreason = (Problemreason) object;
        try {
            response.getWriter().println(
                    problemreasonInterface.viewAllProblemreason(problemreason,
                            this.request).toString());
        } catch (EasyJException ex) {
            ex.getStackTrace();
        }

    }

}
