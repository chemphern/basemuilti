<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/views/common/taglib.jsp" %>

<header class="main-header">

    <!-- Logo -->
    <a href="javascript:void(0);" class="logo">
        <!-- mini logo for sidebar mini 50x50 pixels -->
        <span class="logo-mini"><b>羽</b>辰</span>
        <!-- logo for regular state and mobile devices -->
        <span class="logo-lg"><img src="${res}/dist/img/logo.png" height="41" width="351" alt=""
                                   style="margin-top:9px;"/></span>
    </a>
    <!-- Header Navbar: style can be found in header.less -->
    <nav class="navbar navbar-static-top">
        <!-- Sidebar toggle button-->
        <a href="#" class="sidebar-toggle" data-toggle="offcanvas" role="button">
            <i class="iconfont icon-bars"></i>
        </a>

        <div class="navbar-custom-menu">
            <ul class="nav navbar-nav">
                <!-- user -->
                <li id="navbar" class="navbar-user">
                    <div class="user dropdown">
                        <a href="#" class="user_box dropdown-toggle" data-toggle="dropdown" id="dLabel"><img
                                src="${res}/dist/img/photo1.png" alt="头像"/><span class="user_name">${YCSYS_SESSION_USER.name}</span> <span
                                class="caret"></span></a>
                        <ul class="dropdown-menu memu-list" role="menu" aria-labelledby="dLabel">
                            <li class="personal"><a href="javascript:;" onclick="personalMsg()"><i
                                    class="iconfont icon-user"></i><span>个人信息</span></a></li>
                            <li class="password"><a href="javascript:;" onclick="updatePwd()"><i
                                    class="glyphicon glyphicon-lock"></i><span>修改密码</span></a></li>
                            <li><a href="${ctx}/logout"><i class="glyphicon glyphicon-off"></i><span>退出系统</span></a></li>
                        </ul>
                    </div>
                </li>
                <!-- Messages: style can be found in dropdown.less-->
                <li class="dropdown messages-menu">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                        <i class="iconfont icon-envelope-o"></i>
                        <span class="label label-success" id="notice_count">0</span>
                    </a>
                    <ul class="dropdown-menu">
                        <li class="header" id="notice_header"></li>
                        <li>
                            <!-- inner menu: contains the actual data -->
                            <ul class="menu" id="notice_menu">

                            </ul>
                        </li>
                        <li class="footer"><a href="javascript:;" onclick="showAllNotice()">查看全部消息</a></li>
                    </ul>
                </li>
                <!-- Messages: style can be found in dropdown.less-->
                <%--<li class="dropdown messages-menu">--%>
                    <%--<a href="#" class="dropdown-toggle" data-toggle="dropdown">--%>
                        <%--<i class="iconfont icon-iconfontbell2"></i>--%>
                        <%--<span class="label label-success" style="color:red;">3</span>--%>
                    <%--</a>--%>
                    <%--<ul class="dropdown-menu">--%>
                        <%--<li class="header">你有3个消息待查看</li>--%>
                        <%--<li>--%>
                            <%--<!-- inner menu: contains the actual data -->--%>
                            <%--<ul class="menu">--%>
                                <%--<li><!-- start message -->--%>
                                    <%--<a href="#">--%>
                                        <%--<div class="pull-left">--%>
                                            <%--<i class="iconfont icon-commenting-o"></i>--%>
                                        <%--</div>--%>
                                        <%--<h4>--%>
                                            <%--支持团队--%>
                                            <%--<small><i class="iconfont iconfont-clock-o"></i> 5分钟</small>--%>
                                        <%--</h4>--%>
                                        <%--<p>Why not buy a new awesome theme?</p>--%>
                                    <%--</a>--%>
                                <%--</li>--%>
                                <%--<!-- end message -->--%>
                                <%--<li>--%>
                                    <%--<a href="#">--%>
                                        <%--<div class="pull-left">--%>
                                            <%--<i class="iconfont icon-commenting-o"></i>--%>
                                        <%--</div>--%>
                                        <%--<h4>--%>
                                            <%--AdminLTE 设计团队--%>
                                            <%--<small><i class="iconfont iconfont-clock-o"></i> 2小时</small>--%>
                                        <%--</h4>--%>
                                        <%--<p>Why not buy a new awesome theme?</p>--%>
                                    <%--</a>--%>
                                <%--</li>--%>
                                <%--<li>--%>
                                    <%--<a href="#">--%>
                                        <%--<div class="pull-left">--%>
                                            <%--<i class="iconfont icon-commenting-o"></i>--%>
                                        <%--</div>--%>
                                        <%--<h4>--%>
                                            <%--开发商--%>
                                            <%--<small><i class="iconfont iconfont-clock-o"></i> 今天</small>--%>
                                        <%--</h4>--%>
                                        <%--<p>Why not buy a new awesome theme?</p>--%>
                                    <%--</a>--%>
                                <%--</li>--%>
                            <%--</ul>--%>
                        <%--</li>--%>
                        <%--<li class="footer"><a href="#">查看全部消息</a></li>--%>
                    <%--</ul>--%>
                <%--</li>--%>
                <li class="user_info pr10 ">
                    <a href="javascript:;" title="换肤" id="control-sidebar-skin">
                        <i class="iconfont icon-icon-huanfu"></i>
                    </a>
                </li>
            </ul>
        </div>
    </nav>
