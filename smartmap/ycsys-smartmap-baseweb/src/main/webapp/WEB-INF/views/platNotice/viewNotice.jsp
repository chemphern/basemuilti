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
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="date_add_table">

        <tr>
            <td width="120" class="t_r">消息标题：</td>
            <td >
                ${notice.title}
            </td>
        </tr>
        <tr>
            <td width="120" class="t_r">消息类型：</td>
            <td >
                ${notice.types}
            </td>
        </tr>
        <tr>
            <td class="t_r">时间：</td>
            <td>${notice.createTime}</td>
        </tr>
        <tr>
            <td class="t_r">消息内容：</td>
            <td>${notice.content}</td>
        </tr>
    </table>
    <div class="aui_buttons" style="border-top:0;background-color:unset">
        <button class="bt_sub aui_state_highlight" type="button" onclick="tsclose()">关闭</button>
    </div>
</body>
<script>
    function tsclose(){
        var p = window.parent;
        var dialog = p.art.dialog.list["viewNotice"];
        dialog.close();
    }
</script>
</html>
