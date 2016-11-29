<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@include file="/WEB-INF/views/common/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>羽辰智慧林业综合管理平台-资源管理</title>
<!-- Tell the browser to be responsive to screen width -->
<meta
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"
	name="viewport">
<link rel="shortcut icon" href="${res }/favicon.ico" />
<!-- Bootstrap 3.3.6 -->
<link rel="stylesheet" href="${res }/bootstrap/css/bootstrap.css">
<!-- iconfont -->
<link rel="stylesheet" href="${res }/iconfont/iconfont.css">
<!-- Theme style -->
<link rel="stylesheet" href="${res }/dist/css/AdminLTE.css">
<!-- AdminLTE Skins. Choose a skin from the css/skins
       folder instead of downloading all of them to reduce the load. -->
<link rel="stylesheet" href="${res }/dist/css/skins/_all-skins.css">
<!-- list -->
<link href="${res }/plugins/ligerUI/skins/Aqua/css/ligerui-all.css"
	rel="stylesheet" type="text/css" />
<!-- 弹出框 -->
<link href="${res }/plugins/dialog/dialog.css" rel="stylesheet"
	type="text/css">
<!-- 向导页面插件 -->
<link href="${res }/plugins/wizard-master/demo_style.css"
	rel="stylesheet" type="text/css">
<link href="${res }/plugins/wizard-master/smart_wizard.css"
	rel="stylesheet" type="text/css">
<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
  <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
  <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
  <![endif]-->
<style>
html,body {
	background-color: #ecf0f5
}

body {
	overflow-y: hidden;
}
</style>
</head>
<body>
<body class="hold-transition skin-blue sidebar-mini">
<form method="post" action="${ctx }/service/registerGis" id="form_id" enctype="multipart/form-data">
	<div id="wizard" class="swMain">
		<ul>
			<li><a href="#step-1"> <label class="stepNumber">1</label> <span
					class="stepDesc"> 第一步<br /> <small>服务引擎</small>
				</span>
			</a></li>
			<li><a href="#step-2"> <label class="stepNumber">2</label> <span
					class="stepDesc"> 第二步<br /> <small>基本信息</small>
				</span>
			</a></li>
			<li><a href="#step-3"> <label class="stepNumber">3</label> <span
					class="stepDesc"> 第三步<br /> <small>拓展信息</small>
				</span>
			</a></li>
			<li><a href="#step-4"> <label class="stepNumber">4</label> <span
					class="stepDesc"> 第四步<br /> <small>服务概要</small>
				</span>
			</a></li>
		</ul>
		<div id="step-1">
			<h2 class="StepTitle">服务引擎</h2>
			<table width="50%" border="0" cellpadding="0" cellspacing="0"
				class="date_add_table">
				<tr>
					<td class="t_r">请选择服务引擎：</td>
					<td><select type="text" name="serverEngine1" id="serverEngine" onchange="serverEngineChange(this);"
						class="text">
							<option value="">-请选择-</option>
							<c:forEach var="list" items="${serverEngineList }">
								<option value="${list.id }">${list.configName }</option>
							</c:forEach>
					</select>
				</tr>
			</table>
			<div class="list" id="maingrid4" style="margin-left:150px;"></div>
		</div>

		<div id="step-2">
			<h2 class="StepTitle">基本信息</h2>
			<table width="100%" border="0" cellpadding="0" cellspacing="0"
				class="date_add_table">
				<tr>
					<td class="t_r">服务注册名称：</td>
					<td><input type="text" name="registerName" id="registerName"
						class="text validate[required]" /></td>
				</tr>
				<tr>
					<td class="t_r">服务显示名称：</td>
					<td><input type="text" name="showName" id="showName"
						class="text validate[required]" /></td>
				</tr>
				<tr>
					<td class="t_r">服务描述：</td>
					<td><textarea name="remarks" id="remarks" clos="20" rows="5" class="text_area"></textarea></td>
				</tr>
			</table>
		</div>

		<div id="step-3">
			<h2 class="StepTitle">拓展信息</h2>
			<table width="80%" border="0" cellpadding="0" cellspacing="0"
				class="date_add_table">
				<tr>
					<td class="t_r">服务功能类型：</td>
					<td><input type="text" name="functionType" id="functionType" readonly="readonly"
						class="text validate[required]" /></td>
				</tr>
				<tr>
					<td class="t_r">拓展功能类型：</td>
					<td>
						<input type="checkbox" name="serviceExtend" value="KmlServer" readonly="readonly"/><span>Kml</span>
						<input type="checkbox" name="serviceExtend" value="FeatureServer" readonly="readonly"/><span>Feature</span> 
						<input type="checkbox" name="serviceExtend" value="NAServer" readonly="readonly"/><span>NA</span>
						<input type="checkbox" name="serviceExtend" value="WCSServer" readonly="readonly"/><span>WCS</span>
						<input type="checkbox" name="serviceExtend" value="WFSServer" readonly="readonly"/><span>WFS</span>
						<input type="checkbox" name="serviceExtend" value="WMSServer" readonly="readonly"/><span>WMS</span>
						<input type="checkbox" name="serviceExtend" value="MobileServer" readonly="readonly"/><span>Mobile</span>
						<input type="checkbox" name="serviceExtend" value="JPIPServer" readonly="readonly"/><span>JPIP</span>
					<br /></td>
				</tr>
				<tr>
					<td class="t_r">服务缓存：</td>
					<td><select type="text" name="cacheType" id="cacheType"
						class="text" >
							<option value="0">Dynamic</option>
							<option value="1">Tiled</option>
							
					</select></td>
				</tr>
				<tr>
					<td class="t_r">服务权限类型：</td>
					<td><select type="text" name="permissionStatus" id="permissionStatus"
						class="text">
							<option value="0">自由服务</option>
							<option value="1">安全服务</option>
					</select></td>
				</tr>
				<tr>
					<td class="t_r">服务访问地址：</td>
					<td><input type="text" name="serviceVisitAddress" id="serviceVisitAddress" readonly="readonly"
						class="text validate[required]" width="250px"/></td>
				</tr>
				<tr>
					<td class="t_r">服务缩略图：</td>
					<td><input type="file" id="imagePath" name="imageFile"></td>
				</tr>
				<tr>
					<td class="t_r">元数据访问地址：</td>
					<td><input type="text" name="metadataVisitAddress" id="metadataVisitAddress"
						class="text validate[required]" /></td>
				</tr>
				<tr>
					<td class="t_r">服务分类：</td>
					<td><input type="text" name="registerType" id="registerType" value="GIS服务" di
						class="text validate[required]" /></td>
				</tr>
			</table>
		</div>

		<div id="step-4">
			<h2 class="StepTitle">服务概要</h2>
			<table width="100%" border="0" cellpadding="0" cellspacing="0"
				class="date_add_table">
				<tr>
					<td class="t_r">注册服务类型：</td>
					<td>AGIS SERVER服务</td>
				</tr>
				<tr>
					<td class="t_r">服务注册名；</td>
					<td id="g_registerName"></td>
				</tr>
				<tr>
					<td class="t_r">服务显示名：</td>
					<td id="g_showName"></td>
				</tr>
				<tr>
					<td class="t_r">服务注册功能类型：</td>
					<td id="g_functionType"></td>
				</tr>
				<tr>
					<td class="t_r">服务注册地址：</td>
					<td id="g_serviceVisitAddress"></td>
				</tr>
				<tr>
					<td class="t_r">服务缓存类型：</td>
					<td id="g_cacheType"></td>
				</tr>
				<tr>
					<td class="t_r">服务描述：</td>
					<td id="g_remarks"></td>
				</tr>
				<tr>
					<td class="t_r">服元数据访问地址：</td>
					<td id="g_metadataVisitAddress"></td>
				</tr>
				<tr>
					<td class="t_r">服务拓展功能：</td>
					<td id="g_serviceExtend"></td>
				</tr>
			</table>
		</div>
	</div>
