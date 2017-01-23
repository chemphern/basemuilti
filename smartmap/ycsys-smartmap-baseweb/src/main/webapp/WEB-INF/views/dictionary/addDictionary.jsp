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
    <!-- tree -->
    <link href="${res}/plugins/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
    <script src="${res}/plugins/jQuery/jquery-2.2.3.min.js"></script>
    <script src="${res}/plugins/jquery-validation-1.15.1/dist/jquery.validate.min.js"></script>
    <script src="${res}/plugins/jquery-validation-1.15.1/lib/jquery.form.js"></script>
    <script src="${res}/js/common/form.js"></script>
</head>
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
                <h3>数据字典</h3>
            </td>
        </tr>
        <tr>
            <td class="t_r">字典名称：</td>
            <td>
                <input name="name" id="name" validate="{required:true,messages:{required:'必填'}}" value="" ligeruiid="name" type="text">
            </td>
       	</tr>
        <tr>
            <td class="t_r">字典编码：</td>
            <td>
                <input name="code" id="code" validate="{required:true,maxlength:50,messages:{required:'必填',maxlength:'最大长度50'}}" value="" ligeruiid="code" type="text">
            </td>
        </tr>
        <tr>
            <td class="t_r">备注：</td>
            <td>
                <textarea id="memo" name="memo"></textarea>
            </td>
        </tr>
        <tr style="border-bottom:1px solid #EBEBEB;line-height:40px;">
            <td colspan="6">
                <!--
                <img src="/dxsc/mres/mres/dracodeUi/ligerUI/skins/icons/communication.gif">
                -->
                <h3>数据字典项</h3></td>
        </tr>
        <tr style="line-height:33px;">
            <td colspan="6">
            			<div class="table-responsive">
						  <table id="dicItem_table" class="table table-bordered"  style="width:96%">
						    <tr>
								<th class="th">显示顺序</th> 
								<th class="th">字典项名称</th> 
								<th class="th">字典项值</th> 
								<th class="th">是否显示</th>
								<th class="th">操作</th>
							</tr>
                    <tr id="dic_item">
                        <td class="td">
                                <input readonly="readonly" id="showOrder" value="1" name="showOrder" ligerui="{width:60}" ligeruiid="showOrder" type="text" style="width:20px; border:none; text-align: center;">
                        </td>
                        <td class="td">
                            <div class="l-text" style="width: 140px;"><input name="itemName" ligerui="width:140" class="l-text-field" style="width: 136px;" ligeruiid="TextBox1000" type="text">
                                <div class="l-text-l"></div><div class="l-text-r"></div></div>
                        </td>
                        <td class="td">
                            <div class="l-text" style="width: 140px;"><input name="itemValue" ligerui="width:140" class="l-text-field" style="width: 136px;" ligeruiid="TextBox1001" type="text">
                                <div class="l-text-l"></div><div class="l-text-r"></div></div>
                        </td>
                        <td class="td">
                            <select name="isShow" ligerui="slide:false" change="false">

                                <option value="1">显示</option>
                                <option value="2">不显示</option>
                            </select>
                        </td>
                        <td id="buttontd" width="130" class="td">
                            <input id="addstation" onclick="addStationDiv(this)" class="l-button-submit-search btn" value="添加" type="button" style="  text-align: center;">
                        </td>
                    </tr>
						  </table>
						</div>

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
        var p = window.parent[0];
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
