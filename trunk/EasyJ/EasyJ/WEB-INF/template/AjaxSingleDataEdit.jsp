<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="easyJ.common.*,easyJ.http.Globals"%>
<%@ taglib uri="/WEB-INF/easyJ.tld" prefix="easyJ" %>

<table class="border"><tr><td>
  <easyJ:EditTag columnsPerLine="2"/>
  <input type="hidden" name="<%=Globals.CLASS_NAME%>" id="<%=Globals.CLASS_NAME%>" value="<%=request.getParameter(Globals.CLASS_NAME)%>"/>
  <input type="hidden" name="<%=Globals.ACTION_NAME%>" id="<%=Globals.ACTION_NAME%>" value="<%=request.getAttribute(Globals.ACTION_NAME)%>"/>
  <easyJ:FunctionTag pageName="SingleDataEdit.jsp" position="<%=Globals.FUNCTION_EDIT%>"/>
</td></tr></table>

  <jsp:include flush="true" page="/WEB-INF/template/common/History.jsp">
  	<jsp:param  name=""    value=""/>
  </jsp:include>
