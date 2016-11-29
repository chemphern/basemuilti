<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<script type="text/javascript">

/**
*
*初始化页面，加载工程，监听工程加载完毕事件
*
*/
function to3dMap() {
	//检查是否使用IE浏览器
	if(!checkBrowser())
		return
		
	//检查是否安装Skyline插件	
	if(!checkYcMap3DInstall())
		return
	
	//检查是否初始化项目
	if(YcMap3D==null||YcMap3D==undefined||YcMap3D.Project.Name==""){
		//初始化打开工程并定位
	    try {
	        //fly文件路径
	        var flyPath = configration.FlyPath;
	        //屏蔽右键弹出菜单
	        YcMap3D.AttachEvent("OnRButtonDown", function () { YcMap3D.Navigate.Stop(); return true; });
	        //打开工程
	        YcMap3D.Project.Open(flyPath,true);
	        //添加onloadFinished事件
	        YcMap3D.AttachEvent("OnLoadFinished", OnProjectLoadFinished);
	    }
	    catch (e) {
	        alert("Error: " + e.description);
	    }
	}else{
		var initPositionID = YcMap3D.ProjectTree.FindItem("InitLocation");
		if(initPositionID!=""){
			YcMap3D.Navigate.FlyTo(YcMap3D.ProjectTree.GetObject(initPositionID),14);
		}
	}
	//表示目前显示三维状态
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
}

/**
*
*与二维地图关联地图显示范围
*
*/
function setExtentTo2dMap(){
	var initPositionID = YcMap3D.ProjectTree.FindItem("InitLocation");
	if(initPositionID!=""){
		YcMap3D.Navigate.FlyTo(YcMap3D.ProjectTree.GetObject(initPositionID),0);
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
</body>
</html>