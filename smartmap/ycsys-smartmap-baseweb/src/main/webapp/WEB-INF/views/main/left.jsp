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
                <a href="javascript:void(0);" onclick="left_href(this,'${ctx}','/welcome')">
                    <i class="iconfont icon-th-list"></i>
                    <span>系统首页</span>
                </a>
            </li>
            <c:forEach var="i" items="${index_permissions}" varStatus="status">
                <li class="treeview">
                    <a href="javascript:void(0);" onclick="left_href(this,'${ctx}','${i.url}')">
                        <%--<i id="menu_icon_${status.index}"></i>--%>
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
                                    <a href="javascript:void(0);" onclick="left_href(this,'${ctx}','${child.url}')">
                                        <i class="iconfont icon-circle-o"></i>
                                        ${child.name}
                                        <c:if test="${child.childPermission != null && fn:length(child.childPermission) > 0}">
                                            <span class="pull-right-container">
                                              <i class="iconfont icon-folder-open pull-right"></i>
                                            </span>
                                        </c:if>
                                    </a>
                                    <c:if test="${child.childPermission != null && fn:length(child.childPermission) > 0}">
                                        <ul class="treeview-menu">
                                            <c:forEach var="childs" items="${child.childPermission}">
                                                <li>
                                                    <a href="javascript:void(0);" onclick="left_href(this,'${ctx}','${childs.url}')">
                                                     <i class="iconfont icon-circle-o"></i> ${childs.name}
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
    function left_href(ts,context, href) {
        if (href) {
            $(ts).parent().parent().find("li[class='active']").removeClass("active");
            $(ts).parent().attr("class","active");
            var i = $("<iframe />", {
                "align": "center",
                "id": "main_iframe",
                "class": "firefoxIframeW",
                "name": "main_iframe",
                "frameborder": "0",
                "src": context + href,
                "onload": "iframeResize(this)"
            });
            $("#yc_main").html(i);
        }
    }
    //图标样式定义
    var iconDefined = {
        "系统首页":'iconfont icon-th-list',
        "资源管理":'iconfont icon-folder-open',
        "服务使用与审核":'iconfont icon-gittip',
        "服务管理":'iconfont icon-address-book-o',
        "系统监控":'iconfont icon-video-camera',
        "安全管理":'iconfont icon-user',
        "子系统管理":'iconfont icon-tachometer',
        "系统配置":'iconfont icon-cog',
        "日志管理":'iconfont icon-circle-o',
        "统计分析":'iconfont icon-area-chart'
    };
    var treeviews = document.getElementsByClassName("treeview");
    for(var x = 0;x<treeviews.length;x++){
        var span = treeviews[x].getElementsByTagName("span")[0];
        if(span.innerText){
            var innerText = span.innerText;
            for(var k in iconDefined){
                if(k == innerText){
                    var prev = span.previousElementSibling;
                    if(prev == null){
                        var newI = document.createElement("I");
                        newI.className = iconDefined[k];
                        span.parentNode.insertBefore(newI,span);
                        //还原前后文本空格
                        span.parentNode.insertBefore(document.createTextNode("\n                    "),newI);
                        span.parentNode.insertBefore(document.createTextNode("\n                    "),span);
                    }else{
                        if(prev.tagName == "I" || prev.tagName == "i"){
                            prev.remove();
                            var newI = document.createElement("I");
                            newI.className = iconDefined[k];
                            span.parentNode.insertBefore(newI,span);
                            //还原前后文本空格
                            span.parentNode.insertBefore(document.createTextNode("\n                    "),newI);
                            span.parentNode.insertBefore(document.createTextNode("\n                    "),span);
                        }
                    }
                }
            }
        }
    }
</script>