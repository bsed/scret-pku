package cn.edu.pku.dr.requirement.elicitation.action;

import cn.edu.pku.dr.requirement.elicitation.data.Problem;
import easyJ.common.EasyJException;
import easyJ.business.proxy.DataProxy;
import easyJ.business.proxy.CompositeDataProxy;
import java.util.ArrayList;
import cn.edu.pku.dr.requirement.elicitation.data.Solution;
import easyJ.http.servlet.SingleDataAction;
import cn.edu.pku.dr.requirement.elicitation.data.Ambiguity;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import easyJ.common.BeanUtil;
import cn.edu.pku.dr.requirement.elicitation.data.ProblemVersion;
import cn.edu.pku.dr.requirement.elicitation.data.AmbiguityTypeValue;
import cn.edu.pku.dr.requirement.elicitation.data.ProblemvalueTypeValue;
import easyJ.system.data.SysUser;
import easyJ.system.data.SysUserCache;
import easyJ.http.Globals;
import easyJ.business.proxy.SingleDataProxy;

public class ProblemImpl extends SingleDataAction implements ProblemInterface {

    // 这个方法得到问题的主体部分，问题版本的问题尚未解决
    public StringBuffer getProblem(Problem problem, boolean modifyProblem,
            boolean modify) throws EasyJException {
        /*
         * 用作测试，看看是否能把问题主体部分放入:) StringBuffer buffer = new StringBuffer();
         * buffer.append("<p>测试看看:)</p>"); buffer.append("<TABLE><TBODY><TR>");
         * buffer.append("<TD align=middle><IMG
         * onclick=\"Ajax.loadHistory('pre')\" alt=\"\"
         * src=\"http://localhost:8080/image/arrow_left.gif\">");
         * buffer.append("<IMG onclick=\"Ajax.loadHistory('next')\" alt=\"\"
         * src=\"http://localhost:8080/image/arrow_right.gif\">");
         * buffer.append("</TD></TR></TBODY></TABLE>"); return buffer;
         */

        // 问题主体部分
        CompositeDataProxy cdp = CompositeDataProxy.getInstance();

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

        buffer.append("<div id=\"center2\" ><div class=\"bai\" >");
        buffer
                .append("<div class=\"mb12 bai\"  style=\"display:none\" id=\"problem_main\"> <div class=\"rg_1\"></div><div class=\"rg_2\"></div><div class=\"rg_3\"></div><div class=\"rg\" id=\"problem\">");
        Problem result = new Problem();
        // ArrayList results = cdp.query(problem);
        result = (Problem) cdp.get(problem);
        ArrayList solutions = result.getSolutions();
        if (result.getProblemStatus().equals("最终解决方案征集中")) {
            buffer
                    .append("<div class=\"t1\" id=\"question_status\"><div class=\"ico\"><div class=\"iwhy\"></div></div>问题状态:"
                            + result.getProblemStatus() + " </div>");

        } else {
            buffer
                    .append("<div class=\"t1\" id=\"question_status\"><div class=\"ico\"><div class=\"iok\"></div></div>问题状态:"
                            + result.getProblemStatus() + " </div>");

        }
        // 读取中该问题的解决方案的总数和正在投票中的解决方案的数目
        int total_num = solutions.size();
        int voting_num = 0;
        Solution solution = new Solution();
        for (int i = 0; i < solutions.size(); i++) {
            solution = (Solution) solutions.get(i);
            if (solution.getIsVoting().equals("Y")) {
                voting_num++;
            }
        }
        buffer
                .append("<div class =\"t1\" id=\"solution_status\">现共有<span style=\"color:#FF0099\">"
                        + total_num
                        + "</span>个解决方案,其中<span style=\"color:#FF0099\">"
                        + voting_num + "</span>个解决方案在投票中</div>");
        // buffer.append("<div class =\"t1\" id=\"solution_status\"><A
        // class=\"bluelink\" onclick=;
        // href=\"javascript:showProblem(<%=problemId%>);\">查看所有评论</A> </div>");
        // 取出消息的主体
        buffer.append("<div class=\"bc0\"><div class=\"p90\">");
        // 标题
        buffer
                .append("<div class=\"f14 B wr\" id=\"question_title\"> <cq>问题标题: <br>  "
                        + result.getProblemTitle() + "</cq></div>");
        // 悬赏分
        buffer
                .append("<div class=\"wr\" id=\"question_info\"><span class=\"award\"></span><span class=\"red\">悬赏分:"
                        + result.getProblemAward() + "</span> </div>");
        // 问题内容
        ProblemVersion problemversion = new ProblemVersion();
        problemversion.setProblemId(result.getProblemId());

        ArrayList problemversions = cdp.query(problemversion);
        problemversion = (ProblemVersion) problemversions.get(0);
        buffer.append("<div class=\"f14 wr\" id=\"question_content\"><cd>"
                + problemversion.getProblemContent() + "</cd></div>");
        // 嵌入两个div，modify_problem和ambiguity_list用于修改问题
        buffer.append("<div class=\"f14 wr\" id=\"modify_problem\"></div>");
        buffer.append("<div class=\"f14 wr\" id=\"ambiguity_list\"></div>");
        buffer.append("<div class=\"f14 wr\" id=\"question_sup\"></div></div>");
        // 加入修改按钮,modifyProblem,false是没有修改权限,true是有修改权限

        if (modifyProblem == true) {
            buffer
                    .append("<div align=\"left\" id=\"modify_problem_button\"><a href=\"javascript:Problem.modifyProblem("
                            + problem.getProblemId()
                            + ")\"  style=\"padding:5px 10px 5px 10px !important;padding:4px 10px 4px 10px;height:1px;border:1px solid #7AA9DF;background-color:#EAF3FC;font-size:12px\">修改问题</a></div>");
        }

        // 得到提问者
        buffer
                .append("<div align=\"right\" class=\"gray wr\" id=\"question_author\" >提问者:<a href=\"#\" target=_blank>"
                        + result.getUserName()
                        + "</a> 时间:"
                        + result.getBuildTime()
                        + "<a href=\"#\"   style=\"display:none\"  target=_blank> - 魔法师 五级</a> </div></div>");

        buffer
                .append("</div><div class=\"rg_4\"></div><div class=\"rg_5\"></div><div class=\"rg_1\"></div></div>");
        buffer.append("<div id=\"tabs\" style=\"display:none\">");
        buffer
                .append("<SPAN class=\"NewsTitleRight\" id=\"AmbiguityRightTab\" style=\"width=120px\" onclick=\"Problem.discussProblemAmbiguity("
                        + problem.getProblemId()
                        + ")\"><SPAN class=NewsTitleLeft id=\"AmbiguityLeftTab\">问题可理解性讨论</SPAN></span>");
        buffer
                .append("<SPAN class=\"NewsTitleRight\" id=\"ProblemValueRightTab\" style=\"width=120px\" onclick=\"Problem.discussProblemValue("
                        + problem.getProblemId()
                        + ")\"><SPAN class=NewsTitleLeft id=\"ProblemValueLeftTab\">问题存在价值讨论</SPAN></SPAN>");
        buffer
                .append("<SPAN class=\"NewsTitleRight\" id=\"ProblemReasonRightTab\" style=\"width=120px\" onclick=\"Problem.discussProblemReason("
                        + problem.getProblemId()
                        + ")\"><SPAN class=NewsTitleLeft id=\"ProblemReasonLeftTab\">问题原因讨论</SPAN></SPAN>");
        buffer
                .append("<SPAN class=\"NewsTitleRight\" id=\"ProblemSolutionRightTab\" style=\"width=120px\" onclick=\"Problem.discussProblemSolution("
                        + problem.getProblemId()
                        + ")\"><SPAN class=NewsTitleLeft id=\"ProblemSolutionLeftTab\">问题解决方案讨论</SPAN></span>");
        buffer
                .append("<span 	id=\"reason\" style=\"display:none\">针对问题的原因有<span style=\"color:#FF0000\">1</span>个讨论</span>");
        buffer
                .append("<span  id=\"explan\" style=\"display:none\">针对问题的可理解性有<span style=\"color:#FF0000\">1</span>个讨论</span>");
        buffer
                .append("<span  id=\"value\" style=\"display:none\">针对问题存在价值有<span style=\"color:#FF0000\">1</span>个讨论</span>");
        buffer
                .append("<span  id=\"solution\" style=\"display:none\">针对问题的解决方案有<span style=\"color:#FF0000\">1</span>个讨论</span></div>");
        // 可理解性开始
        buffer
                .append("<div class=\"mb12 bai\" id=\"ambiguity_more\" style=\"display:none\"><div class=\"rr_1\"></div><div class=\"rr_2\"></div><div class=\"rr_3\"></div><div class=\"rr\"  id=\"ambiguity\">Loading...");
        buffer
                .append("</div><div class=\"rr_4\"></div><div class=\"rr_5\"></div><div class=\"rr_1\"></div></div>");
        // buffer.append("<div class=\"pl10\"><A class=\"bluelink \" onclick=;
        // href=\"javascript:showAllAmbituity("+problem.getProblemId()+");\">查看所有关于可理解性的评论</A>
        // </div><div class=\"rr_4\"></div><div class=\"rr_5\"></div><div
        // class=\"rr_1\"></div></div>");
        // 问题价值开始
        buffer
                .append("<div class=\"mb12 bai\" id=\"value_more\" style=\"display:none\"><div class=\"rr_1\"></div><div class=\"rr_2\"></div><div class=\"rr_3\"></div><div class=\"rr\"  id=\"problemvalue\">Loading...");
        buffer
                .append("</div><div class=\"rr_4\"></div><div class=\"rr_5\"></div><div class=\"rr_1\"></div></div>");
        // buffer.append("<div class=\"pl10\"><A class=\"bluelink \" onclick=;
        // href=\"javascript:showAllProblemvalue("+problem.getProblemId()+");\">查看所有关于问题价值的评论</A>
        // </div><div class=\"rr_4\"></div><div class=\"rr_5\"></div><div
        // class=\"rr_1\"></div></div>");
        // 问题原因开始
        buffer
                .append("<div class=\"mb12 bai\" id=\"reason_more\" style=\"display:none\"><div class=\"rr_1\"></div><div class=\"rr_2\"></div><div class=\"rr_3\"></div><div class=\"rr\"  id=\"problemreason\">Loading...");
        buffer
                .append("</div><div class=\"rr_4\"></div><div class=\"rr_5\"></div><div class=\"rr_1\"></div></div>");
        // buffer.append("<div class=\"pl10\"><A class=\"bluelink \" onclick=;
        // href=\"javascript:showAllProblemreason("+problem.getProblemId()+");\">查看所有关于问题原因的评论</A>
        // </div><div class=\"rr_4\"></div><div class=\"rr_5\"></div><div
        // class=\"rr_1\"></div></div>");
        // 问题解决方案开始
        buffer
                .append("<div class=\"mb12 bai\" id=\"solution_more\" style=\"display:none\"><div class=\"rr_1\"></div><div class=\"rr_2\"></div><div class=\"rr_3\"></div><div class=\"rr\"  id=\"problemsolution\">Loading...");
        buffer
                .append("</div><div class=\"rr_4\"></div><div class=\"rr_5\"></div><div class=\"rr_1\"></div></div>");
        buffer.append("</div></div>");
        buffer.append("<TABLE><TBODY><TR>");
        buffer
                .append("<TD align=middle><IMG onclick=\"Ajax.loadHistory('pre')\" alt=\"\" src=\"/image/arrow_left.gif\" class=\"hide_todo\">");
        buffer
                .append("<IMG onclick=\"Ajax.loadHistory('next')\" alt=\"\" src=\"/image/arrow_right.gif\" class=\"hide_todo\">");
        buffer.append("</TD></TR></TBODY></TABLE>");

        return buffer;

    }

