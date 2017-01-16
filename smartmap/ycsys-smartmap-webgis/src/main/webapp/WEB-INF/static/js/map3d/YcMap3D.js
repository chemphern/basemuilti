//-------------------------------------------------全局变量-------------------------------------------------//
var HiddenGroup = "~HiddenGroup";

//----------------------------------------------浏览器类型判断----------------------------------------------//
var BrowserUnit = function () {
    this.isIE = function () {
        if (!!window.ActiveXObject || "ActiveXObject" in window) {
            return true;
        } else {
            return false;
        }
    }
    this.getInfo = function () {
        var BrowserInfo = "", BrowserType = "", BrowserVersion = "";
        var ua = navigator.userAgent.toLocaleLowerCase();
        if (this.isIE()) {
            BrowserInfo = "IE";
            if (/x64/.test(ua)) BrowserType = "　64位";
            else BrowserType = "　32位";
            BrowserVersion = ua.match(/msie([^\;]+)/);
            if (BrowserVersion) BrowserVersion = BrowserVersion[1];
        } else {
            if (/firefox/.test(ua)) BrowserInfo = "火狐(Firefox)";
            if (/opr/.test(ua)) BrowserInfo = "欧朋(Opera)";
            if (/edge/.test(ua)) BrowserInfo = "微软(Edge)";
            if (/chrome/.test(ua) && !/opr/.test(ua) && !/edge/.test(ua)) BrowserInfo = "谷歌(Chrome)";
            if (/safari/.test(ua) && !/chrome/.test(ua) && !/opr/.test(ua) && !/edge/.test(ua)) BrowserInfo = "苹果(Safari)";
        }
        return BrowserInfo + BrowserVersion + BrowserType;
    }
}

function checkBrowser() {
    if ((new BrowserUnit().getInfo()).match(/IE/)) {
        var info = new BrowserUnit().getInfo();
        if (info.match(/64/)) {
            alert("当前使用的" + info.replace(/\s+64位/, "") + "(64位),请使用IE(32位)来运行系统");
            return false;
        }
        else {
            return true;
        }
    } else {
        alert("当前使用的是" + (new BrowserUnit().getInfo()) + "浏览器,三维插件必须在IE浏览器下运行");
        return false;
    }
}

function checkYcMap3DInstall() {
    if (("Version" in YcMap3D) && YcMap3D.Version ) {
        var type = YcMap3D.Version.Type;
        if (type == 2 || type == -1) { 
            alert("当前的插件为Viewer版本,部分功能不能正常使用,请加载授权文件");
            return true;
        }else
        	return true;
    } else {
       if (confirm("是否下载skyline插件?")) {
            location.href = Map3DPlugDownoadPath;
            alert("请安装后再尝试刷新页面；如需使用部分高级特性，请先加载授权文件"); 
            return false;
        }else
        	return true;
    }        
}

//----------------------------------------------共有常用功能----------------------------------------------//

//切换阴影
function SwitchShadow() {
    YcMap3D.Command.Execute(2118, 0);
}

//切换导航条
function SwitchNavigateBar() {
    YcMap3D.Command.Execute(1065, 0);
}

//切换状态栏
function SwitchStatusBar() {
    YcMap3D.Command.Execute(1065, 2);
}

//切换比例尺
function SwitchScaleBar() {
    YcMap3D.Command.Execute(1065, 3);
}

//切换时间滑条
function SwitchTimeSlider() {
    YcMap3D.Command.Execute(1065, 4);
}

//切换地下模式
function SwitchGroundMode() {
    YcMap3D.Command.Execute(1027, 0);
}

//切换碰撞检测
function SwitchCollision() {
    YcMap3D.Command.Execute(1140, 0);
}

//显示隐藏目录树
function SwitchProjectTree() {
    YcMap3D.Command.Execute(1162, 0);
}

//打印地图
function print3DMap(){
	YcMap3D.Window.GetSnapShot(false,0,0,"JPeg75",1);
}

//删除所有包含该名称的对象
function deleteItemsByName(itemname) {
    while (YcMap3D.ProjectTree.FindItem(itemname) != "") {   //skyline6.5
        YcMap3D.ProjectTree.DeleteItem(YcMap3D.ProjectTree.FindItem(itemname));
    }
}

//取得当前工程路径
function getRootPath() {
    var url = window.location.href;   
    var path = url.match(/http:\/\/[^/]+\/[^/]+\//)[0]; 
    return path;
}

//取得当前工程路径
function getProjectPath() {
    var url = window.location.href;
    var path = url.match(/([^\/\\\?]*[\/\\])+/)[0];
    path = path.replace(/file:\/+/, '');
    return path;
}

//取得url中的参数属性
function getUrlValue(url, name) {
    var reg = new RegExp(name + "=([^&]*)");
    try {
        return reg.exec(url)[1];
    } catch (e) {
        return "";
    }
}

function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return decodeURI(r[2]); return null;
}


