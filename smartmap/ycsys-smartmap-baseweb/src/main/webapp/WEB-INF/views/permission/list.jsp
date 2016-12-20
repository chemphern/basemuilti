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
        <style>
        html,body{
            background-color: #f1f1f1
        }
        body{
            overflow-y: hidden;
        }
        </style>
</head>
<body class="hold-transition skin-blue sidebar-mini">
        <!-- Content Header (Page header) -->
        <section class="content-header">
            <h1>
               权限管理
            </h1>
            <ol class="breadcrumb">
                <li><a href="#"><i class="fa fa-dashboard"></i> 系统首页</a></li>
                <li class="active">权限管理</li>
            </ol>
        </section>
		<section class="content">
<div class="row">
    <div class="col-md-12">
        <div class="box box-solid">
            <div class="box-header with-border">
                <h4 class="box-title">权限列表</h4>
                <div class="btn_box">
                    <button onclick="yc_import()"><i class="iconfont icon-angle-double-down"></i>导入</button>
                    <button onclick="yc_export(this)"><i class="iconfont icon-angle-double-up"></i>导出</button>
                </div>
            </div>
            <div class="box_l">
                <div class="list" id="maingrid4"></div>
            </div>
        </div>
    </div>
    <!-- /.col -->
</div>
		</section>
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
</html>
<script type="text/javascript">
    ;(function($){  //避免全局依赖,避免第三方破坏
        $(document).ready(function () {
            //表格列表
            $(function () {
                $("#maingrid4").ligerGrid({
                    checkbox: true,
                    columns: [
                        { display: '名称', name: 'name', align: 'left', width: 200,render:function(row,i,value, item){
                            var text = typeof(value) == 'undefined' || value == null ? '' : value;
                            //对名称进行树处理
                            if(text != '') {
                                if(!row.type){
                                    var html = '';
                                    var im = "";
                                    im +=  row.id || '';
                                    html += '<a href="javascript:void(0)" onClick="loadChildNode(this,\'${ctx}/permission/listData\', \'t_' + im + '\');">';
                                    if(!(row._hasLoadTree)) {
                                        html += '<img id="t_' + im + '" class="dcListTreeOpen" src="${res}/plugins/ligerUI/skins/Aqua/images/grid/grid-tree-close.gif"/>';
                                    }
                                    html += text + '</a>'
                                    return html;
                                }else{
                                    return text;
                                }
                            }else{
                                return text;
                            }
                        }
                        },
                        { display: '编码', name: 'code', minWidth: 60 },
                        { display: '链接', name: 'url', minWidth: 100,align:'left' },
                        { display: '类型', name: 'type', minWidth: 100,render:function(row,rowId,value,item){
                            var text = typeof(value) == 'undefined' || value == null ? '' : value;
                            var dic = dicMappers[item.name];
                            if(dic) {
                                text = dic[text] || text;
                            }
                            return text;
                        }},
                        { display: '操作', name: 'opera' }
                    ], pageSize:30,
                    url:"${ctx}/permission/findSystems",
                    width: '100%',height:'96%',
                    alternatingRow:false,
                    allowAdjustColWidth:true,
                    checkbox:false,
                    usePager:false,
                    onBeforeShowData: function (data)
                    {
                        this.collapsedRows = [];
                    },
                    onTreeExpand:function(data){
                        var c = data.children;
                        if( c && c.length>0) {
                            this.append(c, data);
                        }
                        if(c && c.children && c.children.length >0){
                            return false;
                        }else
                        return true;
                    },
                    tree:{columnName:"name"}
                });
            });
        });
    })(jQuery);
    var dicMappers = {
        "type":{
            "url":"链接",
            "func":"功能点",
            "module":"父模块",
            "menu":"子模块",
            "":"系统"
        }
    };
    function yc_import(){
        var dialog = $.Layer.iframe(
                {
                    id:"importPermissionDialog",
                    title: '导入权限',
                    url:'${ctx}/permission/importPermissionv',
                    width: 400,
                    height: 400
                });

    };
    function yc_export(ts){
        var $fm = $("<form/>",{
            action:"${ctx}/permission/exportPermission",
            method:"get",
            style:"display:none"
        });
        $(document.body).append($fm);
        $fm.submit();
        $fm.remove();
    }
    function getLigerManager(){
        return $("#maingrid4").ligerGetGridManager();
    };
    //加载树函数
    function loadChildNode(o, url, im)  {
        var g = getLigerManager();
        var index = $(o).parents("tr.l-grid-row").get(0).rowIndex;
        var row = g.getRow(index);
        if (g.hasChildren(row) || row._hasLoadTree)
            return;
        var oper = '';
        if(row.id != null) {
            oper += 'sys_code=' + row.code;
        }
        if(oper == '') {
            oper = $.param(row);
        }
        $.ajax({
            url:url,
            type:"post",
            data:oper,
            dataType:"json",
            async:false,
            success:function(data){
                g.appendRow(data.Rows, row);

                $('#' + im).remove();
                row._hasLoadTree = true;
                g.isDataChanged = false;
            }
        });
    };
</script>
