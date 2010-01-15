<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="easyJ.common.*,easyJ.http.Globals,java.util.*,easyJ.system.service.*,easyJ.system.data.*"%>
<%@ page import="easyJ.common.validate.*"%>
<%@ taglib uri="/WEB-INF/easyJ.tld" prefix="easyJ" %>

<table class="border" width="100%"><tr><td>
  <easyJ:EditTag columnsPerLine="1" start="0" size="6"/>

  <easyJ:CompositeDetailTag/>

  <easyJ:EditTag columnsPerLine="1" start="6" size="-1"/>
  <input type="hidden" name="<%=Globals.CLASS_NAME%>" id="<%=Globals.CLASS_NAME%>" value="<%=request.getParameter(Globals.CLASS_NAME)%>"/>
  <input type="hidden" name="<%=Globals.ACTION_NAME%>" id="<%=Globals.ACTION_NAME%>" value="<%=request.getAttribute(Globals.ACTION_NAME)%>"/>
  <input type="hidden" name="detailSize" value="" />
  <easyJ:FunctionTag pageName="CompositeDataEdit.jsp" position="<%=Globals.FUNCTION_EDIT%>"/>
</td></tr></table>

  <jsp:include flush="true" page="/WEB-INF/template/common/History.jsp">
  </jsp:include>

<script language="javascript">
var detailSize=details.rows.length;
form1.detailSize.value=details.rows.length;
</script>

