var nameField;//要素名称字段
var summaryFields;//概要组合字段
var displayFields;//用于地图上属性表显示的字段
var arrFeaturesTemp=[];//保存几何要素的数组
var arrQueryInfoItem;//属性查询结果条目
var pageSize=10;//查询结果每页显示条数
var queryCatalog=0;//查询类别：0,1,2分别代表属性查询、空间查询、逻辑查询
var pageSize=10;
var businessType; //图层业务类型:default,vedio,scene

$(function(argument){
});

/**====================================逻辑查询========================================*/

function addLogicItem2d(lyrItem,fildItem,logicItem,fildValue){
	var fieldName=fildItem.value;
	var logic=logicItem.label;
	var fieldType=fildItem.type;
	var fieldValue=fildValue;
	businessType = lyrItem.businessType;
	
	var id;
	var data=$('#tableLogic').bootstrapTable('getOptions').data;
	if(data){
		id=data.length;
	}else{
		id=0;
	}
	var o=formatOption(logic,fieldValue,fieldType);
	var row={
			id:id,
			fieldName:fieldName,
			fieldOpt:logic,
			logicOpt:o.opt,
			fieldVal:fieldValue,
			logicVal:o.val,
			fieldLogic:''
	}
	$('#tableLogic').bootstrapTable('append',row);
//	setTimeout(function(){
//		$('.switch-button').bootstrapSwitch();
//		$('.switch-button').on('switch-change', function (e, data) {
//		    var $el = $(data.el), value = data.value;
//		    console.log(e, $el, value);
//		});
//	},500);
	
}
function formatOption(fieldOpt,fieldValue,fieldType){
	var o={};
	//处理逻辑符
	switch (fieldOpt) {
	case '大于':
		o.opt='>';
		break;
	case '大于等于':
		o.opt='>=';
		break;
	case '等于':
		o.opt='=';
		break;
	case '小于':
		o.opt='<';
		break;
	case '小于等于':
		o.opt='<=';
		break;
	case '不等':
		o.opt='<>';
		break;
	case '包含':
		o.opt='LIKE';
		fieldValue="%"+fieldValue+"%";
		break;
	case '为空':
		o.opt='IS NULL';
		fieldValue="";
		break;

	default:
		break;
	}
	//处理字符串型的值
	switch (fieldType) {
	case "esriFieldTypeString":
	case "esriFieldTypeDate":
		if(o.opt !='IS NULL'){
			fieldValue="'"+fieldValue+"'";
		}
		break;
	default:
		break;
	}
	o.val=fieldValue;
	return o;
}
function logicFormatter(value, row, index) {
	var id=row.id;
	var opt="<select onchange='setLogic("+index+");' id='logicSel"+index+"' style='width:100%;border:0px;margin:0px;padding:0px;'><option label='' value=''><option value='AND' label='且'><option value='OR' label='或'></selection>";
//	var opt="<input type='checkbox' checked class='switch-button' data-on-text='且' data-off-text='或'>";
	return opt;
}
function setLogic(index){
	var sel=$('#logicSel'+index)[0];
	var indx=sel.selectedIndex;
	var row=$('#tableLogic').bootstrapTable('getOptions').data[index];
	row.fieldLogic=sel.options[indx].value;
}
function delLogic2d(e){
	var row=$('#tableLogic').bootstrapTable('getSelections')[0];
	$('#tableLogic').bootstrapTable('removeByUniqueId',row.id);
}

function queryAttrLogic2d(layerItem){
	queryCatalog=2;//逻辑查询
	initFields(layerItem);
	var data=$('#tableLogic').bootstrapTable('getOptions').data;
	if(!data)return;
	//验证逻辑
	var validate=true;
	var sqls=[];
	for(var i=0;i<data.length;i++){
		var row=data[i];
		var sql;
		if(data.length>1 && i<=data.length-2){
			if(!row.fieldLogic){
				validate=false;
				break;
			}
			sql=row.fieldName+' '+row.logicOpt+' '+row.logicVal+' ' +row.fieldLogic+' ';
		}else{
			sql=row.fieldName+' '+row.logicOpt+' '+row.logicVal +' ';
		}
		sqls.push(sql);
	}
	if(!validate){
		showAlertDialog('查询逻辑不正确，请重试');
		return;
	}
	var sqlWhere=sqls.join("");
	query(layerItem.url,sqlWhere);
}

