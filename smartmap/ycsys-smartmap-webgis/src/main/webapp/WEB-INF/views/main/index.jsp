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
  <script src="${res}/plugins/jQuery/jquery-2.2.3.min.js"></script>
<!--   <script src="${res}/plugins/jQueryUI/jquery-ui.min.js"></script>  -->
  <script src="${res}/plugins/mCustomScrollbar/jquery.mousewheel.js"></script>
  
  <link rel="stylesheet" href="${res}/bootstrap/css/bootstrap.css">
  <script src="${res}/bootstrap/js/bootstrap.min.js"></script>
   <!-- HTML5 IE8 support of HTML5 elements and media queries -->
  <!--[if lt IE 9]>
  <script src="${res}dist/js/html5shiv.min.js"></script>
   <script src="${res}dist/js/respond.min.js"></script>
  <![endif]-->
    <!-- jQuery Easyui js-->
  <script src="${res}/plugins/jeasyui/jquery.easyui.min.js"></script>
  <!-- jQuery 修改浏览器默认滚动条 -->
  <script src="${res}/plugins/mCustomScrollbar/jquery.mCustomScrollbar.concat.min.js"></script>
  <!-- index js  -->
  <script src="${res}/dist/js/map/map_index.js"></script>
  <script src="${res}/dist/js/map/map_index_dialog.js"></script>
  <!--jQuery pagination js  分页 -->
  <script src="${res}/dist/js/map/jquery.pagination.js"></script>
  <!-- 颜色选择 -->
  <!--
 <script src="${res}/plugins/jqColorPicker/colors.js"></script>
 <script src="${res}/plugins/jqColorPicker/jqColorPicker.js"></script>
  <script type="text/javascript">
      $('.color').colorPicker(); 
  </script> -->

  <!-- jQuery 地图鱼骨控件滑动效果 -->
  <script  src="${res}/dist/js/map/scrollBar.js"></script>

   <!--ztree-->
  <link rel="stylesheet" href="${res}/plugins/ztree/style/zTreeStyle.css" >
  <script  src="${res}/plugins/ztree/js/jquery.ztree.core.js"></script>
  <script  src="${res}/plugins/ztree/js/jquery.ztree.excheck.js"></script>

  <script  src="${res}/js/common/layerManager.js"></script>
<%--   <script  src="${res}/dist/js/map/treeMapzt.js"></script> --%>
  
  <!-- 二三维地图工具栏基础功能 -->
  <script  src="${res}/js/common/common.js"></script>
  <script  src="${res}/js/common/locate.js"></script>
  <script  src="${res}/js/common/query.js"></script>
  <!-- 三维地图飞行漫游功能模块 -->
  <script  src="${res}/js/common/flightRoaming.js"></script>
  <!-- 表格 -->
<script src="${res}/plugins/table/bootstrap-table.min.js"></script>
<script src="${res}/plugins/table/bootstrap-table-zh-CN.min.js"></script>
<!-- 封装弹出框dialog -->
<script src="${res}/plugins/dialog/jquery.artDialog.source.js"></script>
<script src="${res}/plugins/dialog/iframeTools.source.js"></script>
<script src="${res}/plugins/dialog/unit.js"></script>
</head>
<body role="document" cz-shortcut-listen="true" class="hold-transition  sidebar-mini skin-cyan">
<!-- wrapper start -->
<div class="wrapper" > 
  <!-- header start -->
  <nav class="header navbar navbar-static-top" role="navigation">
        <div class="navbar-inner clearfix">
          <a class="navbar-brand logo" href="#"><img src="${res}/dist/img/map/logo.png" alt="logo" /></a>
        
          <a href="javascript:;" class="menu-toggler collapsed" ><i class="icon iconfont">&#xe65a;</i></a>  
          <!-- user -->
          <div id="navbar" class="float_r">
            <ul class="navbar-user">
              <li class="user dropdown">             
                <a href="#" class="user_box dropdown-toggle" data-toggle="dropdown" id="dLabel"><img src="${res}/dist/img/map/photo1.png" alt="头像" /><span class="user_name">admin</span> <span class="caret"></span></a>
                <ul class="dropdown-menu memu-list"  role="menu" aria-labelledby="dLabel">
                  <!-- 下面一行为添加的标签 -->
                  <iframe frameborder= "0" scrolling="no" style="background-color:transparent; position: absolute; z-index: -1; width: 100%; height: 100%; top: 0; left:0;"></iframe>               
                  <li><a href="#"><i class="icon iconfont">&#xe60e;</i><span>个人信息</span></a></li>
