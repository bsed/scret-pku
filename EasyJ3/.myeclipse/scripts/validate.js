//Case: New user register.
function validateName(){
	
		var url = "validateName.action";		
		var params = Form.Element.serialize('userName');
	
		var myAjax = new Ajax.Request(url ,
									 {
									 	method:'post',
									 	parameters:params,
									 	onComplete:processResponse,
									 	asynchronous:true
									 	}
									 );
	
}
function processResponse(request){

		// var action = request.responseText.parseJSON();
		var action = json_parse(request.responseText);
	
		$("tip").innerHTML = action.tip;
}
	