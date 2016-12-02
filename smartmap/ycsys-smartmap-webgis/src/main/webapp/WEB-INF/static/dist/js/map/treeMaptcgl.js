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

  var zNodes =[
    { id:1, pId:1, name:"建筑", open:true},
    { id:11, pId:1, name:"L2016111801", open:true},
    { id:12, pId:1, name:"L2016111802", open:true},
    { id:13, pId:1, name:"L2016111803", open:true},
    { id:2, pId:2, name:"通道"},
    { id:121, pId:2, name:"L2016111801", open:true},
    { id:122, pId:2, name:"L2016111801", open:true},
    { id:123, pId:2, name:"L2016111801", open:true},
    { id:3, pId:3, name:"地面、水奚"},
    { id:11, pId:3, name:"L2016111801", open:true},
    { id:12, pId:3, name:"L2016111802", open:true},
    { id:13, pId:3, name:"L2016111803", open:true},
    { id:4, pId:4, name:"植被"},
    { id:121, pId:4, name:"L2016111801", open:true},
    { id:122, pId:4, name:"L2016111801", open:true},
    { id:123, pId:4, name:"L2016111801", open:true},
    { id:5, pId:5, name:"小品"},
    { id:11, pId:5, name:"L2016111805", open:true},
    { id:12, pId:5, name:"L2016111805", open:true},
    { id:13, pId:5, name:"L2016111805", open:true},
    { id:6, pId:6, name:"处理过的mpt"},
    { id:121, pId:6, name:"L2016111806", open:true},
    { id:122, pId:6, name:"L2016111806", open:true},
    { id:123, pId:6, name:"L2016111806", open:true},
  ];
  
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
	  }else{//根节点
		  var nodes=treeNode.children;
		  for(var i=0;i<nodes.length;i++){
			  var node=nodes[i];
			  toggleLayerInMgr(node);
		  }
	  }
	  //初始化查询图层列表
	  var lyrData=initQueryLayerList();
	  console.log(lyrData);
	  $('#queryLyrLst').combobox("clear");
	  $('#queryLyrLst').combobox('loadData', lyrData); 
  }  
  
  function toggleLayerInMgr(treeNode){
	  if(treeNode.checked){
	        var layer;
	        var url=treeNode.address;
	    	if(!treeNode.geometryType || treeNode.geometryType=="" || treeNode.geometryType=="null"){
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
  
  function initQueryLayerList(){
		var ids=map.graphicsLayerIds;
		var opts=[];
		for(var i=0;i<ids.length;i++){
			if(ids[i]=="graphicsLayer1") continue;
			var opt={};
			var featureLyr=map.getLayer(ids[i]);
			opt.name=featureLyr.name;
			opt.value=featureLyr.id;
			opts.push(opt);
		}
		return opts;
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
