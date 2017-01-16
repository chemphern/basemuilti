/**
 * Created by ChenLong on 2016/12/21.
 * Description:实现三维场景各种标绘普通图形、文字、立体标绘
 * version: 1.0.0
 */

//定义标绘对象
var PlotTool = {
    activate: function (plotType,data,altitudeType,description) {
        if(altitudeType!=null&&altitudeType!=undefined)
            this.PlotAltitudeType = altitudeType;
        if(description!=null&&description!=undefined)
            this.PlotDescription = description;
        if(data!=null&&data!="")
            PlotTextOrImageGlobe.Data = data;
        plotShape(plotType);
    },
    deactivate: function () {
        endPlotShape();
    },
    plotEndHandler: function () {

    },
    plotIngHandler: function () {

    },
    clear:function(){
        deleteItemsByName(PlotToolGlobe.PlotToolFolder);
    },
    PlotType:{
        //点图形
        'POINT':'POINT','MULTIPOINT':'MULTIPOINT',
        //线图形
        'POLYLINE':'POLYLINE','ARCLINE':'ARCLINE','FREELINE':'FREELINE','ARROWLINE':'ARROWLINE',
        //面图形
        'CIRCLE':'CIRCLE','ELLIPSE':'ELLIPSE','RECTANGLE':'RECTANGLE','POLYGON':'POLYGON',
        'STRAIGHTARROWAREA':'STRAIGHTARROWAREA','TAILSARROWAREA':'TAILSARROWAREA',
        //3D图形
        'POLYGON3D': 'POLYGON3D',"CYLINDER3D":"CYLINDER3D","CONE3D":"CONE3D","ARROW3D":"ARROW3D",
        "SPHERE3D":"SPHERE3D","PYRAMID3D":"PYRAMID3D","BOX3D":"BOX3D",
        //文字、图片
        "TEXT":"TEXT","IMAGE":"IMAGE"},
    PlotAltitudeType:2,
    PlotDescription:"标绘图形"
};

//定义标绘相关过程变量对象
var PlotToolGlobe={
    "PlotOperation":"",
    "PlotToolFolder":HiddenGroup+ "\\标绘文件夹",
    "PlotToolFolderID":"",
    "PlotSharpID":""
};

//定义标绘点属性对象
var PlotPointGlobe={
    "LineColor":"#FF0000",
    "FillColor":"#FF0000",
    "Radius":5
};

//定义标绘线属性对象
var PlotLineGlobe={
    "Pattern": "SOLID",
    "Color":"#FF0000",
    "BackColor":"#6E6E6E",
    "Alpha":0.5,
    "Width":2
};

//定义标绘面属性对象
var PlotAreaGlobe={
    "Color":"#6e6e6e",
    "Alpha":0.5
};

/**
 *重置绘制标绘风格
 */
function resetShapePlot3DStyle() {
    PlotAreaGlobe.Color = "#6E6E6E";
    PlotAreaGlobe.Alpha = 0.5;
    PlotLineGlobe.Alpha = 0.5;
    PlotLineGlobe.Color = "#FF0000";
    PlotLineGlobe.BackColor = "#6E6E6E";
    PlotLineGlobe.Pattern = "SOLID";
    PlotLineGlobe.Width = 2;
}

//定义标绘体属性对象
var PlotCubeGlobe={
    "LinePattern": "SOLID",
    "LineColor":"#FF0000",
    "LineAlpha":0.5,
    "LineWidth":2,
    "AreaColor":"#6E6E6E",
    "AreaAlpha":0.5
};

/**
 *重置立体标绘风格
 */
function resetCubePlot3DStyle() {
    PlotCubeGlobe.AreaColor = "#6E6E6E";
    PlotCubeGlobe.AreaAlpha = 0.5;
    PlotCubeGlobe.LineColor = "#FF0000";
    PlotCubeGlobe.LineAlpha = 0.5;
    PlotCubeGlobe.LinePattern = "SOLID";
    PlotCubeGlobe.LineWidth = 2;
}

//定义标绘文字属性对象
var PlotTextOrImageGlobe={
    "Data":"",
    "Front":"宋体",
    "Color":"#FFDA22",
    "ColorAlpha":1,
    "BackgroundColor":"#000000",
    "BackgroundColorAlpha":0,
    "Bold":false,
    "Italic":false,
    "Underline":false,
    "Size":16,//Text
    "Scale":1,//Image
    "LabelStyle":0
};

/**
 *重置图文标绘风格
 */
function resetTextOrImagePlot3DStyle() {
    PlotTextOrImageGlobe.Front = "宋体";
    PlotTextOrImageGlobe.Color = "#FFDA22";
    PlotTextOrImageGlobe.ColorAlpha = 1;
    PlotTextOrImageGlobe.BackgroundColor = "#000000";
    PlotTextOrImageGlobe.BackgroundColorAlpha = 0;
    PlotTextOrImageGlobe.Bold = false;
    PlotTextOrImageGlobe.Italic = false;
    PlotTextOrImageGlobe.Underline = false;
    PlotTextOrImageGlobe.Size = 16;
    PlotTextOrImageGlobe.Scale = 1;
    PlotTextOrImageGlobe.LabelStyle = 0;
}

/**
 * 标绘入口、开始方法
 * @param type 标绘类型
 */
function plotShape(type) {
    // deleteItemsByName(PlotToolGlobe.PlotToolFolder);
    PlotToolGlobe.PlotSharpID="";
    PlotToolGlobe.PlotToolFolderID=createFolder(PlotToolGlobe.PlotToolFolder);
    PlotToolGlobe.PlotOperation=type;
    YcMap3D.Window.SetInputMode(1);
    YcMap3D.AttachEvent("OnLButtonUp", plotToolLBUpHandler);
    YcMap3D.AttachEvent("OnRButtonUp", plotToolRBUpHandler);
    YcMap3D.AttachEvent("OnFrame", plotToolFrameHandler);
}

