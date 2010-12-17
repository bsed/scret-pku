<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="elicitation.model.solution.Solution" %>
<%@page import="elicitation.model.solution.SolutionService"%>
<%@page import="elicitation.model.project.Scenario"%>

<%@page import="elicitation.model.question.Question"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
int solutionId = Integer.valueOf(request.getParameter("solutionId"));
Solution solution = SolutionService.selectSolution(solutionId);
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
    		<td><%=solution.getName()%></td>
    	</tr>    	
    	<tr><td>关联场景</td>
    		<%
    		List<Scenario> sces = solution.getRelatedScenarios();
    		for(Scenario sce:sces){
    			%><td><%=sce.getScenarioName() %></td><%	
    		}%>
    	</tr>
    	<tr><td>关联问题</td>
    	<%	
    		List<Question> questions = solution.getRelatedQuestions();
    		for(Question question:questions){
    			%><td><%=question.getTitle()%></td><%
    	}%>
    	</tr>
    	<tr><td>描述</td><td><%=solution.getDescription() %></td></tr>
    </table>
  </body>
</html>
