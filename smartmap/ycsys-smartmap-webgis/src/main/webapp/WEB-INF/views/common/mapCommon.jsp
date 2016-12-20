<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String basePath = request.getScheme() + "://" + request.getServerName()+ ":" + request.getServerPort() + request.getContextPath();
	request.setAttribute("path", basePath);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="http://172.16.10.50:8080/arcgis_js_api/3.18/dijit/themes/claro/claro.css" rel="stylesheet">
<link href="http://172.16.10.50:8080/arcgis_js_api/3.18/esri/css/esri.css" rel="stylesheet">
<!-- <link href="http://localhost:8000/arcgis_js_api/library/3.18/3.18/esri/css/esri.css" rel="stylesheet"> -->
<!-- <link href="http://localhost:8000/arcgis_js_api/library/3.18/3.18/dijit/themes/claro/claro.css" rel="stylesheet"> -->
<style type="text/css">
	.arcgisSearch,.searchExpandContainer,.searchInputGroup,.searchInput{width:160px;}
    /*.templatePicker {*/
        /*border: none;*/
    /*}*/
    /*.dj_ie .infowindow .window .top .right .user .content { position: relative; }*/
    /*.dj_ie .simpleInfoWindow .content { position: relative; }*/
</style>
<script type="text/javascript">
        var dojoConfig = {
            async: true,
            packages: ["layerjs","Extension","ExtensionDraw"],
            paths:{
            	layerjs:"${res}/js/map2d",
            	Extension:"${res}/dist/js/map/plot/drawExtension/Extension",
            	ExtensionDraw:"${res}/dist/js/map/plot/drawExtension/plotDraw"
            }
        };			
</script>
<script src="${res}/plugins/jQuery/jquery-2.2.3.min.js"></script>
<!-- cookies -->
<script src="${res}/plugins/jQuery/jquery.cookie.js"></script> 
  <!-- 颜色选择 -->
 <script src="${res}/plugins/jqColorPicker/colors.js"></script>
 <script src="${res}/plugins/jqColorPicker/jqColorPicker.js"></script>
  <script type="text/javascript">
      $('.color').colorPicker(); 
  </script> 