    public StringBuffer getAmbiguity(Problem problem, HttpServletRequest request)
            throws EasyJException {

        String order = request.getParameter("order");
        String group = request.getParameter("group");
        StringBuffer buffer = new StringBuffer();
        CompositeDataProxy cdp = CompositeDataProxy.getInstance();
        Problem result = new Problem();
        result = (Problem) cdp.get(problem);
        ArrayList ambiguities = result.getAmbiguitiys();
        if (order.equals("time")) {
            // 原本就是按照时间排序的
        } else {
            Collections.sort(ambiguities);
        }
        Ambiguity ambiguity = new Ambiguity();
        int allnum = 0;
        int num0 = 0;
        buffer
                .append("<TABLE class=f9 id=newsTable cellSpacing=0 cellPadding=0 width=\"100%\" align=center bgColor=#ffffff border=0><TBODY>");
        buffer
                .append("<TR bgColor=#f3f3f3><TD class=tdline28 noWrap width=\"5%\"></TD> <TD class=tdline28 width=\"14%\" height=30><b>有歧义</b></TD><TD class=tdline28 width=\"1%\">&nbsp;</TD><TD class=tdline28><B>用户疑问概要</B></TD><TD class=tdline28 width=\"15%\"><B>&nbsp;</B></TD></TR>");
        for (int i = 0; i < ambiguities.size(); i++) {
            ambiguity = (Ambiguity) ambiguities.get(i);
            if (ambiguity.getIsoverdue().equals("N")
                    && ambiguity.getAmbiguityTypeId().intValue() == 0) {
                num0++;
                buffer
                        .append("<TR id=trBG"
                                + allnum
                                + "><TD class=tdline28 noWrap align=left ><INPUT onclick=\"Problem.changeTRbg(this,'trBG"
                                + allnum
                                + "')\" type=\"checkbox\" value=\""
                                + ambiguity.getAmbiguityId()
                                + "\" name=\"overdue_ambiguity\"></TD><TD class=tdline28 align=left>"
                                + num0 + "</TD>");
                if (ambiguity.getAmbiguityContent().length() > 10) {
                    buffer
                            .append("<TD class=tdline28 align=right>&nbsp;</TD><TD class=tdline28 align=left >"
                                    + ambiguity.getAmbiguityContent()
                                            .substring(0, 9) + "....</TD>");
                } else {
                    buffer
                            .append("<TD class=tdline28 align=right>&nbsp;</TD><TD class=tdline28 align=left >"
                                    + ambiguity.getAmbiguityContent() + "</TD>");
                }

                buffer
                        .append("<TD class=tdline28 noWrap align=left><A class=\"bluelink \"  href=\"javascript:Problem.newsShowHide('ambiguity"
                                + allnum + "');\">查看详细描述</A></TD></TR> ");
                buffer
                        .append("<TR><TD class=tdline28 id=ambiguity"
                                + allnum
                                + " style=\"DISPLAY: none\" noWrap align=left colSpan=5><BR><TABLE class=zh cellSpacing=0 cellPadding=0 width=\"96%\" align=center border=0><TBODY><TR><TD>");
                buffer
                        .append("<DIV id=MSGcontent"
                                + allnum
                                + "><div id=\"Lg\"></div><div class=\"f14 p90 pl10\" class=\"answer_content\" ><span style=\"color:#FF0000\">详细描述:</span><br>"
                                + ambiguity.getAmbiguityContent());
                buffer
                        .append("<br><IMG src=\"/image/flower.gif\">（共<SPAN id=\"flower_num\">"
                                + ambiguity.getGoodNum() + "</SPAN>支）");
                buffer
                        .append("<IMG height=15 src=\"/image/badegg.gif\" width=16>(共<SPAN class=orange12>"
                                + ambiguity.getBadNum() + "</SPAN>个)");
                buffer
                        .append("</DIV><BR></TD><TD vAlign=top noWrap align=right width=50><FONT id=close0><A class=bluelink onclick=\"Problem.newsShowHide('ambiguity"
                                + allnum
                                + "')\" href=\"#\">关闭</A></FONT></TD></TR></TBODY></TABLE>");
                allnum++;
            }
        }
        if (num0 == 0) {
            buffer
                    .append("<TR ><TD class=tdline28 noWrap width=\"5%\"></TD> <TD class=tdline28 width=\"14%\" height=30><b></b></TD><TD class=tdline28 width=\"1%\">&nbsp;</TD><TD class=tdline28><B>评论已失效或无此类评论</B></TD><TD class=tdline28 width=\"15%\"><B>&nbsp;</B></TD></TR>");
            buffer
                    .append("</TBODY></TABLE><DIV id=rt0></DIV><BR></TD></TR></TBODY></TABLE>");

        } else {
            buffer.append("<DIV id=rt0></DIV><BR></TD></TR></TBODY></TABLE>");
        }

        int num1 = 0;
        buffer
                .append("<TABLE class=f9 id=newsTable cellSpacing=0 cellPadding=0 width=\"100%\" align=center bgColor=#ffffff border=0><TBODY>");
        buffer
                .append("<TR bgColor=#f3f3f3><TD class=tdline28 noWrap width=\"5%\"></TD> <TD class=tdline28 width=\"14%\" height=30><b>不易理解</b></TD><TD class=tdline28 width=\"1%\">&nbsp;</TD><TD class=tdline28><B>用户疑问概要</B></TD><TD class=tdline28 width=\"15%\"><B>&nbsp;</B></TD></TR>");
        for (int i = 0; i < ambiguities.size(); i++) {
            ambiguity = (Ambiguity) ambiguities.get(i);
            if (ambiguity.getIsoverdue().equals("N")
                    && ambiguity.getAmbiguityTypeId().intValue() == 1) {
                num1++;
                buffer
                        .append("<TR id=trBG"
                                + allnum
                                + "><TD class=tdline28 noWrap align=left ><INPUT onclick=\"Problem.changeTRbg(this,'trBG"
                                + allnum
                                + "')\" type=\"checkbox\" value=\""
                                + ambiguity.getAmbiguityId()
                                + "\" name=\"overdue_ambiguity\"></TD><TD class=tdline28 align=left>"
                                + num1 + "</TD>");
                if (ambiguity.getAmbiguityContent().length() > 10) {
                    buffer
                            .append("<TD class=tdline28 align=right>&nbsp;</TD><TD class=tdline28 align=left >"
                                    + ambiguity.getAmbiguityContent()
                                            .substring(0, 9) + "....</TD>");
                } else {
                    buffer
                            .append("<TD class=tdline28 align=right>&nbsp;</TD><TD class=tdline28 align=left >"
                                    + ambiguity.getAmbiguityContent() + "</TD>");
                }

                buffer
                        .append("<TD class=tdline28 noWrap align=left><A class=\"bluelink \"  href=\"javascript:Problem.newsShowHide('ambiguity"
                                + allnum + "');\">查看详细描述</A></TD></TR> ");
                buffer
                        .append("<TR><TD class=tdline28 id=ambiguity"
                                + allnum
                                + " style=\"DISPLAY: none\" noWrap align=left colSpan=5><BR><TABLE class=zh cellSpacing=0 cellPadding=0 width=\"96%\" align=center border=0><TBODY><TR><TD>");
                buffer
                        .append("<DIV id=MSGcontent"
                                + allnum
                                + "><div id=\"Lg\"></div><div class=\"f14 p90 pl10\" class=\"answer_content\" ><span style=\"color:#FF0000\">详细描述:</span><br>"
                                + ambiguity.getAmbiguityContent());
                buffer
                        .append("<br><IMG src=\"/image/flower.gif\">（共<SPAN id=\"flower_num\">"
                                + ambiguity.getGoodNum() + "</SPAN>支）");
                buffer
                        .append("<IMG height=15 src=\"/image/badegg.gif\" width=16>(共<SPAN class=orange12>"
                                + ambiguity.getBadNum() + "</SPAN>个)");
                buffer
                        .append("</DIV><BR></TD><TD vAlign=top noWrap align=right width=50><FONT id=close0><A class=bluelink onclick=\"Problem.newsShowHide('ambiguity"
                                + allnum
                                + "')\" href=\"#\">关闭</A></FONT></TD></TR></TBODY></TABLE>");
                allnum++;
            }
        }
        if (num1 == 0) {
            buffer
                    .append("<TR ><TD class=tdline28 noWrap width=\"5%\"></TD> <TD class=tdline28 width=\"14%\" height=30><b></b></TD><TD class=tdline28 width=\"1%\">&nbsp;</TD><TD class=tdline28><B>评论已失效或无此类评论</B></TD><TD class=tdline28 width=\"15%\"><B>&nbsp;</B></TD></TR>");
            buffer
                    .append("</TBODY></TABLE><DIV id=rt0></DIV><BR></TD></TR></TBODY></TABLE>");

        } else {
            buffer.append("<DIV id=rt0></DIV><BR></TD></TR></TBODY></TABLE>");
        }

        int num2 = 0;
        buffer
                .append("<TABLE class=f9 id=newsTable cellSpacing=0 cellPadding=0 width=\"100%\" align=center bgColor=#ffffff border=0><TBODY>");
        buffer
                .append("<TR bgColor=#f3f3f3><TD class=tdline28 noWrap width=\"5%\"></TD> <TD class=tdline28 width=\"14%\" height=30><b>易理解</b></TD><TD class=tdline28 width=\"1%\">&nbsp;</TD><TD class=tdline28><B>用户疑问概要</B></TD><TD class=tdline28 width=\"15%\"><B>&nbsp;</B></TD></TR>");
        for (int i = 0; i < ambiguities.size(); i++) {
            ambiguity = (Ambiguity) ambiguities.get(i);
            if (ambiguity.getIsoverdue().equals("N")
                    && ambiguity.getAmbiguityTypeId().intValue() == 2) {
                num2++;
                buffer
                        .append("<TR id=trBG"
                                + allnum
                                + "><TD class=tdline28 noWrap align=left ><INPUT onclick=\"Problem.changeTRbg(this,'trBG"
                                + allnum
                                + "')\" type=\"checkbox\" value=\""
                                + ambiguity.getAmbiguityId()
                                + "\" name=\"overdue_ambiguity\"></TD><TD class=tdline28 align=left>"
                                + num2 + "</TD>");
                if (ambiguity.getAmbiguityContent().length() > 10) {
                    buffer
                            .append("<TD class=tdline28 align=right>&nbsp;</TD><TD class=tdline28 align=left >"
                                    + ambiguity.getAmbiguityContent()
                                            .substring(0, 9) + "....</TD>");
                } else {
                    buffer
                            .append("<TD class=tdline28 align=right>&nbsp;</TD><TD class=tdline28 align=left >"
                                    + ambiguity.getAmbiguityContent() + "</TD>");
                }

                buffer
                        .append("<TD class=tdline28 noWrap align=left><A class=\"bluelink \"  href=\"javascript:Problem.newsShowHide('ambiguity"
                                + allnum + "');\">查看详细描述</A></TD></TR> ");
                buffer
                        .append("<TR><TD class=tdline28 id=ambiguity"
                                + allnum
                                + " style=\"DISPLAY: none\" noWrap align=left colSpan=5><BR><TABLE class=zh cellSpacing=0 cellPadding=0 width=\"96%\" align=center border=0><TBODY><TR><TD>");
                buffer
                        .append("<DIV id=MSGcontent"
                                + allnum
                                + "><div id=\"Lg\"></div><div class=\"f14 p90 pl10\" class=\"answer_content\" ><span style=\"color:#FF0000\">详细描述:</span><br>"
                                + ambiguity.getAmbiguityContent());
                buffer
                        .append("<br><IMG src=\"/image/flower.gif\">（共<SPAN id=\"flower_num\">"
                                + ambiguity.getGoodNum() + "</SPAN>支）");
                buffer
                        .append("<IMG height=15 src=\"/image/badegg.gif\" width=16>(共<SPAN class=orange12>"
                                + ambiguity.getBadNum() + "</SPAN>个)");
                buffer
                        .append("</DIV><BR></TD><TD vAlign=top noWrap align=right width=50><FONT id=close0><A class=bluelink onclick=\"Problem.newsShowHide('ambiguity"
                                + allnum
                                + "')\" href=\"#\">关闭</A></FONT></TD></TR></TBODY></TABLE>");
                allnum++;
            }
        }
        if (num2 == 0) {
            buffer
                    .append("<TR ><TD class=tdline28 noWrap width=\"5%\"></TD> <TD class=tdline28 width=\"14%\" height=30><b></b></TD><TD class=tdline28 width=\"1%\">&nbsp;</TD><TD class=tdline28><B>评论已失效或无此类评论</B></TD><TD class=tdline28 width=\"15%\"><B>&nbsp;</B></TD></TR>");
            buffer
                    .append("</TBODY></TABLE><DIV id=rt0></DIV><BR></TD></TR></TBODY></TABLE>");

        } else {
            buffer.append("<DIV id=rt0></DIV><BR></TD></TR></TBODY></TABLE>");
        }
        // 加入后面选择的内容
        buffer
                .append("<TABLE  height=24 cellSpacing=0 cellPadding=0 width=\"100%\" align=center bgColor=#f3f3f3 border=0><TBODY><TR vAlign=center>");
        if (num1 == 0 && num2 == 0 && num0 == 0) {
            buffer.append("<TD class=pad10L noWrap>&nbsp;&nbsp;</TD>");

        } else {
            buffer
                    .append("<TD class=pad10L noWrap><LABEL for=checkall><INPUT id=checkall style=\"MARGIN: 0px\" onclick=Problem.checkAll(this.checked) type=checkbox value=checkbox name=checkall>&nbsp;全选</LABEL> &nbsp;&nbsp;</TD>");

        }
        buffer
                .append("<TD class=pad10L id=adel noWrap align=right >&nbsp;&nbsp;</TD>");
        buffer.append("<TD align=middle>&nbsp;&nbsp;</TD>");
        buffer.append("</TR></TBODY></TABLE>");
        return buffer;
    }

