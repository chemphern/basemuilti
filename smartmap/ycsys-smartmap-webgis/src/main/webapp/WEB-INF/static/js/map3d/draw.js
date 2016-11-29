/**
 * Created by ChenLong.
 * Description:实现三维场景各种图形各种高度模式下的绘制
 * version: 1.0.0
 */

//----------------------------------------------绘制模块----------------------------------------------//

var DrawTool = {
    activate: function (type) {
        drawShape(type);
    }, 
    deactivate: function () {
        endDrawShape();
    },
    drawClickHandler:function(){
    
    },
    DrawType:{'POLYLINE':'POLYLINE','TERRAPOLYLINE':'TERRAPOLYLINE','SEGMENT':'SEGMENT',
              'TERRASEGMENT':'TERRASEGMENT','CIRCLE':'CIRCLE','TERRACIRCLE':'TERRACIRCLE',
              "LINEOFSIGHT":"LINEOFSIGHT",'POLYGON':'POLYGON','TERRAPOLYGON':'TERRAPOLYGON',
              'RECTANGLE':'RECTANGLE','TERRARECTANGLE':'TERRARECTANGLE'},
    drawEndHandler: function () {

    },
    drawIngHandler: function () {

    },
    clear:function(){
          deleteItemsByName(DrawToolGlobe.DrawToolFolder);
    }
}

//POLYLINE|TERRAPOLYLINE|SEGMENT|TERRASEGMENT|CIRCLE|TERRACIRCLE|LINEOFSIGHT
var DrawToolGlobe={
    "DrawOperation":"",
    "LineOfSightHeight":2,  
    "DrawToolFolder":HiddenGroup+ "\\画图文件夹",  
    "DrawToolFolderID":"",
    "PolygonHeight":0.5
}

function drawShape(type, map) {    
    deleteItemsByName(DrawToolGlobe.DrawToolFolder);
    DrawToolGlobe.DrawToolFolderID=createFolder(DrawToolGlobe.DrawToolFolder);
    DrawToolGlobe.DrawOperation=type;        
    YcMap3D.Window.SetInputMode(1);
    YcMap3D.AttachEvent("OnLButtonUp", drawToolLBUpHandler);
    YcMap3D.AttachEvent("OnRButtonUp", drawToolRBUpHandler);
    YcMap3D.AttachEvent("OnFrame", drawToolFrameHandler);
}

function endDrawShape() {    
    DrawToolGlobe.DrawOperation = "";
    YcMap3D.Window.SetInputMode(0);
    YcMap3D.DetachEvent("OnLButtonUp", drawToolLBUpHandler);
    YcMap3D.DetachEvent("OnRButtonUp", drawToolRBUpHandler);
    YcMap3D.DetachEvent("OnFrame", drawToolFrameHandler);
    //清空用户自定义绘制过程事件
    DrawTool.drawEndHandler = function () { }
    DrawTool.drawIngHandler = function () { }
}

function clearDrawShape(){
	deleteItemsByName(DrawToolGlobe.DrawToolFolder);
}

