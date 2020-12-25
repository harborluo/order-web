layui.config({
    echarts: 'layui/lay/ext/echarts.js'
});

layui.use(['element', 'jquery', 'echarts'], function() {

    var chartZhu = layui.echarts.init(document.getElementById('project-charts'));
    //指定图表配置项和数据
    var optionchart = {
        title: {
            text: '商品订单'
        },
        tooltip: {},
        legend: {
            data: ['销量']
        },
        xAxis: {
            data: ['周一', '周二', '周三', '周四', '周五', '周六', '周天']
        },
        yAxis: {
            type: 'value'
        },
        series: [{
            name: '销量',
            type: 'bar', //柱状
            data: [100,200,300,400,500,600,700],
            itemStyle: {
                normal: { //柱子颜色
                    color: 'red'
                }
            },
        },{
            name:'产量',
            type:'bar',
            data:[120,210,340,430,550,680,720],
            itemStyle:{
                normal:{
                    color:'blue'
                }
            }
        }]
    };

    var optionchartZhe = {
        title: {
            text: '商品订单'
        },
        tooltip: {},
        legend: { //顶部显示 与series中的数据类型的name一致
            data: ['销量', '产量', '营业额', '单价']
        },
        xAxis: {
            // type: 'category',
            // boundaryGap: false, //从起点开始
            data: ['周一', '周二', '周三', '周四', '周五', '周六', '周日']
        },
        yAxis: {
            type: 'value'
        },
        series: [{
            name: '销量',
            type: 'line', //线性
            data: [145, 230, 701, 734, 1090, 1130, 1120],
        }, {
            name: '产量',
            type: 'line', //线性
            data: [720, 832, 801, 834, 1190, 1230, 1220],
        }, {
            smooth: true, //曲线 默认折线
            name: '营业额',
            type: 'line', //线性
            data: [820, 932, 901, 934, 1290, 1330, 1320],
        }, {
            smooth: true, //曲线
            name: '单价',
            type: 'line', //线性
            data: [220, 332, 401, 534, 690, 730, 820],
        }]
    };

    var optionchartBing = {
        title: {
            text: '商品订单',
            subtext: '纯属虚构', //副标题
            x: 'center' //标题居中
        },
        tooltip: {
            // trigger: 'item' //悬浮显示对比
        },
        legend: {
            orient: 'vertical', //类型垂直,默认水平
            left: 'left', //类型区分在左 默认居中
            data: ['单价', '总价', '销量', '产量']
        },
        series: [{
            type: 'pie', //饼状
            radius: '60%', //圆的大小
            center: ['50%', '50%'], //居中
            data: [{
                value: 335,
                name: '单价'
            },
                {
                    value: 310,
                    name: '总价'
                },
                {
                    value: 234,
                    name: '销量'
                },
                {
                    value: 135,
                    name: '产量'
                }
            ]
        }]
    };

    // chartZhu.setOption(optionchartZhe, true);

    layui.jquery.ajax({
        url: "/report?scope=all",
        dataType: 'json',
        success: function(res) {

            var yearData = [];
            var costData= [];

            for (var item of res) {
                yearData.push(item.costYear);
                costData.push(item.costPaid);
            }

            var projectCostChart = {
                title: {
                    text: '收款统计图表'
                },
                tooltip: {},
                legend: {
                    data: ['年份']
                },
                xAxis: {
                    data: yearData
                },
                yAxis: {
                    type: 'value'
                },
                series: [{
                    name: '收款金额',
                    type: 'bar', //柱状
                    data: costData,
                    itemStyle: {
                        normal: { //柱子颜色
                            color: 'red'
                        }
                    },
                }]
            };

            chartZhu.setOption(projectCostChart, true);

        }
    });

});