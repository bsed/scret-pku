Ext.onReady(function(){
	Ext.lib.Ajax.defaultPostHeader += '; charset=utf-8';
	var add_project = Ext.get('add_project');
	add_project.addListener('click',function(){AddProjectWindow.show()});
	
	/////
	var today = new Date();
	var httpProxy = new Ext.data.HttpProxy({
		method:'get',
		url:'getDomainList.so?st='+today.getTime()
	});
	
	
	var Domain = Ext.data.Record.create([
	{name:'id',mapping:0},{name:'domainName',mapping:1},
	{name:'domainDescription',mapping:2},{name:'domainId',mapping:3}]);
	var arrayReader = new Ext.data.ArrayReader({id:0},Domain);
	var domainStore = new Ext.data.Store({reader:arrayReader});
	httpProxy.load(null,arrayReader,callback);
	function callback(result){
		//alert(result);
		if(result != null){
			domainStore.add(result.records);
		}
	}				
	////

	var cbsm = new Ext.grid.CheckboxSelectionModel();
	//domainPanel: 所属领域
	var domainPanel = new Ext.grid.GridPanel({
		title:'选择所属领域',
		///applyTo:'grid-div',
		width:400,
		height:300,
		frame:true,
		store:domainStore,
		//sm:cbsm,
		columns:[
		cbsm,	
		{header:"ID",width:20,dataIndex:'id',sortable:true},
		{header:"名称",width:80,dataIndex:'domainName',sortable:true},
		{header:"描述",width:100,dataIndex:'domainDescription',sortable:true},
		{header:"全局ID",autoWidth:true,dataIndex:'domainId',sortable:false}
		]
	});
	var projectForm = new Ext.form.FormPanel({
		labelWidth:60,
		width:300,
		frame:true,
		labelSeparator:':',
		region:'center',
		items:[
			new Ext.form.TextField({
			fieldLabel:'项目名称',
			name:'projectName',
			allowBlank:false,
			emptyText:'请输入项目名称'
			}),
			new Ext.form.TextArea({
			
			fieldLabel:'项目描述',			
			//autoHeight:true,
			width:250,	
			height:100,		
			name:'projectDescription',
			grow:true,
			allowBlank:false,
			emptyText:'请输入项目描述'
			}),
			domainPanel //所属领域
			],
		buttons:[
			new Ext.Button({
			text:'确定',
			handler:addProject
			}),
			new Ext.Button({
			text:'重置',
			handler:reset
			})
		]
	});//domainForm
	var AddProjectWindow = new Ext.Window({
		contentEI:'window',
		
		title:'新建项目',
		plain:true,
		width:400,
		height:450,
		closeAction:'hide',
		maximizable:true,
		layout:'border',
		items:[projectForm]
	});
	function reset(){
		projectForm.getForm().reset();
	}
	function addProject(){
		var domainId  = "-1"; //default : other domain.
		domainPanel.getSelectionModel().each(function(rec){domainId = rec.get('domainId');});
		projectForm.getForm().submit({
			clientValidation:true,
			waitMsg:'正在添加，请稍后',
			waitTitle:'提示',
			url:'addProjectServlet.so?domainId='+domainId,
			method:'Post',
			success:function(form,action){
				Ext.Msg.alert('提示','添加成功，即将跳转');
				window.location.reload();
			},
			failure:function(form,action){
				Ext.Msg.alert('提示','添加失败，请检查收入数据');
			}
		});
	}
	
});