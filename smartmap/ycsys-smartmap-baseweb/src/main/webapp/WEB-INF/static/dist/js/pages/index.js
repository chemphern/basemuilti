/* 
* @Author: Marte
* @Date:   2016-11-30 19:03:58
* @Last Modified by:   Marte
* @Last Modified time: 2016-12-07 20:14:40
*/

$(document).ready(function(){
      var myChart1 = echarts.init(document.getElementById('index-chart1'),'macarons');
      var myChart2 = echarts.init(document.getElementById('index-chart2'),'macarons');
      var myChart3 = echarts.init(document.getElementById('index-chart3'),'macarons');

      option1 = {
            title : {
                text: '热点服务总计访问量',
                subtext: '',
                x:'center'
            },
            tooltip : {
                trigger: 'item',
                formatter: "{a} <br/>{b} : {c} ({d}%)"
            },
            legend: {
                orient : 'vertical',
                x : 'left',
                data:['直接访问','邮件营销','联盟广告','视频广告','搜索引擎']
            },
            toolbox: {
                show : false,
                feature : {
                    mark : {show: true},
                    dataView : {show: true, readOnly: false},
                    magicType : {
                        show: true, 
                        type: ['pie', 'funnel'],
                        option: {
                            funnel: {
                                x: '25%',
                                width: '50%',
                                funnelAlign: 'left',
                                max: 1548
                            }
                        }
                    },
                    restore : {show: true},
                    saveAsImage : {show: true}
                }
            },
            calculable : true,
            series : [
                {
                    name:'访问来源',
                    type:'pie',
                    radius : '55%',
                    center: ['50%', '60%'],
                    data:[
                        {value:335, name:'直接访问'},
                        {value:310, name:'邮件营销'},
                        {value:234, name:'联盟广告'},
                        {value:135, name:'视频广告'},
                        {value:1548, name:'搜索引擎'}
                    ]
                }
            ]
      };
      option2 = {
            title : {
                text: '热点服务最近访问量'
            },
            tooltip : {
                trigger: 'axis'
            },
            legend: {
                data:['统计次数（次）']
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
                    data : ['usa2','USA','usa','POIG','CQTE','Maps','Web','tile','smap','CQSE']
                }
            ],
            yAxis : [
                {
                    type : 'value'
                }
            ],
            series : [
                {
                    name:'统计次数（次）',
                    type:'bar',
                    data:[2.0, 4.9, 7.0, 23.2, 25.6, 76.7, 135.6, 162.2, 32.6, 20.0],
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
                }
            ]
      };
      option3 = {
            title : {
                text: '服务管理'
            },
            tooltip : {
                trigger: 'axis'
            },
            legend: {
                data:[ '']
            },
            toolbox: {
                show : false,
                feature : {
                    mark : {show: true},
                    dataView : {show: true, readOnly: false},
                    magicType: {show: true, type: ['line', 'bar']},
                    restore : {show: true},
                    saveAsImage : {show: true}
                }
            },
            calculable : true,
            xAxis : [
                {
                    type : 'value',
                    boundaryGap : [0, 0.01]
                }
            ],
            yAxis : [
                {
                    type : 'category',
                    data : ['服务访问成功率','服务访问速度','系统运行']
                }
            ],
            series : [
                {
                    type:'bar',
                    data:[25, 38, 68]
                }
            ]
      };
                    

      myChart1.setOption(option1);
      myChart2.setOption(option2);
      myChart3.setOption(option3);

});