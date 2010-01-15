<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="easyJ.http.Globals,easyJ.business.proxy.SingleDataProxy,cn.edu.pku.dr.requirement.elicitation.action.ScenarioVersionAction"%>
<%@ taglib uri="/WEB-INF/easyJ.tld" prefix="easyJ" %>

<link href="css/iknow1_1.css" rel="stylesheet" type="text/css">
<link href="css/table.css" rel="stylesheet" type="text/css">
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

<style>.cpro table{margin:auto}.cpro{ text-align:center;float:auto;}.mr20{margin-right:20px;}.note{background:url(http://img.baidu.com/img/iknow/popo.gif) no-repeat left; margin:12px 0;padding-left:20px;font-weight:bold;}
.answer1,.answer2,.answer3{background:url(http://img.baidu.com/img/iknow/answer.gif) no-repeat;width:100px;height:25px;overflow:hidden}
.answer1{background-position:0 0}
.answer2{background-position:0 -25px}
.answer3{background-position:0 -50px}
.rk2,.rk4{right:-2px}
#Lg{ border-top:1px dashed #bbb;}
.irelate{background: transparent url(http://img.baidu.com/img/iknow/icons.gif) no-repeat;width:16px;height:16px;margin-right:2px;overflow:hidden;float:left;background-position:0 -176px;}
#cproshow .r{ word-break:break-all;cursor:hand;width:100%;}
#cproshow div.r font {font-size:14px;}
#cproshow div.r  span font {font-size:12px;}
.relateTag{padding-top:12px;padding-left:12px;}
.relateTag a{white-space:nowrap;word-break:keep-all;margin-left:1em;}
.relateTable td{font-size:14px;text-align:left}

</style>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="Expires" content="0">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">

<script language="javascript" src="js/jquery-1.2.3.js"> </script>
<script language="javascript" src="js/jquery.form.js"> </script>
<script language="javascript" src="js/Ajax.js"></script>
<script language="javascript" src="js/Data.js"></script>
<script language="javascript" src="js/Enter2Tab.js"></script>
<script language="javascript" src="js/Problem.js"></script>
<script language="javascript" src="js/Problemsolution.js"></script>





<jsp:include  flush="true" page="/WEB-INF/template/common/Header.jsp"/>
<%
Long problemId = new Long(request.getParameter("problemId"));
%>
<form name="form2" id="form2" method="post" action="" type="enter2tab">
<div id="newSolution"></div>
</form>
<script language="javascript">

window.onload = Problemsolution.loadNewSolution(<%=problemId%>);

</script>

