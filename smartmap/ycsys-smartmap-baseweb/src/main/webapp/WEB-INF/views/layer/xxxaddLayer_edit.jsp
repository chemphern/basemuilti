<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false"%>
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
<%-- <!-- AdminLTE App -->
<script src="${res}/dist/js/app.js"></script>
<!-- AdminLTE for demo purposes -->
<script src="${res}/dist/js/demo.js"></script> --%>
<!-- 封装弹出框dialog -->
<script type="text/javascript" src="${res}/plugins/dialog/jquery.artDialog.source.js"></script>
<script type="text/javascript" src="${res}/plugins/dialog/iframeTools.source.js"></script>
<script type="text/javascript" src="${res}/plugins/dialog/unit.js"></script>
<script src="${res}/plugins/jQuery/jquery-2.2.3.min.js"></script>
<script src="${res}/js/common/multiselect.js"></script>
<script src="${res}/plugins/jquery-validation-1.15.1/dist/jquery.validate.min.js"></script>
<script src="${res}/plugins/jquery-validation-1.15.1/lib/jquery.form.js"></script>
<script src="${res}/js/common/form.js"></script>
<script src="${res}/plugins/ligerUI/js/ligerui.all.js" type="text/javascript"></script>

<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
  <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
  <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
  <![endif]-->
</head>
<body>
	<form method="post" id="form_id">
		<input type="hidden" name="id" value="${layer.id}">
		<table width="100%" border="0" cellpadding="0" cellspacing="0" class="date_add_table">
			<tr>
				<td width="120" class="t_r">上级图层节点：</td>
				<td>
				<input type="text" name="pid" id="pid" class="text validate[required]" />
				</td>
			</tr>
			<tr>
				<td class="t_r">图层名称：</td>
				<td><input type="text" name="name" id="name" size="15"
					value="${layer.name }" class="text validate[required]"
					validate="{required:true,maxlength:15,messages:{required:'必填',maxlength:'结点名称 的字符长度大于15个字符！'}}" /></td>
			</tr>
		</table>
	
	<div class="row">
    <div class="col-md-3">
        <div class="box box-solid">
            <div class="box-header with-border">
                <h4 class="box-title">选择服务</h4>
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
                <h4 class="box-title">服务列表</h4>
            </div>
            <div class="box_l">
                <div class="list" id="maingrid4"></div>
            </div>
        </div>
        <!-- /.col -->
    </div>
    </div>
    <div class="row">
    <div class="col-md-12">
        <div class="box box-solid">
            <div class="box-header with-border">
                <h4 class="box-title">图层列表</h4>
            </div>
            <div class="box_l">
                <div class="list" id="maingrid5"></div>
            </div>
        </div>
        <!-- /.col -->
    </div>
    </div>
   </form> 
</body>
<script type="text/javascript">
var treeManager = null;
var gridManager = null;
;(function($) { //避免全局依赖,避免第三方破坏
	$(document).ready(function() {
		//树节点
		$(function() {
			treeManager = $("#tree1").ligerTree({
				url: "${ctx}/service/listServiceType",
                nodeWidth : 90,
                idFieldName :'id',
                parentIDFieldName :'pid',
                onSelect : onSelectServiceType,
                checkbox:false
			});
		});
		
		//选择了树结点事件
		function onSelectServiceType(obj) {
			var serverEngineId = "";
			if(obj.data.text != '服务分类') {
				serverEngineId = obj.data.id
			}
			gridManager.setParm("registerServerType",serverEngineId);
        	window.gridManager.reload();
        }
		
		//下拉树
        var combo = $("#pid").ligerComboBox({
            width: 200,
            selectBoxWidth: 200,
            selectBoxHeight: 200,
            valueField: 'id',
            textField:'text',
            treeLeafOnly:false,
            isMultiSelect:false,
            detailPostIdField:"test",
            //isShowCheckBox: true,
            tree: { url: '${ctx}/layer/listAll',
                    checkbox: false,
                ajaxType: 'get',
                idFieldName: 'id',
                parentIDFieldName:'pid'
                },
        	onSelected:function(value,text){
        		//console.log("text="+text);
        		//console.log("value="+value);
        		$("#pid").val(text);
        	}
        });
		
		//表格列表
		$(function() {
			gridManager = $("#maingrid4").ligerGrid({
				checkbox : false,
				columns : [ {
					display : '服务注册名',
					name : 'registerName',
					align : 'left',
					minWidth : 80
				}, {
					display : '服务显示名',
					name : 'showName',
					minWidth : 80
				}, {
					display : '注册类型',
					name : 'registerType',
					minWidth : 80,
					align : 'left',
					render: function (item) {
                  	     var obj = parseInt(item.registerType);
	                    	  <c:forEach var="map" items="${serviceRegisterType }">
	                    	  		if(obj == "${map.key }") {
	                    	  			return "${map.value.name }";
	                    	  		}
     						  </c:forEach>
                    	}   
				}, {
					display : '服务状态',
					name : 'serviceStatus',
					minWidth : 80,
					render: function (item) {
                 	     var obj = parseInt(item.serviceStatus);
	                    	  <c:forEach var="map" items="${serviceStatus }">
	                    	  		if(obj == "${map.key }") {
	                    	  			return "${map.value.name }";
	                    	  		}
    						  </c:forEach>
                   	}   
				}],
				pageSize : 10,
				url:"${ctx}/service/listService",
				width : '650px',
				height : '200px'
			});
			$("#pageloading").hide();
			
			gridManager2 = $("#maingrid5").ligerGrid({
	            checkbox: true,
	            columns: [
							{ display: '图层名称', name: 'name', align: 'left', minWidth: 100},
							{ display: '几何类型', name: 'geometryType', minWidth: 100}
	          	        ], 
	          	pageSize:30,
	            url:"${ctx}/layer/listData",
	            width: '900px',height:'300px'
	        });
			
		});
	});
})(jQuery);

	$(function() {
		var form = $("#form_id");
		var val_obj = exec_validate(form);//方法在 ${res}/js/common/form.js
		form.validate(val_obj);
		
		var parentWin = window.parent;
		var dialog = parentWin.art.dialog.list["editLayerDialog"];
		var dialog_div = dialog.DOM.wrap;
		
		dialog_div.on("ok", function() {
            var counts = $('div.l-exclamation'); //填的不对的记录数
			if(counts.length < 1) {
				$.ajax({
					type : "POST",
					url : "${ctx }/layer/save",
					data : $('#form_id').serialize(),
					dataType:"json",
					async : false,
					error : function(request) {
						alert("Connection error");
					},
					success : function(ret) {
						alert(ret.msg);
						if(ret.flag=="1") {
							parentWin.treeManager.reload();
							dialog.close();
						}
						parentWin.layer.hideBtn(); //隐藏新增按钮
					}
				});
			}
			else {
				alert("请重新填写那些有误的信息！");
			}

		});
	});
</script>
</html>
