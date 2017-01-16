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

    <script src="${res}/plugins/jQuery/jquery-2.2.3.min.js"></script>
    <script src="${res}/plugins/jquery-validation-1.15.1/dist/jquery.validate.min.js"></script>
    <script src="${res}/plugins/jquery-validation-1.15.1/lib/jquery.form.js"></script>
    <script src="${res}/js/common/form.js"></script>
    <script src="${res}/plugins/ligerUI/js/ligerui.all.js" type="text/javascript"></script>
</head>
<body>
<form action="${ctx}/serviceMonitor/update" method="post" id="form_id">

    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="date_add_table">

        <tr>
            <td width="120" class="t_r">服务显示名称：</td>
            <td >
                <input type="text" style="display: none" name="id" value="${service.id}">
                <span>${service.showName}</span>
            </td>
        </tr>
        <tr>
            <td class="t_r">监控地址：</td>
            <td><input type="text" name="serviceVisitAddress" id="serviceVisitAddress" readonly value="${service.serviceVisitAddress}"/></td>
        </tr>
        <tr>
            <td class="t_r">监控状态：</td>
            <td >
                <select name="monitorStatus" id="monitorStatus" >
                </select>
            </td>
        </tr>
        <tr>
            <td class="t_r">监控频率：</td>
            <td><input type="text" name="monitorRate" id="monitorRate" validate="{required:true,number:true,messages:{required:'必填',number:'必须为数字！'}}" value="${service.monitorRate}"></td>
        </tr>
        <tr>
            <td class="t_r">监控方式：</td>
            <td><input type="text" name="monitorType" id="monitorType" value="HTTP" readonly></td>
        </tr>
        <tr style="line-height:20px;">
            <td colspan="6" style="color:red;" id="submitmsg" align="center">&nbsp;</td>
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
        return true;
    }
    $(function(){
        //获取数据字典
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
        var service_status = getDicMappers("service_status");
        var $service_status = $("#monitorStatus");
        for(var dic in service_status){
            var opt = $("<option />",{
                value:dic,
                text:service_status[dic]
            });
            $service_status.append(opt);
        }
        //回显
        var monitorStatus = "${service.monitorStatus}";
        $("select[name='monitorStatus']").val(monitorStatus);
        var p = window.parent[0];
        var dialog = p.art.dialog.list["updateDialog"];
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
                            p.getLigerManager("maingrid4").loadData();
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