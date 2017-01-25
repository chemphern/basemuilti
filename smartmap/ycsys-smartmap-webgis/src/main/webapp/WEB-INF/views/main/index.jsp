<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/taglib.jsp" %>
<%@include file="/WEB-INF/views/common/mapCommon.jsp" %>
<%@include file="/WEB-INF/views/common/map3dCommon.jsp" %>

<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>羽辰智慧林业二三维一体化应用系统</title>
  <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
  <link rel="stylesheet" href="${res}/dist/css/mapPublic.css">
  <link rel="stylesheet" href="${res}/dist/css/mapIndex.css"> 
  <!-- Theme style -->
  <link rel="stylesheet" href="${res}/dist/css/mapAdminLTE.css">
  <!-- 换肤 -->
  <link rel="stylesheet" href="${res}/dist/css/mapSkins/_all-skins.css">
  <link href="${res}/plugins/mCustomScrollbar/jquery.mCustomScrollbar.css" rel="stylesheet" type="text/css">
    <!-- jQuery Easyui css-->
  <link rel="stylesheet" href="${res}/plugins/jeasyui/themes/default/easyui.css">
  <link rel="stylesheet"  href="${res}/plugins/jeasyui/themes/icon.css">
  <!-- 表格 -->
  <link href="${res}/plugins/table/bootstrap-table.min.css" rel="stylesheet">
  <!-- 弹出框 -->
  <link href="${res}/plugins/dialog/dialog.css" rel="stylesheet" type="text/css">

  <link href="${res}/dist/css/mapNotice.css">
  <%--   <script src="${res}/plugins/jQuery/jquery-2.2.3.min.js"></script> --%>
   <%--  <script src="${res}/plugins/jQueryUI/jquery-ui.min.js"></script>  --%>
  <script>
    window.ctx = '${ctx}';
    window.res = '${res}';
    window.userId = '${YCSYS_SESSION_USER.id}';
  </script>
<script src="${res}/plugins/mCustomScrollbar/jquery.mousewheel.js"></script>
<!-- jQuery 修改浏览器默认滚动条 -->
<script src="${res}/plugins/mCustomScrollbar/jquery.mCustomScrollbar.concat.min.js"></script>
<link rel="stylesheet" href="${res}/bootstrap/css/bootstrap.css">
<script src="${res}/bootstrap/js/bootstrap.min.js"></script>  
   <!-- HTML5 IE8 support of HTML5 elements and media queries -->
  <!--[if lt IE 9]>
  <script src="${res}dist/js/html5shiv.min.js"></script>
   <script src="${res}dist/js/respond.min.js"></script>
  <![endif]-->
    <!-- jQuery Easyui js-->
  <script src="${res}/plugins/jeasyui/jquery.easyui.min.js"></script>
  <!-- index js  -->
  <script src="${res}/dist/js/map/map_index.js"></script>
  <script src="${res}/dist/js/map/map_index_dialog.js"></script>
  <!--jQuery pagination js  分页 -->
  <script src="${res}/dist/js/map/jquery.pagination.js"></script>

  <!-- jQuery 地图鱼骨控件滑动效果 -->
  <script  src="${res}/dist/js/map/scrollBar.js"></script>

   <!--ztree-->
  <link rel="stylesheet" href="${res}/plugins/ztree/style/zTreeStyle.css" >
  <script  src="${res}/plugins/ztree/js/jquery.ztree.core.js"></script>
  <script  src="${res}/plugins/ztree/js/jquery.ztree.excheck.js"></script>

  <!-- 二三维地图工具栏基础功能 -->
  <script  src="${res}/js/common/common.js"></script>
  <script  src="${res}/js/common/locate.js"></script>
  <script  src="${res}/js/common/query.js"></script>
  <script  src="${res}/js/common/plot.js"></script>
  <script  src="${res}/js/common/layerManager.js"></script>
  <script  src="${res}/js/common/themeManager.js"></script>
  <script  src="${res}/js/common/quickLocation.js"></script>
  <script  src="${res}/js/common/analysis.js"></script>

  <!-- 三维地图飞行漫游功能模块 -->
  <script  src="${res}/js/common/flightRoaming.js"></script>
  <!-- 表格 -->
<script src="${res}/plugins/table/bootstrap-table.min.js"></script>
<script src="${res}/plugins/table/bootstrap-table-zh-CN.min.js"></script>

<!-- 封装弹出框dialog -->
<script src="${res}/plugins/dialog/jquery.artDialog.source.js"></script>
<script src="${res}/plugins/dialog/iframeTools.source.js"></script>
<script src="${res}/plugins/dialog/unit.js"></script>
  <!--右上角个人信息及消息模块-->
  <script src="${res}/js/module/rightTop.js"></script>
<%-- <script src="${res}/bootstrap/js/bootstrap-switch.min.js"></script> --%>
<link href="${res}/bootstrap/css/bootstrap-switch.min.css">
</head>
<body role="document" cz-shortcut-listen="true" class="hold-transition  sidebar-mini skin-cyan">
<!-- wrapper start -->
<div class="wrapper" > 
  <!-- header start -->
  <nav class="header navbar navbar-static-top" role="navigation" id="header">
        <div class="navbar-inner clearfix">
          <a class="navbar-brand logo" href="#"><img src="${res}/dist/img/map/logo.png" alt="logo" /></a>
        
          <a href="javascript:;" class="menu-toggler collapsed" ><i class="icon iconfont">&#xe65a;</i></a>  
          <!-- user -->
          <div id="navbar" class="float_r">
            <ul class="navbar-user">
              <li class="user dropdown">             
                <a href="#" class="user_box dropdown-toggle" data-toggle="dropdown" id="dLabel"><img src="${res}/dist/img/map/photo1.png" alt="头像" /><span class="user_name">${YCSYS_SESSION_USER.name}</span> <span class="caret"></span></a>
                <ul class="dropdown-menu memu-list"  role="menu" aria-labelledby="dLabel">
                  <!-- 下面一行为添加的标签 -->
                  <iframe frameborder= "0" scrolling="no" style="background-color:transparent; position: absolute; z-index: -1; width: 100%; height: 100%; top: 0; left:0;"></iframe>               
                  <li><a href="javascript:;" id="per_msg"><i class="icon iconfont">&#xe60e;</i><span>个人信息</span></a></li>
<!--                   <li><a href="#"><i class="icon iconfont">&#xe614;</i><span>修改资料</span></a></li> -->
                  <li><a href="javascript:;" id="per_pwd"><i class="icon iconfont">&#xe615;</i><span>修改密码</span></a></li>
                  <li><a href="javascript:;" id="per_exit"><i class="icon iconfont">&#xe650;</i><span>退出系统</span></a></li>
                </ul>                         
              </li>
              <li class="user_info dropdown" >
                <a href="javascript:;" title="消息" class="icon-msg dropdown-toggle"  data-toggle="dropdown">
                <i class="icon iconfont">&#xe651;</i><span class="icon-msg-count" id="notice_count">0</span></a>
                <!-- messages start-->
                <ul class="dropdown-menu msg-box">
                  <!-- 下面一行为添加的标签 -->
                  <iframe frameborder= "0" scrolling="no" style="background-color:transparent; position: absolute; z-index: -1; width: 100%; height: 100%; top: 0; left:0;"></iframe>               
                  <li class="msg-box-hd" id="notice_header"></li>
                  <li>
                    <ul class="msg-box-bd" id="notice_menu">
                    </ul>
                  </li>                
                </ul>
                <!-- messages end-->
              </li>
              <li class="user_info pr10 "><a href="javascript:;" title="换肤"  id="control-sidebar-skin"><i class="icon iconfont">&#xe64c;</i></a></li> 
            </ul>
          </div>
          <!-- bigmenu -->
          <div id="navbar-collapse" class="navbar-collapse" >
            <ul class="nav navbar-nav bigmenu" id="idTabs">
              <li class="active"><a href="javaScript:;"><i class="icon iconfont">&#xe893;</i><h1>地图查询</h1></a></li>
              <li><a href="javaScript:;"><i class="icon iconfont">&#xe600;</i><h1>地图定位</h1></a></li>
              <li><a href="javaScript:;"><i class="icon iconfont">&#xe601;</i><h1>地图标注</h1></a></li>
              <li><a href="javaScript:;"><i class="icon iconfont">&#xe60a;</i><h1>专题地图</h1></a></li>
              <li><a href="javaScript:;"><i class="icon iconfont">&#xe622;</i><h1>地图编辑</h1></a></li>
              <li><a href="javaScript:;"><i class="icon iconfont">&#xe6e0;</i><h1>图层管理</h1></a></li>
              <li><a href="javaScript:;"><i class="icon iconfont">&#xe8b4;</i><h1>空间分析</h1></a></li>
              <li onclick="toggleTo3d()" id="3dmy"><a href="javaScript:;" ><i class="icon iconfont">&#xe7f6;</i><h1>三维漫游</h1></a></li>
            </ul>
          </div>
          
        </div>
  </nav>
  <!-- header end -->
  <!-- main start -->
  <div class="container theme-showcase" role="main" >
  <div class="main">
    <!-- left start -->
    <div class="leftBox">
      <!-- fore-core-side  start-->  
      <div class="fore-core-side">
        <div id="fore-2d3d-menu-cx" class="current">
          <!-- 当前位置 -->
          <ol class="breadcrumb">
            <li><i class="icon iconfont home">&#xe640;</i></li>
            <li><a href="#">首页</a></li>
            <li><a href="#">-地图查询</a></li>
          </ol>
          <!-- 二级菜单 -->
          <div class="panelBox">
            <div class="panelBox-heading" d><i class="icon iconfont icon_map">&#xe893;</i><h3 class="panelBox-title">地图查询</h3><span class="arrow iconfont"></span></div>
            <div  class="panelBox-body">
              <div class="row submenu">
                <a href="#fore-2d3d-side-cx-sxcx" class="col-sm-4 active" data-toggle="tab">
                  <i class="icon iconfont mapSearch">&#xe6c8;</i>
                  <h2>属性查询</h2>
                </a>
                <a href="#fore-2d3d-side-cx-kjcx" class="col-sm-4" data-toggle="tab">
                  <i class="icon iconfont mapSearch">&#xe845;</i>
                  <h2>空间查询</h2>
                </a>
                <a href="#fore-2d3d-side-cx-ljcx" class="col-sm-4" data-toggle="tab">
                  <i class="icon iconfont mapSearch">&#xe666;</i>
                  <h2>逻辑查询</h2>
                </a>
              </div>
            </div>
          </div>
          <!-- 当前操作 -->
          <div class="subTabs">
            <div class="panelBox active" id="fore-2d3d-side-cx-sxcx">
              <div class="panelBox-heading">
                <h3 class="panelBox-title">属性查询</h3><span class="arrow arrowUp"></span>
              </div>
              <div class="panelBox-body">
                <div id="Sxcxbox">
                  <div class="form-horizontal search-form" role="form">
                    <div class="form-group form-group-sm">
                      <label for="name" class="col-sm-4">查询图层：</label>
                      <div class="col-sm-8 select-item">
  <%--                       <select class="form-control input-sm easyui-combotree " data-options="url:'${res }/dist/js/map/data/mapLayerData.json',method:'get'" name="mapLayer"></select> --%>
                        <select class="form-control input-sm" id="queryLyrLst" onchange="listFields();"></select>
                      </div>
                    </div>
                    <div class="form-group form-group-sm">                    
                      <label for="name" class="col-sm-4">查询属性：</label>
                      <div class="col-sm-8 select-item">
                        <select class="form-control input-sm" id="queryFieldsLst"></select>
                      </div>
                    </div>
                    <div class="form-group">
                      <label for="name" class="col-sm-4">查询值：</label>
                      <div class="col-sm-8">
                        <input type="text" class="form-control input-sm" placeholder="查询值" id="queryValue">
                      </div>
                    </div>
                    <div class="searchBtn">
                      <button type="button" class="btn btn-success btn_add" id="btnQueryAttr">查 询</button>
<!--                       <button type="button" class="btn btn-success btn_add" onclick="queryAttr();">查 询</button> -->
                      <button type="reset" class="btn btn-warning">重 置</button>
                    </div>
                  </div>
                </div>
                <!-- 查询结果 start-->
                <div id="Sxcxbox-result" style="display:none">                  
                  <form action="" method="get" class="form-inline search-form" role="form">                   
                    <div class="Sxcxlist">
                      <p>共有<span class="red" id='queryNum'></span>条结果<a href="#" class="btn_back" id='btnBack'><i class="arrow-back"></i>返回</a></p>
                      <div id="Searchresult">
                        <ul class="result" id="queryItem">
                        </ul>
                      </div>
                      <div id="Pagination" class="pagination"><span class="current prev"></span><span class="current">1</span><a href="#">2</a><span>...</span><a href="#">4</a><a href="#" class="next"></a></div>                        
                    </div>

                  </form>
                </div>
              </div>

            </div>
            <div class="panelBox" id="fore-2d3d-side-cx-kjcx" >
              <div class="panelBox-heading" >
                <h3 class="panelBox-title">空间查询</h3><span class="arrow arrowUp"></span>
              </div>
              <div class="panelBox-body">
                <div class="search-form" role="form" id="spatialBody">
                  <div class="form-group">
                    <label for="name">请选择查询方式</label>
                    <div class="icon-searchway">
                      <a href="javascript:;" class="col-sm-4 active" id="btnQueryPoint">
                        <i class="icon iconfont">&#xe64d;</i>
                        <h5>点击选择点</h5>
                      </a>
                      <a href="javascript:;" class="col-sm-4 " id="btnQueryPolyline">
                        <i class="icon iconfont">&#xe64f;</i>
                        <h5>点击选择线</h5>
                      </a>
                      <a href="javascript:;" class="col-sm-4 " id="btnQuerypolygon">
                        <i class="icon iconfont">&#xe638;</i>
                        <h5>点击选择多边形</h5>
                      </a>
                    </div>
                  </div>
                  <div class="form-group">
                    <label for="name">操作提示</label>
                    <p class="help-block">1.鼠标单击选择一种查询方式</p>
                    <p class="help-block">2.在地图上点击选择需要查询区域</p>
                  </div>
                </div>
                 <div id="Sxcxbox-geometry" style="display:none">                  
                  <div class="Sxcxlist">
                      <p>共有<span class="red" id='queryGeoNum'></span>条结果<a href="#" class="btn_back" id='btnGeoBack'>返回<i class="arrow-back"></i></a></p>
                      <div>
                        <ul class="result" id="queryItemGeo">
                        </ul>
                      </div>
                      <div id="PaginationGeo" class="pagination"><span class="current prev"></span><span class="current">1</span><a href="#">2</a><span>...</span><a href="#">4</a><a href="#" class="next"></a></div>                        
                    </div>
                </div>
              </div>
            </div>
            <div class="panelBox" id="fore-2d3d-side-cx-ljcx">
              <div class="panelBox-heading" >
                <h3 class="panelBox-title">逻辑查询</h3><span class="arrow arrowUp"></span>
              </div>
              <div  class="panelBox-body">
                <div class="form-horizontal search-form" role="form">
                  <div id="logicBody">
                  	<div class="form-group form-group-sm">
                    <label for="name" class="col-sm-4">查询图层：</label>
                    <div class="col-sm-8 select-item">
                      <select class="form-control input-sm" id="queryLyrLogic" onchange="listFieldsLogic();"></select>
                    </div>
                  </div>
                  <div class="form-group form-group-sm">                    
                    <label for="name" class="col-sm-4">查询字段：</label>
                    <div class="col-sm-8 select-item">
                      <select class="form-control input-sm" id="queryFieldsLogic">
                      </select>
                    </div>
                  </div>
                  <div class="form-group form-group-sm">
                    <label for="name" class="col-sm-4">操作符：</label>
                    <div class="col-sm-8 select-item">
