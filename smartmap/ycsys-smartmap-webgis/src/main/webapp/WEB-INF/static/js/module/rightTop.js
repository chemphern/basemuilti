/**
 * Created by lixiaoxin on 2017/1/19.
 */
(function(wdn,factory){
    if(wdn.jQuery){
        factory(wdn.jQuery);
    }
})(window,function($){
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
    $(function(){
        var msg = $("#per_msg");
        var pwd = $("#per_pwd");
        var exit = $("#per_exit");
        msg.click(function(){
            art.dialog.open(window.ctx + '/user/showUserv?id=' + window.userId, {
                id: "showUserDialog",
                title: '个人信息',
                width: 400,
                height: 500,
                lock: true
            });
        });
        pwd.click(function(){
            var dialog = $.Layer.iframe(
                {
                    id: "updateUserPwd",
                    title: '修改密码',
                    url: window.ctx + '/user/updateUserPwd',
                    width: 400,
                    height: 140
                });
        });
        exit.click(function(){
            window.location.href = window.ctx + "/logout";
        });

        var notice = {
            config: {
                path: window.ctx + "/platNotice/personalNotice",
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
                var numhtml = "您有<span class=\"orange\">" + datanum + "</span>条消息待查看";
                var showAll = $("<a/>",{"href":"javascript:;"}).html("全部+");
                showAll.click(function(){
                    window.showNoticeDetail();
                });
                $("#notice_header").html(numhtml);
                $("#notice_header").append(showAll);
                $("#notice_menu").empty();
                for (var x = 0; x < datanum; x++) {
                    var nt = data[x];
                    var time = returnTime((new Date().getTime() - nt.createTime),nt.createTime);
                    var nte = nt.title + "<small style='color: #999999;font-size: 10px;position: absolute;right: 0;top: 0;'><i class=\"iconfont iconfont-clock-o\"></i>" + time + "</small>";
                    var msg = $("<li/>").append($("<a/>", {"href": "javascript:;","onclick":"indexNoticeDetail('" + nt.id + "','" + nt.title + "','" + nt.content + "','" + nt.createTime + "','" + nt.types + "','" + nt.status + "')"})
                        .append($("<div/>", {"class": "pull-left"}).append($("<i/>", {"class": "iconfont icon"}).html("&#xe6a4")))
                        .append($("<h4/>").html(nte))
                        .append($("<p/>").html(nt.content.length>15?nt.content.substring(0,15) + "...":nt.content))
                    );
                    $("#notice_menu").append(msg);
                }
            }
        }
    });

    window.indexNoticeDetail = function(id,title,content,createTime,types,status){
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
                url:window.ctx + "/platNotice/updateReceiveStatus",
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
    window.indexNoticeClose = function(){
        var dialog = art.dialog.list["indexNotice"];
        dialog.close();
    };
    window.showNoticeDetail = function(){
        $.Layer.iframe(
            {
                id:"listNotices",
                title: "全部消息",
                url:window.ctx + "/platNotice/myNotice",
                width: 1020,
                height: 700,
                button:[]
            });
    };
});