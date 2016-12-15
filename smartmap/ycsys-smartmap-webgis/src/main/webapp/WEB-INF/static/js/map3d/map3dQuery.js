/**
 * Created by ChenLong.
 * Description:实现三维场景地图查询相关操作
 * version: 1.0.0
 */
//声明查询展示字段
var descriptionFields=["Name_CHN","Code","StationNum"];
var arrQueryInfoItem;

//要素图层查询入口
function queryAttr3d(layerName,fieldName,fieldValue){
	var ifAdd = false;
	var wfsServices = getFolderObjects(configration.WFSServiceFolder);
	if(wfsServices!=null&&wfsServices!=undefined&&wfsServices.length>0){
		for(var i=0;i<wfsServices.length;i++){
			var wfs = YcMap3D.ProjectTree.GetObject(wfsServices[i]);
			if(wfs.ObjectType==36 && wfs.TreeItem.Name==layerName){
				searchFeatureLayer(wfs,layerName,fieldName,fieldValue);
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
	// var features = wfsLayer.SelectedFeatures;
	// if(features!=null)
	// 	alert(features.Count);
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
			if(featureAttr!=null&&featureAttr!=undefined&&featureAttr.Value == fieldValue) {
                features.push(feature);
                if(features.length<6){
                    addFeatureImage(feature,features.length);
				}
                var li=createResultList(feature,fieldName,features.length);
                arrQueryInfoItem.push(li);
			}
		}
	}
	return features;
}

//动态创建查询结果列表
function createResultList(feature,fieldName,num) {
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
	summary=summary.join("—");
	var html="<li><i class='no-"+num+"'></i><a href='#' onclick='navigateToFeature("+feature.ID+")'>"+title+"</a><span>"+summary+"</span></li>";
	return html;
}

//结果列表点击飞行到要素方法
function navigateToFeature(featureID) {
	alert(123);
	YcMap3D.Navigate.FlyTo(featureID,0);
}

function clearQueryFeature() {
    alert(234);
    deleteItemsByName(configration.QueryIcoFolder);
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