/**===========================属性查询======================================*/

/**
 * 初始化一些字段
 * @param layerItem
 * @returns
 */
function initFields(layerItem){
	nameField=layerItem.nameField;
	summaryFields=layerItem.summaryFields;
	displayFields=layerItem.displayFields;
	businessType = layerItem.businessType;
}
/**
 * 属性查询
 * @param layerUrl 图层url
 * @param fieldName 字段名
 * @param fieldType 字段类型
 * @param fieldValue 字段值
 * @returns
 */
function queryAttr2d(layerItem,fieldName,fieldType,fieldValue){
	queryCatalog=0;//属性查询
	initFields(layerItem);
	var opt=" = ";
	if(fieldType=="esriFieldTypeString"){
		opt=" LIKE ";
		fieldValue="'%"+fieldValue+"%'";
	}else if(fieldType=="esriFieldTypeDate"){
		fieldValue="date '"+fieldValue+"'";
	}
	var sqlWhere=fieldName + opt + fieldValue;
	query(layerItem.url,sqlWhere);
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
	clear2dMap();
	arrQueryInfoItem=[];
	
	//获取要素的信息
	var displayFieldName=featureSet.displayFieldName;
	var fieldAliases=featureSet.fieldAliases;
	var geometryType=featureSet.geometryType;
	var features=featureSet.features;
	arrFeaturesTemp=features;
	
	//弹出内容
	var info=createTemplateContent(displayFields,fieldAliases);
	//显示查询结果图层
	var tempLayer=new esri.layers.GraphicsLayer({id:'queryLyr'});
	var markerLyr=new esri.layers.GraphicsLayer({id:'markerLyr'});
	markerLyr.on('click',onGraphicClick);
	for(var i=0,n=1;i<features.length;i++,n++){
		var feature=features[i];
		feature.businessType = businessType;
		//赋值属性内容弹窗时使用
		feature.infoBody = info;
		//分页条目信息
		var li=createItem(feature,n,fieldAliases);
		arrQueryInfoItem.push(li);
		if(n >= pageSize){
			n=0;
		}
	}
	map.addLayers([tempLayer,markerLyr]);
	//显示属性查询页
	//initPager(arrQueryInfoItem,pageDiv,resultNumDiv,backDiv,mainDiv)
	if(queryCatalog==0){
		var opt={
				pageId:"#Pagination",
				queryNumId:"#queryNum",
				backId:"#btnBack",
				headerId:"#Sxcxbox",
				resultId:"#Sxcxbox-result"
		}
		$(opt.headerId).css('display','none');
		$(opt.resultId).css('display','block');
		initPager(arrQueryInfoItem,opt);
	}else if(queryCatalog==2){
		var opt={
				pageId:"#PaginationLogic",
				queryNumId:"#queryLogicNum",
				backId:"#btnLogicBack",
				headerId:"#logicBody",
				resultId:"#Sxcxbox-logic"
		}
		$(opt.headerId).css('display','none');
		$(opt.resultId).css('display','block');
		initPager(arrQueryInfoItem,opt);
	}
}

function onGraphicClick(evt){
	evt.preventDefault();
	map.infoWindow.markerSymbol.outline.color=new esri.Color([0,0,0,0]);
	var graphic=evt.graphic;
	var url=graphic.symbol.url;
	var index=url.substring(url.lastIndexOf("_")+1,url.length-4);
	//自定义弹窗
	popupWindow(graphic,evt.mapPoint);
	//地图标注高亮
	lightMarkerFromLyrFeature(graphic.attributes.OBJECTID);
	//属性列表高亮
	$('.no-'+index).parent().toggleClass('active').siblings().removeClass('active');
}
/**
 * 构建弹窗内容
 * @param displayFieldsStr 属性字段字符串，分号相隔
 * @param fieldAliasesObj 要素别名对象
 * @returns
 */
