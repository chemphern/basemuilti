
/**
 * 经纬度定位
 * @param lng 精度
 * @param lat 纬度
 * @returns
 */
function locateLngLat2d(lng,lat){
	var point=new esri.geometry.Point(lng,lat);
	renderPoint(point);
}
/**
 * 平面坐标定位
 * @param x 地图x坐标
 * @param y 地图y坐标
 * @returns
 */
function locateXY2d(x,y){
	var point=new esri.geometry.Point(x,y,new esri.SpatialReference({wkid:3857}));
	renderPoint(point);
}
/**
 * 地名定位
 * @param address 地名
 * @returns
 */
function locateAddress2d(address){
	search.startup();
	search.clear();
	search.search(address);
}
function renderPoint(point){
	var symbol=getPointSymbol();
	var graphic=new esri.Graphic(point,symbol);
	map.graphics.add(graphic);
	map.centerAt(point);
}
function getPointSymbol(){
	var color=new esri.Color([0, 255, 255, 1 ]);
	return new esri.symbol.SimpleMarkerSymbol(esri.symbol.SimpleMarkerSymbol.STYLE_DIAMOND,10,
			new esri.symbol.SimpleLineSymbol(esri.symbol.SimpleLineSymbol.STYLE_SOLID,color,1),color);
}