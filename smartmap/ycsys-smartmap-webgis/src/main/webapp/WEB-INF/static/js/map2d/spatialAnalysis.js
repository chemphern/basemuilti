/********************************空间分析***********************************/
var gp;			//GP服务
var overlayFeatures; //叠加的要素集合对象
var analysisType;	//分析模式：叠加分析;缓冲分析
var aLyrIds="aPgLyr,aPlLyr,aPtLyr,aMkLyr";
var drawTypes = "POINT,POLYLINE,EXTENT,CIRCLE,POLYGON".split(","); //框选全局变量
var analysisLyrCfgObj = []; //用于分析的图层名称
var analysisResults = []; //分析结果要素数组，如：[[features],[features2]]

$(function(){
	$("#PaintOption a").click(function(){
		analysisType = 'overlay';
		var i = $(this).index();
		var drawType;
		switch (i) {
		case 0:
			drawType = drawTypes[2];
			break;
		case 1:
			drawType = drawTypes[3];
			break;
		case 2:
			drawType = drawTypes[4];
			break;
		}
		toAnalysis(drawType,analysisDrawCallback);
	});
	$("#PaintOptionBuf a").click(function(){
		analysisType = 'buffer';
		var indx = $(this).index();
		var drawType;
		switch (indx) {
		case 0:
			drawType = drawTypes[0];
			break;
		case 1:
			drawType = drawTypes[1];
			break;
		case 2:
			drawType = drawTypes[4];
			break;
		}
		
		var distance = $("#bufDistance").val();
		if(!distance){
			showAlertDialog('请填写缓冲距离');
			return;
		}
		toAnalysis(drawType,analysisDrawCallback);
	});
});

function toAnalysis(drawType,analysisDrawCallback){
	clearAnalysisLyr("clsResult");
	map.setMapCursor("crosshair");
	draw.on('draw-complete',analysisDrawCallback);
	
	switch (drawType) {
	case "POINT":
		draw.setFillSymbol(getSymbol(drawType));
		break;
	case "POLYLINE":
		draw.setLineSymbol(getSymbol(drawType));
		break;
	case "POLYGON","EXTENT","ELLIPSE":
		draw.setFillSymbol(getSymbol(drawType));
		break;
	}
	draw.activate(esri.toolbars.Draw[drawType]);
}

function analysisDrawCallback(geoObj){
	draw.deactivate();
	var geo=geoObj.geometry;
	var geoType = geo.type;
	if(geoType == "extent"){
		geo=esri.geometry.Polygon.fromExtent(geo)
	}
	geoType = geoType == "point"?"esriGeometryPoint":(geoType == "polyline"?"esriGeometryPolyline":"esriGeometryPolygon");
	
	//创建用于分析的要素类
	//1、直接Draw的方式
	var fields = [{name:'ObjectID',alias:'ObjectID',type:'esriFieldTypeOID'}];
	var feature = new esri.Graphic(geo,getSymbol(geoType),{OBJECTID:"1"},null);
	//2、图层选择方式(略....)
	var featureSetObj={
			geometryType:geoType,
			spatialReference:map.spatialReference,
			fields:fields,
			features:[feature],
		};
	overlayFeatures = new esri.tasks.FeatureSet(featureSetObj);
		
	//预加载结果图层
	var lyrs=aLyrIds.split(",");
	$.each(lyrs,function(i,id){
		if(!map.getLayer(id)){
			map.addLayer(new esri.layers.GraphicsLayer({id:id}));
		}
	});
	var pgLyr = map.getLayer('aPgLyr').add(feature);
	var mkLyr = map.getLayer('aMkLyr');
	mkLyr.on('click',function(e){
		e.preventDefault();
		//弹窗展示
		popupWindow(e.graphic,e.graphic.mapPoint);
		//气泡颜色
		onBubbleChange(e.graphic.attributes.OBJECTID);
	})
	map.setMapCursor("wait");
	
	//空间分析类型
	if(analysisType == 'buffer'){
		bufferAnalysis(overlayFeatures);
	}else{
		toDoAnalysis(overlayFeatures);
	}
}

