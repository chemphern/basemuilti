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
	background-color: #f1f1f1;
}

body {
	overflow-y: hidden;
}
</style>
</head>
<body>
<form method="post" action="${ctx }/service/updateServiceOther" id="form_id" enctype="multipart/form-data">
	<input type="hidden" name="id" id="id" value="${service.id }">
	<body class="hold-transition skin-blue sidebar-mini">
	<div id="wizard" class="swMain">
            <ul>
                <li><a href="#step-1">
                <label class="stepNumber">1</label>
                <span class="stepDesc">
                   第一步<br />
                   <small>基本信息</small>
                </span>
            </a></li>
                <li><a href="#step-2">
                <label class="stepNumber">2</label>
                <span class="stepDesc">
                   第二步<br />
                   <small>拓展信息</small>
                </span>                   
             </a></li>
                <li><a href="#step-3">
                <label class="stepNumber">3</label>
                <span class="stepDesc">
                   第三步<br />
                   <small>服务概要</small>
                </span>                   
            </a></li>
            </ul>
        <div id="step-1">
          <h2 class="StepTitle">基本信息</h2>   
          <table width="100%" border="0" cellpadding="0" cellspacing="0"
				class="date_add_table">
            <tr>
              <td class="t_r">服务注册名称：</td>
              <td><input type="text" name="registerName" value="${service.registerName }" disabled="disabled"
						id="registerName" class="text validate[required]" /></td>
            </tr>
            <tr>
              <td class="t_r">服务显示名称：</td>
              <td><input type="text" name="showName" value="${service.showName }"
						id="showName" class="text validate[required]" 
						validate="{required:true,maxlength : 15,messages:{required:'必填',maxlength:'字符长度不能超过15个!'}}"/></td>
            </tr>
            <tr>
              <td class="t_r">服务描述：</td>
              <td><textarea name="remarks" id="remarks" clos="20" rows="5"
							class="text_area"
							validate="{maxlength : 100,messages:{maxlength:'备注 的字符长度大于100个字符！'}}">${service.remarks }</textarea>
			  </td>
            </tr>
          </table>         
        </div>

        <div id="step-2">
            <h2 class="StepTitle">拓展信息</h2>   
            <table width="80%" border="0" cellpadding="0"
				cellspacing="0" class="date_add_table">
              <%-- <tr>
                <td class="t_r">集群服务器：</td>
                <td>
                <select type="text" name="serverEngine1" id="serverEngine1"
						class="text">
                  <c:forEach var="list" items="${serverEngineList }">
					 <option value="${list.id }">${list.configName }</option>
				  </c:forEach>
                </select>
                </td>
              </tr> --%>
              
              <tr>
                <td class="t_r">服务访问地址：</td>
                <td><input type="text" name="serviceVisitAddress" value="${service.serviceVisitAddress }"
						id="serviceVisitAddress" class="text validate[required]" 
						validate="{required:true,maxlength : 100,messages:{required:'必填',maxlength:'字符长度不能超过100个!'}}"
						/></td>
              </tr>
              
              <tr>
                <td class="t_r">远程服务类型：</td>
                <td>
                <select type="text" name="remoteServicesType" id="remoteServicesType"
						class="text">
                	<c:forEach var="map" items="${remoteServicesType }">
						<option value="${map.key }">${map.value.name }</option>	
					</c:forEach>
                </select>
                </td>
              </tr>
              <tr>
                <td class="t_r">服务功能类型：</td>
                <td>
	                <select type="text" name="functionType" id="functionType"
							class="text">
	                  <c:forEach var="map" items="${serviceFunctionType }">
							<option value="${map.value.name }">${map.value.name }</option>
						</c:forEach>
	                </select>
               </td>
						
              </tr>
              <tr>
                <td class="t_r">拓展功能类型：</td>
                <td>
                	<c:forEach var="map" items="${serviceExtendType }">
                		<input type="checkbox" name="serviceExtend" value="${map.key }"/><span>${map.value.name }</span>
                	</c:forEach>
                <br /></td>
              </tr>
              <tr>
                <td class="t_r">服务缓存：</td>
                <td>
                <select type="text" name="cacheType" id="cacheType"
						class="text">
                	<c:forEach var="map" items="${serviceCacheType }">
						<option value="${map.key }">${map.value.name }</option>	
					</c:forEach>
                </select>
                </td>
              </tr>
              <tr>
                <td class="t_r">服务权限类型：</td>
                <td>
                <select type="text" name="permissionStatus" id="permissionStatus"
						class="text">
                	<c:forEach var="map" items="${permissionStatus }">
						<option value="${map.key }">${map.value.name }</option>	
					</c:forEach>
                </select>
                </td>
              </tr>
              
              <tr>
                <td class="t_r">服务浓缩图：</td>
                <td><input type="file" id="imagePath" name="imageFile"></td>
              </tr> 
              <tr>
                <td class="t_r">元数据访问地址：</td>
                <td><input type="text" name="metadataVisitAddress" id="metadataVisitAddress"
						 class="text validate[required]" value="${service.metadataVisitAddress }"
						 validate="{maxlength : 100,messages:{maxlength:'字符长度不能超过100个!'}}"/></td>
              </tr>
              <tr>
                <td class="t_r">服务分类：</td>
                <td><input type="text" name="registerType" value="第三方服务" disabled="disabled"
						id="registerType" class="text validate[required]" /></td>
              </tr>
              <tr>
					<td class="t_r">更多属性信息：</td>
					<td>
					<input type="checkbox" name="moreProperty" id="moreProperty" value="1"/>
					</td>
			</tr>
            </table>                                     
        </div>

        <div id="step-3">
            <h2 class="StepTitle">服务概要</h2>   
            <table width="100%" border="0" cellpadding="0"
				cellspacing="0" class="date_add_table">
              <tr>
                <td class="t_r">注册服务类型：</td>
                <td>第三方服务注册</td>
              </tr>
              <tr>
                <td class="t_r">服务注册名：</td>
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
                <td class="t_r">元数据访问地址：</td>
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
	<script type="text/javascript">
		$(document).ready(function() {
			//设置下拉的值
			if("${service.remoteServicesType}") {
				$("#remoteServicesType option[value=${service.remoteServicesType}]").attr("selected",true);
			}
			
			if("${service.functionType}") {
				$("#functionType option[value=${service.functionType}]").attr("selected",true);
			}
			
			if("${service.cacheType}") {
				$("#cacheType option[value=${service.cacheType}]").attr("selected",true);
			}
			
			if("${service.permissionStatus}") {
				$("#permissionStatus option[value=${service.permissionStatus}]").attr("selected",true);
			}
			
			if("${service.moreProperty}") {
				$("#moreProperty").attr("checked",true);
			}
			
			var exNameStr ="${service.serviceExtend}";
			$("input[name = serviceExtend]").each(function() {
				if(exNameStr.indexOf($(this).val()) > -1) {
					$(this).attr("checked",true);
				}
			});
			var parentWin = window.parent[0];
			var dialog = parentWin.art.dialog.list["editOtherServiceDialog"];
			
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
	            		if(result.flag == "0") {
	            			parentWin.gridManager.reload();
	            			dialog.close();
	            		}
	            		else {
	            			$('.actionBar a.buttonFinish').removeClass("buttonDisabled");//完成按钮可用
	            			$('.actionBar a.buttonPrevious').removeClass("buttonDisabled");//上一步按钮可用  
	            		}
	                }
	             });
		    };
		    form.validate(val_obj);
			
			/* $('#form_id').on('submit', function(e) {
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
	        }); */
			
			// Smart Wizard     
			$('#wizard').smartWizard({
				onLeaveStep:onLeaveStepCallback,
				onFinish: onFinishCallback
			});

			function onFinishCallback() {
				$('#form_id').submit();
				//$('#wizard').smartWizard('showMessage', 'Finish Clicked');
				//alert('Finish Clicked');
			}
			
			//上一步和下一步触发的方法
			function onLeaveStepCallback(stepObj) {
				//console.log(stepObj);
				var stepNum= stepObj.attr('rel');
				//console.log("stepNum="+stepNum);
				switch(stepNum) {
				case '1':
					if($("#registerName").val() == '') {
						alert("服务注册名称不能为空");
						return false;
					}
					if($("#showName").val() == '') {
						alert("服务显示名称不能为空！");
						return false;
					}
					return true;
				  	break;
				case '2':
					//设置概要的信息
					$("#g_registerName").html($("#registerName").val());
					$("#g_showName").html($("#showName").val());
					$("#g_functionType").html($("#functionType option:selected").text());
					$("#g_serviceVisitAddress").html($("#serviceVisitAddress").val());
					$("#g_cacheType").html($("#cacheType option:selected").text());
					$("#g_remarks").html($("#remarks").val());
					$("#g_metadataVisitAddress").html($("#metadataVisitAddress").val());
					var tempArr = "";
					$("input[name='serviceExtend']:checked").each(function() {
							tempArr += $(this).next().text() + ","
					});
					//console.log("tempArr="+tempArr);
					$("#g_serviceExtend").html(tempArr.substring(0, tempArr.length - 1));
					return true;
				  	break;
				default:
					return true;
			}
			
		}
			
		});
	</script>
</body>


</html>
