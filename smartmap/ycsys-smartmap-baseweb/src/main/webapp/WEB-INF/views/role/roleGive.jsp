<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/views/common/taglib.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>羽辰智慧林业平台运维管理系统-角色管理</title>
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <link rel="shortcut icon" href="${res}/images/favicon.ico" />
    <!-- iconfont -->
    <link rel="stylesheet" href="${res}/iconfont/iconfont.css">
    <!-- Theme style -->
    <link rel="stylesheet" href="${res}/dist/css/AdminLTE.css">
    <link href="${res}/plugins/dialog/dialog.css" rel="stylesheet" type="text/css">
    <!-- tree -->
    <link href="${res}/plugins/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
</head>
<body>
<form action="${ctx}/role/updatePermission" method="post" id="form_id">

    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="date_add_table">

        <div style="width:99%; height:430px; display:block; margin:0px; background:white;   border:1px solid #ccc; overflow:auto;  ">
            <ul id="tree1">
            </ul>
        </div>
        <input type="hidden" name="id" id="id" value="${roleId}"/>
        <input type="hidden" name="authorities" id="authorities"/>
        <div class="aui_buttons" style="border-top:0;background-color:unset">
            <button class="bt_sub aui_state_highlight"   id="role_saveForm_sub" type="button">提交</button>
            <button class="bt_sub aui_state_highlight"   id="ctrlbut" type="button">收缩</button>
            <button class="bt_sub aui_state_highlight" type="button" onclick="yc_close()">关闭</button>
        </div>
    </table>
</form>
<!-- jQuery 2.2.3 -->
<script src="${res}/plugins/jQuery/jquery-2.2.3.min.js"></script>
<script src="${res}/plugins/jquery-validation-1.15.1/dist/jquery.validate.min.js"></script>
<script src="${res}/plugins/jquery-validation-1.15.1/lib/jquery.form.js"></script>
<script src="${res}/js/common/form.js"></script>
<!--grid-->
<script src="${res}/plugins/ligerUI/js/ligerui.all.js" type="text/javascript"></script>
<script type="text/javascript">
    Array.prototype.doUnique = function(){
        var res = [];
        var json = {};
        for(var i = 0; i < this.length; i++){
            if(!json[this[i]]){
                res.push(this[i]);
                json[this[i]] = 1;
            }
        }
        return res;
    };
    var manager;
    var result;
    function yc_close(){
        var p = window.parent;
        var dialog = p.art.dialog.list["editPermissionDialog"];
        dialog.close();
    }
    function getChecked()
    {
        //树选择的id
        var notes = manager.getChecked();
        var texts = [];
        for (var i = 0; i < notes.length; i++)
        {
            //去除根目录（系统id）
            if(notes[i].data.id != -1 && notes[i].data.id != "-1")
            texts.push(notes[i].data.id);
        }
        //半选择状态的id
        $(".l-checkbox-incomplete").each(function(i){
            var $id = $(this).parent().parent().attr("id");
            //去除根目录（系统id）
            if($id != -1 && $id != "-1")
            texts.push($id);
        });
        $("#authorities").val(texts.doUnique().join(","));
        return true;
    }

    function  getSelected(json){
        manager.selectNode(function (data){
            //匹配则选中
            if($.inArray(data.id + "",json)!=-1)
                return true;
        });
    }
    $(document).ready(function(){
            var p = window.parent[0];
            var dialog = p.art.dialog.list["editPermissionDialog"];
            var $fm = $("#form_id");
            $('#role_saveForm_sub').click(function(o,i) {
                $fm.submit();
            });
            var val_obj = exec_validate($fm);
            val_obj.submitHandler = function(form){
                if (getChecked() === false) {
                    return;
                }else{
                    $("#submitmsg").html("");
                    $(form).ajaxSubmit({
                        success:function(data){
                            if(data.retCode) {
                                p.getLigerManager().loadData();
                                dialog.close();
                            }else{
                                alert(data.retMsg);
                            }
                        },
                        dataType:"json"
                    });

                }
            };
            $fm.validate(
                    val_obj
            );
                $("#tree1").ligerTree({
                            url:'${ctx}/permission/findPermissions.mc',
                            isExpand:false,
                            btnClickToToggleOnly:false,
                            enabledCompleteCheckbox:true,
                            idFieldName: 'id',
                            textFieldName:'name',
                            canClick:false,
                            onSuccess:function(){
                            },
                            onAfterAppend:function(){
                                result = ${permissionList};
                                if(result != ''){
                                    getSelected(result);
                                }
                                $(".l-checkbox-checked").addClass("l-checkbox-checked2");
                                $(".l-checkbox-unchecked").addClass("l-checkbox-unchecked2");
                            }
                 });
                manager = $("#tree1").ligerGetTreeManager();
                $('#ctrlbut').click(function(o) {
                    if($(this).text() == '展开') {
                        manager.expandAll();
                        $(this).text('收缩');
                    }
                    else {
                        manager.collapseAll();
                        $(this).text('展开');
                    }
                });
    });
</script>
</body>
</html>