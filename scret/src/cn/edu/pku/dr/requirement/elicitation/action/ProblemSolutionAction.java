package cn.edu.pku.dr.requirement.elicitation.action;

import java.io.IOException;

import cn.edu.pku.dr.requirement.elicitation.business.proxy.ProblemReasonProxyFactory;
import cn.edu.pku.dr.requirement.elicitation.business.proxy.ProblemSolutionProxyFactory;
import cn.edu.pku.dr.requirement.elicitation.data.Problemreason;
import cn.edu.pku.dr.requirement.elicitation.data.ProblemreasonEvaluation;
import cn.edu.pku.dr.requirement.elicitation.data.Problemsolution;
import cn.edu.pku.dr.requirement.elicitation.data.ProblemsolutionEvaluation;
import cn.edu.pku.dr.requirement.elicitation.data.ProblemsolutionReply;
import cn.edu.pku.dr.requirement.elicitation.data.ProblemsolutionReplyEvaluation;
import easyJ.common.EasyJException;

public class ProblemSolutionAction extends easyJ.http.servlet.SingleDataAction {
    public ProblemSolutionAction() {}

    public void query() throws EasyJException {
        super.query();
        this.returnPath = "/WEB-INF/template/AjaxDetailedSolution.jsp";

    }

    public void getProblemsolution() throws EasyJException, IOException {
        ProblemSolutionImpl targetObject = new ProblemSolutionImpl();
        ProblemSolutionInterface problemsolutionInterface = null;
        Object proxy = ProblemSolutionProxyFactory.getProxy(targetObject);
        problemsolutionInterface = (ProblemSolutionInterface) proxy;
        Problemsolution problemsolution = new Problemsolution();
        problemsolution = (Problemsolution) object;
        try {
            response.getWriter().println(
                    problemsolutionInterface.getProblemsolution(
                            problemsolution, this.request, false).toString());
        } catch (EasyJException ex) {
            ex.getStackTrace();
        }

    }

    public void problemsolutionUpdate() throws EasyJException, IOException {
        ProblemSolutionImpl targetObject = new ProblemSolutionImpl();
        ProblemSolutionInterface problemsolutionInterface = null;
        Object proxy = ProblemSolutionProxyFactory.getProxy(targetObject);
        problemsolutionInterface = (ProblemSolutionInterface) proxy;
        ProblemsolutionEvaluation pse = new ProblemsolutionEvaluation();
        pse = (ProblemsolutionEvaluation) object;
        response.setContentType("text/xml");
        response.getWriter().write(
                problemsolutionInterface.problemsolutionUpdate(pse,
                        this.request, response).toString());

    }

    public void problemsolutionReplyUpdate() throws EasyJException, IOException {
        ProblemSolutionImpl targetObject = new ProblemSolutionImpl();
        ProblemSolutionInterface problemsolutionInterface = null;
        Object proxy = ProblemSolutionProxyFactory.getProxy(targetObject);
        problemsolutionInterface = (ProblemSolutionInterface) proxy;
        ProblemsolutionReplyEvaluation psre = new ProblemsolutionReplyEvaluation();
        psre = (ProblemsolutionReplyEvaluation) object;
        response.setContentType("text/xml");
        response.getWriter().write(
                problemsolutionInterface.problemsolutionReplyUpdate(psre,
                        this.request, response).toString());

    }

    public void creatingReply() throws EasyJException, IOException {
        ProblemSolutionImpl targetObject = new ProblemSolutionImpl();
        ProblemSolutionInterface problemsolutionInterface = null;
        Object proxy = ProblemSolutionProxyFactory.getProxy(targetObject);
        problemsolutionInterface = (ProblemSolutionInterface) proxy;
        ProblemsolutionReply psr = new ProblemsolutionReply();
        psr = (ProblemsolutionReply) object;
        returnMessage = problemsolutionInterface.creatingReply(psr,
                this.request, response);

    }

    public void createSolution() throws EasyJException, IOException {
        ProblemSolutionImpl targetObject = new ProblemSolutionImpl();
        ProblemSolutionInterface problemsolutionInterface = null;
        Object proxy = ProblemSolutionProxyFactory.getProxy(targetObject);
        problemsolutionInterface = (ProblemSolutionInterface) proxy;

        try {
            response.getWriter().println(
                    problemsolutionInterface.createSolution(this.request,
                            response).toString());
        } catch (EasyJException ex) {
            ex.getStackTrace();
        }
    }

    public void creatingSolution() throws EasyJException, IOException {
        ProblemSolutionImpl targetObject = new ProblemSolutionImpl();
        ProblemSolutionInterface problemsolutionInterface = null;
        Object proxy = ProblemSolutionProxyFactory.getProxy(targetObject);
        problemsolutionInterface = (ProblemSolutionInterface) proxy;
        Problemsolution ps = new Problemsolution();
        ps = (Problemsolution) object;
        returnMessage = problemsolutionInterface.creatingSolution(ps,
                this.request);
    }

    public void viewDetailedSolution() throws EasyJException, IOException {
        ProblemSolutionImpl targetObject = new ProblemSolutionImpl();
        ProblemSolutionInterface problemsolutionInterface = null;
        Object proxy = ProblemSolutionProxyFactory.getProxy(targetObject);
        problemsolutionInterface = (ProblemSolutionInterface) proxy;
        Problemsolution problemsolution = new Problemsolution();
        problemsolution = (Problemsolution) object;
        try {
            response.getWriter().println(
                    problemsolutionInterface.viewDetailedSolution(
                            problemsolution, this.request).toString());
        } catch (EasyJException ex) {
            ex.getStackTrace();
        }

    }

}