<!--                       <input type="text" class="form-control input-sm" > -->
                      <select class="form-control input-sm" id="queryOptLogic">
                      	<option value="large" label="大于">
                      	<option value="largeAnd" label="大于等于">
                      	<option value="equal" label="等于">
                      	<option value="little" label="小于">
                      	<option value="littleAnd" label="小于等于">
                      	<option value="notEqual" label="不等">
                      	<option value="contain" label="包含">
                      	<option value="null" label="为空">
                      </select>
                    </div>
                  </div>                  
                  <div class="form-group">
                    <label for="name" class="col-sm-4">查询值：</label>
                    <div class="col-sm-8">
                      <input type="text" class="form-control input-sm" id="queryLogicVal">
                    </div>
                  </div>
                  <div class="searchBtn btn_list">
                    <button type="button" class="btn btn_add active" id='btnAddLogic'>添 加</button>
<!--                     <button type="button" class="btn">并 且</button> -->
                    <button type="button" class="btn" id="btnDelLogic">删除</button>
                  </div>
                  <div class="form-group mg0">                    
                    <label for="name">组合逻辑：</label>
<!--                     <select  multiple class="form-control"> -->
<!--                       <option>1</option> -->
<!--                       <option>2</option> -->
<!--                     </select> -->
					<table class="table  table-hover table-responsive search-form-table" id="tableLogic"  
                     data-toolbar="#toolbar" data-content-type="application/x-www-form-urlencoded"
                     data-toggle="table" data-method="post" data-id-field='id'
                     data-click-to-select="true" data-single-select="true"
                     data-row-style="rowStyle"
                     data-query-params="queryParams">
                        <thead>
                          <tr>
                            <th data-checkbox="true"></th>
                            <th data-field="id" data-visible='false'></th>
                            <th data-field="fieldName">字段</th>
                            <th data-field="fieldOpt">操作符</th>
                            <th data-field="logicOpt" data-visible='false'>操作符</th>
                            <th data-field="fieldVal">字段值</th>
                            <th data-field="logicVal" data-visible='false'>字段值</th>
                            <th data-field="fieldLogic" data-visible='false'>逻辑关系</th>
                            <th data-field="operator" data-formatter="logicFormatter">逻辑关系</th>
                          </tr>
                        </thead>
                    </table> 
                  </div>
                  <div class="searchBtn">
                    <button type="button" class="btn btn-success" id="btnQueryAttrLogic">查询</button>
                    <button type="reset" class="btn btn-warning" id="btnLogicReset">重置</button>
                  </div>
                  </div>
                  <div id="Sxcxbox-logic" style="display:none">                  
                  	<div class="Sxcxlist">
                      <p>共有<span class="red" id='queryLogicNum'></span>条结果<a href="#" class="btn_back" id='btnLogicBack'>返回<i class="arrow-back"></i></a></p>
                      <div>
                        <ul class="result" id="queryItemLogic">
                        </ul>
                      </div>
                      <div id="PaginationLogic" class="pagination"><span class="current prev"></span><span class="current">1</span><a href="#">2</a><span>...</span><a href="#">4</a><a href="#" class="next"></a></div>                        
                    </div>
                </div>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div id="fore-2d3d-menu-dw" >
          <!-- 当前位置 -->
          <ol class="breadcrumb">
            <li><i class="icon iconfont home">&#xe640;</i></li>
            <li><a href="#">首页</a></li>
            <li><a href="#">-地图定位</a></li>
          </ol>
          <!-- 二级菜单 -->
          <div class="panelBox">
            <div class="panelBox-heading"><i class="icon iconfont icon_map">&#xe600;</i><h3 class="panelBox-title">定位方式</h3><span class="arrow iconfont"></span></div>
            <div  class="panelBox-body">
              <div class="row submenu">
                <a href="#fore-2d3d-menu-dw-dmdw" class="col-sm-4 active" data-toggle="tab">
                  <i class="icon iconfont mapSearch">&#xe63e;</i>
                  <h2>地名定位</h2>
                </a>
                <a href="#fore-2d3d-menu-dw-zbdw" class="col-sm-4" data-toggle="tab">
                  <i class="icon iconfont mapSearch">&#xe637;</i>
                  <h2>坐标定位</h2>
                </a>
                <a href="#fore-2d3d-menu-dw-sqdw" class="col-sm-4" data-toggle="tab">
                  <i class="icon iconfont mapSearch">&#xe63c;</i>
                  <h2>书签定位</h2>
                </a>
              </div>
            </div>
          </div>
          <!-- 当前操作 -->
          <div class="subTabs">
            <div class="panelBox active" id="fore-2d3d-menu-dw-dmdw">
              <div class="panelBox-heading">
                <h3 class="panelBox-title">地名定位</h3><span class="arrow arrowUp"></span>
              </div>
              <div id="collapse-dtdw-dmdw" class="panelBox-body">
                <form class="form-horizontal search-form" role="form" id="locateHeader">
                  <div class="form-group form-group-sm">
                    <label for="name" class="col-sm-4">地名：</label>
                    <div class="col-sm-8">
                      <input class="form-control input-sm" id="address">
						<div id="search" style="display:block;width:160px;height:30px;"></div>
                    </div>
                  </div>
                  <div class="searchBtn">
                    <button type="button" class="btn btn-success" id="btnLocateAddress">查询</button>
                    <button type="reset" class="btn btn-warning">重置</button>
                  </div>
                </form>
                <div class="Sxcxlist" id="locateResult" style="display:none"> 
                      <p>共有<span class="red" id='locateNum'></span>条结果<a href="#" class="btn_back" id='btnLocateBack'>返回<i class="arrow-back"></i></a></p>
                      <div>
                        <ul class="result" id="locateItem">
                        </ul>
                      </div>
                      <div id="PaginationLocate" class="pagination"><span class="current prev"></span><span class="current">1</span><a href="#">2</a><span>...</span><a href="#">4</a><a href="#" class="next"></a></div>                        
                 </div>
              </div>
            </div>
            <div class="panelBox" id="fore-2d3d-menu-dw-zbdw" >
              <div class="panelBox-heading" >
                <h3 class="panelBox-title">坐标定位</h3><span class="arrow arrowUp"></span>
              </div>
              <div id="collapse-dtdw-zbdw" class="panelBox-body">
                <ul id="myTab" class="nav nav-tabs">
                  <li class="active"><a href="#Latitude"  data-toggle="tab">经纬度</a><i class="triangle-up"></i></li>
                  <li><a href="#Longitude"  data-toggle="tab">平面坐标</a><i class="triangle-up"></i></li>
                </ul>
                <form class="form-inline search-form" role="form">
                <div id="myTabContent" class="tab-content">
                
                  <div class="tab-pane  active" id="Latitude">
                    <div class="form-group">
                      <label for="name">经度：</label>
                      <div class="col-ms-12">
                        <input type="text" class="form-control input-sm text-min" placeholder="" id="degreeLng"><span>度</span>
                        <input type="text" class="form-control input-sm text-min" placeholder="" id="minLng"><span>分</span>
                        <input type="text" class="form-control input-sm text-min" placeholder="" id="secLng"><span>秒</span>
                      </div>
                    </div>
                    <div class="form-group">
                      <label for="name">纬度：</label>
                      <div class="col-ms-12">
                        <input type="text" class="form-control input-sm text-min" placeholder="" id="degreeLat"><span>度</span>
                        <input type="text" class="form-control input-sm text-min" placeholder="" id="minLat"><span>分</span>
                        <input type="text" class="form-control input-sm text-min" placeholder="" id="secLat"><span>秒</span>
                      </div>
                    </div>
                    <div class="searchBtn">
                      <button type="button" class="btn btn-success" id="btnLocateLngLat">定 位</button>
                      <button type="reset" class="btn btn-warning">重 置</button>
                    </div>  
                  </div>
                  <div class="tab-pane " id="Longitude">
                    <div class="form-group">
                      <label for="name" class="col-sm-4">X轴坐标：</label>
                      <div class="col-sm-8">
                        <input type="text" class="form-control input-sm" id="posX">
                      </div>
                    </div>                  
                    <div class="form-group">
                      <label for="name" class="col-sm-4">Y轴坐标：</label>
                      <div class="col-sm-8">
                        <input type="text" class="form-control input-sm" id="posY">
                      </div>
                    </div>
                    <div class="searchBtn">
                      <button type="button" class="btn btn-success" id="btnlocateXY">定 位</button>
                      <button type="reset" class="btn btn-warning">重 置</button>
                    </div>
                  </div>
                
                </div>  
                </form>
              </div>
            </div>
            <div class="panelBox" id="fore-2d3d-menu-dw-sqdw">
              <div class="panelBox-heading">
                <h3 class="panelBox-title">书签定位</h3><span class="arrow arrowUp"></span>
              </div>
              <div id="collapse-dtdw-sqdw" class="panelBox-body">
                <form class="form-inline search-form" role="form" method="post">
                  <div class="form-group form-group-sm">
                    <label for="name" class="col-sm-4">书签名称：</label>
                    <div class="col-sm-8">
                      <div class="input-group search-btn">
                        <input type="text" class="form-control" name="name" id="bookName">
                        <span class="input-group-btn btn-group-sm">
                          <button class="btn btn-default icon iconfont" type="button" id="bookmarkSearch">&#xe644;</button>
                        </span>
                      </div>
                    </div>
                  </div> 
                    <div class="searchBtn btn_list" id="Sqdwtoolbar">
                      <button type="button" class="btn btn_add active">新 增</button>
                      <button type="button" class="btn btn_edit">编 辑</button>
                      <button type="button" class="btn btn_del">删 除</button>
                    </div>
                    <table class="table  table-hover table-responsive search-form-table" id="tableSqdw"  
                     data-toolbar="#Sqdwtoolbar" data-content-type="application/x-www-form-urlencoded"
                     data-toggle="table" data-method="post"
                     data-url="${path}/locateService/toList.do"
                     data-click-to-select="true" data-single-select="true"
                     data-row-style="rowStyle"
                     data-query-params="queryParams"
                     data-pagination="true"
                     data-page-size="5"
                     data-striped="true">
                        <thead>
                          <tr>
                            <th data-radio="true"></th>
                            <th data-field="id">序号</th>
                            <th data-field="name">书签名称</th>
                            <th data-field="description">描述</th>
                          </tr>
                        </thead>
                    </table> 

                </form>
              </div>
            </div>
          </div>
        </div>
        <div id="fore-2d3d-menu-bz" >
          <!-- 当前位置 -->
          <ol class="breadcrumb">
            <li><i class="icon iconfont home">&#xe640;</i></li>
            <li><a href="#">首页</a></li>
            <li><a href="#">-地图标注</a></li>
          </ol>
          <!-- 二级菜单 -->
          <div class="panelBox">
            <div class="panelBox-heading"><i class="icon iconfont icon_map">&#xe601;</i><h3 class="panelBox-title">标绘类型</h3><span class="arrow iconfont"></span></div>
            <div  class="panelBox-body">
              <div class="row submenu">
                <a href="#fore-2d3d-menu-bz-xzbh" class="col-sm-3 active" data-toggle="tab">
                  <i class="icon iconfont mapSearch">&#xe68f;</i>
                  <h2>形状标绘</h2>
                </a>
                <a href="#fore-2d3d-menu-bz-twbh" class="col-sm-3" data-toggle="tab">
                  <i class="icon iconfont mapSearch">&#xe68e;</i>
                  <h2>图文标绘</h2>
                </a>
                <a href="#fore-2d3d-menu-bz-tsbh" class="col-sm-3" data-toggle="tab">
                  <i class="icon iconfont mapSearch">&#xe618;</i>
                  <h2>特殊标绘</h2>
                </a>
                <a href="#fore-2d3d-menu-bz-3dbh" class="col-sm-3" data-toggle="tab">
                  <i class="icon iconfont mapSearch">&#xe660;</i>
                  <h2>三维标绘</h2>
                </a>
              </div>
            </div>
          </div>
          <!-- 当前操作 -->
          <div class="subTabs">
            <div class="panelBox active" id="fore-2d3d-menu-bz-xzbh">
              <div class="panelBox-heading">
                <h3 class="panelBox-title">形状标绘</h3><span class="arrow arrowUp"></span>
              </div>
              <div  class="panelBox-body">
                <ul id="myTabShape" class="nav nav-tabs">
                  <li class="active"><a href="#planePlot" data-toggle="tab" >点标绘</a><i class="triangle-up"></i></li>
                  <li><a href="#planeLine" data-toggle="tab" >线标绘</a><i class="triangle-up"></i></li>
                  <li><a href="#planeFace" data-toggle="tab" >面标绘</a><i class="triangle-up"></i></li>
                </ul>
                <div class="tab-content">
                  <div class="tab-pane  active" id="planePlot">
                    <ul class="bs-icon-list clearfix">
                      <li class="active">
                        <span class="icon iconfont">&#xe66b;</span>
                        <span class="icon-class">点</span>
                      </li>
                      <li>
                        <span class="icon iconfont">&#xe67e;</span>
                        <span class="icon-class">多点</span>
                      </li>
                    </ul>
<!--                     <p class="mt15 text-right"><button type="button" class="btn btn_set">高级选项</button></p> -->
                    <!-- 高级选项面板 start -->  
                    <div class="advanced-btn">
                      <button type="button" class="btn btn_set">高级选项<span></span></button>
                      <div class="btn-group-adv">
                        <button type="submit" class="btn btn-success" id='btnPointSet'>应用</button>
                        <button type="reset" class="btn btn-warning" id='btnResetPt'>重置</button>
                      </div>
                    </div>
                    <div class="advanced-box">
                      <div class="form-horizontal search-form" role="form">
                        <div class="form-group form-group-sm">
                          <label for="name" class="col-sm-4">样式：</label>
                          <div class="col-sm-8 select-item">
                            <select class="form-control input-sm" id='selPtStyle'>
                              <option value='circle'>圆形</option>
                              <option value='cross'>十字</option>
                              <option value='diamond'>菱形</option>
                              <option value='square'>方块</option>
                              <option value='x'>对角十字</option>
                            </select>
                          </div>
                        </div>
                        <div class="form-group form-group-sm">
                          <label for="name" class="col-sm-4">大小：</label>
                          <div class="col-sm-8 select-item">
                            <select class="form-control input-sm" id='selPtSize'>
                              <option>8</option>
                              <option>10</option>
                              <option>12</option>
                              <option>14</option>
                              <option>16</option>
                            </select>
                          </div>
                        </div>
                        <div class="form-group form-group-sm">
                          <label for="name" class="col-sm-4">颜色：</label>
                          <div class="col-sm-8">
                            <div class="input-group-btn">
                              <input type="text" class="form-control  input-in color" id='txtPtColor' value='rgb(215,23,23)'>
                            </div>
                          </div>
                        </div>
                        <div class="form-group form-group-sm">
                          <label for="name" class="col-sm-4">透明度：</label>
                          <div class="col-sm-8 select-item">
                            <input type="text" class="form-control" value="1"  placeholder="取值0~1之间的小数" id='txtPtAlpha'>
                          </div>
                        </div>
                        <div class="form-group form-group-sm">
                          <label for="name" class="col-sm-4">边线样式：</label>
                          <div class="col-sm-8 select-item">
                            <select class="form-control input-sm" id='selPLStyle'>
                              <option value="dash">— — </option>
                              <option value="dashdot">— .</option>
                              <option value="longdashdotdot">— . .</option>
                              <option value="STYLE_DOT">. . .</option>
                              <option value="longdash">—— ——</option>
                              <option value="longdashdot">—— .</option>
                              <option value="none">无</option>
                              <option value="shortdash">- -</option>
                              <option value="shortdashdot">- .</option>
                              <option value="shortdashdotdot">- . .</option>
                              <option value="shortdot">. .</option>
                              <option value="solid">————</option>
                            </select>
                          </div>
                        </div>
                        <div class="form-group form-group-sm">
                          <label for="name" class="col-sm-4">边线颜色：</label>
                          <div class="col-sm-8">
                            <div class="input-group-btn">
                              <input type="text" class="form-control input-in color" id='txtPlColor'  value='rgb(110,110,110)'>
                            </div>
                          </div>
                        </div>
                        <div class="form-group form-group-sm">
                          <label for="name" class="col-sm-4">边线透明度：</label>
                          <div class="col-sm-8 select-item">
                            <input type="text" class="form-control" value='0.5' placeholder="取值0~1之间的小数" id='txtPlAlpha'>
                          </div>
                        </div>
                        <div class="form-group form-group-sm">
                          <label for="name" class="col-sm-4">线宽：</label>
                          <div class="col-sm-8 select-item">
                            <select class="form-control input-sm" id='selPlWidth'>
                              <option>1</option>
                              <option>2</option>
                              <option>3</option>
                              <option>4</option>
                            </select>
                          </div>
                        </div>                                   
