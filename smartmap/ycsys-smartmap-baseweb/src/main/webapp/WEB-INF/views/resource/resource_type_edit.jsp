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
<script src="${res}/plugins/jquery-validation-1.15.1/dist/jquery.validate.min.js"></script>
<script src="${res}/plugins/jquery-validation-1.15.1/lib/jquery.form.js"></script>
<script src="${res}/js/common/form.js"></script>



<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
  <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
  <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
  <![endif]-->
</head>
<body>
	<form method="post" id="form_id">
		<input type="hidden" name="id" value="${resourceType.id}">
		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			class="date_add_table">

			<tr>
				<td width="120" class="t_r">上级节点：</td>
				<td><input type="text" name="parent.name" id="parent.name"
					disabled="disabled" class="text validate[required]"
					value="${resourceType.parent.name}" /></td>
				<input type="hidden" name="parent.id"
					value="${resourceType.parent.id}">
			</tr>
			<tr>
				<td class="t_r">结点名称：</td>
				<td><input type="text" name="name" id="name" size="15"
					value="${resourceType.name }" class="text validate[required]"
					validate="{required:true,maxlength:15,messages:{required:'必填',maxlength:'结点名称 的字符长度大于15个字符！'}}" />
				<span style="color: red">*</span>
				</td>
			</tr>

			<tr>
				<td class="t_r">备注：</td>
				<td><textarea name="remarks" id="remarks" clos="20" rows="15"
						style="width:170px; height:80px; resize:both; overflow:auto;" 
						validate="{maxlength : 100,messages:{maxlength:'备注的字符长度大于100个字符！'}}">${resourceType.remarks }</textarea></td>
			</tr>
		</table>
	</form>
</body>
<script>
	$(function() {
		var form = $("#form_id");
		var parentWin = window.parent[0];
		var dialog = parentWin.art.dialog.list["editResourceTypeDialog"];
		var dialog_div = dialog.DOM.wrap;
		
		dialog_div.on("ok", function() {
			form.submit();
		});
		var val_obj = exec_validate(form);//方法在 ${res}/js/common/form.js
		val_obj.submitHandler = function(){
			$.ajax({
				type : "POST",
				url : "${ctx }/resourceType/save",
				data : $('#form_id').serialize(),
				dataType:"json",
				async : false,
				error : function(ret) {
					//console.log(ret);
					alert("connection error!");
				},
				success : function(ret) {
					alert(ret.msg);
					if(ret.flag=="1") {
						parentWin.treeManager.reload();
						dialog.close();
					}
					parentWin.resource_type.hideBtn(); //隐藏新增按钮
				}
			});
	    };
	    form.validate(val_obj);
		
	});
</script>
</html>