/**
 * 标绘结束方法
 */
function endPlotShape() {
    PlotToolGlobe.PlotOperation = "";
    YcMap3D.Window.SetInputMode(0);
    YcMap3D.DetachEvent("OnLButtonUp", plotToolLBUpHandler);
    YcMap3D.DetachEvent("OnRButtonUp", plotToolRBUpHandler);
    YcMap3D.DetachEvent("OnFrame", plotToolFrameHandler);
    //清空用户自定义绘制过程事件
    PlotTool.plotEndHandler = function () { }
    PlotTool.plotIngHandler = function () { }
}

/**
 * 标绘左键点击事件
 */
function plotToolLBUpHandler() {
    var clickPosition = getMousePosition(-1);
    clickPosition.AltitudeType = PlotTool.PlotAltitudeType;
    if(PlotToolGlobe.PlotOperation=="POINT"||PlotToolGlobe.PlotOperation=="MULTIPOINT"){
        plotShapePoint(clickPosition);
    }else if(PlotToolGlobe.PlotOperation=="POLYLINE"||PlotToolGlobe.PlotOperation=="POLYLINEADDPOINT"){
        plotShapePolyline(clickPosition);
    }else if(PlotToolGlobe.PlotOperation=="ARCLINE"||PlotToolGlobe.PlotOperation=="ARCLINEEDITX"||PlotToolGlobe.PlotOperation=="ARCLINEEDITY"){
        plotShapeArcLine(clickPosition);
    }else if(PlotToolGlobe.PlotOperation=="FREELINE"||PlotToolGlobe.PlotOperation=="FREELINEADDPOINT"){
        plotShapeFreeLine(clickPosition);
    }else if(PlotToolGlobe.PlotOperation=="ARROWLINE"||PlotToolGlobe.PlotOperation=="ARROWLINEEND"){
        plotShapeArrowLine(clickPosition);
    }else if(PlotToolGlobe.PlotOperation=="CIRCLE"||PlotToolGlobe.PlotOperation=="CIRCLEEND"){
        plotShapeCircle(clickPosition);
    }else if(PlotToolGlobe.PlotOperation=="ELLIPSE"||PlotToolGlobe.PlotOperation=="ELLIPSEEND"){
        plotShapeEllipse(clickPosition);
    }else if(PlotToolGlobe.PlotOperation=="RECTANGLE"||PlotToolGlobe.PlotOperation=="RECTANGLEEND"){
        plotShapeRectangle(clickPosition);
    }else if(PlotToolGlobe.PlotOperation=="POLYGON"||PlotToolGlobe.PlotOperation=="POLYGONADDPOINT"){
        plotShapePolygon(clickPosition);
    }else if(PlotToolGlobe.PlotOperation=="STRAIGHTARROWAREA"||PlotToolGlobe.PlotOperation=="TAILSARROWAREA"||PlotToolGlobe.PlotOperation=="ARROWAREAEND"){
        plotShapeArrowArea(clickPosition);
    }else if(PlotToolGlobe.PlotOperation=="POLYGON3D"){
        // plotShapePolygon3D(clickPosition);暂不使用
    }else if(PlotToolGlobe.PlotOperation=="CYLINDER3D"||PlotToolGlobe.PlotOperation=="CYLINDER3DRADIUS"||PlotToolGlobe.PlotOperation=="CYLINDER3DHEIGHT"){
        plotShapeCylinder3D(clickPosition);
    }else if(PlotToolGlobe.PlotOperation=="CONE3D"||PlotToolGlobe.PlotOperation=="CONE3DRADIUS"||PlotToolGlobe.PlotOperation=="CONE3DHEIGHT"){
        plotShapeCone3D(clickPosition);
    }else if(PlotToolGlobe.PlotOperation=="ARROW3D"||PlotToolGlobe.PlotOperation=="ARROW3DLENGHT"||PlotToolGlobe.PlotOperation=="ARROW3DHEIGHT"){
        plotShapeArrow3D(clickPosition);
    }else if(PlotToolGlobe.PlotOperation=="SPHERE3D"||PlotToolGlobe.PlotOperation=="SPHERE3DEDIT"){
        plotShapeSphere3D(clickPosition);
    }else if(PlotToolGlobe.PlotOperation=="PYRAMID3D"||PlotToolGlobe.PlotOperation=="PYRAMID3DEDIT"||PlotToolGlobe.PlotOperation=="PYRAMID3DHEIGHT"){
        plotShapePyramid3D(clickPosition);
    }else if(PlotToolGlobe.PlotOperation=="BOX3D"||PlotToolGlobe.PlotOperation=="BOX3DEDIT"||PlotToolGlobe.PlotOperation=="BOX3DEND"){
        plotShapeBox3D(clickPosition);
    }else if(PlotToolGlobe.PlotOperation=="TEXT"||PlotToolGlobe.PlotOperation=="IMAGE"){
        plotTextOrImage(clickPosition);
    }
}

/**
 * 标绘每一帧都执行的事件
 */