<!--                         <div class="searchBtn">
  <button type="button" class="btn btn-success" id='btnPointSet'>应用</button>
</div> -->
                      </div>
                    </div>
                    <!-- 高级选项面板 end -->
                  </div>
                  <div class="tab-pane " id="planeLine">
                    <ul class="bs-icon-list clearfix">
                      <li class="active">
                        <span class="icon iconfont">&#xe670;</span>
                        <span class="icon-class">折线</span>
                      </li>
                      <li>
                        <span class="icon iconfont">&#xe66e;</span>
                        <span class="icon-class">圆弧</span>
                      </li>
                      <li>
                        <span class="icon iconfont">&#xe674;</span>
                        <span class="icon-class">曲线</span>
                      </li>
                      <li>
                        <span class="icon iconfont">&#xe673;</span>
                        <span class="icon-class">自由线</span>
                      </li>
                    </ul>
                    <!-- 高级选项面板 start -->  
                    <div class="advanced-btn">
                      <button type="button" class="btn btn_set">高级选项<span></span></button>
                      <div class="btn-group-adv">
                        <button type="submit" class="btn btn-success" id='btnPolylineSet'>应用</button>
                        <button type="reset" class="btn btn-warning" id='btnResetPl'>重置</button>
                      </div>
                    </div>
                    <div class="advanced-box">
                      <div class="form-horizontal search-form" role="form">
                        <div class="form-group form-group-sm">
                          <label for="name" class="col-sm-4">样式：</label>
                          <div class="col-sm-8 select-item">
                            <select class="form-control input-sm" id='selLStyle'>
                              <option value="solid">————</option>
                              <option value="dash">-  -</option>
                              <option value="dashdot">— .</option>
                              <option value="longdashdotdot">— —</option>
                              <option value="dot">. . .</option>
                              <option value="longdash">—— . .</option>
                              <option value="longdashdot">—— .</option>
                              <option value="none">无</option>
                              <option value="shortdash">- -</option>
                              <option value="shortdashdot">- .</option>
                              <option value="shortdashdotdot">- . .</option>
                              <option value="shortdot">. .</option>
                            </select>
                          </div>
                        </div>
                        <div class="form-group form-group-sm">
                          <label for="name" class="col-sm-4">颜色：</label>
                          <div class="col-sm-8">
                            <div class="input-group-btn">
                              <input type="text" class="form-control input-in color" id='txtLColor' value='rgb(215,23,23)'>
                            </div>
                          </div>
                        </div>
                        <div class="form-group form-group-sm">
                          <label for="name" class="col-sm-4">透明度：</label>
                          <div class="col-sm-8 select-item">
                            <input type="text" class="form-control" value='1' placeholder="取值0~1之间的小数" id='txtLAlpha'>
                          </div>
                        </div>
                        <div class="form-group form-group-sm">
                          <label for="name" class="col-sm-4">线宽：</label>
                          <div class="col-sm-8 select-item">
                            <select class="form-control input-sm" id='selLWidth'>
                              <option>1</option>
                              <option>2</option>
                              <option>3</option>
                              <option>4</option>
                            </select>
                          </div>
                        </div>                                
<!--                         <div class="searchBtn">
  <button type="button" class="btn btn-success" id='btnPolylineSet'>应用</button>
</div> -->
                      </div>
                    </div>
                    <!-- 高级选项面板 end --> 
                  </div>
                  <div class="tab-pane " id="planeFace">
                    <ul class="bs-icon-list clearfix">
                      <li class="active">
                        <span class="icon iconfont">&#xe672;</span>
                        <span class="icon-class">圆</span>
                      </li>
                      <li>
                        <span class="icon iconfont">&#xe671;</span>
                        <span class="icon-class">椭圆</span>
                      </li>
                      <li>
                        <span class="icon iconfont">&#xe684;</span>
                        <span class="icon-class">矩形</span>
                      </li>
                      <li>
                        <span class="icon iconfont">&#xe676;</span>
                        <span class="icon-class">多边形</span>
                      </li>
                      <li>
                        <span class="icon iconfont">&#xe679;</span>
                        <span class="icon-class">手绘面</span>
                      </li>
                      <li>
                        <span class="icon iconfont">&#xe67b;</span>
                        <span class="icon-class">聚集区</span>
                      </li>
<!--                       <li> -->
<!--                         <span class="icon iconfont">&#xe677;</span> -->
<!--                         <span class="icon-class">圆角矩形</span> -->
<!--                       </li> -->
<!--                       <li> -->
<!--                         <span class="icon iconfont">&#xe67a;</span> -->
<!--                         <span class="icon-class">闭合曲线</span> -->
<!--                       </li> -->
                    </ul>
                    <!-- 高级选项面板 start -->  
                    <div class="advanced-btn">
                      <button type="button" class="btn btn_set">高级选项<span></span></button>
                      <div class="btn-group-adv">
                        <button type="submit" class="btn btn-success" id='btnPgSet'>应用</button>
                        <button type="reset" class="btn btn-warning" id='btnResetPg'>重置</button>
                      </div>
                    </div>
                    <div class="advanced-box">
                      <div class="form-horizontal search-form" role="form">
                        <div class="form-group form-group-sm">
                          <label for="name" class="col-sm-4">样式：</label>
                          <div class="col-sm-8 select-item">
                            <select class="form-control input-sm" id='selPgStyle'>
                              <option value='backwarddiagonal'>反对角线</option>
                              <option value='cross'>交叉</option>
                              <option value='diagonalcross'>对角交叉</option>
                              <option value='forwarddiagonal'>前对角线</option>
                              <option value='horizontal'>水平线</option>
                              <option value='vertical'>竖直线</option>
                              <option value='solid'>实体填充</option>
                              <option value='none'>无填充</option>
                            </select>
                          </div>
                        </div>
                        <div class="form-group form-group-sm">
                          <label for="name" class="col-sm-4">填充颜色：</label>
                          <div class="col-sm-8">
                            <div class="input-group-btn">
                              <input type="text" class="form-control  input-in color" id='txtPgColor' value='rgb(215,23,23)'>
                            </div>
                          </div>
                        </div>
                        <div class="form-group form-group-sm">
                          <label for="name" class="col-sm-4">透明度：</label>
                          <div class="col-sm-8 select-item">
                            <input type="text" class="form-control" value="0.8"  placeholder="取值0~1之间的小数" id='txtPgAlpha'>
                          </div>
                        </div>
                        <div class="form-group form-group-sm">
                          <label for="name" class="col-sm-4">边线样式：</label>
                          <div class="col-sm-8 select-item">
                            <select class="form-control input-sm" id='selOutlineStyle'>
                              <option value="solid">————</option>
                              <option value="dash">— — </option>
                              <option value="dashdot">— .</option>
                              <option value="longdashdotdot">— . .</option>
                              <option value="STYLE_DOT">. . .</option>
                              <option value="longdash">—— ——</option>
                              <option value="longdashdot">—— .</option>
                              <option value="none">无</option>
                              <option value="shortdash">- -</option>
                              <option value="shortdashdot">- .</option>
                              <option value="shortdashdotdot">- . .</option>
                              <option value="shortdot">. .</option>
                            </select>
                          </div>
                        </div>
                        <div class="form-group form-group-sm">
                          <label for="name" class="col-sm-4">边线颜色：</label>
                          <div class="col-sm-8">
                            <div class="input-group-btn">
                              <input type="text" class="form-control input-in color" id='txtOutlineColor' value='rgb(215,23,23)'>
                            </div>
                          </div>
                        </div>
                        <div class="form-group form-group-sm">
                          <label for="name" class="col-sm-4">边线透明度：</label>
                          <div class="col-sm-8 select-item">
                            <input type="text" class="form-control" value='0.5' placeholder="取值0~1之间的小数" id='txtOutlineAlpha'>
                          </div>
                        </div>
                        <div class="form-group form-group-sm">
                          <label for="name" class="col-sm-4">线宽：</label>
                          <div class="col-sm-8 select-item">
                            <select class="form-control input-sm" id='selOutlineWidth'>
                              <option>1</option>
                              <option>2</option>
                              <option>3</option>
                              <option>4</option>
                            </select>
                          </div>
                        </div>                                 
<!--                         <div class="searchBtn">
  <button type="submit" class="btn btn-success" id='btnPgSet'>应用</button>
</div> -->
                      </div>
                    </div>
                    <!-- 高级选项面板 end --> 
                  </div>
                </div> 
              </div>
            </div>

            <div class="panelBox" id="fore-2d3d-menu-bz-twbh">
              <div class="panelBox-heading">
                <h3 class="panelBox-title">图文标绘</h3><span class="arrow arrowUp"></span>
              </div>
              <div  class="panelBox-body">
                <ul id="myTabTXT" class="nav nav-tabs">
                  <li class="active"><a href="#planePicture" data-toggle="tab" >图片标绘</a><i class="triangle-up"></i></li>
                  <li><a href="#planeText" data-toggle="tab" >文字标绘</a><i class="triangle-up"></i></li>
                </ul>
                <div class="tab-content">
                  <div class="tab-pane active" id="planePicture">
                    <form class="form-horizontal search-form" role="form">
                    <div class="form-group form-group-sm mt15">
                      <label for="name" class="col-sm-6">请选择图标类型：</label>
                      <div class="col-sm-6 select-item" style="width:100px;">
                        <select class="form-control input-sm" name="picType" onchange="Pic(this)">
                          <option value="0" name="picType">车辆类</option>
                          <option value="1" name="picType">建筑类</option>
                          <option value="2" name="picType">人员类</option>
                        </select>
                      </div>
                    </div>
                    </form>
                    <div class="tab-icon-list" id="IconList0">
                      <h4>车辆类</h4>            
                      <ul class="bs-icon-list_pic clearfix">
                        <li><span class="icon_pic car_1"></span><span class="icon-class">消防车1</span></li>
                        <li><span class="icon_pic car_2"></span><span class="icon-class">消防车2</span></li>
                        <li><span class="icon_pic car_3"></span><span class="icon-class">消防车3</span></li>
                        <li><span class="icon_pic car_4"></span><span class="icon-class">警车1</span></li>
                        <li><span class="icon_pic car_5"></span><span class="icon-class">公务用车</span></li>
                        <li><span class="icon_pic car_6"></span><span class="icon-class">警车2</span></li>
                      </ul>
                    </div>
                    <div class="tab-icon-list"  id="IconList1" style="display:none">
                      <h4>建筑类</h4>            
                      <ul class="bs-icon-list_pic clearfix">
                        <li><span class="icon_pic build_1"></span><span class="icon-class">办公楼1</span></li>
                        <li><span class="icon_pic build_2"></span><span class="icon-class">办公楼2</span></li>
                        <li><span class="icon_pic build_3"></span><span class="icon-class">木屋</span></li>
                        <li><span class="icon_pic build_4"></span><span class="icon-class">灯塔</span></li>
                      </ul>
                    </div>
                    <div class="tab-icon-list"  id="IconList2" style="display:none">
                      <h4>人员类</h4>            
                      <ul class="bs-icon-list_pic clearfix">
                        <li><span class="icon_pic per_1"></span><span class="icon-class">护林员1</span></li>
                        <li><span class="icon_pic per_2"></span><span class="icon-class">护林员2</span></li>
                        <li><span class="icon_pic per_3"></span><span class="icon-class">消防人员1</span></li>
                        <li><span class="icon_pic per_4"></span><span class="icon-class">消防人员2</span></li>
                      </ul>
                    </div>                     
                  </div>
                  <script type="text/javascript">
                   //图片标绘下拉选择
                      function Pic(obj){
                          $(obj).parents("form").next("div").find("tab-icon-list").each(function(){
                              $(this).hide().siblings("div").show();
                          });
                          $("#IconList" + obj.value).show().siblings("div").hide();
                      }
                  </script>
                  <div class="tab-pane " id="planeText">              
                    <ul class="bs-icon-list clearfix">
                      <li class="active">
                        <span class="icon iconfont">&#xe6ab;</span>
                        <span class="icon-class">简单文字</span>
                      </li>
                      <li>
                        <span class="icon iconfont">&#xe6ac;</span>
                        <span class="icon-class">标题文字</span>
                      </li>
                      <li>
                        <span class="icon iconfont">&#xe6aa;</span>
                        <span class="icon-class">艺术文字</span>
                      </li>
                    </ul>
                    <textarea rows="" cols="28" placeholder="输入需标注的文字" id='txtContent'></textarea>
                    <!-- 高级选项面板 start -->  
                    <div class="advanced-btn">
                      <button type="button" class="btn btn_set">高级选项<span></span></button>
                      <div class="btn-group-adv">
                        <button type="submit" class="btn btn-success" id='btnFontSet'>应用</button>
                        <button type="reset" class="btn btn-warning" id='btnResetTxt'>重置</button>
                      </div>
                    </div>
                    <div class="advanced-box">
                      <form class="form-horizontal search-form" role="form">
                        <div class="form-group form-group-sm">
                          <label for="name" class="col-sm-4">字体：</label>
                          <div class="col-sm-8 select-item">
                            <select class="form-control input-sm" id='selFontFamily'>
                              <option value='宋体'>宋体</option>
                              <option value='楷体'>楷体</option>
                              <option value='黑体'>黑体</option>
                              <option value='仿宋'>仿宋</option>
                              <option value='华文仿宋'>华文仿宋</option>
                              <option value='微软雅黑'>微软雅黑</option>
                              <option value='华文中宋'>华文中宋</option>
                              <option value='华文隶书'>华文隶书</option>
                              <option value='华文楷体'>华文楷体</option>
                              <option value='华文细黑'>华文细黑</option>
                              <option value='Times New Roman'>Times New Roman</option>
                              <option value='Courier'>Courier</option>
                              <option value='Consolas'>Consolas</option>
                              <option value='Wingdings'>Wingdings</option>
                              <option value='Wingdings 2'>Wingdings 2</option>
                              <option value='Cambria Math'>Cambria Math</option>
                              <option value='serif'>serif</option>
                            </select>
                          </div>
                        </div>
                        <div class="form-group form-group-sm">
                          <label for="name" class="col-sm-4">字体尺寸：</label>
                          <div class="col-sm-8 select-item">
                            <select class="form-control input-sm" id='selFontSize'>
                              <option>10</option>
                              <option>12</option>
                              <option>14</option>
                              <option>16</option>
                              <option>18</option>
                              <option>20</option>
                              <option>22</option>
                              <option>24</option>
                              <option>26</option>
                              <option>28</option>
                              <option>30</option>
                              <option>32</option>
                              <option>34</option>
                            </select>
                          </div>
                        </div>
                        <div class="form-group form-group-sm">
                          <label for="name" class="col-sm-4">样式：</label>
                          <div class="col-sm-8 select-item">
                            <select class="form-control input-sm" id='selFontStyle'>
                              <option value='normal'>常规</option>
                              <option value='italic'>斜体</option>
                            </select>
                          </div>
                        </div>
                        <div class="form-group form-group-sm">
                          <label for="name" class="col-sm-4">字形：</label>
                          <div class="col-sm-8 select-item">
                            <select class="form-control input-sm" id='selFontVariant'>
                              <option value='normal'>常规</option>
                              <option value='small-caps'>小号大写</option>
                            </select>
                          </div>
                        </div>
                        <div class="form-group form-group-sm">
                          <label for="name" class="col-sm-4">粗细：</label>
                          <div class="col-sm-8 select-item">
                            <select class="form-control input-sm" id='selFontBold'>
                              <option value='normal'>标准</option>
                              <option value='bold'>粗体</option>
                              <option value='bolder'>超粗体</option>
                              <option value='lighter'>亮体</option>
                            </select>
                          </div>
                        </div>
                        <div class="form-group form-group-sm">
                          <label for="name" class="col-sm-4">字体颜色：</label>
                          <div class="col-sm-8">
                            <div class="input-group-btn">
                              <input type="text" class="form-control  input-in color" id='txtFontColor' value='rgb(215,23,23)'>
                            </div>
                          </div>
                        </div>
