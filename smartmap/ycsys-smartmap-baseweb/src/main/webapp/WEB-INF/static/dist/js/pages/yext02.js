/* 
* @Author: Marte
* @Date:   2016-11-30 19:03:58
* @Last Modified by:   Marte
* @Last Modified time: 2016-11-30 20:15:24
*/

$(document).ready(function(){
      var myChart = echarts.init(document.getElementById('chart'),'macarons');
      var myChart2 = echarts.init(document.getElementById('chart2'),'macarons');

      option = {
            title : {
                text: '某站点用户访问来源',
                subtext: '纯属虚构',
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
                text: '运维系统操作统计'
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

      myChart.setOption(option);
      myChart2.setOption(option2);

          $(function () {
            $("#maingrid4").ligerGrid({
                checkbox: true,
                columns: [
                { display: '统计次数', name: 'CustomerID', minwidth: 100 },
                { display: '统计分类', name: 'ContactName', minWidth: 100 }
                ], pageSize:10,
                data:CustomersData,
                usePager: false,
                width: '100%',height:'300px'
            });
            $("#pageloading").hide(); 
        });

          $(function () {
            $("#maingrid5").ligerGrid({
                checkbox: true,
                columns: [
                { display: '统计次数', name: 'CustomerID', minwidth: 100 },
                { display: '统计分类', name: 'ContactName', minWidth: 100 }
                ], pageSize:10,
                data:CustomersData,
                usePager: false,
                width: '100%',height:'300px'
            });
            $("#pageloading").hide(); 
        });

});