function plotToolFrameHandler() {
    var mousePosition = getMousePosition(-1);
    mousePosition.AltitudeType = PlotTool.PlotAltitudeType;
    if(PlotToolGlobe.PlotOperation=="POLYLINEADDPOINT"){
        var line = YcMap3D.ProjectTree.GetObject(PlotToolGlobe.PlotSharpID);
        if (typeof line == 'object'){
            line.Geometry.StartEdit();
            line.Geometry.EndPoint.X = mousePosition.X;
            line.Geometry.EndPoint.Y = mousePosition.Y;
            line.Geometry.EndPoint.Z = mousePosition.Altitude;
            line.Geometry.EndEdit();
            PlotTool.plotIngHandler(line);
        }
    }else if(PlotToolGlobe.PlotOperation=="ARCLINEEDITX"){
        var arc = YcMap3D.ProjectTree.GetObject(PlotToolGlobe.PlotSharpID);
        if (typeof arc == 'object'){
            arc.Position.Yaw = arc.Position.AimTo(mousePosition).Yaw-90;
            arc.Radius = arc.Position.DistanceTo(mousePosition);
            PlotTool.plotIngHandler(arc);
        }
    }else if(PlotToolGlobe.PlotOperation=="ARCLINEEDITY"){
        var arc = YcMap3D.ProjectTree.GetObject(PlotToolGlobe.PlotSharpID);
        if (typeof arc == 'object'){
            var yaw = arc.Position.AimTo(mousePosition).Yaw;
            if((yaw>=arc.Position.Yaw&&yaw<=arc.Position.Yaw + 180)||(yaw<=arc.Position.Yaw&&yaw<=arc.Position.Yaw + 180)){
                arc.StartAngle = -90;
                arc.EndAngle = 90;
            }else{
                arc.StartAngle = 90;
                arc.EndAngle = -90;
            }
            arc.Radius2 = arc.Position.DistanceTo(mousePosition);
            PlotTool.plotIngHandler(arc);
        }
    }else if(PlotToolGlobe.PlotOperation=="FREELINEADDPOINT"){
        var line = YcMap3D.ProjectTree.GetObject(PlotToolGlobe.PlotSharpID);
        if (typeof line == 'object') {
            line.Geometry.StartEdit();
            line.Geometry.Points.AddPoint(mousePosition.X, mousePosition.Y, mousePosition.Altitude);
            line.Geometry.EndEdit();
            PlotTool.plotIngHandler(line);
        }
    }else if(PlotToolGlobe.PlotOperation=="ARROWLINEEND"||PlotToolGlobe.PlotOperation=="ARROWAREAEND"){
        var arrow = YcMap3D.ProjectTree.GetObject(PlotToolGlobe.PlotSharpID);
        if (typeof arrow == 'object') {
            arrow.HeadX = mousePosition.X;
            arrow.HeadY = mousePosition.Y;
            PlotTool.plotIngHandler(arrow);
        }
    }else if(PlotToolGlobe.PlotOperation=="CIRCLEEND"){
        var circle = YcMap3D.ProjectTree.GetObject(PlotToolGlobe.PlotSharpID);
        if (typeof circle == 'object') {
            circle.Radius = circle.Position.DistanceTo(mousePosition);
            PlotTool.plotIngHandler(circle);
        }
    }else if(PlotToolGlobe.PlotOperation=="ELLIPSEEND"){
        var ellipse = YcMap3D.ProjectTree.GetObject(PlotToolGlobe.PlotSharpID);
        if (typeof ellipse == 'object') {
            var positionX = YcMap3D.Creator.CreatePosition(ellipse.Position.X,mousePosition.Y,mousePosition.Altitude,mousePosition.AltitudeType);
            ellipse.Radius2 = ellipse.Position.DistanceTo(positionX);
            var positionY = YcMap3D.Creator.CreatePosition(mousePosition.X,ellipse.Position.Y,mousePosition.Altitude,mousePosition.AltitudeType);
            ellipse.Radius = ellipse.Position.DistanceTo(positionY);
            PlotTool.plotIngHandler(ellipse);
        }
    }else if(PlotToolGlobe.PlotOperation=="RECTANGLEEND"||PlotToolGlobe.PlotOperation=="BOX3DEDIT"){
        var rectangle = YcMap3D.ProjectTree.GetObject(PlotToolGlobe.PlotSharpID);
        if (typeof rectangle == 'object') {
            rectangle.Right = mousePosition.X;
            rectangle.Bottom = mousePosition.Y;
            PlotTool.plotIngHandler(rectangle);
        }
    }else if(PlotToolGlobe.PlotOperation=="POLYGONADDPOINT"){
        var area = YcMap3D.ProjectTree.GetObject(PlotToolGlobe.PlotSharpID);
        if (typeof area == 'object'){
            var tempGeo = area.Geometry;
            area.Geometry.StartEdit();
            area.Geometry.Rings.Item(0).EndPoint.X = mousePosition.X;
            area.Geometry.Rings.Item(0).EndPoint.Y = mousePosition.Y;
            area.Geometry.Rings.Item(0).EndPoint.Z = mousePosition.Altitude;
            area.Geometry.EndEdit();
            if (area.Geometry.GeometryTypeStr != 'Polygon') {
                area.Geometry = tempGeo;
            }
        }
    }else if(PlotToolGlobe.PlotOperation=="BOX3DEND"){
        var box3d = YcMap3D.ProjectTree.GetObject(PlotToolGlobe.PlotSharpID);
        if (typeof box3d == 'object'){
            box3d.Height = box3d.Position.DistanceTo(mousePosition);
            PlotTool.plotIngHandler(box3d);
        }
    }else if(PlotToolGlobe.PlotOperation=="CYLINDER3DRADIUS"){
        var clinder = YcMap3D.ProjectTree.GetObject(PlotToolGlobe.PlotSharpID);
        if (typeof clinder == 'object'){
            clinder.Radius = clinder.Position.DistanceTo(mousePosition);
            PlotTool.plotIngHandler(clinder);
        }
    }else if(PlotToolGlobe.PlotOperation=="CYLINDER3DHEIGHT"){
        var clinder = YcMap3D.ProjectTree.GetObject(PlotToolGlobe.PlotSharpID);
        if (typeof clinder == 'object'){
            clinder.Height = clinder.Position.DistanceTo(mousePosition);
            PlotTool.plotIngHandler(clinder);
        }
    }else if(PlotToolGlobe.PlotOperation=="SPHERE3DEDIT"){
        var sphere = YcMap3D.ProjectTree.GetObject(PlotToolGlobe.PlotSharpID);
        if (typeof sphere == 'object'){
            sphere.Radius = sphere.Position.DistanceTo(mousePosition);
            PlotTool.plotIngHandler(sphere);
        }
    }else if(PlotToolGlobe.PlotOperation=="CONE3DRADIUS"){
        var cone = YcMap3D.ProjectTree.GetObject(PlotToolGlobe.PlotSharpID);
        if (typeof cone == 'object'){
            cone.Radius = cone.Position.DistanceTo(mousePosition);
            PlotTool.plotIngHandler(cone);
        }
    }else if(PlotToolGlobe.PlotOperation=="CONE3DHEIGHT"){
        var cone = YcMap3D.ProjectTree.GetObject(PlotToolGlobe.PlotSharpID);
        if (typeof cone == 'object'){
            cone.Height = cone.Position.DistanceTo(mousePosition);
            PlotTool.plotIngHandler(cone);
        }
    }else if(PlotToolGlobe.PlotOperation=="PYRAMID3DHEIGHT"){
        var pyramid = YcMap3D.ProjectTree.GetObject(PlotToolGlobe.PlotSharpID);
        if (typeof pyramid == 'object'){
            pyramid.Height = pyramid.Position.DistanceTo(mousePosition);
            PlotTool.plotIngHandler(pyramid);
        }
    }else if(PlotToolGlobe.PlotOperation=="PYRAMID3DEDIT"){
        var pyramid = YcMap3D.ProjectTree.GetObject(PlotToolGlobe.PlotSharpID);
        if (typeof pyramid == 'object'){
            var positionX = YcMap3D.Creator.CreatePosition(pyramid.Position.X,mousePosition.Y,mousePosition.Altitude,mousePosition.AltitudeType);
            var positionY = YcMap3D.Creator.CreatePosition(mousePosition.X,pyramid.Position.Y,mousePosition.Altitude,mousePosition.AltitudeType);
            pyramid.Width = pyramid.Position.DistanceTo(positionX);
            pyramid.Depth = pyramid.Position.DistanceTo(positionY);
            PlotTool.plotIngHandler(pyramid);
        }
    }else if(PlotToolGlobe.PlotOperation=="ARROW3DLENGHT"){
        var arrow3d = YcMap3D.ProjectTree.GetObject(PlotToolGlobe.PlotSharpID);
        if (typeof arrow3d == 'object'){
            arrow3d.HeadX = mousePosition.X;
            arrow3d.HeadY = mousePosition.Y;
            PlotTool.plotIngHandler(arrow3d);
        }
    }else if(PlotToolGlobe.PlotOperation=="ARROW3DHEIGHT"){
        var arrow3d = YcMap3D.ProjectTree.GetObject(PlotToolGlobe.PlotSharpID);
        if (typeof arrow3d == 'object'){
            arrow3d.Height = arrow3d.Position.DistanceTo(mousePosition);
            PlotTool.plotIngHandler(arrow3d);
        }
    }
}

