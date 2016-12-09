/**
 * Created by ChenLong.
 * Description:实现三维漫游功能模块
 * version: 1.0.0
 */

/////////////////////////////////////////////////////////////////////飞行漫游相关///////////////////////////////////////////////////////////////////////

//初始化加载飞行路径数据到面板
function getFlyPaths(tableName){
    var pathUrl = path + "/flightRoam/roamPathList";
    //初始化Table
    $('#' + tableName).bootstrapTable({
        url:pathUrl,           						  	  //请求后台的URL（*）
        method: 'post',                        			  //请求方式（*）
        toolbar: '#toolbar',                  			  //工具按钮用哪个容器
        striped: true,                                   //是否显示行间隔色
        cache: false,                         			  //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
        pagination: true,                                //是否显示分页（*）
        sortable: false,                      			  //是否启用排序
        sortOrder: "asc",                    			  //排序方式
        queryParams: "",			  					  //传递参数（*）
        sidePagination: "server",             			  //分页方式：client客户端分页，server服务端分页（*）
        pageNumber:1,                        			  //初始化加载第一页，默认第一页
        pageSize: 10,                         			  //每页的记录行数（*）
        pageList: [10, 25, 50, 100],         			  //可供选择的每页的行数（*）
        search: true,                        			  //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
        strictSearch: false,
        showColumns: false,                   			  //是否显示所有的列
        showRefresh: false,                   			  //是否显示刷新按钮
        minimumCountColumns: 2,               			  //最少允许的列数
        clickToSelect: true,                  			  //是否启用点击选中行
        height: 500,                          			  //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
        uniqueId: "id",                       			  //每一行的唯一标识，一般为主键列
        showToggle:false,                     			  //是否显示详细视图和列表视图的切换按钮
        cardView: false,                      			  //是否显示详细视图
        detailView: false                 			      //是否显示父子表
    });
    //刷新表格
    $('#' + tableName).bootstrapTable('refresh', {
        url: pathUrl
    });
}

function getPathPointsFromName(name){
    var i;
    for(i=0;i<testFlyPaths.length;i++){
        if(testFlyPaths[i].PathName == name)
            return testFlyPaths[i];
    }
    return null;
}

//飞行对象
var presentation=null;

//点击开始飞行方法
function beginFly(){
    if(presentation!=null&&presentation.PresentationStatus==2){
        presentation.Resume();
    }else{
        var selectFly = $('#tableFlyPathForRoam').bootstrapTable('getSelections');
        if(selectFly.length==1){
            var selectFlyObject = getPathPointsFromName(selectFly[0].pathName);
            if(selectFlyObject!=null){
                flightTrip.FolderID = createFolder(flightTrip.Folder);
                presentation = YcMap3D.Creator.CreatePresentation(flightTrip.FolderID,flightTrip.Folder);
                presentation.PlayAlgorithm=1;
                var i;
                for(i=0;i<selectFlyObject.PathPoints.length;i++){
                    var point = selectFlyObject.PathPoints[i]
                    var position = YcMap3D.Creator.CreatePosition(point.PointX,point.PointY,point.PointZ,3,point.PointYaw,point.PointPitch,point.PointRoll);
                    presentation.CreateLocationStep(1,point.StopTime,point.PointName,position);
                }
                presentation.Play(0);
            }
        }else{
            alert("请选择一条记录！");
        }
    }
}

//点击暂停飞行方法
function pauseFly(){
    if(presentation!=null){
        presentation.Pause();
    }
}

//点击停止飞行方法
function stopFly(){
    if(presentation!=null){
        presentation.Stop();
    }
}

/////////////////////////////////////////////////////////////////////路径编辑相关///////////////////////////////////////////////////////////////////////
//声明当前选择飞行路径名称
var selectRoamPath;

//界面点击新增飞行路线
function beginAddPath() {
	//清空新增书签表格
    $('#tablePathPoint').bootstrapTable('removeAll');
    //聚焦书签名称输入框
    $('#selectRoamPathName').focus();
}

//界面点击修改飞行路线
function beginEditPath() {
	//判断用户是否选择一条飞行路线
    var selectFly = $('#tableFlyPathForEdit').bootstrapTable('getSelections');
    if(selectFly!=null&&selectFly!=""&&selectFly.length==1){
        selectRoamPath = selectFly[0].pathName;
        //清空新增书签表格
        $('#tablePathPoint').bootstrapTable('removeAll');
        //聚焦书签名称输入框
        $('#selectRoamPathName').focus();
        //后台请求选择的飞行路线点，并加载到表格中
		var pathUrl = path + "/flightRoam/getRoamPathPoints?pathName=" + selectRoamPath + "";
		//请求加载选择的飞行路线点
        $('#tablePathPoint').bootstrapTable('refresh', {
            url: pathUrl
        });
        //飞行路线名称输入框赋值
        $('#selectRoamPathName').val(selectRoamPath);
        //聚焦书签名称输入框
        $('#selectRoamPathName').focus();
	}else{
        alert("请选择需要编辑的飞行路径！");
	}
}

//界面点击删除飞行路线
function beginDeletePath() {
    //判断用户是否选择一条飞行路线

	//提示用户是否删除

	//确认后调用后台接口
}

//界面点击添加飞行路线点
function addRoamPathPoint() {
    var width = YcMap3D.Window.Rect.Width/4-10;
    var height = YcMap3D.Window.Rect.Height-120;
    var pagePath = path + "/static/popup/roamAddPathPoint.html?pathName=" + selectRoamPath;
    var manager = YcMap3D.Creator.CreatePopupMessage("飞行路径添加路径点",pagePath,10,10,300,100,-1);
    manager.ShowCaption = false;
    YcMap3D.Window.ShowPopup(manager);
}

//界面点击编辑飞行路线点
function editRoamPathPoint() {
	
}

//界面点击删除飞行路径点
function deleteRoamPathPint() {
	
}