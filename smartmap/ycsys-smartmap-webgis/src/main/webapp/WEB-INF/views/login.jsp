<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/taglib.jsp" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title>羽辰智慧林业二三维一体化平台-登陆</title>
	<meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
	<link rel="shortcut icon" href="${res}/images/favicon.ico" />
	<link rel="stylesheet" href="${res}/bootstrap/css/bootstrap.css">
	<link rel="stylesheet" href="${res}/dist/css/AdminLTE.css">
	<link rel="stylesheet" href="${res}/plugins/iCheck/square/blue.css">
</head>
<body class="hold-transition login-page">
<div class="login-box">
	<!-- /.login-logo -->
	<div class="login-box-body">
		<img src="${res}/dist/img/map/logo.png" height="84" width="724" class="login-img" alt="" />
		<form action="${ctx}/login" method="post" class="login-r" id="fm">
			<div class="form-group has-feedback">
				<input type="text" class="form-control" placeholder="账号" name="username" id="usr">
				<span class="glyphicon glyphicon-envelope form-control-feedback"></span>
			</div>
			<div class="form-group has-feedback">
				<input type="password" class="form-control" placeholder="密码" name="password" id="pwd">
				<span class="glyphicon glyphicon-lock form-control-feedback"></span>
			</div>
			<div class="form-group has-feedback" style="overflow:hidden;">
				<input type="text" class="form-control login-form" placeholder="验证码" name="captcha" id="cap">
				<span class="glyphicon glyphicon-picture form-control-feedback"></span>
				<img src="${res}/images/kaptcha.jpg" style="position: absolute;margin-left:12px;cursor:pointer" id="cap_btn"/>
				<!--<input type="button" id="checkCode" class="code"/>-->
			</div>
			<div class="row">
				<!-- /.col -->
				<div class="col-xs-12">
					<input type="button" class="login-btn-l" value="登录" id="sub_btn">
					<button type="reset" class="login-btn-r">重置</button>
				</div>
				<!-- /.col -->
			</div>
		</form>

	</div>
	<!-- /.login-box-body -->
</div>
<!-- /.login-box -->
</body>
<!-- jQuery 2.2.3 -->
<script src="${res}/plugins/jQuery/jquery-2.2.3.min.js"></script>
<!-- Bootstrap 3.3.6 -->
<script src="${res}/bootstrap/js/bootstrap.min.js"></script>
<script>
	$("#cap_btn").click(function(){
		var i = $(this);
		var random = new Date().getTime();
		i.attr("src",i.attr("src").split("?")[0] + "?r=" + random);
	});
	$("#sub_btn").click(function(){
		var usr = $("#usr").val();
		var pwd = $("#pwd").val();
		var cap = $("#cap").val();
		var str = "";
		str = usr == null || usr == ""?"登录名不能为空":pwd == null || pwd == "" ? "密码不能为空": cap == null || cap == "" ?"验证码不能为空":"";
		if(str != ""){
			alert(str);
		}else{
			$("#fm").submit();
		}
	});
	$("#fm").keydown(function(e){
		if(e.keyCode==13){
			$("#sub_btn").click();
		}
	});
</script>
</html>

