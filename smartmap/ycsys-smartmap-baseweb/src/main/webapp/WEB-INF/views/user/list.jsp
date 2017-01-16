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
            background-color: #f1f1f1
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
                用户管理
            </h1>
        </section>

<div class="row">
    <div class="col-md-3 col-sm-4">
        <div class="box box-solid">
            <div class="box-header with-border">
                <h4 class="box-title">组织机构</h4>
            </div>
            <div class="box_l">
                <ul id="orgTree"></ul>
            </div>
            <!-- /.box-body -->
        </div>
    </div>
    <!-- /.col -->
    <div class="col-md-9 col-sm-8">
        <div class="box box-solid">
            <div class="box-header with-border">
                <h4 class="box-title">用户列表</h4>
                <div class="btn_box">
                    姓名：<input type="text" />
                    <!--
                    部门：<input type="text" />
                    -->
                    <button class="current"><i class="iconfont icon-8search"></i>查询</button>
                    <button class="current" id="addUser"><i class="iconfont icon-plus"></i>新增</button>
                    <!--
                    <button onclick="yc_import()"><i class="iconfont icon-angle-double-down"></i>导入</button>
                    <button><i class="iconfont icon-angle-double-up"></i>导出</button>
                    -->
                </div>
            </div>
            <div class="box_l">
                <div class="list" id="maingrid4"></div>
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
    function getLigerTreeManager(id){
        return $("#" + id).ligerGetTreeManager();
    };
    function getLigerGridManager(id){
        return $("#" + id).ligerGetGridManager();
    }
    ;(function($){  //避免全局依赖,避免第三方破坏
            var actionNodeID;
            var menu = $.ligerMenu({ top: 100, left: 100, width: 120, items:
                    [
                        { text: '增加', click: yc_add, icon: 'add' },
                        { text: '修改', click: yc_add },
                        { line: true },
                        { text: '查看', click: yc_add }
                    ]
            });

            var $tree = $("#orgTree").ligerTree({
                nodeWidth:160,
                url:"${ctx}/org/getOrgs",
                checkbox: false,
                ajaxType: 'get',
                idFieldName: 'id',
                parentIDFieldName:'pid',
                textFieldName:'name',
                onContextmenu: function (node, e) {
                    actionNodeID = node.data.text;
                    menu.show({ top: e.pageY, left: e.pageX });
                    return false;
              }, onSelect:function(o){
                    var data = o.data;
                    if(data){
                    $("#maingrid4").ligerGrid({
                        checkbox: true,
                        columns: [
                            { display: '归属机构', name: 'organization.name', width: 100 },
                            { display: '登录名', name: 'loginName', width: 100},
                            { display: '姓名', name: 'name', minWidth: 100 },
                            { display: '手机', name: 'phone', minWidth: 100 },
                            { display: '上次登录时间', name: 'lastLoginTime', minWidth: 100 } ,
                            { display: '操作',
                                render: function (rowdata, rowindex, value)
                                {
                                    var h = "";
                                    if (!rowdata._editing)
                                    {
                                        h += "<input type='button' class='list-btn bt_edit' onclick='yc_update(" + rowdata.id + ")'/>";
                                        h += "<input type='button' class='list-btn bt_del' onclick='yc_delete(" + rowdata.id + ")'/>";
                                        h += "<input type='button' class='list-btn bt_view' onclick='yc_showOne(" + rowdata.id + ")'/>";
                                    }
                                    return h;
                                }}
                        ], pageSize:10,
                        url:"${ctx}/user/listData",
                        width: '100%',height:'98%',
                        parms: [
                            {name:'orgId', value:data.id}
                        ],
                    });
                    }
                }, onSuccess:function(){
                    var dt = $tree.getData();
                    if(dt) {
                        $tree.selectNode(dt);
                    }
                }
            });

            //新增弹窗
            $("#addUser").on('click', function (e) {   //添加/编辑解析规则
                e.preventDefault();
                var t = getLigerTreeManager("orgTree");
                var selected = t.getSelected();
                if(selected){
                var sel_data = selected.data;
                var dialog = $.Layer.iframe(
                        {
                            id:"addUserDialog",
                            title: '新增用户',
                            url:'${ctx}/user/addUserv?orgId=' + sel_data.id + "&orgName=" + sel_data.name,
                            width: 400,
                            height: 450
                        });
                }else{
                    alert("请选择机构！");
                }
            });
            function yc_add(){
                var dialog = $.Layer.iframe(
                        {
                            title: '新增用户',
                            url:'${ctx}/user/addUserv',
                            width: 400,
                            height: 450
                        });
            };
            window.yc_import = function(){
                var dialog = $.Layer.iframe(
                        {
                            title: '导入用户',
                            url:'${ctx}/user/importUserv',
                            width: 400,
                            height: 400,
                            ok:function(t){
                                t.document.getElementById("form_id").submit();
                                return false;
                            }
                        });

            };
    })(jQuery);
      function yc_delete(id){
          $.Layer.confirm({
              msg:"确定删除该项？",
              fn:function(){
                  $.ajax({
                      url:"${ctx}/user/delete",
                      data:{id:id},
                      type:"post",
                      dataType:"json",
                      success:function(res){
                          alert(res.retMsg);
                          getLigerGridManager("maingrid4").loadData();
                      },error:function(){
                          alert("删除失败！");
                      }
                  });
              },
              fn2:function(){
              }
          });
      };
      function yc_showOne(id){
          art.dialog.open('${ctx}/user/showUserv?id=' + id,{
              id:"showUserDialog",
              title: '用户详情',
              width: 400,
              height: 500,
              lock: true
          });
      };
      function yc_update(id){
          var dialog = $.Layer.iframe(
                  {
                      id:"updateUserDialog",
                      title: '修改用户',
                      url:'${ctx}/user/updateUserv?id=' + id,
                      width: 400,
                      height: 450
                  });
      };
</script>
</html>
