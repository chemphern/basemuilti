var plotPtStyle;//标绘点样式
var plotPlStyle;//标绘线样式
var plotPgStyle;//标绘面样式
var plotFontStyle;//标绘字体样式
var plotArrStyle;//箭头样式
var plotFlagStyle;
	
$(function(){
	//点样式设置
	$('#btnPointSet').click(function(){
		if(mapOpt==2){
			plotPtStyle=getPlotPtStyle();
		}
	});
	//线样式设置
	$('#btnPolylineSet').click(function(){
		if(mapOpt==2){
			plotPlStyle=getPlotPlStyle();
		}
	});
	//面样式设置
	$('#btnPgSet').click(function(){
		if(mapOpt==2){
			plotPgStyle=getPlotPgStyle();
		}
	});
	//文字样式设置
	$('#btnFontSet').click(function(){
		if(mapOpt==2){
			plotFontStyle=getPlotFontStyle();
		}
	});
	//旗帜高级
	$('#btnFlagSet').click(function(){
		if(mapOpt==2){
			plotFlagStyle=getPlotFlagStyle();
		}
	});
	//字体设置
	$('#btnArrSet').click(function(){
		if(mapOpt==2){
			plotArrStyle=getPlotArrStyle();
		}
	});
	//点高级重置
	$('#btnResetPt').click(function(){
		plotPtStyle=null;
	});
	//线高级重置
	$('#btnResetPl').click(function(){
		plotPlStyle=null;
	});
	//面高级重置
	$('#btnResetPg').click(function(){
		plotPgStyle=null;
	});
	//文字高级重置
	$('#btnResetTxt').click(function(){
		plotFontStyle=null;
	});
	$('#btnResetFlag').click(function(){
		plotFlagStyle=null;
	});
	//箭头高级重置
	$('#btnResetArr').click(function(){
		plotArrStyle=null;
	});
	//事件响应
	$('.bs-icon-list li').click(function() {
        $(this).toggleClass('active').siblings().removeClass('active');
        var i=$(this).index();
        var id=$(this).parent().parent().attr('id');
        switch (id) {
		case 'planePlot'://点
			plotPoint(i);
			break;
		case 'planeLine'://线
			plotPolyline(i);
			break;
		case 'planeFace'://面
			plotPolygon(i);
			break;
		case 'planeFlag'://旗
			plotFlag(i);
			break;
		case 'planeArrows'://箭头
			plotArrows(i);
			break;
		case 'planeText'://文字
			plotText(i);
			break;
		}
    });
	//图片标注
	$('.bs-icon-list_pic li').click(function(){
		$(this).toggleClass('active').siblings().removeClass('active');
        var i=$(this).index();
        var id=$(this).parent().parent().attr('id');
        
        var attr= $(this).children(".icon_pic").attr('class');
		var name=attr.substring(9,attr.length);
		url=path+'/static/dist/img/map/'+name+'.png';
        if(mapOpt==2) {
            plotPic(i, url);
        }else if(mapOpt==3){
            plot23dImage(url);
        }
	})
    //------------------------------------------------三维功能与界面绑定---------------------------------------------//
    //点标绘入口绑定
    $("#planePlot3dPoint").on('click',plot3dPoint);
    $("#planePlot3dMultPoint").on('click',plot3dMultPoint);
    //线标绘入口绑定
    $("#planePlot3dPolyline").on('click',plot3dPolyline);
    $("#planePlot3dArc").on('click',plot3dArc);
    $("#planePlot3dFreeline").on('click',plot3dFreeline);
    $("#planePlot3dArrowline").on('click',plot3dArrowline);
    //面标绘入口绑定
    $("#planePlot3dCircle").on('click',plot3dCircle);
    $("#planePlot3dEllipse").on('click',plot3dEllipse);
    $("#planePlot3dRectangle").on('click',plot3dRectangle);
    $("#planePlot3dPolygon").on('click',plot3dPolygon);
    $("#planePlot3dStraightArrow").on('click',plot3dStraightArrow);
    $("#planePlot3dTailsArrow").on('click',plot3dTailsArrow);
    //体标绘入口绑定
    $("#planePlot3dBox3D").on('click',plot3dBox3D);
    $("#planePlot3dClinder3D").on('click',plot3dClinder3D);
    $("#planePlot3dSphere3D").on('click',plot3dSphere3D);
    $("#planePlot3dPyramid3D").on('click',plot3dPyramid3D);
    $("#planePlot3dCone3D").on('click',plot3dCone3D);
    $("#planePlot3dArrow3D").on('click',plot3dArrow3D);
    //文字标绘入口绑定
    $("#planePlot3dSimpleText").on('click',plot3dSimpleText);
    $("#planePlot3dTitleText").on('click',plot3dTitleText);
    $("#planePlot3dArtText").on('click',plot3dArtText);
    //设置标绘风格绑定
    $("#plot3dSetTextImageStyle").on('click',plot3dSetTextImageStyle);
    $("#plot3dSetFillStyle").on('click',plot3dSetFillStyle);
    $("#plot3dSetLineStyle").on('click',plot3dSetLineStyle);
})

