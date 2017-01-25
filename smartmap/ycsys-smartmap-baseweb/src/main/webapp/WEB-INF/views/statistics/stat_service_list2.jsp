<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false"%>
<%@include file="/WEB-INF/views/common/taglib.jsp"%>
<!DOCTYPE HTML>
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
html,body {
	background-color: #f1f1f1
}

body {
	overflow-y: auto;
}
.tabbox ul li {
	margin: 0;
	padding: 0;
	font: 12px normal "宋体", Arial, Helvetica, sans-serif;
	list-style: none;
}

.tabbox a {
	text-decoration: none;
	color: #000;
	font-size: 14px;
}

.tabbox {
	width: 100%;
	overflow: hidden;
	margin: 0 auto;
}

.tabbox .tab_conbox {
	border: 1px solid #999;
	border-top: none;
}

.tabbox .tab_con {
	display: none;
}

.tabbox .tabs {
	height: 39px;
	border-bottom: 1px solid #999;
	width: 100%;
}

.tabbox .tabs li {
	height: 38px;
	line-height: 38px;
	float: left;
	border-left: none;
	border: 1px solid #dcdcdc;
	margin-bottom: -1px;
	background: #f1f1f1;
	overflow: hidden;
	position: relative;
}

.tabbox .tabs li a {
	display: block;
	padding: 0 20px;
	outline: none;
}

.tabbox .tabs li a:hover {
	background: #27bf8c;
	border: none;
	color:#000;
}

.tabbox .tabs .thistab,.tabs .thistab a:hover {
	background: #27bf8c;
}

.tabbox .tab_con {
	padding: 12px;
	font-size: 14px;
	line-height: 175%;
}
</style>
</head>
<body>
	<div>
    <!-- Content Header (Page header) -->
    <section class="content-header">
      <h1>服务统计</h1>
    </section>

   		<div class="row">
   		  <div class="box box-solid">
        	<div class="col-md-12">
        		<div class="btn_box" style="float: left;margin:5px 0 20px 10px;"> 
		            	时间：<input name="startTime" id="startTime" type="text" value="${curDate }" class="text date_plug" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})"> 
		            	至 <input name="endTime" id="endTime" type="text" class="text date_plug" value="${curDateTo }" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})">
		            <button class="current" id="queryBtn"><i class="glyphicon glyphicon-search"></i>查询</button>
		         </div>
        		<div id="tabbox" class="tabbox">
					<ul class="tabs" id="tabs">
						<li><a href="#">服务访问量统计</a></li>
						<li><a href="#">用户访问量统计</a></li>
						<li><a href="#">IP流量统计</a></li>
						<li><a href="#">运行状态统计</a></li>
						<li><a href="#">平均响应时间统计</a></li>
					</ul>
					<ul class="tab_conbox" id="tab_conbox">
						<li class="tab_con">
							<div class="charts" id="chart1"></div>
							<div id="maingrid1"></div>
						</li>
						<li class="tab_con">
							<div class="charts" id="chart2"></div>
							<div id="maingrid2"></div>
						</li>
						<li class="tab_con">
							<div class="charts" id="chart3"></div>
							<div id="maingrid3"></div>
						</li>
						<li class="tab_con">
							<div class="charts" id="chart4"></div>
							<div id="maingrid4"></div>
						</li>
						<li class="tab_con">
							<div class="charts" id="chart5"></div>
							<div id="maingrid5"></div>
						</li>
					</ul>
				</div>
			</div>
		</div>
	</div>
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
<%-- <script src="${res }/dist/js/pages/yext02.js"></script> --%>
<script>
$(document).ready(function() {
	//选项卡
	jQuery.jqtab = function(tabtit, tab_conbox, event) {
		$(tab_conbox).find("li").hide();
		$(tabtit).find("li:first").addClass("thistab").show();
		$(tab_conbox).find("li:first").show();

		$(tabtit).find("li").bind(event,
			function() {
				$(this).addClass("thistab").siblings("li")
						.removeClass("thistab");
				var activeindex = $(tabtit).find("li").index(
						this);
				$(tab_conbox).children().eq(activeindex).show()
						.siblings().hide();
				return false;
			});

	};
	/*调用方法如下：*/
	$.jqtab("#tabs", "#tab_conbox", "click");
	//$.jqtab("#tabs2", "#tab_conbox", "mouseenter");

});

