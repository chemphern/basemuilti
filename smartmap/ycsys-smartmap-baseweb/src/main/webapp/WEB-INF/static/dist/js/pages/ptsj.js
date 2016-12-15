/* 
* @Author: Marte
* @Date:   2016-11-30 19:03:58
* @Last Modified by:   Marte
* @Last Modified time: 2016-12-07 11:10:23
*/

$(document).ready(function(){
      var myChart = echarts.init(document.getElementById('chart'),'macarons');
      option = {
    title : {
        text : 'Session信息统计'
    },
    tooltip : {
        trigger: 'item',
        formatter : function (params) {
            var date = new Date(params.value[0]);
            data = date.getFullYear() + '-'
                   + (date.getMonth() + 1) + '-'
                   + date.getDate() + ' '
                   + date.getHours() + ':'
                   + date.getMinutes();
            return data + '<br/>'
                   + params.value[1] + ', ' 
                   + params.value[2];
        }
    },
    toolbox: {
        show : false,
        feature : {
            mark : {show: true},
            dataView : {show: true, readOnly: false},
            restore : {show: true},
            saveAsImage : {show: true}
        }
    },
    dataZoom: {
        show: true,
        start : 70
    },
    legend : {
        data : ['Session']
    },
    grid: {
        y2: 80
    },
    xAxis : [
        {
            type : 'time',
            splitNumber:10
        }
    ],
    yAxis : [
        {
            type : 'value'
        }
    ],
    series : [
        {
            name: 'Session',
            type: 'line',
            showAllSymbol: true,
            symbolSize: function (value){
                return Math.round(value[2]/10) + 2;
            },
            data: (function () {
                var d = [];
                var len = 0;
                var now = new Date();
                var value;
                while (len++ < 200) {
                    d.push([
                        new Date(2014, 9, 1, 0, len * 10000),
                        (Math.random()*30).toFixed(2) - 0,
                        (Math.random()*100).toFixed(2) - 0
                    ]);
                }
                return d;
            })()
        }
    ]
      };
      myChart.setOption(option);

          $(function () {
            $("#maingrid4").ligerGrid({
                checkbox: true,
                columns: [
                { display: '数据库服务器名称', name: 'CustomerID', minwidth: 100 },
                { display: '当前登陆会话数最大值发生时间', name: 'CustomerID', minwidth: 100 },
                { display: '当前登陆会话数最大值', name: 'CustomerID', minwidth: 100 },
                { display: '当前登陆会话数最小值发生时间', name: 'CustomerID', minwidth: 100 },
                { display: '当前登陆会话数最小值', name: 'CustomerID', minwidth: 100 },
                { display: '当前登陆会话数平均值', name: 'CustomerID', minwidth: 100 }
                ], pageSize:10,
                data:CustomersData,
                usePager: false,
                width: '100%',height:'300px'
            });
            $("#pageloading").hide(); 
        });

});