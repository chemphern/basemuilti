<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/views/common/taglib.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>羽辰智慧林业平台运维管理系统-添加字典</title>
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
    <script src="${res}/plugins/jQuery/jquery-2.2.3.min.js"></script>
    <script src="${res}/plugins/jquery-validation-1.15.1/dist/jquery.validate.min.js"></script>
    <script src="${res}/plugins/jquery-validation-1.15.1/lib/jquery.form.js"></script>
    <script src="${res}/js/common/form.js"></script>
    <script src="${res}/plugins/ligerUI/js/core/base.js" type="text/javascript"></script>
    <script src="${res}/plugins/ligerUI/js/plugins/ligerGrid.js" type="text/javascript"></script>
    <script src="${res}/plugins/ligerUI/js/plugins/ligerDrag.js" type="text/javascript"></script>
    <script src="${res}/plugins/ligerUI/js/plugins/ligerToolBar.js" type="text/javascript"></script>
    <script src="${res}/bootstrap/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="${res}/plugins/dialog/jquery.artDialog.source.js"></script>
    <script type="text/javascript" src="${res}/plugins/dialog/iframeTools.source.js"></script>
    <script type="text/javascript" src="${res}/plugins/dialog/unit.js"></script>
</head>
<style>
    .date_add_table td{
        padding:0;
        margin:auto;
    }
    .date_add_table tr{
        margin:auto;
    }
    form{
        margin:10px 9px 0;
    }
</style>
<body>
<form action="${ctx}/dictionary/createItem" id="dicItemForm" name="dicItemForm" method="post" ligeruiid="dicItemForm">
    <input name="dict_id" id="dict_id" value="${dictId}" type="hidden">
    <table heigth="100%" class="l-table-edit" border="0" cellpadding="0" cellspacing="0" class="date_add_table">
        <tbody>
        <tr style="border-bottom:1px solid #EBEBEB;line-height:40px;">
            <td colspan="6">
                <span>数据字典项</span></td>
        </tr>
        <tr style="line-height:40px;">
            <td width="72px">名称：</td>
            <td width="160px">
                <div class="l-text" style="width: 180px;">
                    <input name="name" id="itemName" validate="{required:true}" value="" class="l-text-field" style="width: 176px;" ligeruiid="itemName" type="text">
                    </div>
            </td>
            <td width="20px"></td>
            <td width="60px">值：</td>
            <td width="160px">
                <div class="l-text" style="width: 180px;">
                    <input name="value" id="itemValue" validate="{required:true}" value="" class="l-text-field" style="width: 176px;" ligeruiid="itemValue" type="text">
                </div></td>
            <td></td>
        </tr>
        <tr style="line-height:40px;">
            <td>显示顺序：</td>
            <td>
                <div class="l-text" style="width: 180px;">
                <input name="sort" id="showOrder" validate="{required:true}" value="1" class="l-text-field" style="width: 176px;" ligeruiid="showOrder" type="text">
                </div>
            </td>
            <td></td>
            <td>是否显示：</td>
            <td>
                <select name="isShow">
                    <option value="1" selected="true">显示</option>
                    <option value="2">不显示</option>
                </select>
            </td>
            <td>
            </td></tr>
        <tr style="border-bottom:1px solid #EBEBEB;line-height:40px;">
            <td>备注：</td>
            <td colspan="4">
                <textarea cols="78" rows="8" id="memo" name="memo" class="l-textarea"></textarea>
            </td>
            <td>

            </td>

        </tr>
        </tbody></table>
    <br><br>

        <div class="aui_buttons" style="border-top:0;background-color:unset">
            <button class="bt_sub aui_state_highlight" type="submit">提交</button>
            <button type="button" onclick="yc_close()">取消</button>
        </div>

</form>
</body>
<script>
    function yc_close(){
        var p = window.parent;
        var dialog = p.art.dialog.list["addDictItemDialog"];
        dialog.close();
    }
    $(function(){
        var p = window.parent;
        var f = $("#dicItemForm");
        var dialog = p.art.dialog.list["addDictItemDialog"];
        var p_dialog = p.art.dialog.list["updateDictDialog"];
        var pd = p_dialog.iframe.contentWindow;
        var val_obj = exec_validate(f);
        val_obj.submitHandler = function(form){
                $(form).ajaxSubmit({
                    success:function(data){
                        if(data.retCode) {
                            pd.getItemManager().loadData();
                            dialog.close();
                        }else{
                            alert(data.retMsg);
                        }
                    },dataType:"json"
                });
        };
        f.validate(
                val_obj
        );
    });
</script>
</html>
