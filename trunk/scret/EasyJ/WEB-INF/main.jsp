<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib uri="/WEB-INF/easyJ.tld" prefix="easyJ" %>
<html>
<head>
<title>
查询数据
</title>
<link href="css/table.css" rel="stylesheet" type="text/css">
<link href="css/pattern.css" rel="stylesheet" type="text/css">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="Expires" content="0">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<script language="javascript" src="js/tree.js"></script>
<script language="javascript" src="js/Ajax.js"></script>
<script language="javascript" src="js/Data.js"></script>
<script language="javascript" src="js/Enter2Tab.js"></script>
<script language="javascript" src="js/hint.js"></script>
<script language="javascript" src="js/PopUpWindow.js"></script>
<script language="javascript" src="js/jquery-1.2.3.js"> </script>
<script language="javascript">
function moduleQuery(actionName){
  var data={};
  $("input").each(function(){
    data[this.name]=this.value;
   });
  $("select").each(function(){
    data[this.name]=this.value;
   });
  $("#columnMain").load(actionName,data);
}
</script>

</head>
<body>

<form name="form1" method="post" action="" id="form1">
  <br>
  <jsp:include flush="true" page="/WEB-INF/template/common/Header.jsp">
  </jsp:include>
  <jsp:include flush="true" page="/WEB-INF/template/common/Left.jsp">
  </jsp:include>
  <div id="columnMain">
  </div>
  <jsp:include flush="true" page="/WEB-INF/template/common/Right.jsp">
  </jsp:include>
  <div id="footer">
    @Copyright: Institute of Software School of Electronics Engineering and Computer Science,Peking University, Beijing, China
   </div>
</form>
</body>
</html>
