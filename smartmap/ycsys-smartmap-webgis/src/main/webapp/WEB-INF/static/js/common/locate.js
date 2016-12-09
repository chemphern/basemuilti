function validate(x,y){
	if(!$.isNumeric(x) || !$.isNumeric(y)){
		alert("请输入数字");
		return false;
	}
	return true
}
function locateLngLat(){
	var lng=Number($("#degreeLng").val())+$("#minLng").val()/60+$("#secLng").val()/3600;
	var lat=Number($("#degreeLat").val())+$("#minLat").val()/60+$("#secLat").val()/3600;
	if(!validate(lng,lat)){
		return;
	}
	if(mapOpt==2){
		locateLngLat2d(lng,lat);
	}else if(mapOpt==3){
		locate3d(lng,lat);
	}
}

function locateXY(){
	var x=Number($("#posX").val());
	var y=Number($("#posY").val());
	if(!validate(x,y)){
		return;
	}
	if(mapOpt==2){
		locateXY2d(x,y);
	}else if(mapOpt==3){
		locate3d(x,y);
	}
}

function locateAddress(){
	var pos=$("#address").val();
	if(mapOpt==2){
		locateAddress2d(pos);
	}
}