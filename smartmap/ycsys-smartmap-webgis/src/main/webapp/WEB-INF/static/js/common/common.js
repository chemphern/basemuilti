var mapOpt=2;//地图操作状态,默认2维操作
var map3DInit=false;//标识三维是否初始化
var mapExtent;//当前地图范围

//一级菜单切换响应函数
function onModelChangeHandler(i){
	if(i==3){
		loadThemeLayers();
	}else{
		unloadThemeLayers();
	}
}

function getMapExtent(){
	mapExtent = getMapExtentLngLat();
	return mapExtent;
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
		MeasureTool.activate(MeasureTool.MeasureType.HORIZONTAL);
	}
}

function measureArea(){
	if(mapOpt==2){
		measureArea2d();
	}else if(mapOpt==3){
		MeasureTool.activate(MeasureTool.MeasureType.GROUNDAREA)
	}
}