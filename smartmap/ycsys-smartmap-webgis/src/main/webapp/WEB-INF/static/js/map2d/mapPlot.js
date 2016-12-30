
function initPlot2d(){
	if (!DCI.Plot.isload)
        DCI.Plot.Init(map);
    if (DCI.Plot.dialog)
        DCI.Plot.dialog.close();
}

function getPlotPtStyle(){
	var selStyle=$('#selPtStyle')[0];
	var selSize=$('#selPtSize')[0];
	var style=selStyle.options[selStyle.selectedIndex].value;
	var size=selSize.options[selSize.selectedIndex].value;
	var color=$('#txtPtColor').val();
	var ptalpha =$('#txtPtAlpha').val();
	var selLStyle=$('#selPLStyle')[0];
	var lStyle=selLStyle.options[selLStyle.selectedIndex].value;
	var lColor=$('#txtPlColor').val();
	var plalpha =$('#txtPlAlpha').val();
	var plWidth=$('#selPlWidth').val();
	
	if(!color || !lColor){
		showAlertDialog('颜色没有设置');
		return false;
	}
	var ptColorArr=translateColor(color,ptalpha);
	var plColorArr=translateColor(lColor,plalpha);
	var smb=new esri.symbol.SimpleMarkerSymbol(style, size,new esri.symbol.SimpleLineSymbol(lStyle,new esri.Color(plColorArr), plWidth),new esri.Color(ptColorArr));
	return smb;
}

function getPlotPlStyle(){
	var selLStyle=$('#selLStyle')[0];
	var lStyle=selLStyle.options[selLStyle.selectedIndex].value;
	var lColor=$('#txtLColor').val();
	var plalpha =$('#txtLAlpha').val();
	var plWidth=$('#selLWidth').val();
	
	if(!lColor){
		showAlertDialog('颜色没有设置');
		return false;
	}
	var plColorArr=translateColor(lColor,plalpha);
	var smb=new esri.symbol.SimpleLineSymbol(lStyle,new esri.Color(plColorArr), plWidth);
	return smb;
}

function getPlotPgStyle(){
	var selStyle=$('#selPgStyle')[0];
	var style=selStyle.options[selStyle.selectedIndex].value;
	var color=$('#txtPgColor').val();
	var pgalpha =$('#txtPgAlpha').val();
	var selLStyle=$('#selOutlineStyle')[0];
	var lStyle=selLStyle.options[selLStyle.selectedIndex].value;
	var lColor=$('#txtOutlineColor').val();
	var plalpha =$('#txtOutlineAlpha').val();
	var plWidth=$('#selOutlineWidth').val();
	
	if(!color || !lColor){
		showAlertDialog('颜色没有设置');
		return false;
	}
	var pgColorArr=translateColor(color,pgalpha);
	var plColorArr=translateColor(lColor,plalpha);
	var smb=new esri.symbol.SimpleFillSymbol(style,new esri.symbol.SimpleLineSymbol(lStyle,new esri.Color(plColorArr), plWidth),new esri.Color(pgColorArr));
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
	var selStyle=$('#selArrStyle')[0];
	var style=selStyle.options[selStyle.selectedIndex].value;
	var color=$('#txtArrColor').val();
	var pgalpha =$('#txtArrAlpha').val();
	var selLStyle=$('#selArrOutlineStyle')[0];
	var lStyle=selLStyle.options[selLStyle.selectedIndex].value;
	var lColor=$('#txtArrOutlineColor').val();
	var plalpha =$('#txtArrOutlineAlpha').val();
	var plWidth=$('#selArrOutlineWidth').val();
	
	var pgColorArr=translateColor(color,pgalpha);
	var plColorArr=translateColor(lColor,plalpha);
	var smb=new esri.symbol.SimpleFillSymbol(style,new esri.symbol.SimpleLineSymbol(lStyle,new esri.Color(plColorArr), plWidth),new esri.Color(pgColorArr));
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
    case 2://扇形.
    	
    	break;
    case 3://弓形
    	
    	break;
    case 4://矩形
    	DCI.Plot.drawExtent(symbol, function (geometry) {
            DCI.Plot.drawEndPlot(geometry, symbol);
        });
    	break;
    case 5://多边形
    	DCI.Plot.drawPolygon(symbol, function (geometry) {
            DCI.Plot.drawEndPlot(geometry, symbol);
        });
    	break;
    case 6://手绘面
    	DCI.Plot.drawFreeHandPolygon(symbol, function (geometry) {
            DCI.Plot.drawEndPlot(geometry, symbol);
        });
    	break;
    case 7://聚集区
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
	var selFmy=$('#selFontFamily')[0];
	var selFSize=$('#selFontSize')[0];
	var selFStyle=$('#selFontStyle')[0];
	var selFVar=$('#selFontVariant')[0];
	var selFBold=$('#selFontBold')[0];
	var fontFamily=selFmy.options[selFmy.selectedIndex].value;
	var fontSize=selFSize.options[selFSize.selectedIndex].value;
	var fontStyle=selFStyle.options[selFStyle.selectedIndex].value;
	var fontVar=selFVar.options[selFVar.selectedIndex].value;
	var fontBold=selFBold.options[selFBold.selectedIndex].value;
	
	var font=new esri.symbol.Font(fontSize,fontStyle,fontVar,fontBold,fontFamily);
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