//获得三维地图范围
function get3DMapExtent() {
        var cp = YcMap3D.Window.CenterPixelToWorld(0).Position;
        var distance = YcMap3D.Navigate.getPosition().DistanceTo(cp);     
        var radius = Math.tan(26.5 / 180 * Math.PI) * distance;//18 26
        var xmax,xmin,ymax,ymin;
        if(getRefernceType()=="meter"){
            xmax = parseFloat(cp.X) + parseFloat(radius);
            xmin = parseFloat(cp.X) - parseFloat(radius);
            ymax = parseFloat(cp.Y) + parseFloat(radius);
            ymin = parseFloat(cp.Y) - parseFloat(radius);
        }else{
            xmax = parseFloat(cp.X) + parseFloat(radius / 111111);
            xmin = parseFloat(cp.X) - parseFloat(radius / 111111);
            ymax = parseFloat(cp.Y) + parseFloat(radius / 111111);
            ymin = parseFloat(cp.Y) - parseFloat(radius / 111111);    
        }         
        var extent={"xmin":xmin,"xmax":xmax,"ymin":ymin,"ymax":ymax};
        return extent;
}

//默认的LabelStyle
function getLabelStyle() {
    return YcMap3D.Creator.CreateLabelStyle();     
}

//根据路径创建文件夹
function createFolder(path) {
    if (!YcMap3D.ProjectTree.FindItem(path)) {
        var paths = path.split(/[\\\/]/);
        var parentGroup = "";
        if (paths[0] == HiddenGroup) {
            parentGroup = HiddenGroup;
            paths.splice(0, 1);
        }
        for (var i = 0; i < paths.length; i++) {
            parentGroup = YcMap3D.ProjectTree.CreateGroup(paths[i], parentGroup);
        }
        return parentGroup;
    } else {
        return YcMap3D.ProjectTree.FindItem(path);
    }
}

//获得文件夹下的所有对象
function getFolderObjects(path) {
    function getChilds(rootID,arr) {
	    var childID = YcMap3D.ProjectTree.GetNextItem(rootID, 11);
	    while (childID) {
	        arr.push(childID);
	        if (YcMap3D.ProjectTree.GetNextItem(childID, 11)) {
	            getChilds(childID,arr);
	        }           
	        childID = YcMap3D.ProjectTree.GetNextItem(childID, 13);
	    }
    }
    var arr = [];
    if(path!=null&&path!=undefined){
        var rootID = findItemByName(path);
        getChilds(rootID, arr);
    }
    return arr;
}

//打印错误信息
function ajaxError(arguments) {
    var str="";
    for(var i=0;i<arguments.length;i++){
        str+=arguments[i];
    }
    return str;
}

//根据ID定位到对象(主要包括动态路径、图层、文件夹等特殊对象的定位)
function FlyToByID(id) {  
    if(YcMap3D.ProjectTree.IsGroup(id)){//文件夹或者是feature图层、3dml图层
        if(YcMap3D.ProjectTree.IsLayer(id)){//feature图层
            YcMap3D.Navigate.FlyTo(id);
        }else{//文件夹或3dml图层
	        if(YcMap3D.ProjectTree.GetObject(id)){
	            YcMap3D.Navigate.FlyTo(YcMap3D.ProjectTree.GetObject(id).Position);
	        }else{
	            
	        }
        }
    }else{//不是组
        var obj=YcMap3D.ProjectTree.GetObject(id);
        var objType=obj.ObjectType;
        if(objType==34){
            obj.Stop();
            obj.Play(0);
        }else if(objType==23){
            obj.RestartRoute(0);
            YcMap3D.Navigate.FlyTo(id,obj.Action.Code);
        }else{
            YcMap3D.Navigate.FlyTo(id);
        }
    }
}

//获取目标下的子节点
function getChildrens(curID) {
    //递归查找子节点
    function getObjectChildren(curID, resultObj) {
        while (curID) {
            var obj = createTreeObj(curID);
            resultObj.children.push(obj); //将对象加入结果中          
            if (YcMap3D.ProjectTree.GetNextItem(curID, 11)) {//如果当前对象有子节点则先处理子节点
                getObjectChildren(YcMap3D.ProjectTree.GetNextItem(curID, 11), obj)
            }
            curID = YcMap3D.ProjectTree.GetNextItem(curID, 13);    //当对象没有子节点就指向下一个节点继续递归
        }
    }
    //根据当前节点创建对象
    function createTreeObj(curID) {
	    var obj = { id: curID, name: YcMap3D.ProjectTree.GetItemName(curID), children: [] };
	    return obj;
    }

    var name="";
    if(curID!=""){
        name=YcMap3D.ProjectTree.GetItemName(curID);
    }
    var resultArray = {id:curID,name:name, children: [] };   
    var tempChildID = YcMap3D.ProjectTree.GetNextItem(curID, 11); //是否有子节点
    if (tempChildID) {//如果根节点有子节点则遍历开始
        getObjectChildren(tempChildID, resultArray); //递归初始条件
    } 
    return resultArray;
}

