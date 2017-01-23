<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/views/common/taglib.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>羽辰智慧林业平台运维管理系统-运维监控日志</title>
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <link rel="shortcut icon" href="${res}/images/favicon.ico"/>
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
    <style>
        html, body {
            background-color: #f1f1f1
        }

        body {
            overflow-y: hidden;
        }
    </style>
</head>
<body class="hold-transition skin-blue sidebar-mini">
<section class="content-header">
    <h1>
        运维监控日志
    </h1>
</section>
<div class="row">
    <div class="col-md-12">
        <div class="box box-solid">
            <div class="box-header with-border">
                <h4 class="box-title">运维监控日志列表</h4>
                <div class="btn_box">
                    <button onclick="exportLog()"><i class="iconfont icon-angle-double-up"></i>导出</button>
                </div>
            </div>
            <div class="box_l">
                <div class="list" id="maingrid4"></div>
            </div>
        </div>
    </div>
    <!-- /.col -->
</div>

<!-- jQuery 2.2.3 -->
<script src="${res}/plugins/jQuery/jquery-2.2.3.min.js"></script>

<!--grid-->
<script src="${res}/plugins/ligerUI/js/core/base.js" type="text/javascript"></script>
<script src="${res}/plugins/ligerUI/js/plugins/ligerGrid.js" type="text/javascript"></script>
<script src="${res}/plugins/ligerUI/js/plugins/ligerDrag.js" type="text/javascript"></script>
<script src="${res}/plugins/ligerUI/js/plugins/ligerMenu.js" type="text/javascript"></script>
<!-- Bootstrap 3.3.6 -->
<script src="${res}/bootstrap/js/bootstrap.min.js"></script>
<!-- jQuery Knob Chart -->
<!-- Slimscroll 滚动条 -->
<!-- AdminLTE App -->
<script src="${res}/dist/js/app.js"></script>
<!-- AdminLTE for demo purposes -->
<script src="${res}/dist/js/demo.js"></script>
<!-- 封装弹出框dialog -->
<script type="text/javascript" src="${res}/plugins/dialog/jquery.artDialog.source.js"></script>
<script type="text/javascript" src="${res}/plugins/dialog/iframeTools.source.js"></script>
<script type="text/javascript" src="${res}/plugins/dialog/unit.js"></script>

</body>
<script type="text/javascript">
    Date.prototype.Format = function(fmt)
    { //author: meizz
        var o = {
            "M+" : this.getMonth()+1,                 //月份
            "d+" : this.getDate(),                    //日
            "h+" : this.getHours(),                   //小时
            "m+" : this.getMinutes(),                 //分
            "s+" : this.getSeconds(),                 //秒
            "q+" : Math.floor((this.getMonth()+3)/3), //季度
            "S"  : this.getMilliseconds()             //毫秒
        };
        if(/(y+)/.test(fmt))
            fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));
        for(var k in o)
            if(new RegExp("("+ k +")").test(fmt))
                fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
        return fmt;
    };
        //表格列表
        $(function () {
            $("#maingrid4").ligerGrid({
                checkbox: false,
                columns: [
                    {display: '日志时间', name: 'createTime',render:function(rowdata,rowindex,value){
                        var d = new Date(value).Format("yyyy-MM-dd hh:mm:ss");
                        return d;
                    }},
                    {display: '日志内容', name: 'operationName'},
                    {
                        display: '日志类型', name: 'operationType', render: function (rowdata, rowindex, value) {
                        var levels = ["基础类", "基础类", "服务管理类","资源管理类","统计分析类","图层类","监控类"];
                        return "调用" + levels[parseInt(value)] + "接口";
                    }
                    },
                    {display: '操作人', name: 'username'},
                    {display: '请求IP', name: 'requestIp'},
                    {
                        display: '日志状态', name: 'status', render: function (rowdata, rowindex, value) {
                        var levels = ["正常", "正常", "异常"];
                        return levels[parseInt(value)];
                    }
                    },
                    {display: '备注', name: 'remark'}
                ], pageSize: 30,
                url: "${ctx}/log/listData",
                width: '100%', height: '98%'
            });
        });
    function getLigerManager() {
        return $("#maingrid4").ligerGetGridManager();
    }
    function exportLog(){
        var dialog = $.Layer.iframe(
            {
                id:"exportLog",
                title: '导出运维监控日志',
                url:'${ctx}/log/exportv',
                width: 400,
                height: 220
            });
    }
</script>
</html>