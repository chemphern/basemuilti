<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false"%>
<%@include file="/WEB-INF/views/common/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>羽辰智慧林业平台运维管理系统-专题图管理增加</title>
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
<!-- 封装弹出框dialog -->
<script type="text/javascript" src="${res}/plugins/dialog/jquery.artDialog.source.js"></script>
<script type="text/javascript" src="${res}/plugins/dialog/iframeTools.source.js"></script>
<script type="text/javascript" src="${res}/plugins/dialog/unit.js"></script>
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
		<table width="100%" border="0" cellpadding="0" cellspacing="0" class="date_add_table">
			<tr>
				<td class="t_r">上级图层目录：</td>
				<td>
					<select type="text" name="parent.id" id="parentId" class="text">
						<c:forEach var="list" items="${lists }">
							<c:if test="${ not empty list }">
								<option value="${list.id }">${list.name }</option>
							</c:if>
						</c:forEach>
					</select>
				</td>
			</tr>
			
			<tr>
				<td class="t_r">展示服务：</td>
				<td>
					<textarea name="serviceName" id="serviceName" clos="20" rows="15" disabled="disabled"
						style="width:170px; height:80px; resize:both; overflow:auto;" 
						validate="{required:true,messages:{required:'必填'}}"></textarea>
					<input type="hidden" id="serviceIds" name="serviceIds">
					<input type="button" value="选择" id="selectServiceBtn">
					<span style="color: red">*</span>	
				</td>
			</tr>
		</table>
   </form> 
</body>
<script type="text/javascript">
	$(function() {
		//选择服务
		$("#selectServiceBtn").on("click",function(e) {
			e.preventDefault();
			$.Layer.iframe(
	                { 
	                  id:"selectServiceDialog",
	                  title: "选择服务",
	                  url:"${ctx}/service/toSelectService?flag=2",
	                  width: 1020,
	                  height: 500
	              });
		});
		
		var form = $("#form_id");
		var parentWin = window.parent[0];
		var dialog = parentWin.art.dialog.list["addLayerThemeDialog"];
		var dialog_div = dialog.DOM.wrap;
		
		dialog_div.on("ok", function() {
			form.submit();
		}); 
		var val_obj = exec_validate(form);//方法在 ${res}/js/common/form.js
		val_obj.submitHandler = function(){
			if($("#serviceIds").val() == ""||$("#serviceIds").val()==null) {
				alert("服务id不能为空");
				return false;
			}
			if($("#serviceName").val() == ""||$("#serviceName").val() == null) {
				alert("服务名字不能为空");
				return false;
			}
			$.ajax({
				type : "POST",
				url : "${ctx }/layerTheme/addLayerTheme",
				data : $('#form_id').serialize(),
				dataType:"json",
				async : false,
				error : function(ret) {
					alert("connection error!");
				},
				success : function(ret) {
					$.Layer.confirm({
    	                msg:ret["msg"],
    	                fn:function(){
    	                	if(ret["flag"]=="1"){
    	                		parentWin.gridManager.reload();
    	    					parentWin.treeManager.reload();
    	    					dialog.close();
    	                	}
    	                }
    	            });
				}
			});
	    };
	    form.validate(val_obj);
		
	});
</script>
</html>
