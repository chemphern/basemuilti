/**
 * Created by ChenLong.
 * Description:实现三维场景地图查询相关操作
 * version: 1.0.0
 */
//声明查询展示字段
var descriptionFields=["Name_CHN","Name_ENG"];
var arrQueryInfoItem;

//要素图层查询入口
function queryAttr3d(layerName,fieldName,fieldValue){
	var ifAdd = false;
	var wfsServices = getFolderObjects(configration.WFSServiceFolder);
	if(wfsServices!=null&&wfsServices!=undefined&&wfsServices.length>0){
		for(var i=0;i<wfsServices.length;i++){
			var wfs = YcMap3D.ProjectTree.GetObject(wfsServices[i]);
			if(wfs != null && wfs.ObjectType==36 && $.trim(wfs.TreeItem.Name)==$.trim(layerName)){
				searchFeatureLayer(wfs,$.trim(layerName),fieldName,fieldValue);
				ifAdd = true;
				break;
			}
		}
	}
	if(!ifAdd){
		alert("要素图层未加载，请先加载要素图层！");
	}
}

//要素图层查询主函数
function searchFeatureLayer(wfsLayer,layerName,fieldName,fieldValue){
	// wfsLayer.Filter = fieldName + " = '" + fieldValue +"'";
	// wfsLayer.Refresh();
    wfsLayer.Visibility.Show = false;
    var features = getFeatuesFromName(wfsLayer,layerName,fieldName,fieldValue);
    if(features!=null&&features.length>0){
        YcMap3D.Navigate.FlyTo(wfsLayer,0);
	}
    if(queryCatalog==0){
        $('#Sxcxbox-result').css('display','block');
        initPager(arrQueryInfoItem,"#Pagination","#queryNum","#btnBack","#Sxcxbox-result");
    }else if(queryCatalog==2){
        $('#Sxcxbox-logic').css('display','block');
        initPager(arrQueryInfoItem,"#PaginationLogic","#queryLogicNum","#btnLogicBack","#Sxcxbox-logic");
    }
}

//根据字段名称和字段值查询要素
function getFeatuesFromName(featureLayers,layerName,fieldName,fieldValue) {
	var features = [];
    arrQueryInfoItem = [];
    for(var j=0;j<featureLayers.FeatureGroups.Count;j++){
		var featureLayer = featureLayers.FeatureGroups.Item(j);
		for(var i=0;i<featureLayer.GetCurrentFeatures().Count;i++){
			var feature = featureLayer.GetCurrentFeatures().Item(i);
			var featureAttr = feature.FeatureAttributes.GetFeatureAttribute(fieldName);
			console.log(featureAttr.Value);
            console.log(featureAttr.Value.indexOf(fieldValue));
			if(featureAttr!=null&&featureAttr!=undefined&&featureAttr.Value.indexOf(fieldValue)>=0) {
                features.push(feature);
                if(features.length<6){
                    addFeatureImage(feature,features.length);
				}
                var li=createPropotyResultList(feature,fieldName,features.length);
                arrQueryInfoItem.push(li);
			}
		}
	}
	return features;
}

//动态创建属性查询结果列表
function createPropotyResultList(feature,fieldName,num) {
	//名称信息
	var title=feature.FeatureAttributes.GetFeatureAttribute(fieldName).Value;
	//简略显示的字段数组
	var summary=[];
	if(descriptionFields){
		for(var i=0;i<descriptionFields.length;i++){
			var fieldName=descriptionFields[i];
			var fieldValue = feature.FeatureAttributes.GetFeatureAttribute(fieldName).Value;
			summary.push(fieldName + ":" + fieldValue);
		}
	}
	//组合查询信息
	summary=summary.join("—");
	//获取FeatureID用来点击定位
	var featureID = feature.ID;
	//创建列表html对象
	var html="<li onclick='navigateToFeature(" + '"' + featureID + '"' + ")'><i class='no-" + num + "'></i><a href='#'>" + title +"</a><span>" + summary + "</span></li>";
	return html;
}

//结果列表点击飞行到要素方法
function navigateToFeature(featureID) {
    FlyToByID(featureID);
}

//清除场景中Feature标识
function clearQueryFeature() {
    deleteFolderObjects(configration.QueryIcoFolder);
}

//为查询出来的要素在场景中添加图片
function addFeatureImage(feature,num) {
	var position = getFeaturePosition(feature);
	var imgPath = getProjectPath() + "/static/dist/img/map/icon_features_0" + num.toString() + ".png";
	var LabelStyle = YcMap3D.Creator.CreateLabelStyle(0);
	var groupID = YcMap3D.ProjectTree.FindItem(configration.QueryIcoFolder);
    YcMap3D.Creator.CreateImageLabel(position,imgPath,LabelStyle,groupID,"要素查询");
}

