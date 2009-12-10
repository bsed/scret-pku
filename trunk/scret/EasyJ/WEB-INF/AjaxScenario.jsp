<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page
	import="easyJ.system.service.*,easyJ.business.proxy.*,java.util.*,easyJ.common.*,easyJ.common.validate.*"%>
<%@ page
	import="cn.edu.pku.dr.requirement.elicitation.data.*,easyJ.http.*,easyJ.system.data.SysUserCache"%>
<%@ page import="cn.edu.pku.dr.requirement.elicitation.tools.*"%>

<link rel="stylesheet" href="css/AjaxScenario.css" />

<script language="javascript" src="js/jqDnR.js"> </script>
<script language="javascript" src="js/jquery-1.2.3.js"> </script>
<script language="javascript" src="js/jquery.form.js"> </script>
<script language="javascript" src="js/Data.js"></script>
<script language="javascript" src="js/Enter2Tab.js"></script>
<script language="javascript" src="js/Problem.js"></script>
<script language="javascript" src="js/Problemsolution.js"></script>
<script>
function getAbsolutePosition(obj)
{
position = new Object();
position.x = 0;
position.y = 0;
var tempobj = obj;
    while(tempobj!=null && tempobj!=document.body)
    {
        if(window.navigator.userAgent.indexOf("MSIE")!=-1)
        {
            position.x += tempobj.offsetLeft;
            position.y += tempobj.offsetTop;
        }
        else if(window.navigator.userAgent.indexOf("Firefox")!=-1)
        {
            position.x += tempobj.offsetLeft;
            position.y += tempobj.offsetTop;
        }
        tempobj = tempobj.offsetParent
    }
return position;
}
$().ready(function(){
		$('#close').bind("click",function(){
		$('#showDescriptionHistory').addClass("target-closed");
		});
});

</script>

<%
    Scenario scenario = (Scenario) request.getAttribute(Globals.OBJECT);
    ArrayList roles = scenario.getRoles();
    int roleSize = 0;
    if (roles != null)
        roleSize = roles.size();
    ArrayList datas = scenario.getDatas();
    int dataSize = 0;
    if (datas != null)
        dataSize = datas.size();
    ArrayList participants = new ArrayList();
    ArrayList descriptions = scenario.getDescriptions();
    String strParticipants = "", strCommunicators = "", strObservers = "", strDatas = "";
    SysUserCache userCache = (SysUserCache) session
            .getAttribute(Globals.SYS_USER_CACHE);
    Long userId = userCache.getUser().getUserId();
    //得到当前用户所拥有的角色
    String roleIds = userCache.getUser().getRoleIds();
%>

<input type="hidden" id="scenarioId" name="scenarioId"
	value="<%=scenario.getScenarioId()%>" />
<input type="hidden" name="<%=Globals.CLASS_NAME%>"
	value="<%=request.getParameter(Globals.CLASS_NAME)%>" />
<input type="hidden" name="<%=Globals.ACTION_NAME%>"
	id=<%=Globals.ACTION_NAME%>
	value="<%=request.getAttribute(Globals.ACTION_NAME)%>" />
<!-- 
<input value="查看描述历史记录" type="button" class="button"
	onclick="showDescriptionHistory(<%=userId%>)" />
	 -->
<input value="查看历史记录" type="button" class="button"
	onclick="showScenarioHistory(<%=scenario.getScenarioId()%>)" />
	<!-- 
<input value="查看问题" type="button" class="button"
	onclick="showProblemInside(1)" />
	-->
<input value="查看活动图" type="button" class="button"
	onclick="showDiagram(this)"/>
<input value="处理加入申请" type="button" class="button"/>
	<!-- 
<input value="加入申请处理" type="button" class="button"
	onclick="showJoinApply()"/>
	-->

