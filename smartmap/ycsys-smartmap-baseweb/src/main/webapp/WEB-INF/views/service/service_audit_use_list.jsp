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
<link rel="shortcut icon" href="${res}/favicon.ico" />
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
<div>
    <!-- Content Header (Page header) -->
    <!-- <section class="content-header">
      <h1>
        使用服务审核
      </h1>
      <ol class="breadcrumb">
        <li><a href="list_fwgl.html"><i class="iconfont iconfont-bars"></i> 首页</a></li>
        <li class="active">使用服务审核</li>
      </ol>
    </section> -->

    <!-- Main content -->
    <section class="content">
      <div class="row">
        <div class="col-md-12">
          <div class="box box-solid">
            <div class="box-header with-border">
              <h4 class="box-title">服务列表</h4>
                <div class="btn_box">
                  <button class="current"><i class="glyphicon glyphicon-search"></i> 查询</button>
                  <button id="serviceApplyDelete"><i class="iconfont icon-trash"></i> 删除</button>
                </div>
            </div>
          <div class="box_l">
            <div class="list" id="maingrid4"></div>
          </div>
        </div>
      </div>
        <!-- /.col -->
    </div>
  </section>
    <!-- /.content -->
  </div>
  <!-- /.content-wrapper -->

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
<script src="${res}/plugins/knob/jquery.knob.js"></script>
<script src="${res}/plugins/slimScroll/jquery.slimscroll.js"></script>
<script src="${res}/dist/js/app.js"></script>
<script src="${res}/dist/js/demo.js"></script>
<script type="text/javascript"
	src="${res}/plugins/dialog/jquery.artDialog.source.js"></script>
<script type="text/javascript"
	src="${res}/plugins/dialog/iframeTools.source.js"></script>
<script type="text/javascript" src="${res}/plugins/dialog/unit.js"></script>
	<script type="text/javascript">
	var gridManager = null;	
	;
		(function($) { //避免全局依赖,避免第三方破坏
			$(document).ready(function() {
				
				//服务删除
				$("#serviceApplyDelete").on("click",function(e) {
					e.preventDefault();
					var selectedRows = gridManager.getSelecteds();
			    	if(selectedRows.length > 0) {
				    	var ids = [];
				    	for(var i = 0; i < selectedRows.length; i++) {
				    		ids.push(selectedRows[i].id);
				    	}
				    	var str = ids.join(",");
				    	$.Layer.confirm({
			                msg:"确定要删除选择的【" +selectedRows.length+ "】记录吗？",
			                fn:function(){
			                    $.ajax({
			                    	url: "${ctx}/serviceApply/deletes",
			                        data:{'idsStr':str},
			                        type:"post",
			                        dataType:"json",
			                        success:function(result){
			                        	alert(result.msg);
							    		gridManager.reload();
			                        },error:function(){
			                            alert("删除失败！");
			                        }
			                    });
			                },
			                fn2:function(){
			                	//gridManager.reload();
			                }
			                
			            });
			    	}
			    	else {
			    		alert("请选择数据！");
			    		return false;
			    	}
			    	
				});
				//树节点
				var menu;
				var actionNodeID;
				function itemclick(item, i) {
					alert(actionNodeID + " | " + item.text);
				}
				$(function() {
					menu = $.ligerMenu({
						top : 100,
						left : 100,
						width : 120,
						items : [ {
							text : '增加',
							click : aa,
							icon : 'add'
						}, {
							text : '修改',
							click : aa
						}, {
							line : true
						}, {
							text : '查看',
							click : itemclick
						} ]
					});

					$("#tree1").ligerTree({
						onContextmenu : function(node, e) {
							actionNodeID = node.data.text;
							menu.show({
								top : e.pageY,
								left : e.pageX
							});
							return false;
						}
					});
				});
				function aa() {
					var dialog = $.Layer.iframe({
						title : '用户注册审批',
						url : 'add_yhzc.html',
						width : 400,
						height : 400
					});
				}

				//表格列表
				$(function() {
					gridManager = $("#maingrid4").ligerGrid({
						checkbox: true,
		                columns: [
		                { display: '审批状态', name: 'auditStastus', align: 'center', width: 100,
		                	render: function (item) {
  	                    	     var obj = parseInt(item.auditStastus);
      	                    	  <c:forEach var="map" items="${auditStatus }">
      	                    	  		if(obj == "${map.key }") {
      	                    	  			return "${map.value.name }";
      	                    	  		}
  	       						  </c:forEach>
   	                     }    
		                },
		                { display: '标题', name: 'title', minWidth: 60 },
		                { display: '有效期', name: 'validDate', width: 100,align:'left',
		                	render: function (item) {
 	                    	     var obj = parseInt(item.validDate);
     	                    	  <c:forEach var="map" items="${validDate }">
     	                    	  		if(obj == "${map.key }") {
     	                    	  			return "${map.value.name }";
     	                    	  		}
 	       						  </c:forEach>
  	                     }    
		                },
		                { display: '服务', name: 'service.showName', minWidth: 100 },
		               /*  { display: '通过服务', name: 'ContactName', minWidth: 60 },
		                { display: '拒绝服务', name: 'ContactName', minWidth: 100 }, */
		                { display: '申请人', name: 'applyUser.name', minWidth: 100 },
		                { display: '申请时间', name: 'applyDate', minWidth: 100 },
		                { display: '操作', 
		                	isSort: false, render: function (rowdata, rowindex, value)
	                        {
	                          var h = "";
	                          if (!rowdata._editing)
	                          {
	                            h += "<input type='button' class='list-btn bt_edit' onclick='service_audit_list.audit("+ rowdata.id+ ")'/>";
	                            h += "<input type='button' class='list-btn bt_view' onclick='service_audit_list.view(" + rowdata.id + ")'/>";
	                          }
	                          return h;
	                        }	
		                }
		                ], 
		                pageSize:30,
		                url:"${ctx}/serviceApply/listData",
		                width: '100%',height:'95%'
					});

					$("#pageloading").hide();
				});
			});
		})(jQuery);
		
		var service_audit_list = {
				audit: function(id) {
	               if(id) {
			    		var dialog = $.Layer.iframe(
				                { 
				                  id:"auditServiceUseDialog",
				                  title: '审核服务',
				                  url:"${ctx}/serviceApply/toAudit?id="+id,
				                  width: 400,
				                  height: 350
				               });
					}
				},
				view: function(id) {
					if(id) {
			    		var dialog = $.Layer.iframe(
				                { 
				                  id:"viewServiceApplyDialog",
				                  title: '查看服务申请',
				                  url:'${ctx}/serviceApply/view?id='+id,
				                  width: 400,
				                  height: 280,
				                  button:[]
				               });
					}
				},
				
				
		}
	</script>
</body>
</html>
