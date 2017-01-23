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
    YcMap3D.AttachEvent("OnLButtonUp", onLeftButtonUpHandler);
	YcMap3D.AttachEvent("OnMButtonUp", viewMovedHandler);
	YcMap3D.AttachEvent("OnMouseWheel", viewMovedHandler);
}

//处理左键点击事件
function onLeftButtonUpHandler(Flag,X,Y) {
	//具有工具栏控件，使场景左上角控件显示
    $(".city-popup-main").fadeOut();

    //监听鼠标左键事件并记录改变后视图
    viewMovedHandler();

	//场景点击选择对象
    var objid = YcMap3D.Window.PixelToWorld(X, Y, -1).ObjectID;
    if (objid) {
    	var objType = YcMap3D.ProjectTree.GetObject(objid).ObjectType;
    	var ifDefaultFeature = false;
        //判断是否点击到Feature图标，如果有则替换成高亮图标
    	if(objType==24&&YcMap3D.ProjectTree.GetNextItem(objid,15)!=""){
            var parentName = YcMap3D.ProjectTree.GetItemName(YcMap3D.ProjectTree.GetNextItem(objid,15));
            if (objType == 24&&parentName==configration.QueryIcoFolder) {
                navigateToSceneFeature("",objid);
                ifDefaultFeature = true;
            }
		}
        //判断是否点到视频、全景Feature
		if(objType==33&&!ifDefaultFeature){
			var feature = YcMap3D.ProjectTree.GetObject(objid);
			if(YcMap3D.ProjectTree.GetItemName(feature.LayerID)=="广州市视频点信息"){
                videoView3D(feature);
			}else if(YcMap3D.ProjectTree.GetItemName(feature.LayerID)=="广东省全景视频点"){
                panoramaView3D(feature);
			}
		}
    }

    //标识当前活跃地图为三维
    activetedMap = 3;

    //三维模式下，设置快速定位区域
	if(mapOpt==3)
    	setQuickLocationMap3d();
}

//中键弹起事件和滑轮滚动事件并记录改变后视图
function viewMovedHandler(){
	var currentPosition = YcMap3D.Navigate.GetPosition(3);
	if(views.length >= viewNum){
		views.shift();
	}
	views.push(currentPosition);
	currentViewStep = views.length-1;

	//二三维同步视图下，设置二维地图范围
	if(mapOpt==0&&activetedMap==3){
        setMapExtent();
	}
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