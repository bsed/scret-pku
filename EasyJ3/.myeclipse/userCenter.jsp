<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>个人中心</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

	</head>

	<body>
		<!--我创建的项目 我参与的项目  -->
		<table class="table_projectCreated">
			<s:iterator id="project" value="model.projectsCreated">
				<s:url id="projectUrl" value="getProjectDetail.do">
					<s:param name="projectId" value="#project.projectId"></s:param>
				</s:url>
				<tr class="tr_projectCreated">
					<td>
						<s:a href="%{projectUrl}">
							<s:property value="#project.projectName" />
						</s:a>
					</td>
					<td>
						<s:property value="#project.projectDescription" />
					</td>
				</tr>
			</s:iterator>

		</table>
	</body>
</html>
