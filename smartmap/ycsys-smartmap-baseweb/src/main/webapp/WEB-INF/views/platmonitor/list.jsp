<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/views/common/taglib.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>羽辰智慧林业综合管理平台-资源管理</title>
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <!-- Bootstrap 3.3.6 -->
    <link rel="stylesheet" href="${res}/bootstrap/css/bootstrap.css">
    <!-- iconfont -->
    <link rel="stylesheet" href="${res}/iconfont/iconfont.css">
    <!-- Theme style -->
    <link rel="stylesheet" href="${res}/dist/css/AdminLTE.css">
    <!-- AdminLTE Skins. Choose a skin from the css/skins
           folder instead of downloading all of them to reduce the load. -->
    <link rel="stylesheet" href="${res}/dist/css/skins/_all-skins.css">
    <!-- iCheck -->
    <link rel="stylesheet" href="${res}/plugins/iCheck/flat/blue.css">
    <!-- list -->
    <link href="${res}/plugins/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css"/>
    <!-- 弹出框 -->
    <link href="${res}/plugins/dialog/dialog.css" rel="stylesheet" type="text/css">

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
    <style>
        html, body {
            background-color: #f1f1f1;
        }

        body {
            overflow-y: auto;
        }

        .box-solid .bluedon {
            background-color: #46c99d;
            text-align: center;
            color: #fff;
        }

        .box-solid .bluedon01 {
            background-color: #f1f1f1;
            text-align: center;
            color: #46c99d;
        }
    </style>
</head>
<body>
<!-- Content Header (Page header) -->
<section class="content-header">
    <h1>
        平台监控
    </h1>
</section>

<div class="row">
    <div class="col-md-3 col-sm-4">
        <div class="box box-solid">
            <div class="box-header with-border">
                <h4 class="box-title">监控列表</h4>
            </div>
            <div class="box_l">
                <ul id="tree">

                </ul>
            </div>
            <!-- /.box-body -->
        </div>
    </div>
    <!-- /.col -->
    <div class="col-md-9 col-sm-8">
        <div class="box box-solid" id="box_content">
            <!--服务器监控-->
            <div id="service_monitor_content" style="display:none">
                <div class="box-header with-border" style="padding-top:20px;">
                    <h4 class="box-title02">系统信息</h4>
                </div>
                <div class="table-responsive">
                    <table class="table table-bordered">
                        <tr>
                            <th class="bluedon">系统运行时间</th>
                            <th class="bluedon">计算机名</th>
                        </tr>
                        <tr id="system_info">
                        </tr>
                    </table>
                </div>
                <div class="box-header with-border">
                    <h4 class="box-title02">硬盘信息</h4>
                </div>
                <div class="table-responsive">
                    <table class="table table-bordered">
                        <thead>
                        <tr>
                            <th class="bluedon01">盘符</th>
                            <th class="bluedon01">硬盘容量</th>
                            <th class="bluedon01">硬盘空闲容量</th>
                            <th class="bluedon01">硬盘已用容量</th>
                            <th class="bluedon01">硬盘使用率</th>
                        </tr>
                        </thead>
                        <tbody id="disk_info">
                        </tbody>
                    </table>
                </div>
                <div class="box-header with-border">
                    <h4 class="box-title02">CPU信息</h4>
                </div>
                <div class="table-responsive">
                    <table class="table table-bordered">
                        <tr>
                            <th class="bluedon01">CPU核数</th>
                            <th class="bluedon01">CPU使用率</th>
                            <th class="bluedon01">CPU空闲率</th>
                        </tr>
                        <tr id="cpu_info">
                        </tr>
                    </table>
                </div>
                <div class="box-header with-border">
                    <h4 class="box-title02">内存信息</h4>
                </div>
                <div class="table-responsive">
                    <table class="table table-bordered">
                        <tr>
                            <th class="bluedon01">内存总大小</th>
                            <th class="bluedon01">内存空闲量</th>
                            <th class="bluedon01">内存使用量</th>
                            <th class="bluedon01">内存使用率</th>
                        </tr>
                        <tr id="memory_info">
                        </tr>
                    </table>
                </div>

                <div id="cpuChart" style="height: 350px;overflow: hidden;width: 100%;"></div>
                <div id="netChart" style="height: 350px;overflow: hidden;width: 100%;"></div>
                <div id="netflowChart" style="height: 350px;overflow: hidden;width: 100%;"></div>
            </div>
            <!--Tomcat监控-->
            <div id="tomcat_monitor_content" style="display:none">
                <div class="box-header with-border" style="padding-top:20px;">
                    <h4 class="box-title02">JVM内存</h4>
                </div>
                <div class="table-responsive">
                    <table class="table table-bordered">
                        <tr>
                            <th class="bluedon">空闲内存</th>
                            <th class="bluedon">总内存</th>
                            <th class="bluedon">最大可使用内存</th>
                        </tr>
                        <tr id="jvm_info">
                        </tr>
                    </table>
                </div>
                <div class="box-header with-border">
                    <h4 class="box-title02">Tomcat线程</h4>
                </div>
                <div class="table-responsive">
                    <table class="table table-bordered">
                        <thead>
                        <tr>
                            <th class="bluedon01">最大线程数</th>
                            <th class="bluedon01">当前线程数</th>
                            <th class="bluedon01">当前繁忙线程数</th>
                        </tr>
                        </thead>
                        <tbody id="thread_info">
                        </tbody>
                    </table>
                </div>
                <div class="box-header with-border">
                    <h4 class="box-title02">Tomcat请求</h4>
                </div>
                <div class="table-responsive">
                    <table class="table table-bordered">
                        <tr>
                            <th class="bluedon01">总请求数</th>
                            <th class="bluedon01">最大处理时间</th>
                            <th class="bluedon01">平均处理时间</th>
                            <th class="bluedon01">接收字节</th>
                            <th class="bluedon01">发送字节</th>
                        </tr>
                        <tr id="request_info">
                        </tr>
                    </table>
                </div>
            </div>
        </div>

        <!--tomcat监控-->
        <!-- /.col -->
    </div>