<script src="http://172.16.10.50:8080/arcgis_js_api/3.18/init.js"></script>
<!-- <script src="http://localhost:8000/arcgis_js_api/library/3.18/3.18/init.js"></script> -->
<script src="${res}/js/map2d/tiandituImgLayer.js"></script>
<script type="text/javascript">
	var path="${path}";
	var centerPt=[113.244931,23.115074];
	var map,naviBar,measure,draw,printer,locator,baseLyr,imgLyr,search;
	var NAVI,bookmarks,ArcGISTiledMapServiceLayerLocal;
	var minx,miny,maxx,maxy;
	var lyrList=[];
	var DrawEx,DrawExt;
	
	require([
		  "dojo/parser",
		  "esri/map",
          "esri/layers/ArcGISTiledMapServiceLayer",
		  "esri/tasks/GeometryService",
		  "esri/toolbars/navigation",
		  "dojo/dom",
		  "esri/toolbars/draw",
		  "esri/dijit/OverviewMap",
		  "layerjs/MeasureTools",  
		  "esri/dijit/Print",
		  "esri/tasks/PrintTask", 
		  "esri/tasks/PrintParameters",
		  "esri/tasks/PrintTemplate",
		  "esri/dijit/Scalebar",
		  "esri/dijit/LocateButton",
		  "esri/dijit/Legend",
		  "esri/layers/FeatureLayer",
		  "esri/geometry/Extent",
		  "esri/SpatialReference",
		  "esri/Color",
		  "esri/symbols/SimpleMarkerSymbol",
		  "esri/symbols/SimpleLineSymbol",
		  "esri/symbols/SimpleFillSymbol",
		  "esri/dijit/editing/Editor",
		  "esri/dijit/editing/TemplatePicker",
		  "esri/config",
		  "dojo/i18n!esri/nls/jsapi",
		  "dojo/_base/array",
		  "dojo/keys",
	      "esri/renderers/SimpleRenderer",
		  "esri/layers/ArcGISImageServiceLayer",
	      "esri/tasks/QueryTask",
		  "esri/tasks/query",
		  "esri/InfoTemplate",
		  "esri/geometry/Point",
		  "ExtensionDraw/DrawExt",
		  "Extension/DrawEx",
	      "esri/tasks/locationproviders/QueryTaskLocationProvider",
		  "esri/graphic","esri/dijit/Search",
	      "esri/symbols/PictureMarkerSymbol",
		  "esri/geometry/webMercatorUtils",
		  "esri/dijit/Bookmarks",
		  "esri/dijit/BookmarkItem",
	      "esri/tasks/IdentifyTask",
		  "esri/tasks/IdentifyParameters",
		  "dojo/promise/all",
		  "esri/symbols/PictureMarkerSymbol",
		  "esri/dijit/InfoWindow",
		  "dojo/domReady!"
		], function(parser,Map,ArcGISTiledMapServiceLayer,GeometryService,Navigation,dom,Draw,OverviewMap,MeasureTools,Print,PrintTask,PrintParameters,PrintTemplate,Scalebar,LocateButton,Legend,
					FeatureLayer,Extent,SpatialReference,Color,SimpleMarkerSymbol,SimpleLineSymbol,SimpleFillSymbol,Editor,TemplatePicker,esriConfig,jsapiBundle,arrayUtils,keys,SimpleRenderer,
					ArcGISImageServiceLayer,QueryTask,Query,InfoTemplate,Point,drawExt,drawEx) {
			parser.parse();

			// snapping is enabled for this sample - change the tooltip to reflect this
			jsapiBundle.toolbars.draw.start = jsapiBundle.toolbars.draw.start +  "<br>按住 <b>ALT</b>键启用捕捉";
			// refer to "Using the Proxy Page" for more information:  https://developers.arcgis.com/javascript/3/jshelp/ags_proxy.html
			esriConfig.defaults.io.proxyUrl = "http://172.16.10.50:8080/Java/proxy.jsp";
//            esriConfig.defaults.io.alwaysUseProxy= false;
			//This service is for development and testing purposes only. We recommend that you create your own geometry service for use within your applications.
			esriConfig.defaults.geometryService = new GeometryService("http://172.16.10.52:6080/arcgis/rest/services/Utilities/Geometry/GeometryServer");
			NAVI=Navigation;
			DrawExt=drawExt;
			DrawEx=drawEx;
            ArcGISTiledMapServiceLayerLocal =ArcGISTiledMapServiceLayer;
            map = new Map("map2d",{
			    logo:false,
	            zoom: 8,
	            autoResize:true,
//	            basemap:"streets",
	            displayGraphicsOnPan:false,
	            slider:false,
	            center: centerPt
			  });
//            baseLyr = new TDTLayer();
//            map.addLayer(baseLyr);//天地图地图
//            annoLyr= new TDTAnnoLayer();
//            map.addLayer(annoLyr);//天地图注记图
            baseLyr = new ArcGISTiledMapServiceLayer("http://172.16.10.52:6080/arcgis/rest/services/%E5%B9%BF%E5%B7%9E%E5%B8%82_%E7%9F%A2%E9%87%8F%E5%9C%B0%E5%9B%BE/MapServer");
            map.addLayer(baseLyr);
            imgLyr = new  ArcGISTiledMapServiceLayerLocal("http://172.16.10.52:6080/arcgis/rest/services/%E5%B9%BF%E5%B7%9E%E5%B8%82%E5%BD%B1%E5%83%8F%E5%9C%B0%E5%9B%BE/MapServer");

            map.on("load",init);
// 			map.on("layers-add-result", initEditor);
            //add boundaries and place names
//             var responsePoints = new FeatureLayer("https://sampleserver6.arcgisonline.com/arcgis/rest/services/Wildfire/FeatureServer/0", {
//                 mode: FeatureLayer.MODE_ONDEMAND,
//                 outFields: ['*']
//             });
//             var responsePolys = new FeatureLayer("https://sampleserver6.arcgisonline.com/arcgis/rest/services/Wildfire/FeatureServer/2", {
//                 mode: FeatureLayer.MODE_ONDEMAND,
//                 outFields: ['*']
//             });
//             map.addLayers([responsePolys, responsePoints]);
			function init(){
				map.on("onMouseMove",showCoordinate);
				
				naviBar=new Navigation(map);
				locator=new LocateButton({map:map,visible:false},dom.byId("curPos"));
				locator.startup();
				measure=new MeasureTools({map:map},dom.byId("measureDiv"));
				draw=new Draw(map);
				
				var overView = new OverviewMap({
					map: map,  
		            visible: true,  // 初始化可见，默认为false  
		            opacity: .40,    // 透明度 默认0.5  
		            attachTo:"bottom-right",
		            width:230,
		            height:120,
		            maximizeButton: true,   // 最大化,最小化按钮，默认false  
		            expandFactor: 3,    //概览地图和总览图上显示的程度矩形的大小之间的比例。默认值是2，这意味着概览地图将至少是两倍的大小的程度矩形。  
		            color: "red"});
				overView.startup();
				var scalebar = new Scalebar({map: map,scalebarUnit:"dual"},dom.byId("scaleBar"));
				var legend = new Legend({map: map}, "legendDiv");
  				legend.startup();
  				
  				var symbol=getSymbol('point',true);
  				search=new esri.dijit.Search({
  					sources: [],
//   				addLayersFromMap:true,
  					zoomScale:2308574,
  					visible:false,
  					enableInfoWindow:false,
  					highlightSymbol:symbol,
  					enableLabel:false,
  					map:map
  				},dom.byId("search"));
  				var sources = search.get("sources");
  				 sources.push({
  			         featureLayer: new esri.layers.FeatureLayer("http://172.16.10.52:6080/arcgis/rest/services/%E5%B9%BF%E5%B7%9E%E5%B8%82%E5%85%B4%E8%B6%A3%E7%82%B9/FeatureServer/0"),
  			         searchFields: ["NAME_CHN"],
  			         displayField: "NAME_CHN",
  			         exactMatch: false,
  			         outFields: ["NAME_CHN","OBJECTID"],
  			         name: "兴趣点",
  			       	 enableSuggestions: false,
  			         maxResults: 6,
  			         maxSuggestions: 6
  			      });
  				 sources.push({
  					 featureLayer: new esri.layers.FeatureLayer("http://172.16.10.52:6080/arcgis/rest/services/%E5%B9%BF%E5%B7%9E%E5%B8%82%E5%85%B4%E8%B6%A3%E7%82%B9/FeatureServer/1"),
  					 searchFields: ["NAME_CHN"],
  					 displayField: "NAME_CHN",
  					 exactMatch: false,
  					 outFields: ["NAME_CHN","OBJECTID"],
  					 name: "自然地名",
  					 enableSuggestions: false,
  					 maxResults: 6,
  					 maxSuggestions: 6
  				 });
  				search.set("sources", sources);
  				search.startup();
  				
			}
			function initEditor(evt) {
				var templateLayers = arrayUtils.map(evt.layers, function (result) {
					return result.layer;
				});
				var templatePicker = new TemplatePicker({
					featureLayers: templateLayers,
					grouping: true,
					rows: "auto",
					columns: 3
				}, "templateDiv");
				templatePicker.startup();

				var layers = arrayUtils.map(evt.layers, function (result) {
					return {featureLayer: result.layer};
				});
				var settingsEditor = {
					map: map,
					templatePicker: templatePicker,
					layerInfos: layers,
					toolbarVisible: true,
					createOptions: {
						polylineDrawTools: [Editor.CREATE_TOOL_FREEHAND_POLYLINE],
						polygonDrawTools: [Editor.CREATE_TOOL_FREEHAND_POLYGON,
							Editor.CREATE_TOOL_CIRCLE,
							Editor.CREATE_TOOL_TRIANGLE,
							Editor.CREATE_TOOL_RECTANGLE
						]
					},
					toolbarOptions: {
						reshapeVisible: true
					}
				};

				var params = {settings: settingsEditor};
				var myEditor = new Editor(params, 'editorDiv');
				//define snapping options
				var symbol = new SimpleMarkerSymbol(
						SimpleMarkerSymbol.STYLE_CROSS,
						15,
						new SimpleLineSymbol(
								SimpleLineSymbol.STYLE_SOLID,
								new Color([255, 0, 0, 0.5]),
								5
						),
						null
				);
				map.enableSnapping({
					snapPointSymbol: symbol,
					tolerance: 20,
					snapKey: keys.ALT
				});
				myEditor.startup();
			}
		});

	//获取经纬度范围
	function getMapExtentLngLat(){
		return esri.geometry.webMercatorToGeographic(map.extent);
	}
	function showCoordinate(evt) {
		var mp = evt.mapPoint;
		dojo.byId("coord").innerHTML = "坐标：" + mp.x + " , " + mp.y;
	}
	function to2dMap() {
		mapOpt = 2;
		map.removeLayer(imgLyr);
		map.addLayer(baseLyr);
//		map.addLayer(annoLyr);
	}
	function to2dImgMap() {
        console.log("触发切换地图事件");
		mapOpt = 2;
//		imgLyr = new TDTImageryLayer();
//        imgLyr = new  ArcGISTiledMapServiceLayerLocal("http://172.16.10.52:6080/arcgis/rest/services/%E5%B9%BF%E5%B7%9E%E5%B8%82%E5%BD%B1%E5%83%8F%E5%9C%B0%E5%9B%BE/MapServer");
		map.removeLayer(baseLyr);
        map.addLayer(imgLyr);
//		map.removeLayer(annoLyr);
	}
	function zoomInAuto() {
		map.setLevel(map.getLevel() + 1);
	}
	function zoomOutAuto() {
		map.setLevel(map.getLevel() - 1);
	}
	function panLeft() {
		var deta = (map.extent.xmax - map.extent.xmin) / 3;
		map.setExtent(map.extent.offset(-deta, 0), true);
	}
	function panRight() {
		var deta = (map.extent.xmax - map.extent.xmin) / 3;
		map.setExtent(map.extent.offset(deta, 0), true);
	}
	function panUp() {
		var deta = (map.extent.ymax - map.extent.ymin) / 3;
		map.setExtent(map.extent.offset(0, deta), true);
	}
	function panDown() {
		var deta = (map.extent.ymax - map.extent.ymin) / 3;
		map.setExtent(map.extent.offset(0, -deta), true);
	}
	function panCenter() {
		map.centerAt(centerPt);
	}
	function locateCurPos() {
		locator.locate();
	}
	function print2dMap() {
		map.setMapCursor("wait");
		var printTask = new esri.tasks.PrintTask("http://172.16.10.52:6080/arcgis/rest/services/Utilities/PrintingTools/GPServer/Export%20Web%20Map%20Task");
		var template = new esri.tasks.PrintTemplate();
// 		var dpi = document.getElementById("dpi").value ;
		template.exportOptions = {width : 800,height : 600,
// 		dpi: Number(dpi)
		};
		template.format = "PDF";
		template.layout = "MAP_ONLY";
		template.preserveScale = false;
		var params = new esri.tasks.PrintParameters();
		params.map = map;
		params.template = template;
		printTask.execute(params, function(evt) {
			map.setMapCursor("default");
			window.open(evt.url, "_blank");
		});
	}
	function clear2dMap() {
		console.log(map.graphicsLayerIds);
		console.log(map.graphics);
		map.infoWindow.hide();
		if (measure) {
			measure._measureLayer.clear();
		}
		//清除查询添加的图层
		var iPointLyr=map.getLayer("iPointLyr");
		var iPolylineLyr=map.getLayer("iPolylineLyr");
		var iPolygonLyr=map.getLayer("iPolygonLyr");
		var queryLyr=map.getLayer("queryLyr");
		if(iPointLyr) map.removeLayer(iPointLyr);
		if(iPolylineLyr) map.removeLayer(iPolylineLyr);
		if(iPolygonLyr) map.removeLayer(iPolygonLyr);
		if(queryLyr) map.removeLayer(queryLyr);
		//清除标绘图层
		if(DCI.Plot.graphicslayer){
			DCI.Plot.graphicslayer.clear();
		}
 		// map.graphics.clear();
	}
	function clearResult(){
		var iPointLyr=map.getLayer("iPointLyr");
		var iPolylineLyr=map.getLayer("iPolylineLyr");
		var iPolygonLyr=map.getLayer("iPolygonLyr");
		var queryLyr=map.getLayer("queryLyr");
		if(iPointLyr) iPointLyr.clear();
		if(iPolylineLyr) iPolylineLyr.clear();
		if(iPolygonLyr) iPolygonLyr.clear();
		if(queryLyr) queryLyr.clear();
	}
	function pan2dMap() {
		map.showPanArrows();
		naviBar.activate(NAVI.PAN);
	}
	function zoomIn2dMap() {
		map.showZoomSlider();
		naviBar.activate(NAVI.ZOOM_IN);
	}
	function zoomOut2dMap() {
		naviBar.activate(NAVI.ZOOM_OUT);
	}
	function preView2dMap() {
		naviBar.zoomToPrevExtent();
	}
	function nextView2dMap() {
		naviBar.zoomToNextExtent();
	}
	function measureDistance2d() {
		measure._startMeasureDistance();
	}
	function measureArea2d() {
		measure._startMeasureArea();
	}
	function showAlertDialog(content){
		artDialog({
			id:'$dialog',
			content:content,
			ok:function(){
				$.dialog.list['$dialog'].close();
			}
		});
	}
	/**
	 * @param type 类型：point、polyline、polygon
	 * @param highlightShow 是否高亮显示[boolean]
	 * @returns
	 */
	function getSymbol(type,highlightShow){
		type=type.toLowerCase();
		var symbol,lineSmb;
		var pointColor;//点颜色
		var polylineColor;//线颜色
		var polygonColor;//面颜色
		var outline=new esri.symbol.SimpleLineSymbol(esri.symbol.SimpleLineSymbol.STYLE_SOLID,new esri.Color([255,80,0,0.5]), 2);//轮廓线
		if(highlightShow){
			pointColor=new esri.Color([0, 255, 255, 0.5 ]);
			polylineColor=new esri.Color([255, 0, 0, 1 ]);
			polygonColor=new esri.Color([89,132,7,0.4]);
		}else{//默认样式
			pointColor=new esri.Color([222, 22, 22, 0.5 ]);
			polylineColor=new esri.Color([222, 22, 22, 1 ]);
			polygonColor=new esri.Color([103,152,7,0.2]);
		}
		if(type.indexOf("point")>-1){
			symbol = new esri.symbol.SimpleMarkerSymbol(esri.symbol.SimpleMarkerSymbol.STYLE_DIAMOND, 10,
					new esri.symbol.SimpleLineSymbol(esri.symbol.SimpleLineSymbol.STYLE_SOLID,pointColor, 1),pointColor);
		}else if(type.indexOf("polyline")>-1){
			symbol =new esri.symbol.SimpleLineSymbol(esri.symbol.SimpleLineSymbol.STYLE_SOLID,polylineColor, 2);

		}else if(type.indexOf("polygon")>-1){
			symbol = new esri.symbol.SimpleFillSymbol(esri.symbol.SimpleFillSymbol.STYLE_SOLID, outline, polygonColor);
		}
		return symbol;
	}
	
	function getPicSymbol(url,width,height){
		if(!width){//默认定位图标大小
			width=25;
			height=45;
		}
		var smb=esri.symbol.PictureMarkerSymbol(url,width,height);
		smb.setOffset(0,20);
		return smb;
	}
	
	function graphicsConvertor(arrGraphics,isToMercato){
		if($.isArray(arrGraphics)){
			for(var i=0;i<arrGraphics.length;i++){
				var geo=arrGraphics[i].geometry;
				if(isToMercato){//经纬度转web墨卡托
					if(geo.spatialReference.wkid==3857) continue;
					arrGraphics[i].geometry=esri.geometry.geographicToWebMercator(geo);
				}else{//web转经纬度
					if(geo.spatialReference.wkid==4326) continue;
					arrGraphics[i].geometry=esri.geometry.webMercatorToGeographic(geo);
				}
			}
		}else{
			var geo=arrGraphics.geometry;
			if(isToMercato){
				if(geo.spatialReference.wkid==3857) return;
				arrGraphics.geometry=esri.geometry.geographicToWebMercator(geo);
			}else{
				if(geo.spatialReference.wkid==4326) return;
				arrGraphics.geometry=esri.geometry.webMercatorToGeographic(geo);
			}
		}
	}
</script>
<script type="text/javascript" src="${res }/js/map2d/mapLocate.js"></script>
<script type="text/javascript" src="${res }/js/map2d/mapQuery.js"></script>
<script type="text/javascript" src="${res}/dist/js/map/plot/plot.js"></script>
<script type="text/javascript" src="${res }/js/map2d/mapPlot.js"></script>
</head>
<body>
</body>
</html>