$(document).ready(function(){
	$("#queryBtn").on("click",function(){
		query();
	});
	
	$("#queryBtn").click();
	
	//query start
	function query() {
		$("#chart2").css("width", $("#tab_conbox").width()-20);
		$("#chart3").css("width", $("#tab_conbox").width()-20);
		$("#chart4").css("width", $("#tab_conbox").width()-20);
		$("#chart5").css("width", $("#tab_conbox").width()-20);
	    var chart1 = echarts.init(document.getElementById('chart1'),'macarons');
	    var chart2 = echarts.init(document.getElementById('chart2'),'macarons');
	    var chart3 = echarts.init(document.getElementById('chart3'),'macarons');
	    var chart4 = echarts.init(document.getElementById('chart4'),'macarons');
	    var chart5 = echarts.init(document.getElementById('chart5'),'macarons');
	    chart1.showLoading({
	        text: "图表数据正在努力加载..."
	    });
	    chart2.showLoading({
	        text: "图表数据正在努力加载..."
	    });
	    chart3.showLoading({
	        text: "图表数据正在努力加载..."
	    });
	    chart4.showLoading({
	        text: "图表数据正在努力加载..."
	    });
	    chart5.showLoading({
	        text: "图表数据正在努力加载..."
	    });
	 	//获取服务访问信息 start
	    $.ajax({
			url:"${ctx}/statistics/getServiceVisitInfo",
			method:"post",
			data:{'startTime':$("#startTime").val(),'endTime':$("#endTime").val()},
			dataType:"json",
			success:function(ret) {
				chart1.hideLoading();
				var xVisitAxisData = "";
				var visitSeriesData = "";
				if(ret.xVisitAxisData) {
					xVisitAxisData = JSON.parse(ret.xVisitAxisData);
				}
				if(ret.visitSeriesData) {
					visitSeriesData = JSON.parse(ret.visitSeriesData);
				}
				
				//option1 start
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
				//option1 end
				if(xVisitAxisData == ""){
					option1.series = [''];
				}
				chart1.setOption(option1);
			},
			error:function(ret){
				alert("Connecton error!");
			}
	    });
	   //获取服务访问信息 end
	  
	    //获取信息 start
	    $.ajax({
			url:"${ctx}/statistics/getServiceInfo",
			method:"post",
			data:{'startTime':$("#startTime").val(),'endTime':$("#endTime").val()},
			dataType:"json",
			success:function(ret) {
				chart2.hideLoading();
				chart3.hideLoading();
				chart4.hideLoading();
				chart5.hideLoading();
				/* var xVisitAxisData = "";
				var visitSeriesData = ""; */
				var xUserVisitAxisData = "";
				var userVisitSeriesData = "";
				var xIpVisitAxisData = "";
				var ipVisitSeriesData = "";
				var xResTimeData = "";
				var resTimeSeriesData = "";
				var serviceStatus = "";
				/* if(ret.xVisitAxisData) {
					xVisitAxisData = JSON.parse(ret.xVisitAxisData);
				}
				if(ret.visitSeriesData) {
					visitSeriesData = JSON.parse(ret.visitSeriesData);
				} */
				if(ret.xUserVisitAxisData) {
					xUserVisitAxisData = JSON.parse(ret.xUserVisitAxisData);
				}
				if(ret.userVisitSeriesData) {
					userVisitSeriesData = JSON.parse(ret.userVisitSeriesData);
				}
				if(ret.xIpVisitAxisData) {
					xIpVisitAxisData = JSON.parse(ret.xIpVisitAxisData);
				}
				if(ret.ipVisitSeriesData) {
					ipVisitSeriesData = JSON.parse(ret.ipVisitSeriesData);
				}
				if(ret.xResTimeData) {
					xResTimeData = JSON.parse(ret.xResTimeData);
				}
				if(ret.resTimeSeriesData) {
					resTimeSeriesData = JSON.parse(ret.resTimeSeriesData);
				}
				if(ret.serviceStatus) {
					serviceStatus = JSON.parse(ret.serviceStatus);
				}
				//option1 start
				/* var option1 = {
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
				      }; */
				//option1 end
				
				//option2 start
				var option2 = {
			            title : {
			                text: '用户访问量统计TOP10'
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
				
				//option3 start
				var option3 = {
			            title : {
			                text: 'IP流量统计TOP10'
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
			                    data:xIpVisitAxisData
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
			                    data:ipVisitSeriesData,
			                    markPoint : {
			                        data : [
			                            {type : 'max', name: '最大值'},
			                            {type : 'min', name: '最小值'}
			                        ]
			                    }
			                }
			            ]
				      };
				//option3 end
				
				//option5 start
				var option5 = {
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
				//option5 end
				
				//option4 start
				   var option4 = {
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
		                                x: '55%',
		                                width: '60%',
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
				//option4 end
				
				/* if(xVisitAxisData == ""){
					option1.series = [''];
				} */
				if(xUserVisitAxisData == "") {
					option2.series = [''];
				}
				if(xIpVisitAxisData == "") {
					option3.series = [''];
				}
				if(serviceStatus == ""){
					option4.series=[''];				
				}
				if(xResTimeData == "") {
					option5.series = [''];
				}
				//chart1.setOption(option1);
				chart2.setOption(option2);
				chart3.setOption(option3);
				chart4.setOption(option4);
				chart5.setOption(option5);
			},
			error: function(result) {
				alert("connection error!");
			}
	    });
	   //获取信息 end
	   
	   //服务访问列表start
	     $(function () {
	 	   $("#maingrid1").ligerGrid({
	 	         checkbox: false,
	 	         columns: [
	 	         { display: '服务名', name: 'name', minwidth: 90 },
	 	         { display: '服务访问量（单位：次）', name: 'operateCount', minwidth: 90 }
	 	         ], pageSize:5,
	 	         url:"${ctx}/statistics/listServiceData?type=1&startTime=" + $("#startTime").val() + "&endTime=" + $("#endTime").val(),
	 	         usePager: false,
	 	         width: '100%',
	 	         height:'300px'
	 	     });
	       $("#pageloading").hide(); 
	 	 }); 
	 	//服务访问列表end
	 	
	 	//用户访问列表start
	     $(function () {
	 	   $("#maingrid2").ligerGrid({
	 	         checkbox: false,
	 	         columns: [
	 	         { display: '服务名', name: 'name', minwidth: 90 },
	 	         { display: '服务访问量（单位：次）', name: 'operateCount', minwidth: 90 }
	 	         ], pageSize:5,
	 	         url:"${ctx}/statistics/listServiceData?type=2&startTime=" + $("#startTime").val() + "&endTime=" + $("#endTime").val(),
	 	         usePager: false,
	 	         width: $("#tab_conbox").width()-25,
	 	         height:'300px'
	 	     });
	       $("#pageloading").hide(); 
	 	 }); 
	 	//用户访问列表end
	 	
	 	//IP列表start
	     $(function () {
	 	   $("#maingrid3").ligerGrid({
	 	         checkbox: false,
	 	         columns: [
	 	         { display: 'IP地址', name: 'name', minwidth: 90 },
	 	         { display: '服务访问量（单位：次）', name: 'operateCount', minwidth: 90 }
	 	         ], pageSize:5,
	 	         url:"${ctx}/statistics/listServiceData?type=3&startTime=" + $("#startTime").val() + "&endTime=" + $("#endTime").val(),
	 	         usePager: false,
	 	         width: $("#tab_conbox").width()-25,
	 	         height:'300px'
	 	     });
	       $("#pageloading").hide(); 
	 	 }); 
	 	//IP列表end
	 	
	 	// 服务状态列表start
	     $(function () {
	 	   $("#maingrid4").ligerGrid({
	 	         checkbox: false,
	 	         columns: [
	 	         { display: '状态', name: 'name', minwidth: 90 },
	 	         { display: '数量（单位：个）', name: 'operateCount', minwidth: 90 }
	 	         ], pageSize:5,
	 	         url:"${ctx}/statistics/listServiceData?type=4&startTime=" + $("#startTime").val() + "&endTime=" + $("#endTime").val(),
	 	         usePager: false,
	 	         width: $("#tab_conbox").width()-25,
	 	         height:'300px'
	 	     });
	       $("#pageloading").hide(); 
	 	 }); 
	 	//服务状态列表 end
			
	}
	//query end
});
</script>
</html>
