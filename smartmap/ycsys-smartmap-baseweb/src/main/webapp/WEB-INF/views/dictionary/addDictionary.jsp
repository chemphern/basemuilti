<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/views/common/taglib.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>羽辰智慧林业综合管理平台-添加字典</title>
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <link rel="shortcut icon" href="${res}/images/favicon.ico" />
    <!-- iconfont -->
    <link rel="stylesheet" href="${res}/iconfont/iconfont.css">
    <!-- Theme style -->
    <link rel="stylesheet" href="${res}/dist/css/AdminLTE.css">
    <!-- tree -->
    <link href="${res}/plugins/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
    <script src="${res}/plugins/jQuery/jquery-2.2.3.min.js"></script>
    <script src="${res}/plugins/jquery-validation-1.15.1/dist/jquery.validate.min.js"></script>
    <script src="${res}/plugins/jquery-validation-1.15.1/lib/jquery.form.js"></script>
    <script src="${res}/js/common/form.js"></script>
</head>
<style>
    .date_add_table td{
        padding:0;
        margin:auto;
    }
    .date_add_table tr{
        margin:auto;
    }
    .date_add_table{
        margin:10px 9px 0;
    }
</style>
<script>
    //提交前进行校验
    function before(){
        //字典项名称
        var dicItemNameArr = new Array();
        $("input[name='itemName']").each(function(i,o){
            dicItemNameArr[i]=this.value;
        });

        for(var k=0;k<dicItemNameArr.length;k++){
            if(dicItemNameArr[k]==""){
                $("#submitmsg").html("第"+(k+1)+"行字典项名称不能为空！");
                return false;
            }
        }

        //判断字典项名称是否重复
        if(isRepeat(dicItemNameArr)){
            $("#submitmsg").html("字典项名称存在重复，请重新输入字典项名称！");
            return false;
        }

        //字典项值
        var dicItemValueArr = new Array();
        $("input[name='itemValue']").each(function(i,o){
            dicItemValueArr[i]=this.value;
        });

        for(var l=0;l<dicItemValueArr.length;l++){
            if(dicItemValueArr[l]==""){
                $("#submitmsg").html("第"+(l+1)+"行字典项值不能为空！");
                return false;
            }
        }

        //判断字典项是否重复
        if(isRepeat(dicItemValueArr)){
            $("#submitmsg").html("字典项值存在重复，请重新输入字典项值！");
            return false;
        }

        return true;
    }

    function isRepeat(arr){
        return /(\x0f[^\x0f]+\x0f)[\s\S]*\1/.test("\x0f"+ arr.join("\x0f\x0f") +"\x0f");
    }
    var htl = "";
    var url = "";
    var bt = "<input type='button' id='delstation' class='l-button-submit-search ' onclick='removeStationDiv(this)' value='删除'   />";
    function addStationDiv(dom) {
        var myDate = new Date();
        var tr = $(dom).parent().parent();
        tr.after("<tr id='stations" + myDate + "' style='line-height:20px;'>" + htl + "</tr>");
        var newtr = $(dom).parent().parent().next();
        newtr.find("#buttontd").append(bt);
        var tds = $("#dicItem_table").find("#showOrder[type='text']");
        $.each(tds, function(n, value) {
            $(this).val(n+1);
        });
    }

    function removeStationDiv(dom) {
        $(dom).parent().parent().remove();
    }
