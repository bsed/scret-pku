package cn.edu.pku.dr.requirement.elicitation.action;

import easyJ.http.servlet.SingleDataAction;
import cn.edu.pku.dr.requirement.elicitation.data.Ambiguity;
import cn.edu.pku.dr.requirement.elicitation.data.AmbiguityTypeValue;
import cn.edu.pku.dr.requirement.elicitation.data.Finalsolution;
import cn.edu.pku.dr.requirement.elicitation.data.FinalsolutionEvaluation;
import cn.edu.pku.dr.requirement.elicitation.data.Problemreason;
import cn.edu.pku.dr.requirement.elicitation.data.ProblemreasonEvaluation;
import cn.edu.pku.dr.requirement.elicitation.data.ProblemreasonSolution;
import cn.edu.pku.dr.requirement.elicitation.data.Problemsolution;
import cn.edu.pku.dr.requirement.elicitation.data.ProblemsolutionEvaluation;
import cn.edu.pku.dr.requirement.elicitation.data.ProblemsolutionReply;
import cn.edu.pku.dr.requirement.elicitation.data.ProblemsolutionReplyEvaluation;
import cn.edu.pku.dr.requirement.elicitation.data.Problemvalue;
import javax.servlet.http.HttpServletRequest;
import easyJ.common.EasyJException;
import java.util.ArrayList;
import cn.edu.pku.dr.requirement.elicitation.data.ProblemvalueTypeValue;
import easyJ.business.proxy.DataProxy;
import easyJ.business.proxy.SingleDataProxy;
import cn.edu.pku.dr.requirement.elicitation.data.ProblemvalueEvaluation;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import easyJ.system.data.SysUser;
import easyJ.system.data.SysUserCache;
import easyJ.http.Globals;
import easyJ.business.proxy.CompositeDataProxy;
import easyJ.common.BeanUtil;
import easyJ.database.dao.command.SelectCommand;
import easyJ.database.dao.DAOFactory;
import easyJ.database.dao.Filter;
import easyJ.database.dao.SQLOperator;
import easyJ.database.dao.LogicOperator;
import easyJ.database.session.Session;
import easyJ.database.session.SessionFactory;
import cn.edu.pku.dr.requirement.elicitation.data.Problem;
import java.util.Collections;
import cn.edu.pku.dr.requirement.elicitation.data.ProblemVersion;

