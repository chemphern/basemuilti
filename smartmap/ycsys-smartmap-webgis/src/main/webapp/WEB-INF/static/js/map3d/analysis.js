/**
 * Created by ChenLong.
 * Description:实现三维场景中常用分析
 * version: 1.0.0
 */

//----------------------------------------------分析模块----------------------------------------------//

var AnalyseTool={
    activate:function(type){
        return analyseStart(type);
    },
    AnalyseType:{'LINEOFSIGHT':'LINEOFSIGHT','SHADOW':'SHADOW','FLOOD':'FLOOD','VIEWSHED3D':'VIEWSHED3D'},
    deactivate:function(){
        shadowEndHandler();
        lineOfSightEndHandler();
        floodEndHandler();
    }
}

function analyseStart(type){   
    if(type==AnalyseTool.AnalyseType.LINEOFSIGHT){//通视分析
        lineOfSightAnalyse();
    }
    else if(type==AnalyseTool.AnalyseType.SHADOW){
        shadowAnalyse();
    }
    else if(type==AnalyseTool.AnalyseType.FLOOD){
        return floodAnalyse();
    }
    else if(type==AnalyseTool.AnalyseType.VIEWSHED3D){
    	Viewshed3DAnalyse();
    }
}

//----------------------------------------------通视分析----------------------------------------------//
var lineOfSightGlobe={
    "FolderID":"",
    "Folder":HiddenGroup+"\\通视分析"
}

function lineOfSightAnalyse(){
    lineOfSightGlobe.FolderID=createFolder(lineOfSightGlobe.Folder);
    YcMap3D.Window.ShowMessageBarText("请选择要进行通视分析的观察点和目标位置",1,-1);
    DrawTool.activate(DrawTool.DrawType.LINEOFSIGHT);
    DrawTool.drawEndHandler=function(polyline){              
        var viewerPoint = polyline.Geometry.Points.Item(1);
        var viewerZ=getLineOfSightZ(viewerPoint.X,viewerPoint.Y,viewerPoint.Z);
        var viewerPosition = YcMap3D.Creator.CreatePosition(viewerPoint.X, viewerPoint.Y, viewerZ, 3, 0, 0, 0, 0);
        var targetPositions=[];
        for(var i=0;i<polyline.Geometry.Points.Count;i=i+2){
               var tempPoint=polyline.Geometry.Points.Item(i);
               var tempZ=getLineOfSightZ(tempPoint.X,tempPoint.Y,tempPoint.Z);
               var tempPosition=YcMap3D.Creator.CreatePosition(tempPoint.X,tempPoint.Y,tempZ,3);
               targetPositions.push(tempPosition); 
        }
        YcMap3D.AttachEvent("OnRButtonUp", lineOfSightEndHandler);
        YcMap3D.Analysis.CreateLineOfSight(viewerPosition,1,targetPositions,lineOfSightGlobe.FolderID,"LineOfSight"); 
        YcMap3D.Window.ShowMessageBarText("通视分析完成,绿色线表示观察点和目标点可视,红色线表示观察点和目标点不可视",1,-1)
        YcMap3D.Window.SetInputMode(1);
        YcMap3D.ProjectTree.DeleteItem(polyline.ID);       
	}
}

function lineOfSightEndHandler(){
    YcMap3D.Window.HideMessageBarText();
    YcMap3D.Window.SetInputMode(0);
    deleteItemsByName(lineOfSightGlobe.Folder+"\\LineOfSight");
    YcMap3D.DetachEvent("OnRButtonUp", lineOfSightEndHandler);
}

function getLineOfSightZ(x, y, z) {
    var terrainHeight = YcMap3D.Terrain.GetGroundHeightInfo(x, y, 2, false).Position.Altitude;
    var xyToHeight = YcMap3D.Terrain.GetGroundHeightInfo(x, y, 2, true).Position.Altitude;
    return parseFloat(z) + parseFloat(terrainHeight) - parseFloat(xyToHeight);
}

//----------------------------------------------视域分析----------------------------------------------//
var viewshed3DGlobe={
    "FolderID":"",
    "Folder":HiddenGroup+"\\视域分析"
}