function toDoAnalysis(featureSet){
	var urls=getVisibleFeatureLyr();
	var deferreds=[];
	$.each(urls,function(i,url){
		deferreds.push(preQuery(url,featureSet.features[0].geometry));
	});
	var deferredList=new dojo.DeferredList(deferreds);
	deferredList.then(preAnalysis);
}

/**
 * 缓冲分析
 * @param featureSet 用于缓冲的要素类/图层
 * @returns 要素类
 */
function bufferAnalysis(featureSet){
	var distance = $("#bufDistance").val();
	gp = new esri.tasks.Geoprocessor(mapConfig.bufferService);
	gp.setOutSpatialReference(map.spatialReference);
	gp.setProcessSpatialReference(map.spatialReference)
	var unit = new esri.tasks.LinearUnit();
	unit.distance = Number(distance);
	unit.units = "esriMeters";
	var param={
			input:overlayFeatures,
			distance:unit,
			sideType:"FULL",
			endType:"ROUND",
			dissolveType:"ALL",
			method:"PLANAR",
		}
	var deferred = gp.submitJob(param,function(job){
		if(job.jobStatus == "esriJobFailed"){
			showAlertDialog("缓冲分析失败");
			return false;
		}
		gp.getResultData(job.jobId,"output",function(result){
			var featureSet = result.value;
			overlayFeatures = featureSet;
			//添加缓冲区显示
			var polygonColor=new esri.Color([103,152,7,0.2]);
			var outline=new esri.symbol.SimpleLineSymbol(esri.symbol.SimpleLineSymbol.STYLE_DASH,new esri.Color([91,127,239,0.5]), 2);
			var symbol = new esri.symbol.SimpleFillSymbol(esri.symbol.SimpleFillSymbol.STYLE_SOLID, outline, polygonColor);
			var feature = featureSet.features[0];
			feature.setSymbol(symbol);
			map.getLayer("aPgLyr").add(feature);
			//执行下一步分析
			toDoAnalysis(featureSet);
		});
	});
}

/*******预处理：执行空间查询*/
function preQuery(url,geometry){
	var query=new esri.tasks.Query();
	query.geometry=geometry;
	query.returnGeometry=true;
	query.outFields=["*"];
	var task=new esri.tasks.QueryTask(url);
	return task.execute(query);
}

/******预处理并执行空间分析*/
function preAnalysis(featureSetArr){
	if(!$.isArray(featureSetArr[0])){
		showAlertDialog("查询出错");
		return;
	}
	var total = 0;
	var nextStep = true;
	$.each(featureSetArr,function(i,featureSetObj){
		var featureSet=featureSetObj[1];
		if(featureSet.features.length>50000){
			nextStep = false;
			return false;
		}
		total += featureSet.features.length;
	});
	//数据过大提示缩小分析范围
	if(!nextStep){
		showAlertDialog("当前范围数据量过大，请缩小分析范围");
		map.setMapCursor("default");
		return;
	}
	if(total < 1){
		showAlertDialog("当前范围内没有可供查询的地理要素");
		map.setMapCursor("default");
		return;
	}
	//执行分析
	postAnalysis(featureSetArr);
}

/********执行叠加分析*/
function postAnalysis(featureSetArr){
	gp=new esri.tasks.Geoprocessor(mapConfig.intersectService);
	gp.setOutSpatialReference(map.spatialReference);
	gp.setProcessSpatialReference(map.spatialReference)
	var deferreds=[];
	$.each(featureSetArr,function(i,featureSetObj){
		var featureSet=featureSetObj[1];
		var unit = new esri.tasks.LinearUnit();
		unit.distance = 1;
		unit.units = "esriMeters";
		var param={
				inputLayer:overlayFeatures,
				overlayLayer:featureSet,
				XY_tolerance:unit,
				joinAttribute:"ALL",
				outputType:"INPUT"
			}
		var deferred = gp.submitJob(param);
		deferreds.push(deferred);
	});
	var deferredList=new dojo.DeferredList(deferreds);
	deferredList.then(showAnalysisResult);
}

