var currentOptLayer;

var setting = {
    view: {
      selectedMulti: false
    },
    check: {
      enable: true
    },
    data: {
      simpleData: {
        enable: true
      }
    },
    async: {
		enable: true,
		contentType: "application/json",
		url: path+"/layerService/layerList.do",
		dataFilter:filterHandler,
		autoParam: ["id", "name"]
	},
    callback: {
      beforeCheck: beforeCheck,
      onCheck: onCheck
    }
  };
  
function filterHandler(treeId, parentNode, childNodes){
	if (childNodes) {
	      for(var i =0; i < childNodes.length; i++) {
	    	var treeNode = childNodes[i];
	    	treeNode.address = mapConfig.preAddProxyUrl(treeNode.address);
	      }
	    }
	return childNodes;
}

function beforeCheck(treeId, treeNode){
}
  
function onCheck(e, treeId, treeNode) {
	  var nodeType=treeNode.type;
	  if(nodeType=='n'){//子节点node
		  toggleLayerInMgr(treeNode);
		  if(map3DInit==true)
			  changeVisibleInMap3D(treeNode);
	  }else if(nodeType=='r'){//根节点root
		  var nodes=treeNode.children;
		  for(var i=0;i<nodes.length;i++){
			  var node=nodes[i];
			  toggleLayerInMgr(node);
			  if(map3DInit==true)
				  changeVisibleInMap3D(node);
		  }
	  }
}  

function addLayerItem(treeNode){
	  var select=$('#queryLyrLst')[0];
	  var selectLogic=$('#queryLyrLogic')[0];
	  doAddLayerItem(treeNode,select);
	  doAddLayerItem(treeNode,selectLogic);
	  
}

//参数select为下拉框对象
function doAddLayerItem(treeNode,select){
	  if(treeNode.geometryType && treeNode.geometryType.toLowerCase()!="null"){
		  if(treeNode.checked){
			  var opt=new Option(treeNode.name,treeNode.id);
			  opt.url=treeNode.address;
			  opt.nameField=treeNode.nameField;
			  opt.summaryFields=treeNode.summaryFields;
			  opt.displayFields=treeNode.displayFields;
			  opt.businessType = treeNode.businessType;
			  select.options.add(opt);
		  }else{
			  for ( var i=select.length-1;i>-1;i--) {
				if(select.options[i].url==treeNode.address){
					select.options.remove(i);
					break;
				}
			}
		  } 
	  }
}

function toggleLayerInMgr(treeNode){
	  addLayerItem(treeNode);
	  if(treeNode.checked){
	        var layer;
	        var url=treeNode.address;
	    	if(!treeNode.geometryType || treeNode.geometryType=="" || treeNode.geometryType.toLowerCase()=="null"){
	    		if(url.indexOf("MapServer")>-1){
	    			url=url.substring(0,url.lastIndexOf("MapServer")+9);
		        	layer=new esri.layers.ArcGISDynamicMapServiceLayer(url,{id:String(treeNode.id)});
	    		}else if(url.indexOf("ImageServer")>-1){
		        	layer=new esri.layers.ArcGISImageServiceLayer(url,{id:String(treeNode.id)});
	    		}
	    		map.addLayer(layer);
	    		map.reorderLayer(layer,1);
	        }else{
	        	var type=treeNode.geometryType.toLowerCase();
	        	layer=new esri.layers.FeatureLayer(url,{id:String(treeNode.id)});
	        	//图层业务类型(默认,全景,视频)
	        	layer.businessType = treeNode.businessType;
	        	layer.displayFields = treeNode.displayFields;
	        	layer.on('click',toPopDetail);
	            map.addLayer(layer);
	        	if(type.indexOf("polygon")>-1){
	        		map.reorderLayer(layer,1);
	        	}else if(type.indexOf("polyline")>-1){
	        		map.reorderLayer(layer,2);
	        	}
	        }
	    	
	  	  //图层加载完毕初始化查询字段
	  	  setTimeout(listFields,500);
	  	  setTimeout(listFieldsLogic,500);
	    }else{
	    	var lyr=map.getLayer(treeNode.id);
	    	if(lyr){
	    		map.removeLayer(lyr);
	    	}
	    }
}

function toPopDetail(e){
	var geo = e.graphic.geometry;
	var businessType = e.graphic._layer.businessType;
	var task = new esri.tasks.QueryTask(e.graphic._layer.url);
	var query = new esri.tasks.Query();
	query.outFields = ["*"];
	query.returnGeometry = true;
	for ( var k in e.graphic.attributes) {
		query.where = k + " = " + e.graphic.attributes[k];
		break;
	}
	task.execute(query,function(featureSet){
		var features = featureSet.features;
		if(features.length < 1) return ;
		
		var fieldAliasesObj = featureSet.fieldAliases;
		var feature = features[0];
		if(businessType == "video"){
			var resource = mapConfig.realIpPort + feature.attributes["Link"]; 
			var data = {resourcePath:resource};
			var url = path+'/static/popup/map_videojk_fullscreen.html';
			popPlayer(feature.attributes.Name_CHN ,url,"video",popWindConfig.video.width,popWindConfig.video.height,data);
		}else if(businessType == "scene"){
			var url = mapConfig.realIpPort + feature.attributes["Link"]; 
			popPlayer(feature.attributes.Name_CHN,url,"panorama",popWindConfig.scene.width,popWindConfig.scene.height);
		}else{
			var info = createTemplateContent(e.graphic._layer.displayFields,fieldAliasesObj);
			feature.setInfoTemplate(new esri.InfoTemplate("信息",info));
			popupNormalWindow(e.mapPoint,feature);
		}
	});
}

function popupNormalWindow(point,graphic){
	map.infoWindow.setFeatures([graphic]);
	map.infoWindow.markerSymbol.outline.color=new esri.Color([0,0,0,0]);
	map.infoWindow.fillSymbol.outline.color=new esri.Color([0,255,255,0.4]);
	map.infoWindow.lineSymbol.color=new esri.Color([0,255,255,0.4]);
	map.infoWindow.show(point,esri.dijit.InfoWindow.ANCHOR_UPPERRIGHT);
}

function popPlayer(title,url,id,width,height,data){
	var player = $.dialog.open(url,{
        title: title,
        id:id,
        width: width,
        height: height,
        lock:false,
        opacity:0,
        button:[],
        data:data,
        resize:true
    });
}

$(document).ready(function(){
  $.fn.zTree.init($("#treeMaptcgl"), setting);
});
