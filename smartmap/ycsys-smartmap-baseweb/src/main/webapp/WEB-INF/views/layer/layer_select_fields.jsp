<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@include file="/WEB-INF/views/common/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>羽辰智慧林业平台运维管理系统-图层管理</title>
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
	background-color: #f1f1f1
}

body {
	overflow-y: hidden;
}
</style>
</head>
<body>
	<div>
		<!-- Main content -->
		<section class="content">
			<div class="row">
				<div class="col-md-9">
					<div class="box box-solid">
						<div class="box-header with-border">
							<h4 class="box-title">域列表</h4>
							<div class="btn_box">
							
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
	<script type="text/javascript"
	src="${res}/plugins/dialog/jquery.artDialog.source.js"></script>
	<script type="text/javascript"
		src="${res}/plugins/dialog/iframeTools.source.js"></script>
	<script type="text/javascript" src="${res}/plugins/dialog/unit.js"></script>
	<script type="text/javascript">
	var gridManager = null;
		;(function($) { //避免全局依赖,避免第三方破坏
			$(document).ready(function() {
				var parentWin = window.parent[1];
				var dialog = parentWin.art.dialog.list["selectFieldsDialog"];
				var dialog_div = dialog.DOM.wrap;
				dialog_div.on("ok", function() {
					var selectedRows = gridManager.getSelecteds();
			    	if("${flag}" != "1" && selectedRows.length < 1) {
			    		alert("请至少选择一条记录进行操作！");
			    		return false;
			    	}
			    	else if("${flag}" == "1" && selectedRows.length != 1) {
			    		alert("请选择一条记录进行操作！");
			    		return false;
			    	}
			    	else {
			    		var fileds = "";
			    		for(var i = 0; i < selectedRows.length; i++) {
			    			fileds = fileds + selectedRows[i].filed + ",";
			    		}
			    		var flag = "${flag}";
			    		fileds = fileds.substring(0,fileds.length-1);
			    		if(flag == "1") {
							parentWin.document.getElementById("nameField").value = fileds;
							parentWin.document.getElementById("tempNameField").value = fileds;
			    		}
			    		else if(flag == "2") {
			    			parentWin.document.getElementById("summaryFields").value = fileds;
							parentWin.document.getElementById("tempSummaryFields").value = fileds;
			    		}
			    		else if(flag == "3") {
			    			parentWin.document.getElementById("displayFields").value = fileds;
							parentWin.document.getElementById("tempDisplayFields").value = fileds;
			    		}
			    		//parentWin.document.getElementById("geometryType").value = selectedRows[0].geometryType;
						dialog.close();
					}

				});
				
				//表格列表
				$(function() {
					gridManager = $("#maingrid4").ligerGrid({
						checkbox : true,
						columns : [ 
						{
							display : 'Field',
							name : 'filed',
							align : 'center'
						}],
						pageSize : 50,
						url:"${ctx}/layer/listFields",
						parms: [
	                            {name:'address', value:'${address}'}
	                        ],
						width : '300%',
						height : '97%'
					});

					$("#pageloading").hide();
				});
			});
		})(jQuery);
		
	</script>
</body>
</html>
