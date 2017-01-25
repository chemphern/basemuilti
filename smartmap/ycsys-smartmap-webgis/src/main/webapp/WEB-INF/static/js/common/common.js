var mapOpt=2;//地图操作状态,默认2维操作
var map3DInit=false;//标识三维是否初始化
var mapExtent;//当前地图范围
var activetedMap = 2;//表示是否当前激活的是二维或三维
var modelIndex = 0;//顶部模块索引

//一级菜单切换响应函数
function onModelChangeHandler(i){
	if(i==3){
		modelIndex = 3;
		loadThemeLayers();
	}else if(i==4){
		modelIndex = 4;
		initEditor();
	}else{
		unloadThemeLayers();
		modelIndex = null;
	}
}

function getMapExtent(){
	mapExtent = getMapExtentLngLat();
	return mapExtent;
}

function setMapExtent() {
	var extent3d = get3DMapExtent();
    var sr = new esri.SpatialReference(4326);
	var extent2d = new esri.geometry.Extent(extent3d.xmin,extent3d.ymin,extent3d.xmax,extent3d.ymax,sr);
	map.setExtent(extent2d);//设置地图范围
    // setQuickLocationMap2d();//更新快速定位面板名称
}

function setMap3dExtent() {
	if(map3DInit){
        mapExtent = getMapExtentLngLat();
        setZoomInLevel(mapExtent);
	}
}

function toggleTo3d(){
	mapExtent=getMapExtent();
	to3dMap(mapExtent);
}

function clearMap(){
	if(mapOpt==2){
		clear2dMap();
	}else if(mapOpt==3){
		clearMap3D();
	}
}

function print(){
	if(mapOpt==2){
		print2dMap();
	}else if(mapOpt==3){
		print3DMap();
	}
}

function pan(){
	if(mapOpt==2){
		pan2dMap();
	}else if(mapOpt==3){
		PanScan();
	}
}

function zoomIn(){
	if(mapOpt==2){
		zoomIn2dMap();
	}else if(mapOpt==3){
		zoomInMapExtent();
	}
}

function zoomOut(){
	if(mapOpt==2){
		zoomOut2dMap();
	}else if(mapOpt==3){
		zoomOutMapExtent();
	}
	
}

function preView(){
	if(mapOpt==2){
		preView2dMap();
	}else if(mapOpt==3){
		getPreviousView();
	}
}

function nextView(){
	if(mapOpt==2){
		nextView2dMap();
	}else if(mapOpt==3){
		getNextView();
	}
}

function measureDistance(){
	if(mapOpt==2){
		measureDistance2d();
	}else if(mapOpt==3){
		MeasureTool.activate(MeasureTool.MeasureType.GROUNDLINE);
	}
}

function measureArea(){
	if(mapOpt==2){
		measureArea2d();
	}else if(mapOpt==3){
		MeasureTool.activate(MeasureTool.MeasureType.GROUNDAREA)
	}
}

/**
 * 勾选二三维同步时，三维操作
 */
function getMap3dReadyInCommon() {
	if(!map3DInit){
        toggleTo3d();
	}
    mapOpt = 0;
}