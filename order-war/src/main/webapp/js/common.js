function formatTable(tableId){

  var tableObject = document.all(tableId);
    
  for(var i=1;i<tableObject.rows.length;i++){
  	for(var j=0;j<tableObject.rows[i].cells.length;j++){
  	
  		tableObject.rows[i].onmouseover=function(){
  			this.style.backgroundColor="#EEEEEE";
  		}
  		
  		tableObject.rows[i].onmouseout=function(){
  			this.style.backgroundColor="#FFFFFF";
  		}
  		
  	}
  }

}

function checkNotEmpty(obj,objLabel){
  if(obj.value==''||obj.value==null){
    alert("["+objLabel+"]不能为空！");
    obj.focus();
  	return false;
  }else{
  	return true;
  }
}

function checkNumber(obj,objLabel){
  if(isNaN(obj.value)==true){
    alert("["+objLabel+"]只能是数字！");
    obj.focus();
  	return false;
  }else{
  	return true;
  }
}