<!--                         <div class="searchBtn">
  <button type="button" class="btn btn-success" id='btnFontSet'>应用</button>
</div> -->
                      </form>
                    </div>
                    <!-- 高级选项面板 end -->
                  </div>
                </div>    
              </div>
            </div>
                        
            <div class="panelBox" id="fore-2d3d-menu-bz-tsbh">
              <div class="panelBox-heading">
                <h3 class="panelBox-title">特殊标绘</h3><span class="arrow arrowUp"></span>
              </div>
              <div  class="panelBox-body">
                <ul id="myTabSpecial" class="nav nav-tabs">
                  <li class="active"><a href="#planeFlag" data-toggle="tab" >旗帜标绘</a><i class="triangle-up"></i></li>
                  <li><a href="#planeArrows" data-toggle="tab" >箭头标绘</a><i class="triangle-up"></i></li>
                </ul>
                <div class="tab-content">                
                  <div class="tab-pane active" id="planeFlag">
                    <ul class="bs-icon-list clearfix">
                      <li class="active">
                        <span class="icon iconfont">&#xe683;</span>
                        <span class="icon-class">曲线旗标</span>
                      </li>
                      <li>
                        <span class="icon iconfont">&#xe688;</span>
                        <span class="icon-class">直角旗标</span>
                      </li>
                      <li>
                        <span class="icon iconfont">&#xe687;</span>
                        <span class="icon-class">三角旗标</span>
                      </li>
                    </ul>
                    <!-- 高级选项面板 start -->  
<!--                     <div class="advanced-btn"> -->
<!--                       <button type="button" class="btn btn_set">高级选项<span></span></button> -->
<!--                       <div class="btn-group-adv"> -->
<!--                         <button type="submit" class="btn btn-success" id='btnFlagSet'>应用</button> -->
<!--                         <button type="reset" class="btn btn-warning" id='btnResetFlag'>重置</button> -->
<!--                       </div> -->
<!--                     </div> -->
<!--                     <div class="advanced-box"> -->
<!--                       <div class="form-horizontal search-form" role="form"> -->
<!--                         <div class="form-group form-group-sm"> -->
<!--                           <label for="name" class="col-sm-4">填充颜色：</label> -->
<!--                           <div class="col-sm-8"> -->
<!--                             <div class="input-group-btn"> -->
<!--                               <input type="text" class="form-control  input-in color" id='txtFlagColor' value='rgb(255,0,0)'> -->
<!--                             </div> -->
<!--                           </div> -->
<!--                         </div> -->
<!--                         <div class="form-group form-group-sm"> -->
<!--                           <label for="name" class="col-sm-4">透明度：</label> -->
<!--                           <div class="col-sm-8 select-item"> -->
<!--                             <input type="text" class="form-control" value="0.8"  placeholder="取值0~1之间的小数" id='txtFlagAlpha'> -->
<!--                           </div> -->
<!--                         </div> -->
<!--                       </div> -->
<!--                     </div> -->
                    <!-- 高级选项面板 end -->     
                  </div>
                  <div class="tab-pane " id="planeArrows">
                    <ul class="bs-icon-list clearfix">
                      <li class="active">
                        <span class="icon iconfont">&#xe682;</span>
                        <span class="icon-class">直箭头</span>
                      </li>
                      <li>
                        <span class="icon iconfont">&#xe690;</span>
                        <span class="icon-class">斜箭头</span>
                      </li>
                      <li>
                        <span class="icon iconfont">&#xe686;</span>
                        <span class="icon-class">双箭头</span>
                      </li>
                      <li>
                        <span class="icon iconfont">&#xe689;</span>
                        <span class="icon-class">燕尾直箭头</span>
                      </li>
                      <li>
                        <span class="icon iconfont">&#xe691;</span>
                        <span class="icon-class">燕尾斜箭头</span>
                      </li>
<!--                       <li> -->
<!--                         <span class="icon iconfont">&#xe68a;</span> -->
<!--                         <span class="icon-class">折线箭头</span> -->
<!--                       </li> -->
<!--                       <li> -->
<!--                         <span class="icon iconfont">&#xe68b;</span> -->
<!--                         <span class="icon-class">平行搜寻区</span> -->
<!--                       </li> -->
<!--                       <li> -->
<!--                         <span class="icon iconfont">&#xe68c;</span> -->
<!--                         <span class="icon-class">扇形搜寻区</span> -->
<!--                       </li> -->
                    </ul> 
                    <!-- 高级选项面板 start -->  
                    <div class="advanced-btn">
                      <button type="button" class="btn btn_set">高级选项<span></span></button>
                      <div class="btn-group-adv">
                        <button type="submit" class="btn btn-success" id='btnArrSet'>应用</button>
                        <button type="reset" class="btn btn-warning" id='btnResetArr'>重置</button>
                      </div>
                    </div>
                    <div class="advanced-box">
                      <div class="form-horizontal search-form" role="form">
                        <div class="form-group form-group-sm">
                          <label for="name" class="col-sm-4">样式：</label>
                          <div class="col-sm-8 select-item">
                            <select class="form-control input-sm" id='selArrStyle'>
                              <option value='backwarddiagonal'>反对角线</option>
                              <option value='cross'>交叉</option>
                              <option value='diagonalcross'>对角交叉</option>
                              <option value='forwarddiagonal'>前对角线</option>
                              <option value='horizontal'>水平线</option>
                              <option value='vertical'>竖直线</option>
                              <option value='solid'>实体填充</option>
                              <option value='none'>无填充</option>
                            </select>
                          </div>
                        </div>
                        <div class="form-group form-group-sm">
                          <label for="name" class="col-sm-4">填充颜色：</label>
                          <div class="col-sm-8">
                            <div class="input-group-btn">
                              <input type="text" class="form-control  input-in color" id='txtArrColor' value='rgb(215,23,23)'>
                            </div>
                          </div>
                        </div>
                        <div class="form-group form-group-sm">
                          <label for="name" class="col-sm-4">透明度：</label>
                          <div class="col-sm-8 select-item">
                            <input type="text" class="form-control" value="0.8"  placeholder="取值0~1之间的小数" id='txtArrAlpha'>
                          </div>
                        </div>
                        <div class="form-group form-group-sm">
                          <label for="name" class="col-sm-4">边线样式：</label>
                          <div class="col-sm-8 select-item">
                            <select class="form-control input-sm" id='selArrOutlineStyle'>
                              <option value="solid">————</option>
                              <option value="dash">— — </option>
                              <option value="dashdot">— .</option>
                              <option value="longdashdotdot">— . .</option>
                              <option value="STYLE_DOT">. . .</option>
                              <option value="longdash">—— ——</option>
                              <option value="longdashdot">—— .</option>
                              <option value="none">无</option>
                              <option value="shortdash">- -</option>
                              <option value="shortdashdot">- .</option>
                              <option value="shortdashdotdot">- . .</option>
                              <option value="shortdot">. .</option>
                            </select>
                          </div>
                        </div>
                        <div class="form-group form-group-sm">
                          <label for="name" class="col-sm-4">边线颜色：</label>
                          <div class="col-sm-8">
                            <div class="input-group-btn">
                              <input type="text" class="form-control input-in color" id='txtArrOutlineColor' value='rgb(215,23,23)'>
                            </div>
                          </div>
                        </div>
                        <div class="form-group form-group-sm">
                          <label for="name" class="col-sm-4">边线透明度：</label>
                          <div class="col-sm-8 select-item">
                            <input type="text" class="form-control" value='0.5' placeholder="取值0~1之间的小数" id='txtArrOutlineAlpha'>
                          </div>
                        </div>
                        <div class="form-group form-group-sm">
                          <label for="name" class="col-sm-4">线宽：</label>
                          <div class="col-sm-8 select-item">
                            <select class="form-control input-sm" id='selArrOutlineWidth'>
                              <option>1</option>
                              <option>2</option>
                              <option>3</option>
                              <option>4</option>
                            </select>
                          </div>
                        </div>                                 
                      </div>
                    </div>
                    <!-- 高级选项面板 end -->    
                  </div>  
                </div>                
              </div>
            </div>

            <div class="panelBox" id="fore-2d3d-menu-bz-3dbh">
              <div class="panelBox-heading">
                <h3 class="panelBox-title">三维标绘</h3><span class="arrow arrowUp"></span>
              </div>
              <div  class="panelBox-body">
                <ul id="myTab3D" class="nav nav-tabs">
                  <li class="active"><a href="#planeShape" data-toggle="tab" >形状标绘</a><i class="triangle-up"></i></li>
                  <li><a href="#planeTXT" data-toggle="tab" >图文标绘</a><i class="triangle-up"></i></li>
                  <li><a href="#plane3D" data-toggle="tab" >立体标绘</a><i class="triangle-up"></i></li>
                </ul>
                <div class="tab-content">
                  <div class="tab-pane  active" id="planeShape">
                    <ul class="bs-icon-list clearfix">
                      <li class="active" id="planePlot3dPoint">
                        <span class="icon iconfont">&#xe66b;</span>
                        <span class="icon-class">点</span>
                      </li>
                      <li id="planePlot3dMultPoint">
                        <span class="icon iconfont">&#xe67e;</span>
                        <span class="icon-class">多点</span>
                      </li>
                    </ul>
                    <ul class="bs-icon-list clearfix">
                      <li class="active" id="planePlot3dPolyline">
                        <span class="icon iconfont">&#xe670;</span>
                        <span class="icon-class">折线</span>
                      </li>
                      <li id="planePlot3dArc">
                        <span class="icon iconfont">&#xe66e;</span>
                        <span class="icon-class">圆弧</span>
                      </li>
                      <li id="planePlot3dFreeline">
                        <span class="icon iconfont">&#xe673;</span>
                        <span class="icon-class">自由线</span>
                      </li>
                      <li id="planePlot3dArrowline">
                        <span class="icon iconfont">&#xe68d;</span>
                        <span class="icon-class">箭头线</span>
                      </li>
                    </ul>
                    <ul class="bs-icon-list clearfix">
                      <li class="active" id="planePlot3dCircle">
                        <span class="icon iconfont">&#xe672;</span>
                        <span class="icon-class">圆</span>
                      </li>
                      <li id="planePlot3dEllipse">
                        <span class="icon iconfont">&#xe671;</span>
                        <span class="icon-class">椭圆</span>
                      </li>
                      <li id="planePlot3dRectangle">
                        <span class="icon iconfont">&#xe684;</span>
                        <span class="icon-class">矩形</span>
                      </li>
                      <li id="planePlot3dPolygon">
                        <span class="icon iconfont">&#xe676;</span>
                        <span class="icon-class">多边形</span>
                      </li>
                      <li id="planePlot3dStraightArrow">
                        <span class="icon iconfont">&#xe682;</span>
                        <span class="icon-class">直箭头</span>
                      </li>
                      <li id="planePlot3dTailsArrow">
                        <span class="icon iconfont">&#xe681;</span>
                        <span class="icon-class">燕尾斜箭头</span>
                      </li>
                    </ul>                  
                    <!-- 高级选项面板 start -->  
                    <div class="advanced-btn">
                      <button type="button" class="btn btn_set">高级选项<span></span></button>
                      <div class="btn-group-adv">
                        <button type="submit" class="btn btn-success" id="plot3dSetLineStyle">应用</button>
                        <button type="reset" class="btn btn-warning" id="plot3dSetLineStyleDefault">重置</button>
                      </div>
                    </div>
                    <div class="advanced-box">
                      <div class="form-horizontal search-form" role="form">
                        <div class="form-group form-group-sm">
                          <label for="name" class="col-sm-4">填充颜色：</label>
                          <div class="col-sm-8">
                            <div class="input-group-btn">
                              <input type="text" class="form-control  input-in color" id="txtPgColorPlot2dStyle" value="#6e6e6e">
                            </div>
                          </div>
                        </div>
                        <div class="form-group form-group-sm">
                          <label for="name" class="col-sm-4">填充透明度：</label>
                          <div class="col-sm-8 select-item">
                            <input type="text" class="form-control" value="0.5"  placeholder="取值0~1之间的小数" id='txtPgAlphaPlot2dStyle'>
                          </div>
                        </div>
                        <div class="form-group form-group-sm">
                          <label for="name" class="col-sm-4">边线样式：</label>
                          <div class="col-sm-8 select-item">
                            <select class="form-control input-sm" id='selOutlineStylePlot2dStyle'>
                              <option value="SOLID" selected="true">-----------</option>
                              <option value="XLARGE_DASH">----- -----</option>
                              <option value="LARGE_DASH"> ---- ---- </option>
                              <option value="MEDIUM_DASH">--- --- ---</option>
                              <option value="SMALL_DASH">-- -- -- --</option>
                              <option value="TINY_DASH">- - - - - -</option>
                              <option value="DOTS">•••••••••••</option>
                              <option value="DASH_DOT_DASH">• - • - • -</option>
                              <option value="DASH_DOT_DOT_DASH">•• - •• - ••</option>
                            </select>
                          </div>
                        </div>
                        <div class="form-group form-group-sm">
                          <label for="name" class="col-sm-4">边线颜色：</label>
                          <div class="col-sm-8">
                            <div class="input-group-btn">
                              <input type="text" class="form-control input-in color" id="txtOutlineColorPlot2dStyle" value="#FF0000">
                            </div>
                          </div>
                        </div>
                        <div class="form-group form-group-sm">
                          <label for="name" class="col-sm-4">边线透明度：</label>
                          <div class="col-sm-8 select-item">
                            <input type="text" class="form-control" value='0.5' placeholder="取值0~1之间的小数" id='txtOutlineAlphaPlot2dStyle'>
                          </div>
                        </div>
                        <div class="form-group form-group-sm">
                          <label for="name" class="col-sm-4">边线宽：</label>
                          <div class="col-sm-8 select-item">
                            <select class="form-control input-sm" id='selOutlineWidthPlot2dStyle'>
                              <option value="1">1</option>
                              <option value="2" selected="true">2</option>
                              <option value="3">3</option>
                              <option value="4">4</option>
                            </select>
                          </div>
                        </div>
<!--                         <div class="searchBtn">
  <button class="btn btn-success" id="plot3dSetLineStyle">应用</button>
