<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/views/common/taglib.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>羽辰智慧林业平台运维管理系统-用户管理展示</title>
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <!-- iconfont -->
    <link rel="stylesheet" href="${res}/iconfont/iconfont.css">
    <!-- Theme style -->
    <link rel="stylesheet" href="${res}/dist/css/AdminLTE.css">
    <link href="${res}/plugins/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
    <link href="${res}/plugins/dialog/dialog.css" rel="stylesheet" type="text/css">

    <script src="${res}/plugins/jQuery/jquery-2.2.3.min.js"></script>
    <script src="${res}/plugins/jquery-validation-1.15.1/dist/jquery.validate.min.js"></script>
    <script src="${res}/plugins/jquery-validation-1.15.1/lib/jquery.form.js"></script>
    <script src="${res}/js/common/form.js"></script>
</head>
<body>
<form action="${ctx}/user/saveOrUpdate" method="post" id="form_id">

    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="date_add_table">

        <tr>
            <td width="120" class="t_r">所属机构：</td>
            <td >
                ${user.organization.name}
            </td>
        </tr>
        <tr>
            <td width="120" class="t_r">拥有角色：</td>
            <td >
                ${roleNames}
            </td>
        </tr>
        <tr>
            <td class="t_r">用户姓名：</td>
            <td>${user.name}</td>
        </tr>
        <tr>
            <td class="t_r">登录名：</td>
            <td>${user.loginName}</td>
        </tr>
        <tr>
            <td class="t_r">性别：</td>
            <td >
                <span id="sex">${user.sex}</span>
            </td>
        </tr>
        <tr>
            <td class="t_r">用户类型：</td>
            <td >
                <span id="type">${user.type}</span>
            </td>
        </tr>
        <tr>
            <td class="t_r">邮箱：</td>
            <td>${user.email}</td>
        </tr>
        <tr>
            <td class="t_r">手机号码：</td>
            <td>${user.phone}</td>
        </tr>
        <tr>
            <td class="t_r">上次登录：</td>
            <td>${user.lastLoginTime}</td>
        </tr>
        <tr>
            <td class="t_r">上次登录ip：</td>
            <td>${user.lastLoginIp}</td>
        </tr>
        <tr>
            <td class="t_r">备注：</td>
            <td>${user.remark}</td>
        </tr>
    </table>
    <div class="aui_buttons" style="border-top:0;background-color:unset">
        <button class="bt_sub aui_state_highlight" type="button" onclick="tsclose()">关闭</button>
    </div>
</form>
</body>
<script>
    function getDicObject(code){
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
    var sex_dic = getDicObject("sex");
    var type_dic = getDicObject("user_type");
    var sex_val = sex_dic["${user.sex}"];
    if(sex_val){
        $("#sex").html(sex_val);
    }
    var type_val = type_dic["${user.type}"];
    if(type_val){
        $("#type").html(type_val);
    }
    function tsclose(){
        var p = window.parent;
        var dialog = p.art.dialog.list["showUserDialog"];
        dialog.close();
    }
</script>
</html>
