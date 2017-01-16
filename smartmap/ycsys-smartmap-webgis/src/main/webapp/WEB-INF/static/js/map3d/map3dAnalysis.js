/**
 * Created by ChenLong.
 * Description:实现三维场景中常用分析
 * version: 1.0.0
 */

//----------------------------------------------分析模块----------------------------------------------//

var AnalyseTool={
    activate:function(type){
        analyseStart(type);
    },
    AnalyseType:{'LINEOFSIGHT':'LINEOFSIGHT','SHADOW':'SHADOW','FLOOD':'FLOOD','VIEWSHED3D':'VIEWSHED3D'},
    deactivate:function(){
        if(AnalysisToolGloble.AnalysisType=="SHADOW")
            shadowEndHandler();
        else if(AnalysisToolGloble.AnalysisType=="LINEOFSIGHT")
            lineOfSightEndHandler();
        else if(AnalysisToolGloble.AnalysisType=="FLOOD")
            floodEndHandler();
        else if(AnalysisToolGloble.AnalysisType=="VIEWSHED3D")
            viewshed3DEndHandler();
    },
    clear:function () {
        deleteItemsByName(AnalysisToolGloble.Folder);
        deleteItemsByName(DrawToolGlobe.DrawToolFolder);
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
        // floodAnalyse();
        floodAnalysis3d();
    }
    else if(type==AnalyseTool.AnalyseType.VIEWSHED3D){
    	Viewshed3DAnalyse();
    }
}

var AnalysisToolGloble = {
    "Folder":HiddenGroup+"\\三维分析",
    "WaterImage":getProjectPath()+"static/dist/img/map/water.jpg",
    "FolderID":"",
    "AnalysisType":"",
    "Activate":false
};

//----------------------------------------------通视分析----------------------------------------------//

function lineOfSightAnalyse(){
    AnalyseTool.deactivate();
    AnalysisToolGloble.AnalysisType = "LINEOFSIGHT";
    AnalysisToolGloble.FolderID=createFolder(AnalysisToolGloble.Folder);
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
        // YcMap3D.AttachEvent("OnRButtonUp", lineOfSightEndHandler);
        YcMap3D.Analysis.CreateLineOfSight(viewerPosition,1,targetPositions,AnalysisToolGloble.FolderID,"LineOfSight");
        YcMap3D.Window.ShowMessageBarText("通视分析完成,绿色线表示观察点和目标点可视,红色线表示观察点和目标点不可视",1,-1);
        YcMap3D.Window.SetInputMode(1);
        YcMap3D.ProjectTree.DeleteItem(polyline.ID);       
	}
}

function lineOfSightEndHandler(){
    YcMap3D.Window.HideMessageBarText();
    YcMap3D.Window.SetInputMode(0);
    deleteItemsByName(AnalysisToolGloble.Folder+"\\LineOfSight");
    deleteItemsByName(DrawToolGlobe.DrawToolFolder);
    // YcMap3D.DetachEvent("OnRButtonUp", lineOfSightEndHandler);
    AnalysisToolGloble.AnalysisType = "";
}

function getLineOfSightZ(x, y, z) {
    var terrainHeight = YcMap3D.Terrain.GetGroundHeightInfo(x, y, 2, false).Position.Altitude;
    var xyToHeight = YcMap3D.Terrain.GetGroundHeightInfo(x, y, 2, true).Position.Altitude;
    return parseFloat(z) + parseFloat(terrainHeight) - parseFloat(xyToHeight);
}

//----------------------------------------------视域分析----------------------------------------------//

function Viewshed3DAnalyse(){
    AnalyseTool.deactivate();
	var viewshed3D = null;
    AnalysisToolGloble.AnalysisType = "VIEWSHED3D";
    AnalysisToolGloble.FolderID=createFolder(AnalysisToolGloble.Folder);
    YcMap3D.Window.ShowMessageBarText("请左键点击要进行视域分析的观察点并移动鼠标，调整观察目标位置",1,-1);
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
				viewshed3D = YcMap3D.Analysis.Create3DViewshed(viewPosition,120,90,viewDistance,AnalysisToolGloble.FolderID,'Viewshed3D');
			else{
				viewshed3D.Position = viewPosition;
				viewshed3D.Distance = viewDistance; 
			}
		}
	}
    DrawTool.drawEndHandler=function(LineGeometry){
        YcMap3D.Window.ShowMessageBarText("视域分析完成,绿色区域表示观察点和目标点可视,红色区域表示观察点和目标点不可视",1,-1);
    	// YcMap3D.AttachEvent("OnRButtonUp", viewshed3DEndHandler);
    }
}