//获得鼠标位置
function getMousePosition(type) {
    var position = YcMap3D.Window.PixelToWorld(YcMap3D.Window.GetMouseInfo().X, YcMap3D.Window.GetMouseInfo().Y, type).Position;
    return position;
}

//获得鼠标位置的对象
function getMouseObjectID(type) {
    var objectID = YcMap3D.Window.PixelToWorld(YcMap3D.Window.GetMouseInfo().X, YcMap3D.Window.GetMouseInfo().Y, type).ObjectID;
    return objectID;
}

//查找该名称的对象
function findItemByName(name) {
    // if (YcMap3D.ProjectTree.FindItem(name) > 0) {//skyline6.1
    if (YcMap3D.ProjectTree.FindItem(name) != "") {//skyline6.5
        if (YcMap3D.ProjectTree.IsLayer(YcMap3D.ProjectTree.FindItem(name))) {
            return YcMap3D.ProjectTree.GetLayer(YcMap3D.ProjectTree.FindItem(name));
        } else if(YcMap3D.ProjectTree.IsGroup(YcMap3D.ProjectTree.FindItem(name))){
            return YcMap3D.ProjectTree.FindItem(name);
        } else {
            return YcMap3D.ProjectTree.GetObject(YcMap3D.ProjectTree.FindItem(name));
        }
    } else {
        return 'null';
    }
}

//从文件夹查找对象
function findItemByNameInFolder(folderPath,objectName) {
    if (YcMap3D.ProjectTree.FindItem(folderPath) != "") {
        var groupID = YcMap3D.ProjectTree.FindItem(folderPath);
        var firstChild = YcMap3D.ProjectTree.GetNextItem(groupID,11);
        if(firstChild!=null&&firstChild!=""&&YcMap3D.ProjectTree.GetItemName(firstChild)==objectName)
            return firstChild;
        else{
            var childID = YcMap3D.ProjectTree.GetNextItem(firstChild,13);
            while(childID!=""){
                if(YcMap3D.ProjectTree.GetItemName(childID)==objectName){
                    return childID;
                }
                childID = YcMap3D.ProjectTree.GetNextItem(childID,13);
            }
        }
    }
}

//三维场景全局清除
function clearMap3D(){
	if(ZoomInOutToolGlobe.ZoomInOutType!=""){//拉框缩放
		zoomInOutRBUpHandler();
	}else if(YcMap3D.ProjectTree.FindItem(DrawToolGlobe.DrawToolFolder)!=""){//绘制
		if(DrawToolGlobe.DrawOperation!="")
            DrawTool.deactivate();
        DrawTool.clear();
	}else if(YcMap3D.ProjectTree.FindItem(MeasureToolGlobe.MeasureFolder)!=""){//量测
		if(MeasureToolGlobe.MeasureOperation!="")
			MeasureTool.deactivate();
		deleteItemsByName(MeasureToolGlobe.MeasureFolder);
	}else if(YcMap3D.ProjectTree.FindItem(PlotToolGlobe.PlotToolFolder)!=""){//标绘
	    if(PlotToolGlobe.PlotOperation!="")
	        PlotTool.deactivate();
        PlotTool.clear();
    }
    if(AnalysisToolGloble.AnalysisType!=""){//三维分析清除
        AnalyseTool.deactivate();
    }
    YcMap3D.Window.HideMessageBarText();//清除隐藏消息栏
    deleteFolderObjects(configration.QueryIcoFolder);
    deleteFolderObjects(configration.QueryDrawFolder);
}

//删除文件夹下的所有对象
function deleteFolderObjects(folderName) {
    var rootID = YcMap3D.ProjectTree.FindItem(folderName);
    if(rootID!=""&&YcMap3D.ProjectTree.IsGroup(rootID)){
        var arr = [];
        var childID = YcMap3D.ProjectTree.GetNextItem(rootID, 11);
        while (childID) {
            arr.push(childID);
            if (YcMap3D.ProjectTree.GetNextItem(childID, 11)) {
                getChilds(childID,arr);
            }
            childID = YcMap3D.ProjectTree.GetNextItem(childID, 13);
        }
        for(var i=0;i<arr.length;i++){
            YcMap3D.ProjectTree.DeleteItem(arr[i]);
        }
    }
}

//确认删除对话框
function deleteConfirm(mes) {
    return confirm(mes);
}

//判断项目坐标类型
function getRefernceType() {
     var referenceType = "meter";
     if(!YcMap3D.CoordServices.SourceCoordinateSystem.IsPlanar())
         referenceType = "globe";
     return referenceType;
}