package cn.edu.pku.dr.requirement.elicitation.action;

import easyJ.http.servlet.SingleDataAction;
import cn.edu.pku.dr.requirement.elicitation.data.Problemreason;
import cn.edu.pku.dr.requirement.elicitation.data.Problemvalue;
import javax.servlet.http.HttpServletRequest;
import easyJ.common.EasyJException;
import java.util.ArrayList;
import cn.edu.pku.dr.requirement.elicitation.data.ProblemvalueTypeValue;
import easyJ.business.proxy.SingleDataProxy;
import cn.edu.pku.dr.requirement.elicitation.data.ProblemvalueEvaluation;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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

public class ProblemValueImpl extends SingleDataAction implements
        ProblemValueInterface {

    public StringBuffer getProblemvalue(Problemvalue problemvalue,
            HttpServletRequest request, boolean vote) throws EasyJException {
        // 这个方法输出问题的价值界面
        ProblemvalueTypeValue pvtv = new ProblemvalueTypeValue();
        StringBuffer buffer = new StringBuffer();
        CompositeDataProxy cdp = CompositeDataProxy.getInstance();
        Object o = BeanUtil
                .getEmptyObject("cn.edu.pku.dr.requirement.elicitation.data.Problemvalue");
        Class clazz = o.getClass();
        SelectCommand scmd = DAOFactory.getSelectCommand(clazz);
        Long problemId = problemvalue.getProblemId();
        Filter filter = DAOFactory.getFilter("problemId", SQLOperator.EQUAL,
                problemId);
        scmd.setFilter(filter);
        Session session = SessionFactory.openSession();
        ArrayList problemvalues = session.query(scmd);
        // 标题
        buffer
                .append("<div class=\"t1\"><div class=\"ico\"><div class=\"icomment\"></div></div>");
        buffer.append("对于问题的价值现有<span style=\"color:#FF0000\">"
                + problemvalues.size() + "</span>个讨论</div>");
        // 投票面板,先从缓存中得到目前用户的信息,然后查看ProblemvalueTypeValue表,是否已经投过票了,这里更高的效率应该是用主子表查询
        SysUserCache userCache = (SysUserCache) request.getSession()
                .getAttribute(Globals.SYS_USER_CACHE);
        Long userId = userCache.getUser().getUserId();
        pvtv.setProblemId(problemvalue.getProblemId());
        // evaluation所有人的关于这个问题可理解行的没有过期的投票结果。
        ArrayList evaluation_num = cdp.query(pvtv);
        pvtv.setCreatorId(userId);
        // 当前登陆者关于这个问题可理解行的没有过期的投票结果。
        ArrayList myevaluation = cdp.query(pvtv);
        o = BeanUtil
                .getEmptyObject("cn.edu.pku.dr.requirement.elicitation.data.ProblemvalueTypeValue");
        clazz = o.getClass();
        scmd = DAOFactory.getSelectCommand(clazz);
        Filter filter2 = DAOFactory.getFilter("creatorId", SQLOperator.EQUAL,
                userId);
        scmd.setFilter(filter2);
        // 查询当前用户是否有或者有过评论
        ArrayList myallevaluation = session.query(scmd);
        session.close();
        // todo:有没有问题应该是针对problem版本的，现在是针problemId，以后需要修改
        if (myallevaluation.size() > 0) {
            if (myevaluation.size() == 0) {
                buffer
                        .append("<div class=\"bc0\" style=\"padding:0px 0pt;\" id=\"notyetvoting\"><div class=\"t2\">问题已修改,请重新投票&nbsp;&nbsp;&nbsp;&nbsp;<span class=\"f12 gray\" style=\"font-weight:normal\">目前有 "
                                + evaluation_num.size() + "个人投票</span>");

                // 已经有过评论，当前评论对应着当前版本。如果版本变化，会对ProblemvalueTypeValue中problemId选到的所有的项的use_state置位为N
                buffer.append(displayProblemvalueTypeEvaluating(myevaluation,
                        evaluation_num, problemvalue, userId).toString());
            } else {
                buffer.append(displayProblemvalueTypeEvaluation(myevaluation,
                        evaluation_num, userId).toString());
                // 已有评论，但是当前版本已经过期
            }
        } else {
            // 目前还没有评论
            buffer
                    .append("<div class=\"bc0\" style=\"padding:0px 0pt;\" id=\"notyetvoting\"><div class=\"t2\">您觉得这个问题的价值有多大,请投票&nbsp;&nbsp;&nbsp;&nbsp;<span class=\"f12 gray\" style=\"font-weight:normal\">目前有 "
                            + evaluation_num.size() + "个人投票</span>");

            buffer.append(displayProblemvalueTypeEvaluating(myevaluation,
                    evaluation_num, problemvalue, userId).toString());
        }

        // 下面是用户讨论的页面
        buffer.append("<div class=\"t2\">对于问题具有价值多少的讨论:</div>");
        buffer
                .append("<a  id=\"problemvalue_order1\" href=\"javascript:Problem.problemvalueOrderByGroup("
                        + problemvalue.getProblemId()
                        + ") \"  style=\"padding :5px 10px 5px 10px!important;padding:4px 10px 4px 10px;height:1px;border:1px solid#7AA9DF;background-color:#EAF3FC;font-size:12px \">按分类排序</a>");
        buffer
                .append("<div id=\"problemvalue_order11\" style=\"display:none\">false</div>");
        buffer
                .append("<a  id=\"problemvalue_order2\"  href=\"javascript:Problem.problemvalueOrderByGoodNum("
                        + problemvalue.getProblemId()
                        + ")\"  style=\"padding:5px 10px 5px 10px!important;padding:4px 10px 4px 10px;height:1px;border:1px solid#7AA9DF;background-color:#EAF3FC;font-size:12px \">按好评升序</a>");
        buffer
                .append("<div id=\"problemvalue_order22\" style=\"display:none\">false</div>");
        buffer.append("<div id=\"viewAllProblemvalue\">");
        String group = "false";
        String good_num = "false";
        Problem problem = new Problem();
        problem.setProblemId(problemvalue.getProblemId());
        problem = (Problem) cdp.get(problem);
        ArrayList problemvalues2 = problem.getProblemvalues();
        if (good_num.equals("true")) {
            Collections.sort(problemvalues2);
        }
        String content = "";
        if (group.equals("true")) {
            content = getProblemvalueByGroup(userId, problemvalues2);
        } else {
            content = getProblemvalueNotByGroup(userId, problemvalues2);
        }
        buffer.append(content);
        // buffer.append("</div>");
        buffer
                .append("<div id=\"Lg\" ></div><div id=\"createProblemValueDiscuss\"><a  id=\"order1\" href=\"javascript:Problem.createProblemValueDiscuss("
                        + problemvalue.getProblemId()
                        + ")\"  style=\"padding:5px 10px 5px 10px !important;padding:4px 10px 4px 10px;height:1px;border:1px solid #7AA9DF;background-color:#EAF3FC;font-size:12px\">发表评论</a></div>");

        buffer
                .append("<div id=\"Lg\" ></div><div class=\"pl10\"><A class=\"bluelink \" onclick=; href=\"javascript:showAllProblemvalue("
                        + problem.getProblemId()
                        + ");\">查看所有关于问题价值的评论</A> </div>");

        return buffer;

    }

    public String creatingProblemValue(Problemvalue pv,
            HttpServletRequest request, HttpServletResponse response)
            throws EasyJException, IOException {
        SysUserCache userCache = (SysUserCache) request.getSession()
                .getAttribute(Globals.SYS_USER_CACHE);
        Long userId = userCache.getUser().getUserId();

        SingleDataProxy sdp = SingleDataProxy.getInstance();
        String type0[] = request.getParameterValues("problemavalue_type0");
        String type1[] = request.getParameterValues("problemavalue_type1");
        String type2[] = request.getParameterValues("problemavalue_type2");
        if (type0 != null) {
            pv.setProblemvalueTypeId(new Long("0"));
        } else if (type1 != null) {
            pv.setProblemvalueTypeId(new Long("1"));
        } else if (type2 != null) {
            pv.setProblemvalueTypeId(new Long("2"));
        }
        pv.setCreatorId(userId);
        ProblemVersion problemversion = new ProblemVersion();
        problemversion.setProblemId(pv.getProblemId());
        Long problemversioncount = sdp.getCount(problemversion);
        pv.setProblemvalueEvaluationProblemVersionId(problemversioncount);

        // 设置Problemvalue表的默认字段
        pv.setAnonymous("N");
        pv.setGoodNum(new Long("0"));
        pv.setBadNum(new Long("0"));
        pv.setProblemChange("N");
        pv.setIsoverdue("N");
        // 设置Problemvalue表默认字段结束
        try {

            sdp.create(pv);
            returnMessage = "create_problemvalue_success**"
                    + pv.getProblemvalueContent();
        } catch (EasyJException e) {
            returnMessage = "create_problemvalue_failure**";
        }
        return returnMessage;

    }

    public StringBuffer displayProblemvalueTypeEvaluation(ArrayList evaluation,
            ArrayList evaluation_num, Long userId) {
        int type0 = 0;
        int type1 = 0;
        int type2 = 0;
        ProblemvalueTypeValue pvtv = new ProblemvalueTypeValue();
        StringBuffer buffer = new StringBuffer();

        for (int i = 0; i < evaluation_num.size(); i++) {

            pvtv = (ProblemvalueTypeValue) evaluation_num.get(i);
            if (pvtv.getProblemvalueTypeId().intValue() == 0) {
                type0++;
            } else if (pvtv.getProblemvalueTypeId().intValue() == 1) {
                type1++;
            } else if (pvtv.getProblemvalueTypeId().intValue() == 2) {
                type2++;
            }

        }
        pvtv = (ProblemvalueTypeValue) evaluation.get(evaluation.size() - 1);
        buffer
                .append("<div class=\"bc0\" style=\"padding:0px 0pt;\"><div style=\"font-size:14px;padding-left:10px;height:24px;line-height:24px;border-top:1px solid #FED6D2;\">您的评价是：<span style=\"color:#FF0000;font-weight:bold\">"
                        + pvtv.getProblemvalueContent() + "</span>");
        buffer
                .append("<div  style=\"font-size:14px\" > 目前有 <span style=\"color:#FF0000;font-weight:bold\">"
                        + evaluation_num.size()
                        + "</span>个人评价:<br>有<span style=\"color:#FF0000;font-weight:bold\">"
                        + type0
                        + "</span>个人认为<span style=\"color:#FF0000;font-weight:bold\">无价值</span>,有<span style=\"color:#FF0000;font-weight:bold\">"
                        + type1
                        + "</span>个人认为<span style=\"color:#FF0000;font-weight:bold\">一般价值</span,有<span style=\"color:#FF0000;font-weight:bold\">"
                        + type2
                        + "</span>个人认为<span style=\"color:#FF0000;font-weight:bold\">非常有价值</span></div></div>");
        return buffer;

    }

    public StringBuffer displayProblemvalueTypeEvaluating(ArrayList evaluation,
            ArrayList evaluation_num, Problemvalue problemvalue, Long userId) {
        int type0 = 0;
        int type1 = 0;
        int type2 = 0;
        ProblemvalueTypeValue pvtv = new ProblemvalueTypeValue();
        StringBuffer buffer = new StringBuffer();

        for (int i = 0; i < evaluation_num.size(); i++) {

            pvtv = (ProblemvalueTypeValue) evaluation_num.get(i);
            if (pvtv.getProblemvalueTypeId().intValue() == 0) {
                type0++;
            } else if (pvtv.getProblemvalueTypeId().intValue() == 1) {
                type1++;
            } else if (pvtv.getProblemvalueTypeId().intValue() == 2) {
                type2++;
            }

        }
        buffer
                .append("<table  border=\"0\" cellpadding=\"0\" cellspacing=\"0\"><tbody><tr>");
        buffer
                .append("<td width=\"120\" class=\"f14\"><a href=\"javascript:Problem.problemvalueEvaluation(2,"
                        + userId
                        + ","
                        + problemvalue.getProblemId()
                        + ")\"  style=\"padding:5px 10px 5px 10px !important;padding:4px 10px 4px 10px;height:1px;border:1px solid #7AA9DF;background-color:#EAF3FC;font-size:12px\">非常有价值</a><br>共<span class=\"red\" id=\"type2\">"
                        + type0 + "</span>票</td>");
        buffer
                .append("<td width=\"120\" class=\"f14\"><a href=\"javascript:Problem.problemvalueEvaluation(1,"
                        + userId
                        + ","
                        + problemvalue.getProblemId()
                        + ")\"  style=\"padding:5px 10px 5px 10px !important;padding:4px 10px 4px 10px;height:1px;border:1px solid #7AA9DF;background-color:#EAF3FC;font-size:12px\">具有一般价值</a><br>共<span class=\"red\" id=\"type1\">"
                        + type1 + "</span>票</td>");
        buffer
                .append("<td width=\"120\" class=\"f14\"><a href=\"javascript:Problem.problemvalueEvaluation(0,"
                        + userId
                        + ","
                        + problemvalue.getProblemId()
                        + ")\"  style=\"padding:5px 10px 5px 10px !important;padding:4px 10px 4px 10px;height:1px;border:1px solid #7AA9DF;background-color:#EAF3FC;font-size:12px\">无价值</a><br>共<span class=\"red\" id=\"type0\">"
                        + type2 + "</span>票</td>");
        buffer.append("</tr></tbody></table></div>");
        return buffer;
    }

    public String problemvalueTypeEvaluation(ProblemvalueTypeValue pvtv)
            throws EasyJException {
        this.dp = dp;
        SingleDataProxy sdp = SingleDataProxy.getInstance();

        try {
            sdp.create(pvtv);
            returnMessage = "problemvalueTypeEvaluation_success**"
                    + pvtv.getProblemId().toString();
        } catch (EasyJException e) {
            returnMessage = "problemvalueTypeEvaluation_failure**";
        }
        return returnMessage;
    }

    public StringBuffer problemvalueUpdate(ProblemvalueEvaluation pve,
            HttpServletRequest request, HttpServletResponse response)
            throws EasyJException, IOException {
        String confirm;
        String overdue;
        CompositeDataProxy cdp = CompositeDataProxy.getInstance();
        SingleDataProxy sdp = SingleDataProxy.getInstance();
        Problemvalue pv = new Problemvalue();
        pv.setProblemvalueId(pve.getProblemvalueId());
        pv = (Problemvalue) cdp.get(pv);
        StringBuffer xml = new StringBuffer("<result>");
        try {
            if (request.getParameter("flower") != null) {

                pv.setGoodNum(new Long(pv.getGoodNum().intValue() + 1));
                cdp.update(pv);
                pve.setIsGood("Y");
                sdp.create(pve);
                xml
                        .append("<message>success</message><choose>flower</choose><problemvalueId>"
                                + pve.getProblemvalueId()
                                + "</problemvalueId></result>");

            }
        } catch (EasyJException e) {
            xml.append("<message>failure</message></result>");
        }
        try {
            if (request.getParameter("badegg") != null) {
                pv.setBadNum(new Long(pv.getBadNum().intValue() + 1));
                cdp.update(pv);
                pve.setIsGood("N");
                sdp.create(pve);
                xml
                        .append("<message>success</message><choose>badegg</choose><problemvalueId>"
                                + pve.getProblemvalueId()
                                + "</problemvalueId></result>");

            }

        } catch (EasyJException e) {
            xml.append("<message>failure</message></result>");
        }

        // 对于确认的处理，暂时只是设置一下用户的选择，以后如果peoblem_version建立之后会有改动，要增加确认对应的版本信息

        if (request.getParameter("confirm") != null) {
            if (request.getParameter("confirm").equals("y")) {
                pv.setProblemChange("C");
                try {
                    cdp.update(pv);
                    xml
                            .append("<message>success</message><choose>confirm</choose><problemvalueId>"
                                    + pve.getProblemvalueId()
                                    + "</problemvalueId></result>");

                } catch (EasyJException e) {
                    xml.append("<message>failure</message></result>");
                }

            } else {
                pv.setProblemChange("D");
                try {

                    cdp.update(pv);
                    xml
                            .append("<message>success</message><choose>reject</choose><problemvalueId>"
                                    + pve.getProblemvalueId()
                                    + "</problemvalueId></result>");

                } catch (EasyJException e) {
                    xml.append("<message>failure</message></result>");
                }

            }
        }
        try {
            if (request.getParameter("overdue") != null) {
                ProblemVersion problemversion = new ProblemVersion();
                problemversion.setProblemId(pv.getProblemId());
                Long problemversioncount = sdp.getCount(problemversion);
                pv.setProblemvalueOverdueVersion(problemversioncount);

                pv.setIsoverdue("Y");
                cdp.update(pv);
                xml
                        .append("<message>success</message><choose>overdue</choose><problemvalueId>"
                                + pve.getProblemvalueId()
                                + "</problemvalueId></result>");

            }
        } catch (EasyJException e) {
            xml.append("<message>failure</message></result>");
        }
        return xml;

    }

    public StringBuffer viewAllProblemvalue(Problemvalue problemvalue,
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
                        .append("<div class=\"mb12 bai\" id=\"value_more\" ><div class=\"rr_1\"></div><div class=\"rr_2\"></div><div class=\"rr_3\"></div><div class=\"rr\">");
                buffer
                        .append("<div class=\"t1\" id=\"problemvalue_head\"><div class=\"ico\"><div class=\"icomment\"></div></div>问题价值讨论页面<button  style=\"display:none\" onClick=\"Problem.remark()\" id=\"do_comment\">关闭评论</button></div>");
                buffer.append("<div class=\"bc0\" style=\"padding:0px 0pt;\">");
                buffer
                        .append("<div id=\"problemvalue\" style=\"display:block\"><div class=\"t2\">对于问题价值的详细解释：</div><a  id=\"problemvalue_order1\" href=\"javascript:Problem.problemvalueOrderByGroup("
                                + problemvalue.getProblemId()
                                + ")\"  style=\"padding:5px 10px 5px 10px !important;padding:4px 10px 4px 10px;height:1px;border:1px solid #7AA9DF;background-color:#EAF3FC;font-size:12px\">按分类排序</a><div id=\"problemvalue_order11\" style=\"display:none\">false</div>");
                buffer
                        .append("<a  id=\"problemvalue_order2\" href=\"javascript:Problem.problemvalueOrderByGoodNum("
                                + problemvalue.getProblemId()
                                + ")\"  style=\"padding:5px 10px 5px 10px !important;padding:4px 10px 4px 10px;height:1px;border:1px solid #7AA9DF;background-color:#EAF3FC;font-size:12px\">按好评排序</a><div id=\"problemvalue_order22\" style=\"display:none\">false</div></div>");
                buffer
                        .append("<div id=\"viewAllProblemvalue\" style=\"display:block\">");

            }
        }
        String group = request.getParameter("group");
        String good_num = request.getParameter("good_num");
        CompositeDataProxy cdp = CompositeDataProxy.getInstance();
        Problem problem = new Problem();
        problem.setProblemId(problemvalue.getProblemId());
        problem = (Problem) cdp.get(problem);
        ArrayList problemvalues = problem.getProblemvalues();
        if (good_num.equals("true")) {
            Collections.sort(problemvalues);
        }
        String content = "";
        if (group.equals("true")) {
            content = getProblemvalueByGroup(userId, problemvalues);
        } else {
            content = getProblemvalueNotByGroup(userId, problemvalues);
        }
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
                    .append("<div id=\"Lg\" ></div><div id=\"createProblemValueDiscuss\"><a  id=\"order1\" href=\"javascript:Problem.createProblemValueDiscuss("
                            + problemvalue.getProblemId()
                            + ")\"  style=\"padding:5px 10px 5px 10px !important;padding:4px 10px 4px 10px;height:1px;border:1px solid #7AA9DF;background-color:#EAF3FC;font-size:12px\">发表评论</a></div>");

            buffer
                    .append("<div id=\"Lg\" ></div><div class=\"pl10\"><A class=\"bluelink \" onclick=; href=\"javascript:showAllProblemvalue("
                            + problem.getProblemId()
                            + ");\">查看所有关于问题价值的评论</A> </div>");

        }

        return buffer;

    }

    public String getProblemvalueByGroup(Long userId, ArrayList problemvalues) {
        StringBuffer buffer = new StringBuffer();
        Problemvalue pv = new Problemvalue();
        ArrayList type2 = new ArrayList();
        ArrayList type1 = new ArrayList();
        ArrayList type0 = new ArrayList();
        for (int i = 0; i < problemvalues.size(); i++) {
            pv = (Problemvalue) problemvalues.get(i);
            if (pv.getProblemvalueTypeId().intValue() == 2) {
                type2.add(pv);
            } else if (pv.getProblemvalueTypeId().intValue() == 1) {
                type1.add(pv);
            } else if (pv.getProblemvalueTypeId().intValue() == 0) {
                type0.add(pv);
            }
        }
        buffer
                .append("<div class=\"t2\"><span style=\"color:#FF0000\">分类:无价值</span></div>");
        buffer.append(getProblemvalueNotByGroup(userId, type0));
        buffer
                .append("<div class=\"t2\"><span style=\"color:#FF0000\">分类:一般价值</span></div>");
        buffer.append(getProblemvalueNotByGroup(userId, type1));
        buffer
                .append("<div class=\"t2\"><span style=\"color:#FF0000\">分类:非常有价值</span></div>");
        buffer.append(getProblemvalueNotByGroup(userId, type2));

        return buffer.toString();
    }

    public String getProblemvalueNotByGroup(Long userId, ArrayList problemvalues) {
        StringBuffer buffer = new StringBuffer();
        ProblemvalueEvaluation pve = new ProblemvalueEvaluation();
        Problemvalue pv = new Problemvalue();
        if (problemvalues.size() > 0) {
            for (int i = 0; i < problemvalues.size(); i++) {
                pv = (Problemvalue) problemvalues.get(i);
                buffer
                        .append("<div class=\"f14 p90 pl10\" class=\"answer_content\">"
                                + pv.getProblemvalueContent() + "</div>");
                boolean flag = true;
                int j = 0;
                ArrayList pves = pv.getProblemvalueEvaluations();
                while (flag == true && j < pves.size()) {
                    pve = (ProblemvalueEvaluation) pves.get(j);
                    if (pve.getCreatorId().intValue() == userId.intValue()) {
                        flag = false;

                    }
                    j++;
                }
                if (userId.intValue() != pv.getCreatorId().intValue()
                        && pv.getIsoverdue().equals("N") && flag) {
                    // 登陆者不是这个problemvalue的作者,且没有评价过,且这个problemvalue还没有过期
                    buffer
                            .append("<IMG src=\"/image/flower.gif\"> <A class=brown12 id=\"problemvalue_flower"
                                    + pv.getProblemvalueId()
                                    + "\" href=\"javascript:Problem.problemvalueFlower("
                                    + pv.getProblemId()
                                    + ","
                                    + pv.getProblemvalueId()
                                    + ","
                                    + userId
                                    + ")\">送鲜花</A>（得<SPAN id=\"problemvalue_flowernum"
                                    + pv.getProblemvalueId()
                                    + "\">"
                                    + pv.getGoodNum() + "</SPAN>支）");
                    buffer
                            .append("<IMG height=15 src=\"/image/badegg.gif\" width=16><A id=\"problemvalue_badegg"
                                    + pv.getProblemvalueId()
                                    + "\"class=brown12  href=\"javascript:Problem.problemvalueBadegg("
                                    + pv.getProblemId()
                                    + ","
                                    + pv.getProblemvalueId()
                                    + ","
                                    + userId
                                    + ")\">扔鸡蛋</A>（得<SPAN class=orange12 id=\"problemvalue_badeggnum"
                                    + pv.getProblemvalueId()
                                    + "\">"
                                    + pv.getBadNum() + "</SPAN>个)");
                } else {
                    buffer.append("<IMG src=\"/image/flower.gif\"> （得<SPAN>"
                            + pv.getGoodNum() + "</SPAN>支）");
                    buffer
                            .append("<IMG height=15 src=\"/image/badegg.gif\" width=16>（得<SPAN class=orange12>"
                                    + pv.getBadNum() + "</SPAN>个)");
                }

                if (pv.getIsoverdue().equals("N")
                        && userId.intValue() == pv.getCreatorId().intValue()) {
                    // 只有是作者且还没有失效的时候才会出现实效按钮
                    buffer
                            .append("<br><IMG height=15 src=\"/image/assoc_delete.gif\" width=16><A id=\"problemvalue_overdue"
                                    + pv.getProblemvalueId()
                                    + "\" class=brown12  href=\"javascript:Problem.problemvalueOverdue("
                                    + pv.getProblemId()
                                    + ","
                                    + pv.getProblemvalueId()
                                    + ","
                                    + userId
                                    + ")\">失效</A><div id=\"problemvalue_overdue2"
                                    + pv.getProblemvalueId()
                                    + "\" style=\"display:none\">您已设置该回复失效</div>");
                } else if (pv.getIsoverdue().equals("Y")
                        && userId.intValue() == pv.getCreatorId().intValue()) {
                    buffer
                            .append("<br><IMG height=15 src=\"/image/assoc_delete.gif\" width=16>您已设置该回复失效");
                } else if (pv.getIsoverdue().equals("Y")
                        && userId.intValue() != pv.getCreatorId().intValue()) {
                    buffer
                            .append("<br><IMG height=15 src=\"/image/assoc_delete.gif\" width=16>该作者已设置该回复失效");
                }

                if (userId.intValue() == pv.getCreatorId().intValue()
                        && pv.getProblemChange().equals("Y")) {
                    buffer
                            .append("<br><IMG height=15 src=\"/image/agree.gif\" width=16><A id=\"problemvalue_confirm1"
                                    + pv.getProblemvalueId()
                                    + "\" class=brown12  href=\"javascript:Problem.problemvalueConfirm("
                                    + pv.getProblemId()
                                    + ","
                                    + pv.getProblemvalueId()
                                    + ","
                                    + userId
                                    + ")\">确认</A>&nbsp;&nbsp;<A id=\"problemvalue_reject1"
                                    + pv.getProblemvalueId()
                                    + "\" class=brown12  href=\"javascript:Problem.problemvalueReject("
                                    + pv.getProblemId()
                                    + ","
                                    + pv.getProblemvalueId()
                                    + ","
                                    + userId
                                    + ")\">拒绝</A><div id=\"problemvalue_confirm2"
                                    + pv.getProblemvalueId()
                                    + "\" style=\"display:none\">您已确认</div><div id=\"problemvalue_reject2"
                                    + pv.getProblemvalueId()
                                    + "\" style=\"display:none\">您已拒绝</div>");
                    // 只有是作者
                } else if (pv.getProblemChange().equals("C")
                        && userId.intValue() == pv.getCreatorId().intValue()) {
                    buffer
                            .append("<br><IMG height=15 src=\"/image/agree.gif\" width=16>您已确认");
                } else if (pv.getProblemChange().equals("C")
                        && userId.intValue() != pv.getCreatorId().intValue()) {
                    buffer
                            .append("<br><IMG height=15 src=\"/image/agree.gif\" width=16>该文章作者已确认");
                } else if (pv.getProblemChange().equals("D")
                        && userId.intValue() == pv.getCreatorId().intValue()) {
                    buffer
                            .append("<br><IMG height=15 src=\"/image/agree.gif\" width=16>您已拒绝");
                } else if (pv.getProblemChange().equals("D")
                        && userId.intValue() != pv.getCreatorId().intValue()) {
                    buffer
                            .append("<br><IMG height=15 src=\"/image/agree.gif\" width=16>该文章作者已拒绝");
                }

                buffer
                        .append("<div align=\"right\" style=\"margin: 5px 5px 8px;\" id=\"comment_info\"><span class=\"gray\">评论者:"
                                + pv.getUserName()
                                + " 时间:"
                                + pv.getBuildTime()
                                + "</span></div>");
                if (i < problemvalues.size() - 1) {
                    buffer.append("<div id=\"Lg\" ></div>");

                }
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
