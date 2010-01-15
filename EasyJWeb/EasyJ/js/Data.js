//此文件用来封装页面对数据的各种操作
var Data = {};


(function(){
    var _dataArea = "#columnMain";

    Data.edit = function(primaryKey, id){
        var actionName = document.getElementById("easyJ.http.Globals.ACTION_NAME");
        Ajax.submitLoad(actionName.value + ".do?ACTION=edit&" + primaryKey + "=" + id, _dataArea, "#form1", null);
    }

	//用来刷新编辑页面用的。
    Data.refreshEdit = function(){
        var actionName = document.getElementById("easyJ.http.Globals.ACTION_NAME");
        Ajax.submitLoad(actionName.value + ".do?ACTION=edit", _dataArea, "#form1", null);
    }

    Data.orderBy = function orderBy(fieldName, editable){
        //如果有弹出窗口，则执行弹出窗口相应的方法
        if(document.getElementById("selectDataForm")) {
           PopUpWindow.orderBy(fieldName);
           return;
        }
    
        var actionName = document.getElementById("easyJ.http.Globals.ACTION_NAME");
        var form1 = document.getElementById("form1");
        form1.ORDER_BY_COLUMN.value = fieldName;
        if (form1.ORDER_DIRECTION.value == '')
            form1.ORDER_DIRECTION.value = "DESC";
        if (form1.ORDER_DIRECTION.value == 'DESC')
            form1.ORDER_DIRECTION.value = "ASC";
        else
            form1.ORDER_DIRECTION.value = "DESC";
        if (editable)
        	Ajax.submitLoad(actionName.value + ".do?ACTION=queryEdit&ajax=true",
        	 _dataArea, "#form1", null);
       	else
       	     Ajax.submitLoad(actionName.value + ".do?ACTION=query&ajax=true",
        	 _dataArea, "#form1", null);
    }

    Data.checkAll = function(){
    	var form1 = document.getElementById("form1");
        var checked = false;
        if (form1.checkAll.checked == true)
            checked = true;
        else
            checked = false;
        for (i = 0; i < 20; i++) {
            var a = document.getElementById("check" + i);
            if (a != null)
                a.checked = checked;
        }
    }

    Data.query = function(pageNo){
        //如果有弹出窗口，则执行弹出窗口相应的方法
        if(document.getElementById("selectDataForm")){
           PopUpWindow.query(pageNo);
    		return;
        }
        var actionName = document.getElementById("easyJ.http.Globals.ACTION_NAME");
        document.getElementById("PAGENO").value = pageNo;
        Ajax.submitLoad(actionName.value + ".do?ACTION=query", _dataArea, "#form1", null);
    }

    Data.queryEdit = function(pageNo){
        //如果有弹出窗口，则执行弹出窗口相应的方法
        if(document.getElementById("selectDataForm")){
           PopUpWindow.query(pageNo);
    		return;
        }
        var actionName = document.getElementById("easyJ.http.Globals.ACTION_NAME");
        //alert(actionName);
        document.getElementById("PAGENO").value = pageNo;
        Ajax.submitLoad(actionName.value + ".do?ACTION=queryEdit", _dataArea, "#form1", null);
    }

    Data.updateObj = function(){
        var obj = document.getElementById("id");
        var actionName = document.getElementById("easyJ.http.Globals.ACTION_NAME");
        var action = '';
        if (obj.value == '') {
            action = actionName.value + ".do?ACTION=newObject";
            Ajax.submitLoad(action, _dataArea, "#form1");
        }
        else {
            action = actionName.value + ".do?ACTION=update";
            Ajax.submitLoad(action, _dataArea,"#form1");
        }
    }
	
	Data.createObj = function(){
		// @deprecated   to use Data.updateObj.
		var actionName = document.getElementById("easyJ.http.Globals.ACTION_NAME");
		var action = actionName.value+".do?ACTION=create";
		Ajax.submitLoad(action,_dataArea);	
	}
	
    Data.create = function(){
		alert('Data.creat');
        var actionName = document.getElementById("easyJ.http.Globals.ACTION_NAME");
        Ajax.submitLoad(actionName.value + ".do?ACTION=edit", _dataArea, "#form1", null);
    }

    Data.deleteObj = function(){
        var actionName = document.getElementById("easyJ.http.Globals.ACTION_NAME");
        var checked=false;
        for (var i = 0; i < 20; i++) {
            var a = document.getElementById("check" + i);
            if (a != null && a.checked)
                checked=true;
        }
        if(!checked) {
         	alert("请选择要删除的数据");
         	return;
        }
        Ajax.submitLoad(actionName.value + ".do?ACTION=delete", _dataArea, "#form1", null);
    }

    Data.submitThisPage = function(pageNo, totalPages, editable){
    
            //如果有弹出窗口，则执行弹出窗口相应的方法
        if(document.getElementById("selectDataForm"))
           PopUpWindow.submitThisPage(pageNo, totalPages);
           
        var errStr = "";
        if (pageNo == '') {
            errStr = "请输入查询的页数！！";
        }
        if (pageNo > totalPages)
            errStr = "超过总页数！！";
        if (errStr != "") {
            alert(errStr);
            return;
        }
        document.getElementById("PAGENO").value = pageNo;
        if (editable)
        	Data.queryEdit(pageNo);
        else
        	Data.query(pageNo);
    }

    //用在主子表编辑form1.properties.value的格式是"propertyName,propertyName," 最后面是有一个逗号的，
    //所以split之后有用的应该是length-1。 form1.classes.value代表相应的property对应的类
    //如果thisRow存在，则是在thisRow之前插入数据。
    Data.addDetail = function(thisRow, direction){
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
        var column = document.createElement("td");


        var deleteImg = document.createElement("image");
        deleteImg.src = "image\\delete.gif";
        deleteImg.onclick = function(){
            Data.deleteDetail(this);
        }
        deleteImg.id = "delete" + detailSize;

        var upImg = document.createElement("image");
        upImg.src = "image\\up.gif";
        upImg.onclick = function(){
            Data.insert(this, "up");
        }
        upImg.id = "insertBefore" + detailSize;

        var downImg = document.createElement("image");
        downImg.src = "image\\down.gif";
        downImg.onclick = function(){
            Data.insert(this, "down");
        }
        downImg.id = "insertAfter" + detailSize;

        //用来增加hidden的主键
        var hiddenInput = document.createElement("input");
        hiddenInput.type = "hidden";
        hiddenInput.setAttribute("name", document.getElementById("subPrimaryKeyName").value);
        hiddenInput.id = "subPrimaryKeyName" + detailSize;

        column.appendChild(deleteImg);
        column.appendChild(upImg);
        column.appendChild(downImg);
        column.appendChild(hiddenInput);

        row.appendChild(column);
        for (i = 0; i < properties.length - 1; i++) {
            var column = document.createElement("td");
            var input;
            if (propertyTypes[i] == "textarea")
                input = document.createElement("textarea");
            else {
                input = document.createElement("input");
                input.type = "text";
            }
            input.name = properties[i];
            input.id = properties[i] + detailSize;
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
        tbody.appendChild(row);
        if (thisRow) {
            if (direction == 'up')
                thisRow.parentNode.insertBefore(row, thisRow);
            else
                Data.insertAfter(row, thisRow);
        }
        //$("#"+thisRow.id).before(tbody.innerHTML);
        else
            details.appendChild(tbody);

        form1.detailSize.value = details.rows.length;
        //alert(form1.detailSize.value);
    }

    Data.insertAfter = function(newElement, targetElement){
        var parent = targetElement.parentNode;
        if (parent.lastChild == targetElement) {
            parent.appendChild(newElement);
        }
        else {
            parent.insertBefore(newElement, targetElement.nextSibling);
        }
    }

    //在某条记录前面增加记录
    Data.insert = function(button, direction){
        var id = "#" + button.id;
        //firefox不支持parentElement，所以使用jQuery对象。
        var row = $(id).parent().parent()[0];
        Data.addDetail(row, direction);
    }

    //用在主子表删除新增明细
    Data.deleteDetail = function(button){
        var detailTable = document.getElementById("details");
        var detailSize = details.rows.length;
        var id = "#" + button.id;
        var rindex = $(id).parent().parent()[0].rowIndex;
        detailTable.deleteRow(rindex);
        detailSize--;
        form1.detailSize.value = detailSize;
    }

    //用在主子表隐藏已有明细。
    Data.hiddenDetail = function(button){
        var detailTable = document.getElementById("details");
        var detailSize = details.rows.length;
        var id = "#" + button.id;
        var rindex = $(id).parent().parent()[0].rowIndex;
        $(id).parent().parent()[0].style.display = "none";

        //*下面减1是为了去掉题头的一行*/
        var useState = document.getElementById("useState" + (rindex - 1));
        useState.value = "N";
    }
    
	//用来调整每个类的字段显示顺序
	Data.adjustProperty=function() {
		var data={};
		var propertyType=document.getElementById("multiSelectPropertyType");
		if(propertyType)
			data.propertyType=propertyType.value;
			
		PopUpWindow.showPropertyMultiSelect("easyJ.http.servlet.SingleDataAction.do?ACTION=adjustProperty", "#form1",data,Data.adjustPropertyConfirm,null);
	}
	
	Data.adjustPropertyConfirm=function() {
		alert("adjustPropertyConfirm");
	}
	
	
	//用来申请加入某个项目或场景的
	Data.apply=function(action,primaryKey,id) {
        //如果有弹出窗口，则执行弹出窗口相应的方法
        var formName = "#form1";
        if(document.getElementById("selectDataForm")){
            formName =  "#selectDataForm";
        }
		var actionName=action;
		Ajax.submit(actionName + ".do?ACTION=apply&" + primaryKey + "=" + id,"#form1", null);
	}
	
	//用来确认某个人的申请的
	Data.confirmApply=function(action,primaryKey,id) {
	    var formName = "form1";
        if(document.getElementById("selectDataForm")){
            formName =  "selectDataForm";
        }
		var data = {};
		data["easyJ.http.Globals.CLASS_NAME"] = document.getElementById(formName)["easyJ.http.Globals.CLASS_NAME"].value;
		var actionName=action;
		Ajax.submit(actionName + ".do?ACTION=confirmApply&" + primaryKey + "=" + id,null, data);
	}

	//用来拒绝某个人的申请的
	Data.rejectApply=function(action,primaryKey,id) {
	    var formName = "form1";
        if(document.getElementById("selectDataForm")){
            formName =  "selectDataForm";
        }
		var data = {};
		data["easyJ.http.Globals.CLASS_NAME"] = document.getElementById(formName)["easyJ.http.Globals.CLASS_NAME"].value;

		var actionName=action;
		Ajax.submit(actionName + ".do?ACTION=rejectApply&" + primaryKey + "=" + id,null, data);
	}
	
	//用来取消某个人的资格的
	Data.cancel=function(action,primaryKey,id) {
		var formName = "form1";
        if(document.getElementById("selectDataForm")){
            formName =  "selectDataForm";
        }
		var actionName=action;
		var data = {};
		data["easyJ.http.Globals.CLASS_NAME"] = document.getElementById(formName)["easyJ.http.Globals.CLASS_NAME"].value;
		Ajax.submit(actionName + ".do?ACTION=cancel&" + primaryKey + "=" + id,null, data);
	}
    
    /*
    	这里暂时用不到action，所以不做处理，以后如果用到，再处理
    */
    Data.updateDisplayEdit = function(actionName, primaryKey, id, line) {
    	var formName = "form1";
		var properties = $("#properties").attr("value");
		var propertiesAttr = properties.split(",");
		var data = {};
		for ( i = 0; i < propertiesAttr.length; i++ ) {
			var values = document.getElementsByName(propertiesAttr[i]+line);
			//如果只有一个值， 如果没有值则不做处理
			if (values.length == 1)
				data[propertiesAttr[i]] = values[0].value;
			else  if (values.length > 1){
			    //如果是多个值，radio或者checkbox的情况
			    if (values[0].type == "radio") {
					for ( j = 0; j < values.length; j++) {
						if (values[j].checked)
							data[propertiesAttr[i]] = values[j].value;
					}
				}
				
				if (values[0].type == "checkbox") {
					for ( j = 0; j < values.length; j++) {
						if (values[j].checked) {
							if (data[propertiesAttr[i]])
								data[propertiesAttr[i]] =data[propertiesAttr[i]]+","+ values[j].value;
							else
								data[propertiesAttr[i]] =  values[j].value;
						}
					}
				}
			}
			
		}
		data["easyJ.http.Globals.CLASS_NAME"] = document.getElementById(formName)["easyJ.http.Globals.CLASS_NAME"].value;
		//alert(objToString(data));
		Ajax.submit(actionName + ".do?ACTION=update&" + primaryKey + "=" + id,null, data);
    }
})();
