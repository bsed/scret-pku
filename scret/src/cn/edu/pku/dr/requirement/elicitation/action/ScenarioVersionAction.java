package cn.edu.pku.dr.requirement.elicitation.action;

import easyJ.common.EasyJException;
import easyJ.http.Globals;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import easyJ.common.BeanUtil;
import java.util.ArrayList;
import easyJ.business.proxy.SingleDataProxy;
import easyJ.database.dao.*;
import easyJ.common.EasyJException;
import java.util.ArrayList;
import easyJ.common.BeanUtil;
import easyJ.database.dao.command.SelectCommand;
import cn.edu.pku.dr.requirement.elicitation.data.ScenarioVersion;
import cn.edu.pku.dr.requirement.elicitation.data.*;
import easyJ.database.session.SessionFactory;
import easyJ.database.session.Session;
import java.io.*;

public class ScenarioVersionAction extends easyJ.http.servlet.SingleDataAction {
    private String preDisplayValue;

    public ScenarioVersionAction() {}

    public boolean encapsulateObject() throws EasyJException {
        super.encapsulateObject();
        return true;
    }

    public String changename(String preDisplayValue) throws EasyJException {
        this.preDisplayValue = preDisplayValue;
        String displayValue = new String();

        if (preDisplayValue == null) {
            displayValue = preDisplayValue;
        } else {
            String[] postDisplayValue = preDisplayValue.split("\\*");
            if (postDisplayValue.length == 1) {

                displayValue = "场景是: " + postDisplayValue[0] + " 没有发生改变";
            }
            if (postDisplayValue.length == 2) {
                displayValue = "场景是: " + postDisplayValue[0] + "  "
                        + postDisplayValue[1] + "  发生改变";
            }
        }
        return displayValue;

    }

    public void edit() throws EasyJException {
        super.edit();
    }

    public void newObject() throws EasyJException {
        super.newObject();
    }

    public void update() throws EasyJException {
        super.update();
    }

    public void query() throws EasyJException {
        super.query();
        returnPath = "/WEB-INF/AjaxScenarioVersion.jsp";
    }

