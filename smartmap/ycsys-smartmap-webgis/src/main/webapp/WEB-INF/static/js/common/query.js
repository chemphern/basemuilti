var layerSelect;//图层选择列表
var fieldSelect;//字段选择列表
var fieldValue;//字段值

function initCtrl(){
	layerSelect=$('#queryLyrLst')[0];
	fieldSelect=$('#queryFieldsLst')[0];
	fieldValue=$("#queryValue").val();
}

function queryAttrLogic(){
	initCtrl();
	if(mapOpt==2){
		queryAttrLogic2d();
	}else if(mapOpt==3){
		
	}
}

function queryAttr(){
	initCtrl();
	var layerItem=layerSelect.options[layerSelect.selectedIndex];
	var fieldItem=fieldSelect.options[fieldSelect.selectedIndex];
	
	var layerUrl=layerItem.url;//图层url
	var fieldName=fieldItem.value;//字段名（非别名）
	var fieldType=fieldItem.type;//字段类型
	if(mapOpt==2){
		queryAttr2d(layerUrl,fieldName,fieldType,fieldValue);
	}else if(mapOpt==3){
		queryAttr3d(layerItem.text,fieldName,fieldValue);
	}
}