//获取要素的定位信息
function getFeaturePosition(feature) {
	var geometry = feature.Geometry;
	if(geometry.GeometryType==0){
		return YcMap3D.Creator.CreatePosition(geometry.X,geometry.Y,geometry.Z,3);
	}else if(geometry.GeometryType==1||geometry.GeometryType==2||geometry.GeometryType==3){
        return YcMap3D.Creator.CreatePosition(geometry.Centroid.X,geometry.Centroid.Y,geometry.Centroid.Z,3);
	}else{
        return YcMap3D.Creator.CreatePosition(geometry.Envelope.Centroid.X,geometry.Envelope.Centroid.Y,geometry.Envelope.Centroid.Z,3);
	}
}

var spatialReserchType;
//空间点查询
function queryPoint3d() {
    spatialReserchType = "point"
    spatialFeatureReserch();
}

//空间线查询
function queryPolyline3d() {
    spatialReserchType = "line"
    spatialFeatureReserch();
}

//空间面查询
function queryPolygon3d() {
    spatialReserchType = "area"
    spatialFeatureReserch();
}

//空间查询主函数
function spatialFeatureReserch() {
	if(spatialReserchType == "point"){
        YcMap3D.Window.SetInputMode(1);
        YcMap3D.AttachEvent("OnLButtonUp", SpatialPointReserchOnLButtonUp);
	}else if(spatialReserchType == "line"){
        //空间查询线查询绘制操作
        DrawTool.activate(DrawTool.DrawType.TERRAPOLYLINE);
        //空间查询线查询结果处理
        DrawTool.drawEndHandler = function(polyline) {
            featureSpatialQuery(polyline.Geometry);
        };
	}else if(spatialReserchType == "area"){
        //空间查询面查询绘制操作
        DrawTool.activate(DrawTool.DrawType.TERRAPOLYGON);
        //空间查询面查询结果处理
        DrawTool.drawEndHandler = function (polygon) {
            featureSpatialQuery(polygon.Geometry);
        };
	}
}

//左键点击选择要素事件(空间查询点查询绘制操作及结果处理)
function SpatialPointReserchOnLButtonUp(Flags,X,Y) {
	var pointGeometry = YcMap3D.Creator.GeometryCreator.CreatePointGeometry([X,Y,0]);
    featureSpatialQuery(pointGeometry);
    YcMap3D.Window.SetInputMode(0);
    YcMap3D.DetachEvent("OnLButtonUp", SpatialPointReserchOnLButtonUp);
}

//空间查询要素空间分析
function featureSpatialQuery(geometry) {
    var wfsServices = getFolderObjects(configration.WFSServiceFolder);
    if(wfsServices!=null&&wfsServices!=undefined&&wfsServices.length>0){
        for(var i=0;i<wfsServices.length;i++){
            var featureLayer = YcMap3D.ProjectTree.GetObject(wfsServices[i]);
            var intersectFeatures = featureLayer.ExecuteSpatialQuery(geometry,1);
            if(intersectFeatures!=null&&intersectFeatures.length>0){
				for(var j=0;j<intersectFeatures.length;j++){
					var feature = intersectFeatures.Item(j);
                    if(intersectFeatures.length<6){
                        addFeatureImage(feature,intersectFeatures.length);
                    }
                    var li=createSpatialResultList(feature,fieldName,features.length);
                    arrQueryInfoItem.push(li);
				}
			}
		}
    }
}

//动态创建空间查询结果列表
function createSpatialResultList(feature,num) {
    //名称信息
    var title=feature.FeatureAttributes.GetFeatureAttribute(fieldName).Value;
    //简略显示的字段数组
    var summary=[];
    if(descriptionFields){
        for(var i=0;i<descriptionFields.length;i++){
            var fieldName=descriptionFields[i];
            var fieldValue = feature.FeatureAttributes.GetFeatureAttribute(fieldName).Value;
            summary.push(fieldName + ":" + fieldValue);
        }
    }
    //组合查询信息
    summary=summary.join("—");
    //获取FeatureID用来点击定位
    var featureID = feature.ID;
    //创建列表html对象
    var html="<li onclick='navigateToFeature(" + '"' + featureID + '"' + ")'><i class='no-" + num + "'></i><a href='#'>" + title +"</a><span>" + summary + "</span></li>";
    return html;
}

//ArcGIS查询结果转变添加到三维场景中
function fromArcgisTo3dScene(featureSet) {
    var displayFieldName=featureSet.displayFieldName;
    alert(displayFieldName);
    var fieldAliases=featureSet.fieldAliases;
    alert(fieldAliases);
    var geometryType=featureSet.geometryType;
    alert(geometryType);
    var features=featureSet.features;
    alert(features.length);
}