</script>
<body>
<form action="${ctx}/dictionary/create" method="post" id="form_id">
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="date_add_table">
        <tbody><tr style="border-bottom:1px solid #EBEBEB;">
            <td colspan="6">
                <!--
                <img src="/dxsc/mres/mres/dracodeUi/ligerUI/skins/icons/communication.gif">
                -->
                <span>数据字典</span>
            </td>
        </tr>
        <tr style="line-height:40px;">
            <td style="width:70px">字典名称：</td>
            <td style="width:160px"><div class="l-text" style="width: 180px;">
                <input name="name" id="name" validate="{required:true,messages:{required:'必填'}}" value="" class="l-text-field" style="width: 176px;" ligeruiid="name" type="text">
                <div class="l-text-l"></div><div class="l-text-r"></div></div></td>
            <td style="width:40px"></td>
            <td style="width:70px">字典编码：</td>
            <td style="width:160px"><div class="l-text" style="width: 180px;">
                <input name="code" id="code" validate="{required:true,maxlength:50,messages:{required:'必填',maxlength:'最大长度50'}}" value="" class="l-text-field" style="width: 176px;" ligeruiid="code" type="text">
                <div class="l-text-l"></div><div class="l-text-r"></div></div></td>
            <td></td>
        </tr>
        <tr style="border-bottom:1px solid #EBEBEB;">
            <td>备注：</td>
            <td colspan="4">
                <textarea cols="80" rows="5" id="memo" name="memo" class="l-textarea"></textarea>
            </td>
            <td></td>
        </tr>
        <tr style="border-bottom:1px solid #EBEBEB;line-height:40px;">
            <td colspan="6">
                <!--
                <img src="/dxsc/mres/mres/dracodeUi/ligerUI/skins/icons/communication.gif">
                -->
                <span>数据字典项</span></td>
        </tr>
        <tr style="line-height:33px;">
            <td colspan="6">
                <table id="dicItem_table" style="border:1px solid #BFCFE1;margin-top:5px;width:96%">
                    <tbody><tr style="border-bottom:1px solid #AECAF0;">
                        <td>
                            <font style="margin-left:10px;">显示顺序</font>
                        </td>
                        <td>
                            <font style="margin-left:40px;">字典项名称</font>
                        </td>
                        <td>
                            <font style="margin-left:40px;">字典项值</font>
                        </td>
                        <td>
                            <font style="margin-left:20px;">是否显示</font>
                        </td>
                        <td>
                            <font style="margin-left:30px;">操作</font>
                        </td>
                    </tr>
                    <tr id="dic_item">
                        <td>
                            <div class="l-text l-text-readonly" style="width: 60px;">
                                <input readonly="readonly" id="showOrder" value="1" name="showOrder" ligerui="{width:60}" class="l-text-field" style="width: 56px;" ligeruiid="showOrder" type="text">
                                <div class="l-text-l"></div><div class="l-text-r"></div></div>
                        </td>
                        <td>
                            <div class="l-text" style="width: 140px;"><input name="itemName" ligerui="width:140" class="l-text-field" style="width: 136px;" ligeruiid="TextBox1000" type="text">
                                <div class="l-text-l"></div><div class="l-text-r"></div></div>
                        </td>
                        <td>
                            <div class="l-text" style="width: 140px;"><input name="itemValue" ligerui="width:140" class="l-text-field" style="width: 136px;" ligeruiid="TextBox1001" type="text">
                                <div class="l-text-l"></div><div class="l-text-r"></div></div>
                        </td>
                        <td>
                            <select name="isShow" ligerui="slide:false" change="false">

                                <option value="1">显示</option>
                                <option value="2">不显示</option>
                            </select>
                        </td>
                        <td id="buttontd" width="130">
                            <input id="addstation" onclick="addStationDiv(this)" class="l-button-submit-search btn" value="添加" type="button">
                        </td>
                    </tr><tr>
                    </tr></tbody></table>

                <script type="text/javascript">
                    $(document).ready(function() {
                        htl = $("#dic_item").html();
                    });

                </script>		      		</td>
        </tr>
        <tr style="line-height:20px;">
            <td colspan="6" style="color:red;" id="submitmsg" align="center">&nbsp;</td>
        </tr>
        </tbody>
    </table>
</form>
</body>
<script>
    $(function(){
        var p = window.parent;
        var f = $("#form_id");
        var dialog = p.art.dialog.list["addDictDialog"];
        var dialog_div = dialog.DOM.wrap;
        dialog_div.on("ok",function(){
            f.submit();
        });
        var val_obj = exec_validate(f);
        val_obj.submitHandler = function(form){
            if (before() === false) {
                return;
            }else{
                $("#submitmsg").html("");
//                p.$.Layer.alert({
//                    msg:"保存成功!",
//                    fn:function(){
//                        dialog.close();
//                    }
//                });
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
        f.validate(
                val_obj
        );
    });
</script>
</html>