</div> -->
                      </div>                      
                    </div>
                    <!-- 高级选项面板 end -->
                  </div>
                  <div class="tab-pane " id="planeTXT">
                    <!-- 图片标绘 start-->
                    <form class="form-horizontal search-form" role="form">
                    <div class="form-group form-group-sm mt15">
                      <label for="name" class="col-sm-6">请选择图标类型：</label>
                      <div class="col-sm-6 select-item" style="width:100px;">
                        <select class="form-control input-sm" name="pic3dType" onchange="Pic3d(this)">
                          <option value="0" name="pic3dType">车辆类</option>
                          <option value="1" name="pic3dType">建筑类</option>
                          <option value="2" name="pic3dType">人员类</option>
                          <option value="3" name="pic3dType">旗帜类</option>
                        </select>
                      </div>
                    </div>
                    </form>
                    <div class="tab-icon-list" id="3dIconList0">
                      <h4>车辆类</h4>            
                      <ul class="bs-icon-list_pic clearfix">
                        <li><span class="icon_pic car_1"></span><span class="icon-class">消防车1</span></li>
                        <li><span class="icon_pic car_2"></span><span class="icon-class">消防车2</span></li>
                        <li><span class="icon_pic car_3"></span><span class="icon-class">消防车3</span></li>
                        <li><span class="icon_pic car_4"></span><span class="icon-class">警车1</span></li>
                        <li><span class="icon_pic car_5"></span><span class="icon-class">公务用车</span></li>
                        <li><span class="icon_pic car_6"></span><span class="icon-class">警车2</span></li>
                      </ul>
                    </div>
                    <div class="tab-icon-list"  id="3dIconList1" style="display:none">
                      <h4>建筑类</h4>            
                      <ul class="bs-icon-list_pic clearfix">
                        <li><span class="icon_pic build_1"></span><span class="icon-class">办公楼1</span></li>
                        <li><span class="icon_pic build_2"></span><span class="icon-class">办公楼2</span></li>
                        <li><span class="icon_pic build_3"></span><span class="icon-class">木屋</span></li>
                        <li><span class="icon_pic build_4"></span><span class="icon-class">灯塔</span></li>
                      </ul>
                    </div>
                    <div class="tab-icon-list"  id="3dIconList2" style="display:none">
                      <h4>人员类</h4>            
                      <ul class="bs-icon-list_pic clearfix">
                        <li><span class="icon_pic per_1"></span><span class="icon-class">护林员1</span></li>
                        <li><span class="icon_pic per_2"></span><span class="icon-class">护林员2</span></li>
                        <li><span class="icon_pic per_3"></span><span class="icon-class">消防人员1</span></li>
                        <li><span class="icon_pic per_4"></span><span class="icon-class">消防人员2</span></li>
                      </ul>
                    </div>
                    <div class="tab-icon-list"  id="3dIconList3" style="display:none">
                      <h4>旗帜类</h4>            
                      <ul class="bs-icon-list_pic clearfix">
                        <li><span class="icon_pic flg3dCurve"></span><span class="icon-class">曲线旗标</span></li>
                        <li><span class="icon_pic flg3dRectangle"></span><span class="icon-class">直角旗标</span></li>
                        <li><span class="icon_pic flg3dTriang"></span><span class="icon-class">三角旗标</span></li>
                      </ul>
                    </div>                      
                    <script type="text/javascript">
                     //图片标绘下拉选择
                        function Pic3d(obj){
                            $(obj).parents("form").next("div").find("tab-icon-list").each(function(){
                                $(this).hide().siblings("div.tab-icon-list").show();
                            });
                            $("#3dIconList" + obj.value).show().siblings("div.tab-icon-list").hide();
                        }
                    </script>
                    <!-- 图片标绘 end-->
                    <ul class="bs-icon-list clearfix">
                      <li class="active" id="planePlot3dSimpleText">
                        <span class="icon iconfont">&#xe6ab;</span>
                        <span class="icon-class">简单文字</span>
                      </li>
                      <li id="planePlot3dTitleText">
                        <span class="icon iconfont">&#xe6ac;</span>
                        <span class="icon-class">标题文字</span>
                      </li>
                      <li id="planePlot3dArtText">
                        <span class="icon iconfont">&#xe6aa;</span>
                        <span class="icon-class">艺术文字</span>
                      </li>
                    </ul>
                    <textarea rows="" cols="28" placeholder="输入需标注的文字" id='txtContentPlot3d'></textarea>
                    <!-- 高级选项面板 start -->  
                    <div class="advanced-btn">
                      <button type="button" class="btn btn_set">高级选项<span></span></button>
                      <div class="btn-group-adv">
                        <button type="submit" class="btn btn-success" id="plot3dSetTextImageStyle">应用</button>
                        <button type="reset" class="btn btn-warning" id="plot3dSetTextImageStyleDefault">重置</button>
                      </div>
                    </div>
                    <div class="advanced-box">
                      <div class="form-horizontal search-form" role="form">
                        <div class="form-group form-group-sm">
                          <label for="name" class="col-sm-4">图标比例：</label>
                          <div class="col-sm-8 select-item">
                            <input type="text" class="form-control" value='1' placeholder="取值1~10之间的数" id='txtImageScalePlot3dStyle'>
                          </div>
                        </div>
                        <div class="form-group form-group-sm">
                          <label for="name" class="col-sm-4">字体：</label>
                          <div class="col-sm-8 select-item">
                            <select class="form-control input-sm" id='selFontFamilyPlot3d'>
                              <option value='宋体' selected="true">宋体</option>
                              <option value='楷体'>楷体</option>
                              <option value='黑体'>黑体</option>
                              <option value='仿宋'>仿宋</option>
                              <option value='华文仿宋'>华文仿宋</option>
                              <option value='微软雅黑'>微软雅黑</option>
                              <option value='华文中宋'>华文中宋</option>
                              <option value='华文隶书'>华文隶书</option>
                              <option value='华文楷体'>华文楷体</option>
                              <option value='华文细黑'>华文细黑</option>
                              <option value='Times New Roman'>Times New Roman</option>
                              <option value='Courier'>Courier</option>
                              <option value='Consolas'>Consolas</option>
                              <option value='Wingdings'>Wingdings</option>
                              <option value='Wingdings 2'>Wingdings 2</option>
                              <option value='Cambria Math'>Cambria Math</option>
                              <option value='serif'>serif</option>
                            </select>
                          </div>
                        </div>
                        <div class="form-group form-group-sm">
                          <label for="name" class="col-sm-4">字体尺寸：</label>
                          <div class="col-sm-8 select-item">
                            <select class="form-control input-sm" id='selFontSizePlot3d'>
                              <option value="10">10</option>
                              <option value="12">12</option>
                              <option value="14">14</option>
                              <option value="16" selected="true">16</option>
                              <option value="18">18</option>
                              <option value="20">20</option>
                              <option value="22">22</option>
                              <option value="24">24</option>
                              <option value="26">26</option>
                              <option value="28">28</option>
                              <option value="30">30</option>
                              <option value="32">32</option>
                              <option value="34">34</option>
                            </select>
                          </div>
                        </div>
                        <div class="form-group form-group-sm">
                          <label for="name" class="col-sm-4">样式：</label>
                          <div class="col-sm-8 select-item">
                            <select class="form-control input-sm" id='selFontStylePlot3d'>
                              <option selected="true" value='0'>常规</option>
                              <option value='1'>下划线</option>
                            </select>
                          </div>
                        </div>
                        <div class="form-group form-group-sm">
                          <label for="name" class="col-sm-4">字形：</label>
                          <div class="col-sm-8 select-item">
                            <select class="form-control input-sm" id='selFontVariantPlot3d'>
                              <option selected="true" value='0'>常规</option>
                              <option value='1'>斜体</option>
                            </select>
                          </div>
                        </div>
                        <div class="form-group form-group-sm">
                          <label for="name" class="col-sm-4">粗细：</label>
                          <div class="col-sm-8 select-item">
                            <select class="form-control input-sm" id='selFontBoldPlot3d'>
                              <option selected="true" value='0'>常规</option>
                              <option value='0'>粗体</option>
                            </select>
                          </div>
                        </div>
                        <div class="form-group form-group-sm">
                          <label for="name" class="col-sm-4">字体颜色：</label>
                          <div class="col-sm-8">
                            <div class="input-group-btn">
                              <input type="text" class="form-control  input-in color" value="#ffda22" id='txtFontColorPlot3d'>
                            </div>
                          </div>
                        </div>
                        <div class="form-group form-group-sm">
                          <label for="name" class="col-sm-4">文字透明度：</label>
                          <div class="col-sm-8 select-item">
                            <input type="text" class="form-control" value='1' placeholder="取值0~1之间的小数" id='txtFontAlphaPlot3dStyle'>
                          </div>
                        </div>
                        <div class="form-group form-group-sm">
                          <label for="name" class="col-sm-4">背景颜色：</label>
                          <div class="col-sm-8">
                            <div class="input-group-btn">
                              <input type="text" class="form-control  input-in color" value="#000000" id='txtBackColorPlot3d'>
                            </div>
                          </div>
                        </div>
                        <div class="form-group form-group-sm">
                          <label for="name" class="col-sm-4">背景透明度：</label>
                          <div class="col-sm-8 select-item">
                            <input type="text" class="form-control" value='0' placeholder="取值0~1之间的小数" id='txtFontBackAlphaPlot3dStyle'>
                          </div>
                        </div>
<!--                         <div class="searchBtn">
  <button class="btn btn-success" id="plot3dSetTextImageStyle">应用</button>
</div> -->
                      </div>
                    </div>
                    <!-- 高级选项面板 end --> 
                  </div>
                  <div class="tab-pane " id="plane3D">
                    <ul class="bs-icon-list clearfix">
                      <li class="active" id="planePlot3dBox3D">
                        <span class="icon iconfont">&#xe661;</span>
                        <span class="icon-class">立方体</span>
                      </li>
                      <li id="planePlot3dClinder3D">
                        <span class="icon iconfont">&#xe65c;</span>
                        <span class="icon-class">圆柱体</span>
                      </li>
                      <li id="planePlot3dSphere3D">
                        <span class="icon iconfont">&#xe657;</span>
                        <span class="icon-class">球体</span>
                      </li>
                      <li id="planePlot3dCone3D">
                        <span class="icon iconfont">&#xe65e;</span>
                        <span class="icon-class">圆锥体</span>
                      </li>
                      <li id="planePlot3dPyramid3D">
                        <span class="icon iconfont">&#xe662;</span>
                        <span class="icon-class">立方椎体</span>
                      </li>
                      <li id="planePlot3dArrow3D">
                        <span class="icon iconfont">&#xe665;</span>
                        <span class="icon-class">三维箭头</span>
                      </li>
                    </ul>
                    <!-- 高级选项面板 start -->  
                    <div class="advanced-btn">
                      <button type="button" class="btn btn_set">高级选项<span></span></button>
                      <div class="btn-group-adv">
                        <button type="submit" class="btn btn-success" id="plot3dSetFillStyle">应用</button>
                        <button type="reset" class="btn btn-warning" id="plot3dSetFillStyleDefault">重置</button>
                      </div>
                    </div>
                    <div class="advanced-box">
                      <div class="form-horizontal search-form" role="form">
                        <div class="form-group form-group-sm">
                          <label for="name" class="col-sm-4">填充颜色：</label>
                          <div class="col-sm-8">
                            <div class="input-group-btn">
                              <input type="text" class="form-control  input-in color" id="txtPgColorPlot3d" value="#6e6e6e">
                            </div>
                          </div>
                        </div>
                        <div class="form-group form-group-sm">
                          <label for="name" class="col-sm-4">填充透明度：</label>
                          <div class="col-sm-8 select-item">
                            <input type="text" class="form-control" value="0.5"  placeholder="取值0~1之间的小数" id='txtPgAlphaPlot3d'>
                          </div>
                        </div>
                        <div class="form-group form-group-sm">
                          <label for="name" class="col-sm-4">边线样式：</label>
                          <div class="col-sm-8 select-item">
                            <select class="form-control input-sm" id='selOutlineStylePlot3d'>
                              <option value="SOLID" selected="true">-----------</option>
                              <option value="XLARGE_DASH">----- -----</option>
                              <option value="LARGE_DASH"> ---- ---- </option>
                              <option value="MEDIUM_DASH">--- --- ---</option>
                              <option value="SMALL_DASH">-- -- -- --</option>
                              <option value="TINY_DASH">- - - - - -</option>
                              <option value="DOTS">•••••••••••</option>
                              <option value="DASH_DOT_DASH">• - • - • -</option>
                              <option value="DASH_DOT_DOT_DASH">•• - •• - ••</option>
                            </select>
                          </div>
                        </div>
                        <div class="form-group form-group-sm">
                          <label for="name" class="col-sm-4">边线颜色：</label>
                          <div class="col-sm-8">
                            <div class="input-group-btn">
                              <input type="text" class="form-control input-in color" id="txtOutlineColorPlot3d" value="#FF0000">
                            </div>
                          </div>
                        </div>
                        <div class="form-group form-group-sm">
                          <label for="name" class="col-sm-4">边线透明度：</label>
                          <div class="col-sm-8 select-item">
                            <input type="text" class="form-control" value='0.5' placeholder="取值0~1之间的小数" id='txtOutlineAlphaPlot3d'>
                          </div>
                        </div>
                        <div class="form-group form-group-sm">
                          <label for="name" class="col-sm-4">边线宽：</label>
                          <div class="col-sm-8 select-item">
                            <select class="form-control input-sm" id='selOutlineWidthPlot3d'>
                              <option value="1">1</option>
                              <option value="2" selected="true">2</option>
                              <option value="3">3</option>
                              <option value="4">4</option>
                            </select>
                          </div>
                        </div>
<!--                         <div class="searchBtn">
  <button class="btn btn-success" id="plot3dSetFillStyle">应用</button>
</div> -->
                      </div>
                    </div>
                    <!-- 高级选项面板 end --> 
                  </div>
                </div> 
              </div>
            </div> 
          </div>
        </div>
        <div id="fore-2d3d-menu-zt" >
          <!-- 当前位置 -->
          <ol class="breadcrumb">
            <li><i class="icon iconfont home">&#xe640;</i></li>
            <li><a href="#">首页</a></li>
            <li><a href="#">-专题地图</a></li>
          </ol>
          <!-- 二级菜单 -->
          <div class="panelBox">
            <div class="panelBox-heading" ><i class="icon iconfont icon_map">&#xe60a;</i><h3 class="panelBox-title">专题地图</h3><span class="arrow iconfont"></span></div>
            <div class="panelBox-body">
              <div class="row submenu">
                <a href="#fore-2d3d-menu-zt-zttzs" class="col-sm-12 active" data-toggle="tab">
                  <i class="icon iconfont mapSearch">&#xe6c8;</i>
                  <h2>专题地图</h2>
                </a>
              </div>
            </div>
          </div>
          <!-- 当前操作 -->
          <div class="subTabs">
            <div class="panelBox active" id="fore-2d3d-menu-zt-zttzs">
              <div class="panelBox-heading">
                <h3 class="panelBox-title">专题图展示</h3><span class="arrow arrowUp"></span>
              </div>
              <div  class="panelBox-body">
                <!---tree start-->
<%--                 <ul id="treeMapzt"  class="easyui-tree" data-options="url:'${res }/dist/js/map/data/mapLayerData.json',method:'get',animate:true,lines:true,checkbox:true"> --%>
                <ul id="treeMapzt"  class="ztree">
                </ul>
                 <!---tree end-->
              </div>
            </div>

          </div>
        </div>
        <div id="fore-2d3d-menu-bj" >
          <ol class="breadcrumb">
            <li><i class="icon iconfont home">&#xe640;</i></li>
            <li><a href="#">首页</a></li>
            <li><a href="#">-地图编辑</a></li>
          </ol>
          <div class="subTabs">
            <div class="panelBox active" id="fore-2d3d-menu-bj-dtbj">
              <div class="panelBox-heading"><i class="icon iconfont icon_map">&#xe622;</i>
                <h3 class="panelBox-title">地图编辑</h3><span class="arrow arrowUp"></span>
              </div>
              <div  class="panelBox-body">
              <form class="form-horizontal search-form" role="form">
                <div class="form-group form-group-sm">
                  <label for="name" class="col-sm-4">选择图层：</label>
                  <div class="col-sm-8 select-item">
                    <select class="form-control input-sm" id='editableLyrs'>
                    </select>
                  </div>
                </div>                                   
<!--                 <div class="searchBtn btn_list"> -->
<!--                   <button type="submit" class="btn btn-sm active">开始编辑</button> -->
<!--                   <button type="submit" class="btn btn-sm">保存编辑</button> -->
<!--                   <button type="submit" class="btn btn-sm">取消编辑</button> -->
<!--                 </div> -->
                <!-- 编辑工作区 -->
                <div class="editorRegion">
                  <div id="templateDiv"></div>
                  <div id="editorDiv"></div>
                </div>
              </form>
              </div>
            </div>


          </div>
        </div>
        <div id="fore-2d3d-menu-tc" >
          <!-- 当前位置 -->
          <ol class="breadcrumb">
            <li><i class="icon iconfont home">&#xe640;</i></li>
            <li><a href="#">首页</a></li>
            <li><a href="#">-图层管理</a></li>
          </ol>
          <!-- 当前操作 -->
          <div class="subTabs">
            <div class="panelBox active" id="fore-2d3d-menu-tc-tcgl">
              <div class="panelBox-heading"><i class="icon iconfont icon_map">&#xe6e0;</i>
                <h3 class="panelBox-title">图层管理</h3><span class="arrow arrowUp"></span>
              </div>
              <div  class="panelBox-body">
                <!---tree start-->
