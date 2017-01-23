<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/views/common/taglib.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>羽辰智慧林业平台运维管理系统-修改字典</title>
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
    .date_add_table{
        margin:10px 9px 0;
    }
</style>
<body>
<form action="${ctx}/dictionary/update" method="post" id="form_id">
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
                <input name="id" style="display:none" value="${dictionary.id}">
                <input name="name" id="name" validate="{required:true,messages:{required:'必填'}}" value="${dictionary.name}" class="l-text-field" style="width: 176px;" ligeruiid="name" type="text">
                <div class="l-text-l"></div><div class="l-text-r"></div></div></td>
            <td style="width:40px"></td>
            <td style="width:70px">字典编码：</td>
            <td style="width:160px"><div class="l-text" style="width: 180px;">
                <input name="code" id="code" validate="{required:true,maxlength:50,messages:{required:'必填',maxlength:'最大长度50'}}" value="${dictionary.code}" class="l-text-field" style="width: 176px;" ligeruiid="code" type="text">
                <div class="l-text-l"></div><div class="l-text-r"></div></div></td>
            <td></td>
        </tr>
        <tr style="border-bottom:1px solid #EBEBEB;">
            <td>备注：</td>
            <td colspan="4">
                <textarea cols="80" rows="5" id="memo" name="memo" class="l-textarea">${dictionary.memo}</textarea>
            </td>
            <td></td>
        </tr>
        </tbody>
    </table>
    <br>
    <div class="aui_buttons" style="border-top:0;background-color:unset">
        <button class="bt_sub aui_state_highlight" type="submit">提交</button>
        <button type="button" onclick="close_dialog()">关闭</button>
    </div>
</form>
<div style=" margin:2px 10px;">
<span>数据字典项</span>
<div style="max:250px;overflow:auto;">
    <div id="maingrid" style="width: 99%;" class="list"></div>
</div>
    </div>
</body>
<script>
    //关闭当前窗口
    function close_dialog(){
        var p = window.parent;
        var dialog = p.art.dialog.list["updateDictDialog"];
        dialog.close();
    }
    //打开修改字典子项窗口
    function yc_updateItem(id){
        art.dialog.open("${ctx}/dictionary/updateDictItemv?itemId=" + id,{
            id:"updateDictItemDialog",
            title: '修改字典子项',
            width: 680,
            height: 450,
            lock: true
        });
    }
    //打开删除子项窗口
    function yc_deleteItem(id){
        $.Layer.confirm({
            msg:"确定删除该子项？",
            fn:function(){
                $.ajax({
                    url:"${ctx}/dictionary/deleteItem",
                    data:{id:id},
                    type:"post",
                    dataType:"json",
                    success:function(res){
                        getItemManager().loadData();
                        alert(res.retMsg);
                    },error:function(){
                        alert("删除失败！");
                    }
                });
            },
            fn2:function(){
            }
        });
    }
    function getItemManager(){
        return $("#maingrid").ligerGetGridManager();
    }
    var dicMappers = {
        "isShow":{
            "1":"是",
            "2":"否"
        }
    };
    $(function(){
        var p = window.parent[0];
        var f = $("#form_id");
        var dialog = p.art.dialog.list["updateDictDialog"];
        var val_obj = exec_validate(f);
        //表单验证
        val_obj.submitHandler = function(form){
            //表单提交
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
        };
        f.validate(
                val_obj
        );
        //列表
        $("#maingrid").ligerGrid({
            checkbox: false,
            columns: [
                { display: '显示顺序', name: 'sort',minWidth:127},
                { display: '字典项名称', name: 'name',minWidth:130},
                { display: '字典项值', name: 'value',minWidth:130},
                { display: '是否显示', name: 'isShow',minWidth:130,render:function(row,rowId,value,item){
                    var text = typeof(value) == 'undefined' || value == null ? '' : value;
                    var dic = dicMappers[item.name];
                    if(dic) {
                        text = dic[text] || text;
                    }
                    return text;
                }},
                { display: '操作',minWidth:130,
                    render: function (rowdata, rowindex, value)
                    {
                        var str = JSON.stringify(rowdata);
                        var h = "";
                        if (!rowdata._editing)
                        {
                            h += "<input type='button' class='list-btn bt_edit' onclick='yc_updateItem(" + rowdata.id + ")'/>";
                            h += "<input type='button' class='list-btn bt_del' onclick='yc_deleteItem(" + rowdata.id + ")'/>";
                        }
                        return h;
                    }
                }
            ],
            alternatingRow:true,
            allowAdjustColWidth:true,
            url:"${ctx}/dictionary/listItemData",
            width: '96%',height:'240',
            parms:[{"name":"dictId",value:"${dictionary.id}"}],
            usePager:false,
            toolbar: {
            items: [//新增子项按钮
                { text: '新增', click: function(){
                    art.dialog.open("${ctx}/dictionary/addItemv?dictId=" + '${dictionary.id}',{
                        id:"addDictItemDialog",
                        title: '新增字典子项',
                        width: 680,
                        height: 450,
                        lock: true
                    });
                }, icon: 'add'}
            ]
        }
        });
    });
</script>
</html>
