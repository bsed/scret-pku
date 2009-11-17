<%@ page contentType="text/html; charset=utf-8"%>
<%@ page
	import="easyJ.http.Globals,easyJ.business.proxy.SingleDataProxy,cn.edu.pku.dr.requirement.elicitation.action.ScenarioVersionAction,java.lang.String"%>
<%@ taglib uri="/WEB-INF/easyJ.tld" prefix="easyJ"%>

<link href="/css/table.css" rel="stylesheet" type="text/css">
<link href="/css/pattern.css" rel="stylesheet" type="text/css">

<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="Expires" content="0">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">

<link rel="stylesheet" href="/css/scenario.css" />
<link href="/css/iknow1_1.css" rel="stylesheet" type="text/css">
<link href="/css/iknow1_1.css" rel="stylesheet" type="text/css">
<link href="/css/table.css" rel="stylesheet" type="text/css">

<script language="javascript" src="/js/jquery-1.2.3.js"> </script>
<script language="javascript" src="/js/jquery.form.js"> </script>
<script language="javascript" src="/js/Ajax.js"></script>
<script language="javascript" src="/js/Data.js"></script>
<script language="javascript" src="/js/Enter2Tab.js"></script>
<script language="javascript" src="/js/Problem.js"></script>
<script language="javascript" src="/js/ProblemsolutionReply.js"></script>
<script language="javascript" src="/js/Problemsolution.js"></script>
<easyJ:QueryHiddenTag />
<%
	Long problemSolutionId = new Long(request
			.getParameter("problemSolutionId"));
%>


<style>
.cpro table {
	margin: auto
}

.cpro {
	text-align: center;
	float: auto;
}

.mr20 {
	margin-right: 20px;
}

.note {
	background: url(http://img.baidu.com/img/iknow/popo.gif) no-repeat left;
	margin: 12px 0;
	padding-left: 20px;
	font-weight: bold;
}

.answer1,.answer2,.answer3 {
	background: url(http://img.baidu.com/img/iknow/answer.gif) no-repeat;
	width: 100px;
	height: 25px;
	overflow: hidden
}

.answer1 {
	background-position: 0 0
}

.answer2 {
	background-position: 0 -25px
}

.answer3 {
	background-position: 0 -50px
}

.rk2,.rk4 {
	right: -2px
}

#Lg {
	border-top: 1px dashed #bbb;
}

.irelate {
	background: transparent url(http://img.baidu.com/img/iknow/icons.gif)
		no-repeat;
	width: 16px;
	height: 16px;
	margin-right: 2px;
	overflow: hidden;
	float: left;
	background-position: 0 -176px;
}

#cproshow .r {
	word-break: break-all;
	cursor: hand;
	width: 100%;
}

#cproshow div.r font {
	font-size: 14px;
}

#cproshow div.r  span font {
	font-size: 12px;
}

.relateTag {
	padding-top: 12px;
	padding-left: 12px;
}

.relateTag a {
	white-space: nowrap;
	word-break: keep-all;
	margin-left: 1em;
}

.relateTable td {
	font-size: 14px;
	text-align: left
}

.BG3 {
	background-color: #E7FBE3
}
</style>


<div id="center2" style="position: absolute; RIGHT: 10%; left: 10%;">
	<div class="bai">


		<div class="mb12 bai" id="value_more">
			<div class="rr_1"></div>
			<div class="rr_2"></div>
			<div class="rr_3"></div>
			<div class="rr">

				<div class="t1">
					<div class="ico">
						<div class="ibest"></div>
					</div>
					问题解决方案详细页面
				</div>

				<div class="bc0" style="padding: 0px 0pt;">

					<div id="detailedSolution">
						<%--服务器生成的内容--%>

						<%--服务器生成的内容--%>
					</div>


				</div>

			</div>
			<div class="rr_4"></div>
			<div class="rr_5"></div>
			<div class="rr_1"></div>
		</div>
	</div>











	<script language="javascript">
window.onload = Problem.detailedSolution (<%=problemSolutionId%>);
</script>