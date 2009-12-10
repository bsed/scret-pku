package cn.edu.pku.dr.requirement.elicitation.action;

import javax.servlet.http.HttpServletRequest;
import easyJ.common.EasyJException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import cn.edu.pku.dr.requirement.elicitation.data.Problemsolution;
import cn.edu.pku.dr.requirement.elicitation.data.ProblemsolutionEvaluation;
import cn.edu.pku.dr.requirement.elicitation.data.ProblemsolutionReply;
import cn.edu.pku.dr.requirement.elicitation.data.ProblemsolutionReplyEvaluation;

public interface ProblemSolutionInterface {
    public StringBuffer getProblemsolution(Problemsolution problemsolution,
            HttpServletRequest request, boolean vote) throws EasyJException;

    public StringBuffer problemsolutionUpdate(ProblemsolutionEvaluation pse,
            HttpServletRequest request, HttpServletResponse response)
            throws EasyJException, IOException;

    public StringBuffer problemsolutionReplyUpdate(
            ProblemsolutionReplyEvaluation psre, HttpServletRequest request,
            HttpServletResponse response) throws EasyJException, IOException;

    public String creatingReply(ProblemsolutionReply psr,
            HttpServletRequest request, HttpServletResponse response)
            throws EasyJException, IOException;

    public StringBuffer createSolution(HttpServletRequest request,
            HttpServletResponse response) throws EasyJException, IOException;

    public String creatingSolution(Problemsolution problemsolution,
            HttpServletRequest request) throws EasyJException, IOException;

    public StringBuffer viewDetailedSolution(Problemsolution problemsolution,
            HttpServletRequest request) throws EasyJException;

}