public class ProblemSolutionImpl extends SingleDataAction implements
        ProblemSolutionInterface {
    public ProblemSolutionImpl() {}

    public StringBuffer getProblemsolution(Problemsolution problemsolution,
            HttpServletRequest request, boolean vote) throws EasyJException {
        SysUserCache userCache = (SysUserCache) request.getSession()
                .getAttribute(Globals.SYS_USER_CACHE);
        Long userId = userCache.getUser().getUserId();

        // 这个方法输出问题的价值界面
        StringBuffer buffer = new StringBuffer();
        CompositeDataProxy cdp = CompositeDataProxy.getInstance();
        SingleDataProxy sdp = SingleDataProxy.getInstance();
        ArrayList problemsolutions = cdp.query(problemsolution);
        Finalsolution finalsolution = new Finalsolution();
        finalsolution.setProblemId(problemsolution.getProblemId());
        int finalsolution_count = sdp.getCount(finalsolution).intValue();
        int solution_count = sdp.getCount(problemsolution).intValue();
        problemsolution.setIsVoting("Y");
        problemsolution.setIsoverdue("N");
        int voting_count = sdp.getCount(problemsolution).intValue();
        // 标题
        buffer
                .append("<div class=\"t1\"><div class=\"ico\"><div class=\"ibest\"></div></div>问题解决方案讨论页面</div>");
        buffer.append("<div class=\"t1\">现共有<span style=\"color:#FF0000\">"
                + solution_count
                + "</span>个解决方案,<span style=\"color:#FF0000\">" + voting_count
                + "</span>个解决方案正在投票，<span style=\"color:#FF0000\">"
                + finalsolution_count + "</span>个最终解决方案</div>");
        // 下面是用户讨论的页面
        buffer
                .append("<div class=\"bc0\" style=\"padding:0px 0pt;\"><div class=\"t2\">");
        // js文件待改
        buffer
                .append("<a  id=\"\"  href=\"javascript:createNewSolution("
                        + problemsolution.getProblemId()
                        + ")\"  style=\"padding:5px 10px 5px 10px!important;padding:4px 10px 4px 10px;height:1px;border:1px solid#7AA9DF;background-color:#EAF3FC;font-size:12px \">发表新的解决方案</a></div>");
        Problem problem = new Problem();
        problem.setProblemId(problemsolution.getProblemId());
        problem = (Problem) cdp.get(problem);
        // 找到正式的解决方案
        Problemsolution temp1 = new Problemsolution();
        temp1.setProblemId(problem.getProblemId());
        temp1.setIsVoting("C");
        ArrayList finalsolutions = cdp.query(temp1);
        // 找到正在投票的解决方案
        Problemsolution temp2 = new Problemsolution();
        temp2.setProblemId(problem.getProblemId());
        temp2.setIsVoting("Y");
        // 找到所有的解决方案
        ArrayList votingsolutions = cdp.query(temp2);
        Problemsolution temp3 = new Problemsolution();
        temp3.setProblemId(problem.getProblemId());
        ArrayList allsolutions = cdp.query(temp3);

        // 处理最终解决方案情况
        if (finalsolution_count >= 0) {
            buffer
                    .append("<div class=\"t2\" onclick=\"Problemsolution.showHide('finalSolution')\" style=\"cursor:pointer\">最终解决方案:</div>");
            buffer.append("<div style=\"display:none\" id=\"finalSolution\">");
            String finalSolutionContent = "";
            finalSolutionContent = getSimpleProblemsolution(userId,
                    finalsolutions);
            buffer.append(finalSolutionContent);
            buffer.append("</div>");
        }
        // 处理正在投票中的解决方案
        if (voting_count >= 0) {
            buffer
                    .append("<div class=\"t2\" onclick=\"Problemsolution.showHide('votingSolution')\" style=\"cursor:pointer\">正在投票中的解决方案:</div>");
            buffer.append("<div style=\"display:none\" id=\"votingSolution\">");
            String votingSolutionContent = "";
            votingSolutionContent = getSimpleProblemsolution(userId,
                    votingsolutions);
            buffer.append(votingSolutionContent);
            buffer.append("</div>");
        }
        // 所有的解决方案
        if (solution_count >= 0) {
            buffer
                    .append("<div class=\"t2\" onclick=\"Problemsolution.showHide('allSolution')\" style=\"cursor:pointer\">所有的解决方案:</div>");
            buffer.append("<div style=\"display:none\" id=\"allSolution\">");
            String allSolutionContent = "";
            allSolutionContent = getSimpleProblemsolution(userId, allsolutions);
            buffer.append(allSolutionContent);
            buffer.append("</div>");
        }

        // buffer
        // .append("<div id=\"Lg\"></div><div class=\"pl10\"><a href=\"#\"
        // class=\"lmore\" target=\"_blank\"
        // id=\"more_comment\">查看所有解决方案&gt;&gt;</a></div>");
        // buffer
        // .append("<div class=\"rr_4\"></div><div class=\"rr_5\"></div><div
        // class=\"rr_1\"></div></div></div></div>");

        return buffer;
    }

    public String getSimpleProblemsolution(Long userId, ArrayList solutions)
            throws EasyJException {
        StringBuffer buffer = new StringBuffer();
        SingleDataProxy sdp = SingleDataProxy.getInstance();
        Problemsolution problemsolution = new Problemsolution();
        ProblemreasonSolution prs = new ProblemreasonSolution();
        ProblemreasonSolution prs2 = new ProblemreasonSolution();
        String solution = "";
        if (solutions.size() > 0) {
            for (int i = 0; i < solutions.size(); i++) {
                problemsolution = (Problemsolution) solutions.get(i);
                buffer
                        .append("<div id=\"Lg\"></div><div class=\"f14 p90 pl10\" class=\"answer_content\">"
                                + problemsolution.getProblemsolutionContent());
                buffer.append("<br><a href=\"javascript:showDetailedSolution("
                        + problemsolution.getProblemsolutionId()
                        + ")\">详细</a></div>");
                buffer
                        .append("<div align=\"right\" style=\"margin: 5px 5px 8px;\" id=\"comment_info\"><span class=\"gray\">评论者:</span><a href=\"#\">"
                                + problemsolution.getUserName()
                                + "</a><span class=\"gray\"> 时间:"
                                + problemsolution.getBuildTime()
                                + "</span></div>");
            }
        } else {
            buffer
                    .append("<div class=\"f14 p90 pl10\" class=\"answer_content\">目前暂无此类内容</div>");
        }
        return buffer.toString();
    }

    public StringBuffer problemsolutionUpdate(ProblemsolutionEvaluation pse,
            HttpServletRequest request, HttpServletResponse response)
            throws EasyJException, IOException {
        SysUserCache userCache = (SysUserCache) request.getSession()
                .getAttribute(Globals.SYS_USER_CACHE);
        Long userId = userCache.getUser().getUserId();
        pse.setCreatorId(userId);
        CompositeDataProxy cdp = CompositeDataProxy.getInstance();
        SingleDataProxy sdp = SingleDataProxy.getInstance();
        Problemsolution ps = new Problemsolution();
        ps.setProblemsolutionId(pse.getProblemsolutionId());
        ps = (Problemsolution) cdp.get(ps);
        StringBuffer xml = new StringBuffer("<result>");
        if (request.getParameter("flower") != null) {
            try {
                if (request.getParameter("useState").equals("C")) {
                    Finalsolution finalsolution = new Finalsolution();
                    finalsolution.setProblemsolutionId(pse
                            .getProblemsolutionId());
                    finalsolution = (Finalsolution) sdp.get(finalsolution);
                    finalsolution.setFinalgoodNum(new Long(finalsolution
                            .getFinalgoodNum().intValue() + 1));
                    sdp.update(finalsolution);
                    FinalsolutionEvaluation fse = new FinalsolutionEvaluation();
                    fse.setFinalsolutionId(finalsolution.getFinalsolutionId());
                    fse.setIsGood("Y");
                    sdp.create(fse);
                    xml
                            .append("<message>success</message><choose>flower</choose><problemsolutionId>"
                                    + pse.getProblemsolutionId()
                                    + "</problemsolutionId></result>");
                }
            } catch (EasyJException e) {
                xml.append("<message>failure</message></result>");
            }
            try {
                if (request.getParameter("useState").equals("Y")) {
                    ps.setVotingGoodNum((new Long(ps.getVotingGoodNum()
                            .intValue() + 1)));
                    cdp.update(ps);
                    pse.setStage(2);
                    pse.setIsGood("Y");
                    sdp.create(pse);
                    xml
                            .append("<message>success</message><choose>flower</choose><problemsolutionId>"
                                    + pse.getProblemsolutionId()
                                    + "</problemsolutionId></result>");
                }
            } catch (EasyJException e) {
                xml.append("<message>failure</message></result>");
            }
            try {
                if (request.getParameter("useState").equals("N")) {
                    ps.setGoodNum(new Long(ps.getGoodNum().intValue() + 1));
                    cdp.update(ps);
                    pse.setIsGood("Y");
                    pse.setStage(2);
                    sdp.create(pse);
                    xml
                            .append("<message>success</message><choose>flower</choose><problemsolutionId>"
                                    + pse.getProblemsolutionId()
                                    + "</problemsolutionId></result>");
                }
            } catch (EasyJException e) {
                xml.append("<message>failure</message></result>");
            }
        }

        if (request.getParameter("badegg") != null) {
            try {
                if (request.getParameter("useState").equals("C")) {
                    Finalsolution finalsolution = new Finalsolution();
                    finalsolution.setProblemsolutionId(pse
                            .getProblemsolutionId());
                    finalsolution = (Finalsolution) sdp.get(finalsolution);
                    finalsolution.setFinalbadNum(new Long(finalsolution
                            .getFinalbadNum().intValue() + 1));
                    sdp.update(finalsolution);
                    FinalsolutionEvaluation fse = new FinalsolutionEvaluation();
                    fse.setFinalsolutionId(finalsolution.getFinalsolutionId());
                    fse.setIsGood("N");
                    sdp.create(fse);
                    xml
                            .append("<message>success</message><choose>badegg</choose><problemsolutionId>"
                                    + pse.getProblemsolutionId()
                                    + "</problemsolutionId></result>");
                }
            } catch (EasyJException e) {
                xml.append("<message>failure</message></result>");
            }
            try {
                if (request.getParameter("useState").equals("Y")) {
                    ps.setVotingBadNum((new Long(ps.getVotingBadNum()
                            .intValue() + 1)));
                    cdp.update(ps);
                    pse.setIsGood("N");
                    sdp.create(pse);
                    xml
                            .append("<message>success</message><choose>badegg</choose><problemsolutionId>"
                                    + pse.getProblemsolutionId()
                                    + "</problemsolutionId></result>");
                }
            } catch (EasyJException e) {
                xml.append("<message>failure</message></result>");
            }
            try {
                if (request.getParameter("useState").equals("N")) {
                    ps.setBadNum(new Long(ps.getBadNum().intValue() + 1));
                    cdp.update(ps);
                    pse.setIsGood("N");
                    sdp.create(pse);
                    xml
                            .append("<message>success</message><choose>badegg</choose><problemsolutionId>"
                                    + pse.getProblemsolutionId()
                                    + "</problemsolutionId></result>");
                }
            } catch (EasyJException e) {
                xml.append("<message>failure</message></result>");
            }

        }

        try {
            if (request.getParameter("overdue") != null) {
                ProblemVersion problemversion = new ProblemVersion();
                problemversion.setProblemId(ps.getProblemId());
                Long problemversioncount = sdp.getCount(problemversion);
                ps.setProblemsolutionOverdueVersion(problemversioncount);
                ps.setIsoverdue("Y");
                cdp.update(ps);
                xml
                        .append("<message>success</message><choose>overdue</choose><problemsolutionId>"
                                + pse.getProblemsolutionId()
                                + "</problemsolutionId></result>");

            }
        } catch (EasyJException e) {
            xml.append("<message>failure</message></result>");
        }

        try {
            if (request.getParameter("decision") != null) {
                if (request.getParameter("decision").equals("voting")) {
                    ps.setIsVoting("Y");
                    sdp.update(ps);

                } else if (request.getParameter("decision").equals("best")) {
                    ps.setIsVoting("C");
                    Finalsolution fs = new Finalsolution();
                    fs.setProblemId(ps.getProblemId());
                    fs.setProblemsolutionId(ps.getProblemsolutionId());
                    fs.setCreatorId(userId);
                    fs.setFinalgoodNum(new Long("0"));
                    fs.setFinalbadNum(new Long("0"));
                    sdp.create(fs);
                    sdp.update(ps);
                }

            }
        } catch (EasyJException e) {
            xml.append("<message>failure</message></result>");
        }

        return xml;

    }

    public StringBuffer problemsolutionReplyUpdate(
            ProblemsolutionReplyEvaluation psre, HttpServletRequest request,
            HttpServletResponse response) throws EasyJException, IOException {
        CompositeDataProxy cdp = CompositeDataProxy.getInstance();
        SingleDataProxy sdp = SingleDataProxy.getInstance();
        ProblemsolutionReply psr = new ProblemsolutionReply();
        psr.setProblemsolutionReplyId(psre.getProblemsolutionReplyId());
        psr = (ProblemsolutionReply) cdp.get(psr);
        StringBuffer xml = new StringBuffer("<result>");
        if (request.getParameter("flower") != null) {
            try {
                psr.setGoodNum(new Long(psr.getGoodNum().intValue() + 1));
                cdp.update(psr);
                psre.setIsGood("Y");
                sdp.create(psre);
                xml
                        .append("<message>success</message><choose>flower</choose><problemsolutionReplyId>"
                                + psre.getProblemsolutionReplyId()
                                + "</problemsolutionReplyId></result>");
            } catch (EasyJException e) {
                xml.append("<message>failure</message></result>");
            }
        }

        if (request.getParameter("badegg") != null) {
            try {
                psr.setBadNum(new Long(psr.getBadNum().intValue() + 1));
                cdp.update(psr);
                psre.setIsGood("N");
                sdp.create(psre);
                xml
                        .append("<message>success</message><choose>badegg</choose><problemsolutionReplyId>"
                                + psre.getProblemsolutionReplyId()
                                + "</problemsolutionReplyId></result>");
            } catch (EasyJException e) {
                xml.append("<message>failure</message></result>");
            }
        }

        try {
            if (request.getParameter("overdue") != null) {
                Problemsolution ps = new Problemsolution();
                ps.setProblemsolutionId(psr.getProblemsolutionId());
                ps = (Problemsolution) sdp.get(ps);
                ProblemVersion problemversion = new ProblemVersion();
                problemversion.setProblemId(ps.getProblemId());
                Long problemversioncount = sdp.getCount(problemversion);
                psr.setProblemsolutionReplyOverdueVersion(problemversioncount);
                psr.setIsoverdue("Y");
                cdp.update(psr);
                xml
                        .append("<message>success</message><choose>overdue</choose><problemsolutionReplyId>"
                                + psre.getProblemsolutionReplyId()
                                + "</problemsolutionReplyId></result>");

            }
        } catch (EasyJException e) {
            xml.append("<message>failure</message></result>");
        }
        return xml;

    }

    public String creatingReply(ProblemsolutionReply psr,
            HttpServletRequest request, HttpServletResponse response)
            throws EasyJException, IOException {
        SysUserCache userCache = (SysUserCache) request.getSession()
                .getAttribute(Globals.SYS_USER_CACHE);
        Long userId = userCache.getUser().getUserId();

        // 这里还缺少一个对主贴和跟贴的回复的区别
        Long referenceId = new Long(request.getParameter("referenceId"));
        if (referenceId.equals(new Long("000"))) {} else if (referenceId
                .equals(new Long("000"))) {

        } else {
            psr.setProblemsoltuionReplyReferenceId(referenceId);

        }
        SingleDataProxy sdp = SingleDataProxy.getInstance();

        // 设置problemsolutionreply表的默认字段
        psr.setBadNum(new Long("0"));
        psr.setGoodNum(new Long("0"));
        psr.setIsoverdue("N");
        ProblemVersion pv = new ProblemVersion();
        Problemsolution ps = new Problemsolution();
        ps.setProblemsolutionId(psr.getProblemsolutionId());
        ps = (Problemsolution) sdp.get(ps);
        pv.setProblemId(ps.getProblemId());
        Long problemversioncount = sdp.getCount(pv);
        psr.setProblemsolutionReplyProblemId(problemversioncount);
        psr.setCreatorId(userId);
        try {

            sdp.create(psr);
            returnMessage = "create_reply_success**"
                    + psr.getProblemsolutionReplyContent();
        } catch (EasyJException e) {
            returnMessage = "create_reply_failure**";
        }
        return returnMessage;

    }

    public StringBuffer viewDetailedSolution(Problemsolution problemsolution,
            HttpServletRequest request) throws EasyJException {
        SysUserCache userCache = (SysUserCache) request.getSession()
                .getAttribute(Globals.SYS_USER_CACHE);
        Long userId = userCache.getUser().getUserId();
        StringBuffer buffer = new StringBuffer();
        buffer
                .append("<meta http-equiv=\"Expires\" content=\"0\"><meta http-equiv=\"Pragma\" content=\"no-cache\"><meta http-equiv=\"Cache-Control\" content=\"no-cache\">");
        buffer
                .append("<link href=\"/css/iknow1_1.css\" rel=\"stylesheet\" type=\"text/css\">");
        buffer
                .append("<link href=\"/css/table.css\" rel=\"stylesheet\" type=\"text/css\">");
        buffer
                .append("<link href=\"/css/pattern.css\" rel=\"stylesheet\" type=\"text/css\">");
        buffer
                .append("<link href=\"/css/scenario.css\" rel=\"stylesheet\" type=\"text/css\">");
        buffer
                .append("<script language=\"javascript\" src=\"/js/ProblemsolutionReply.js\"></script>");
        buffer.append("<div id=\"center2\" ><div class=\"bai\" >");
        buffer
                .append("<div class=\"mb12 bai\" id=\"value_more\"><div class=\"rr_1\"></div><div class=\"rr_2\"></div><div class=\"rr_3\"></div><div class=\"rr\">");
        buffer
                .append("<div class=\"t1\"><div class=\"ico\"><div class=\"icomment\"></div></div>解决方案详细情况</div><div class=\"bc0\" style=\"padding: 0px 0pt;\"><div id=\"detailedSolution\">");

        CompositeDataProxy cdp = CompositeDataProxy.getInstance();
        String content = "";
        content = getDetailedSolution(userId, problemsolution);
        buffer.append(content);
        buffer.append("<div id=\"Lg\"></div>");
        // 00代表没有回复别人的贴
        buffer
                .append("<div id=\"createReply_button1\" align=\"left\" ><a href=\"javascript:ProblemsolutionReply.createReply("
                        + problemsolution.getProblemsolutionId()
                        + ","
                        + 00
                        + ",'"
                        + "sub"
                        + "')\"  style=\"padding:5px 10px 5px 10px !important;padding:4px 10px 4px 10px;height:1px;border:1px solid #7AA9DF;background-color:#EAF3FC;font-size:12px\">发表评论</a></div>");
        buffer
                .append("<div id=\"createReply\"></div><div id=\"createReply_button\" style=\"display:none\"></div>");
        buffer
                .append("</div></div></div><div class=\"rr_4\"></div><div class=\"rr_5\"></div><div class=\"rr_1\"></div></div></div>");
        buffer.append("<TABLE><TBODY><TR>");
        buffer
                .append("<TD align=middle><IMG onclick=\"Ajax.loadHistory('pre')\" alt=\"\" src=\"/image/arrow_left.gif\" class=\"hide_todo\">");
        buffer
                .append("<IMG onclick=\"Ajax.loadHistory('next')\" alt=\"\" src=\"/image/arrow_right.gif\" class=\"hide_todo\">");
        buffer.append("</TD></TR></TBODY></TABLE>");
        return buffer;
    }

    public String getDetailedSolution(Long userId,
            Problemsolution problemsolution) throws EasyJException {

        StringBuffer buffer = new StringBuffer();
        SingleDataProxy sdp = SingleDataProxy.getInstance();
        CompositeDataProxy cdp = CompositeDataProxy.getInstance();

        ProblemsolutionReply psr = new ProblemsolutionReply();
        ProblemreasonSolution prs = new ProblemreasonSolution();
        ProblemreasonSolution prs2 = new ProblemreasonSolution();
        buffer.append("<div class=\"bc0\" style=\"padding:0px 0pt;\">");
        String reason = "";
        problemsolution = (Problemsolution) cdp.get(problemsolution);
        prs.setProblemsolutionId(problemsolution.getProblemsolutionId());
        ArrayList prss = sdp.query(prs);
        ArrayList psrs = problemsolution.getProblemsolutionReplys();
        buffer.append("<div id=\"Lg\" ></div>");
        if (prss.size() > 0) {
            for (int j = 0; j < prss.size(); j++) {
                prs2 = (ProblemreasonSolution) prss.get(j);
                reason = reason + "," + prs2.getProblemreasonId();
            }
            buffer.append("<div id=\"Lg\" ></div>"
                    + "<div class=\"f14 p90 pl10\" class=\"answer_content\">"
                    + problemsolution.getProblemsolutionContent() + "</div>");
            buffer.append("<p></p>共有对应的原因为" + reason + "<p></p>");

        } else {
            buffer
                    .append("<div class=\"f14 p90 pl10\" class=\"answer_content\">"
                            + problemsolution.getProblemsolutionContent()
                            + "</div><div id=\"Lg\" ></div>");

        }
        // 首先显示主贴，然后显示回复，主贴需考虑solution的三个状态
        // 1。已被选择为最佳解决方案。2。正在投票中的解决方案，3。一般状态的解决方案
        // 1。需要显示三种投票结果
        // isVoting为C表示正在第三阶段投票，isVoting为Y表示正在第二阶段投票，isVoting为N表示正在第一阶段
        if (problemsolution.getIsVoting().equals("C")) {
            buffer.append("<div></div>");
            buffer.append("作为备选方案前的投票结果：");
            buffer.append("<IMG src=\"/image/flower.gif\"> （得<SPAN>"
                    + problemsolution.getGoodNum() + "</SPAN>支）");
            buffer
                    .append("<IMG height=15 src=\"/image/badegg.gif\" width=16>（得<SPAN class=orange12>"
                            + problemsolution.getBadNum() + "</SPAN>个)<br>");
            buffer.append("作为备选方案时的投票结果：");
            buffer.append("<IMG src=\"/image/flower.gif\"> （得<SPAN>"
                    + problemsolution.getVotingGoodNum() + "</SPAN>支）");
            buffer
                    .append("<IMG height=15 src=\"/image/badegg.gif\" width=16>（得<SPAN class=orange12>"
                            + problemsolution.getVotingBadNum()
                            + "</SPAN>个)<br>");

            buffer.append(getContent(userId, problemsolution, null, "C"));
        } else if (problemsolution.getIsVoting().equals("Y")) {
            buffer
                    .append("<A class=brown12 href=\"javascript:Problemsolution.problemsolutionVoting("
                            + problemsolution.getProblemsolutionId()
                            + ",'best')\" > 选择为最佳解决方案</A><br>");
            buffer.append("作为备选方案前的投票结果：");
            buffer.append("<IMG src=\"/image/flower.gif\"> （得<SPAN>"
                    + problemsolution.getGoodNum() + "</SPAN>支）");
            buffer
                    .append("<IMG height=15 src=\"/image/badegg.gif\" width=16>（得<SPAN class=orange12>"
                            + problemsolution.getBadNum() + "</SPAN>个)<br>");

            buffer.append(getContent(userId, problemsolution, null, "Y"));

        } else if (problemsolution.getIsVoting().equals("N")) {
            buffer
                    .append("<p></p><A class=brown12 href=\"javascript:Problemsolution.problemsolutionVoting("
                            + problemsolution.getProblemsolutionId()
                            + ",'voting')\")>选择为备选方案</A><p></p>");
            buffer.append(getContent(userId, problemsolution, null, "N"));
        }
        // 取得回帖的信息
        for (int i = psrs.size() - 1; i >= 0; i--) {
            psr = (ProblemsolutionReply) psrs.get(i);
            buffer.append("<div id=\"Lg\" ></div>"
                    + "<div class=\"f14 p90 pl10\" class=\"answer_content\">"
                    + psr.getProblemsolutionReplyContent() + "</div>");
            buffer.append(getContent(userId, null, psr, null));
        }

        return buffer.toString();
    }

    public String getContent(Long userId, Problemsolution problemsolution,
            ProblemsolutionReply psr, String useState) throws EasyJException {
        StringBuffer buffer = new StringBuffer();
        SingleDataProxy sdp = SingleDataProxy.getInstance();
        ProblemsolutionEvaluation pse = new ProblemsolutionEvaluation();
        ProblemsolutionReplyEvaluation psre = new ProblemsolutionReplyEvaluation();
        if (problemsolution != null) {
            boolean flag = true;
            boolean stageflag1 = true;
            boolean stageflag2 = true;
            boolean stageflag3 = true;

            int j = 0;
            ArrayList pses = problemsolution.getProblemsolutionEvaluations();
            while (flag == true && j < pses.size()) {
                pse = (ProblemsolutionEvaluation) pses.get(j);
                if (pse.getCreatorId().intValue() == userId.intValue()) {
                    flag = false;

                }
                j++;
            }
            ProblemsolutionEvaluation pse_stage = new ProblemsolutionEvaluation();
            pse_stage.setProblemsolutionId(problemsolution
                    .getProblemsolutionId());
            pse_stage.setStage(1);
            ArrayList pses_stage1 = sdp.query(pse_stage);
            pse_stage.setStage(2);
            ArrayList pses_stage2 = sdp.query(pse_stage);
            FinalsolutionEvaluation fse = new FinalsolutionEvaluation();
            Finalsolution fs = new Finalsolution();
            fs.setProblemsolutionId(problemsolution.getProblemsolutionId());
            try {
                fs = (Finalsolution) sdp.get(fs);
                if (fs != null) {
                    fs = (Finalsolution) sdp.get(fs);
                    fse.setFinalsolutionId(fs.getFinalsolutionId());
                    ArrayList fses = sdp.query(fse);
                    while (stageflag3 == true && j < fses.size()) {
                        fs = (Finalsolution) fses.get(j);
                        if (pse.getCreatorId().intValue() == userId.intValue()) {
                            stageflag3 = false;

                        }
                        j++;
                    }
                }

            } catch (EasyJException e) {

            }

            while (stageflag1 == true && j < pses_stage1.size()) {
                pse = (ProblemsolutionEvaluation) pses_stage1.get(j);
                if (pse.getCreatorId().intValue() == userId.intValue()) {
                    stageflag1 = false;

                }
                j++;
            }
            while (stageflag2 == true && j < pses_stage2.size()) {
                pse = (ProblemsolutionEvaluation) pses_stage2.get(j);
                if (pse.getCreatorId().intValue() == userId.intValue()) {
                    stageflag2 = false;

                }
                j++;
            }

            if (useState.equals("C")) {
                Finalsolution finalsolution = new Finalsolution();
                finalsolution.setProblemsolutionId(problemsolution
                        .getProblemsolutionId());
                finalsolution = (Finalsolution) sdp.get(finalsolution);
                buffer.append("把该解决方案作为最佳解决方案您是否满意？：<br>");
                if (userId.intValue() != problemsolution.getCreatorId()
                        .intValue()
                        && problemsolution.getIsoverdue().equals("N")
                        && stageflag3) {
                    // 登陆者不是这个problemsolution的作者,且没有评价过,且这个problemsolution还没有过期
                    buffer
                            .append("<IMG src=\"/image/flower.gif\"> <A class=brown12 id=\"problemsolution_flower"
                                    + problemsolution.getProblemsolutionId()
                                    + "\" href=\"javascript:Problemsolution.problemsolutionFlower("
                                    + problemsolution.getProblemId()
                                    + ","
                                    + problemsolution.getProblemsolutionId()
                                    + ","
                                    + userId
                                    + ",'"
                                    + useState
                                    + "')\">送鲜花</A>（得<SPAN id=\"problemsolution_flowernum"
                                    + problemsolution.getProblemsolutionId()
                                    + "\">"
                                    + finalsolution.getFinalgoodNum()
                                    + "</SPAN>支）");
                    buffer
                            .append("<IMG height=15 src=\"/image/badegg.gif\" width=16><A id=\"problemsolution_badegg"
                                    + problemsolution.getProblemsolutionId()
                                    + "\"class=brown12  href=\"javascript:Problemsolution.problemsolutionBadegg("
                                    + problemsolution.getProblemId()
                                    + ","
                                    + problemsolution.getProblemsolutionId()
                                    + ","
                                    + userId
                                    + ",'"
                                    + useState
                                    + "')\">扔鸡蛋</A>（得<SPAN class=orange12 id=\"problemsolution_badeggnum"
                                    + problemsolution.getProblemsolutionId()
                                    + "\">"
                                    + finalsolution.getFinalbadNum()
                                    + "</SPAN>个)");
                } else {
                    buffer.append("<IMG src=\"/image/flower.gif\"> （得<SPAN>"
                            + finalsolution.getFinalgoodNum() + "</SPAN>支）");
                    buffer
                            .append("<IMG height=15 src=\"/image/badegg.gif\" width=16>（得<SPAN class=orange12>"
                                    + finalsolution.getFinalbadNum()
                                    + "</SPAN>个)");
                }
            } else if (useState.equals("Y")) {
                buffer.append("作为备选方案正在投票中：<br>");
                if (userId.intValue() != problemsolution.getCreatorId()
                        .intValue()
                        && problemsolution.getIsoverdue().equals("N")
                        && stageflag1) {
                    // 登陆者不是这个problemsolution的作者,且没有评价过,且这个problemsolution还没有过期
                    buffer
                            .append("<IMG src=\"/image/flower.gif\"> <A class=brown12 id=\"problemsolution_flower"
                                    + problemsolution.getProblemsolutionId()
                                    + "\" href=\"javascript:Problemsolution.problemsolutionFlower("
                                    + problemsolution.getProblemId()
                                    + ","
                                    + problemsolution.getProblemsolutionId()
                                    + ","
                                    + userId
                                    + ",'"
                                    + useState
                                    + "')\">送鲜花</A>（得<SPAN id=\"problemsolution_flowernum"
                                    + problemsolution.getProblemsolutionId()
                                    + "\">"
                                    + problemsolution.getVotingGoodNum()
                                    + "</SPAN>支）");
                    buffer
                            .append("<IMG height=15 src=\"/image/badegg.gif\" width=16><A id=\"problemsolution_badegg"
                                    + problemsolution.getProblemsolutionId()
                                    + "\"class=brown12  href=\"javascript:Problemsolution.problemsolutionBadegg("
                                    + problemsolution.getProblemId()
                                    + ","
                                    + problemsolution.getProblemsolutionId()
                                    + ","
                                    + userId
                                    + ",'"
                                    + useState
                                    + "')\">扔鸡蛋</A>（得<SPAN class=orange12 id=\"problemsolution_badeggnum"
                                    + problemsolution.getProblemsolutionId()
                                    + "\">"
                                    + problemsolution.getVotingBadNum()
                                    + "</SPAN>个)");
                } else {
                    buffer.append("<IMG src=\"/image/flower.gif\"> （得<SPAN>"
                            + problemsolution.getVotingGoodNum() + "</SPAN>支）");
                    buffer
                            .append("<IMG height=15 src=\"/image/badegg.gif\" width=16>（得<SPAN class=orange12>"
                                    + problemsolution.getVotingBadNum()
                                    + "</SPAN>个)");
                }
            } else if (useState.equals("N")) {
                buffer.append("第一阶段投票数：<p></p>");
                if (userId.intValue() != problemsolution.getCreatorId()
                        .intValue()
                        && problemsolution.getIsoverdue().equals("N")
                        && stageflag2) {
                    // 登陆者不是这个problemsolution的作者,且没有评价过,且这个problemsolution还没有过期
                    buffer
                            .append("<IMG src=\"/image/flower.gif\"> <A class=brown12 id=\"problemsolution_flower"
                                    + problemsolution.getProblemsolutionId()
                                    + "\" href=\"javascript:Problemsolution.problemsolutionFlower("
                                    + problemsolution.getProblemId()
                                    + ","
                                    + problemsolution.getProblemsolutionId()
                                    + ","
                                    + userId
                                    + ",'"
                                    + useState
                                    + "')\">送鲜花</A>（得<SPAN id=\"problemsolution_flowernum"
                                    + problemsolution.getProblemsolutionId()
                                    + "\">"
                                    + problemsolution.getGoodNum()
                                    + "</SPAN>支）");
                    buffer
                            .append("<IMG height=15 src=\"/image/badegg.gif\" width=16><A id=\"problemsolution_badegg"
                                    + problemsolution.getProblemsolutionId()
                                    + "\"class=brown12  href=\"javascript:Problemsolution.problemsolutionBadegg("
                                    + problemsolution.getProblemId()
                                    + ","
                                    + problemsolution.getProblemsolutionId()
                                    + ","
                                    + userId
                                    + ",'"
                                    + useState
                                    + "')\">扔鸡蛋</A>（得<SPAN class=orange12 id=\"problemsolution_badeggnum"
                                    + problemsolution.getProblemsolutionId()
                                    + "\">"
                                    + problemsolution.getBadNum()
                                    + "</SPAN>个)");
                } else {
                    buffer.append("<IMG src=\"/image/flower.gif\"> （得<SPAN>"
                            + problemsolution.getGoodNum() + "</SPAN>支）");
                    buffer
                            .append("<IMG height=15 src=\"/image/badegg.gif\" width=16>（得<SPAN class=orange12>"
                                    + problemsolution.getBadNum() + "</SPAN>个)");
                }
            }

            if (problemsolution.getIsoverdue().equals("N")
                    && userId.intValue() == problemsolution.getCreatorId()
                            .intValue()) {
                // 只有是作者且还没有失效的时候才会出现失效按钮
                buffer
                        .append("<br><IMG height=15 src=\"/image/assoc_delete.gif\" width=16><A id=\"problemsolution_overdue"
                                + problemsolution.getProblemsolutionId()
                                + "\" class=brown12  href=\"javascript:Problemsolution.problemsolutionOverdue("
                                + problemsolution.getProblemId()
                                + ","
                                + problemsolution.getProblemsolutionId()
                                + ","
                                + userId
                                + ")\">失效</A><div id=\"problemsolution_overdue2"
                                + problemsolution.getProblemsolutionId()
                                + "\" style=\"display:none\">您已设置该主贴失效</div>");
            } else if (problemsolution.getIsoverdue().equals("Y")
                    && userId.intValue() == problemsolution.getCreatorId()
                            .intValue()) {
                buffer
                        .append("<br><IMG height=15 src=\"/image/assoc_delete.gif\" width=16>您已设置该主贴失效<br>");
            } else if (problemsolution.getIsoverdue().equals("Y")
                    && userId.intValue() != problemsolution.getCreatorId()
                            .intValue()) {
                buffer
                        .append("<br><IMG height=15 src=\"/image/assoc_delete.gif\" width=16>该作者已设置该主贴失效<br>");
            }
            // main代表回复了主贴，这时候00没有意义，可去除
            buffer
                    .append("&nbsp<A class=brown12  href=\"javascript:ProblemsolutionReply.createReply("
                            + problemsolution.getProblemsolutionId()
                            + ","
                            + "000" + ",'" + "main" + "')\">回复</A>");

        }
        // 解决方案回复
        else if (psr != null) {
            boolean flag = true;
            int j = 0;
            ArrayList psres = psr.getProblemsolutionReplyEvaluations();
            while (flag == true && j < psres.size()) {
                psre = (ProblemsolutionReplyEvaluation) psres.get(j);
                if (psre.getCreatorId().intValue() == userId.intValue()) {
                    flag = false;

                }
                j++;
            }
            /*
             * Problemsolution temp = new Problemsolution();
             * temp.setProblemsolutionId(psr.getProblemsolutionId()); temp =
             * (Problemsolution) sdp.get(temp);
             */
            Problemsolution ps = new Problemsolution();
            ps.setProblemsolutionId(psr.getProblemsolutionId());
            ps = (Problemsolution) sdp.get(ps);
            if (userId.intValue() != psr.getCreatorId().intValue()
                    && psr.getIsoverdue().equals("N") && flag
                    && ps.getIsoverdue() != "Y") {
                // 登陆者不是这个problemsolutionReply的作者,且没有评价过,且这个problemsolutionReply还没有过期,而且这个problemsolution还没有过期
                buffer
                        .append("<IMG src=\"/image/flower.gif\"> <A class=brown12 id=\"problemsolutionreply_flower"
                                + psr.getProblemsolutionReplyId()
                                + "\" href=\"javascript:ProblemsolutionReply.problemsolutionreplyFlower("
                                + psr.getProblemsolutionId()
                                + ","
                                + psr.getProblemsolutionReplyId()
                                + ","
                                + userId
                                + ")\">送鲜花</A>（得<SPAN id=\"problemsolutionreply_flowernum"
                                + psr.getProblemsolutionReplyId()
                                + "\">"
                                + psr.getGoodNum() + "</SPAN>支）");
                buffer
                        .append("<IMG height=15 src=\"/image/badegg.gif\" width=16><A id=\"problemsolutionreply_badegg"
                                + psr.getProblemsolutionReplyId()
                                + "\"class=brown12  href=\"javascript:ProblemsolutionReply.problemsolutionreplyBadegg("
                                + psr.getProblemsolutionId()
                                + ","
                                + psr.getProblemsolutionReplyId()
                                + ","
                                + userId
                                + ")\">扔鸡蛋</A>（得<SPAN class=orange12 id=\"problemsolutionreply_badeggnum"
                                + psr.getProblemsolutionReplyId()
                                + "\">"
                                + psr.getBadNum() + "</SPAN>个)");
            } else {
                buffer.append("<IMG src=\"/image/flower.gif\"> （得<SPAN>"
                        + psr.getGoodNum() + "</SPAN>支）");
                buffer
                        .append("<IMG height=15 src=\"/image/badegg.gif\" width=16>（得<SPAN class=orange12>"
                                + psr.getBadNum() + "</SPAN>个)");
            }

            if (psr.getIsoverdue().equals("N")
                    && userId.intValue() == psr.getCreatorId().intValue()) {
                // 只有是作者且还没有失效的时候才会出现实效按钮
                buffer
                        .append("<br><IMG height=15 src=\"/image/assoc_delete.gif\" width=16><A id=\"problemsolutionreply_overdue"
                                + psr.getProblemsolutionReplyId()
                                + "\" class=brown12  href=\"javascript:ProblemsolutionReply.problemsolutionreplyOverdue("
                                + psr.getProblemsolutionId()
                                + ","
                                + psr.getProblemsolutionReplyId()
                                + ","
                                + userId
                                + ")\">失效</A><span id=\"problemsolutionreply_overdue2"
                                + psr.getProblemsolutionReplyId()
                                + "\" style=\"display:none\">您已设置该回复失效</span>");
            } else if ((psr.getIsoverdue().equals("Y") || ps.getIsoverdue()
                    .equals("Y"))
                    && userId.intValue() != psr.getCreatorId().intValue()) {
                buffer
                        .append("<br><IMG height=15 src=\"/image/assoc_delete.gif\" width=16>该回复已失效");
            } else if (psr.getIsoverdue().equals("Y")
                    && userId.intValue() == psr.getCreatorId().intValue()) {
                buffer
                        .append("<br><IMG height=15 src=\"/image/assoc_delete.gif\" width=16>您已设置该回复失效");
            } else if (ps.getIsoverdue().equals("Y")) {
                buffer
                        .append("<br><IMG height=15 src=\"/image/assoc_delete.gif\" width=16>由于主贴失效，该回复失效");
            }
            buffer
                    .append("&nbsp<A class=brown12  href=\"javascript:ProblemsolutionReply.createReply("
                            + psr.getProblemsolutionId()
                            + ","
                            + psr.getProblemsolutionReplyId()
                            + ",'"
                            + "sub"
                            + "')\">回复</A>");
            if (psr.getProblemsoltuionReplyReferenceId() != null) {
                Long replyRefreneId = psr.getProblemsoltuionReplyReferenceId();
                ProblemsolutionReply problemsolutionreply = new ProblemsolutionReply();
                problemsolutionreply.setProblemsolutionReplyId(replyRefreneId);
                problemsolutionreply = (ProblemsolutionReply) sdp
                        .get(problemsolutionreply);
                buffer.append("<p></p>您回复了：<p></p>");
                buffer.append(problemsolutionreply
                        .getProblemsolutionReplyContent());
            }

        }
        return buffer.toString();

    }

    public StringBuffer createSolution(HttpServletRequest request,
            HttpServletResponse response) throws EasyJException {
        Long problemId = new Long(request.getParameter("problemId"));
        SingleDataProxy sdp = SingleDataProxy.getInstance();
        Problemreason problemreason = new Problemreason();
        ProblemreasonSolution prs = new ProblemreasonSolution();
        problemreason.setProblemId(problemId);
        String solution = "无解决方案";
        ArrayList problemreasons = sdp.query(problemreason);

        StringBuffer buffer = new StringBuffer();
        buffer
                .append("<meta http-equiv=\"Expires\" content=\"0\"><meta http-equiv=\"Pragma\" content=\"no-cache\"><meta http-equiv=\"Cache-Control\" content=\"no-cache\">");
        buffer
                .append("<link href=\"/css/iknow1_1.css\" rel=\"stylesheet\" type=\"text/css\">");
        buffer
                .append("<link href=\"/css/table.css\" rel=\"stylesheet\" type=\"text/css\">");
        buffer
                .append("<link href=\"/css/pattern.css\" rel=\"stylesheet\" type=\"text/css\">");
        buffer
                .append("<link href=\"/css/scenario.css\" rel=\"stylesheet\" type=\"text/css\">");
        buffer
                .append("<script language=\"javascript\" src=\"/js/ProblemsolutionReply.js\"></script>");
        buffer
                .append("<form name=\"form2\" id=\"form2\" method=\"post\" action=\"\" type=\"enter2tab\"><div id=\"newSolution\"></div>");

        buffer.append("<div id=\"center2\"   ><div class=\"bai\">");
        buffer
                .append("<div class=\"mb12 bai\" id=\"solution_more\" ><div class=\"rr_1\"></div><div class=\"rr_2\"></div><div class=\"rr_3\"></div><div class=\"rr\">");
        buffer
                .append("<div class=\"t1\"><div class=\"ico\"><div class=\"ianswer\"></div></div>发表新的解决方案</div>");
        buffer.append("<div class=\"bc0\" style=\"padding:0px 0pt;\">");
        // 产生标题
        buffer
                .append("<div class=\"t2\"><TABLE style=\"MARGIN-TOP: 8px; MARGIN-BOTTOM: 8px\" cellSpacing=0 cellPadding=0 width=\"100%\" align=center border=0><TBODY></TBODY></TABLE><TABLE class=f9 id=newsTable cellSpacing=0 cellPadding=0 width=\"100%\" align=center bgColor=#ffffff border=0><TBODY>");
        buffer
                .append("<TR bgColor=#f3f3f3><TD class=tdline28 noWrap width=\"5%\"></TD><TD class=tdline28 width=\"14%\" height=30><b>序号</b></TD><TD class=tdline28 width=\"1%\">&nbsp;</TD><TD class=tdline28><B>原因概要</B></TD><TD class=tdline28 width=\"15%\"><B>&nbsp;</B></TD></TR>");
        // 产生所有的原因，供选择
        if (problemreasons != null) {
            for (int i = 0; i < problemreasons.size(); i++) {
                solution = "无解决方案";
                // 原因简写部分
                problemreason = (Problemreason) problemreasons.get(i);
                buffer.append("<TR id=trBG" + i + ">");
                buffer
                        .append("<TD class=tdline28 noWrap align=left ><INPUT onclick=\"Problemsolution.changeTRbg(this,'trBG"
                                + i
                                + "')\" type=\"checkbox\" value=\""
                                + problemreason.getProblemreasonId()
                                + "\" name=\"choose_reason\"></TD>");
                buffer.append("<TD class=tdline28 align=left>" + (i + 1)
                        + "</TD>");
                buffer.append("<TD class=tdline28 align=right>&nbsp;</TD>");

                if (problemreason.getProblemreasonContent().length() > 10) {
                    buffer.append("<TD class=tdline28 align=left title="
                            + problemreason.getProblemreasonContent()
                            + ">"
                            + problemreason.getProblemreasonContent()
                                    .subSequence(0, 9) + "...</TD>");
                } else {
                    buffer
                            .append("<TD class=tdline28 align=left title="
                                    + problemreason.getProblemreasonContent()
                                    + ">"
                                    + problemreason.getProblemreasonContent()
                                    + "</TD>");
                }

                buffer
                        .append("<TD class=tdline28 noWrap align=left><A class=\"bluelink\" onclick=; href=\"javascript:Problemsolution.newsShowHide('news"
                                + i + "');\">查看该原因以及解决方案</A></TD></TR>");
                // 原因主要内容（隐藏）
                prs.setProblemreasonId(problemreason.getProblemreasonId());
                ArrayList prss = sdp.query(prs);
                if (prss.size() > 0) {
                    solution = "";
                    for (int j = 0; j < prss.size(); j++) {
                        prs = (ProblemreasonSolution) prss.get(j);
                        solution = solution + prs.getProblemsolutionId();
                    }
                }
                buffer
                        .append("<TR><TD class=tdline28 id=news"
                                + i
                                + " style=\"DISPLAY: none\" noWrap align=left colSpan=5><BR>");
                buffer
                        .append("<TABLE class=zh cellSpacing=0 cellPadding=0 width=\"96%\" align=center border=0><TBODY><TR>");
                buffer.append("<TD><DIV id=MSGcontent" + i
                        + "><div id=\"Lg\"></div>");
                buffer
                        .append("<div class=\"f14 p90 pl10\" class=\"answer_content\" ><span style=\"color:#FF0000\">该原因的详细内容是:"
                                + problemreason.getProblemreasonContent()
                                + "</span></div>");
                buffer.append("<div id=\"Lg\"></div>");
                buffer
                        .append("<div class=\"f14 p90 pl10\" class=\"answer_content\" ><span style=\"color:#FF0000\">原因解决状态:</span>针对该原因的解决方案有<span style=\"color:#FF0000\">"
                                + solution + "</span></div>");
                buffer.append("</DIV><BR></TD>");
                buffer
                        .append("<TD vAlign=top noWrap align=right width=50><FONT id=close0><A class=bluelink onclick=\"Problemsolution.newsShowHide('news"
                                + i + "')\" href=\"#\">关闭</A></FONT></TD>");
                buffer.append("</TR></TBODY></TABLE>");
            }
            buffer.append("<DIV id=rt0></DIV><BR></TD></TR>");
            buffer
                    .append("<TABLE  height=24 cellSpacing=0 cellPadding=0 width=\"100%\" align=center bgColor=#f3f3f3 border=0><TBODY><TR vAlign=center>");
            buffer
                    .append("<TD class=\"pad10L noWrap\"><LABEL for=\"checkall\"><INPUT id=\"checkall\" style=\"MARGIN: 0px\" onclick=Problemsolution.checkAll(this.checked) type=\"checkbox\" value=\"checkbox\" name=\"checkall\">&nbsp;全选</LABEL> &nbsp;&nbsp;</TD>");
            buffer.append("<TD align=middle>&nbsp;&nbsp;</TD>");
            buffer.append("</TR></TBODY></TABLE></TBODY></TABLE></div>");
        } else {
            // 没有原因的时候
        }
        buffer.append("<div class=\"t2\">&nbsp;");
        buffer
                .append("请您描述您的解决方案<br><textarea name=\"solutionContent\" id = \"solutionContent\" cols=\"75%\" rows=\"6\" ></textarea><br><br><div>");
        buffer.append("<div class=\"t2\">&nbsp;");
        buffer
                .append("<a  id=\"\"  href=\"javascript:Problemsolution.submitSolution("
                        + problemId
                        + ")\"  style=\"padding:5px 10px 5px 10px!important;padding:4px 10px 4px 10px;height:1px;border:1px solid#7AA9DF;background-color:#EAF3FC;font-size:12px \">发表</a></div>");
        buffer
                .append("</div></div></div><div class=\"rr_4\"></div><div class=\"rr_5\"></div><div class=\"rr_1\"></div></div>");
        buffer.append("<TABLE><TBODY><TR>");
        buffer
                .append("<TD align=middle><IMG onclick=\"Ajax.loadHistory('pre')\" alt=\"\" src=\"/image/arrow_left.gif\">");
        buffer
                .append("<IMG onclick=\"Ajax.loadHistory('next')\" alt=\"\" src=\"/image/arrow_right.gif\">");
        buffer.append("</TD></TR></TBODY></TABLE>");
        buffer.append("</form>");

        return buffer;

    }

    public String creatingSolution(Problemsolution problemsolution,
            HttpServletRequest request) throws EasyJException {

        SysUserCache userCache = (SysUserCache) request.getSession()
                .getAttribute(Globals.SYS_USER_CACHE);
        Long userId = userCache.getUser().getUserId();
        String name[] = request.getParameterValues("choose_reason");
        String solutionContent = request.getParameter("solutionContent");
        Problem problem = new Problem();
        Problemsolution ps = new Problemsolution();
        ProblemreasonSolution psr = new ProblemreasonSolution();
        SingleDataProxy sdp = SingleDataProxy.getInstance();
        CompositeDataProxy cdp = CompositeDataProxy.getInstance();
        problem.setProblemId(problemsolution.getProblemId());
        Long version = new Long(cdp.getCount(problem));
        Problemsolution ps2 = new Problemsolution();
        try {
            // 需要创建新的problemsolution
            ps.setProblemId(problemsolution.getProblemId());
            ps.setProblemsolutionContent(solutionContent);
            ps.setProblemsolutionProblemId(version);
            // 设置problemsolution表默认字段
            ps.setGoodNum(new Long("0"));
            ps.setBadNum(new Long("0"));
            ps.setProblemChange("N");
            ps.setIsVoting("N");
            ps.setVotingGoodNum(new Long("0"));
            ps.setVotingBadNum(new Long("0"));
            ps.setIsoverdue("N");

            // 创建solution的时候problemversion
            ProblemVersion pv = new ProblemVersion();
            pv.setProblemId(ps.getProblemId());
            Long problemversioncount = sdp.getCount(pv);
            ps.setProblemsolutionProblemId(problemversioncount);
            ps.setCreatorId(userId);
            Object o = sdp.create(ps);
            ps2 = (Problemsolution) o;
            // 需要创建新的problemreasonSolution
            psr.setProblemId(problemsolution.getProblemId());
            psr.setProblemsolutionId(ps2.getProblemsolutionId());
            psr.setCreatorId(userId);
            if (name != null) {
                for (int i = 0; i < name.length; i++) {
                    psr.setProblemreasonId(new Long(name[i]));
                    sdp.create(psr);
                }
            }

            returnMessage = "create_solution_success**";
        } catch (EasyJException e) {
            returnMessage = "create_solution_failure**";
        }
        return returnMessage;

    }
}
