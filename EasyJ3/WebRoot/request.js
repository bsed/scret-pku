Ext.onReady(function(){
	var req_nums = Ext.select(".req_num");
	for(var i = 0 ;i<req_nums.getCount();i++){
		req_nums.item(i).addListener("click",function(link){
			var projectId = link.target.id;
			req_process(projectId);
		});//addListener.
		
	}//for
});

function req_process(projectId){
	var httpProxy = new Ext.data.HttpProxy({
		method:'get',
		url:'getProjectRequest.so?projectId='+projectId
	});
	
	var Request = Ext.data.Record.create([
	{name:'id',mapping:0},{name:'userName',mapping:1},
	{name:'description',mapping:2},{name:'role',mapping:3},
	{name:'reqId',mapping:4}]);
	var arrayReader = new Ext.data.ArrayReader({id:0},Request);
	var ds = new Ext.data.Store({reader:arrayReader});
	httpProxy.load(null,arrayReader,callback);

	function callback(result){
		//alert(result);
		if(result != null){
		
			ds.add(result.records);
		}
	}
	function approveReq(){
	
		var reqList = "";
		var ok = requestPanel.getSelectionModel().each(
			function(rec){
				reqList = reqList+","+rec.get('reqId');
				requestPanel.getView().getRow(rec.get('id')).style.backgroundColor='#9CFC7C';
			}
		);
		if(reqList.length > 2)
			reqList = reqList.substring(1);
		RequestForm.form.submit({			
			waitMsg:'正在处理，请稍后',
			waitTitle:'提示',
			url:'approveRequest.so?reqList='+reqList,
			method:'POST',
			success:function(form,action){
				Ext.Msg.alert('提示','处理成功，即将跳转');
				//TODO 
			},
			failure:function(form,action){
				Ext.Msg.alert('提示','处理失败！');
			}
		});//submit.
		
	}
	//TODO
	function rejectReq(){
		var reqList = "";
		var ok = requestPanel.getSelectionModel().each(
			function(rec){
				reqList = reqList+","+rec.get('reqId')
				requestPanel.getView().getRow(rec.get('id')).style.backgroundColor='#FE8870';
			}
		);
		if(reqList.length > 2)
			reqList = reqList.substring(1);
		RequestForm.form.submit({			
			waitMsg:'正在处理，请稍后',
			waitTitle:'提示',
			url:'rejectRequest.so?reqList='+reqList,
			method:'POST',
			success:function(form,action){
				Ext.Msg.alert('提示','拒绝成功，即将跳转');
				//TODO 
			},
			failure:function(form,action){
				Ext.Msg.alert('提示','处理失败！');
			}
		});//submit.
	}

	var requestPanel = new Ext.grid.GridPanel({
		title:'',
		///applyTo:'grid-div',
		width:'auto',
		autoHeight:true,
		frame:false,
		
		store:ds,
		selModel:new Ext.grid.CheckboxSelectionModel(),
		
		columns:[
		new Ext.grid.CheckboxSelectionModel(),
		{header:"ID",width:20,dataIndex:'id',sortable:true},
		{header:"申请人",width:80,dataIndex:'userName',sortable:true},
		{header:"说明",width:100,dataIndex:'description',sortable:true},
		//{header:"场景",autoWidth:true,dataIndex:'scenario',sortable:false},
		{header:"申请角色",autoWidth:true,dataIndex:'role',sortable:false},
		{header:"全局申请ID",autoWidth:true,dataIndex:'reqId',sortable:false}
		]
		
	});	
	function closeWindow(){
		RequestWindow.close();
		window.location.reload();
	}
	var RequestForm  = new Ext.form.FormPanel({
		labelWidth:60,
		width:'auto',
		frame:true,
		labelSeparator:':',
		region:'center',
		
		items:[requestPanel],
		buttons:[
			new Ext.Button({text:"批准",handler:approveReq}),
			new Ext.Button({text:'拒绝',handler:rejectReq}),
			new Ext.Button({text:'关闭窗口',handler:closeWindow})
		]
	})
	var RequestWindow = new Ext.Window({
		contentEI:'window',
		title:'处理请求',
		width : 550,
		height : 300,
		plain:true,
		closeAction:'hide',
		maximizable:true,
		layout:'border',
		closable:false,
		items:[RequestForm]
	});
	RequestWindow.addListener('close',function(){
		window.location.reload();
	});
	RequestWindow.show();
}//function req_process