function showAnalysisResult(results){
	map.setMapCursor("default");
	if(!$.isArray(results[0])) return;
	
	var deferreds = [];
	$.each(results,function(i,result){//图层级别
		var obj=result[1];
		if(obj.jobStatus == "esriJobFailed")return true;
		deferreds.push(gp.getResultData(obj.jobId,"output"));
	});
	var deferredList=new dojo.DeferredList(deferreds);
	deferredList.then(function(datas){
		if(!$.isArray(results[0])){
			showAlertDialog("获取分析结果出错");
			return;
		};
		
		analysisResults = [];
		var mkLyr = map.getLayer("aMkLyr");
		var url=path+"/static/dist/img/map/icon_feature_0.png";
		var symbol=getPicSymbol(url);
		$.each(datas,function(i,data){
			var featureSet = data[1].value;
			var features = featureSet.features;
			var geoType = featureSet.geometryType;
			var style = getSymbol(geoType,true);
			
			var configFields = analysisLyrCfgObj[i].displayFields;
			var templateInfo = createTemplateInfo(configFields,featureSet.fields);
			var lyr;
			if(geoType.indexOf("Point")>-1){
				lyr = map.getLayer("aPtLyr");
			}else if(geotType.indexOf("Polyline")>-1){
				lyr = map.getLayer("aPlLyr");
			}else{
				lyr = map.getLayer("aPgLyr");
			}
			$.each(features,function(i,feature){
				feature.setSymbol(style);
				feature.infoBody = templateInfo;
				lyr.add(feature);
				//气泡要素
				var center=getGeomoetryCenter(feature);
//				var templateInfo = createInfoBody(feature);
				var graphic=new esri.Graphic(center,symbol);
				graphic.setAttributes(feature.attributes);
				graphic.infoBody = feature.infoBody;
				mkLyr.add(graphic);
			});
			
			analysisResults.push(featureSet);
		});
		
		if(analysisResults.length<1){
			showAlertDialog("缓冲区范围内没有可供查询的地理要素");
			return ;
		}
		//属性查看
		popAttributeWindow();
	});
}

function createInfoBodyOnFeature(feature){
	var html=[];
	html.push("<table id='attrTb'");
	$.each(feature.attributes,function(k,v){
		html.push("<tr><td class='attrTd'>"+k+"</td><td class='attrTd2'>"+v+"</td></tr>");
	});
	html.push("</table>");
	html=html.join('');
	return html;
}

function popAttributeWindow(){
	var url = path+'/static/popup/map_dialog_space.html';
	var player = $.dialog.open(url,{
		title: '结果展示',
        id:'popAttrWin',
        url:url,
        width: 950,
        height: 350,
        lock:false,
        opacity:0,
        resize:true
    });
}

function onBubbleChange(featureObjId){
	var url=path+"/static/dist/img/map/icon_feature_0.png";
	var selUrl=path+"/static/dist/img/map/icon_feature_0h.png";
	var markLyr=map.getLayer('aMkLyr');
	var graphics=markLyr.graphics;
	$.each(graphics,function(i,g){
		if(g.attributes.OBJECTID==featureObjId){
			g.setSymbol(g.symbol.setUrl(selUrl));
		}else{
			g.setSymbol(g.symbol.setUrl(url));
		}
	});
}

function getVisibleFeatureLyr(){
	var tree=$.fn.zTree.getZTreeObj('treeMaptcgl');
	var chkNodes = tree.getCheckedNodes(true);
	var allUrl = [];
	$.each(chkNodes,function(i,node){
		if(!node.isParent){
			allUrl.push(node.address);
			var lyrCfgObj = {
				name:node.name,
				displayFields:node.displayFields,
				businessType:node.businessType
			}
			analysisLyrCfgObj.push(lyrCfgObj);
		}
	});
	return allUrl;
}

function clearAnalysisLyr(clearResult){
	var aIds=aLyrIds.split(",");
	if(clearResult){
		$.each(aIds,function(i,id){
			var lyr=map.getLayer(id);
			if(lyr && lyr.graphics.length>0) lyr.clear();
		});
	}else{
		$.each(aIds,function(i,id){
			var lyr=map.getLayer(id);
			if(lyr) map.removeLayer(lyr);
		});
	}
}
