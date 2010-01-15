<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="easyJ.http.Globals,easyJ.business.proxy.SingleDataProxy,cn.edu.pku.dr.requirement.elicitation.action.ScenarioVersionAction"%>
<%@ taglib uri="/WEB-INF/easyJ.tld" prefix="easyJ" %>

<link href="css/table.css" rel="stylesheet" type="text/css">
<link href="css/pattern.css" rel="stylesheet" type="text/css">

<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="Expires" content="0">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">

<link rel="stylesheet" href="css/scenario.css"/>
<link href="css/iknow1_1.css" rel="stylesheet" type="text/css">
<link href="css/iknow1_1.css" rel="stylesheet" type="text/css">
<link href="css/table.css" rel="stylesheet" type="text/css">

<script language="javascript" src="js/jquery-1.2.3.js"> </script>
<script language="javascript" src="js/jquery.form.js"> </script>
<script language="javascript" src="js/Ajax.js"></script>
<script language="javascript" src="js/Data.js"></script>
<script language="javascript" src="js/Enter2Tab.js"></script>
<script language="javascript" src="js/Problem.js"></script>
<script language="javascript" src="js/Problemsolution.js"></script>
<easyJ:QueryHiddenTag/>
<%
Long problemId = new Long(request.getParameter("problemId"));
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


<jsp:include flush="true" page="/WEB-INF/template/common/Header.jsp"/>



<div id="center2"  style="position:absolute;RIGHT:10%;left:10%;" >
<div class="bai" >
<%--以下部分为问题主体--%>
<div class="mb12 bai"  style="display:none" id="problem_main"> 
<div class="rg_1"></div><div class="rg_2"></div><div class="rg_3"></div>
<div class="rg" id="problem">

</div>
<div class="rg_4"></div><div class="rg_5"></div><div class="rg_1"></div>
</div>
<%--问题主体结束--%>

<%--tab开始--%>
<div id="tabs" style="display:none">
<SPAN class="NewsTitleRight" style="width=120px" onclick="Problem.discussProblemAmbiguity(<%=problemId%>)"><SPAN class=NewsTitleLeft>问题可理解性讨论</SPAN></span>
<SPAN class="NewsTitleRight" style="width=120px" onclick="Problem.discussProblemValue(<%=problemId%>)"><SPAN class=NewsTitleLeft>问题存在价值讨论</SPAN></SPAN>
<SPAN class="NewsTitleRight" style="width=120px" onclick="Problem.discussProblemReason(<%=problemId%>)"><SPAN class=NewsTitleLeft>问题原因讨论</SPAN></SPAN>
<SPAN class="NewsTitleRight" style="width=120px" onclick="Problem.discussProblemSolution(<%=problemId%>)"><SPAN class=NewsTitleLeft>问题解决方案讨论</SPAN></span>
<span 	id="reason" style="display:none">针对问题的原因有<span style="color:#FF0000">1</span>个讨论</span>
<span  id="explan" style="display:none">针对问题的可理解性有<span style="color:#FF0000">1</span>个讨论</span>
<span  id="value" style="display:none">针对问题存在价值有<span style="color:#FF0000">1</span>个讨论</span>
<span  id="solution" style="display:none">针对问题的解决方案有<span style="color:#FF0000">1</span>个讨论</span>
</div>
<%--tab结束--%>

<%--问题可理解性开始--%>
<div class="mb12 bai" id="ambiguity_more" style="display:none">
<div class="rr_1"></div><div class="rr_2"></div><div class="rr_3"></div>
<div class="rr"  id="ambiguity">

</div>
<div class="pl10"><A class="bluelink " onclick=; href="javascript:showAllAmbituity(<%=problemId%>);">查看所有关于可理解性的评论</A> </div>
<div class="rr_4"></div><div class="rr_5"></div><div class="rr_1"></div>
</div>
<%--问题可理解性结束--%>

<%--问题价值开始--%>
<div class="mb12 bai" id="value_more" style="display:none">
<div class="rr_1"></div><div class="rr_2"></div><div class="rr_3"></div>
<div class="rr"  id="problemvalue">

</div>
<div class="pl10"><A class="bluelink " onclick=; href="javascript:showAllProblemvalue(<%=problemId%>);">查看所有关于问题价值的评论</A> </div>
<div class="rr_4"></div><div class="rr_5"></div><div class="rr_1"></div>
</div>
<%--问题价值结束--%>

<%--问题原因开始--%>
<div class="mb12 bai" id="reason_more" style="display:none">
<div class="rr_1"></div><div class="rr_2"></div><div class="rr_3"></div>
<div class="rr"  id="problemreason">

</div>
<div class="pl10"><A class="bluelink " onclick=; href="javascript:showAllProblemreason(<%=problemId%>);">查看所有关于问题原因的评论</A> </div>
<div class="rr_4"></div><div class="rr_5"></div><div class="rr_1"></div>
</div>
<%--问题原因结束--%>

