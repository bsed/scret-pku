package cn.edu.pku.dr.requirement.elicitation.action;

import cn.edu.pku.dr.requirement.elicitation.data.Ambiguity;
import javax.servlet.http.HttpServletRequest;
import easyJ.common.EasyJException;
import cn.edu.pku.dr.requirement.elicitation.data.AmbiguityTypeValue;
import cn.edu.pku.dr.requirement.elicitation.data.AmbiguityEvaluation;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import easyJ.business.proxy.DataProxy;

public interface AmbiguityInterface {
    public StringBuffer getAmbiguity(Ambiguity ambiguity,
            HttpServletRequest request, boolean vote) throws EasyJException;

    public String ambiguityTypeEvaluation(AmbiguityTypeValue atv)
            throws EasyJException;

    public StringBuffer ambiguityUpdate(AmbiguityEvaluation ae,
            HttpServletRequest request, HttpServletResponse response)
            throws EasyJException, IOException;

    public String creatingAmbiguity(Ambiguity ab, HttpServletRequest request,
            HttpServletResponse response) throws EasyJException, IOException;

    public StringBuffer viewAllAmbiguity(Ambiguity ambiguity,
            HttpServletRequest request) throws EasyJException;

}
