<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/views/common/taglib.jsp" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>羽辰智慧林业综合管理平台-系统配置</title>
  <!-- Tell the browser to be responsive to screen width -->
  <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
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
  <link href="${res}/plugins/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
  <!-- 弹出框 -->
  <link href="${res}/plugins/dialog/dialog.css" rel="stylesheet" type="text/css">
  <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
  <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
  <!--[if lt IE 9]>
  <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
  <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
  <![endif]-->
    <style>
        html,body{
            background-color: #f1f1f1;
        }
        body{
            overflow-y: hidden;
        }
        </style>
</head>
<body>
        <!-- Content Header (Page header) -->
        <section class="content-header">
            <h1>
                异常报警配置
            </h1>
            <ol class="breadcrumb">
                <li><a href="#"><i class="fa fa-dashboard"></i> 系统首页</a></li>
                <li class="active">异常报警配置</li>
            </ol>
        </section>
		<section class="content">
<div class="row">
    <div class="col-md-3">
        <div class="box box-solid">
            <div class="box-header with-border">
                <h4 class="box-title">异常报警规则</h4>
            </div>
            <div class="box_l">
                <ul id="tree1">
                
                </ul>
            </div>
            <!-- /.box-body -->
        </div>
    </div>
    <!-- /.col -->
    <div class="col-md-9">
        <div class="box box-solid">
            <div class="box-header with-border">
                <h4 class="box-title">异常报警规则列表</h4>
                <div class="btn_box">
                    <button class="current" onclick="addConfigExceptionAlarm('1');"><i class="iconfont icon-plus"></i>增加</button>
                	<button onclick="addConfigExceptionAlarm('2');"><i class="iconfont icon-edit"></i>编辑</button>
                	<button onclick="deleteConfigExceptionAlarm();"><i class="iconfont icon-trash"></i>删除</button>
                	<button class="cog" onclick="addEmailServerConfig();"><i class="glyphicon glyphicon-cog"></i>邮件服务器参数设置</button>
                    <button class="cog" onclick="addSMSConfig();"><i class="glyphicon glyphicon-cog"></i>SMS发送设置</button>
                </div>
            </div>
            <div class="box_l">
                <div class="list" id="maingrid4"></div>
            </div>
        </div>
        <!-- /.col -->
    </div>
</div>
        </section>
</body>

