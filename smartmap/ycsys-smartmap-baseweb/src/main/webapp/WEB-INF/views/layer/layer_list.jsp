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
            background-color: #f1f1f1;
        }
        body{
            overflow-y: hidden;
        }
        </style>
</head>
<body>
<!-- Content Header (Page header) -->
      <section class="content-header">
          <h1>
            	 图层管理
          </h1>
      </section>
<div class="row">
    <div class="col-md-3 col-sm-4">
        <div class="box box-solid">
            <div class="box-header with-border">
                <h4 class="box-title">图层树</h4>
						<div class="btn_box" id="layer_btn">
						<shiro:hasPermission name="sys-layer-create">
							<button class="current" onclick="layer.add();">
							<i class="iconfont icon-plus"></i>新增
							</button>
						</shiro:hasPermission>
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
    <div class="col-md-9 col-sm-8">
        <div class="box box-solid">
            <div class="box-header with-border">
                <h4 class="box-title">图层列表</h4>
                <div class="btn_box">
                	<shiro:hasPermission name="sys-layer-create">
	                    <button class="current" onclick="layer_list.addLayer();"><i class="iconfont icon-plus"></i>新增</button>
	                </shiro:hasPermission>
	                <shiro:hasPermission name="sys-layer-edit">
	                	<button onclick="layer_list.editLayer();"><i class="iconfont icon-edit"></i>编辑</button>
	                </shiro:hasPermission>
	                <shiro:hasPermission name="sys-layer-delete">
	                	<button onclick="layer_list.deleteLayer();"><i class="iconfont icon-trash"></i>删除</button>
	               	</shiro:hasPermission>
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
		                url: "${ctx}/layer/listLayerType",
	                    nodeWidth : 105,
	                    idFieldName :'id',
	                    parentIDFieldName :'pid',
	                    onSelect : onSelectLayer
		             }); 
	    	treeManager = $("#tree1").ligerGetTreeManager();
	        var menu;
	        var actionNodeID;
	        var actionNodeName;
	        var pId;
	        
	        //树右键处理
	        $(function () {
	            menu = $.ligerMenu({ top: 100, left: 100, width: 120, items:
	            [
				<shiro:hasPermission name="sys-layer-create">
	            	{ text: '增加', click: operate, icon: 'add' },
	            </shiro:hasPermission>
	            <shiro:hasPermission name="sys-layer-edit">
	            	{ text: '修改', click: operate },
	            </shiro:hasPermission>
	            <shiro:hasPermission name="sys-layer-delete">
	           		{ text: '删除', click: deleteLayerType},
	            </shiro:hasPermission>
	            { line: true }
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
	        	var layerId = "";
	        	if(flag == 0) {
	        		layerId = actionNodeID;
	        	}
	            var dialog = $.Layer.iframe(
	                {
	                  id:"editLayerDialog",
	                  title: item.text+'图层管理',
	                  url:"${ctx}/layer/toEditLayerType?id="+layerId+"&actionNodeID="+actionNodeID,
	                  width: 400,
	                  height: 300
	                });
	        }
	    	function onSelectLayer(obj) {
	    		pId = obj.data.id;
	    		window.tempLayerId = pId;
				gridManager.setParm("pId",pId);
	        	window.gridManager.reload();
	        }
	    	
	    	//删除图层节点
	    	function deleteLayerType(item,i) {
	    		$.Layer.confirm({
	                msg:"确定删除该记录吗？",
	                fn:function(){
	                    $.ajax({
	                        data:{'id':actionNodeID},
	                    	url: "${ctx}/layer/delete",
	                        type:"post",
	                        dataType:"json",
	                        success:function(result){
	                        	$.Layer.confirm({
                	                msg:result["msg"],
                	                fn:function(){
                	                	if(result["flag"]=="1"){
	                	                	treeManager.reload();
	                	                	gridManager.reload();
                	                	}
                	                }
                	            });
	                        },
	                        error:function(){
	                            $.Layer.confirm({
                	                msg:"删除失败！"
                	            });
	                        }
	                    });
	                },
	            });
	    		
	    	}
	    	
	    	//树 end
	    	
	   //表格列表 start
       gridManager = $("#maingrid4").ligerGrid({
            checkbox: true,
            columns: [
						{ display: '图层名称', name: 'name', align: 'left', minWidth: 100},
						{ display: '几何类型', name: 'geometryType', minWidth: 60,
							render: function (item) {
								var obj = item.geometryType;
      	                    	  <c:forEach var="map" items="${geometryType }">
      	                    	  		if(obj == "${map.key }") {
      	                    	  			return "${map.value.name }";
      	                    	  		}
  	       						  </c:forEach>
  	                     	}     	
						},
	          	        { display: '服务名称',  name: 'service.showName', minWidth: 100 },
	          	        { display: '服务注册类型', name: 'service.registerType', minWidth: 60,
	   	                    render: function (item) {
	   	                    		if(item.service != null){
	   	                    			var obj = parseInt(item.service.registerType);
	        	                    	  <c:forEach var="map" items="${serviceRegisterType }">
	        	                    	  		if(obj == "${map.key }") {
	        	                    	  			return "${map.value.name }";
	        	                    	  		}
	    	       						  </c:forEach>
		   	                             
	   	                    		}
	   	                    		else {
	   	                    			return "";
	   	                    		}
		    	                 }
	          	        },
	          			{ display: '操作', 
	          	        	isSort: false, render: function (rowdata, rowindex, value)
	                        {
	                          var h = "";
	                          if (!rowdata._editing)
	                          {
	                        	 <shiro:hasPermission name="sys-layer-edit">
	                        	  	h += "<input type='button' class='list-btn bt_edit' onclick='layer_list.editLayer("+ rowdata.id+ ")'/>";
	                        	 </shiro:hasPermission>
	                        	 <shiro:hasPermission name="sys-layer-delete">
		                         	 h += "<input type='button' class='list-btn bt_del' onclick='layer_list.deleteLayer(" + rowdata.id + ")'/>";
		                         </shiro:hasPermission>
		                         <shiro:hasPermission name="sys-layer-view">
		                          	h += "<input type='button' class='list-btn bt_view' onclick='layer_list.viewLayer(" + rowdata.id + ")'/>";
		                         </shiro:hasPermission>
	                          }
	                          return h;
	                        }
	          			}
          	        ], 
          	pageSize:30,
            url:"${ctx}/layer/listData",
            width: '100%',height:'98%'
        });
    	//表格列表 end
	});
    	
    })(jQuery);	

	//图层管理树操作 function
    var layer = {
   		add:function() {
               var dialog = $.Layer.iframe(
                   {
                     id:"editLayerDialog",
                     title: '增加图层管理',
                     url:"${ctx}/layer/toEditLayerType",
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
			                  title: "查看图层",
			                  url:"${ctx}/layer/view?id="+id,
			                  width: 400,
			                  height: 500,
			                  button:[]
			               });
				}
				
			},
			//增加图层
			addLayer:function(){
				$.Layer.iframe(
	                { 
	                  id:"addLayerDialog",
	                  title: "增加图层",
	                  url:"${ctx}/layer/toAddLayer?layerTypeId="+tempLayerId,
	                  width: 400,
	                  height: 300
	               });
			},
			//修改图层
			editLayer: function(rowId) {
				var id = "";
		    	if(rowId) {
		    		id = rowId;
		    	}
		    	else {
			    	var selectedRows = gridManager.getSelecteds();
			    	if(selectedRows.length != 1) {
			    		$.Layer.confirm({
	                		msg:"请选择一条记录进行修改！",
	            		});
			    		return false;
			    	}
			    	else {
			    		id = selectedRows[0].id;
			    	}
		    	}
				$.Layer.iframe(
	                { 
	                  id:"editLayerDialog",
	                  title:"编辑图层",
	                  url:"${ctx}/layer/toEditLayer?id=" + id,
	                  width: 400,
	                  height: 450
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
		                        	$.Layer.confirm({
	                	                msg:result["msg"],
	                	                fn:function(){
	                	                	if(result["flag"]=="1"){
		                	                	treeManager.reload();
		                	                	gridManager.reload();
	                	                	}
	                	                }
	                	            });
		                        },
		                        error:function(){
		                            $.Layer.confirm({
	                	                msg:"删除失败！"
	                	            });
		                        }
		                    });
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
			                        	$.Layer.confirm({
		                	                msg:result["msg"],
		                	                fn:function(){
		                	                	if(result["flag"]=="1"){
			                	                	treeManager.reload();
			                	                	gridManager.reload();
		                	                	}
		                	                }
		                	            });
			                        },
			                        error:function(){
			                            $.Layer.confirm({
		                	                msg:"删除失败！"
		                	            });
			                        }
			                    });
			                }
			            });
			    	} 
			    	else{
			    		$.Layer.confirm({
        	                msg:"请选择记录进行删除!"
        	            });
			    		return false;
			    	}
		    	}
		    }
		};
	
</script>
</html>