function createTemplateContent(displayFieldsStr,fieldAliasesObj){
	//别名和字段名数组
	var arrDisplayFieldsAlias=[],html;
	var arrDisplayFields=[];
	if(displayFieldsStr){
		arrDisplayFieldsAlias=displayFieldsStr.split(",");
		for(var p=0;p<arrDisplayFieldsAlias.length;p++){
			$.each(fieldAliasesObj,function(o,v){//o为字段名,v为别名
				if(v==arrDisplayFieldsAlias[p]){
					arrDisplayFields.push(o);
				}
			});
		}
	}else if(fieldAliasesObj){
		$.each(fieldAliasesObj,function(o,v){//o为字段名,v为别名
			arrDisplayFieldsAlias.push(v);
			arrDisplayFields.push(o);
		})
	}else{
		return "${*}";
	}
	//构建模板内容
	var arr=[];
	var len=arrDisplayFields.length;
	if(len>6){//属性框风格
		arr.push("<table id='attrTb'");
		for(var i=0;i<arrDisplayFields.length;i++){
			arr.push("<tr><td class='attrTd'>"+arrDisplayFieldsAlias[i]+"</td><td class='attrTd2'>${"+arrDisplayFields[i]+"}</td></tr>")
		}
		arr.push("</table>");
	}else{//普通表单风格
		arr.push("<table class='attrPopFm'>");
		for(var i=0;i<arrDisplayFields.length;i++){
			arr.push("<tr><td class='attrTdFm1'>"+arrDisplayFieldsAlias[i]+"</td><td class='attrTdFm2'>：</td><td class='attrTdFm3'>${"+arrDisplayFields[i]+"}</td></tr>")
		}
		arr.push("</table>");
	}
	html=arr.join("");
	return html;
}

/**
 * 创建分页条目内容.属性查询中attributes没有采用别名，需转换
 * @param feature
 * @param index
 * @param fieldAliases
 * @returns
 */
function createItem(feature,index,fieldAliasesObj){//
	//名称信息
	var title;
	$.each(fieldAliasesObj,function(o,v){//o为字段名,v为别名
		if(v==nameField){
			title=feature.attributes[o];
		}
	});
	
	//简略显示的字段数组
	var arrSummaryFields=[];
	var summary=[];
	if(summaryFields){
		arrSummaryFields=summaryFields.split(",");
		for(var j=0;j<arrSummaryFields.length;j++){
			var name=arrSummaryFields[j];//别名
			$.each(fieldAliasesObj,function(o,v){//o为字段名,v为别名
				if(v==name){
					summary.push(feature.attributes[o]);
				}
			})
			
		}
	}
	summary=summary.join("—");
	var html="<li onclick='toFeature("+[feature.attributes.OBJECTID,index]+")'><i class='no-"+index+"'></i><a href='#'>"+title+"</a><span>"+summary+"</span></li>";
	return html;
}

/**
 * 分页公用函数
 * @param arrQueryInfoItem 查询结果条目
 * @param pageDiv 分页#id
 * @param resultNumDiv 结果条数#id
 * @param backDiv 返回#id
 * @param mainContainerDiv 最外层#id
 * @returns
 */
function initPager(arrQueryInfoItem,opt,callback){
	var fnCallback=callback?callback:pageCallback;
	var initPagination = function() {
        var num_entries = arrQueryInfoItem.length;
        // 创建分页
        $(opt.pageId).pagination(num_entries, {
          num_edge_entries: 1, //边缘页数
          num_display_entries: 4, //主体页数
          callback: fnCallback,
          items_per_page:pageSize//每页显示1项
        });
       }();
       //初始化第一页
       fnCallback(0);
       $(opt.queryNumId).text(arrQueryInfoItem.length);
       $(opt.backId).on('click',function(){
    	   $(opt.resultId).css('display','none');
    	   $(opt.headerId).css('display','block');
       });
}

function pageCallback(page_index, jq){
	clearResult();
    var new_content;
    if(queryCatalog==1){
    	new_content = createPageContentSpatial(page_index);
    }else{
    	new_content = createPageContent(page_index);
    }
    if(queryCatalog==0){
    	$("#queryItem").empty().append(new_content);
    }else if(queryCatalog==2){
        $("#queryItemLogic").empty().append(new_content);
    }else{
    	$("#queryItemGeo").empty().append(new_content);
    }
    return false;

  }

/**
 * 获取分页内容
 * @param pageIndex
 * @returns
 */
