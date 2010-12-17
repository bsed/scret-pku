<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page isELIgnored ="false"%>
<%@page import="elicitation.service.request.RequestService"%>
<%@ page import="elicitation.model.user.SysUser" %>
<%@page import="elicitation.model.project.Project,elicitation.model.request.Request,java.util.List"%>
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
		<script type="text/javascript" src="ext-2.3.0/adapter/ext/ext-base.js"></script>
		<link rel="stylesheet" href="ext-2.3.0/resources/css/ext-all.css" type="text/css"></link>
		<link rel="stylesheet" style="text/css" href="css/layout.css" />
		<script type="text/javascript" src="ext-2.3.0/ext-all.js"></script>
		<script type="text/javascript" src="domain.js"></script>
		<script type="text/javascript" src="project.js"></script>
		<script type="text/javascript" src="request.js"></script>
	</head>
<%
	SysUser user = (SysUser)request.getSession().getAttribute("user");
%>
	<body>
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
						
					</div>
	<div class="top_menu">
						<ul>
							<li>
								<a href="">首页</a>
							</li>
							<li>
								<a href="">关于系统</a>
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
						<li> 		<a href =<%=url%>><%=user.getUserName() %></a></li>
						<li><a href=<%=logout %>>注销</a></li>
						<%} %>
						</ul>
					</div>
					
				</div>
				<!--  innerwrap -->
			</div>
			<!-- outerwrap -->
	<!-- <div class="user_center_welcome">
	</div> -->
	<div id="main_user_center">
	
	<div class="user_center_function">
		<ul>
			<li><a href="#" id="add_domain"  class="add_domain" onclick="return false">创建新领域</a></li>
			<li><a href="#" id="add_project" class="add_project" onclick="return false">创建新项目</a></li>
		</ul>	
	</div>
	<div class="user_center_domains">
		<!-- 我创建的领域 -->
		<span id="user_center_domain_tag">我创建的领域</span>
		<div class="domains_des">		
		<table class="table_domainCreated">
			<!-- <th>
			我创建的领域
			</th> -->
			<s:iterator id="domain" value="model.domainCreated">
				<s:url id="domainUrl" value="getProjectsOfDomain.do">
					<s:param name="domainId" value="#domain.domainId"></s:param>
				</s:url>
				<tr class="tr_domainCreated">
					<td>
						<s:a href="%{domainUrl}">
							<s:property value="#domain.domainName"/>
						</s:a>
					</td>
					<td class="tr_domain_des">
						<s:property value="#domain.domainDes"/>
					</td>
				</tr>
			</s:iterator>
		</table>
		</div>
	</div>
	<div class="user_center_projects">
		<!--我创建的项目 我参与的项目  -->
		<span id="user_center_project_tag">我创建的项目</span>
		<div class="project_des">
		<table class="table_projectCreated">
		<tr class="head_row">
				<th>项目名称</th>
				<th>项目描述</th>
				<th>我的角色</th>
			</tr>			
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
					<td class="td_project_des">
						<s:property value="#project.projectDescription" />
					</td>
					<td><!-- N requset items -->
						<s:set name="rnum" value="#project.requestNum"/>
						<s:if test="#rnum>0">						
							<a href ="#" class="req_num" id=<s:property value="#project.projectId"/> onclick="return false"> <s:property value="#rnum"/>条请求</a>
						</s:if>						
					</td>
				</tr>
			</s:iterator>
		</table>
		</div>
	</div>
	<div class="user_center_project_involves">
	<span id="user_center_project_involve_tag">我参与的项目</span>
	<div class="project_des">
		<table class="table_projectAttended">
	
			<tr class="head_row">
				<th>项目名称</th>
				<th>项目描述</th>
				<th>我的角色</th>
			</tr>
			<!-- upr=>user_project_role.Need to display the user's role in the project -->
			<s:iterator id="upr" value="model.projectsAttended">
				<s:url id="projectUrl" value="getProjectDetail.do">
					<s:param name="projectId" value="#upr.project.projectId"></s:param>
				</s:url>
				<tr class="tr_projectAttended">
					<td>
						<s:a href="%{projectUrl}">
							<s:property value="#upr.project.projectName" />
						</s:a>
					</td>
					<td class="td_project_des">
						<s:property value="#upr.project.projectDescription" />
					</td>
					<s:iterator id="role" value="#upr.roles">
						<td><s:property value="#role.roleName"/></td>
					</s:iterator>
				</tr>
			</s:iterator>
		</table>
	</div>
	</div>
	</div><!-- main_user_center -->
	<p><br clear="all"></p>
	<div id="footer_wrapper">
				<div id="footer">
					<div id="site_about">
						
						<ul >
							<li>关于我们</li>
							<li>常见问题</li>
							<li>帮助文档</li>
							<li>联系我们</li>
						</ul>
					</div>
				</div>
	</div>
	</div><!-- wrapper -->
	</body>
</html>
