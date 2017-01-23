<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/views/common/taglib.jsp" %>
<html>
<head>
    <title>羽辰智慧林业平台运维管理系统-运维监控日志</title>
    <link rel="stylesheet" href="${res}/dist/css/AdminLTE.css">
    <link href="${res}/plugins/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
    <script src="${res}/plugins/jQuery/jquery-2.2.3.min.js"></script>
    <script src="${res }/plugins/My97DatePicker/WdatePicker.js"></script>
    <script src="${res}/plugins/jquery-validation-1.15.1/lib/jquery.form.js"></script>
    <script src="${res}/plugins/jquery-validation-1.15.1/dist/jquery.validate.min.js" type="text/javascript"></script>
    <script src="${res}/js/common/form.js"></script>
</head>
<body>
<form action="${ctx}/log/export" method="post" id="form_id">
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="date_add_table">
        <tr>
            <td width="120" class="t_r">起始时间：</td>
            <td >
                <input type="text" id="startTimes" name="startTimes" class="text date_plug" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" validate="{required:true,messages:{required:'必选'}}"/>
                <input name="startTime" id="startTime" style="display:none">
            </td>
        </tr>
        <tr>
            <td class="t_r">截止时间：</td>
            <td><input type="text" id="endTimes" name="endTimes" class="text date_plug" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" validate="{required:true,messages:{required:'必选'}}"/>
                <input name="endTime" id="endTime" style="display:none">
            </td>
        </tr>
        <tr>
            <td class="t_r">日志类型：</td>
            <td>
                <select type="text" name="operationType" id="operationType" class="text">
                    <option value="-1">所有</option>
                    <option value="1">基础类</option>
                    <option value="2">服务管理类</option>
                    <option value="3">资源管理类</option>
                    <option value="4">统计分析类</option>
                    <option value="5">图层类</option>
                    <option value="6">监控类</option>
                </select>
            </td>
        </tr>
        <tr>
            <td class="t_r">日志状态：</td>
            <td>
                <select type="text" name="status" id="status" class="text">
                    <option value="-1">所有</option>
                    <option value="1">一般</option>
                    <option value="2">严重</option>
                </select>
            </td>
        </tr>
        <tr>
            <td class="t_r">导出条数</td>
            <td><input type="text" name="num" value="500" validate="{required:true,messages:{required:'必填'}}"></td>
        </tr>
    </table>
</form>
<script>
    $(function(){
        var p = window.parent;
        var dialog = p.art.dialog.list["exportLog"];
        var $fm = $("#form_id");
        var dialog_div = dialog.DOM.wrap;
        dialog_div.on("ok",function(){
            $fm.submit();
        });
        var val_obj = exec_validate($fm);
        val_obj.submitHandler = function(form){
            var startTimes = $("#startTimes").val();
            var endTimes = $("#endTimes").val();
            $("#startTime").val(new Date(startTimes).getTime());
            $("#endTime").val(new Date(endTimes).getTime() + 24*60*60*1000 -1);
            var ser = $fm.serialize();
            dialog.close();
            p.location.href="${ctx}/log/export?" + ser;
        };
        $fm.validate(
            val_obj
        );
    });
</script>
</body>
</html>