function createPageContent(pageIndex){
	if(!pageSize) pageSize=10;//每页显示5条
	var _start=pageIndex*pageSize;
	var _end=_start+(pageSize-1);
	
	//图标代号
	var pIndx=1;
	var http=path+"/static/dist/img/map/icon_features_";
	var resultLyr=map.getLayer("queryLyr");
	var markerLyr=map.getLayer("markerLyr");
	var arrTemp=[];
	var arrFeaturePerPage=[];
	for(var i=_start;i<=_end;i++){
		if(i<arrQueryInfoItem.length){
			arrTemp.push(arrQueryInfoItem[i]);
			var url=http+pIndx+".png";
			var symbol=getPicSymbol(url);
			//显示一页中的要素对象.
			var feature=arrFeaturesTemp[i];
			var geoType=feature.geometry.type;
			var style=getSymbol(geoType,true);
			feature.setSymbol(style);
			resultLyr.add(feature)
			
			var center=getGeomoetryCenter(feature);
			var graphic=new esri.Graphic(center,symbol);
			graphic.setAttributes(feature.attributes);
			graphic.infoBody = feature.infoBody;
			graphic.businessType = feature.businessType;
			markerLyr.add(graphic);
			arrFeaturePerPage.push(feature);
			pIndx++;
		}
		
	}
	//传递要素给3纬处理
	graphicsConvertor(arrFeaturePerPage,false);
	fromArcgisTo3dScene(arrFeaturePerPage,true);
	return arrTemp.join("");
}

function createPageContentSpatial(pageIndex){
	if(!pageSize) pageSize=10;//每页显示5条
	var _start=pageIndex*pageSize;
	var _end=_start+(pageSize-1);
	
	//图标代号
	var pIndx=1;
	var http=path+"/static/dist/img/map/icon_features_";
	var pointLyr=map.getLayer("iPointLyr");
	var polylineLyr=map.getLayer("iPolylineLyr");
	var polygonLyr=map.getLayer("iPolygonLyr");
	var markerLyr=map.getLayer("markerLyr");
	var arrTemp=[];
	var arrFeaturePerPage=[];
	for(var i=_start;i<=_end;i++){
		if(i<arrQueryInfoItem.length){
			arrTemp.push(arrQueryInfoItem[i]);
			var url=http+pIndx+".png";
			var symbol=getPicSymbol(url);
			//显示一页中的要素对象.
			var feature=arrFeaturesTemp[i];
			var geoType=feature.geometry.type;
			var style=getSymbol(geoType,true);
			if(geoType.indexOf("point")>-1){
				feature.setSymbol(style);
				pointLyr.add(feature)
			}else if(geoType.indexOf("polyline")>-1){
				feature.setSymbol(style);
				polylineLyr.add(feature);
			}else{
				feature.setSymbol(style);
				polygonLyr.add(feature);
			}
			var center=getGeomoetryCenter(feature);
			var graphic=new esri.Graphic(center,symbol);
			graphic.setAttributes(feature.attributes);
			graphic.infoBody = feature.infoBody;
			graphic.businessType = feature.businessType;
			markerLyr.add(graphic);
			arrFeaturePerPage.push(feature);
			pIndx++;
		}
		
	}
	//传递要素给3纬处理
	graphicsConvertor(arrFeaturePerPage);
	fromArcgisTo3dScene(arrFeaturePerPage,true);
	return arrTemp.join("");
}
/**
 * 导航到地图中对应的要素
 * @param featureObjId
 * @returns
 */
function toFeature(featureObjId,index){
	var targetFeature;
	for(var i=0;i<arrFeaturesTemp.length;i++){
		var feature=arrFeaturesTemp[i];
		if(feature.attributes.OBJECTID==featureObjId){
			targetFeature=feature;
			break;
		}
	}
	//居中放大显示
	centerAtGraphic(targetFeature);
	//高亮效果
	$('.no-'+index).parent().toggleClass('active').siblings().removeClass('active');
	if(mapOpt == 2 || mapOpt == 0){
		lightMarkerFromLyrFeature(featureObjId);
	}else if(mapOpt == 3 || mapOpt == 0){
		navigateToSceneFeature(featureObjId);
	}
}
/**
 * 高亮某一个图标元素
 * @param lyrFeature 图层中的某个要素
 * @returns
 */
