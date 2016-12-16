var layerSelect;//图层选择列表
var fieldSelect;//字段选择列表
var fieldValue;//字段值

$(function(){
	$('#btnQueryAttr').on('click',queryAttr);
	$('#btnQueryPoint').on('click',queryPoint);
	$('#btnQueryPolyline').on('click',queryPolyline);
	$('#btnQuerypolygon').on('click',queryPolygon);
	$('#btnQueryAttrLogic').on('click',queryAttrLogic);
	$("#btnAddLogic").on('click',addLogicItem);
	$("#btnDelLogic").on('click',delLogic2d);
	$("#btnLogicReset").on('click',resetLogic);
});

function resetLogic(){
	$('#tableLogic').bootstrapTable('removeAll');
}
function initCtrl(){
	layerSelect=$('#queryLyrLst')[0];
	fieldSelect=$('#queryFieldsLst')[0];
	fieldValue=$("#queryValue").val();
}

function queryAttrLogic(){
	var lyrSelect=$('#queryLyrLogic')[0];
	var lyrItem=lyrSelect.options[lyrSelect.selectedIndex];
	if(mapOpt==2){
		queryAttrLogic2d(lyrItem);
	}else if(mapOpt==3){
		
	}
}
function delLogic(e){
	if(mapOpt==2){
		delLogic2d(e);
	}else if(mapOpt==3){
		
	}
}

function addLogicItem(){
	var lyrSelect=$('#queryLyrLogic')[0];
	var fildSelect=$('#queryFieldsLogic')[0];
	var logicSelect=$('#queryOptLogic')[0];
	var fildValue=$("#queryLogicVal").val();
	var lyrItem=lyrSelect.options[lyrSelect.selectedIndex];
	var fildItem=fildSelect.options[fildSelect.selectedIndex];
	var logicItem=logicSelect.options[logicSelect.selectedIndex];
	if(mapOpt==2){
		addLogicItem2d(lyrItem,fildItem,logicItem,fildValue);
	}
}

function queryAttr(){
	initCtrl();
	var layerItem=layerSelect.options[layerSelect.selectedIndex];
	var fieldItem=fieldSelect.options[fieldSelect.selectedIndex];
	
	var layerUrl=layerItem.url;//图层url
	var fieldName=fieldItem.value;//字段名（非别名）
	var fieldType=fieldItem.type;//字段类型
    queryAttr2d(layerItem,fieldName,fieldType,fieldValue);
	// if(mapOpt==2){
	// 	queryAttr2d(layerItem,fieldName,fieldType,fieldValue);
	// }else if(mapOpt==3){
     //    queryAttr3d(layerItem.text,fieldName,fieldValue);
	// }
}

function queryPoint(){
	if(mapOpt==2){
		queryPoint2d();
	}else if(mapOpt==3){
        queryPoint3d();
	}
}
function queryPolyline(){
	if(mapOpt==2){
		queryPolyline2d();
	}else if(mapOpt==3){
        queryPolyline3d();
    }
}
function queryPolygon(){
	if(mapOpt==2){
		queryPolygon2d();
	}else if(mapOpt==3){
        queryPolygon3d();
    }
}