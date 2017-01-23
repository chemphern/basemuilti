<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/views/common/taglib.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>羽辰智慧林业平台运维管理系统-用户管理更新</title>
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
<form action="${ctx}/user/saveOrUpdate" method="post" id="form_id">

    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="date_add_table">

        <tr>
            <td width="120" class="t_r">所属机构：</td>
            <td >
                <input type="text" style="display: none" name="id" value="${user.id}">
                <input type="text" style="display: none" name="orgId" value="${user.organization.id}">
                <span>${user.organization.name}</span>
            </td>
        </tr>
        <tr>
            <td class="t_r">拥有角色：</td>
            <td>
                <input id="roleName" readonly name="roleName" type="text">
            </td>
        </tr>
        <tr>
            <td class="t_r">用户姓名：</td>
            <td><input type="text" name="name" id="name" validate="{required:true,messages:{required:'必填'}}" value="${user.name}"/></td>
        </tr>
        <tr>
            <td class="t_r">登录名：</td>
            <td><input type="text" name="loginName" id="loginName"  validate="{required:true,messages:{required:'必填'}}" value="${user.loginName}"/></td>
        </tr>

        <tr>
            <td class="t_r">密码：</td>
            <td>
                <input type="password" name="password" id="password">
            </td>
        </tr>
        <tr>
            <td class="t_r">确认密码：</td>
            <td>
                <input type="password" name="plainPassword" id="plainPassword">
            </td>
        </tr>
        <tr>
            <td class="t_r">性别：</td>
            <td >
                <select name="sex" id="sex" >
                </select>
            </td>
        </tr>
        <tr>
            <td class="t_r">用户类型：</td>
            <td >
                <select name="type" id="type" >
                </select>
            </td>
        </tr>
        <tr>
            <td class="t_r">邮箱：</td>
            <td><input type="text" name="email" id="email" value="${user.email}"></td>
        </tr>
        <tr>
            <td class="t_r">手机号码：</td>
            <td><input type="text" name="phone" id="phone" value="${user.phone}"></td>
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
        if($("#password").val() != $("#plainPassword").val()){
            $("#submitmsg").html("两次输入密码不一致！");
            return false;
        };
        if($("#roleId").val() == ""){
            $("#submitmsg").html("请选择拥有的角色！");
            return false;
        }else {
            var obj = $("#roleId").val().split(",").doUnique();
            $("#roleId").val(obj.join(","));
        }
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
        var type_dics = getDicMappers("user_type");
        var sex_dics = getDicMappers("sex");
        var $typeselect = $("#type");
        var $sexselect = $("#sex");
        for(var dic in type_dics){
            var opt = $("<option />",{
                value:dic,
                text:type_dics[dic]
            });
            $typeselect.append(opt);
        }
        for(var dic in sex_dics){
            var opt = $("<option />",{
                value:dic,
                text:sex_dics[dic]
            });
            $sexselect.append(opt);
        }
        //回显性别和类型
        var tsex = "${user.sex}";
        var ttype = "${user.type}";
        $("select[name='sex']").val(tsex);
        $("select[name='type']").val(ttype);
        var p = window.parent[0];
        var dialog = p.art.dialog.list["updateUserDialog"];
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
                            p.getLigerGridManager("maingrid4").loadData();
                            dialog.close();
                        }else{
                            alert(data.retMsg);
                        }
                    },error:function(){
                        alert("添加失败");
                    },
                    dataType:"json"
                });

            }
        };
        $fm.validate(
                val_obj
        );
        $("#roleName").ligerComboBox({
            isShowCheckBox: true,
            url:"${ctx}/role/getRoles",
            textField:"name",
            valueField:"id",
            isMultiSelect:true,
            split:",",
            valueFieldID: 'roleId',
            label:'选择角色',
            labelWidth:200,
            labelAlign:'center',
            onBeforeSetData:function(data){
                var m = $("#roleName").ligerGetComboBoxManager();
                var arr = ${roleIds};
                if(arr.length > 0)
                m.selectValue(${roleIds});
            }
        });
    });
</script>
</html>