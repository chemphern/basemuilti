/********************************空间分析***********************************/
var gp;			//GP服务
var overlayFeatures; //叠加的要素集合对象
var aLyrIds="aPgLyr,aPlLyr,aPtLyr,aMkLyr";
var drawTypes = "POINT,POLINE,EXTENT,CIRCLE,POLYGON".split(","); //框选全局变量

$(function(){
	$("#PaintOption a").click(function(){
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
	case "POLYGON","EXTENT":
		draw.setFillSymbol(getSymbol(drawType));
		break;
	}
	draw.activate(esri.toolbars.Draw[drawType]);
}

function analysisDrawCallback(geoObj){
	draw.deactivate();
	//框选图形显示
	var geo=geoObj.geometry;
	var type = geo.type;
	if(type == "extent"){
		geo=esri.geometry.Polygon.fromExtent(geo)
	}
	var symbol=getSymbol(type);
	if(!map.getLayer("aMkLyr")){
		map.addLayer(new esri.layers.GraphicsLayer({id:"aMkLyr"}));
	}
	//预加载结果图层
	var lyrs=aLyrIds.split(",");
	$.each(lyrs,function(i,id){
		if(!map.getLayer(id)){
			map.addLayer(new esri.layers.GraphicsLayer({id:id}));
		}
	});
	map.getLayer('aMkLyr').add(new esri.Graphic(geo,symbol));
	map.setMapCursor("wait");
	
	//叠加要素
	var featureSetObj={
		geometryType:"esriGeometryPolygon",
		spatialReference:map.spatialReference,
		fields:[{
			name:"ObjectID",
			alias:"ObjectID",
			type:"esriFieldTypeOID"
		}],
		features:[new esri.Graphic(geo,null,{"OBJECTID":"1"},null)],
	};
	overlayFeatures = new esri.tasks.FeatureSet(featureSetObj);
	
	//先执行空间查询，减少无关数据
	var lyrs=getVisibleFeatureLyr();
	var deferreds=[];
	$.each(lyrs,function(i,lyr){
		deferreds.push(preQuery(lyr.url,geo));
	});
	var deferredList=new dojo.DeferredList(deferreds);
	deferredList.then(preAnalysis);
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
	var nextStep = true;
	$.each(featureSetArr,function(i,featureSetObj){
		var featureSet=featureSetObj[1];
		if(featureSet.features.length>10000){
			nextStep = false;
			return false;
		}
	});
	if(!nextStep) return;
	//执行分析
	postAnalysis(featureSetArr);
}

/********执行叠加分析********/
function postAnalysis(featureSetArr){
	gp=new esri.tasks.Geoprocessor(mapConfig.intersectService);
	gp.setOutSpatialReference(map.spatialReference);
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
	console.log("results:");
	console.log(results);
	map.setMapCursor("default");
	if(!$.isArray(results[0])) return;
	
	$.each(results,function(i,result){//图层级别
		var obj=result[1];
		gp.getResultData(obj.jobId,"output",function(data){
			console.log(data);
			var features=data.value.features;
			var geoType=data.value.geometryType;
			var lyr;
			if(geoType.indexOf("Point")>-1){
				lyr=map.getLayer("aPtLyr");
			}else if(geoType.indexOf("Polyline")>-1){
				lyr=map.getLayer("aPlLyr");
			}else{
				lyr=map.getLayer("aPgLyr");
			}
			$.each(features,function(j,feature){//要素级别
				var symbol=getSymbol(geoType,true);
				feature.setSymbol(symbol);
				setAnalysisTemplateInfo(feature);
				lyr.add(feature);
			});
		},function(error){
			
		});
	})
}

/*****结果弹窗信息*/
function setAnalysisTemplateInfo(feature){
	var arr = ["<table id='attrTb'"];
	$.each(feature,function(k,v){
		for(var i=0;i<arrDisplayFields.length;i++){
			arr.push("<tr><td class='attrTd'>"+ k +"</td><td class='attrTd2'>${"+ v +"}</td></tr>");
		}
	});
	arr.push("</table>");
	return arr.join("");
}

function getVisibleFeatureLyr(){
	var vlyrs=map.getLayersVisibleAtScale();
	var lyrs=[];
	$.each(vlyrs,function(i,lyr){
		var id=lyr.id;
		var url=lyr.url;
		//自定义id,不查地图以及图形图层
		if($.isNumeric(id) && lyr.visible){
			lyrs.push(lyr);
		}
	});
	return lyrs;
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