function viewshed3DEndHandler(){
    AnalysisToolGloble.AnalysisType = "";
    YcMap3D.Window.SetInputMode(0);
    YcMap3D.Window.HideMessageBarText();
    deleteItemsByName(AnalysisToolGloble.Folder+"\\Viewshed3D");
    deleteItemsByName(DrawToolGlobe.DrawToolFolder);
    // YcMap3D.DetachEvent("OnRButtonUp", viewshed3DEndHandler);
}

//----------------------------------------------阴影分析----------------------------------------------//

function shadowAnalyse(){
    AnalyseTool.deactivate();
    AnalysisToolGloble.AnalysisType = "SHADOW";
    // YcMap3D.AttachEvent("OnRButtonUp", shadowEndHandler);
    YcMap3D.Window.ShowMessageBarText("请通过左上角时间控件，修改日照时间",1,-1);
    SwitchTimeSlider(); //打开时间条
    SwitchShadow();  //打开阴影
}

function shadowEndHandler(){
    // YcMap3D.DetachEvent("OnRButtonUp", shadowEndHandler);
    YcMap3D.Window.HideMessageBarText();
    AnalysisToolGloble.Activate=false;
    AnalysisToolGloble.AnalysisType = "";
    SwitchTimeSlider(); //关闭时间条
    var date=new Date();
    date.setHours(11,0,0,0);
    YcMap3D.DateTime.FixedLocalTime =date; //还原时间
    SwitchShadow();  //关闭阴影
}

 //----------------------------------------------水淹分析----------------------------------------------//

function floodAnalyse() {
    AnalyseTool.deactivate();
    AnalysisToolGloble.AnalysisType = "FLOOD";
    AnalysisToolGloble.FolderID = createFolder(AnalysisToolGloble.Folder);
    var centerPosition = YcMap3D.Window.CenterPixelToWorld(0).Position;
    var floodModel = YcMap3D.Creator.CreateCylinder(centerPosition, 2000, 0.1, "#FFFFFF", "#47FCEA", 99, AnalysisToolGloble.FolderID, "floodModel");
    floodModel.LineStyle.Color.SetAlpha(0);
    floodModel.FillStyle.Color.SetAlpha(0.5);
    floodModel.FillStyle.Texture.FileName = AnalysisToolGloble.WaterImage;
    // YcMap3D.AttachEvent("OnRButtonUp", floodEndHandler);
    return floodModel;
}

function floodAnalysis3d() {
    AnalyseTool.deactivate();
    AnalysisToolGloble.AnalysisType = "FLOOD";
    YcMap3D.Window.ShowMessageBarText("鼠标左键点击并拖拽分析区域，再次点击左键结束拖拽",1,-1);
    AnalysisToolGloble.FolderID = createFolder(AnalysisToolGloble.Folder);
    DrawTool.activate(DrawTool.DrawType.TERRACIRCLE);
    DrawTool.drawEndHandler = function (CircleGeometry) {
        if(CircleGeometry.Radius>=30){
            // var startTime = YcMap3D.DateTime.Current;
            var startTime = new Date("January 12,2017 09:00:00");
            // YcMap3D.DateTime.TimeRangeStart = startTime;
            // var myDate=new Date();
            // var endTime = myDate.setDate(myDate.getDate()+10);
            var endTime =  new Date("January 12,2017 19:00:00");
            // YcMap3D.DateTime.TimeRangeEnd = endTime;
            // YcMap3D.DateTime.SetMode(16);
            // SwitchTimeSlider(); //打开时间条
            YcMap3D.Analysis.CreateFloodContinuousWaterRise(CircleGeometry.Position.X,CircleGeometry.Position.Y,CircleGeometry.Radius,0.5,5,1,startTime,endTime,AnalysisToolGloble.FolderID,"floodModel");
            deleteItemsByName(DrawToolGlobe.DrawToolFolder);
            YcMap3D.Window.ShowMessageBarText("分析完成，蓝色区域为涨水区域，鼠标移入可显示涨水高度",1,-1);
        }else{
            alert("分析半径过小，请重新绘制！");
        }
    };
}

function floodEndHandler() {
	deleteItemsByName(AnalysisToolGloble.Folder + "\\floodModel");
    YcMap3D.Window.HideMessageBarText();
    AnalysisToolGloble.AnalysisType = "";
}