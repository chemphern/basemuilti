<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/views/common/taglib.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>羽辰智慧林业平台运维管理系统-用户管理密码</title>
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <!-- iconfont -->
    <link rel="stylesheet" href="${res}/iconfont/iconfont.css">
    <!-- Theme style -->
    <link rel="stylesheet" href="${res}/dist/css/AdminLTE.css">
    <link href="${res}/plugins/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />

    <script src="${res}/plugins/jQuery/jquery-2.2.3.min.js"></script>
    <script src="${res}/plugins/jquery-validation-1.15.1/dist/jquery.validate.min.js"></script>
    <script src="${res}/plugins/jquery-validation-1.15.1/lib/jquery.form.js"></script>
    <script src="${res}/js/common/form.js"></script>
    <script src="${res}/plugins/ligerUI/js/ligerui.all.js" type="text/javascript"></script>
</head>
<body>
<form action="${ctx}/user/saveOrUpdate" method="post" id="form_id">

    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="date_add_table">
        <tr>
            <td class="t_r">密码：</td>
            <td>
                <input type="text" style="display: none" name="id" value="${user.id}">
                <input id="roleId" readonly name="roleId" type="text" value="${roleIds}" style="display: none">
                <input type="text" style="display: none" name="orgId" value="${user.organization.id}">
                <input type="password" name="password" id="password" validate="{required:true,messages:{required:'必填'}}">
            </td>
        </tr>
        <tr>
            <td class="t_r">确认密码：</td>
            <td>
                <input type="password" name="plainPassword" id="plainPassword" validate="{required:true,messages:{required:'必填'}}">
            </td>
        </tr>
    </table>
</form>
</body>
<script>
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
    }
    function before(){
        if($("#password").val() != $("#plainPassword").val()){
            $("#submitmsg").html("两次输入密码不一致！");
            return false;
        };
        return true;
    }
    $(function(){
        var p = window.parent;
        var dialog = p.art.dialog.list["updateUserPwd"];
        var $fm = $("#form_id");
        var dialog_div = dialog.DOM.wrap;
        dialog_div.on("ok",function(){
            $fm.submit();
        });
        var val_obj = exec_validate($fm);
        val_obj.submitHandler = function(form){
            if (before() === false) {
                return;
            }else{
                $("#submitmsg").html("");
                $(form).ajaxSubmit({
                    success:function(data){
                        if(data.retCode) {
                            dialog.close();
                        }else{
                            alert(data.retMsg);
                        }
                    },error:function(){
                        alert("修改失败");
                    },
                    dataType:"json"
                });

            }
        };
        $fm.validate(
            val_obj
        );
    });
</script>
</html>