    public void getContent() throws EasyJException {
        Long ScenarioVersionId = new Long(request
                .getParameter("ScenarioVersionId"));
        int tempId = ScenarioVersionId.intValue();
        tempId = tempId - 1;
        Long oldScenarioVersionId = new Long(tempId);
        ScenarioVersion scenarioversion = new ScenarioVersion();
        ScenarioVersion oldscenarioversion = new ScenarioVersion();
        Data data = new Data();
        Role communicator = new Role();
        Role participant = new Role();
        Role observer = new Role();
        DescriptionVersion description = new DescriptionVersion();
        DescriptionVersion oldescription = new DescriptionVersion();
        // 查询当前版本的scenarioversion
        Object object = BeanUtil
                .getEmptyObject("cn.edu.pku.dr.requirement.elicitation.data.ScenarioVersion");
        Class clazz = object.getClass();
        SelectCommand scmdScenarioVersion = DAOFactory.getSelectCommand(clazz);
        Filter filter = DAOFactory.getFilter("scenarioVersionId",
                SQLOperator.EQUAL, ScenarioVersionId.toString());
        scmdScenarioVersion.setFilter(filter);
        Session session = SessionFactory.openSession();
        ArrayList scenarioVersions = session.query(scmdScenarioVersion);
        if (scenarioVersions.size() == 1) {
            scenarioversion = (ScenarioVersion) scenarioVersions.get(0);
        }
        // 查询上一版本的scenarioversion
        if (oldScenarioVersionId.intValue() > -1) {
            SelectCommand scmdOldScenarioVersion = DAOFactory
                    .getSelectCommand(clazz);
            Filter oldfilter = DAOFactory.getFilter("scenarioVersionId",
                    SQLOperator.EQUAL, oldScenarioVersionId.toString());
            scmdOldScenarioVersion.setFilter(oldfilter);
            ArrayList oldscenarioVersions = session
                    .query(scmdOldScenarioVersion);
            if (oldscenarioVersions.size() == 1) {
                oldscenarioversion = (ScenarioVersion) oldscenarioVersions
                        .get(0);
            }
        }
        // 提取当前版本的变化的内容
        String modifyMark = scenarioversion.getModifyMark();
        String[] modifyDomain = modifyMark.split("\\,");
        Object o;
        for (int k = 0; k < modifyDomain.length; k++) {
            String content = " ";
            // 得到datas最新和上一版本的内容
            if ("datas".equals(modifyDomain[k])) {
                String newDatas = "";
                String oldDatas = "无";
                // 取最新的datas
                o = BeanUtil
                        .getEmptyObject("cn.edu.pku.dr.requirement.elicitation.data.Data");
                clazz = o.getClass();
                SelectCommand scmd = DAOFactory.getSelectCommand(clazz);
                String[] datas = new String[10];
                datas = (String[]) scenarioversion.getDatas().split("\\,");
                Filter filternew = DAOFactory.getFilter("dataId",
                        SQLOperator.IN, datas);
                scmd.setFilter(filternew);
                ArrayList dataList = session.query(scmd);
                for (int j = 0; j < dataList.size(); j++) {
                    data = (Data) dataList.get(j);
                    newDatas = newDatas + data.getDataName();
                }
                // 取以前的datas
                if (oldscenarioversion != null) {
                    datas = (String[]) oldscenarioversion.getDatas().split(
                            "\\,");
                    Filter filterold = DAOFactory.getFilter("dataId",
                            SQLOperator.IN, datas);
                    scmd.setFilter(filterold);
                    dataList = session.query(scmd);
                    for (int j = 0; j < dataList.size(); j++) {
                        data = (Data) dataList.get(j);
                        oldDatas = oldDatas + data.getDataName();
                    }
                }
                // 得到content
                /*
                 * content = content + "<table class=\"changeborder\">" + "<tr class=\"change\" ><td>observers</td></tr>" + "<tr class=\"version\"><td>上一版本:</td></tr>" + "<tr class=\"content\"><td>" +
                 * oldDatas + "</td></tr>" + "<tr class=\"version\"><td>目前版本:</td></tr>" + "<tr class=\"content\"><td>" +
                 * newDatas + "</td></tr></table>";
                 */
                content = content + "<table class=\"changeborder\">"
                        + "<tr class=\"change\" ><td>datas</td></tr>"
                        + "<tr class=\"version\"><td>目前版本:</td></tr>"
                        + "<tr class=\"content\"><td>" + newDatas
                        + "</td></tr></table>";

            }
            // 得到participants最新和上一版本的内容
            else if ("participants".equals(modifyDomain[k])) {
                String newParticipants = "";
                String oldParticipants = "无";
                // 取最新的participants
                o = BeanUtil
                        .getEmptyObject("cn.edu.pku.dr.requirement.elicitation.data.Role");
                clazz = o.getClass();
                SelectCommand scmd = DAOFactory.getSelectCommand(clazz);
                String[] participants = new String[10];
                participants = (String[]) scenarioversion.getParticipants()
                        .split("\\,");
                Filter filternew = DAOFactory.getFilter("roleId",
                        SQLOperator.IN, participants);
                scmd.setFilter(filternew);
                ArrayList participantList = session.query(scmd);
                for (int j = 0; j < participantList.size(); j++) {
                    participant = (Role) participantList.get(j);
                    newParticipants = newParticipants
                            + participant.getRoleName();
                }
                // 取以前的participants
                if (oldscenarioversion != null) {
                    participants = (String[]) oldscenarioversion
                            .getParticipants().split("\\,");
                    Filter filterold = DAOFactory.getFilter("roleId",
                            SQLOperator.IN, participants);
                    scmd.setFilter(filterold);
                    participantList = session.query(scmd);
                    session.close();
                    for (int j = 0; j < participantList.size(); j++) {
                        participant = (Role) participantList.get(j);
                        oldParticipants = oldParticipants
                                + participant.getRoleName();
                    }
                }
                // 得到content
                /*
                 * content = content + "<table class=\"changeborder\">" + "<tr class=\"change\" ><td>observers</td></tr>" + "<tr class=\"version\"><td>上一版本:</td></tr>" + "<tr class=\"content\"><td>" +
                 * oldParticipants + "</td></tr>" + "<tr class=\"version\"><td>目前版本:</td></tr>" + "<tr class=\"content\"><td>" +
                 * newParticipants + "</td></tr></table>";
                 */
                content = content + "<table class=\"changeborder\">"
                        + "<tr class=\"change\" ><td>participants</td></tr>"
                        + "<tr class=\"version\"><td>目前版本:</td></tr>"
                        + "<tr class=\"content\"><td>" + newParticipants
                        + "</td></tr></table>";

            } else if ("communicators".equals(modifyDomain[k])) {
                String newCommunicators = "";
                String oldCommunicators = "无";
                // 取最新的communicators
                o = BeanUtil
                        .getEmptyObject("cn.edu.pku.dr.requirement.elicitation.data.Role");
                clazz = o.getClass();
                SelectCommand scmd = DAOFactory.getSelectCommand(clazz);
                String[] communicators = new String[10];
                communicators = (String[]) scenarioversion.getParticipants()
                        .split("\\,");
                Filter filternew = DAOFactory.getFilter("roleId",
                        SQLOperator.IN, communicators);
                scmd.setFilter(filternew);
                ArrayList communicatorList = session.query(scmd);
                for (int j = 0; j < communicatorList.size(); j++) {
                    communicator = (Role) communicatorList.get(j);
                    newCommunicators = newCommunicators
                            + communicator.getRoleName();
                }
                // 取以前的communicators
                if (oldscenarioversion != null) {
                    communicators = (String[]) oldscenarioversion
                            .getParticipants().split("\\,");
                    Filter filterold = DAOFactory.getFilter("roleId",
                            SQLOperator.IN, communicators);
                    scmd.setFilter(filterold);
                    communicatorList = session.query(scmd);
                    session.close();
                    for (int j = 0; j < communicatorList.size(); j++) {
                        communicator = (Role) communicatorList.get(j);
                        oldCommunicators = oldCommunicators
                                + participant.getRoleName();
                    }
                }
                // 得到content
                /*
                 * content = content + "<table class=\"changeborder\">" + "<tr class=\"change\" ><td>observers</td></tr>" + "<tr class=\"version\"><td>上一版本:</td></tr>" + "<tr class=\"content\"><td>" +
                 * oldCommunicators + "</td></tr>" + "<tr class=\"version\"><td>目前版本:</td></tr>" + "<tr class=\"content\"><td>" +
                 * newCommunicators + "</td></tr></table>";
                 */
                content = content + "<table class=\"changeborder\">"
                        + "<tr class=\"change\" ><td>Communicators</td></tr>"
                        + "<tr class=\"version\"><td>目前版本:</td></tr>"
                        + "<tr class=\"content\"><td>" + newCommunicators
                        + "</td></tr></table>";

            } else if ("observers".equals(modifyDomain[k])) {
                String newObservers = "";
                String oldObservers = "";
                // 取最新的observers
                o = BeanUtil
                        .getEmptyObject("cn.edu.pku.dr.requirement.elicitation.data.Role");
                clazz = o.getClass();
                SelectCommand scmd = DAOFactory.getSelectCommand(clazz);
                String[] observers = new String[10];
                observers = (String[]) scenarioversion.getParticipants().split(
                        "\\,");
                Filter filternew = DAOFactory.getFilter("roleId",
                        SQLOperator.IN, observers);
                scmd.setFilter(filternew);
                ArrayList communicatorList = session.query(scmd);
                for (int j = 0; j < communicatorList.size(); j++) {
                    observer = (Role) communicatorList.get(j);
                    newObservers = newObservers + observer.getRoleName();
                }
                // 取以前的observers
                if (oldscenarioversion != null) {
                    observers = (String[]) oldscenarioversion.getParticipants()
                            .split("\\,");
                    Filter filterold = DAOFactory.getFilter("roleId",
                            SQLOperator.IN, observers);
                    scmd.setFilter(filterold);
                    communicatorList = session.query(scmd);
                    session.close();
                    for (int j = 0; j < communicatorList.size(); j++) {
                        observer = (Role) communicatorList.get(j);
                        oldObservers = oldObservers + observer.getRoleName();
                    }
                }
                // 得到content
                /*
                 * content = content + "<table class=\"changeborder\">" + "<tr class=\"change\" ><td>observers</td></tr>" + "<tr class=\"version\"><td>上一版本:</td></tr>" + "<tr class=\"content\"><td>" +
                 * oldObservers + "</td></tr>" + "<tr class=\"version\"><td>目前版本:</td></tr>" + "<tr class=\"content\"><td>" +
                 * newObservers + "</td></tr></table>";
                 */
                content = content + "<table class=\"changeborder\">"
                        + "<tr class=\"version\"><td>目前版本:</td></tr>"
                        + "<tr class=\"content\"><td>" + newObservers
                        + "</td></tr></table>";

            }
            // 取description最新的版本和原来的版本
            else if ("description".equals(modifyDomain[k])) {
                String newDescription = "";
                String oldDescription = "";
                // 取最新的description
                o = BeanUtil
                        .getEmptyObject("cn.edu.pku.dr.requirement.elicitation.data.DescriptionVersion");
                clazz = o.getClass();
                SelectCommand scmd = DAOFactory.getSelectCommand(clazz);
                String descriptions = (String) scenarioversion
                        .getScenarioVersionId().toString();
                Filter filternew = DAOFactory.getFilter("scenarioVersionId",
                        SQLOperator.EQUAL, descriptions);
                scmd.setFilter(filternew);
                ArrayList descriptionList = session.query(scmd);
                for (int j = 0; j < descriptionList.size(); j++) {
                    description = (DescriptionVersion) descriptionList.get(j);
                    newDescription = newDescription
                            + description.getDescriptionVersionContent();
                }
                // 取以前的description
                Filter filter1 = DAOFactory.getFilter();
                Filter filter2 = DAOFactory.getFilter("scenarioVersionId",
                        SQLOperator.LESS, ScenarioVersionId.toString());
                Filter filter3 = DAOFactory.getFilter("creatorId",
                        SQLOperator.EQUAL, scenarioversion.getCreatorId()
                                .toString());
                filter1.addFilter(filter2);
                filter1.addFilter(filter3, LogicOperator.AND);
                scmd.setFilter(filter1);
                scmd.addOrderRule(new OrderRule("scenarioVersionId",
                        OrderDirection.DESC));
                descriptionList = session.query(scmd);
                if (descriptionList.size() > 0) {

                    oldescription = (DescriptionVersion) descriptionList.get(0);
                    oldDescription = oldDescription
                            + oldescription.getDescriptionVersionContent();
                } else {
                    oldDescription = "无";
                }
                // 得到content
                /*
                 * content = content + "<table class=\"changeborder\">" + "<tr class=\"change\" ><td>descriptions</td></tr>" + "<tr class=\"version\"><td>上一版本:</td></tr>" + "<tr class=\"content\"><td>" +
                 * oldDescription + "</td></tr>" + "<tr class=\"version\"><td>目前版本:</td></tr>" + "<tr class=\"content\"><td>" +
                 * newDescription + "</td></tr>"; oldDescription = "";
                 */
                content = content + "<table class=\"changeborder\">"
                        + "<tr class=\"change\" ><td>descriptions</td></tr>"
                        + "<tr class=\"version\"><td>目前版本:</td></tr>"
                        + "<tr class=\"content\"><td>" + newDescription
                        + "</td></tr>";
                oldDescription = "";

            }
            content = content
                    + "<input class=\"hide_todo\" value=\"查看本场景版本的全局\" type=\"button\" class=\"button\"  onclick=\"showScenario("
                    + "'" + ScenarioVersionId + "'" + ")\"/></table>";
            try {
                response.getWriter().println(content);

            } catch (IOException ex) {
                ex.getStackTrace();
            }
        }
    }

