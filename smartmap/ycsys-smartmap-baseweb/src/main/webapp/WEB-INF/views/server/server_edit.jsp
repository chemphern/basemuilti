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
<%-- <script src="${res}/plugins/dialog/iframeTools.source.js"></script>
<script src="${res}/plugins/dialog/jquery.artDialog.source.js"></script> --%>

<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
  <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
  <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
  <![endif]-->
</head>
<body>
    <form method="post" id="form_id" enctype="multipart/form-data">
		<input type="hidden" name="id" value="${server.id}">
		<table width="100%" border="0" cellpadding="0" cellspacing="0" class="date_add_table">
		  <%-- <tr>
		    <td class="t_r">是否取自服务器引擎配置：</td>
		    <td><input type="text" placeholder="只能填是或否" name="fromServerEngineFlag" 
		    value="${server.fromServerEngineFlag}" id="fromServerEngineFlag" /></td>
		  </tr> --%>
		  <tr>
		  	<td class="t_r">是否取自服务器引擎配置：</td>
			<td>
			  <select type="text" name="fromServerEngineFlag" id="fromServerEngineFlag" class="text">
				<option value="0" >是</option>
				<option value="1" >否</option>
			  </select>
			</td>
		  </tr>
		  <tr>
		    <td class="t_r">服务器引擎：</td>
		    <td>
		    <select type="text" name="serverEngine.id"  id="serverEngineId" class="text">
		      <c:forEach var="list" items="${lists}">
				<c:if test="${ not empty list }">
					<c:if test="${server.serverEngine.id eq list.id }">
						<option value="${list.id }" selected="selected">${list.configName }</option>
					</c:if>
					<c:if test="${server.serverEngine.id ne list.id }">
						<option value="${list.id }">${list.configName }</option>
					</c:if>
				</c:if>
			  </c:forEach>
		    </select>
		    </td>
		  </tr>
		  <tr>
		    <td class="t_r">服务器名称：</td>
		    <td><input type="text" name="name" value="${server.name}" id="name" /></td>
		  </tr>
		  <tr>
		    <td class="t_r">服务器IP地址：</td>
		    <td><input type="text" name="ipAddress" value="${server.ipAddress}" id="ipAddress" class="text" /></td>
		  </tr>
		  <tr>
		    <td class="t_r">SNMP协议端口：</td>
		    <td>
		    	<%-- <input type="Number" placeholder="请输入正整数" value="${server.snmpPort}" name="snmpPort" id="snmpPort" /> --%>
		    	<input 
		    	value="${server.snmpPort}" name="snmpPort" id="snmpPort" placeholder="请输入正整数"
		    	onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}" 
		    	onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}">
		    </td>
		  </tr>
		  <tr>
		    <td class="t_r">服务器类型：</td>
		    <td>
		    <select type="text" name="serverType"  id="serverType" class="text">
				<c:forEach var="map" items="${serverType}">
						<option value="${map.key }">${map.value.name }</option>
				</c:forEach>
		    </select>
		    </td>
		  </tr>
		  <tr>
		    <td class="t_r">备注：</td>
		    <td><textarea name="remarks" cols="20" rows="5" class="text_area">${server.remarks}</textarea></td>
		  </tr>
		</table>
	</form>
</body>
<script >
$(function() {
	//设置下拉的值
	if("${server.id}"){
		var fromServerEngineFlag = "${server.fromServerEngineFlag}";
		var serverType = "${server.serverType}";
		$("#fromServerEngineFlag option[value="+fromServerEngineFlag+"]").attr("selected",true);
		$("#serverType option[value="+serverType+"]").attr("selected",true);
	}
	
	
	var form = $("#form_id");
	var val_obj = exec_validate(form); //方法在 ${res}/js/common/form.js
	form.validate(val_obj);
	
	var parentWin = window.parent;
	var dialog = parentWin.art.dialog.list["editServerDialog"];
	var dialog_div = dialog.DOM.wrap;
	dialog_div.on("ok", function() {
		var counts = $('div.l-exclamation'); //填的不对的记录数
		if(counts.length < 1) {
			//支持文件上传的ajax提交方式
			$("#form_id").ajaxSubmit({
				url : "${ctx}/server/save",
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
