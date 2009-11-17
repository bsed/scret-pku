<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="easyJ.common.*,easyJ.http.Globals,java.util.*,easyJ.system.service.*,easyJ.system.data.*"%>
<%@ page import="easyJ.common.validate.*,cn.edu.pku.dr.requirement.elicitation.action.UseCaseAction"%>
<%@ taglib uri="/WEB-INF/easyJ.tld" prefix="easyJ" %>
<link rel="stylesheet" href="/css/scenario.css"/>
<script language="javascript">
  var UseCase={};
  var manager = EditorManagerFactory.createEditorManager("plaintext");
  manager.addTerms("主管,仓管员", "role");
  manager.addTerms("入库单,质检单", "data");
  (function(){
  	var focusObj;
    var _dataArea = "#columnMain";

    //用在主子表编辑form1.properties.value的格式是"propertyName,propertyName," 最后面是有一个逗号的，
    //所以split之后有用的应该是length-1。 form1.classes.value代表相应的property对应的类
    //如果thisRow存在，则是在thisRow之前插入数据。
    UseCase.addDetail = function(thisRow, direction,type){
        var details = document.getElementById("details");
        var properties = form1.properties.value.split(",");
        var classes = form1.classes.value.split(",");
        var propertyTypes = form1.propertyTypes.value.split(",");
        var detailTable = document.getElementById("details");
        var detailSize = details.rows.length - 1;
        //alert(detailSize);
        var tbody = document.createElement("tbody");
        var row = document.createElement("tr");
        row.id = "tr" + detailSize;
/*        var column = document.createElement("td");
        column.onmouseover=function(){
          showImg(this);
        }

        column.onmouseleave=function(){
          hideImg(this);
        }

        var deleteImg = document.createElement("image");
        deleteImg.style.display="none";
        deleteImg.src = "image\\delete.gif";
        deleteImg.onclick = function(){
            UseCase.deleteDetail();
        }
        deleteImg.id = "delete" + detailSize;

        var upImg = document.createElement("image");
        upImg.style.display="none";
        upImg.src = "image\\up.gif";
        upImg.onclick = function(){
            UseCase.insert(this, "up");
        }
        upImg.id = "insertBefore" + detailSize;

        var upSysImg = document.createElement("image");
        upSysImg.style.display="none";
        upSysImg.src = "image\\up.gif";
        upSysImg.onclick = function(){
            UseCase.insert(this, "up","sys");
        }
        upSysImg.id = "insertBeforeSys" + detailSize;


        var downImg = document.createElement("image");
        downImg.style.display="none";
        downImg.src = "image\\down.gif";
        downImg.onclick = function(){
            UseCase.insert(this, "down");
        }
        downImg.id = "insertAfter" + detailSize;

        var downSysImg = document.createElement("image");
        downSysImg.style.display="none";
        downSysImg.src = "image\\down.gif";
        downSysImg.onclick = function(){
            UseCase.insert(this, "down","sys");
        }
        downSysImg.id = "insertAfterSys" + detailSize;

        //用来增加hidden的主键
        var hiddenInput = document.createElement("input");
        hiddenInput.type = "hidden";
        hiddenInput.setAttribute("name", document.getElementById("subPrimaryKeyName").value);
        hiddenInput.id = "subPrimaryKeyName" + detailSize;

        column.appendChild(deleteImg);
        column.appendChild(upImg);
        column.appendChild(upSysImg);
        column.appendChild(document.createElement("br"));
        column.appendChild(downImg);
        column.appendChild(downSysImg);
        column.appendChild(hiddenInput);
*/

        //用来增加hidden的主键
        var hiddenInput = document.createElement("input");
        hiddenInput.type = "hidden";
        //hiddenInput.setAttribute("name", document.getElementById("subPrimaryKeyName").value);
        //alert(document.getElementById("subPrimaryKeyName").value);
        hiddenInput.name = document.getElementById("subPrimaryKeyName").value;
        hiddenInput.id = "subPrimaryKeyName" + detailSize;

        //此处先把每行的按钮屏蔽掉
        //row.appendChild(column);
        for (i=0; i < properties.length - 1; i++) {
            var column = document.createElement("td");
            if(i!=0||type!="sys"){
            	column.className="trContent";
            	//column.setAttribute("className",'trContent');
            }
            var input;
            if (propertyTypes[i] == "textarea") {
                input = document.createElement("textarea");
                input.onpaste= function(){
                  setHeight();
                }
                input.style.overflow="auto";
                input.style.width="100%";
                input.style.height="100%";
                input.onpropertychange=function(){
                  setHeight();
                }
            }
            else {
                input = document.createElement("input");
                input.type = "text";
            }
            input.name = properties[i];
            input.id = properties[i] + detailSize;
            input.onfocus=function(){
              UseCase.rememberFocus(this);
            }
            //如果是系统动作,则第一列不显示
            if(i==0&&type=="sys"){
              input.style.display="none";
              input.value="^^sys$$";
            }


            //如果是需要选择数据的话，就需要这样
            if (classes[i] != '') {
                input.type = "hidden"

                var className = classes[i];
                i++;
                var inputDisplay = document.createElement("input");
                inputDisplay.type = "text";
                inputDisplay.name = properties[i];
                inputDisplay.id = properties[i] + detailSize;
                inputDisplay.readOnly = "true";
                //PopUpWindow.showTable(classes[i], detailSize)不能直接用classes[i]，传不过去，可能是因为当作了对象，
                //则成了引用传递，而classes出了这个方法就失效了。
                inputDisplay.onclick = function(){
                    PopUpWindow.showTable(className, detailSize);
                };
                column.appendChild(inputDisplay);
            }
            column.appendChild(input);
            row.appendChild(column);
        }
        row.appendChild(hiddenInput);
        //alert(row.innerHTML);
        tbody.appendChild(row);
        if (thisRow) {
            if (direction == 'up')
                thisRow.parentNode.insertBefore(row, thisRow);
            else
                UseCase.insertAfter(row, thisRow);
        }
        //$("#"+thisRow.id).before(tbody.innerHTML);
        else {
        	//alert(row.innerHTML);
        	UseCase.insertAfter(row, details.rows[1]);
        }
            //details.appendChild(tbody);

        form1.detailSize.value = details.rows.length;

        var child=row.firstChild;
        /*
        while(child){
          child.setAttribute("className","trcontent");
          child=child.nextSibling;
        }*/
        //alert(form1.detailSize.value);
    }
    UseCase.insertAfter = function(newElement, targetElement){
        var parent = targetElement.parentNode;
        if (parent.lastChild == targetElement) {
            parent.appendChild(newElement);
        }
        else {
            parent.insertBefore(newElement, targetElement.nextSibling);
        }
    }

    //在某条记录前面增加记录
    UseCase.insertRow = function(row, direction,type){
        //var id = "#" + button.id;
        //firefox不支持parentElement，所以使用jQuery对象。
        //var row = $(id).parent().parent()[0];
        UseCase.addDetail(row, direction,type);
    }

    UseCase.insert = function(direction,type){
      var row=UseCase.getRow();
      //alert(row);
      if(row) {
      	//如果等于1，说明此时是添加的第一行。
      	if(row=="1")
        	UseCase.insertRow(null,direction,type);
        else
        	UseCase.insertRow(row,direction,type);
        if(focusObj)
      		focusObj.focused="true";
      	UseCase.replaceWithEditor(details.rows.length-2);
      }
    }
    //用来模拟UseCase的执行
    UseCase.simulate = function(action) {
    	var useCaseId = $("input[@name=useCaseId]").val();
    	var data = {};
    	data["useCaseId"] = useCaseId;
    	data["ACTION"]="simulate";
    	data["easyJ.http.Globals.CLASS_NAME"]="cn.edu.pku.dr.requirement.elicitation.data.UseCaseInteraction";
    	Ajax.submitLoad(action+".do", "#columnMain", null,data);
    }
    
    //用来模拟执行的时候对功能键的点击
    UseCase.simulateClick = function(useCaseId, flowNodeCode) {
    	var action = "cn.edu.pku.dr.requirement.elicitation.action.FlowSimulateAction";
    	var data = {};
    	data["useCaseId"] = useCaseId;
    	data["ACTION"]="simulateFunctionClick";
    	data["flowNodeCode"] = flowNodeCode;
    	data["easyJ.http.Globals.CLASS_NAME"]="cn.edu.pku.dr.requirement.elicitation.data.UseCaseInteraction";
    	var $win = $("#PopUpWindowFrame");
    	Ajax.submitLoadWithoutHistory(action+".do", $(".dialogContent", $win), null,data,false,{
    		success: function(msg) {
		    	PopUpWindow.setWidth(600);
		        PopUpWindow.popUp();
    		}
    	});
    }
    //hint是指当用户点击当前按钮的时候，会在显示页面上给用户什么提示信息
    UseCase.simulateJump = function(useCaseId, hint, flowNodeCode) {
    	PopUpWindow.hide();
    	var action = "cn.edu.pku.dr.requirement.elicitation.action.FlowSimulateAction";
    	var data = {};
    	data["useCaseId"] = useCaseId;
    	data["ACTION"]="simulate";
    	data["flowNodeCode"] = flowNodeCode;
    	data["hintMessage"]=hint;
    	data["easyJ.http.Globals.CLASS_NAME"]="cn.edu.pku.dr.requirement.elicitation.data.UseCaseInteraction";
    	var $win = $("#PopUpWindowFrame");
    	Ajax.submitLoad(action+".do","#columnMain", null,data);
    }
    //当改变处理对象时，用来得到选择对象的属性
    UseCase.getProperties = function(lineNum, classId) {
    	    	var action = "cn.edu.pku.dr.requirement.elicitation.action.UseCaseAction";
    	var data = {};
    	data["classId"] = classId;
    	data["lineNum"] = lineNum;
    	data["ACTION"]="getProperty";
    	data["easyJ.http.Globals.CLASS_NAME"]="cn.edu.pku.dr.requirement.elicitation.data.UseCaseInteraction";
    	Ajax.submit(action+".do",null,data,{
    	    success: function(msg) {
    			$(this).parent()("#columns").html(msg);
    		}
    	});
    }
    
    UseCase.getRow=function(){
      var row=focusObj;
      //alert(focusObj);
      if(row){
        while(row.nodeName.toLowerCase() != "tr")
          row=row.parentNode;
        if(row.parentNode.parentNode.id!="details"){
          alert("请选择一行");
          return null;
        }
      }
      else {
        //如果details有两行，说明还没有明细，则这个时候应该直接加入一行，否则提示请选择一行。
        if(details.rows.length>2) {
        	alert("请选择一行");
        	return;
        }
        else if(details.rows.length==2){
        	row="1";
        	return row;
        }
      }
      return row;
    }

    UseCase.hiddenDetail = function(){
      var row=UseCase.getRow();
      if(row){
        row.style.display="none";
        var rindex = row.rowIndex;
        //alert(rindex);
        //*下面减1是为了去掉题头的一行*/
        var useState = document.getElementById("useState" + (rindex));
        useState.value = "N";
      }
    }
    UseCase.replaceWithEditor=function(line){
      var operatorAction=document.getElementById("operatorAction"+line);
      if(!operatorAction) return;
      var systemAction=document.getElementById("systemAction"+line);
      var systemOutput=document.getElementById("systemOutput"+line);
      var options = {
		content: operatorAction.value,
        width: "100%",
        height: "50px",
        scrollY: false
      };
      if(operatorAction.style.display!="none")
        manager.createEditor(operatorAction, options,"operatorActionEditor"+line);
      var options = {
		content: systemAction.value,
        width: "100%",
        height: "50px",
        scrollY: false
      };
      manager.createEditor(systemAction, options,"systemActionEditor"+line);
      var options = {
		content: systemOutput.value,
        width: "100%",
        height: "50px",
        scrollY: false
      };
      manager.createEditor(systemOutput, options,"systemOutputEditor"+line);

      function addEvent(id, id2, id3) {
        var iframe = document.getElementById(id);
        if(!iframe)
          return;
        var doc = document;
        $(iframe.contentWindow.document).bind("click", function() {
          UseCase.rememberFocus(iframe);
        }).bind("keyup", function(event) {
          event = event || window.event;
          var key = event.keyCode || event.which;
          if (key != 13 && key != 8 && key != 46) { // Enter, Delete, Backspace
            return;
          }
          var iframe2 = doc.getElementById(id2);
          var iframe3 = doc.getElementById(id3);
          var h = (iframe ? iframe.contentWindow.document.body.scrollHeight : 0);
          var h2 = (iframe2 ? iframe2.contentWindow.document.body.scrollHeight : 0);
          var h3 = (iframe3 ? iframe3.contentWindow.document.body.scrollHeight:0);
          var maxHeight = (h > h2 ? h : h2);
          maxHeight = maxHeight > h3 ? maxHeight : h3;

          if (iframe)
          iframe.style.height = maxHeight + "px";
          if (iframe2)
          iframe2.style.height = maxHeight + "px";
          if (iframe3)
          iframe3.style.height = maxHeight + "px";
        });
      }

      addEvent("operatorActionEditor"+line, "systemActionEditor"+line, "systemOutputEditor"+line);
      addEvent("systemActionEditor"+line, "operatorActionEditor"+line, "systemOutputEditor"+line);
      addEvent("systemOutputEditor"+line, "systemActionEditor"+line, "operatorActionEditor"+line);
    }

    UseCase.replaceAllWithEditor=function(){
      for(i=0;i<details.rows.length;i++){
        UseCase.replaceWithEditor(i);
      }
    }
    UseCase.deleteDetail=function(){
      var row=UseCase.getRow();
      if(row){
        row.style.display="none";
        var rindex = row.rowIndex;
        var detailTable = document.getElementById("details");
        var detailSize = details.rows.length;
        detailTable.deleteRow(rindex);
        detailSize--;
        form1.detailSize.value = detailSize;
      }
    }

    UseCase.updateObj=function(){
      var iframes=document.getElementsByTagName("iframe");
      for(i=0;i<iframes.length;i++){
        var iframe=iframes[i];
        var iframeId=iframe.id;
        //alert(iframeId);
        var content=iframeId.split("Editor");
        line=content[1];
        //alert(line);
        textArea=document.getElementById(content[0]+content[1]);
        //alert(textArea);
        textArea.value=getIframeContent(iframeId);
      }
      Data.updateObj();
    }
    
    UseCase.rememberFocus=function(obj){
    	focusObj=obj;
  	}
  })();

  function getIframeContent(iframeId){
    return document.getElementById(iframeId).contentWindow.document.body.innerHTML;
  }



  function showImg(td){
    var child=td.firstChild;
    while(child){
      child.style.display="block";
      child=child.nextSibling;
    }
  }
  function hideImg(td){
    var child=td.firstChild;
    while(child){
      child.style.display="none";
      child=child.nextSibling;
    }
  }
  function setHeight()
  {
      //txt1.style.height=txt1.scrollHeight+19;
  }
</script>
<table class="border" width="100%"><tr><td>
  <easyJ:EditTag columnsPerLine="2" start="0" size="6"/>
  <table class="border"><tr><td>
  <%out.print(UseCaseAction.getCompositeDetail(request).toString());%>
  </td></tr></table>

  <easyJ:EditTag columnsPerLine="2" start="6" size="-1"/>
  <input type="hidden" name="<%=Globals.CLASS_NAME%>" id="<%=Globals.CLASS_NAME%>" value="<%=request.getParameter(Globals.CLASS_NAME)%>"/>
  <input type="hidden" name="<%=Globals.ACTION_NAME%>" id="<%=Globals.ACTION_NAME%>" value="<%=request.getAttribute(Globals.ACTION_NAME)%>"/>
  <input type="hidden" name="detailSize" value="" />
  <easyJ:FunctionTag pageName="AjaxUseCaseEdit.jsp" position="<%=Globals.FUNCTION_EDIT%>"/>
</td></tr></table>
  <jsp:include flush="true" page="/WEB-INF/template/common/History.jsp"/>

<script language="javascript">
UseCase.replaceAllWithEditor();
var detailSize=details.rows.length;
form1.detailSize.value=details.rows.length;
</script>

