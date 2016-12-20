/**
 * Created by ChenLong.
 * Description:实现三维场景地图查询相关操作
 * version: 1.0.0
 */

// Skyline本身添加WFS服务做查询
// {
// //声明查询展示字段
// var descriptionFields=["Name_CHN","Name_ENG"];
// var arrQueryInfoItem;

// //要素图层查询入口
// function queryAttr3d(layerName,fieldName,fieldValue){
// 	var ifAdd = false;
// 	var wfsServices = getFolderObjects(configration.WFSServiceFolder);
// 	if(wfsServices!=null&&wfsServices!=undefined&&wfsServices.length>0){
// 		for(var i=0;i<wfsServices.length;i++){
// 			var wfs = YcMap3D.ProjectTree.GetObject(wfsServices[i]);
// 			if(wfs != null && wfs.ObjectType==36 && $.trim(wfs.TreeItem.Name)==$.trim(layerName)){
// 				searchFeatureLayer(wfs,$.trim(layerName),fieldName,fieldValue);
// 				ifAdd = true;
// 				break;
// 			}
// 		}
// 	}
// 	if(!ifAdd){
// 		alert("要素图层未加载，请先加载要素图层！");
// 	}
// }
//
// //要素图层查询主函数
// function searchFeatureLayer(wfsLayer,layerName,fieldName,fieldValue){
// 	// wfsLayer.Filter = fieldName + " = '" + fieldValue +"'";
// 	// wfsLayer.Refresh();
//     wfsLayer.Visibility.Show = false;
//     var features = getFeatuesFromName(wfsLayer,layerName,fieldName,fieldValue);
//     if(features!=null&&features.length>0){
//         YcMap3D.Navigate.FlyTo(wfsLayer,0);
// 	}
//     if(queryCatalog==0){
//         $('#Sxcxbox-result').css('display','block');
//         initPager(arrQueryInfoItem,"#Pagination","#queryNum","#btnBack","#Sxcxbox-result");
//     }else if(queryCatalog==2){
//         $('#Sxcxbox-logic').css('display','block');
//         initPager(arrQueryInfoItem,"#PaginationLogic","#queryLogicNum","#btnLogicBack","#Sxcxbox-logic");
//     }
// }
//
// //根据字段名称和字段值查询要素
// function getFeatuesFromName(featureLayers,layerName,fieldName,fieldValue) {
// 	var features = [];
//     arrQueryInfoItem = [];
//     for(var j=0;j<featureLayers.FeatureGroups.Count;j++){
// 		var featureLayer = featureLayers.FeatureGroups.Item(j);
// 		for(var i=0;i<featureLayer.GetCurrentFeatures().Count;i++){
// 			var feature = featureLayer.GetCurrentFeatures().Item(i);
// 			var featureAttr = feature.FeatureAttributes.GetFeatureAttribute(fieldName);
// 			console.log(featureAttr.Value);
//             console.log(featureAttr.Value.indexOf(fieldValue));
// 			if(featureAttr!=null&&featureAttr!=undefined&&featureAttr.Value.indexOf(fieldValue)>=0) {
//                 features.push(feature);
//                 if(features.length<6){
//                     addFeatureImage(feature,features.length);
// 				}
//                 var li=createPropotyResultList(feature,fieldName,features.length);
//                 arrQueryInfoItem.push(li);
// 			}
// 		}
// 	}
// 	return features;
// }
//
// //动态创建属性查询结果列表
// function createPropotyResultList(feature,fieldName,num) {
// 	//名称信息
// 	var title=feature.FeatureAttributes.GetFeatureAttribute(fieldName).Value;
// 	//简略显示的字段数组
// 	var summary=[];
// 	if(descriptionFields){
// 		for(var i=0;i<descriptionFields.length;i++){
// 			var fieldName=descriptionFields[i];
// 			var fieldValue = feature.FeatureAttributes.GetFeatureAttribute(fieldName).Value;
// 			summary.push(fieldName + ":" + fieldValue);
// 		}
// 	}
// 	//组合查询信息
// 	summary=summary.join("—");
// 	//获取FeatureID用来点击定位
// 	var featureID = feature.ID;
// 	//创建列表html对象
// 	var html="<li onclick='navigateToFeature(" + '"' + featureID + '"' + ")'><i class='no-" + num + "'></i><a href='#'>" + title +"</a><span>" + summary + "</span></li>";
// 	return html;
// }
//
// //清除场景中Feature标识
// function clearQueryFeature() {
//     deleteFolderObjects(configration.QueryIcoFolder);
// }
//
// //获取要素的定位信息
// function getFeaturePosition(feature) {
// 	var geometry = feature.Geometry;
// 	if(geometry.GeometryType==0){
// 		return YcMap3D.Creator.CreatePosition(geometry.X,geometry.Y,geometry.Z,3);
// 	}else if(geometry.GeometryType==1||geometry.GeometryType==2||geometry.GeometryType==3){
//         return YcMap3D.Creator.CreatePosition(geometry.Centroid.X,geometry.Centroid.Y,geometry.Centroid.Z,3);
// 	}else{
//         return YcMap3D.Creator.CreatePosition(geometry.Envelope.Centroid.X,geometry.Envelope.Centroid.Y,geometry.Envelope.Centroid.Z,3);
// 	}
// }
//
// //空间点查询
// function queryPoint3d() {
//     spatialFeatureReserch("point");
// }
//
// //空间线查询
// function queryPolyline3d() {
//     spatialFeatureReserch("line");
// }
//
// //空间面查询
// function queryPolygon3d() {
//     spatialFeatureReserch("area");
// }
//
// //空间查询主函数
// function spatialFeatureReserch(spatialReserchType) {
//     if(spatialReserchType == "point"){
//         YcMap3D.Window.SetInputMode(1);
//         YcMap3D.AttachEvent("OnLButtonUp", SpatialPointReserchOnLButtonUp);
//     }else if(spatialReserchType == "line"){
//         //空间查询线查询绘制操作
//         DrawTool.activate(DrawTool.DrawType.TERRAPOLYLINE);
//         //空间查询线查询结果处理
//         DrawTool.drawEndHandler = function(polyline) {
//             featureSpatialQuery(polyline.Geometry);
//         };
//     }else if(spatialReserchType == "area"){
//         //空间查询面查询绘制操作
//         DrawTool.activate(DrawTool.DrawType.TERRAPOLYGON);
//         //空间查询面查询结果处理
//         DrawTool.drawEndHandler = function (polygon) {
//             featureSpatialQuery(polygon.Geometry);
//         };
//     }
// }
//
// //左键点击选择要素事件(空间查询点查询绘制操作及结果处理)
// function SpatialPointReserchOnLButtonUp(Flags,X,Y) {
//     var pointGeometry = YcMap3D.Creator.GeometryCreator.CreatePointGeometry([X,Y,0]);
//     featureSpatialQuery(pointGeometry);
//     YcMap3D.Window.SetInputMode(0);
//     YcMap3D.DetachEvent("OnLButtonUp", SpatialPointReserchOnLButtonUp);
// }
//
// //空间查询要素空间分析
// function featureSpatialQuery(geometry) {
//     var wfsServices = getFolderObjects(configration.WFSServiceFolder);
//     if(wfsServices!=null&&wfsServices!=undefined&&wfsServices.length>0){
//         for(var i=0;i<wfsServices.length;i++){
//             var featureLayer = YcMap3D.ProjectTree.GetObject(wfsServices[i]);
//             var intersectFeatures = featureLayer.ExecuteSpatialQuery(geometry,1);
//             if(intersectFeatures!=null&&intersectFeatures.length>0){
//                 for(var j=0;j<intersectFeatures.length;j++){
//                     var feature = intersectFeatures.Item(j);
//                     if(intersectFeatures.length<6){
//                         addFeatureImage(feature,intersectFeatures.length);
//                     }
//                     var li=createSpatialResultList(feature,fieldName,features.length);
//                     arrQueryInfoItem.push(li);
//                 }
//             }
//         }
//     }
// }
//
// //动态创建空间查询结果列表
// function createSpatialResultList(feature,num) {
//     //名称信息
//     var title=feature.FeatureAttributes.GetFeatureAttribute(fieldName).Value;
//     //简略显示的字段数组
//     var summary=[];
//     if(descriptionFields){
//         for(var i=0;i<descriptionFields.length;i++){
//             var fieldName=descriptionFields[i];
//             var fieldValue = feature.FeatureAttributes.GetFeatureAttribute(fieldName).Value;
//             summary.push(fieldName + ":" + fieldValue);
//         }
//     }
//     //组合查询信息
//     summary=summary.join("—");
//     //获取FeatureID用来点击定位
//     var featureID = feature.ID;
//     //创建列表html对象
//     var html="<li onclick='navigateToFeature(" + '"' + featureID + '"' + ")'><i class='no-" + num + "'></i><a href='#'>" + title +"</a><span>" + summary + "</span></li>";
//     return html;
// }
// }

