/**
 * Created by ChenLong.
 * Description:实现三维场景服务图层相关操作
 * version: 1.0.0
 */

//图层管理控制图层显示隐藏
function changeVisibleInMap3D(treeNode,layerNum){
	var ifAdd = false;
	var wmsServices = getFolderObjects(configration.WMSServiceFolder);
	if(wmsServices!=null&&wmsServices!=undefined&&wmsServices.length>0){
		for(var i=0;i<wmsServices.length;i++){
			var wms = YcMap3D.ProjectTree.GetObject(wmsServices[i]);
			if(wms.ObjectType==26 && wms.TreeItem.Name==treeNode.name){
				if(YcMap3D.ProjectTree.GetVisibility(wms.ID)==1)
					YcMap3D.ProjectTree.SetVisibility(wms.ID,false);
				else if(YcMap3D.ProjectTree.GetVisibility(wms.ID)==0)
					YcMap3D.ProjectTree.SetVisibility(wms.ID,true);
				ifAdd = true;
				break;
			}
		}
	}
	if(!ifAdd){
		addWMSServiceToMap(treeNode.address,treeNode.name,layerNum);
		//addWFSServiceToMap(decodeURI(treeNode.address),treeNode.name);
	}
}

//初始化三维加载已存在服务
function initMapLayer(){
	var zTree = $.fn.zTree.getZTreeObj("treeMaptcgl");
    var nodes = zTree.getCheckedNodes("checked");
    for(var i=0;i<nodes.length;i++){
    	var node = nodes[i];
    	if(node.isParent==false){
    		addWMSServiceToMap(node.address,node.name);
    		//addWFSServiceToMap(decodeURI(node.address),node.name);
    	}
    }
    //初始化POI图层
    // initPOILayer();
}

function initPOILayer() {
    var sConnectionString = "TEPlugName=WFS;WFSVersion=1.0.0;Server=" + configration.POIUrl + ";LayerName=" + configration.POILayer + ";";
    var POIFolderID = findItemByName(configration.POIFolder);
    var wfsLayer = YcMap3D.Creator.CreateFeatureLayer(configration.POILayer,sConnectionString,POIFolderID);
    wfsLayer.Streaming=false;
    wfsLayer.DataSourceInfo.Attributes.ImportAll=true;
    wfsLayer.Load();
    wfsLayer.Visibility.Show = false;
}

function addWMSServiceToMap(address,name,layerNum){
	 setTimeout(function(){
	 	if(layerNum!=null||layerNum!=undefined){
            var server = getThemeWMSLayerServer(address);
            var url= server + "?request=GetMap&Version=1.1.1&Service=WMS&SRS=EPSG:4326&Styles=default&Transparent=true&Format=image/png&BBOX=-180.000000,-90.000000,180.000000,90.000000&WIDTH=256&HEIGHT=256 HTTP/1.1&Layers=0";
            CreatWMSlayer(url,name);
		}else{
            var server = getWMSLayerServer(address);
            var layer = address.substring(address.lastIndexOf("/")+1,address.length);
            var url= server + "?request=GetMap&Version=1.1.1&Service=WMS&SRS=EPSG:4326&Styles=default&Transparent=true&Format=image/png&BBOX=-180.000000,-90.000000,180.000000,90.000000&WIDTH=256&HEIGHT=256 HTTP/1.1&Layers=" + layer;
            CreatWMSlayer(url,name);
        }
     },500);
}

function addWFSServiceToMap(address,name){
	if(address!=""){
		var layerServer = getSFSLayerServer(address);
		var parentName = getSFSParentName(address);
		var sConnectionString = "TEPlugName=WFS;WFSVersion=1.0.0;Server=" + layerServer + ";LayerName=" + parentName + ":" + name + ";";
		var wfsLayer = YcMap3D.Creator.CreateFeatureLayer(name,sConnectionString,findItemByName(configration.WFSServiceFolder));
		wfsLayer.Streaming=false;
		wfsLayer.DataSourceInfo.Attributes.ImportAll=true;
		wfsLayer.Load();
        wfsLayer.Visibility.Show = false;
	}
}

function CreatWMSlayer(url,name) {
     var box = url.split("BBOX=")[1].split(',');
     var LayerName = name;
     var box3 = box[3].split('&')[0];
     var buffer='<EXT><ExtInfo><![CDATA[';
     buffer+='[INFO]\n';
     buffer+='MPP=2.68220901489258E-06\n';
     buffer+='Url=' + url + '\n';
     //定义 加载图片大小
     buffer+='xul=' + box[0] + '\n';
     buffer+='ylr=' + box[1] + '\n';
     buffer+='xlr=' + box[2] + '\n';
     buffer+='yul=' + box3 + '\n';
     buffer+=']]></ExtInfo><ExtType>wms</ExtType></EXT>'; 
     YcMap3D.Creator.CreateImageryLayer(LayerName, box[0], box3, box[2], box[1], buffer, "gisplg.rct", findItemByName(configration.WMSServiceFolder), LayerName)  
}

function getWMSLayerServer(url){
	var loseRest = url.replace("rest/", "");
	var server = loseRest.substring(0,loseRest.lastIndexOf("/"));
	return server + "/WMSServer";
}

function getThemeWMSLayerServer(url) {
    var loseRest = url.replace("rest/", "");
    var server = loseRest.substring(0,loseRest.lastIndexOf("/"));
    return server + "/MapServer/WMSServer";
}

function getSFSLayerServer(url){
	var loseRest = url.replace("rest/", "");
	var loseLayer = loseRest.substring(0,loseRest.lastIndexOf("/"));
	return loseLayer + "/WFSServer";
}

function getSFSParentName(url){
	return url.substring(url.indexOf("/rest/services/") + 15,url.indexOf("/MapServer"));
}