/*******标绘默认设置*/
$(function(){
	    plotSet.init();
});

var plotSet = {
	point:null,
	polyline:null,
	polygon:null,
	text:null,
	arrow:null,
	pointSet:false,
	polylineSet:false,
	polygonSet:false,
	textSet:false,
	arrowSet:false,
	init:function(){
		if(!this.pointSet){
			this.point = getPointSet();
			this.pointSet = true;
		}
		if(!this.polylineSet){
			this.polyline = getPolylineSet();
			this.polylineSet = true;
		}
		if(!this.polygonSet){
			this.polygon = getPolygonSet();
			this.polygonSet = true;
		}
		if(!this.textSet){
			this.text = getTextSet();
			this.textSet = true;
		}
		if(!this.arrowSet){
			this.arrow = getArrowSet();
			this.arrowSet = true;
		}
	}
}

function initPlot2d(){
	if (!DCI.Plot.isload)
        DCI.Plot.Init(map);
    if (DCI.Plot.dialog)
        DCI.Plot.dialog.close();
}

function getPointSet(){
	return {
		style:$('#selPtStyle option:selected').val(),
		size:$('#selPtSize option:selected').val(),
		color:$('#txtPtColor').val(),
		ptAlpha:$('#txtPtAlpha').val(),
		plStyle:$('#selPLStyle option:selected').val(),
		plColor:$('#txtPlColor').val(),
		plAlpha:$('#txtPlAlpha').val(),
		plWidth:$('#selPlWidth').val()
	}
}

function getPolylineSet(){
	return{
		lStyle:$('#selLStyle option:selected').val(),
		lColor:$('#txtLColor').val(),
		plalpha:$('#txtLAlpha').val(),
		plWidth:$('#selLWidth').val()
	}
}

function getPolygonSet(){
	return{
		style:$('#selPgStyle option:selected').val(),
		color:$('#txtPgColor').val(),
		pgalpha :$('#txtPgAlpha').val(),
		lStyle:$('#selOutlineStyle option:selected').val(),
		lColor:$('#txtOutlineColor').val(),
		plalpha :$('#txtOutlineAlpha').val(),
		plWidth:$('#selOutlineWidth').val()
	}
}

function getTextSet(){
	return{
		fontFamily:$('#selFontFamily option:selected').val(),
		fontSize:$('#selFontSize option:selected').val(),
		fontStyle:$('#selFontStyle option:selected').val(),
		fontVar:$('#selFontVariant option:selected').val(),
		fontBold:$('#selFontBold option:selected').val(),
		fontColor:$('#txtFontColor').val()
	}
}

function getArrowSet(){
	return{
		style:$('#selArrStyle option:selected').val(),
		color:$('#txtArrColor').val(),
		pgalpha :$('#txtArrAlpha').val(),
		lStyle:$('#selArrOutlineStyle').val(),
		lColor:$('#txtArrOutlineColor').val(),
		plalpha :$('#txtArrOutlineAlpha').val(),
		plWidth:$('#selArrOutlineWidth').val()
	}
}

function resetPlotSet(type){
	switch (type) {
	case "point":
		var p = plotSet.point;
		$('#selPtStyle').val(p.style);
		$('#selPtSize').val(p.size);
		$('#txtPtColor').val(p.color);
		$('#txtPtColor').css('backgroundColor',p.color);
		$('#txtPtAlpha').val(p.ptAlpha);
		$('#selPLStyle').val(p.plStyle);
		$('#txtPlColor').val(p.plColor);
		$('#txtPlColor').css('backgroundColor',p.color);
		$('#txtPlAlpha').val(p.plAlpha);
		$('#selPlWidth').val(p.plWidth);
		break;
	case "polyline":
		var l = plotSet.polyline;
		$('#selLStyle').val(l.lStyle);
		$('#txtLColor').val(l.lColor);
		$('#txtLColor').css('backgroundColor',l.lColor);
		$('#txtLAlpha').val(l.plalpha);
		$('#selLWidth').val(l.plWidth);
		break;
	case "polygon":
		var g = plotSet.polygon;
		$('#selPgStyle').val(g.style);
		$('#txtPgColor').val(g.color);
		$('#txtPgColor').css('backgroundColor',g.color);
		$('#txtPgAlpha').val(g.pgalpha);
		$('#selOutlineStyle').val(g.lStyle);
		$('#txtOutlineColor').val(g.lColor);
		$('#txtOutlineColor').css('backgroundColor',g.color);
		$('#txtOutlineAlpha').val(g.plalpha);
		$('#selOutlineWidth').val(g.plWidth);
		break;
	case "text":
		var t = plotSet.text;
		$('#selFontFamily').val(t.fontFamily);
		$('#selFontSize').val(t.fontSize);
		$('#selFontStyle').val(t.fontStyle);
		$('#selFontVariant').val(t.fontVar);
		$('#selFontBold').val(t.fontBold);
		$('#txtFontColor').val(t.fontColor);
		$('#txtFontColor').css('backgroundColor',t.fontColor);
		break;
	case "arrow":
		var a = plotSet.arrow;
		$('#selArrStyle').val(a.style);
		$('#txtArrColor').val(a.color);
		$('#txtArrColor').css('backgroundColor',a.color);
		$('#txtArrAlpha').val(a.pgalpha);
		$('#selArrOutlineStyle').val(a.lStyle);
		$('#txtArrOutlineColor').val(a.lColor);
		$('#txtArrOutlineColor').css('backgroundColor',a.color);
		$('#txtArrOutlineAlpha').val(a.plalpha);
		$('#selArrOutlineWidth').val(a.plWidth);
		break;
	}
}

