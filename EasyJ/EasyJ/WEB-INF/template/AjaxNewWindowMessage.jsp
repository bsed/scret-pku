<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib uri="/WEB-INF/easyJ.tld" prefix="easyJ" %>
<%@ page import="easyJ.system.service.*,java.util.*,easyJ.common.*,easyJ.common.validate.*"%>
<%@ page import="easyJ.http.Globals,java.util.ArrayList,easyJ.business.proxy.SingleDataProxy,cn.edu.pku.dr.requirement.elicitation.data.Message"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="Expires" content="0">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">

<script language="javascript" src="js/Message.js"></script>


<easyJ:QueryHiddenTag/>

<STYLE type=text/css>
body{margin:4px 8px 0 0}
td{font-size:12px;line-height:18px}
a:link {text-decoration: underline}
a:visited {color: #261cdc; text-decoration: underline}
.rightarea{margin-left:155px!important;margin-left:15px;padding-top:0px;padding-left:0px;width:auto!important;width:100%}
.leftarea{float:left;padding-left:5px;padding-top:0px;width:150px!important;width:150px;margin-right:-5px}
.welcome{font-weight:bold;width:147px!important;width:147px;margin-left:0px;margin-top:0px;}
.subitema{padding:5px 0 5px 0px;width:150px;font-size:12px;}
.bgactive{padding:5px 0 5px 0px;width:150px;font-weight:bold;background-color:#D9F0F0;font-size:12px;text-decoration:none;}
.subitema14{padding:5px 0 5px 0px;width:150px;font-size:14px;}
.bgactive14{padding:5px 0 5px 0px;width:150px;font-weight:bold;background-color:#D9F0F0;font-size:14px;text-decoration:none;}

.hdch{margin-top:3px;height:21px;font-size:14px;font-family:arial}
a.top{font-family:arial}
a.top:link {COLOR: #0000cc; text-decoration: underline}
a.top:visited {COLOR: #800080; text-decoration: underline}
a.top:active {COLOR: #0000cc; text-decoration: underline}
.fB{ font-weight:bold;}
.i{font-size:16px; font-family:arial}
.c{color:#7777CC;}
a.c{color:#7777CC;}
a.c:visited{color:#7777CC;}
a.bluelink:link{color:#0033cc;}
a.bluelink:hover{color:#0033cc;}
a.bluelink:visited{color:#800080;}

.Tit1{height:24px;line-height:24px;font-size:14px;font-family:Arial}
.usrbar{font-size:12px;height:19px;line-height:19px;font-family:Arial}
#ft_ms{clear:both;margin-top:10px;line-height:20px;text-align:center}
#ft_ms,#ft_ms *{color:#77C;font-size:12px;font-family:Arial;white-space:nowrap}
.c{color:#7777CC}
a.c{color:#7777CC}
a.c:visited{color:#7777CC}
.BG3{background-color:#E7FBE3}
.ud {PADDING-RIGHT: 0px; PADDING-LEFT: 15px; PADDING-BOTTOM: 0px; LINE-HEIGHT: 24px; PADDING-TOP: 0px; HEIGHT: 24px; BACKGROUND-COLOR: #d9f0f0
}
.l {
	BORDER-LEFT: #d9f0f0 20px solid;

	width:100%;
	position:relative;
	zoom:1

}
</STYLE>
	<!----------左侧树列表---------->
    <%

		SingleDataProxy dp=SingleDataProxy.getInstance();
		Message message = new Message();
		Long userId = new Long(request.getParameter("userId"));
   		message.setCreatorId(userId);
  		ArrayList messageList=dp.query(message);
		int sent_num = messageList.size();

  		message.setCreatorId(null);
  		message.setMessageReceiver(userId);
  		messageList=dp.query(message);
  		int total_msg_num = messageList.size();
  		message.setIsRead("N");
  		messageList=dp.query(message);
  		int total_msg_num_notread = total_msg_num -messageList.size();

		message.setIsRead(null);
  		message.setCreatorId(new Long(2));
  		messageList=dp.query(message);
  		int sys_num = messageList.size();
  		message.setIsRead("N");
  		messageList=dp.query(message);
  		int sys_num_notread = sys_num - messageList.size();

  		int friends_msg_num = total_msg_num - sys_num;
  		int friends_msg_num_notread = total_msg_num_notread - sys_num_notread;

    %>

	<!----------左侧树列表---------->
	<div style="text-align:center"><FONT>消息系统</FONT></div>
	<div class="leftarea"><div >我收到的消息<span id="msg_num"></span></div>
	<div id="leftarea"></div>
	<DIV class=bgactive id=0><span style="cursor:pointer" style="padding-left:18px" onclick="Message.getMessage(1)">我的消息</span><span id="msg_num"></span></DIV>
	<DIV class=subitema id=2><span style="cursor:pointer" style="padding-left:18px" onclick="Message.getMessage(2)">系统消息</span><span id="msg_num"></span></DIV>
	<DIV class=subitema id=3><span style="cursor:pointer" onclick="Message.getMessage(3)">我发送的消息</span></DIV></DIV>
	<!--------左侧树列表结束-------->


	<div id="page" style="display:none"></div>



	<div class="rightarea" id="rightarea" >
	<DIV class=l>
	<DIV class=ud><SPAN style="FLOAT: right">**消息提示处**</span></div>
	<DIV class=con id="boforewritemessage">
	<DIV  id="writemessage"></div>
	<DIV style="MARGIN-BOTTOM: 10px" id ="messages">


	</div>

	</DIV>



	<DIV class=ud>&nbsp;</DIV>

	</div>

	</div>

	<script language="javascript">
window.onload = Message.getMessage(1);
</script>
	<!--------右侧消息列表-------->



