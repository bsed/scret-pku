<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib uri="/WEB-INF/easyJ.tld" prefix="easyJ" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="Expires" content="0">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<easyJ:QueryHiddenTag/>
<STYLE type=text/css>
body{margin:4px 8px 0 0}
td{font-size:12px;line-height:18px}
a:link {text-decoration: underline}
a:visited {color: #261cdc; text-decoration: underline}
.rightarea{margin-left:155px!important;margin-left:150px;padding-top:0px;padding-left:0px;width:auto!important;width:100%}
.leftarea{float:left;padding-left:5px;padding-top:0px;width:150px!important;width:150px;margin-right:-5px}
.welcome{font-weight:bold;width:147px!important;width:147px;margin-left:5px;margin-top:5px;font-size:14px;}
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
</STYLE>


<div style="position:relative;width:98%;">

	<!----------左侧树列表---------->
	<div class="leftarea"><div class="welcome">我收到的消息<span id="msg_num"></span></div>
	<div id="leftarea"></div>
	<DIV class=subitema id=0><A href="http://msg.baidu.com/?ct=109000&amp;cm=AcSendPrepare&amp;tn=bmMSGContainer#"><FONT
style="PADDING-LEFT: 18px">好友消息</FONT></A></DIV>
	<DIV class=bgactive id=1><A href="http://msg.baidu.com/?ct=109000&amp;cm=AcSendPrepare&amp;tn=bmMSGContainer#"><FONT
style="PADDING-LEFT: 18px">陌生人消息</FONT></A></DIV>
	<DIV class=subitema id=2><A href="http://msg.baidu.com/?ct=109000&amp;cm=AcSendPrepare&amp;tn=bmMSGContainer#"><FONT
style="PADDING-LEFT: 18px">系统消息</FONT></A></DIV>
	<DIV class=subitema id=3><A href="http://msg.baidu.com/?ct=109000&amp;cm=AcSendPrepare&amp;tn=bmMSGContainer#"><FONT
style="PADDING-LEFT: 5px; FONT-SIZE: 14px">我发送的消息</FONT><FONT style="FONT-SIZE: 14px">(1)</FONT></A></DIV></DIV></div>
	<!--------左侧树列表结束-------->

	<!--------右侧消息列表-------->

	<div class="rightarea">
<script>
var resultno=0;
var gp="",gp1="";
switch(resultno)
{
 case 0:
      gp="好友消息";
      gp1="收到"+gp;
      break;
 case 1:
      gp="陌生人消息";
      gp1="收到"+gp;
      break;
 case 2:
      gp="系统消息";
      gp1="收到"+gp;
      break;
 case 3:
      gp="发送消息";
      gp1=gp;
      break;
}
</script>
<div class="titbar f12" align="right"><script>if(resultno!=3){document.write("0条"+gp+"，0条未读");}else{document.write("共发送 0 条消息");}</script></div>
<div style="width:98%; padding-left:10px;font-size:12px;">

<script>
if(0!=2&&0!=3)
document.write('<br><a href="/?cm=AcSendPrepare&tn=bmMessagePost&ct=109000&rid=0" style="padding:5px 10px 5px 10px !important;padding:4px 10px 4px 10px;height:1px;border:1px solid #7AA9DF;background-color:#EAF3FC;font-size:12px">发送新消息</a><br>');
</script>


<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" style="margin-bottom:8px; margin-top:8px;">

</table>

<div style="padding:70px 0 100px 0;font-size:12px;line-height:18px" align="center">您暂时还没有<script>document.write(gp1);</script></div>
<div style="margin:0px 15px 0px 15px;padding:10px 0 15px 0;border-top:1px solid #E1E1E1;font-size:12px;line-height:18px">
如何发送消息？<br>
  1.您可以点击某个用户的用户名进入用户信息中心页面，点击页面中的“发送消息”链接，即可向该用户发送消息。<br>
  2.您还可以直接进入消息页面点击”发送新消息”链接，输入发送对象的用户名，即可向该用户发送消息。</div>
</div>
<div class="titbar">　</div>
</div>
<div>
	<iframe id="ifr" name="ifr" src="about:blank" width="100%" height="200" frameborder="0"  scrolling="no"></iframe>
	
	<!--------右侧消息列表结束-------->

</div>
<br>
<input id="sUrl" type="hidden" value="/?ct=18&cm=3&tn=bmPPStat&un=">


<!--scriptDIV-->
<div id="scriptDiv">
<script language="JavaScript" src="/?ct=18&cm=3&tn=bmPPStat&un="></script>
</div>
<!--scriptDIV结束-->

<script language="JavaScript" src="/msgContainer.js"></script>



