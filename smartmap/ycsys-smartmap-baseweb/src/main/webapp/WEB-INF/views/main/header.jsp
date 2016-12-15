<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/views/common/taglib.jsp" %>
<header class="main-header">
    <!-- Logo -->
    <a href="javascript:void(0);" class="logo">
        <!-- mini logo for sidebar mini 50x50 pixels -->
        <span class="logo-mini"><b>羽</b>辰</span>
        <!-- logo for regular state and mobile devices -->
        <span class="logo-lg"><img src="${res}/dist/img/logo.png" height="41" width="351" alt="" style="margin-top:9px;"/></span>
    </a>
    <!-- Header Navbar: style can be found in header.less -->
    <nav class="navbar navbar-static-top">
        <!-- Sidebar toggle button-->
        <a href="#" class="sidebar-toggle" data-toggle="offcanvas" role="button">
            <i class="iconfont icon-bars"></i>
        </a>

        <div class="navbar-custom-menu">
            <ul class="nav navbar-nav">
                <!-- Messages: style can be found in dropdown.less-->
                <li class="dropdown messages-menu">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                        <i class="iconfont icon-envelope-o"></i>
                        <span class="label label-success">4</span>
                    </a>
                    <ul class="dropdown-menu">
                        <li class="header">你有4个消息待查看</li>
                        <li>
                            <!-- inner menu: contains the actual data -->
                            <ul class="menu">
                                <li><!-- start message -->
                                    <a href="#">
                                        <div class="pull-left">
                                           <i class="iconfont icon-commenting-o"></i>
                                        </div>
                                        <h4>
                                            支持团队
                                            <small><i class="iconfont iconfont-clock-o"></i> 5分钟</small>
                                        </h4>
                                        <p>Why not buy a new awesome theme?</p>
                                    </a>
                                </li>
                                <!-- end message -->
                                <li>
                                    <a href="#">
                                        <div class="pull-left">
                                            <i class="iconfont icon-commenting-o"></i>
                                        </div>
                                        <h4>
                                            AdminLTE 设计团队
                                            <small><i class="iconfont iconfont-clock-o"></i> 2小时</small>
                                        </h4>
                                        <p>Why not buy a new awesome theme?</p>
                                    </a>
                                </li>
                                <li>
                                    <a href="#">
                                        <div class="pull-left">
                                            <i class="iconfont icon-commenting-o"></i>
                                        </div>
                                        <h4>
                                            开发商
                                            <small><i class="iconfont iconfont-clock-o"></i> 今天</small>
                                        </h4>
                                        <p>Why not buy a new awesome theme?</p>
                                    </a>
                                </li>
                                <li>
                                    <a href="#">
                                        <div class="pull-left">
                                            <i class="iconfont icon-commenting-o"></i>
                                        </div>
                                        <h4>
                                            市场部
                                            <small><i class="iconfont iconfont-clock-o"></i> 昨天</small>
                                        </h4>
                                        <p>Why not buy a new awesome theme?</p>
                                    </a>
                                </li>
                                <li>
                                    <a href="#">
                                        <div class="pull-left">
                                            <i class="iconfont icon-commenting-o"></i>
                                        </div>
                                        <h4>
                                            审稿人
                                            <small><i class="fa fa-clock-o"></i> 2天内</small>
                                        </h4>
                                        <p>Why not buy a new awesome theme?</p>
                                    </a>
                                </li>
                            </ul>
                        </li>
                        <li class="footer"><a href="#">查看全部消息</a></li>
                    </ul>
                </li>
          		<!-- user -->
          		<li id="navbar" class="navbar-user">
            		<div class="user dropdown">           
                		<a href="#" class="user_box dropdown-toggle" data-toggle="dropdown" id="dLabel" ><img src="${res}/dist/img/photo1.png" alt="头像" /><span class="user_name">admin</span> <span class="caret"></span></a>
                		<ul class="dropdown-menu memu-list"  role="menu" aria-labelledby="dLabel">
                  		<li class="personal"><a href="#"><i class="iconfont icon-user"></i><span>个人信息</span></a></li>
                  		<li class="password"><a href="#"><i class="glyphicon glyphicon-lock"></i><span>修改密码</span></a></li>
                  		<li><a href="login.html"><i class="glyphicon glyphicon-off"></i><span>退出系统</span></a></li>
                		</ul>              
              		</div>
          		</li>
                <li>
                    <a href="#" data-toggle="control-sidebar"><i class="iconfont icon-icon-huanfu"></i></a>
                </li>
            </ul>
        </div>
    </nav>
</header>