function plotPoint(i){
	if(mapOpt==2){
		plotPoint2d(i);
	}
}
function plotPolyline(i){
	if(mapOpt==2){
		plotPolyline2d(i);
	}
}
function plotPolygon(i){
	if(mapOpt==2){
		plotPolygon2d(i);
	}
}
function plotArrows(i){
	if(mapOpt==2){
		plotArrows2d(i);
	}
}

function plot3dPoint() {
    if(mapOpt==3) {
        PlotTool.activate(PlotTool.PlotType.POINT);
    }else
        alert("请切换到三维地图下使用！");
}

function plot3dMultPoint() {
    if(mapOpt==3) {
        PlotTool.activate(PlotTool.PlotType.MULTIPOINT);
    }else
        alert("请切换到三维地图下使用！");
}

function plot3dPolyline() {
    if(mapOpt==3) {
        PlotTool.activate(PlotTool.PlotType.POLYLINE);
    }else
        alert("请切换到三维地图下使用！");
}

function plot3dArc() {
    if(mapOpt==3) {
        PlotTool.activate(PlotTool.PlotType.ARCLINE);
    }else
        alert("请切换到三维地图下使用！");
}

function plot3dFreeline() {
    if(mapOpt==3) {
        PlotTool.activate(PlotTool.PlotType.FREELINE);
    }else
        alert("请切换到三维地图下使用！");
}

function plot3dArrowline() {
    if(mapOpt==3) {
        PlotTool.activate(PlotTool.PlotType.ARROWLINE);
    }else
        alert("请切换到三维地图下使用！");
}

function plot3dCircle() {
    if(mapOpt==3) {
        PlotTool.activate(PlotTool.PlotType.CIRCLE);
    }else
        alert("请切换到三维地图下使用！");
}

function plot3dEllipse() {
    if(mapOpt==3) {
        PlotTool.activate(PlotTool.PlotType.ELLIPSE);
    }else
        alert("请切换到三维地图下使用！");
}

function plot3dRectangle() {
    if(mapOpt==3) {
        PlotTool.activate(PlotTool.PlotType.RECTANGLE);
    }else
        alert("请切换到三维地图下使用！");
}

function plot3dPolygon() {
    if(mapOpt==3) {
        PlotTool.activate(PlotTool.PlotType.POLYGON);
    }else
        alert("请切换到三维地图下使用！");
}

function plot3dStraightArrow() {
    if(mapOpt==3) {
        PlotTool.activate(PlotTool.PlotType.STRAIGHTARROWAREA);
    }else
        alert("请切换到三维地图下使用！");
}

function plot3dTailsArrow() {
    if(mapOpt==3) {
        PlotTool.activate(PlotTool.PlotType.TAILSARROWAREA);
    }else
        alert("请切换到三维地图下使用！");
}

function plot3dBox3D() {
    if(mapOpt==3) {
        PlotTool.activate(PlotTool.PlotType.BOX3D);
    }else
        alert("请切换到三维地图下使用！");
}

function plot3dClinder3D() {
    if(mapOpt==3) {
        PlotTool.activate(PlotTool.PlotType.CYLINDER3D);
    }else
        alert("请切换到三维地图下使用！");
}

function plot3dSphere3D() {
    if(mapOpt==3) {
        PlotTool.activate(PlotTool.PlotType.SPHERE3D);
    }else
        alert("请切换到三维地图下使用！");
}

function plot3dPyramid3D() {
    if(mapOpt==3) {
        PlotTool.activate(PlotTool.PlotType.PYRAMID3D);
    }else
        alert("请切换到三维地图下使用！");
}

function plot3dCone3D() {
    if(mapOpt==3) {
        PlotTool.activate(PlotTool.PlotType.CONE3D);
    }else
        alert("请切换到三维地图下使用！");
}

function plot3dArrow3D() {
    if(mapOpt==3) {
        PlotTool.activate(PlotTool.PlotType.ARROW3D);
    }else
        alert("请切换到三维地图下使用！");
}

function plot3dSimpleText() {
    if(mapOpt==3) {
        if($('#txtContentPlot3d').val()==""){
            alert("请先输入文字内容！");
            return;
        }
        PlotTool.activate(PlotTool.PlotType.TEXT,$('#txtContentPlot3d').val());
    }else
        alert("请切换到三维地图下使用！");
}

function plot3dTitleText() {
    if(mapOpt==3) {
        PlotTextOrImageGlobe.Bold = true;
        PlotTextOrImageGlobe.Size = 24;
        if($('#txtContentPlot3d').val()==""){
            alert("请先输入文字内容！");
            return;
        }
        PlotTool.activate(PlotTool.PlotType.TEXT,$('#txtContentPlot3d').val());
    }else
        alert("请切换到三维地图下使用！");
}

