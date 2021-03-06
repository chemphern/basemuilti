<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/views/common/taglib.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>羽辰智慧林业平台运维管理系统-地区管理</title>
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <link rel="shortcut icon" href="${res}/images/favicon.ico" />
    <!-- iconfont -->
    <link rel="stylesheet" href="${res}/iconfont/iconfont.css">
    <!-- Theme style -->
    <link rel="stylesheet" href="${res}/dist/css/AdminLTE.css">
    <!-- tree -->
    <link href="${res}/plugins/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
</head>
<body>
<form action="${ctx}/area/saveOrUpdate" method="post" id="form_id">
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="date_add_table">
        <tr>
            <td class="t_r">区域名称：</td>
            <td><input type="text" name="name" id="name" validate="{required:true,messages:{required:'必填'}}"/></td>
        </tr>
        <tr>
            <td class="t_r">区域编码：</td>
            <td><input type="text" name="code" id="code" validate="{required:true,messages:{required:'必填'}}"/></td>
        </tr>
        <tr>
            <td class="t_r">区域类型：</td>
            <td><select name="type" id="type" validate="{number:true,messages:{number:'必选'}}">
                <option>请选择</option>
                <option value="1">省</option>
                <option value="2">市</option>
                <option value="3">区</option>
            </select></td>
        </tr>
        <tr style="line-height:20px;">
            <td colspan="6" style="color:red;" id="submitmsg" align="center">&nbsp;</td>
        </tr>
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
    function before(){
//        if($("#orgId_val").val() == ""){
//            $("#submitmsg").html("请选择所属机构！");
//            return false;
//        }
        return true;
    }
    ;(function($){
        var p = window.parent[0];
        var dialog = p.art.dialog.list["addAreaDialog"];
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
        $("#type").change(function(){

        });
    })(jQuery);
</script>
</body>
</html>
