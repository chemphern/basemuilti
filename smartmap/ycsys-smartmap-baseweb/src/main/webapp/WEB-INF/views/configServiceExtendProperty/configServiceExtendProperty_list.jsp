<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/views/common/taglib.jsp" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>羽辰智慧林业综合管理平台-资源管理</title>
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
                服务拓展属性配置
            </h1>
        </section>

<div class="row">
    <div class="col-md-12">
        <div class="box box-solid">
            <div class="box-header with-border">
                <h4 class="box-title">服务扩展列表</h4>
                <div class="btn_box">
                	<shiro:hasPermission name="sys-serviceExtendProperty-create">
                    	<button class="current" onclick="addConfigServiceExtendProperty('1');"><i class="iconfont icon-plus"></i>创建</button>
                   	</shiro:hasPermission>
                	<shiro:hasPermission name="sys-serviceExtendProperty-edit">
                		<button onclick="addConfigServiceExtendProperty('2');"><i class="iconfont icon-edit"></i>编辑</button>
                	</shiro:hasPermission>
                	<shiro:hasPermission name="sys-serviceExtendProperty-delete">
                		<button onclick="deleteConfigServiceExtend();"><i class="iconfont icon-trash"></i>删除</button>
                	</shiro:hasPermission>
                </div>
            </div>
            <div class="box_l">
                <div class="list" id="maingrid4"></div>
            </div>
        </div>
        <!-- /.col -->
    </div>
    </div>

</body>

<!-- jQuery 2.2.3 -->
<script src="${res}/plugins/jQuery/jquery-2.2.3.min.js"></script>
<!--grid-->
<script src="${res}/plugins/ligerUI/js/core/base.js" type="text/javascript"></script>
<script src="${res}/plugins/ligerUI/js/plugins/ligerGrid.js" type="text/javascript"></script>  
<script src="${res}/plugins/ligerUI/js/plugins/ligerDrag.js" type="text/javascript"></script>  
<script src="${res}/plugins/ligerUI/js/plugins/ligerMenu.js" type="text/javascript"></script>  
<script src="${res}/plugins/ligerUI/js/plugins/ligerTree.js" type="text/javascript"></script>  
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
	 ;(function($){  //避免全局依赖,避免第三方破坏
    	//表格列表 start
       gridManager = $("#maingrid4").ligerGrid({
            checkbox: true,
            columns: [
	          	        { display: '属性名字段',  name: 'name', minWidth: 100 },
	          	        { display: '扩展属性显示名', name: 'showName', minWidth: 100 },
	          	        { display: '类型', name: 'type', minWidth: 100,
	          	        	type:'int',
       	                    render: function (item) {
       	                    	     var obj = parseInt(item.type);
       	                             if (obj == 0) {
       	                            	 return '字符';
       	                             }
       	                             else if(obj == 1) {
       	                            	 return '日期';
       	                             }
       	                             else if(obj == 2) {
	        	                        return '数字';
       	                             }
        	                     }
	          	        },
	          	      	{ display: '是否必填', name: 'need', minWidth: 100,
	          	        	need:'int',
       	                    render: function (item) {
       	                    	     var obj = parseInt(item.need);
       	                             if (obj == 0) {
       	                            	 return '是';
       	                             }
       	                             else if(obj == 1) {
       	                            	 return '否';
       	                             }
        	                     }
	          	      	},
	          	      	{ display: '默认值', name: 'defaultValue', minWidth: 100 },
	          	      	{ display: '参考值', name: 'referenceValue', minWidth: 100 }
          	        ], 
          	pageSize:30,
            url:"${ctx}/configServiceExtendProperty/listData",
            width: '100%',height:'96%'
        });
    	//表格列表 end
    })(jQuery);
	 
  //增加或是修改服务扩展属性
	function addConfigServiceExtendProperty(flag,rowId) {
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
                  id:"editConfigServiceExtendPropertyDialog",
                  title: flag =='1'?'增加属性信息':'编辑属性信息',
                  url:'${ctx}/configServiceExtendProperty/toEdit?flag='+flag+"&id="+id,
                  width: 400,
                  height: 300
               });
	}
	//删除服务引擎配置
    function deleteConfigServiceExtend(id) {
    	if(id) {
    		$.Layer.confirm({
    			msg:"确定删除该条记录吗？",
                fn:function(){
                    $.ajax({
                    	url: "${ctx}/configServiceExtendProperty/delete",
                        data:{id:id},
                        type:"post",
                        dataType:"json",
                        success:function(res){
                        	console.log(res);
                        	console.log(res.retMsg);
                        	if(res.retMsg=='删除成功'){
                        		$.Layer.confirm({
                	                msg:"删除成功",
                	                fn:function(){
                	                	//console.log(3);
                	                	gridManager.reload();
                	                	//console.log(4);
                	                	
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
                        	url: "${ctx}/configServiceExtendProperty/deletes",
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
                            },error:function(){
                                alert("删除记录失败！");
                            }
                        });
                    },
                    fn2:function(){
                    	gridManager.reload();
                    }
                    
                });
        	} 
        	else{
        		//alert("请选择需要删除的数据！");
        		$.Layer.confirm({
	                msg:"请选择需要删除的数据！",
	            });
        	}
    	}
    } 
</script>
</html>
