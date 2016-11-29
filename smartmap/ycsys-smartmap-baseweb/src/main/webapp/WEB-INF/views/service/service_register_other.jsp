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
              <td><input type="text" name="registerName"
						id="registerName" class="text validate[required]" /></td>
            </tr>
            <tr>
              <td class="t_r">服务显示名称：</td>
              <td><input type="text" name="showName"
						id="showName" class="text validate[required]" /></td>
            </tr>
            <tr>
              <td class="t_r">服务描述：</td>
              <td><textarea name="remarks" id="remarks" clos="20" rows="5"
							class="text_area"></textarea></td>
            </tr>
          </table>         
        </div>

        <div id="step-2">
            <h2 class="StepTitle">拓展信息</h2>   
            <table width="80%" border="0" cellpadding="0"
				cellspacing="0" class="date_add_table">
              <tr>
                <td class="t_r">集群服务器：</td>
                <td>
                <select type="text" name="serverEngine1" id="serverEngine1"
						class="text">
                  <c:forEach var="list" items="${serverEngineList }">
					 <option value="${list.id }">${list.configName }</option>
				  </c:forEach>
                </select>
                </td>
              </tr>
              <tr>
                <td class="t_r">远程服务类型：</td>
                <td>
                <select type="text" name="textfield3" id="textfield3"
						class="text">
                  <c:forEach var="map" items="${remoteServicesType }">
						<option value="${map.key }">${map.value.name }</option>	
					</c:forEach>
                </select>
                </td>
              </tr>
              <tr>
                <td class="t_r">服务功能类型：</td>
                <td><input type="text" name="textfield2"
						id="textfield2" class="text validate[required]" /></td>
              </tr>
              <tr>
                <td class="t_r">拓展功能类型：</td>
                <td><input type="checkbox" name="category"
						value="信息" />SampleWordcities <input type="checkbox"
						name="category" value="信息" />WFS <input type="checkbox"
						name="category" value="信息" />WPS <input type="checkbox"
						name="category" value="信息" />Webmap <input type="checkbox"
						name="category" value="信息" />DZDTGF<br /></td>
              </tr>
              <tr>
                <td class="t_r">服务缓存：</td>
                <td>
                <select type="text" name="textfield3" id="textfield3"
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
                <select type="text" name="textfield3" id="textfield3"
						class="text">
                	<c:forEach var="map" items="${serviceCacheType }">
						<option value="${map.key }">${map.value.name }</option>	
					</c:forEach>
                </select>
                </td>
              </tr>
              <tr>
                <td class="t_r">服务访问地址：</td>
                <td><input type="text" name="textfield2"
						id="textfield2" class="text validate[required]" /></td>
              </tr>
              <tr>
                <td class="t_r">服务浓缩图：</td>
                <td><input type="file" id="exampleInputFile"></td>
              </tr> 
              <tr>
                <td class="t_r">元数据访问地址：</td>
                <td><input type="text" name="textfield2"
						id="textfield2" class="text validate[required]" /></td>
              </tr>
              <tr>
                <td class="t_r">服务分类：</td>
                <td><input type="text" name="textfield2"
						id="textfield2" class="text validate[required]" /></td>
              </tr>
            </table>                                     
        </div>

        <div id="step-3">
            <h2 class="StepTitle">服务概要</h2>   
            <table width="100%" border="0" cellpadding="0"
				cellspacing="0" class="date_add_table">
              <tr>
                <td class="t_r">注册服务类型：</td>
                <td>AGIS SERVER服务</td>
              </tr>
              <tr>
                <td class="t_r">服务注册名；</td>
                <td>WEBMAP</td>
              </tr>
              <tr>
                <td class="t_r">服务显示名：</td>
                <td>WEBMAP</td>
              </tr>
              <tr>
                <td class="t_r">服务注册功能类型：</td>
                <td>MAP SERVER</td>
              </tr>
              <tr>
                <td class="t_r">服务注册地址：</td>
                <td>HTTPS://173.15.552.56</td>
              </tr>
              <tr>
                <td class="t_r">服务缓存类型：</td>
                <td>dynake</td>
              </tr>
              <tr>
                <td class="t_r">服务描述：</td>
                <td></td>
              </tr>
              <tr>
                <td class="t_r">服元数据访问地址：</td>
                <td></td>
              </tr>
              <tr>
                <td class="t_r">服务拓展功能：</td>
                <td>mapping</td>
              </tr>
            </table>                         
        </div>
    </div>

  <!-- /.content-wrapper --><!-- jQuery 2.2.3 -->
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
	<script type="text/javascript">
		$(document).ready(function() {
			// Smart Wizard     
			$('#wizard').smartWizard();

			function onFinishCallback() {
				$('#wizard').smartWizard('showMessage', 'Finish Clicked');
				//alert('Finish Clicked');
			}
		});
	</script>
</body>


</html>