//------------------------------------------------------------------------------空间查询------------------------------------------------------------------------//
//声明当前点击对象
var currentSelectFeature=null;

//空间点查询
function queryPoint3d() {
    spatialFeatureReserch("point");
}

//空间线查询
function queryPolyline3d() {
    spatialFeatureReserch("line");
}

//空间面查询
function queryPolygon3d() {
    spatialFeatureReserch("area");
}

//空间查询主函数
function spatialFeatureReserch(spatialReserchType) {
    if(spatialReserchType == "point"){
        YcMap3D.Window.SetInputMode(1);
        YcMap3D.AttachEvent("OnLButtonUp", SpatialPointReserchOnLButtonUp);
    }else if(spatialReserchType == "line"){
        //空间查询线查询绘制操作
        DrawTool.activate(DrawTool.DrawType.TERRAPOLYLINE);
        //空间查询线查询结果处理
        DrawTool.drawEndHandler = function(polyline) {
            featureSpatialQueryLine(polyline.Geometry);
        };
    }else if(spatialReserchType == "area"){
        //空间查询面查询绘制操作
        DrawTool.activate(DrawTool.DrawType.TERRAPOLYGON);
        //空间查询面查询结果处理
        DrawTool.drawEndHandler = function (polygon) {
            featureSpatialQueryArea(polygon.Geometry);
        };
    }
}

