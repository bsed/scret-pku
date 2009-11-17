package cn.edu.pku.dr.requirement.elicitation.action;

import javax.servlet.http.HttpServletRequest;
import easyJ.common.EasyJException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import cn.edu.pku.dr.requirement.elicitation.data.Problemreason;
import cn.edu.pku.dr.requirement.elicitation.data.Problemvalue;
import cn.edu.pku.dr.requirement.elicitation.data.ProblemvalueTypeValue;
import cn.edu.pku.dr.requirement.elicitation.data.ProblemvalueEvaluation;

public interface ProblemValueInterface {
    public StringBuffer getProblemvalue(Problemvalue problemvalue,
            HttpServletRequest request, boolean vote) throws EasyJException;

    public String problemvalueTypeEvaluation(ProblemvalueTypeValue pvtv)
            throws EasyJException;

    public StringBuffer problemvalueUpdate(ProblemvalueEvaluation pve,
            HttpServletRequest request, HttpServletResponse response)
            throws EasyJException, IOException;

    public StringBuffer viewAllProblemvalue(Problemvalue problemvalue,
            HttpServletRequest request) throws EasyJException;

    public String creatingProblemValue(Problemvalue pv,
            HttpServletRequest request, HttpServletResponse response)
            throws EasyJException, IOException;

}
