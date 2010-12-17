
function contributeOk(roleId,scenarioId){
	
	var requestConfig = {
						url: 'contributeOK.so',
						params:{'roleId':roleId,'scenarioId':scenarioId},
						method:'GET',
						success:function(response){							
							window.location.reload();
						},
						failure:function(){
							alert("contribute FAIL@!"); 
						}
					};
	Ext.Ajax.request(requestConfig);	
}