<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="easyJ.common.*,easyJ.http.Globals,easyJ.system.data.*,easyJ.business.proxy.*"%>
<%@ page import="easyJ.common.validate.*"%>
<%@ taglib uri="/WEB-INF/easyJ.tld" prefix="easyJ" %>
<html>
<head>
<title>
协同需求获取工具
</title>
<link href="/css/table.css" rel="stylesheet" type="text/css">
<link href="/css/pattern.css" rel="stylesheet" type="text/css">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="Expires" content="0">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">

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

</script>
<script language="javascript" type="text/javascript" src="/js/Validate.js"></script>
<script language="javascript" src="/js/jquery-1.2.3.js"> </script>
<script language="javascript" src="/js/jquery.form.js"> </script>
<script language="javascript" src="/js/tree.js"></script>
<script language="javascript" src="/js/Ajax.js"></script>
<script language="javascript" src="/js/Data.js"></script>
<script language="javascript" src="/js/jquery.dimensions.js"></script>
<script language="javascript" src="/js/ui.base.js"></script>
<script language="javascript" src="/js/ui.draggable.js"></script>
<script language="javascript" src="/js/Enter2Tab.js"></script>
<script language="javascript" src="/js/hint.js"></script>
<script language="javascript" src="/js/PopUpWindow.js"></script>
<script language="javascript" src="/js/ScriptLoader.js"></script>

</head>
<body>
<%
	String url=request.getParameter("url");
        url=java.net.URLDecoder.decode(url,"utf-8");
%>
<script language="javascript">
Ajax.submitLoad(<%="\""+url+"\""%>,'#columnMain','');
</script>
<form name="form1" id="form1" method="post" action="" type="enter2tab">
  <div id="message" align="center">
  </div>
  <div id="columnLeft" >
  </div>
  <div id="columnMain">
  </div>
  <div id="columnRight" >
  </div>

</form>
</body>
</html>