<table class="scenario">
		<tr>
			<th>
				场景名称
			</th>
			<td onclick="editScenarioName()" id="scenarioNameTd">
				<%=scenario.getScenarioName()%>
			</td>
		</tr>
		<tr>
			<th>
				参与者
			</th>
			<td id="participant">
				<span id="participant_list"> <%
     boolean firstParticipant = true;
     for (int i = 0; i < roleSize; i++) {
         ScenarioRoleRelation srr = (ScenarioRoleRelation) roles.get(i);
         if ("participant".equals(srr.getRoleType())) {

             participants.add(srr);
             out.print((firstParticipant ? "" : ", ")
                     + srr.getRoleName());
             strParticipants += (firstParticipant ? "" : ", ")
                     + srr.getRoleName();
             firstParticipant = false;
         }
     }
 %> </span>
				<input type="button" id="part_add" name="part_add" class="button"
					value="编辑" onclick="Scenario.add('participant')" />

			</td>
		</tr>
		<tr>
			<th width="80px">
				外部交互者
			</th>
			<td id="communicator">
				<span id="communicator_list"> <%
     boolean firstCommunicator = true;
     for (int i = 0; i < roleSize; i++) {
         ScenarioRoleRelation srr = (ScenarioRoleRelation) roles.get(i);
         if ("communicator".equals(srr.getRoleType())) {
             out.print((firstCommunicator ? "" : ", ")
                     + srr.getRoleName());
             strCommunicators += (firstCommunicator ? "" : ", ")
                     + srr.getRoleName();
             firstCommunicator = false;
         }
     }
 %> </span>
				<input type="button" id="com_add" name="com_add" class="button"
					value="编辑" onclick="Scenario.add('communicator')" />
			</td>
		</tr>
		<tr>
			<th>
				观察者
			</th>
			<td id="observer">
				<span id="observer_list"> <%
     boolean firstObserver = true;
     for (int i = 0; i < roleSize; i++) {
         ScenarioRoleRelation srr = (ScenarioRoleRelation) roles.get(i);
         if ("observer".equals(srr.getRoleType())) {
             out.print((firstObserver ? "" : ", ") + srr.getRoleName());
             strObservers += (firstObserver ? "" : ", ")
                     + srr.getRoleName();
             firstObserver = false;
         }
     }
 %> </span>
				<input type="button" id="ob_add" name="ob_add" class="button"
					value="编辑" onclick="Scenario.add('observer')" />

			</td>
		</tr>
		<tr>
			<th width="80px">
				数据
			</th>
			<td id="data" colspan="3">
				<span id="data_list"> <%
     for (int i = 0; i < dataSize; i++) {
         ScenarioDataRelation sdr = (ScenarioDataRelation) datas.get(i);
         out.print((i == 0 ? "" : ", ") + sdr.getDataName());
         strDatas += (i == 0 ? "" : ", ") + sdr.getDataName();
     }
 %> </span>
				<input type="button" id="data_add" name="data_add" class="button"
					value="编辑" onclick="Scenario.add('data')" />
			</td>
		</tr>
		<!--下面是内容，将所有的内容用div包起来。然后就可以显示图形。-->
		<%
		    String content = "";
		    Long descriptionId = null;
		    for (int i = 0; i < participants.size(); i++) {
		        ScenarioRoleRelation srr = (ScenarioRoleRelation) participants
		                .get(i);
		%>
		<tr>
			<th rowspan="2"><%=srr.getRoleName()%></th>
			<%
			    boolean found = false;
			        Description description = null;
			        /*看是否有对应的description*/
			        for (int j = 0; j < descriptions.size(); j++) {
			            description = (Description) descriptions.get(j);
			            if (srr.getRoleId().equals(description.getRoleId())) {
			                found = true;
			                break;
			            }
			        }
			        //如果没有找到，则需要吧description 设为空
			        if (!found)
			            description = null;
			        /*如果当前登陆的用户拥有此场景的某个角色，则需要可以编辑。*/
			        if (!GenericValidator.isBlankOrNull(roleIds)
			                && roleIds.indexOf("," + srr.getRoleId().toString()
			                        + ",") >= 0) {
			%>
			<td>
				<%
				    out
				                    .println("<div class=\"editor_border\" id=\"div_edit\">");
				            out.println("<textarea id=\"edit\"></textarea></div>");
				            out.println("<input type=\"hidden\" id=\"roleId\" value=\""
				                    + srr.getRoleId() + "\"/>");
				            if (found) {
				                descriptionId = description.getDescriptionId();
				                content = description.getDescriptionContent();
				                out.print("<input type=\"hidden\" id=\"descriptionId"
				                        + userId + "\" name=\"descriptionId" + userId
				                        + "\" value=\""
				                        + StringUtil.fixEmpty(descriptionId) + "\"/>");
				            } else {
				                out.print("<input type=\"hidden\" id=\"descriptionId"
				                        + userId + "\" name=\"descriptionId" + userId
				                        + "\" value=\"\"/>");
				            }
				        } else {
				%>
			
			<td class="readonly">
				<%
				    if (found) {
				                out.print(description.getDescriptionContent());
				            } else {
				                //如果没有找到description，则输出两个空白的行。
				                System.out.println("not found");
				                out.print("<br><br>");
				            }
				        }
				%>
			</td>
		</tr>
		<tr>
		    <td class="remark">
				<%
				/*下面列出所有的评论*/
				        if (found) {
				            out.print("<div id=\"remark_header_" + i
				                    + "\" class=\"tableTopic\">评论");

				            out
				                    .print("<input align=\"right\" type=\"button\" value=\"添加\" id=\"remark\""
				                            + descriptionId
				                            + " class=\"button\" onclick=\"addRemark(this,"
				                            + description.getDescriptionId()
				                            + ", "
				                            + i
				                            + ")\"/>");
				            out.print("</div>");
				        }
				        if (description != null) {

				            ArrayList remarks = description.getRemarks();
				            int size = remarks.size();
				            if (size > 0) {
				                out
				                        .print("<table class=\"innerTable\" id=\"remark_body_"
				                                + i + "\">");
				                out
				                        .print("<tr id=\"remark_title_"
				                                + i
				                                + "\"><th>内容</th><th>评论人</th><th>评论时间</th><th>操作</th></tr>");
				            }
				            for (int j = 0; j < remarks.size(); j++) {
				                Remark remark = (Remark) remarks.get(j);
				                out.print("<tr><td width=\"60%\">"
				                        + remark.getRemarkContent() + "</td><td>"
				                        + remark.getUserName() + "</td><td>"
				                        + remark.getBuildTime() + "</td>");

				                if (remark.getCreatorId().equals(userId))
				                    out
				                            .print("<td><input align=\"right\" type=\"button\" class=\"button\" value=\"取消\" onclick=\"revokeRemark(this,"
				                                    + remark.getRemarkId()
				                                    + ")\"/></td>");
				                else {
				                    /*如果当前登陆的用户拥有此场景的某个角色，则需要可以提醒提出remark的人将remark取消。*/
				                    if (!GenericValidator.isBlankOrNull(roleIds)
				                            && roleIds.indexOf(","
				                                    + srr.getRoleId().toString() + ",") >= 0)
				                        out
				                                .print("<td><input align=\"right\" type=\"button\" class=\"button\" value=\"已解决\" onclick=\"resolve()\"/></td>");
				                    else
				                        out.print("<td></td>");
				                }
				                out.print("</tr>");
				            }
				            if (size > 0)
				                out.print("</table>");

				        }
				 %>		    
		    </td>
		</tr>

		<%
		    }
		%>
