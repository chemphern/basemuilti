<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/views/common/taglib.jsp" %>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>羽辰智慧林业平台运维管理系统-首页</title>
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <link rel="stylesheet" href="${res}/bootstrap/css/bootstrap.css">
    <link rel="stylesheet" href="${res}/iconfont/iconfont.css">
    <link rel="stylesheet" href="${res}/dist/css/AdminLTE.css">
    <link rel="stylesheet" href="${res}/dist/css/skins/_all-skins.css">
    <style>
        html,body{
            background-color: #f1f1f1;
        }
        body{
        	overflow-y: auto;
        }
    </style>
</head>
        <!-- Content Header (Page header) -->
        <section class="content-header">
            <h1>
                首页
            </h1>

        </section>

    <!-- Main content -->
      <!-- Small boxes (Stat box) -->
      <div class="row">
        <div class="col-lg-3 col-xs-6">
          <!-- small box -->
          <div class="small-box bg-red">
            <div class="inner">
              <h3>${serviceStopCount }&nbsp;</h3>

              <p>停止服务数量</p>
            </div>
            <div class="icon">
              <i class="ion ion-bag"></i>
            </div>
            <a href="${ctx }/serviceMonitor/list" class="small-box-footer">查看详情 <i class="fa fa-arrow-circle-right"></i></a>
          </div>
        </div>
        <!-- ./col -->
        <div class="col-lg-3 col-xs-6">
          <!-- small box -->
          <div class="small-box bg-green">
            <div class="inner">
              <h3>${receiveMsgCount }&nbsp;</h3>

              <p>未读消息</p>
            </div>
            <div class="icon">
              <i class="ion ion-stats-bars"></i>
            </div>
            <a href="${ctx}/platNotice/myNotice" class="small-box-footer">查看详情 <i class="fa fa-arrow-circle-right"></i></a>
          </div>
        </div>
        <!-- ./col -->
        <div class="col-lg-3 col-xs-6">
          <!-- small box -->
          <div class="small-box bg-yellow">
            <div class="inner">
              <h3>${alarmCount }&nbsp;</h3>

              <p>监控报警</p>
            </div>
            <div class="icon">
              <i class="ion ion-person-add"></i>
            </div>
            <a href="${ctx }/exceptionAlarm/list" class="small-box-footer">查看详情 <i class="fa fa-arrow-circle-right"></i></a>
          </div>
        </div>
        <!-- ./col -->
        <div class="col-lg-3 col-xs-6">
          <!-- small box -->
          <div class="small-box bg-aqua">
            <div class="inner">
              <h3>${serviceApplyCount }&nbsp;</h3>

              <p>服务审核</p>
            </div>
            <div class="icon">
              <i class="ion ion-pie-graph"></i>
            </div>
            <a href="${ctx }/serviceApply/list" class="small-box-footer">查看详情 <i class="fa fa-arrow-circle-right"></i></a>
          </div>
        </div>
        <!-- ./col -->
      </div>
      <!-- /.row -->
      <!-- Main row -->
      <div class="row">
        <!-- Left col -->
        <section class="col-lg-5 connectedSortable">
          <!-- Custom tabs (Charts with tabs)-->
          <div class="nav-tabs-custom">
            <div class="tab-content no-padding">
              <!-- Morris chart - Sales -->
              <div class="chart tab-pane active" id="index-chart1" style="position: relative; height: 300px;"></div>
            </div>
          </div>
          <!-- /.nav-tabs-custom -->
        </section>
        <!-- /.Left col -->
        <!-- right col (We are only adding the ID to make the widgets sortable)-->
        <section class="col-lg-7 connectedSortable">
          <!-- solid sales graph -->
          <div class="nav-tabs-custom">
            <div class="tab-content no-padding">
              <!-- Morris chart - Sales -->
              <div class="chart tab-pane active" id="index-chart2" style="position: relative; height: 300px;"></div>
            </div>
          </div>
        </section>
        
         <section class="col-lg-12 connectedSortable">
          <!-- solid sales graph -->
          <div class="nav-tabs-custom">
            <div class="tab-content no-padding">
              <!-- Morris chart - Sales -->
              <div class="chart tab-pane active" id="index-chart3" style="position: relative; height: 350px;"></div>
            </div>
          </div>
        </section>
        
      </div>
      <!-- /.row (main row) -->