function lightMarkerFromLyrFeature(objectId){
	var markerLyr=map.getLayer("markerLyr");
	var graphics=markerLyr.graphics;
	$.each(graphics,function(i,g){
		var url=g.symbol.url;
		var index=url.substring(url.lastIndexOf("_")+1,url.length-4);
		if(g.attributes.OBJECTID==objectId){
			if($.isNumeric(index)){//由红置蓝
				url=url.substr(0,url.length-4)+"h.png";
				g.setSymbol(g.symbol.setUrl(url));
			}
		}else{//全部置红
			if(!$.isNumeric(index)){
				index=index.substring(0,index.length-1);
				url=url.substring(0,url.lastIndexOf("_")+1)+index+".png";
				g.setSymbol(g.symbol.setUrl(url));
			}
		}
	});
}
function lightMarkerFromGraphic(graphic){
	var g=graphic;
	var url=g.symbol.url;
	var index=url.substring(url.lastIndexOf("_")+1,url.length-4);
	if($.isNumeric(index)){//由红置蓝
		url=url.substr(0,url.length-4)+"h.png";
		g.symbol.setUrl(url);
	}
}
function centerAtGraphic(graphic){
	var deferred=$.Deferred();
	if(graphic.geometry.type!="point"){
		var _extent=graphic.geometry.getExtent();
		deferred =map.setExtent(_extent);
	}else{
		var point=getGeomoetryCenter(graphic);
		deferred =map.centerAndZoom(point,12);
	}
	return deferred;
}
function getGeomoetryCenter(feature){
	var geoType=feature.geometry.type;
	var point;
	if(geoType.indexOf("point")>-1){
		point=feature.geometry;
	}else if(geoType.indexOf("polygon")>-1){
		point=feature.geometry.getExtent().getCenter();
	}else{
		var len=feature.geometry.paths.length;
		var pathIndex,pointIndex;
		if(len%2==0){
			pathIndex=len/2-1;
			pointIndex=feature.geometry.paths[pathIndex].length-1;
		}else{
			pathIndex=(len/2).toFixed()-1;
			pointIndex=((feature.geometry.paths[pathIndex].length)/2).toFixed();
		}
		point=feature.geometry.getPoint(pathIndex,pointIndex);
	}
	return point;
}

/**
 * 弹窗
 * @param graphic 要被弹窗展示的要素
 * @param mapPoint 弹窗位置
 */
function popupWindow(graphic,mapPoint){
	var type=graphic.geometry.type;
	
	//根据图层业务类型设置不同的弹窗信息
	var businessType = graphic.businessType;
	if(businessType == "video"){
		var link = graphic.attributes["Link"];
		var resource = mapConfig.realIpPort + (link?link:graphic.attributes["链接"]); 
		var data = {resourcePath:resource};
		var url = path+'/static/popup/map_videojk_fullscreen.html';
		popPlayer(graphic.attributes.Name_CHN ,url,"video",popWindConfig.video.width,popWindConfig.video.height,data);
	}else if(businessType == "scene"){
		var link = graphic.attributes["Link"];
		var url = mapConfig.realIpPort + (link?link:graphic.attributes["链接"]); 
		popPlayer(graphic.attributes.Name_CHN,url,"panorama",popWindConfig.scene.width,popWindConfig.scene.height);
	}else{
		graphic.setInfoTemplate(new esri.InfoTemplate("信息",graphic.infoBody));
		map.infoWindow.setFeatures([graphic]);
		map.infoWindow.markerSymbol.outline.color=new esri.Color([0,0,0,0]);
		map.infoWindow.fillSymbol.outline.color=new esri.Color([0,255,255,0.4]);
		map.infoWindow.lineSymbol.color=new esri.Color([0,255,255,0.4]);
		map.infoWindow.show(mapPoint,esri.dijit.InfoWindow.ANCHOR_UPPERRIGHT);
	}
}

function failCall(){
	
}

/**===========================空间查询======================================*/
var arrNameField=[];//多个图层的要素名称字段数组
var arrSummaryFields=[];//多个图层的概要组合字段数组
var arrDisplayFields=[];//多个图层的用于地图上属性表显示的字段数组
var arrService=[];//树根节点级别数组，属性包含根节点url以及子节点（图层）的配置字段
//var arrFeaturesTemp;

