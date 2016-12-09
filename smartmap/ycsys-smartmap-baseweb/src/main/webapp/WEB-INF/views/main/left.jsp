<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/views/common/taglib.jsp" %>
<!-- Left side column. contains the logo and sidebar -->
<aside class="main-sidebar">
    <!-- sidebar: style can be found in sidebar.less -->
    <section class="sidebar">
        <!-- search form -->
        <form action="#" method="get" class="sidebar-form">
            <div class="input-group">
                <input type="text" name="q" class="form-control" placeholder="Search...">
                <span class="input-group-btn">
                <button type="submit" name="search" id="search-btn" class="btn btn-flat"><i class="fa fa-search"></i>
                </button>
              </span>
            </div>
        </form>
        <!-- /.search form -->
        <!-- sidebar menu: : style can be found in sidebar.less -->
        <!--左边菜单-->
        <ul class="sidebar-menu">
            <li class="header">主菜单</li>
            <li class="active treeview">
                <a href="javascript:void(0);" onclick="left_href('${ctx}','/welcome')">
                    <!--
                    <i class="iconfont icon-th-list"></i>
                    -->
                    <span>系统首页</span>
                </a>
            </li>
            <c:forEach var="i" items="${index_permissions}" varStatus="status">
                <li class="treeview">
                    <a href="javascript:void(0);" onclick="left_href('${ctx}','${i.url}')">
                        <i id="menu_icon_${status.index}"></i>
                        <span>${i.name}</span>
                        <c:if test="${i.childPermission != null && fn:length(i.childPermission) > 0}">
                            <span class="pull-right-container">
                                <i class="iconfont icon-arrowdown pull-right"></i>
                            </span>
                        </c:if>
                    </a>
                    <c:if test="${i.childPermission != null && fn:length(i.childPermission) > 0}">
                        <ul class="treeview-menu">
                            <c:forEach var="child" items="${i.childPermission}">
                                <li>
                                    <a href="javascript:void(0);" onclick="left_href('${ctx}','${child.url}')">
                                        <i class="fa fa-circle-o"></i>
                                        ${child.name}
                                        <c:if test="${child.childPermission != null && fn:length(child.childPermission) > 0}">
                                            <span class="pull-right-container">
                                              <i class="iconfont icon-arrowdown pull-right"></i>
                                            </span>
                                        </c:if>
                                    </a>
                                    <c:if test="${child.childPermission != null && fn:length(child.childPermission) > 0}">
                                        <ul class="treeview-menu">
                                            <c:forEach var="childs" items="${child.childPermission}">
                                                <li>
                                                    <a href="javascript:void(0);" onclick="left_href('${ctx}','${childs.url}')">
                                                     <i class="fa fa-circle-o"></i> ${childs.name}
                                                    </a>
                                                </li>
                                            </c:forEach>
                                        </ul>
                                    </c:if>
                                </li>
                            </c:forEach>
                        </ul>
                    </c:if>
                </li>
            </c:forEach>


        </ul>
    </section>
    <!-- /.sidebar -->
</aside>
<script>
    function left_href(context, href) {
        if (href) {
            var i = $("<iframe />", {
                "width": "100%",
                "align": "center",
                "id": "main_iframe",
                "name": "main_iframe",
                "frameborder": "0",
                "src": context + href,
                "onload": "iframeResize(this)"
            });
            $("#yc_main").html(i);
        }
    }
</script>