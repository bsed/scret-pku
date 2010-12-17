/*
	project.jsp 
	   add_role.
*/
Ext.onReady(function(){

	var projectId = Ext.get('project_id').dom.value;
	var add_role = Ext.get('add_role');
	if(add_role == null) return;
	add_role.addListener('click',function(){AddRoleWindow.show()});
	var dataForm = new Ext.form.FormPanel({
		labelWidth:60,
		width:300,
		frame:true,
		labelSeparator:':',
		region:'center',
		items:[
			new Ext.form.TextField({
			fieldLabel:'角色名称',
			name:'roleName',
			allowBlank:false,
			emptyText:'请输入角色名称'
			}),
			new Ext.form.TextArea({
			
			fieldLabel:'角色描述',			
			//autoHeight:true,
			width:250,	
			height:300,		
			name:'roleDescription',
			grow:true,
			allowBlank:false,
			emptyText:'请输入角色描述'
			})
			],
		buttons:[
			new Ext.Button({
			text:'确定',
			handler:addRole
			}),
			new Ext.Button({
			text:'重置',
			handler:reset
			})
		]
	});//domainForm
	var AddRoleWindow = new Ext.Window({
		contentEI:'window',
		
		title:'新建角色',
		plain:true,
		width:350,
		height:200,
		closeAction:'hide',
		maximizable:true,
		layout:'border',
		items:[dataForm]
	});
	function reset(){
		dataForm.getForm().reset();
	}
	function addRole(){
		dataForm.getForm().submit({
			clientValidation:true,
			waitMsg:'正在添加，请稍后',
			waitTitle:'提示',
			url:'addRoleServlet.so?projectId='+projectId,
			method:'POST',
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