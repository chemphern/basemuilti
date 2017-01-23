<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/views/common/taglib.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>羽辰智慧林业平台运维管理系统-添加子机构</title>
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <!-- iconfont -->
    <link rel="stylesheet" href="${res}/iconfont/iconfont.css">
    <!-- Theme style -->
    <link rel="stylesheet" href="${res}/dist/css/AdminLTE.css">
    <link href="${res}/plugins/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
    <script src="${res}/plugins/jQuery/jquery-2.2.3.min.js"></script>
    <script src="${res}/js/common/multiselect.js"></script>
    <script src="${res}/plugins/jquery-validation-1.15.1/dist/jquery.validate.min.js"></script>
    <script src="${res}/plugins/jquery-validation-1.15.1/lib/jquery.form.js"></script>
    <script src="${res}/js/common/form.js"></script>
</head>
<script>
    function before(){
        if($("[name='areaId']").val() == ""){
            $("#submitmsg").html("请选择地区！");
            return false;
        }else if($("#type").val() == ""){
            $("#submitmsg").html("请选择类型！");
            return false;
        }
        return true;
    }
    $(function(){
        multi_select({
            id:"place",//id
            pram://参数映射[左边为自己能识别的参数，右边为远程参数]
            {"areaId":"code",
                "areaName":"allName",
                "name":"name"},
            level:3,//显示层级
            url:"${ctx}/area/getAreas",//远程地址
            key:"areaId",//option里的值
            params:"areaId",//请求参数
            text:"name",//option里显示的文本
            showName:"areaId,areaName"
        });
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
        var dics = getDicMappers("org_type");
        var $select = $("#type");
        $select.html("");
        for(var dic in dics){
            var opt = $("<option />",{
                value:dic,
                text:dics[dic]
            });
            $select.append(opt);
        }

        var p = window.parent[0];
        var dialog = p.art.dialog.list["addOrgChildDialog"];
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
    });
</script>
<body>
<form action="${ctx}/org/saveOrUpdate" method="post" id="form_id">

    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="date_add_table">
        <tr>
            <td class="t_r">机构类型：</td>
            <td>
                <select name="type" id="type" >
                </select>
            </td>
        </tr>
        <tr>
            <td width="120" class="t_r">上级机构</td>
            <td ><input type="text" name="pid" id="pid" style="display: none" value="${pid}"/>
                <input type="text" name="pname" id="pname"  value="${pname}" readonly/></td>
        </tr>
        <tr>
            <td class="t_r">机构名称：</td>
            <td>
                <input type="text" name="name" id="name" class="text" validate="{required:true,messages:{required:'必填'}}"/>
            </td>
        </tr>
        <tr>
            <td class="t_r">归属区域</td>
            <td><div id="place"/></td>
        </tr>

        <tr>
            <td class="t_r">机构编码：</td>
            <td>
                <input type="text" name="code" id="code" class="text validate[required]" validate="{required:true,messages:{required:'必填'}}"/>
            </td>
        </tr>
        <tr>
            <td class="t_r">备注：</td>
            <td><textarea name="remark" id="remark" cols="20"rows="5" class="text_area"></textarea></td>
        </tr>
        <tr style="line-height:20px;">
            <td colspan="6" style="color:red;" id="submitmsg" align="center">&nbsp;</td>
        </tr>
    </table>
</form>
</body>
</html>
