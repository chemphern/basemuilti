/**
 * Created by ChenLong.
 * Description:实现三维场景各种模式线、面的量测
 * version: 1.0.0
 */

var MeasureTool={
    activate:function(type){
        measureStart(type);
    },
    MeasureType:{'HORIZONTAL':'HORIZONTAL','VERTICAL':'VERTICAL','AERICAL':'AERICAL','AREA':'AREA','GROUNDAREA':'GROUNDAREA'},
    deactivate:function(){
        MeasureToolGlobe.MeasureOperation="EndMeasure";
        measureToolRBUpHandler();
    }     
}

var MeasureToolGlobe={
    "MeasureFolder":HiddenGroup+"\\测量临时文件",
    "MeasureOperation":"",
    "LineColor":"#FF0000",
    "FillColor":"#384048",
    "FillAlpha":0.5,
    "LabelStyle":"",
    "ImagePath":getProjectPath()+"img/YcMap3D/point.png",
    "MeasureFolderID":""
}   

function measureStart(type){
    deleteItemsByName(MeasureToolGlobe.MeasureFolder);
    MeasureToolGlobe.MeasureFolderID=createFolder(MeasureToolGlobe.MeasureFolder);
    MeasureToolGlobe.LabelStyle=YcMap3D.Creator.CreateLabelStyle(0);
    MeasureToolGlobe.LabelStyle.TextColor.FromHTMLColor("#ffff00");
    MeasureToolGlobe.LabelStyle.Bold = true;
    MeasureToolGlobe.LabelStyle.PivotAlignment = "Center,Center";
    MeasureToolGlobe.MeasureOperation=type;
    YcMap3D.Window.SetInputMode(1);
//  YcMap3D.Window.ShowMessageBarText("请绘制要进行测量的图形",1,-1);
    YcMap3D.AttachEvent("OnLButtonUp", measureToolLBUpHandler);
    YcMap3D.AttachEvent("OnRButtonUp", measureToolRBUpHandler);
    YcMap3D.AttachEvent("OnFrame", measureToolFrameHandler);
}

function measureToolLBUpHandler(flags, x, y) {
    var clickPosition = getMousePosition(-1);
    var tempOperation=MeasureToolGlobe.MeasureOperation;
       if (tempOperation == MeasureTool.MeasureType.HORIZONTAL || 
           tempOperation == MeasureTool.MeasureType.VERTICAL)
       {    //测量水平距离
            measureSegmentByLBUp(clickPosition);
       }else if(tempOperation=="ADDHORIZONTAL"||tempOperation=="ADDVERTICAL"){
            addSegmentPointByLBUp(clickPosition);
       }
       if(tempOperation==MeasureTool.MeasureType.AERICAL){//测量空间距离
            measureAerialByLBUp(clickPosition);
       }else if(tempOperation=="ADDAERIAL"){
            addAerialPointByLUp(clickPosition);
       }
       if(tempOperation==MeasureTool.MeasureType.AREA){
            measureAreaByLBUp(clickPosition);
       }else if(MeasureToolGlobe.MeasureOperation=="ADDAREA"){
                addAreaPointByLUp(clickPosition);
       }
       if(tempOperation==MeasureTool.MeasureType.GROUNDAREA){
            measureGroundAreaByLBUp(clickPosition);
       }else if(MeasureToolGlobe.MeasureOperation=="ADDGROUNDAREA"){
                addGroundAreaPointByLUp(clickPosition);
       }
}
  
function measureToolRBUpHandler(clickPosition){
	//结束量测操作
	if(MeasureToolGlobe.MeasureOperation=="ADDAERIAL"){           
        addAerialPointByRUp();
    }else if(MeasureToolGlobe.MeasureOperation=="ADDAREA"){           
        addAreaPointByRUp();
    }else if(MeasureToolGlobe.MeasureOperation=="ADDGROUNDAREA"){
        addGroundAreaPointByRUp();
    }
    //结束量测状态
    endMeasureOperation();
}

