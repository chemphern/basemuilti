/**
 * Created by ChenLong.
 * Description:实现三维场景操作视角记录，以此在三维中实现上一视图、下一视图
 * version: 1.0.0
 */

//需要记录的最近的多少个视图
var viewNum = 6;

//当前视图在视图数组中的索引
var currentViewStep;

//定义保存记录数组对象
var views = new Array();

//初始化视图记录，并记录当前视图
function beginRecordView(){
	//获取初始视图或当前视图并记录
	var initPositionID = YcMap3D.ProjectTree.FindItem("InitLocation");
	if(initPositionID!=""){
		views.push(YcMap3D.ProjectTree.GetObject(initPositionID).Position);
	}else{
		views.push(YcMap3D.Navigate.GetPosition(3));
	}
	//开始鼠标左键、中键弹起事件和滑轮滚动事件
	YcMap3D.AttachEvent("OnLButtonUp", viewMovedHandler);
	YcMap3D.AttachEvent("OnMButtonUp", viewMovedHandler);
	YcMap3D.AttachEvent("OnMouseWheel", viewMovedHandler);
}

//监听鼠标左键、中键弹起事件和滑轮滚动事件并记录改变后视图
function viewMovedHandler(){
	var currentPosition = YcMap3D.Navigate.GetPosition(3);
	if(views.length >= viewNum){
		views.shift();
	}
	views.push(currentPosition);
	currentViewStep = views.length-1;
}

//获取当前视图的前一个视图
function getPreviousView(){
	if(currentViewStep!=0){
		currentViewStep = currentViewStep - 1;
		var newPosition = views[currentViewStep];
		YcMap3D.Navigate.FlyTo(newPosition,14);
	}
}

//获取当前视图的后一个视图
function getNextView(){
	if(currentViewStep != viewNum-1){
		currentViewStep = currentViewStep + 1;
		var newPosition = views[currentViewStep];
		YcMap3D.Navigate.FlyTo(newPosition,14);
	}
}