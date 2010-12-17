function voteSolution(id){
	var requestConfig = {
						url: 'voteSolution.so',
						params:{'solutionId':id},
						method:'GET',
						success:function(response){
							var jsonText = Ext.decode(response.responseText);
							var repeat = jsonText.repeat;
							if(repeat == true) {
								alert('您已经投过票!');
							}else{
							window.location.reload();
							}
						},
						failure:function(){
							alert("vote solution(id="+id+") FAILED!");
						}
					};
	Ext.Ajax.request(requestConfig);
}