</div>

</body>
<script src="${res}/plugins/jQuery/jquery-2.2.3.min.js"></script>
<script src="${res}/plugins/ligerUI/js/core/base.js" type="text/javascript"></script>
<script src="${res}/plugins/ligerUI/js/plugins/ligerGrid.js" type="text/javascript"></script>
<script src="${res}/plugins/ligerUI/js/plugins/ligerDrag.js" type="text/javascript"></script>
<script src="${res}/plugins/ligerUI/js/plugins/ligerMenu.js" type="text/javascript"></script>
<script src="${res}/plugins/ligerUI/js/plugins/ligerTree.js" type="text/javascript"></script>
<script src="${res}/plugins/ligerUI/js/plugins/CustomersData.js" type="text/javascript"></script>
<script src="${res}/bootstrap/js/bootstrap.min.js"></script>
<script src="${res}/dist/js/app.js"></script>
<script src="${res}/dist/js/demo.js"></script>
<script type="text/javascript" src="${res}/plugins/dialog/jquery.artDialog.source.js"></script>
<script type="text/javascript" src="${res}/plugins/dialog/iframeTools.source.js"></script>
<script type="text/javascript" src="${res}/plugins/dialog/unit.js"></script>
<script src="${res}/plugins/echarts/echarts-all.js"></script>
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
    function toDecimal(x) {
        var f = parseFloat(x);
        if (isNaN(f)) {
            return;
        }
        f = Math.round(f * 100) / 100;
        return f;
    }
    function toGB(x) {
        return toDecimal(x) + " GB";
    }
    function mBToGB(x) {
        var f = parseFloat(x) / 1024;
        if (isNaN(f)) {
            return;
        }
        f = Math.round(f * 100) / 100;
        return f + " GB";
    }
    function toPercentage(x) {
        return toDecimal(x) + " %";
    }
    $(function () {
        var data = [];
        var suffix = "type_";
        data.push({id: suffix + "1", pid: -1, text: '服务器监控'});
        data.push({id: suffix + "2", pid: -1, text: 'Tomcat监控'});
        data.push({id: suffix + "3", pid: -1, text: 'Oracle监控'});
        data.push({id: suffix + "4", pid: -1, text: 'Arcgis监控'});
        $.ajax({
            url: "${ctx}/platmonitor/getMonitorServices",
            type: "get",
            dataType: "json",
            async: false,
            success: function (d) {
                for (var x = 0; x < d.length; x++) {
                    var dt = d[x];
                    data.push({id: dt.id, pid: suffix + dt.monitorType, text: dt.name});
                }
            }
        });
        $("#tree").ligerTree({
            nodeWidth: 160,
            data: data,
            checkbox: false,
            idFieldName: 'id',
            isExpand: true,
            slide: false,
            parentIDFieldName: 'pid',
            onClick: function (a) {
                $("#service_monitor_content").hide();
                $("#tomcat_monitor_content").hide();
                //能点击的
                if (a.data.id > 0) {
                    $.ajax({
                        url: "${ctx}/platmonitor/getMonitorServiceData?id=" + a.data.id,
                        type: "get",
                        dataType: "json",
                        success: function (res) {
                            console.log(res);
                            var dt = res.retData;
                            if (dt) {
                                //服务器监控
                                if (dt.type == "1") {
                                    serverMonitor(dt);
                                    //tomcat监控
                                } else if (dt.type == "2") {
                                    tomcatMonitor(dt);
                                }
                            } else {
                            }
                        }
                    });
                }
            }
        });
        var m = $("#tree").ligerGetTreeManager();
        var fir = m.getDataByID(suffix + "1").children[0];
        m.getNodeDom(fir).click();
    });
    function resizeIframe(i) {
        var parent = i.contentWindow.parent.parent;
        var aside = parent.$(".main-sidebar");
        var header = parent.$(".main-header");
        var ih = aside.height() - header.height();
        i.height = ih;
    }

    var coption = {
        tooltip: {
            trigger: 'axis'
        },
        toolbox: {
            show: true,
            feature: {
                mark: {show: true},
                dataView: {show: true, readOnly: false},
                magicType: {show: true, type: ['line', 'bar']},
                restore: {show: true},
                saveAsImage: {show: true}
            }
        }, color: ['#90d3ce', '#38a4dd', '#26bf8c', '#ff7f50', '#87cefa', '#da70d6', '#32cd32', '#6495ed',
            '#ff69b4', '#ba55d3', '#cd5c5c', '#ffa500', '#40e0d0',
            '#1e90ff', '#ff6347', '#7b68ee', '#00fa9a', '#ffd700',
            '#6b8e23', '#ff00ff', '#3cb371', '#b8860b', '#30e0e0']
    };
    function serverMonitor(dt) {
        var $sysInfo = $("#system_info");
        var sysInfo = dt.systemInfo;
        $sysInfo.empty();
        $sysInfo.append($("<td/>").html(sysInfo.sysUpTime)).append($("<td/>").html(sysInfo.sysName));
        var $diskInfo = $("#disk_info");
        $diskInfo.empty();
        var diskInfo = dt.diskInfo;
        for (var x = 0; x < diskInfo.length; x++) {
            var r = $("<tr/>");
            var des = diskInfo[x].diskDesc.split(" ");
            r.append($("<td/>").html(diskInfo[x].diskDesc.split("\\")[0]))
                .append($("<td/>").html(toGB(diskInfo[x].diskSize)))
                .append($("<td/>").html(toGB(diskInfo[x].diskFreeSize)))
                .append($("<td/>").html(toGB(diskInfo[x].diskUsedSize)))
                .append($("<td/>").html(toPercentage(diskInfo[x].percentUsed)));
            $diskInfo.append(r);
        }
        var $cpuInfo = $("#cpu_info");
        var cpuInfo = dt.cpuInfo;
        $cpuInfo.empty();
        $cpuInfo.append($("<td/>").html(cpuInfo.coreNum)).append($("<td/>").html(toPercentage(cpuInfo.sysRate))).append($("<td/>").html(toPercentage(cpuInfo.freeRate)));
        var $memoryInfo = $("#memory_info");
        var memoryInfo = dt.memoryInfo;
        $memoryInfo.empty();
        $memoryInfo.append($("<td/>").html(mBToGB(memoryInfo.memorySize))).append($("<td/>").html(mBToGB(memoryInfo.memoryFreeSize))).append($("<td/>").html(mBToGB(memoryInfo.memoryUsedSize))).append($("<td/>").html(toPercentage(memoryInfo.memoryPercentage)));
        var $cpuChart = $("#cpuChart");
        $cpuChart.html("").removeAttr("_echarts_instance_");
        var $netChart = $("#netChart");
        $netChart.html("").removeAttr("_echarts_instance_");
        var $netflowChart = $("#netflowChart");
        $netflowChart.html("").removeAttr("_echarts_instance_");
        $("#service_monitor_content").show();
        if (dt.chartData && dt.chartData.length > 0) {
            var xlist = [],cpu_ylist = [],net_yGetpacklist = [],net_ySendpacklist = [],net_yInlist = [],net_yOutlist = [];
            for (var x in dt.chartData.reverse()) {
                xlist.push(new Date(dt.chartData[x].monitorTime).Format("hh:mm:ss"));
                cpu_ylist.push(toPercentage(dt.chartData[x].cpuUsedRate));
                net_ySendpacklist.push(dt.chartData[x].sendPackage);
                net_yGetpacklist.push(dt.chartData[x].receivePackage);
                net_yInlist.push(dt.chartData[x].recByte);
                net_yOutlist.push(dt.chartData[x].sendByte);
            }
            var cpuchart = echarts.init($cpuChart[0], 'macarons');
            var netchart = echarts.init($netChart[0], 'macarons');
            var netflowchart = echarts.init($netflowChart[0], 'macarons');
            cpuchart.setOption($.extend(coption, {
                title: {
                    text: 'CPU使用信息',
                }, legend: {
                    data: ['CPU使用率']
                }, calculable: true,
                xAxis: [
                    {
                        type: 'category',
                        boundaryGap: false,
                        data: xlist
                    }
                ],
                yAxis: [
                    {
                        type: 'value',
                        axisLabel: {
                            formatter: '{value} %'
                        }
                    }
                ],
                series: [
                    {
                        name: 'CPU使用率',
                        type: 'line',
                        data: cpu_ylist,
                        markPoint: {
                            data: [
                                {type: 'max', name: '最大值'},
                                {type: 'min', name: '最小值'}
                            ]
                        },
                        markLine: {
                            data: [
                                {type: 'average', name: '平均值'}
                            ]
                        }
                    }
                ]
            }));
            netchart.setOption($.extend(coption, {
                title: {
                    text: '网络包裹信息',
                }, legend: {
                    data: ['网络接受的总包裹量', '网络发送的总包裹量']
                }, calculable: true,
                xAxis: [
                    {
                        type: 'category',
                        boundaryGap: false,
                        data: xlist
                    }
                ], yAxis: [
                    {
                        type: 'value',
                        axisLabel: {
                            formatter: '{value}M'
                        }
                    }
                ], series: [
                    {
                        name: '网络接受的总包裹量',
                        type: 'line',
                        data: net_yGetpacklist,
                        markPoint: {
                            data: [
                                {type: 'max', name: '最大值'},
                                {type: 'min', name: '最小值'}
                            ]
                        }
                    },
                    {
                        name: '网络发送的总包裹量',
                        type: 'line',
                        data: net_ySendpacklist,
                        markPoint: {
                            data: [
                                {name: '周最低', value: -2, xAxis: 1, yAxis: -1.5}
                            ]
                        }
                    }
                ]
            }));
            netflowchart.setOption($.extend(coption, {
                title: {
                    text: '网络流量信息',
                }, legend: {
                    data: ['网络接受的流量', '网络发送的流量']
                }, xAxis: [
                    {
                        type: 'category',
                        boundaryGap: false,
                        data: xlist
                    }
                ],
                yAxis: [
                    {
                        type: 'value',
                        axisLabel: {
                            formatter: '{value}M'
                        }
                    }
                ],
                series: [
                    {
                        name: '网络接受的流量',
                        type: 'line',
                        data: net_yInlist,
                        markPoint: {
                            data: [
                                {type: 'max', name: '最大值'},
                                {type: 'min', name: '最小值'}
                            ]
                        }
                    },
                    {
                        name: '网络发送的流量',
                        type: 'line',
                        data: net_yOutlist,
                        markPoint: {
                            data: [
                                {name: '周最低', value: -2, xAxis: 1, yAxis: -1.5}
                            ]
                        }
                    }
                ]
            }));
        }
    }
    ;
    function tomcatMonitor(dt) {
        if (dt.jvmMemoryInfo) {
            var $jvm = $("#jvm_info");
            var jvm = dt.jvmMemoryInfo;
            $jvm.empty();
            $jvm.append($("<td/>").html(jvm.free)).append($("<td/>").html(jvm.total)).append($("<td/>").html(jvm.max));
            var $thread = $("#thread_info");
            var thread = dt.threadInfo;
            $thread.empty();
            $thread.append($("<td/>").html(thread.maxThreads)).append($("<td/>").html(thread.currentThreadCount)).append($("<td/>").html(thread.currentThreadsBusy));
            var $request = $("#request_info");
            var request = dt.requestInfo;
            $request.empty();
            $request.append($("<td/>").html(request.requestCount)).append($("<td/>").html(request.maxTime)).append($("<td/>").html(request.processingTime))
                .append($("<td/>").html(request.bytesReceived)).append($("<td/>").html(request.bytesSent));
            $("#tomcat_monitor_content").show();
        }
    }
</script>
</html>