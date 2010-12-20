/**
	之前仅仅是发送ajax请求，更改scenario的状态 ； 
	后续要改成弹出问题窗口,选择哪些问题被fix掉.
*/
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