//结束量测状态
function endMeasureOperation(){
    YcMap3D.Window.SetInputMode(0);
    YcMap3D.DetachEvent("OnLButtonUp", measureToolLBUpHandler);
    YcMap3D.DetachEvent("OnRButtonUp", measureToolRBUpHandler);
    YcMap3D.DetachEvent("OnFrame", measureToolFrameHandler);
    MeasureToolGlobe.MeasureOperation="";
}

function measureToolFrameHandler(){       
    var mousePosition = getMousePosition(-1);
    if(mousePosition.X && mousePosition.Y){
	    if(MeasureToolGlobe.MeasureOperation=="ADDHORIZONTAL"||MeasureToolGlobe.MeasureOperation=="ADDVERTICAL"){   
	        addSegmentPointFrame(mousePosition);
	    }
	    else if(MeasureToolGlobe.MeasureOperation=="ADDAERIAL"){
	        addAerialPointFrame(mousePosition);
	    }
	    else if(MeasureToolGlobe.MeasureOperation=="ADDAREA"){
	        addAreaPointFrame(mousePosition);
	    }
	    else if(MeasureToolGlobe.MeasureOperation=="ADDGROUNDAREA"){
	        addGroundAreaPointFrame(mousePosition);
	    }
    }
}

//获得画拆线时中点的位置
function getCenterPosition(startPoint,endPosition){
    var startZ=startPoint.Z;
    var centerX,centerY,centerZ;       
    var endZ=endPosition.Altitude;      
    if(startZ>endZ){
        centerZ=startZ;
        centerX=endPosition.X;
        centerY=endPosition.Y;            
    }else{
        centerZ=endZ;
        centerX=startPoint.X;
        centerY=startPoint.Y;       
    }
    return  YcMap3D.Creator.CreatePosition(centerX,centerY,centerZ,3);
}

function measureSegmentByLBUp(clickPosition){
    //首先创建一条包含三个点的拆线和三个点位置的Label及一个显示结果的Label
    var geoArr = new Array(clickPosition.x, clickPosition.y, clickPosition.Altitude, clickPosition.x, clickPosition.y, clickPosition.Altitude, clickPosition.x, clickPosition.y, clickPosition.Altitude);
    var polyline = YcMap3D.Creator.CreatePolylineFromArray(geoArr,MeasureToolGlobe.LineColor , 3, MeasureToolGlobe.MeasureFolderID, 'Horizontal');
    YcMap3D.Creator.CreateImageLabel(clickPosition,MeasureToolGlobe.ImagePath,MeasureToolGlobe.LabelStyle,MeasureToolGlobe.MeasureFolderID,"HorizontalStartLabel");
    YcMap3D.Creator.CreateImageLabel(clickPosition,MeasureToolGlobe.ImagePath,MeasureToolGlobe.LabelStyle,MeasureToolGlobe.MeasureFolderID,"HorizontalCenterLabel");
    YcMap3D.Creator.CreateImageLabel(clickPosition,MeasureToolGlobe.ImagePath,MeasureToolGlobe.LabelStyle,MeasureToolGlobe.MeasureFolderID,"HorizontalEndLabel");
    var resultlabel=YcMap3D.Creator.CreateTextLabel(clickPosition,"0米",MeasureToolGlobe.LabelStyle,MeasureToolGlobe.MeasureFolderID,"HorizontalResultLabel");
    if (MeasureToolGlobe.MeasureOperation == MeasureTool.MeasureType.HORIZONTAL){
//          YcMap3D.Window.ShowMessageBarText("水平距离:0米",0,-1);
            MeasureToolGlobe.MeasureOperation="ADDHORIZONTAL";
            resultlabel.Style.PivotAlignment='Bottom,Center';
     }else{
//          YcMap3D.Window.ShowMessageBarText("垂直距离:0米",0,-1);
            MeasureToolGlobe.MeasureOperation="ADDVERTICAL";
            resultlabel.Style.PivotAlignment='Center,Left';
     } 
}