</header>
<script>
    Date.prototype.Format = function (fmt) { //author: meizz
        var o = {
            "M+": this.getMonth() + 1,                 //月份
            "d+": this.getDate(),                    //日
            "h+": this.getHours(),                   //小时
            "m+": this.getMinutes(),                 //分
            "s+": this.getSeconds(),                 //秒
            "q+": Math.floor((this.getMonth() + 3) / 3), //季度
            "S": this.getMilliseconds()             //毫秒
        };
        if (/(y+)/.test(fmt))
            fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
        for (var k in o)
            if (new RegExp("(" + k + ")").test(fmt))
                fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
        return fmt;
    };
    function returnTime(time,createTime) {
        var interval;
        if(time/1000 < 10 && time/1000 >= 0) {
            //如果时间间隔小于10秒则显示“刚刚”time/10得出的时间间隔的单位是秒
            interval ="刚刚";

        } else if(time/3600000 < 24 && time/3600000 >= 0) {
            //如果时间间隔小于24小时则显示多少小时前
            var h = parseInt(time/3600000);//得出的时间间隔的单位是小时
            interval = h + "小时前";

        } else if(time/60000 < 60 && time/60000 > 0) {
            //如果时间间隔小于60分钟则显示多少分钟前
            var m = parseInt((time%3600000)/60000);//得出的时间间隔的单位是分钟
            interval = m + "分钟前";

        } else if(time/1000 < 60 && time/1000 > 0) {
            //如果时间间隔小于60秒则显示多少秒前
            var se = parseInt((time%60000)/1000);
            interval = se + "秒前";

        }else {
            interval = new Date(createTime).Format("yyyy-MM-dd hh:mm:ss");
        }
        return interval;
    };
    function personalMsg() {
        art.dialog.open('${ctx}/user/showUserv?id=' + ${YCSYS_SESSION_USER.id}, {
            id: "showUserDialog",
            title: '个人信息',
            width: 400,
            height: 500,
            lock: true
        });
    }
    ;
    function updatePwd() {
        var dialog = $.Layer.iframe(
            {
                id: "updateUserPwd",
                title: '修改密码',
                url: '${ctx}/user/updateUserPwd',
                width: 400,
                height: 140
            });
    }
    function showAllNotice(){
        var p = $(".sidebar").find("li[ycname='" + "系统监控" + "']");
        if(!p.hasClass("active")){
                p.find("a")[0].click();
        }
        var m = p.find("li[ycname='" + "平台消息" + "']");
        if(!m.hasClass("active")){
                m.find("a")[0].click();

        }
        var my = m.find("li[ycname='" + "我的消息" + "']");
        if(!my.hasClass("active'")){
            setTimeout(function(){
                $(my.find("a")[0]).trigger("click");
            },500);

        }
    }
    ;
    (function (wdn, factory) {
        if (wdn.jQuery) {
            factory(wdn.jQuery);
        }
    })(window, function ($) {
            var notice = {
                config: {
                    path: "${ctx}/platNotice/personalNotice",
                    timer: 60000,
                    laterTime: 1000,
                },
                pollTimer: null,
                getTimer: function () {
                    if (this.pollTimer) {
                        return this.pollTimer;
                    } else {
                        this.pollTimer = setInterval(function () {
                            notice.poller();
                        }, this.config.timer);
                        return this.pollTimer;
                    }
                },
                callback: function (i, data) {
                    var arr = [function (a) {
                        if (notice.success) {
                            notice.success(a);
                        }
                    }, function (a) {

                    }];
                    arr[i](data);
                },
                poller: function () {
                    setTimeout(function () {
                       notice.doAjax();
                    }, this.config.laterTime);

                },
                doAjax:function(){
                    var ts = this;
                    var _timestamp = new Date().getTime();
                    $.ajax({
                        url: ts.config.path + "?_=" + _timestamp,
                        type: "get",
                        dataType: "json",
                        success: function (data) {
                            ts.callback(0, data);
                        }, error: function (data) {
                            ts.callback(1, data);
                        }
                    });
                }
            }
            var timer = notice.getTimer();
            notice.doAjax();
            notice.success = function (res) {
                if (res.retCode) {
                    var data = res.retData;
                    var datanum = data.length ? data.length : 0;
                    $("#notice_count").html(datanum);
                    $("#notice_header").html("您有" + datanum + "条消息待查看");
                    $("#notice_menu").empty();
                    for (var x = 0; x < datanum; x++) {
                        var nt = data[x];
                        var time = returnTime((new Date().getTime() - nt.createTime),nt.createTime);
                        var nte = nt.title + "<small><i class=\"iconfont iconfont-clock-o\"></i>" + time + "</small>";
                        var msg = $("<li/>").append($("<a/>", {"href": "javascript:;","onclick":"indexNoticeDetail('" + nt.id + "','" + nt.title + "','" + nt.content + "','" + nt.createTime + "','" + nt.types + "','" + nt.status + "')"})
                            .append($("<div/>", {"class": "pull-left"}).append($("<i/>", {"class": "iconfont icon-commenting-o"})))
                            .append($("<h4/>").html(nte))
                            .append($("<p/>").html(nt.content.length>15?nt.content.substring(0,15) + "...":nt.content))
                        );
                        $("#notice_menu").append(msg);
                    }
                }
            }
        }
    );

    function indexNoticeDetail(id,title,content,createTime,types,status){
        var titles = "<input value='" + title + "' disabled>";
        var times = "<input value='" + new Date(parseInt(createTime)).Format("yyyy-MM-dd hh:mm:ss") + "' disabled>";
        var typess = "<input value='" + types + "' disabled>";
        var rows =  Math.ceil(content.length/10);
        var contents = "<textarea disabled rows='" + rows + "'>" + content + "</textarea>";
        var ct = "<table class='date_add_table'><tr><td class='t_r'>消息标题：</td><td>" + titles + "</td></tr>" +
                "<tr><td class='t_r'>接收时间:</td><td>" + times + "</td></tr>" +
                "<tr><td class='t_r'>消息类型:</td><td>" + types + "</td></tr>" +
                "<tr><td class='t_r'>消息内容:</td><td>" + contents + "</td></tr>" +
            "</table>";
        var btn ='<div class="aui_buttons" style="border-top:0;background-color:unset;margin-left: -50px;"><button class="bt_sub aui_state_highlight" type="button" onclick="indexNoticeClose()">关闭</button></div>';
        ct = ct + btn;
        if(status == 1 || status == "1"){
            $.ajax({
                url:"${ctx}/platNotice/updateReceiveStatus",
                data:{id:id},
                type:"post",
                success:function(){}
            });
        }
        var dialog = art.dialog({
            id:"indexNotice",
            title: '消息详情',
            content: ct,
            height:500,
            lock:true,
            width:400
        });
    };

    function indexNoticeClose(){
        var dialog = art.dialog.list["indexNotice"];
        dialog.close();
    }

</script>