function queryPoint2d(){
	draw.activate(esri.toolbars.Draw.POINT);
	toDraw(draw);
}
function queryPolyline2d(){
	draw.activate(esri.toolbars.Draw.POLYLINE);
	toDraw(draw);
}
function queryPolygon2d(){
	draw.activate(esri.toolbars.Draw.POLYGON);
	toDraw(draw);
}
function getQueryServiceArr(){
	var zTree = $.fn.zTree.getZTreeObj("treeMaptcgl"),
	nodes = zTree.getCheckedNodes();
	if(nodes.length<1)return null;
	
	arrService=[];
	var rootes=getRootNodes(nodes);
	for(var n=0;n<rootes.length;n++){
		var parent=rootes[n];
		var root={
				url:parent.address,
				ids:[],
				layerLevel:[]
		}
		var children=parent.children;
		for(var p=0;p<children.length;p++){
			var child=children[p];
			if(!child.checked) continue;
			var address=child.address;
			var id = address.substr(address.lastIndexOf("/")+1);
			var fieldObj={//图层级别展示字段对象
					id:id,
					nameField:child.nameField,
					summaryFields:child.summaryFields,
					displayFields:child.displayFields,
					businessType:child.businessType
			}
			root.ids.push(id);
			root.layerLevel.push(fieldObj);
		}
		arrService.push(root);
	}
	return arrService;
}
//获取所有选中的一级节点
function getRootNodes(selectedNodes){
	var nodes=selectedNodes;
	var roots=[];
	for(var i=0;i<nodes.length;i++){
		var node=nodes[i];
		if(node.type=='r'){
			roots.push(node);
		}
	}
	return roots;
}
function getQueryDefereds(geometry){
	var services=getQueryServiceArr();
	var deferreds=[];
	if(services && services.length>0){
		for(var i=0;i<services.length;i++){
			var service=services[i];
			deferreds.push(doQueryGeometry(service,geometry));
		}
	}else{
		showAlertDialog("当前没有可用于查询的图层");
		return ;
	}
	var all= new dojo.DeferredList(deferreds);
	return all;
}
function doQueryGeometry(service,geometry){
	var task=new esri.tasks.IdentifyTask(service.url);
	var params=new esri.tasks.IdentifyParameters();
	params.geometry=geometry;
	params.layerIds=service.ids;
	params.layerOption=esri.tasks.IdentifyParameters.LAYER_OPTION_VISIBLE;
	params.mapExtent=map.extent;
	params.returnGeometry=true;
	params.tolerance=3;
	return task.execute(params);
}
function toDraw(draw){
	clear2dMap();
	map.setMapCursor("crosshair");
	draw.on('draw-complete',onDrawEnd);
}

function onDrawEnd(geometryObj){//geometryObj对象含geometry属性
	queryCatalog=1;//空间查询
	map.setMapCursor("wait");
	var deferredList=getQueryDefereds(geometryObj.geometry);
	if(deferredList){
		deferredList.then(dealResults);
	}
	map.setMapCursor("default");
	draw.deactivate();
}

/**
 * 显示空间查询结果
 * @param results
 * @returns
 */
