<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/views/common/taglib.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>羽辰智慧林业平台运维管理系统-服务监控</title>
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <link rel="shortcut icon" href="${res}/images/favicon.ico" />
    <link rel="stylesheet" href="${res}/bootstrap/css/bootstrap.css">
    <link rel="stylesheet" href="${res}/iconfont/iconfont.css">
    <link rel="stylesheet" href="${res}/dist/css/AdminLTE.css">
    <link rel="stylesheet" href="${res}/dist/css/skins/_all-skins.css">
    <link href="${res}/plugins/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
    <link href="${res}/plugins/dialog/dialog.css" rel="stylesheet" type="text/css">
        <style>
        html,body{
            background-color: #f1f1f1
        }
        body{
            overflow-y: hidden;
        }
        </style>
</head>
<body>
<section class="content-header">
    <h1>
        服务监控
    </h1>
</section>
            <div class="row">
                <div class="col-md-12">
                    <div class="box box-solid">
                        <div class="box-header with-border">
                            <h4 class="box-title">服务监控列表</h4>
                            <div class="btn_box">
                                <button class="current"><i class="iconfont icon-plus"></i>刷新</button>
                            </div>
                        </div>
                        <div class="box_l">
                            <div class="list" id="maingrid4"></div>
                        </div>
                    </div>
                </div>
                <!-- /.col -->
            </div>
</body>
<script src="${res}/plugins/jQuery/jquery-2.2.3.min.js"></script>
<script src="${res}/plugins/ligerUI/js/core/base.js" type="text/javascript"></script>
<script src="${res}/plugins/ligerUI/js/plugins/ligerGrid.js" type="text/javascript"></script>
<script src="${res}/plugins/ligerUI/js/plugins/ligerDrag.js" type="text/javascript"></script>
<script src="${res}/plugins/ligerUI/js/plugins/ligerMenu.js" type="text/javascript"></script>
<script src="${res}/bootstrap/js/bootstrap.min.js"></script>
<script src="${res}/dist/js/app.js"></script>
<script src="${res}/dist/js/demo.js"></script>
<script type="text/javascript" src="${res}/plugins/dialog/jquery.artDialog.source.js"></script>
<script type="text/javascript" src="${res}/plugins/dialog/iframeTools.source.js"></script>
<script type="text/javascript" src="${res}/plugins/dialog/unit.js"></script>

<script type="text/javascript">
            //表格列表
            $(function () {
                $("#maingrid4").ligerGrid({
                    checkbox: false,
                    columns: [
                        { display: '状态', name: 'status'},
                        { display: '服务注册名称', name: 'serviceName' },
                        { display: '服务显示名称', name: 'serviceShowName'},
                        { display: '监控状态', name: 'monitorStatus'},
                        { display: '监控地址', name: 'monitorUrl'},
                        { display: '监控类型', name: 'monitorType'},
                        { display: '监控频率', name: 'monitorRate'},
                        { display: '可用率', name: 'availableRate',render:function(rowdata,rowindex,value){
                           var arr = value.split(".");
                           if(arr.length >1){
                               if(arr[1].length < 2){
                                   arr[1] = arr[1] + "0";
                               }else{
                                    arr[1] =  arr[1].substring(0,2);
                               }
                               value = arr[0] + "." + arr[1];
                           }
                            return value;
                        }},
                        { display: '平均响应时间', name: 'averageRespTime',render:function(rowdata,rowindex,value){
                            var arr = value.split(".");
                            if(arr.length >1){
                                if(arr[1].length < 2){
                                    arr[1] = arr[1] + "0";
                                }else{
                                    arr[1] =  arr[1].substring(0,2);
                                }
                                value = arr[0] + "." + arr[1];
                            }
                            return value;
                        }},
                        { display: '操作', width: 100,
                            render: function (rowdata, rowindex, value)
                            {
                                var h = "";
                                if (!rowdata._editing)
                                {
                                    h += "<input type='button' class='list-btn bt_edit' onclick='yc_update(" + rowdata.id + ")'/>";
                                }
                                return h;
                            } }
                    ], pageSize:30,
                    url:"${ctx}/serviceMonitor/listData",
                    width: '100%',height:'98%'
                });
                //新增弹窗
                $(".current").on('click', function (e) {   //添加/编辑解析规则
                    window.location.reload();
                });
            });
    function getLigerManager(){
        return $("#maingrid4").ligerGetGridManager();
    };
    function yc_update(id){
        var dialog = $.Layer.iframe(
                {
                    id:"updateDialog",
                    title: '编辑监控方案',
                    url:'${ctx}/serviceMonitor/editv?id=' + id,
                    width: 400,
                    height: 400
                });
    };
</script>
</html>
