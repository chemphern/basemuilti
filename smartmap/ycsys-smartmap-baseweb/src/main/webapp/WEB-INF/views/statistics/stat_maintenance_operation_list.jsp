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
      <h1>运维系统操作统计</h1>
      <ol class="breadcrumb">
        <li><a href="#"><i class="iconfont iconfont-bars"></i> 首页</a></li>
        <li class="active">运维系统操作统计</li>
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
          <div class="charts" id="chart2"></div>
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
<%-- <script src="${res }/dist/js/pages/yext02.js"></script> --%>
<script>
$(document).ready(function(){
	$("#queryBtn").on("click",function(){
		query();
	});
	
	$("#queryBtn").click();
	
	function query() {
		var myChart1 = echarts.init(document.getElementById('chart'),'macarons');
	    var myChart2 = echarts.init(document.getElementById('chart2'),'macarons');
	    //var myChart3 = echarts.init(document.getElementById('chart3'),'macarons');
		
	     $.ajax({
			url:"${ctx}/statistics/getMaintenanceOperationInfo",
			method:"post",
			data:{'startTime':$("#startTime").val(),'endTime':$("#endTime").val()},
			dataType:"json",
			success:function(ret) {
				var successRate = ret.successRate;
				var xOpetAxisData = ret.xOpetAxisData;
				var opeSeriesData = ret.opeSeriesData;
				if(successRate) {
					successRate = JSON.parse(successRate);
				}
				if(xOpetAxisData) {
					xOpetAxisData = JSON.parse(xOpetAxisData);
				}
				if(opeSeriesData) {
					opeSeriesData = JSON.parse(opeSeriesData);
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
				                    data : ['usa2','USA','usa','POIG','CQTE','Maps','Web','tile','smap','CQSE']
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
				                    data:[2.0, 4.9, 7.0, 23.2, 25.6, 76.7, 135.6, 162.2, 32.6, 20.0],
				                    markPoint : {
				                        data : [
				                            {type : 'max', name: '最大值'},
				                            {type : 'min', name: '最小值'}
				                        ]
				                    },
				                    markLine : {
				                        data : [
				                            {type : 'average', name: '平均值'}
				                        ]
				                    }
				                }
				            ]
				      };

				    myChart1.setOption(option1);
				    myChart2.setOption(option2);
				    //myChart3.setOption(option3);
			},
			error: function(result) {
				alert("connection error!");		
			}
	     });
	}

});
</script>
</html>
