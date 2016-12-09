/**
 * Created by ChenLong.
 * Description:实现三维场景地图查询相关操作
 * version: 1.0.0
 */

function queryAttr3d(layerName,fieldName,fieldValue){
	var ifAdd = false;
	var wfsServices = getFolderObjects(configration.WFSServiceFolder);
	if(wfsServices!=null&&wfsServices!=undefined&&wfsServices.length>0){
		for(var i=0;i<wfsServices.length;i++){
			var wfs = YcMap3D.ProjectTree.GetObject(wfsServices[i]);
			if(wfs.ObjectType==36 && wfs.TreeItem.Name==layerName){
				searchFeatureLayer(layerName,wfs,fieldName,fieldValue);
				ifAdd = true;
				break;
			}
		}
	}
	if(!ifAdd){
		alert("要素图层未加载，请先加载要素图层！");
	}
}

function searchFeatureLayer(layerName,wfsLayer,fieldName,fieldValue){
	wfsLayer.Filter = fieldName + " = '" + "科韵路'";
//	for(var j=0;j<wfsLayer.FeatureGroups.Count;j++){
//		var featureLayer = wfsLayer.FeatureGroups.Item(j);
//		if(featureLayer.TreeItem.Name == layerName){
//			alert(featureLayer.GetCurrentFeatures().Count);
//			for(var i=0;i<featureLayer.GetCurrentFeatures().Count;i++){
//				var feature = featureLayer.GetCurrentFeatures().Item(i);
//				var featureAttr = feature.FeatureAttributes.GetFeatureAttribute(fieldName);
//				if(featureAttr!=null&&featureAttr!=undefined&&featureAttr.Value.tostring() == fieldValue){
//					alert(123);
//					feature.Show = true;
//				}else{
//					feature.Show = false;
//				}
//			}
//		}
//		break;
//	}
	wfsLayer.Refresh();
	var selectFeature = wfsLayer.SelectedFeatures;
	for(var i=0;i<selectFeature.Count;i++){
		var feture = selectFeature.Item(i);
		feture.Tint.FromHTMLColor("#EB2828");
	}
	YcMap3D.ProjectTree.SetVisibility(wfsLayer.ID,true);
	YcMap3D.Navigate.FlyTo(wfsLayer,0);
}