    public String modifyProblem(Problem problem, DataProxy dp,
            HttpServletRequest request) throws EasyJException {
        SysUserCache userCache = (SysUserCache) request.getSession()
                .getAttribute(Globals.SYS_USER_CACHE);
        Long userId = userCache.getUser().getUserId();

        this.dp = dp;
        String name[] = request.getParameterValues("overdue_ambiguity");
        CompositeDataProxy cdp = CompositeDataProxy.getInstance();
        // 将所有的Ambiguity的ProblemChange设置为Y,将所有的AmbiguityEvaluationType中的设置use_state设置为N

        try {
            ProblemVersion pv = new ProblemVersion();
            pv.setProblemId(problem.getProblemId());
            SingleDataProxy sdp = SingleDataProxy.getInstance();
            // todo:需要判定是否有overdue_ambiguity，即name是否为空
            if (name != null) {
                for (int i = 0; i < name.length; i++) {
                    Ambiguity ambiguity = new Ambiguity();

                    ambiguity.setAmbiguityId(new Long(name[i]));
                    ambiguity = (Ambiguity) cdp.get(ambiguity);
                    ambiguity.setProblemChange("Y");
                    ambiguity.setSolvePendingVersion(sdp.getCount(pv));
                    cdp.update(ambiguity);

                }
            }

            AmbiguityTypeValue atv = new AmbiguityTypeValue();
            atv.setProblemId(problem.getProblemId());
            ArrayList atvs = cdp.query(atv);
            for (int j = 0; j < atvs.size(); j++) {
                atv = (AmbiguityTypeValue) atvs.get(j);
                atv.setProblemId(problem.getProblemId());
                atv.setUseState("N");
                dp.update(atv);
            }
            ProblemvalueTypeValue pvtv = new ProblemvalueTypeValue();
            pvtv.setProblemId(problem.getProblemId());
            ArrayList pvtvs = cdp.query(pvtv);
            for (int j = 0; j < pvtvs.size(); j++) {
                pvtv = (ProblemvalueTypeValue) pvtvs.get(j);
                pvtv.setProblemId(problem.getProblemId());
                pvtv.setUseState("N");
                dp.update(pvtv);
            }

            String problemcontent = request.getParameter("problemContent");
            pv.setProblemContent(problemcontent);
            pv.setCreatorId(userId);
            dp.create(pv);

            returnMessage = "modify_problem_success**" + problemcontent;
        } catch (EasyJException e) {
            returnMessage = "modify_problem_failure**";
        }
        return returnMessage;

    }

