<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="easyJ.common.Const"%>
<%@ taglib uri="/WEB-INF/easyJ.tld" prefix="easyJ" %>

<%
      String className=request.getParameter("className");
      String selectMode=request.getParameter("selectMode");
      String functionUrl=request.getParameter("functionUrl");
      System.out.println("aa");
%>
<easyJ:TreeTag className="<%=className%>" selectMode="<%=selectMode%>" functionUrl="<%=functionUrl%>"/>
