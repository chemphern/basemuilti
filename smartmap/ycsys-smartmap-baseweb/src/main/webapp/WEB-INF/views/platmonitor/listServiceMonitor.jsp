<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/views/common/taglib.jsp" %>
<html>
<head>
    <title>Title</title>
    <script src="${res}/plugins/jQuery/jquery-2.2.3.min.js"></script>
    <script src="${res}/plugins/echarts/echarts-all.js"></script>
</head>
<body>
<c:if test="${msg ==null}">
    硬盘信息：
    <c:forEach var="i" items="${diskInfo}">
        盘符:${i.diskName} || 卷标名：${i.diskLabel} || 序列号： ${i.diskSN} || 硬盘容量：${i.diskSize}
        || 硬盘空闲容量 ：${i.diskFreeSize} || 硬盘已用容量:${i.diskUsedSize} || 硬盘已用百分比: ${i.percentUsed} ||硬盘描述:${i.diskDesc}
        <br>
    </c:forEach>
    <br>
    系统信息
    系统描述:${systemInfo.sysDesc}||系统运行时间：${systemInfo.sysUpTime}||系统联系人：${systemInfo.sysContact}||计算机名：${systemInfo.sysName}
    ||计算机位置：${systemInfo.sysLocation}
    <br>
    内存信息：
    <br>
    内存总大小:${memoryInfo.memorySize} ||内存空闲量:${memoryInfo.memoryFreeSize} || 内存使用量:${memoryInfo.memoryUsedSize} ||内存使用率:${memoryInfo.memoryPercentage}
    <br>
    Cpu信息:
    <br>
    cpu信息描述:${cpuInfo.cpuDesc} ||cpu核数:${cpuInfo.coreNum} ||cpu使用率 ${cpuInfo.sysRate}||cpu空闲率${cpuInfo.freeRate}
</c:if>
<c:if test="${msg != null}">
    ${msg}
</c:if>
<br>
监控图表:
<br>
<c:if test="${cpu_xlist != null}">
    <div id="cpuChart" style="height: 350px;overflow: hidden;width: 100%;"></div>
    <div id="netChart" style="height: 350px;overflow: hidden;width: 100%;"></div>
    <div id="netflowChart" style="height: 350px;overflow: hidden;width: 100%;"></div>
    <script>
        window.onload = function () {
            var cpuchart = echarts.init(document.getElementById('cpuChart'), 'macarons');
            var netchart = echarts.init(document.getElementById('netChart'), 'macarons');
            var netflowchart = echarts.init(document.getElementById('netflowChart'), 'macarons');
            var option = {
                title: {
                    text: 'CPU使用信息',
                },
                tooltip: {
                    trigger: 'axis'
                },
                legend: {
                    data: ['CPU使用率']
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
                },
                calculable: true,
                xAxis: [
                    {
                        type: 'category',
                        boundaryGap: false,
                        data: ${cpu_xlist}
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
                        data:${cpu_ylist},
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
                ],color:['#90d3ce','#38a4dd','#26bf8c','#ff7f50', '#87cefa', '#da70d6', '#32cd32', '#6495ed',
                    '#ff69b4', '#ba55d3', '#cd5c5c', '#ffa500', '#40e0d0',
                    '#1e90ff', '#ff6347', '#7b68ee', '#00fa9a', '#ffd700',
                    '#6b8e23', '#ff00ff', '#3cb371', '#b8860b', '#30e0e0']
            };
            var option1 = {
                title : {
                    text: '网络包裹信息',
                },
                tooltip : {
                    trigger: 'axis'
                },
                legend: {
                    data:['网络接受的总包裹量','网络发送的总包裹量']
                },
                toolbox: {
                    show : true,
                    feature : {
                        mark : {show: true},
                        dataView : {show: true, readOnly: false},
                        magicType : {show: true, type: ['line', 'bar']},
                        restore : {show: true},
                        saveAsImage : {show: true}
                    }
                },
                calculable : true,
                xAxis : [
                    {
                        type : 'category',
                        boundaryGap : false,
                        data : ${net_xlist}
                    }
                ],
                yAxis : [
                    {
                        type : 'value',
                        axisLabel : {
                            formatter: '{value}M'
                        }
                    }
                ],
                series : [
                    {
                        name:'网络接受的总包裹量',
                        type:'line',
                        data:${net_yGetpacklist},
                        markPoint : {
                            data : [
                                {type : 'max', name: '最大值'},
                                {type : 'min', name: '最小值'}
                            ]
                        }
                    },
                    {
                        name:'网络发送的总包裹量',
                        type:'line',
                        data:${net_ySendpacklist},
                        markPoint : {
                            data : [
                                {name : '周最低', value : -2, xAxis: 1, yAxis: -1.5}
                            ]
                        }
                    }
                ],color:['#90d3ce','#26bf8c','#38a4dd','#ff7f50', '#87cefa', '#da70d6', '#32cd32', '#6495ed',
                    '#ff69b4', '#ba55d3', '#cd5c5c', '#ffa500', '#40e0d0',
                    '#1e90ff', '#ff6347', '#7b68ee', '#00fa9a', '#ffd700',
                    '#6b8e23', '#ff00ff', '#3cb371', '#b8860b', '#30e0e0']
            };
            var option2 = {
                title : {
                    text: '网络流量信息',
                },
                tooltip : {
                    trigger: 'axis'
                },
                legend: {
                    data:['网络接受的流量','网络发送的流量']
                },
                toolbox: {
                    show : true,
                    feature : {
                        mark : {show: true},
                        dataView : {show: true, readOnly: false},
                        magicType : {show: true, type: ['line', 'bar']},
                        restore : {show: true},
                        saveAsImage : {show: true}
                    }
                },
                calculable : true,
                xAxis : [
                    {
                        type : 'category',
                        boundaryGap : false,
                        data : ${net_xlist}
                    }
                ],
                yAxis : [
                    {
                        type : 'value',
                        axisLabel : {
                            formatter: '{value}M'
                        }
                    }
                ],
                series : [
                    {
                        name:'网络接受的流量',
                        type:'line',
                        data:${net_yInlist},
                        markPoint : {
                            data : [
                                {type : 'max', name: '最大值'},
                                {type : 'min', name: '最小值'}
                            ]
                        }
                    },
                    {
                        name:'网络发送的流量',
                        type:'line',
                        data:${net_yOutlist},
                        markPoint : {
                            data : [
                                {name : '周最低', value : -2, xAxis: 1, yAxis: -1.5}
                            ]
                        }
                    }
                ],color:['#90d3ce','#38a4dd','#26bf8c','#ff7f50', '#87cefa', '#da70d6', '#32cd32', '#6495ed',
                    '#ff69b4', '#ba55d3', '#cd5c5c', '#ffa500', '#40e0d0',
                    '#1e90ff', '#ff6347', '#7b68ee', '#00fa9a', '#ffd700',
                    '#6b8e23', '#ff00ff', '#3cb371', '#b8860b', '#30e0e0']
            };
            cpuchart.setOption(option);
            netchart.setOption(option1);
            netflowchart.setOption(option2);
        }
    </script>
</c:if>
<c:if test="${cpu_xlist == null}">
    未启动监控！请先启动监控
</c:if>
</body>
</html>