    public String newProblem(Problem problem, DataProxy dp,
            HttpServletRequest request) throws EasyJException {
        String problemContent = request.getParameter("problemContent");
        SysUserCache userCache = (SysUserCache) request.getSession()
                .getAttribute(Globals.SYS_USER_CACHE);
        Problem newProblem = new Problem();

        Long userId = userCache.getUser().getUserId();
        problem.setCreatorId(userId);
        Short a;
        a = 0;
        problem.setProblemAward(a);
        problem.setStatusId(0);
        problem.setVotingNum(new Long(0));
        ProblemVersion pv = new ProblemVersion();
        pv.setCreatorId(userId);
        pv.setProblemContent(problemContent);
        // problem.setRoleId(roleId);
        this.dp = dp;

        try {
            dp.create(problem);
            SysUser user = new SysUser();
            user.setUserId(userId);
            user = (SysUser) dp.get(user);

            // Problem pro = new Problem();
            // pro.setProblemId(problem.getProblemId());
            // ArrayList pros = dp.query(pro);
            // newProblem = (Problem)pros.get(0);
            pv.setProblemId(problem.getProblemId());
            dp.create(pv);
            StringBuffer buffer = BeanUtil.serializeObjectToClient(problem);
            buffer.append("<message>保存成功");
            returnMessage = buffer.toString();
        } catch (EasyJException e) {
            returnMessage = "new_problem_failure**";
        }
        return returnMessage;

    }

}
