<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@include file="/WEB-INF/views/common/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>羽辰智慧林业综合管理平台-资源管理</title>
<!-- Tell the browser to be responsive to screen width -->
<meta
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"
	name="viewport">
<link rel="shortcut icon" href="${res}/favicon.ico" />
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
<link href="${res}/plugins/ligerUI/skins/Aqua/css/ligerui-all.css"
	rel="stylesheet" type="text/css" />
<!-- 弹出框 -->
<link href="${res}/plugins/dialog/dialog.css" rel="stylesheet"
	type="text/css">
<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
  <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
  <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
  <![endif]-->
<style>
html,body {
	background-color: #ecf0f5
}

body {
	overflow-y: hidden;
}
</style>
</head>
<body>
	<div>
		<!-- Content Header (Page header) -->
		<section class="content-header">
			<h1>服务管理</h1>
			<ol class="breadcrumb">
				<li><a href="list_fwgl.html"><i
						class="iconfont iconfont-bars"></i> 首页</a></li>
				<li class="active">服务管理</li>
			</ol>
		</section>

		<!-- Main content -->
		<section class="content">
			<div class="row">
				<div class="col-md-3">
					<div class="box box-solid">
						<div class="box-header with-border">
							<h4 class="box-title">服务分类</h4>
						</div>
						<div class="box_l">
							<ul id="tree1">
								
							</ul>
						</div>
						<!-- /.box-body -->
					</div>
				</div>
				<!-- /.col -->
				<div class="col-md-9">
					<div class="box box-solid">
						<div class="box-header with-border">
							<h4 class="box-title">服务列表</h4>
							<div class="btn_box">
								<button class="current" id="serviceStart">
									<i class="glyphicon glyphicon-play"></i> 启动
								</button>
								<button id="serviceStop">
									<i class="glyphicon glyphicon-stop"></i> 停止
								</button>
								<button id="serviceDelete">
									<i class="iconfont icon-trash"></i> 删除
								</button>
								<button id="refreshVersion">
									<i class="glyphicon glyphicon-refresh"></i> 版本刷新
								</button>
								<button id="serviceRegister">
									<i class="glyphicon glyphicon-plus-sign"></i> 注册
								</button>
								<button id="serviceImport">
									<i class="glyphicon glyphicon-import"></i> 导入
								</button>
								<button id="serviceExport">
									<i class="glyphicon glyphicon-export"></i> 导出
								</button>
								<button id="seriveQuery">
									<i class="glyphicon glyphicon-search"></i> 查询
								</button>
							</div>
						</div>
						<div class="box_l">
							<div class="list" id="maingrid4"></div>
						</div>
					</div>
					<!-- /.col -->
				</div>
				<!-- /.row -->
		</section>
		<!-- /.content -->
	</div>
	<!-- /.content-wrapper -->

	<!-- jQuery 2.2.3 -->
	<script src="${res}/plugins/jQuery/jquery-2.2.3.min.js"></script>

	<!--grid-->
	<script src="${res}/plugins/ligerUI/js/core/base.js"
		type="text/javascript"></script>
	<script src="${res}/plugins/ligerUI/js/plugins/ligerGrid.js"
		type="text/javascript"></script>
	<script src="${res}/plugins/ligerUI/js/plugins/ligerDrag.js"
		type="text/javascript"></script>
	<script src="${res}/plugins/ligerUI/js/plugins/ligerMenu.js"
		type="text/javascript"></script>
	<script src="${res}/plugins/ligerUI/js/plugins/ligerTree.js"
		type="text/javascript"></script>
	<!-- Bootstrap 3.3.6 -->
	<script src="${res}/bootstrap/js/bootstrap.min.js"></script>
	<!-- AdminLTE App -->
	<script src="${res}/dist/js/app.js"></script>
	<!-- AdminLTE for demo purposes -->
	<script src="${res}/dist/js/demo.js"></script>
	<script type="text/javascript">
	var treeManager = null;
	var gridManager = null;
		;(function($) { //避免全局依赖,避免第三方破坏
			$(document).ready(function() {
				//树节点
				$(function() {
					treeManager = $("#tree1").ligerTree({
						url: "${ctx}/service/listServiceType",  
	                    nodeWidth : 90,
	                    idFieldName :'id',
	                    parentIDFieldName :'pid',
	                    onSelect : onSelectServiceType
					});
				});
				
				//选择了树结点事件
				function onSelectServiceType(obj) {
					var serverEngineId = "";
					if(obj.data.text != '服务分类') {
						serverEngineId = obj.data.id
					}
					gridManager.setParm("registerServerType",serverEngineId);
		        	window.gridManager.reload();
		        }
				
				//服务启动
				$("#serviceStart").on("click",function(e) {
					e.preventDefault();
					var selectedRows = gridManager.getSelecteds();
			    	if(selectedRows.length != 1) {
			    		alert("请选择一条记录进行操作！");
			    		return false;
			    	}
			    	else {
			    		$.ajax({
	                    	url: "${ctx}/service/start",
	                        data:{'id':selectedRows[0].id},
	                        type:"post",
	                        dataType:"json",
	                        success:function(res){
	                        	gridManager.reload();
	                            alert(res.msg);
	                        },error:function(){
	                            alert("启动失败！");
	                        }
	                    });
			    	}
				});
				
				//服务停止
				$("#serviceStop").on("click",function(e) {
					e.preventDefault();
					var selectedRows = gridManager.getSelecteds();
			    	if(selectedRows.length != 1) {
			    		alert("请选择一条记录进行操作！");
			    		return false;
			    	}
			    	else {
			    		$.ajax({
	                    	url: "${ctx}/service/stop",
	                        data:{'id':selectedRows[0].id},
	                        type:"post",
	                        dataType:"json",
	                        success:function(res){
	                        	gridManager.reload();
	                            alert(res.msg);
	                        },error:function(){
	                            alert("停止失败！");
	                        }
	                    });
			    	}
				});
				
				//服务停止
				$("#serviceDelete").on("click",function(e) {
					e.preventDefault();
					var selectedRows = gridManager.getSelecteds();
			    	if(selectedRows.length != 1) {
			    		alert("请选择一条记录进行操作！");
			    		return false;
			    	}
			    	else {
			    		$.ajax({
	                    	url: "${ctx}/service/delete",
	                        data:{'id':selectedRows[0].id},
	                        type:"post",
	                        dataType:"json",
	                        success:function(res){
	                        	gridManager.reload();
	                            alert(res.msg);
	                        },error:function(){
	                            alert("删除失败！");
	                        }
	                    });
			    	}
				});
				
				//表格列表
				$(function() {
					gridManager = $("#maingrid4").ligerGrid({
						checkbox : true,
						columns : [ {
							display : '服务注册名',
							name : 'registerName',
							align : 'left',
							width : 100
						}, {
							display : '服务显示名',
							name : 'showName',
							minWidth : 60
						}, {
							display : '注册类型',
							name : 'registerType',
							width : 100,
							align : 'left',
							render: function (item) {
  	                    	     var obj = parseInt(item.registerType);
      	                    	  <c:forEach var="map" items="${serviceRegisterType }">
      	                    	  		if(obj == "${map.key }") {
      	                    	  			return "${map.value.name }";
      	                    	  		}
  	       						  </c:forEach>
   	                     	}   
						}, {
							display : '服务状态',
							name : 'serviceStatus',
							minWidth : 100,
							render: function (item) {
 	                    	     var obj = parseInt(item.serviceStatus);
     	                    	  <c:forEach var="map" items="${serviceStatus }">
     	                    	  		if(obj == "${map.key }") {
     	                    	  			return "${map.value.name }";
     	                    	  		}
 	       						  </c:forEach>
  	                     	}   
						}, {
							display : '权限状态',
							name : 'permissionStatus',
							minWidth : 60,
							render: function (item) {
	                    	     var obj = parseInt(item.permissionStatus);
    	                    	  <c:forEach var="map" items="${permissionStatus }">
    	                    	  		if(obj == "${map.key }") {
    	                    	  			return "${map.value.name }";
    	                    	  		}
	       						  </c:forEach>
 	                     	}   
						}, {
							display : '最大版本号',
							name : 'maxVersionNum',
							minWidth : 100
						}, {
							display : '操作',
							isSort: false, render: function (rowdata, rowindex, value)
	                        {
	                          var h = "";
	                          if (!rowdata._editing)
	                          {
	                            h += "<input type='button' class='list-btn bt_edit' onclick='resource_list.editResource(2,"+ rowdata.id+ ")'/>";
	                            h += "<input type='button' class='list-btn bt_del' onclick='resource_list.deleteResource(" + rowdata.id + ")'/>";
	                            h += "<input type='button' class='list-btn bt_view' onclick='resource_list.viewResource(" + rowdata.id + ")'/>";
	                          }
	                          return h;
	                        }
						} ],
						pageSize : 30,
						url:"${ctx}/service/listService",
						width : '100%',
						height : '97%'
					});

					$("#pageloading").hide();
				});
			});
		})(jQuery);
	</script>
</body>
</html>
