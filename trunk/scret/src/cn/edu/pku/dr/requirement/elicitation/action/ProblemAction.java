package cn.edu.pku.dr.requirement.elicitation.action;

import easyJ.common.EasyJException;
import java.io.IOException;
import cn.edu.pku.dr.requirement.elicitation.data.Problem;
import cn.edu.pku.dr.requirement.elicitation.business.proxy.ProblemPorxyFactory;

public class ProblemAction extends easyJ.http.servlet.SingleDataAction {
    public ProblemAction() {}

    public void query() throws EasyJException {
        super.query();
        this.returnPath = "/WEB-INF/template/AjaxNewWindowProblem.jsp";

    }

    // 参数要对应Problem中的参数，jsp在调用这些方法后，会被送到ProblemIntercepter中进行判断是否有页面显示的权限，true代表
    // 这个元素有显示的权限。转发的工作是由ProblemProxy完成的。
    public void getProblem() throws EasyJException, IOException {
        ProblemImpl targetObject = new ProblemImpl();
        ProblemInterface problemInterface = null;
        Object proxy = ProblemPorxyFactory.getProxy(targetObject);
        problemInterface = (ProblemInterface) proxy;
        Problem problem = new Problem();
        problem = (Problem) object;
        try {
            response.getWriter().println(
                    problemInterface.getProblem(problem, false, false)
                            .toString());
        } catch (EasyJException e) {
            e.getStackTrace();
        }
    }

    public void getAmbiguity() throws EasyJException, IOException {
        ProblemImpl targetObject = new ProblemImpl();
        ProblemInterface problemInterface = null;
        Object proxy = ProblemPorxyFactory.getProxy(targetObject);
        problemInterface = (ProblemInterface) proxy;
        Problem problem = new Problem();
        problem = (Problem) object;
        try {
            response.getWriter().println(
                    problemInterface.getAmbiguity(problem, request).toString());
        } catch (EasyJException e) {
            e.getStackTrace();
        }
    }

    public void modifyProblem() throws EasyJException, IOException {
        ProblemImpl targetObject = new ProblemImpl();
        ProblemInterface problemInterface = null;
        Object proxy = ProblemPorxyFactory.getProxy(targetObject);
        problemInterface = (ProblemInterface) proxy;
        Problem problem = new Problem();
        problem = (Problem) object;
        returnMessage = problemInterface.modifyProblem(problem, dp, request);

    }

    public void newProblem() throws EasyJException, IOException {
        ProblemImpl targetObject = new ProblemImpl();
        ProblemInterface problemInterface = null;
        Object proxy = ProblemPorxyFactory.getProxy(targetObject);
        problemInterface = (ProblemInterface) proxy;
        Problem problem = new Problem();
        problem = (Problem) object;
        returnMessage = problemInterface.newProblem(problem, dp, request);

    }

}
