Ext.onReady(function(){
	
	var projectId =  Ext.get('project_id').dom.value;
	var scenarioId = Ext.get('scenario_id').dom.value;
	var pick_solution = Ext.get('pick_solution');
	if(pick_solution == null) return;
	pick_solution.addListener('click',function(){PickSolutionWin.show()});
	///////////////////
	var Solution = Ext.data.Record.create([
			{name:'id',mapping:0},{name:'title',mapping:1},{name:'voteNum',mapping:2},{name:'solutionId',mapping:3}
			]);
	var solutionReader  = new Ext.data.ArrayReader({id:0},Solution);
	var Solution_store = new Ext.data.Store({reader:solutionReader});
	var SolutionProxy = new Ext.data.HttpProxy({
				method:'get',
				url:'getScenarioSolution.so?scenarioId='+scenarioId
			});
	SolutionProxy.load(null,solutionReader,callback);
			function callback(result){
				if(result != null)
					Solution_store.add(result.records);
			}
			// Choose related Solutions.
			var Solutions = new Ext.grid.GridPanel({
				//title:'解决方案',
				width:'auto',
				autoHeight:true,
				frame:false,
				store:Solution_store,
				selModel:new Ext.grid.CheckboxSelectionModel(),
				columns:[
				new Ext.grid.CheckboxSelectionModel(),
				{header:"ID",width:20,dataIndex:'id',sortable:false},
				{header:"方案标题",width:100,dataIndex:'title',sortable:false},
				{header:"得票数",width:100,dataIndex:'voteNum',sortable:true},
				{header:"全局ID",width:40,dataIndex:'solutionId',sortable:false}
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
		items:[new Ext.form.Hidden({name : 'scenarioId',value : scenarioId}),Solutions],
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