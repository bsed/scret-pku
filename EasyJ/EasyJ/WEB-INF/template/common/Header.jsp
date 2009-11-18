<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="easyJ.common.*,easyJ.http.Globals,easyJ.system.data.*,easyJ.business.proxy.*"%>
<%@page import="easyJ.common.validate.GenericValidator,cn.edu.pku.dr.requirement.elicitation.system.Context"%>
<script language="javascript" src="js/Message.js"></script>
<script language="javascript" src="js/Ajax.js"></script>

<script language="javascript">
window.onload = Message.remindNewMessage;
function logout() {
    if(document.forms[0]) {
	    document.forms[0].action="cn.edu.pku.dr.requirement.elicitation.action.LoginAction.do?ACTION=logout&easyJ.http.Globals.CLASS_NAME=easyJ.system.data.SysUser";
	    document.forms[0].submit();
    }
}
function login() {
    window.location="Login.jsp?clickLogin=true";
}

function refresh() {
	alert("refresh");
	Ajax.submit("easyJ.http.servlet.SingleDataAction.do?ACTION=refresh&easyJ.http.Globals.CLASS_NAME=easyJ.system.data.SysUser",null,null,null);
}
</script>
<%
   SysUserCache userCache=(SysUserCache)request.getSession().getAttribute(Globals.SYS_USER_CACHE);
   Context context = userCache.getContext();
   SysUser user=userCache.getUser();
%>
  <div id="header">
      
      <div class="hide_todo" id="headerLinks">
        <a href="http://192.168.4.133:8081">报告BUG</a>&nbsp;|&nbsp;
        <a href="javascript:refresh()">刷新</a>&nbsp;|&nbsp;
        <a href="#">我的主页</a>&nbsp;|&nbsp;
        <a href="javascript:message();" id="mymessage" >消息</a>&nbsp;|&nbsp;
        <%System.out.println(user.getUserName());if("guest".equals(user.getUserName())) {%>
        <a href="javascript:login();">登陆</a>
        <%}else{ %>
        <a href="javascript:logout();">注销</a>
        <%} %>
      </div>
      <h1><img src="/image/seforge_logo.gif"/></h1>
      <div id="headerNavLinks">
        您所在的位置: 
        <ul>
          <li><a href="http://www.seforge.org">SE Forge</a>
            
              <%
              	if (context != null && !GenericValidator.isBlankOrNull(context.getProjectName())) {
              		out.print("<ul><li><a href = \"javascript: history.go(-1)\">需求库</a></li></ul><ul><li>"+context.getProjectName()+"</li></ul>");
              	} else {
              		out.print("<ul><li>需求库</li></ul><ul><li>");
              	}
              %>
          </li>
        </ul>
      </div>
  </div>

  <SCRIPT language="JavaScript" type="text/JavaScript">
  function message(){
   Message.getMessageTemplate(1);
   }
 </SCRIPT>
