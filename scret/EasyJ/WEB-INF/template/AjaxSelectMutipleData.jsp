<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="easyJ.common.*,easyJ.http.Globals,easyJ.system.data.*,easyJ.business.proxy.*"%>
<%@ page import="easyJ.common.validate.*"%>
<%@ taglib uri="/WEB-INF/easyJ.tld" prefix="easyJ" %>

<%
   String className=request.getParameter(Globals.CLASS_NAME);
   Class clazz=Class.forName(className);
   String primaryKey=(String)BeanUtil.getPubStaticFieldValue(clazz,Const.PRIMARY_KEY);
%>
<p align="right"><img align="right" onclick="PopUpWindow.hide()" src="image/arrow_right.gif" /></p>
<br>
<form name="selectDataForm" method="post" action="" id="selectDataForm" type="enter2tab">
  <table class="border"><tr><td>
  <easyJ:QueryTag columnsPerLine="2"/>
  <easyJ:FunctionTag pageName="SelectData.jsp" position="<%=Globals.FUNCTION_QUERY%>"/>
  </td></tr></table>

  <table class="border"><tr><td>
  <easyJ:DataSelectTag/>

  <easyJ:PageTag position="PopUpWindow"/>
  </td></tr></table>
  <input type="hidden" name="<%=Globals.ORDER_BY_COLUMN%>" value="<%=Format.format(request.getParameter(Globals.ORDER_BY_COLUMN))%>"/>
  <input type="hidden" name="<%=Globals.ORDER_DIRECTION%>" value="<%=Format.format(request.getParameter(Globals.ORDER_DIRECTION))%>"/>
  <input type="hidden" name="<%=Globals.CLASS_NAME%>" value="<%=request.getParameter(Globals.CLASS_NAME)%>"/>
  <input type="hidden" name="<%=Globals.PAGENO %>" value="<%if(request.getParameter(Globals.PAGENO)==null)out.print("1");else out.print(request.getParameter(Globals.PAGENO));%>"/>
  <input type="hidden" id="easyJ.http.Globals.SELECT_ACTION_NAME" value="<%=request.getAttribute(Globals.ACTION_NAME)%>"/>
  <input type="hidden" id="<%=Globals.RETURN_PATH%>" name="<%=Globals.RETURN_PATH%>" value="SelectData.jsp"/>

</form>

