package cn.edu.pku.dr.requirement.elicitation.action;

import easyJ.common.EasyJException;
import cn.edu.pku.dr.requirement.elicitation.data.Problem;
import cn.edu.pku.dr.requirement.elicitation.data.Ambiguity;
import javax.servlet.http.HttpServletRequest;
import cn.edu.pku.dr.requirement.elicitation.data.AmbiguityTypeValue;
import easyJ.business.proxy.DataProxy;
import cn.edu.pku.dr.requirement.elicitation.data.AmbiguityEvaluation;

public interface ProblemInterface {
    // 这个接口的所有的函数都在ProblemImpl中实现
    public StringBuffer getProblem(Problem Problem, boolean modifyProblem,
            boolean modify) throws EasyJException;

    public StringBuffer getAmbiguity(Problem Problem, HttpServletRequest request)
            throws EasyJException;

    public String modifyProblem(Problem Problem, DataProxy dp,
            HttpServletRequest request) throws EasyJException;

    public String newProblem(Problem Problem, DataProxy dp,
            HttpServletRequest request) throws EasyJException;

}
