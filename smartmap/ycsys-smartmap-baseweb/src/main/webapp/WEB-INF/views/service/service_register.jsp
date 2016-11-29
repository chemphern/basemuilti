<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@include file="/WEB-INF/views/common/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>羽辰智慧林业综合管理平台-资源管理</title>
<!-- Tell the browser to be responsive to screen width -->
<meta
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"
	name="viewport">
<link rel="shortcut icon" href="${res }/favicon.ico" />
<!-- Bootstrap 3.3.6 -->
<link rel="stylesheet" href="${res }/bootstrap/css/bootstrap.css">
<!-- iconfont -->
<link rel="stylesheet" href="${res }/iconfont/iconfont.css">
<!-- Theme style -->
<link rel="stylesheet" href="${res }/dist/css/AdminLTE.css">
<!-- AdminLTE Skins. Choose a skin from the css/skins
       folder instead of downloading all of them to reduce the load. -->
<link rel="stylesheet" href="${res }/dist/css/skins/_all-skins.css">
<!-- list -->
<link href="${res }/plugins/ligerUI/skins/Aqua/css/ligerui-all.css"
	rel="stylesheet" type="text/css" />
<!-- 弹出框 -->
<link href="${res }/plugins/dialog/dialog.css" rel="stylesheet"
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
			<h1>服务注册</h1>
			<ol class="breadcrumb">
				<li><a href="list_index.html"><i
						class="iconfont iconfont-bars"></i> 首页</a></li>
				<li class="active">服务注册</li>
			</ol>
		</section>

		<!-- Main content -->
		<section class="content" style="min-height: 650px">
			<div class="row">
				<div class="col-md-12">
					<h4 style="text-align:center;color:#666;">请选择您所需的服务类型</h4>
					<ul class="gis">
						<li><h5 style="font-weight: bold; color:#26bf8c;">地图服务</h5>
							<hr />
							<ul>
								<li style="float:left;margin-right:50px;"><a href="#"
									class="arcgis"><i class="glyphicon glyphicon-globe"></i></a>
									<p>Arcgis Server服务</p></li>
								<li style="float:left;"><a href="#" class="server"><i
										class="glyphicon glyphicon-pencil"></i></a>
									<p>第三方服务注册</p></li>
							</ul></li>
						<li class="aa">
							<button class="btn next">下一步</button>
							<button class="btn ">取消</button>
						</li>
					</ul>


				</div>
				<!-- /.col -->
			</div>
		</section>
		<!-- /.content -->
	</div>
	<!-- /.content-wrapper -->

	<!-- jQuery 2.2.3 -->
	<script src="${res }/plugins/jQuery/jquery-2.2.3.min.js"></script>
	<script
		src="${res}/plugins/jquery-validation-1.15.1/lib/jquery.form.js"></script>
	<!--grid-->
	<script src="${res }/plugins/ligerUI/js/core/base.js"
		type="text/javascript"></script>
	<script src="${res }/plugins/ligerUI/js/plugins/ligerGrid.js"
		type="text/javascript"></script>
	<script src="${res }/plugins/ligerUI/js/plugins/ligerDrag.js"
		type="text/javascript"></script>
	<script src="${res }/plugins/ligerUI/js/plugins/ligerMenu.js"
		type="text/javascript"></script>
	<script src="${res }/plugins/ligerUI/js/plugins/CustomersData.js"
		type="text/javascript"></script>
	<!-- Bootstrap 3.3.6 -->
	<script src="${res }/bootstrap/js/bootstrap.min.js"></script>
	<!-- AdminLTE App -->
	<script src="${res }/dist/js/app.js"></script>
	<!-- AdminLTE for demo purposes -->
	<script src="${res }/dist/js/demo.js"></script>
	<!-- AdminLTE for demo purposes -->
	<script type="text/javascript"
		src="${res }/plugins/wizard-master/jquery.smartWizard.js"></script>
	<!-- 封装弹出框dialog -->
	<script type="text/javascript"
		src="${res }/plugins/dialog/jquery.artDialog.source.js"></script>
	<script type="text/javascript"
		src="${res }/plugins/dialog/iframeTools.source.js"></script>
	<script type="text/javascript" src="${res }/plugins/dialog/unit.js"></script>
	
	<script type="text/javascript">
	 ;(function($){  //避免全局依赖,避免第三方破坏
		    $(document).ready(function () {
		         //arcgis弹窗
		         $(".arcgis").on('click', function (e) {   //添加/编辑解析规则
		            e.preventDefault();
		            var dialog = $.Layer.iframe(
		                {	
		                	id:"registerGisDialog",
		                    title: 'ArcServer服务注册',
		                    url:'${ctx}/service/toRegisterGis',
		                    width: 1000,
		                    height: 600,
		                    button:[]
		                });
		            //dialog.hGrid = table;
		        });

		         //第三方服务弹窗
		         $(".server").on('click', function (e) {   //添加/编辑解析规则
		            e.preventDefault();
		            var dialog = $.Layer.iframe(
		                {	
		                	id:"registerOtherDialog",
		                    title: '新增角色',
		                    url:'${ctx}/service/toRegisterOther',
		                    width: 1000,
		                    height: 600
		                });
		            //dialog.hGrid = table;
		        });
		    });
		})(jQuery);
	</script>
</body>
</html>
