<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="elicitation.model.user.SysUser" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

		<script type="text/javascript" src="ext-2.3.0/adapter/ext/ext-base.js"></script>
		<link rel="stylesheet" href="ext-2.3.0/resources/css/ext-all.css"
			type="text/css"></link>
		<link rel="stylesheet" style="text/css" href="css/layout.css" /> 
		
		<script type="text/javascript" src="ext-2.3.0/ext-all.js"></script>
		<script type="text/javascript" src="data.js"></script>
		<script type="text/javascript" src="role.js"></script>
		<script type="text/javascript" src="scenario.js"></script>
		<script type="text/javascript" src="projectFun.js"></script>
		<script type="text/javascript" src="join_project.js"></script>
		<script type="text/javascript" src="scripts/log.js"></script>
		<script type="text/javascript" src="pick_role.js"></script>
		<title><s:property value="model.projectName" />
		</title>
		<script type="text/javascript">			
		</script>
	</head>
	<%
		SysUser user = (SysUser)request.getSession().getAttribute("user");
   
		response.setHeader( "Pragma ", "No-cache ");   
		response.setHeader( "Cache-Control ", "no-cache ");   
		response.setDateHeader( "Expires ",   0);   
   

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
								<a href="/EasyJ3">协同式需求获取</a>
							</h1>
						</div>
						
					</div>

					<div class="top_menu">
						<ul>
							<li>
								<a href="/EasyJ3">首页</a>
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


			<div id="main_project">
				<input type="hidden" id="project_id"
					value='<s:property value="model.projectId"/>' />
				<div class="des_unit">
					<div class="projectName">
						<s:property value="model.projectName" />
					</div>
					<div id="main_project_description">
						<s:property value="model.projectDescription" />
					</div>
					<div class="main_project_general_info">
						<ul>
							<li>所属领域 : <s:property value="model.domain.domainName"/></li>
							<li>创建人   ：<s:property value="model.creator.userName"/></li>
							<li>创建时间 ：<s:property value="model.buildTime"/></li>
							<%if (user!=null){ %>
							<li>我的角色 : <s:property value="model.userRoleListStr"/></li>
							<%}%>
						</ul>
					</div>
					
					<div class="project_function">
						<s:if test="model.writePermission">
						<!-- 功能区: 增加数据，增加角色 ; 涉及到权限控制了-->
						<a href="#" class="add_data" id="add_data" onclick="return false">增加数据</a>
						<a href="#" class="add_role" id="add_role" onclick="return false">增加角色</a>
						<a href="#" title="请先创建场景所需的数据和角色" class="add_scenario" id="add_scenario"
							onclick="return false">增加场景</a>
						</s:if>
					</div>
				</div>

				<div class="data">
					<div id="data_title">
						数据
					</div>
					<table class="dataTable">
						<tr>
							<th>名称</th>
							<th>说明</th>
							<th>创建时间</th>
							<s:if test="model.writePermission">
							<th>操作</th>
							</s:if>
						</tr>
						<s:iterator id="data" value="model.dataList">
							<tr>
								<td class="dataName">
									<s:property value="#data.dataName" />
								</td>
								<td class="dataDescription">
									<s:property value="#data.dataDes" />
								</td>
								<td>
									<s:property value="#data.buildTime"/>
									</td>
								<s:if test="model.writePermission">
								<td class="data_delete">
									<a href="#"
										onclick="deleteInProject('dataIn',<s:property value="#data.dataId"/>)">删除</a>
								</td>
								</s:if>
							</tr>
						</s:iterator>

					</table>
				</div>
				<div class="role">
					<div id="role_title">
						角色
					</div>
					<div>
						<s:if test="model.joinPermission">
							<button id="req_join_project">
								申请角色
							</button>
						</s:if>
						<s:if test="model.pickRolePermission">
							<button id="pick_role">
								选择角色
							</button>
						</s:if>
					</div>
					<table class="roleTable">
					<tr>
							<th>名称</th>
							<th>说明</th>
							<th>创建时间</th>
							<s:if test="model.writePermission">
							<th>操作</th>
							</s:if>
						</tr>
						<s:iterator id="role" value="model.roleList">
							<tr>
								<td class="roleName">

									<s:property value="#role.roleName" />
								</td>
								<td class="roleDescription">
									<s:property value="#role.roleDes" />
								</td>
								<td>
									<s:property value="#role.buildTime"/>
									</td>
								<s:if test="model.writePermission">
								<td class="role_delete">
									<a href="#"
										onclick="deleteInProject('roleIn',<s:property value="#role.roleId"/>)">删除</a>
								</td>
								</s:if>
							</tr>
						</s:iterator>

					</table>

				</div>
				<div class="scenario">
					<div id="scenarioTitle">
						场景
					</div>
					<div id="version_div">
					版本
					<table class="scenarioTable">
						<tr>
							<th>名称</th>							
							<th>创建时间</th>							
							<s:if test="model.writePermission">
							<th>操作</th>
							</s:if>
							<th>角色</th>
						</tr>
						<s:iterator id="scenario" value="model.scenarioVersionList">
							
							
								<s:url id="scenarioUrl" value="getScenarioDetail.do">
									<s:param name="scenarioId" value="#scenario.scenarioId"></s:param>
								</s:url>
								<!--<s:set name="count" value="#st.count"/>
								<s:if test="#st.count == 1">
									<tr>
										<td><s:property value="#scenario.scenarioName"/></td>
										<td><s:a href="%{scenarioUrl}">当前内容</s:a></td>							
									</tr>
								</s:if>
								<tr>
									<td></td><td><s:a href="%{scenarioUrl}">历史版本<s:property value="#st.count"/></s:a></td>
								</tr> -->
								<tr>
									<td class="scenarioName">
										<s:url id="scenarioUrl" value="getScenarioDetail.do">
											<s:param name="scenarioId" value="#scenario.scenarioId"></s:param>
											<s:param name="_st" value="#scenario.rand"></s:param>
										</s:url>
										<s:a href="%{scenarioUrl}">
											<s:property value="#scenario.scenarioName" />
										</s:a>
									</td>
									<td>
										<s:property value="#scenario.buildTime"/>
										</td>
									
									<s:if test="model.writePermission">
									<td class="scenario_delete">
										<a href="#"
											onclick="deleteInProject('scenarioIn',<s:property value="#scenario.scenarioId"/>)">删除</a>
									</td>
									</s:if>
									<td>
										<s:property value="#scenario.roleListStr"/>
									</td>
								</tr>
							
							<!-- scenarioList -->
						</s:iterator>
						<!-- scenarioVersionList -->
					</table>
					</div><!-- version_div -->
					<div id="draft_div">
						草稿
						<table class="scenarioTable">
						<tr>
							<th>名称</th>							
							<th>创建时间</th>
							<s:if test="model.writePermission">
							<th>操作</th>
							</s:if>
							<th>角色</th>
						</tr>
						<s:iterator id="scenario" value="model.scenarioDraftList">
							<!-- scenarioList是单个scenario的各版本形成的list -->
							
								<s:url id="scenarioUrl" value="getScenarioDetail.do">
									<s:param name="scenarioId" value="#scenario.scenarioId"></s:param>
								</s:url>							
								<tr>
									<td class="scenarioName">
										<s:url id="scenarioUrl" value="getScenarioDetail.do">
											<s:param name="scenarioId" value="#scenario.scenarioId"></s:param>
											<s:param name="_st" value="#scenario.rand"></s:param>
										</s:url>
										<s:a href="%{scenarioUrl}">
											<s:property value="#scenario.scenarioName" />
										</s:a>
									</td>
									<td>
										<s:property value="#scenario.buildTime"/>
										</td>
									
									<s:if test="model.writePermission">
									<td class="scenario_delete">
										<a href="#"
											onclick="deleteInProject('scenarioIn',<s:property value="#scenario.scenarioId"/>)">删除</a>
									</td>
									</s:if>
									<td>
										<s:property value="#scenario.roleListStr"/>
									</td>
								</tr>
							
							
						</s:iterator>
						<!-- scenarioDraftList -->
					</table>
					</div>
				</div>
			</div>



			<p>
				<br clear="all" />
			</p>
			<div id="footer_wrapper">
				<div id="footer">
					<div id="site_about">

						<ul>
							<li>
								关于我们
							</li>
							<li>
								常见问题
							</li>
							<li>
								帮助文档
							</li>
							<li>
								联系我们
							</li>
						</ul>
					</div>
				</div>
			</div>
	</body>
</html>