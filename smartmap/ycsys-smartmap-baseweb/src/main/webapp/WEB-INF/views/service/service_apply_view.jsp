<%@ page contentType="text/html;charset=UTF-8" language="java"
	isELIgnored="false"%>
<%@include file="/WEB-INF/views/common/taglib.jsp"%>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>羽辰智慧林业综合管理平台-资源管理</title>
<!-- Tell the browser to be responsive to screen width -->
<meta
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"
	name="viewport">
<!-- iconfont -->
<link rel="stylesheet" href="${res }/iconfont/iconfont.css">
<!-- Theme style -->
<link rel="stylesheet" href="${res }/dist/css/AdminLTE.css">

<link href="${res}/plugins/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<script src="${res}/plugins/jQuery/jquery-2.2.3.min.js"></script>
<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
  <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
  <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
  <![endif]-->
</head>
<body>
	<form method="post" id="form_id" enctype="multipart/form-data">
		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			class="date_add_table">
			<tr>
				<td width="120" class="t_r">标题</td>
				<td><input type="text" name="title" id="title"
					disabled="disabled" class="text validate[required]"
					value="${serviceApply.title}" /></td>
			</tr>
			
			<tr>
				<td width="120" class="t_r">待审服务</td>
				<td><input type="text" name="title" id="title"
					disabled="disabled" class="text validate[required]"
					value="${serviceApply.service.showName}" /></td>
			</tr>
			
			<tr>
				<td class="t_r">有效期限：</td>
				<td>
					<select type="text" name="validDate" id="validDate" disabled="disabled"
						class="text">
							<c:forEach var="map" items="${validDate }">
								<option value="${map.key }">${map.value.name }</option>	
							</c:forEach>
					</select>
					</td>
			</tr>
			
			<tr>
				<td width="120" class="t_r">审核结果</td>
				<td>
					<c:forEach var="map" items="${auditStatus }" begin="1">
						${map.value.name }<input type="radio" name="auditStastus" value="${map.key }">
					</c:forEach>
					
					</td>
			</tr>

			<tr>
				<td class="t_r">审核意见：</td>
				<td><textarea name="auditOption" id="auditOption" clos="20" rows="15" disabled="disabled"
						class="text_area"
						validate="{maxlength : 100,messages:{maxlength:'审核意见的字符长度大于100个字符！'}}">${serviceApply.auditOption }</textarea></td>
			</tr>
		</table>
	</form>
</body>
<script>
	$(function() {
		//设置下拉的值
		//var validDate = "${serviceApply.validDate}";
		//var fileType = "${serviceApply.fileType}";
		//$("#validDate option[value="+validDate+"]").attr("selected",true);
		//$("#fileType option[value="+fileType+"]").attr("selected",true);
	});
</script>
</html>
