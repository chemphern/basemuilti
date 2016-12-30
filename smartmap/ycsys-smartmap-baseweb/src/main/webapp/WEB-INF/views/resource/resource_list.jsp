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
	background-color: #f1f1f1;
}

body {
	overflow-y: hidden;
}
</style>
</head>
<body>
        <!-- Content Header (Page header) -->
        <section class="content-header">
            <h1>
                资源管理
            </h1>
        </section>
	<div class="row">
		<div class="col-md-3 col-sm-4">
			<div class="box box-solid">
				<div class="box-header with-border">
					<h4 class="box-title">资源分类</h4>
					<c:if test="${emptyTreeFlag == '1' }">
						<div class="btn_box" id="resource_type_btn">
							<button class="current" onclick="resource_type.add();">
								<i class="iconfont icon-plus"></i>新增
							</button>
						</div>
					</c:if>
				</div>
				<div class="box_l">
					<ul id="tree1">

					</ul>
				</div>
				<!-- /.box-body -->
			</div>
		</div>
		<!-- /.col -->
		<div class="col-md-9 col-sm-8">
			<div class="box box-solid">
				<div class="box-header with-border">
					<h4 class="box-title">资源列表</h4>
					<div class="btn_box">
						资源全称：<input name="fullName" id="fullName" class="text">
						资源名称：<input name="name" id="name" class="text">
						资源类型：<select type="text" name="type" id="type" class="text">
									<option value="">请选择</option>
									<c:forEach var="map" items="${resourceType }">
										<option value="${map.key }">${map.value.name }</option>	
									</c:forEach>
							  </select>
					          详细分类：<select type="text" name="fileType" id="fileType" class="text">
									<option value="">请选择</option>
									<c:forEach var="map" items="${fileType }">
										<option value="${map.key }">${map.value.name }</option>	
									</c:forEach>
							  </select>
						<shiro:hasPermission name="resource-list">
							<button class="current" onclick="resource_list.query();">
								<i class="glyphicon glyphicon-search"></i>查询
							</button>
						</shiro:hasPermission>
						<shiro:hasPermission name="resource-create">
							<button class="" onclick="resource_list.editResource('1');">
								<i class="iconfont icon-plus"></i>新增
							</button>
						</shiro:hasPermission>
						<shiro:hasPermission name="resource-delete">
							<button onclick="resource_list.deleteResource();">
								<i class="iconfont icon-trash"></i>删除
							</button>
						</shiro:hasPermission>
						<shiro:hasPermission name="resource-edit">
							<button onclick="resource_list.editResource('2');">
								<i class="iconfont icon-edit"></i>编辑
							</button>
						</shiro:hasPermission>
						<shiro:hasPermission name="resource-move">
							<button onclick="resource_list.up();">
								<i class="iconfont icon-angle-double-up"></i>上移
							</button>
							<button onclick="resource_list.down();">
								<i class="iconfont icon-angle-double-down"></i>下移
							</button>
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
<script src="${res}/dist/js/app.js"></script>
<script src="${res}/dist/js/demo.js"></script>
<script type="text/javascript"
	src="${res}/plugins/dialog/jquery.artDialog.source.js"></script>
<script type="text/javascript"
	src="${res}/plugins/dialog/iframeTools.source.js"></script>