function Viewshed3DAnalyse(){
	var viewshed3D = null;
	viewshed3DGlobe.FolderID=createFolder(viewshed3DGlobe.Folder);
 	DrawTool.activate(DrawTool.DrawType.TERRASEGMENT);
    DrawTool.drawIngHandler=function(LineGeometry){
		if(typeof LineGeometry == 'object' && LineGeometry.Geometry.GeometryType=='1'){
			var pointStart = LineGeometry.Geometry.StartPoint;
			var positionStart = YcMap3D.Creator.CreatePosition(pointStart.X,pointStart.Y,pointStart.Z);
			var pointEnd = LineGeometry.Geometry.EndPoint;
			var positionEnd = YcMap3D.Creator.CreatePosition(pointEnd.X,pointEnd.Y,pointEnd.Z);
			var viewPosition = positionStart.AimTo(positionEnd);
			var viewDistance = positionStart.DistanceTo(positionEnd);
			viewPosition.Altitude = 0;
			if(viewshed3D==null)
				viewshed3D = YcMap3D.Analysis.Create3DViewshed(viewPosition,120,90,viewDistance,viewshed3DGlobe.FolderID,'Viewshed3D');
			else{
				viewshed3D.Position = viewPosition;
				viewshed3D.Distance = viewDistance; 
			}
		}
	}
    DrawTool.drawEndHandler=function(LineGeometry){
    	YcMap3D.AttachEvent("OnRButtonUp", viewshed3DEndHandler);
    }
}

function viewshed3DEndHandler(){
    YcMap3D.Window.SetInputMode(0);
    deleteItemsByName(viewshed3DGlobe.Folder+"\\Viewshed3D");
    deleteItemsByName(DrawToolGlobe.DrawToolFolder);
    YcMap3D.DetachEvent("OnRButtonUp", viewshed3DEndHandler);
}

//----------------------------------------------阴影分析----------------------------------------------//
var ShadowGlobe={
    "Activate":false
};

function shadowAnalyse(){
    if(!ShadowGlobe.Activate){      
	    YcMap3D.AttachEvent("OnRButtonUp", shadowEndHandler);
	    YcMap3D.Window.ShowMessageBarText("请修改日照时间",1,-1);
	    SwitchTimeSlider(); //打开时间条         
	    SwitchShadow();  //打开阴影 
	    ShadowGlobe.Activate=true;   
    }else{
        shadowEndHandler();
    }
}

function shadowEndHandler(){
    if(ShadowGlobe.Activate){
	    YcMap3D.DetachEvent("OnRButtonUp", shadowEndHandler);
	    YcMap3D.Window.HideMessageBarText();
	    ShadowGlobe.Activate=false;
	    SwitchTimeSlider(); //关闭时间条
	    var date=new Date();
	    date.setHours(11,0,0,0);
	    YcMap3D.DateTime.FixedLocalTime =date; //还原时间 
	    SwitchShadow();  //关闭阴影
    }
}

 //----------------------------------------------水淹分析----------------------------------------------//
 var floodAnalyseGlobe = {
	"Folder":HiddenGroup+"\\水淹分析",
	"FolderID":""
}

function floodAnalyse() {
    floodEndHandler();
    floodAnalyseGlobe.FolderID = createFolder(floodAnalyseGlobe.Folder);
    var centerPosition = YcMap3D.Window.CenterPixelToWorld(0).Position;
    var floodModel = YcMap3D.Creator.CreateCylinder(centerPosition, 2000, 0.1, "#FFFFFF", "#47FCEA", 99, floodAnalyseGlobe.FolderID, "floodModel");
    floodModel.LineStyle.Color.SetAlpha(0);
    floodModel.FillStyle.Color.SetAlpha(0.5);
    floodModel.FillStyle.Texture.FileName = getProjectPath() + "img/YcMap3D/water.jpg";
    YcMap3D.AttachEvent("OnRButtonUp", floodEndHandler);
    return floodModel;
}

function floodEndHandler() {
	deleteItemsByName(floodAnalyseGlobe.Folder + "\\floodModel");
}