<!--                   <li><a href="#"><i class="icon iconfont">&#xe614;</i><span>修改资料</span></a></li> -->
                  <li><a href="#"><i class="icon iconfont">&#xe615;</i><span>修改密码</span></a></li>
                  <li><a href="#"><i class="icon iconfont">&#xe650;</i><span>退出系统</span></a></li>               
                </ul>                         
              </li>
              <li class="user_info dropdown" ><a href="javascript:;" title="消息" class="icon-msg dropdown-toggle"  data-toggle="dropdown"><i class="icon iconfont">&#xe651;</i><span class="icon-msg-count">2</span></a>
                <!-- messages start-->
                <ul class="dropdown-menu msg-box">
                  <!-- 下面一行为添加的标签 -->
                  <iframe frameborder= "0" scrolling="no" style="background-color:transparent; position: absolute; z-index: -1; width: 100%; height: 100%; top: 0; left:0;"></iframe>               
                  <li class="msg-box-hd">你有<span class="orange">4个</span>消息待查看<a href="#">全部+</a></li>
                  <li>
                    <ul class="msg-box-bd">
                      <li>
                        <a href="javascript:;">
                          <div class="pull-left">
                            <i class="iconfont icon">&#xe6a4;</i>
                          </div>
                          <h4>
                            支持团队
                          </h4>
                          <p>Why not buy a new awesome theme?</p>
                        </a>
                      </li>

                      <li>
                        <a href="#">
                          <div class="pull-left">
                            <i class="iconfont icon">&#xe6a4;</i>
                          </div>
                          <h4>
                            AdminLTE 设计团队
                          </h4>
                          <p>Why not buy a new awesome theme?</p>
                        </a>
                      </li>
                      <li>
                        <a href="#">
                          <div class="pull-left">
                            <i class="iconfont icon">&#xe6a4;</i>
                          </div>
                          <h4>
                            开发商
                          </h4>
                          <p>Why not buy a new awesome theme?</p>
                        </a>
                      </li>
                      <li>
                        <a href="#">
                          <div class="pull-left">
                            <i class="iconfont icon">&#xe6a4;</i>
                          </div>
                          <h4>
                           市场部
                          </h4>
                          <p>Why not buy a new awesome theme?</p>
                        </a>
                      </li>
                      <li>
                        <a href="#">
                          <div class="pull-left">
                            <i class="iconfont icon">&#xe6a4;</i>
                          </div>
                          <h4>
                          审稿人
                          </h4>
                          <p>Why not buy a new awesome theme?</p>
                        </a>
                      </li>
                    </ul>
                  </li>                
                </ul>
                <!-- messages end-->
              </li>
              <li class="user_info pr10 "><a href="javascript:;" title="换肤"  data-toggle="control-sidebar"><i class="icon iconfont">&#xe64c;</i></a></li> 
            </ul>
          </div>
          <!-- bigmenu -->
          <div id="navbar-collapse" class="navbar-collapse collapse" >
            <ul class="nav navbar-nav bigmenu" id="idTabs">
              <li class="active"><a href="javaScript:;"><i class="icon iconfont">&#xe893;</i><h1>地图查询</h1></a></li>
              <li><a href="javaScript:;"><i class="icon iconfont">&#xe600;</i><h1>地图定位</h1></a></li>
              <li><a href="javaScript:;"><i class="icon iconfont">&#xe601;</i><h1>地图标注</h1></a></li>
              <li><a href="javaScript:;"><i class="icon iconfont">&#xe60a;</i><h1>专题地图</h1></a></li>
              <li><a href="javaScript:;"><i class="icon iconfont">&#xe622;</i><h1>地图编辑</h1></a></li>
              <li><a href="javaScript:;"><i class="icon iconfont">&#xe6e0;</i><h1>图层管理</h1></a></li>
              <li><a href="javaScript:;"><i class="icon iconfont">&#xe8b4;</i><h1>空间分析</h1></a></li>
              <li onclick="to3dMap()"><a href="javaScript:;" id="3dmy"><i class="icon iconfont">&#xe7f6;</i><h1>三维漫游</h1></a></li>
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
                  <form class="form-horizontal search-form" role="form">
                    <div class="form-group form-group-sm">
                      <label for="name" class="col-sm-4">查询图层：</label>
                      <div class="col-sm-8">
  <%--                       <select class="form-control input-sm easyui-combotree " data-options="url:'${res }/dist/js/map/data/mapLayerData.json',method:'get'" name="mapLayer"></select> --%>
                        <select class="form-control input-sm" id="queryLyrLst" onchange="listFields();"></select>
                      </div>
                    </div>
                    <div class="form-group form-group-sm">                    
                      <label for="name" class="col-sm-4">查询属性：</label>
                      <div class="col-sm-8">
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
                      <button type="button" class="btn btn-success btn_add" >查 询</button>
