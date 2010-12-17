<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
		<title>协同式需求建模</title>
		<link rel="stylesheet" style="text/css" href="css/layout.css" />
	</head>
	<body>
		<div class="header">
		
		</div>
		<!-- <div class="sector_navigator">
			<a href="#">首页 </a>
			<a href="#"><s:property value="domainName"/></a>
		</div> -->
		<div class="left">
			<ul>
			
				<s:iterator id="domain" value="domains">
					<li>
						<s:url id="domainUrl" value="getProjectsOfDomain.do">
							<s:param name="domainId" value="#domain.domainId"></s:param>
						</s:url>
						<s:a href="%{domainUrl}">
							<s:property value="#domain.domainName" />

						</s:a>
					</li>
				</s:iterator>
			
			</ul>
		</div>
		<div class="right">
			<div class="domainDes">
				<s:property value="domainDescription" />
			</div>
			<div class="projectList">
				<div class="projectNameandDes">
					<s:iterator id="project" value="projects">
						<div class="projectName">
						<s:url id="projectUrl" value="getProjectDetail.do">
							<s:param name="projectId" value="#project.projectId"></s:param>
						</s:url>
						<s:a href="%{projectUrl}">
							<s:property value="#project.projectName" />
							</s:a>
						</div>
						<div class="projectDescription">
							<s:property value="#project.projectDescription" />
						</div>
					</s:iterator>
				</div>
			</div>
	</body>
</html>
