<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/views/common/taglib.jsp" %>
<!DOCTYPE html>
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
    <link href="${res}/plugins/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css"/>
    <!-- 弹出框 -->
    <link href="${res}/plugins/dialog/dialog.css" rel="stylesheet" type="text/css">

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
    <style>
        html, body {
            background-color: #f1f1f1;
        }

        body {
            overflow-y: hidden;
        }
    </style>
</head>
<body>
        <!-- Content Header (Page header) -->
        <section class="content-header">
            <h1>
                平台监控配置
            </h1>
        </section>
<div class="row">
    <div class="col-md-3 col-sm-4">
        <div class="box box-solid">
            <div class="box-header with-border">
                <h4 class="box-title">分类</h4>
            </div>
            <div class="box_l">
                <ul id="tree">

                </ul>
            </div>
            <!-- /.box-body -->
        </div>
    </div>
    <!-- /.col -->
    <div class="col-md-9 col-sm-8">
        <div class="box box-solid">
			<div class="box_l">
                <div class="list" id="targetDiv"></div>
            </div>
        </div>
        <!-- /.col -->
    </div>
</div>
</body>
<script src="${res}/plugins/jQuery/jquery-2.2.3.min.js"></script>
<script src="${res}/plugins/ligerUI/js/core/base.js" type="text/javascript"></script>
<script src="${res}/plugins/ligerUI/js/plugins/ligerGrid.js" type="text/javascript"></script>
<script src="${res}/plugins/ligerUI/js/plugins/ligerDrag.js" type="text/javascript"></script>
<script src="${res}/plugins/ligerUI/js/plugins/ligerMenu.js" type="text/javascript"></script>
<script src="${res}/plugins/ligerUI/js/plugins/ligerTree.js" type="text/javascript"></script>
<script src="${res}/plugins/ligerUI/js/plugins/CustomersData.js" type="text/javascript"></script>
<script src="${res}/bootstrap/js/bootstrap.min.js"></script>
<script src="${res}/dist/js/app.js"></script>
<script src="${res}/dist/js/demo.js"></script>
<script type="text/javascript" src="${res}/plugins/dialog/jquery.artDialog.source.js"></script>
<script type="text/javascript" src="${res}/plugins/dialog/iframeTools.source.js"></script>
<script type="text/javascript" src="${res}/plugins/dialog/unit.js"></script>
<script>
    $(function () {
        var data = [];
        data.push({id: -1, pid: -2, text: '平台监控分类'});
        data.push({id: 1, pid: -1, text: '服务器性能监控'});
        data.push({id: 0, pid: -1, text: '服务性能监控'});
        data.push({id: 2, pid: 0, text: 'tomcat'});
        data.push({id: 3, pid: 0, text: 'oracle'});
        data.push({id: 4, pid: 0, text: 'arcgis'});
        $("#tree").ligerTree({
            nodeWidth: 160,
            data: data,
            checkbox: false,
            idFieldName: 'id',
            isExpand: true,
            slide: false,
            parentIDFieldName:'pid',
            onClick:function(a){
                //能点击的
                if(a.data.id > 0){
                    var ifm = $('<iframe frameborder="0" align="center" onload="resizeIframe(this)" width="100%" id="right_iframe" style="overflow-y:hidden"/>').attr("src","${ctx}/platMonitorConfig/listMonitorService?type=" + a.data.id);
                    $("#targetDiv").html(ifm);
                }
            }
        });
        var m = $("#tree").ligerGetTreeManager();
        m.getNodeDom(1).click();
    });
    function resizeIframe(i){
        var parent = i.contentWindow.parent.parent;
        var aside = parent.$(".main-sidebar");
        var header = parent.$(".main-header");
        var ih = aside.height() - header.height();
        i.height = ih;
    }
</script>
</html>