//左键点击选择要素事件(空间查询点查询绘制操作及结果处理)
function SpatialPointReserchOnLButtonUp(Flags,X,Y) {
    var point = new esri.geometry.Point(X,Y);
    //定义二维空间查询参数对象
    var geometryObj={geometry:null}
    geometryObj.geometry = point;
    onDrawEnd(geometryObj);
}

//空间查询线要素空间查询
function featureSpatialQueryLine(geometry) {
    if(geometry!=null&&geometry.Points.Count>0){
        var coords = convert3DTo2DCoord(geometry.Points);
        var polylin = new esri.geometry.Polyline(coords);
        var geometryObj={geometry:null}
        geometryObj.geometry = polylin;
        onDrawEnd(geometryObj);
    }
}

//空间查询面要素空间查询
function featureSpatialQueryArea(geometry) {
    if(geometry!=null&&geometry.Rings.Count>0){
        var polygon = new esri.geometry.Polygon();
        for(var i=0;i<geometry.Rings.Count;i++){
            var ring = geometry.Rings.Item(i);
            var coords = convert3DTo2DCoord(ring.Points);
            polygon.addRing(coords);
        }
        var geometryObj={geometry:null}
        geometryObj.geometry = polygon;
        onDrawEnd(geometryObj);
    }
}

//------------------------------------------------------------------------------属性查询/逻辑查询------------------------------------------------------------------------//
//ArcGIS查询结果转变添加到三维场景中
function fromArcgisTo3dScene(featureSet) {
    if(map3DInit==true){
        if(featureSet!=null&&featureSet.length>0){
            clearFeatureSearchFolder();
            var featureExtent3D = null;
            var featurePointExtent = null;
            for(var i=0;i<featureSet.length;i++){
                var feature = featureSet[i];
                //绘制结果图标和要素到场景中
                var featureExtent = addFeatureToScene3D(feature,i+1);
                //获取得到结果集区域信息
                if(feature.geometry.type=="point"){
                    featurePointExtent = getPointFeatureSetExtent(featureSet,feature,featurePointExtent);
                }else if(feature.geometry.type=="polyline"||feature.geometry.type=="polygon"){
                    featureExtent3D = getPolygonPolylineFeatureSetExtent(featureExtent,featureExtent3D);
                }
            }
            //结束场景绘制，定位到结果集区域
            if(featureExtent3D!=null)
                navigateSceneToFeatures(featureExtent3D);
            else if(featurePointExtent!=null){
                navigateSceneToFeatures(featurePointExtent.getExtent());
            }
        }
    }
}

/**
 * 根据不同情况，获取线、面要素集的坐标范围
 * @param featureExtent 当前Feature对应的范围
 * @param featureExtent 当前遍历过的要素集的范围
 * @returns
 */
function getPolygonPolylineFeatureSetExtent(featureExtent,featureExtent3D) {
    if(featureExtent!=null){
        if(featureExtent3D==null)
            featureExtent3D = featureExtent;
        else{
            if(featureExtent.xmax>featureExtent3D.xmax)
                featureExtent3D.xmax = featureExtent.xmax;
            if(featureExtent.ymax>featureExtent3D.ymax)
                featureExtent3D.ymax = featureExtent.ymax;
            if(featureExtent.xmin<featureExtent3D.xmin)
                featureExtent3D.xmin=featureExtent.xmin;
            if(featureExtent.ymin<featureExtent3D.ymin)
                featureExtent3D.ymin=featureExtent.ymin;
        }
    }
    return featureExtent3D;
}

