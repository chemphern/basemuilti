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
    <script src="${res}/plugins/jQuery/jquery-2.2.3.min.js"></script>
    <script src="${res}/js/common/multiselect.js"></script>
    <script src="${res}/plugins/jquery-validation-1.15.1/dist/jquery.validate.min.js"></script>
    <script src="${res}/plugins/jquery-validation-1.15.1/lib/jquery.form.js"></script>
    <script src="${res}/js/common/form.js"></script>
</head>
<body>
<form action="${ctx}/permission/importPermission" method="post" id="form_id" enctype="multipart/form-data">
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="date_add_table">

        <tr>
            <td width="120" class="t_r">选择文件：</td>
            <td ><input type="file" name="file" id="file1" class="text validate[required]" /></td>
        </tr>

    </table>
</form>
</body>
<script>
    $(function(){
        var p = window.parent;
        var dialog = p.art.dialog.list["importPermissionDialog"];
        var $fm = $("#form_id");
        var dialog_div = dialog.DOM.wrap;
        dialog_div.on("ok",function(){
            $fm.submit();
        });
        var val_obj = exec_validate($fm);
        val_obj.submitHandler = function(form){
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
        };
        $fm.validate(
                val_obj
        );
    });
</script>
</html>