<!--                       <button type="button" class="btn btn-success btn_add" onclick="queryAttr();">查 询</button> -->
                      <button type="reset" class="btn btn-warning">重 置</button>
                    </div>
                  </form>
                </div>
                <!-- 查询结果 start-->
                <div id="Sxcxbox-result" style="display:none">                  
                  <form action="" method="get" class="form-inline search-form" role="form">                   
                    <div class="Sxcxlist">
                      <p>共有<span class="red">8</span>条结果<a href="#" class="btn_back">返回<i class="arrow-back"></i></a></p>
                      <!-- 第一页内容 -->
                      <div id="Searchresult">
                        <ul class="result">
                          <li><i class="no-1"></i><a>锦江之星酒店</a><span>广州市越秀区解放北路777号</span></li>
                          <li><i class="no-2"></i><a>锦江之星酒店</a><span>广州市越秀区解放北路777号</span></li>
                          <li><i class="no-3"></i><a>锦江之星酒店</a><span>广州市越秀区解放北路777号</span></li>
                          <li><i class="no-4"></i><a>锦江之星酒店</a><span>广州市越秀区解放北路777号</span></li>
                          <li><i class="no-5"></i><a>锦江之星酒店</a><span>广州市越秀区解放北路777号</span></li>
                        </ul>
                      </div>
                      <!-- 下一页内容 -->
                      <div id="hiddenresult" style="display:none">
                        <ul class="result">
                          <li><i class="no-1"></i><a>锦江之星酒店</a><span>广州市越秀区解放北路777号</span></li>
                          <li><i class="no-2"></i><a>锦江之星酒店</a><span>广州市越秀区解放北路777号</span></li>
                          <li><i class="no-3"></i><a>锦江之星酒店</a><span>广州市越秀区解放北路777号</span></li>
                          <li><i class="no-4"></i><a>锦江之星酒店</a><span>广州市越秀区解放北路777号</span></li>
                          <li><i class="no-5"></i><a>锦江之星酒店</a><span>广州市越秀区解放北路777号</span></li>
                        </ul>                      
                        <ul class="result">
                          <li><i class="no-1"></i><a>锦江之星酒店</a><span>广州市越秀区解放北路777号</span></li>
                          <li><i class="no-2"></i><a>锦江之星酒店</a><span>广州市越秀区解放北路777号</span></li>
                          <li><i class="no-3"></i><a>锦江之星酒店</a><span>广州市越秀区解放北路777号</span></li>
                          <li><i class="no-4"></i><a>锦江之星酒店</a><span>广州市越秀区解放北路777号</span></li>
                          <li><i class="no-5"></i><a>锦江之星酒店</a><span>广州市越秀区解放北路777号</span></li>
                        </ul>
                        <ul class="result">
                          <li><i class="no-1"></i><a>锦江之星酒店</a><span>广州市越秀区解放北路777号</span></li>
                          <li><i class="no-2"></i><a>锦江之星酒店</a><span>广州市越秀区解放北路777号</span></li>
                          <li><i class="no-3"></i><a>锦江之星酒店</a><span>广州市越秀区解放北路777号</span></li>
                          <li><i class="no-4"></i><a>锦江之星酒店</a><span>广州市越秀区解放北路777号</span></li>
                          <li><i class="no-5"></i><a>锦江之星酒店</a><span>广州市越秀区解放北路777号</span></li>
                        </ul>
                        <ul class="result">
                          <li><i class="no-1"></i><a>锦江之星酒店</a><span>广州市越秀区解放北路777号</span></li>
                          <li><i class="no-2"></i><a>锦江之星酒店</a><span>广州市越秀区解放北路777号</span></li>
                          <li><i class="no-3"></i><a>锦江之星酒店</a><span>广州市越秀区解放北路777号</span></li>
                          <li><i class="no-4"></i><a>锦江之星酒店</a><span>广州市越秀区解放北路777号</span></li>
                          <li><i class="no-5"></i><a>锦江之星酒店</a><span>广州市越秀区解放北路777号</span></li>
                        </ul> 
                      </div> 
                      <div id="Pagination" class="pagination"><span class="current prev"></span><span class="current">1</span><a href="#">2</a><span>...</span><a href="#">4</a><a href="#" class="next"></a></div>                        
                    </div>

                  </form>
                </div>
                <script type="text/javascript">
                  $(function(){
                    //这是一个非常简单的demo实例，让列表元素分页显示
                    //回调函数的作用是显示对应分页的列表项内容
                    //回调函数在用户每次点击分页链接的时候执行
                    //参数page_index{int整型}表示当前的索引页
                    var initPagination = function() {
                      var num_entries = $("#hiddenresult ul.result").length;
                      // 创建分页
                      $("#Pagination").pagination(num_entries, {
                        num_edge_entries: 1, //边缘页数
                        num_display_entries: 4, //主体页数
                        callback: pageselectCallback,
                        items_per_page:1 //每页显示1项
                      });
                     }();
                     
                    function pageselectCallback(page_index, jq){
                      var new_content = $("#hiddenresult ul.result:eq("+page_index+")").clone();
                      $("#Searchresult").empty().append(new_content); //装载对应分页的内容
                      return false;
                    }
                  });
                  </script>
                <!-- 查询结果 end-->   
              </div>

            </div>
            <div class="panelBox" id="fore-2d3d-side-cx-kjcx" >
              <div class="panelBox-heading" >
                <h3 class="panelBox-title">空间查询</h3><span class="arrow arrowUp"></span>
              </div>
              <div class="panelBox-body">
                <form class="search-form" role="form">
                  <div class="form-group">
                    <label for="name">请选择查询方式</label>
                    <div class="icon-searchway">
                      <a href="javascript:;" class="col-sm-4 active">
                        <i class="icon iconfont">&#xe64d;</i>
                        <h5>点击选择点</h5>
                      </a>
                      <a href="javascript:;" class="col-sm-4 ">
                        <i class="icon iconfont">&#xe64f;</i>
                        <h5>点击选择线</h5>
                      </a>
                      <a href="javascript:;" class="col-sm-4 ">
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
                </form>
              </div>
            </div>
            <div class="panelBox" id="fore-2d3d-side-cx-ljcx">
              <div class="panelBox-heading" >
                <h3 class="panelBox-title">逻辑查询</h3><span class="arrow arrowUp"></span>
              </div>
              <div  class="panelBox-body">
                <form class="form-horizontal search-form" role="form">
                  <div class="form-group form-group-sm">
                    <label for="name" class="col-sm-4">查询图层：</label>
                    <div class="col-sm-8">
