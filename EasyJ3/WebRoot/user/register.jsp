<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%> 

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<title>注册</title>
		<script type="text/javascript" src=<%=basePath+"scripts/JSON.js"%>></script>
		<script type="text/javascript" src=<%=basePath+"scripts/json_parse.js"%>></script>
		<script type="text/javascript" src=<%=basePath+"scripts/prototype-1.6.0.3.js"%>></script>
		<SCRIPT type="text/javascript" src=<%=basePath+"scripts/validate.js"%>></SCRIPT>	
		
</head>
<body>
	<div id="regBox">
				<s:actionerror />
				<label id="tip" style="color: red"></label>
				<s:form action="register" namespace="/" validate="true">
					<s:textfield name="userName" label="用户名" onblur="validateName();" ></s:textfield>
					<s:password name="passwd" label="密码"></s:password>
					<s:password name="confirmpw" label="确认密码"></s:password>
					<s:textfield name="email" label="Email"></s:textfield>
					<s:submit cssClass="btn_reg" value="确定注册"></s:submit>
				</s:form>
				
			</div>
</body>
</html>