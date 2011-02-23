Ext.onReady(function(){
	
	var projectId =  Ext.get('project_id').dom.value;
	var scenarioId = Ext.get('scenario_id').dom.value;
	var pick_solution = Ext.get('pick_solution');
	if(pick_solution == null) return;
	pick_solution.addListener('click',function(){PickSolutionWin.show()});
	///////////////////
	var Solution = Ext.data.Record.create([
			{name:'id',mapping:0},{name:'title',mapping:1},{name:'voteNum',mapping:2},{name:'solutionId',mapping:3},
			{name:'qNum',mapping:4}
			]);
	var solutionReader  = new Ext.data.ArrayReader({id:0},Solution);
	var SolutionProxy = new Ext.data.HttpProxy({
				method:'get',
				url:'getScenarioSolution.so?scenarioId='+scenarioId
			});
	var Solution_store = new Ext.data.Store({
		reader:solutionReader,
		proxy: SolutionProxy,
		autoLoad:true
		});
	/**
	TODO: 
	     在select或deselect的时候，要能够找到该方案关联的问题，从而明确告诉用户，当前方案覆盖了哪些问题。
	*/	
	var csm = new Ext.grid.CheckboxSelectionModel({
				    listeners:{
				      'rowselect':function(sm,rowIndex,record){
				        console.log('rowselect',rowIndex)
				      },
				      'rowdeselect':function(sm,rowIndex,record){
				        console.log('rowdeselect',rowIndex)
				      },
				      'selectionchange':function(sm){
				        console.log('selectionchange',sm.getSelections().length);
				      }
				    }
				    });
		
	// Choose related Solutions.
	var Solutions = new Ext.grid.GridPanel({
		title:'解决方案',
		width:'auto',
		autoHeight:true,
		frame:false,
		store:Solution_store,
		selModel:csm,
		columns:[
		csm,
		{header:"ID",width:20,dataIndex:'id',sortable:false},
		{header:"方案标题",width:100,dataIndex:'title',sortable:false},
		{header:"得票数",width:100,dataIndex:'voteNum',sortable:true},
		{header:"全局ID",width:40,dataIndex:'solutionId',sortable:false},
		{header:"解决问题数",widht:100,dataIndex:'qNum',sortable:true}
		]
		
	});
	///////////////////////////////////////////////
		var Question = Ext.data.Record.create([
			{name:'id',mapping:0},{name:'questionTitle',mapping:1},{name:'questionId',mapping:3}
			]);
	var questionReader  = new Ext.data.ArrayReader({id:0},Question);
	var QuestionProxy = new Ext.data.HttpProxy({
				method:'get',
				url:'getScenarioQuestion.so?scenarioId='+scenarioId
			});
	var Question_store = new Ext.data.Store({
		reader:questionReader,
		proxy: QuestionProxy,
		autoLoad:true
		});
	var Questions = new Ext.grid.GridPanel({
		title:'问题',
		width:'auto',
		autoHeight:true,
		frame:false,
		store:Question_store,
		//selModel:new Ext.grid.CheckboxSelectionModel(),
		columns:[
		//new Ext.grid.CheckboxSelectionModel(),
		{header:"ID",width:20,dataIndex:'id',sortable:false},
		{header:"问题标题",width:100,dataIndex:'questionTitle',sortable:false},		
		{header:"全局ID",width:40,dataIndex:'questionId',sortable:false}
		]
	});
	//////////////////
	
	var SolutionForm = new Ext.form.FormPanel({
		labelWidth:60,
		width:300,
		frame:true,
		labelSeparator:':',
		region:'center',
		autoScroll:true,
		items:[new Ext.form.Hidden({name : 'scenarioId',value : scenarioId}),Questions,Solutions],
		buttons:[new Ext.Button({text : '确定',handler : pickSolution
										}), new Ext.Button({
											text : '重置',
											handler : reset
										})]
	});
	function pickSolution(){
			/**Just get one solution id TODO: Change to  radioSelectionModel**/
				var SolutionList = "";
				var SolutionOk = Solutions.getSelectionModel().each(function(rec){SolutionList = rec.get('solutionId');});
				SolutionForm.getForm().submit({
					clientValidation : true,
					waitMsg : '正在更新，请稍候',
					waitTitle : '提示',
					url : 'pickSolution.so?solutionId='+ SolutionList,
					method : 'GET',
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
	var PickSolutionWin = new Ext.Window({
						contentEI : 'window',
						title : '确定解决方案',
						width : 500,
						height : 500,
						plain : true,						
						closeAction : 'hide',
						maximizable : false,
						layout : 'border',
						items : [SolutionForm]
					});
	
});