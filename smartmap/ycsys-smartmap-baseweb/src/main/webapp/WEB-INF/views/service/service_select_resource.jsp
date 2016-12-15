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
	<div class="row">
		<div class="col-md-3">
			<div class="box box-solid">
				<div class="box-header with-border">
					<h4 class="box-title">资源分类</h4>
				</div>
				<div class="box_l">
					<ul id="tree1">

					</ul>
				</div>
				<!-- /.box-body -->
			</div>
		</div>
		<!-- /.col -->
	</div>
</body>
<script src="${res}/plugins/jQuery/jquery-2.2.3.min.js"></script>
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
<script src="${res}/plugins/ligerUI/js/plugins/CustomersData.js"
	type="text/javascript"></script>
<script src="${res}/bootstrap/js/bootstrap.min.js"></script>
<script src="${res}/dist/js/app.js"></script>
<script src="${res}/dist/js/demo.js"></script>
<script type="text/javascript"
	src="${res}/plugins/dialog/jquery.artDialog.source.js"></script>
<script type="text/javascript"
	src="${res}/plugins/dialog/iframeTools.source.js"></script>
<script type="text/javascript" src="${res}/plugins/dialog/unit.js"></script>
<script>
var treeManager = null;
    ;(function($){  //避免全局依赖,避免第三方破坏
    	//树 start
    	$("#tree1").ligerTree(
	            {
	                url: "${ctx}/resource/listAll",  
                    nodeWidth : 350,
                    idFieldName :'id',
                    parentIDFieldName :'pid',
                    onSelect : onSelectResource
                    	
	             });
    	treeManager = $("#tree1").ligerGetTreeManager();
    	function onSelectResource(obj) {
    		//alert(11);
        	//console.log(obj.data);
        	//console.log(treeManager);
        	//console.log(treeManager.getChecked());
        	//console.log("resourceTypeId="+resourceTypeId);
        	//window.tempResourceTypeId = resourceTypeId;
        }
    })(jQuery);
    
    $(function() {
		var parentWin = window.parent;
		var dialog = parentWin.art.dialog.list["viewResourceFileDialog"];
		var dialog_div = dialog.DOM.wrap;
		
		dialog_div.on("ok", function() {
			var obj = treeManager.getChecked();
			console.log(treeManager.getChecked());
			if(obj.length == 1) {
				var id = obj[0].data.id;
				var text =  obj[0].data.text;
				parentWin.document.getElementById("resourceFileId").value = id;
				parentWin.document.getElementById("resourceFile").value = text;
				dialog.close();
			}
			else {
				alert("请选择一条记录");
			}

		});
	});
</script>
</html>
