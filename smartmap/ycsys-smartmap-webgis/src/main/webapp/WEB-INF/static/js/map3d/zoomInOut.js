/**
 * Created by ChenLong.
 * Description:实现三维场景拉框缩放功能
 * version: 1.0.0
 */

var ZoomInOutToolGlobe={
    "DrawOperation":"",
    "DrawToolFolder":HiddenGroup+ "\\拉框缩放画图文件夹",  
    "DrawToolFolderID":"",
    "ZoomInOutType":""
}

function preperZoomInOut(){
	//判断是否上一个操作未结束
	if(ZoomInOutToolGlobe.ZoomInOutType!=""){
		zoomInOutRBUpHandler();
	}
}

//拉框放大
function zoomInMapExtent(){
	preperZoomInOut();
	jumpToNorth();
	ZoomInOutToolGlobe.ZoomInOutType = "ZoomIn";
	YcMap3D.AttachEvent("OnLButtonDown", zoomInOutLBDownHandler);
	YcMap3D.AttachEvent("OnLButtonUp", zoomInOutLBUpHandler);
	YcMap3D.AttachEvent("OnRButtonUp", zoomInOutRBUpHandler);
	YcMap3D.AttachEvent("OnFrame", zoomInOutFrameHandler);
	YcMap3D.Window.SetInputMode(1);
	YcMap3D.Window.ShowMessageBarText("请按住左键，拖拉出需要放大的矩形区域。可重复操作，右键结束放大操作。",1,-1);
}

//拉框缩小
function zoomOutMapExtent(){
	preperZoomInOut();
	jumpToNorth();
	ZoomInOutToolGlobe.ZoomInOutType = "ZoomOut";
	YcMap3D.AttachEvent("OnLButtonDown", zoomInOutLBDownHandler);
	YcMap3D.AttachEvent("OnLButtonUp", zoomInOutLBUpHandler);
	YcMap3D.AttachEvent("OnRButtonUp", zoomInOutRBUpHandler);
	YcMap3D.AttachEvent("OnFrame", zoomInOutFrameHandler);
	YcMap3D.Window.SetInputMode(1);
	YcMap3D.Window.ShowMessageBarText("请按住左键，拖拉出需要缩小的中心矩形区域。可重复操作，右键结束缩小操作。",1,-1);
}

//拉框缩放左键事件
function zoomInOutLBUpHandler(flags, x, y){
	var rectangle = findItemByName(ZoomInOutToolGlobe.DrawToolFolder+"\\TempFreeHand");
	if (typeof rectangle == 'object') {
		//结束绘制矩形
		ZoomInOutToolGlobe.DrawOperation = ""
		//过滤微小操作和高度过高、过低
		var cameraHeight = YcMap3D.Navigate.GetPosition(0).Altitude;
		var rectScreenScale = getMinScale(rectangle);
		if(rectScreenScale < 100)
		{
			//缩放操作
			if(ZoomInOutToolGlobe.ZoomInOutType == "ZoomIn"&&cameraHeight>=50){
				ZoomInWithRectangle(rectangle);
			}else if(ZoomInOutToolGlobe.ZoomInOutType == "ZoomOut"&&cameraHeight<=20850000){
				ZoomOutWithRectangle(rectangle);
			}else if(ZoomInOutToolGlobe.ZoomInOutType == "ZoomIn"&&cameraHeight<50){
				YcMap3D.Window.ShowMessageBarText("已经放大到最大程度。右键结束放大操作或点击缩小功能进行缩小操作！",1,-1);
			}else if(ZoomInOutToolGlobe.ZoomInOutType == "ZoomOut"&&cameraHeight>20850000){
				YcMap3D.Window.ShowMessageBarText("已经缩小到最大程度。右键结束缩小操作或点击放大功能进行放大操作！",1,-1);
			}
		}
		//删除绘制图形
		deleteItemsByName(ZoomInOutToolGlobe.DrawToolFolder);
	}
}

//获取屏幕范围与矩形范围的最小比值
function getMinScale(rectangle){
	var rectPositionLT = YcMap3D.Creator.CreatePosition(rectangle.Left,rectangle.Top,0);
	var rectPositionRB = YcMap3D.Creator.CreatePosition(rectangle.Right,rectangle.Bottom,0);
	var rectScreenLT = YcMap3D.Window.PixelFromWorld(rectPositionLT,0);
	var rectScreenRB = YcMap3D.Window.PixelFromWorld(rectPositionRB,0);
	var rectScreenW = Math.abs((rectScreenLT.X-rectScreenRB.X));
	var rectScreenH = Math.abs((rectScreenLT.Y-rectScreenRB.Y));
	return Math.min(YcMap3D.Window.Rect.Width/rectScreenW,YcMap3D.Window.Rect.Height/rectScreenH);
}

