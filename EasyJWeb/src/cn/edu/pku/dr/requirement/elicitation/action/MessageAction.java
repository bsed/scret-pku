package cn.edu.pku.dr.requirement.elicitation.action;

import easyJ.business.proxy.CompositeDataProxy;
import easyJ.business.proxy.SingleDataProxy;
import easyJ.common.EasyJException;
import easyJ.common.BeanUtil;
import easyJ.database.dao.command.SelectCommand;
import easyJ.database.dao.DAOFactory;
import easyJ.database.dao.Filter;
import easyJ.database.dao.SQLOperator;
import easyJ.database.session.Session;
import java.util.ArrayList;
import easyJ.database.session.SessionFactory;
import cn.edu.pku.dr.requirement.elicitation.data.Message;
import java.io.IOException;
import easyJ.system.data.SysUserCache;
import easyJ.system.data.SysUser;
import easyJ.http.Globals;
import java.util.Arrays;

import java.io.PrintWriter;
import easyJ.database.dao.LogicOperator;

public class MessageAction extends easyJ.http.servlet.SingleDataAction {
    private String msg;

    public MessageAction() {}

    public void query() throws EasyJException {
        super.query();
        this.returnPath = "/WEB-INF/template/AjaxNewWindowMessage.jsp";

    }

    public void getMessageTemplate() throws EasyJException, IOException {
        SysUserCache userCache = (SysUserCache) request.getSession()
                .getAttribute(Globals.SYS_USER_CACHE);
        Long userId = userCache.getUser().getUserId();
        StringBuffer buffer = new StringBuffer();
        buffer
                .append("<meta http-equiv=\"Expires\" content=\"0\"><meta http-equiv=\"Pragma\" content=\"no-cache\"><meta http-equiv=\"Cache-Control\" content=\"no-cache\">");
        buffer
                .append("<link href=\"/css/iknow1_1.css\" rel=\"stylesheet\" type=\"text/css\">");
        buffer
                .append("<link href=\"/css/message.css\" rel=\"stylesheet\" type=\"text/css\">");
        buffer
                .append("<link href=\"/css/pattern.css\" rel=\"stylesheet\" type=\"text/css\">");
        buffer
                .append("<link href=\"/css/scenario.css\" rel=\"stylesheet\" type=\"text/css\">");
        buffer
                .append("<script language=\"javascript\" src=\"/js/Message.js\"></script>");
        buffer
                .append("<div style=\"text-align:center\"><FONT>消息系统</FONT></div><div class=\"leftarea\"><div >我收到的消息<span id=\"msg_num\"></span></div><div id=\"leftarea\"></div>");
        buffer
                .append("<DIV class=bgactive id=0><span style=\"cursor:pointer\" style=\"padding-left:18px\" onclick=\"Message.getMessage(1)\">我的消息</span><span id=\"msg_num\"></span></DIV>");
        buffer
                .append("<DIV class=subitema id=2><span style=\"cursor:pointer\" style=\"padding-left:18px\" onclick=\"Message.getMessage(2)\">系统消息</span><span id=\"msg_num\"></span></DIV>");
        buffer
                .append("<DIV class=subitema id=3><span style=\"cursor:pointer\" onclick=\"Message.getMessage(3)\">我发送的消息</span></DIV></DIV>");
        buffer
                .append("	<div id=\"page\" style=\"display:none\"></div><div class=\"rightarea\" id=\"rightarea\" ><DIV class=l><DIV class=ud><SPAN style=\"FLOAT: right\">**消息提示处**</span></div><DIV class=con id=\"boforewritemessage\"><DIV  id=\"writemessage\"></div><DIV style=\"MARGIN-BOTTOM: 10px\" id =\"messages\">");
        buffer.append("	</div></DIV><DIV class=ud>&nbsp;</DIV></div></div>");
        try {
            response.getWriter().println(buffer.toString());
        } catch (IOException ex) {
            ex.getStackTrace();
        }

    }

