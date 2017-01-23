<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false"%>
<%@include file="/WEB-INF/views/common/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>羽辰智慧林业平台运维管理系统-图层管理</title>
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
		<table width="100%" border="0" cellpadding="0" cellspacing="0" class="date_add_table">
			<tr>
				<td class="t_r">服务名称：</td>
				<td>
					<input type="text" name="serviceName" id="serviceName" value="${layer.service.showName }" class="text" disabled="disabled"/>
				</td>
			</tr>
			<tr>
				<td class="t_r">服务注册类型：</td>
				<td>
					<select type="text" name="registerType" id="registerType" class="text" disabled="disabled">
						<c:forEach var="map" items="${serviceRegisterType}">
							<option value="${map.key }">${map.value.name }</option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<td class="t_r">上级图层目录：</td>
				<td>
					<input type="text" name="pName" id="pName" value="${layer.parent.name}" class="text" disabled="disabled">
				</td>
			</tr>
			<tr>
				<td class="t_r">图层名称：</td>
				<td><input type="text" name="name" id="name" value="${layer.name }" class="text validate[required]" disabled="disabled"/></td>
			</tr>
			<tr>
				<td class="t_r">几何类型：</td>
				<td>
					<select type="text" name="geometryType" id="geometryType" class="text" disabled="disabled">
						<c:forEach var="map" items="${geometryType}">
							<option value="${map.key }">${map.value.name }</option>
						</c:forEach>
					</select>
			    </td>
			</tr>
			<tr>
				<td class="t_r">nameField：</td>
				<td>
					<textarea name="tempNameField" id="tempNameField" clos="20" rows="15" disabled="disabled"
						style="width:170px; height:80px; resize:both; overflow:auto;">${layer.nameField }</textarea>
				</td>
			</tr>
			
			<tr>
				<td class="t_r">summaryFields：</td>
				<td>
					<textarea name="tempSummaryFields" id="tempSummaryFields" clos="20" rows="15" disabled="disabled"
						style="width:170px; height:80px; resize:both; overflow:auto;">${layer.summaryFields }</textarea>
				</td>
			</tr>
			
			<tr>
				<td class="t_r">displayFields：</td>
				<td>
					<textarea name="tempDisplayFields" id="tempDisplayFields" clos="20" rows="15" disabled="disabled"
						style="width:170px; height:80px; resize:both; overflow:auto;">${layer.displayFields }</textarea>
				</td>
			</tr>
			
		</table>
   </form> 
</body>
<script type="text/javascript">
	$(function() {
		//设置下拉的值
		if("${layer.geometryType}") {
			$("#geometryType option[value=${layer.geometryType}]").attr("selected",true);
		}
		
		if("${layerTheme.service.registerType}") {
			$("#registerType option[value=${layerTheme.service.registerType}]").attr("selected",true);
		}

	});
</script>
</html>
