<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/views/common/taglib.jsp" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>羽辰智慧林业综合管理平台-资源管理</title>
  <!-- Tell the browser to be responsive to screen width -->
  <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
  <!-- Bootstrap 3.3.6 -->
  <link rel="stylesheet" href="${res}/bootstrap/css/bootstrap.css">
  <!-- iconfont -->
  <link rel="stylesheet" href="${res}/iconfont/iconfont.css">
  <!-- Theme style -->
  <link rel="stylesheet" href="${res}/dist/css/AdminLTE.css">
  <!-- AdminLTE Skins. Choose a skin from the css/skins
       folder instead of downloading all of them to reduce the load. -->
  <link rel="stylesheet" href="${res}/dist/css/skins/_all-skins.css">
  <!-- iCheck -->
  <link rel="stylesheet" href="${res}/plugins/iCheck/flat/blue.css">
  <!-- list -->
  <link href="${res}/plugins/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
  <!-- 弹出框 -->
  <link href="${res}/plugins/dialog/dialog.css" rel="stylesheet" type="text/css">
  <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
  <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
  <!--[if lt IE 9]>
  <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
  <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
  <![endif]-->
    <style>
        html,body{
            background-color: #ecf0f5
        }
        body{
            overflow-y: hidden;
        }
        </style>
</head>
<body>
<div class="row">
    <div class="col-md-2">
        <div class="box box-solid">
            <div class="box-header with-border">
                <h4 class="box-title">图层树</h4>
						<div class="btn_box" id="layer_btn">
							<button class="current" onclick="layer.add();">
							<i class="iconfont icon-plus"></i>新增
							</button>
						</div>
				
            </div>
            <div class="box_l">
                <ul id="tree1">
                
                </ul>
            </div>
            <!-- /.box-body -->
        </div>
    </div>
    <!-- /.col -->
    <div class="col-md-10">
        <div class="box box-solid">
            <div class="box-header with-border">
                <h4 class="box-title">图层列表</h4>
                <div class="btn_box">
                    <button class="current" onclick="layer_list.editLayer('1');"><i class="iconfont icon-plus"></i>新增</button>
                	<button onclick="layer_list.editLayer('2');"><i class="iconfont icon-edit"></i>编辑</button>
                	<button onclick="layer_list.deleteLayer();"><i class="iconfont icon-trash"></i>删除</button>
                </div>
            </div>
            <div class="box_l">
                <div class="list" id="maingrid4"></div>
            </div>
        </div>
        <!-- /.col -->
    </div>
    </div>
</body>