//画图工具左击事件
function drawToolLBUpHandler(flags, x, y) {
    var clickPosition = getMousePosition(-1);
    if (DrawToolGlobe.DrawOperation == "POLYLINE" || DrawToolGlobe.DrawOperation == "TERRAPOLYLINE" || DrawToolGlobe.DrawOperation == "SEGMENT" || DrawToolGlobe.DrawOperation == "TERRASEGMENT") {
        var altitudeType = 3;
        var isSegment = false;
        if (DrawToolGlobe.DrawOperation == "TERRAPOLYLINE" || DrawToolGlobe.DrawOperation == "TERRASEGMENT") altitudeType = 2; //Altitude 2为在地表高度  
        if (DrawToolGlobe.DrawOperation == "SEGMENT" || DrawToolGlobe.DrawOperation == "TERRASEGMENT") isSegment = true;
        drawPolylineByLBUp(clickPosition, altitudeType, isSegment);
    } else if (DrawToolGlobe.DrawOperation == "ADDPOLYLINEPOINT" || DrawToolGlobe.DrawOperation == "ADDSEGMENTPOINT") {
        addPolylinePointByLBUp(clickPosition);
        if (DrawToolGlobe.DrawOperation == "ADDSEGMENTPOINT") {
            DrawTool.drawEndHandler(findItemByName(DrawToolGlobe.DrawToolFolder+"\\TempFreeHand"));
            DrawTool.deactivate();
        }
    }
    if (DrawToolGlobe.DrawOperation == "LINEOFSIGHT") {
        drawLineOfSightByLBUp(clickPosition);
    } else if (DrawToolGlobe.DrawOperation == "ADDLINEOFSIGHTPOINT") {
        addLineOfSightPointByLBUp(clickPosition);
    }
    if (DrawToolGlobe.DrawOperation == "CIRCLE" || DrawToolGlobe.DrawOperation == "TERRACIRCLE") {
        var altitudeType = 3;
        if (DrawToolGlobe.DrawOperation == "TERRACIRCLE") altitudeType = 2;
        drawCircleByLBUp(clickPosition, altitudeType);
    } else if (DrawToolGlobe.DrawOperation == "CHANGERADIUS") {
        DrawTool.drawEndHandler(findItemByName(DrawToolGlobe.DrawToolFolder+"\\TempFreeHand"));
        DrawTool.deactivate();
    }
    if (DrawToolGlobe.DrawOperation == "POLYGON" || DrawToolGlobe.DrawOperation == "TERRAPOLYGON") {
        var altitudeType = 3;
        if (DrawToolGlobe.DrawOperation == "TERRAPOLYGON") altitudeType = 2;
        drawPolygonByLBUp(clickPosition, altitudeType);
    } else if (DrawToolGlobe.DrawOperation == "ADDPOLYGONPOINT") {
        addPolygonPointByLBUp(clickPosition);
    }
    if (DrawToolGlobe.DrawOperation == "RECTANGLE" || DrawToolGlobe.DrawOperation == "TERRARECTANGLE") {
        var altitudeType = 3;
        if (DrawToolGlobe.DrawOperation == "TERRARECTANGLE") altitudeType = 2;
        drawRectangleByLBUp(clickPosition, altitudeType);
    } else if (DrawToolGlobe.DrawOperation == "CHANGERECT") {
        DrawTool.drawEndHandler(findItemByName(DrawToolGlobe.DrawToolFolder+"\\TempFreeHand"));
        DrawTool.deactivate();
    }
}

//每一帧都执行的事件
function drawToolFrameHandler() {
    var mousePosition = getMousePosition(-1);
    if (DrawToolGlobe.DrawOperation == 'ADDPOLYLINEPOINT' || DrawToolGlobe.DrawOperation == "ADDSEGMENTPOINT") {//画线的拖动操作
        var polyline = findItemByName(DrawToolGlobe.DrawToolFolder+"\\TempFreeHand");
        if (typeof polyline == 'object') {
            var tempGeo = polyline.Geometry;
            polyline.Geometry.StartEdit();
            polyline.Geometry.EndPoint.X = mousePosition.X;
            polyline.Geometry.EndPoint.Y = mousePosition.Y;
            polyline.Geometry.EndPoint.Z = mousePosition.Altitude;
            polyline.Geometry.EndEdit();
            //绘制过程中用户自定义事件
        	DrawTool.drawIngHandler(polyline);
        }
    }
    if (DrawToolGlobe.DrawOperation == "ADDLINEOFSIGHTPOINT") {
        var lineOfSight = findItemByName(DrawToolGlobe.DrawToolFolder+"\\TempFreeHand");
        var tempgeo = lineOfSight.Geometry;
        tempgeo.StartEdit();
        var endpoint = tempgeo.Points.item(tempgeo.Points.Count - 1);
        endpoint.X = mousePosition.x;
        endpoint.Y = mousePosition.y;
        endpoint.Z = mousePosition.altitude;
        tempgeo.EndEdit();
        //绘制过程中用户自定义事件
        DrawTool.drawIngHandler(lineOfSight);
    }
    if (DrawToolGlobe.DrawOperation == "CHANGERADIUS") {
        var circle = findItemByName(DrawToolGlobe.DrawToolFolder+"\\TempFreeHand");
        if (typeof circle == 'object') {
            var distance = circle.Position.DistanceTo(mousePosition);
            circle.Radius = distance;
            //绘制过程中用户自定义事件
            DrawTool.drawIngHandler(circle);
        }
    }
    if (DrawToolGlobe.DrawOperation == "CHANGERECT") {
        var rectangle = findItemByName(DrawToolGlobe.DrawToolFolder+"\\TempFreeHand");
        if (typeof rectangle == 'object') {
			rectangle.Right = mousePosition.X;
			rectangle.Bottom = mousePosition.Y;
            //绘制过程中用户自定义事件
            DrawTool.drawIngHandler(rectangle);
        }
    }
    if (DrawToolGlobe.DrawOperation == "ADDPOLYGONPOINT") {
        var polygon = findItemByName(DrawToolGlobe.DrawToolFolder+"\\TempFreeHand");
        var height = parseFloat(mousePosition.Altitude) + DrawToolGlobe.PolygonHeight;
        if (typeof polygon == 'object') {
            var tempGeo = polygon.Geometry;
            polygon.Geometry.StartEdit();
            polygon.Geometry.Rings.Item(0).EndPoint.X = mousePosition.X;
            polygon.Geometry.Rings.Item(0).EndPoint.Y = mousePosition.Y;
            polygon.Geometry.Rings.Item(0).EndPoint.Z = mousePosition.Altitude;
            polygon.Geometry.EndEdit();
            if (polygon.Geometry.GeometryTypeStr != 'Polygon') {
                polygon.Geometry = tempGeo;
            }
            //绘制过程中用户自定义事件
    		DrawTool.drawIngHandler(polygon);
        }
    }
}

