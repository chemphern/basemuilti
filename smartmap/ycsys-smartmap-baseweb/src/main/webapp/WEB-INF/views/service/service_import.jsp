<%@ page contentType="text/html;charset=UTF-8" language="java"
	isELIgnored="false"%>
<%@include file="/WEB-INF/views/common/taglib.jsp"%>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>羽辰智慧林业平台运维管理系统-服务导入</title>
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
	            <td width="120" class="t_r">
	            	选择zip格式文件：
	            </td>
	            <td >
		            <input type="file" name="file" id="serviceFileId" class="text validate[required]" validate="{required:true,messages:{required:'请选择zip格式的文件'}}"/>
		            <span id="fileToUploadTip" style="color: red">*</span>
	            </td>
        	</tr>
        	<tr>
        		<td colspan="2">
        			<a href="javascript:void(0)" id="downLoadTemplate">点击下载模版</a>
        		</td>
        	</tr>
		</table>
	</form>
</body>
<script>
	$(function() {
		var form = $("#form_id");
		var parentWin = window.parent[0];
		var dialog = parentWin.art.dialog.list["importServiceDialog"];
		var dialog_div = dialog.DOM.wrap;
		dialog_div.on("ok", function() {
			form.submit();
		});
		var val_obj = exec_validate(form);//方法在 ${res}/js/common/form.js
		val_obj.submitHandler = function(){
			//支持文件上传的ajax提交方式
			$("#form_id").ajaxSubmit({
				url : "${ctx }/service/importFile",
				dataType:"json",
                success:function(ret){
                	alert(ret.msg);
                	if(ret.flag != 1){
	                	parentWin.gridManager.reload();
						dialog.close();
                	}
                	
                }
             });
	    };
	    form.validate(val_obj);
	    
	    //下载导入模版
	    $("#downLoadTemplate").on("click",function(e) {
	    	e.preventDefault();
	    	window.location.href="${ctx}/service/downLoadTemplate";
	    });
	});
</script>
</html>