<!-- jQuery 2.2.3 -->
<script src="${res}/plugins/jQuery/jquery-2.2.3.min.js"></script>
<!--grid-->
<script src="${res}/plugins/ligerUI/js/core/base.js" type="text/javascript"></script>
<script src="${res}/plugins/ligerUI/js/plugins/ligerGrid.js" type="text/javascript"></script>  
<script src="${res}/plugins/ligerUI/js/plugins/ligerDrag.js" type="text/javascript"></script>  
<script src="${res}/plugins/ligerUI/js/plugins/ligerMenu.js" type="text/javascript"></script>  
<script src="${res}/plugins/ligerUI/js/plugins/ligerTree.js" type="text/javascript"></script>  
<script src="${res}/plugins/ligerUI/js/plugins/CustomersData.js" type="text/javascript"></script>
<!-- Bootstrap 3.3.6 -->
<script src="${res}/bootstrap/js/bootstrap.min.js"></script>
<!-- jQuery Knob Chart -->
<!-- Slimscroll 滚动条 -->
<!-- AdminLTE App -->
<script src="${res}/dist/js/app.js"></script>
<!-- AdminLTE for demo purposes -->
<script src="${res}/dist/js/demo.js"></script>
<!-- 封装弹出框dialog -->
<script type="text/javascript" src="${res}/plugins/dialog/jquery.artDialog.source.js"></script>
<script type="text/javascript" src="${res}/plugins/dialog/iframeTools.source.js"></script>
<script type="text/javascript" src="${res}/plugins/dialog/unit.js"></script>
<script>
	var treeManager = null;
	var gridManager = null;
	var tempLayerId = null;
	;(function($){//避免全局依赖,避免第三方破坏
		$(document).ready(function() {
			//树 start
				
	    	$("#tree1").ligerTree(
		            {
		                url: "${ctx}/layer/listAll",
	                    nodeWidth : 105,
	                    idFieldName :'id',
	                    parentIDFieldName :'pid',
	                    onSelect : onSelectLayer,
	                    /* onContextmenu : function(node, e) {
							actionNodeID = node.data.text;
							return false;
						} */
		             }); 
	    	treeManager = $("#tree1").ligerGetTreeManager();
	        var menu;
	        var actionNodeID;
	        var actionNodeName;
	        var layerId;
	        
	      //树右键处理
	        $(function () {
	            menu = $.ligerMenu({ top: 100, left: 100, width: 120, items:
	            [
	            { text: '增加', click: operate, icon: 'add' },
	            { text: '修改', click: operate },
	            { text: '删除', click: deleteLayer},
	            { line: true },
	            //{ text: '查看', click: see }
	            ]
	            });

	            $("#tree1").ligerTree({ onContextmenu: function (node, e)
	            { 
	                actionNodeID = node.data.id;
	                actionNodeName = node.data.text;
	                menu.show({ top: e.pageY, left: e.pageX });
	                return false;
	            }
	            });
	        });
	      
	      //添加或修改图层管理节点
	        function operate(item){
	        	var flag = item.text=='增加'?1:0;
	            var dialog = $.Layer.iframe(
	                {
	                  id:"editLayerDialog",
	                  title: item.text+'图层管理',
	                  url:'${ctx}/layer/toEdit?flag='+flag+'&actionNodeID='+actionNodeID,
	                  width: 400,
	                  height: 300
	                });
	        }
	    	function onSelectLayer(obj) {
	    		layerId = obj.data.id;
	    		window.tempLayerId = layerId;
				gridManager.setParm("layerId",layerId);
	        	window.gridManager.reload();
	        }
	    	
	    	//删除图层节点
	    	function deleteLayer(item,i) {
	    		$.Layer.confirm({
	                msg:"确定删除该记录吗？",
	                fn:function(){
	                    $.ajax({
	                        data:{'id':actionNodeID},
	                    	url: "${ctx}/layer/delete",
	                        type:"post",
	                        dataType:"json",
	                        success:function(result){
	            				if(result["result"]=="0") {
	            			         treeManager.reload();
	            			         //成功则删除这个树结点
	            			         //layer_type.removeTreeItem(actionNodeID);
	            			         //把所有结点都删除了，这时需要把新增按钮显示
	            			         if(result["showBtnFlag"] == "1") {
	            			        	 window.layer.showBtn();
	            			         }
	            				}
	            				else if(result["result"]=="1"){
	            					alert("该结点下有子结点,不能删除");
	            				}
	            				else {
	            					alert("请选择需要删除的图层!");
	            				}
	                        },error:function(){
	                            alert("删除失败！");
	                        }
	                    });
	                },
	                fn2:function(){
	                }
	                
	            });
	    		
	    	}
	    	
	    	//树 end
	    	
	    	//表格列表 start
	       gridManager = $("#maingrid4").ligerGrid({
	            checkbox: true,
	            columns: [
							{ display: '图层名称', name: 'name', align: 'left', minWidth: 100},
							{ display: '几何类型', name: 'geometryType', minWidth: 60},
		          	        { display: '服务注册名称',  name: 'service.registerName', align: 'left', minWidth: 100 },
		          	        { display: '服务注册类型', name: 'service.registerType', minWidth: 60},
		          	      	{ display: '远程服务类型', name: 'service.registerName', minWidth: 60 },
		          			{ display: '操作', 
		          	        	isSort: false, render: function (rowdata, rowindex, value)
		                        {
		                          var h = "";
		                          if (!rowdata._editing)
		                          {
		                        	  h += "<input type='button' class='list-btn bt_edit' onclick='layer_list.editLayer(2,"+ rowdata.id+ ")'/>";
			                          h += "<input type='button' class='list-btn bt_del' onclick='layer_list.deleteLayer(" + rowdata.id + ")'/>";
			                          h += "<input type='button' class='list-btn bt_view' onclick='layer_list.viewLayer(" + rowdata.id + ")'/>";
		                          }
		                          return h;
		                        }
		          			}
	          	        ], 
	          	pageSize:30,
	            url:"${ctx}/layer/listData",
	            width: '100%',height:'96%'
	        });
	    	//表格列表 end
		});
    	
    })(jQuery);	

	//图层管理树操作 function
    var layer = {
   		//删除数据结点
   		removeTreeItem: function(actionNodeID) {
   			var node = treeManager.getDataByID(actionNodeID);
   			if(node) {
   	    		treeManager.remove(node);
   			}
   		},
   		add:function() {
               var dialog = $.Layer.iframe(
                   {
                     id:"editLayerDialog",
                     title: '增加图层管理',
                     url:'${ctx}/layer/toEdit?flag=1',
                     width: 400,
                     height: 300
                   });
   		},
   		hideBtn: function() {
   			$("#layer_btn").hide();
   		},
   		showBtn: function() {
   			$("#layer_btn").show();
   		}
    }
  //图层管理列表操作 function
	var layer_list = {
    		viewLayer: function(id) {
				if(id) {
		    		var dialog = $.Layer.iframe(
			                { 
			                  id:"viewLayerDialog",
			                  title: '查看图层管理',
			                  url:'${ctx}/layer/detail?id='+id,
			                  width: 400,
			                  height: 500,
			                  button:[]
			               });
				}
				
			},
			//增加或修改图层管理
			editLayer: function(flag,rowId) {
			    var actionNodeID = "";
			    if(flag=='2') {
			    	if(rowId) {
			    		actionNodeID = rowId;
			    	}
			    	else {
				    	var selectedRows = gridManager.getSelecteds();
				    	if(selectedRows.length != 1) {
				    		alert("请选择一条记录进行修改！");
				    		return false;
				    	}
				    	else {
				    		actionNodeID = selectedRows[0].id;
				    	}
			    	}
			    }
				var dialog = $.Layer.iframe(
		                { 
		                  id:"editLayerDialog",
		                  title: flag =='1'?'增加图层管理':'编辑图层管理',
		                  url:'${ctx}/layer/toEditLayer?flag='+flag+'&actionNodeID='+actionNodeID,
		                  width: 1000,
		                  height: 600
		               });
				
			},
			//删除图层管理
		    deleteLayer: function(id) {
		    	if(id) {
		    		$.Layer.confirm({
		                msg:"确定删除该记录吗？",
		                fn:function(){
		                    $.ajax({
		                    	url: "${ctx}/layer/delete",
		                        data:{'id':id},
		                        type:"post",
		                        dataType:"json",
		                        success:function(result){
		                        	if(result["result"]=="0") {
		            			         treeManager.reload();
		            			         //成功则删除这个树结点
		            			         //resource_type.removeTreeItem(actionNodeID);
		            			         //把所有结点都删除了，这时需要把新增按钮显示
		            			         if(result["showBtnFlag"] == "1") {
		            			        	 window.layer.showBtn();
		            			         }
		            				}
		            				else if(result["result"]=="1"){
		            					alert("该结点下有子结点，不能删除");
		            				}
		            				else {
		            					alert("请选择行进行删除!");
		            				}
		                        	gridManager.reload();
		                        },error:function(){
		                            alert("删除失败！");
		                        }
		                    });
		                },
		                fn2:function(){
		                	gridManager.reload();
		                }
		                
		            });
		    	}
		    	else {
			    	var url = "${ctx}/layer/deletes";
			    	var selectedRows = gridManager.getSelecteds();
			    	if(selectedRows.length > 0) {
				    	var ids = [];
				    	for(var i = 0; i < selectedRows.length; i++) {
				    		ids.push(selectedRows[i].id);
				    	}
				    	var str = ids.join(",");
				    	$.Layer.confirm({
			                msg:"确定删除选择的记录吗？",
			                fn:function(){
			                    $.ajax({
			                    	url: "${ctx}/layer/deletes",
			                        data:{'idsStr':str},
			                        type:"post",
			                        dataType:"json",
			                        success:function(result){
			                        	if(result["result"]=="0") {
			            			         treeManager.reload();
			            			         //成功则删除这个树结点
			            			         //resource_type.removeTreeItem(actionNodeID);
			            			         //把所有结点都删除了，这时需要把新增按钮显示
			            			         if(result["showBtnFlag"] == "1") {
			            			        	 window.layer.showBtn();
			            			         }
			            				}
			            				else if(result["result"]=="1"){
			            					alert("该结点下有子结点，不能删除");
			            				}
			            				
			                        	gridManager.reload();
			                        },error:function(){
			                            alert("删除失败！");
			                        }
			                    });
			                },
			                fn2:function(){
			                	gridManager.reload();
			                }
			                
			            });
			    	} 
			    	else{
			    		alert("请选择数据！");
			    		return false;
			    	}
		    	}
		    }
		
		};
	
</script>
</html>
