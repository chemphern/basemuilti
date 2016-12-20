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
                <h4 class="box-title">Oracle监控列表</h4>
                <div class="btn_box">
                    <button class="current"><i class="iconfont icon-plus"></i>新增</button>
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
    ;(function($){
        //表格列表
        $(function () {
            $("#maingrid4").ligerGrid({
                checkbox: false,
                columns: [
                    { display: 'Oracle服务器名称', name: 'name', width: 100 },
                    { display: '连接地址', name: 'url', minWidth: 60 },
                    { display: '监控频率', name: 'time', minWidth: 60 },
                    { display: '最后监控时间', name: 'lasttime', minWidth: 100 },
                    { display: '监控状态', name: 'status', minWidth: 100 },
                    { display: '操作',
                        render: function (rowdata, rowindex, value)
                        {
                            var h = "";
                            if (!rowdata._editing)
                            {
                                h += "<input type='button' class='list-btn bt_edit' onclick='yc_update(" + rowdata.id + ")'/>";
                                h += "<input type='button' class='list-btn bt_del' onclick='yc_delete(" + rowdata.id + ")'/>";
                                h += "<input type='button' class='list-btn bt_view' onclick='yc_editPermission(" + rowdata.id + ")'/>";
                            }
                            return h;
                        } }
                ], pageSize:30,
                url:"${ctx}/platMonitorConfig/listTomcatData",
                width: '100%',height:'97%'
            });
        });

        //新增弹窗
        $(".current").on('click', function (e) {   //添加/编辑解析规则
            e.preventDefault();
            var dialog = $.Layer.iframe(
                {
                    id:"addRoleDialog",
                    title: '新增角色',
                    url:'${ctx}/role/addRolev',
                    width: 400,
                    height: 350
                });
        });
    })(jQuery);
    function getLigerManager(){
        return $("#maingrid4").ligerGetGridManager();
    };
    function yc_delete(id){
        $.Layer.confirm({
            msg:"确定删除该项？",
            fn:function(){
                $.ajax({
                    url:"${ctx}/role/delete",
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
                id:"updateRoleDialog",
                title: '修改角色',
                url:'${ctx}/role/updateRolev?id=' + id,
                width: 400,
                height: 400
            });
    };
    function yc_editPermission(id){
        art.dialog.open('${ctx}/role/roleGivev?id=' + id,{
            id:"editPermissionDialog",
            title: '修改权限',
            width: 600,
            height: 500,
            lock: true
        });
    };
</script>
</html>
