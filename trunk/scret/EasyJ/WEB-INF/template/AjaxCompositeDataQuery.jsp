<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="easyJ.http.Globals"%>
<%@ taglib uri="/WEB-INF/easyJ.tld" prefix="easyJ" %>
<easyJ:FunctionScriptTag pageName="CompositeDataQuery.jsp"/>
<easyJ:QueryHiddenTag/>

  <table class="border"><tr><td>
    <easyJ:QueryTag/>
    <easyJ:FunctionTag pageName="CompositeDataQuery.jsp" position="<%=Globals.FUNCTION_QUERY%>"/>
  </td></tr></table>

  <table class="border"><tr><td>
    <easyJ:DisplayTag/>
    <easyJ:PageTag position="Data"/>
  </td></tr></table>

  <jsp:include flush="true" page="/WEB-INF/template/common/History.jsp">
  <jsp:param  name=""    value=""/>
  </jsp:include>
