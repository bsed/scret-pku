<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="elicitation.model.user.SysUser"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
		<title>需求获取工具</title>


		<script type="text/javascript" src="ext-2.3.0/adapter/ext/ext-base.js"></script>

		<link rel="stylesheet" href="ext-2.3.0/resources/css/ext-all.css"
			type="text/css"></link>
		<script type="text/javascript" src="ext-2.3.0/ext-all.js"></script>
		<script type="text/javascript" src="log.js"></script>
	</head>
	<body>

		<%
		SysUser user = (SysUser)request.getSession().getAttribute("user");
		if(user == null){	
	%>
		<a href="#" id='login'>登录</a>
		<a href="#" id='register'>注册</a>

		<div id="form" style="height: 100">
		</div>
		<div id="window">
		</div>
		<%}else{				
			String url  ="userCenter.do?userId="+user.getUserId();
			String logout = "logoutServlet.so";
		%>
		<div class="welcomeUser">
			欢迎您，
			<a href="<%=url%>"><%=user.getUserName()%></a>
			<a href="<%=logout%>">注销</a>
		</div>
		<%} %>

	</body>
</html>
