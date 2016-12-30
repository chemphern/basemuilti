<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@include file="/WEB-INF/views/common/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
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
	background-color: #f1f1f1;
}

body {
	overflow-y: hidden;
}



</style>
</head>
<body>
	<!-- Content Header (Page header) -->
	<section class="content-header">
		<h1>服务发布</h1>
	</section>
	<form method="post" id="form_id" action="${ctx }/service/publish" enctype="multipart/form-data">
	<!-- Main content -->
		<div class="row">
			<div class="col-md-12">
				<div id="wizard" class="swMain">
					<ul>
						<li><a href="#step-1"> <label class="stepNumber">1</label>
								<span class="stepDesc"> 第一步<br /> <small>服务引擎</small>
							</span>
						</a></li>
						<li><a href="#step-2"> <label class="stepNumber">2</label>
								<span class="stepDesc"> 第二步<br /> <small>基本信息</small>
							</span>
						</a></li>
						<li><a href="#step-3"> <label class="stepNumber">3</label>
								<span class="stepDesc"> 第三步<br /> <small>拓展信息</small>
							</span>
						</a></li>
						<li><a href="#step-4"> <label class="stepNumber">4</label>
								<span class="stepDesc"> 第四步<br /> <small>服务概要</small>
							</span>
						</a></li>
					</ul>
					
					<div id="step-1">
						<h2 class="StepTitle">服务引擎</h2>
						<table width="80%" border="0" cellpadding="0" cellspacing="0"
							class="date_add_table">
							<tr>
								<td class="t_r">请选择服务引擎：</td>
								<td><select type="text" name="serverEngine" id="serverEngine"
									class="text">
										<c:forEach var="list" items="${serverEngineList }">
											<option value="${list.id }">${list.configName }</option>
										</c:forEach>
								</select>
									<button id="checkConnectBtn" class="btn">验证链接</button></td>
							</tr>
						</table>
						<!-- <div class="list02" id="maingrid4"></div> -->

						<div class="table-responsive">
						  <table class="table table-bordered">
						    <tr>
								<th class="th">节点名称</th> 
								<th class="th">节点主机</th> 
								<th class="th">状态</th> 
								<th class="th">验证信息</th>
							</tr>
							<tr>
								<td class="td" id="configName">1</td> 
								<td class="td" id="intranetIp" >2</td> 
								<td class="td" id="runningStatus">3</td> 
								<td class="td"id="checkConnectMsg">未验证</td>
							</tr>
						  </table>
						</div>
						
					</div>

					<div id="step-2">
						<h2 class="StepTitle">基本信息</h2>
						<table width="100%" border="0" cellpadding="0" cellspacing="0"
							class="date_add_table">
							<%-- <tr>
								<td class="t_r">服务资源：</td>
								<td>
									<c:forEach var="map" items="${serviceResource }">
										${map.value.name }<input type="radio" name="serviceResource" value="${map.key }">
									</c:forEach>
									</td>
							</tr> --%>
							<tr>
								<td class="t_r">服务类型：</td>
								<td><select type="text" name="serviceType" id="serviceType"
									class="text">
										<!-- GeometryServer | ImageServer | MapServer | GeocodeServer | GeoDataServer | GPServer | GlobeServer | SearchServer -->
										<c:forEach var="map" items="${serviceFunctionType }">
											<option value="${map.value.name }">${map.value.name }</option>
										</c:forEach>
								</select></td>
							</tr>
							<tr>
								<td class="t_r">资源文件：</td>
								<td>
									<input type="text" disabled="disabled" id="resourceFile" name="resourceFile" 
										validate="{required:true,messages:{required:'必填'}}">
									<input type="hidden" id="resourceFileId" name="resourceFileId">
									<input type="button" value="浏览" id="viewResourceFile">
									<span style="color: red">*</span>
								</td>
							</tr>
							<tr>
								<td class="t_r">服务名称：</td>
								<td><input type="text" name="serviceName" id="serviceName" class="text validate[required]" 
									validate="{required:true,maxlength : 15,messages:{required:'必填',maxlength:'字符长度不能超过15个!'}}"/>
									<span style="color: red">*</span>
									</td>
							</tr>
							<tr>
								<td class="t_r">GIS Services集群：</td>
								<td><select type="text" name="clusterName" id="clusterName"
									class="text">
										<%-- <c:forEach var="list" items="${clusterNames }">
											<option value="${list }">${list }</option>
										</c:forEach> --%>
								</select></td>
							</tr>
							<tr>
								<td class="t_r">发布目录：</td>
								<td><select type="text" name="folderName" id="folderName"
									class="text">
										<option value="/">根目录</option>
										<%-- <c:forEach var="list" items="${listFolder }">
											<option value="${list }">${list }</option>
										</c:forEach> --%>
								</select></td>
							</tr>
						</table>
					</div>

					<div id="step-3">
						<h2 class="StepTitle">拓展信息</h2>
						<table width="80%" border="0" cellpadding="0" cellspacing="0"
							class="date_add_table">
							<tr>
								<td class="t_r">请选择服务拓展模块：</td>
								<td>
									<c:forEach var="map" items="${serviceExtendType }">
				                		<input type="checkbox" name="extensionName" value="${map.key }"/><span>${map.value.name }</span>
				                	</c:forEach>
									<br /></td>
							</tr>
						</table>
					</div>

					<div id="step-4">
						<h2 class="StepTitle">服务概要</h2>
						<table width="100%" border="0" cellpadding="0" cellspacing="0"
							class="date_add_table">
							<tr>
								<td class="t_r">服务名称：</td>
								<td><input type="text" name="g_serviceName" disabled="disabled" id="g_serviceName"
									class="text validate[required]" /></td>
							</tr>
							<tr>
								<td class="t_r">服务使用资源：</td>
								<td><input type="text" name="g_resourceFile" disabled="disabled" id="g_resourceFile"
									class="text validate[required]" /></td>
							</tr>
							<tr>
								<td class="t_r">GIS Services集群；</td>
								<td><input type="text" name="g_clusterName" disabled="disabled" id="g_clusterName"
									class="text validate[required]" /></td>
							</tr>
							<tr>
								<td class="t_r">服务类型：</td>
								<td><input type="text" name="g_serviceType" disabled="disabled" id="g_serviceType"
									class="text validate[required]" /></td>
							</tr>
							<tr>
								<td class="t_r">发布目录：</td>
								<td><input type="text" name="g_folderName" disabled="disabled" id="g_folderName"
									class="text validate[required]" /></td>
							</tr>
							<tr>
								<td class="t_r">拓展：</td>
								<td><input type="text" name="g_extensionName" disabled="disabled" id="g_extensionName"
									class="text validate[required]" /></td>
							</tr>
						</table>
					</div>
				</div>
			</div>
			<!-- /.col -->
		</div>
	</form>

