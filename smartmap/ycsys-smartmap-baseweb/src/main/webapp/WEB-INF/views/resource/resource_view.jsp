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
				<td width="120" class="t_r">上传节点：</td>
				
				<td><select type="text" name="resourceType.id" disabled="disabled"
					id="resourceTypeId" class="text">
						<c:forEach var="list" items="${lists }">
							<c:if test="${ not empty list.parent }">
								<c:if test="${resource.resourceType.id eq list.id }">
									<option value="${list.id }" selected="selected">${list.name }</option>
								</c:if>
								<c:if test="${resource.resourceType.id ne list.id }">
									<option value="${list.id }">${list.name }</option>
								</c:if>
							</c:if>
						</c:forEach>
				</select></td>
			</tr>
			<tr>
				<td class="t_r">资源名称：</td>
				<td><input type="text" name="name" id="name" size="20" disabled="disabled"
					value="${resource.name }" class="text validate[required]" /></td>
			</tr>
			<tr>
				<td class="t_r">资源全称：</td>
				<td><input type="text" name="fullName" id="fullName" size="50" disabled="disabled"
					value="${resource.fullName }" class="text validate[required]"/></td>
			</tr>
			
			<tr>
				<td class="t_r">资源状态：</td>
				<td><input type="text" name="status" id="status" size="50" disabled="disabled"
					value="${resource.status eq '0' ? '待发布':'已发布' }" class="text validate[required]" /></td>
			</tr>

			<tr>
				<!--  0:文档资料;1:专题地图;2:网络图集 -->
				<td class="t_r">资源类型：</td>
				<td><select type="text" name="type" id="type" class="text" disabled="disabled">
						<option value="0">文档资料</option>
						<option value="1">专题地图</option>
						<option value="2">网络图集</option>
				</select></td>
			</tr>
			<tr>
				<!-- 0：txt;1:zip; -->
				<td class="t_r">详细分类：</td>
				<td><select type="text" name="fileType" id="fileType" disabled="disabled"
					class="text">
						<c:forEach var="map" items="${fileType }">
							<option value="${map.key }">${map.value.name }</option>	
						</c:forEach>
				</select></td>
			</tr>
			
			<tr>
				<td class="t_r">上传状态：</td>
				<td><input type="text" name="uploadStatus" id="status" size="50" disabled="disabled"
					value="${resource.status eq '0' ? '失败':'成功' }" class="text validate[required]" /></td>
			</tr>
			
			<tr>
				<td class="t_r">上传人：</td>
					<td><input type="text" name="name" id="name" size="20" disabled="disabled"
						value="${resource.uploadPerson.name }" class="text validate[required]"/></td>
			</tr>
			
			<tr>
				<td class="t_r">所属部门：</td>
					<td><input type="text" name="orgs" id="orgs" size="20" disabled="disabled"
						value="${orgs}" class="text validate[required]"/></td>
			</tr>
			
			<tr>
				<td class="t_r">上传时间：</td>
				<td><input type="text" name="name" id="name" size="20" disabled="disabled"
					value='<fmt:formatDate value="${resource.uploadDate }" pattern="yyyy-MM-dd HH:mm:ss" />' class="text validate[required]"/></td>
			</tr>
			
			<tr>
				<td class="t_r">选择集群：</td>
				<td><select type="text" name="clusterName" id="clusterName" disabled="disabled"
					class="text">
					<c:forEach var="cn" items="${clusterNames }">
						<c:if test="${cn eq resource.clusterName }">
							<option value="${cn }" selected="selected">${cn }</option>
						</c:if>
						<c:if test="${cn ne resource.clusterName }">
							<option value="${cn }">${cn }</option>
						</c:if>
					</c:forEach>
				</select></td>
			</tr>
			
			<tr>
				<td class="t_r">备注：</td>
				<td><textarea name="remarks" clos="20" rows="20" disabled="disabled"
						class="text_area">${resource.remarks }</textarea></td>
			</tr>
		</table>
	</form>
</body>
<script>
	$(function() {
		//设置下拉的值
		var type = "${resource.type}";
		var fileType = "${resource.fileType}";
		$("#type option[value="+type+"]").attr("selected",true);
		$("#fileType option[value="+fileType+"]").attr("selected",true);
	});
</script>
</html>
