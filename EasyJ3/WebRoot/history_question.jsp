<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>历史问题页面</title>
</head>
<body>
	当前版本的历史问题： (绿色表示该问题等待修复)
	<table class="history_question_table">
		<tr>
			<th>ID</th>
			<th>标题</th>
			<th>时间</th>			
			<th>提问人</th>
			<th>操作</th>
			<th>状态</th>			
		</tr>
		<s:iterator id ="question" value="historyQuestion">
		<s:if test="%{#question.status=='wait'}">
			<tr class="history_question_tr" style="background: #9CFC7C">
		</s:if>		
			<s:else>
			<tr class="history_question_tr">
			</s:else>		
		
			<td><s:property value="#question.questionId"/></td>
			<td><s:property value="#question.title"/></td>
			<td><s:property value="#question.buildTime"/></td>
			<s:url id="questionUrl" value="question.jsp">
							<s:param name="questionId">
								<s:property value="#question.questionId" />
							</s:param>
			</s:url>
			<td><s:property value="#question.user.userName"/></td>
			<td><s:a href="%{questionUrl}">查看</s:a></td>
			<td><s:property value="#question.status"/></td>			
		</tr>			
		</s:iterator>
	</table>

</body>
</html>