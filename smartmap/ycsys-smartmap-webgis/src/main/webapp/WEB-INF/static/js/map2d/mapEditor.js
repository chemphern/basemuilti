/************地图编辑模块*/
var editGlobal = {
	layers:[],
	initialized:false,
	setting:null
}
$(function(){
	$("#editableLyrs").change(getEditLayer);
});

//添加可编辑图层
function addEditableLyr(e){
	if(e.layer.isEditable && e.layer.isEditable()){
		editGlobal.layers.push(e.layer);
		$("#editableLyrs").append("<option value='"+e.layer.id+"'>"+e.layer.name+"</option>");
	}
}

//移除可编辑图层
function removeEditableLyr(e){
	$("#editableLyrs option[value='"+e.layer.id+"']").remove(); 
}

//获取可编辑图层初始化一些参数
function getEditLayer(){
	/** 默认地图编辑工具对所有编辑图层类型一次性初始化，不支持单个图层切换
	var id= $("#editableLyrs").children('option:selected').val();
	editGlobal.layers = [];
	editGlobal.layers.push(map.getLayer(id));
	initEditor();*/
}

function initEditor() {
//	var id= $("#editableLyrs option:selected").val();
//	if(!id) return;
	if(editGlobal.layers.length<1) return;
	if(!editGlobal.initialized){
		editGlobal.initialized = true;
//		editGlobal.layers.push(map.getLayer(id));
		//初始化TemplatePicker
		editGlobal.templatePicker = new esri.dijit.editing.TemplatePicker({
			featureLayers: editGlobal.layers,
			grouping: true,
			rows: "auto",
			columns: 3
		}, "templateDiv");
		editGlobal.templatePicker.on("selection-change", function(){
			var obj = editGlobal.templatePicker.getSelected();
			console.log(obj);
		 })
		editGlobal.templatePicker.startup();
		//初始化layerInfos
		editGlobal.layerInfos = arrayUtils.map(editGlobal.layers,function(result){
			return {featureLayer: result};
		});
		var settings = {
			map: map,
			templatePicker: editGlobal.templatePicker,
			layerInfos: editGlobal.layerInfos,
			toolbarVisible: true,
			createOptions: {
				polylineDrawTools: [esri.dijit.editing.Editor.CREATE_TOOL_POLYLINE],
				polygonDrawTools: [
					esri.dijit.editing.Editor.CREATE_TOOL_POLYGON,
					esri.dijit.editing.Editor.CREATE_TOOL_CIRCLE,
					esri.dijit.editing.Editor.CREATE_TOOL_TRIANGLE,
					esri.dijit.editing.Editor.CREATE_TOOL_RECTANGLE
				]
			},
			toolbarOptions: {
				reshapeVisible: true
			}
		};
		editGlobal.setting = settings;
		
		var symbol = new esri.symbol.SimpleMarkerSymbol(esri.symbol.SimpleMarkerSymbol.STYLE_CROSS,15,
				new esri.symbol.SimpleLineSymbol(esri.symbol.SimpleLineSymbol.STYLE_SOLID,new esri.Color([255, 0, 0, 0.5]),5),null);
		map.enableSnapping({
			snapPointSymbol: symbol,
			tolerance: 20,
			snapKey: dojo.keys.ALT
		});
		var params = {settings: editGlobal.setting};
		var editor = new esri.dijit.editing.Editor(params, 'editorDiv');
		editor.startup();
	}else{
		//修改TemplatePicker
		editGlobal.setting.templatePicker = editGlobal.templatePicker.attr('featureLayers',editGlobal.layers);
		editGlobal.setting.templatePicker.update();
		
		editGlobal.setting.layerInfos = arrayUtils.map(editGlobal.layers,function(result){
			return {featureLayer: result};
		});
	}
}

