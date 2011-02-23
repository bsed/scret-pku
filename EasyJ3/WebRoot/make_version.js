Ext.onReady(function(){
	
	
	
	var make_version = Ext.get('make_version');
	if(make_version == null) return;
	var scenarioId = Ext.get('scenario_id').dom.value;
    var Question  = Ext.data.Record.create([
    	{name:'id',mapping:0},{name:'questionTitle',mapping:1},
    		{name:'qKindName',mapping:2},{name:'questionId',mapping:3},
    		{name:'voteNum',mapping:4}]);
	var questionReader = new Ext.data.ArrayReader({id:0},Question);
	var today = new Date();
	var questionProxy = new Ext.data.HttpProxy({
		method:'get',
		url:'getScenarioQuestion.so?scenarioId='+scenarioId +'&_st='+today.getTime() //TODO获得综合票数.
	});
	var question_store = new Ext.data.Store({reader:questionReader,autoLoad:true,proxy:questionProxy});
	//question_store.load();
	var csm =  new Ext.grid.CheckboxSelectionModel();
	var questionGrid = new Ext.grid.GridPanel({
				
				width:'auto',
				height:150,
				//autoHeight:true,
				autoScroll:true,
				frame:false,
				store:question_store,
				selModel:csm,
				columns:[
				csm,
				{header:"ID",width:20,dataIndex:'id',sortable:false},
				{header:"问题",width:200,dataIndex:'questionTitle',sortable:false},
				{header:"类别",width:100,dataIndex:'qKindName',sortable:false},
				{header:"全局ID",width:40,dataIndex:'questionId',sortable:false},
				{header:"票数",width:40,dataIndex:'voteNum',sortable:true}
				]
				
	});
	var QuestionForm = new Ext.form.FormPanel({
		labelWidth:60,
		width:300,
		frame:true,
		labelSeparator:':',
		region:'center',
		items:[questionGrid],
		buttons:[new Ext.Button({text : '确定',handler : fixQuestion
										}), new Ext.Button({
											text : '重置',
											handler : reset
										})]
	});
	var MakeVersionWin = new Ext.Window({
						contentEI : 'window',
						title : '修复问题',
						width : 500,
						height : 500,
						plain : true,
						closeAction : 'hide',
						maximizable : true,
						layout : 'border',
						items : [QuestionForm]
					});
	make_version.addListener('click',function(){MakeVersionWin.show()});
	function reset(){
		QuestionForm.getForm().reset();
	}
	function fixQuestion(){
		
		var questionList = "";
		var questionOk = questionGrid.getSelectionModel().each(function(rec){questionList= questionList+","+rec.get('questionId');});
		if(questionList.length >1 ) questionList = questionList.substring(1);
		
		
		QuestionForm.getForm().submit({
			clientValidation:true,
			waitMsg:'正在更新，请稍候',
			waitTitle:'提示',
			url:'makeVersion.so?questionList='+questionList+
				'&scenarioId='+scenarioId,
			method:'Get',
			success:function(form,action){
				Ext.Msg.alert('成功', '提交成功,即将跳转');
				window.location.reload();},
			failure:function(form,action){alert("Make Version FAIL@!");}
			
		});
	}
	
});//Ext.get.