<%--问题解决方案开始--%>
<div class="mb12 bai" id="solution_more" style="display:none">
<div class="rr_1"></div><div class="rr_2"></div><div class="rr_3"></div>
<div class="rr"  id="problemsolution">

</div>
<div class="rr_4"></div><div class="rr_5"></div><div class="rr_1"></div>
</div>
<%--问题解决方案结束--%>



</div>
</div>








<script language="javascript">
window.onload = pageLoaded (<%=problemId%>);
function pageLoaded(){ 

	Problem.loadProblem(<%=problemId%>);
  	Problem.discussProblemAmbiguity(<%=problemId%>);
}

    function showAllAmbituity(problemId){

     <%
    	String ambiguityUrl=java.net.URLEncoder.encode("cn.edu.pku.dr.requirement.elicitation.action.AmbiguityAction.do?ACTION=query&easyJ.http.Globals.CLASS_NAME=cn.edu.pku.dr.requirement.elicitation.data.Ambiguity&easyJ.http.Globals.RETURN_PATH=/WEB-INF/template/AjaxNewWindowViewAll.jsp&choose=ambiguity&problemId=","utf-8");
    %>
    var showUrl=<%="\""+ambiguityUrl+"\""%>;
    window.open("easyJ.http.servlet.FowardAction.do?"+"jspurl=/WEB-INF/template/AjaxNewWindow.jsp&url="+showUrl+problemId,"用户消息","status=yes,scrollbars=yes, toolbar=yes,menubar=yes,location=yes,resizable=yes");
 	}
	
	    function showAllProblemvalue(problemId){

     <%
    	String problemvalueUrl=java.net.URLEncoder.encode("cn.edu.pku.dr.requirement.elicitation.action.ProblemValueAction.do?ACTION=query&easyJ.http.Globals.CLASS_NAME=cn.edu.pku.dr.requirement.elicitation.data.Problemvalue&easyJ.http.Globals.RETURN_PATH=/WEB-INF/template/AjaxNewWindowViewAll.jsp&choose=problemvalue&problemId=","utf-8");
    %>
    var showUrl=<%="\""+problemvalueUrl+"\""%>;
    window.open("easyJ.http.servlet.FowardAction.do?"+"jspurl=/WEB-INF/template/AjaxNewWindow.jsp&url="+showUrl+problemId,"用户消息","status=yes,scrollbars=yes, toolbar=yes,menubar=yes,location=yes,resizable=yes");
 	}
		    function showAllProblemreason(problemId){

     <%
    	String problemreasonUrl=java.net.URLEncoder.encode("cn.edu.pku.dr.requirement.elicitation.action.ProblemReasonAction.do?ACTION=query&easyJ.http.Globals.CLASS_NAME=cn.edu.pku.dr.requirement.elicitation.data.Problemreason&easyJ.http.Globals.RETURN_PATH=/WEB-INF/template/AjaxNewWindowViewAll.jsp&choose=problemreason&problemId=","utf-8");
    %>
    var showUrl=<%="\""+problemreasonUrl+"\""%>;
    window.open("easyJ.http.servlet.FowardAction.do?"+"jspurl=/WEB-INF/template/AjaxNewWindow.jsp&url="+showUrl+problemId,"用户消息","status=yes,scrollbars=yes, toolbar=yes,menubar=yes,location=yes,resizable=yes");
 	}
 			    function showDetailedSolution(problemSolutionId){

     <%
    	String problemSolutionUrl=java.net.URLEncoder.encode("cn.edu.pku.dr.requirement.elicitation.action.ProblemSolutionAction.do?ACTION=query&easyJ.http.Globals.CLASS_NAME=cn.edu.pku.dr.requirement.elicitation.data.Problemsolution&easyJ.http.Globals.RETURN_PATH=/WEB-INF/template/AjaxDetailedSolution.jsp&problemSolutionId=","utf-8");
    %>
    var showUrl=<%="\""+problemSolutionUrl+"\""%>;
    window.open("easyJ.http.servlet.FowardAction.do?"+"jspurl=/WEB-INF/template/AjaxNewWindow.jsp&url="+showUrl+problemSolutionId,"用户消息","status=yes,scrollbars=yes, toolbar=yes,menubar=yes,location=yes,resizable=yes");
 	}
 	 	function createNewSolution(problemId){

    window.open("easyJ.http.servlet.FowardAction.do?"+"jspurl=/WEB-INF/template/AjaxNewWindowNewSolution.jsp&problemId="+problemId,"创建新解决方案","status=yes,scrollbars=yes, toolbar=yes,menubar=yes,location=yes,resizable=yes");
 	
 	}
</script>