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
		autoParam: ["id", "name"]
	},
    callback: {
      beforeCheck: beforeCheck,
      onCheck: onCheck
    }
  };
  
  var code, log, className = "dark";
  function beforeCheck(treeId, treeNode) {
    className = (className === "dark" ? "":"dark");
    showLog("[ "+getTime()+" beforeCheck ]&nbsp;&nbsp;&nbsp;&nbsp;" + treeNode.name );
    return (treeNode.doCheck !== false);
  }
  
  function onCheck(e, treeId, treeNode) {
	  var url=treeNode.address;
	  if(url){//子节点
		  toggleLayerInMgr(treeNode);
		  if(map3DInit==true)
			  changeVisibleInMap3D(treeNode);
	  }else{//根节点
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
			  select.options.add(opt);
		  }else{
			  for ( var i=select.length-1;i>-1;i--) {
				if(select.options[i].value==treeNode.address){
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
  
  function showLog(str) {
    if (!log) log = $("#log");
    log.append("<li class='"+className+"'>"+str+"</li>");
    if(log.children("li").length > 6) {
      log.get(0).removeChild(log.children("li")[0]);
    }
  }
  function getTime() {
    var now= new Date(),
    h=now.getHours(),
    m=now.getMinutes(),
    s=now.getSeconds(),
    ms=now.getMilliseconds();
    return (h+":"+m+":"+s+ " " +ms);
  }

  function checkNode(e) {
    var zTree = $.fn.zTree.getZTreeObj("treeMaptcgl"),
    type = e.data.type,
    nodes = zTree.getSelectedNodes();
    if (type.indexOf("All")<0 && nodes.length == 0) {
      alert("请先选择一个节点");
    }

    if (type == "checkAllTrue") {
      zTree.checkAllNodes(true);
    } else if (type == "checkAllFalse") {
      zTree.checkAllNodes(false);
    } else {
      var callbackFlag = $("#callbackTrigger").attr("checked");
      for (var i=0, l=nodes.length; i<l; i++) {
        if (type == "checkTrue") {
          zTree.checkNode(nodes[i], true, false, callbackFlag);
        } else if (type == "checkFalse") {
          zTree.checkNode(nodes[i], false, false, callbackFlag);
        } else if (type == "toggle") {
          zTree.checkNode(nodes[i], null, false, callbackFlag);
        }else if (type == "checkTruePS") {
          zTree.checkNode(nodes[i], true, true, callbackFlag);
        } else if (type == "checkFalsePS") {
          zTree.checkNode(nodes[i], false, true, callbackFlag);
        } else if (type == "togglePS") {
          zTree.checkNode(nodes[i], null, true, callbackFlag);
        }
      }
    }
  }

  function setAutoTrigger(e) {
    var zTree = $.fn.zTree.getZTreeObj("treeMaptcgl");
    zTree.setting.check.autoCheckTrigger = $("#autoCallbackTrigger").attr("checked");
    $("#autoCheckTriggerValue").html(zTree.setting.check.autoCheckTrigger ? "true" : "false");
  }

  $(document).ready(function(){
    $.fn.zTree.init($("#treeMaptcgl"), setting);
    $("#autoCallbackTrigger").bind("change", {}, setAutoTrigger);
  });
