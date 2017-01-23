<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false"%>
<%@include file="/WEB-INF/views/common/taglib.jsp"%>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>羽辰智慧林业平台运维管理系统-服务统计</title>
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
        	overflow-y: auto;
        }
        </style>
</head>
<body>
<div>
    <!-- Content Header (Page header) -->
    <section class="content-header">
      <h1>服务统计</h1>
    </section>
<div class="box box-solid">
	<div class="row">
        <div class="col-md-12" style="margin:0 0 20px 10px;">
          <div class="btn_box" style="float: left;margin-top:5px;"> 
           	 时间：<input type="text" id="startTime" class="text date_plug" value="${curDate }" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})"/> 
           	 至 <input type="text" id="endTime" class="text date_plug" value="${curDateTo }" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})"/>
            <button class="current" id="queryBtn"><i class="glyphicon glyphicon-search"></i>查询</button>
          </div>
        </div>
        <div class="col-md-8">
          <div class="charts" id="chart"></div>
          <div class="charts" id="chart1" style="margin-top:30px;"></div>
        </div>
        <!-- /.col -->
        <div class="col-md-4">
          <h4 style="line-height:30px">响应最慢的服务列表(TOP5)</h4>
          <div id="maingrid4"></div>
          <div class="charts" id="chart2" style="margin-top:80px;"></div>
        <!-- /.col -->
      </div>
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
<!-- echarts -->
<script src="${res }/plugins/echarts/echarts-all.js"></script>
<script src="${res }/plugins/My97DatePicker/WdatePicker.js"></script>
<%-- <script src="${res }/dist/js/pages/fwtj.js"></script> --%>
<script>
var gridManager = null;
$(document).ready(function(){
	$("#queryBtn").on("click",function(){
		query();
	});
	
	$("#queryBtn").click();
	
	function query() {
		/* if(gridManager) {
			gridManager.setParm("startTime",$("#startTime").val());
	    	gridManager.setParm("endTime",$("#endTime").val());
	    	gridManager.reload();
		} */
		
		var myChart = echarts.init(document.getElementById('chart'),'macarons');
		var myChart1 = echarts.init(document.getElementById('chart1'),'macarons');
		var myChart2 = echarts.init(document.getElementById('chart2'),'macarons');
		//获取服务响应时间、服务访问量、服务运行状态
		$.ajax({
			url:"${ctx}/statistics/getServiceInfo",
			method:"post",
			data:{'startTime':$("#startTime").val(),'endTime':$("#endTime").val()},
			dataType:"json",
			success:function(ret) {
				var xResTimeData = "";
				var resTimeSeriesData = "";
				var serviceStatus = "";
				var xVisitAxisData = "";
				var visitSeriesData = "";
				if(ret.xResTimeData) {
					xResTimeData = JSON.parse(ret.xResTimeData);
				}
				if(ret.resTimeSeriesData) {
					resTimeSeriesData = JSON.parse(ret.resTimeSeriesData);
				}
				if(ret.serviceStatus) {
					serviceStatus = JSON.parse(ret.serviceStatus);
				}
				if(ret.xVisitAxisData) {
					xVisitAxisData = JSON.parse(ret.xVisitAxisData);
				}
				if(ret.visitSeriesData) {
					visitSeriesData = JSON.parse(ret.visitSeriesData);
				}
				 
				
				var option = {
			           title : {
			               text: '平均响应时间'
			           },
			           tooltip : {
			               trigger: 'axis'
			           },
			           legend: {
			               data:['服务响应时间（ms）']
			           },
			           toolbox: {
			               show : true,
			               feature : {
			                   mark : {show: true},
			                   dataView : {show: true, readOnly: false},
			                   magicType : {show: true, type: ['line', 'bar']},
			                   restore : {show: true},
			                   saveAsImage : {show: true}
			               }
			           },
			           calculable : true,
			           xAxis : [
			               {
			                   type : 'category',
			                   boundaryGap : false,
			                   data:xResTimeData
			                   //data : ['周一','周二','周三','周四','周五','周六','周日']
			               }
			           ],
			           yAxis : [
			               {
			                   type : 'value',
			                   axisLabel : {
			                       formatter: '{value} ms'
			                   },
			                   splitNumber:4
			               }
			           ],
			           series : [
			               {
			                   name:'服务响应时间（ms）',
			                   type:'line',
			                   data:resTimeSeriesData,
			                   //data:[11, 11, 15, 13, 12, 13, 10],
			                   markPoint : {
			                       data : [
			                           {type : 'max', name: '最大值'},
			                           {type : 'min', name: '最小值'}
			                       ]
			                   }
			                   /* markLine : {
			                       data : [
			                           {type : 'average', name: '平均值'}
			                       ]
			                   } */
			               }
			           ]
				     };
				
				var option1 = {
		            title : {
		                text: '服务访问量统计TOP10'
		            },
		            tooltip : {
		                trigger: 'axis'
		            },
		            legend: {
		                data:['访问量（次）']
		            },
		            toolbox: {
		                show : true,
		                feature : {
		                    mark : {show: true},
		                    dataView : {show: true, readOnly: false},
		                    magicType : {show: true, type: ['line', 'bar']},
		                    restore : {show: true},
		                    saveAsImage : {show: true}
		                }
		            },
		           calculable : true,
		            xAxis : [
		                {
		                    type : 'category',
		                    data:xVisitAxisData
		                    //data : ['usa2','USA','usa','POIG','CQTE','Maps','Web','tile','smap','CQSE']
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
		                    //data:[2.0, 4.9, 7.0, 23.2, 25.6, 76.7, 135.6, 162.2, 32.6, 20.0],
		                    markPoint : {
		                        data : [
		                            {type : 'max', name: '最大值'},
		                            {type : 'min', name: '最小值'}
		                        ]
		                    }
		                   /*  markLine : {
		                        data : [
		                            {type : 'average', name: '平均值'}
		                        ]
		                    } */
		                }
		            ]
			      };
			      var option2 = {
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
				if(xResTimeData == "") {
					option.series=[''];
				}
				
				if(xVisitAxisData == ""){
					option1.series=[''];
				}
				if(serviceStatus == ""){
					option2.series=[''];				
				}
				myChart.setOption(option);
				myChart1.setOption(option1);
				myChart2.setOption(option2);
			},
			error: function(result) {
				alert("connection error!");
			}
		});
	}
	
	//grid start
      $(function () {
    	  gridManager = $("#maingrid4").ligerGrid({
            checkbox: false,
            columns: [
            { display: '服务名称', name: 'service.showName', minwidth: 100 },
            { display: '响应时间(ms)', name: 'averageResponseTime', minWidth: 100 }
            ], pageSize:5,
            url:"${ctx}/statistics/getServiceSlowTop5?startTime=" + $("#startTime").val() + "&endTime=" + $("#endTime").val(),
            usePager: false,
            width: '100%',height:'230px'
        });
        $("#pageloading").hide(); 
    });
    //grid end

});
</script>
</html>
