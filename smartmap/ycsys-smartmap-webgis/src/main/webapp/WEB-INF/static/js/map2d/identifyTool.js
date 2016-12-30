$(function(){
	$("#btnIdentify").click(function(){
		clear2dMap();
		//若外部需要查询结果数据，只需提供回调函数
		IdentifyTool.identify(null);
	});
});

IdentifyTool={
	_map:null,
	_draw:null,
	isLoaded:false,
	_callback:null,
	
	init:function(map){
		this._map=map;
		isLoaded=true;
		this._draw=new esri.toolbars.Draw(map);
	},
	identify:function(callbackFunc){
		this.clear();
		this._map.setMapCursor("crosshair");
		this._map.disablePan();
		this._draw.activate(esri.toolbars.Draw.EXTENT);
		this._draw.on('draw-complete',this._onDrawEnd);
		if(callbackFunc){
			_callback=callbackFunc;
		}
	},
	clear:function(){
		var plyr=this._map.getLayer('iPtLyr');
		var llyr=this._map.getLayer('iPlLyr');
		var glyr=this._map.getLayer('iPgLyr');
		var ilyr=this._map.getLayer('iMkLyr');
		if(plyr) this._map.removeLayer(plyr);
		if(llyr) this._map.removeLayer(llyr);
		if(glyr) this._map.removeLayer(glyr);
		if(ilyr) this._map.removeLayer(ilyr);
	},
	_onDrawEnd:function(e){
		queryCatalog=3;//I查询
		IdentifyTool._map.enablePan();
		var lyrs=IdentifyTool._map.getLayersVisibleAtScale();
		var deferreds=[];
		$.each(lyrs,function(i,lyr){
			var id=lyr.id;
			var url=lyr.url;
			if($.isNumeric(id) && lyr.visible){//自定义id
				deferreds.push(IdentifyTool._doTask(url,e.geometry));
			}
		});
		var deferredList=new dojo.DeferredList(deferreds);
		deferredList.then(IdentifyTool._dealResults);
		IdentifyTool._map.setMapCursor("default");
		IdentifyTool._draw.deactivate();
	},
	_doTask:function(url,geometry){
		var task=new esri.tasks.IdentifyTask(url);
		var params=new esri.tasks.IdentifyParameters();
		params.geometry=geometry;
		params.layerOption=esri.tasks.IdentifyParameters.LAYER_OPTION_VISIBLE;
		params.mapExtent=IdentifyTool._map.extent;
		params.returnGeometry=true;
		params.tolerance=3;
		return task.execute(params);
	},
	_dealResults:function(results){
		//结果预处理
		var large=false;
		$.each(results,function(i,arr){
			var count=arr[1].length;
			if(count>500){
				showAlertDialog('当前查询结果数据量较大，请指定合理的查询范围');
				large=true;
				return false;
			}
		});
		if(large) return;
		
		var features=[];
		var url=path+"/static/dist/img/map/icon_feature_0.png";
		var pointLyr=new esri.layers.GraphicsLayer({id:"iPtLyr"});
		var polylineLyr=new esri.layers.GraphicsLayer({id:"iPlLyr"});
		var polygonLyr=new esri.layers.GraphicsLayer({id:"iPgLyr"});
		var markerLyr=new esri.layers.GraphicsLayer({id:'iMkLyr'});
		markerLyr.on('click',IdentifyTool._onGraphicClick);
		map.addLayers([polygonLyr,polylineLyr,pointLyr,markerLyr]);
		for(var i=0;i<results.length;i++){
			var result=results[i];
			if(!result[0]) continue;
			var featureSet=result[1];
			for(var j=0;j<featureSet.length;j++){
				var featureObj=featureSet[j];
				var feature=featureObj.feature;
				var geoType=feature.geometry.type;
				var style=getSymbol(geoType,true);//未封装的方法
				var template=IdentifyTool._createInfoTemplate(feature);
				
				features.push(feature);
				feature.setInfoTemplate(template);
				if(geoType.indexOf('point')>-1){
					feature.setSymbol(style);
					pointLyr.add(feature);
				}else if(geoType.indexOf('polyline')>-1){
					feature.setSymbol(style);
					polylineLyr.add(feature);
				}else if(geoType.indexOf('polygon')>-1){
					feature.setSymbol(style);
					polygonLyr.add(featuer);
				}
				var symbol=getPicSymbol(url);//未封装的方法
				var center=getGeomoetryCenter(feature);//未封装的方法
				var graphic=new esri.Graphic(center,symbol);
				graphic.setAttributes(feature.attributes);
				graphic.setInfoTemplate(feature.infoTemplate);
				markerLyr.add(graphic);
			}
		}
		//回调函数，向外部提供查询结果
		if(IdentifyTool._callback){
			IdentifyTool._callback(features);
		}
	},
	_createInfoTemplate:function(feature){
		var html=[];
		html.push("<table id='attrTb'");
		$.each(feature.attributes,function(k,v){
			html.push("<tr><td class='attrTd'>"+k+"</td><td class='attrTd2'>"+v+"</td></tr>");
		});
		html.push("</table>");
		html=html.join('');
		var template=new esri.InfoTemplate('信息',html);
		return template;
	},
	_onGraphicClick:function(e){
		e.preventDefault();
		IdentifyTool._map.infoWindow.markerSymbol.outline.color=new esri.Color([0,0,0,0]);
		var url=path+"/static/dist/img/map/icon_feature_0.png";
		var selUrl=path+"/static/dist/img/map/icon_feature_1.png";
		var markLyr=IdentifyTool._map.getLayer('iMkLyr');
		var graphics=markLyr.graphics;
		$.each(graphics,function(i,g){
			if(g==e.graphic){
				g.setSymbol(g.symbol.setUrl(selUrl));
			}else{
				g.setSymbol(g.symbol.setUrl(url));
			}
		});
	}
		
}