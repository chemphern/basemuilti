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
<style>
html,body {
	background-color: #f1f1f1
}

body {
	overflow-y: auto;
}

body,ul,li {
	margin: 0;
	padding: 0;
	font: 12px normal "宋体", Arial, Helvetica, sans-serif;
	list-style: none;
}

a {
	text-decoration: none;
	color: #000;
	font-size: 14px;
}

#tabbox {
	width: 100%;
	overflow: hidden;
	margin: 0 10px;
}

.tab_conbox {
	border: 1px solid #999;
	border-top: none;
}

.tab_con {
	display: none;
}

.tabs {
	height: 32px;
	border-bottom: 1px solid #999;
	border-left: 1px solid #999;
	width: 100%;
}

.tabs li {
	height: 31px;
	line-height: 31px;
	float: left;
	border: 1px solid #999;
	border-left: none;
	margin-bottom: -1px;
	background: #e0e0e0;
	overflow: hidden;
	position: relative;
}

.tabs li a {
	display: block;
	padding: 0 20px;
	border: 1px solid #fff;
	outline: none;
}

.tabs li a:hover {
	background: #ccc;
}

.tabs .thistab,.tabs .thistab a:hover {
	background: #fff;
	border-bottom: 1px solid #fff;
}

.tab_con {
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
      <h1>运维系统操作统计</h1>
    </section>

   		<div class="row">
   		  <div class="box box-solid">
        	<div class="col-md-12">
        		<div class="btn_box" style="float: left;margin:5px 0 20px 10px;"> 
		            	时间：<input name="startTime" id="startTime" type="text" value="${curDate }" class="text date_plug" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})"> 
		            	至 <input name="endTime" id="endTime" type="text" class="text date_plug" value="${curDateTo }" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})">
		            <button class="current" id="queryBtn"><i class="glyphicon glyphicon-search"></i>查询</button>
		         </div>
        		<div id="tabbox">
					<ul class="tabs" id="tabs">
						<li><a href="#">用户操作统计</a></li>
						<li><a href="#">操作类别统计</a></li>
					</ul>
					<ul class="tab_conbox" id="tab_conbox">
						<li class="tab_con">
							<div class="charts" id="chart2"></div>
						</li>
						<li class="tab_con">
							<div class="charts" id="chart3"></div>
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
		//$(tab_conbox).find("li").eq(1).find("#chart3").width($(tab_conbox).width()-20)
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
	
	function query() {
		//var myChart1 = echarts.init(document.getElementById('chart'),'macarons');
		//$("#chart3").css("width", $("#chart2").width());
		$("#chart2").css("width", $("#tab_conbox").width()-20);
		$("#chart3").css("width", $("#tab_conbox").width()-20);
	    var myChart2 = echarts.init(document.getElementById('chart2'),'macarons');
	    var myChart3 = echarts.init(document.getElementById('chart3'),'macarons');
		
	     $.ajax({
			url:"${ctx}/statistics/getMaintenanceOperationInfo",
			method:"post",
			data:{'startTime':$("#startTime").val(),'endTime':$("#endTime").val()},
			dataType:"json",
			success:function(ret) {
				var successRate = "";
				var xOpetAxisData = "";
				var opeSeriesData = "";
				var xOpetTypeAxisData = "";
				var opeTypeSeriesData = "";
				if(ret.successRate) {
					successRate = JSON.parse(ret.successRate);
				}
				if(ret.xOpetAxisData) {
					xOpetAxisData = JSON.parse(ret.xOpetAxisData);
				}
				if(ret.opeSeriesData) {
					opeSeriesData = JSON.parse(ret.opeSeriesData);
				}
				if(ret.xOpetTypeAxisData) {
					xOpetTypeAxisData = JSON.parse(ret.xOpetTypeAxisData);
				}
				if(ret.opeTypeSeriesData) {
					opeTypeSeriesData = JSON.parse(ret.opeTypeSeriesData);
				}
				var option1 = {
				          title : {
				              text: '成功率统计',
				              x:'center'
				          },
				          tooltip : {
				              trigger: 'item',
				              formatter: "{a} <br/>{b} : {c} ({d}%)"
				          },
				          legend: {
				              orient : 'vertical',
				              x : 'left',
				              data:['正常','错误']
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
				                  name:'成功率统计',
				                  type:'pie',
				                  radius : '50%',
				                  center: ['50%', '60%'],
				                  data:successRate
				                  
				              }
				          ]
				    };
				    var option2 = {
				          title : {
				              text: '用户操作统计'
				          },
				          tooltip : {
				              trigger: 'axis'
				          },
				          legend: {
				              data:['统计次数（次）']
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
				                  data : xOpetAxisData
				                  //data : ['usa2','USA','usa','POIG','CQTE','Maps','Web','tile','smap','CQSE']
				              }
				          ],
				          yAxis : [
				              {
				                  type : 'value'
				              }
				          ],
				          series : [
				              {
				                  name:'统计次数（次）',
				                  type:'bar',
				                  data:opeSeriesData,
				                  //data:[2.0, 4.9, 7.0, 23.2, 25.6, 76.7, 135.6, 162.2, 32.6, 20.0],
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
				    
				    var option3 = {
					          title : {
					              text: '操作类别统计'
					          },
					          tooltip : {
					              trigger: 'axis'
					          },
					          legend: {
					              data:['统计次数（次）']
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
					                  data : xOpetTypeAxisData
					                  //data : ['usa2','USA','usa','POIG','CQTE','Maps','Web','tile','smap','CQSE']
					              }
					          ],
					          yAxis : [
					              {
					                  type : 'value'
					              }
					          ],
					          series : [
					              {
					                  name:'统计次数（次）',
					                  type:'bar',
					                  data:opeTypeSeriesData,
					                  //data:[2.0, 4.9, 7.0, 23.2, 25.6, 76.7, 135.6, 162.2, 32.6, 20.0],
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
					
				    if(opeSeriesData == "") {
				    	option2.series=[''];
				    }
				    if(opeTypeSeriesData == "") {
				    	option3.series=[''];
				    }
				    
				    
				    //myChart1.setOption(option1);
				    myChart2.setOption(option2);
				    myChart3.setOption(option3);
			},
			error: function(result) {
				alert("connection error!");		
			}
	     });
	}
	
});
</script>
</html>
