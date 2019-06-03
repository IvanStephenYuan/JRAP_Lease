$(document).ready(function () {
    //头部中访问量的echart
    var cardVisitOption={
        tooltip : {
            trigger: 'axis'
        },
        toolbox: {
            show : false,
            feature : {
                mark : {show: true},
                dataView : {show: true, readOnly: false},
                magicType : {show: true, type: ['line', 'bar', 'stack', 'tiled']},
                restore : {show: true},
                saveAsImage : {show: true}
            }
        },
        calculable : true,
        grid: {
            left: '5%',
            right: '5%',
            top: '0%',
            bottom: '0%'
        },
        xAxis : [
            {
                show:false,
                type : 'category',
                boundaryGap : false,
                data : ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月']
            }
        ],
        yAxis : [
            {
                show:false,
                type : 'value'
            }
        ],
        series : [
            {
                name:'访问量',
                type:'line',
                smooth:true,
                itemStyle: {
                    normal: {
                        color: '#289df5', // 折线条的颜色
                        borderColor: '#289df5', // 拐点边框颜色
                        areaStyle: {
                            type: 'default',
                            opacity: 0.1
                        }
                    }
                },
                data:[100, 120, 210, 540, 260, 830, 710,666,555,444,333,222]
            },
        ]
    };
    var cardVisitCharts = echarts.init(document.getElementById('card-visit-card'));
    cardVisitCharts.setOption(cardVisitOption);


    //头部中支付笔数的echart
    var cardPayOption={
        tooltip : {
            trigger: 'axis'
        },
        toolbox: {
            show : false,
            feature : {
                mark : {show: true},
                dataView : {show: true, readOnly: false},
                magicType : {show: true, type: ['line', 'bar', 'stack', 'tiled']},
                restore : {show: true},
                saveAsImage : {show: true}
            }
        },
        calculable : true,
        grid: {
            left: '5%',
            right: '5%',
            top: '0%',
            bottom: '0%'
        },
        xAxis : [
            {
                show:false,
                type : 'category',
                boundaryGap : false,
                data : ['2019-05-21','2019-05-22','2019-05-23','2019-05-24','2019-05-25','2019-05-26','2019-05-27']
            }
        ],
        yAxis : [
            {
                show:false,
                type : 'value'
            }
        ],
        series : [
            {
                name:'支付笔数',
                type:'bar',
                itemStyle:{
                    normal:{
                        color:'#4ad2ff'
                    }
                },
                data:[100, 120, 210, 540, 260, 830, 710]
            },
        ]
    };
    var cardPayCharts = echarts.init(document.getElementById('card-pay-card'));
    cardPayCharts.setOption(cardPayOption);








    //销售趋势
    var startdate ="2019-5-1";
    var endDate = "2019-5-30";
    var trendGridCharts = echarts.init(document.getElementById('trendGrid'));
    var trendGridOption={
        tooltip : {
            trigger: 'axis'
        },
        toolbox: {
            show : false,
            feature : {
                mark : {show: true},
                dataView : {show: true, readOnly: false},
                magicType : {show: true, type: ['line', 'bar', 'stack', 'tiled']},
                restore : {show: true},
                saveAsImage : {show: true}
            }
        },
        calculable : true,
        grid: {
            left: '10%',
            right: '10%',
            top: '5%',
            bottom: '18%'
        },
        xAxis : [
            {
                show:true,
                type : 'category',
                boundaryGap : false,
                data : ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月'],
                axisLabel: {
                    interval:0,
                    rotate:30
                }
            }
        ],
        yAxis : [
            {
                show:true,
                type : 'value'
            }
        ],
        series : [
            {
                name:'支付笔数',
                type:'bar',
                itemStyle:{
                    normal:{
                        color:'#4ad2ff'
                    }
                },
                data:[100, 120, 210, 540, 260, 830, 710,660,550,300,200,345],
                barWidth: '50%'
            },
        ]
    };

    function renderSaleEcharts(startdate,endDate) {
        var arrDate=[startdate,endDate];
        var xAxisData=[];
        var ySeriesData=[];
        $.ajax({
            url:baseUrl+"/con/order/leaseamount",
            type: "POST",
            dataType: "json",
            data: kendo.stringify(arrDate),
            async: false,
            contentType: "application/json",
            success:function (res) {
                if(res.success && res.total){
                    var saleData = res.rows;
                    for(var i=0;i<res.total;i++){
                        var saleDate=saleData[i].paymentDate.split(" ");
                        xAxisData.push(saleDate[0]);
                        ySeriesData.push(saleData[i].leaseMoney);
                    }
                    console.log(xAxisData);
                    console.log(ySeriesData);
                    trendGridOption.xAxis[0].data=xAxisData;
                    trendGridOption.series[0].data=ySeriesData;
                };
            }
        });
        $.ajax({
            url:baseUrl+"/con/order/rankOrder",
            type: "POST",
            dataType: "json",
            data: kendo.stringify(arrDate),
            async: false,
            contentType: "application/json",
            success:function (res) {
                if(res.success && res.total){
                    var len=(res.total>8)?8:res.total;
                    var rankDate=res.rows;
                    var str="";
                    var className="";
                    for(var i=0;i<len;i++){
                        className=(i>2)?"":"sale-rank-top";
                        str+=`<li class="${className}">
                            <span class="sale-rank-order">${i+1}</span>
                            <span class="sale-rank-shop">${rankDate[i].ename}</span>
                            <span class="sale-rank-num pull-right">${rankDate[i].amcount}</span>
                        </li>`;
                    }
                    $(".sale-rank").html(str);
                }else{
                    $(".sale-rank").html('<p style="margin-top: 50px;text-align: center;font-size: 16px">暂无内容</p>');
                }
            }
        });
        trendGridCharts.setOption(trendGridOption,true);
    }

    renderSaleEcharts(startdate,endDate);

    var startDatePicker = $("#startDate").data("kendoDatePicker");
    var endDatePicker = $("#endDate").data("kendoDatePicker");
    startDatePicker.bind("change", function() {
        var initialStartDate=startDatePicker.value();
        var initialEndDate=endDatePicker.value();
        renderSaleEcharts(initialStartDate,initialEndDate);

    });





    //搜索引擎的表格
    var search_subChart1 = echarts.init(document.getElementById('search-subChart1'));
    search_subChart1.setOption(cardVisitOption);
    var search_subChart2 = echarts.init(document.getElementById('search-subChart2'));
    search_subChart2.setOption(cardVisitOption);


    //销售额类别占比饼图
    var pieData=[
        {value:335, name:'家用电器'},
        {value:310, name:'食用酒水'},
        {value:234, name:'个护健康'},
        {value:135, name:'服饰箱包'},
        {value:1548, name:'其他'}
    ];
    var pieOption = {
        tooltip : {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        legend: {
            orient: 'horizontal',
            x: 'center',
            y:'top',
            itemGap: 20,
            itemWidth: 10,
            itemHeight: 10,
            icon: "circle",
            data:['家用电器','食用酒水','个护健康','服饰箱包','其他'],
            formatter:function(name){
                let target;
                let sum=0;
                for(let i=0;i<pieData.length;i++){
                    sum+=pieData[i].value;
                    if(pieData[i].name===name){
                        target=pieData[i].value
                    }
                }
                let arr="{name|"+name+"}"+"  {value|￥"+target+"}"+"  {ratio|"+((target/sum)*100).toFixed(2)+"%}";
                return arr;
            },
            textStyle:{
                rich:{
                    name:{
                        fontSize:14,
                        color:"rgba(0,0,0,.85)",
                    },
                    value:{
                        fontSize:14,
                        color:"rgba(0,0,0,.35)"
                    },
                    ratio:{
                        fontSize:14,
                        color:"rgba(0,0,0,.65)"
                    }
                }
            }
        },
        toolbox: {
            show : false,
        },
        calculable : true,
        series : [
            {
                name:'访问来源',
                type:'pie',
                radius : ['30%', '50%'],
                center:['50%','60%'],
                color:['#6787ed', '#00837e','#1b7099','#1ab1d8','#99d2dd','#2d69f2'],
                data:pieData
            }
        ]
    };
    var pieChart = echarts.init(document.getElementById('second-pie-box'));

    //使用制定的配置项和数据显示图表
    pieChart.setOption(pieOption);



    // echart图表自适应
    window.addEventListener("resize", function() {
        cardVisitCharts.resize();
        cardPayCharts.resize();
        trendGridCharts.resize();
        search_subChart1.resize();
        search_subChart2.resize();
        pieChart.resize();
    });
});