/**
 * 根据不同情况，获取点要素集的坐标范围
 * @param featureExtent 当前Feature对应的范围
 * @param featureExtent 当前遍历过的要素集的范围
 * @returns
 */
function getPointFeatureSetExtent(featureSet,feature,featurePointExtent) {
    if(featurePointExtent==null&&featureSet.length>=3)
        featurePointExtent = new esri.geometry.Multipoint(feature.geometry.spatialReference);
    else if(featurePointExtent==null&&featureSet.length<3){
        if(featureSet.length==1)
            featurePointExtent = new esri.geometry.Extent(feature.geometry.x-0.01, feature.geometry.y-0.01, feature.geometry.x+0.01, feature.geometry.y+0.01, feature.geometry.spatialReference)
        else if(featureSet.length==2) {
            var xmax = Math.max(featureSet[0].geometry.x,featureSet[1].geometry.x);
            var xmin = Math.min(featureSet[0].geometry.x,featureSet[1].geometry.x);
            var ymax = Math.max(featureSet[0].geometry.y,featureSet[1].geometry.y);
            var ymin = Math.min(featureSet[0].geometry.y,featureSet[1].geometry.y);
            featurePointExtent = new esri.geometry.Extent(xmin, ymin, xmax, ymax, feature.geometry.spatialReference)
        }
    }else if(featurePointExtent!=null&&featureSet.length>=3){
        featurePointExtent.addPoint(feature.geometry);
    }
    return featurePointExtent;
}

//将查询结果Feature展示到三维中
function addFeatureToScene3D(feature,num) {
    var geometry = feature.geometry;
    if(geometry!=null){
        if(geometry.type=="point"){
            addFeatureImage(getPointFeaturePosition(feature),feature,num);
            return null;
        }else if(geometry.type=="polygon"){
            addFeaturePolygonToScene(feature,num);
            return geometry.getExtent();
        }else if(geometry.type=="polyline"){
            addFeaturePolylineToScene(feature,num);
            return geometry.getExtent();
        }
    }else
        return null;
}

//为查询出来的要素在场景中添加图片
function addFeatureImage(position,feature,num) {
    //创建场景中要素图标
    var imgPath = getProjectPath() + "static/dist/img/map/icon_features_" + num.toString() + ".png";
    var LabelStyle = YcMap3D.Creator.CreateLabelStyle(0);
    var groupID = YcMap3D.ProjectTree.FindItem(configration.QueryIcoFolder);
    var featureImage = YcMap3D.Creator.CreateImageLabel(position,imgPath,LabelStyle,groupID,feature.attributes.OBJECTID);
    //创建图标点击信息弹窗
    var propotyUrl = path + "/static/popup/featureProperty.html?id=" + featureImage.ID + "&&attributes=" + JSON.stringify(feature.attributes) + "&&info=" + JSON.stringify(feature.infoTemplate);
    var featureMessage = YcMap3D.Creator.CreateMessage(5,propotyUrl,1,true);
    featureImage.Message.MessageID = featureMessage.ID;
}

//讲查询结果面绘制到三维场景中并高亮
function addFeaturePolygonToScene(feature,num) {
    var rings = feature.geometry.rings;
    if(rings!=null&&rings.length>0){
        for(var i=0;i<rings.length;i++){
            var ring = rings[i];
            var points = convert2DTo3DCoord(ring);
            var ringGeometry = YcMap3D.Creator.GeometryCreator.CreateLinearRingGeometry(points);
            var groupID = YcMap3D.ProjectTree.FindItem(configration.QueryDrawFolder);
            var polygon = YcMap3D.Creator.CreatePolygon(ringGeometry,"#FF0000","#FF0000",2,groupID,feature.attributes.OBJECTID);
            var centerPosition = YcMap3D.Creator.CreatePosition(polygon.Geometry.Centroid.X,polygon.Geometry.Centroid.Y,0,0);
            addFeatureImage(centerPosition,feature,num);
        }
    }
}

