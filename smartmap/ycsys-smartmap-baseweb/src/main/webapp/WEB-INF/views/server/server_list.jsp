<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@include file="/WEB-INF/views/common/taglib.jsp"%>
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
html,body {
	background-color: #ecf0f5
}

body {
	overflow-y: hidden;
}
</style>
</head>
<body>
	<div class="row">
		<div class="col-md-3">
			<div class="box box-solid">
				<div class="box-header with-border">
					<h4 class="box-title">平台监控配置</h4>
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
					<h4 class="box-title">监控列表</h4>
					<div class="btn_box">
						<button class="current" onclick="editServer('1');">
							<i class="iconfont icon-plus"></i>新增
						</button>
						<button onclick="deleteServer();">
							<i class="iconfont icon-trash"></i>删除
						</button>
						<button onclick="editServer('2');">
							<i class="iconfont icon-edit"></i>编辑
						</button>
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
<script src="${res}/plugins/jQuery/jquery-2.2.3.min.js"></script>
<script src="${res}/plugins/ligerUI/js/core/base.js" type="text/javascript"></script>
<script src="${res}/plugins/ligerUI/js/plugins/ligerGrid.js" type="text/javascript"></script>
<script src="${res}/plugins/ligerUI/js/plugins/ligerDrag.js" type="text/javascript"></script>
<script src="${res}/plugins/ligerUI/js/plugins/ligerMenu.js" type="text/javascript"></script>
<script src="${res}/plugins/ligerUI/js/plugins/ligerTree.js" type="text/javascript"></script>
<script src="${res}/plugins/ligerUI/js/plugins/CustomersData.js" type="text/javascript"></script>
<script src="${res}/bootstrap/js/bootstrap.min.js"></script>
<script src="${res}/plugins/knob/jquery.knob.js"></script>
<script src="${res}/plugins/slimScroll/jquery.slimscroll.js"></script>
<script src="${res}/dist/js/app.js"></script>
<script src="${res}/dist/js/demo.js"></script>
<script type="text/javascript" src="${res}/plugins/dialog/jquery.artDialog.source.js"></script>
<script type="text/javascript" src="${res}/plugins/dialog/iframeTools.source.js"></script>
<script type="text/javascript" src="${res}/plugins/dialog/unit.js"></script>
<script>
var treeManager = null;
var gridManager = null;
    ;(function($){  //避免全局依赖,避免第三方破坏
    	$(document).ready(function() {
			//树 start
			//树节点
			var menu;
			var actionNodeID;
			$("#tree1").ligerTree(
	            {
	                url: "${ctx}/server/listServerType",
                    nodeWidth : 140,
                    idFieldName :'id',
                    parentIDFieldName :'pid',
                    //textFieldName :'name',
                    onSelect : onSelectServerType,
                    onContextmenu : function(node, e) {
						actionNodeID = node.data.text;
						/* menu.show({
							top : e.pageY,
							left : e.pageX
						}); */
						return false;
					}
	             });
	    	/* function onSelectServerName(obj) {
	    		name = obj.data.name;
	        	gridManager.setParm("name",name);
	        	window.gridManager.reload();
	        } */
	    	//树 end
	            
			function onSelectServerType(obj) {
				var serverType = "";
				if(obj.data.text != '平台监控配置') {
					//alert(obj.data.id);
					serverType = obj.data.id
				}
				gridManager.setParm("serverType",serverType);
	        	window.gridManager.reload();
	        }
	    	//树 end
	    	
	    	//表格列表 start
       gridManager = $("#maingrid4").ligerGrid({
            checkbox: true,
            rownumbers : true,
            columns: [
                      { display: '服务器名称', name: 'name', align: 'center', width: 100 },
                      { display: '服务器类型', name: 'serverType', Width: 60 ,
                    	  render: function (item) {
	                    	     var obj = parseInt(item.serverType);
  	                    	  <c:forEach var="map" items="${serverType }">
  	                    	  		if(obj == "${map.key }") {
  	                    	  			return "${map.value.name }";
  	                    	  		}
	       						  </c:forEach>
	                     }      
                      },
                      { display: '是否取自服务引擎配置', name: 'fromServerEngineFlag', minWidth: 100,
                    	  fromServerEngineFlag:'int',
	   	                    render: function (item) {
	   	                    	     var obj = parseInt(item.fromServerEngineFlag);
	   	                             if (obj == 0) {
	   	                            	 return '是';
	   	                             }
	   	                             else if(obj == 1) {
	   	                            	 return '否';
	   	                             }
		    	                 }  
                      },
                      { display: '服务器IP地址/机器名', name: 'ipAddress', minWidth: 100 },
                      { display: 'SNMP协议端口', name: 'snmpPort', minWidth: 60 },
                      { display: '服务引擎', name: 'serverEngine.configName', minWidth: 80 },
                      { display: '备注', name: 'remarks', minWidth: 60 },
                      { display: '操作', isSort: false, render: function (rowdata, rowindex, value)
                        {
                          var h = "";
                          if (!rowdata._editing)
                          {
                        	  h += "<input type='button' class='list-btn bt_edit' onclick='editServer(2,"+ rowdata.id+ ")'/>";
	                          h += "<input type='button' class='list-btn bt_del' onclick='deleteServer(" + rowdata.id + ")'/>"; 
                          }
                          return h;
                        }
                      
                      }
                      ], 
          	pageSize:30,
            url:"${ctx}/server/listData",
            width: '100%',height:'96%'
        });
    	//表格列表 end
		});
    })(jQuery);
  //增加或修改平台监控配置
	function editServer(flag,rowId) {
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
                  id:"editServerDialog",
                  title: flag =='1'?'增加平台监控配置':'编辑平台监控配置',
                  url:'${ctx}/server/toEdit?flag='+flag+"&id="+id,
                  width: 400,
                  height: 350
                  
               });
		
	}
	//删除服务引擎配置
    function deleteServer(id) {
    	if(id) {
    		$.Layer.confirm({
                msg:"确定删除该条记录吗？",
                fn:function(){
                    $.ajax({
                    	url: "${ctx}/server/delete",
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
    		gridManager.reload();
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
                        	url: "${ctx}/server/deletes",
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
        		alert("请选择需要删除的数据！");
        	}
    	}
    };
   
</script>
</html>
