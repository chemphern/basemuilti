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
	  setTimeout(listFields,500);
	  setTimeout(listFieldsLogic,500);
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
	            map.addLayer(layer);
	        	if(type.indexOf("polygon")>-1){
	        		map.reorderLayer(layer,1);
	        	}else if(type.indexOf("polyline")>-1){
	        		map.reorderLayer(layer,2);
	        	}
	        }
	    }else{
	    	var lyr=map.getLayer(treeNode.id);
	    	if(lyr){
	    		map.removeLayer(lyr);
	    	}
	    }
}

$(document).ready(function(){
  $.fn.zTree.init($("#treeMaptcgl"), setting);
});
