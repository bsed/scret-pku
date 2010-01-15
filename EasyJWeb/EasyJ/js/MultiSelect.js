var MultiSelect={};
(function(){
    //这个方法用来将source中选择的数据加入到desc中。其中source和dest都是select的多选框。
	var cb=checkBrowser();
	//用户可以在输入框里对源数据进行过滤，sourceDataCache用来暂时缓存原来所有的源数据。
    var sourceDataCache;
    
    //用来将source中的数据cache到sourceDataCache中，直接指向新的数据可能会造成内存泄露，现在不清楚，需要注意。
    MultiSelect.cacheSourceData=function() {
    	var source=document.getElementById("sourceSelect");
    	sourceDataCache=[];
		for(i=0;i<source.length;i++) {
			sourceDataCache[i]=source.options[i];
		}
    };
    
	MultiSelect.select=function() {
	    var source=document.getElementById("sourceSelect");
	    var dest=document.getElementById("destSelect");
		
		//将source中所有选择的数据加入到selected数组当中，并且从当前source中移去。并且加入到dest中
		for(i=source.length-1;i>=0;i--){
			if(source.options[i].selected){
				if(cb=='IE') {
					var option=document.createElement('option');
					option.text=source.options[i].text;
					option.value=source.options[i].value;
					dest.options.add(option);
				}
				else
					dest.add(source.options[i],null);
				source.remove(i);
			}
		}
		//每次操作都应该对原数据重新缓存一下。
		MultiSelect.cacheSourceData();
	};
	MultiSelect.cancel=function() {
		PopUpWindow.hide();
	};
	
	//用来对元数据进行过滤，首先将原数据清空，然后从sourceDataCache中找到符合pattern条件的，再加入到source中。
	MultiSelect.filter=function(pattern) {
	    var source=document.getElementById("sourceSelect");
	    var dest=document.getElementById("destSelect");
	    
		//将source数据移去。
		for(i=source.length-1;i>=0;i--) {
			source.remove(i);
		}
		
		//符合条件的加入到source中。
		if(pattern&&pattern!='') {
			for(i=0;i<sourceDataCache.length;i++){
				if(sourceDataCache[i].text.indexOf(pattern)>=0) {
					if(cb=='IE'){
						//var option=document.createElement('option');
						//option.text=dest.options[i].text;
						//option.value=dest.options[i].value;
						source.options.add(sourceDataCache[i]);
					}
					else
						source.add(sourceDataCache[i],null);
				}
			}
		}else {
			//如果patter为空，则将sourceDataCache中的数据全部加入source中
			for(i=0;i<sourceDataCache.length;i++) {
				if(cb=='IE'){
					//var option=document.createElement('option');
					//option.text=dest.options[i].text;
					//option.value=dest.options[i].value;
					source.options.add(sourceDataCache[i]);
				}
				else
					source.add(sourceDataCache[i],null);
			}
		}
	};
	
    //这个方法用来将dest中选择的数据加入到source中。其中dest和source都是select的多选框。
	MultiSelect.deSelect=function() {
	    var source=document.getElementById("sourceSelect");
	    var dest=document.getElementById("destSelect");

		//将source中所有选择的数据加入到selected数组当中，并且从当前source中移去。并且加入到dest中
		for(i=dest.length-1;i>=0;i--){   
			if(dest.options[i].selected){
				if(cb=='IE'){
					var option=document.createElement('option');
					option.text=dest.options[i].text;
					option.value=dest.options[i].value;
					source.options.add(option);
				}
				else
					source.add(dest.options[i],null);
				dest.remove(i);
			}
		}
		//每次操作都应该对原数据重新缓存一下。
		MultiSelect.cacheSourceData();
	};
	/*用户点击确认的时候执行的操作：向服务器提交，关闭选择页面，刷新编辑页面。*/
	MultiSelect.commonConfirm=function() {
		var actionPath = "easyJ.http.servlet.SingleDataAction.do?ACTION=multiSelectConfirm&easyJ.http.Globals.CLASS_NAME="+MultiSelect.data.destClass;
		//得到需要将所选择的值放入到哪个字段中
		var property=document.getElementById("multiSelectProperty");
		var selectedValue="";
		//将所有的ID取出来。
		var dest=document.getElementById("destSelect");
		for(i=0;i<dest.length;i++){
			//if(dest.options[i].selected){
			selectedValue=selectedValue+dest.options[i].value+",";
			//}
		}
		var data={};
		data.selectedValue=selectedValue;
		//MultiSelect.data实在PopUpWindow.showMultiSelect方法中设置的。
		if (MultiSelect.data) {
			for (var option in MultiSelect.data) {
              //alert(option);
              //alert(MultiSelect.data[option]);
				data[option]=MultiSelect.data[option];
			}
			data.property=property.value;
		}
        Ajax.submit(actionPath, null,data);
        PopUpWindow.hide();
        //刷新整个编辑页面，这是在场景中选择数据和角色的时候做得，在别的地方可能不需要刷新，或者刷新的是别的页面。
		Data.refreshEdit();
	};
	
	//这个方法用来将某个select中的位置进行调整。可以允许用户一次选择多个并调整
	MultiSelect.move=function(direction) {
	    var dest=document.getElementById("destSelect");

		var select=dest;
		//将source中所有选择的数据加入到selected数组当中，并且从当前source中移去。并且加入到dest中
		if(direction=='up') {
			for(i=0;i<dest.length;i++){
			//alert(dest.options[i].text);
				if(dest.options[i].selected){
					var element=dest.options[i];
					result=MultiSelect.singleMove(select,element,direction);
					if(!result) break;
				}
			}
		}else {
			for(i=dest.length-1;i>=0;i--){
				if(dest.options[i].selected){
					var element=dest.options[i];
					result=MultiSelect.singleMove(select,element,direction);
					if(!result) break;
				}
			}
		}
	};
	//只移动select中的一个元素。返回值用来说明是否还能移动
	MultiSelect.singleMove=function(select,element,direction) {
		var index=element.index;
		var next; //用来找到要交换的节点
		if(direction=='up') {
			//如果已经位于最上方，则不做移动
			if(index==0)
				return false;
			else {
				next=select.options[index-1];
			}
		}else {
			//如果已经位于最下方，则不做移动
			if(index==select.length-1)
				return false;
			else {
				next=select.options[index+1];
			}
		}

		if(cb=='IE') 
			element.swapNode(next);
		else {
			var nextValue=next.value;
			var nextText=next.text;
			next.value=element.value;
			next.text=element.text;
			//alert(nextText);
			//alert(next.index);
			element.text=nextText;
			element.value=nextValue;
			next.selected=true;
			element.selected=false;
		}
		return true;
	};
})();