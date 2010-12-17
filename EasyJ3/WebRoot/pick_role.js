/**
 * 管理员角色 
 *   project.jsp 
 *    直接选择自己的角色即可 ；
 *    其他成员，则需要提出申请。
 * 
 */
 
 Ext.onReady(function(){
	Ext.lib.Ajax.defaultPostHeader += '; charset=utf-8';
	var pick_role = Ext.get('pick_role');
	if(pick_role == null) return;
	pick_role.addListener('click',function(){
			PickRoleWin.show();
	});	
	var projectId = Ext.get('project_id').dom.value;
////////////
	var Role = Ext.data.Record.create([{name:'id',mapping:0},{name:'roleName',mapping:1},
	{name:'roleDescription',mapping:2},{name:'roleId',mapping:3}
	]);
	var roleReader = new Ext.data.ArrayReader({id:0},Role);
	var roleHttpProxy = new Ext.data.HttpProxy({
		method:'GET',
		url:"getProjectRole.so?projectId="+projectId
		});
	
	var role_store = new Ext.data.Store({
		reader:roleReader,
		proxy: roleHttpProxy,
		autoLoad:true
		});
	role_store.load();
	////////////////////
	var role_sm = new Ext.grid.CheckboxSelectionModel();
	var rolePanel = new Ext.grid.GridPanel({
		//title:'场景角色',
		width:500,
		height:200,
		frame:false,
		store:role_store,
		// sm:role_sm, //一次选择一个角色.
		columns:[
			role_sm,
			{header:"ID",width:20,dataIndex:'id',sortable:true},
			{header:"名称",width:80,dataIndex:'roleName',sortable:true},
			{header:"描述",width:100,dataIndex:'roleDescription',sortable:true},
			{header:"全局ID",autoWidth:true,dataIndex:'roleId',sortable:false}
		]
	});
	var pickRoleForm = new Ext.form.FormPanel({
		labelWidth:60,
		width:500,
		frame:true,
		labelSeparator:':',
		region:'center',
		items:[	new Ext.form.Hidden({name:'projectId',value:projectId}),
				new Ext.form.Hidden({name:'isCreator',value:'1'}),
		        rolePanel],
		buttons:[
			new Ext.Button({text:"确定",	handler:pickRole}),
			new Ext.Button({text:'重置',	handler:reset})]
	});
	function pickRole(){
		var roleId = "";
		var roleOk = rolePanel.getSelectionModel().each(function(rec){roleId = rec.get('roleId');});
		
		pickRoleForm.getForm().submit({clientValidation:true,waitMsg:'正在更新，请稍候',waitTitle:'提示',
		url:'pickRole.so?roleId='+roleId,method:'POST',
		success:function(form,action){
			Ext.Msg.alert('成功','更新成功,即将跳转');
			window.location.reload();
			},//success.
		failure:function(form,action){
			Ext.Msg.alert('失败','添加失败，请检查输入内容');
		}});//submit
	}
	function reset(){
		pickRoleForm.getForm().reset();
	}
	var PickRoleWin = new Ext.Window({
		contentEI:'window',
		title:'项目角色',
		width:500,
		height:500,
		plain:true,
		closeAction:'hide',
		maximizable:true,
		layout:'border',
		items:[pickRoleForm]		
	});

});