/**
 * 标绘右键点击事件
 */
function plotToolRBUpHandler() {
    if(PlotToolGlobe.PlotOperation == "MULTIPOINT"){

    }else if(PlotToolGlobe.PlotOperation == "POLYLINEADDPOINT"){
        var line = YcMap3D.ProjectTree.GetObject(PlotToolGlobe.PlotSharpID);
        if (typeof line == 'object'){
            line.Geometry.StartEdit();
            line.Geometry.Points.DeletePoint(line.Geometry.Points.Count-1);
            line.Geometry.EndEdit();
            PlotTool.plotEndHandler(line);
        }
    }else if(PlotToolGlobe.PlotOperation=="POLYGONADDPOINT"){
        var area = YcMap3D.ProjectTree.GetObject(PlotToolGlobe.PlotSharpID);
        if (typeof area == 'object'){
            area.Geometry.StartEdit();
            area.Geometry.Rings.Item(0).Points.DeletePoint( area.Geometry.Rings.Item(0).Points.Count-1);
            area.Geometry.EndEdit();
            PlotTool.plotEndHandler(area);
        }
    }else if(PlotToolGlobe.PlotOperation=="FREELINEADDPOINT"||PlotToolGlobe.PlotOperation=="ARCLINEEDITY"||PlotToolGlobe.PlotOperation=="ARROWLINEEND"||PlotToolGlobe.PlotOperation=="CIRCLEEND"
                ||PlotToolGlobe.PlotOperation=="ELLIPSEEND"||PlotToolGlobe.PlotOperation=="RECTANGLEEND"||PlotToolGlobe.PlotOperation=="ARROWAREAEND"){
        PlotTool.plotEndHandler(YcMap3D.ProjectTree.GetObject(PlotToolGlobe.PlotSharpID));
    }
    PlotTool.deactivate();
}

/**
 * 标绘单点或多点
 * @param position 绘制位置
 */
function plotShapePoint(position) {
    var point = YcMap3D.Creator.CreateCircle(position,PlotPointGlobe.Radius,PlotPointGlobe.LineColor,PlotPointGlobe.FillColor, PlotToolGlobe.PlotToolFolderID,PlotTool.PlotDescription);
    setAreaStyle(point);
    point.FillStyle.Color.SetAlpha(1);
    point.NumberOfSegments = 100;
    if(PlotToolGlobe.PlotOperation=="POINT"){
        PlotTool.plotEndHandler(point);
        PlotTool.deactivate();
    }
}