<!--                 <ul id="treeMaptcgl"  class="easyui-tree" data-options="url:'dist/js/map/data/mapLayerData.json',method:'get',animate:true,lines:true,checkbox:true"> -->
<!--                 <ul id="treeMaptcgl"  class="easyui-tree" data-options="animate:true,lines:true,checkbox:true"> -->
					<ul id="treeMaptcgl" class="ztree">
                </ul>
                <!---tree end-->
              </div>
            </div>

          </div>
        </div>
        <div id="fore-2d3d-menu-fx" >
          <!-- 当前位置 -->
          <ol class="breadcrumb">
            <li><i class="icon iconfont home">&#xe640;</i></li>
            <li><a href="#">首页</a></li>
            <li><a href="#">-空间分析</a></li>
          </ol>
          <!-- 二级菜单 -->
          <div class="panelBox">
            <div class="panelBox-heading" ><i class="icon iconfont icon_map">&#xe8b4;</i><h3 class="panelBox-title">空间分析</h3><span class="arrow iconfont"></span></div>
            <div  class="panelBox-body">
              <div class="row submenu">
                <a href="#fore-2d3d-menu-fx-djfx" class="col-sm-4 active" data-toggle="tab">
                  <i class="icon iconfont mapSearch">&#xe669;</i>
                  <h2>叠加分析</h2>
                </a>
                <a href="#fore-2d3d-menu-fx-hcqfx" class="col-sm-4" data-toggle="tab">
                  <i class="icon iconfont mapSearch">&#xe667;</i>
                  <h2>缓冲区分析</h2>
                </a>
                <a href="#fore-2d3d-menu-fx-3dfx" class="col-sm-4" data-toggle="tab">
                  <i class="icon iconfont mapSearch">&#xe664;</i>
                  <h2>三维分析</h2>
                </a>
              </div>
            </div>
          </div>          
          <!-- 当前操作 -->
          <div class="subTabs">
            <div class="panelBox active" id="fore-2d3d-menu-fx-djfx">
              <div class="panelBox-heading">
                <h3 class="panelBox-title">叠加分析</h3><span class="arrow arrowUp"></span>
              </div>
              <div  class="panelBox-body">
                <ul id="myTabPaintOption" class="nav nav-tabs">
                  <li class="active"><a href="#PaintOption" data-toggle="tab" aria-expanded="true">请选择绘制方式</a><i class="triangle-up"></i></li>
                </ul>
                <div id="myTabContentPlot" class="tab-content">                
                  <div class="tab-pane side-plot active" id="PaintOption">
                     <a href="javascript:;" class="active"><span class="icon iconfont">&#xe697;</span></a>
                     <a href="javascript:;"><span class="icon iconfont">&#xe696;</span></a>
                     <a href="javascript:;"><span class="icon iconfont">&#xe632;</span></a>      
                  </div>               
                </div>
<!--                 <div class="col-sm-8 select-item"> -->
<!--                     <select class="easyui-combobox" id="overlay" multiple="multiple"></select> -->
<!--                 </div> -->
                <form class="form-inline search-form" role="form">
<!--                 <div class="form-group form-group-sm"> -->
<!--                     <label for="name">导入GPS</label> -->
<!--                     <div class="filebox"><input type="file" name="file_0_ture" size="20" onchange="document.getElementById('file_0').value=this.value" class="filetext opacity "><input name="file_0" id="file_0" value="" class="form-control"> <button type="button"  class="btn btn_import">导 入</button></div> -->
<!--                 </div> -->
                </form> 
                <div style="display: none;" id='overlayPnl'></div>             
              </div>
            </div>
            <div class="panelBox" id="fore-2d3d-menu-fx-hcqfx">
              <div class="panelBox-heading">
                <h3 class="panelBox-title">缓冲区分析</h3><span class="arrow arrowUp"></span>
              </div>
              <div  class="panelBox-body">
                <ul id="myTabPaintOption" class="nav nav-tabs">
                  <li class="active"><a href="#PaintOption" data-toggle="tab" aria-expanded="true">请选择绘制方式</a><i class="triangle-up"></i></li>
                </ul>
                <div id="myTabContentPlot" class="tab-content">                
                  <div class="tab-pane side-plot active" id="PaintOptionBuf">
                     <a href="javascript:;" class="active"><span class="icon iconfont">&#xe64d;</span></a>
                     <a href="javascript:;"><span class="icon iconfont">&#xe64f;</span></a>
                     <a href="javascript:;"><span class="icon iconfont">&#xe632;</span></a>      
                  </div>               
                </div>
                <form class="form-horizontal search-form" role="form">
<!--                 <div class="form-group form-group-sm"> -->
<!--                     <label for="name" class="col-sm-5">导入GPS</label> -->
<!--                     <div class="filebox col-sm-7"><input type="file" name="file_1_ture" size="20" onchange="document.getElementById('file_1').value=this.value" class="filetext opacity "><input name="file_1" id="file_1" value="" class="form-control"> <button type="button"  class="btn file_btn">导 入</button></div> -->
<!--                 </div> -->
                <div class="form-group form-group-sm">
<!--                     <label for="name" class="col-sm-5">分析图层：</label> -->
<!--                     <div class="col-sm-7 select-item"> -->
<!--                       <select class="form-control input-sm"> -->
<!--                         <option>1</option> -->
<!--                         <option>2</option> -->
<!--                         <option>3</option> -->
<!--                       </select> -->
<!--                     </div> -->
                  </div>
                  <div class="form-group">
                    <label for="name" class="col-sm-5">缓冲距离(米)：</label>
                    <div class="col-sm-7">
                      <input type="text" class="form-control input-sm" id='bufDistance'>
                    </div>
                  </div>
<!--                   <div class="form-group form-group-sm"> -->
<!--                     <label for="name" class="col-sm-5">统计类似：</label> -->
<!--                     <div class="col-sm-7 select-item"> -->
<!--                       <select class="form-control input-sm"> -->
<!--                         <option>村</option> -->
<!--                         <option>2</option> -->
<!--                         <option>3</option> -->
<!--                       </select> -->
<!--                     </div> -->
<!--                   </div> -->
<!--                   <div class="searchBtn"> -->
<!--                     <button type="button" class="btn btn-success" id='bufAnalysis'>分析</button> -->
<!--                   </div> -->
                </form>              
              </div>
            </div>
            <div class="panelBox" id="fore-2d3d-menu-fx-3dfx">
              <div id="sideMenu">
                <div class="panelBox-heading">
                  <h3 class="panelBox-title">三维分析</h3><span class="arrow arrowUp"></span>
                </div>
                <div  class="panelBox-body">
                  <div class="row side-menu">
                    <a href="javascript:void(0)" class="col-sm-4 active" onclick="analysis3dSunlight()">
                      <i class="icon iconfont side-menu-icon">&#xe64b;</i>
                      <h2>光照分析</h2>
                    </a>
                    <a href="javascript:void(0)" class="col-sm-4" onclick="analysis3dFlood()">
                      <i class="icon iconfont side-menu-icon">&#xe60b;</i>
                      <h2>水淹分析</h2>
                    </a>
                    <a href="javascript:void(0)" class="col-sm-4" onclick="analysis3dLineOfSight()">
                      <i class="icon iconfont side-menu-icon">&#xe65d;</i>
                      <h2>视线分析</h2>
                    </a>
                    <a href="javascript:void(0)" class="col-sm-4" onclick="analysis3dViewShed()">
                      <i class="icon iconfont side-menu-icon">&#xe658;</i>
                      <h2>视域分析</h2>
                    </a>
                    <a href="javascript:void(0)" class="col-sm-4" id="forestFire">
                      <i class="icon iconfont side-menu-icon">&#xe694;</i>
                      <h2>林火蔓延分析</h2>
                    </a>
                  </div>
                </div>
              </div>
              <!-- 林火蔓延分析 start -->
              <div id="forestFirebox">
                <div class="panelBox-heading-disable">
                  <h3 class="panelBox-title">林火蔓延分析</h3><span class="arrow-back"></span>
                </div>          
                <div  class="panelBox-body">
                    <!-- 参数设置 start-->
                    <div class="block_title">
                      <div class="block_title_tags">
                        <a href="#" class="tag">参数设置</a>
                        <i class="tag_hr"></i>
                      </div>
                      <span class="rtime" style="display: none">剩余时间：<i class="red" id="fireRemainingTime"></i></span>
                    </div>
                    <div class="tag_conents clearfix">
                      <form class="conents-form" role="form">
                        <div class="form-group col-sm-6">
                          <label for="name" class="label col-sm-5">温度</label>
                          <input type="text" class="input-sm col-sm-7" value="27" id="fireWenDu">
                        </div>
                        <div class="form-group col-sm-6">
                          <label for="name" class="label col-sm-5">湿度</label>
                          <input type="text" class="input-sm col-sm-7" value="0.32" id="fireShiDu">
                        </div>
                        <div class="form-group col-sm-6">
                          <label for="name" class="label col-sm-5">风速</label>
                          <input type="text" class="input-sm col-sm-7" value="5" id="fireFengSu">
                        </div>
                        <div class="form-group col-sm-6">
                          <label for="name" class="label col-sm-5">风向</label>
                          <!-- <input type="text" class="input-sm col-sm-7" value="西南" id="fireFengXiang"> -->
                          <select name="" id="fireFengXiang" class="input-sm col-sm-7">
                            <option value="东">东</option>
                            <option value="南">南</option>
                            <option value="西">西</option>
                            <option value="北">北</option>
                            <option value="东北">东北</option>
                            <option value="东南">东南</option>
                            <option value="西北">西北</option>
                            <option value="西南" selected>西南</option>
                          </select>
                        </div>
                        <div class="form-group col-sm-6">
                          <label for="name" class="label col-sm-7">干旱码</label>
                          <input type="text" class="input-sm col-sm-5" value="0.5" id="fireGanHanMa">
                        </div>
                        <div class="form-group col-sm-6">
                          <label for="name" class="label col-sm-7">模拟时间</label>
                          <input type="text" class="input-sm col-sm-5" value="0.5" id="fireMNTime">
                        </div>
                      </form>
                    </div>
                    <!-- 参数设置 end-->
                    <!-- 蔓延速度 start-->
                    <div class="block_title">
                      <div class="block_title_tags">
                        <a href="#" class="tag">蔓延速度（米/秒）</a>
                        <i class="tag_hr"></i>
                      </div>
                    </div>
                    <div class="tag_conents clearfix">
                      <form class="conents-form" role="form">
                        <div class="form-group col-sm-6">
                          <label for="name" class="label col-sm-5">东</label>
                          <input type="text" class="input-sm col-sm-7" value="0.0" disabled id="fireEast">
                        </div>
                        <div class="form-group col-sm-6">
                          <label for="name" class="label col-sm-5">东北</label>
                          <input type="text" class="input-sm col-sm-7" value="0.0" disabled id="fireEastNorth">
                        </div>
                        <div class="form-group col-sm-6">
                          <label for="name" class="label col-sm-5">南</label>
                          <input type="text" class="input-sm col-sm-7" value="0.0" disabled id="fireSouth">
                        </div>
                        <div class="form-group col-sm-6">
                          <label for="name" class="label col-sm-5">东南</label>
                          <input type="text" class="input-sm col-sm-7" value="0.0" disabled id="fireEastSouth">
                        </div>
                        <div class="form-group col-sm-6">
                          <label for="name" class="label col-sm-5">西</label>
                          <input type="text" class="input-sm col-sm-7" value="0.0" disabled id="fireWest">
                        </div>
                        <div class="form-group col-sm-6">
                          <label for="name" class="label col-sm-5">西北</label>
                          <input type="text" class="input-sm col-sm-7" value="0.0" disabled id="fireWestNorth">
                        </div>
                        <div class="form-group col-sm-6">
                          <label for="name" class="label col-sm-5">北</label>
                          <input type="text" class="input-sm col-sm-7" value="0.0" disabled id="fireNorth">
                        </div>
                        <div class="form-group col-sm-6">
                          <label for="name" class="label col-sm-5">西南</label>
                          <input type="text" class="input-sm col-sm-7" value="0.0" disabled id="fireWestSouth">
                        </div>
                      </form>
                    </div>      
                    <!-- 蔓延速度 end-->
                    <!-- 模拟蔓延 start-->
                    <div class="block_title">
                      <div class="block_title_tags">
                        <a href="#" class="tag">模拟蔓延</a>
                        <i class="tag_hr"></i>
                      </div>
                    </div>
                    <div class="tag_conents clearfix">
                      <%--<form class="conents-form">--%>
                        <div class="form-group col-sm-12">                          
                          <input type="text" class="input-sm col-sm-6" value="" disabled id="firePosition">
                          <button for="name" class="col-sm-6" onclick="getFireClick()">拾取起火点位置</button>
                        </div>
                        <div class="btn-group col-sm-12" id="forestFireBtn">
                          <button type="button" class="btn btn-default col-sm-3" onclick="startSimulation()">开始模拟</button>
                          <button type="button" class="btn btn-default col-sm-3" onclick="pauseSimulation()">暂停模拟</button>
                          <button type="button" class="btn btn-default col-sm-3" onclick="stopSimulation()">停止模拟</button>
                          <button type="button" class="btn btn-default col-sm-3" onclick="getSimulationPic()">渲染结果</button>
                        </div>
                      <%--</form>--%>
                    </div>      
                    <!-- 模拟蔓延 end-->
                </div>
              </div>
              <!-- 林火蔓延分析 end -->
            </div>
          </div>
        </div>
        <div id="fore-2d3d-menu-my" >
          <!-- 当前位置 -->
          <ol class="breadcrumb">
            <li><i class="icon iconfont home">&#xe640;</i></li>
            <li><a href="#">首页</a></li>
            <li><a href="#">-三维漫游</a></li>
          </ol>
          <!-- 二级菜单 -->
          <div class="panelBox">
            <div class="panelBox-heading" ><i class="icon iconfont icon_map">&#xe7f6;</i><h3 class="panelBox-title">三维漫游</h3><span class="arrow iconfont"></span></div>
            <div  class="panelBox-body">
              <div class="row submenu">
                <a href="#fore-2d3d-menu-my-fxmy" class="col-sm-6 active" data-toggle="tab" onclick="getFlyPaths('tableFlyPathForRoam')">
                  <i class="icon iconfont mapSearch">&#xe630;</i>
                  <h2>飞行漫游</h2>
                </a>
                <a href="#fore-2d3d-menu-my-ljgl" class="col-sm-6" data-toggle="tab" onclick="getFlyPaths('tableFlyPathForEdit')">
                  <i class="icon iconfont mapSearch">&#xe647;</i>
                  <h2>路径管理</h2>
                </a>
              </div>
            </div>
          </div>          
          <!-- 当前操作 -->
          <div class="subTabs">
            <div class="panelBox active" id="fore-2d3d-menu-my-fxmy">
              <div class="panelBox-heading">
                <h3 class="panelBox-title">飞行漫游</h3><span class="arrow arrowUp"></span>
              </div>
              <div  class="panelBox-body">
                <form class="form-inline search-form" role="form" method="post">
                  <div class="input-group search-btn wd200">
                    <input type="text" class="form-control input-sm" placeholder="飞行漫游信息">
                    <span class="input-group-btn btn-group-sm">
                      <button class="btn btn-default icon iconfont" type="submit">&#xe644;</button>
                    </span>
                  </div>
                    <div class="form-group form-group-sm btn_list" id="pathtoolbar">
                      <button type="button" class="btn btn-default btn-sm active" onclick="beginFly()">
                        <span class="glyphicon glyphicon-play"></span> 飞行
                      </button>
                       <button type="button" class="btn btn-default btn-sm" onclick="pauseFly()">
                        <span class="glyphicon glyphicon-pause"></span> 暂停
                      </button>
                       <button type="button" class="btn btn-default btn-sm" onclick="stopFly()">
                        <span class="glyphicon glyphicon-stop"></span> 停止
                      </button>                                      
                    </div>

                    <table class="table  table-hover table-responsive search-form-table" id="tableFlyPathForRoam"
                     data-toggle="table" data-cache="false"
                     data-row-style="rowStyle" data-click-to-select="true">
                        <thead>
                          <tr>
                            <th data-field="state" data-radio="true"></th>
                            <th data-field="id">编号</th>
                            <th data-field="pathName">路径名称</th>
                            <th data-field="createTime" >创建时间</th>
                          </tr>
                        </thead>
                    </table>
                </form>              
              </div>
            </div>
            <div class="panelBox" id="fore-2d3d-menu-my-ljgl">
              <div class="panelBox-heading">
                <h3 class="panelBox-title">路径管理</h3><span class="arrow arrowUp"></span>
              </div>
              <div  class="panelBox-body">
                <div class="pathbox">
                  <form class="form-inline search-form" role="form" method="post">
                    <div class="input-group search-btn wd200">
                      <input type="text" class="form-control input-sm " placeholder="飞行漫游信息">
                      <span class="input-group-btn btn-group-sm">
                        <button class="btn btn-default icon iconfont" type="submit">&#xe644;</button>
                      </span>
                    </div>
                      <div class="form-group form-group-sm btn_list" id="pathtoolbar2">
                        <button type="button" class="btn btn-default btn-sm btn_add active" onclick="beginAddPath()">
                          <span class="glyphicon glyphicon-plus"></span> 新增
                        </button>
                         <button type="button" class="btn btn-default btn-sm btn_edit" onclick="beginEditPath()">
                          <span class="glyphicon glyphicon-pencil"></span> 修改
                        </button>
                         <button type="button" class="btn btn-default btn-sm btn_del" onclick="beginDeletePath()">
                          <span class="glyphicon glyphicon-minus"></span> 删除
                        </button>                                      
                      </div>
                    
                      <table class="table  table-hover table-responsive search-form-table" id="tableFlyPathForEdit"
                             data-toggle="table" data-cache="false"
                             data-row-style="rowStyle" data-click-to-select="true">
                        <thead>
                        <tr>
                          <th data-field="state" data-radio="true"></th>
                          <th data-field="id">编号</th>
                          <th data-field="pathName">路径名称</th>
                          <th data-field="createTime">创建时间</th>
                        </tr>
                        </thead>
                      </table>              
                  </form>
                </div>
                <!-- 新增路径 or 编辑路径-->
                <div class="pathbox-add" style="display:none">                  
                  <form action="" method="get" class="form-inline search-form" role="form">
                    <div class="form-group">
                      <label for="name" class="col-sm-4">路径名称：</label>
                      <div class="col-sm-8">
                        <input type="text" class="form-control input-sm" id="selectRoamPathName" oninput="roamPathEdit()">
                      </div>
                    </div>
                     <div class="form-group form-group-sm btn_list" id="pathtoolbar3">
                        <button type="button" class="btn btn-default btn-sm btn_add active" onclick="addRoamPathPoint()">
                          <span class="glyphicon glyphicon-plus"></span> 添加
                        </button>
                         <button type="button" class="btn btn-default btn-sm btn_edit" onclick="editRoamPathPoint()">
                          <span class="glyphicon glyphicon-pencil"></span> 修改
                        </button>
                        <button type="button" class="btn btn-default btn-sm btn_del" onclick="deleteRoamPathPint()">
                          <span class="glyphicon glyphicon-minus"></span> 删除
                        </button>                                    
                      </div>
                    
                      <table class="table  table-hover table-responsive search-form-table" id="tablePathPoint"
                             data-toggle="table" data-cache="false"  data-method="post"
                             data-row-style="rowStyle" data-click-to-select="true">
                          <thead>
                            <tr>
                              <th data-field="state" data-radio="true"></th>
                              <th data-field="pointIndex">编号</th>
                              <th data-field="pointName">路径点名</th>
                              <th data-field="stopTime">停留时间</th>
                              <th data-field="X" data-visible="false"></th>
                              <th data-field="Y" data-visible="false"></th>
                              <th data-field="Z" data-visible="false"></th>
                              <th data-field="Yaw" data-visible="false"></th>
                              <th data-field="Pitch" data-visible="false"></th>
                              <th data-field="Roll" data-visible="false"></th>
                            </tr>
                          </thead>
                      </table> 
                    <%--<div class="form-group">--%>
                      <%--<label for="name" class="col-sm-4">路径名称：</label>--%>
                      <%--<div class="col-sm-8">--%>
                        <%--<input type="text" class="form-control input-sm" >--%>
                      <%--</div>--%>
                    <%--</div>  --%>
                    <div class="form-group-sm btn_list text-right" style="margin-top: 20px">
                        <button id="roamPathNameEditSave" type="button" class="btn btn-default btn-sm btn_save active" onclick="addOrEditRoamPath()" disabled="true">
                          <span class="glyphicon glyphicon-saved"></span> 保存
                        </button>
                         <button type="button" class="btn btn-default btn-sm btn_cancel">
                          <span class="glyphicon glyphicon-remove"></span> 返回
                        </button>                                     
                    </div>
                                     
                  </form>
                </div>
                <!-- 新增路径 end-->               
              </div>

            </div>
          </div>
        </div>                                                                    
      </div>
      <!-- fore-core-side  end-->
    </div>
    <!-- left end -->
    <!--left hidden-->
    <div class="left_h">
    <!-- 下面一行为添加的标签 让标签浮在三维地图上-->
    <iframe frameborder= "0" scrolling="no" style="background-color:transparent; position: absolute; z-index: -1; width: 100%; height: 100%; top: 0; left:0;"></iframe>
      <div class="l_icon"></div>
    </div>
    <!-- content start -->
    <div class="mapcontent" id="mapContent">
      <!-- 地图工具条 start-->
      <div class="toolbar" id="measureDiv">
      	  <a href="javascript:;" id="btnIdentify"><i class="icon iconfont">&#xe692;</i><h3>查询</h3></a>
      	  <a href="javascript:;" id="printDiv" onclick="print()"><i class="icon iconfont">&#xe63f;</i><h3>打印</h3></a>
          <a href="javascript:;" title="全屏" id="fullScreenBtn"><i class="icon iconfont">&#xe643;</i><h3>全屏</h3></a>
          <a href="javascript:;" title="退出" id="noFullScreenBtn" style="display:none;" ><i class="icon iconfont">&#xe643;</i><h3>退出</h3></a>
          <a href="javascript:;" onclick="clearMap()"><i class="icon iconfont">&#xe646;</i><h3>清除</h3></a>
          <a href="javascript:;" onclick="pan();"><i class="icon iconfont">&#xe76d;</i><h3>平移</h3></a>
          <a href="javascript:;" onclick="zoomIn();"><i class="icon iconfont">&#xe624;</i><h3>放大</h3></a>
          <a href="javascript:;" onclick="zoomOut();"><i class="icon iconfont">&#xe625;</i><h3>缩小</h3></a>
          <a href="javascript:;" onclick="preView();"><i class="icon iconfont">&#xe62d;</i><h3>前一视图</h3></a>
          <a href="javascript:;" onclick="nextView();"><i class="icon iconfont">&#xe62c;</i><h3>后一视图</h3></a>
          <a href="javascript:;" onclick="measureDistance()" class="measure-distance"><i class="icon iconfont">&#xe641;</i><h3>距离</h3></a>
          <a href="javascript:;" onclick="measureArea()" class="measure-area"><i class="icon iconfont">&#xe642;</i><h3>面积</h3></a>
      </div>
      <!-- 地图图例 start-->
      <div id="js_legend" class="legendWrap">
       <iframe frameborder= "0" scrolling="no" style="background-color:transparent; position: absolute; z-index: -1; width: 100%; height: 100%; top: 0; left:0;"></iframe>
        
      <div class="legend-inner">

        <div class="legendBox" id="legendBox">
          <div class="legend_hd"><h2>图例</h2></div>
          	<div id="legendDiv" class="legendBody"></div>
