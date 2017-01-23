<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/views/common/taglib.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>羽辰智慧林业平台运维管理系统-服务管理</title>
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
<!-- Bootstrap 3.3.6 -->
  <link rel="stylesheet" href="${res}/bootstrap/css/bootstrap.css">
  <!-- iconfont -->
  <link rel="stylesheet" href="${res}/iconfont/iconfont.css">
  <!-- Theme style -->
  <link rel="stylesheet" href="${res}/dist/css/AdminLTE.css">
  <!-- AdminLTE Skins. Choose a skin from the css/skins
       folder instead of downloading all of them to reduce the load. -->
  <link rel="stylesheet" href="${res}/dist/css/skins/_all-skins.css">
  <!-- iCheck -->
  <link rel="stylesheet" href="${res}/plugins/iCheck/flat/blue.css">
  <!-- list -->
  <link href="${res}/plugins/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
  <!-- 弹出框 -->
  <link href="${res}/plugins/dialog/dialog.css" rel="stylesheet" type="text/css">
    <script src="${res}/js/common/multiselect.js"></script>
    <%-- <script src="${res}/plugins/jquery-validation-1.15.1/lib/jquery.form.js"></script> --%>
    <script src="${res}/js/common/form.js"></script>
	<script src="${res}/plugins/jQuery/jquery-2.2.3.min.js"></script>
	<script src="${res}/plugins/jquery-validation-1.15.1/lib/jquery.form.js"></script>
	<script src="${res}/plugins/jquery-validation-1.15.1/dist/jquery.validate.min.js" type="text/javascript"></script>
	<!-- 封装弹出框dialog -->
	<script type="text/javascript" src="${res}/plugins/dialog/jquery.artDialog.source.js"></script>
	<script type="text/javascript" src="${res}/plugins/dialog/iframeTools.source.js"></script>
	<script type="text/javascript" src="${res}/plugins/dialog/unit.js"></script>
</head>
<body>
<form action="${ctx}/configServerEngine/importFile" method="post" id="form_id" enctype="multipart/form-data">
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="date_add_table">

        <tr>
            <td width="120" class="t_r">选择文件：</td>
            <td ><input type="file" name="file" id="file1" class="text validate[required]" /></td>
        </tr>

    </table>
</form>
</body>
<script>
    $(function(){
        var p = window.parent[0];
        var dialog = p.art.dialog.list["importConfigServerEngineDialog"];
        var $fm = $("#form_id");
        var dialog_div = dialog.DOM.wrap;
        dialog_div.on("ok",function(){
            $fm.submit();
        });
        var val_obj = exec_validate($fm);
        val_obj.submitHandler = function(form){
                $(form).ajaxSubmit({
                    success:function(data){
                       /*  if(data.retCode) {
                            p.getLigerManager().loadData();
                            dialog.close();
                        }else{
                            alert(data.retMsg);
                        } */
                        console.log(data);
                        console.log(data.retCode);
                        dialog.close();
                        if(data.retCode) {
                            //p.getLigerManager().loadData();
                            $.Layer.confirm({
            	                msg:"导入成功",
            	                fn:function(){
            	                	//console.log(3);
            	                	//gridManager.reload();
            	                	 p.getLigerManager().loadData();
            	                	//console.log(4);
            	                	
            	                }
            	            });
                        }else{
                            alert(data.retMsg);
                        }
                    	
                    },
                    dataType:"json"
                }); 
               
        };
        $fm.validate(
                val_obj
        );
    });
</script>
</html>