/**
 * 标绘折线（创建折现和添加折线点）
 * @param position 绘制位置
 */
function plotShapePolyline(position) {
    if(PlotToolGlobe.PlotOperation=="POLYLINE"){
        var geoArr = new Array(position.X, position.Y, position.Altitude, position.X, position.Y, position.Altitude);
        var shape = YcMap3D.Creator.GeometryCreator.CreateLineStringGeometry(geoArr);
        var line = YcMap3D.Creator.CreatePolyline(shape,PlotLineGlobe.Color,PlotTool.PlotAltitudeType,PlotToolGlobe.PlotToolFolderID,PlotTool.PlotDescription);
        setLineStyle(line);
        PlotToolGlobe.PlotSharpID = line.ID;
        PlotToolGlobe.PlotOperation="POLYLINEADDPOINT";
    }else if(PlotToolGlobe.PlotOperation=="POLYLINEADDPOINT"){
        if(PlotToolGlobe.PlotSharpID!=""&&YcMap3D.ProjectTree.GetObject(PlotToolGlobe.PlotSharpID).ObjectType==1){
            var line = YcMap3D.ProjectTree.GetObject(PlotToolGlobe.PlotSharpID);
            line.Geometry.StartEdit();
            line.Geometry.Points.AddPoint(position.X, position.Y, position.Altitude);
            line.Geometry.EndEdit();
        }
    }
}

/**
 * 标绘圆弧线（创建折现和添加折线点）
 * @param position 绘制位置
 */
function plotShapeArcLine(position) {
    if(PlotToolGlobe.PlotOperation=="ARCLINE"){
        var arc = YcMap3D.Creator.CreateArc(position,0,0,-90,90,PlotLineGlobe.Color,PlotAreaGlobe.Color,60,PlotToolGlobe.PlotToolFolderID,PlotTool.PlotDescription);
        setLineStyle(arc);
        arc.FillStyle.Color.SetAlpha(0);
        PlotToolGlobe.PlotSharpID = arc.ID;
        PlotToolGlobe.PlotOperation="ARCLINEEDITX";
    }else if(PlotToolGlobe.PlotOperation=="ARCLINEEDITX"){
        PlotToolGlobe.PlotOperation="ARCLINEEDITY";
    }else if(PlotToolGlobe.PlotOperation=="ARCLINEEDITY"){
        PlotTool.plotEndHandler(YcMap3D.ProjectTree.GetObject(PlotToolGlobe.PlotSharpID));
        PlotTool.deactivate();
    }
}

/**
 * 标绘自由线
 * @param position 起始点
 */
function plotShapeFreeLine(position) {
    if(PlotToolGlobe.PlotOperation=="FREELINE"){
        var geoArr = new Array(position.X, position.Y, position.Altitude, position.X, position.Y, position.Altitude);
        var shape = YcMap3D.Creator.GeometryCreator.CreateLineStringGeometry(geoArr);
        var line = YcMap3D.Creator.CreatePolyline(shape,PlotLineGlobe.Color,PlotTool.PlotAltitudeType,PlotToolGlobe.PlotToolFolderID,PlotTool.PlotDescription);
        setLineStyle(line);
        PlotToolGlobe.PlotSharpID = line.ID;
        PlotToolGlobe.PlotOperation="FREELINEADDPOINT";
    }else if(PlotToolGlobe.PlotOperation=="FREELINEADDPOINT"){
        PlotTool.plotEndHandler(YcMap3D.ProjectTree.GetObject(PlotToolGlobe.PlotSharpID));
        PlotTool.deactivate();
    }
}

/**
 * 标绘箭头线
 * @param position 起始点
 */
function plotShapeArrowLine(position) {
    if(PlotToolGlobe.PlotOperation=="ARROWLINE"){
        var arrow = YcMap3D.Creator.CreateArrow(position,0,0,PlotLineGlobe.Color,PlotAreaGlobe.Color,PlotToolGlobe.PlotToolFolderID,PlotTool.PlotDescription);
        setLineStyle(arrow);
        PlotToolGlobe.PlotSharpID = arrow.ID;
        PlotToolGlobe.PlotOperation="ARROWLINEEND";
    }else if(PlotToolGlobe.PlotOperation=="ARROWLINEEND"){
        PlotTool.plotEndHandler(YcMap3D.ProjectTree.GetObject(PlotToolGlobe.PlotSharpID));
        PlotTool.deactivate();
    }
}

/**
 * 标绘圆形面
 * @param position 圆心
 */
function plotShapeCircle(position) {
    if(PlotToolGlobe.PlotOperation=="CIRCLE"){
        var circle = YcMap3D.Creator.CreateCircle(position,0,PlotLineGlobe.Color,PlotAreaGlobe.Color,PlotToolGlobe.PlotToolFolderID,PlotTool.PlotDescription);
        setAreaStyle(circle);
        circle.NumberOfSegments = 100;
        PlotToolGlobe.PlotSharpID = circle.ID;
        PlotToolGlobe.PlotOperation="CIRCLEEND";
    }else if(PlotToolGlobe.PlotOperation=="CIRCLEEND"){
        PlotTool.plotEndHandler(YcMap3D.ProjectTree.GetObject(PlotToolGlobe.PlotSharpID));
        PlotTool.deactivate();
    }
}

/**
 * 标绘椭圆形面
 * @param position 椭圆心
 */
