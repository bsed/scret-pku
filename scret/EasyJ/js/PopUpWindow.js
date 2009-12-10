//下面变量用来定义弹出窗口的信息
var valueName; //用来指示保存内容的input的名称，用在数据选择
var idName; //用来指示保存id的input的名称，用在数据选择
var selectRow; //用来指示当前是为那一行进行数据选择。

//queryFrame用来进行查询，也就是显示整个查询页面的。 valueFrame用来存放多选的结果，封装多选数据用的。
//到底是多选数据然后一次插入还是单选数据，由showTable()方法的最后一个参数multi来决定。
var PopUpWindow = {};

(function(){
    
    // 定义弹出窗口的外观HTML，其中div.dialogTitle和div.dialogContent分别是标题栏和窗口内容
    // 弹出窗口的样式定义在css/common/dialog.css中
    var loadingMessage = '<p>请稍候...</p><p><img src="./image/busy.gif" alt="loading" />	</p>'; 
    var windowHtml = '<div id="PopUpWindowFrame" class="dialogWindow">' +
                     '<div class="dialogTopLeft"><div class="dialogTopRight"><div class="dialogTitle"></div></div></div>' + 
                     '<div class="dialogBottomLeft"><div class="dialogBottomRight"><div class="dialogBody">' + 
                     '<div class="dialogContent"><div class="loading">' + loadingMessage + '</div><div id="queryFrame"></div><div><table id="valueFrame"></table></div></div></div></div></div>' + 
                     '<input type="image" src="./image/close.gif" class="dialogClose" /></div>';
    // 定义模态对话框的“页面挡板”（overlay）
    var overlayHtml = '<div id="dialogOverlay"></div>';        
    
    //multiSelect用来标明是不是批量选择数据。
	var multiSelect=false;
	//用来缓存弹出窗口的内容，如果弹出窗口的内容已经存在，则可以不用再从服务器获得，可以提高用户体验和效率。
	var popUpWindowContent='';
	//保存回调函数表
	var callbackMap = null;             
	
	// 
	//Ajax开始之前，会调用这个方法
	var onAjaxStart = function() {
	    var $win = $("#PopUpWindowFrame");
	    $(".loading", $win).show();
	    $("#queryFrame", $win).hide();
	    $("#valueFrame", $win).hide();
	    if (callbackMap && callbackMap["show"]) {
	        callbackMap["show"]($win.get(0));
	    }
	};
	//Ajax成功之后，会调用这个方法
	//注意：在对话框内容中，凡是具有class=dialogHide的元素，当被click的时候都会触发PopUpWindow.hide方法，因此，调用PopUpWindow的代码，
	//可以将内容中的“确认”、“取消”等按钮的class设为dialogHide
	//另外，凡是具有class=dialogConfirm的元素，将被自动的关联上callbackMap["confirm"]
	var onAjaxSuccess = function() {
	    var $win = $("#PopUpWindowFrame");
	    $(".loading", $win).hide();
	    $("#queryFrame", $win).show();
	    $("#valueFrame", $win).show();
	    if (callbackMap && callbackMap["load"]) {
	        callbackMap["load"]($win.get(0));
	    }
	    $(".dialogHide", $win).click(PopUpWindow.hide);
	    $(".dialogConfirm", $win).click(function() {
	        if (callbackMap && callbackMap["confirm"]) {
	            callbackMap["confirm"]($win.get(0));
	        }
	        PopUpWindow.hide();
	    });
	};
	
    PopUpWindow.init = function() {
        //TODO: draggable和现有CSS有冲突
        $(windowHtml).appendTo(document.body);
        $(overlayHtml).appendTo(document.body);
        var $win = $("#PopUpWindowFrame");
        $('input.dialogClose', $win)
			  .hover(
			    function(){ $(this).addClass('dialogCloseHover'); }, 
			    function(){ $(this).removeClass('dialogCloseHover'); })
			  .focus( 
			    function(){ this.hideFocus=true; $(this).addClass('dialogCloseHover'); })
			  .blur( 
			    function(){ $(this).removeClass('dialogCloseHover'); })
			  .click(PopUpWindow.hide);
    };                 
    
	


    //////////////////////////////////////////////////////////////////////////
    // showTable的执行过程：
    // 1、调用的地方调用showTable，并且制定点击选择之后执行的操作
    // 2、然后当点击选择按钮的时候就执行PopUpWindow.select
    // 3、PopUpWindow.singleSelect 以及 PopUpWindow.multiSelect 都是点击是具体执行的方法。分别是要赋值给PopUpWindow.select的。
    //////////////////////////////////////////////////////////////////////////


    //用来选择数据，j用来指示对第几行进行数据选择。fn,parameters用来设置点击选择的时候执行的方法
    //用在两个地方，一个是批量数据添加，参见page_function表中的相应功能。
    //另一个是选择一个数据，在新增一个明细之后，当点击某个字段的时候，会需要使用此功能，见Data.addDetail();
    PopUpWindow.showTable = function (className, j,fn,parameters){
		PopUpWindow.selectRow=fn;
		PopUpWindow.selectParam=parameters;
        selectRow = j;
		if (className == '') {
			className = $("#detailPropertyClass").attr("value");
			multiSelect=true;
		}
        var actionPath = "easyJ.http.servlet.SingleDataAction.do?ACTION=query&easyJ.http.Globals.CLASS_NAME=" + className + "&easyJ.http.Globals.RETURN_PATH=/WEB-INF/template/AjaxSelectData.jsp";
		//如果缓存不存在，或者用户又选择另外一个表的时候，需要重新从服务器获取。在此次获取的时候采用同步的方式，这样可以保证缓存页面
		if (popUpWindowContent == '' || popUpWindowContent.indexOf(className) < 0) {
			var message=Ajax.submitLoadWithoutHistory(actionPath, "#queryFrame", '',null,false);
			popUpWindowContent='';
			//alert(document.getElementById("queryFrame").innerHTML);
			popUpWindowContent = document.getElementById("queryFrame").innerHTML;
		}
		else //从缓存中读取数据
		{
			document.getElementById("PopUpWindowFrame").innerHTML = popUpWindowContent;
		}
		document.getElementById("PopUpWindowFrame").style.width='600px';
        PopUpWindow.popUp();
    }



	//在弹出框中选择数据，并把数据放到原数据中
    PopUpWindow.select = function (button){
		if(PopUpWindow.selectRow)
		{
			PopUpWindow.selectRow(button,PopUpWindow.selectParam);
			return;
		}
    }

	PopUpWindow.singleSelect=function (button)
	{
		var properties =document.getElementById("properties").value.split(",");
		var id = "#" + button.id;
		//firefox不支持parentElement，所以使用jQuery对象。
		var row = $(id).parent().parent()[0];
		var retValue = [];
		var title = document.getElementById("title");
		//用来得到所有的column中的属性名和值*/
		for (i = 0; i < row.cells.length - 1; i++) {
			var property = properties[i];
			document.getElementById((property + selectRow)).value = row.cells[i + 1].innerHTML;
		}
	}

    PopUpWindow.multiSelect=function (button)
	{
		var properties = document.getElementById("properties").value.split(",");
		var id = "#" + button.id;
		//firefox不支持parentElement，所以使用jQuery对象。
		var valueRow=$(id).parent().parent().clone();
		var delButton="<input type=\"button\" class=\"button\" id=\""+button.id+"1\" onclick=\"PopUpWindow.deleteSelectRow(this)\" value=\"删除\"/>";
		$("#valueFrame").append(valueRow);
		var newButton=valueRow.children().children();
		newButton.replaceWith(delButton);
	};

	PopUpWindow.deleteSelectRow=function(button)
	{
        var detailTable = document.getElementById("valueFrame");
		var id="#" + button.id;
        var rindex = $(id).parent().parent()[0].rowIndex;
        detailTable.deleteRow(rindex);
	};

    PopUpWindow.popUp = function(){
        var $doc = $(document);
    	$("#dialogOverlay").width($doc.width()).height($doc.height()).show();
    	$("#PopUpWindowFrame").show();
   };

    PopUpWindow.hide = function(){
        if (callbackMap && callbackMap["hide"]) {
            callbackMap["hide"]($("#PopUpWindowFrame").get(0));
        }
        $("#PopUpWindowFrame").hide();
		$("#dialogOverlay").hide(); 
    };
    
    PopUpWindow.setWidth = function(w) {
        $("#PopUpWindowFrame").css({
            "width": w + "px",
            "margin-left": (-w/2) + "px"
        });
    };
    //TODO: 在下面的showXXX方法中，设置弹出窗口的Title

    PopUpWindow.showTree = function (name, id, valueId){
        valueName = name;
        idName = id;
        var $win = $("#PopUpWindowFrame");
        $win.css("width", "auto");
        var actionPath = "DisplayTree.jsp?className=easyJ.system.data.Dictionary&selectMode=3&rootId=" + valueId
        Ajax.submitLoadWithoutHistory(actionPath, $(".dialogContent", $win), '',null,true);
        PopUpWindow.popUp();
    }
    /*
    	用来实现左边用select存放一些数据，右边用select显示已选择的数据。data是放一些调用者需要传到服务器的一些数据。
    	data中包括：选择sourceClass以及destClass数据的条件。具体应用见：
    	此方法在场景中增加数据和增加角色的地方用到，也会在选择字段的地方用到。
    */
    PopUpWindow.showMultiSelect=function (actionName,sourceClass,destClass,data,fn) {
    	//alert(actionName);
    	//这句话是为了在MultiSelect.confirm方法中使用destClass变量。
    	var $win = $("#PopUpWindowFrame");
    	data.destClass=destClass;
    	var actionPath = actionName+".do?ACTION=multiSelect&sourceClass=" + sourceClass + "&destClass="+destClass;
    	//alert(actionPath);
    	Ajax.submitLoadWithoutHistory(actionPath, $(".dialogContent", $win), '',data,false);
    	PopUpWindow.setWidth(600); // 600px
        PopUpWindow.popUp();
        //将参数传递给MultiSelect，在确认的时候使用。
        MultiSelect.confirm=fn;
        MultiSelect.data=data;
        MultiSelect.cacheSourceData();
    }
    
    /*这个方法用来按照actionPath来显示内容，对显示内容的操作不会对原窗口产生影响，所以只需要传进来一个actionPath。
      其他的方法之所有有专门的名字，例如: showMultiSelect等，这是因为对这些弹出内容的操作会影响到原来的窗口，或者需要
      原来的窗口提供不同的回调函数（例如showPropertyMultiSelect），所以都分别单独写了。
      options.callbacks包含show, load, hide, confirm四个可选的成员，每个成员是一个以PopUpWindow的HTML元素为参数的函数
    */
    PopUpWindow.show = function(options) {
        callbackMap = options.callbacks;  // 必须先改变callbackMap的值
    	Ajax.submitLoadWithoutHistory(options.url, "#queryFrame", '',options.data,false, {
    	    start: onAjaxStart,
    	    success: onAjaxSuccess});
    	PopUpWindow.popUp();
    }

    /*
    	用来实现对属性配置的操作，和showMultiSelect基本一样，是复用了showMultiSelect修改了一些界面。
    	fn和parameter是用来设置MultiSelect.confirm方法的，因为点击”确定“的时候只是执行MultiSelect.confirm()，但可能不同的页面执行的
    	方法不同，所以需要使用回调函数fn。
    */
    PopUpWindow.showPropertyMultiSelect=function (actionName,form,data,fn,parameter) {
    	//alert(actionName);
    	var $win = $("#PopUpWindowFrame");
    	var actionPath = "easyJ.http.servlet.SingleDataAction.do?ACTION=adjustProperty"
    	//alert(actionPath);
    	Ajax.submitLoadWithoutHistory(actionPath, $(".dialogContent", $win), form,data,false);
    	PopUpWindow.setWidth(600);
    	MultiSelect.data=parameter;
    	MultiSelect.confirm=fn;
        PopUpWindow.popUp();
        MultiSelect.cacheSourceData();
    }

    //用来选择数据，j用来指示对第几行进行数据选择。fn,parameters用来设置点击选择的时候执行的方法
    PopUpWindow.showTable = function (className, j,fn,parameters){
        var $win = $("#PopUpWindowFrame");
		PopUpWindow.selectRow=fn;
		PopUpWindow.selectParam=parameters;
        selectRow = j;
		if (className == '') {
			className = $("#detailPropertyClass").attr("value");
			multiSelect=true;
		}
        var actionPath = "easyJ.http.servlet.SingleDataAction.do?ACTION=query&easyJ.http.Globals.CLASS_NAME=" + className + "&easyJ.http.Globals.RETURN_PATH=/WEB-INF/template/AjaxSelectData.jsp";
		//如果缓存不存在，或者用户又选择另外一个表的时候，需要重新从服务器获取。在此次获取的时候采用同步的方式，这样可以保证缓存页面
		if (popUpWindowContent == '' || popUpWindowContent.indexOf(className) < 0) {
			var message=Ajax.submitLoadWithoutHistory(actionPath, "#queryFrame", '',null,false);
			popUpWindowContent='';
			//alert(document.getElementById("queryFrame").innerHTML);
			popUpWindowContent = document.getElementById("queryFrame").innerHTML;
		}
		else //从缓存中读取数据
		{
			$(".dialogContent", $win).html(popUpWindowContent);
		}
		PopUpWindow.setWidth(600);
        PopUpWindow.popUp();
    }



    PopUpWindow.query = function(pageNo){
        //actionName是用来标识前一步的操作是使用的哪个Action.见ServletAction的process方法，在那里做了处理
        var $win = $("#PopUpWindowFrame");
        var actionName = document.getElementById("easyJ.http.Globals.SELECT_ACTION_NAME");
        document.getElementById("selectDataForm").PAGENO.value = pageNo;
        //var actionPath = actionName.value + ".do?ACTION=query&easyJ.http.Globals.RETURN_PATH=/WEB-INF/template/AjaxSelectData.jsp";
        var actionPath = actionName.value + ".do?ACTION=query";
        //alert(actionPath);
        //alert("aa");
        Ajax.submitLoadWithoutHistory(actionPath, "#queryFrame", "#selectDataForm",null,false);
		popUpWindowContent = $win.html();
    }

    PopUpWindow.orderBy = function (fieldName){
        var $win = $("#PopUpWindowFrame");
        actionName = document.getElementById("easyJ.http.Globals.SELECT_ACTION_NAME");
        document.getElementById("selectDataForm").ORDER_BY_COLUMN.value = fieldName;
        if (document.getElementById("selectDataForm").ORDER_DIRECTION.value == '')
            document.getElementById("selectDataForm").ORDER_DIRECTION.value = "DESC";
        if (document.getElementById("selectDataForm").ORDER_DIRECTION.value == 'DESC')
            document.getElementById("selectDataForm").ORDER_DIRECTION.value = "ASC";
        else
            document.getElementById("selectDataForm").ORDER_DIRECTION.value = "DESC";
        Ajax.submitLoadWithoutHistory(actionName.value + ".do?ACTION=query&ajax=true", "#queryFrame", "#selectDataForm",null,true);
		popUpWindowContent = $win.html();
    }

    PopUpWindow.submitThisPage = function (pageNo,totalPages){
    	//alert("submit");
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
        document.getElementById("selectDataForm").PAGENO.value = pageNo;
        PopUpWindow.query(pageNo);
    }
})();

$(document).ready(function() {
    PopUpWindow.init();
});
