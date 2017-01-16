<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/views/common/taglib.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>羽辰智慧林业综合管理平台-资源管理</title>
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
        当前请求列表
    </h1>
</section>
            <div class="row">
                <div class="col-md-12">
                    <div class="box box-solid">
                        <div class="box-header with-border">
                            <h4 class="box-title">当前请求列表</h4>
                            <div class="btn_box">
                                <button class="current"><i class="iconfont icon-plus"></i>查询</button>
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
                        { display: '请求时间', name: 'requestDate',render:function(rowdata,rowindex,value){
                            var d = new Date(value).Format("yyyy-MM-dd hh:mm:ss");
                            return d;
                        }},
                        { display: '用户名', name: 'requestUser.name' },
                        { display: '用户请求IP', name: 'requestIp'},
                        { display: '服务器IP', name: 'serverIp'},
                        { display: '服务器端口', name: 'serverPort'},
                        { display: '服务名称', name: 'serviceName'},
                        { display: '服务类型', name: 'serviceType'},
                        { display: '服务方法', name: 'serviceMethod'},
                        { display: '请求URI', name: 'requestUrl'},
                        { display: '访问时间', name: 'visitTime'},
                        { display: '返回状态', name: 'returnStatus'},
                        { display: '操作', width: 100,
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
                    url:"${ctx}/requestList/listData",
                    width: '100%',height:'98%'
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
            });
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
