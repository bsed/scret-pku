//TODO 点击确定以后要实实在在添加到数据库中/周二争取完成这个任务+场景编辑页面
Ext.onReady(function(){

	var projectId = Ext.get('project_id').dom.value;
	var add_scenario = Ext.get('add_scenario');
	if(add_scenario == null) return;
	add_scenario.addListener('click',function(){				
									AddScenarioWindow.show()
		
		
		
									});
									
		var Role = Ext.data.Record.create([{name:'id',mapping:0},{name:'roleName',mapping:1},
	{name:'roleDescription',mapping:2},{name:'roleId',mapping:3}
	]);
	var roleReader = new Ext.data.ArrayReader({id:0},Role);
	var role_store = new Ext.data.Store({reader:roleReader});
	var roleHttpProxy = new Ext.data.HttpProxy({
		method:'GET',
		url:"getProjectRole.so?projectId="+projectId
		}
	);
	roleHttpProxy.load(null,roleReader,role_callback);
	function role_callback(result){
		if(result != null){
			rolePanel.getStore().add(result.records);
//			role_store.add(result.records);
		}
	}
	
	var httpProxy = new Ext.data.HttpProxy({
		method:'GET',
		url:'getProjectData.so?projectId='+projectId
	});
	var Data = Ext.data.Record.create([
	{name:'id',mapping:0},{name:'dataName',mapping:1},
	{name:'dataDescription',mapping:2},{name:'dataId',mapping:3}]);
	var arrayReader = new Ext.data.ArrayReader({id:0},Data);
	var ds = new Ext.data.Store({reader:arrayReader});
	httpProxy.load(null,arrayReader,callback);
	function callback(result){
		//alert(result);
		if(result != null){
			dataPanel.getStore().add(result.records);
			//alert(result.records[0].get('dataName'));
			//ds.add(result.records);
			
		}
	}				
	var sm  = new Ext.grid.CheckboxSelectionModel();
	var dataPanel = new Ext.grid.GridPanel({
		title:'场景数据',
		///applyTo:'grid-div',
		width:400,
		height:200,
		frame:true,
		store:ds,
		sm:sm,
		columns:[
		sm,	
		{header:"ID",width:20,dataIndex:'id',sortable:true},
		{header:"名称",width:80,dataIndex:'dataName',sortable:true},
		{header:"描述",width:100,dataIndex:'dataDescription',sortable:true},
		{header:"全局ID",autoWidth:true,dataIndex:'dataId',sortable:false}
		]
	});	

	var role_sm = new Ext.grid.CheckboxSelectionModel();
	var rolePanel = new Ext.grid.GridPanel({
		title:'场景角色',
		width:400,
		height:200,
		frame:false,
		store:role_store,
		sm:role_sm,
		columns:[
			role_sm,
			{header:"ID",width:20,dataIndex:'id',sortable:true},
			{header:"名称",width:80,dataIndex:'roleName',sortable:true},
			{header:"描述",width:100,dataIndex:'roleDescription',sortable:true},
			{header:"全局ID",autoWidth:true,dataIndex:'roleId',sortable:false}
		]
	});
	var scenarioForm = new Ext.form.FormPanel({
		labelWidth:60,
		width:480,
		frame:true,
		labelSeparator:':',
		region:'center',
		items:[
			new Ext.form.TextField({
			width:335,
			fieldLabel:'场景名',
			name:'scenarioName',
			allowBlank:false,
			emptyText:'请输入场景名称'
			}),
			dataPanel,
			rolePanel
			],
		buttons:[
			new Ext.Button({
			text:'确定',
			handler:addScenario
			}),
			new Ext.Button({
			text:'重置',
			handler:reset
			})
		]
	});//scenarioForm

	var AddScenarioWindow = new Ext.Window({
		contentEI:'window',
		title:'新建场景',
		width : 500,
		height : 500,
		plain:true,
		closeAction:'hide',
		maximizable:true,
		layout:'border',
		items:[scenarioForm]
	});
	function reset(){
		scenarioForm.getForm().reset();
	}
	/*click button[确定]*/
	function addScenario(){
		//将选择的data 和 role 附加到 url参数列表中 :dataPanel and rolePanel
		var dataList = "";
		var dataOk = dataPanel.getSelectionModel().each(function(rec){dataList = dataList + ","+rec.get('dataId');});
		dataList  = dataList.substring(1);
		//alert('DataList='+dataList);
		var roleList = "";
		var roleOk = rolePanel.getSelectionModel().each(function(rec){roleList=roleList+","+rec.get('roleId');});
		roleList = roleList.substring(1);
		//alert('RoleList='+roleList);
		scenarioForm.form.submit({
			clientValidation:true,
			waitMsg:'正在添加，请稍后',
			waitTitle:'提示',
			url:'addScenarioServlet.so?roleList='+roleList+'&dataList='+dataList+'&projectId='+projectId,
			method:'Post',
			success:function(form,action){
				Ext.Msg.alert('提示','添加成功，即将跳转');
				window.location.reload();
			},
			failure:function(form,action){
				Ext.Msg.alert('提示','添加失败，请检查收入数据');
			}
		});//submit.
	}//addScenario()
						
									//AddScenarioWindow.show()
	}//function().	
);//onReady