</table>
<div id="graphContent" style="display: none">

</div>
<table>
		<tr>
			<td colspan="4">
				<div class="tableTopic" id="problem">
					问题
					<input id="problem_btn" value="添加" type="button" class="button"
						onclick="addProblem()" />
				</div>
			</td>
		</tr>

		<%
		    Long scenarioId = scenario.getScenarioId();
		    CompositeDataProxy cdp = CompositeDataProxy.getInstance();
		    Problem p = new Problem();
		    p.setScenarioId(scenarioId);
		    ArrayList problems = cdp.query(p);

		    if (problems.size() > 0) {
		%>
		<tr id="problem_header">
			<th>
				标题
			</th>
			<th>
				提问人
			</th>
			<th>
				提问时间
			</th>
			<th width="10%">
				操作
			</th>
		</tr>
		<%
		    }
		    for (int j = 0; j < problems.size(); j++) {
		        Problem problem = (Problem) problems.get(j);
		        String problemTitle = problem.getProblemTitle();
		        String buildTime = problem.getBuildTime().toString();
		        String roleName = problem.getUserName();
		        out
		                .print("<tr><td>"
		                        + problemTitle
		                        + "</td><td>"
		                        + roleName
		                        + "</td><td>"
		                        + buildTime
		                        + "</td>"
		                        + "<td><input type=\"button\" class=\"button\" value=\"参与讨论\" onclick=\"showProblemInside("
		                        + problem.getProblemId() + ");\"/></td></tr>");
		    }
		%>


