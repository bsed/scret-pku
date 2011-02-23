<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@ page import="elicitation.model.user.SysUser" %>
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

		<title><s:property value="model.scenarioName" />
		</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">

		<script type="text/javascript" src="ext-2.3.0/adapter/ext/ext-base.js"></script>
		<link rel="stylesheet" href="ext-2.3.0/resources/css/ext-all.css"
			type="text/css"></link>
		<link rel="stylesheet" type="text/css" href="css/layout.css">
		<script type="text/javascript" src="ext-2.3.0/ext-all.js"></script>
		<script type="text/javascript" src="review.js"></script>
		<script type="text/javascript" src="edit_scenario.js"></script>
		<!--<script type="text/javascript" src="join_scenario.js"></script>-->
		<script type="text/javascript" src="question_scenario.js"></script>
		<script type="text/javascript" src="question_kind_custom.js"></script>
		<script type="text/javascript" src="solution.js"></script>
		<script type="text/javascript" src="vote.js"></script>
		<script type="text/javascript" src="pick_solution.js"></script>
		<script type="text/javascript" src="contribute.js"></script>
		<script type="text/javascript" src="make_version.js"></script>
		
		<script type="text/javascript" src="scripts/log.js"></script>
		<script type="text/javascript" src="question_vote.js"></script>
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
								<a href="/EasyJ3">首页</a>
							</li>
							<li>
								<a href="">关于系统</a>
							</li>
		<%	
		if(user == null){	
		%>
							<li>
								<a  href = "javascript:void(0)" id='register' >注册</a>
							</li>
							<li>
								<a  href = "javascript:void(0)" id='login'>登录</a>
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

			<input type="hidden" id="scenario_id"
				value="<s:property value="model.scenarioId" />" />
			<input type="hidden" id="project_id"
				value="<s:property value="model.projectId" />" />
			<div id="main_scenario">
				<div class="scenario_function">
					<!-- <a href="#" id="req_join_scenario" onclick="return false">请求加入场景</a> -->
				<!-- 	<s:if test="model.joinPermission">
					<button id="req_join_scenario">
						请求加入场景
					</button>
					</s:if>  -- >
					<!--<a href="#" id="question_scenario" onclick="return false">提问</a> 
				<a href="#" id="question_kind_custom" onclick="return false">自定义问题类别</a> -->
					<!-- <a href="#" id="make_solution" onclick="return false">提出解决方案</a>
				<a href="javascript:void(0)" id="pick_solution" onclick="return false">确定解决方案</a> -->
					<!-- <a href="javascript:void(0)" id="make_version" onclick="makeVersion(<s:property value="model.scenarioId" />)">确定为版本</a> -->
					<s:if test="model.draft">
						<s:if test="model.makeVersionPermission">
							<button id="make_version"
								onclick="makeVersion(<s:property value="model.scenarioId" />)">
								确定为版本
							</button>
						</s:if>
					</s:if>
					
					
				</div>
				<div class="des_unit">
					<div class="scenarioName">
						<s:property value="model.scenarioName" />(<s:property value="model.viewUseState"/>)					
					</div>					
					<div id="main_scenario_description">
						<s:property value="model.scenarioDescription" />
					</div>
					<div class="scenario_general_info">
					<s:url id="prevUrl" value="getScenarioDetail.do">
						<s:param name="scenarioId">
							<s:property value="model.prevId"/>
						</s:param>
					</s:url>
					<s:url id="hisUrl" value="history_version.do">
							<s:param name="scenarioName">
								<s:property value="model.scenarioName" escape="false"/>
							</s:param>
							<s:param name="scenarioId">
								<s:property value="model.scenarioId" />
							</s:param> 
					</s:url>
					
					<s:url id="projectUrl" value="getProjectDetail.do">
						<s:param name="projectId">
							<s:property value="model.project.projectId"/>
						</s:param>
						<s:param name="_st">
							<s:property value="model.project.rand"/>
						</s:param>
					</s:url>
						<ul >				
						<li>所属项目 : <s:a href="%{projectUrl}"><s:property value="model.project.projectName"/></s:a></li>
						<li>管理员   : <s:property value="model.sysUser.userName"/></li>
						<li>创建时间 : <s:property value="model.buildTime"/></li>
						<li>类型     : <s:property value="model.viewUseState"/></li>
						<s:if test="model.prevId != -1">
							<li><s:a href="%{prevUrl}"><< 上一版本</s:a></li>
						</s:if>
						<s:else>
							<li>根版本</li>
						</s:else>
						<li><s:a href="%{hisUrl}">历史版本>></s:a></li>						
						</ul>
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
									
							</tr>
						</s:iterator>

					</table>
				</div>
				<div class="role">
					<div id="role_title">
						角色
					</div>
					<table class="roleTable">
					 <tr>
							<th>名称</th>
							<th>说明</th>
							<th>创建时间</th>
							
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
							</tr>
						</s:iterator>
					</table>
				</div>

				<div class="div_roleDes">
					<div id="role_des_title">
						场景描述
					</div>
					<table>
						<s:iterator id="roleMap" value="model.roleMap">
							<tr class="tr_role_des">
								<td class="roleName">
									<s:property value="#roleMap.role.roleName" />
								</td>
									
								<td class="role_des_edit">
									<s:if test="model.draft">
										<s:if test="#roleMap.role.editPermission">
											<a href="#" class="edit_scenario"
												name='<s:property value="#roleMap.role.roleId" />'
												onclick="return false">编辑场景</a>
										</s:if>
									</s:if>
								</td>
								<td>
									<s:if test="model.draft">
										<s:if test="#roleMap.role.editPermission">
										<a href="javascript:void(0)"
											onclick="contributeOk(<s:property value="#roleMap.role.roleId"/>,<s:property value="model.scenarioId" />)">
											贡献完毕</a>
										</s:if>
									</s:if>
								</td>
								<td>
									贡献率(
									<s:property value="#roleMap.role.voteRate" />
									)
								</td>
								
							</tr>
							<tr class="roleDes">
								<td colspan="4"><s:property value="#roleMap.description" escape="false" /></td>
							</tr>
						</s:iterator>
					</table>
				</div>

			</div>
			<!-- main_scenario -->
			<div class="review_scenario">
			<s:if test="model.draft">
				<a href="#" class="review_scenario" id="review" onclick="return false">
					评论
				</a>
			</s:if>
				<s:iterator id="review" value="model.reviews">
				<div class="review">	
					<ul>
						<li>评论人:  <s:property value="#review.user.name"/></li>
						<li>评论时间:<s:property value="#review.time"/></li>
						<span>关联角色</span>
						<s:iterator id="role" value="#review.roles">
						<li><s:property value="#role.roleName"/></li>
						</s:iterator>
						<li><s:property value="#review.content" escape="false"/></li>
					</ul>
				</div>
				</s:iterator>
				
			</div>
		
			<div class="question_scenario">
				<div class="question_function">
					<!--<span><a href="#" id="question_scenario" onclick="return false">提问</a></span>-->
					<!-- <a href="#" id="question_kind_custom" onclick="return false">自定义问题类别</a>-->
					<s:if test="model.version">
						<s:if test="model.questionPermission">
							<button id="question_scenario">
								提问
							</button>
							</s:if>
						<s:if test="model.defineQTypePermission">
							<button id="question_kind_custom">
								自定义问题类别
							</button>
						</s:if>
					</s:if>
					<s:url id="history_question" value="history_question.do">
						<s:param name="scenarioId"><s:property value="model.scenarioId"/></s:param>
					</s:url>
					<s:a target="_blank" href="%{history_question}">查看历史问题 >></s:a>
				</div>
				<br />
				<table class="questionTable">
					<tr>
						<th>
							标题
						</th>
						<th>
							类别
						</th>
						<th>
							提问人
						</th>
						
						<s:if test="model.draft">
							<s:if test="model.voteQuestionPermission">
								<th>
									操作
								</th>
								<th>
									综合票数
								</th>
							</s:if>
						</s:if>
					</tr>
					<s:iterator id="question" value="model.question">
						<s:url id="qUrl" value="question.jsp">
							<s:param name="questionId">
								<s:property value="#question.id" />
							</s:param>
						</s:url>
						<tr
							title='<s:property value="#question.description" escape='true'/>'>
							<td>
								<s:a href="%{qUrl}" target="_blank">
									<s:property value="#question.title" />
								</s:a>
							</td>
							<td>
								<s:property value="#question.qkind.name" />
							</td>
							<td>
								<s:property value="#question.user.userName"/>
							</td>
							<s:if test="model.draft">
								<s:if test="model.voteQuestionPermission">
									<td>
										<a href="javascript:void(0)" onclick="
										                   voteQuestion( <s:property value='#question.questionId'/> , 1 )
										                ">
										 修复</a>
										<a href="javascript:void(0)"
										 onclick=" voteQuestion( <s:property value='#question.questionId'/> , -1 )
										                "> 
										未修复</a>
									</td>
									<td>
										<s:property value="#question.voteNum"/>
									</td>
								</s:if>
							</s:if>
						</tr>

					</s:iterator>
				</table>
			</div>
			<div class="solution">
				<div class="solution_function">
					<!-- <a href="#" id="make_solution" onclick="return false">提出解决方案</a>
				<a href="javascript:void(0)" id="pick_solution" onclick="return false">确定解决方案</a>-->
					<s:if test="model.version || model.freeze">
						<s:if test="model.solutionPermission">
						<button id="make_solution">
							提出解决方案
						</button>
						</s:if>
						<s:if test="model.chooseSolutionPermission">
						<button id="pick_solution">
							确定解决方案
						</button>
						</s:if>
					</s:if>
					<s:if test="model.draft">
					<span id="pick_solution_title">
						采取的解决方案
						</span>
					</s:if>
				</div>
				<br />
				<table class="solutionTable">
					<tr>
						<th>
							方案名称
						</th>
						<th>
							方案描述
						</th>
						<th>
							方案作者
						</th>
					</tr>
					<s:iterator id="solution" value="solutions">
						<s:url id="sUrl" value="solution.jsp">
							<s:param name="solutionId" value="#solution.id"></s:param>
						</s:url>
						<s:url id="vUrl" value="vote.so">
							<s:param name="solutionId" value="#solution.id"></s:param>
						</s:url>
						<tr>
							<td>
								<s:property value="#solution.name" />
							</td>
							<td>
								<s:a target="_blank" href="%{sUrl}">
									<s:property value="#solution.displayDes" />
								</s:a>
							</td>
							<td>
								<s:property value="#solution.creator.userName"/>
							</td>
							<s:if test="model.version">
								<td>
									<a href="javascript:void(0)"
										onclick="voteSolution(<s:property value="#solution.id"/>)">赞成</a>
								</td>
							</s:if>
							<td>
								已得
								<s:property value="#solution.voteNum" />
								票
							</td>
							<!-- <td><a href="javascript:void(0)" onclick="use_solution(<s:property value="#solution.id"/>)">使用该解决方案</a></td> -->
						</tr>
					</s:iterator>
				</table>
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
		</div><!-- wrapper -->
	</body>
</html>