    public void showScenarioVersion() throws EasyJException {
        String content = "";
        String descriptiontable = "暂无描述";
        String remarktable = "暂无评论";
        // Long scenarioversionId = new Long(88);
        Long scenarioversionId = new Long(request
                .getParameter("scenarioversionId"));
        ScenarioVersion scenarioversion = new ScenarioVersion();
        Role participant = new Role();
        Role communicator = new Role();
        Role observer = new Role();
        Data data = new Data();
        DescriptionVersion description = new DescriptionVersion();
        Remark remark = new Remark();
        Object object = BeanUtil
                .getEmptyObject("cn.edu.pku.dr.requirement.elicitation.data.ScenarioVersion");
        Class clazz = object.getClass();
        SelectCommand scmd = DAOFactory.getSelectCommand(clazz);
        Filter filter = DAOFactory.getFilter("scenarioVersionId",
                SQLOperator.EQUAL, scenarioversionId);
        
        scmd.setFilter(filter);
        Session session = SessionFactory.openSession();
        ArrayList scenarioVersions = session.query(scmd);
        if (scenarioVersions.size() > 0) {
            scenarioversion = (ScenarioVersion) scenarioVersions.get(0);
        }
        // 将参与者取出
        object = BeanUtil
                .getEmptyObject("cn.edu.pku.dr.requirement.elicitation.data.Role");
        clazz = object.getClass();
        scmd = DAOFactory.getSelectCommand(clazz);
        String[] participants = new String[10];
        participants = scenarioversion.getParticipants().split("\\,");
        Filter filter_participants = DAOFactory.getFilter("roleId",
                SQLOperator.IN, participants);
        scmd.setFilter(filter_participants);
        ArrayList participantList = session.query(scmd);
        String participantcontent = "";
        for (int j = 0; j < participantList.size(); j++) {
            participant = (Role) participantList.get(j);
            participantcontent = participantcontent + participant.getRoleName();
        }
        // scenarioversion.setParticipants(participantcontent);
        // 将外部交互者取出
        object = BeanUtil
                .getEmptyObject("cn.edu.pku.dr.requirement.elicitation.data.Role");
        clazz = object.getClass();
        scmd = DAOFactory.getSelectCommand(clazz);
        String[] communicators = new String[10];
        communicators = scenarioversion.getCommunicators().split("\\,");
        Filter filter_communicators = DAOFactory.getFilter("roleId",
                SQLOperator.IN, communicators);
        scmd.setFilter(filter_communicators);
        ArrayList communicatorList = session.query(scmd);
        String communicatorcontent = "";
        for (int j = 0; j < communicatorList.size(); j++) {
            communicator = (Role) communicatorList.get(j);
            communicatorcontent = communicatorcontent
                    + communicator.getRoleName();
        }
        scenarioversion.setCommunicators(communicatorcontent);
        // 将观察者取出
        object = BeanUtil
                .getEmptyObject("cn.edu.pku.dr.requirement.elicitation.data.Role");
        clazz = object.getClass();
        scmd = DAOFactory.getSelectCommand(clazz);
        String[] observers = new String[10];
        observers = scenarioversion.getObservers().split("\\,");
        Filter filter_observers = DAOFactory.getFilter("roleId",
                SQLOperator.IN, observers);
        scmd.setFilter(filter_observers);
        ArrayList observerList = session.query(scmd);
        String observercontent = "";
        for (int j = 0; j < observerList.size(); j++) {
            observer = (Role) observerList.get(j);
            observercontent = observercontent + observer.getRoleName();
        }
        scenarioversion.setObservers(observercontent);
        // 将数据取出
        object = BeanUtil
                .getEmptyObject("cn.edu.pku.dr.requirement.elicitation.data.Data");
        clazz = object.getClass();
        scmd = DAOFactory.getSelectCommand(clazz);
        String[] datas = new String[10];
        datas = scenarioversion.getDatas().split("\\,");
        Filter filter_datas = DAOFactory.getFilter("dataId", SQLOperator.IN,
                datas);
        scmd.setFilter(filter_datas);
        ArrayList dataList = session.query(scmd);
        String datacontent = "";
        for (int j = 0; j < dataList.size(); j++) {
            data = (Data) dataList.get(j);
            datacontent = datacontent + data.getDataName();
        }
        scenarioversion.setDatas(datacontent);
        // 将描述前面的部分放入content
        content = content
                + "<table><tbody id=\"reality\"><tr  align=\"center\">"
                + "场景名:&nbsp;" + scenarioversion.getScenarioName()
                + "&nbsp;&nbsp;&nbsp; &nbsp;版本号:"
                + scenarioversion.getScenarioVersionId() + "</tr>";
        // 加入场景名称
        content = content
                + "<tr class=\"trContent\" ><th class=\"trContent\">场景名称</th><td class=\"trContent\" id=\"sn\">"
                + scenarioversion.getScenarioName() + "</td>";
        // 加入参与者
        content = content
                + "<th class=\"trContent\" width=\"80px\">参与者</th><td  class=\"trContent\" id=\"participant\"><span id=\"participant_list\">"
                + participantcontent + "</span></td></tr>";
        // 加入外部交互者
        content = content
                + "<tr class=\"trContent\" ><th class=\"trContent\">外部交互者</th><td  class=\"trContent\" id=\"communicator\"><span id=\"communicator_list\">"
                + communicatorcontent + "</span></td>";
        // 加入观察者
        content = content
                + "<th class=\"trContent\" width=\"80px\">观察者</th><td  class=\"trContent\" id=\"observer\"><span id=\"observer_list\">"
                + observercontent + "</span></td></tr>";
        // 加入数据
        content = content
                + "<tr class=\"trContent\" width=\"80px\" ><th class=\"trContent\">数据</th><td  class=\"trContent\" id=\"data\" colspan=\"3\"><span id=\"data_list\">"
                + datacontent + "</span></td></tr>";
        // 添加描述以及remark
        int length = scenarioversion.getParticipants().split("\\,").length;
        String[] parti = new String[length];
        parti = scenarioversion.getParticipants().split("\\,");

        for (int i = 0; i < parti.length; i++) {
            object = BeanUtil
                    .getEmptyObject("cn.edu.pku.dr.requirement.elicitation.data.Role");
            clazz = object.getClass();
            scmd = DAOFactory.getSelectCommand(clazz);
            Filter filter_participant = DAOFactory.getFilter("roleId",
                    SQLOperator.EQUAL, parti[i]);
            scmd.setFilter(filter_participant);
            ArrayList participantsList = session.query(scmd);
            if (participantsList.size() > 0) {
                participant = (Role) participantsList.get(0);
            }
            content = content
                    + "<tr  class=\"trContent\" ><th class=\"trContent\" width=\"80px\">"
                    + participant.getRoleName() + "</th>";
            // 取描述
            object = BeanUtil
                    .getEmptyObject("cn.edu.pku.dr.requirement.elicitation.data.DescriptionVersion");
            clazz = object.getClass();
            scmd = DAOFactory.getSelectCommand(clazz);
            Filter filterdescription2 = DAOFactory.getFilter(
                    "scenarioVersionId", SQLOperator.EQUAL, scenarioversion
                            .getScenarioVersionId());
            Filter filterdescription3 = DAOFactory.getFilter("creatorId",
                    SQLOperator.EQUAL, parti[i]);
            Filter filterdescription1 = DAOFactory.getFilter();
            filterdescription1.addFilter(filterdescription3);
            filterdescription1.addFilter(filterdescription2, LogicOperator.AND);
            scmd.setFilter(filterdescription1);
            ArrayList descriptionsList = session.query(scmd);
            if (descriptionsList.size() > 0) {
                description = (DescriptionVersion) descriptionsList.get(0);

                content = content
                        + "<td colspan=\"3\" class=\"tr\"><div class=\"trContent\">"
                        + description.getDescriptionVersionContent() + "</div>";
            } else {
                content = content
                        + "<td colspan=\"3\" class=\"tr\"><div class=\"trContent\">"
                        + descriptiontable + "</div>";
            }
            // 得到评论数据
            object = BeanUtil
                    .getEmptyObject("cn.edu.pku.dr.requirement.elicitation.data.Remark");
            clazz = object.getClass();
            scmd = DAOFactory.getSelectCommand(clazz);
            Filter filterremark = DAOFactory.getFilter("roleId",
                    SQLOperator.EQUAL, parti[i]);
            scmd.setFilter(filterremark);
            ArrayList remarkList = session.query(scmd);
            content = content + "<div class=\"trtitle\">评论</div>";
            if (remarkList.size() > 0) {
                for (int j = 0; j < remarkList.size(); j++) {
                    remark = (Remark) remarkList.get(j);
                    content = content + "<div class=\"trContent\">"
                            + remark.getRemarkContent() + "</div>";
                }
            } else {
                content = content + "<div class=\"trContent\">" + remarktable
                        + "</div>";
            }
            content = content + "</td></tr>";
        }
        content = content + "</table>";
        try {
            response.getWriter().println(content);
        } catch (IOException ex) {
            ex.getStackTrace();
        }

    }

