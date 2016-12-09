
//var template=new esri.InfoTemplate("Info","${*}");

//传参highlightShow[boolean]表示需要高亮显示
function renderSymbol(type,highlightShow){
	var symbol,lineSmb,color;
	type=type.toLowerCase();
	if(highlightShow){
		color=new esri.Color([0, 255, 255, 1 ]);
		lineSmb=new esri.symbol.SimpleLineSymbol(esri.symbol.SimpleLineSymbol.STYLE_SOLID,color, 3);
	}else{//默认样式
		color=new esri.Color([222, 22, 22, 0.5 ]);
		lineSmb=new esri.symbol.SimpleLineSymbol(esri.symbol.SimpleLineSymbol.STYLE_SOLID,color, 1);
	}
	if(type.indexOf("point")>-1){
		symbol = new esri.symbol.SimpleMarkerSymbol(esri.symbol.SimpleMarkerSymbol.STYLE_DIAMOND, 10,lineSmb,color);
	}else if(type.indexOf("polyline")>-1){
		symbol = lineSmb;
	}else if(type.indexOf("polygon")>-1){
		symbol = new esri.symbol.SimpleFillSymbol(esri.symbol.SimpleFillSymbol.STYLE_SOLID, lineSmb, color);
	}
	return symbol;
}

function queryAttrLogic2d(){
//	var url = "http://172.16.10.52:6080/arcgis/rest/services/GuangzhouTraffic/MapServer/0";
//	var fLyr = new esri.layers.FeatureLayer(url, {
//		mode : esri.layers.FeatureLayer.MODE_SNAPSHOT,
//		outFields: ["*"]
//	});
//	fLyr.setDefinitionExpression("StationNum = 7 AND ExitCount = 4 ");
//	var symbol = new esri.symbol.SimpleMarkerSymbol(esri.symbol.SimpleMarkerSymbol.STYLE_DIAMOND, 10,
//		    new esri.symbol.SimpleLineSymbol(esri.symbol.SimpleLineSymbol.STYLE_SOLID,
//		    	    new esri.Color([255,0,0]), 1),
//		    	    new esri.Color([0,0,255,0.25]));
//	fLyr.setRenderer(new esri.renderer.SimpleRenderer(symbol));
//	map.addLayer(fLyr);
	var layerSelect=$('#queryLyrLogic')[0];
	var fieldSelect=$('#queryFieldsLogic')[0];
	var layerItem=layerSelect.options[layerSelect.selectedIndex];
	var fieldItem=fieldSelect.options[fieldSelect.selectedIndex];
	
	var layerUrl=layerItem.url;
	var fieldName=fieldItem.value;
	var fieldType=fieldItem.type;
	var fieldValue=$("#queryValue").val();
}
/**
 * 属性查询
 * @param layerUrl 图层url
 * @param fieldName 字段名
 * @param fieldType 字段类型
 * @param fieldValue 字段值
 * @returns
 */
function queryAttr2d(layerUrl,fieldName,fieldType,fieldValue){
	if(fieldType=="esriFieldTypeString"){
		fieldValue="'"+fieldValue+"'";
	}else if(fieldType=="esriFieldTypeDate"){
		fieldValue="date '"+fieldValue+"'";
	}
	var sqlWhere=fieldName + " = " + fieldValue;
	query(layerUrl,sqlWhere);
}

function query(url,sqlWhere){
	var query=new esri.tasks.Query();
	query.returnGeometry=true;
	query.outFields=["*"];
	query.where=sqlWhere;
	var task=new esri.tasks.QueryTask(url);
	task.execute(query,okCall,failCall);
}

function okCall(featureSet){
	console.log(featureSet);
	map.graphics.clear();
	var symbol=renderSymbol(featureSet.geometryType);
	//图形展示
	var features=featureSet.features;
	var data=[];
	for(var i=0;i<features.length;i++){
		var feature=features[i];
		feature.setSymbol(symbol);
//		feature.setInfoTemplate(template);
		map.graphics.add(feature);
		//属性
		feature.attributes.feature=feature;//添加属性扩展，属性列表高亮显示
		data.push(feature.attributes);
	}
	//属性展示
	var fields=featureSet.fields;
	var columns=[],column=[];
	for(var i=0;i<fields.length;i++){
		var item={
				title:fields[i].alias,
				field:fields[i].name
		}
		column.push(item);
	}
	columns.push(column);
	showAttribute(columns,data);
}

function showAttribute(columns,data){
	$("#dialog").css('display','block');
	$('#grid').datagrid({
		singleSelect:true,
		columns:columns,
		data:data,
		onDblClickRow:highlightView
	});
}

function highlightView(rowIndex, rowData){
	var feature=rowData.feature;
	var type=feature.geometry.type;
	var symbol=renderSymbol(type,true);
	feature.setSymbol(symbol);
	map.setExtent(feature._extent,true);
	map.centerAndZoom(feature._extent.getCenter(),10);
}

function failCall(){
	
}

/**
 * 添加字段列表
 * @param layer 图层对象
 * @param selectCtrl 下拉框对象
 * @returns
 */
function doListFields(layer,selectCtrl){
	var fields=layer.fields;
	for(var i=0;i<fields.length;i++){
		var field=fields[i];
		switch (field.type) {
		case "esriFieldTypeSmallInteger":
		case "esriFieldTypeInteger":
		case "esriFieldTypeSingle":
		case "esriFieldTypeDouble":
		case "esriFieldTypeString":
		case "esriFieldTypeDate":
			var opt=new Option(field.alias,field.name);
			opt.type=field.type;
			selectCtrl.options.add(opt);
			break;
		default:
			break;
		}
	}
}
/**
 * 查询图层字段
 * @returns
 */
function listFields(){
	var select=$('#queryLyrLst')[0];
	var layerItem=select.options[select.selectedIndex];
	var layer=map.getLayer(layerItem.value);
	var selectField=$("#queryFieldsLst")[0];
	selectField.length=0;
	doListFields(layer,selectField);
}
/**
 * 逻辑查询图层字段
 * @returns
 */
function listFieldsLogic(){
	var select=$('#queryLyrLogic')[0];
	var layerItem=select.options[select.selectedIndex];
	var layer=map.getLayer(layerItem.value);
	var selectField=$("#queryFieldsLogic")[0];
	selectField.length=0;
	doListFields(layer,selectField);
}

