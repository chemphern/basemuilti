<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false"%>
<%@include file="/WEB-INF/views/common/taglib.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>羽辰智慧林业平台运维管理系统-应用服务器统计</title>
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
<body style="overflow-y: auto;">
	<div>
    <!-- Content Header (Page header) -->
    <section class="content-header">
      <h1 style="background-color: #f1f1f1;">平台应用服务器统计</h1>
    </section>

    <!-- Main content -->
    <!-- <section class="content"> -->
    <div class="row">
        <div class="col-md-12">
          <div class="btn_box" style="float: left;margin:10px 0 0 10px;"> 
            	时间：<input name="startTime" id="startTime" type="text" class="text date_plug" value="${curDate }" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})"> 
            	至 <input name="endTime" id="endTime" type="text" class="text date_plug" value="${curDateTo }" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})">&nbsp;&nbsp;
            	统计类型：<select id="statType" name="statType">
		            		<option value="1">线程池信息</option>
		            		<option value="2">JVM内存信息</option>
		              </select>
            <button class="current" id="queryBtn"><i class="glyphicon glyphicon-search"></i>查询</button><hr />
          </div>
          <div class="charts" id="chart"></div>
          <div id="maingrid4"></div>
        </div>
        </div>
      <!-- /.row -->
    <!-- </section> -->
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
$(document).ready(function(){
	//统计类型改变事件
	$("#statType").on("change",function(e) {
		e.preventDefault();
		/* if($(this).val() == "1") {
			$("#spanId1").html("&nbsp;&nbsp;当前线程数");
			$("#spanId2").html("&nbsp;&nbsp;繁忙线程数");
		}
		else {
			$("#spanId1").html("&nbsp;&nbsp;已占用内存");
			$("#spanId2").html("&nbsp;&nbsp;空闲内存");
		} */
		$("#queryBtn").click();
	});
	
	//查询按钮绑定单击事件
	$("#queryBtn").on("click",function(e){
		e.preventDefault();
		query();
	});
	
	//点击查询
	$("#queryBtn").click();
	
	//查询 start
	function query() {
	 	var myChart = echarts.init(document.getElementById('chart'),'macarons');
		myChart.showLoading({
	        text: "图表数据正在努力加载..."
	    });
	 	//统计类型
	 	var statType = $("#statType").val();
	 	
	 	var legendName = "";
	 	if(statType=="1") {
	 		legendName = ['当前线程数','繁忙线程数'];
	 	}
	 	else {
	 		legendName = ['已占用内存','空闲内存'];
	 	}
	    //获取应用服务器信息 start
	    $.ajax({
			url:"${ctx}/statistics/getAppServerInfo",
			method:"post",
			data:{"startTime":$("#startTime").val(),"endTime":$("#endTime").val(),"statType":$("#statType").val()},
			dataType:"json",
			success:function(ret) {
				myChart.hideLoading();
				var xData = "";
				var seriesData1 = "";
				var seriesData2 = "";
				if(ret.xAxisData) {
					 xData = JSON.parse(ret.xAxisData);
				}
				if(ret.seriesData1) {
					seriesData1 = JSON.parse(ret.seriesData1);
				}
				if(ret.seriesData2) {
					seriesData2 = JSON.parse(ret.seriesData2);
				}
				
			    var option = {
			    		  title : {
			    		      text : '平台应用服务器统计'
			    		  },
			    		  tooltip : {
			    		      trigger: 'item',
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
			    		      //data : statType=="1"?['线程池参数统计']:['JVM内存参数统计']
			    		      data : legendName
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
				                       formatter: statType=="1"?'{value} 个':'{value} MB'
				                   },
			    		      }
			    		  ],
			    		  series : [
			    		      {
			    		          name: statType=="1"?'当前线程数':'已占用内存',
			    		          type: 'line',
			    		          data:seriesData1,
			    		          markPoint : {
				                       data : [
				                           {type : 'max', name: '最大值'},
				                           {type : 'min', name: '最小值'}
				                       ]
				                   }
			    		      },
			    		      {
			    		          name: statType=="1"?'繁忙线程数':'空闲内存',
			    		          type: 'line',
			    		          data:seriesData2,
			    		          markPoint : {
				                       data : [
				                           {type : 'max', name: '最大值'},
				                           {type : 'min', name: '最小值'}
				                       ]
				                   }
			    		      }
			    		  ]
			    		};
			    myChart.setOption(option);
			},
			error: function(result) {
				alert("connection error!");		
			}
	    });
	  //获取应用服务器信息  end
	  
	   //应用服务器列表start
	   $(function () {
		 if(statType == "1") {
			 $("#maingrid4").ligerGrid({
		         checkbox: false,
		         columns: [
		         { display: '应用服务器名称', name: 'serverName', minwidth: 100 },
		         { display: '当前线程数最大值', name: 'threadMax', minwidth: 100 },
		         { display: '当前线程数最小值', name: 'threadMin', minwidth: 100 },
		         { display: '当前线程数平均值', name: 'threadAverage', minwidth: 100 },
		         { display: '繁忙线程数最大值', name: 'busyThreadMax', minwidth: 100 },
		         { display: '繁忙线程数最小值', name: 'busyThreadMin', minwidth: 100 },
		         { display: '繁忙线程数平均值', name: 'busyThreadAverage', minwidth: 100 }
		         ], pageSize:10,
		         url:"${ctx}/statistics/listAppServerData?statType=" + statType + "&startTime=" + $("#startTime").val() + "&endTime=" + $("#endTime").val(),
		         usePager: false,
		         width: '100%',height:'300px'
		     });
		 }
		 else {
			 $("#maingrid4").ligerGrid({
		         checkbox: false,
		         columns: [
		         { display: '应用服务器名称', name: 'serverName', minwidth: 100 },
		         { display: '已占用内存最大值 (MB)', name: 'haveMaxMemory', minwidth: 100 },
		         { display: '已占用内存最小值 (MB)', name: 'haveMinMemory', minwidth: 100 },
		         { display: '已占用内存平均值 (MB)', name: 'haveAverageMemory', minwidth: 100 },
		         { display: '空闲内存最大值 (MB)', name: 'freeMaxMemory', minwidth: 100 },
		         { display: '空闲内存最小值 (MB)', name: 'freeMinMemory', minwidth: 100 },
		         { display: '空闲内存平均值 (MB)', name: 'freeAverageMemory', minwidth: 100 }
		         ], pageSize:10,
		         url:"${ctx}/statistics/listAppServerData?statType=" + statType + "&startTime=" + $("#startTime").val() + "&endTime=" + $("#endTime").val(),
		         usePager: false,
		         width: '100%',height:'300px'
		     });
		 }
	     $("#pageloading").hide(); 
	 });
	//应用服务器列表end
	}
	//查询  end
});
</script>
</html>
