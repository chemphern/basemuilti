<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/views/common/taglib.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>羽辰智慧林业平台运维管理系统-字典管理</title>
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <link rel="shortcut icon" href="${res}/images/favicon.ico" />
    <link rel="stylesheet" href="${res}/bootstrap/css/bootstrap.css">
    <link rel="stylesheet" href="${res}/iconfont/iconfont.css">
    <link rel="stylesheet" href="${res}/dist/css/AdminLTE.css">
    <link rel="stylesheet" href="${res}/dist/css/skins/_all-skins.css">
    <link rel="stylesheet" href="${res}/plugins/iCheck/flat/blue.css">
    <link href="${res}/plugins/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
    <link href="${res}/plugins/dialog/dialog.css" rel="stylesheet" type="text/css">
    <style>
        html,body{
            background-color: #f1f1f1;
        }
        body{
            overflow-y: hidden;
        }
    </style>
</head>
<body>
        <!-- Content Header (Page header) -->
        <section class="content-header">
            <h1>
                字典管理
            </h1>
        </section>
            <div class="row">
                <div class="col-md-12">
					<div class="box box-solid">
					    <div class="box-header with-border">
					        <h4 class="box-title">数据字典列表</h4>
					        <div class="btn_box">
					            <button class="current" id="addDict"><i class="iconfont icon-plus"></i>新增</button>
					            <!--<button><i class="iconfont icon-trash"></i>导入</button>-->
					        </div>
					    </div>
					    <div class="box_l">
					        <div class="list" id="maingrid4"></div>
					    </div>
					</div>
                </div>
                <!-- /.col -->
            </div>

<!-- /.col -->
</body>
<script src="${res}/plugins/jQuery/jquery-2.2.3.min.js"></script>
<script src="${res}/plugins/ligerUI/js/core/base.js" type="text/javascript"></script>
<script src="${res}/plugins/ligerUI/js/plugins/ligerGrid.js" type="text/javascript"></script>
<script src="${res}/plugins/ligerUI/js/plugins/ligerDrag.js" type="text/javascript"></script>
<script src="${res}/plugins/ligerUI/js/plugins/ligerMenu.js" type="text/javascript"></script>
<script src="${res}/plugins/ligerUI/js/plugins/ligerTree.js" type="text/javascript"></script>
<script src="${res}/bootstrap/js/bootstrap.min.js"></script>
<script src="${res}/dist/js/app.js"></script>
<script src="${res}/dist/js/demo.js"></script>
<script type="text/javascript" src="${res}/plugins/dialog/jquery.artDialog.source.js"></script>
<script type="text/javascript" src="${res}/plugins/dialog/iframeTools.source.js"></script>
<script type="text/javascript" src="${res}/plugins/dialog/unit.js"></script>
<script>
    $(function(){
        $("#maingrid4").ligerGrid({
            checkbox: false,
            columns: [
                { display: '字典名称', name: 'name'},
                { display: '字典编码', name: 'code'},
                { display: '备注', name: 'memo'},
                { display: '操作', width:'100',
                    render: function (rowdata, rowindex, value)
                    {
                        var h = "";
                        if (!rowdata._editing)
                        {
                            h += "<input type='button' class='list-btn bt_edit' onclick='yc_update(" + rowdata.id + ")'/>";
                            h += "<input type='button' class='list-btn bt_del' onclick='yc_delete(" + rowdata.id + ")'/>";
                        }
                        return h;
                    }
                }
            ],
            pageSize:10,
            url:"${ctx}/dictionary/listData",
            width: '100%',height:'98%'
            // parms:[{name:""}]
        });
        $("#addDict").click(function(e){
            e.preventDefault();
            var dialog = $.Layer.iframe(
                    {
                        id:"addDictDialog",
                        title: '新增字典',
                        url:'${ctx}/dictionary/addDictv',
                        width: 700,
                        height: 500
                    });
        });
    });
    function yc_update(id){
        art.dialog.open('${ctx}/dictionary/updateDictv?id=' + id,{
            id:"updateDictDialog",
            title: '修改字典',
            width: 700,
            height: 500,
            lock: true
        });
        <%--$.ajax({--%>
            <%--type: "POST",--%>
            <%--url: "${ctx}/dictionary/updateDictv",--%>
            <%--data: JSON.stringify(nb),//将对象序列化成JSON字符串--%>
            <%--contentType : 'application/json;charset=utf-8', //设置请求头信息--%>
            <%--success: function(res){--%>
                <%--console.log(res);--%>
                <%--var dialog = art.dialog(--%>
                        <%--{--%>
                            <%--id:"updateDictDialog",--%>
                            <%--title: '修改字典',--%>
                            <%--width: 700,--%>
                            <%--height: 500--%>
                        <%--});--%>
                <%--dialog.content(res);--%>
            <%--}--%>
        <%--});--%>
        <%--$.post("${ctx}/dictionary/updateDictv",nb,function(res){--%>
            <%--var dialog = $.Layer.iframe(--%>
                    <%--{--%>
                        <%--id:"updateDictDialog",--%>
                        <%--content:res,--%>
                        <%--title: '修改字典',--%>
                        <%--width: 700,--%>
                        <%--height: 500--%>
                    <%--});--%>
        <%--});--%>


    };
    function yc_delete(id){
        $.Layer.confirm({
            msg:"确定删除该项？",
            fn:function(){
                $.ajax({
                    url:"${ctx}/dictionary/delete",
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
    }
    function getLigerManager(){
        return $("#maingrid4").ligerGetGridManager();
    }
</script>
</html>