function dealResults(results){
	console.log(results);
	arrFeaturesTemp=[];
	arrQueryInfoItem=[];
	var pointLyr=new esri.layers.GraphicsLayer({id:"iPointLyr"});
	var polylineLyr=new esri.layers.GraphicsLayer({id:"iPolylineLyr"});
	var polygonLyr=new esri.layers.GraphicsLayer({id:"iPolygonLyr"});
	var markerLyr=new esri.layers.GraphicsLayer({id:'markerLyr'});
	markerLyr.on('click',onGraphicClick);
	map.addLayers([polygonLyr,polylineLyr,pointLyr,markerLyr]);
	var p=1;
	for(var i=0;i<results.length;i++){//服务级别
		var result=results[i];
		if(!result[0]) continue;
		var featureSet=result[1];
		var layerArr=arrService[i].layerLevel;
		for(var n=0;n<featureSet.length;n++,p++){//图层级别:所有图层
			var featureObj=featureSet[n];//图层下的要素级别
			var feature=featureObj.feature;
			var geoType=feature.geometry.type
			var layerName=featureObj.layerName;
			var layerId=featureObj.layerId;
			feature.attributes.layerName=layerName;

			//遍历一个服务下面所有图层，找该要素对应的层的配置字段
			for(var j=0;j<layerArr.length;j++){
				var layerObj=layerArr[j];
				if(layerId==layerObj.id){
					nameField=layerObj.nameField;
					summaryFields=layerObj.summaryFields;
					displayFields=layerObj.displayFields;
					feature.businessType=layerObj.businessType;
					break;
				}
			}
			
			//查询列表展示
			var li=createItemSpatial(feature,p);
			arrQueryInfoItem.push(li);
			if(p >= pageSize){
				p=0;
			}

			var plateInfo=createTemplateInfo(displayFields);
			feature.infoBody = plateInfo;
			arrFeaturesTemp.push(feature);
		}
	}
	var opt={
			pageId:"#PaginationGeo",
			queryNumId:"#queryGeoNum",
			backId:"#btnGeoBack",
			headerId:"#spatialBody",
			resultId:"#Sxcxbox-geometry"
	}
	$(opt.headerId).css('display','none');
	$(opt.resultId).css('display','block');
	initPager(arrQueryInfoItem,opt);
}

function createItemSpatial(feature,index){
	//名称信息
	var title=feature.attributes[nameField];
	var layerName=feature.attributes.layerName;
	if(layerName){
		summaryFields="layerName,"+summaryFields;
	}
	//简略显示的字段数组
	var arrSummaryFields=[];
	var summary=[];
	if(summaryFields){
		arrSummaryFields=summaryFields.split(",");
		for(var j=0;j<arrSummaryFields.length;j++){
			var name=arrSummaryFields[j];
			summary.push(feature.attributes[name]);
		}
		summary=summary.join("—");
	}else{
		summary="";
	}
	var html="<li onclick='toFeature("+[feature.attributes.OBJECTID,index]+")'><i class='no-"+index+"'></i><a href='#'>"+title+"</a><span>"+summary+"</span></li>";
	return html;
}

//没有别名对象的情况(空间查询的属性键采用别名)
function createTemplateInfo(displayFieldsStr,fields){
	//构建模板内容.有配置字段显示配置字段，否则显示所有字段
	if(!displayFieldsStr){
		if(fields){
			var html=["<table id='attrTb'"];
			$.each(fields,function(i,field){
				html.push("<tr><td class='attrTd'>"+field.name+"</td><td>${"+field.name+"}</td></tr>");
			});
			html.push("</table>");
			return html.join("");
		}else{
			return "${*}";
		}
	}
	var arrDisplayFields=displayFieldsStr.split(",");
	var arr=[];
	if(arrDisplayFields.length>6){
		arr.push("<table id='attrTb'>")
		arr.push("<tr><td class='attrTd'>字段</td><td>值</td></tr>")
		for(var i=0;i<arrDisplayFields.length;i++){
			arr.push("<tr><td class='attrTd'>"+arrDisplayFields[i]+"</td><td>${"+arrDisplayFields[i]+"}</td></tr>")
		}
		arr.push("</table>");
	}else{
		arr.push("<table class='attrPopFm'>");
		for(var i=0;i<arrDisplayFields.length;i++){
			arr.push("<tr><td class='attrTdFm1'>"+arrDisplayFields[i]+"</td><td class='attrTdFm2'>：</td><td class='attrTdFm3'>${"+arrDisplayFields[i]+"}</td></tr>")
		}
	}
	
	return arr.join("");
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
	var selectField=$("#queryFieldsLst")[0];
	selectField.length=0;
	if(layerItem){
		var layer=map.getLayer(layerItem.value);
		doListFields(layer,selectField);
	}
}
/**
 * 逻辑查询图层字段
 * @returns
 */
function listFieldsLogic(){
	var select=$('#queryLyrLogic')[0];
	var layerItem=select.options[select.selectedIndex];
	if(layerItem){
		var layer=map.getLayer(layerItem.value);
		var selectField=$("#queryFieldsLogic")[0];
		selectField.length=0;
		doListFields(layer,selectField);
	}
}