//画图工具右击事件
function drawToolRBUpHandler(flags, x, y) {
    if (DrawToolGlobe.DrawOperation == "ADDPOLYLINEPOINT" || DrawToolGlobe.DrawOperation == "ADDPOLYGONPOINT" || DrawToolGlobe.DrawOperation == "ADDLINEOFSIGHTPOINT") {
        DrawTool.drawEndHandler(findItemByName(DrawToolGlobe.DrawToolFolder+"\\TempFreeHand"));
    }
    DrawTool.deactivate();
}

function drawPolylineByLBUp(clickPosition, altitudeType, isSegment) {
    var geoArr = new Array(clickPosition.x, clickPosition.y, clickPosition.Altitude, clickPosition.x, clickPosition.y, clickPosition.Altitude);
    var polyline = YcMap3D.Creator.CreatePolylineFromArray(geoArr, "#FF0000", altitudeType, DrawToolGlobe.DrawToolFolderID, 'TempFreeHand');
    polyline.Terrain.GroundObject = false;
    polyline.LineStyle.Color.FromHTMLColor("#FF0000");
    polyline.LineStyle.Width = 0.5;
    polyline.LineStyle.Color.SetAlpha(0.8);
    if (isSegment) {
        DrawToolGlobe.DrawOperation = 'ADDSEGMENTPOINT';
    } else {
        DrawToolGlobe.DrawOperation = 'ADDPOLYLINEPOINT';
    }
}

function addPolylinePointByLBUp(clickPosition) {
    var polyline = findItemByName(DrawToolGlobe.DrawToolFolder+"\\TempFreeHand");
    if (typeof polyline == 'object') {
        var tempGeo = polyline.Geometry;
        polyline.Geometry.StartEdit();
        polyline.Geometry.Points.AddPoint(clickPosition.X, clickPosition.Y, clickPosition.Altitude);
        polyline.Geometry.EndEdit();
    }
}

function drawLineOfSightByLBUp(clickPosition) {
    var geoArr = new Array(clickPosition.x, clickPosition.y, clickPosition.Altitude, clickPosition.x, clickPosition.y, parseFloat(DrawToolGlobe.LineOfSightHeight) + clickPosition.Altitude, clickPosition.x, clickPosition.y, parseFloat(DrawToolGlobe.LineOfSightHeight) + clickPosition.Altitude);
    var polyline = YcMap3D.Creator.CreatePolylineFromArray(geoArr, "#0080ff", 3, DrawToolGlobe.DrawToolFolderID, 'TempFreeHand');
    polyline.Terrain.GroundObject = false;
    polyline.LineStyle.Color.FromHTMLColor("#ffff00");
    polyline.LineStyle.Width = 0.5;
    polyline.LineStyle.Color.SetAlpha(0.8);
    DrawToolGlobe.DrawOperation = 'ADDLINEOFSIGHTPOINT';
}

