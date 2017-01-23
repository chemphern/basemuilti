<%@ page contentType="text/html;charset=UTF-8" language="java"
	isELIgnored="false"%>
<%@include file="/WEB-INF/views/common/taglib.jsp"%>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>羽辰智慧林业平台运维管理系统-服务查看</title>
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
				<td class="t_r">服务注册名称：</td>
				<td><input type="text" name="name" id="name" size="20" disabled="disabled"
					value="${service.registerName }" class="text validate[required]" /></td>
			</tr>
			<tr>
				<td class="t_r">服务显示名称：</td>
				<td><input type="text" name="showName" id="showName" value="${service.showName }"
					class="text validate[required]" disabled="disabled"/></td>
			</tr>
			<tr>
                <td class="t_r">服务访问地址：</td>
                <td><input type="text" name="serviceVisitAddress" value="${service.serviceVisitAddress }"
						id="serviceVisitAddress" class="text validate[required]" disabled="disabled"/></td>
              </tr>
              
              <tr>
                <td class="t_r">远程服务类型：</td>
                <td>
                <select type="text" name="remoteServicesType" id="remoteServicesType"
						class="text" disabled="disabled">
                	<c:forEach var="map" items="${remoteServicesType }">
						<option value="${map.key }">${map.value.name }</option>	
					</c:forEach>
                </select>
                </td>
              </tr>
              <tr>
                <td class="t_r">服务功能类型：</td>
                <td>
	                <select type="text" name="functionType" id="functionType"
							class="text" disabled="disabled">
	                  <c:forEach var="map" items="${serviceFunctionType }">
							<option value="${map.value.name }">${map.value.name }</option>
						</c:forEach>
	                </select>
               </td>
						
              </tr>
              <tr>
                <td class="t_r">拓展功能类型：</td>
                <td>
                	<c:forEach var="map" items="${serviceExtendType }">
                		<input type="checkbox" name="serviceExtend" value="${map.key }"/><span>${map.value.name }</span>
                	</c:forEach>
                <br /></td>
              </tr>
              <tr>
                <td class="t_r">服务缓存：</td>
                <td>
                <select type="text" name="cacheType" id="cacheType"
						class="text" disabled="disabled">
                	<c:forEach var="map" items="${serviceCacheType }">
						<option value="${map.key }">${map.value.name }</option>	
					</c:forEach>
                </select>
                </td>
              </tr>
              <tr>
                <td class="t_r">服务权限类型：</td>
                <td>
                <select type="text" name="permissionStatus" id="permissionStatus"
						class="text" disabled="disabled">
                	<c:forEach var="map" items="${permissionStatus }">
						<option value="${map.key }">${map.value.name }</option>	
					</c:forEach>
                </select>
                </td>
              </tr>
              
              <tr>
                <td class="t_r">元数据访问地址：</td>
                <td><input type="text" name="metadataVisitAddress" id="metadataVisitAddress" disabled="disabled"
						 class="text validate[required]" value="${service.metadataVisitAddress }"/></td>
              </tr>
              <tr>
                <td class="t_r">服务分类：</td>
                <td>
	                <select type="text" name="registerType" id="registerType"
							class="text" disabled="disabled">
	                	<c:forEach var="map" items="${registerType }">
							<option value="${map.key }">${map.value.name }</option>	
						</c:forEach>
					</select>
                </td>
              </tr>
              
			<tr>
				<td class="t_r">备注：</td>
				<td><textarea name="remarks" clos="20" rows="20" disabled="disabled"
						class="text_area">${service.remarks }</textarea></td>
			</tr>
		</table>
	</form>
</body>
<script>
	$(function() {
		//设置下拉的值
		if("${service.remoteServicesType}") {
			$("#remoteServicesType option[value=${service.remoteServicesType}]").attr("selected",true);
		}
		
		if("${service.functionType}") {
			$("#functionType option[value=${service.functionType}]").attr("selected",true);
		}
		
		if("${service.cacheType}") {
			$("#cacheType option[value=${service.cacheType}]").attr("selected",true);
		}
		
		if("${service.permissionStatus}") {
			$("#permissionStatus option[value=${service.permissionStatus}]").attr("selected",true);
		}
		if("${service.registerType}") {
			$("#registerType option[value=${service.registerType}]").attr("selected",true);
		}
		
		
		var exNameStr ="${service.serviceExtend}";
		$("input[name = serviceExtend]").each(function() {
			if(exNameStr.indexOf($(this).val()) > -1) {
				$(this).attr("checked",true);
			}
		});
	});
</script>
</html>