function addSegmentPointByLBUp(clickPosition){    
    MeasureToolGlobe.MeasureOperation="EndMeasure";
    YcMap3D.Window.SetInputMode(0);
}

function addSegmentPointFrame(mousePosition){
    var horizontal=YcMap3D.ProjectTree.GetObject(YcMap3D.ProjectTree.FindItem(MeasureToolGlobe.MeasureFolder+"\\Horizontal"))
    var startPoint=horizontal.Geometry.Points.Item(0);
    var centerPoint=horizontal.Geometry.Points.Item(1);
    var centerPosition=getCenterPosition(startPoint,mousePosition);
    //设置中点和终点位置
    horizontal.Geometry.StartEdit();
    horizontal.Geometry.Points.Item(1).X=centerPosition.X;
    horizontal.Geometry.Points.Item(1).Y=centerPosition.Y;
    horizontal.Geometry.Points.Item(1).Z=centerPosition.Altitude;
    horizontal.Geometry.EndPoint.X=mousePosition.X;
    horizontal.Geometry.EndPoint.Y=mousePosition.Y;
    horizontal.Geometry.EndPoint.Z=mousePosition.Altitude;
    horizontal.Geometry.EndEdit();
    //设置中点Label和终点的Label位置
    var endLabel=YcMap3D.ProjectTree.GetObject(YcMap3D.ProjectTree.FindItem(MeasureToolGlobe.MeasureFolder+"\\HorizontalEndLabel"));
    endLabel.Position=mousePosition;
    var centerLabel=YcMap3D.ProjectTree.GetObject(YcMap3D.ProjectTree.FindItem(MeasureToolGlobe.MeasureFolder+"\\HorizontalCenterLabel"));
    centerLabel.Position.X=centerPosition.X;
    centerLabel.Position.Y=centerPosition.Y;
    centerLabel.Position.Altitude=centerPosition.Altitude;
    //显示结果距离及设置结果Label位置
    if(MeasureToolGlobe.MeasureOperation=="ADDHORIZONTAL"){
        var distance=(centerPosition.DistanceTo(mousePosition)).toFixed(2);
        var resultLabel=YcMap3D.ProjectTree.GetObject(YcMap3D.ProjectTree.FindItem(MeasureToolGlobe.MeasureFolder+"\\HorizontalResultLabel"));
        resultLabel.Text=distance+"米";
//      YcMap3D.Window.ShowMessageBarText("水平距离:"+distance+"米",0,-1);
        resultLabel.Position.X=(startPoint.X+mousePosition.X)*0.5;
        resultLabel.Position.Y=(startPoint.Y+mousePosition.Y)*0.5;
        resultLabel.Position.Altitude=centerPosition.Altitude;
    }else{
        var startPosition=YcMap3D.Creator.CreatePosition(startPoint.X,startPoint.Y,startPoint.Z,3);
        var distance=(centerPosition.DistanceTo(startPosition)).toFixed(2);
        var resultLabel=YcMap3D.ProjectTree.GetObject(YcMap3D.ProjectTree.FindItem(MeasureToolGlobe.MeasureFolder+"\\HorizontalResultLabel"));
        resultLabel.Text=distance+"米";
//      YcMap3D.Window.ShowMessageBarText("垂直距离:"+distance+"米",0,-1);
        resultLabel.Position.X=centerPoint.X;
        resultLabel.Position.Y=centerPoint.Y;
        resultLabel.Position.Altitude=(startPosition.Altitude+mousePosition.Altitude)*0.5;
    }       
}

  function measureAerialByLBUp(clickPosition){
  	clickPosition.AltitudeType=3;
    //先创建一个包含起点和终点的拆线及起点Label终点Label
    var geoArr = new Array(clickPosition.x, clickPosition.y, clickPosition.Altitude, clickPosition.x, clickPosition.y, clickPosition.Altitude);
    var polyline = YcMap3D.Creator.CreatePolylineFromArray(geoArr,MeasureToolGlobe.LineColor , 3, MeasureToolGlobe.MeasureFolderID, 'Aerial');
    //创建起点标识
    YcMap3D.Creator.CreateTextLabel(clickPosition,"起点",MeasureToolGlobe.LabelStyle,MeasureToolGlobe.MeasureFolderID,"AerialResultLabel");
    YcMap3D.Creator.CreateImageLabel(clickPosition,MeasureToolGlobe.ImagePath,MeasureToolGlobe.LabelStyle,MeasureToolGlobe.MeasureFolderID,"AerialStartLabel");
    YcMap3D.Creator.CreateImageLabel(clickPosition,MeasureToolGlobe.ImagePath,MeasureToolGlobe.LabelStyle,MeasureToolGlobe.MeasureFolderID,"AerialMoveEndLabel");
    var resultlabel=YcMap3D.Creator.CreateTextLabel(clickPosition,"0米",MeasureToolGlobe.LabelStyle,MeasureToolGlobe.MeasureFolderID,"AerialMoveResultLabel");
//  YcMap3D.Window.ShowMessageBarText("空间距离:0米",0,-1);
    MeasureToolGlobe.MeasureOperation="ADDAERIAL";
    resultlabel.Style.PivotAlignment='Bottom,Center';
}