function plotShapeEllipse(position) {
    if(PlotToolGlobe.PlotOperation=="ELLIPSE"){
        var ellipse = YcMap3D.Creator.CreateEllipse(position,0,0,PlotLineGlobe.Color,PlotAreaGlobe.Color,100,PlotToolGlobe.PlotToolFolderID,PlotTool.PlotDescription);
        setAreaStyle(ellipse);
        PlotToolGlobe.PlotSharpID = ellipse.ID;
        PlotToolGlobe.PlotOperation="ELLIPSEEND";
    }else if(PlotToolGlobe.PlotOperation=="ELLIPSEEND"){
        PlotTool.plotEndHandler(YcMap3D.ProjectTree.GetObject(PlotToolGlobe.PlotSharpID));
        PlotTool.deactivate();
    }
}

/**
 * 标绘矩形面
 * @param position 矩形起点
 */
function plotShapeRectangle(position) {
    if(PlotToolGlobe.PlotOperation=="RECTANGLE"){
        var rectangle = YcMap3D.Creator.CreateRectangle(position,0,0,PlotLineGlobe.Color,PlotAreaGlobe.Color,PlotToolGlobe.PlotToolFolderID,PlotTool.PlotDescription);
        setAreaStyle(rectangle);
        PlotToolGlobe.PlotSharpID = rectangle.ID;
        PlotToolGlobe.PlotOperation="RECTANGLEEND";
    }else if(PlotToolGlobe.PlotOperation=="RECTANGLEEND"){
        PlotTool.plotEndHandler(YcMap3D.ProjectTree.GetObject(PlotToolGlobe.PlotSharpID));
        PlotTool.deactivate();
    }
}

/**
 * 标绘多边形面
 * @param position 绘制起点
 */
function plotShapePolygon(position) {
    if(PlotToolGlobe.PlotOperation=="POLYGON"){
        var geoArr = new Array(position.X, position.Y, position.Altitude, position.X, position.Y, position.Altitude,position.X, position.Y, position.Altitude,position.X, position.Y, position.Altitude);
        var shape = YcMap3D.Creator.GeometryCreator.CreateLinearRingGeometry(geoArr);
        var area = YcMap3D.Creator.CreatePolygon(shape,PlotLineGlobe.Color,PlotAreaGlobe.Color,PlotTool.PlotAltitudeType,PlotToolGlobe.PlotToolFolderID,PlotTool.PlotDescription);
        setAreaStyle(area);
        PlotToolGlobe.PlotSharpID = area.ID;
        PlotToolGlobe.PlotOperation="POLYGONADDPOINT";
    }else if(PlotToolGlobe.PlotOperation=="POLYGONADDPOINT"){
        var area = YcMap3D.ProjectTree.GetObject(PlotToolGlobe.PlotSharpID);
        if (typeof area == 'object') {
            var tempGeo = area.Geometry;
            area.Geometry.StartEdit();
            area.Geometry.Rings.Item(0).Points.AddPoint(position.X, position.Y, position.Altitude);
            area.Geometry.EndEdit();
            if (area.Geometry.GeometryTypeStr != 'Polygon') {
                area.Geometry = tempGeo;
            }
        }
    }
}

/**
 * 标绘箭头面
 * @param position 绘制起点
 */
function plotShapeArrowArea(position) {
    if(PlotToolGlobe.PlotOperation=="STRAIGHTARROWAREA"||PlotToolGlobe.PlotOperation=="TAILSARROWAREA"){
        var type = null;
        if(PlotToolGlobe.PlotOperation=="STRAIGHTARROWAREA")
            type = 3;
        else
            type = 4;
        var arrowArea = YcMap3D.Creator.CreateArrow(position,0,type,PlotLineGlobe.Color,PlotAreaGlobe.Color,PlotToolGlobe.PlotToolFolderID,PlotTool.PlotDescription);
        setAreaStyle(arrowArea);
        PlotToolGlobe.PlotSharpID = arrowArea.ID;
        PlotToolGlobe.PlotOperation="ARROWAREAEND";
    }else if(PlotToolGlobe.PlotOperation=="ARROWAREAEND"){
        PlotTool.plotEndHandler(YcMap3D.ProjectTree.GetObject(PlotToolGlobe.PlotSharpID));
        PlotTool.deactivate();
    }
}

/**
 * 标绘多面体
 * @param position 绘制起点
 */
function plotShapePolygon3D(position) {
    if(PlotToolGlobe.PlotOperation=="POLYGON3D"){
        DrawTool.activate(DrawTool.DrawType.POLYGON);
        DrawTool.drawEndHandler = function (polygon) {
            var polygon3d = YcMap3D.Creator.Create3DPolygon(polygon.Geometry,0,PlotCubeGlobe.LineColor,PlotCubeGlobe.AreaColor,PlotToolGlobe.PlotToolFolderID,PlotTool.PlotDescription);
            setCubeStyle(polygon3d);
            PlotToolGlobe.PlotOperation="POLYGON3DEDIT";
        }
    }else if(PlotToolGlobe.PlotOperation=="POLYGON3DEDIT"){
        PlotTool.plotEndHandler(YcMap3D.ProjectTree.GetObject(PlotToolGlobe.PlotSharpID));
        PlotTool.deactivate();
    }
}

/**
 * 标绘圆柱体
 * @param position 绘制起点
 */
function plotShapeCylinder3D(position) {
    if(PlotToolGlobe.PlotOperation=="CYLINDER3D"){
        var clinder = YcMap3D.Creator.CreateCylinder(position,0,0,PlotCubeGlobe.LineColor,PlotCubeGlobe.AreaColor,100,PlotToolGlobe.PlotToolFolderID,PlotTool.PlotDescription);
        setCubeStyle(clinder);
        PlotToolGlobe.PlotSharpID = clinder.ID;
        PlotToolGlobe.PlotOperation="CYLINDER3DRADIUS";
    }else if(PlotToolGlobe.PlotOperation=="CYLINDER3DRADIUS"){
            PlotToolGlobe.PlotOperation="CYLINDER3DHEIGHT";
    }else if(PlotToolGlobe.PlotOperation="CYLINDER3DHEIGHT"){
        PlotTool.plotEndHandler(YcMap3D.ProjectTree.GetObject(PlotToolGlobe.PlotSharpID));
        PlotTool.deactivate();
    }
}

