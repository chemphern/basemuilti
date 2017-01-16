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
    <script charset="utf-8" src="${res}/plugins/ligerUI/js/ligerui.min.js"></script>
    <style type="text/css">
        .middle input {
            display: block;width:35px; margin:2px;
        }
        .llistbox div table tbody tr td{
            border:none !important;
            padding:0px;
        }
        caption, th {
            text-align: center;
        }
        .shaixuan input[type="text"]{
            height:24px;
            width:120px;
        }
        .shaixuan input[type="button"]{
            width:80px;
            height:24px;
            border:0;
            background:#2DC3E8;
            color:#fff;
            line-height:24px;
            margin-left:8px;
            border-radius:3px;
        }
        .view tbody td,.view tbody th{padding:8px;border:1px solid #ddd;}
        .line-space span{padding-right:8px;}
    </style>
</head>
<body>
<table class="view">
    <tr>
        <th style="width:200px;">发送对象</th>
        <td>
            <div style="margin-left:15px;padding-top:10px;" class="line-space">
                <input type="radio" value="1" id="fenzhi" name="ttype" checked onclick="fzjg()"><span>按分支机构</span>
                <input type="radio" value="2" id="juese" name="ttype" onclick="juese()"><span>按角色</span>
                <input type="radio" value="3" id="jingji" name="ttype" onclick="jjr()" ><span>按用户</span>
                <div style="display:inline;padding-left:15px;" id="shaixuan" class="shaixuan"></div>
            </div>
            <div id="member_select">
                <div style="width: 350px; height: 200px; margin: 10px; margin-right: 1px; float: left;border: 1px solid #ccc; overflow: auto;background-color:#fff">
                    <div id="tree1"></div>
                </div>
                <div style="margin:4px;float:left;padding-top:60px;padding-left:10px;" class="middle">
                    <input onclick="moveToLeft()" value="<" type="button">
                    <input onclick="moveToRight()" value=">" type="button">
                    <input onclick="moveAllToLeft()" value="<<" type="button">
                    <input onclick="moveAllToRight()" value=">>" type="button">
                </div>
                <div style="width: 350px; height: 200px; margin: 10px; margin-right: 1px; float: left;border: 1px solid #ccc; overflow: auto;">
                    <div id="list1"></div>
                </div>
            </div>
        </td></tr>
    <form id="ljform">
        <tr>
            <th>消息标题</th>
            <td>
                <c:if test="${message == null}">
                    <input type="text" style="width:650px;height:21px;" name="title" id="title">
                </c:if>
                <c:if test="${message != null}">
                    <input type="text" style="width:650px;height:21px;" name="title" id="title" value="${message.title}" readonly>

                </c:if>
            </td>
        </tr>
        <tr>
            <th>通知类型</th>
            <td>
                <select type="text" name="type" id="type" class="text">
                </select>
            </td>
        </tr>
        <tr>
            <th>消息内容</th>
            <td>
                <textarea id="content" style="width:780px;height:300px;" name="content"><c:if test="${message != null}">${message.content}</c:if></textarea>
                </td>
        </tr>
        <tr>
            <th colspan="2">
                <input type="button" value="发送" style="width:150px;border:0;background:#2DC3E8;height:34px;color:#fff;border-radius:3px;" onclick="message_send()" id="btn_submit">
            </th>
        </tr>
        <div style="display:none">
            <textarea name="send_companys" id="send_companys"></textarea>
            <textarea name="send_roles" id="send_roles"></textarea>
            <textarea name="send_users" id="send_users"></textarea>
            <#if message?exists>
                <textarea name="message_id" id="message_id">${message.id}</textarea>
            </#if>
        </div>
    </form>
</table>
<script>
    //将平行结构的树转化成树状结构的树
    function parseDataToTree(obj){
        var idKey = obj.idKey?obj.idKey:"id";
        var pidKey = obj.pidKey?obj.pidKey:"pid";
        var childrenKey = obj.childrenKey?obj.childrenKey:"children";
        var copyData = obj.data.slice(0);
        var orm = {};
        for(var x = 0;x<obj.data.length;x++){
            for(var y = 0;y<obj.data.length;y++){
                var _temp_data = obj.data;
                if(_temp_data[y][pidKey] == obj.data[x][idKey]){
                    delete copyData[y];
                    var ts_id = _temp_data[x][idKey];
                    if(typeof orm[ts_id] == "undefined"){
                        orm[ts_id] = [];
                    }
                    orm[ts_id].push(_temp_data[y]);
                }
            }
        }
        var delete_none_temp;
        var delete_none = [];
        for(var x = 0;x<copyData.length;x++){
            delete_none_temp = copyData[x];
            if(typeof delete_none_temp != "undefined"){
                delete_none.push(delete_none_temp);
            }
        }
        var final = [];
        var doOrm= function(obj,orm,idKey){
            if(orm[obj[idKey]]){
                var _temp_o = orm[obj[idKey]];
                for(var x = 0;x<_temp_o.length;x++){
                    doOrm(_temp_o[x],orm,idKey);
                }
                obj[childrenKey] = _temp_o;
            }
        };
        for(var index in delete_none){
            var _object = delete_none[index];
            doOrm(_object,orm,idKey);
            final.push(_object);
        }
        return final;
    }
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


    function message_send(){
        var obj = liger.get("list1").data;
        if(obj){
            var companys = [];
            var roles = [];
            var users = [];
            for(var x = 0;x<obj.length;x++){
                var r = obj[x];
                if(r.type == 1){
                    companys.push(r.id);
                }else if(r.type == 2){
                    roles.push(r.id);
                }else if(r.type == 3){
                    users.push(r.id);
                }
            }
            $("#send_companys").html(companys.join());
            $("#send_roles").html(roles.join());
            $("#send_users").html(users.join());
        }
        send_contents.sync();
        var validate_val = validate_form();
        if(validate_val){
            $.ligerDialog.error(validate_val);
        }else{
            $("#message_send").attr("onclick","");
            $.ajax({
                url:"${ctx}/platNotice/sendNoticeDo",
                data:$("#ljform").serialize(),
                type:"post",
                dataType:"json",
                success:function(data){
                    if(data.retCode){
                        $.ligerDialog.success(data.retMsg,"提示",function (type) {
                            window.location.href="${ctx}/platNotice/sendNotice";
                        });
                    }else{
                        $.ligerDialog.error(data.retMsg);
                        $("#btn_submit").attr("onclick","message_send()");
                    }
                },
                error:function(){
                    $.ligerDialog.error("发送失败");
                    $("#btn_submit").attr("onclick","message_send()");
                }
            });
        }
    }
    function validate_form(){
        var tip = "";
        tip = !($("#send_companys").html() || $("#send_roles").html() || $("#send_users").html())?"发送对象不能为空！":
            $.trim($("#title").val()) == ""?"消息标题不能为空！":$.trim($("#content").val()) == ""?"消息内容不能为空！":"";
        return tip;
    }
    $("#fenzhi,#juese,#jingji").ligerRadio();
    var objectSelect = {
        _init:function(uri){
            $.ajax({
                url:uri,
                success:function(data){
                    var treeData = parseDataToTree({data:data});
                    $("#tree1").ligerTree({
                        data:treeData,
                        autoCheckboxEven:false,
                        nodeWidth:220
                    });
                }
            })
        },
        initTree:function(uri){
            if(uri === undefined)
                uri = "${ctx}/platNotice/getOrgData"
            if(liger.get("tree1") === undefined){
                this._init(uri);
            }else{
                $.ajax({
                    url:uri,
                    success:function(data){
                        var treeData = parseDataToTree({data:data});
                        liger.get("tree1").setData(treeData);
                    }
                })
            }
        },
        initList:function(uri){
            if(uri === undefined)
                uri = "${ctx}/platNotice/getRoleData";
            $.ajax({
                url:uri,
                success:function(data){
                    liger.get("tree1").setData(data);
                }
            })
        }
    }
    $("#list1").ligerListBox({
        isShowCheckBox: true,
        isMultiSelect: true,
        height: 198.5,
        width:348.5,
        css:"llistbox",
        valueField: 'id',
        textField: 'text'
    });
    var selectbutton = {
        0:'',//'<form id="sxform" style="padding-top:12px;"><label>机构名称：</label><input name="company_name" type="text" style=""><input type="button" value="筛选" onclick="selectbutton.submit(0)"></form>',
        1:'',
        2:'',//'<form id="sxform" style="padding-top:12px;"><label>登录名：</label><input name="user_name" type="text"><label>&nbsp;&nbsp;昵称：</label><input name="nickname" type="text"><label>&nbsp;&nbsp;经纪机构：</label><input name="company_name" type="text"><input type="button" value="筛选" onclick="selectbutton.submit(2)"></form>',
        uri:["${ctx}/message/getCompanys.mc","${ctx}/message/getRole.mc","${ctx}/message/getUsers.mc"],
        init:function(x){
            $("#shaixuan").html(this[x]);
        },
        submit:function(x){
            var fdata = $("#sxform").serialize();
            $.ajax({
                url:this.uri[x],
                data:fdata,
                success:function(data){
                    liger.get("tree1").setData(data);
                }
            });
        }
    }
    //选择对象单选按钮
    function fzjg(){
        selectbutton.init(0);
        objectSelect.initTree();
    }
    function juese(){
        selectbutton.init(1);
        objectSelect.initList();
    }
    function jjr(){
        selectbutton.init(2);
        objectSelect.initList('${ctx}/platNotice/getUserData');
    }
    //功能
    function moveToRight(){
        var leftChecked = liger.get("tree1").getChecked();
        var arr = [];
        for(x in leftChecked){
            arr.push(leftChecked[x]["data"]);
        }
        liger.get("list1").addItems(arr);
    }
    function moveToLeft(){
        var list1 = liger.get("list1");
        var listData = list1.getSelectedItems();
        list1.removeItems(listData);
    }
    function moveAllToRight(){
        var leftData = liger.get("tree1").data;
        var leftTreeData = parseTreeDataToList(leftData);
        liger.get("list1").addItems(leftTreeData);
    }
    function moveAllToLeft(){
        var list1 = liger.get("list1");
        list1.removeItems(list1.data)
    }
    function parseTreeDataToList(treeData,flag){
        if(flag === undefined){
            window.messageTreeData = [];
        }
        for(x in treeData){
            var data = treeData[x];
            if(data.children){
                messageTreeData.push(data);
                parseTreeDataToList(data.children,true);
            }else{
                messageTreeData.push(data);
            }
        }
        var result;
        if(flag === undefined){
            result = messageTreeData.slice(0);
            window.messageTreeData = null;
        }
        return result;
    }
    objectSelect.initTree();
    selectbutton.init(0);

    $(function(){
        <c:if test="${message != null}">
            $("#type").val(${message.type});
        </c:if>
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
