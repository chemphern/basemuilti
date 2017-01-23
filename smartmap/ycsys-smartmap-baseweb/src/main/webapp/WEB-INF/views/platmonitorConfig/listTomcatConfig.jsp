<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/views/common/taglib.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>羽辰智慧林业平台运维管理系统-Tomcat监控列表</title>
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <link rel="shortcut icon" href="${res}/images/favicon.ico" />
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
</head>
<body>
<div class="row">
    <div class="col-md-12">
        <div class="box box-solid">
            <div class="box-header with-border">
                <h4 class="box-title">Tomcat监控列表</h4>
                <div class="btn_box">
                    <button class="current" onclick="y_add()"><i class="iconfont icon-plus"></i>新增</button>
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
    //获取字典
    function getDicMappers(code){
        var dics = {};
        $.ajax({
            url:"${ctx}/dictionary/getDictItemByCode",
            data:{"code":code},
            dataType:"JSON",
            type:"get",
            async:false,
            success:function(res){
                if(res.retCode){
                    dics = res.retData;
                }
            }
        });
        return dics;
    };
    var service_status = getDicMappers("service_status");
    var dicMappers = {
        "status":service_status
    };
    $(function () {
        $("#maingrid4").ligerGrid({
            checkbox: false,
            columns: [
                { display: '服务器名称', name: 'name', width: 100 },
                { display: '监控地址', name: 'url', minWidth: 60 },
                { display: '监控频率', name: 'monitorRate', minWidth: 100 },
                { display: '最后监控时间', name: 'lastMonitorTime', minWidth: 100 },
                { display: '监控状态', name: 'status', minWidth: 100,render:function(row,rowId,value,item){
                    var text = typeof(value) == 'undefined' || value == null ? '' : value;
                    var dic = dicMappers[item.name];
                    if(dic) {
                        text = dic[text] || text;
                    }
                    return text;
                }},
                { display: '操作',
                    render: function (rowdata, rowindex, value)
                    {
                        var h = "";
                        if (!rowdata._editing)
                        {
                            if(rowdata.status == "0" || rowdata.status == 0) {
                                h += "<a href='javascript:;' onclick='yc_start(" + rowdata.id + ")' style='top:-4px;margin-right:4px; position: relative;'>启动监控</a>";
                            }else {
                                h += "<a href='javascript:;' onclick='yc_stop(" + rowdata.id + ")' style='top:-4px;margin-right:4px; position: relative;'>停止监控</a>";
                            }
                            h += "<input type='button' class='list-btn bt_edit' onclick='yc_update(" + rowdata.id + ")'/>";
                            h += "<input type='button' class='list-btn bt_del' onclick='y_delete(" + rowdata.id + ")'/>";
                        }
                        return h;
                    } }
            ], pageSize:30,
            url:"${ctx}/platMonitorConfig/listMonitorConfigData?type=2",
            width: '100%',height:'97%'
        });
    });
    //启动监控
    function yc_start(id){
        $.Layer.confirm({
            msg:"确定启动该监控？",
            fn:function(){
                $.ajax({
                    url:"${ctx}/platMonitorConfig/startMonitor",
                    data:{id:id},
                    type:"post",
                    dataType:"json",
                    success:function(res){
                        alert(res.retMsg);
                        getLigerManager().loadData();
                    },error:function(){
                        alert("启动失败！");
                    }
                });
            },
            fn2:function(){
            }
        });
    };
    //停止监控
    function yc_stop(id){
        $.Layer.confirm({
            msg:"确定停止该监控？",
            fn:function(){
                $.ajax({
                    url:"${ctx}/platMonitorConfig/stopMonitor",
                    data:{id:id},
                    type:"post",
                    dataType:"json",
                    success:function(res){
                        alert(res.retMsg);
                        getLigerManager().loadData();
                    },error:function(){
                        alert("停止失败！");
                    }
                });
            },
            fn2:function(){
            }
        });
    };
    //新增弹窗
    function y_add(){   //添加/编辑解析规则
        var dialog = $.Layer.iframe(
            {
                id:"addTomcatDialog",
                title: '新增tomcat监控',
                url:'${ctx}/platMonitorConfig/addConfigv?type=2',
                width: 400,
                height: 350
            });
    };
    function getLigerManager(){
        return $("#maingrid4").ligerGetGridManager();
    };
    function y_delete(id){
        $.Layer.confirm({
            msg:"确定删除该项？",
            fn:function(){
                $.ajax({
                    url:"${ctx}/platMonitorConfig/delete",
                    data:{id:id},
                    type:"post",
                    dataType:"json",
                    success:function(res){
                        alert(res.retMsg);
                        getLigerManager().loadData();
                    },error:function(){
                        alert("删除失败！");
                    }
                });
            },
            fn2:function(){
            }
        });
    };
    function yc_update(id){
        var dialog = $.Layer.iframe(
            {
                id:"updateTomcatDialog",
                title: '修改监控配置',
                url:'${ctx}/platMonitorConfig/updateConfigv?id=' + id + '&type=2',
                width: 400,
                height: 400
            });
    };
</script>
</html>