function addAerialPointFrame(mousePosition){
    var aerial=YcMap3D.ProjectTree.GetObject(YcMap3D.ProjectTree.FindItem(MeasureToolGlobe.MeasureFolder+"\\Aerial"));
    var startPoint=aerial.Geometry.StartPoint;
    mousePosition.AltitudeType=3;
    //设置终点位置
    aerial.Geometry.StartEdit();
    aerial.Geometry.EndPoint.X=mousePosition.X;
    aerial.Geometry.EndPoint.Y=mousePosition.Y;
    aerial.Geometry.EndPoint.Z=mousePosition.Altitude;
    aerial.Geometry.EndEdit();
    //设置终点的Label位置
    var endLabel=YcMap3D.ProjectTree.GetObject(YcMap3D.ProjectTree.FindItem(MeasureToolGlobe.MeasureFolder+"\\AerialMoveEndLabel"));
    endLabel.Position=mousePosition; 
    //显示结果距离及设置结果Label位置   
    var lastSecondPoint= aerial.Geometry.Points.Item(aerial.Geometry.Points.Count-2);
    var lastSecondPosition=YcMap3D.Creator.CreatePosition(lastSecondPoint.X,lastSecondPoint.Y,lastSecondPoint.Z,3);
    var distance=(lastSecondPosition.DistanceTo(mousePosition)).toFixed(2);
    var resultLabel=YcMap3D.ProjectTree.GetObject(YcMap3D.ProjectTree.FindItem(MeasureToolGlobe.MeasureFolder+"\\AerialMoveResultLabel"));
    resultLabel.Text=distance+"米";
//  YcMap3D.Window.ShowMessageBarText("空间距离:"+(aerial.Geometry.Length).toFixed(2)+"米",0,-1);
    resultLabel.Position.X=(lastSecondPosition.X+mousePosition.X)*0.5;
    resultLabel.Position.Y=(lastSecondPosition.Y+mousePosition.Y)*0.5;
    resultLabel.Position.Altitude=(lastSecondPosition.Altitude+mousePosition.Altitude)*0.5;      
}

function addAerialPointByLUp(clickPosition){
	clickPosition.AltitudeType=3;
    //点击时添加一个终点及一个结果Label、转折点Label
    var aerial=YcMap3D.ProjectTree.GetObject(YcMap3D.ProjectTree.FindItem(MeasureToolGlobe.MeasureFolder+"\\Aerial"));
    aerial.Geometry.StartEdit();
    aerial.Geometry.Points.AddPoint(clickPosition.X,clickPosition.Y,clickPosition.Z);
    aerial.Geometry.EndEdit();      
    YcMap3D.Creator.CreateImageLabel(clickPosition,MeasureToolGlobe.ImagePath,MeasureToolGlobe.LabelStyle,MeasureToolGlobe.MeasureFolderID,"AerialLabel"); 
    var resultLabel=YcMap3D.ProjectTree.GetObject(YcMap3D.ProjectTree.FindItem(MeasureToolGlobe.MeasureFolder+"\\AerialMoveResultLabel"));
    YcMap3D.Creator.CreateTextLabel(resultLabel.Position,resultLabel.Text,resultLabel.Style,MeasureToolGlobe.MeasureFolderID,"AerialResultLabel");
}

