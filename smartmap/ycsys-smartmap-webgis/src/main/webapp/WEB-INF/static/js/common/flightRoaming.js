/**
 * Created by ChenLong.
 * Description:实现三维漫游功能模块
 * version: 1.0.0
 */

/////////////////////////////////////////////////////////////////////飞行漫游相关///////////////////////////////////////////////////////////////////////
var currentRoamPathUrl;

//初始化加载飞行路径数据到面板
function getFlyPaths(tableName){
    currentRoamPathUrl = path + "/flightRoam/roamPathList";
    //刷新表格
    $('#' + tableName).bootstrapTable('refresh', {
        url: currentRoamPathUrl
    });
}

//飞行对象
var presentation=null;

//点击开始飞行方法
function beginFly(){
    if(presentation!=null&&presentation.PresentationStatus==2){
        presentation.Resume();
    }else {
        //判断用户是否选择一条飞行路线
        var selectFly = $('#tableFlyPathForRoam').bootstrapTable('getSelections');
        if (selectFly != null && selectFly != "" && selectFly.length == 1) {
            selectRoamPath = selectFly[0].pathName;
            //清空飞行路径点表
            $('#tablePathPoint').bootstrapTable('removeAll');
            //后台请求选择的飞行路线点，并加载到表格中
            currentRoamPathPointUrl = path + "/flightRoam/getRoamPathPoints";
            $.ajax({
                url: currentRoamPathPointUrl,
                type: "Post",
                dataType:"json",
                data:{
                    pathName:selectRoamPath
                },
                success: function (points) {
                    if (points != null && points != undefined && points != "" && points.length > 0) {
                        flightTrip.FolderID = createFolder(flightTrip.Folder);
                        presentation = YcMap3D.Creator.CreatePresentation(flightTrip.FolderID, flightTrip.Folder);
                        presentation.PlayAlgorithm = 1;
                        for (var i = 0; i < points.length; i++) {
                            var point = points[i]
                            var position = YcMap3D.Creator.CreatePosition(point.pointX, point.pointY, point.pointZ, 3, point.pointYaw, point.pointPitch, point.pointRoll);
                            presentation.CreateLocationStep(1, point.stopTime, point.pointName, position);
                        }
                        presentation.Play(0);
                    } else {
                        alert("该飞行路径下没有数据，请先添加飞行路径数据！")
                    }
                },
                error: function (textStatus) {
                    alert("获取飞行行路径点出错！详细：" + textStatus);
                }
            });
        } else {
            alert("请选择需要编辑的飞行路径！");
            return;
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
//声明当前是否添加或编辑
var pathOperationType;
//声明当前查询路径
var currentRoamPathPointUrl;

//界面点击新增飞行路线
function beginAddPath() {
    //清空路径选择
    $('#selectRoamPathName').val("");
    selectRoamPath="";
	//清空新增路径表格
    $('#tablePathPoint').bootstrapTable('removeAll');
    //聚焦书签名称输入框
    $('#selectRoamPathName').focus();
    //标识为添加操作
    pathOperationType = "add";
}

//界面点击修改飞行路线
function beginEditPath() {
    //清空路径选择
    $('#selectRoamPathName').val("");
    selectRoamPath="";
	//判断用户是否选择一条飞行路线
    var selectFly = $('#tableFlyPathForEdit').bootstrapTable('getSelections');
    if(selectFly!=null&&selectFly!=""&&selectFly.length==1){
        //设置选择的飞行路径名称
        selectRoamPath = selectFly[0].pathName;
        //填充飞行路径点到表格
        getPathPointsFromPathName(selectRoamPath);
        //飞行路线名称输入框赋值
        $('#selectRoamPathName').val(selectRoamPath);
        //聚焦飞行路径名称输入框
        $('#selectRoamPathName').focus();
        //标识为编辑操作
        pathOperationType = "edit";
        //显示添加面板
        if ($('.pathbox').is(":visible")) {
            $('.pathbox').hide();
            $('.pathbox-add').show();
        }
	}else{
        alert("请选择需要编辑的飞行路径！");
	}
}

//根据选择的飞行路径名称填充飞行路径点表
function getPathPointsFromPathName(pathName) {
    //清空飞行路径点表
    $('#tablePathPoint').bootstrapTable('removeAll');
    //后台请求选择的飞行路线点，并加载到表格中
    currentRoamPathPointUrl = path + "/flightRoam/getRoamPathPoints";
    $.ajax({
        url: currentRoamPathPointUrl,
        type: "post",
        data: {
            pathName: pathName
        },
        dataType:"json",
        success: function (points) {
            if (points != null && points != undefined && points != "" && points.length > 0) {
                for (var i = 0; i < points.length; i++) {
                    var point = points[i]
                    $('#tablePathPoint').bootstrapTable('append', {'pointIndex':point.pointIndex,'pointName':point.pointName,'stopTime':point.stopTime,
                    'X':point.pointX,'Y':point.pointY,'Z':point.pointZ,'Yaw':point.pointYaw,'Pitch':point.pointPitch,'Roll':point.pointRoll});
                }
            }
        },
        error: function (textStatus) {
            alert("获取飞行路径点出错！详细：" + textStatus);
        }
    });
}

//界面点击删除飞行路线
function beginDeletePath() {
    //判断用户是否选择一条飞行路线
    var selectFly = $('#tableFlyPathForEdit').bootstrapTable('getSelections');
    if(selectFly!=null&&selectFly!=""&&selectFly.length==1){
        //提示用户是否删除
        if(deleteConfirm("是否删除所选飞行路径点？")) {
            //获取选择的路径名称
            selectRoamPath = selectFly[0].pathName;
            //后台请求选择的飞行路线点，并加载到表格中
            var deletePath = path + "/flightRoam/deleteRoamPath";
            $.ajax({
                url: deletePath,
                type: "Post",
                data:{
                    pathName:selectRoamPath
                },
                complete: function () {
                    //请求加载选择的飞行路线点
                    $('#tableFlyPathForEdit').bootstrapTable('refresh', {
                        url: currentRoamPathUrl
                    });
                    //请求加载选择的飞行路线点
                    $('#tableFlyPathForRoam').bootstrapTable('refresh', {
                        url: currentRoamPathUrl
                    });
                },
                error: function (textStatus) {
                    alert("删除飞行路径出错！详细：" + textStatus);
                }
            });
        }
    }else{
        alert("请选择需要编辑的飞行路径！");
    }
}

//界面点击添加飞行路线点
function addRoamPathPoint() {
    if($("#roamPathNameEditSave").attr("disabled")=="disabled"){
        if(pathOperationType=="edit"){
            var width = YcMap3D.Window.Rect.Width/4-10;
            var height = YcMap3D.Window.Rect.Height-120;
            var pagePath = path + "/static/popup/roamAddPathPoint.html?path=" + selectRoamPath + "&&url=" + path;
            var manager = YcMap3D.Creator.CreatePopupMessage("飞行路径添加路径点",pagePath,10,10,405,300,-1);
            manager.ShowCaption = false;
            YcMap3D.Window.ShowPopup(manager);
        }else if(pathOperationType=="add"){
            var newPathName = $('#selectRoamPathName').val();
            if(newPathName!=""){
                var width = YcMap3D.Window.Rect.Width/4-10;
                var height = YcMap3D.Window.Rect.Height-120;
                var pagePath = path + "/static/popup/roamAddPathPoint.html?path=" + newPathName + "&&url=" + path;
                var manager = YcMap3D.Creator.CreatePopupMessage("飞行路径添加路径点",pagePath,10,10,405,300,-1);
                manager.ShowCaption = false;
                YcMap3D.Window.ShowPopup(manager);
            }else{
                alert("请先填写新建路径名称！");
            }
        }
    }else{
        alert("请先点击保存，保存新建或修改后的飞行路径名称！")
    }

}

//界面点击编辑飞行路线点
function editRoamPathPoint() {
    if($("#roamPathNameEditSave").attr("disabled")=="disabled") {
        //判断用户是否选择一条飞行路线点
        var selectPoint = $('#tablePathPoint').bootstrapTable('getSelections');
        if (selectPoint != null && selectPoint != "" && selectPoint.length == 1) {
            var selectRoamPathPoint = selectPoint[0].pointIndex;
            var top = YcMap3D.Window.Rect.Width / 4 - 10;
            var left = YcMap3D.Window.Rect.Height - 120;
            var pagePath = path + "/static/popup/roamEditPathPoint.html?url=" + path + "&&path=" + selectRoamPath + "&&point=" + selectRoamPathPoint;
            var manager = YcMap3D.Creator.CreatePopupMessage("飞行路径编辑路径点", pagePath, 10, 10, 405, 300, -1);
            manager.ShowCaption = false;
            YcMap3D.Window.ShowPopup(manager);

        } else {
            alert("请选择需要编辑的飞行路径点！");
        }
    }else{
        alert("请先点击保存，保存新建或修改后的飞行路径名称！")
    }
}

//界面点击删除飞行路径点
function deleteRoamPathPint() {
    //判断用户是否选择一条飞行路线点
    var selectPoint = $('#tablePathPoint').bootstrapTable('getSelections');
    if(selectPoint!=null&&selectPoint!=""&&selectPoint.length==1){
        var selectRoamPathPoint = selectPoint[0].pointIndex;
        var selectRoamPath = $('#selectRoamPathName').val();
        if(selectRoamPathPoint!=null&&selectRoamPathPoint!=""&&selectRoamPath!=""){
            if(deleteConfirm("是否删除所选飞行路径点？")){
                var deletePath = path + "/flightRoam/deleteRoamPathPoint";
                $.ajax({
                    url: deletePath,
                    type: "Post",
                    data:{
                        pathName:selectRoamPath,
                        pointIndex:selectRoamPathPoint
                    },
                    complete:function () {
                        getPathPointsFromPathName(selectRoamPath);
                    },
                    error: function(textStatus){
                        alert("删除飞行路径点出错！详细：" + textStatus);
                    }
                });
            }
        }else{
            alert("数据错误，请重新操作！");
        }
    }else{
        alert("请选择需要删除的飞行路径点！");
    }
}

function addOrEditRoamPath() {
    if(pathOperationType=="add"){
        var newRoamPathName = $("#selectRoamPathName").val();
        if(newRoamPathName!=""){
            var addRoamPathUrl = path + "/flightRoam/addRoamPath";
            $.ajax({
                url: addRoamPathUrl,
                type: "Post",
                data:{
                    pathName:newRoamPathName
                },
                complete:function () {
                    //请求加载选择的飞行路线
                    $('#tableFlyPathForEdit').bootstrapTable('refresh', {
                        url: currentRoamPathUrl
                    });
                    //请求加载选择的飞行路线
                    $('#tableFlyPathForRoam').bootstrapTable('refresh', {
                        url: currentRoamPathUrl
                    });
                    $("#roamPathNameEditSave").attr("disabled", true);
                },
                error: function(textStatus){
                    alert("保存飞行路径出错！详细：" + textStatus);
                }
            });
        }else{
            alert("请输入正确的飞行路线名称！");
        }
    }else{
        var newRoamPathName = $("#selectRoamPathName").val();
        if(newRoamPathName!=""&&selectRoamPath!=""){
            var updateRoamPathUrl = path + "/flightRoam/updateRoamPath";
            $.ajax({
                url: updateRoamPathUrl,
                type: "Post",
                data:{
                    oldPathName:selectRoamPath,
                    newPathName:newRoamPathName
                },
                complete:function () {
                    //请求加载选择的飞行路线
                    $('#tableFlyPathForEdit').bootstrapTable('refresh', {
                        url: currentRoamPathUrl
                    });
                    //请求加载选择的飞行路线
                    $('#tableFlyPathForRoam').bootstrapTable('refresh', {
                        url: currentRoamPathUrl
                    });
                    $("#roamPathNameEditSave").attr("disabled", true);
                },
                error: function(textStatus){
                    alert("保存飞行路径出错！详细：" + textStatus);
                }
            });
        }else{
            alert("请输入正确的飞行路线名称！");
        }
    }
}

//飞行路径点记录点击定位事件
function locatePathPoint(e, row, field) {
    var clickPosition = YcMap3D.Creator.CreatePosition(row.X,row.Y,row.Z,3,row.Yaw,row.Pitch,row.Roll);
    YcMap3D.Navigate.FlyTo(clickPosition,14);
}

//飞行路径输入修改事件
function roamPathEdit() {
    $("#roamPathNameEditSave").attr("disabled",false);
}

