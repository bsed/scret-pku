<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="easyJ.http.Globals,easyJ.business.proxy.SingleDataProxy,cn.edu.pku.dr.requirement.elicitation.data.Problem"%>
<%@ taglib uri="/WEB-INF/easyJ.tld" prefix="easyJ" %>
<script language="javascript" src="js/Discuss.js"></script>
<easyJ:QueryHiddenTag/>
<SPAN class="NewsTitleRight" style="width=80px" ><SPAN class=NewsTitleLeft>问题描述：</span></span>
<table class="borderNoSpace">
  <tr><td></td></tr>
  <tr><td>
    <%
  	SingleDataProxy dp=SingleDataProxy.getInstance();
  	Problem problem=new Problem();
        problem.setProblemId(new Long(request.getParameter("discussSourceId")));
        problem=(Problem)dp.get(problem);
//        out.print(problem.getProblemContent());
    %>
  </td></tr>
</table>
<br>

<SPAN class="NewsTitleRight" style="width=60px" onclick="Discuss.queryProblemDiscuss()"><SPAN class=NewsTitleLeft>问题讨论</SPAN></SPAN><SPAN class="NewsTitleRight" style="width=60px" onclick="Discuss.querySolutionDiscuss()"><SPAN class=NewsTitleLeft>方案讨论</SPAN></span>
<div id="problemDiscuss" style="display:block">
<table class="borderNoSpace"><tr><td>
<easyJ:FunctionTag pageName="AjaxForumQuery.jsp" position="<%=Globals.FUNCTION_DISPLAY%>"/>
<easyJ:DisplayForumTag/>
<easyJ:PageTag position="Data"/>
</td></tr></table>
</div>

<table class="border"><tr><td>
<easyJ:FunctionTag pageName="AjaxForumQuery.jsp" position="<%=Globals.FUNCTION_QUERY%>"/>
<easyJ:QueryTag columnsPerLine="3"/>
</td></tr></table>
<div id="solutionDiscuss" style="display:none">
</div>

<jsp:include flush="true" page="/WEB-INF/template/common/History.jsp">
</jsp:include>
