<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<script type="text/javascript">

//声明地图范围变量
var mapExtent2d = null;

/**
*
*初始化页面，加载工程，监听工程加载完毕事件
*
*/
function to3dMap(mapExtent) {
	mapExtent2d = mapExtent;
	//检查是否使用IE浏览器
	if(!checkBrowser())
		return
		
	//检查是否安装Skyline插件	
	if(!checkYcMap3DInstall())
		return
	
	//检查是否初始化项目
	//if(YcMap3D==null||YcMap3D==undefined||YcMap3D.Project.Name==""){
	if(map3DInit==false){
		//初始化打开工程并定位
	    try {
	        //fly文件路径
	        var flyPath = configration.FlyPath;
	        //屏蔽右键弹出菜单
	        YcMap3D.AttachEvent("OnRButtonDown", function () { YcMap3D.Navigate.Stop(); return true; });
	        //打开工程
	        YcMap3D.Project.Open(flyPath,true);
	        //关闭自带漫游控件
//	        YcMap3D.Window.DisablePresentationControl = true;
	        //添加onloadFinished事件
	        YcMap3D.AttachEvent("OnLoadFinished", OnProjectLoadFinished);
	        //标识三维已经初始化状态
	        map3DInit = true;
	    }
	    catch (e) {
	        alert("Error: " + e.description);
	    }
	}else{
		setExtentTo2dMap();
	}
	//标识目前显示三维状态
    mapOpt = 3;
}

/**
*
*onloadFinished事件方法
*
*/
function OnProjectLoadFinished() {
    //隐藏自有导航控件
    SwitchNavigateBar();
    //与二维地图关联地图显示范围
    window.setTimeout("setExtentTo2dMap()",500);
  	//初始化试图记录
    beginRecordView();
  	//创建临时文件夹、文件夹
  	creatCacheFolder();
	//判断加载二维中已加载图层
    initMapLayer();
    //初始化相关操作标识
    initOperationFlag();
    //关闭OnLoadFinish事件
    YcMap3D.DetachEvent("OnLoadFinished", OnProjectLoadFinished);
    //开启侦听全局更改事件OnInputModeChanged
    YcMap3D.AttachEvent("OnInputModeChanged", OnInputModeChangedListening);
}

function creatCacheFolder(){
	if(configration.WMSServiceFolder!="")
		createFolder(configration.WMSServiceFolder);
	if(configration.WFSServiceFolder!="")
		createFolder(configration.WFSServiceFolder);
//	if(configration.POIFolder != "")
//        createFolder(configration.POIFolder);
	if(configration.QueryIcoFolder!="")
        createFolder(configration.QueryIcoFolder);
	if(configration.QueryDrawFolder!="")
        createFolder(configration.QueryDrawFolder);
}

/**
*
*与二维地图关联地图显示范围
*
*/
function setExtentTo2dMap(){
	if(mapExtent2d!=null){
		setZoomInLevel(mapExtent2d);
	}
}

function initOperationFlag(){
    var storage=window.localStorage;
	storage.setItem("RoamPathEdit","false");
    storage.setItem("RoamPathPointEdit","false");
    storage.setItem("BookMarkEdit","false");
}

//自动更新(废弃)
function beginMonitorRefresh() {
    window.setInterval(function () {
        if(mapOpt==3){
            var storage=window.localStorage;
            if(storage.getItem("RoamPathEdit")=="true"){
                storage.setItem("RoamPathEdit","false");
                $('#tableFlyPathForEdit').bootstrapTable('refresh', {
                    url: "${path}/flightRoam/roamPathList"
                });

            }
            if(storage.getItem("RoamPathPointEdit")=="true"){
                storage.setItem("RoamPathPointEdit","false");
                getPathPointsFromPathName(selectRoamPath);
            }
            if(storage.getItem("BookMarkEdit")=="true"){
                $('#tableSqdw').bootstrapTable('refresh', {
                    url: "${path}/locateService/toList.do"
                });
                storage.setItem("BookMarkEdit","false");
            }
        }
    },500)
}

//自动更新
function OnInputModeChangedListening(NewMode) {
    if(mapOpt==3&&NewMode==1){
        var storage=window.localStorage;
        if(storage.getItem("RoamPathEdit")=="true"){
            storage.setItem("RoamPathEdit","false");
            YcMap3D.Window.SetInputMode(0);
            $('#tableFlyPathForEdit').bootstrapTable('refresh', {
                url: "${path}/flightRoam/roamPathList"
            });
        }
        if(storage.getItem("RoamPathPointEdit")=="true"){
            storage.setItem("RoamPathPointEdit","false");
            YcMap3D.Window.SetInputMode(0);
            getPathPointsFromPathName(selectRoamPath);
        }
        if(storage.getItem("BookMarkEdit")=="true"){
            storage.setItem("BookMarkEdit","false");
            YcMap3D.Window.SetInputMode(0);
            $('#tableSqdw').bootstrapTable('refresh', {
                url: "${path}/locateService/toList.do"
            });
        }
    }
}

</script>

</head>
<body>
	<script type="text/javascript" charset="utf-8" src="${res}/js/map3d/YcMap3D.js"></script>
	<script type="text/javascript" charset="utf-8" src="${res}/js/map3d/viewRecorder.js"></script>
	<script type="text/javascript" charset="utf-8" src="${res}/js/map3d/zoomInOut.js"></script>
	<script type="text/javascript" charset="utf-8" src="${res}/js/map3d/measure.js"></script>
	<script type="text/javascript" charset="utf-8" src="${res}/js/map3d/draw.js"></script>
	<script type="text/javascript" charset="utf-8" src="${res}/js/map3d/scan.js"></script>
	<script type="text/javascript" charset="utf-8" src="${res}/js/map3d/analysis.js"></script>
	<script type="text/javascript" charset="utf-8" src="${res}/js/map3d/config.js"></script>
	<script type="text/javascript" charset="utf-8" src="${res}/js/map3d/fly.js"></script>
	<script type="text/javascript" charset="utf-8" src="${res}/js/map3d/map3dQuery.js"></script>
	<script type="text/javascript" charset="utf-8" src="${res}/js/map3d/map3dService.js"></script>
</body>
</html>