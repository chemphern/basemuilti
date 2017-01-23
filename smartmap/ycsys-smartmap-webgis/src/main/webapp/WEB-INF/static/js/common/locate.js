$(function(){
	//地名定位
	$('#btnLocateAddress').on('click',locateAddress);
	//坐标定位
	$('#btnLocateLngLat').on('click',locateLngLat);
	$('#btnlocateXY').on('click',locateXY);
	//书签定位
	$("#bookmarkSearch").on('click',bookmarkSearch);
	$("#Sqdwtoolbar").on('click','.btn_add', bookmarkAdd);
	$("#Sqdwtoolbar").on('click','.btn_edit', bookmarkEdit);
	$("#Sqdwtoolbar").on('click','.btn_del', bookmarkDel);
	$("#tableSqdw").on('click-row.bs.table',locateBookmark);
    $("#tablePathPoint").on('click-row.bs.table',locatePathPoint);
});

function validate(x,y){
	if(!$.isNumeric(x) || !$.isNumeric(y)){
		showAlertDialog("请输入数字");
		return false;
	}
	return true
}
function locateLngLat(){
	if(!validate($("#degreeLng").val(),$("#degreeLat").val())){
		return;
	}
	var lng=Number($("#degreeLng").val())+$("#minLng").val()/60+$("#secLng").val()/3600;
	var lat=Number($("#degreeLat").val())+$("#minLat").val()/60+$("#secLat").val()/3600;
	if(mapOpt==2){
		locateLngLat2d(lng,lat);
	}else if(mapOpt==3){
		locate3d(lng,lat);
	}
}

function locateXY(){
	if(!validate($("#posX").val(),$("#posY").val())){
		return;
	}
	var x=Number($("#posX").val());
	var y=Number($("#posY").val());
	if(mapOpt==2){
		locateXY2d(x,y);
	}else if(mapOpt==3){
		locate3d(x,y);
	}
}

function locateAddress(){
	var pos=$("#address").val();
    locateAddress2d(pos);
	// if(mapOpt==2){
	// 	locateAddress2d(pos);
	// }else if(mapOpt==3){
     //    locateAddress3d(pos);
	// }
}
function bookmarkSearch(){
	if(mapOpt==2){
		bookmarkSearch2d();
	}
}
function bookmarkCheck(){
	if($('#tableSqdw').bootstrapTable('getSelections').length<1){
    	showAlertDialog("请选一个书签");
    	return false;
	}
	return true;
}
function bookmarkAdd(e){
	e.preventDefault();  
	if(mapOpt==2){
		bookmarkAdd2d(e);
	}else if(mapOpt==3){
        bookmarkAdd3d();
    }
}
function bookmarkEdit(e){
	e.preventDefault();
	if(mapOpt==2){
        if(!bookmarkCheck()){
            return;
        }
		bookmarkEdit2d(e);
	}else if(mapOpt==3){
        bookmarkEdit3d();
    }
}
function bookmarkDel(e){
    e.preventDefault();
	if(mapOpt==2){
        if(!bookmarkCheck()){
            return;
        }
		bookmarkDel2d(e);
	}else if(mapOpt==3){
        bookmarkDel3d();
    }
}

function locateBookmark(e, row, field){
	if(mapOpt==2){
		locateBookmark2d(e,row,field);
	}else if(mapOpt==3){
        locateBookmark3d(row);
	}
}