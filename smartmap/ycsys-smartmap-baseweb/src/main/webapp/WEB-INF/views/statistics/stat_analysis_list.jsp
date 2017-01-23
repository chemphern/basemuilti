<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false"%>
<%@include file="/WEB-INF/views/common/taglib.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>羽辰智慧林业平台运维管理系统-统计分析报告</title>
  <!-- Tell the browser to be responsive to screen width -->
  <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
  <link rel="shortcut icon" href="favicon.ico" />
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
  <link href="${res }/plugins/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
  <!-- 弹出框 -->
  <link href="${res }/plugins/dialog/dialog.css" rel="stylesheet" type="text/css">
<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
  <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
  <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
  <![endif]-->
      <style>
        html,body{
            background-color: #f1f1f1
        }
        body{
        	overflow-y: hidden;
        }
        </style>
</head>
<body>
<div>
    <!-- Content Header (Page header) -->
    <section class="content-header">
      <h1>综合分析报告</h1>
    </section>
<div class="box box-solid">
	<div class="row">
        <div class="col-md-12" style="margin:0 0 20px 10px;">
          <div class="btn_box" style="float: left;margin-top:5px;"> 
           	 报告生成时间：<input type="text" id="startTime" class="text date_plug" value="${curDate }" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})"/> 
           	 至 <input type="text" id="endTime" class="text date_plug" value="${curDateTo }" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})"/>
            <button class="current" id="downLoadBtn"><i class="glyphicon glyphicon-export"></i>下载</button>
          </div>
        </div>
        <!-- /.col -->
      <!-- /.row -->
    </div>
    <!-- /.content -->
    </div>
  </div>
  <!-- /.content-wrapper -->
</body>
<!-- jQuery 2.2.3 -->
<script src="${res }/plugins/jQuery/jquery-2.2.3.min.js"></script> 
<!--grid-->
<script src="${res }/plugins/ligerUI/js/core/base.js" type="text/javascript"></script>
<script src="${res }/plugins/ligerUI/js/plugins/ligerGrid.js" type="text/javascript"></script>  
<script src="${res }/plugins/ligerUI/js/plugins/ligerDrag.js" type="text/javascript"></script>  
<script src="${res }/plugins/ligerUI/js/plugins/ligerMenu.js" type="text/javascript"></script>  
<script src="${res }/plugins/ligerUI/js/plugins/ligerTree.js" type="text/javascript"></script>  
<script src="${res }/plugins/ligerUI/js/plugins/CustomersData.js" type="text/javascript"></script> 
<!-- Bootstrap 3.3.6 -->
<script src="${res }/bootstrap/js/bootstrap.min.js"></script>
<!-- AdminLTE App -->
<script src="${res }/dist/js/app.js"></script>
<!-- AdminLTE for demo purposes -->
<script src="${res }/dist/js/demo.js"></script>
<script src="${res }/plugins/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript"
	src="${res}/plugins/dialog/jquery.artDialog.source.js"></script>
	<script type="text/javascript"
		src="${res}/plugins/dialog/iframeTools.source.js"></script>
	<script type="text/javascript" src="${res}/plugins/dialog/unit.js"></script>
<script>
$(document).ready(function(){
	$("#downLoadBtn").on("click",function(e) {
		e.preventDefault();
		var startTime = $("#startTime").val();
		var endTime = $("#endTime").val();
    	$.Layer.confirm({
              msg:"确定要下载这个时间段的报告吗？",
              fn:function(){
              		window.location.href="${ctx}/statistics/exportAnalysisDatas?startTime="+startTime +"&endTime=" + endTime;
              }
        });
	});
	
});
</script>
</html>
