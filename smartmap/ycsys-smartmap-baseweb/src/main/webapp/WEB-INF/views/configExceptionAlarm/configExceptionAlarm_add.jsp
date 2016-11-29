<%@ page contentType="text/html;charset=UTF-8" language="java"
	isELIgnored="false"%>
<%@include file="/WEB-INF/views/common/taglib.jsp"%>
<!DOCTYPE html>
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
    <form method="post" id="form_id">
		<input type="hidden" name="id" value="${configExceptionAlarm.id}">
		<table width="100%" border="0" cellpadding="0" cellspacing="0" class="date_add_table">
		  <tr>
		    <td class="t_r">选择部门：</td>
		    <td>
		    <select type="text" name="org.id" id="orgId" class="text">
		      <!-- <option>所有</option>
		      <option>防火墙</option> -->
		      <c:forEach var="list" items="${orgLists }">
				<c:if test="${ not empty list }">
					<c:if test="${configExceptionAlarm.org.id eq list.id }">
						<option value="${list.id }" selected="selected">${list.name }</option>
					</c:if>
					<c:if test="${configExceptionAlarm.org.id ne list.id }">
						<option value="${list.id }">${list.name }</option>
					</c:if>
				</c:if>
			  </c:forEach>
		    </select>
		    </td>
		  </tr>
		  <tr>
		    <td class="t_r">用户名称：</td>
		    <td>
		    <select type="text" name="user.id" id="userId" class="text">
		      <c:forEach var="list" items="${lists }">
				<c:if test="${ not empty list }">
					<c:if test="${configExceptionAlarm.user.id eq list.id }">
						<option value="${list.id }" selected="selected">${list.name }</option>
					</c:if>
					<c:if test="${configExceptionAlarm.user.id ne list.id }">
						<option value="${list.id }">${list.name }</option>
					</c:if>
				</c:if>
			  </c:forEach>
		    </select>
		    </td>
		  </tr>
		  <tr>
		    <td class="t_r">时间间隔：</td>
		    <td><input type="text" placeholder="请输入正整数" name="intervalTime" id="intervalTime" 
		    value="${configExceptionAlarm.intervalTime}" class="text validate[required]" /><i>(小时)</i></td>
		  </tr>
		  <tr>
		    <td class="t_r">发送方式：</td>
		    <td>
		    <select type="text" name="sendWay" id="sendWay" class="text">
		      <option value="0" >电子邮件</option>
			  <option value="1" >手机短信</option>
		    </select>
		    </td>
		  </tr>
		  <tr>
		    <td class="t_r">邮件地址：</td>
		    <td><input type="email" name="mailAddress" id="mailAddress" 
		    value="${configExceptionAlarm.mailAddress}" class="text validate[required]" />
		    <i>请正确填写邮箱地址</i></td>
		  </tr>
		  <tr>
		    <td class="t_r">电话号码：</td>
		    <td><input type="text" name="phone" id="phone" 
		    value="${configExceptionAlarm.phone}" class="text validate[required]" /></td>
		  </tr>
		  <tr>
		    <td class="t_r">异常类型：</td>
		    <td><input type="text" name="ruleType" id="ruleType" 
		    value="${configExceptionAlarm.ruleType}" class="text validate[required]" /></td>
		  </tr>
		  <!-- <tr>
			<td class="t_r">异常类型：</td>
			<td>
				<select type="text" name="ruleType" id="ruleType" class="text">
					<option value="0" >服务异常</option>
					<option value="1" >网络异常</option>
					<option value="2" >数据库异常</option>
					<option value="3" >应用服务器异常</option>
					<option value="4" >GIS服务器异常</option>
				</select>
			</td>
		  </tr> -->
		  <tr>
			<td class="t_r">用户等级：</td>
			<td>
				<select type="text" name="userGrade" id="userGrade" class="text">
					<option value="0" >第一级</option>
					<option value="1" >第二级</option>
					<option value="2" >第三级</option>
				</select>
			</td>
		  </tr>
		</table>
</form>
</body>
<script >
	$(function() {
		//设置下拉的值
		if("${configExceptionAlarm.id}"){
			var sendWay = "${configExceptionAlarm.sendWay}";
			var ruleType = "${configExceptionAlarm.ruleType}";
			var userGrade = "${configExceptionAlarm.userGrade}";
			$("#sendWay option[value="+sendWay+"]").attr("selected",true);
			$("#ruleType option[value="+ruleType+"]").attr("selected",true);
			$("#userGrade option[value="+userGrade+"]").attr("selected",true);
		}
		
		var form = $("#form_id");
		var val_obj = exec_validate(form); //方法在 ${res}/js/common/form.js
		form.validate(val_obj);
		
		var parentWin = window.parent;
		var dialog = parentWin.art.dialog.list["addConfigExceptionAlarmDialog"];
		var dialog_div = dialog.DOM.wrap;
		dialog_div.on("ok", function() {
			var counts = $('div.l-exclamation'); //填的不对的记录数
			if(counts.length < 1) {
				//支持文件上传的ajax提交方式
				$("#form_id").ajaxSubmit({
					url : "${ctx }/configExceptionAlarm/save",
	                success:function(data){
	                	parentWin.gridManager.reload();
						dialog.close();
	                 }//,
	                 //dataType:"json"
	             });
			}
			else {
				alert("请重新填写那些有误的信息！");
			}
		});
		
	});
</script>
</html>
