var addTag = function(html){
//get the string and add tad on it	
		var ar = new Array();
		var out = new Array();
		ar = html.split("");
		var count = 0;
		var num =0;
		var position=0;
		var whetherCount=true;
		while(num<ar.length)
		{
			if(ar[num]=='<')
			   whetherCount=false;

			if(whetherCount==true)
			{
			    
				out[position] = '<span id="'+count+'">'+ar[num]+'</span>';
				count++;
				
			}else
				out[position]=ar[num];

			if(ar[num]=='>')
          	   whetherCount=true;

			num++;
			position++;
		}
		var ss = out[0];
		for(var i = 1;i<out.length;i++){
		var ss = ss + out[i];
		}
		
	return ss;
	};
	
var compare = function(ss,content){
			var results = "";
		for(var i=0;i<count;i++){
			var a = new String(i);		

			var cc = a;

		
			if(!editor.document.getElementById(cc)){
		    	var enter = i;
				while(!editor.document.getElementById(cc)){
			    	i++;
			    	var cc = new String(i);
				}
		    	var b = cc;

		    	if(editor.document.getElementById(String(enter-1)).innerHTML.length==1){
			 		var middle = "";
		
			 		for(var k = enter;k <i;k++){

						middle = middle+'<span id ="'+k+'"><font color="blue"><strike>'+editor1.document.getElementById(String(k)).innerText+'</strike></font></span>';

					}

					editor.document.getElementById(b).outerHTML=middle+editor.document.getElementById(b).outerHTML;
 				}
	   			else{
		    		var middle2 = "";
			 		for(var k2 = enter;k2 <i;k2++){	
						middle2 = middle2+'<span id ="'+k2+'"><font color="blue"><strike>'+editor1.document.getElementById(String(k2)).innerText+'</strike></font></span>';	
   					}
					editor.document.getElementById(b).outerHTML=middle2+editor.document.getElementById(b).outerHTML;
				}	
			}
			//�ж���ӵ����
			if (editor.document.getElementById(a).innerText.length >=2){
			var add = new Array();
				add = editor.document.getElementById(a).innerText.split("");
				middle1 = "";
			for(var ii =1;ii<add.length;ii++){
				middle1 = middle1 + add[ii];
			}
			editor.document.getElementById(a).innerHTML=add[0]+'<font color="red"><u>'+middle1+'</u></font>';
			}

		 	editor1.document.close();
  	    	editor.document.close();		
			
		}
		for(var res=0;res<count;res++){
			var res_a = new String(res);
			editor.document.getElementById(res_a).outerHTML=editor.document.getElementById(res_a).innerHTML					
			
		}
	
	
	
	
	
	
	}
	
	
	
	
	