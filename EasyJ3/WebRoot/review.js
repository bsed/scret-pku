Ext.onReady(function() {
	var review = Ext.get('review');
	if(review == null) return;
	review.addListener('click',function(){
		ReviewWin.show();	
	});
	var scenarioId = Ext.get('scenario_id').dom.value;
	
	var editor = new Ext.form.HtmlEditor({
						name : 'review_scenario',
						autoHeight : false,
						height : 500,
						width : 510,
						fieldLabel : '评论内容',
						createLinkText : '创建超链接',
						defaultLinkValue : "http://www.",
						enableAlignments : true,
						enableColors : true,
						enableFont : true,
						enableFontSize : true,
						enableFormat : true,
						enableLinks : true,
						enableLists : true,
						enableSourceEdit : true,
						fontFamilies : ['宋体', '隶书', '黑体']
					});
	var Role = Ext.data.Record.create([{name:'id',mapping:0},{name:'roleName',mapping:1},
	{name:'roleDescription',mapping:2},{name:'roleId',mapping:3}
	]);
	var roleReader = new Ext.data.ArrayReader({id:0},Role);
	var role_store = new Ext.data.Store({reader:roleReader});
	var roleHttpProxy = new Ext.data.HttpProxy({
		method:'GET',
		url:"getScenarioRole.so?scenarioId="+scenarioId
		}
	);
	roleHttpProxy.load(null,roleReader,role_callback);
	function role_callback(result){
		if(result != null){
			role_store.add(result.records);
			rolePanel.getStore().add(result.records);
		}
	}
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
	
	var reviewForm = new Ext.form.FormPanel({
		labelWidth : 60,
		width : 400,
		frame : false,
		labelSeparator : ':',
		region : 'center',
		items : [rolePanel,editor, new Ext.form.Hidden({
							name : 'scenarioId',
							value : scenarioId
						})],
		buttons : [new Ext.Button({
							text : '确定',
							handler : insertReview
						}), new Ext.Button({
							text : '重置',
							handler : reset
						})]
	});
	var ReviewWin =  new Ext.Window({
						contentEI : 'window',
						title : '场景评论',
						width : 500,
						height : 500,
						plain : true,
						closeAction : 'hide',
						maximizable : true,
						layout : 'border',
						items : [reviewForm]
	});
	
	function insertReview(){
		var roleList = "";
		var roleOk = rolePanel.getSelectionModel().each(function(rec){roleList=roleList+","+rec.get('roleId');});
		roleList = roleList.substring(1);
		//alert('RoleList='+roleList);
		reviewForm.getForm().submit({clientValidation : true,
							waitMsg : '正在插入评论，请稍候',
							waitTitle : '提示',
							url : 'reviewServlet.so?roleList='+roleList,
							method : 'POST',
							success : function(form, action) {								
								window.location.reload();
							},// success.
							failure : function(form, action) {
								Ext.Msg.alert('失败', '添加失败，请检查输入内容');
							}
		});
	}//insertReview() 
	function reset(){
		reviewForm.getForm().reset();
		}
});