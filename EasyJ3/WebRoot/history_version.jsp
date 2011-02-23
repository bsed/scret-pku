<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>历史版本</title>
		<script type="text/javascript" src="ext-2.3.0/adapter/ext/ext-base.js"></script>
		<script type="text/javascript" src="ext-2.3.0/ext-all.js"></script>
		<link rel="stylesheet" href="ext-2.3.0/resources/css/ext-all.css"	type="text/css"></link>
		<SCRIPT type="text/javascript" src="version_tree.js"></SCRIPT>
		<STYLE type="text/css">
			.history_version_table
			{
				margin-left:25px;
				border:1px solid ;
				boder-collapse:collapse;
				
			}
			.history_version_table tr td,						
			.history_version_table th
			{
			
				border:1px solid ;
				border-collapse :collapse;
				
				
			}
		</STYLE>
</head>
<body>
<div id="tree_div">
	<a href="javascript:void(0)" id="version_tree">演化树</a>
	<input type="hidden" id="root_id" value='<s:property value="rootScenario.scenarioId"/>'/>
</div>

	<table class="history_version_table">
		<tr>
			<th>ID-1</th>
			<th>标题</th>
			<th>时间</th>
			<th>采纳方案</th>
			<th>ID-2</th>
		</tr>
		<s:iterator id ="scenario" value="historyVersion">
		<tr class="history_version_tr">		
			<s:url id="versionUrl" value="getScenarioDetail.do">
							<s:param name="scenarioId">
								<s:property value="#scenario.scenarioId" />
							</s:param>
			</s:url>
			<td><s:property value="#scenario.scenarioId"/></td>
			<td><s:a href="%{versionUrl}"><s:property value="#scenario.scenarioName"/></s:a></td>
			<td><s:property value="#scenario.buildTime"/></td>
			<td>
				<ol>
					<s:iterator id ="best" value="#scenario.preSolutions">
							<s:url id="solutionUrl" value="solution.jsp">
								<s:param name="solutionId" value="#best.id"/>
							</s:url>
						<li>
							<s:a href="%{solutionUrl}">
								<s:property value="#best.name" />
							</s:a>
						</li>
					</s:iterator>
				</ol>
			</td>
			<td><!-- 进化以后的ID -->
				<ol>
					<s:iterator id="next" value="#scenario.nextScenarios">					
						<li>
							<s:property value="#next.scenarioName" />(<s:property value="#next.scenarioId"/>)
						</li>
					</s:iterator>
				</ol>
			</td>
		</tr>			
		</s:iterator>
	</table>
</body>
</html>