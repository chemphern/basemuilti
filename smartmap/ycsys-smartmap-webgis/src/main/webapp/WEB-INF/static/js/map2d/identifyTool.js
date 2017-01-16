$(function(){
	$("#btnIdentify").click(function(){
		clear2dMap();
		if(mapOpt==2){
			//二维调用，若外部需要查询结果数据，只需提供回调函数(返回参数为feature集合)
			IdentifyTool.identify();
		}else{
			//三维调用方式
            identifySpatialQuery3d();
		}
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
		IdentifyTool._map.setMapCursor("wait");
		queryCatalog=3;//I查询
		IdentifyTool._map.enablePan();
		var lyrs=IdentifyTool._map.getLayersVisibleAtScale();
		var deferreds=[];
		//获取所有地图服务,区分专题和其他图层。专题图查询地址不是展示图层地址
		var allUrl=[];
		if(modelIndex == 3){
			var tree=$.fn.zTree.getZTreeObj('treeMapzt');
			var selNodes=tree.getSelectedNodes();
			allUrl.push(selNodes[0].queryAddress);
		}else{
			$.each(lyrs,function(i,lyr){
				var id=lyr.id;
				var url=lyr.url;
				if($.isNumeric(id) && lyr.visible){//自定义id,不查地图以及图形图层
					var msUrl=url.substring(0,url.indexOf('MapServer')+9);
					allUrl.push(msUrl);
				}
			});
		}
		var arrMapServerUrl=IdentifyTool._formatToMapServer(allUrl);
		$.each(arrMapServerUrl,function(i,u){
			deferreds.push(IdentifyTool._doTask(u,e.geometry));
		});
		
		var deferredList=new dojo.DeferredList(deferreds);
		deferredList.then(IdentifyTool._dealResults);
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
	_formatToMapServer:function(urls){
		var arr=[];
		$.each(urls,function(i,u){
			if($.inArray(u,arr)<0){
				arr.push(u);
			}
		});
		return arr;
	},
	_dealResults:function(results){
		IdentifyTool._map.setMapCursor("default");
		//结果预处理
		if(!$.isArray(results[0])) return;
		var large=false,all=0;
		$.each(results,function(i,arr){
			var count=arr[1].length;
			if(count>500){
				showAlertDialog('当前查询结果数据量较大，请指定合理的查询范围');
				large=true;
				return false;
			}
			all+=count;
		});
		if(large) return;
		if(all==0) showAlertDialog('没有查询到结果');
		
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
					polygonLyr.add(feature);
				}
				var symbol=getPicSymbol(url);//未封装的方法
				var center=getGeomoetryCenter(feature);//未封装的方法
				var graphic=new esri.Graphic(center,symbol);
				graphic.setAttributes(feature.attributes);
				graphic.setInfoTemplate(feature.infoTemplate);
				markerLyr.add(graphic);
			}
		}
		//向三维提供查询结果
		if(mapOpt!=2)
            fromArcgisTo3dScene(features,false);
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
		var selUrl=path+"/static/dist/img/map/icon_feature_0h.png";
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