    public static String showDescriptionHistoryNewWindow(
            HttpServletRequest request) throws EasyJException {
        // 看看Obj是否存在，如果存在，则不用再次去通过getObject()来获得，这样效率稍微高一些
        SingleDataProxy sdp = SingleDataProxy.getInstance();
        String pageNo = request.getParameter(Globals.PAGENO);
        if (pageNo == null) {
            pageNo = "1";
        }
        // 由lower和upper查询得到数据
        Object lower = request.getAttribute(Globals.LOWER);
        Object upper = request.getAttribute(Globals.UPPER);
        Page page = sdp.query(lower, upper, new Integer(pageNo));
        ArrayList descriptionVersions = page.getPageData();
        int size = descriptionVersions.size();
        DescriptionVersion descriptionversion;
        // 将查询到的信息放到page中
        for (int i = 0; i < size; i++) {
            descriptionversion = (DescriptionVersion) descriptionVersions
                    .get(i);
            descriptionVersions.set(i, descriptionversion);
            page.setPageData(descriptionVersions);
        }
        request.setAttribute(Globals.PAGE, page);
        Long creatorId = new Long(request.getParameter("CreatorId"));
        Class clazz = BeanUtil
                .getClass("cn.edu.pku.dr.requirement.elicitation.data.DescriptionVersion");
        Session session = SessionFactory.openSession();
        String content = "";
        // 取用户名
        clazz = BeanUtil
                .getClass("cn.edu.pku.dr.requirement.elicitation.data.Role");
        SelectCommand scmd = DAOFactory.getSelectCommand(clazz);
        Filter filter1 = DAOFactory.getFilter("roleId", SQLOperator.EQUAL,
                creatorId);
        scmd.setFilter(filter1);
        ArrayList creators = session.query(scmd);
        String creatorName = "";
        if (creators.size() > 0) {
            Role creator = (Role) creators.get(0);
            creatorName = creator.getRoleName();
        }

        descriptionversion = (DescriptionVersion) descriptionVersions.get(0);
        String temp = "";
        for (int i = 0; i < descriptionVersions.size(); i++) {
            descriptionversion = (DescriptionVersion) descriptionVersions
                    .get(i);
            temp = temp
                    + "<div class=\"first\" ></div><div class=\"target-closed\"><span class =\"changeDomain\">&nbsp;&nbsp;&nbsp;&nbsp;版本"
                    + descriptionversion.getDescriptionVersionId() + "</span>";
            temp = temp + "<span class=\"name\">&nbsp;修改时间:</span>";
            temp = temp + "<span class=\"time\">&nbsp;"
                    + descriptionversion.getUpdateTime().toGMTString()
                    + "</span>";
            temp = temp + "<span>修改</span></div>";
            temp = temp
                    + "<div class=\"target-closed\"><table class=\"changeborder\"><tr class=\"content\"><td>"
                    + descriptionversion.getDescriptionVersionContent()
                    + "</td></table></div>";
        }
        content = content + temp + "<div class=\"jqHandle jqResize\"></div>";
        return content;

    }

}