function getPlotPtStyle(){
	var p = getPointSet();
	var ptColorArr=translateColor(p.color,p.ptAlpha);
	var plColorArr=translateColor(p.plColor,p.plAlpha);
	var smb=new esri.symbol.SimpleMarkerSymbol(p.style, p.size,new esri.symbol.SimpleLineSymbol(p.plStyle,new esri.Color(plColorArr), p.plWidth),new esri.Color(ptColorArr));
	return smb;
}

function getPlotPlStyle(){
	var l = getPolylineSet();
	var plColorArr=translateColor(l.lColor,l.plalpha);
	var smb=new esri.symbol.SimpleLineSymbol(l.lStyle,new esri.Color(plColorArr), l.plWidth);
	return smb;
}

function getPlotPgStyle(){
	var g = getPolygonSet();
	var pgColorArr=translateColor(g.color,g.pgalpha);
	var plColorArr=translateColor(g.lColor,g.plalpha);
	var smb=new esri.symbol.SimpleFillSymbol(g.style,new esri.symbol.SimpleLineSymbol(g.lStyle,new esri.Color(plColorArr), g.plWidth),new esri.Color(pgColorArr));
	return smb;
}

function getPlotFlagStyle(){
	var color=$('#txtFlagColor').val();
	var alpha =$('#txtFlagAlpha').val();
	var flagColor=translateColor(color,alpha);
	var smb=new esri.symbol.SimpleFillSymbol('solid',new esri.symbol.SimpleLineSymbol('solid',new esri.Color(flagColor), 1),new esri.Color(flagColor));
	return smb;
}

function getPlotArrStyle(){
	var a = getArrowSet();
	var pgColorArr=translateColor(a.color,a.pgalpha);
	var plColorArr=translateColor(a.lColor,a.plalpha);
	var smb=new esri.symbol.SimpleFillSymbol(a.style,new esri.symbol.SimpleLineSymbol(a.lStyle,new esri.Color(plColorArr), a.plWidth),new esri.Color(pgColorArr));
	return smb;
}

function translateColor(colorString,alphaString){
	if(!colorString) return;
	var color=colorString.replace('rgb(','').replace(')','')+','+alphaString;
	var rgba=color.split(',');
	var temp=[];
	$.each(rgba,function(i,v){
		temp.push($.trim(v));
	});
	return temp;
}
function plotPoint2d(i){
	initPlot2d();
	var symbol;
	if(plotPtStyle){
		symbol=plotPtStyle;
	}else{
		symbol=DCI.Plot.markerSymbol;
	}
	DCI.Plot.map.setMapCursor('crosshair');
	switch (i) {
    case 0:
    	DCI.Plot.drawPoint(symbol,function(geo){
    		DCI.Plot.drawEndPlot(geo,symbol);
    	});
    	break;
    case 1:
    	DCI.Plot.drawMultiPoints(symbol,function(geo){
    		DCI.Plot.drawEndPlot(geo,symbol);
    	});
    	break;
    }
}
function plotPolyline2d(i){
	initPlot2d();
	var symbol;
	if(plotPlStyle){
//		DCI.Plot.toolbar.setLineSymbol(plotPlStyle);
//		DCI.Plot.lineSymbol=plotPlStyle;
		symbol=plotPlStyle;
	}else{
		symbol=DCI.Plot.lineSymbol;
	}
	DCI.Plot.map.setMapCursor('crosshair');
	
	switch (i) {
    case 0://折线
    	DCI.Plot.drawPolyline(symbol,function(geo){
    		DCI.Plot.drawEndPlot(geo,symbol);
    	});
    	break;
    case 1://圆弧
    	DCI.Plot.drawArc(symbol);
    	break;
    case 2://曲线.
    	DCI.Plot.drawBezier_curve(symbol);
    	break;
    case 3://自由线
    	DCI.Plot.drawFreeHandPolyline(symbol, function (geometry) {
            DCI.Plot.drawEndPlot(geometry, symbol);
        });
    	break;
    }        
}
function plotPolygon2d(i){
	initPlot2d();
	var symbol;
	if(plotPgStyle){
//		DCI.Plot.toolbar.setFillSymbol(plotPgStyle);
//		DCI.Plot.fillSymbol=plotPgStyle;
		symbol=plotPgStyle;
	}else{
		symbol=DCI.Plot.fillSymbol;
	}
	
	DCI.Plot.map.setMapCursor('crosshair');
	switch (i) {
    case 0://圆
    	DCI.Plot.drawCircle(symbol, function (geometry) {
            DCI.Plot.drawEndPlot(geometry, symbol);
        });
    	break;
    case 1://椭圆
    	DCI.Plot.drawEllipse(symbol, function (geometry) {
    		DCI.Plot.drawEndPlot(geometry,symbol);
    	});
    	break;
    case 2://矩形
    	DCI.Plot.drawExtent(symbol, function (geometry) {
            DCI.Plot.drawEndPlot(geometry, symbol);
        });
    	break;
    case 3://多边形
    	DCI.Plot.drawPolygon(symbol, function (geometry) {
            DCI.Plot.drawEndPlot(geometry, symbol);
        });
    	break;
    case 4://手绘面
    	DCI.Plot.drawFreeHandPolygon(symbol, function (geometry) {
            DCI.Plot.drawEndPlot(geometry, symbol);
        });
    	break;
    case 5://聚集区
    	DCI.Plot.drawBezierPolygon(symbol);
    	break;
    }
}
function plotArrows2d(i){
	initPlot2d();
	var gSymbol;
	if(plotArrStyle){
		gSymbol=plotArrStyle;
	}else{
		gSymbol = DCI.Plot.fillSymbol;
	}
	
	DCI.Plot.map.setMapCursor('crosshair');
	switch (i) {
	case 0://直箭头
		 DCI.Plot.drawStraightArrow(gSymbol, function (geometry) {
             DCI.Plot.drawEndPlot(geometry, gSymbol);
         });
		break;
	case 1://斜箭头
		DCI.Plot.drawFreeHandArrow(gSymbol);
		break;
	case 2://双箭头
		
		break;
	case 3://燕尾箭头
		 DCI.Plot.drawTailArrow(gSymbol)
		break;
	case 4://燕尾斜箭头
		DCI.Plot.drawTailArrow(gSymbol)
		break;
	}
}
function getPlotFontStyle(){
	var t = getTextSet();
	var font=new esri.symbol.Font(t.fontSize,t.fontStyle,t.fontVar,t.fontBold,t.fontFamily);
	return font;
}

