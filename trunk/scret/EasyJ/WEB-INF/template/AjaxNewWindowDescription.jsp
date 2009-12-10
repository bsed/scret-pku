<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="easyJ.http.Globals,easyJ.business.proxy.SingleDataProxy,cn.edu.pku.dr.requirement.elicitation.action.ScenarioVersionAction"%>
<%@ taglib uri="/WEB-INF/easyJ.tld" prefix="easyJ" %>
<link href="css/table.css" rel="stylesheet" type="text/css">
<link href="css/pattern.css" rel="stylesheet" type="text/css">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="Expires" content="0">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">

<script language="javascript" type="text/javascript" src="js/Validate.js"></script>
<script language="javascript" src="js/jquery-1.2.3.js"> </script>
<script language="javascript" src="js/jquery.form.js"> </script>
<script language="javascript" src="js/tree.js"></script>
<script language="javascript" src="js/Ajax.js"></script>
<script language="javascript" src="js/Data.js"></script>
<script language="javascript" src="js/jquery.dimensions.js"></script>
<script language="javascript" src="js/ui.base.js"></script>
<script language="javascript" src="js/ui.draggable.js"></script>
<script language="javascript" src="js/Enter2Tab.js"></script>
<script language="javascript" src="js/hint.js"></script>
<script language="javascript" src="js/PopUpWindow.js"></script>
<script language="javascript" src="js/ScriptLoader.js"></script>
<link rel="stylesheet" href="css/scenario.css"/>

<script type="text/javascript">
$(document).ready(function(){

        $("#1").bind("click",function(){


        if($(this).nextAll(".first").next().hasClass("target-closed")){

                $(this).nextAll(".first").next().removeClass("target-closed").removeClass("down").addClass("up");
                $(this).removeClass("panel-up").addClass("panel-down");

        }
        else{
                var temp =$(this).nextAll(".first").next();
                if(temp.next().hasClass("target-closed")){
                temp.next().addClass("target-closed");
                temp.next().removeClass("up");
                }

                $(this).nextAll(".first").next().removeClass("up").addClass("target-closed");


                $(this).removeClass("panel-down").addClass("panel-up");
        }
        });
        //$('.display').unbind("click");
        $("#1").nextAll(".first").next().bind("click",function(){
				var table = $("#1").nextAll(".first").next().next();
		var length = table.length;
		var content = new Array(length);
		for(var i =0;i<length;i++)
			content[i] = new Array(2);
		for(var row = 0; row<content.length;row++){
					content[row][0]= table[row].getAttribute("id");
					content[row][1]= table[row].innerHTML;
		}
		var scenarioVersionId = $(this).next().attr("id");
		var flag = false;
		for(var j = 0; j<content.length;j++){
		if ((content[j][0] ==scenarioVersionId)&&$(this).next().hasClass("added") ){
			flag = true;
			$(this).next().replaceWith(content[j][1]);
		}
		}
		if(flag == false){
				Ajax.submitLoad(			'cn.edu.pku.dr.requirement.elicitation.action.ScenarioVersionAction.do?ACTION=getContent&easyJ.http.Globals.CLASS_NAME=cn.edu.pku.dr.requirement.elicitation.data.ScenarioVersion&ScenarioVersionId='+scenarioVersionId+'&ajax=true','#'+scenarioVersionId,'','');
				$(this).next().addClass("added");
				}
        if($(this).next().hasClass("target-closed")){
						$(this).addClass("down");
				$("#1").nextAll(".first").next().next().removeClass("down").addClass("target-closed");
				$(this).removeClass("up");
				$("#1").nextAll(".first").next().next().addClass("up");

                $(this).removeClass("up").addClass("down");
                $(this).next().removeClass("target-closed");
        }
        else{
                $(this).next().addClass("target-closed");
                $(this).removeClass("down").addClass("up");
        }
        });

});
</script>
<input type="hidden" id="CreatorId" name="CreatorId" value="<%=request.getParameter("CreatorId")%>"/>
<easyJ:QueryHiddenTag/>
  <div id="message" align="center">
  </div>
  	<div align="center">场景历史记录</div>
  <table class="border"><tr><td>
    <easyJ:QueryTag columnsPerLine="3"/>
	<easyJ:FunctionTag pageName="SingleDataQuery.jsp" position="<%=Globals.FUNCTION_QUERY%>"/>
	</td></tr></table>
	<table class="border"><tr><td>
	<div id="1" class="panel-up name" >&nbsp;&nbsp;&nbsp;场景历史记录：</div>
    <%=ScenarioVersionAction.showDescriptionHistoryNewWindow(request)%>
<easyJ:PageTag position="Data"/>
  </td></tr></table>
