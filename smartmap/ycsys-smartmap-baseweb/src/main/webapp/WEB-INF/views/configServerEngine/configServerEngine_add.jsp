<%@ page contentType="text/html;charset=UTF-8" language="java"
	isELIgnored="false"%>
<%@include file="/WEB-INF/views/common/taglib.jsp"%>
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

<script src="${res}/js/common/form.js"></script>
<script src="${res}/plugins/jQuery/jquery-2.2.3.min.js"></script>
<script src="${res}/plugins/jquery-validation-1.15.1/lib/jquery.form.js"></script>
<script src="${res}/plugins/jquery-validation-1.15.1/dist/jquery.validate.min.js" type="text/javascript"></script>
<!-- 封装弹出框dialog -->
<script type="text/javascript" src="${res}/plugins/dialog/jquery.artDialog.source.js"></script>
<script type="text/javascript" src="${res}/plugins/dialog/iframeTools.source.js"></script>
<script type="text/javascript" src="${res}/plugins/dialog/unit.js"></script>
<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
  <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
  <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
  <![endif]-->
    <style>
        html,body{
            background-color: #ecf0f5
        }
        body{
            overflow-y: hidden;
        }
        </style>
</head>
<body>
	<form method="post" id="form_id" enctype="multipart/form-data" >
		<input type="hidden" name="id" value="${configServerEngine.id}">
		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			class="date_add_table">
			<tr>
				<td class="t_r">配置名称：</td>
				<td><input type="text" name="configName" id="configName"
					value="${configServerEngine.configName}" class="text validate[required]" /></td>
			</tr>
			<tr>
				<td width="120" class="t_r">引擎类型：</td>
				<td>
				<select type="text" name="engineType" id="engineType" class="text">
						<c:forEach var="map" items="${engineType}">
							<option value="${map.key }">${map.value.name }</option>
						</c:forEach>
				</select>
				</td>
			</tr>
			<tr>
				<!--  0:单机;1:集群; -->
				<td class="t_r">集成模式：</td>
				<td><select type="text" name="integrationModel" id="integrationModel"
					class="text">
						<option value="0" >单机</option>
						<option value="1" >集群</option>
				</select></td>
			</tr>
			<tr>
				<td class="t_r">机器名：</td>
				<td><input type="text" name="machineName" id="machineName"
					value="${configServerEngine.machineName}" class="text validate[required]" 
					validate="{required:true,maxlength : 50,messages:{required:'必填',maxlength:'机器名 的字符长度大于50个字符！'}}"/></td>
			</tr>
			<tr>
				<td class="t_r">内网IP：</td>
				<td><input type="text" name="intranetIp" id="intranetIp"
					value="${configServerEngine.intranetIp}"  /></td>
			</tr>
			<tr>
				<td class="t_r">内网端口：</td>
				<td>
					<%-- <input type="text" name="intranetPort" id="intranetPort"
					placeholder="请输入正整数" value="${configServerEngine.intranetPort}" /> --%>
					<input 
			    	value="${configServerEngine.intranetPort}" name="intranetPort" id="intranetPort" placeholder="请输入正整数"
			    	onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}" 
			    	onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}">
				</td>
			</tr>
			<tr>
				<td class="t_r">引擎管理员：</td>
				<td><input type="text" name="engineManager" id="engineManager"
					value="${configServerEngine.engineManager}" /></td>
			</tr>
			<tr>
				<td class="t_r">管理密码：</td>
				<td><input type="text" name="managerPassword" id="managerPassword"
					value="${configServerEngine.managerPassword}" /></td>
			</tr>
			<tr>
				<td class="t_r">上传服务地址：</td>
				<td><input type="text" name="dataUploadPath" id="dataUploadPath"
					value="${configServerEngine.dataUploadPath}" /></td>
			</tr>
			<tr>
				<td class="t_r">上传绝对路径：</td>
				<td><input type="text" name="dataUploadRealPath" id="dataUploadRealPath"
					value="${configServerEngine.dataUploadRealPath}" /></td>
			</tr>
			<tr>
				<td class="t_r">运行状态：</td>
				<td><select type="text" name="runningStatus" id="runningStatus" class="text">
						<option value="0" >启用</option>
						<option value="1" >禁用</option>
				</select></td>
			</tr>
		</table>
	</form>
</body>
<script >
$(function() {
	//设置下拉的值
	if("${configServerEngine.id}"){
		var integrationModel = "${configServerEngine.integrationModel}";
		var runningStatus = "${configServerEngine.runningStatus}";
		var engineType = "${configServerEngine.engineType}";
		$("#integrationModel option[value="+integrationModel+"]").attr("selected",true);
		$("#runningStatus option[value="+runningStatus+"]").attr("selected",true);
		$("#engineType option[value="+engineType+"]").attr("selected",true);
	}
	
	/* if("${engineType}") {
		$("#engineType option[value=${engineType}]").attr("selected",true);
	}  */
	
	var form = $("#form_id");
	var val_obj = exec_validate(form); //方法在 ${res}/js/common/form.js
	form.validate(val_obj);
	
	var parentWin = window.parent[0];
	var dialog = parentWin.art.dialog.list["editConfigServerEngineDialog"];
	var dialog_div = dialog.DOM.wrap;
	dialog_div.on("ok", function() {
		var counts = $('div.l-exclamation'); //填的不对的记录数
		if(counts.length < 1) {
			//支持文件上传的ajax提交方式
			$("#form_id").ajaxSubmit({
				url : "${ctx }/configServerEngine/save",
                success:function(data){
                	dialog.close();
                	if(data=='success'){
                		$.Layer.confirm({
        	                msg:"保存成功",
        	                fn:function(){
        	                	parentWin.gridManager.reload();
        	                }
        	            });
                	} 
                	//parentWin.gridManager.reload();
					//dialog.close();
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
