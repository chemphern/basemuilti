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
    <link href="${res}/plugins/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
    <link href="${res}/plugins/dialog/dialog.css" rel="stylesheet" type="text/css">
</head>
<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">

    <jsp:include page="header.jsp"></jsp:include>
    <jsp:include page="left.jsp"></jsp:include>



    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper">


        <!-- Main content -->
        <section class="content">
            <!-- Small boxes (Stat box) -->
            <!-- /.row -->
            <!-- Main row -->
            <!--主要内容-->
            <div id="yc_main">
                <iframe class="firefoxIframeW" id="main_iframe" name="main_iframe" src="${ctx}/welcome" frameborder="0" align="center" onload="iframeResize(this)"></iframe>
            </div>
            <!-- /.row (main row) - ->

        </section>
        <!-- /.content -->
    </div>
    </div>

   <jsp:include page="right.jsp"></jsp:include>
<!-- ./wrapper -->

<script src="${res}/plugins/jQuery/jquery-2.2.3.min.js"></script>
<!-- jQuery UI 1.11.4 -->
<script src="${res}/plugins/jQueryUI/jquery-ui.min.js"></script>
<!-- Resolve conflict in jQuery UI tooltip with Bootstrap tooltip -->
<script>
    $.widget.bridge('uibutton', $.ui.button);
</script>
<!-- Bootstrap 3.3.6 -->
<script src="${res}/bootstrap/js/bootstrap.js"></script>
<!-- AdminLTE App -->
<script src="${res}/dist/js/app.js"></script>
 <!-- cookies -->
<script src="${res}/dist/js/jquery.cookie.js"></script>
<!-- AdminLTE for demo purposes -->
<script src="${res}/dist/js/demo.js"></script>
<script type="text/javascript" src="${res}/plugins/dialog/jquery.artDialog.source.js"></script>
<script type="text/javascript" src="${res}/plugins/dialog/iframeTools.source.js"></script>
<script type="text/javascript" src="${res}/plugins/dialog/unit.js"></script>
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