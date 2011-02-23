/**
 * 
 * @param {} id
 * @param {} num -- 1 赞成
 *               -- -1 反对.
 */
function voteQuestion(id,num){
	var requestConfig = {
						url: 'voteQuestion.so',
						params:{'questionId':id,'num':num},
						method:'GET',
						success:function(response){
							var jsonText = Ext.decode(response.responseText);
							var repeat = jsonText.repeat;
							if(repeat == 1) {
								alert('您已经投过赞成票!');
							}else if(repeat == -1){
								alert('您已经投过反对票');
							}else{
								window.location.reload();
							}
						},
						failure:function(){
							alert("vote Question(id="+id+") FAILED!");
						}
					};
	Ext.Ajax.request(requestConfig);
}