
	Ext.onReady(function(){
		
		//Ext.QuickTips.init();
		var loginbtn = Ext.get('login');
		if(loginbtn != null) 
			loginbtn.addListener('click',function(){Window.show()});
		
		var registerbtn=Ext.get('register');
		if(registerbtn != null)
			registerbtn.addListener('click',function(){RegisterW.show()});
		var loginForm = new Ext.form.FormPanel({
			
									labelWidth:60,
									width:230,
									frame:true,
									labelSeparator:': ',
									region:'center',
									items:[
										new Ext.form.TextField({
											fieldLabel:'用户名',
											name:'userName',
											allowBlank:false,
											vtype:'email',
											emptyText:'请输入常用邮箱',
											}),
										new Ext.form.TextField({
											fieldLabel:'密码',
											name:'password',
											inputType:'password',
											allowBlank:false
											})
										],
									buttons:[
										new Ext.Button({
											text:'登录',
											handler:login
										}),
										new Ext.Button({
											text:'重置',
											handler:reset
										})
										]
										
									});//FormPanel;
		var Window = new Ext.Window({
						contentEI:'window',
						title : '登录',
						width : 250,
						height : 150,
						plain : true,
						closeAction : 'hide',// 关闭窗口
						maximizable : false,// 最大化控制 值为true时可以最大化窗体
						layout:'border',
						items : [
									loginForm
						]			
					});
		var registerForm = new Ext.form.FormPanel({
									labelWidth:60,
									width:230,									
									frame:true,
									labelSeparator:': ',
									region:'center',
									items:[
										new Ext.form.TextField({
											fieldLabel:'用户名',
											name:'userName',
											allowBlank:false,
											vtype:'email',
											emptyText:'请输入常用邮箱',
											}),
										new Ext.form.TextField({
											fieldLabel:'密码',
											name:'password',
											inputType:'password',
											allowBlank:false
											}),
										new Ext.form.TextField({
											fieldLabel:'确认密码',
											name:'password2',
											inputType:'password',
											allowBlank:false
											})
										],
									buttons:[
										new Ext.Button({
											text:'注册',
											handler:register
										}),
										new Ext.Button({
											text:'重置',
											handler:registerReset
										})
										]
										
									});//FormPanel
		
		var RegisterW = new Ext.Window({
					contentEI:'window',
					title:'注册',
					width:250,
					height:200,					
					plain:true,
					closeAction:'hide',
					maximizable:false,
					layout:'border',
					items:[registerForm]
					});
					
					
		function login(){
			loginForm.form.submit({
				clientValidation:true,
				waitMsg:'正在登录系统请稍候',
				waitTitle:'提示',
				url:'loginServlet.so',
				method:'GET',
				success:function(form,action){
					Ext.Msg.alert('提示','系统登录成功！');
					Window.close();
					//redirect to the Login.jsp.
					//window.location.href='Login.jsp';
					window.location.reload();
					},
				failure:function(form,action){
					Ext.Msg.alert('提示','系统登录失败,原因:'+ action.failureType);
					}
			});
		}
		function reset(){
			loginForm.form.reset();			
		}
		function registerReset(){
			registerForm.form.reset();
		}	
		function register(){
			registerForm.form.submit({
				clientValidation:true,
				waitMsg:'正在注册，请稍候',
				waitTitle:'提示',
				url:'registerServlet.so',
				method:'GET',
				success:function(form,action){
					Ext.Msg.alert('提示','注册成功,请登录!');
					window.location.href="forward.jsp";
					},
				failure:function(form,action){
					Ext.Msg.alert('提示','注册失败,原因:'+action.failureType);
					}
				});
		}//register();
		
		
		
		});//onReady.		

	
