<%@ page contentType="text/html; charset=gb2312" language="java"
	import="java.sql.*" errorPage=""%>
<%@ taglib uri="/struts-tags" prefix="s"%> 

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<SCRIPT type="text/javascript">
function register(){
	login.action="user/register.jsp";
	login.submit();
}
</SCRIPT>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
		<title>�����ȡ����</title>
</head>
	<body>
		<p id="title" align="left">
			<a href="http://www.seforge.org">��Դ����ҳ&gt;&gt;</a>�����
		</p>
		<p align="center">
			<img src="image/requirement.gif" height="40%" />
		</p>
		<div align="center">
		<s:form id="login" action="login" namespace="/" theme="simple">
			�û�����<s:textfield name="userName"></s:textfield>
			���룺<s:password name="password"></s:password>
			<s:submit value="��¼"></s:submit>	
			<s:submit type="button" value="ע��" onclick="register();"></s:submit>		
		</s:form>
		</div>
	</body>
</html>
