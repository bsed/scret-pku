Ext.onReady(function() {
	Ext.lib.Ajax.defaultPostHeader += '; charset=utf-8';
	var edit_scenario = Ext.select('.edit_scenario');
	var scenarioId = Ext.get('scenario_id').dom.value;
	var Scenario = Ext.data.Record.create([{
				name : 'id',
				mapping : 0
			}, {
				name : 'description',
				mapping : 1
			}]);
	var arrayReader = new Ext.data.ArrayReader({
				id : 0
			}, Scenario);

	for (var i = 0; i < edit_scenario.getCount(); i++) {		
		edit_scenario.item(i).addListener('click', function(link) {
			var editor = new Ext.form.HtmlEditor({
						name : 'scenarioDes',
						autoHeight : false,
						height : 500,
						width : 510,
						fieldLabel : '场景描述',
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
			var roleId = link.target.name;
			var httpProxy = new Ext.data.HttpProxy({
						method : 'GET',
						url : 'getScenarioDes.so?scenarioId=' + scenarioId
								+ '&roleId=' + roleId
					});
			httpProxy.load(null, arrayReader, callback);
			var editScenarioForm = new Ext.form.FormPanel({
						labelWidth : 60,
						width : 400,
						frame : false,
						labelSeparator : ':',
						region : 'center',
						items : [editor, new Ext.form.Hidden({
											name : 'scenarioId',
											value : scenarioId
										}), new Ext.form.Hidden({
											name : 'roleId',
											value : roleId
										})],
						buttons : [new Ext.Button({
											text : '确定',
											handler : updateDescription
										}), new Ext.Button({
											text : '重置',
											handler : reset
										}),new Ext.Button({
											text:'关闭',
											handler:closeWindow
											})]
					});
			var EditScenarioWin = new Ext.Window({
						contentEI : 'window',
						title : '编辑场景',
						width : 500,
						height : 500,
						plain : true,
						closeAction : 'close',
						closable:false,
						maximizable : true,
						layout : 'border',
						items : [editScenarioForm]
					});
			
			function updateDescription() {
				// 更新编辑内容
				editScenarioForm.getForm().submit({
							clientValidation : true,
							waitMsg : '正在更新，请稍候',
							waitTitle : '提示',
							url : 'updateDesServlet.so',
							method : 'POST',
							success : function(form, action) {
								Ext.Msg.alert('成功', '更新成功,即将跳转');
								window.location.reload();
							},// success.
							failure : function(form, action) {
								Ext.Msg.alert('失败', '添加失败，请检查输入内容');
							}
						});// submit
			}
			function reset() {
				editScenarioForm.getForm().reset();
			}
			
			function closeWindow(){
				EditScenarioWin.close();
				/*
					set the des to 'free'.
				*/
				var requestConfig = {
						url: 'freeDesServlet.so',
						params:{'roleId':roleId,'scenarioId':scenarioId},
						method:'GET',
						success:function(response){							
							window.location.reload();
						},
						failure:function(){
							alert("free the des-lock. FAIL@!"); 
						}
					};
				Ext.Ajax.request(requestConfig);	
				
			}
			function callback(result) {
				// alert(result);
				if (result != null) {
					editor.setValue(result.records[0].get('description'));
					// alert(editor.getValue());
					EditScenarioWin.show();
				}else{
					isFree = false;
					alert("对不起, 该描述已被锁定!请稍候编辑");
				}
			}
		});
	}
});