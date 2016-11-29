<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/taglib.jsp" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>羽辰智慧林业综合管理平台系统</title>
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <link rel="shortcut icon" href="${res}/images/favicon.ico" />
    <link rel="stylesheet" href="${res}/bootstrap/css/bootstrap.css">
    <link rel="stylesheet" href="${res}/iconfont/iconfont.css">
    <link rel="stylesheet" href="${res}/dist/css/AdminLTE.css">
    <link rel="stylesheet" href="${res}/dist/css/skins/_all-skins.css">
    <link rel="stylesheet" href="${res}/plugins/iCheck/flat/blue.css">
</head>
<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">

    <jsp:include page="header.jsp"></jsp:include>
    <jsp:include page="left.jsp"></jsp:include>



    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper">
        <!-- Content Header (Page header) -->
        <section class="content-header">
            <h1>
                首页
            </h1>
            <ol class="breadcrumb">
                <li><a href="#"><i class="fa fa-dashboard"></i> 系统首页</a></li>
                <li class="active">首页</li>
            </ol>
        </section>

        <!-- Main content -->
        <section class="content">
            <!-- Small boxes (Stat box) -->
            <!-- /.row -->
            <!-- Main row -->
            <!--主要内容-->
            <div id="yc_main">
                <iframe style="width: 100%;" id="main_iframe" name="main_iframe" src="${ctx}/welcome" frameborder="0" align="center" onload="iframeResize(this)"></iframe>
            </div>
            <!-- /.row (main row) -->

        </section>
        <!-- /.content -->
    </div>
    </div>
   <jsp:include page="footer.jsp"></jsp:include>

   <jsp:include page="right.jsp"></jsp:include>
<!-- ./wrapper -->

<script src="${res}/plugins/jQuery/jquery-2.2.3.min.js"></script>
<!-- jQuery UI 1.11.4 -->
<script src="${res}/plugins/ui/1.11.4/jquery-ui.min.js"></script>
<!-- Resolve conflict in jQuery UI tooltip with Bootstrap tooltip -->
<script>
    $.widget.bridge('uibutton', $.ui.button);
</script>
<!-- Bootstrap 3.3.6 -->
<script src="${res}/bootstrap/js/bootstrap.js"></script>
<!-- jQuery Knob Chart -->
<script src="${res}/plugins/knob/jquery.knob.js"></script>
<!-- AdminLTE App -->
<script src="${res}/dist/js/app.js"></script>
<!-- AdminLTE for demo purposes -->
<script src="${res}/dist/js/demo.js"></script>
<script>
    function iframeResize(iframe) {
        try {
//            var idocumentElement = iframe.contentWindow.document.documentElement;
//            iframe.scrolling = "no";
//            iframe.height = "";
//            if (idocumentElement.scrollHeight > 560) {
//                iframe.height = idocumentElement.scrollHeight + 100;
//            }
//            else {
//                iframe.height = 560;
//            }
            var parent = iframe.contentWindow.parent;
            var aside = parent.$(".main-sidebar");
            var header = parent.$(".main-header");
            var ih = aside.height() - header.height();
            iframe.height = ih;
        }
        catch (e) {
            window.status = 'Error: ' + e.number + '; ' + e.description;
        }
    }
</script>
</body>
</html>