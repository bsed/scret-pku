
Ext.onReady(function(){
	Ext.lib.Ajax.defaultPostHeader += '; charset=utf-8';
	var add_domain = Ext.get('add_domain');
	add_domain.addListener('click',function(){
									AddDomainWindow.show()
									});
	
	var domainForm = new Ext.form.FormPanel({
		labelWidth:60,
		width:300,
		frame:true,
		labelSeparator:':',
		region:'center',
		items:[
			new Ext.form.TextField({
			fieldLabel:'领域名',
			name:'domainName',
			allowBlank:false,
			emptyText:'请输入领域名称'
			}),
			new Ext.form.TextArea({
	
			fieldLabel:'领域描述',
			autoHeight:true,
			width:250,
			grow:true,
			name:'domainDescription',
			allowBlank:false,
			emptyText:'请输入领域描述'
			})
			],
		buttons:[
			new Ext.Button({
			text:'确定',
			handler:addDomain
			}),
			new Ext.Button({
			text:'重置',
			handler:reset
			})
		]
	});//domainForm
	var AddDomainWindow = new Ext.Window({
		width : 300,
		height : 150,
		contentEI:'window',
		title:'新建领域',
		plain:true,
		closeAction:'hide',
		maximizable:true,
		layout:'border',
		items:[domainForm]
	});
	function reset(){
		domainForm.form.reset();
		
	}
	
	function addDomain(){
		domainForm.form.submit({
			clientValidation:true,
			waitMsg:'正在添加，请稍后',
			waitTitle:'提示',
			url:'addDomainServlet.so',
			method:'POST',
			success:function(form,action){
				Ext.Msg.alert('提示','添加成功，即将跳转');
				window.location.reload(); 
			},
			failure:function(form,action){
				Ext.Msg.alert('提示','添加失败，请检查收入数据');
			}
		});//submit.
	}//addDomain()
	}//function().	
);//onReady