<script src="${res}/plugins/jQuery/jquery-2.2.3.min.js"></script>
<!-- jQuery UI 1.11.4 -->
<script src="${res}/plugins/jQueryUI/jquery-ui.min.js"></script>
<!-- Bootstrap 3.3.6 -->
<script src="${res}/bootstrap/js/bootstrap.js"></script>
<!-- AdminLTE App -->
<script src="${res}/dist/js/app.js"></script>
<!-- AdminLTE for demo purposes -->
<script src="${res}/dist/js/demo.js"></script>
<!-- echarts -->
<script src="${res }/plugins/echarts/echarts-all.js"></script>
<script>
$(document).ready(function() {
	var indexChart1 = echarts.init(document.getElementById('index-chart1'),'macarons');
	var indexChart2 = echarts.init(document.getElementById('index-chart2'),'macarons');
	var indexChart3 = echarts.init(document.getElementById('index-chart3'),'macarons');
	indexChart1.showLoading({
        text: "图表数据正在努力加载..."
    });
	indexChart2.showLoading({
        text: "图表数据正在努力加载..."
    });
	indexChart3.showLoading({
        text: "图表数据正在努力加载..."
    });
	
	//获取图表信息 start
	$.ajax({
		url:"${ctx}/statistics/getIndexChatInfo",
		method:"post",
		dataType:"json",
		success:function(ret) {
			//var serviceStatus = "";
			var xUserVisitAxisData = "";
			var userVisitSeriesData = "";
			var xVisitAxisData = "";
			var visitSeriesData = "";
			indexChart1.hideLoading();
			indexChart2.hideLoading();
			indexChart3.hideLoading();
			if(ret.serviceStatus) {
				serviceStatus = JSON.parse(ret.serviceStatus);
			}
			
			if(ret.xVisitAxisData) {
				xVisitAxisData = JSON.parse(ret.xVisitAxisData);
			}
			if(ret.visitSeriesData) {
				visitSeriesData = JSON.parse(ret.visitSeriesData);
			}
			
			if(ret.xUserVisitAxisData) {
				xUserVisitAxisData = JSON.parse(ret.xUserVisitAxisData);
			}
			
			if(ret.userVisitSeriesData) {
				userVisitSeriesData = JSON.parse(ret.userVisitSeriesData);
			}
			
	        var option1 = {
            title : {
                text: '服务运行状态统计',
                x:'center'
            },
            tooltip : {
                trigger: 'item',
                formatter: "{a} <br/>{b} : {c} ({d}%)"
            },
            legend: {
                orient : 'vertical',
                x : 'left',
                data:['服务启动','服务停止']
            },
            toolbox: {
                show : false,
                feature : {
                    mark : {show: true},
                    dataView : {show: true, readOnly: false},
                    magicType : {
                        show: true, 
                        type: ['pie', 'funnel'],
                        option: {
                            funnel: {
                                x: '25%',
                                width: '50%',
                                funnelAlign: 'left',
                                max: 1548
                            }
                        }
                    },
                    restore : {show: true},
                    saveAsImage : {show: true}
                }
            },
            calculable : true,
            series : [
                {
                    name:'服务状态',
                    type:'pie',
                    radius : '55%',
                    center: ['50%', '50%'],
                    data:serviceStatus
                }
            ]
	      };
	   
	      //option2 start
	      var option2 = {
		            title : {
		                text: '用户访问量统计TOP 5'
		            },
		            tooltip : {
		                trigger: 'axis'
		            },
		            legend: {
		                data:['访问量（次）']
		            },
		            /* toolbox: {
		                show : true,
		                feature : {
		                    mark : {show: true},
		                    dataView : {show: true, readOnly: false},
		                    magicType : {show: true, type: ['line', 'bar']},
		                    restore : {show: true},
		                    saveAsImage : {show: true}
		                }
		            }, */
		           calculable : true,
		            xAxis : [
		                {
		                    type : 'category',
		                    data:xUserVisitAxisData
		                }
		            ],
		            yAxis : [
		                {
		                    type : 'value',
		                    splitNumber:4
		                }
		            ],
		            series : [
		                {
		                    name:'访问量（次）',
		                    type:'bar',
		                    data:userVisitSeriesData,
		                    markPoint : {
		                        data : [
		                            {type : 'max', name: '最大值'},
		                            {type : 'min', name: '最小值'}
		                        ]
		                    }
		                }
		            ]
			      };
			//option2 end
			
	      	   var option3 = {
		            title : {
		                text: '服务访问量统计TOP 10'
		            },
		            tooltip : {
		                trigger: 'axis'
		            },
		            legend: {
		                data:['访问量（次）']
		            },
		            /* toolbox: {
		                show : true,
		                feature : {
		                    mark : {show: true},
		                    dataView : {show: true, readOnly: false},
		                    magicType : {show: true, type: ['line', 'bar']},
		                    restore : {show: true},
		                    saveAsImage : {show: true}
		                }
		            }, */
		           calculable : true,
		            xAxis : [
		                {
		                    type : 'category',
		                    data:xVisitAxisData
		                }
		            ],
		            yAxis : [
		                {
		                    type : 'value',
		                    splitNumber:4
		                }
		            ],
		            series : [
		                {
		                    name:'访问量（次）',
		                    type:'bar',
		                    data:visitSeriesData,
		                    markPoint : {
		                        data : [
		                            {type : 'max', name: '最大值'},
		                            {type : 'min', name: '最小值'}
		                        ]
		                    }
		                }
		            ]
			      };
	      
			if(serviceStatus == "") {
				option1.series=[''];
			}
			if(xVisitAxisData == ""){
				option2.series=[''];
			}
			if(xUserVisitAxisData == "") {
				option3.series=[''];
			}
			indexChart1.setOption(option1);
			indexChart2.setOption(option2);
			indexChart3.setOption(option3);
		},
		error: function(result) {
			//alert("connection error!");
		}
	});
	//获取图表信息 end
	
	
	//获取停止服务 start
	/* $.ajax({
		url:"${ctx}/getStopServiceCount",
		method:"post",
		dataType:"json",
		success:function(ret) {
			$("#serviceStopCountId").html(ret.serviceStopCount);
		},
		error: function(result) {
			//alert("connection error!");
		}
	}); */
	//获取停止服务 end
	
});


</script>
</body>
</html>