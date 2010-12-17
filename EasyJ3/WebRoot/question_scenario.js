Ext.onReady(function() {
			Ext.BLANK_IMAGE_URL = './ext-2.3.0/resources/images/default/s.gif';
			Ext.lib.Ajax.defaultPostHeader += '; charset=utf-8';
			var question_scenario = Ext.get('question_scenario');
			if(question_scenario == null) return;
			var scenarioId = Ext.get('scenario_id').dom.value;
			var projectId = Ext.get('project_id').dom.value;
			question_scenario.addListener('click', function() {
						QuestionWin.show();
					});
			var desEditor = new Ext.form.HtmlEditor({
						name : 'description',
						autoHeight : false,
						height : 500,
						width : 510,
						fieldLabel : '问题描述',
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
			var title = new Ext.form.TextField({
						fieldLabel : '标题',
						id : 'title',
						selectOnFocus : true,
						allowBlank : false
					});
			// TODO
			var today = new Date();
			var qkindStore = new Ext.data.SimpleStore({
						proxy : new Ext.data.HttpProxy({
									url : 'getQKind.so?projectId=' + projectId+'&_st='+today.getTime(),
									method : 'post'
								}),
						fields : [{name:'qkindId',mapping:0},{name:'qkindName',mapping:1}]
					});
			var qkind = new Ext.form.ComboBox({
						id : 'qkind',
						allQuery : 'allqkind',
						loadingText : '正在加载类别信息...',
						minChars : 2,
						queryDelay : 300,
						queryParam : 'searchQKind',
						fieldLabel : '问题类别',
						triggerAction : 'all',
						store : qkindStore,
						displayField : 'qkindName',
						valueField:'qkindId',
						hiddenName:'qkindId',
						readOnly:true,
						emptyText:'请选择类别.',
						mode : 'remote'
					});
			var Scenario = Ext.data.Record.create([
			{name:'id',mapping:0},{name:'scenarioName',mapping:1},{name:'scenarioId',mapping:2}
			]);
			var sceReader  = new Ext.data.ArrayReader({id:0},Scenario);
			var scenario_store = new Ext.data.Store({reader:sceReader});
			var scenarioProxy = new Ext.data.HttpProxy({
				method:'get',
				url:'getProjectScenario.so?projectId='+projectId
			});
			scenarioProxy.load(null,sceReader,callback);
			function callback(result){
				if(result != null)
					scenario_store.add(result.records);
			}
			// Choose related scenarios.
			var scenarios = new Ext.grid.GridPanel({
				title:'关联场景',
				width:'auto',
				height:150,
				autoScroll:true,				
				frame:false,
				store:scenario_store,
				selModel:new Ext.grid.CheckboxSelectionModel(),
				columns:[
				new Ext.grid.CheckboxSelectionModel(),
				{header:"ID",width:20,dataIndex:'id',sortable:false},
				{header:"场景名称",width:100,dataIndex:'scenarioName',sortable:false},
				{header:"全局ID",width:20,dataIndex:'scenarioId',sortable:false}
				]				
			});
			var QuestionForm = new Ext.form.FormPanel({
						labelWidth : 60,
						width : 400,
						frame : false,
						labelSeparator : ':',
						region : 'center',
						items : [title, qkind, scenarios,desEditor],
						buttons : [new Ext.Button({
											text : '提交',
											handler : submitQuestion
										}), new Ext.Button({
											text : '重置',
											handler : reset
										})]
					});
			var QuestionWin = new Ext.Window({
						contentEI : 'window',
						title : '提问',
						width : 500,
						height : 500,
						plain : true,
						closeAction : 'hide',
						maximizable : true,
						layout : 'border',
						items : [QuestionForm]
					});
			function submitQuestion() {
				/*Get relatec Scenario list*/
				var scenarioList = "";
				//var dataOk = dataPanel.getSelectionModel().each(function(rec){dataList = dataList + ","+rec.get('dataId');});
				var scenarioOk = scenarios.getSelectionModel().each(function(rec){scenarioList = scenarioList +","+rec.get('scenarioId');});
				if(scenarioList.length>1) scenarioList = scenarioList.substring(1);
				QuestionForm.getForm().submit({
					clientValidation : true,
					waitMsg : '正在更新，请稍候',
					waitTitle : '提示',
					url : 'submitQuestion.so?projectId=' + projectId
							+ '&scenarioId=' + scenarioId + '&scenarioList='+scenarioList,// TODO
					method : 'POST',
					success : function(form, action) {
						Ext.Msg.alert('成功', '提交成功,即将跳转');
						window.location.reload();
					},// success.
					failure : function(form, action) {
						Ext.Msg.alert('失败', '提交失败，请检查输入内容');
					}
				});// submit
			}
			function reset() {
				QuestionForm.getForm().reset();
			}

		});