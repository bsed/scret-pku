

	//I added the following code before onready function
Ext.override(Ext.grid.GridView, {
    afterRender: function(){
        this.mainBody.dom.innerHTML = this.renderRows();
        this.processRows(0, true);
        if(this.deferEmptyText !== true){
            this.applyEmptyText();
        }
        this.fireEvent("viewready", this);//new event
    }   
});
	
Ext.onReady(function(){
	
	Ext.lib.Ajax.defaultPostHeader += '; charset=utf-8';
	var projectId =  Ext.get('project_id').dom.value;
	var scenarioId = Ext.get('scenario_id').dom.value;
	var make_solution = Ext.get('make_solution');
	if(make_solution  == null) return;
	make_solution.addListener('click',function(){
			MakeSolutionWin.show();
		}
	);
	///////////////////
	var Scenario = Ext.data.Record.create([
			{name:'id',mapping:0},{name:'scenarioName',mapping:1},{name:'scenarioId',mapping:2}
			]);
			var sceReader  = new Ext.data.ArrayReader({id:0},Scenario);
			var scenarioProxy = new Ext.data.HttpProxy({
				method:'get',
				url:'getProjectScenario.so?projectId='+projectId
			});
			var scenario_store = new Ext.data.Store({
				reader:sceReader,
				proxy:scenarioProxy,
				autoLoad:true,
				/*listeners:{
					load:callback
					}*/
				});
			scenario_store.load();
			var cbs = new Ext.grid.CheckboxSelectionModel();
			// Choose related scenarios.
			var scenarios = new Ext.grid.GridPanel({
				title:'关联场景',
				width:'auto',
				//autoHeight:true,
				height:150,
				autoScroll:true,
				frame:false,
				store:scenario_store,
				selModel:cbs,
				columns:[
				cbs,
				{header:"ID",width:20,dataIndex:'id',sortable:false},
				{header:"场景名称",width:100,dataIndex:'scenarioName',sortable:false},
				{header:"全局ID",width:40,dataIndex:'scenarioId',sortable:false}
				]
				
			});
		
		 scenarios.getView().on('viewready',function(){
		 			var store = scenarios.getStore();
					var data = store.data.items;
					for(var i = 0 ;i<data.length;i++){
						var sid = data[i].data.scenarioId;
						if(sid == scenarioId) {
							scenarios.getSelectionModel().selectRow(i);
						}
					}
		 });
		
		
	//////////////////
	var Question  = Ext.data.Record.create([{name:'id',mapping:0},{name:'questionTitle',mapping:1},{name:'qKindName',mapping:2},{name:'questionId',mapping:3}]);
	var questionReader = new Ext.data.ArrayReader({id:0},Question);
	var question_store = new Ext.data.Store({reader:questionReader});
	var today = new Date();
	var questionProxy = new Ext.data.HttpProxy({
		method:'get',
		url:'getProjectQuestion.so?projectId='+projectId +'&_st='+today.getTime() //TOOD serlvet.
	});
	questionProxy.load(null,questionReader,function(result){if(result!=null) question_store.add(result.records);});
	
	var questionGrid = new Ext.grid.GridPanel({
				title:'关联问题',
				width:'auto',
				height:150,
				//autoHeight:true,
				autoScroll:true,
				frame:false,
				store:question_store,
				selModel:new Ext.grid.CheckboxSelectionModel(),
				columns:[
				new Ext.grid.CheckboxSelectionModel(),
				{header:"ID",width:20,dataIndex:'id',sortable:false},
				{header:"问题",width:200,dataIndex:'questionTitle',sortable:false},
				{header:"类别",width:100,dataIndex:'qKindName',sortable:false},
				{header:"全局ID",width:40,dataIndex:'questionId',sortable:false}
				]
				
	});
	questionGrid.getView().on('viewready',function(){
		 			var store = questionGrid.getStore();
					var data = store.data.items;
					for(var i = 0 ;i<data.length;i++){
						/*
						TODO: 得到 当前场景的问题，并进行关联；比较困难，以后做.
						*/
					}
		});
	var SolutionForm = new Ext.form.FormPanel({
		labelWidth:60,
		width:300,
		frame:true,
		labelSeparator:':',
		region:'center',
		items:[
		new Ext.form.TextField({
			fieldLabel:'题目',
			name:'title',
			allowBlank:false,
			emptyText:'请输入题目'
			}),
		questionGrid,
		scenarios,
		
		new Ext.form.TextArea({
			
			fieldLabel:'方案描述',
			width:250,	
			height:300,		
			name:'description',
			grow:true,
			allowBlank:false,
			emptyText:'请输入方案描述'
			})
		],
		buttons:[new Ext.Button({text : '提交',handler : submitSolution
										}), new Ext.Button({
											text : '重置',
											handler : reset
										})]
	});
	function submitSolution(){
		/*Get relatec Scenario list*/
				var scenarioList = "";
				var scenarioOk = scenarios.getSelectionModel().each(function(rec){scenarioList = scenarioList +","+rec.get('scenarioId');});
				if(scenarioList.length>1) scenarioList = scenarioList.substring(1);
				
				var questionList = "";
				var questionOk = questionGrid.getSelectionModel().each(function(rec){questionList= questionList+","+rec.get('questionId');});
				if(questionList.length >1 ) questionList = questionList.substring(1);
				SolutionForm.getForm().submit({
					clientValidation : true,
					waitMsg : '正在更新，请稍候',
					waitTitle : '提示',
					url : 'submitSolution.so?projectId=' + projectId+'&scenarioId='+scenarioId
							+ '&questionList=' + questionList + '&scenarioList='+scenarioList,// TODO
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
	function reset(){
		SolutionForm.getForm().reset();
	}
	var MakeSolutionWin = new Ext.Window({
						contentEI : 'window',
						title : '解决方案',
						width : 500,
						height : 500,
						plain : true,
						closeAction : 'hide',
						maximizable : true,
						layout : 'border',
						items : [SolutionForm]
					});
	
});