    public void getMessage() throws EasyJException, IOException {
        String choose = request.getParameter("id");
        String content = "";
        // 生成开头的固定模式
        String ii = request.toString();
        content = content
                + "<TABLE style=\"MARGIN-TOP: 8px; MARGIN-BOTTOM: 8px\" cellSpacing=0 cellPadding=0 width=\"100%\" align=center border=0><TBODY></TBODY></TABLE>";
        content = content
                + "<TABLE class=f9 id=newsTable cellSpacing=0 cellPadding=0 width=\"100%\" align=center bgColor=#ffffff border=0><TBODY>";
        content = content
                + "<TR bgColor=#f3f3f3><TD class=tdline28 noWrap width=\"5%\"></TD>";
        if (choose.equals("3")) {
            content = content
                    + " <TD class=tdline28 width=\"14%\" height=30><b>收件人</b></TD>";
        } else {
            content = content
                    + " <TD class=tdline28 width=\"14%\" height=30><b>发件人</b></TD>";
        }

        content = content
                + "<TD class=tdline28 width=\"1%\">&nbsp;</TD><TD class=tdline28><B>消息</B></TD><TD class=tdline28 width=\"15%\"><B>发送时间</B></TD></TR>";
        // 生成消息标题行
        SysUserCache userCache = (SysUserCache) request.getSession()
                .getAttribute(Globals.SYS_USER_CACHE);
        SysUser user = userCache.getUser();
        Message message = new Message();
        ArrayList messageList = new ArrayList();
        // 查询好友发送的信息
        if (choose.equals("1")) {
            Object o = BeanUtil
                    .getEmptyObject("cn.edu.pku.dr.requirement.elicitation.data.Message");
            Class clazz = o.getClass();
            SelectCommand scmd = DAOFactory.getSelectCommand(clazz);
            Filter filter1 = DAOFactory.getFilter();
            Filter filter2 = DAOFactory.getFilter("messageReceiver",
                    SQLOperator.EQUAL, user.getUserId());
            Filter filter3 = DAOFactory.getFilter("creatorId",
                    SQLOperator.NOT_EQUAL, "3");
            Filter filter4 = DAOFactory.getFilter("useState",
                    SQLOperator.EQUAL, "Y");
            filter1.addFilter(filter2);
            filter1.addFilter(filter3, LogicOperator.AND);
            filter1.addFilter(filter4, LogicOperator.AND);
            scmd.setFilter(filter1);
            Session session = SessionFactory.openSession();
            messageList = session.query(scmd);
        }
        // 查询系统发送的消息
        else if (choose.equals("2")) {
            message.setMessageReceiver(user.getUserId());
            Long sys = new Long("3");
            message.setCreatorId(sys);
            message.setUseState("Y");
            messageList = dp.query(message);
        }
        // 处理我发送的信息
        else if (choose.equals("3")) {
            message.setCreatorId(user.getUserId());
            message.setUseState("Y");
            messageList = dp.query(message);
        }
        for (int i = 0; i < messageList.size(); i++) {
            message = (Message) messageList.get(i);
            content = content
                    + "<TR id=trBG"
                    + i
                    + "><TD class=tdline28 noWrap align=left><INPUT onclick=\"Message.changeTRbg(this,'trBG"
                    + i + "')\" type=\"checkbox\" value=\""
                    + message.getMessageId()
                    + "\" name=\"delete_messgaeId\"></TD>";
            if (choose.equals("1")) {
                content = content + "<TD class=tdline28 align=left>"
                        + message.getUserName() + "</TD>";
            } else if (choose.equals("2")) {
                content = content + "<TD class=tdline28 align=left>"
                        + message.getUserName() + "</TD>";
            } else {
                content = content + "<TD class=tdline28 align=left>"
                        + message.getMessageReceiverName() + "</TD>";
            }
            content = content + "<TD class=tdline28 align=right>&nbsp;</TD>";
            content = content
                    + "<TD class=tdline28 align=left><A class=\"bluelink \" onclick=; href=\"javascript:Message.newsShowHide('news"
                    + i + "');Message.setRead(" + message.getMessageId()
                    + ");Message.showMSG(" + i + "," + message.getMessageId()
                    + ");\">" + message.getMessageTitle() + "</A></TD>";
            content = content + "<TD class=tdline28 noWrap align=left>"
                    + message.getBuildTime() + "</TD></TR>";
            content = content
                    + "<TR ><TD class=tdline28 id=news"
                    + i
                    + " style=\"DISPLAY: none\" noWrap align=left colSpan=5><BR><TABLE class=zh cellSpacing=0 cellPadding=0 width=\"96%\" align=center border=0><TBODY><TR>";
            if (choose.equals("1")) {
                content = content + "<TD><DIV id=MSGcontent" + i
                        + "></DIV><BR><FONT id=replyLink" + i
                        + "><A class=bluelink href=\"javascript:Message.reply("
                        + i + "," + message.getMessageId() + ","
                        + message.getMessageReceiver() + ",'"
                        + message.getMessageTitle() + "',"
                        + message.getCreatorId() + ")\">回复此消息</A> </FONT></TD>";
            } else {
                content = content + "<TD><DIV id=MSGcontent" + i
                        + "></DIV></TD>";
            }
            content = content
                    + "<TD vAlign=top noWrap align=right width=50><FONT id=close"
                    + i
                    + "><A class=bluelink onclick=\"Message.newsShowHide('news"
                    + i
                    + "')\" href=\"#\">关闭</A></FONT></TD></TR></TBODY></TABLE>";
            content = content + "<DIV id=rt" + i + "></DIV><BR></TD></TR>";
        }
        content = content
                + "<TABLE  height=24 cellSpacing=0 cellPadding=0 width=\"100%\" align=center bgColor=#f3f3f3 border=0>";
        content = content + "<TBODY><TR vAlign=center><TD class=pad10L noWrap>";
        content = content
                + "<LABEL for=checkall><INPUT id=checkall style=\"MARGIN: 0px\" onclick=Message.checkAll(this.checked) type=checkbox value=checkbox name=checkall>&nbsp;全选</LABEL> &nbsp;&nbsp;</TD>";
        content = content
                + "<TD class=pad10L id=adel noWrap align=right><A class=bluelink href=\"javascript:Message.del()\">删除</A></TD><TD align=middle>&nbsp;&nbsp;</TD></TR>";
        content = content + "</TBODY></TABLE></TBODY></TABLE>";
        try {
            response.getWriter().println(content);
        } catch (IOException ex) {
            ex.getStackTrace();
        }

    }