function addAerialPointByRUp(){
     var aerial=YcMap3D.ProjectTree.GetObject(YcMap3D.ProjectTree.FindItem(MeasureToolGlobe.MeasureFolder+"\\Aerial"));
     aerial.Geometry.StartEdit();
     aerial.Geometry.Points.DeletePoint(aerial.Geometry.Points.Count-1);
     aerial.Geometry.EndEdit();  
     YcMap3D.Window.SetInputMode(0);
     //标识终点及总长度
     var resultText = "总长度：" + (aerial.Geometry.Length).toFixed(2) + "米";
     var aerialEndPosition = YcMap3D.Creator.CreatePosition(aerial.Geometry.EndPoint.X,aerial.Geometry.EndPoint.Y,aerial.Geometry.EndPoint.Z + 10,3);
     YcMap3D.Creator.CreateTextLabel(aerialEndPosition,resultText,MeasureToolGlobe.LabelStyle,MeasureToolGlobe.MeasureFolderID,"AerialResultLabel");
     //删除过程元素
     YcMap3D.ProjectTree.DeleteItem(YcMap3D.ProjectTree.FindItem(MeasureToolGlobe.MeasureFolder+"\\AerialMoveResultLabel"));
     YcMap3D.ProjectTree.DeleteItem(YcMap3D.ProjectTree.FindItem(MeasureToolGlobe.MeasureFolder+"\\AerialMoveEndLabel"));
//   YcMap3D.Window.ShowMessageBarText("空间距离:"+(aerial.Geometry.Length).toFixed(2)+"米",0,-1);
     MeasureToolGlobe.MeasureOperation="EndMeasure";
}

function measureAreaByLBUp(clickPosition){
    //先创建一个多边形及显示结果的Label
    var geoArr = new Array(
    clickPosition.X, clickPosition.Y, clickPosition.Altitude,
    clickPosition.X, clickPosition.Y, clickPosition.Altitude,
    clickPosition.X, clickPosition.Y, clickPosition.Altitude, 
    clickPosition.X, clickPosition.Y, clickPosition.Altitude);
    var pogygon = YcMap3D.Creator.CreatePolygonFromArray(geoArr,MeasureToolGlobe.LineColor,MeasureToolGlobe.FillColor, 3, MeasureToolGlobe.MeasureFolderID, 'Area');
    pogygon.FillStyle.Color.SetAlpha(MeasureToolGlobe.FillAlpha);
    var resultlabel=YcMap3D.Creator.CreateTextLabel(clickPosition,"0平方米",MeasureToolGlobe.LabelStyle,MeasureToolGlobe.MeasureFolderID,"AreaResultLabel");
//  YcMap3D.Window.ShowMessageBarText("表面面积:0平方米",0,-1);
    MeasureToolGlobe.MeasureOperation="ADDAREA";
    resultlabel.Style.PivotAlignment='Center,Center';
}

 function addAreaPointFrame(mousePosition){
    var area=YcMap3D.ProjectTree.GetObject(YcMap3D.ProjectTree.FindItem(MeasureToolGlobe.MeasureFolder+"\\Area"));
    var geoBak=area.Geometry.Clone();
    var geo=area.Geometry.Rings.Item(0);
    //修改终点位置
    area.Geometry.StartEdit();
    geo.EndPoint.X=mousePosition.X;
    geo.EndPoint.Y=mousePosition.Y;
    geo.EndPoint.Z=mousePosition.Altitude;
    area.Geometry.EndEdit();
     if (area.Geometry.GeometryType == 6) {       
            area.Geometry = geoBak;
     }
    //显示结果距离及设置结果Label位置
    var resultLabel=YcMap3D.ProjectTree.GetObject(YcMap3D.ProjectTree.FindItem(MeasureToolGlobe.MeasureFolder+"\\AreaResultLabel"));
    resultLabel.Text=(area.Geometry.Area).toFixed(2)+"平方米";
//  YcMap3D.Window.ShowMessageBarText("表面面积:"+(area.Geometry.Area).toFixed(2)+"平方米"+mousePosition.X+":"+mousePosition.Y,0,-1);
    labelHeight = (resultLabel.Position.Altitude + mousePosition.Altitude)/2;
    resultLabel.Position=YcMap3D.Creator.CreatePosition(area.Geometry.Centroid.X,area.Geometry.Centroid.Y,labelHeight,3);   
}
         
