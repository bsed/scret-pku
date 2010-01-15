package cn.edu.pku.dr.requirement.elicitation.action;

import java.util.*;
import javax.servlet.http.*;

import cn.edu.pku.dr.requirement.elicitation.data.*;
import easyJ.business.proxy.*;
import easyJ.common.*;
import easyJ.database.dao.*;
import easyJ.database.dao.command.*;
import easyJ.database.session.*;
import easyJ.http.*;
import easyJ.http.servlet.*;
import easyJ.system.data.*;
import cn.edu.pku.dr.requirement.elicitation.data.Ambiguity;
import java.io.IOException;

public class AmbiguityImpl extends SingleDataAction implements
        AmbiguityInterface {

    public StringBuffer getAmbiguity(Ambiguity ambiguity,
            HttpServletRequest request, boolean vote) throws EasyJException {
        // 这个方法输出问题的可理解性界面
        AmbiguityTypeValue atv = new AmbiguityTypeValue();
        StringBuffer buffer = new StringBuffer();
        CompositeDataProxy cdp = CompositeDataProxy.getInstance();
        Object o = BeanUtil
                .getEmptyObject("cn.edu.pku.dr.requirement.elicitation.data.Ambiguity");
        Class clazz = o.getClass();
        SelectCommand scmd = DAOFactory.getSelectCommand(clazz);
        Long problemId = ambiguity.getProblemId();
        Filter filter = DAOFactory.getFilter("problemId", SQLOperator.EQUAL,
                problemId);
        scmd.setFilter(filter);
        Session session = SessionFactory.openSession();
        ArrayList ambiguitys = session.query(scmd);
        // 标题
        buffer
                .append("<div class=\"t1\"><div class=\"ico\"><div class=\"icomment\"></div></div>");
        buffer.append("针对问题的可理解性现有<span style=\"color:#FF0000\">"
                + ambiguitys.size() + "</span>个讨论</div>");
        // 投票面板,先从缓存中得到目前用户的信息,然后查看AmbiguityTypeValue表,是否已经投过票了,这里更高的效率应该是用主子表查询
        SysUserCache userCache = (SysUserCache) request.getSession()
                .getAttribute(Globals.SYS_USER_CACHE);
        Long userId = userCache.getUser().getUserId();
        atv.setProblemId(ambiguity.getProblemId());
        // evaluation所有人的关于这个问题可理解行的没有过期的投票结果。
        ArrayList evaluation_num = cdp.query(atv);
        atv.setCreatorId(userId);
        // 当前登陆者关于这个问题可理解行的没有过期的投票结果。
        ArrayList myevaluation = cdp.query(atv);
        o = BeanUtil
                .getEmptyObject("cn.edu.pku.dr.requirement.elicitation.data.AmbiguityTypeValue");
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

                // 已经有过评论，当前评论对应着当前版本。如果版本变化，会对AmbiguityTypeValue中problemId选到的所有的项的use_state置位为N
                buffer.append(displayAmbiguityTypeEvaluating(myevaluation,
                        evaluation_num, ambiguity, userId).toString());
            } else {
                buffer.append(displayAmbiguityTypeEvaluation(myevaluation,
                        evaluation_num, userId).toString());
            }
        } else {
            // 目前还没有评论
            buffer
                    .append("<div class=\"bc0\" style=\"padding:0px 0pt;\" id=\"notyetvoting\"><div class=\"t2\">您觉得这个问题有没有描述清楚,请投票&nbsp;&nbsp;&nbsp;&nbsp;<span class=\"f12 gray\" style=\"font-weight:normal\">目前有 "
                            + evaluation_num.size() + "个人投票</span>");

            buffer.append(displayAmbiguityTypeEvaluating(myevaluation,
                    evaluation_num, ambiguity, userId).toString());
        }
        // 下面是用户讨论的页面
        buffer.append("<div class=\"t2\">对于问题是否有可理解性的讨论:</div>");
        buffer
                .append("<a  id=\"order1\" href=\"javascript:Problem.orderByGroup("
                        + ambiguity.getProblemId()
                        + ") \"  style=\"padding :5px 10px 5px 10px!important;padding:4px 10px 4px 10px;height:1px;border:1px solid#7AA9DF;background-color:#EAF3FC;font-size:12px \">按分类排序</a>");
        buffer.append("<div id=\"order11\" style=\"display:none\">false</div>");
        buffer
                .append("<a  id=\"order2\"  href=\"javascript:Problem.orderByGoodNum("
                        + ambiguity.getProblemId()
                        + ")\"  style=\"padding:5px 10px 5px 10px!important;padding:4px 10px 4px 10px;height:1px;border:1px solid#7AA9DF;background-color:#EAF3FC;font-size:12px \">按好评升序</a>");
        buffer.append("<div id=\"order22\" style=\"display:none\">false</div>");
        buffer.append("<div id=\"viewAll\">");
        String group = "false";
        String good_num = "false";
        Problem problem = new Problem();
        problem.setProblemId(ambiguity.getProblemId());
        problem = (Problem) cdp.get(problem);
        ArrayList ambiguities = problem.getAmbiguitiys();
        if (good_num.equals("true")) {
            Collections.sort(ambiguities);
        }
        String content = "";
        if (group.equals("true")) {
            content = getAmbiguityByGroup(userId, ambiguities);
        } else {
            content = getAmbiguityNotByGroup(userId, ambiguities);
        }
        buffer.append(content);
        // buffer.append("</div>");
        buffer
                .append("<div id=\"Lg\" ></div><div id=\"createAmbiguityDiscuss\"><a  id=\"order1\" href=\"javascript:Problem.createAmbiguityDiscuss("
                        + ambiguity.getProblemId()
                        + ")\"  style=\"padding:5px 10px 5px 10px !important;padding:4px 10px 4px 10px;height:1px;border:1px solid #7AA9DF;background-color:#EAF3FC;font-size:12px\">发表评论</a></div>");
        buffer
                .append("<div id=\"Lg\" ></div><div class=\"pl10\"><A class=\"bluelink \" onclick=; href=\"javascript:showAllAmbituity("
                        + problem.getProblemId()
                        + ");\">查看所有关于可理解性的评论</A> </div>");

        return buffer;

    }

    public StringBuffer displayAmbiguityTypeEvaluation(ArrayList evaluation,
            ArrayList evaluation_num, Long userId) {
        int type0 = 0;
        int type1 = 0;
        int type2 = 0;
        AmbiguityTypeValue atv = new AmbiguityTypeValue();
        StringBuffer buffer = new StringBuffer();

        for (int i = 0; i < evaluation_num.size(); i++) {

            atv = (AmbiguityTypeValue) evaluation_num.get(i);
            if (atv.getAmbiguityTypeId().intValue() == 0) {
                type0++;
            } else if (atv.getAmbiguityTypeId().intValue() == 1) {
                type1++;
            } else if (atv.getAmbiguityTypeId().intValue() == 2) {
                type2++;
            };

        }
        atv = (AmbiguityTypeValue) evaluation.get(evaluation.size() - 1);
        buffer
                .append("<div class=\"bc0\" style=\"padding:0px 0pt;\"><div style=\"font-size:14px;padding-left:10px;line-height:24px;border-top:1px solid #FED6D2;\">您的评价是：<span style=\"color:#FF0000;font-weight:bold\">"
                        + atv.getAmbiguityContent() + "</span>");
        buffer
                .append("<div  style=\"font-size:14px\" > 目前有<span style=\"color:#FF0000;font-weight:bold\"> "
                        + evaluation_num.size()
                        + "</span>个人评价:<br>有<span style=\"color:#FF0000;font-weight:bold\">"
                        + type0
                        + "</span>个人认为<span style=\"color:#FF0000;font-weight:bold\">有歧义</span>,有<span style=\"color:#FF0000;font-weight:bold\">"
                        + type1
                        + "</span>个人认为<span style=\"color:#FF0000;font-weight:bold\">不易理解</span>,有<span style=\"color:#FF0000;font-weight:bold\">"
                        + type2
                        + "</span>个人认为<span style=\"color:#FF0000;font-weight:bold\">容易理解</span></div></div>");
        return buffer;

    }

    public StringBuffer displayAmbiguityTypeEvaluating(ArrayList evaluation,
            ArrayList evaluation_num, Ambiguity ambiguity, Long userId) {
        int type0 = 0;
        int type1 = 0;
        int type2 = 0;
        AmbiguityTypeValue atv = new AmbiguityTypeValue();
        StringBuffer buffer = new StringBuffer();

        for (int i = 0; i < evaluation_num.size(); i++) {

            atv = (AmbiguityTypeValue) evaluation_num.get(i);
            if (atv.getAmbiguityTypeId().intValue() == 0) {
                type0++;
            } else if (atv.getAmbiguityTypeId().intValue() == 1) {
                type1++;
            } else if (atv.getAmbiguityTypeId().intValue() == 2) {
                type2++;
            }

        }
        buffer
                .append("<table  border=\"0\" cellpadding=\"0\" cellspacing=\"0\"><tbody><tr>");
        buffer
                .append("<td width=\"120\" class=\"f14\"><a href=\"javascript:Problem.ambiguityEvaluation(2,"
                        + userId
                        + ","
                        + ambiguity.getProblemId()
                        + ")\"  style=\"padding:5px 10px 5px 10px !important;padding:4px 10px 4px 10px;height:1px;border:1px solid #7AA9DF;background-color:#EAF3FC;font-size:12px\">容易理解</a><br>共<span class=\"red\" id=\"type2\">"
                        + type2 + "</span>票</td>");
        buffer
                .append("<td width=\"120\" class=\"f14\"><a href=\"javascript:Problem.ambiguityEvaluation(1,"
                        + userId
                        + ","
                        + ambiguity.getProblemId()
                        + ")\"  style=\"padding:5px 10px 5px 10px !important;padding:4px 10px 4px 10px;height:1px;border:1px solid #7AA9DF;background-color:#EAF3FC;font-size:12px\">不易理解</a><br>共<span class=\"red\" id=\"type1\">"
                        + type1 + "</span>票</td>");
        buffer
                .append("<td width=\"120\" class=\"f14\"><a href=\"javascript:Problem.ambiguityEvaluation(0,"
                        + userId
                        + ","
                        + ambiguity.getProblemId()
                        + ")\"  style=\"padding:5px 10px 5px 10px !important;padding:4px 10px 4px 10px;height:1px;border:1px solid #7AA9DF;background-color:#EAF3FC;font-size:12px\">有歧义</a><br>共<span class=\"red\" id=\"type0\">"
                        + type0 + "</span>票</td>");
        buffer.append("</tr></tbody></table></div>");
        return buffer;
    }

    public String creatingAmbiguity(Ambiguity ab, HttpServletRequest request,
            HttpServletResponse response) throws EasyJException, IOException {
        SysUserCache userCache = (SysUserCache) request.getSession()
                .getAttribute(Globals.SYS_USER_CACHE);
        SingleDataProxy sdp = SingleDataProxy.getInstance();
        Long userId = userCache.getUser().getUserId();
        String type0[] = request.getParameterValues("ambiguity_type0");
        String type1[] = request.getParameterValues("ambiguity_type1");
        String type2[] = request.getParameterValues("ambiguity_type2");
        if (type0 != null) {
            ab.setAmbiguityTypeId(new Long("0"));
        } else if (type1 != null) {
            ab.setAmbiguityTypeId(new Long("1"));

        } else if (type2 != null) {
            ab.setAmbiguityTypeId(new Long("2"));

        }
        ab.setCreatorId(userId);
        ProblemVersion pv = new ProblemVersion();
        pv.setProblemId(ab.getProblemId());
        Long problemversioncount = sdp.getCount(pv);
        ab.setAmbiguityEvaluationProblemVersionId(problemversioncount);
        // 设置Ambiguity表默认字段
        ab.setAnonymous("N");
        ab.setGoodNum(new Long("0"));
        ab.setBadNum(new Long("0"));
        ab.setProblemChange("N");
        ab.setIsoverdue("N");
        // 设置Ambiguity表默认字段完毕
        try {

            sdp.create(ab);
            returnMessage = "create_ambiguity_success**"
                    + ab.getAmbiguityContent();
        } catch (EasyJException e) {
            returnMessage = "create_ambiguity_failure**";
        }
        return returnMessage;

    }

    public String ambiguityTypeEvaluation(AmbiguityTypeValue atv)
            throws EasyJException {
        this.dp = dp;
        SingleDataProxy sdp = SingleDataProxy.getInstance();
        ProblemVersion pv = new ProblemVersion();
        pv.setProblemId(atv.getProblemId());
        ArrayList pvs = sdp.query(pv);
        if (pvs.size() > 1) {
            pv = (ProblemVersion) pvs.get(0);
            atv.setTypechangeProblemId(pv.getProblemVersionId());
        }

        try {
            sdp.create(atv);
            returnMessage = "ambiguityTypeEvaluation_success**"
                    + atv.getProblemId().toString();
        } catch (EasyJException e) {
            returnMessage = "ambiguityTypeEvaluation_failure";
        }
        return returnMessage;
    }

    public StringBuffer ambiguityUpdate(AmbiguityEvaluation ae,
            HttpServletRequest request, HttpServletResponse response)
            throws EasyJException, IOException {
        String confirm;
        String overdue;
        CompositeDataProxy cdp = CompositeDataProxy.getInstance();
        SingleDataProxy sdp = SingleDataProxy.getInstance();
        Ambiguity ab = new Ambiguity();
        ab.setAmbiguityId(ae.getAmbiguityId());
        ab = (Ambiguity) cdp.get(ab);
        StringBuffer xml = new StringBuffer("<result>");
        ProblemVersion problemversion = new ProblemVersion();
        problemversion.setProblemId(ab.getProblemId());
        Long problemversioncount = sdp.getCount(problemversion);

        try {
            if (request.getParameter("flower") != null) {

                ab.setGoodNum(new Long(ab.getGoodNum().intValue() + 1));
                cdp.update(ab);
                ae.setIsGood("Y");
                sdp.create(ae);
                xml
                        .append("<message>success</message><choose>flower</choose><ambiguityId>"
                                + ae.getAmbiguityId()
                                + "</ambiguityId></result>");

            }
        } catch (EasyJException e) {
            xml.append("<message>failure</message></result>");
        }
        try {
            if (request.getParameter("badegg") != null) {
                ab.setBadNum(new Long(ab.getBadNum().intValue() + 1));
                cdp.update(ab);
                ae.setIsGood("N");
                sdp.create(ae);
                xml
                        .append("<message>success</message><choose>badegg</choose><ambiguityId>"
                                + ae.getAmbiguityId()
                                + "</ambiguityId></result>");

            }

        } catch (EasyJException e) {
            xml.append("<message>failure</message></result>");
        }

        // 对于确认的处理，暂时只是设置一下用户的选择，以后如果peoblem_version建立之后会有改动，要增加确认对应的版本信息

        if (request.getParameter("confirm") != null) {
            if (request.getParameter("confirm").equals("y")) {
                ab.setProblemChange("C");
                ab.setSolvedVersion(problemversioncount);
                try {
                    cdp.update(ab);
                    xml
                            .append("<message>success</message><choose>confirm</choose><ambiguityId>"
                                    + ae.getAmbiguityId()
                                    + "</ambiguityId></result>");

                } catch (EasyJException e) {
                    xml.append("<message>failure</message></result>");
                }

            } else {
                ab.setProblemChange("D");
                try {

                    cdp.update(ab);
                    xml
                            .append("<message>success</message><choose>reject</choose><ambiguityId>"
                                    + ae.getAmbiguityId()
                                    + "</ambiguityId></result>");

                } catch (EasyJException e) {
                    xml.append("<message>failure</message></result>");
                }

            }
        }
        try {
            if (request.getParameter("overdue") != null) {

                ab.setIsoverdue("Y");
                ab.setProblemOverdueVersion(problemversioncount);
                cdp.update(ab);
                xml
                        .append("<message>success</message><choose>overdue</choose><ambiguityId>"
                                + ae.getAmbiguityId()
                                + "</ambiguityId></result>");

            }
        } catch (EasyJException e) {
            xml.append("<message>failure</message></result>");
        }
        return xml;

    }

    @SuppressWarnings("unchecked")
    public StringBuffer viewAllAmbiguity(Ambiguity ambiguity,
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
                        .append("<div class=\"mb12 bai\" id=\"ambiguity_more\" ><div class=\"rr_1\"></div><div class=\"rr_2\"></div><div class=\"rr_3\"></div><div class=\"rr\">");
                buffer
                        .append("<div class=\"t1\" id=\"ambiguity_head\"><div class=\"ico\"><div class=\"icomment\"></div></div>问题可理解性讨论页面<button  style=\"display:none\" onClick=\"Problem.remark()\" id=\"do_comment\">关闭评论</button></div>");
                buffer.append("<div class=\"bc0\" style=\"padding:0px 0pt;\">");
                buffer
                        .append("<div id=\"ambiguity\" style=\"display:block\"><div class=\"t2\">对于问题可理解性的讨论：</div><a  id=\"order1\" href=\"javascript:Problem.orderByGroup("
                                + ambiguity.getProblemId()
                                + ")\"  style=\"padding:5px 10px 5px 10px !important;padding:4px 10px 4px 10px;height:1px;border:1px solid #7AA9DF;background-color:#EAF3FC;font-size:12px\">按分类排序</a><div id=\"order11\" style=\"display:none\">false</div>");
                buffer
                        .append("<a  id=\"order2\" href=\"javascript:Problem.orderByGoodNum("
                                + ambiguity.getProblemId()
                                + ")\"  style=\"padding:5px 10px 5px 10px !important;padding:4px 10px 4px 10px;height:1px;border:1px solid #7AA9DF;background-color:#EAF3FC;font-size:12px\">按好评排序</a><div id=\"order22\" style=\"display:none\">false</div></div>");
                buffer.append("<div id=\"viewAll\" style=\"display:block\">");

            }
        }

        String group = request.getParameter("group");
        String good_num = request.getParameter("good_num");
        CompositeDataProxy cdp = CompositeDataProxy.getInstance();
        Problem problem = new Problem();
        problem.setProblemId(ambiguity.getProblemId());
        problem = (Problem) cdp.get(problem);
        ArrayList ambiguities = problem.getAmbiguitiys();
        if (good_num.equals("true")) {
            Collections.sort(ambiguities);
        }
        String content = "";
        if (group.equals("true")) {
            content = getAmbiguityByGroup(userId, ambiguities);
        } else {
            content = getAmbiguityNotByGroup(userId, ambiguities);
        }
        buffer.append(content);
        if (request.getParameter("ifNewWindow") != null) {
            ifNewWindow = request.getParameter("ifNewWindow");
            if (ifNewWindow.equals("Y")) {
                // buffer.append("<div id=\"Lg\" ></div><div
                // id=\"createAmbiguityDiscuss\"><a id=\"order1\"
                // href=\"javascript:Problem.createAmbiguityDiscussNewWindow("
                // + ambiguity.getProblemId()
                // + ")\" style=\"padding:5px 10px 5px 10px
                // !important;padding:4px 10px 4px 10px;height:1px;border:1px
                // solid
                // #7AA9DF;background-color:#EAF3FC;font-size:12px\">发表评论</a></div>");

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
                    .append("<div id=\"Lg\" ></div><div id=\"createAmbiguityDiscuss\"><a  id=\"order1\" href=\"javascript:Problem.createAmbiguityDiscuss("
                            + ambiguity.getProblemId()
                            + ")\"  style=\"padding:5px 10px 5px 10px !important;padding:4px 10px 4px 10px;height:1px;border:1px solid #7AA9DF;background-color:#EAF3FC;font-size:12px\">发表评论</a></div>");

            buffer
                    .append("<div id=\"Lg\" ></div><div class=\"pl10\"><A class=\"bluelink \" onclick=; href=\"javascript:showAllAmbiguity("
                            + problem.getProblemId()
                            + ");\">查看所有关于问题了理解性的评论</A> </div>");

        }
        return buffer;

    }

    public String getAmbiguityByGroup(Long userId, ArrayList ambiguities) {
        StringBuffer buffer = new StringBuffer();
        Ambiguity ab = new Ambiguity();
        ArrayList type2 = new ArrayList();
        ArrayList type1 = new ArrayList();
        ArrayList type0 = new ArrayList();
        for (int i = 0; i < ambiguities.size(); i++) {
            ab = (Ambiguity) ambiguities.get(i);
            if (ab.getAmbiguityTypeId().intValue() == 2) {
                type2.add(ab);
            } else if (ab.getAmbiguityTypeId().intValue() == 1) {
                type1.add(ab);
            } else if (ab.getAmbiguityTypeId().intValue() == 0) {
                type0.add(ab);
            }
        }
        buffer
                .append("<div class=\"t2\"><span style=\"color:#FF0000\">分类:有歧义</span></div>");
        buffer.append(getAmbiguityNotByGroup(userId, type0));
        buffer
                .append("<div class=\"t2\"><span style=\"color:#FF0000\">分类:不易理解</span></div>");
        buffer.append(getAmbiguityNotByGroup(userId, type1));
        buffer
                .append("<div class=\"t2\"><span style=\"color:#FF0000\">分类:易理解</span></div>");
        buffer.append(getAmbiguityNotByGroup(userId, type2));

        return buffer.toString();
    }

    public String getAmbiguityNotByGroup(Long userId, ArrayList ambiguities) {
        StringBuffer buffer = new StringBuffer();
        AmbiguityEvaluation ae = new AmbiguityEvaluation();
        Ambiguity ab = new Ambiguity();
        if (ambiguities.size() > 0) {
            for (int i = 0; i < ambiguities.size(); i++) {
                ab = (Ambiguity) ambiguities.get(i);
                buffer
                        .append("<div class=\"f14 p90 pl10\" class=\"answer_content\">"
                                + ab.getAmbiguityContent() + "</div>");
                boolean flag = true;
                int j = 0;
                ArrayList aes = ab.getAmbiguityEvaluations();
                while (flag == true && j < aes.size()) {
                    ae = (AmbiguityEvaluation) aes.get(j);
                    if (ae.getCreatorId().intValue() == userId.intValue()) {
                        flag = false;

                    }
                    j++;
                }
                if (userId.intValue() != ab.getCreatorId().intValue()
                        && ab.getIsoverdue().equals("N") && flag) {
                    // 登陆者不是这个ambiguity的作者,且没有评价过,且这个ambiguity还没有过期
                    buffer
                            .append("<IMG src=\"/image/flower.gif\"> <A class=brown12 id=\"flower"
                                    + ab.getAmbiguityId()
                                    + "\" href=\"javascript:Problem.flower("
                                    + ab.getProblemId()
                                    + ","
                                    + ab.getAmbiguityId()
                                    + ","
                                    + userId
                                    + ")\">送鲜花</A>（得<SPAN id=\"flowernum"
                                    + ab.getAmbiguityId()
                                    + "\">"
                                    + ab.getGoodNum() + "</SPAN>支）");
                    buffer
                            .append("<IMG height=15 src=\"/image/badegg.gif\" width=16><A id=\"badegg"
                                    + ab.getAmbiguityId()
                                    + "\"class=brown12  href=\"javascript:Problem.badegg("
                                    + ab.getProblemId()
                                    + ","
                                    + ab.getAmbiguityId()
                                    + ","
                                    + userId
                                    + ")\">扔鸡蛋</A>（得<SPAN class=orange12 id=\"badeggnum"
                                    + ab.getAmbiguityId()
                                    + "\">"
                                    + ab.getBadNum() + "</SPAN>个)");
                } else {
                    buffer.append("<IMG src=\"/image/flower.gif\"> （得<SPAN>"
                            + ab.getGoodNum() + "</SPAN>支）");
                    buffer
                            .append("<IMG height=15 src=\"/image/badegg.gif\" width=16>（得<SPAN class=orange12>"
                                    + ab.getBadNum() + "</SPAN>个)");
                }

                if (ab.getIsoverdue().equals("N")
                        && userId.intValue() == ab.getCreatorId().intValue()) {
                    // 只有是作者且还没有失效的时候才会出现实效按钮
                    buffer
                            .append("<br><IMG height=15 src=\"/image/assoc_delete.gif\" width=16><A id=\"overdue"
                                    + ab.getAmbiguityId()
                                    + "\" class=brown12  href=\"javascript:Problem.overdue("
                                    + ab.getProblemId()
                                    + ","
                                    + ab.getAmbiguityId()
                                    + ","
                                    + userId
                                    + ")\">失效</A><div id=\"overdue2"
                                    + ab.getAmbiguityId()
                                    + "\" style=\"display:none\">您已设置该回复失效</div>");
                } else if (ab.getIsoverdue().equals("Y")
                        && userId.intValue() == ab.getCreatorId().intValue()) {
                    buffer
                            .append("<br><IMG height=15 src=\"/image/assoc_delete.gif\" width=16>您已设置该回复失效");
                } else if (ab.getIsoverdue().equals("Y")
                        && userId.intValue() != ab.getCreatorId().intValue()) {
                    buffer
                            .append("<br><IMG height=15 src=\"/image/assoc_delete.gif\" width=16>该作者已设置该回复失效");
                }

                if (userId.intValue() == ab.getCreatorId().intValue()
                        && ab.getProblemChange().equals("Y")) {
                    buffer
                            .append("<br><IMG height=15 src=\"/image/agree.gif\" width=16><A id=\"confirm1"
                                    + ab.getAmbiguityId()
                                    + "\" class=brown12  href=\"javascript:Problem.confirm("
                                    + ab.getProblemId()
                                    + ","
                                    + ab.getAmbiguityId()
                                    + ","
                                    + userId
                                    + ")\">确认</A>&nbsp;&nbsp;<A id=\"reject1"
                                    + ab.getAmbiguityId()
                                    + "\" class=brown12  href=\"javascript:Problem.reject("
                                    + ab.getProblemId()
                                    + ","
                                    + ab.getAmbiguityId()
                                    + ","
                                    + userId
                                    + ")\">拒绝</A><div id=\"confirm2"
                                    + ab.getAmbiguityId()
                                    + "\" style=\"display:none\">您已确认</div><div id=\"reject2"
                                    + ab.getAmbiguityId()
                                    + "\" style=\"display:none\">您已拒绝</div>");
                    // 只有是作者
                } else if (ab.getProblemChange().equals("C")
                        && userId.intValue() == ab.getCreatorId().intValue()) {
                    buffer
                            .append("<br><IMG height=15 src=\"/image/agree.gif\" width=16>您已确认");
                } else if (ab.getProblemChange().equals("C")
                        && userId.intValue() != ab.getCreatorId().intValue()) {
                    buffer
                            .append("<br><IMG height=15 src=\"/image/agree.gif\" width=16>该文章作者已确认");
                } else if (ab.getProblemChange().equals("D")
                        && userId.intValue() == ab.getCreatorId().intValue()) {
                    buffer
                            .append("<br><IMG height=15 src=\"/image/agree.gif\" width=16>您已拒绝");
                } else if (ab.getProblemChange().equals("D")
                        && userId.intValue() != ab.getCreatorId().intValue()) {
                    buffer
                            .append("<br><IMG height=15 src=\"/image/agree.gif\" width=16>该文章作者已拒绝");
                }

                buffer
                        .append("<div align=\"right\" style=\"margin: 5px 5px 8px;\" id=\"comment_info\"><span class=\"gray\">评论者:"
                                + ab.getUserName()
                                + " 时间："
                                + ab.getBuildTime()
                                + "</span></div>");
                if (i < ambiguities.size() - 1) {
                    buffer.append("<div id=\"Lg\" ></div>");

                }
            }
        } else {
            buffer
                    .append("<div class=\"f14 p90 pl10\" class=\"answer_content\">尚无评论</div>");
        }
        // buffer.append("<div class=\"pl10\"><A class=\"bluelink\" onclick=;
        // href=\"javascript:showProblem(<%=problemId%>);\">查看所有评论&gt;&gt;</a></div>");
        // buffer.append("</div></div></div><div class=\"rr_4\"></div><div
        // class=\"rr_5\"></div><div class=\"rr_1\"></div></div>");
        return buffer.toString();
    }

}