</table>
<div id="close" class="jqHandle close  target-closed scenariocontent">
	关闭
</div>
<div id="showDescriptionHistory" class="scenariocontent"></div>
<div id="moredescriptionhistory"
	class="moredescriptionhistory target-closed scenariocontent">
	&nbsp;&nbsp;&nbsp;more...
</div>
<div id="console" style="font-size: 1.4em; display: none;"></div>

<script>
$('#showDescriptionHistory').addClass("target-closed");
var editorBorder = document.getElementById("div_edit");
if (editorBorder) {
  var pos = getAbsolutePosition(editorBorder);
  
  var closeBtn = document.getElementById("close");
  closeBtn.style.top=pos.y+"px";
  closeBtn.style.left=pos.x+editorBorder.offsetWidth+"px";
  closeBtn.style.width="380px";
  closeBtn.style.height="15px";
  
  var showBtn = document.getElementById("showDescriptionHistory");
  showBtn.style.top=pos.y+15+"px";
  showBtn.style.left=pos.x+editorBorder.offsetWidth+"px";
  showBtn.style.width="380px";
  //showBtn.style.height=editorBorder.offsetHeight+"px";  
  //showBtn.style.height=editorBorder.offsetHeight+"px";
  showBtn.style.height="100px";
  
  var moreBtn = document.getElementById("moredescriptionhistory");
  moreBtn.style.top=pos.y+115+"px";
  moreBtn.style.left=pos.x+editorBorder.offsetWidth+"px";
  moreBtn.style.width="380px";
  moreBtn.style.height="12px";
}
</script>



