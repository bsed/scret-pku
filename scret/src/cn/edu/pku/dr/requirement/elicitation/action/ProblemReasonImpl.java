package cn.edu.pku.dr.requirement.elicitation.action;

import easyJ.http.servlet.SingleDataAction;
import cn.edu.pku.dr.requirement.elicitation.data.Problemreason;
import javax.servlet.http.HttpServletRequest;
import easyJ.common.EasyJException;
import java.util.ArrayList;
import easyJ.system.data.SysUserCache;
import easyJ.http.Globals;
import easyJ.common.BeanUtil;
import cn.edu.pku.dr.requirement.elicitation.data.ProblemreasonEvaluation;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import easyJ.business.proxy.CompositeDataProxy;
import easyJ.business.proxy.SingleDataProxy;
import cn.edu.pku.dr.requirement.elicitation.data.Ambiguity;
import cn.edu.pku.dr.requirement.elicitation.data.ProblemVersion;
import cn.edu.pku.dr.requirement.elicitation.data.Problem;
import java.util.Collections;
import cn.edu.pku.dr.requirement.elicitation.data.ProblemreasonSolution;

public class ProblemReasonImpl extends SingleDataAction implements
        ProblemReasonInterface {

    public StringBuffer getProblemreason(Problemreason problemreason,
            HttpServletRequest request, boolean vote) throws EasyJException {
        SysUserCache userCache = (SysUserCache) request.getSession()
                .getAttribute(Globals.SYS_USER_CACHE);
        Long userId = userCache.getUser().getUserId();

        // 这个方法输出问题的价值界面
        StringBuffer buffer = new StringBuffer();
        CompositeDataProxy cdp = CompositeDataProxy.getInstance();
        ArrayList problemreasons = cdp.query(problemreason);
        // 用problemvalue或者ambiguity那样用session求也可以
        // 标题
        buffer
                .append("<div class=\"t1\"><div class=\"ico\"><div class=\"icomment\"></div></div>");
        buffer.append("针对问题的价值现有<span style=\"color:#FF0000\">"
                + problemreasons.size() + "</span>个讨论</div>");
        // 下面是用户讨论的页面
        buffer.append("<div class=\"bc0\" style=\"padding:0px 0pt;\">");
        buffer.append("<div class=\"t2\">对于问题存在原因的讨论:</div>");
        buffer
                .append("<a  id=\"problemreason_order2\"  href=\"javascript:Problem.problemreasonOrderByGoodNum("
                        + problemreason.getProblemId()
                        + ")\"  style=\"padding:5px 10px 5px 10px!important;padding:4px 10px 4px 10px;height:1px;border:1px solid#7AA9DF;background-color:#EAF3FC;font-size:12px \">按好评升序</a>");
        buffer
                .append("<div id=\"problemreason_order22\" style=\"display:none\">false</div>");
        buffer.append("<div id=\"viewAllProblemreason\">");
        String good_num = "false";
        Problem problem = new Problem();
        problem.setProblemId(problemreason.getProblemId());
        problem = (Problem) cdp.get(problem);
        ArrayList problemreasons2 = problem.getProblemreasons();
        if (good_num.equals("true")) {
            Collections.sort(problemreasons2);
        }
        String content = "";

        content = getProblemreasonNotByGroup(userId, problemreasons2);
        buffer.append(content);
        // buffer.append("</div>");
        buffer
                .append("<div id=\"Lg\" ></div><div id=\"createProblemReasonDiscuss\"><a  id=\"order1\" href=\"javascript:Problem.createProblemReasonDiscuss("
                        + problemreason.getProblemId()
                        + ")\"  style=\"padding:5px 10px 5px 10px !important;padding:4px 10px 4px 10px;height:1px;border:1px solid #7AA9DF;background-color:#EAF3FC;font-size:12px\">发表评论</a></div>");

        buffer
                .append("<div id=\"Lg\" ></div><div class=\"pl10\"><A class=\"bluelink \" onclick=; href=\"javascript:showAllProblemreason("
                        + problem.getProblemId()
                        + ");\">查看所有关于问题原因的评论</A> </div>");

        return buffer;

    }

    public String creatingProblemReason(Problemreason pr,
            HttpServletRequest request, HttpServletResponse response)
            throws EasyJException, IOException {
        SingleDataProxy sdp = SingleDataProxy.getInstance();

        ProblemVersion pv = new ProblemVersion();
        pv.setProblemId(pr.getProblemId());
        Long problemversioncount = sdp.getCount(pv);
        pr.setProblemreasonEvaluationProblemId(problemversioncount);
        SysUserCache userCache = (SysUserCache) request.getSession()
                .getAttribute(Globals.SYS_USER_CACHE);
        Long userId = userCache.getUser().getUserId();
        pr.setCreatorId(userId);
        pr.setAnonymous("N");
        pr.setGoodNum(new Long("0"));
        pr.setBadNum(new Long("0"));
        pr.setProblemChange("N");
        pr.setIsoverdue("N");
        try {

            sdp.create(pr);
            returnMessage = "create_problemreason_success**"
                    + pr.getProblemreasonContent();
        } catch (EasyJException e) {
            returnMessage = "create_problemreason_failure**";
        }
        return returnMessage;

    }

    public StringBuffer problemreasonUpdate(ProblemreasonEvaluation pre,
            HttpServletRequest request, HttpServletResponse response)
            throws EasyJException, IOException {
        String confirm;
        String overdue;
        CompositeDataProxy cdp = CompositeDataProxy.getInstance();
        SingleDataProxy sdp = SingleDataProxy.getInstance();
        Problemreason pr = new Problemreason();
        pr.setProblemreasonId(pre.getProblemreasonId());
        pr = (Problemreason) cdp.get(pr);
        StringBuffer xml = new StringBuffer("<result>");
        try {
            if (request.getParameter("flower") != null) {

                pr.setGoodNum(new Long(pr.getGoodNum().intValue() + 1));
                cdp.update(pr);
                pre.setIsGood("Y");
                sdp.create(pre);
                xml
                        .append("<message>success</message><choose>flower</choose><problemreasonId>"
                                + pre.getProblemreasonId()
                                + "</problemreasonId></result>");

            }
        } catch (EasyJException e) {
            xml.append("<message>failure</message></result>");
        }
        try {
            if (request.getParameter("badegg") != null) {
                pr.setBadNum(new Long(pr.getBadNum().intValue() + 1));
                cdp.update(pr);
                pre.setIsGood("N");
                sdp.create(pre);
                xml
                        .append("<message>success</message><choose>badegg</choose><problemreasonId>"
                                + pre.getProblemreasonId()
                                + "</problemreasonId></result>");

            }

        } catch (EasyJException e) {
            xml.append("<message>failure</message></result>");
        }

        // 对于确认的处理，暂时只是设置一下用户的选择，以后如果peoblem_version建立之后会有改动，要增加确认对应的版本信息

        if (request.getParameter("confirm") != null) {
            if (request.getParameter("confirm").equals("y")) {
                pr.setProblemChange("C");
                try {
                    cdp.update(pr);
                    xml
                            .append("<message>success</message><choose>confirm</choose><problemreasonId>"
                                    + pre.getProblemreasonId()
                                    + "</problemreasonId></result>");

                } catch (EasyJException e) {
                    xml.append("<message>failure</message></result>");
                }

            } else {
                pr.setProblemChange("D");
                try {

                    cdp.update(pr);
                    xml
                            .append("<message>success</message><choose>reject</choose><problemreasonId>"
                                    + pre.getProblemreasonId()
                                    + "</problemreasonId></result>");

                } catch (EasyJException e) {
                    xml.append("<message>failure</message></result>");
                }

            }
        }
        try {
            if (request.getParameter("overdue") != null) {
                ProblemVersion problemversion = new ProblemVersion();
                problemversion.setProblemId(pr.getProblemId());
                Long problemversioncount = sdp.getCount(problemversion);
                pr.setProblemreasonOverdueVersion(problemversioncount);

                pr.setIsoverdue("Y");
                cdp.update(pr);
                xml
                        .append("<message>success</message><choose>overdue</choose><problemreasonId>"
                                + pre.getProblemreasonId()
                                + "</problemreasonId></result>");

            }
        } catch (EasyJException e) {
            xml.append("<message>failure</message></result>");
        }
        return xml;

    }

    public StringBuffer viewAllProblemreason(Problemreason problemreason,
            HttpServletRequest request) throws EasyJException {
        SysUserCache userCache = (SysUserCache) request.getSession()
                .getAttribute(Globals.SYS_USER_CACHE);
        Long userId = userCache.getUser().getUserId();
        StringBuffer buffer = new StringBuffer();
        String ifNewWindow = new String();
        if (request.getParameter("ifNewWindow") != null) {
            ifNewWindow = request.getParameter("ifNewWindow");
            if (ifNewWindow.equals("Y")) {
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
                buffer.append("<div id=\"center2\"   ><div class=\"bai\" >");
                buffer
                        .append("<div class=\"mb12 bai\" id=\"reason_more\" ><div class=\"rr_1\"></div><div class=\"rr_2\"></div><div class=\"rr_3\"></div><div class=\"rr\">");
                buffer
                        .append("<div class=\"t1\" id=\"problemreason_head\"><div class=\"ico\"><div class=\"icomment\"></div></div>问题原因讨论页面<button  style=\"display:none\" onClick=\"Problem.remark()\" id=\"do_comment\">关闭评论</button></div>");
                buffer.append("<div class=\"bc0\" style=\"padding:0px 0pt;\">");
                buffer
                        .append("<div id=\"problemreason\" style=\"display:block\"><div class=\"t2\">对于问题原因的讨论：</div>");
                buffer
                        .append("<a  id=\"problemreason_order2\" href=\"javascript:Problem.problemreasonOrderByGoodNum("
                                + problemreason.getProblemId()
                                + ")\"  style=\"padding:5px 10px 5px 10px !important;padding:4px 10px 4px 10px;height:1px;border:1px solid #7AA9DF;background-color:#EAF3FC;font-size:12px\">按好评排序</a><div id=\"problemreason_order22\" style=\"display:none\">false</div></div>");
                buffer
                        .append("<div id=\"viewAllProblemreason\" style=\"display:block\">");

            }
        }
        String good_num = request.getParameter("good_num");
        CompositeDataProxy cdp = CompositeDataProxy.getInstance();
        Problem problem = new Problem();
        problem.setProblemId(problemreason.getProblemId());
        problem = (Problem) cdp.get(problem);
        ArrayList problemreasons = problem.getProblemreasons();
        if (good_num.equals("true")) {
            Collections.sort(problemreasons);
        }
        String content = "";

        content = getProblemreasonNotByGroup(userId, problemreasons);

        buffer.append(content);
        if (request.getParameter("ifNewWindow") != null) {
            ifNewWindow = request.getParameter("ifNewWindow");
            if (ifNewWindow.equals("Y")) {
                buffer
                        .append("</div><div class=\"rr_4\"></div><div class=\"rr_5\"></div><div class=\"rr_1\"></div></div>");
                buffer.append("</div></div>");
                buffer.append("<TABLE><TBODY><TR>");
                buffer
                        .append("<TD align=middle><IMG onclick=\"Ajax.loadHistory('pre')\" alt=\"\" src=\"/image/arrow_left.gif\" class=\"hide_todo\">");
                buffer
                        .append("<IMG onclick=\"Ajax.loadHistory('next')\" alt=\"\" src=\"/image/arrow_right.gif\" class=\"hide_todo\">");
                buffer.append("</TD></TR></TBODY></TABLE>");
            }
        } else {
            buffer
                    .append("<div id=\"Lg\" ></div><div id=\"createProblemReasonDiscuss\"><a  id=\"order1\" href=\"javascript:Problem.createProblemReasonDiscuss("
                            + problemreason.getProblemId()
                            + ")\"  style=\"padding:5px 10px 5px 10px !important;padding:4px 10px 4px 10px;height:1px;border:1px solid #7AA9DF;background-color:#EAF3FC;font-size:12px\">发表评论</a></div>");

            buffer
                    .append("<div id=\"Lg\" ></div><div class=\"pl10\"><A class=\"bluelink \" href=\"javascript:showAllProblemreason("
                            + problem.getProblemId()
                            + ");\">查看所有关于问题原因的评论</A> </div>");

        }
        return buffer;

    }

    public String getProblemreasonNotByGroup(Long userId,
            ArrayList problemreasons) throws EasyJException {
        StringBuffer buffer = new StringBuffer();
        ProblemreasonEvaluation pre = new ProblemreasonEvaluation();
        Problemreason pr = new Problemreason();
        SingleDataProxy sdp = SingleDataProxy.getInstance();
        ProblemreasonSolution prs = new ProblemreasonSolution();
        ProblemreasonSolution prs2 = new ProblemreasonSolution();
        buffer.append("<div class=\"bc0\" style=\"padding:0px 0pt;\">");
        String solution = "";
        if (problemreasons.size() > 0) {
            for (int i = 0; i < problemreasons.size(); i++) {
                pr = (Problemreason) problemreasons.get(i);
                prs.setProblemreasonId(pr.getProblemreasonId());
                ArrayList prss = sdp.query(prs);
                if (prss.size() > 0) {
                    for (int j = 0; j < prss.size(); j++) {
                        prs2 = (ProblemreasonSolution) prss.get(j);
                        solution = solution + ","
                                + prs2.getProblemreasonSolutionId();
                    }
                    buffer
                            .append("<div class=\"f14 p90 pl10\" class=\"answer_content\">"
                                    + pr.getProblemreasonContent() + "</div>");
                    buffer.append("<p></p>共有" + sdp.getCount(prs)
                            + "个解决方案，对应的解决方案为" + solution + "<p></p>");
                    solution = "";

                } else {
                    buffer
                            .append("<div id=\"Lg\" ></div><div class=\"f14 p90 pl10\" class=\"answer_content\">"
                                    + pr.getProblemreasonContent()
                                    + "</div><p></p>目前无该原因的解决方案，<A class=brown12  href=\"javascript:Problemsolution.loadNewSolution("
                                    + pr.getProblemId()
                                    + ")\">添加一个？</A><p></p>");

                }

                boolean flag = true;
                int j = 0;
                ArrayList pres = pr.getProblemreasonEvaluations();
                while (flag == true && j < pres.size()) {
                    pre = (ProblemreasonEvaluation) pres.get(j);
                    if (pre.getCreatorId().intValue() == userId.intValue()) {
                        flag = false;

                    }
                    j++;
                }
                if (userId.intValue() != pr.getCreatorId().intValue()
                        && pr.getIsoverdue().equals("N") && flag) {
                    // 登陆者不是这个problemreason的作者,且没有评价过,且这个problemreason还没有过期
                    buffer
                            .append("<IMG src=\"/image/flower.gif\"> <A class=brown12 id=\"problemreason_flower"
                                    + pr.getProblemreasonId()
                                    + "\" href=\"javascript:Problem.problemreasonFlower("
                                    + pr.getProblemId()
                                    + ","
                                    + pr.getProblemreasonId()
                                    + ","
                                    + userId
                                    + ")\">送鲜花</A>（得<SPAN id=\"problemreason_flowernum"
                                    + pr.getProblemreasonId()
                                    + "\">"
                                    + pr.getGoodNum() + "</SPAN>支）");
                    buffer
                            .append("<IMG height=15 src=\"/image/badegg.gif\" width=16><A id=\"problemreason_badegg"
                                    + pr.getProblemreasonId()
                                    + "\"class=brown12  href=\"javascript:Problem.problemreasonBadegg("
                                    + pr.getProblemId()
                                    + ","
                                    + pr.getProblemreasonId()
                                    + ","
                                    + userId
                                    + ")\">扔鸡蛋</A>（得<SPAN class=orange12 id=\"problemreason_badeggnum"
                                    + pr.getProblemreasonId()
                                    + "\">"
                                    + pr.getBadNum() + "</SPAN>个)");
                } else {
                    buffer.append("<IMG src=\"/image/flower.gif\"> （得<SPAN>"
                            + pr.getGoodNum() + "</SPAN>支）");
                    buffer
                            .append("<IMG height=15 src=\"/image/badegg.gif\" width=16>（得<SPAN class=orange12>"
                                    + pr.getBadNum() + "</SPAN>个)");
                }

                if (pr.getIsoverdue().equals("N")
                        && userId.intValue() == pr.getCreatorId().intValue()) {
                    // 只有是作者且还没有失效的时候才会出现实效按钮
                    buffer
                            .append("<br><IMG height=15 src=\"/image/assoc_delete.gif\" width=16><A id=\"problemreason_overdue"
                                    + pr.getProblemreasonId()
                                    + "\" class=brown12  href=\"javascript:Problem.problemreasonOverdue("
                                    + pr.getProblemId()
                                    + ","
                                    + pr.getProblemreasonId()
                                    + ","
                                    + userId
                                    + ")\">失效</A><div id=\"problemreason_overdue2"
                                    + pr.getProblemreasonId()
                                    + "\" style=\"display:none\">您已设置该回复失效</div>");
                } else if (pr.getIsoverdue().equals("Y")
                        && userId.intValue() == pr.getCreatorId().intValue()) {
                    buffer
                            .append("<br><IMG height=15 src=\"/image/assoc_delete.gif\" width=16>您已设置该回复失效");
                } else if (pr.getIsoverdue().equals("Y")
                        && userId.intValue() != pr.getCreatorId().intValue()) {
                    buffer
                            .append("<br><IMG height=15 src=\"/image/assoc_delete.gif\" width=16>该作者已设置该回复失效");
                }

                if (userId.intValue() == pr.getCreatorId().intValue()
                        && pr.getProblemChange().equals("Y")) {
                    buffer
                            .append("<br><IMG height=15 src=\"/image/agree.gif\" width=16><A id=\"problemreason_confirm1"
                                    + pr.getProblemreasonId()
                                    + "\" class=brown12  href=\"javascript:Problem.problemreasonConfirm("
                                    + pr.getProblemId()
                                    + ","
                                    + pr.getProblemreasonId()
                                    + ","
                                    + userId
                                    + ")\">确认</A>&nbsp;&nbsp;<A id=\"problemreason_reject1"
                                    + pr.getProblemreasonId()
                                    + "\" class=brown12  href=\"javascript:Problem.problemreasonReject("
                                    + pr.getProblemId()
                                    + ","
                                    + pr.getProblemreasonId()
                                    + ","
                                    + userId
                                    + ")\">拒绝</A><div id=\"problemreason_confirm2"
                                    + pr.getProblemreasonId()
                                    + "\" style=\"display:none\">您已确认</div><div id=\"problemreason_reject2"
                                    + pr.getProblemreasonId()
                                    + "\" style=\"display:none\">您已拒绝</div>");
                    // 只有是作者
                } else if (pr.getProblemChange().equals("C")
                        && userId.intValue() == pr.getCreatorId().intValue()) {
                    buffer
                            .append("<br><IMG height=15 src=\"/image/agree.gif\" width=16>您已确认");
                } else if (pr.getProblemChange().equals("C")
                        && userId.intValue() != pr.getCreatorId().intValue()) {
                    buffer
                            .append("<br><IMG height=15 src=\"/image/agree.gif\" width=16>该文章作者已确认");
                } else if (pr.getProblemChange().equals("D")
                        && userId.intValue() == pr.getCreatorId().intValue()) {
                    buffer
                            .append("<br><IMG height=15 src=\"/image/agree.gif\" width=16>您已拒绝");
                } else if (pr.getProblemChange().equals("D")
                        && userId.intValue() != pr.getCreatorId().intValue()) {
                    buffer
                            .append("<br><IMG height=15 src=\"/image/agree.gif\" width=16>该文章作者已拒绝");
                }

                buffer
                        .append("<div align=\"right\" style=\"margin: 5px 5px 8px;\" id=\"comment_info\"><span class=\"gray\">评论者:"
                                + pr.getUserName()
                                + " 时间"
                                + pr.getBuildTime()
                                + "</span></div>");
                /*
                 * if(i<problemreasons.size()-1){ buffer.append("<div
                 * id=\"Lg\" ></div>"); }
                 */
            }
        } else {
            buffer
                    .append("<div class=\"f14 p90 pl10\" class=\"answer_content\">尚无评论</div>");
        }
        // buffer.append("<div id=\"Lg\"></div><div class=\"pl10\"><A
        // class=\"bluelink\" onclick=;
        // href=\"javascript:showProblem(<%=problemId%>);\">查看所有评论&gt;&gt;</a></div>");
        return buffer.toString();
    }

}