<!--           <ul class="legend_list"> -->
<!--             <li><i class="legend_icon i_glj"></i><a href="#">管理局</a></li> -->
<!--             <li><i class="legend_icon i_glz"></i><a href="#">管护站</a></li> -->
<!--             <li><i class="legend_icon i_exsyd"></i><a href="#">鳄蜥饲养点</a></li> -->
<!--             <li><i class="legend_icon i_bjjzd"></i><a href="#">界碑界桩点</a></li> -->
<!--             <li><i class="legend_icon i_qxz"></i><a href="#">气象站</a></li> -->
<!--             <li><i class="legend_icon i_hwxj"></i><a href="#">红外相机</a></li> -->
<!--             <li><i class="legend_icon i_jd"></i><a href="#">景点</a></li> -->
<!--             <li><i class="legend_icon i_db"></i><a href="#">大坝</a></li> -->
<!--             <li><i class="legend_icon i_sf"></i><a href="#">山峰</a></li> -->
<!--             <li><i class="legend_icon i_cun"></i><a href="#">村</a></li> -->
<!--             <li><i class="legend_icon i_sdz"></i><a href="#">水电站</a></li> -->
<!--             <li><i class="legend_icon i_zwdcyd"></i><a href="#">植物调查样点</a></li> -->
<!--           </ul> -->
        </div>
        <button type="button" class="btn btn-default legendIcon"  data-placement="top" data-toggle="tooltip" title="图例"></button>
      </div>
      <!-- 地图图例 end-->
      <!-- 地图鹰眼 start-->
      <div class="esriOverviewMap" id="overviewDiv"></div>