<script type="text/javascript">
  var Scenario = EditorManagerFactory.createEditorManager("scenario");
  
  // 从不可编辑的description中删去多余的内容
  $("td.readonly").each(function(i) {
      // this == td
      Scenario.createReadOnlyArea(this, i);
  });
  
  Scenario.addTerms(<%="'" + strParticipants + "'"%>, "role");
  Scenario.addTerms(<%="'" + strCommunicators + "'"%>, "role");
  Scenario.addTerms(<%="'" + strObservers + "'"%>, "role");
  Scenario.addTerms(<%="'" + strDatas + "'"%>, "data");
  var idField = document.getElementById("descriptionId"+<%=userId%>);
  //当前用户拥有角色，将来如果支持当前用户可以在场景中具有多个角色的话，需要进行修改。
  var roleId= document.getElementById("roleId");
  
  var options = {
        data: {
          descriptionContent: null,
          descriptionId: (idField ? idField.value : null),
          roleId:(roleId ? roleId.value : null),
          scenarioId:<%="'" + StringUtil.fixEmpty(scenario.getScenarioId())
                    + "'"%>
        },
        height: 300,
	content: <%="'" + StringUtil.fixString(content) + "'"%>,
    	url:"cn.edu.pku.dr.requirement.elicitation.action.ScenarioAction.do?ACTION=saveDescription&easyJ.http.Globals.CLASS_NAME=cn.edu.pku.dr.requirement.elicitation.data.Description",
	commitUrl:"cn.edu.pku.dr.requirement.elicitation.action.ScenarioAction.do?ACTION=saveDescriptionVersion&easyJ.http.Globals.CLASS_NAME=cn.edu.pku.dr.requirement.elicitation.data.Description",
        form:''
  };
  Scenario.createEditor(document.getElementById("edit"), "description", options);


  function editScenarioName() {
    
  	var scenarioNameTd=document.getElementById("scenarioNameTd");
  	if(scenarioNameTd.firstChild.nodeType==3) {
  	//当节点类型是纯文本的时候才替换，否则不做处理 
  		var value=scenarioNameTd.innerHTML;
  		scenarioNameTd.innerHTML="<input type=\"text\" id=\"scenarioName\" value=\""+value+"\"/>"+
  		"<input type=\"button\" value=\"保存\" onclick=\"saveScenarioName(event)\"/>";
  	}
  }
  
  function revokeRemark(button,remarkId) {
  	var actionPath="cn.edu.pku.dr.requirement.elicitation.action.ScenarioAction.do?ACTION=revokeRemark&easyJ.http.Globals.CLASS_NAME=cn.edu.pku.dr.requirement.elicitation.data.Remark";
  	var data={};
  	data.remarkId=remarkId;
  	Ajax.submit(actionPath,null,data);
  	var rindex;
  	var remarkTable;
  	
  	if(button.parentElement) {
  		remarkTable=button.parentElement.parentElement.parentElement;
  		rindex=button.parentElement.parentElement.rowIndex;
  	}else {
  		remarkTable=button.parentNode.parentNode.parentNode;
  		rindex=button.parentNode.parentNode.rowIndex;
  	}
  	//alert(remarkTable);
  	//alert(rindex);
  	remarkTable.deleteRow(rindex);
  }
  
  function saveScenarioName(event) {
  	event = normalizeEvent(event);
  	var actionPath="cn.edu.pku.dr.requirement.elicitation.action.ScenarioAction.do?ACTION=saveScenarioName&easyJ.http.Globals.CLASS_NAME=cn.edu.pku.dr.requirement.elicitation.data.Scenario";
  	var data={};
  	var scenarioName=document.getElementById("scenarioName");
  	var scenarioNameTd=document.getElementById("scenarioNameTd");
  	var scenarioId=document.getElementById("scenarioId");
  	if(!scenarioName) return;
  	var scenarioNameValue=scenarioName.value;
  	var scenarioIdValue=scenarioId.value;  	
  	data.scenarioName=scenarioNameValue;
  	data.scenarioId=scenarioIdValue;
  	var message=Ajax.submit(actionPath,null,data);
  	if("保存成功"==message["hintMessage"].trim()) {
  		scenarioNameTd.innerHTML=scenarioNameValue;
  	}
	event.stop();
  }

  function resolve(scenarioName,remarkContent,creatorId) {
  	var data={};
  	data.messageTitle="场景："+scenarioName+"取消评论提醒";
  	data.messageContent="场景"+scenarioName+"中您的评论：\n"+remarkContent+"\n已被解决，请您尽快去确认取消评论";
  	data.messageReceiver=creatorId;
  	var actionName="cn.edu.pku.dr.requirement.elicitation.action.ScenarioAction.do?ACTION=resolve&easyJ.http.Globals.CLASS_NAME=cn.edu.pku.dr.requirement.elicitation.data.Message&ajax=true";
  	Ajax.submit(actionName,null,data);
  }



  function addRemark(button,descId, index)
  {
    //TODO: 需要submit当前角色，才能知道是谁提交了Remark
    var editBorder = button.parentNode;
    var options = {
        data: {
          remarkContent: null,
          remarkId:'',
          descriptionId:descId,
          scenarioId:<%="'" + StringUtil.fixEmpty(scenario.getScenarioId())
                    + "'"%>
        },
    	url:"cn.edu.pku.dr.requirement.elicitation.action.ScenarioAction.do?ACTION=addRemark&easyJ.http.Globals.CLASS_NAME=cn.edu.pku.dr.requirement.elicitation.data.Remark",
        form:'',
        menu: {
          save: 'y',
          anonymous: 'y'
        },
        // Remark只保存一次
        editOnce: 'y',
        createAfter: button,
        // 在点击保存之后回调这个函数
        editOnceCallback: function(remarkObject) {
            button.style.display = "inline";
            var html = remarkObject.html;
            // 如果评论内容为空则直接返回 
            if (!html || stripeAllHtmlTags(html).search(/[^\s\u00a0]/) < 0) {
                return; 
            }
            // 把最新评论添加在remark_body_"index"的第一行，如果remark_body_"index"不存在，
            // 则要先创建id=remark_body_"index"的<table>，把这个table放在remark_header_"index"之后
            var remarks = document.getElementById("remark_body_" + index);
            if (!remarks) {
                remarks = document.createElement("table");
                remarks.id = "remark_body_" + index;
                remarks.className = "innerTable";
                var header = document.getElementById("remark_header_" + index);
                insertAfter(remarks, header);
                var titleTr = document.createElement("tr");
                titleTr.id = "remark_title_" + index;
                titleTr.innerHTML = "<th>内容</th><th>评论人</th><th>评论时间</th><th>操作</th>";
                remarks.appendChild(titleTr);
            }
            // 创建新的一行，来显示刚刚保存的remark内容、评论人、时间和操作
            var remarkTr = document.createElement("tr");
            var td1 = document.createElement("td");
            var td2 = document.createElement("td");
            var td3 = document.createElement("td");
            var td4 = document.createElement("td");
            td1.innerHTML = html;
            td2.innerHTML = (remarkObject.userName && remarkObject.userName != "null" ? remarkObject.userName : " -- ");
            td3.innerHTML = (remarkObject.buildTime && remarkObject.buildTime != "null" ? remarkObject.buildTime : " -- ");
            td4.innerHTML = "<input align=\"right\" type=\"button\" class=\"button\" value=\"取消\" onclick=\"revokeRemark(this," + remarkObject.remarkId + ")\"/>";
            remarkTr.appendChild(td1);
            remarkTr.appendChild(td2);
            remarkTr.appendChild(td3);
            remarkTr.appendChild(td4);
            insertAfter(remarkTr, document.getElementById("remark_title_" + index));
        }
    };
    Scenario.createEditor(editBorder, "remark", options);
    button.style.display = "none";
  }

  function addProblem()
  {
    document.getElementById("problem_btn").style.display = "none";
    var editBorder = document.getElementById("problem");
    
    var titleLine = document.createElement("div");
    titleLine.id = "problem_title_line";
    $(titleLine).css({
        marginTop: "6px",
        marginBottom: "3px",
        marginLeft: "0",
        paddingLeft: "3px"
    });
    titleLine.appendChild(document.createTextNode("标题：  "));
    var title = document.createElement("input");
    title.name="problemTitle";
    title.id = "problem_title";
    title.type = "text";
    title.size = 40;
    title.maxLength = 80;
    titleLine.appendChild(title);
    
    var contentLine = document.createElement("div");
    contentLine.id = "problem_content_line";
    $(contentLine).css({
        marginTop: "3px",
        marginBottom: "3px",
        marginLeft: "0",
        paddingLeft: "3px"
    });
    contentLine.appendChild(document.createTextNode("内容：  "));
    var content = document.createElement("textarea");
    content.name = "problemContent";
    content.id = "problem_content";
    content.style.width = "100%";
    content.rows = 10;
    contentLine.appendChild(content);
    
    var save = document.createElement("input");
    save.id = "problem_save";
    save.className = "button";
    save.value = "保存";
    $(save).css({
        marginTop: "3px",
        marginBottom: "3px",
        marginLeft: "3px"
    });
    save.type = "button";
    save.onclick = function() {
        //TODO: 保存到数据库
        var titleValue = document.getElementById("problem_title").value;
        var contentValue = document.getElementById("problem_content").value;
        titleValue = titleValue.trim();
        contentValue = contentValue.trim();
        if (titleValue == "") {
            alert("标题不能为空！");
            return;
        }
        if (contentValue == "") {
            alert("内容不能为空！");
            return;
        }
        var scenarioId = <%="'" + StringUtil.fixEmpty(scenario.getScenarioId())
                    + "'"%>;
        //var roleId = document.getElementById("roleId").value;
        //TODO: 怎么取得roleID
        var returnVal = Ajax.submit("cn.edu.pku.dr.requirement.elicitation.action.ProblemAction.do?ACTION=newProblem&easyJ.http.Globals.CLASS_NAME=cn.edu.pku.dr.requirement.elicitation.data.Problem&scenarioId="+scenarioId+"&roleId=1&ajax=true",'#form1','');
	    
	    //TODO: 怎么获得当前用户名？
	    
	    
	    removeElement(document.getElementById("problem_title_line"));
	    removeElement(document.getElementById("problem_content_line"));
	    removeElement(document.getElementById("problem_save"));
	    removeElement(document.getElementById("problem_exit"));
	    
	    var newTr = document.createElement("tr");
	    var td1 = document.createElement("td");
	    var td2 = document.createElement("td");
	    var td3 = document.createElement("td");
	    var td4 = document.createElement("td");
	    td1.innerHTML = titleValue;
	    td2.innerHTML = returnVal.userName && returnVal.userName != "null" ? returnVal.userName : " -- ";
	    td3.innerHTML = returnVal.buildTime && returnVal.buildTime != "null" ? returnVal.buildTime : " -- ";
	    var toDiscuss = document.createElement("input");
	    toDiscuss.className = "button";
	    toDiscuss.type = "button";
	    toDiscuss.value = "参与讨论";
	    toDiscuss.onclick = function() {
            var pid = returnVal.problemId;
            if (pid && pid != "null") {
                showProblemInside(pid);
            }
	    };
	    td4.appendChild(toDiscuss);
	    
	    newTr.appendChild(td1);
	    newTr.appendChild(td2);
	    newTr.appendChild(td3);
	    newTr.appendChild(td4);
	    
	    if (document.getElementById("problem_header")) {
	        insertAfter(newTr, document.getElementById("problem_header"));
	    } else {
	        var header = document.createElement("tr");
	        header.id = "problem_header";
	        header.innerHTML = '<th>标题</th><th>提问人</th><th>提问时间</th><th width="10%">操作</th>';
	        insertAfter(header, tr);
	        insertAfter(newTr, header);
	    }
        document.getElementById("problem_btn").style.display = "inline";
    };
    
    var exit = document.createElement("input");
    exit.id = "problem_exit";
    exit.className = "button";
    exit.value = "取消";
    $(exit).css({
        marginTop: "3px",
        marginBottom: "3px",
        marginLeft: "3px"
    });
    exit.type = "button";
    exit.onclick = function() {
        removeElement(document.getElementById("problem_title_line"));
	    removeElement(document.getElementById("problem_content_line"));
	    removeElement(document.getElementById("problem_save"));
	    removeElement(document.getElementById("problem_exit"));
	    
        document.getElementById("problem_btn").style.display = "inline";
    };
    
    editBorder.appendChild(titleLine);
    editBorder.appendChild(contentLine);
    editBorder.appendChild(save);
    editBorder.appendChild(exit);    
  }
  
  function discuss(problemId)
  {
     var   w=screen.availWidth;
     var   h=screen.availHeight;
    <%
    	String url=java.net.URLEncoder.encode("cn.edu.pku.dr.requirement.elicitation.action.DiscussAction.do?ACTION=query&easyJ.http.Globals.CLASS_NAME=cn.edu.pku.dr.requirement.elicitation.data.Discuss&discussType=problem&easyJ.http.Globals.RETURN_PATH=/WEB-INF/template/AjaxForumQuery.jsp&discussSourceId=","utf-8");
    %>
    var showUrl=<%="\"" + url + "\""%>;
    window.open("easyJ.http.servlet.FowardAction.do?"+
    "jspurl=/WEB-INF/template/AjaxNewWindow.jsp&url="+showUrl+problemId,"问题讨论","height="+h+",width="+w+",status=yes,toolbar=yes,resizable=yes, menubar=yes,location=yes");
  }
  /*此方法为了效率考虑，没有每次从服务器刷新。如果需要刷新，程序需要简单修改一下*/
  function showSolution(problemId,button)
  {
      var solution=document.getElementById("solution"+problemId);
      if(solution)
      {
        if(solution.style.display=='none')
        {
           solution.style.display='block';
	   button.value="隐藏解决方案";
        }
        else
        {
           solution.style.display='none';
	   button.value="查看解决方案";
        }
      }
      else
      {
      	var msg=Ajax.submitLoad("cn.edu.pku.dr.requirement.elicitation.action.DiscussAction.do?ACTION=showSolution&easyJ.http.Globals.CLASS_NAME=cn.edu.pku.dr.requirement.elicitation.data.Discuss&discussSourceId="+problemId,null,null,null,false);
        button.value="隐藏解决方案";
      	$("#problem"+problemId).after(msg);
      }
  }


  function showScenarioHistory(id)
  {
    var data={
      scenarioId:id
    };
    Ajax.submitLoad("cn.edu.pku.dr.requirement.elicitation.action.ScenarioVersionAction.do?ACTION=query&easyJ.http.Globals.CLASS_NAME=cn.edu.pku.dr.requirement.elicitation.data.ScenarioVersion&ajax=true","#columnMain",'',data);
  }
  function showProblemInside(id)
  {
	  
	  
      Ajax.submitLoad("cn.edu.pku.dr.requirement.elicitation.action.ProblemAction.do?ACTION=getProblem&easyJ.http.Globals.CLASS_NAME=cn.edu.pku.dr.requirement.elicitation.data.Problem&problemId=" + id + "&ajax=true", '#columnMain','','', false);
      Problem.discussProblemAmbiguity(id);
	  document.getElementById("problem_main").style.display = "block";
	  document.getElementById("tabs").style.display = "block";

	       
  }
