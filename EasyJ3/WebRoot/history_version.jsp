<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>历史版本</title>
</head>
<body>
history.

	<table class="history_version_table">
		<tr>
			<th>ID</th>
			<th>标题</th>
			<th>时间</th>
			<th>操作</th>
			<th>采纳方案</th>
		</tr>
		<s:iterator id ="scenario" value="historyVersion">
		<tr class="history_version_tr">		
			<td><s:property value="#scenario.scenarioId"/></td>
			<td><s:property value="#scenario.scenarioName"/></td>
			<td><s:property value="#scenario.buildTime"/></td>
			<s:url id="versionUrl" value="getScenarioDetail.do">
							<s:param name="scenarioId">
								<s:property value="#scenario.scenarioId" />
							</s:param>
			</s:url>
			<td><s:a href="%{versionUrl}">查看</s:a></td>
			<s:url id="solutionUrl" value="solution.jsp">
				<s:param name="solutionId" value="#scenario.bestSolution.id"/>
			</s:url>
			<td><s:a href="%{solutionUrl}"><s:property value="#scenario.bestSolution.name" /></s:a></td>
		</tr>			
		</s:iterator>
	</table>
</body>
</html>