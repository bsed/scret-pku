<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>场景</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
	
		<link rel="stylesheet" type="text/css" href="css/layout.css">
	

	</head>

	<body>
		<div class="header"></div>
		<div id="main_scenario">
			<div class="scenarioName">
				<s:property value="model.scenarioName" />
			</div>
			<div id="main_scenario_description">
				<s:property value="model.scenarioDescription" />
			</div>
			<div class="data">
				数据
				<table class="dataTable">
					<s:iterator id="data" value="model.dataList">
						<tr>
							<td class="dataName">
								<s:property value="#data.dataName" />
							</td>
							<td class="dataDescription">
								<s:property value="#data.dataDes" />
							</td>
						</tr>
					</s:iterator>

				</table>
			</div>
			<div class="role">
				角色
				<table class="roleTable">
					<s:iterator id="role" value="model.roleList">
						<tr>
							<td class="roleName">
								<s:property value="#role.roleName" />
							</td>
							<td class="roleDescription">
								<s:property value="#role.roleDes" />
							</td>
						</tr>
					</s:iterator>
				</table>
			</div>
			<div class="roleDes">
				<table>
					<s:iterator id="roleMap" value="model.roleMap">
						<tr>
							<td class=roleName>
								<s:property value="key.roleName" />
							</td>
							<td class=roleDes>
								<s:property value="value" />
							</td>
						</tr>
					</s:iterator>
				</table>
			</div>
		</div>
		<!-- main_scenario -->



		<div class="footer">
			copyright
		</div>
	</body>
</html>