function showAllAmbituity(problemId){
	    Problem.viewAllAmbiguityNew(problemId,'Y');

}
	
	    function showAllProblemvalue(problemId){
	    Problem.viewAllProblemvalueNew(problemId,'Y');

    }
		    function showAllProblemreason(problemId){
	    Problem.viewAllProblemreasonNew(problemId,'Y');

 	}
 	
function showDetailedSolution(problemSolutionId){
 	Problem.detailedSolution (problemSolutionId);
}

function createNewSolution(problemId){
    Problemsolution.loadNewSolution(problemId);
}


  function showViewApply() {
    var data={};
    data.scenarioId = document.getElementById("scenarioId").value;
    data["easyJ.http.Globals.RETURN_PATH"]="/WEB-INF/template/AjaxPopUpWindowDataQuery.jsp";
    var actionPath = "easyJ.http.servlet.SingleDataAction.do?ACTION=query&easyJ.http.Globals.CLASS_NAME=cn.edu.pku.dr.requirement.elicitation.data.UserScenarioRelation&ajax=true";
  	PopUpWindow.show(actionPath,data);
  }
  
  function showDiagram(button){
	if(button.value=="查看活动图"){
          var fileName=Ajax.submit("cn.edu.pku.dr.requirement.elicitation.action.ScenarioAction.do?ACTION=paint&easyJ.http.Globals.CLASS_NAME=cn.edu.pku.dr.requirement.elicitation.data.Scenario&ajax=true","#form1");
          //alert(fileName["height"]);
          button.value="查看文字描述";
          document.getElementById("graphContent").style.display="block";
          document.getElementById("graphContent").innerHTML=
          "<applet code=\"FlowAppletStart.class\" archive = \"graph.jar\" height=\""+fileName["height"] + "px\" width=\"100%\" height=\""+fileName["height"] + "px\" >"+   // 如果不设置height，那么会自动按图的总高度显示
            "<param name = \"node\" value = \""+fileName["nodeFile"]+"\">"+
            "<param name = \"edge\" value = \""+fileName["edgeFile"]+"\">"
          "</applet>";
          document.getElementById("textContent").style.display="none";

	}else{
          button.value="查看活动图";
          document.getElementById("graphContent").style.display="none";
          document.getElementById("graphContent").innerHTML="";
          document.getElementById("textContent").style.display="block";
	}
  }
  
  function showDescriptionHistory(id)
  {

  $('#moredescriptionhistory').removeClass('target-closed');
   $('#close').removeClass('target-closed');
  $('#showDescriptionHistory').removeClass('target-closed');
	Ajax.submitLoad("cn.edu.pku.dr.requirement.elicitation.action.DescriptionVersionAction.do?ACTION=showDescriptionHistory&easyJ.http.Globals.CLASS_NAME=cn.edu.pku.dr.requirement.elicitation.data.DescriptionVersion&CreatorId="+id+"&ajax=true","#showDescriptionHistory",'','',false);
	onclick();
	$(".moredescriptionhistory").bind("click",function(){
		  <%
    	String url_descriptionhistory=java.net.URLEncoder.encode("cn.edu.pku.dr.requirement.elicitation.action.DescriptionVersionAction.do?ACTION=query&easyJ.http.Globals.CLASS_NAME=cn.edu.pku.dr.requirement.elicitation.data.DescriptionVersion&easyJ.http.Globals.RETURN_PATH=/WEB-INF/template/AjaxNewWindowDescription.jsp&CreatorId=","utf-8");
      %>
	      var showUrl=<%="\"" + url_descriptionhistory + "\""%>;
     window.open("easyJ.http.servlet.FowardAction.do?"+"jspurl=/WEB-INF/template/AjaxNewWindow.jsp&url="+showUrl+id,"描述版本","status=yes,scrollbars=yes, toolbar=yes,menubar=yes,location=yes,resizable=yes");

});
  }
  function onclick(){
 // $('#showDescriptionHistory').jqResize('.jqResize');
  $("#close").bind("click",function(){
  $("#moredescriptionhistory").addClass('target-closed');
  $("#close").addClass('target-closed');
  	$("#showDescriptionHistory").addClass('target-closed');
  });


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
  }
  //函数

</script>


<jsp:include flush="true" page="/WEB-INF/template/common/History.jsp">
	<jsp:param name="" value="" />
</jsp:include>

