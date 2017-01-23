<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/views/common/taglib.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>羽辰智慧林业平台运维管理系统-平台消息</title>
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
    <!-- list -->
    <link href="${res}/plugins/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
    <!-- 弹出框 -->
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
      <h1>消息管理</h1>
    </section>
<div class="row">
    <div class="col-md-12">
        <div class="box box-solid">
            <div class="box-header with-border">
                <h4 class="box-title">消息列表</h4>
                <div class="btn_box">
                    <button onclick="add_notice()"><i class="iconfont icon-plus"></i>新增消息</button>
                    <button onclick="add_new_notice()"><i class="iconfont icon-plus"></i>发送新消息</button>
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
                    { display: '消息标题', name: 'title', width: 100 },
                    { display: '消息类型', name: 'types', minWidth: 60 },
                    { display: '消息内容', name: 'content', minWidth: 100 },
                    { display: '消息状态', name: 'statuss', minWidth: 100 ,render:function(rowdata, rowindex, value){
                    var d = "未发送";
                    if(rowdata.sendNum >0){
                            d = "已发送";
                        }
                        return d;
                    }},
                    { display: '发送数量', name: 'sendNum', minWidth: 100 },
                    { display: '创建时间', name: 'createTime', minWidth: 100,render: function (rowdata, rowindex, value)
                     {
                        var d;
                        if(value > 0 ){
                            d = new Date(value).Format("yyyy-MM-dd hh:mm:ss");
                        }else{
                            d = "";
                        }
                        return d;
                     }
                    },
                    { display: '操作', width: 150,
                        render: function (rowdata, rowindex, value)
                        {
                            var h = "";
                            if (!rowdata._editing)
                            {
                                h += "<input type='button' class='list-btn bt_view' onclick='yc_show(" + rowdata.id + ")'/>";
                                if(rowdata.sendNum > 1){
                                    h += "<input type='button' class='list-btn bt_edit' onclick='yc_update(" + rowdata.id + ")'/>";
                                    h += "<input type='button' class='list-btn bt_del' onclick='yc_delete(" + rowdata.id + ")'/>";
                                }else{
                                    h += "<input type='button' class='list-btn bt_sender' onclick='yc_showSender(" + rowdata.id + ")'/>";
                                }
                                h += "<input type='button' class='list-btn bt_edit' onclick='yc_toSend(" + rowdata.id + ")'/>";
                            }
                            return h;
                        } }
                ], pageSize:30,
                url:"${ctx}/platNotice/listData",
                width: '100%',height:'98%'
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
                    url:"${ctx}/platNotice/delete",
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
                id:"updateNotice",
                title: '编辑消息',
                url:'${ctx}/platNotice/updateNoticev?id=' + id,
                width: 700,
                height: 410
            });
    };
    function add_notice(){
        var dialog = $.Layer.iframe(
            {
                id:"addNotice",
                title: '添加消息',
                url:'${ctx}/platNotice/addNoticev',
                width: 700,
                height: 410
            });
    }
    function yc_showSender(id){
        art.dialog.open('${ctx}/platNotice/listSender?noticeId=' + id,{
            id:"showSender",
            title: '查看发送人',
            width: 600,
            height: 500,
            lock: true
        });
    };
    function yc_toSend(id){
        $.Layer.iframe(
            {
                id:"sendNoticeDialog",
                title: "发送消息",
                url:"${ctx}/platNotice/sendNotice?id=" + id,
                width: 1020,
                height: 700
            });
        <%--var a = window.parent.$(".treeview .active");--%>
        <%--var active = $(a[a.length-1]);--%>
        <%--active.removeClass("active");--%>
        <%--active.next().addClass("active");--%>
        <%--window.location.href = "${ctx}/platNotice/sendNotice?id=" + id;--%>
    };

    function add_new_notice(id){
        $.Layer.iframe(
            {
                id:"sendNoticeDialog",
                title: "发送新消息",
                url:"${ctx}/platNotice/sendNotice",
                width: 1020,
                height: 700
            });
    };
    function yc_show(id){
        art.dialog.open('${ctx}/platNotice/viewNotice?id=' + id,{
            id:"viewNotice",
            title: '消息详情',
            width: 400,
            height: 500,
            lock: true
        });
    }
</script>
</html>