<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/views/common/taglib.jsp" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="${res}/dist/css/AdminLTE.css">
    <link href="${res}/plugins/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
    <script src="${res}/plugins/jQuery/jquery-2.2.3.min.js"></script>
    <script src="${res}/plugins/jquery-validation-1.15.1/lib/jquery.form.js"></script>
    <script src="${res}/plugins/jquery-validation-1.15.1/dist/jquery.validate.min.js" type="text/javascript"></script>
    <script src="${res}/js/common/form.js"></script>
    <script charset="utf-8" src="${res}/plugins/kindeditor-4.1.7/kindeditor-min.js"></script>
    <script charset="utf-8" src="${res}/plugins/kindeditor-4.1.7/lang/zh_CN.js"></script>
</head>
<body>
<form action="${ctx}/platNotice/saveNotice" method="post" id="form_id">
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="date_add_table">
        <tr>
            <td class="t_r">消息标题：</td>
            <td>
                <input type="text" name="title" validate="{required:true,messages:{required:'必填'}}">
            </td>
        </tr>
        <tr>
            <td class="t_r">通知类型：</td>
            <td>
                <select type="text" name="type" id="type" class="text">
                </select>
            </td>
        </tr>
        <tr>
            <td class="t_r">消息内容: </td>
            <td>
                <textarea id="content" name="content" style="width:580px;height:200px;visibility:hidden;"></textarea>
            </td>
        </tr>
        <tr style="line-height:20px;">
            <td colspan="6" style="color:red;" id="submitmsg" align="center">&nbsp;</td>
        </tr>
    </table>
</form>
<script>
    function before(){
        if($("#content").val() == ""){
            $("#submitmsg").html("消息内容不能为空！");
            return false;
        }
        return true;
    };
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
    var dics = getDicMappers("platMsg_type");
    var $select = $("#type");
    $select.html("");
    for(var dic in dics){
        var opt = $("<option />",{
            value:dic,
            text:dics[dic]
        });
        $select.append(opt);
    };
    $(function(){
        var p = window.parent;
        var dialog = p.art.dialog.list["addNotice"];
        var $fm = $("#form_id");
        var dialog_div = dialog.DOM.wrap;
        dialog_div.on("ok",function(){
            send_contents.sync();
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
                            console.log(p);
                            p[0].getLigerManager().loadData();
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
     KindEditor.ready(function(K) {
        K.each({
            'plug-align' : {
                name : '对齐方式',
                method : {
                    'justifyleft' : '左对齐',
                    'justifycenter' : '居中对齐',
                    'justifyright' : '右对齐'
                }
            },
            'plug-order' : {
                name : '编号',
                method : {
                    'insertorderedlist' : '数字编号',
                    'insertunorderedlist' : '项目编号'
                }
            },
            'plug-indent' : {
                name : '缩进',
                method : {
                    'indent' : '向右缩进',
                    'outdent' : '向左缩进'
                }
            }
        },function( pluginName, pluginData ){
            var lang = {};
            lang[pluginName] = pluginData.name;
            KindEditor.lang( lang );
            KindEditor.plugin( pluginName, function(K) {
                var self = this;
                self.clickToolbar( pluginName, function() {
                    var menu = self.createMenu({
                        name : pluginName,
                        width : pluginData.width || 100
                    });
                    K.each( pluginData.method, function( i, v ){
                        menu.addItem({
                            title : v,
                            checked : false,
                            iconClass : pluginName+'-'+i,
                            click : function() {
                                self.exec(i).hideMenu();
                            }
                        });
                    })
                });
            });
        });
         window.send_contents = K.create('#content', {
            themeType : 'qq',
            items : [
                'bold','italic','underline','fontname','fontsize','forecolor','hilitecolor','plug-align','plug-order','plug-indent','link'
            ]
        });
    });
</script>
</body>
</html>