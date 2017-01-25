<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/views/common/taglib.jsp" %>
<%response.setStatus(200);%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title>错误500</title>
	<!-- Tell the browser to be responsive to screen width -->
	<meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
	<link rel="shortcut icon" href="${res}/images/favicon.ico" />
	<!-- Bootstrap 3.3.6 -->
	<link rel="stylesheet" href="${res}/bootstrap/css/bootstrap.css">
	<!-- Theme style -->
	<link rel="stylesheet" href="${res}/dist/css/AdminLTE.css">
	<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
	<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
	<%--<!--[if lt IE 9]>--%>
	<%--<script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>--%>
	<%--<script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>--%>
	<%--<![endif]-->--%>
	<style type="text/css">
		.error{width:500px; margin:8% auto;}
		.error ul{list-style: none; margin:0; padding:0; font-family:微软雅黑;}
		.error h4{font-size: 20px; font-weight: bold; padding-left: 70px; margin-top: 30px;}
		.error li{ padding-left: 70px; line-height: 22px;}
	</style>
</head>
<body>
<div class="error">
	<img src="${res}/dist/img/500.png" height="260" width="500" alt="" />
	<h4>抱歉~ 服务器内部错误，崩溃了~</h4>
	<%--<ul>--%>
		<%--<li>10秒之后会制动跳转，您还可以：</li>--%>
		<%--<li>01）<a href="">返回首页</a></li>--%>
		<%--<li>02）去其他地方逛逛：<a href="">返回上一个页面</a></li>--%>
	<%--</ul>--%>
</div>
</body>
</html>
