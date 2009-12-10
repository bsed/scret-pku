<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="easyJ.database.dao.Page,easyJ.http.Globals"%>
<%@ page import="java.util.*" %>
<table>
  <tr>
    <td>
<%
  Page aPage=(Page)request.getAttribute(Globals.PAGE);
  Long totalPage=null;
  Long totalNum=null;
  if(aPage!=null)
  {
    totalPage=aPage.getTotalPage();
    totalNum=aPage.getTotalNum();
  }
%>
 总条数：<%=totalNum%> 共<%=totalPage%>页 </td>
 <td align="right">
<%
  String actionName=request.getParameter("actionName");
  String pageNo=request.getParameter(Globals.PAGENO);
  int currentPage=0;
  if(pageNo!=null)
    currentPage=Integer.parseInt(pageNo);
  int pageNums=(currentPage-1)/10;
  if(currentPage<=10)
  {
	out.print(" << ");
  }else
  {
	out.print("<a href='javascript:submitThisPage("+(pageNums*10)+")'> << </a>");
  }
  int pages=10;
  if((pageNums*10+10)>totalPage.intValue())
  pages=totalPage.intValue()-pageNums*10;
  for(int i=1;i<=pages;i++)
  {
    if(pageNums*10+i==currentPage)
    {
      out.print((pageNums*10+i));
      out.print("  ");
    }
    else
    {
      out.print("<a href='javascript:submitThisPage("+(pageNums*10+i)+")'>"+(pageNums*10+i)+"</a>");
      out.print("  ");
    }
  }
  if((pageNums*10+10)<totalPage.intValue())
  {
	out.print("<a href='javascript:submitThisPage("+(pageNums*10+11)+")'> >> </a>");
  }else
  {
	out.print(" >> ");
  }
%>

  跳转到：<input type="text" size="4" class="inputButton" value="" onkeydown="javascript:if(window.event.keyCode==13) submitThisPage(this.value)">
 </td>
 </tr>
 </table>
 <script language="javascript">
  function submitThisPage()
  {
   var pageNo = submitThisPage.arguments[0];
   var errStr="";
   if(pageNo==''){
     errStr="请输入查询的页数！！";
   }
   if(pageNo><%=totalPage%>)
     errStr="超过总页数！！";
  if(errStr!=""){
     alert(errStr);
     return;
  }
  document.forms[0].<%=Globals.PAGENO%>.value=pageNo;
  document.forms[0].<%=Globals.ACTION%> = "<%=actionName%>" +"&<%=Globals.PAGENO%>="+pageNo;
  document.forms[0].submit();
  }
 </script>