<!--       <div  id="overviewDiv"></div> -->
      <!-- 地图鹰眼 end-->
      </div>
      <!-- 地图比例尺 start-->
      <div class="scalebar_bottom-left esriScalebar" id="scaleBar">        
      </div>
      <!-- 地图比例尺 end-->  
      <!-- 地图控制按钮 start-->
      <div class="map-operate">
        <div class="BMap_stdMpPan">
          <div class="BMap_button BMap_panN" title="向上平移" onclick="panUp();"></div>
          <div class="BMap_button BMap_panW" title="向左平移" onclick="panLeft();"></div>
          <div class="BMap_button BMap_panE" title="向右平移" onclick="panRight();"></div>
          <div class="BMap_button BMap_panS" title="向下平移" onclick="panDown();"></div>
          <div class="BMap_button BMap_panM" title="居中显示" onclick="panCenter();"></div>
        </div>
        <div class="BMap_stdMpZoom" >
            <div class="BMap_button BMap_stdMpPos" title="定位当前位置" onclick="locateCurPos();" ><input type="hidden" id="curPos"> </div>
            <div class="BMap_button BMap_stdMpZoomIn" title="放大一级" onclick="zoomInAuto();"></div>
            <div class="BMap_button BMap_stdMpZoomOut" title="缩小一级" onclick="zoomOutAuto();"></div>
          <div class="BMap_stdMpSlider">
            <div class="BMap_stdMpSliderBgTop"></div>
            <div class="BMap_stdMpSliderBgBot" ></div>           
            <div class="BMap_stdMpSliderBar" title="拖动缩放" style="cursor: url(&quot;http://webmap0.map.bdimg.com/image/api/openhand.cur&quot;) 0 0, default; "></div>
          </div>

        </div>
      </div>
      <!-- 地图控制按钮 end-->    
      <!-- 地图切换按钮 start-->
      <div class="tab-mapBtn">
            
          <div class="mapBtn" id="myTab">
            <!-- 地区选择 start-->
            <div class="btn-group sel-city">
            <!-- 下面一行为添加的标签 -->
              <iframe id="map3dSceneViewIframe" frameborder= "0" scrolling="no" style="background-color:transparent; position: absolute; z-index: -1; width: 100%; height: 100%; top: 0; left:0;"></iframe>
              <a href="#"  class="btn city-change-inner"> <span>广东省</span><em></em></a>
              <div class="city-popup-main city-dropdown-menu" id="city-popup-main">
              <iframe id="map3dSceneViewIframe" frameborder= "0" scrolling="no" style="background-color:transparent; position: absolute; z-index: -1; width: 100%; height: 100%; top: 0; left:0;"></iframe>
                <i class="city-popup-triangle-up"></i>
                <div class="city-title">全图范围：<span>广东省</span></div>
                <button class="city-pupup-close" title="关闭"></button>
                <ul class="city-list clearfix" id="city-listUI">
                               
                  <li class="shi clearfix">
                    <span class="label" lon="113.26143" lat="23.118912" zoom="9">广州市</span>
                    <ul class="shi-list">
                                        
                      <li class="" lon="113.588233" lat="23.549492" zoom="12">从化市</li>
                                        
                      <li class="" lon="113.838826" lat="23.292648" zoom="12">增城市</li>
                                        
                      <li class="" lon="113.502856" lat="22.828951" zoom="12">南沙区</li>
                                        
                      <li class="" lon="113.212453" lat="23.375889" zoom="12">花都区</li>
                                        
                      <li class="" lon="113.366353" lat="22.934621" zoom="12">番禺区</li>
                                        
                      <li class="" lon="113.446185" lat="23.102284" zoom="12">黄埔区</li>
                                        
                      <li class="" lon="113.278184" lat="23.155847" zoom="12">白云区</li>
                                        
                      <li class="" lon="113.31778" lat="23.131245" zoom="12">天河区</li>
                                        
                      <li class="" lon="113.263297" lat="23.105591" zoom="12">海珠区</li>
                                        
                      <li class="" lon="113.269808" lat="23.12977" zoom="12">越秀区</li>
                                        
                      <li class="" lon="113.245079" lat="23.126476" zoom="12">荔湾区</li>
                                        
                    </ul>
                  </li>
                               
                  <li class="shi clearfix">
                    <span class="label" lon="114.118705" lat="22.554205" zoom="9">深圳市</span>
                    <ul class="shi-list">
                                        
                      <li class="" lon="114.276959" lat="22.586221" zoom="12">盐田区</li>
                                        
                      <li class="" lon="114.279119" lat="22.733331" zoom="12">龙岗区</li>
                                        
                      <li class="" lon="113.8915" lat="22.569477" zoom="12">宝安区</li>
                                        
                      <li class="" lon="113.917055" lat="22.535885" zoom="12">南山区</li>
                                        
                      <li class="" lon="114.056619" lat="22.516535" zoom="12">福田区</li>
                                        
                      <li class="" lon="114.123225" lat="22.538097" zoom="12">罗湖区</li>
                                        
                    </ul>
                  </li>
                               
                  <li class="shi clearfix">
                    <span class="label" lon="113.576113" lat="22.279419" zoom="9">珠海市</span>
                    <ul class="shi-list">
                                        
                      <li class="" lon="113.349779" lat="22.049713" zoom="12">金湾区</li>
                                        
                      <li class="" lon="113.294074" lat="22.210261" zoom="12">斗门区</li>
                                        
                      <li class="" lon="113.578735" lat="22.278235" zoom="12">香洲区</li>
                                        
                    </ul>
                  </li>
                               
                  <li class="shi clearfix">
                    <span class="label" lon="116.593337" lat="23.462295" zoom="9">汕头市</span>
                    <ul class="shi-list">
                                        
                      <li class="" lon="117.029085" lat="23.423069" zoom="12">南澳县</li>
                                        
                      <li class="" lon="116.772879" lat="23.462295" zoom="12">澄海区</li>
                                        
                      <li class="" lon="116.593337" lat="23.262126" zoom="12">潮阳区</li>
                                        
                      <li class="" lon="116.705891" lat="23.363654" zoom="12">金平区</li>
                                        
                      <li class="" lon="116.720439" lat="23.37861" zoom="12">龙湖区</li>
                                        
                    </ul>
                  </li>
                               
                  <li class="shi clearfix">
                    <span class="label" lon="113.109095" lat="23.013873" zoom="9">佛山市</span>
                    <ul class="shi-list">
                                        
                      <li class="" lon="112.889457" lat="22.898637" zoom="12">高明区</li>
                                        
                      <li class="" lon="112.899079" lat="23.166755" zoom="12">三水区</li>
                                        
                      <li class="" lon="113.253168" lat="22.836775" zoom="12">顺德区</li>
                                        
                      <li class="" lon="113.142709" lat="23.032857" zoom="12">南海区</li>
                                        
                      <li class="" lon="113.128735" lat="23.004918" zoom="12">禅城区</li>
                                        
                    </ul>
                  </li>
                               
                  <li class="shi clearfix">
                    <span class="label" lon="113.601289" lat="24.812147" zoom="9">韶关市</span>
                    <ul class="shi-list">
                                        
                      <li class="" lon="114.308309" lat="25.117305" zoom="12">南雄市</li>
                                        
                      <li class="" lon="113.35687" lat="25.133896" zoom="12">乐昌市</li>
                                        
                      <li class="" lon="114.207573" lat="24.057387" zoom="12">新丰县</li>
                                        
                      <li class="" lon="113.277449" lat="24.777755" zoom="12">乳源瑶族自治县</li>
                                        
                      <li class="" lon="114.134031" lat="24.348015" zoom="12">翁源县</li>
                                        
                      <li class="" lon="113.747433" lat="25.08712" zoom="12">仁化县</li>
                                        
                      <li class="" lon="114.067287" lat="24.946125" zoom="12">始兴县</li>
                                        
                      <li class="" lon="113.601149" lat="24.678409" zoom="12">曲江区</li>
                                        
                      <li class="" lon="113.601292" lat="24.812147" zoom="12">浈江区</li>
                                        
                      <li class="" lon="113.587864" lat="24.797493" zoom="12">武江区</li>
                                        
                    </ul>
                  </li>
                               
                  <li class="shi clearfix">
                    <span class="label" lon="113.081487" lat="22.578699" zoom="9">江门市</span>
                    <ul class="shi-list">
                                        
                      <li class="" lon="112.309843" lat="22.183013" zoom="12">恩平市</li>
                                        
                      <li class="" lon="112.963464" lat="22.765529" zoom="12">鹤山市</li>
                                        
                      <li class="" lon="112.697745" lat="22.377145" zoom="12">开平市</li>
                                        
                      <li class="" lon="112.793449" lat="22.251073" zoom="12">台山市</li>
                                        
                      <li class="" lon="113.034161" lat="22.458477" zoom="12">新会区</li>
                                        
                      <li class="" lon="113.110741" lat="22.561583" zoom="12">江海区</li>
                                        
                      <li class="" lon="113.077789" lat="22.599969" zoom="12">蓬江区</li>
                                        
                    </ul>
                  </li>
                               
                  <li class="shi clearfix">
                    <span class="label" lon="110.345255" lat="21.272326" zoom="9">湛江市</span>
                    <ul class="shi-list">
                                        
                      <li class="" lon="110.777011" lat="21.434391" zoom="12">吴川市</li>
                                        
                      <li class="" lon="110.100341" lat="20.913715" zoom="12">雷州市</li>
                                        
                      <li class="" lon="110.284939" lat="21.606739" zoom="12">廉江市</li>
                                        
                      <li class="" lon="110.173167" lat="20.327675" zoom="12">徐闻县</li>
                                        
                      <li class="" lon="110.259499" lat="21.375789" zoom="12">遂溪县</li>
                                        
                      <li class="" lon="110.324667" lat="21.268655" zoom="12">麻章区</li>
                                        
                      <li class="" lon="110.453329" lat="21.245654" zoom="12">坡头区</li>
                                        
                      <li class="" lon="110.410609" lat="21.197026" zoom="12">霞山区</li>
                                        
                      <li class="" lon="110.358811" lat="21.270621" zoom="12">赤坎区</li>
                                        
                    </ul>
                  </li>
                               
                  <li class="shi clearfix">
                    <span class="label" lon="110.901095" lat="21.667469" zoom="9">茂名市</span>
                    <ul class="shi-list">
                                        
                      <li class="" lon="110.945963" lat="22.353015" zoom="12">信宜市</li>
                                        
                      <li class="" lon="110.642107" lat="21.655318" zoom="12">化州市</li>
                                        
                      <li class="" lon="110.854201" lat="21.915287" zoom="12">高州市</li>
                                        
                      <li class="" lon="111.008164" lat="21.504338" zoom="12">电白县</li>
                                        
                      <li class="" lon="111.03134" lat="21.476827" zoom="12">茂港区</li>
                                        
                      <li class="" lon="110.926799" lat="21.648921" zoom="12">茂南区</li>
                                        
                    </ul>
                  </li>
                               
                  <li class="shi clearfix">
                    <span class="label" lon="112.465019" lat="23.045525" zoom="9">肇庆市</span>
                    <ul class="shi-list">
                                        
                      <li class="" lon="112.705561" lat="23.34482" zoom="12">四会市</li>
                                        
                      <li class="" lon="112.457173" lat="23.024434" zoom="12">高要市</li>
                                        
                      <li class="" lon="111.786354" lat="23.145372" zoom="12">德庆县</li>
                                        
                      <li class="" lon="111.502805" lat="23.435211" zoom="12">封开县</li>
                                        
                      <li class="" lon="112.184895" lat="23.911875" zoom="12">怀集县</li>
                                        
                      <li class="" lon="112.440115" lat="23.634113" zoom="12">广宁县</li>
                                        
                      <li class="" lon="112.567117" lat="23.159258" zoom="12">鼎湖区</li>
                                        
                      <li class="" lon="112.468687" lat="23.044472" zoom="12">端州区</li>
                                        
                    </ul>
                  </li>
                               
                  <li class="shi clearfix">
                    <span class="label" lon="114.415191" lat="23.112429" zoom="9">惠州市</span>
                    <ul class="shi-list">
                                        
                      <li class="" lon="114.254703" lat="23.727807" zoom="12">龙门县</li>
                                        
                      <li class="" lon="114.720057" lat="22.983493" zoom="12">惠东县</li>
                                        
                      <li class="" lon="114.281755" lat="23.161034" zoom="12">博罗县</li>
                                        
                      <li class="" lon="114.471985" lat="22.801221" zoom="12">惠阳区</li>
                                        
                      <li class="" lon="114.382681" lat="23.080097" zoom="12">惠城区</li>
                                        
                    </ul>
                  </li>
                               
                  <li class="shi clearfix">
                    <span class="label" lon="116.122755" lat="24.302015" zoom="9">梅州市</span>
                    <ul class="shi-list">
                                        
                      <li class="" lon="116.090211" lat="24.289897" zoom="12">梅县</li>
                                        
                      <li class="" lon="116.124033" lat="24.309781" zoom="12">梅江区</li>
                                        
                      <li class="" lon="115.730914" lat="24.139759" zoom="12">兴宁市</li>
                                        
                      <li class="" lon="116.170507" lat="24.657077" zoom="12">蕉岭县</li>
                                        
                      <li class="" lon="115.896474" lat="24.570221" zoom="12">平远县</li>
                                        
                      <li class="" lon="115.778562" lat="23.923691" zoom="12">五华县</li>
                                        
                      <li class="" lon="116.181587" lat="23.756575" zoom="12">丰顺县</li>
                                        
                      <li class="" lon="116.696281" lat="24.352805" zoom="12">大埔县</li>
                                        
                    </ul>
                  </li>
                               
                  <li class="shi clearfix">
                    <span class="label" lon="115.364066" lat="22.768743" zoom="9">汕尾市</span>
                    <ul class="shi-list">
                                        
                      <li class="" lon="115.65175" lat="22.944121" zoom="12">陆丰市</li>
                                        
                      <li class="" lon="115.654252" lat="23.302943" zoom="12">陆河县</li>
                                        
                      <li class="" lon="115.335955" lat="22.970711" zoom="12">海丰县</li>
                                        
                    </ul>
                  </li>
                               
                  <li class="shi clearfix">
                    <span class="label" lon="114.699573" lat="23.745272" zoom="9">河源市</span>
                    <ul class="shi-list">
                                        
                      <li class="" lon="114.746163" lat="23.789015" zoom="12">东源县</li>
                                        
                      <li class="" lon="114.935981" lat="24.443091" zoom="12">和平县</li>
                                        
                      <li class="" lon="114.489885" lat="24.371109" zoom="12">连平县</li>
                                        
                      <li class="" lon="115.259826" lat="24.100973" zoom="12">龙川县</li>
                                        
                      <li class="" lon="115.184513" lat="23.639327" zoom="12">紫金县</li>
                                        
                      <li class="" lon="114.702405" lat="23.734254" zoom="12">源城区</li>
                                        
                    </ul>
                  </li>
                               
                  <li class="shi clearfix">
                    <span class="label" lon="111.959075" lat="21.851238" zoom="9">阳江市</span>
                    <ul class="shi-list">
                                        
                      <li class="" lon="111.788003" lat="22.171847" zoom="12">阳春市</li>
                                        
                      <li class="" lon="112.018447" lat="21.872147" zoom="12">阳东县</li>
                                        
                      <li class="" lon="111.620222" lat="21.753396" zoom="12">阳西县</li>
                                        
                      <li class="" lon="111.963555" lat="21.851497" zoom="12">江城区</li>
                                        
                    </ul>
                  </li>
                               
                  <li class="shi clearfix">
                    <span class="label" lon="113.046599" lat="23.690937" zoom="9">清远市</span>
                    <ul class="shi-list">
                                        
                      <li class="" lon="112.381087" lat="24.778289" zoom="12">连州市</li>
                                        
                      <li class="" lon="113.404543" lat="24.186053" zoom="12">英德市</li>
                                        
                      <li class="" lon="113.017364" lat="23.730049" zoom="12">清新县</li>
                                        
                      <li class="" lon="112.293917" lat="24.720857" zoom="12">连南瑶族自治县</li>
                                        
                      <li class="" lon="112.083063" lat="24.570229" zoom="12">连山壮族瑶族自治县</li>
                                        
                      <li class="" lon="112.643689" lat="24.476067" zoom="12">阳山县</li>
                                        
                      <li class="" lon="113.531541" lat="23.877219" zoom="12">佛冈县</li>
                                        
                      <li class="" lon="113.030891" lat="23.706165" zoom="12">清城区</li>
                                        
                    </ul>
                  </li>
                               
                  <li class="shi clearfix">
                    <span class="label" lon="113.750554" lat="23.052587" zoom="9">东莞市</span>
                    <ul class="shi-list">
                                        
                    </ul>
                  </li>
                               
                  <li class="shi clearfix">
                    <span class="label" lon="113.37226" lat="22.520437" zoom="9">中山市</span>
                    <ul class="shi-list">
                                        
                    </ul>
                  </li>
                               
                  <li class="shi clearfix">
                    <span class="label" lon="116.647103" lat="23.668237" zoom="9">潮州市</span>
                    <ul class="shi-list">
                                        
                      <li class="" lon="117.007493" lat="23.672778" zoom="12">饶平县</li>
                                        
                      <li class="" lon="116.687975" lat="23.444563" zoom="12">潮安县</li>
                                        
                      <li class="" lon="116.652313" lat="23.669153" zoom="12">湘桥区</li>
                                        
                    </ul>
                  </li>
                               
                  <li class="shi clearfix">
                    <span class="label" lon="116.358693" lat="23.535991" zoom="9">揭阳市</span>
                    <ul class="shi-list">
                                        
                      <li class="" lon="116.425779" lat="23.56567" zoom="12">揭东区</li>
                                        
                      <li class="" lon="116.351131" lat="23.538571" zoom="12">榕城区</li>
                                        
                      <li class="" lon="116.178179" lat="23.29433" zoom="12">普宁市</li>
                                        
                      <li class="" lon="116.299759" lat="23.032404" zoom="12">惠来县</li>
                                        
                      <li class="" lon="115.838463" lat="23.426669" zoom="12">揭西县</li>
                                        
                    </ul>
                  </li>
                               
                  <li class="shi clearfix">
                    <span class="label" lon="112.044659" lat="22.914745" zoom="9">云浮市</span>
                    <ul class="shi-list">
                                        
                      <li class="" lon="111.568668" lat="22.769177" zoom="12">罗定市</li>
                                        
                      <li class="" lon="112.006235" lat="23.069579" zoom="12">云安县</li>
                                        
                      <li class="" lon="111.533336" lat="23.232562" zoom="12">郁南县</li>
                                        
                      <li class="" lon="112.224443" lat="22.695999" zoom="12">新兴县</li>
                                        
                      <li class="" lon="112.042547" lat="22.928181" zoom="12">云城区</li>
                                        
                    </ul>
                  </li>
                               
                </ul>

              </div>
            </div>
            <!-- 地区选择 end-->

            <div class="btn-group mapView">
            <iframe id="map3dSceneViewIframe" frameborder= "0" scrolling="no" style="background-color:transparent; position: absolute; z-index: -1; width: 100%; height: 100%; top: 0; left:0;"></iframe>
              <button type="button" class="btn btn-default mapView-btn active" onclick="to2dMap();" id="mapView-btn-2dmap">地图</button>
              <button type="button" class="btn btn-default mapView-btn" onclick="to2dImgMap();" id="mapView-btn-2dwx">卫星</button>
              <button type="button" class="btn btn-default mapView-btn" id="mapView-btn-3dmy" onclick="toggleTo3d()">三维</button>
              <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" style="height:34px;">
                <span class="caret"></span>
                <span class="sr-only">切换下拉菜单</span>
              </button>
              <ul class="dropdown-menu mapBtn-sel" role="menu">
                <!-- 下面一行为添加的标签 -->
                <iframe frameborder= "0" scrolling="no" style="background-color:transparent; position: absolute; z-index: -1; width: 100%; height: 100%; top: 0; left:0;"></iframe>
                <li><a href="#"><label> <input type="checkbox" name="2d3dcheckbox" id="2d3dcheckbox">二三维联动</label></a></li>
              </ul>
            </div>

        </div>
      </div>
      <!-- 地图切换按钮 end-->

      <!-- tab-content start-->
      <div class="tab-mapCon tab-content" id="resizable">
        <div class="tab-mapCon-pane mapCon-resizable resizable-left active" id="map2d" style="width:100%;"></div>
        <div class="handler" ></div>
        <div class="tab-mapCon-pane mapCon-resizable resizable-right" id="map3d"   style="width:100%;">
            <%--<object style="position: absolute;width: 100px;height: 500px" ID="YcMap3DInformationWindow" classid="CLSID:3a4f9193-65a8-11d5-85c1-0001023952c1"></object>--%>
        	<!--定义一个TerraExplorer 3D窗口对象-->
			<object ID="YcMap3DWindow" classid="CLSID:3a4f9192-65a8-11d5-85c1-0001023952c1" style="width:100%;height:100%"></object>
			<!--定义TerraExplorer对象-->
			<object ID="YcMap3D" classid="CLSID:3A4F9199-65A8-11D5-85C1-0001023952C1" style="display: none;"></object>
        </div>
      </div>
      <!-- tab-content end-->
    </div>
    <!-- content end -->
  </div>
</div>
<!-- main end -->
<div class="select-skin">
     <!-- Control Sidebar 换肤弹窗-->
    <aside class="control-sidebar control-sidebar-dark" style="display:none;">
    <iframe frameborder= "0" scrolling="no" style="background-color:transparent; position: absolute; z-index: -1; width: 100%; height: 100%; top: 0; left:0;"></iframe>
      <div class="tab-content">
        <div class="tab-pane" id="control-sidebar-home-tab"></div>
      </div>
    </aside>       
  </div>

  
  <div id="dialog" style="position:absolute;display:none;right:100px;top:200px;z-index:100 ;width:500px;height:600px; ">
		<table id="grid"></table>
  </div>
	
</div>
<!-- wrapper end -->

<!-- 换肤 -->
<script src="${res}/dist/js/map/mapSkinSelect.js"></script>
</body>
</html>