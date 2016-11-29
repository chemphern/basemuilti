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

<script src="${res}/js/common/form.js"></script>
<script src="${res}/plugins/jQuery/jquery-2.2.3.min.js"></script>
<script src="${res}/plugins/jquery-validation-1.15.1/lib/jquery.form.js"></script>
<script
	src="${res}/plugins/jquery-validation-1.15.1/dist/jquery.validate.min.js"
	type="text/javascript"></script>

<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
  <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
  <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
  <![endif]-->
</head>
<body>
	<form method="post" id="form_id" enctype="multipart/form-data">
		<input type="hidden" name="id" value="${resource.id}">
		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			class="date_add_table">
			<tr>
				<td width="120" class="t_r">上传节点：</td>
				
				<td><select type="text" name="resourceType.id"
					id="resourceTypeId" class="text">
						<c:forEach var="list" items="${resourceTypeLists }">
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
				<td><input type="text" name="name" id="name" size="20"
					value="${resource.name }" class="text validate[required]"
					validate="{required:true,maxlength : 15,messages:{required:'必填',maxlength:'资源名称 的字符长度大于15个字符！'}}" /></td>
			</tr>
			<tr>
				<td class="t_r">资源全称：</td>
				<td><input type="text" name="fullName" id="fullName" size="50"
					value="${resource.fullName }" class="text validate[required]"
					validate="{required:true,maxlength : 50,messages:{required:'必填',maxlength:'资源全称 的字符长度大于50个字符！'}}"/></td>
			</tr>

			<tr>
				<!--  0:文档资料;1:专题地图;2:网络图集 -->
				<td class="t_r">资源类型：</td>
				<td><select type="text" name="type" id="type" class="text">
						<%-- <option value="0" selected="${resource.type eq '0' ? 'selected':''}">文档资料</option>
						<option value="1" selected="${resource.type eq '1' ? 'selected':''}">专题地图</option>
						<option value="2" selected="${resource.type eq '2' ? 'selected':''}">网络图集</option> --%>
						<option value="0">文档资料</option>
						<option value="1">专题地图</option>
						<option value="2">网络图集</option>
				</select></td>
			</tr>
			<tr>
				<!-- 0：txt;1:zip; -->
				<td class="t_r">详细分类：</td>
				<td><select type="text" name="fileType" id="fileType"
					class="text">
						<c:forEach var="map" items="${fileType }">
							<option value="${map.key }">${map.value.name }</option>	
						</c:forEach>
				</select></td>
			</tr>
			<tr>
				<td class="t_r">选择集群：</td>
				<td><select type="text" name="clusterName" id="clusterName"
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
				<td class="t_r">上传文件：</td>
				<td><input type="file" name="file" id="fileToUpload" src="${resource.filePath }"/></td>
			</tr>
			<tr>
				<td class="t_r">备注：</td>
				<td><textarea name="remarks" clos="20" rows="20"
						class="text_area"
						validate="{maxlength : 100,messages:{maxlength:'备注 的字符长度大于100个字符！'}}">${resource.remarks }</textarea></td>
			</tr>
		</table>
	</form>
</body>
<script>
	$(function() {
		//设置下拉的值
		if("${resource.id}"){
			var type = "${resource.type}";
			var fileType = "${resource.fileType}";
			$("#type option[value="+type+"]").attr("selected",true);
			$("#fileType option[value="+fileType+"]").attr("selected",true);
		}
		
		if("${resourceTypeId}") {
			$("#resourceTypeId option[value=${resourceTypeId}]").attr("selected",true);
		}
		
		var form = $("#form_id");
		var val_obj = exec_validate(form); //方法在 ${res}/js/common/form.js
		form.validate(val_obj);
		
		var parentWin = window.parent;
		var dialog = parentWin.art.dialog.list["editResourceDialog"];
		var dialog_div = dialog.DOM.wrap;
		dialog_div.on("ok", function() {
			var counts = $('div.l-exclamation'); //填的不对的记录数
			if(counts.length < 1) {
				//支持文件上传的ajax提交方式
				$("#form_id").ajaxSubmit({
					url : "${ctx }/resource/save",
					dataType:"json",
	                success:function(result){
	                	alert(result.msg);
	                	if(result.flag=="3"||result.flag=="4") {
		                	parentWin.gridManager.reload();
							dialog.close();
	                	}
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