function addAreaPointByLUp(clickPosition){
    var area=YcMap3D.ProjectTree.GetObject(YcMap3D.ProjectTree.FindItem(MeasureToolGlobe.MeasureFolder+"\\Area"));
    var geoBak=area.Geometry.Clone();
    //添加端点
    var geo= area.Geometry.Rings.Item(0);
    area.Geometry.StartEdit();
    geo.Points.AddPoint(clickPosition.X,clickPosition.Y,clickPosition.Altitude);
    area.Geometry.EndEdit();
     if (area.Geometry.GeometryType == 6) {       
            area.Geometry = geoBak;
     }
    //显示结果距离及设置结果Label位置
    var resultLabel=YcMap3D.ProjectTree.GetObject(YcMap3D.ProjectTree.FindItem(MeasureToolGlobe.MeasureFolder+"\\AreaResultLabel"));
    resultLabel.Text=(area.Geometry.Area).toFixed(2)+"平方米";
//  YcMap3D.Window.ShowMessageBarText("表面面积:"+(area.Geometry.Area).toFixed(2)+"平方米",0,-1);     
    labelHeight = (resultLabel.Position.Altitude + clickPosition.Altitude)/2;
    resultLabel.Position=YcMap3D.Creator.CreatePosition(area.Geometry.Centroid.X,area.Geometry.Centroid.Y,labelHeight,3);     
}

function addAreaPointByRUp(){
    var area=YcMap3D.ProjectTree.GetObject(YcMap3D.ProjectTree.FindItem(MeasureToolGlobe.MeasureFolder+"\\Area"));
    area.Geometry.StartEdit();
    area.Geometry.Rings.Item(0).Points.DeletePoint(area.Geometry.Rings.Item(0).NumPoints-1);
    area.Geometry.EndEdit();
    YcMap3D.Window.SetInputMode(0);
    var resultLabel=YcMap3D.ProjectTree.GetObject(YcMap3D.ProjectTree.FindItem(MeasureToolGlobe.MeasureFolder+"\\AreaResultLabel"));
    resultLabel.Text=(area.Geometry.Area).toFixed(2)+"平方米";
//  YcMap3D.Window.ShowMessageBarText("表面面积:"+(area.Geometry.Area).toFixed(2)+"平方米",0,-1);
    MeasureToolGlobe.MeasureOperation="EndMeasure";
}

