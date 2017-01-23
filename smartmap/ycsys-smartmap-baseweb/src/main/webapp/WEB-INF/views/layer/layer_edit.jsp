<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false"%>
<%@include file="/WEB-INF/views/common/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>羽辰智慧林业平台运维管理系统-图层管理编辑</title>
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
<script src="${res}/js/common/form.js"></script>
<script src="${res}/plugins/jQuery/jquery-2.2.3.min.js"></script>
<script src="${res}/plugins/jquery-validation-1.15.1/lib/jquery.form.js"></script>
<script src="${res}/plugins/jquery-validation-1.15.1/dist/jquery.validate.min.js" type="text/javascript"></script>
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
		<!-- <input type="hidden" name="geometryType" id="geometryType"> -->
		<table width="100%" border="0" cellpadding="0" cellspacing="0" class="date_add_table">
			<tr>
				<td class="t_r">上级图层目录：</td>
				<td>
					<select type="text" name="parent.id" id="parentId" class="text" disabled="disabled">
						<c:forEach var="list" items="${layerTypes }">
							<c:if test="${ not empty list }">
								<option value="${list.id }">${list.name }</option>
							</c:if>
						</c:forEach>
					</select>
				</td>
			</tr>
			
			<tr>
				<td class="t_r">服务名称：</td>
				<td>
					<input type="text" id="serviceName" disabled="disabled" class="text" value="${layer.service.showName }">
				</td>
			</tr>
			
			<tr>
				<td class="t_r">图层名称：</td>
				<td>
					<input type="text" id="name" name="name" class="text" value="${layer.name }">
				</td>
			</tr>
			<tr>
				<td class="t_r">业务类型：</td>
				<td><select type="text" name="businessType" id="businessType" class="text">
						<c:forEach var="map" items="${businessType }">
							<option value="${map.key }">${map.value.name }</option>	
						</c:forEach>
				</select></td>
			</tr>
			
			<tr>
				<td class="t_r">标题显示字段：</td>
				<td>
					<%-- <textarea name="tempNameField" id="tempNameField" clos="20" rows="15" disabled="disabled"
						style="width:170px; height:80px; resize:both; overflow:auto;">${layer.nameField }</textarea> --%>
					<input name="tempNameField" id="tempNameField" class="text" disabled="disabled">
					<input type="hidden" id="nameField" name="nameField" value="${layer.nameField }">
					<input type="button" value="选择" onclick="selectFields('1')">
				</td>
			</tr>
			
			<tr>
				<td class="t_r">摘要显示字段：</td>
				<td>
					<textarea name="tempSummaryFields" id="tempSummaryFields" clos="20" rows="15" disabled="disabled"
						style="width:170px; height:80px; resize:both; overflow:auto;">${layer.summaryFields }</textarea>
					<input type="hidden" id="summaryFields" name="summaryFields" value="${layer.summaryFields }">
					<input type="button" value="选择" onclick="selectFields('2')">
				</td>
			</tr>
			
			<tr>
				<td class="t_r">属性显示字段：</td>
				<td>
					<textarea name="tempDisplayFields" id="tempDisplayFields" clos="20" rows="15" disabled="disabled"
						style="width:170px; height:80px; resize:both; overflow:auto;">${layer.displayFields }</textarea>
					<input type="hidden" id="displayFields" name="displayFields" value="${layer.displayFields }">
					<input type="button" value="选择" onclick="selectFields('3')">
				</td>
			</tr>
		</table>
   </form> 
</body>
<script type="text/javascript">
	//选择域
	function selectFields(flag) {
		$.Layer.iframe(
	            { 
	              id:"selectFieldsDialog",
	              title: "选择域",
	              url:"${ctx}/layer/toSelectFields?address=${layer.realAddress}&flag=" + flag,
	              width: 800,
	              height: 500
	          });
	}
	$(function() {
		//设置下拉的值
		if("${layer.parent.id}") {
			$("#parentId option[value=${layer.parent.id}]").attr("selected",true);
		}
		
		if("${layer.businessType}") {
			$("#businessType option[value=${layer.businessType}]").attr("selected",true);
		}
		
		//选择服务
		$("#selectServiceBtn").on("click",function(e) {
			e.preventDefault();
			$.Layer.iframe(
	                { 
	                  id:"selectServiceDialog",
	                  title: "选择服务",
	                  url:"${ctx}/service/toSelectService",
	                  width: 1020,
	                  height: 500
	              });
		});
		
		//选择图层 selectLayerBtn
		$("#selectLayerBtn").on("click",function(e) {
			e.preventDefault();
			$.Layer.iframe(
	                { 
	                  id:"selectLayerDialog",
	                  title: "选择服务",
	                  url:"${ctx}/service/toSelectLayer?id=" + $("#serviceId").val() +"&serviceVisitAddress=" + $("#serviceVisitAddress").val(),
	                  width: 800,
	                  height: 500
	              });
		});
		
		
		var form = $("#form_id");
		var parentWin = window.parent[0];
		var dialog = parentWin.art.dialog.list["editLayerDialog"];
		var dialog_div = dialog.DOM.wrap;
		
		dialog_div.on("ok", function() {
			form.submit();
		});
		var val_obj = exec_validate(form);//方法在 ${res}/js/common/form.js
		val_obj.submitHandler = function(){
			$.ajax({
				type : "POST",
				url : "${ctx }/layer/updateLayer",
				data : $('#form_id').serialize(),
				dataType:"json",
				async : false,
				error : function(ret) {
					$.Layer.confirm({
    	                msg:"connection error!"
    	            });
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