function addLineOfSightPointByLBUp(clickPosition) {
    var lineOfSight = findItemByName(DrawToolGlobe.DrawToolFolder+"\\TempFreeHand");
    lineOfSight.Geometry.StartEdit();
    var viewpoint = lineOfSight.Geometry.Points.item(1);
    lineOfSight.Geometry.Points.AddPoint(viewpoint.x, viewpoint.y, viewpoint.z);
    lineOfSight.Geometry.Points.AddPoint(viewpoint.x, viewpoint.y, viewpoint.z);
    lineOfSight.Geometry.EndEdit();
}

function drawCircleByLBUp(clickPosition, altitudeType) {        
    clickPosition.AltitudeType = altitudeType;
    var circle = YcMap3D.Creator.CreateCircle(clickPosition, 0.1, "#FF0000", "#384048", DrawToolGlobe.DrawToolFolderID, 'TempFreeHand');
    circle.Terrain.GroundObject = false;
    circle.NumberOfSegments = 100;
	//circle.FillStyle.Texture.FileName = "$$WATER$$";
//	circle.FillStyle.Color.FromHTMLColor("#0080ff");
    circle.FillStyle.Color.SetAlpha(0.5);
//  circle.LineStyle.Color.SetAlpha(0.5);
    DrawToolGlobe.DrawOperation = 'CHANGERADIUS';
}

function drawRectangleByLBUp(clickPosition, altitudeType){
	clickPosition.AltitudeType = altitudeType;
	var rectangle = YcMap3D.Creator.CreateRectangle(clickPosition,0.000001,0.000001, "#FF0000", "#384048",DrawToolGlobe.DrawToolFolderID, 'TempFreeHand');
	rectangle.Terrain.GroundObject = false;
	rectangle.FillStyle.Color.SetAlpha(0.5);
    DrawToolGlobe.DrawOperation = 'CHANGERECT';
}

function drawPolygonByLBUp(clickPosition, altitudeType) {
//  deleteItemsByName(DrawToolGlobe.DrawToolFolder);;
    var height = parseFloat(clickPosition.Altitude) + DrawToolGlobe.PolygonHeight;
    var geoArr = new Array(clickPosition.x, clickPosition.y, clickPosition.Altitude, clickPosition.x, clickPosition.y, clickPosition.Altitude, clickPosition.x, clickPosition.y, clickPosition.Altitude, clickPosition.x, clickPosition.y, clickPosition.Altitude);
    var polygongeo = YcMap3D.Creator.GeometryCreator.CreateLinearRingGeometry(geoArr);
    var polygon = YcMap3D.Creator.CreatePolygon(polygongeo, "#FF0000", "#384048", altitudeType, DrawToolGlobe.DrawToolFolderID, 'TempFreeHand');
    polygon.Terrain.GroundObject = false;
    polygon.FillStyle.Color.FromHTMLColor("#384048");
    polygon.FillStyle.Color.SetAlpha(0.5);
//  polygon.LineStyle.Color.SetAlpha(0.5);
    DrawToolGlobe.DrawOperation = 'ADDPOLYGONPOINT';
}

function addPolygonPointByLBUp(clickPosition) {
    var polygon = findItemByName(DrawToolGlobe.DrawToolFolder+"\\TempFreeHand");
    var height = parseFloat(clickPosition.Altitude) + DrawToolGlobe.PolygonHeight;
    if (typeof polygon == 'object') {
        var tempGeo = polygon.Geometry;
        polygon.Geometry.StartEdit();
        polygon.Geometry.Rings.Item(0).Points.AddPoint(clickPosition.X, clickPosition.Y, clickPosition.Altitude);
        polygon.Geometry.EndEdit();
        if (polygon.Geometry.GeometryTypeStr != 'Polygon') {
            polygon.Geometry = tempGeo;
        }
    }
}