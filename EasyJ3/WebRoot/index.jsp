<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<%@ page import="elicitation.model.user.SysUser" %>
<%@page import="com.opensymphony.xwork2.ActionContext"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
		<title>协同式需求建模</title>
		<script type="text/javascript" src="ext-2.3.0/adapter/ext/ext-base.js"></script>

		<link rel="stylesheet" href="ext-2.3.0/resources/css/ext-all.css"
			type="text/css"></link>
		<script type="text/javascript" src="ext-2.3.0/ext-all.js"></script>
		
		<script type="text/javascript" src="scripts/log.js"></script>
		<link rel="stylesheet" style="text/css" href="css/layout.css" />
	</head>
	<body>
	<%
		//SysUser user = (SysUser)getServletContext().getAttribute("user");
		SysUser user =  (SysUser) request.getSession().getAttribute("user");
		response.setHeader( "Pragma ", "No-cache ");   
		response.setHeader( "Cache-Control ", "no-cache ");   
		response.setDateHeader( "Expires ",   0);   
	%>
	<div id="wrapper">
		<div class="outerwrap">
		<div class="innerwrap">
		<div class="PageTop">
			<div class="widget_core_colls_list_public">
	            <ul>
	              <li></li>
	            </ul>
          	</div>
		</div>
					<div class="pageHeader">
						<div class="widget_core_coll_title">
							<h1>
								<a href="http://192.168.4.133:8080/EasyJ3">协同式需求获取</a>
							</h1>
						</div>
						
						<!-- <div class="widget_core_coll_tagline">
							<p align="right" style="margin-bottom: -10px;">
								欢迎您，
								
							</p>
						</div>-->
						
					</div>
			<!-- 		<div class="header">
		<a href="#" id='register'>注册</a>
		<a href="#" id='login'>登录</a>

		</div> -->
					<div class="top_menu">
						<ul>
							<li>
								<a href="">首页</a>
							</li>
							<li>
								<a href="system_info.jsp">关于系统</a>
							</li>
		<%	
		if(user == null){	
		%>
							<li>
								<a href="#" id='register'>注册</a>
							</li>
							<li>
								<a href="#" id='login'>登录</a>
							</li>
		<%} %>
		<%
						if(user!=null){ 
							String url  ="userCenter.do?userId="+user.getUserId();
							String logout = "logoutServlet.so";
						%>
						<li> 		<a title="个人中心,可以创建领域，项目等" href =<%=url%>><%=user.getUserName() %></a></li>
						<li><a href=<%=logout %>>注销</a></li>
						<%} %>
						</ul>
					</div>
				</div><!--  innerwrap -->
		</div> <!-- outerwrap -->
		<!-- <div class="sector_navigator">
			<a href="#">首页 </a>
			<a href="#"><s:property value="domainName"/></a>
		</div> -->
			<div class="SideBar">
				<div class="left">
					<div class="bSideItem">
						<h2 id="domain_list_title">领域列表</h2>
					</div>
					<div class="bSideItem common_links">
						<ul>
							<!--<span id="domain_list">领域列表</span>-->
							<s:iterator id="domain" value="domains">
								<li>
										
									
									<s:url id="domainUrl" value="getProjectsOfDomain.do">
										<s:param name="domainId" value="#domain.domainId"></s:param>
										<s:param name="_st" value="#domain.rand"></s:param>
									</s:url>
									<s:a href="%{domainUrl}">
										<s:property value="#domain.domainName" />
									</s:a>
								</li>
							</s:iterator>
						</ul>
					</div>
				</div>
			</div>
			<div class="Post">
				<div class="right">
				<div class="domainDes">
					<!--<span id="title">领域描述</span>-->
					<h2 id="title">领域描述</h2>
					<span id="domain_des">				
					<s:property value="domainDescription" />
					</span>
				</div>
				<div class="projectList">
					<h2 id="project_list">工程列表</h2>
					<div class="projectNameandDes">
						<s:iterator id="project" value="projects">
						<div class="project_unit">
							<div class="projectName">
							<s:url id="projectUrl" value="getProjectDetail.do">
								<s:param name="projectId" value="#project.projectId"></s:param>
								<s:param name="_st" value="#project.rand"></s:param>
							</s:url>
							<s:a href="%{projectUrl}">
								<s:property value="#project.projectName" />
								</s:a>
							</div>
							<div class="projectDescription">
								<s:property value="#project.projectDescription" />
							</div>
						</div><!-- project unit -->
						</s:iterator>
					</div>
				</div>
			</div><!-- div right -->
			</div><!-- div Post -->
		<p><br clear="all"></p>
		<div id="footer_wrapper">
				<div id="footer">
					<div id="site_about">
						
						<ul >
							<li><a href="about_us.jsp">关于我们</a></li>
							<li>常见问题</li>
							<li>帮助文档</li>
							<li><a href="contact.jsp">联系我们</a></li>
						</ul>
					</div>
				</div>
		</div>
	</div><!-- wrapper -->
	</body>
</html>
