<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="easyJ.http.Globals"%>
<%@ taglib uri="/WEB-INF/easyJ.tld" prefix="easyJ" %>

<easyJ:QueryHiddenTag/>
<table class="border"><tr><td>
<easyJ:FunctionTag pageName="SingleDataQuery.jsp" position="<%=Globals.FUNCTION_QUERY%>"/>

<easyJ:QueryTag columnsPerLine="2"/>

</td></tr></table>

<table class="border"><tr><td>
<easyJ:FunctionTag pageName="SingleDataQuery.jsp" position="<%=Globals.FUNCTION_DISPLAY%>"/>

<easyJ:DisplayTag/>
<easyJ:PageTag position="Data"/>
</td></tr></table>


  <jsp:include flush="true" page="/WEB-INF/template/common/History.jsp">
  	<jsp:param  name=""    value=""/>
  </jsp:include>

