function isDate(date,message,obj){    
    
    var Expression = /^((((1[6-9]|[2-9]\d)\d{2})(\/|\-)(0?[13578]|1[02])(\/|\-)(0?[1-9]|[12]\d|3[01]))|(((1[6-9]|[2-9]\d)\d{2})(\/|\-)(0?[13456789]|1[012])(\/|\-)(0?[1-9]|[12]\d|30))|(((1[6-9]|[2-9]\d)\d{2})(\/|\-)0?2(\/|\-)(0?[1-9]|1\d|2[0-8]))|(((1[6-9]|[2-9]\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29-))$/;
    
    var objExp = new RegExp(Expression);
    
    if(objExp.test(date)==false)
        {
             alert(message);
             obj.focus();
        }

}

function isEmail(str,message,obj){
    //在JavaScript中，正则表达式只能使用"/"开头和结束，不能使用双引号

    var Expression=/^([a-zA-Z0-9._\-])+@([a-zA-Z0-9_\-])+(\.[a-zA-Z0-9_\-])+/; 

    if(str!=''&&str.search(Expression)==-1)
    {
         alert(message);
         obj.focus();
    }

}

function isUrl(str,message,obj)
{
    //在JavaScript中，正则表达式只能使用"/"开头和结束，不能使用双引号

    var Expression=/http(s)?:\/\/([\w\-]+\.)+[\w\-]+(\/[\w\- .\/?%&=]*)?/;

    var objExp=new RegExp(Expression);

    if(objExp.test(str)==true)
        {
             alert(message);
             obj.focus();
        }
}

function isLong(longValue,message,obj){

 if (isNaN(longValue))
    {
         alert(message);
         obj.focus();
         return;
    }

 else {

    j = 0;

    for(i=0; i<s.length; i++){

       if (s.charAt(i)=='.') j=1;

    }

    if (j==1) 
        {
             alert(message);
             obj.focus();
             return;
        }
 }
}

function isInteger(intValue,message,obj){

 if (isNaN(intValue))
    {
         alert(message);
         obj.focus();
         return;
    }
 else {
        if(intValue>2147483648 || intValue< -2147483648 )
        {
             alert(message);
             obj.focus();
             return;
        }
        
    j = 0;

    for(i=0; i<s.length; i++){

       if (s.charAt(i)=='.') j=1;

    }

    if (j==1) 
        {
             alert(message);
             obj.focus();
             return;
        }
 }
}



function isFloat(floatValue,message,obj){
 if (isNaN(floatValue))
    {
         alert(message);
         obj.focus();
    }

}

function isDouble(doubleValue,message,obj){
 if (isNaN(doubleValue))
    {
         alert(message);
         obj.focus();
    }

}

function isInRange(numValue,minValue,maxValue,obj)
{
    if(numValue<minValue||numValue>maxValue)
    {
         alert(message);
         obj.focus();
    }
}

function minLen(str,len,message,obj)
{
    if(str.length<len)
    {
         alert(message);
         obj.focus();
    }
}

function maxLen(str,len,message,obj)
{
    if(str.length>len)
    {
         alert(message);
         obj.focus();
    }
}

function minValue(str,minVal,message,obj)
{
    if(str<minVal)
    {
         alert(message);
         obj.focus();
    }
}

function maxValue(str,maxVal,message,obj)
{
    if(str>maxVal)
    {
         alert(message);
         obj.focus();
    }
}