<!-- /.content-wrapper -->

<!-- jQuery 2.2.3 -->
<script src="${res}/js/common/form.js"></script>
<script src="${res}/plugins/jQuery/jquery-2.2.3.min.js"></script>
<script src="${res}/plugins/jquery-validation-1.15.1/lib/jquery.form.js"></script>
<script src="${res}/plugins/jquery-validation-1.15.1/dist/jquery.validate.min.js" type="text/javascript"></script>
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
	var nextFlag = ""; //验证服务引擎是否可以下一步的标记 0：不可以；1：可以
	$(document).ready(function() {
		/* $('#form_id').on('submit', function(e) {
            e.preventDefault(); // <-- important
            $(this).ajaxSubmit({
            	success:function(data){
            		alert("发布完成！");
                 }
            });
        }); */
        
     	//设置单选择第一个值
		//$("input[name='serviceResource']:radio").eq(0).attr('checked','true');
        
        //得到第一个引擎的信息
		$.ajax({
			url:"${ctx }/service/getServerEngineInfo",
			method:"post",
			data:{"id":$("#serverEngine").val()},
			dataType:"json",
			success:function(result) {
				$("#configName").html(result.configName);
				$("#intranetIp").html(result.intranetIp);
				$("#runningStatus").html(result.runningStatus);
			},
			error: function(result) {
				alert("连接错误！");
			}
		});
        
		var form = $("#form_id");
		var val_obj = exec_validate(form);//方法在 ${res}/js/common/form.js
		val_obj.submitHandler = function(){
			$('.actionBar a.buttonFinish').addClass("buttonDisabled");//完成按钮变灰
			$('.actionBar a.buttonPrevious').addClass("buttonDisabled");//上一步按钮变灰 
			//支持文件上传的ajax提交方式
			form.ajaxSubmit({
				dataType:"json",
                success:function(result){
                	alert(result.msg);
            		if(result.flag == "1") {
            			//form.reset();
            			//$('#wizard').smartWizard('skipTo',1);  
            			//dialog.close();
            		}
            		else {
            			$('.actionBar a.buttonFinish').removeClass("buttonDisabled");//完成按钮可用
            			$('.actionBar a.buttonPrevious').removeClass("buttonDisabled");//上一步按钮可用  
            		}
                }
             });
	    };
	    form.validate(val_obj);
		// Smart Wizard     
		$('#wizard').smartWizard({ 
			onLeaveStep:onLeaveStepCallback,
			onFinish:onFinishCallback
		});
		//上一步和下一步触发的方法
		function onLeaveStepCallback(stepObj) {
			//console.log(stepObj);
			var stepNum= stepObj.attr('rel');
			//console.log("stepNum="+stepNum);
			switch(stepNum) {
				case '1':
					if(nextFlag != '1') {
						alert("验证服务器引擎连接失败，请重试或选择其它服务器引擎！");
						return false;
					}
					//验证成功则找服务器引擎的相关信息（集群和服务发布的目录）
					$.ajax({
	    				url:"${ctx}/service/getClusterAndFolder",
	    				method:"post",
	    				data:{'id':$("#serverEngine").val()},
	    				dataType:"json",
	    				success:function(ret) {
	    					$("#clusterName").append(ret.clusterList);
	    					$("#folderName").append(ret.folderList);
	    				},
	    				error: function(ret) {
	    					
	    				}
	    			});
					
					return true;
				  	break;
				case '2':
					if($("#resourceFile").val() == '') {
						//alert("请选择资源！");
						return false;
					}
					if($("#serviceName").val() == '') {
						//alert("服务名不能为空");
						return false;
					}
					
					return true;
				  	break;
				case '3':
					var extensionNames = $("input[name='extensionName']:checked");
					/* if(extensionNames.length==0) {
						alert("请选择至少一个服务扩展类型！");
						return false;
					} */
					$("#g_serviceName").val($("#serviceName").val());
					$("#g_resourceFile").val($("#resourceFile").val());
					$("#g_clusterName").val($("#clusterName").val());
					$("#g_serviceType").val($("#serviceType").val());
					//$("#g_folderName").val($("#folderName").find("option:selected").text());
					$("#g_folderName").val($("#folderName option:selected").text());
					
					var temp = [];
					extensionNames.each(function() {
						temp.push($(this).val());
					});
					$("#g_extensionName").val(temp);
					
					return true;
					break;
				default:
					return true;
			}
			
		}
		
		//验证连接
		$("#checkConnectBtn").on("click",function(e) {
			e.preventDefault();
			//得到下拉的值
			var serverEngineId = $("#serverEngine").val();
			//console.log(serverEngineId);
			$.ajax({
				url:"${ctx }/service/toCheckServer",
				method:"post",
				data:{"id":serverEngineId},
				dataType:"json",
				success:function(result) {
					$("#configName").html(result.configName);
					$("#intranetIp").html(result.intranetIp);
					$("#runningStatus").html(result.runningStatus);
					$("#checkConnectMsg").html(result.msg);
					nextFlag = result.nextFlag;
					//console.log("nextFlag="+nextFlag);
				},
				error: function(result) {
					alert("连接错误！");
				}
			});
			
		});
		$("#viewResourceFile").on("click",function(e) {
			e.preventDefault();
			var dialog = $.Layer.iframe(
	                { 
	                  id:'viewResourceFileDialog',
	                  title: '选择资源',
	                  url:'${ctx}/service/viewResource',
	                  width: 400,
	                  height: 500
	               });
			
		});
		//完成触发的方法
		function onFinishCallback() {
			/* $('.actionBar a.buttonFinish').addClass("buttonDisabled");//完成按钮变灰
			$("#form_id").ajaxSubmit({
				url : "${ctx }/service/publish",
				dataType:"json",
                success:function(ret){
                	alert(ret.msg);
                	//成功
                	if(ret.flag!="1") {
                		$('.actionBar a.buttonFinish').removeClass("buttonDisabled");//完成按钮可用  
                	}
                }
             }); */
             var counts = $('div.l-exclamation'); //填的不对的记录数
				if(counts.length < 1) {
					$('#form_id').submit();
				}
				else {
					$("#wizard").smartWizard("showMessage","请重新填写有误的信息！");
					return false;
				}
		}
		
	});
</script>

</body>
</html>
