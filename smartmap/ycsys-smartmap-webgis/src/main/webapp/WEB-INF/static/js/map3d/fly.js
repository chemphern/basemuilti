/**
 * Created by ChenLong.
 * Description:实现三维场景飞行定位、查询定位、书签定位、坐标定位等相关飞行定位操作
 * version: 1.0.0
 */

var flightTrip = {
    "Folder":HiddenGroup+"\\飞行漫游",
    "FolderID":""
}

function locate3d(x,y){
	var current = YcMap3D.Navigate.GetPosition(3);
	var position = YcMap3D.Creator.CreatePosition(x,y,configration.LocateAltitude,3,current.Yaw,-90,current.Roll,0);
	YcMap3D.Navigate.SetPosition(position);
//	YcMap3D.Navigate.FlyTo(position,0);
}

function locateAddress3d(name) {
    var poiLayers = getFolderObjects(configration.POIFolder);
	if(poiLayers!=null&&poiLayers!=undefined&&poiLayers.length>0){
		for(var i=0;i<poiLayers.length;i++){
            var poiLayer = YcMap3D.ProjectTree.GetObject(poiLayers[i]);
			if(poiLayer.ObjectType == 36){
                poiLayer.Filter = configration.POIField + " = '" + name + "'";
                poiLayer.Refresh();
                YcMap3D.ProjectTree.SetVisibility(poiLayer.ID,true);
                YcMap3D.Navigate.FlyTo(poiLayer,0);
				// if(poiLayer.SelectedFeatures.Count>0){
				// 	var selectFeature = poiLayer.SelectedFeatures.Item(0);
				// 	YcMap3D.Navigate.FlyTo(selectFeature.ID,0);
				// }else{
				// 	alert("未找到对应地名！");
				// }
			}
		}
	}
}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

//增加书签
function bookmarkAdd3d() {
	var addBookMarkurl = path+'/locateService/addBookMark';
    var width = YcMap3D.Window.Rect.Width/4-10;
    var height = YcMap3D.Window.Rect.Height-120;
    var pagePath = path + "/static/popup/addBookMark.html?url=" + addBookMarkurl;
    var manager = YcMap3D.Creator.CreatePopupMessage("添加书签",pagePath,10,10,415,350,-1);
    manager.ShowCaption = false;
    YcMap3D.Window.ShowPopup(manager);
}



//编辑书签
function bookmarkEdit3d() {
    if($('#tableSqdw').bootstrapTable('getSelections').length<1){
        //提示选择记录窗口
        alert("请选择需要编辑的书签记录！");
    }else{
        var editBookMarkurl = path+'/locateService/editBookMark';
        var getBookMarkUrl = path+'/locateService/getBookMark';
        var bookmarkId=$('#tableSqdw').bootstrapTable('getSelections')[0].id;
        var width = YcMap3D.Window.Rect.Width/4-10;
        var height = YcMap3D.Window.Rect.Height-120;
        var pagePath = path + "/static/popup/editBookMark.html?url=" + editBookMarkurl + "&&get=" + getBookMarkUrl + "&&id=" + bookmarkId;
        var manager = YcMap3D.Creator.CreatePopupMessage("编辑书签",pagePath,10,10,415,350,-1);
        manager.ShowCaption = false;
        YcMap3D.Window.ShowPopup(manager);
	}
}


//删除书签
function bookmarkDel3d() {
    if($('#tableSqdw').bootstrapTable('getSelections').length<1){
        //提示选择记录窗口
        alert("请选择需要删除的书签记录！");
    }else{
        if(deleteConfirm("是否删除所选飞行路径点？")){
            var bookmarkId=$('#tableSqdw').bootstrapTable('getSelections')[0].id;
            var deletePath = path + "/locateService/deleteBookMark";
            $.ajax({
                url: deletePath,
                type: "Post",
                data:{
                    bookID:bookmarkId
                },
                complete:function () {
                    $('#tableSqdw').bootstrapTable('refresh', {
                        url: path + "/locateService/toList.do"
                    });
                },
                error: function(textStatus){
                    alert("删除书签出错！详细：" + textStatus);
                }
            });
        }
	}
}

//定位书签
function locateBookmark3d(extent) {
    var centerX = (extent.xmax + extent.xmin) * 0.5;
    var centerY = (extent.ymax + extent.ymin) * 0.5;
    var distanceY, distanceX;
    if (getRefernceType() == "meter") {
        distanceY = (extent.ymax - extent.ymin) * 0.5 / (Math.tan(26 / 180 * Math.PI));
        distanceX = (extent.xmax - extent.xmin) * 0.5 / (Math.tan(26 / 180 * Math.PI));
    } else {
        distanceY = (extent.ymax - extent.ymin) * 0.5 * 111111 / (Math.tan(26 / 180 * Math.PI));
        distanceX = (extent.xmax - extent.xmin) * 0.5 * 111111 / (Math.tan(26 / 180 * Math.PI));
    }
    var yaw=0;
    var pitch=-90;
    var roll=0;
    if(extent.yaw!=null&&extent.pitch!=null&&extent.roll!=null){
        yaw = extent.yaw;
        pitch = extent.pitch;
        roll = extent.roll;
    }
    var pos = YcMap3D.Creator.CreatePosition(centerX, centerY, 0, 0, yaw, pitch, roll, 0);
    pos.distance = Math.max(distanceX, distanceY);
    YcMap3D.Navigate.FlyTo(pos, 14);
}