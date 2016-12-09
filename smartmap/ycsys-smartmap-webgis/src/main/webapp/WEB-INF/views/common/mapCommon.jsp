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
<link href="http://172.16.10.50:8080/arcgis_js_api/3.18/esri/css/esri.css" rel="stylesheet">
<link href="http://172.16.10.50:8080/arcgis_js_api/3.18/dijit/themes/claro/claro.css" rel="stylesheet">
<!-- <link href="http://localhost:8000/arcgis_js_api/library/3.18/3.18/esri/css/esri.css" rel="stylesheet"> -->
<!-- <link href="http://localhost:8000/arcgis_js_api/library/3.18/3.18/dijit/themes/claro/claro.css" rel="stylesheet"> -->
<style type="text/css">
	.arcgisSearch,.searchExpandContainer,.searchInputGroup,.searchInput{width:160px;}
</style>
<script type="text/javascript">
        var dojoConfig = {
            async: true,
            packages: [{
                "name": "layerjs",
                "location": "${res}/js/map2d"
            }]
        };			
</script>
<script src="${res}/plugins/jQuery/jquery-2.2.3.min.js"></script>
<script src="http://172.16.10.50:8080/arcgis_js_api/3.18/init.js"></script>
<!-- <script src="http://localhost:8000/arcgis_js_api/library/3.18/3.18/init.js"></script> -->
<script src="${res}/js/map2d/tiandituImgLayer.js"></script>
<script type="text/javascript">
	var path="${path}";
	var centerPt=[113.244931,23.115074];
	var map,naviBar,measure,draw,printer,locator,baseLyr,imgLyr,search;
	var NAVI,bookmarks;
	var minx,miny,maxx,maxy;
	var lyrList=[];
	
	require([
		  "dojo/parser",
		  "esri/map",
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
		  "esri/symbols/SimpleFillSymbol", "esri/symbols/SimpleLineSymbol",
	      "esri/renderers/SimpleRenderer","esri/Color","esri/layers/ArcGISImageServiceLayer",
	      "esri/tasks/QueryTask","esri/tasks/query","esri/InfoTemplate","esri/geometry/Point",
	      "esri/tasks/locationproviders/QueryTaskLocationProvider","esri/graphic","esri/dijit/Search",
	      "esri/symbols/PictureMarkerSymbol","esri/geometry/webMercatorUtils","esri/dijit/Bookmarks","esri/dijit/BookmarkItem",
		  "dojo/domReady!"
		], function(parser,Map, Navigation,dom,Draw,OverviewMap,MeasureTools,Print,PrintTask,PrintParameters,PrintTemplate,Scalebar,LocateButton,Legend,
				FeatureLayer,Extent,SpatialReference) {
			parser.parse();
			NAVI=Navigation;
			map = new Map("map2d",{
			    logo:false,
	            zoom: 8,
	            autoResize:true,
	            basemap:"streets",
	            displayGraphicsOnPan:false,
	            slider:false,
	            center: centerPt
			  });
			map.on("load",init);
// 			baseLyr = new TDTLayer();
// 			map.addLayer(baseLyr);//天地图地图
// 			annoLyr= new TDTAnnoLayer();
// 			map.addLayer(annoLyr);//天地图注记图
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
  				
  				search=new esri.dijit.Search({
  					sources: [],
  					addLayersFromMap:true,
  					zoomScale:2308574,
  					visible:false,
  					enableInfoWindow:false,
  					highlightSymbol:new esri.symbol.PictureMarkerSymbol('${res}/images/mark_red.png', 19, 33),
  					enableLabel:true,
  					map:map
  				},dom.byId("search"));
  				
  				//获取图层树
  				$.fn.zTree.init($("#treeMaptcgl"), setting);
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
		map.addLayer(annoLyr);
	}
	function to2dImgMap() {
		mapOpt = 2;
		imgLyr = new TDTImageryLayer();
		map.removeLayer(baseLyr);
		map.removeLayer(annoLyr);
		map.addLayer(imgLyr);
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
		map.graphics.clear();
		if (measure) {
			measure._measureLayer.clear();
		}
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
</script>
<script type="text/javascript" src="${res }/js/map2d/mapLocate.js"></script>
<script type="text/javascript" src="${res }/js/map2d/mapQuery.js"></script>
</head>
<body>
</body>
</html>