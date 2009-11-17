<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="easyJ.common.*,easyJ.http.Globals"%>
<%@ taglib uri="/WEB-INF/easyJ.tld" prefix="easyJ" %>
<html>
<head>
<title>
查询数据
</title>
<link href="/css/table.css" rel="stylesheet" type="text/css">
<link href="/css/pattern.css" rel="stylesheet" type="text/css">

<script language="javascript">
function checkBrowser(){
    var cb = "Unknown";
    if(window.ActiveXObject){
        cb = "IE";
    }else if(navigator.userAgent.toLowerCase().indexOf("firefox") != -1){
        cb = "FireFox";
    }else if((typeof document.implementation != "undefined") && (typeof document.implementation.createDocument != "undefined") && (typeof HTMLDocument != "undefined")){
        cb = "Mozilla";
    }else if(navigator.userAgent.toLowerCase().indexOf("opera") != -1){
        cb = "Opera";
    }
    return cb;
}

var broswer=checkBrowser();
function registerSave(){
  action="cn.edu.pku.dr.requirement.elicitation.action.LoginAction.do?ACTION=registerSave&easyJ.http.Globals.CLASS_NAME=easyJ.system.data.SysUser";
  var message=Ajax.submit(action, "#registerForm");
  if(message){

  }
}
</script>
<script language="javascript" type="text/javascript" src="/js/Validate.js"></script>
<script language="javascript" type="text/javascript" src="/js/jquery-1.2.3.js"> </script>
<script language="javascript" type="text/javascript" src="/js/jquery.form.js"> </script>
<script language="javascript" type="text/javascript" src="/js/tree.js"></script>
<script language="javascript" type="text/javascript" src="/js/Ajax.js"></script>
<script language="javascript" type="text/javascript" src="/js/Data.js"></script>
<script language="javascript" type="text/javascript" src="/js/jquery.dimensions.js"></script>
<script language="javascript" type="text/javascript" src="/js/ui.base.js"></script>
<script language="javascript" type="text/javascript" src="/js/ui.draggable.js"></script>
<script language="javascript" type="text/javascript" src="/js/Enter2Tab.js"></script>
<script language="javascript" type="text/javascript" src="/js/hint.js"></script>
<script language="javascript" type="text/javascript" src="/js/PopUpWindow.js"></script>
<script language="javascript" type="text/javascript" src="/js/ScriptLoader.js"></script>
</head>
<div id="message"></div>
<div width="500" align="center">
<form name="registerForm" action="" method="POST" id="registerForm">
<table class="border"><tr><td>
  <easyJ:EditTag columnsPerLine="1" hint="Y" hintType="cursor"/>
  <input type="hidden" name="<%=Globals.CLASS_NAME%>" id="<%=Globals.CLASS_NAME%>" value="<%=request.getParameter(Globals.CLASS_NAME)%>"/>
  <input type="hidden" name="<%=Globals.ACTION_NAME%>" id="<%=Globals.ACTION_NAME%>" value="<%=request.getAttribute(Globals.ACTION_NAME)%>"/>
  <easyJ:FunctionTag pageName="AjaxRegister.jsp" position="<%=Globals.FUNCTION_EDIT%>"/>
</td></tr></table>
</form>
</div>
<jsp:include flush="true" page="/WEB-INF/template/common/History.jsp"/>
</html>
