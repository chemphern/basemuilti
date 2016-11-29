<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="http://172.16.10.50:8080/arcgis_js_api/3.18/esri/css/esri.css" rel="stylesheet">
<link href="http://172.16.10.50:8080/arcgis_js_api/3.18/dijit/themes/claro/claro.css" rel="stylesheet">

<script type="text/javascript">
        var dojoConfig = {
            async: true,
            packages: [{
                "name": "layerjs",
                "location": "${res}/js/map2d"
            }]
        };			
</script>
<script src="http://172.16.10.50:8080/arcgis_js_api/3.18/init.js"></script>

<script type="text/javascript">
	var centerPt=[113.244931,23.115074];
	var map,naviBar,measure,draw,printer,locator;
	var NAVI;
	var minx,miny,maxx,maxy;
	require([
		  "dojo/parser",
		  "esri/map",
		  "esri/toolbars/navigation",
		  "esri/dijit/Measurement",
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
		  "dojo/domReady!"
		], function(parser,Map, Navigation,Measurement,dom,Draw,OverviewMap,MeasureTools,Print,PrintTask,PrintParameters,PrintTemplate,Scalebar,LocateButton,Legend,
				FeatureLayer) {
			parser.parse();
			NAVI=Navigation;
			map = new Map("map2d",{
			    basemap: "streets",
	            zoom: 8,
	            autoResize:true,
	            displayGraphicsOnPan:false,
	            slider:false,
	            navigationMode:"css-transform",//css-transform|classic
	            center: centerPt
			  });
			map.on("load",init);
			
			function init(){
				map.on("onMouseMove",showCoordinate);
	  			
				naviBar=new Navigation(map);
				locator=new LocateButton({map:map,visible:false},dom.byId("curPos"));
				locator.startup();
				
				measure=new MeasureTools({map:map},dom.byId("measureDiv"));
				
				draw=new Draw(map);
//	 			dojo.connect(draw, "onDrawEnd", doMeasure);
				
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
			}
			
			
		});
	
	function showCoordinate(evt){
		var mp = evt.mapPoint;
        dojo.byId("coord").innerHTML ="坐标：" + mp.x + " , " + mp.y;
	}
	
	function to2dMap(){
		mapOpt=2;
		map.setBasemap("streets");
	}
	
	function to2dImgMap(){
		mapOpt=2;
		map.setBasemap("satellite");
	}
	
	function zoomInAuto(){
		map.setLevel(map.getLevel()+1);
	}
	
	function zoomOutAuto(){
		map.setLevel(map.getLevel()-1);
	}
	
	function panLeft(){
		var deta=(map.extent.xmax-map.extent.xmin)/3;
		map.setExtent(map.extent.offset(-deta,0),true);
	}
	function panRight(){
		var deta=(map.extent.xmax-map.extent.xmin)/3;
		map.setExtent(map.extent.offset(deta,0),true);
	}
	function panUp(){
		var deta=(map.extent.ymax-map.extent.ymin)/3;
		map.setExtent(map.extent.offset(0,deta),true);
	}
	function panDown(){
		var deta=(map.extent.ymax-map.extent.ymin)/3;
		map.setExtent(map.extent.offset(0,-deta),true);
	}
	function panCenter(){
		map.centerAt(centerPt);
	}
	function locateCurPos(){
		locator.locate();
	}
	function print2dMap(){
		map.setMapCursor("wait");
		var printTask = new esri.tasks.PrintTask("https://sampleserver6.arcgisonline.com/arcgis/rest/services/Utilities/PrintingTools/GPServer/Export%20Web%20Map%20Task");  
        var template = new esri.tasks.PrintTemplate();  
//         var dpi = document.getElementById("dpi").value ;  
        template.exportOptions = {  
            width: 800,  
            height: 600,  
//             dpi: Number(dpi)  
        };  
        template.format = "PDF";  
        template.layout = "MAP_ONLY";  
        template.preserveScale = false;  
        var params = new esri.tasks.PrintParameters();  
        params.map = map;  
        params.template = template;  
        printTask.execute(params, function(evt){ 
        	map.setMapCursor("default");
            window.open(evt.url,"_blank");  
        });  
	}
	
	function clear2dMap(){
		map.graphics.clear();	
		if(measure){
			measure._measureLayer.clear();
		}
	}
	
	function pan2dMap(){
		if(mapOpt==2){
			map.showPanArrows();
			naviBar.activate(NAVI.PAN);
		}else{
			
		}
	}
	
	function zoomIn2dMap(){
		if(mapOpt==2){
			map.showZoomSlider();
			naviBar.activate(NAVI.ZOOM_IN);
		}else{
			
		}
	}
	
	function zoomOut2dMap(){
		if(mapOpt==2){
			naviBar.activate(NAVI.ZOOM_OUT);
		}else{
			
		}
		
	}
	
	function preView2dMap(){
		if(mapOpt==2){
			naviBar.zoomToPrevExtent();
		}else{
			
		}
	}
	
	function nextView2dMap(){
		if(mapOpt==2){
			naviBar.zoomToNextExtent();
		}else{
			
		}
	}
	
	function measureDistance2d(){
		measure._startMeasureDistance();
	}
	
	function measureArea2d(){
		measure._startMeasureArea();
	}
	
	
	
</script>
</head>
<body>

</body>
</html>