function plot3dArtText() {
    if(mapOpt==3) {
        PlotTextOrImageGlobe.Front = "华文彩云";
        if($('#txtContentPlot3d').val()==""){
            alert("请先输入文字内容！");
            return;
        }
        PlotTool.activate(PlotTool.PlotType.TEXT,$('#txtContentPlot3d').val());
    }else
        alert("请切换到三维地图下使用！");
}

function plot23dImage(image) {
    PlotTool.activate(PlotTool.PlotType.IMAGE,image);
}

function plot3dSetLineStyle() {
    var fillColor = $("#txtPgColorPlot2dStyle").val();
    if(fillColor==""){
        alert("请选择填充颜色！");
        return;
    };
    var fillAlpha = $("#txtPgAlphaPlot2dStyle").val();
    if(fillAlpha==""){
        alert("请正确输入填充颜色透明度！取值范围：0~1");
        return;
    }
    var lineColor = $("#txtOutlineColorPlot2dStyle").val();
    if(lineColor==""){
        alert("请选择边线颜色！");
        return;
    }
    var lineAlpha = $("#txtOutlineAlphaPlot2dStyle").val();
    if(lineAlpha==""){
        alert("请正确输入填充颜色透明度！取值范围：0~1");
        return;
    }
    PlotAreaGlobe.Color = fillColor;
    PlotAreaGlobe.Alpha = fillAlpha;
    PlotLineGlobe.Color = lineColor;
    PlotLineGlobe.Alpha = lineAlpha;
    //边线样式及线宽
    PlotLineGlobe.Pattern = $('#selOutlineStylePlot2dStyle option:selected').val();
    PlotLineGlobe.Width = $('#selOutlineWidthPlot2dStyle option:selected').val();
}

function plot3dSetFillStyle() {
    var fillColor = $("#txtPgColorPlot3d").val();
    if(fillColor==""){
        alert("请选择填充颜色！");
        return;
    }
    var fillAlpha = $("#txtPgAlphaPlot3d").val();
    if(fillAlpha==""){
        alert("请正确输入填充颜色透明度！取值范围：0~1");
        return;
    }
    var lineColor = $("#txtOutlineColorPlot3d").val();
    if(lineColor==""){
        alert("请选择边线颜色！");
        return;
    }
    var lineAlpha = $("#txtOutlineAlphaPlot3d").val();
    if(lineAlpha==""){
        alert("请正确输入填充颜色透明度！取值范围：0~1");
        return;
    }
    PlotCubeGlobe.AreaColor = fillColor;
    PlotCubeGlobe.AreaAlpha = fillAlpha;
    PlotCubeGlobe.LineColor = lineColor;
    PlotCubeGlobe.LineAlpha = lineAlpha;
    //边线样式及线宽
    PlotCubeGlobe.LinePattern = $('#selOutlineStylePlot3d option:selected').val();
    PlotCubeGlobe.LineWidth = $('#selOutlineWidthPlot3d option:selected').val();
}

function plot3dSetTextImageStyle() {
    var textColor = $("#txtFontColorPlot3d").val();
    if(textColor==""){
        alert("请选择填充颜色！");
        return;
    }
    var backColor = $("#txtBackColorPlot3d").val();
    if(backColor==""){
        alert("请选择填充颜色！");
        return;
    }
    var textAlpha = $("#txtFontAlphaPlot3dStyle").val();
    if(textAlpha==""){
        alert("请正确输入填充颜色透明度！取值范围：0~1");
        return;
    }
    var backAlpha = $("#txtFontBackAlphaPlot3dStyle").val();
    if(backAlpha==""){
        alert("请正确输入填充颜色透明度！取值范围：0~1");
        return;
    }
    PlotTextOrImageGlobe.Front = $('#selFontFamilyPlot3d option:selected').val();
    PlotTextOrImageGlobe.Color = textColor;
    PlotTextOrImageGlobe.ColorAlpha=textAlpha;
    PlotTextOrImageGlobe.BackgroundColor = backColor;
    PlotTextOrImageGlobe.BackgroundColorAlpha = backAlpha;
    PlotTextOrImageGlobe.Bold = getBoolenValuePlot3d($('#selFontBoldPlot3d option:selected').val());
    PlotTextOrImageGlobe.Italic = getBoolenValuePlot3d($('#selFontVariantPlot3d option:selected').val());
    PlotTextOrImageGlobe.Underline = getBoolenValuePlot3d($('#selFontStylePlot3d option:selected').val());
    PlotTextOrImageGlobe.Size = $('#selFontSizePlot3d option:selected').val();
}


function getBoolenValuePlot3d(name) {
    if(name=="常规")
        return false;
    else
        return true;
}