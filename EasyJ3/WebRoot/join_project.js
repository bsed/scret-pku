/**
 * 一次申请一个角色，加入项目中.
 */
 
 Ext.onReady(function(){
	Ext.lib.Ajax.defaultPostHeader += '; charset=utf-8';
	var join_project = Ext.get('req_join_project');
	if(join_project == null) return;
	join_project.addListener('click',function(){
			JoinprojectWin.show();
	});	
	var projectId = Ext.get('project_id').dom.value;
////////////
	var Role = Ext.data.Record.create([{name:'id',mapping:0},{name:'roleName',mapping:1},
	{name:'roleDescription',mapping:2},{name:'roleId',mapping:3}
	]);
	var roleReader = new Ext.data.ArrayReader({id:0},Role);
	var role_store = new Ext.data.Store({reader:roleReader});
	var roleHttpProxy = new Ext.data.HttpProxy({
		method:'GET',
		url:"getProjectRole.so?projectId="+projectId
		});//TODO: getprojectRole.so!
	roleHttpProxy.load(null,roleReader,role_callback);
	function role_callback(result){
		if(result!=null)
			role_store.add(result.records);
	}
	////////////////////
	var role_sm = new Ext.grid.CheckboxSelectionModel();
	var rolePanel = new Ext.grid.GridPanel({
		//title:'项目角色',
		width:500,
		height:200,
		frame:false,
		store:role_store,
		//sm:role_sm,
		columns:[
			role_sm,
			{header:"ID",width:20,dataIndex:'id',sortable:true},
			{header:"名称",width:80,dataIndex:'roleName',sortable:true},
			{header:"描述",width:100,dataIndex:'roleDescription',sortable:true},
			{header:"全局ID",autoWidth:true,dataIndex:'roleId',sortable:false}
		]
	});
	var joinprojectForm = new Ext.form.FormPanel({
		labelWidth:60,
		width:500,
		frame:true,
		labelSeparator:':',
		region:'center',
		items:[	new Ext.form.Hidden({name:'projectId',value:projectId}),
				rolePanel,
				new Ext.form.TextArea({name:'req_description',fieldLabel:'申请描述',width:400,height:200,grow:false,allowBlank:false,emptyText:'请输入申请描述'})
				],
		buttons:[
			new Ext.Button({
			text:"确定",
			handler:sendJoinRequest}),
			new Ext.Button({
			text:'重置',
			handler:reset})
			]
	});
	function sendJoinRequest(){
		var roleId = "";
		var roleOk = rolePanel.getSelectionModel().each(function(rec){roleId = rec.get('roleId');});
		//提交请求信息
		joinprojectForm.getForm().submit({clientValidation:true,waitMsg:'正在更新，请稍候',waitTitle:'提示',
		url:'sendJoinRequest.so?roleId='+roleId,method:'POST',
		success:function(form,action){
			Ext.Msg.alert('成功','更新成功,即将跳转');
			window.location.reload();
			//TODO
			},//success.
		failure:function(form,action){
			Ext.Msg.alert('失败','添加失败，请检查输入内容');
		}});//submit
	}
	function reset(){
		joinprojectForm.getForm().reset();
	}
	var JoinprojectWin = new Ext.Window({
		contentEI:'window',
		title:'项目角色',
		width:500,
		height:500,
		plain:true,
		closeAction:'hide',
		maximizable:true,
		layout:'border',
		items:[joinprojectForm]		
	});

});