/**
 * 标绘圆锥体
 * @param position 绘制起点
 */
function plotShapeCone3D(position) {
    if(PlotToolGlobe.PlotOperation=="CONE3D"){
        var cone = YcMap3D.Creator.CreateCone(position,0,0,PlotCubeGlobe.LineColor,PlotCubeGlobe.AreaColor,100,PlotToolGlobe.PlotToolFolderID,PlotTool.PlotDescription);
        setCubeStyle(cone);
        PlotToolGlobe.PlotSharpID = cone.ID;
        PlotToolGlobe.PlotOperation="CONE3DRADIUS";
    }else if(PlotToolGlobe.PlotOperation=="CONE3DRADIUS"){
        PlotToolGlobe.PlotOperation="CONE3DHEIGHT";
    }else if(PlotToolGlobe.PlotOperation=="CONE3DHEIGHT"){
        PlotTool.plotEndHandler(YcMap3D.ProjectTree.GetObject(PlotToolGlobe.PlotSharpID));
        PlotTool.deactivate();
    }
}

/**
 * 标绘箭头体
 * @param position 绘制起点
 */
function plotShapeArrow3D(position) {
    if(PlotToolGlobe.PlotOperation=="ARROW3D"){
        var arrow3D = YcMap3D.Creator.Create3DArrow(position,0,0,0,PlotCubeGlobe.LineColor,PlotCubeGlobe.AreaColor,PlotToolGlobe.PlotToolFolderID,PlotTool.PlotDescription);
        setCubeStyle(arrow3D);
        PlotToolGlobe.PlotSharpID = arrow3D.ID;
        PlotToolGlobe.PlotOperation="ARROW3DLENGHT";
    }else if(PlotToolGlobe.PlotOperation=="ARROW3DLENGHT"){
        PlotToolGlobe.PlotOperation="ARROW3DHEIGHT";
    }else if(PlotToolGlobe.PlotOperation=="ARROW3DHEIGHT"){
        PlotTool.plotEndHandler(YcMap3D.ProjectTree.GetObject(PlotToolGlobe.PlotSharpID));
        PlotTool.deactivate();
    }
}

/**
 * 标绘圆球体
 * @param position 绘制起点
 */
function plotShapeSphere3D(position) {
    if(PlotToolGlobe.PlotOperation=="SPHERE3D"){
        var sphere = YcMap3D.Creator.CreateSphere(position,0,0,PlotCubeGlobe.LineColor,PlotCubeGlobe.AreaColor,100,PlotToolGlobe.PlotToolFolderID,PlotTool.PlotDescription);
        setCubeStyle(sphere);
        PlotToolGlobe.PlotSharpID = sphere.ID;
        PlotToolGlobe.PlotOperation="SPHERE3DEDIT";
    }else if(PlotToolGlobe.PlotOperation=="SPHERE3DEDIT"){
        PlotTool.plotEndHandler(YcMap3D.ProjectTree.GetObject(PlotToolGlobe.PlotSharpID));
        PlotTool.deactivate();
    }
}

/**
 * 标绘金字塔体
 * @param position 绘制起点
 */
function plotShapePyramid3D(position) {
    if(PlotToolGlobe.PlotOperation=="PYRAMID3D"){
        var pyramid = YcMap3D.Creator.CreatePyramid(position,0,0,0,PlotCubeGlobe.LineColor,PlotCubeGlobe.AreaColor,PlotToolGlobe.PlotToolFolderID,PlotTool.PlotDescription);
        setCubeStyle(pyramid);
        PlotToolGlobe.PlotSharpID = pyramid.ID;
        PlotToolGlobe.PlotOperation="PYRAMID3DEDIT";
    }else if(PlotToolGlobe.PlotOperation=="PYRAMID3DEDIT"){
        PlotToolGlobe.PlotOperation="PYRAMID3DHEIGHT";
    }else if(PlotToolGlobe.PlotOperation=="PYRAMID3DHEIGHT"){
        PlotTool.plotEndHandler(YcMap3D.ProjectTree.GetObject(PlotToolGlobe.PlotSharpID));
        PlotTool.deactivate();
    }
}

/**
 * 标绘立方体
 * @param position 绘制起点
 */
function plotShapeBox3D(position) {
    if(PlotToolGlobe.PlotOperation=="BOX3D"){
        var rectangle = YcMap3D.Creator.CreateRectangle(position,0,0,PlotCubeGlobe.LineColor,PlotCubeGlobe.AreaColor,PlotToolGlobe.PlotToolFolderID,PlotTool.PlotDescription);
        setCubeStyle(rectangle);
        PlotToolGlobe.PlotSharpID = rectangle.ID;
        PlotToolGlobe.PlotOperation="BOX3DEDIT";
    }else if(PlotToolGlobe.PlotOperation=="BOX3DEDIT"){
        var rectangle = YcMap3D.ProjectTree.GetObject(PlotToolGlobe.PlotSharpID);
        var box = YcMap3D.Creator.CreateBox(rectangle.Position,rectangle.Width,rectangle.Depth,0,PlotCubeGlobe.LineColor,PlotCubeGlobe.AreaColor,PlotToolGlobe.PlotToolFolderID,PlotTool.PlotDescription);
        setCubeStyle(box);
        YcMap3D.ProjectTree.DeleteItem(PlotToolGlobe.PlotSharpID);
        PlotToolGlobe.PlotSharpID = box.ID;
        PlotToolGlobe.PlotOperation="BOX3DEND";
    }else if(PlotToolGlobe.PlotOperation="BOX3DEND"){
        PlotTool.plotEndHandler(YcMap3D.ProjectTree.GetObject(PlotToolGlobe.PlotSharpID));
        PlotTool.deactivate();
    }
}