    public void getMessageContent() throws EasyJException, IOException {
        Message message = new Message();
        Message resultMessage = new Message();
        message = (Message) object;
        resultMessage = (Message) dp.get(message);
        message.setMessageId(resultMessage.getReMessageId());
        StringBuffer xml = new StringBuffer("<result><MSGcontentId>");
        xml.append(request.getParameter("MSGcontentId"));
        xml.append("</MSGcontentId>");
        xml.append("<content>" + resultMessage.getMessageContent()
                + "</content>");
        if (message.getMessageId() == null) {
            xml.append("<recontent>noreference</recontent>");
        } else {
            resultMessage = (Message) dp.get(message);
            xml.append("<recontent>" + resultMessage.getMessageContent()
                    + "</recontent>");
        }

        xml.append("</result>");
        response.setContentType("text/xml");
        response.getWriter().write(xml.toString());
    }

    public void writeMessage() throws EasyJException, IOException {
        String tr = (String) request.getParameter("rtId");
        Message message = (Message) object;
        StringBuffer xml = new StringBuffer();
        message.setIsRead("N");
        try {
            dp.create(message);
            xml.append("<begin><result>success</result><rtId>" + tr
                    + "</rtId></begin>");
        } catch (EasyJException e) {
            xml.append("<result>failure</result>");
        }
        response.setContentType("text/xml");
        response.getWriter().write(xml.toString());
    }