<script type="text/javascript" src="${res}/plugins/dialog/unit.js"></script>
<script>
var treeManager = null;
var gridManager = null;
var tempResourceTypeId = null;
    ;(function($){  //避免全局依赖,避免第三方破坏
    	//树 start
    	$("#tree1").ligerTree(
	            {
	                url: "${ctx}/resourceType/listAll",  
                    nodeWidth : 170,
                    idFieldName :'id',
                    parentIDFieldName :'pid',
                    onSelect : onSelectResourceType
                    	
	             });
    	treeManager = $("#tree1").ligerGetTreeManager();
        var menu;
        var actionNodeID;
        var actionNodeName;
        var resourceTypeId;
        
        
        //树右键处理
        $(function () {
            menu = $.ligerMenu({ top: 100, left: 100, width: 120, items:
            [
			<shiro:hasPermission name="resourec-type-create">
            	{ text: '增加', click: operate, icon: 'add' },
            </shiro:hasPermission>
            <shiro:hasPermission name="resource-type-edit">
            	{ text: '修改', click: operate },
            </shiro:hasPermission>
            <shiro:hasPermission name="resource-type-delete">
            	{ text: '删除', click: deleteResourceType},
            </shiro:hasPermission>
            <shiro:hasPermission name="resource-type-backup">
           		{ line: true },
            	{text: '备份', click: backupResource}
           	</shiro:hasPermission>
            ]
            });

            $("#tree1").ligerTree({ onContextmenu: function (node, e)
            { 
                actionNodeID = node.data.id;
                actionNodeName = node.data.text;
                menu.show({ top: e.pageY, left: e.pageX });
                return false;
            }
            });
        });
        
        //添加或修改 资源分类
        function operate(item){
        	var flag = item.text=='增加'?1:0;
            var dialog = $.Layer.iframe(
                {
                  id:"editResourceTypeDialog",
                  title: item.text+'资源分类',
                  url:'${ctx}/resourceType/toEdit?flag='+flag+'&actionNodeID='+actionNodeID,
                  width: 400,
                  height: 300
                  /*
                  ok:function(window){
                	  //console.log("window="+window);
                      var form = window.document.getElementById("form_id");
                      window.sumbmit();
                      //treeManager.refreshTree();
                      //treeManager.reload();
                      //dialog.close();
                      return false;
                  }
                  */
                });
        }
        
        function onSelectResourceType(obj) {
        	//console.log(obj.data.id);
        	resourceTypeId = obj.data.id;
        	//console.log("resourceTypeId="+resourceTypeId);
        	//console.log(gridManager);
        	window.tempResourceTypeId = resourceTypeId;
        	gridManager.setParm("resourceTypeId",resourceTypeId);
        	gridManager.setParm("fullName",$("#fullName").val());
			gridManager.setParm("name",$("#name").val());
			gridManager.setParm("type",$("#type").val());
			gridManager.setParm("fileType",$("#fileType").val());
        	window.gridManager.reload();
        }
        //删除资源分类
    	function deleteResourceType(item,i) {
    		$.Layer.confirm({
                msg:"确定删除该记录吗？",
                fn:function(){
                    $.ajax({
                        data:{'id':actionNodeID},
                    	url: "${ctx}/resourceType/delete",
                        type:"post",
                        dataType:"json",
                        success:function(result){
            				if(result["result"]=="0") {
            			         treeManager.reload();
            			         //成功则删除这个树结点
            			         //resource_type.removeTreeItem(actionNodeID);
            			         //把所有结点都删除了，这时需要把新增按钮显示
            			         if(result["showBtnFlag"] == "1") {
            			        	 window.resource_type.showBtn();
            			         }
            				}
            				else if(result["result"]=="1"){
            					alert("该结点下有子结点或已上传资源，不能删除");
            				}
            				else {
            					alert("请选择资源进行删除!");
            				}
                        },error:function(){
                            alert("删除失败！");
                        }
                    });
                },
                fn2:function(){
                }
                
            });
    		
    	}
    	
        function backupResource(item,i) {
        	$.Layer.confirm({
                msg:"确定要备份该节点下全部资源吗？",
                fn:function(){
                    $.ajax({
                        data:{'id':actionNodeID,'name':actionNodeName},
                    	url: "${ctx}/resourceType/backupResource",
                        type:"post",
                        dataType:"json",
                        success:function(result){
            				alert(result.msg);
                        },error:function(){
                            alert("备份失败！");
                        }
                    });
                },
                fn2:function(){
                }
                
            });
        }
        
    	 //查看
        function see(item, i) {
            alert(actionNodeID + " | " + item.text);
        }
    	//树 end
            
    	//表格列表 start
       gridManager = $("#maingrid4").ligerGrid({
            checkbox: true,
            rownumbers : true,
            columns: [
	          	        { display: '资源名称', name: 'name', align: 'left', width: 100 },
	          	        { display: '资源全称', name: 'fullName', minWidth: 60 },
	          	        { display: '资源类型', name: 'type', width: 100,align:'center',
	          	        			type:'int',
   	        	                    render: function (item) {
   	        	                    	     var obj = parseInt(item.type);
	   	        	                    	  <c:forEach var="map" items="${resourceType }">
		        	                    	  		if(obj == "${map.key }") {
		        	                    	  			return "${map.value.name }";
		        	                    	  		}
	     	       						  	  </c:forEach>
    	        	                     }
	          	        },
	          	        { display: '详细类型', name: 'fileType', minWidth: 100 ,
    	        	                    	 render: function (item) {
       	        	                    	     var obj = parseInt(item.fileType);
	       	        	                    	  <c:forEach var="map" items="${fileType }">
	       	        	                    	  		//console.log("${map.value.name }");
	       	        	                    	  		//console.log("obj="+obj);
	       	        	                    	  		if(obj == "${map.key }") {
	       	        	                    	  			return "${map.value.name }";
	       	        	                    	  		}
			       	       						  </c:forEach>
        	        	                     }              	 
	          	        },
	          	        { display: '上传人', name: 'uploadPerson.name', minWidth: 60 },
	          	        { display: '上传时间', name: 'uploadDate', minWidth: 100, format: 'yyyy-MM-dd HH:mm:ss' },
	          	        { display: '操作', 
	          	        	isSort: false, render: function (rowdata, rowindex, value)
	                        {
	                          var h = "";
	                          if (!rowdata._editing)
	                          {
	                        	<shiro:hasPermission name="resource-edit">
		                            h += "<input type='button' class='list-btn bt_edit' onclick='resource_list.editResource(2,"+ rowdata.id+ ")'/>";
	                        	</shiro:hasPermission>
	                           <shiro:hasPermission name="resource-delete">
		                            h += "<input type='button' class='list-btn bt_del' onclick='resource_list.deleteResource(" + rowdata.id + ")'/>";
	                           </shiro:hasPermission>
	                           <shiro:hasPermission name="resource-view">
	                            	h += "<input type='button' class='list-btn bt_view' onclick='resource_list.viewResource(" + rowdata.id + ")'/>";
	                            </shiro:hasPermission>
	                          }
	                          return h;
	                        }
	          	        }
          	        ], 
          	pageSize:30,
            url:"${ctx}/resource/listData",
            width: '100%',height:'96%'
        });
    	//表格列表 end
           
    })(jQuery);
    
    //删除数据结点
	function removeTreeItem(actionNodeID) {
		var node = treeManager.getDataByID(actionNodeID);
		if(node) {
    		treeManager.remove(node);
		}
    }
    
    //资源分类操作 function
    var resource_type = {
    		//删除数据结点
    		removeTreeItem: function(actionNodeID) {
    			var node = treeManager.getDataByID(actionNodeID);
    			if(node) {
    	    		treeManager.remove(node);
    			}
    		},
    		add:function() {
                var dialog = $.Layer.iframe(
                    {
                      id:"editResourceTypeDialog",
                      title: '增加资源分类',
                      url:'${ctx}/resourceType/toEdit?flag=1',
                      width: 400,
                      height: 300
                    });
    		},
    		hideBtn: function() {
    			$("#resource_type_btn").hide();
    		},
    		showBtn: function() {
    			$("#resource_type_btn").show();
    		}
    }
    //资源管理操作 function
	var resource_list = {
			up: function() {
				var selectedRows = gridManager.getSelecteds();
		    	if(selectedRows.length == 1) {
		    		var currRow = gridManager.getSelectedRow();
		    		//前一条记录
		    		var preRow = gridManager.getRow(gridManager.getSelectedRow().__previd);
		    		//console.log(preRow);
		    		if(preRow != "undefined" && typeof(preRow) != "undefined" && preRow != null) {
		    			//console.log(preRow.id);
		    			$.ajax({
		    				url:"${ctx}/resource/move",
		    				method:"post",
		    				data:{'srcId':currRow.id,'targetId':preRow.id},
		    				dataType:"json",
		    				success:function(result) {
		    					//console.log(result);
		    					gridManager.up(selectedRows[0]);
		    				},
		    				error: function(result) {
		    					//console.log(result);
		    					alert(result.msg);
		    				}
		    			});
			    		
		    		}
		    		else {
		    			alert("这是第一条记录，不能进行上移！");
		    		}
		    	}
		    	else{
		    		alert("请选择一条记录进行操作！");
		    		return false;
		    	}
			},
			down: function() {
				var selectedRows = gridManager.getSelecteds();
		    	if(selectedRows.length == 1) {
		    		var currRow = gridManager.getSelectedRow();
		    		//console.log(currRow);
		    		//下一条记录
		    		var nextRow = gridManager.getRow(gridManager.getSelectedRow().__nextid);
		    		//console.log(nextRow);
		    		if(nextRow != "undefined" && typeof(nextRow) != "undefined" && nextRow != null) {
		    			//console.log(nextRow.id);
		    			console.log(111);
		    			$.ajax({
		    				url:"${ctx}/resource/move",
		    				method:"post",
		    				data:{'srcId':currRow.id,'targetId':nextRow.id},
		    				dataType:"json",
		    				success:function(result) {
		    					//console.log(result.msg);
		    					gridManager.down(selectedRows[0]);
		    				},
		    				error: function(result) {
		    					alert(result.msg);
		    				}
		    			});
			    		
		    		}
		    		else {
		    			alert("这是第最后一条记录，不能进行下移！");
		    		}
		    	}
		    	else{
		    		alert("请选择一条记录进行操作！");
		    		return false;
		    	}
			},
			query:function() {
	        	gridManager.setParm("resourceTypeId",tempResourceTypeId);
				gridManager.setParm("fullName",$("#fullName").val());
				gridManager.setParm("name",$("#name").val());
				gridManager.setParm("type",$("#type").val());
				gridManager.setParm("fileType",$("#fileType").val());
	        	window.gridManager.reload();
			},
			viewResource: function(id) {
				if(id) {
		    		var dialog = $.Layer.iframe(
			                { 
			                  id:"viewResourceDialog",
			                  title: '查看资源',
			                  url:'${ctx}/resource/view?id='+id,
			                  width: 400,
			                  height: 500,
			                  button:[]
			               });
				}
				
			},
			//增加或修改资源
			editResource: function(flag,rowId) {
			    var id = "";
			    if(flag=='2') {
			    	if(rowId) {
			    		id = rowId;
			    	}
			    	else {
				    	var selectedRows = gridManager.getSelecteds();
				    	if(selectedRows.length != 1) {
				    		alert("请选择一条记录进行修改！");
				    		return false;
				    	}
				    	else {
				    		id = selectedRows[0].id;
				    	}
			    	}
			    }
				var dialog = $.Layer.iframe(
		                { 
		                  id:"editResourceDialog",
		                  title: flag =='1'?'增加资源':'编辑资源',
		                  url:'${ctx}/resource/toEdit?resourceTypeId='+tempResourceTypeId+"&id="+id,
		                  width: 400,
		                  height: 500
		                  /*
		                  ok:function(window){
		                	  //console.log("window="+window);
		                      var form = window.document.getElementById("form_id");
		                      window.sumbmit();
		                      //treeManager.refreshTree();
		                      //treeManager.reload();
		                      //dialog.close();
		                      return false;
		                  },
		                  button:[]
		                  */
		               });
				
			},
			//删除资源
		    deleteResource: function(id) {
		    	if(id) {
		    		$.Layer.confirm({
		                msg:"确定删除该记录吗？",
		                fn:function(){
		                    $.ajax({
		                    	url: "${ctx}/resource/delete",
		                        data:{'id':id},
		                        type:"post",
		                        dataType:"json",
		                        success:function(res){
		                        	gridManager.reload();
		                            alert(res.msg);
		                        },error:function(){
		                            alert("删除失败！");
		                        }
		                    });
		                },
		                fn2:function(){
		                	gridManager.reload();
		                }
		                
		            });
		    	}
		    	else {
			    	var url = "${ctx}/resource/deletes";
			    	var selectedRows = gridManager.getSelecteds();
			    	if(selectedRows.length > 0) {
				    	//console.log(selectedRows);
				    	var ids = [];
				    	for(var i = 0; i < selectedRows.length; i++) {
				    		ids.push(selectedRows[i].id);
				    	}
				    	var str = ids.join(",");
				    	$.Layer.confirm({
			                msg:"确定删除选择的记录吗？",
			                fn:function(){
			                    $.ajax({
			                    	url: "${ctx}/resource/deletes",
			                        data:{'idsStr':str},
			                        type:"post",
			                        dataType:"json",
			                        success:function(result){
			                        	alert(result.msg);
							    		 /*
							    		for(var i = 0; i < selectedRows.length; i++) {
								    		gridManager.remove(selectedRows[i]);
							        	}
							    		 */
							    		 gridManager.reload();
			                        },error:function(){
			                            alert("删除失败！");
			                        }
			                    });
			                },
			                fn2:function(){
			                	gridManager.reload();
			                }
			                
			            });
				    	
				    	/*
						$.post(url,{"idsStr":str},function(result){
							if("success"==result) {
						         //成功则删除这个树结点
						         if (!selectedRows) { 
						    		alert('请选择行'); 
						    		return; 
						    	 }
						    	 else {
						    		 
						    		//for(var i = 0; i < selectedRows.length; i++) {
							    		//gridManager.remove(selectedRows[i]);
						        	//}
						    		 gridManager.reload();
						    	 }
							}
						});
				    	*/
			    	} 
			    	else{
			    		alert("请选择数据！");
			    		return false;
			    	}
		    	}
		    }
		
		};
</script>
</html>
