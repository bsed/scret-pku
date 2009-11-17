<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="easyJ.http.Globals,easyJ.business.proxy.SingleDataProxy,cn.edu.pku.dr.requirement.elicitation.action.ScenarioVersionAction,java.lang.String"%>
<%@ taglib uri="/WEB-INF/easyJ.tld" prefix="easyJ" %>

<link href="/css/table.css" rel="stylesheet" type="text/css">
<link href="/css/pattern.css" rel="stylesheet" type="text/css">

<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="Expires" content="0">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">

<link rel="stylesheet" href="/css/scenario.css"/>
<link href="/css/iknow1_1.css" rel="stylesheet" type="text/css">
<link href="/css/iknow1_1.css" rel="stylesheet" type="text/css">
<link href="/css/table.css" rel="stylesheet" type="text/css">

<script language="javascript" src="/js/jquery-1.2.3.js"> </script>
<script language="javascript" src="/js/jquery.form.js"> </script>
<script language="javascript" src="/js/Ajax.js"></script>
<script language="javascript" src="/js/Data.js"></script>
<script language="javascript" src="/js/Enter2Tab.js"></script>
<script language="javascript" src="/js/Problem.js"></script>
<easyJ:QueryHiddenTag/>
<%
Long problemId = new Long(request.getParameter("problemId"));
String choose = new String();
choose = request.getParameter("choose").toString();

%>


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
.BG3{background-color:#E7FBE3}
</style>


<div id="center2"  style="position:absolute;RIGHT:10%;left:10%;"  >
<div class="bai" >


<div class="mb12 bai" id="value_more" >
<div class="rr_1"></div><div class="rr_2"></div><div class="rr_3"></div>
<div class="rr">

<div class="t1" id="ambiguity_head"><div class="ico"><div class="ibest"></div></div>问题可理解性讨论页面<button  style="display:none" onClick="Problem.remark()" id="do_comment">关闭评论</button></div>
<div class="t1" id="problemvalue_head"><div class="ico"><div class="ibest"></div></div>问题价值讨论页面<button  style="display:none" onClick="Problem.remark()" id="do_comment">关闭评论</button></div>
<div class="t1" id="problemreason_head"><div class="ico"><div class="ibest"></div></div>问题原因讨论页面<button  style="display:none" onClick="Problem.remark()" id="do_comment">关闭评论</button></div>

<div class="bc0" style="padding:0px 0pt;">

<div id="ambiguity" style="display:none">

<div class="t2">对于问题可理解值的详细解释：</div>
<a  id="order1" href="javascript:Problem.orderByGroup(<%=problemId%>)"  style="padding:5px 10px 5px 10px !important;padding:4px 10px 4px 10px;height:1px;border:1px solid #7AA9DF;background-color:#EAF3FC;font-size:12px">按分类排序</a>
<div id="order11" style="display:none">false</div>

<a  id="order2" href="javascript:Problem.orderByGoodNum(<%=problemId%>)"  style="padding:5px 10px 5px 10px !important;padding:4px 10px 4px 10px;height:1px;border:1px solid #7AA9DF;background-color:#EAF3FC;font-size:12px">按好评排序</a>
<div id="order22" style="display:none">false</div>
</div>

<div id="problemvalue" style="display:none">
<div class="t2">对于问题价值的详细解释：</div>
<a  id="problemvalue_order1" href="javascript:Problem.problemvalueOrderByGroup(<%=problemId%>)"  style="padding:5px 10px 5px 10px !important;padding:4px 10px 4px 10px;height:1px;border:1px solid #7AA9DF;background-color:#EAF3FC;font-size:12px">按分类排序</a>
<div id="problemvalue_order11" style="display:none">false</div>

<a  id="problemvalue_order2" href="javascript:Problem.problemvalueOrderByGoodNum(<%=problemId%>)"  style="padding:5px 10px 5px 10px !important;padding:4px 10px 4px 10px;height:1px;border:1px solid #7AA9DF;background-color:#EAF3FC;font-size:12px">按好评排序</a>
<div id="problemvalue_order22" style="display:none">false</div>
</div>

<div id="problemreason" style="display:none">
<div class="t2">对于问题原因的详细解释：</div>
<a  id="problemreason_order2" href="javascript:Problem.problemreasonOrderByGoodNum(<%=problemId%>)"  style="padding:5px 10px 5px 10px !important;padding:4px 10px 4px 10px;height:1px;border:1px solid #7AA9DF;background-color:#EAF3FC;font-size:12px">按好评排序</a>
<div id="problemreason_order22" style="display:none">false</div>
</div>

<div id="viewAllProblemreason" style="display:none">
<%--服务器生成的内容--%>

<%--服务器生成的内容--%>
</div>

<div id="viewAllProblemvalue" style="display:none">
<%--服务器生成的内容--%>

<%--服务器生成的内容--%>
</div>

<div id="viewAll" style="display:none">
<%--服务器生成的内容--%>

<%--服务器生成的内容--%>
</div>

</div>

</div>
<div class="rr_4"></div><div class="rr_5"></div><div class="rr_1"></div>
</div>
</div>












<script language="javascript">
window.onload = pageLoaded (<%=problemId%>,<%=choose%>);
function pageLoaded(problemId,choose){
var choose_string=<%="\""+choose+"\""%>;
	if(choose_string=="ambiguity"){
		document.getElementById("problemvalue_head").style.display = "none";
		document.getElementById("problemvalue").style.display = "none";
		document.getElementById("problemreason_head").style.display = "none";
		document.getElementById("problemreason").style.display = "none";
		document.getElementById("ambiguity_head").style.display = "block";
		document.getElementById("ambiguity").style.display = "block";
		Problem.viewAllAmbiguity(problemId);
	}
	else if(choose_string=="problemvalue"){
		document.getElementById("ambiguity_head").style.display = "none";
		document.getElementById("ambiguity").style.display = "none";
		document.getElementById("problemreason_head").style.display = "none";
		document.getElementById("problemreason").style.display = "none";
		document.getElementById("problemvalue_head").style.display = "block";
		document.getElementById("problemvalue").style.display = "block";
		Problem.viewAllProblemvalue(problemId);
	}
	else if(choose_string=="problemreason"){
		document.getElementById("ambiguity_head").style.display = "none";
		document.getElementById("ambiguity").style.display = "none";
		document.getElementById("problemvalue_head").style.display = "none";
		document.getElementById("problemvalue").style.display = "none";
		document.getElementById("problemreason_head").style.display = "block";
		document.getElementById("problemreason").style.display = "block";
		Problem.viewAllProblemreason(problemId);
	}
	
}
</script>