//讲查询结果线绘制到三维场景中并高亮
function addFeaturePolylineToScene(feature,num) {
    //获取路径
    var paths = feature.geometry.paths;
    if(paths!=null&&paths.length>0){
        for(var i=0;i<paths.length;i++){
            var points = convert2DTo3DCoord(paths[i]);
            var lineGeometry = YcMap3D.Creator.GeometryCreator.CreateLineStringGeometry(points);
            var groupID = YcMap3D.ProjectTree.FindItem(configration.QueryDrawFolder);
            var polyline = YcMap3D.Creator.CreatePolyline(lineGeometry,"#FF0000",2,groupID,feature.attributes.OBJECTID);
            var centerPointNum = Math.ceil(polyline.Geometry.Points.Count/2);
            var centerPoint = polyline.Geometry.Points.Item(centerPointNum);
            var centerPosition = YcMap3D.Creator.CreatePosition(centerPoint.X,centerPoint.Y,0,0);
            addFeatureImage(centerPosition,feature,num);
        }
    }
}

//转换数组到三维坐标
function convert2DTo3DCoord(coords) {
    if(coords!=null&&coords.length>0){
        var points = [];
        for(var j=0;j<coords.length;j++){
            points.push(coords[j][0]);
            points.push(coords[j][1]);
            points.push(0);
        }
        return points;
    }else
        return null;
}

//转换数组到二维坐标
function convert3DTo2DCoord(points) {
    if(points!=null&&points.Count>0){
        var coords = [];
        for(var j=0;j<points.Count;j++){
            var coord = [];
            coord.push(points.Item(j).X);
            coord.push(points.Item(j).Y);
            coords.push(coord);
        }
        return coords;
    }else
        return null;
}

//获取要素点的Position
function getPointFeaturePosition(feature) {
    var position;
    if(getRefernceType=="meter")
        position = YcMap3D.Creator.CreatePosition(feature.geometry.x,feature.geometry.y,0,0);
    else{
        var featureX = feature.geometry.getLongitude();
        var featureY = feature.geometry.getLatitude();
        position = YcMap3D.Creator.CreatePosition(featureX,featureY,0,0);
    }
    return position;
}

//定位到结果要素集区域
function navigateSceneToFeatures(featureExtent3D) {
    if(featureExtent3D!=null&&featureExtent3D!=undefined){
        var centerx = (featureExtent3D.xmax + featureExtent3D.xmin) * 0.5;
        var centery = (featureExtent3D.ymax + featureExtent3D.ymin) * 0.5;
        var distanceY, distanceX;
        if (getRefernceType() == "meter") {
            distanceY = (featureExtent3D.ymax - featureExtent3D.ymin) * 0.5 / (Math.tan(10 / 180 * Math.PI));
            distanceX = (featureExtent3D.xmax - featureExtent3D.xmin) * 0.5 / (Math.tan(10 / 180 * Math.PI));
        } else {
            distanceY = (featureExtent3D.ymax - featureExtent3D.ymin) * 0.5 * 111111 / (Math.tan(10 / 180 * Math.PI));
            distanceX = (featureExtent3D.xmax - featureExtent3D.xmin) * 0.5 * 111111 / (Math.tan(10 / 180 * Math.PI));
        }
        var pos = YcMap3D.Creator.CreatePosition(centerx, centery, 0, 0, 0, -90, 0, 0);
        pos.distance = Math.max(distanceX, distanceY);
        YcMap3D.Navigate.FlyTo(pos, 14);
    }
}

//结果列表点击飞行到要素范围方法
function navigateToSceneFeature(featureObjId,sceneObjID) {
    if((sceneObjID==null||sceneObjID=="")&&featureObjId!=undefined){
        sceneObjID = findItemByNameInFolder(configration.QueryIcoFolder,featureObjId);
    }
    if(sceneObjID!=null&&sceneObjID!=""){
        if(currentSelectFeature!=null){
            var imagePath = currentSelectFeature.ImageFileName;
            currentSelectFeature.ImageFileName = imagePath.substr(0,imagePath.lastIndexOf(".")-1) + ".png";
        }
        var selectFeature = YcMap3D.ProjectTree.GetObject(sceneObjID);
        if(selectFeature!=null&&selectFeature.ObjectType==24){
            var imagePath = selectFeature.ImageFileName;
            selectFeature.ImageFileName = imagePath.substr(0,imagePath.lastIndexOf(".")) + "h.png";
            currentSelectFeature = selectFeature;
        }
        //YcMap3D.Navigate.FlyTo(sceneObjID,0);
        selectFeature.Message.Activate();
    }
}

//清空缓存文件夹进行下一次操作
function clearFeatureSearchFolder() {
    deleteFolderObjects(configration.QueryIcoFolder);
    deleteFolderObjects(configration.QueryDrawFolder);
}