<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/views/common/taglib.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>Title</title>
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
            background-color: #ecf0f5
        }
        body{
            overflow-y: hidden;
        }
        </style>
</head>
<body>
        <div class="box box-solid">
            <div class="box-header with-border">
                <h4 class="box-title">组织机构列表</h4>
                <div class="btn_box">
                    <button class="current" id="addOrg"><i class="iconfont icon-plus"></i>新增</button>
                </div>
            </div>
            <div class="box_l">
                <div class="list" id="maingrid4"></div>
            </div>
        </div>
        <!-- /.col -->
</body>
<script src="${res}/plugins/jQuery/jquery-2.2.3.min.js"></script>
<script src="${res}/plugins/ligerUI/js/ligerui.all.js" type="text/javascript"></script>
<script src="${res}/bootstrap/js/bootstrap.min.js"></script>
<script src="${res}/plugins/knob/jquery.knob.js"></script>
<script src="${res}/plugins/slimScroll/jquery.slimscroll.js"></script>
<script src="${res}/dist/js/app.js"></script>
<script src="${res}/dist/js/demo.js"></script>
<script type="text/javascript" src="${res}/plugins/dialog/jquery.artDialog.source.js"></script>
<script type="text/javascript" src="${res}/plugins/dialog/iframeTools.source.js"></script>
<script type="text/javascript" src="${res}/plugins/dialog/unit.js"></script>
<script>
    //加载树函数
        function loadChildNode(o, url, im)  {
            var g = getLigerManager();
            var index = $(o).parents("tr.l-grid-row").get(0).rowIndex;
            var row = g.getRow(index);
            if (g.hasChildren(row) || row._hasLoadTree)
                return;

            var oper = '';
            if(row.id != null) {
                oper += 'pid=' + row.id;
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
    //获取字典
    function getDicMappers(code){
        var dics;
        $.ajax({
            url:"${ctx}/dictionary/getDictItemByCode",
            data:{"code":code},
            dataType:"json",
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
    var org_type = getDicMappers("org_type");
    var dicMappers = {
        "type":org_type
    };
    $(function(){
        $("#maingrid4").ligerGrid({
            checkbox: false,
            columns: [
                { display: '机构名称', name: 'name',align:'left',
                    render:function(row,i,value, item){
                        var text = typeof(value) == 'undefined' || value == null ? '' : value;
                        //对名称进行树处理
                        if(text != '') {
                            var html = '';
                            var im = "";
                            im +=  row.id || '';
                            html += '<a href="javascript:void(0)" onClick="loadChildNode(this,\'${ctx}/org/listData\', \'t_' + im + '\');" style="color:blue;">';
                            if(!(row._hasLoadTree)) {
                                html += '<img id="t_' + im + '" class="dcListTreeOpen" src="${res}/plugins/ligerUI/skins/Aqua/images/grid/grid-tree-close.gif"/>';
                            }
                            html += text + '</a>'
                            return html;
                        }else{
                            return text;
                        }
                     }
            },
                { display: '归属区域', name: 'areaName', minWidth: 60 },
                { display: '机构编码', name: 'code', width: 100,align:'left' },
                { display: '机构类型', name: 'type', minWidth: 100,render:function(row,rowId,value,item){
                    var text = typeof(value) == 'undefined' || value == null ? '' : value;
                    var dic = dicMappers[item.name];
                    if(dic) {
                        text = dic[text] || text;
                    }
                    return text;
                }},
                { display: '备注', name: 'mobile', minWidth: 100 },
                { display: '操作',
                    render: function (rowdata, rowindex, value)
                    {
                        var h = "";
                        if (!rowdata._editing)
                        {
                            h += "<input type='button' class='list-btn bt_edit' onclick='yc_update(" + rowdata.id + ")'/>";
                            h += "<input type='button' class='list-btn bt_del' onclick='yc_delete(" + rowdata.id + ")'/>";
                            h += "<input type='button' class='list-btn bt_view' onclick='yc_addChild(" + rowdata.id + ",\"" + rowdata.name + "\")'/>";
                        }
                        return h;
                    }}
            ],
            pageSize:10,
            url:"${ctx}/org/listData",
            width: '100%',height:'96%',
            tree:{columnName:"name"},
            enabledSort:false,
            rownumbers:true,
            alternatingRow:true
        });
        $("#addOrg").click(function(e){
            e.preventDefault();
            var dialog = $.Layer.iframe(
                    {
                        id:"addOrgDialog",
                        title: '新增机构',
                        url:'${ctx}/org/addOrgv',
                        width: 400,
                        height: 400
                    });
        });
    });
    function getLigerManager(){
        return $("#maingrid4").ligerGetGridManager();
    }
    function yc_delete(id){
        $.Layer.confirm({
            msg:"确定删除该项？",
            fn:function(){
                $.ajax({
                    url:"${ctx}/org/delete",
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
    function yc_addChild(id,name){
        var dialog = $.Layer.iframe(
                {
                    id:"addOrgChildDialog",
                    title: '新增子机构',
                    url:'${ctx}/org/addChildOrgv?id=' + id + "&name=" + name,
                    width: 400,
                    height: 400
                });
    }
    function yc_update(id){
        var dialog = $.Layer.iframe(
                {
                    id:"updateOrgDialog",
                    title: '修改机构',
                    url:'${ctx}/org/updateOrgv?id=' + id,
                    width: 400,
                    height: 400
                });
    }
</script>
</html>
