<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%request.setAttribute("path", request.getContextPath()); %>
<!DOCTYPE html>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!--   <meta http-equiv="X-UA-Compatible" content="IE=edge"> -->
  <title>羽辰智慧林业二三维一体化应用系统</title>
  <!-- Tell the browser to be responsive to screen width -->
  <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
  <link rel="stylesheet" href="${path}/static/dist/css/mapPublic.css">
  <style type="text/css">
  	body{height:100%}
  	tr,td{padding:5px}
  	#bookPos{background: #F0F0F0}
  </style>
  <!-- jQuery  -->
  <script src="${path}/static/plugins/jQuery/jquery-2.2.3.min.js"></script>
  <script src="${path}/static/plugins/jquery-validation-1.15.1/lib/jquery.form.js"></script>
  <script src="${path}/static/plugins/dialog/jquery.artDialog.source.js"></script>
<script src="${path}/static/plugins/dialog/iframeTools.source.js"></script>
<script src="${path}/static/plugins/dialog/unit.js"></script>
<script type="text/javascript">
	function getExtent(){
		var ext=parent.getMapExtentLngLat();
		$("#xmin").val(ext.xmin);
		$("#ymin").val(ext.ymin);
		$("#xmax").val(ext.xmax);
		$("#ymax").val(ext.ymax);
		var str="xmin:"+$("#xmin").val()+"\r\nymin:"+$("#ymin").val()+"\r\nxmax:"+$("#xmax").val()+"\r\nymax:"+$("#ymax").val()
		$("#bookPos").val(str);
	}
	
	function validate(){
		if(!$("#bookPos").val()){
			alert("书签不能为空");
			return false
		}
		if(!$("#name").val()){
			alert("书签名不能为空");
			return false
		}
		return true
	}
	$(function(){
		getExtent();
		
		var dialog = parent.art.dialog.list["bookmarkadd"];
		var dialog_div = dialog.DOM.wrap;
		dialog_div.on("ok", function() {
			if(!validate()) return;
			var option = {
					url : "${path}/locateService/add.do",
					cache : false,
					dataType : 'json',
					type:'post',
					data:$("#form_id").serialize(),
					success:function(data){
						if(data.retCode==1){
	                		$.Layer.confirm({
	        	                msg:"保存成功",
	        	                fn:function(){
	        	                	parent.$('#tableSqdw').bootstrapTable('refresh');
	        						dialog.close();
	        	                }
	        	            });
	                	}else{
	                		showAlertDialog(data.retMsg);
	                	}
					}
				};
			$("#form_id").ajaxSubmit(option);
		});

	});
</script>
</head>
<body>
<form action="${path}/locateService/add.do" method="post" id="form_id" accept-charset="UTF-8">
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="date_add_table">
  <tr>
    <td width="120" class="t_r">书签名称：</td>
    <td ><input type="text" name="name" id="name" width="163px" class="text" /></td>
  </tr>
  <tr>
    <td class="t_r">书签描述：</td>
    <td><textarea name="description" id="" cols="28" rows="3" class="text_wid_area"></textarea></td>
  </tr>
  <tr>
    <td class="t_r">书签位置：</td>
    <td>    
      <textarea cols="28" rows="5" id="bookPos" readonly="readonly"></textarea><br>
<!--       <button type="button" class="btn" >当前位置</button> -->
    </td>
  </tr>
</table>
<input type="hidden" name="xmin" id="xmin">
<input type="hidden" name="ymin" id="ymin">
<input type="hidden" name="xmax" id="xmax">
<input type="hidden" name="ymax" id="ymax">
</form>
</body>

</html>