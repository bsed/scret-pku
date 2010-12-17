<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<link rel="stylesheet" style="text/css" href="css/layout.css" />
		<title>Insert title here</title>
	</head>
	<body>
		<div class="header"></div>
		<div id="main_project">
			<div class="projectName">
				<s:property value="%{project.projectName}" />
			</div>
			<div id="main_project_description">
				<s:property value="%{project.projectDescription}" />
			</div>
			<div class="data">
				数据
				<table class="dataTable">
					<s:iterator id="data" value="dataList">
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
					<s:iterator id="role" value="roleList">
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
			<div class="scenario">
				场景
				<table class="scenarioTable">
					<s:iterator id="scenario" value="scenarioList">
						<tr>
							<td class="scenarioName">
							<s:url id="scenarioUrl" value="getScenarioDetail.do">
								<s:param name="scenarioId" value="#scenario.scenarioId"></s:param>
							</s:url>
								<s:a href="%{scenarioUrl}">
									<s:property value="#scenario.scenarioName" />
								</s:a>
							</td>
							<td class="scenarioDescription">
								<s:property value="#scenario.scenarioDescription" />
							</td>
						</tr>
					</s:iterator>
				</table>
			</div>
		</div>



		<div class="footer">
			copyright
		</div>
	</body>
</html>