<%--                       <select class="form-control input-sm easyui-combotree " data-options="url:'${res }/dist/js/map/data/mapLayerData.json',method:'get'" name="mapLayer2"></select> --%>
                      <select class="form-control input-sm" id="queryLyrLogic" onchange="listFieldsLogic();"></select>
                    </div>
                  </div>
                  <div class="form-group form-group-sm">                    
                    <label for="name" class="col-sm-4">查询字段：</label>
                    <div class="col-sm-8">
                      <select class="form-control input-sm" id="queryFieldsLogic">
                      </select>
                    </div>
                  </div>
                  <div class="form-group">
                    <label for="name" class="col-sm-4">操作符：</label>
                    <div class="col-sm-8">
                      <input type="text" class="form-control input-sm" >
                    </div>
                  </div>                  
                  <div class="form-group">
                    <label for="name" class="col-sm-4">查询值</label>
                    <div class="col-sm-8">
                      <input type="text" class="form-control input-sm" >
                    </div>
                  </div>
                  <div class="searchBtn btn_list">
                    <button type="button" class="btn btn_add active">添 加</button>
                    <button type="button" class="btn">并 且</button>
                    <button type="button" class="btn">或 者</button>
                  </div>
                  <div class="form-group mg0">                    
                    <label for="name">组合逻辑：</label>
                    <select  multiple class="form-control">
                      <option>1</option>
                      <option>2</option>
                    </select>
                  </div>
                  <div class="searchBtn">
                    <button type="button" class="btn btn-success" onclick="queryAttrLogic();">查询</button>
                    <button type="reset" class="btn btn-warning">重置</button>
                  </div>
                </form>
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
                <form class="form-horizontal search-form" role="form">
                  <div class="form-group form-group-sm">
                    <label for="name" class="col-sm-4">地名：</label>
                    <div class="col-sm-8">
                      <input class="form-control input-sm" id="address">
						<div id="search" style="display:block;width:160px;height:30px;"></div>
                    </div>
                  </div>
                  <div class="searchBtn">
                    <button type="button" class="btn btn-success" onclick="locateAddress();">查询</button>
                    <button type="reset" class="btn btn-warning">重置</button>
                  </div>
                </form>
              </div>
            </div>
            <div class="panelBox" id="fore-2d3d-menu-dw-zbdw" >
              <div class="panelBox-heading" >
                <h3 class="panelBox-title">坐标定位</h3><span class="arrow arrowUp"></span>
              </div>
              <div id="collapse-dtdw-zbdw" class="panelBox-body">
                <ul id="myTab" class="nav nav-tabs">
                  <li class="active"><a href="#Latitude"  data-toggle="tab">经纬度</a></li>
                  <li><a href="#Longitude"  data-toggle="tab">平面坐标</a></li>
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
                      <button type="button" class="btn btn-success" onclick="locateLngLat();">定 位</button>
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
                      <button type="button" class="btn btn-success" onclick="locateXY();">定 位</button>
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
                <form class="form-inline search-form" role="form">
                  <div class="form-group form-group-sm">
                    <label for="name" class="col-sm-4">书签名称：</label>
                    <div class="col-sm-8">
                      <div class="input-group search-btn">
                        <input type="text" class="form-control">
                        <span class="input-group-btn btn-group-sm">
                          <button class="btn btn-default icon iconfont" type="submit" >&#xe644;</button>
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
                     data-toolbar="#Sqdwtoolbar"
                     data-toggle="table"
                     data-url="dist/js/map/data/tableFxmyData.json"
                     data-click-to-select="true"
                     data-row-style="rowStyle"
                     data-query-params="queryParams"
                     data-pagination="true"
                     data-page-size="5"
                     data-striped="true">
                        <thead>
                          <tr>
                            <th data-field="state" data-radio="true"></th>
                            <th data-field="id">序号</th>
                            <th data-field="pathName">书签名称</th>
                            <th data-field="pathDataSource">描述</th>
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
                <a href="#fore-2d3d-menu-bz-dbh" class="col-sm-3 active" data-toggle="tab">
                  <i class="icon iconfont mapSearch">&#xe659;</i>
                  <h2>点标绘</h2>
                </a>
                <a href="#fore-2d3d-menu-bz-xbh" class="col-sm-3" data-toggle="tab">
                  <i class="icon iconfont mapSearch">&#xe629;</i>
                  <h2>线标绘</h2>
                </a>
                <a href="#fore-2d3d-menu-bz-mbh" class="col-sm-3" data-toggle="tab">
                  <i class="icon iconfont mapSearch">&#xe660;</i>
                  <h2>面标绘</h2>
                </a>
                <a href="#fore-2d3d-menu-bz-tsbh" class="col-sm-3" data-toggle="tab">
                  <i class="icon iconfont mapSearch">&#xe618;</i>
                  <h2>特殊标绘</h2>
                </a>
              </div>
            </div>
          </div>
          <!-- 当前操作 -->
          <div class="subTabs">
            <div class="panelBox active" id="fore-2d3d-menu-bz-dbh">
              <div class="panelBox-heading">
                <h3 class="panelBox-title">点标绘</h3><span class="arrow arrowUp"></span>
              </div>
              <div  class="panelBox-body">
                <form class="form-horizontal search-form" role="form">
                  <div class="form-group form-group-sm">
                    <label for="name" class="col-sm-4">字体：</label>
                    <div class="col-sm-8">
                      <select class="form-control input-sm">
                        <option>1</option>
                        <option>2</option>
                        <option>3</option>
                      </select>
                    </div>
                  </div>
                  <div class="form-group form-group-sm">
                    <label for="name" class="col-sm-4">字体尺寸：</label>
                    <div class="col-sm-8">
                      <select class="form-control input-sm">
                        <option>1</option>
                        <option>2</option>
                        <option>3</option>
                      </select>
                    </div>
                  </div>
                  <div class="form-group form-group-sm">
                    <label for="name" class="col-sm-4">字体颜色：</label>
                    <div class="col-sm-8">
                      <div class="input-group-btn">
                        <input type="text" class="form-control text-mid input-in color">
                      </div>
                    </div>
                  </div>
                  <div class="form-group form-group-sm">
                    <label for="name" class="col-sm-4">图形颜色：</label>
                    <div class="col-sm-8">
                      <div class="input-group-btn">
                        <input type="text" class="form-control text-mid input-in color">
                      </div>
                    </div>
                  </div>
                  <div class="form-group form-group-sm">
                    <label for="name" class="col-sm-4">填充颜色：</label>
                    <div class="col-sm-8">
                      <div class="input-group-btn">
                        <input type="text" class="form-control text-mid input-in color">
                      </div>
                    </div>
                  </div>
                  <div class="form-group form-group-sm">
                    <label for="name" class="col-sm-4">透明度：</label>
                    <div class="col-sm-8">
                      <select class="form-control input-sm">
                        <option>1</option>
                        <option>2</option>
                        <option>3</option>
                      </select>
                    </div>
                  </div>
                  <div class="form-group form-group-sm">
                    <label for="name" class="col-sm-4">线宽：</label>
                    <div class="col-sm-8">
                      <select class="form-control input-sm">
                        <option>1</option>
                        <option>2</option>
                        <option>3</option>
                      </select>
                    </div>
                  </div>                                   
                  <div class="searchBtn">
                    <button type="submit" class="btn btn-success">应用</button>
                  </div>
                </form>
              </div>
            </div>
            <div class="panelBox" id="fore-2d3d-menu-bz-xbh">
              <div class="panelBox-heading">
                <h3 class="panelBox-title">线标绘</h3><span class="arrow arrowUp"></span>
              </div>
              <div  class="panelBox-body">
                <form class="form-horizontal search-form" role="form">
                  <div class="form-group form-group-sm">
                    <label for="name" class="col-sm-4">字体：</label>
                    <div class="col-sm-8">
                      <select class="form-control input-sm">
                        <option>1</option>
                        <option>2</option>
                        <option>3</option>
                      </select>
                    </div>
                  </div>
                  <div class="form-group form-group-sm">
                    <label for="name" class="col-sm-4">字体尺寸：</label>
                    <div class="col-sm-8">
                      <select class="form-control input-sm">
                        <option>1</option>
                        <option>2</option>
                        <option>3</option>
                      </select>
                    </div>
                  </div>
                  <div class="form-group form-group-sm">
                    <label for="name" class="col-sm-4">字体颜色：</label>
                    <div class="col-sm-8">
                      <div class="input-group-btn">
                        <input type="text" class="form-control text-mid input-in color">
                      </div>
                    </div>
                  </div>
                  <div class="form-group form-group-sm">
                    <label for="name" class="col-sm-4">图形颜色：</label>
                    <div class="col-sm-8">
                      <div class="input-group-btn">
                        <input type="text" class="form-control text-mid input-in color">
                      </div>
                    </div>
                  </div>
                  <div class="form-group form-group-sm">
                    <label for="name" class="col-sm-4">填充颜色：</label>
                    <div class="col-sm-8">
                      <div class="input-group-btn">
                        <input type="text" class="form-control text-mid input-in color">
                      </div>
                    </div>
                  </div>
                  <div class="form-group form-group-sm">
                    <label for="name" class="col-sm-4">透明度：</label>
                    <div class="col-sm-8">
                      <select class="form-control input-sm">
                        <option>1</option>
                        <option>2</option>
                        <option>3</option>
                      </select>
                    </div>
                  </div>
                  <div class="form-group form-group-sm">
                    <label for="name" class="col-sm-4">线宽：</label>
                    <div class="col-sm-8">
                      <select class="form-control input-sm">
                        <option>1</option>
                        <option>2</option>
                        <option>3</option>
                      </select>
                    </div>
                  </div>                                   
                  <div class="searchBtn">
                    <button type="submit" class="btn btn-success">应用</button>
                  </div>
                </form>
              </div>
            </div>
            <div class="panelBox" id="fore-2d3d-menu-bz-mbh">
              <div class="panelBox-heading">
                <h3 class="panelBox-title">面标绘</h3><span class="arrow arrowUp"></span>
              </div>
              <div  class="panelBox-body">
                <form class="form-horizontal search-form" role="form">
                  <div class="form-group form-group-sm">
                    <label for="name" class="col-sm-4">字体：</label>
                    <div class="col-sm-8">
                      <select class="form-control input-sm">
                        <option>1</option>
                        <option>2</option>
                        <option>3</option>
                      </select>
                    </div>
                  </div>
                  <div class="form-group form-group-sm">
                    <label for="name" class="col-sm-4">字体尺寸：</label>
                    <div class="col-sm-8">
                      <select class="form-control input-sm">
                        <option>1</option>
                        <option>2</option>
                        <option>3</option>
                      </select>
                    </div>
                  </div>
                  <div class="form-group form-group-sm">
                    <label for="name" class="col-sm-4">字体颜色：</label>
                    <div class="col-sm-8">
                      <div class="input-group-btn">
                        <input type="text" class="form-control text-mid input-in color">
                      </div>
                    </div>
                  </div>
                  <div class="form-group form-group-sm">
                    <label for="name" class="col-sm-4">图形颜色：</label>
                    <div class="col-sm-8">
                      <div class="input-group-btn">
                        <input type="text" class="form-control text-mid input-in color">
                      </div>
                    </div>
                  </div>
                  <div class="form-group form-group-sm">
                    <label for="name" class="col-sm-4">填充颜色：</label>
                    <div class="col-sm-8">
                      <div class="input-group-btn">
                        <input type="text" class="form-control text-mid input-in color">
                      </div>
                    </div>
                  </div>
                  <div class="form-group form-group-sm">
                    <label for="name" class="col-sm-4">透明度：</label>
                    <div class="col-sm-8">
                      <select class="form-control input-sm">
                        <option>1</option>
                        <option>2</option>
                        <option>3</option>
                      </select>
                    </div>
                  </div>
                  <div class="form-group form-group-sm">
                    <label for="name" class="col-sm-4">线宽：</label>
                    <div class="col-sm-8">
                      <select class="form-control input-sm">
                        <option>1</option>
                        <option>2</option>
                        <option>3</option>
                      </select>
                    </div>
                  </div>                                   
                  <div class="searchBtn">
                    <button type="submit" class="btn btn-success">应用</button>
                  </div>
                </form>
              </div>
            </div>                         
            <div class="panelBox" id="fore-2d3d-menu-bz-tsbh">
              <div class="panelBox-heading">
                <h3 class="panelBox-title">特殊标绘</h3><span class="arrow arrowUp"></span>
              </div>
              <div  class="panelBox-body">
                <ul id="myTabPlot" class="nav nav-tabs">
                  <li class="active"><a href="#planePlot" data-toggle="tab" >平面标绘</a></li>
                  <li><a href="#3dPlot" data-toggle="tab" >三维标绘</a></li>
                </ul>
                <div id="myTabContentPlot" class="tab-content">                
                  <div class="tab-pane side-plot active" id="planePlot">
                     <a href="javascript:;" class="active"><span class="icon iconfont">&#xe639;</span></a>
                     <a href="javascript:;" ><span class="icon iconfont">&#xe63a;</span></a>
                     <a href="javascript:;" ><span class="icon iconfont">&#xe652;</span></a>
                     <a href="javascript:;" ><span class="icon iconfont">&#xe653;</span></a>
                     <a href="javascript:;" ><span class="icon iconfont">&#xe654;</span></a>
                     <a href="javascript:;" ><span class="icon iconfont">&#xe655;</span></a>       
                  </div>
                  <div class="tab-pane side-plot" id="3dPlot">
                     <a href="javascript:;" class="active"><span class="icon iconfont">&#xe661;</span></a>
                     <a href="javascript:;" ><span class="icon iconfont">&#xe65c;</span></a>
                     <a href="javascript:;" ><span class="icon iconfont">&#xe657;</span></a>
                     <a href="javascript:;" ><span class="icon iconfont">&#xe65e;</span></a>
                     <a href="javascript:;" ><span class="icon iconfont">&#xe662;</span></a>
                     <a href="javascript:;" ><span class="icon iconfont">&#xe665;</span></a>
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
                <ul id="treeMapzt"  class="easyui-tree" data-options="url:'${res }/dist/js/map/data/mapLayerData.json',method:'get',animate:true,lines:true,checkbox:true">
                </ul>
                 <!---tree end-->
              </div>
            </div>

          </div>
        </div>
        <div id="fore-2d3d-menu-bj" >
          <!-- 当前位置 -->
          <ol class="breadcrumb">
            <li><i class="icon iconfont home">&#xe640;</i></li>
            <li><a href="#">首页</a></li>
            <li><a href="#">-地图编辑</a></li>
          </ol>

          <!-- 当前操作 -->
          <div class="subTabs">
            <div class="panelBox active" id="fore-2d3d-menu-bj-dtbj">
              <div class="panelBox-heading"><i class="icon iconfont icon_map">&#xe622;</i>
                <h3 class="panelBox-title">地图编辑</h3><span class="arrow arrowUp"></span>
              </div>
              <div  class="panelBox-body">
              <form class="form-horizontal search-form" role="form">
                <div class="form-group form-group-sm">
                  <label for="name" class="col-sm-4">选择图层：</label>
                  <div class="col-sm-8">
                    <select class="form-control input-sm">
                      <option>1</option>
                      <option>2</option>
                      <option>3</option>
                    </select>
                  </div>
                </div>                                   
                <div class="searchBtn btn_list">
                  <button type="submit" class="btn btn-sm active">开始编辑</button>
                  <button type="submit" class="btn btn-sm">保存编辑</button>
                  <button type="submit" class="btn btn-sm">取消编辑</button>
                </div>
                <ul id="myTab" class="nav nav-tabs">
                  <li class="active"><a href="#pointSymbol"  data-toggle="tab">点符号</a></li>
                  <li><a href="#lineSymbol"  data-toggle="tab">线符号</a></li>
                  <li><a href="#planeSymbol"  data-toggle="tab">面符号</a></li>
                </ul>
                <div id="myTabContent" class="tab-content">                
                  <div class="tab-pane  active" id="pointSymbol">
                    <ul class="bs-glyphicons-list clearfix">
                      <li>
                        <span class="glyphicon glyphicon-asterisk"></span>
                        <span class="glyphicon-class">锚点1</span>
                      </li>
                      <li>
                        <span class="glyphicon glyphicon-asterisk"></span>
                        <span class="glyphicon-class">锚点</span>
                      </li>
                      <li>
                        <span class="glyphicon glyphicon-asterisk"></span>
                        <span class="glyphicon-class">锚点</span>
                      </li>
                      <li>
                        <span class="glyphicon glyphicon-asterisk"></span>
                        <span class="glyphicon-class">锚点</span>
                      </li>
                      <li>
                        <span class="glyphicon glyphicon-asterisk"></span>
                        <span class="glyphicon-class">锚点</span>
                      </li>
                      <li>
                        <span class="glyphicon glyphicon-asterisk"></span>
                        <span class="glyphicon-class">锚点</span>
                      </li>
                    </ul>
                  </div>
                  <div class="tab-pane " id="lineSymbol">
                    <ul class="bs-glyphicons-list clearfix">
                      <li>
                        <span class="glyphicon glyphicon-asterisk"></span>
                        <span class="glyphicon-class">锚点2</span>
                      </li>
                      <li>
                        <span class="glyphicon glyphicon-asterisk"></span>
                        <span class="glyphicon-class">锚点</span>
                      </li>
                      <li>
                        <span class="glyphicon glyphicon-asterisk"></span>
                        <span class="glyphicon-class">锚点</span>
                      </li>
                      <li>
                        <span class="glyphicon glyphicon-asterisk"></span>
                        <span class="glyphicon-class">锚点</span>
                      </li>
                      <li>
                        <span class="glyphicon glyphicon-asterisk"></span>
                        <span class="glyphicon-class">锚点</span>
                      </li>
                      <li>
                        <span class="glyphicon glyphicon-asterisk"></span>
                        <span class="glyphicon-class">锚点</span>
                      </li>
                    </ul> 
                  </div>
                  <div class="tab-pane " id="planeSymbol">
                    <ul class="bs-glyphicons-list clearfix">
                      <li>
                        <span class="glyphicon glyphicon-asterisk"></span>
                        <span class="glyphicon-class">锚点3</span>
                      </li>
                      <li>
                        <span class="glyphicon glyphicon-asterisk"></span>
                        <span class="glyphicon-class">锚点</span>
                      </li>
                      <li>
                        <span class="glyphicon glyphicon-asterisk"></span>
                        <span class="glyphicon-class">锚点</span>
                      </li>
                      <li>
                        <span class="glyphicon glyphicon-asterisk"></span>
                        <span class="glyphicon-class">锚点</span>
                      </li>
                      <li>
                        <span class="glyphicon glyphicon-asterisk"></span>
                        <span class="glyphicon-class">锚点</span>
                      </li>
                      <li>
                        <span class="glyphicon glyphicon-asterisk"></span>
                        <span class="glyphicon-class">锚点</span>
                      </li>
                    </ul> 
                  </div>

                    <div class="form-group form-group-sm">
                      <label for="name" class="col-sm-4">颜色：</label>
                      <div class="col-sm-8">
                        <select class="form-control input-sm">
                          <option>1</option>
                          <option>2</option>
                          <option>3</option>
                        </select>
                      </div>
                    </div>
                    <div class="form-group">
                      <label for="name" class="col-sm-4">大小：</label>
                      <div class="col-sm-8">
                        <input type="text" class="form-control input-sm" >
                      </div>
                    </div>                  
                    <div class="form-group">
                      <label for="name" class="col-sm-4">角度：</label>
                      <div class="col-sm-8">
                        <input type="text" class="form-control input-sm">
                      </div>
                    </div>
                    <div class="searchBtn">
                      <button type="submit" class="btn btn-success">新增</button>
                      <button type="reset" class="btn btn-warning">删除</button>
                    </div> 

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
                  <li class="active"><a href="#PaintOption" data-toggle="tab" aria-expanded="true">请选择绘制方式</a></li>
                </ul>
                <div id="myTabContentPlot" class="tab-content">                
                  <div class="tab-pane side-plot active" id="PaintOption">
                     <a href="javascript:;" class="active"><span class="icon iconfont">&#xe64d;</span></a>
                     <a href="javascript:;"><span class="icon iconfont">&#xe64f;</span></a>
                     <a href="javascript:;"><span class="icon iconfont">&#xe632;</span></a>      
                  </div>               
                </div>
                <form class="form-inline search-form" role="form">
                <div class="form-group form-group-sm">
                    <label for="name">导入GPS</label>
                    <div class="filebox"><input type="file" name="file_0_ture" size="20" onchange="document.getElementById('file_0').value=this.value" class="filetext opacity "><input name="file_0" id="file_0" value="" class="form-control"> <button type="button"  class="btn">导 入</button></div>
                </div>
                </form>              
              </div>
            </div>
            <div class="panelBox" id="fore-2d3d-menu-fx-hcqfx">
              <div class="panelBox-heading">
                <h3 class="panelBox-title">缓冲区分析</h3><span class="arrow arrowUp"></span>
              </div>
              <div  class="panelBox-body">
                <ul id="myTabPaintOption" class="nav nav-tabs">
                  <li class="active"><a href="#PaintOption" data-toggle="tab" aria-expanded="true">请选择绘制方式</a></li>
                </ul>
                <div id="myTabContentPlot" class="tab-content">                
                  <div class="tab-pane side-plot active" id="PaintOption">
                     <a href="javascript:;" class="active"><span class="icon iconfont">&#xe64d;</span></a>
                     <a href="javascript:;"><span class="icon iconfont">&#xe64f;</span></a>
                     <a href="javascript:;"><span class="icon iconfont">&#xe632;</span></a>      
                  </div>               
                </div>
                <form class="form-horizontal search-form" role="form">
                <div class="form-group form-group-sm">
                    <label for="name" class="col-sm-5">导入GPS</label>
                    <div class="filebox col-sm-7"><input type="file" name="file_1_ture" size="20" onchange="document.getElementById('file_1').value=this.value" class="filetext opacity "><input name="file_1" id="file_1" value="" class="form-control"> <button type="button"  class="btn file_btn">导 入</button></div>
                </div>
                <div class="form-group form-group-sm">
                    <label for="name" class="col-sm-5">分析图层：</label>
                    <div class="col-sm-7">
                      <select class="form-control input-sm">
                        <option>1</option>
                        <option>2</option>
                        <option>3</option>
                      </select>
                    </div>
                  </div>
                  <div class="form-group">
                    <label for="name" class="col-sm-5">缓冲距离(米)：</label>
                    <div class="col-sm-7">
                      <input type="text" class="form-control input-sm" >
                    </div>
                  </div>
                  <div class="form-group form-group-sm">
                    <label for="name" class="col-sm-5">统计类似：</label>
                    <div class="col-sm-7">
                      <select class="form-control input-sm">
                        <option>村</option>
                        <option>2</option>
                        <option>3</option>
                      </select>
                    </div>
                  </div>
                  <div class="searchBtn">
                    <button type="submit" class="btn btn-success">分析</button>
                  </div>
                </form>              
              </div>
            </div>
            <div class="panelBox" id="fore-2d3d-menu-fx-3dfx">
              <div class="panelBox-heading">
                <h3 class="panelBox-title">三维分析</h3><span class="arrow arrowUp"></span>
              </div>
              <div  class="panelBox-body">
                <div class="row side-menu">
                  <a href="#" class="col-sm-6 active">
                    <i class="icon iconfont side-menu-icon">&#xe64b;</i>
                    <h2>光照分析</h2>
                  </a>
                  <a href="#" class="col-sm-6" >
                    <i class="icon iconfont side-menu-icon">&#xe60b;</i>
                    <h2>水淹分析</h2>
                  </a>
                  <a href="#" class="col-sm-6" >
                    <i class="icon iconfont side-menu-icon">&#xe65d;</i>
                    <h2>视线分析</h2>
                  </a>
                  <a href="#" class="col-sm-6" >
                    <i class="icon iconfont side-menu-icon">&#xe658;</i>
                    <h2>视域分析</h2>
                  </a>
                </div>
              </div>
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
                <form class="form-inline search-form" role="form">
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
                     data-toggle="table"
                     data-row-style="rowStyle">
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
            </div>
            <div class="panelBox" id="fore-2d3d-menu-my-ljgl">
              <div class="panelBox-heading">
                <h3 class="panelBox-title">路径管理</h3><span class="arrow arrowUp"></span>
              </div>
              <div  class="panelBox-body">
                <div class="pathbox">
                  <form class="form-inline search-form" role="form">
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
                             data-toggle="table"
                             data-row-style="rowStyle">
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
                        <input type="text" class="form-control input-sm" id="selectRoamPathName">
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
                             data-toggle="table"
                             data-row-style="rowStyle">
                          <thead>
                            <tr>
                              <th data-field="state" data-radio="true"></th>
                              <th data-field="pointIndex">编号</th>
                              <th data-field="pointName">路径点名</th>
                              <th data-field="stopTime">停留时间</th>
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
                        <button type="button" class="btn btn-default btn-sm btn_save active">
                          <span class="glyphicon glyphicon-saved"></span> 保存
                        </button>
                         <button type="button" class="btn btn-default btn-sm btn_cancel">
                          <span class="glyphicon glyphicon-remove"></span> 取消
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
      <div class="l_icon"></div>
    </div>
    <!-- content start -->
    <div class="content" id="mapContent">
      <!-- 地图工具条 start-->
      <div class="toolbar" id="measureDiv">
      	  <a href="javascript:;" id="printDiv" onclick="print()"><i class="icon iconfont">&#xe63f;</i><h3>打印</h3></a>
          <a href="javascript:;" id="fullScreenBtn"><i class="icon iconfont">&#xe643;</i><h3>全屏</h3></a>
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
        <div class="legendBox">
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
            <!-- 下面一行为添加的标签 -->
          <iframe frameborder= "0" scrolling="no" style="background-color:transparent; position: absolute; z-index: -1; width: 100%; height: 100%; top: 0; left:0;"></iframe>
          <div class="mapBtn" id="myTab">
           <div class="btn-group mapView">
              <button type="button" class="btn btn-default mapView-btn active" onclick="to2dMap();">地图</button>
              <button type="button" class="btn btn-default " onclick="to2dImgMap();">卫星</button>
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
          <div class="btn-group">
            <button type="button" class="btn btn-default  maplayer-btn dropdown-toggle" ata-toggle="dropdown">图层<span class="caret"></span></button>
            <ul class="dropdown-menu maplayer" role="menu" >
              <!-- 下面一行为添加的标签 -->
              <iframe frameborder= "0" scrolling="no" style="background-color:transparent; position: absolute; z-index: -1; width: 100%; height: 100%; top: 0; left:0;"></iframe>
              <li><a href="#">森林资源分布图</a></li>
              <li><a href="#">基础数据</a></li>
              <li><a href="#">功能分析</a></li>
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
    <!-- 下面一行为添加的标签 -->
    <iframe frameborder= "0" scrolling="no" style="background-color:transparent; position: absolute; z-index: -1; width: 100%; height: 100%; top: 0; left:0;"></iframe>
     <!-- Control Sidebar 换肤弹窗-->
    <aside class="control-sidebar control-sidebar-dark">
      <div class="tab-content">
        <div class="tab-pane" id="control-sidebar-home-tab"></div>
      </div>
    </aside>
    <div class="control-sidebar-bg"></div>         
  </div>

  
  <div id="dialog" style="position:absolute;display:none;right:100px;top:200px;z-index:100 ;width:500px;height:600px; ">
		<table id="grid"></table>
  </div>
	
</div>
<!-- wrapper end -->
<!-- AdminLTE App -->
<script src="${res}/dist/js/app.js"></script>
<!-- 换肤 -->
<script src="${res}/dist/js/map/mapSkinSelect.js"></script>
</body>
</html>