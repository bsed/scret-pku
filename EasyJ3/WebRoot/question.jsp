<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="elicitation.model.question.Question" %>
<%@page import="elicitation.model.question.QuestionService"%>
<%@page import="elicitation.model.project.Scenario"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
int questionId = Integer.valueOf(request.getParameter("questionId"));
Question question = QuestionService.selectQuestion(questionId);
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>问题</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	</head>
  
  <body>
    <table>
    	<tr>
    		<td>标题</td>
    		<td><%=question.getTitle() %></td>
    	</tr>
    	<tr><td>类别</td><td><%=question.getQkind().getName() %></td></tr>
    	<tr><td>描述</td><td><%=question.getDescription() %></td></tr>
    	<tr><td>关联场景</td>
    		<%
    		List<Scenario> sces = question.getRelatedScenarios();
    		for(Scenario sce:sces){
    			%><td><%=sce.getScenarioName() %></td><%	
    		}%>
    		
    	</tr>
    </table>
  </body>
</html>
