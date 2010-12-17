function makeVersion(scenarioId){
	var requestConfig = {
						url: 'makeVersion.so',
						params:{'scenarioId':scenarioId},
						method:'GET',
						success:function(response){
							alert("该场景已被保存为版本！");							
							window.location.reload();
						},
						failure:function(){
							alert("Make Version FAIL@!"); 
						}
					};
	Ext.Ajax.request(requestConfig);	
}