<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false"%>
<%@include file="/WEB-INF/views/common/taglib.jsp"%>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>羽辰智慧林业综合管理平台-资源管理</title>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>羽辰智慧林业综合管理平台-资源管理</title>
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
</head>
<body>
	<div>
    <!-- Content Header (Page header) -->
    <section class="content-header">
      <h1>平台数据库统计</h1>
      <ol class="breadcrumb">
        <li><a href="#"><i class="iconfont iconfont-bars"></i> 首页</a></li>
        <li class="active">平台数据库统计</li>
      </ol>
    </section>

    <!-- Main content -->
    <section class="content">
    <div class="row">
        <div class="col-md-12">
          <div class="btn_box" style="float: left;margin-top:30px;"> 
            	时间：<input name="startTime" id="startTime" type="text" class="text date_plug" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})"> 
            	至 <input name="endTime" id="endTime" type="text" class="text date_plug" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})">
            <button class="current" id="queryBtn"><i class="glyphicon glyphicon-search"></i>查询</button><hr />
          </div>
          <div class="charts" id="chart"></div>
          <div id="maingrid4"></div>
        </div>
        </div>
      <!-- /.row -->
    </section>
    <!-- /.content -->
  </div>
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
<!-- echarts -->
<script src="${res }/plugins/echarts/echarts-all.js"></script>
<script src="${res }/plugins/My97DatePicker/WdatePicker.js"></script>
<%-- <script src="${res }/dist/js/pages/ptsj.js"></script> --%>
<script>
var gridManager = null;
$(document).ready(function(){
	$("#queryBtn").on("click",function(){
		query();
	});
	
	$("#queryBtn").click();
	
	//数据库列表start
    $(function () {
	   gridManager = $("#maingrid4").ligerGrid({
	         checkbox: false,
	         columns: [
	         { display: '数据库服务器名称', name: 'databaseName', minwidth: 90 },
	         { display: '登陆会话数最大值发生时间', name: 'sessionMaxDate', minwidth: 90 },
	         { display: '登陆会话数最大值', name: 'sessionMaxCount', minwidth: 90 },
	         { display: '登陆会话数最小值发生时间', name: 'sessionMinDate', minwidth: 90 },
	         { display: '登陆会话数最小值', name: 'sessionMinCount', minwidth: 90 },
	         { display: '登陆会话数平均值', name: 'sessionAverage', minwidth: 90 }
	         ], pageSize:5,
	         url:"${ctx}/statistics/listDatabaseData",
	         usePager: false,
	         width: '100%',height:'300px'
	     });
      $("#pageloading").hide(); 
	 });
	//数据库列表end
	
	function query() {
		if(gridManager) {
			gridManager.setParm("startTime",$("#startTime").val());
	    	gridManager.setParm("endTime",$("#endTime").val());
	    	gridManager.reload();
		}
		
	 	var myChart = echarts.init(document.getElementById('chart'),'macarons');
	    //获取session信息 start
	    $.ajax({
			url:"${ctx}/statistics/getSessionInfo",
			method:"post",
			data:{'startTime':$("#startTime").val(),'endTime':$("#endTime").val()},
			dataType:"json",
			success:function(ret) {
				var xData = JSON.parse(ret.xAxisData);
				var seriesData = JSON.parse(ret.seriesData);
			    var option = {
			    		  title : {
			    		      text : 'Session信息统计'
			    		  },
			    		  tooltip : {
			    		      trigger: 'axis',
			    		  },
			    		  toolbox: {
			    		      show : false,
			    		      feature : {
			    		          mark : {show: true},
			    		          dataView : {show: true, readOnly: false},
			    		          restore : {show: true},
			    		          saveAsImage : {show: true}
			    		      }
			    		  },
			    		  /* dataZoom: {
			    		      show: true,
			    		      start : 80
			    		  }, */
			    		  legend : {
			    		      data : ['Session']
			    		  },
			    		  grid: {
			    		      y2: 80
			    		  },
			    		  xAxis : [
			    		      {	
			    		    	  type : 'category',
				                  boundaryGap : false,
			    		    	  data:xData
			    		          //type : 'time',
			    		          //splitNumber:10
			    		      }
			    		  ],
			    		  yAxis : [
			    		      {
			    		          type : 'value',
			    		          axisLabel : {
				                       formatter: '{value} 个'
				                   },
			    		      }
			    		  ],
			    		  series : [
			    		      {
			    		          name: 'Session',
			    		          type: 'line',
			    		          data:seriesData
			    		      }
			    		  ]
			    		};
			    myChart.setOption(option);
			},
			error: function(result) {
				alert("connection error!");		
			}
	    });
	  //获取session信息 end
}
});
</script>
</html>
