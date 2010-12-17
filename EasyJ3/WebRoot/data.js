Ext.onReady(function(){

	var projectId = Ext.get('project_id').dom.value;
	var add_data = Ext.get('add_data');
	if(add_data == null) return;
	add_data.addListener('click',function(){AddDataWindow.show()});
	var dataForm = new Ext.form.FormPanel({
		labelWidth:60,
		width:300,
		frame:true,
		labelSeparator:':',
		region:'center',
		items:[
			new Ext.form.TextField({
			fieldLabel:'数据名称',
			name:'dataName',
			allowBlank:false,
			emptyText:'请输入数据名称'
			}),
			new Ext.form.TextArea({
			
			fieldLabel:'数据描述',			
			//autoHeight:true,
			width:250,	
			height:300,		
			name:'dataDescription',
			grow:true,
			allowBlank:false,
			emptyText:'请输入数据描述'
			})
			],
		buttons:[
			new Ext.Button({
			text:'确定',
			handler:addData
			}),
			new Ext.Button({
			text:'重置',
			handler:reset
			})
		]
	});//domainForm
	var AddDataWindow = new Ext.Window({
		contentEI:'window',
		
		title:'新建数据',
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
	function addData(){
		dataForm.getForm().submit({
			clientValidation:true,
			waitMsg:'正在添加，请稍后',
			waitTitle:'提示',
			url:'addDataServlet.so?projectId='+projectId,
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