//拉框缩放左键事件
function zoomInOutLBDownHandler(flags, x, y){
	var clickPosition = getMousePosition(-1);
	//绘制矩形
	clickPosition.AltitudeType = 2;
	ZoomInOutToolGlobe.DrawToolFolderID=createFolder(ZoomInOutToolGlobe.DrawToolFolder);
	var rectangle = YcMap3D.Creator.CreateRectangle(clickPosition,0.000001,0.000001, "#FF0000", "#384048",ZoomInOutToolGlobe.DrawToolFolderID, 'TempFreeHand');
	rectangle.Terrain.GroundObject = false;
	rectangle.FillStyle.Color.SetAlpha(0.5);
	//开始侦听每一帧事件
	ZoomInOutToolGlobe.DrawOperation = "CHANGERECT"
}

//拉框缩放每一帧事件
function zoomInOutFrameHandler(){
	if (ZoomInOutToolGlobe.DrawOperation == "CHANGERECT") {
		var mousePosition = getMousePosition(-1);
        var rectangle = findItemByName(ZoomInOutToolGlobe.DrawToolFolder+"\\TempFreeHand");
        if (typeof rectangle == 'object') {
			rectangle.Right = mousePosition.X;
			rectangle.Bottom = mousePosition.Y;
        }
    }
}

//拉框缩放右键事件
function zoomInOutRBUpHandler(){
	YcMap3D.DetachEvent("OnLButtonDown", zoomInOutLBDownHandler);
	YcMap3D.DetachEvent("OnLButtonUp", zoomInOutLBUpHandler);
	YcMap3D.DetachEvent("OnRButtonUp", zoomInOutRBUpHandler);
	YcMap3D.DetachEvent("OnFrame", zoomInOutFrameHandler);
	ZoomInOutToolGlobe.ZoomInOutType = "";
	ZoomInOutToolGlobe.DrawOperation = "";
	ZoomInOutToolGlobe.DrawToolFolderID = "";
	YcMap3D.Window.SetInputMode(0);
	YcMap3D.Window.HideMessageBarText();
}

//拉框放大算法
function ZoomInWithRectangle(Rectabgle){
	//判断是球面左边还是平面坐标
	var type = "meter";
	var isPlaner = YcMap3D.CoordServices.SourceCoordinateSystem.IsPlanar();
	if(!isPlaner)
		type = "globe";
	//获取左上、右下坐标
	var extent={"xmin":Math.min(Rectabgle.Left,Rectabgle.Right),"xmax":Math.max(Rectabgle.Left,Rectabgle.Right),"ymin":Math.min(Rectabgle.Bottom,Rectabgle.Top),"ymax":Math.max(Rectabgle.Bottom,Rectabgle.Top)};
	//设置到地图范围
	set3DMapExtent(extent, type)
}

//拉框缩小算法
function ZoomOutWithRectangle(Rectabgle){
	//计算缩小后的地图中心，即矩形的中心
	var centerx = (Rectabgle.Left + Rectabgle.Right) * 0.5;
	var centery = (Rectabgle.Top + Rectabgle.Bottom) * 0.5;
	var centerPosition = YcMap3D.Creator.CreatePosition(centerx, centery, 0, 0, 0, -90, 0, 0);
	//计算缩小后相机位置的高度
	var heightScale = getZoomOutHeight(Rectabgle);
	if(heightScale>20850000)
		centerPosition.Altitude = 20850001;
	else if(heightScale<YcMap3D.Navigate.GetPosition(0).Altitude)
		centerPosition.Altitude = YcMap3D.Navigate.GetPosition(0).Altitude + 10000;
	else
		centerPosition.Altitude = heightScale
	YcMap3D.Navigate.FlyTo(centerPosition,14);
}

//计算拉框缩小高度值
function getZoomOutHeight(Rectabgle){
	var screenLT = YcMap3D.Window.PixelToWorld(YcMap3D.Window.Rect.Left,YcMap3D.Window.Rect.Top,0);
	var rbx = YcMap3D.Window.Rect.Width - YcMap3D.Window.Rect.Left;
	var rby = YcMap3D.Window.Rect.Height - YcMap3D.Window.Rect.Top;
	var screenRB = YcMap3D.Window.PixelToWorld(rbx,rby,0);
	var rectW = (Math.max(Rectabgle.Top,Rectabgle.Bottom) - Math.min(Rectabgle.Top,Rectabgle.Bottom)) * 0.5;
	var screenW = Math.abs((screenLT.Position.X - screenRB.Position.X)) * 0.5;
	var rectH = (Math.max(Rectabgle.Left,Rectabgle.Right) - Math.min(Rectabgle.Left,Rectabgle.Right)) * 0.5;
	var screenH = Math.abs((screenLT.Position.Y - screenRB.Position.Y)) * 0.5;
	var currentH = YcMap3D.Navigate.GetPosition(3).Altitude;
    var distanceY = screenH*currentH/rectH;
    var distanceX = screenW*currentH/rectW;
	return Math.max(distanceX,distanceY);
}