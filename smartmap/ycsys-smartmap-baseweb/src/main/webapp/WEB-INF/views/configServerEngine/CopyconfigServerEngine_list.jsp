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
            background-color: #ecf0f5
        }
        body{
            overflow-y: hidden;
        }
        </style>
</head>
<body>
<div class="row">
    <div class="col-md-2">
        <div class="box box-solid">
            <div class="box-header with-border">
                <h4 class="box-title">服务引擎组织</h4>
            </div>
            <div class="box_l">
                <ul id="tree1">
                
                </ul>
            </div>
            <!-- /.box-body -->
        </div>
    </div>
    <!-- /.col -->
    <div class="col-md-10">
        <div class="box box-solid">
            <div class="box-header with-border">
                <h4 class="box-title">服务引擎列表</h4>
                <div class="btn_box">
                    <button class="current" onclick="editConfigServerEngine('1');"><i class="iconfont icon-plus"></i>新增</button>
                	<button onclick="editConfigServerEngine('2');"><i class="iconfont icon-edit"></i>编辑</button>
                	<button onclick="deleteConfigServerEngine();"><i class="iconfont icon-trash"></i>删除</button>
                	<button onclick="yc_import()"><i class="glyphicon glyphicon-import"></i>导入</button>
                	<button onclick="yc_output()"><i class="glyphicon glyphicon-export"></i>导出</button>
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
	;(function($){//避免全局依赖,避免第三方破坏
    	//树 start
    	$("#tree1").ligerTree(
	            {
	                url: "${ctx}/configServerEngine/listAllName",
	                checkbox: false, 
                    nodeWidth : 140,
                    idFieldName :'id',
                    textFieldName :'name'
	               // onBeforeExpand: onBeforeExpand,
	                //onExpand: onExpand
	             });
    	//树 end
    	
    	//表格列表 start
       gridManager = $("#maingrid4").ligerGrid({
            checkbox: true,
            columns: [
	          	        { display: '配置名称',  name: 'configName', align: 'left', width: 100 },
	          	        { display: '引擎类型', name: 'engineType.name', align: 'left', width: 100},
	          	        { display: '集成模式', name: 'integrationModel', minWidth: 60,
	          	        	integrationModel:'int',
	          	        	render: function (item) {
  	                    	     var obj = parseInt(item.integrationModel);
  	                             if (obj == 0) {
  	                            	 return '单机';
  	                             }
  	                             else if(obj == 1) {
  	                            	 return '集群';
  	                             }
   	                    	}    
	          	        },
	          	      	{ display: '机器名', name: 'machineName', minWidth: 60 },
	          	      	{ display: '内网IP', name: 'intranetIp', minWidth: 60 },
	          	      	{ display: '内网端口', name: 'intranetPort', minWidth: 60 },
	          	    	{ display: '运行状态', name: 'runningStatus', minWidth: 60,
	          	      		 runningStatus:'int',
        	        		 render: function (item) {
                    	     var obj = parseInt(item.runningStatus);
                             if (obj == 0) {
                            	 return '启用';
                             }
                             else if(obj == 1) {
                            	 return '禁用';
                             }
	                    	}    
	          	    	},
	          	  		{ display: '数据上传服务地址', name: 'dataUploadPath', width: 100 },
	          			{ display: '数据上传绝对路径', name: 'dataUploadRealPath', width: 100 },
	          			{ display: '更新者', name: 'updator.name', minWidth: 60 },
	          			{ display: '操作', 
	          	        	isSort: false, render: function (rowdata, rowindex, value)
	                        {
	                          var h = "";
	                          if (!rowdata._editing)
	                          {
	                        	  h += "<input type='button' class='list-btn bt_edit' onclick='editConfigServerEngine(2,"+ rowdata.id+ ")'/>";
		                          h += "<input type='button' class='list-btn bt_del' onclick='deleteConfigServerEngine(" + rowdata.id + ")'/>"; 
	                          }
	                          return h;
	                        }
	          			}
          	        ], 
          	pageSize:30,
            url:"${ctx}/configServerEngine/listData",
            width: '100%',height:'96%'
        });
    	//表格列表 end
    })(jQuery);	
  /* //增加服务引擎配置
	function addConfigServerEngine() {
		var dialog = $.Layer.iframe(
                {
                  title: '增加引擎配置',
                  url:'${ctx}/configServerEngine/toAdd',
                  width: 400,
                  height: 480,
                  button: [{
		                      name: '提交',
		                      callback: function () {
		                          //$d.DOM.wrap.trigger('ok');
		                          dialog.close();
		                          return false;
		                      },
		                      disabled: false,
		                      className: 'bt_sub',
		                      focus: true
                  		  }]
                });
	} */
	//增加或修改资源
	function editConfigServerEngine(flag,rowId) {
	    var id = "";
	    if(flag=='2') {
	    	if(rowId) {
	    		id = rowId;
	    	}
	    	else {
		    	var selectedRows = gridManager.getSelecteds();
		    	if(selectedRows.length > 1 || selectedRows.length < 1) {
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
                  id:"editConfigServerEngineDialog",
                  title: flag =='1'?'增加服务引擎配置':'编辑服务引擎配置',
                  url:'${ctx}/configServerEngine/toEdit?flag='+flag+"&id="+id,
                  width: 400,
                  height: 500
                  
               });
		
	}
	//删除服务引擎配置
    function deleteConfigServerEngine(id) {
    	if(id) {
    		$.Layer.confirm({
                msg:"确定删除该条记录吗？",
                fn:function(){
                    $.ajax({
                    	url: "${ctx}/configServerEngine/delete",
                        data:{id:id},
                        type:"post",
                        dataType:"json",
                        success:function(res){
                        //gridManager.reload();
                            alert(res.retMsg);
                            gridManager.reload();
                        },error:function(){
                            alert("删除记录失败！");
                        }
                    });
                },
                fn2:function(){
                	gridManager.reload();
                }
                
            });
    	}else{
    		var url = "${ctx}/configServerEngine/deletes";
        	var selectedRows = gridManager.getSelecteds();
        	if(selectedRows.length > 0) {
    	    	//console.log(selectedRows);
    	    	var ids = [];
    	    	for(var i = 0; i < selectedRows.length; i++) {
    	    		ids.push(selectedRows[i].id);
    	    	}
    	    	var str = ids.join(",");
    			$.post(url,{"idsStr":str},function(result){
    				if("success"==result) {
    			    	gridManager.reload();
    				}
    			});
        	} 
        	else{
        		alert("请选择需要删除的数据！");
        	}
    	}
    };
    function yc_import(){
        var dialog = $.Layer.iframe(
                {
                    id:"importConfigServerEngineDialog",
                    title: '导入服务引擎配置',
                    url:'${ctx}/configServerEngine/importConfigServerEngine',
                    width: 400,
                    height: 100
                });

    };
   function yc_output(){
		var fm = $("<form/>",{
			action:"${ctx}/configServerEngine/export",
			method:"post",
			id:"test",
			style:"display:none"
		});
	   $(document.body).append(fm);
	   fm.submit();
	   fm.remove();
    };
    function getLigerManager(){
        return $("#maingrid4").ligerGetGridManager();
    };
</script>
</html>