function plotText(i){
	initPlot2d();
	DCI.Plot.map.setMapCursor('crosshair');
	var text=$('#txtContent').val();
	var font,color,symbol;
	if(plotFontStyle){
		font=plotFontStyle;
		color=$('#txtFontColor').val();
		color=translateColor(color,1);
		symbol=new esri.symbol.TextSymbol(text,font,color);
	}else{//默认设置
		color=new esri.Color([0,0,0]);
		switch (i) {
		case 0:
			font=new esri.symbol.Font(14);
			symbol=new esri.symbol.TextSymbol(text,font,color);
			break;
		case 1:
			font=new esri.symbol.Font(20,esri.symbol.Font.STYLE_NORMAL,esri.symbol.Font.VARIANT_NORMAL,
					esri.symbol.Font.WEIGHT_BOLDER);
			symbol=new esri.symbol.TextSymbol(text,font,color);
			break;
		case 2:
			font=new esri.symbol.Font(18,esri.symbol.Font.STYLE_ITALIC,esri.symbol.Font.VARIANT_SMALLCAPS,
					esri.symbol.Font.WEIGHT_LIGHTER,'华文隶书');
			symbol=new esri.symbol.TextSymbol(text,font,new esri.Color([155,78,249,1]));
			symbol.setHaloColor(new esri.Color([247,244,23]));
			symbol.setHaloSize(3);
			break;
		}
	}
	DCI.Plot.drawPoint(symbol, function (geometry) {
        DCI.Plot.drawEndPlot(geometry, symbol);
    });
}

function plotPic(i,url){
	initPlot2d();
	DCI.Plot.imgPath = url;
    DCI.Plot.map.setMapCursor('crosshair');
    var symbol = new esri.symbol.PictureMarkerSymbol(DCI.Plot.imgPath, 32, 32);
    DCI.Plot.drawPoint(symbol, function (geometry) {
        DCI.Plot.drawEndPlot(geometry, symbol);
    });
}

function plotFlag(i){
	initPlot2d();
	DCI.Plot.map.setMapCursor('crosshair');
	DCI.Plot.imgPath=path+'/static/dist/img/map/flg_'+i+'.svg';
    DCI.Plot.drawExtent(null, function (geometry) {
    	var pmin=new esri.geometry.Point(geometry.xmin,geometry.ymin,new esri.SpatialReference({ wkid: 3857 }));
    	var pmax=new esri.geometry.Point(geometry.xmax,geometry.ymax,new esri.SpatialReference({ wkid: 3857 }));
    	var smin=map.toScreen(pmin);
    	var smax=map.toScreen(pmax);
    	var width=Math.abs(smax.x-smin.x);
    	var height=Math.abs(smax.y-smin.y);
    	var symbol = new esri.symbol.PictureMarkerSymbol(DCI.Plot.imgPath, width, height);
    	symbol.setOffset(width/2,height/2);
    	symbol.setColor(new esri.Color([0,0,255]));
    	DCI.Plot.drawEndPlot(pmin, symbol);
    });
}