<!-- jQuery 2.2.3 -->
<script src="${res}/plugins/jQuery/jquery-2.2.3.min.js"></script>
<!--grid-->
<script src="${res}/plugins/ligerUI/js/core/base.js" type="text/javascript"></script>
<script src="${res}/plugins/ligerUI/js/plugins/ligerGrid.js" type="text/javascript"></script>  
<script src="${res}/plugins/ligerUI/js/plugins/ligerDrag.js" type="text/javascript"></script>  
<script src="${res}/plugins/ligerUI/js/plugins/ligerMenu.js" type="text/javascript"></script>  
<script src="${res}/plugins/ligerUI/js/plugins/ligerTree.js" type="text/javascript"></script>
<script src="${res}/plugins/ligerUI/js/ligerui.all.js" type="text/javascript"></script>   
<script src="${res}/plugins/ligerUI/js/plugins/CustomersData.js" type="text/javascript"></script>  
<!-- Bootstrap 3.3.6 -->
<script src="${res}/bootstrap/js/bootstrap.min.js"></script>
<!-- jQuery Knob Chart -->
<!-- Slimscroll 滚动条 -->
<!-- AdminLTE App -->
<script src="${res}/dist/js/app.js"></script>
<!-- AdminLTE for demo purposes -->
<script src="${res}/dist/js/demo.js"></script>
<!-- 封装弹出框dialog -->
<script type="text/javascript" src="${res}/plugins/dialog/jquery.artDialog.source.js"></script>
<script type="text/javascript" src="${res}/plugins/dialog/iframeTools.source.js"></script>
<script type="text/javascript" src="${res}/plugins/dialog/unit.js"></script>
<script>
	var treeManager = null;
	var gridManager = null;
	var ruleTypeName = null;
	;(function($){//避免全局依赖,避免第三方破坏
		$(document).ready(function() {
			//树 start
			//树节点
			var menu;
			var actionNodeID;
			$("#tree1").ligerTree(
	            {
	                url: "${ctx}/configExceptionAlarm/listRuleType",
                    nodeWidth : 140,
                    idFieldName :'id',
                    parentIDFieldName :'pid',
                    //textFieldName :'name',
                    onSelect : onSelectRuleType,
                    onContextmenu : function(node, e) {
						actionNodeID = node.data.text;
						/* menu.show({
							top : e.pageY,
							left : e.pageX
						}); */
						return false;
					}
	             });
		
	    	function onSelectRuleType(obj) {
				var ruleType = "";
				if(obj.data.text != '异常报警规则') {
					//alert(obj.data.id);
					ruleType = obj.data.id
				}
				gridManager.setParm("ruleType",ruleType);
	        	window.gridManager.reload();
	        }
	    	//树 end
	    	
	    	//表格列表 start
       gridManager = $("#maingrid4").ligerGrid({
            checkbox: true,
            columns: [
	          	        { display: '异常分类',  name: 'ruleType', align: 'left', width: 100,
	          	        	render: function (item) {
 	                    	     var obj = parseInt(item.ruleType);
     	                    	  <c:forEach var="map" items="${ruleType }">
     	                    	  		if(obj == "${map.key }") {
     	                    	  			return "${map.value.name }";
     	                    	  		}
 	       						  </c:forEach>
  	                     }      
	          	        },
	          	        { display: '用户名称', name: 'user.name', align: 'left', width: 100 },
	          	        { display: '用户等级', name: 'userGrade', minWidth: 60,
	          	        	userGrade:'int',
	   	                    render: function (item) {
	   	                    	     var obj = parseInt(item.userGrade);
	   	                             if (obj == 0) {
	   	                            	 return '第一级';
	   	                             }
	   	                             else if(obj == 1) {
	   	                            	 return '第二级';
	   	                             }
	   	                         	 else if(obj == 2) {
	   	                            	 return '第三级';
	   	                             }
		    	                 }
	          	        },
	          	      	{ display: '时间间隔（小时）', name: 'intervalTime', minWidth: 60 },
	          	      	{ display: '发送方式', name: 'sendWay', minWidth: 60,
	          	      		sendWay:'int',
	   	                    render: function (item) {
	   	                    	     var obj = parseInt(item.sendWay);
	   	                             if (obj == 0) {
	   	                            	 return '电子邮件';
	   	                             }
	   	                             else if(obj == 1) {
	   	                            	 return '手机短信';
	   	                             }
		    	                 }
	          	      	},
	          	      	{ display: '邮件地址', name: 'mailAddress', minWidth: 60 },
	          	    	{ display: '电话号码', name: 'phone', minWidth: 60 },
	          			{ display: '操作', 
	          	    		isSort: false, render: function (rowdata, rowindex, value)
	                        {
	                          var h = "";
	                          if (!rowdata._editing)
	                          {
	                        	  h += "<input type='button' class='list-btn bt_edit' onclick='addConfigExceptionAlarm(2,"+ rowdata.id+ ")'/>";
		                          h += "<input type='button' class='list-btn bt_del' onclick='deleteConfigExceptionAlarm(" + rowdata.id + ")'/>";
	                          }
	                          return h;
	                        }
	          			}
          	        ], 
          	pageSize:30,
            url:"${ctx}/configExceptionAlarm/listData",
            width: '100%',height:'96%'
        });
    	//表格列表 end
		});
    	
    })(jQuery);	

	//邮件服务器参数配置窗口
	function addEmailServerConfig() {
		var dialog = $.Layer.iframe(
                { 
                  id:"addEmailServerConfigDialog",
                  title: '邮件服务器参数配置',
                  url:"${ctx}/sendEmail/toEdit",
                  width: 400,
                  height: 200
               });
	}
  
	//SMS发送设置
	function addSMSConfig() {
		var dialog = $.Layer.iframe(
                { 
                  id:"sendSMSDialog",
                  title: 'SMS发送设置',
                  url:"${ctx}/sendEmail/toSMS",
                  width: 400,
                  height: 150
               });
	}
  
  //增加或是修改异常报警配置
	function addConfigExceptionAlarm(flag,rowId) {
		var id = "";
	    if(flag=='2') {
	    	if(rowId) {
	    		id = rowId;
	    	}
	    	else {
		    	var selectedRows = gridManager.getSelecteds();
		    	if(selectedRows.length > 1 || selectedRows.length < 1) {
		    		//alert("请选择一条记录进行修改！");
		    		$.Layer.confirm({
		                msg:"请选择一条记录进行修改！",
		            });
		    		return false;
		    	}
		    	else {
		    		id = selectedRows[0].id;
		    	}
	    	}
	    }
		var dialog = $.Layer.iframe(
                { 
                  id:"addConfigExceptionAlarmDialog",
                  title: flag =='1'?'增加报警规则':'编辑报警规则',
                  url:'${ctx}/configExceptionAlarm/toEdit?flag='+flag+"&id="+id,
                  width: 400,
                  height: 350
               });
	}
	//删除服务引擎配置
  function deleteConfigExceptionAlarm(id) {
    	if(id) {
    		$.Layer.confirm({
                msg:"确定删除该条记录吗？",
                fn:function(){
                    $.ajax({
                    	url: "${ctx}/configExceptionAlarm/delete",
                        data:{id:id},
                        type:"post",
                        dataType:"json",
                        success:function(res){
                        	if(res.retMsg=='删除成功'){
                        		$.Layer.confirm({
                	                msg:"删除成功",
                	                fn:function(){
                	                 gridManager.reload();
                	                }
                	            });
                        	}
                        },error:function(){
                            alert("删除记录失败！");
                        }
                    });
                },
            });
    	}else{ 
    		var selectedRows = gridManager.getSelecteds();
        	if(selectedRows.length > 0) {
    	    	var ids = [];
    	    	for(var i = 0; i < selectedRows.length; i++) {
    	    		ids.push(selectedRows[i].id);
    	    	}
    	    	var str = ids.join(",");
    	    	$.Layer.confirm({
                    msg:"确定删除该条记录吗？",
                    fn:function(){
                        $.ajax({
                        	url: "${ctx}/configExceptionAlarm/deletes",
                            data:{idsStr:str},
                            type:"post",
                            dataType:"json",
                            success:function(res){
                            	if(res.retMsg=='删除成功'){
                            		$.Layer.confirm({
                    	                msg:"删除成功",
                    	                fn:function(){
                    	                 gridManager.reload();
                    	                }
                    	            });
                            	}
                            }
                        });
                    },
                });
        	} 
        	else{
        		//alert("请选择需要删除的数据！");
        		$.Layer.confirm({
	                msg:"请选择需要删除的数据！",
	                cancel:null
	            });
        	}
    	}
    } 
</script>
</html>
