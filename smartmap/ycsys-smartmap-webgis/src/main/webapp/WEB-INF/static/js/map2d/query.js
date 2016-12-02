
function queryAttr2d() {
		var url = "http://172.16.10.52:6080/arcgis/rest/services/GuangzhouTraffic/MapServer/2";
		var field = "Name_CHN";
		var value = "天河区";
		var fLyr = new esri.layers.FeatureLayer(url, {
			mode : esri.layers.FeatureLayer.MODE_SNAPSHOT,
			outFields: ["*"]
		});
		fLyr.setDefinitionExpression(field + " = '" + value + "'");
		var symbol = new esri.symbol.SimpleFillSymbol(
				esri.symbol.SimpleFillSymbol.STYLE_SOLID, new esri.symbol.SimpleLineSymbol(
						esri.symbol.SimpleLineSymbol.STYLE_SOLID,
						new esri.Color([ 255, 0, 0, 1 ]), 1), new esri.Color([
						0, 0, 125, 0.35 ]));
		fLyr.setRenderer(new esri.renderer.SimpleRenderer(symbol));
		map.addLayer(fLyr);
	}

function queryAttrLogic2d(){
	var url = "http://172.16.10.52:6080/arcgis/rest/services/GuangzhouTraffic/MapServer/0";
	var fLyr = new esri.layers.FeatureLayer(url, {
		mode : esri.layers.FeatureLayer.MODE_SNAPSHOT,
		outFields: ["*"]
	});
	fLyr.setDefinitionExpression("StationNum = 7 AND ExitCount = 4 ");
	var symbol = new esri.symbol.SimpleMarkerSymbol(esri.symbol.SimpleMarkerSymbol.STYLE_DIAMOND, 10,
		    new esri.symbol.SimpleLineSymbol(esri.symbol.SimpleLineSymbol.STYLE_SOLID,
		    	    new esri.Color([255,0,0]), 1),
		    	    new esri.Color([0,0,255,0.25]));
	fLyr.setRenderer(new esri.renderer.SimpleRenderer(symbol));
	map.addLayer(fLyr);
}

function query(url){
	var task=new esri.tasks.Query(url);
	
}

