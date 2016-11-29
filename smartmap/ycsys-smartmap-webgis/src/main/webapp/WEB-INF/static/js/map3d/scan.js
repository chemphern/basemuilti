/**
 * Created by ChenLong.
 * Description:实现三维场景各种浏览模式切换
 * version: 1.0.0
 */

//----------------------------------------------浏览模块----------------------------------------------//

//平移浏览
function PanScan(){
	YcMap3D.Command.Execute(1049,0);
}

//滑动浏览
function SlideScan(){
	YcMap3D.Command.Execute(1050,0);
}

//倾斜浏览
function TiltScan(){
	YcMap3D.Command.Execute(1051,0);
}

//环绕浏览
function RoundScan(){
	YcMap3D.Command.Execute(1057,0);
}

//地下模式
function Underground(){
	if(YcMap3D.Navigate.UndergroundMode==true)
		YcMap3D.Navigate.UndergroundMode=false;
	else
		YcMap3D.Navigate.UndergroundMode=true;
}

//正北方向
function flyToNorth(){
	YcMap3D.Command.Execute(1056,0);
}

//正北方向
function jumpToNorth(){
	var currentPosition = YcMap3D.Navigate.GetPosition(3);
	currentPosition.Yaw = 0;
	YcMap3D.Navigate.FlyTo(currentPosition,14);
}