    public void deleteMessage() throws EasyJException {

        String name[] = request.getParameterValues("delete_messgaeId");
        Object object = BeanUtil
                .getEmptyObject("cn.edu.pku.dr.requirement.elicitation.data.Message");
        Class clazz = object.getClass();
        try {
            dp.deleteBatch(clazz, name);
            returnMessage = "sucess";
        } catch (EasyJException e) {
            returnMessage = "faliure";
        }
    }

    public void setRead() throws EasyJException {
        SingleDataProxy sdp = SingleDataProxy.getInstance();

        Long messageId = new Long(request.getParameter("messageId"));
        Message msg = new Message();
        msg.setMessageId(messageId);
        msg = (Message) sdp.get(msg);
        if (msg.getIsRead().equals("N")) {

            msg.setIsRead("Y");
            sdp.update(msg);
        }
    }

    public void writeMessageToFriend() throws EasyJException {
        Message message = new Message();
        message = (Message) object;
        Object o = BeanUtil.getEmptyObject("easyJ.system.data.SysUser");
        Class clazz = o.getClass();
        SelectCommand scmd = DAOFactory.getSelectCommand(clazz);
        Filter filter1 = DAOFactory.getFilter("userName", SQLOperator.EQUAL,
                message.getUserName());

        scmd.setFilter(filter1);
        Session session = SessionFactory.openSession();
        ArrayList userId = session.query(scmd);
        if (userId.size() > 0) {
            SysUser sys = (SysUser) userId.get(0);
            message.setMessageReceiver(sys.getUserId());
            SysUserCache syscuercache = (SysUserCache) request.getSession()
                    .getAttribute(Globals.SYS_USER_CACHE);
            SysUser sysuser = syscuercache.getUser();
            message.setCreatorId(sysuser.getUserId());
            message.setIsRead("N");
            try {
                dp.create(message);
                returnMessage = "sucess";
                BusinessObject businessObject = new BusinessObject();
                SystemMessage systemMessage = new SystemMessage(businessObject);
                String msg = new String("您刚刚给您的朋友发送了一条消息");
                businessObject.setMessage(msg);

            } catch (EasyJException e) {
                returnMessage = "faliure";
            }
        } else {
            returnMessage = "faliure";
        }
    }

    public void remindNewMessage() throws EasyJException, IOException {
        Message message = new Message();
        message = (Message) object;
        message.setIsRead("N");
        message.setMessageReceiver(userCache.getUser().getUserId());
        ArrayList notreadMessage = dp.query(message);
        StringBuffer xml = new StringBuffer();
        if (notreadMessage.size() > 0) {
            xml.append("<result><message>success</message><num>"
                    + notreadMessage.size() + "</num></result>");
        } else {
            xml.append("<result><message>failure</message></result>");
        }
        response.setContentType("text/xml");
        response.setHeader("Cache-Control", "no-cache");
        response.getWriter().println(xml.toString());
    }

    public void testMessage(String msg) throws EasyJException {
        this.msg = msg;
        Object object = BeanUtil
                .getEmptyObject("cn.edu.pku.dr.requirement.elicitation.data.Message");
        Message message = new Message();
        message = (Message) object;
        // Long creatorId = new Long("3");
        // message.setCreatorId(creatorId);
        // message.setMessageContent(this.msg);
        message.setMessageTitle("***系统自动的信息***");
        // message.setUseState("Y");
        // Long receiverId = new Long("1");
        // message.setMessageReceiver(receiverId);
        // System.out.println(message.getMessageContent());
        try {
            dp.create(message);
        } catch (EasyJException e) {}

    }
}
