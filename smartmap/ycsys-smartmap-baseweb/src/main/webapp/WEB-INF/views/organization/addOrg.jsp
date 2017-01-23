<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/views/common/taglib.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>羽辰智慧林业平台运维管理系统-添加机构</title>
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
    <script src="${res}/plugins/ligerUI/js/ligerui.all.js" type="text/javascript"></script>
</head>
<script>
    function before(){
        if($("[name='areaId']").val() == ""){
            $("#submitmsg").html("请选择地区！");
            return false;
        }else if($("#type").val() == ""){
            $("#submitmsg").html("请选择类型！");
            return false;
        }else if($("[name='pid'][type='checkbox']").length > 0 && !$("[name='pid'][type='checkbox']")[0].checked &&$("[name='pid'][type='text']:enabled").val() == "" && $("#pid_val").val() == ""){
            $("#submitmsg").html("请选择上级机构！");
            return false;
        }
        if($("[name='pid'][type='text']:enabled").val() != ""){
            //$("[name='pid'][type='text']").val($("#pid_val").val());
            $("[name='pid'][type='text']:enabled").attr("name","");
            $("#pid_val").attr("name","pid");
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
        //下拉改变事件
        $select.change(function(){
            var ts = $(this);
            var v = ts.val();
            if(v == 1 || v == "1"){
                var $is =ts.parent().find("div");
                if($is.length < 1){
                    var dom = $("<div />",{
                        style:"display:inline"
                    });
                    var chk = $("<input />",{
                        type:"checkbox",
                        name:"pid",
                        value:"-1"
                    });
                    //选中事件
                    chk.change(function(){
                       var isc = $(this)[0].checked;
                        $("[name='pid'][type='text']").attr("disabled",isc);
                        if(isc){
                            combo.setDisabled(true);
                        }else{
                            combo.setEnabled(true);
                        }
                    });
                    dom.append(chk).append("是根公司");
                    ts.parent().append(dom);
                }
            }else{
                $("[name='pid'][type='text']").attr("disabled",true);
                combo.setEnabled(true);
                ts.parent().find("div").remove();
            }
        });
        $select.html("");
        for(var dic in dics){
            var opt = $("<option />",{
                value:dic,
                text:dics[dic]
            });
            $select.append(opt);
        }
        $select.trigger("change");

        var p = window.parent[0];
        var dialog = p.art.dialog.list["addOrgDialog"];
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

        //下拉树
        var combo = $("#pid").ligerComboBox({
            width: 200,
            selectBoxWidth: 200,
            selectBoxHeight: 200,
            valueField: 'id',
            textField:'name',
            treeLeafOnly:false,
            isMultiSelect:false,
            detailPostIdField:"test",
            tree: { url: '${ctx}/org/getOrgs',
                    checkbox: false,
                ajaxType: 'get',
                idFieldName: 'id',
                parentIDFieldName:'pid',
                textFieldName:'name'}
        });
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
            <td width="120" class="t_r">上级机构：</td>
            <td ><input type="text" name="pid" id="pid" class="validate[required]" /></td>
        </tr>
        <tr>
            <td class="t_r">机构名称：</td>
            <td>
                <input type="text" name="name" id="name" class="text" validate="{required:true,messages:{required:'必填'}}"/>
            </td>
        </tr>
        <tr>
            <td class="t_r">归属区域：</td>
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