function measureGroundAreaByLBUp(clickPosition){
    //先创建一个多边形及显示结果的Label
    var geoArr = new Array(
    clickPosition.X, clickPosition.Y, clickPosition.Altitude,
    clickPosition.X, clickPosition.Y, clickPosition.Altitude,
    clickPosition.X, clickPosition.Y, clickPosition.Altitude, 
    clickPosition.X, clickPosition.Y, clickPosition.Altitude);
    var pogygon = YcMap3D.Creator.CreatePolygonFromArray(geoArr,MeasureToolGlobe.LineColor,MeasureToolGlobe.FillColor, 2, MeasureToolGlobe.MeasureFolderID, 'GoundArea');
    pogygon.FillStyle.Color.SetAlpha(MeasureToolGlobe.FillAlpha);
    var resultlabel=YcMap3D.Creator.CreateTextLabel(clickPosition,"0平方米",MeasureToolGlobe.LabelStyle,MeasureToolGlobe.MeasureFolderID,"AreaResultLabel");
//  YcMap3D.Window.ShowMessageBarText("地表面积:0平方米",0,-1);
    MeasureToolGlobe.MeasureOperation="ADDGROUNDAREA";
    resultlabel.Style.PivotAlignment='Center,Center';
}

 function addGroundAreaPointFrame(mousePosition){
    var area=YcMap3D.ProjectTree.GetObject(YcMap3D.ProjectTree.FindItem(MeasureToolGlobe.MeasureFolder+"\\GoundArea"));
    var geoBak=area.Geometry.Clone();
    var geo=area.Geometry.Rings.Item(0);
    //修改终点位置
    area.Geometry.StartEdit();
    geo.EndPoint.X=mousePosition.X;
    geo.EndPoint.Y=mousePosition.Y;
    geo.EndPoint.Z=mousePosition.Altitude;
    area.Geometry.EndEdit();
     if (area.Geometry.GeometryType == 6) {       
            area.Geometry = geoBak;
     }
    //显示结果距离及设置结果Label位置
    var resultLabel=YcMap3D.ProjectTree.GetObject(YcMap3D.ProjectTree.FindItem(MeasureToolGlobe.MeasureFolder+"\\AreaResultLabel"));
    resultLabel.Text=(area.Geometry.Area).toFixed(2)+"平方米";
//  YcMap3D.Window.ShowMessageBarText("地表面积:"+(area.Geometry.Area).toFixed(2)+"平方米"+mousePosition.X+":"+mousePosition.Y,0,-1);
    resultLabel.Position=YcMap3D.Creator.CreatePosition(area.Position.X,area.Position.Y,2,0);   
}
         
function addGroundAreaPointByLUp(clickPosition){
    var area=YcMap3D.ProjectTree.GetObject(YcMap3D.ProjectTree.FindItem(MeasureToolGlobe.MeasureFolder+"\\GoundArea"));
    var geoBak=area.Geometry.Clone();
    //添加端点
    var geo= area.Geometry.Rings.Item(0);
    area.Geometry.StartEdit();
    geo.Points.AddPoint(clickPosition.X,clickPosition.Y,clickPosition.Altitude);
    area.Geometry.EndEdit();
     if (area.Geometry.GeometryType == 6) {       
            area.Geometry = geoBak;
     }
    //显示结果距离及设置结果Label位置
    var resultLabel=YcMap3D.ProjectTree.GetObject(YcMap3D.ProjectTree.FindItem(MeasureToolGlobe.MeasureFolder+"\\AreaResultLabel"));
    resultLabel.Text=(area.Geometry.Area).toFixed(2)+"平方米";
//  YcMap3D.Window.ShowMessageBarText("地表面积:"+(area.Geometry.Area).toFixed(2)+"平方米",0,-1);         
    resultLabel.Position=YcMap3D.Creator.CreatePosition(area.Position.X,area.Position.Y,2,0);     
}

function addGroundAreaPointByRUp(){
     var area=YcMap3D.ProjectTree.GetObject(YcMap3D.ProjectTree.FindItem(MeasureToolGlobe.MeasureFolder+"\\GoundArea"));
     area.Geometry.StartEdit();
     area.Geometry.Rings.Item(0).Points.DeletePoint(area.Geometry.Rings.Item(0).NumPoints-1);
     area.Geometry.EndEdit();  
     YcMap3D.Window.SetInputMode(0);
     var resultLabel=YcMap3D.ProjectTree.GetObject(YcMap3D.ProjectTree.FindItem(MeasureToolGlobe.MeasureFolder+"\\AreaResultLabel"));
     resultLabel.Text=(area.Geometry.Area).toFixed(2)+"平方米";
//   YcMap3D.Window.ShowMessageBarText("地表面积:"+(area.Geometry.Area).toFixed(2)+"平方米",0,-1);
     MeasureToolGlobe.MeasureOperation="EndMeasure";
}