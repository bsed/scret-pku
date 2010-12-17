
	function deleteInProject( kind,  id){
				var requestConfig = "";
				if(kind == 'dataIn'){
					requestConfig = {
						url: 'deleteProjectData.so',
						params:{'dataId':id},
						method:'GET',
						success:function(){
							window.location.reload();
						},
						failure:function(){
							alert("delete data(id="+id+") FAILED!");
						}
					}
				}
				if(kind == 'roleIn'){
					requestConfig = {
						url: 'deleteProjectRole.so',
						params:{'roleId':id},
						method:'GET',
						success:function(){
							window.location.reload();
						},
						failure:function(){
							alert("delete role(id="+id+") FAILED!");
						}
					}
				}
				if(kind == 'scenarioIn'){
					requestConfig = {
						url: 'deleteProjectScenario.so',
						params:{'scenarioId':id},
						method:'GET',
						success:function(){
							window.location.reload();
						},
						failure:function(){
							alert("delete scenario(id="+id+") FAILED!");
						}
					}
				}
				Ext.Ajax.request(requestConfig);
			}
