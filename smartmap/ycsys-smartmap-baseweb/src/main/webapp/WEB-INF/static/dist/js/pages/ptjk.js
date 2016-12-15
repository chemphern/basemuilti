/* 
* @Author: Marte
* @Date:   2016-11-30 19:03:58
* @Last Modified by:   Marte
* @Last Modified time: 2016-12-05 15:30:31
*/

$(document).ready(function(){
      var myChart = echarts.init(document.getElementById('chart'),'macarons');
      var myChart1 = echarts.init(document.getElementById('chart1'),'macarons');
      option = {
    title : {
        text: 'CPU参数信息',
    },
    tooltip : {
        trigger: 'axis'
    },
    legend: {
        data:['CPU总使用率','CPU用户使用率']
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
            data : ['周一','周二','周三','周四','周五','周六','周日']
        }
    ],
    yAxis : [
        {
            type : 'value',
            axisLabel : {
                formatter: '{value} %'
            }
        }
    ],
    series : [
        {
            name:'CPU总使用率',
            type:'line',
            data:[31, 31, 35, 33, 22, 23, 30],
            markPoint : {
                data : [
                    {type : 'max', name: '最大值'},
                    {type : 'min', name: '最小值'}
                ]
            },
            markLine : {
                data : [
                    {type : 'average', name: '平均值'}
                ]
            }
        },
        {
            name:'CPU用户使用率',
            type:'line',
            data:[11, 12, 12, 15, 13, 12, 12],
            markPoint : {
                data : [
                    {name : '周最低', value : -2, xAxis: 1, yAxis: -1.5}
                ]
            },
            markLine : {
                data : [
                    {type : 'average', name : '平均值'}
                ]
            }
        }
    ]
      };
      option1 = {
            title : {
                text: '网络包裹流量信息',
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
                    data : ['周一','周二','周三','周四','周五','周六','周日']
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
                    data:[31, 31, 35, 33, 22, 23, 30],
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
                    data:[11, 12, 12, 15, 13, 12, 12],
                    markPoint : {
                        data : [
                            {name : '周最低', value : -2, xAxis: 1, yAxis: -1.5}
                        ]
                    }
                }
            ]
      };
                    
      myChart.setOption(option);
      myChart1.setOption(option1);
});