/**
 * 标绘图片或文字
 * @param position 绘制位置
 */
function plotTextOrImage(position) {
    var style = YcMap3D.Creator.CreateLabelStyle(PlotTextOrImageGlobe.LabelStyle);
    position.Altitude = 0;
    if(PlotToolGlobe.PlotOperation=="TEXT"){
        var text = YcMap3D.Creator.CreateTextLabel(position,PlotTextOrImageGlobe.Data,style,PlotToolGlobe.PlotToolFolderID,PlotTool.PlotDescription);
        setTextOrImageStyle(text);
    }else if(PlotToolGlobe.PlotOperation=="IMAGE"){
        var image = YcMap3D.Creator.CreateImageLabel(position,PlotTextOrImageGlobe.Data,style,PlotToolGlobe.PlotToolFolderID,PlotTool.PlotDescription);
        image.Position.Altitude = 0;
        setTextOrImageStyle(image);
    }
}

/**
 * 设置面风格
 * @param position 面图形
 */
function setAreaStyle(shape) {
    shape.LineStyle.Color.FromHTMLColor(PlotLineGlobe.Color);
    shape.LineStyle.Color.SetAlpha(PlotLineGlobe.Alpha);
    shape.LineStyle.BackColor.FromHTMLColor(PlotLineGlobe.BackColor);
    shape.LineStyle.BackColor.SetAlpha(PlotLineGlobe.Alpha);
    shape.LineStyle.Pattern = setPlot3dLinePattern(PlotLineGlobe.Pattern);
    shape.LineStyle.Width = PlotLineGlobe.Width;
    shape.FillStyle.Color.FromHTMLColor(PlotAreaGlobe.Color);
    shape.FillStyle.Color.SetAlpha(PlotAreaGlobe.Alpha);
}

/**
 * 设置线风格
 * @param position 线图形
 */
function setLineStyle(shape) {
    shape.LineStyle.Color.FromHTMLColor(PlotLineGlobe.Color);
    shape.LineStyle.Color.SetAlpha(PlotLineGlobe.Alpha);
    shape.LineStyle.BackColor.FromHTMLColor(PlotLineGlobe.BackColor);
    shape.LineStyle.BackColor.SetAlpha(PlotLineGlobe.Alpha);
    shape.LineStyle.Pattern = setPlot3dLinePattern(PlotLineGlobe.Pattern);
    shape.LineStyle.Width = PlotLineGlobe.Width;
}

/**
 * 设置体风格
 * @param position 面图形
 */
function setCubeStyle(shape) {
    shape.LineStyle.Color.FromHTMLColor(PlotCubeGlobe.LineColor);
    shape.LineStyle.Color.SetAlpha(PlotCubeGlobe.LineAlpha);
    // shape.LineStyle.BackColor.FromHTMLColor(PlotCubeGlobe.BackColor);
    shape.LineStyle.BackColor.SetAlpha(PlotCubeGlobe.LineAlpha);
    shape.LineStyle.Pattern = setPlot3dLinePattern(PlotCubeGlobe.LinePattern);
    shape.LineStyle.Width = PlotCubeGlobe.LineWidth;
    shape.FillStyle.Color.FromHTMLColor(PlotCubeGlobe.AreaColor);
    shape.FillStyle.Color.SetAlpha(PlotCubeGlobe.AreaAlpha);
}

/**
 * 设置文字、图片标签风格
 * @param label 标签
 */
function setTextOrImageStyle(label) {
    if(PlotToolGlobe.PlotOperation=="TEXT") {
        label.Style.Bold = PlotTextOrImageGlobe.Bold;
        label.Style.FontName = PlotTextOrImageGlobe.Front;
        label.Style.FontSize = PlotTextOrImageGlobe.Size;
        label.Style.Italic = PlotTextOrImageGlobe.Italic;
        label.Style.Underline = PlotTextOrImageGlobe.Underline;
        label.Style.TextColor.FromHTMLColor(PlotTextOrImageGlobe.Color);
        label.Style.TextColor.SetAlpha(PlotTextOrImageGlobe.ColorAlpha);
        label.Style.BackgroundColor.FromHTMLColor(PlotTextOrImageGlobe.BackgroundColor);
        label.Style.BackgroundColor.SetAlpha(PlotTextOrImageGlobe.BackgroundColorAlpha);
    }else{
        label.Style.Scale = PlotTextOrImageGlobe.Scale;
    }
    label.Style.LineToGround = PlotTextOrImageGlobe.LineToGround;
}

/**
 * 根据线风格名称设置风格值
 * @param name 风格名称
 */
function setPlot3dLinePattern(name) {
    switch (name){
        case "SOLID":
            return 0xFFFFFFFF;
            break;
        case "XLARGE_DASH":
            return 0xFFF00FFF;
            break;
        case "LARGE_DASH":
            return 0xFF0000FF;
            break;
        case "MEDIUM_DASH":
            return 0xF00FF00F;
            break;
        case "SMALL_DASH":
            return 0xC3C3C3C3;
            break;
        case "TINY_DASH":
            return 0x99999999;
            break;
        case "DOTS":
            return 0xAAAAAAAA;
            break;
        case "DASH_DOT_DASH":
            return 0xFF0180FF;
            break;
        case "DASH_DOT_DOT_DASH":
            return 0xFF0C30FF;
            break;
        default:
            return 0xFFFFFFFF;
    }
}