</form>
	<!-- /.content-wrapper -->

	<!-- jQuery 2.2.3 -->
	<script src="${res }/plugins/jQuery/jquery-2.2.3.min.js"></script>
	<script
		src="${res}/plugins/jquery-validation-1.15.1/lib/jquery.form.js"></script>
	<!--grid-->
	<script src="${res }/plugins/ligerUI/js/core/base.js"
		type="text/javascript"></script>
	<script src="${res }/plugins/ligerUI/js/plugins/ligerGrid.js"
		type="text/javascript"></script>
	<script src="${res }/plugins/ligerUI/js/plugins/ligerDrag.js"
		type="text/javascript"></script>
	<script src="${res }/plugins/ligerUI/js/plugins/ligerMenu.js"
		type="text/javascript"></script>
	<script src="${res }/plugins/ligerUI/js/plugins/CustomersData.js"
		type="text/javascript"></script>
	<!-- Bootstrap 3.3.6 -->
	<script src="${res }/bootstrap/js/bootstrap.min.js"></script>
	<!-- AdminLTE App -->
	<script src="${res }/dist/js/app.js"></script>
	<!-- AdminLTE for demo purposes -->
	<script src="${res }/dist/js/demo.js"></script>
	<!-- AdminLTE for demo purposes -->
	<script type="text/javascript"
		src="${res }/plugins/wizard-master/jquery.smartWizard.js"></script>
	<!-- 封装弹出框dialog -->
	<script type="text/javascript"
		src="${res }/plugins/dialog/jquery.artDialog.source.js"></script>
	<script type="text/javascript"
		src="${res }/plugins/dialog/iframeTools.source.js"></script>
	<script type="text/javascript" src="${res }/plugins/dialog/unit.js"></script>
	<script type="text/javascript">
		var serverEngineId = null;
		var folderName = null;	
		var showName = null;
		var functionType = null;
		$(document).ready(function() {
			var parentWin = window.parent;
			var dialog = parentWin.art.dialog.list["registerGisDialog"];
			
			$('#form_id').on('submit', function(e) {
	            e.preventDefault(); // <-- important
	            $(this).ajaxSubmit({
	            	dataType:"json",
	            	success:function(result){
	            		alert(result.msg);
	            		if(result.flag == "0") {
	            			dialog.close();
	            		}
	            		
	                 }
	            });
	        });
			// Smart Wizard     
			$('#wizard').smartWizard({
				onLeaveStep:onLeaveStepCallback,
				onFinish: onFinishCallback
			});
			
			//完成触发的方法
			function onFinishCallback() {
				$('#form_id').submit();
			}
			
			//上一步和下一步触发的方法
			function onLeaveStepCallback(stepObj) {
				//console.log(stepObj);
				var stepNum= stepObj.attr('rel');
				//console.log("stepNum="+stepNum);
				switch(stepNum) {
					case '1':
						var selectedRows = gridManager.getSelecteds();
						if(selectedRows.length != 1) {
							alert("请选择一条服务记录！");
							return false;
						}
						folderName = selectedRows[0].folderName;
						showName = selectedRows[0].showName;
						functionType = selectedRows[0].functionType;
						//console.log(selectedRows[0].showName);
						//console.log(selectedRows[0].folderName);
						//console.log(selectedRows[0].functionType);
						return true;
					  	break;
					case '2':
						if($("#registerName").val() == '') {
							alert("服务注册名称不能为空");
							return false;
						}
						if($("#showName").val() == '') {
							alert("服务显示名称不能为空！");
							return false;
						}
						//找服务相关的信息
						$.ajax({
		    				url:"${ctx}/service/getServiceInfo",
		    				method:"post",
		    				data:{'serverEngineId':serverEngineId,'folderName':folderName,'showName':showName,'functionType':functionType},
		    				dataType:"json",
		    				success:function(result) {
		    					//console.log(result);
		    					var exName = result.serviceExtend;
		    					//console.log("exName="+exName);
		    					$("#functionType").val(result.functionType);
		    					$("input[name = serviceExtend]").each(function() {
		    						//console.log($(this).val());
		    						if(exName.indexOf($(this).val()) > -1) {
		    							//console.log("选中");
		    							//console.log($(this).val());
		    							$(this).attr("checked",true);
		    						}
		    					});
		    					$("#serviceVisitAddress").val(result.serviceVisitAddress);
		    					$("#functionType").val(result.functionType);
		    				},
		    				error: function(result) {
		    					//console.log(result);
		    					//alert(result.msg);
		    				}
		    			});
						
						return true;
					  	break;
					case '3':
						//设置概要的信息
						$("#g_registerName").html($("#registerName").val());
						$("#g_showName").html($("#showName").val());
						$("#g_functionType").html($("#functionType").val());
						$("#g_serviceVisitAddress").html($("#serviceVisitAddress").val());
						$("#g_cacheType").html($("#cacheType").val() == 0 ? "Dynamic":"");
						$("#g_remarks").html($("#remarks").val());
						$("#g_metadataVisitAddress").html($("#metadataVisitAddress").val());
						var tempArr = "";
						$("input[name='serviceExtend']:checked").each(function() {
    							tempArr += $(this).next().text() + ","
    					});
						console.log("tempArr="+tempArr);
						$("#g_serviceExtend").html(tempArr.substring(0, tempArr.length - 1));
						return true;
						break;
					default:
						return true;
				}
				
			}
		});
	</script>
	<script type="text/javascript">
		var gridManager = null;
		function serverEngineChange(obj) {
			gridManager.setParm("serverEngineId",obj.value);
			serverEngineId = obj.value;
			//console.log("gridManager");
			//console.log(gridManager);
        	gridManager.reload();
		}
		
		;(function($) { //避免全局依赖,避免第三方破坏
			$(document).ready(function() {
				//表格列表
				$(function() {
					gridManager = $("#maingrid4").ligerGrid({
						checkbox : true,
						columns : [ {
							display : '服务名',
							name : 'showName',
							align : 'left',
							width : 100
						}, {
							display : '服务类型',
							name : 'functionType',
							minWidth : 60
						} ],
						pageSize : 10,
						url:"${ctx}/service/listData",
						width : '100%',
						height : '80%'
					});
					$("#pageloading").hide();
				});
			});
		})(jQuery);
	</script>
</body>
</html>
