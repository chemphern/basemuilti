var allLyrIdsExceptTheme=[];//除专题图外的所有图层id数组
var preThemeId;//上一个专题图id

var themeSetting = {
    view: {
      selectedMulti: false
    },
    check: {
      enable: false
    },
    data: {
      simpleData: {
        enable: true
      }
    },
    async: {
		enable: true,
		contentType: "application/json",
		url: path+"/themeService/themeList.do",
		dataFilter:filterHandler,
		autoParam: ["id", "name"]
	},
    callback: {
      beforeClick: beforeClickHandler,
      onClick: clickHanlder
    }
};
  
function filterHandler(treeId, parentNode, childNodes){
	if (childNodes) {
	      for(var i =0; i < childNodes.length; i++) {
	    	var treeNode = childNodes[i];
	    	treeNode.showAddress = mapConfig.preAddProxyUrl(treeNode.showAddress);
	    	treeNode.queryAddress = treeNode.queryAddress ? mapConfig.preAddProxyUrl(treeNode.queryAddress) : treeNode.showAddress;
	      }
	    }
	return childNodes;
}

$(document).ready(function(){
    $.fn.zTree.init($("#treeMapzt"), themeSetting);
});
  
function beforeClickHandler(treeId,treeNode,flag){
	if(treeNode.type!='n') return false;
}

function clickHanlder(e, treeId, treeNode,flag) {
	addThemeLayer(treeNode.id,treeNode.showAddress);
	if(mapOpt!=2)
   		changeVisibleInMap3D(treeNode,0);
}  

function addThemeLayer(lyrId,lyrUrl){
	//清除专题查询结果
	IdentifyTool.clear();
	if(preThemeId){
		var preLyr=map.getLayer(preThemeId);
		if(preLyr) map.removeLayer(preLyr);
	}
	var id=String(lyrId);
	var lyr=new esri.layers.ArcGISDynamicMapServiceLayer(lyrUrl,{id:id});
	map.addLayer(lyr);
	//记录当前专题图id
	preThemeId=id;
	return lyr;
}

//初始化专题模块
function loadThemeLayers(){
	map.setZoom(9);
	var bIds=map.basemapLayerIds;
	var lIds=map.layerIds;
	var gIds=map.graphicsLayerIds;
	
	allLyrIdsExceptTheme=[];
	$.merge(allLyrIdsExceptTheme,lIds);
	$.merge(allLyrIdsExceptTheme,gIds);
	if(bIds){
		$.merge(allLyrIdsExceptTheme,bIds);
	}
	
	$.each(allLyrIdsExceptTheme,function(i,id){
		map.getLayer(id).hide();
	});
	
	//添加一个专题
	var tree=$.fn.zTree.getZTreeObj('treeMapzt');
	var roots=tree.getNodes();
	if(roots && roots.length>0){
		tree.expandAll(true);
		var selNodes=tree.getSelectedNodes();
		var node;
		if(selNodes.length>0){
			node=selNodes[0];
		}else{
			node=roots[0].children?roots[0].children[0]:roots[0];
			tree.selectNode(node);
		}
		var lyr=addThemeLayer(node.id,node.showAddress);
		if(!lyr.visibleAtMapScale){
			lyr.show();
		}
	}
}

//卸载专题模块
function unloadThemeLayers(){
	if(preThemeId){
		//专题图处理
		removeAllTheme();
		preThemeId=null;
		//其他模块处理
		$.each(allLyrIdsExceptTheme,function(i,id){
			toggleLayer(id,true);
		});
	}
}
function toggleLayer(layerId,isShow){
	if($.isNumeric(layerId)){
		layerId=String(layerId);
	}
	var lyr=map.getLayer(layerId);
	if(lyr){
		if(isShow){
			lyr.show();
		}else{
			lyr.hide();
		} 
	}
}
function toggleAllLayers(isShow){
	toggleLayer(preThemeId,isShow);
	toggleLayer('iMkLyr',isShow);
	toggleLayer('iPtLyr',isShow);
	toggleLayer('iPlLyr',isShow);
	toggleLayer('iPgLyr',isShow);
}
function removeAllTheme(){
	var tLyr=map.getLayer(preThemeId);
	var ptLyr=map.getLayer('iPtLyr');
	var plLyr=map.getLayer('iPlLyr');
	var pgLyr=map.getLayer('iPgLyr');
	var mkLyr=map.getLayer('iMkLyr');
	if(tLyr) map.removeLayer(tLyr);
	if(ptLyr) map.removeLayer(ptLyr);
	if(plLyr) map.removeLayer(plLyr);
	if(pgLyr) map.removeLayer(pgLyr);
	if(mkLyr) map.removeLayer(mkLyr);
	map.infoWindow.hide();
}