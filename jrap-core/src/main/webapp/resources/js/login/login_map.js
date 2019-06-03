

jQuery(document).ready(function() {
    var mapBoxEchart = echarts.init(document.getElementById('mapBox'));

    var mapdata = [];
    


    $.getJSON(_baseContext+"resources/js/login/china.json",function (data) {
        var d=[];
        for( var i=0;i<data.features.length;i++ ){
            var name = data.features[i].properties.name
            if( name == '辽宁' || name == '吉林' || name == '黑龙江' || name == '内蒙古' ) {
                d.push({
                    name: name,
                    value: 1
                });
            }else if( name == "新疆" || name == '青海' || name == '甘肃' || name == '宁夏' ){
                d.push({
                    name: name,
                    value: 2
                });
            }else if( name == "西藏" || name == '四川' || name == '云南' || name == '贵州' || name == '重庆' ){
                d.push({
                    name: name,
                    value: 3
                });
            }else if( name == "湖南" || name == '江西' || name == '福建' || name == '广东' || name == '广西' || name == '海南' || name == '台湾'){
                d.push({
                    name: name,
                    value: 4
                });
            }else if( name == "浙江" || name == '上海' || name == '江苏' || name == '安徽' || name == '山东' || name == '河北' || name == '天津' || name == '北京' ){
                d.push({
                    name: name,
                    value: 5
                });
            }else if( name == "陕西" || name == '山西' || name == '河南' || name == '湖北' ){
                d.push({
                    name: name,
                    value: 6
                });
            }
        }
        mapdata = d;
        //注册地图
        echarts.registerMap('china', data);
        renderMap("china",d, shopPoint, 22, 14);
    });



    var convertData = function(data) {
        var res = [];
        var fromCoord = ['120.206311', '30.219134'];
        var toCoord = data.value;
        if (fromCoord && toCoord) {
            res.push([{
                coord: fromCoord,
            }, {
                coord: toCoord,
            }]);
        }
        return res;
    };

    // 指定相关的配置项和数据
    var mapBoxOption = {
        geo: {
            map: 'china',
            roam: false,
            aspectScale: 0.75,
            zoom:1.2,
            layoutSize: 100,
            label: {
                normal: {
                    show: true,
                    textStyle: {
                        color: '#00a0c9',
                        fontSize:12
                    }
                },
                emphasis: {
                    show: false,
                    textStyle: {
                        color: "#00a0c9",
                        fontSize:12
                    }
                }
            },
            backgroundColor: '#0083ce',  		// 图表背景色
            tooltip: {
                trigger: 'item',
                formatter: '{b}'
            },
            animationDuration: 400,
            animationEasing:'cubicOut',
            animationDurationUpdate: 400
        }
    };
    // 使用制定的配置项和数据显示图表
    function renderMap(map, data, shopPoint, ledSize, subSize){
        mapBoxOption.visualMap = {
            type: 'piecewise',
            show:false,
            pieces: [
                {value: 1, label: '东北区', color: '#6787ed'},
                {value: 2, label: '西北区', color: '#2d69f2'},
                {value: 3, label: '西南区', color: '#99d2dd'},
                {value: 4, label: '南区', color: '#1b7099'},
                {value: 5, label: '东区', color: '#1ab1d8'},
                {value: 6, label: '中区', color: '#00837e'},

            ],
        };
        mapBoxOption.geo.map = map;
        mapBoxOption.series=[{
                name: map,
                type: 'map',
                mapType: map,
                roam: false,
                zoom:1.2,
                label: {
                    normal:{
                        show:true,
                        textStyle:{
                            color:'#fff',
                            fontSize:12
                        }
                    }
                },
                itemStyle: {
                    normal: {
                        areaColor: '#C5C5C5',
                        borderColor: '#fff'
                    },
                    emphasis: {
                        areaColor: '#e87a7a'
                    }
                },
                data:mapdata
            },
            {
                name: 'point',
                type: 'custom',
                coordinateSystem: 'geo',
                symbolSize: ledSize,
                zlevel: 4,
                itemStyle: {
                    normal: {
                        color: 'rgba(206,64,61,1)'
                    },
                    emphasis: {
                        borderColor: '#fff'
                    }
                },
                renderItem: function (params, api) {//具体实现自定义图标的方法
                    if( shopPoint[params.dataIndex].name.indexOf("杭州") >-1 ) {
                        return {
                            type: 'image',
                            style: {
                                image: _baseContext+"/resources/images/login/star4.png",
                                width: ledSize,
                                height: ledSize,
                                x: api.coord([
                                    shopPoint[params.dataIndex].value[0], shopPoint[params.dataIndex]
                                        .value[1]
                                ])[0] - 12,
                                y: api.coord([
                                    shopPoint[params.dataIndex].value[0], shopPoint[params.dataIndex]
                                        .value[1]
                                ])[1] -12
                            }
                        }
                    }else{
                        return {
                            type: 'image',
                            style: {
                                image: _baseContext+"/resources/images/login/star.png",
                                width: subSize,
                                height: subSize,
                                x: api.coord([
                                    shopPoint[params.dataIndex].value[0], shopPoint[params.dataIndex]
                                        .value[1]
                                ])[0] - 6,
                                y: api.coord([
                                    shopPoint[params.dataIndex].value[0], shopPoint[params.dataIndex]
                                        .value[1]
                                ])[1] - 8
                            }
                        }
                    }
                },
                data: shopPoint
            }
        ]

        var flag=true;
        if(flag){
            for( var i=0; i<shopPoint.length; i++) {
                var fast = Math.ceil(Math.random()*2 + 2);
                mapBoxOption.series.push(
                    {
                        name: 'name',
                        type: 'lines',
                        zlevel: 1,
                        effect: {
                            show: true,
                            period: fast,
                            trailLength: 0.7,
                            color: '#fc7e1d',
                            symbol: "arrow",
                            symbolSize: 3
                        },
                        lineStyle: {
                            normal: {
                                color: '#fc7e1d',
                                width: 0,
                                curveness: 0.2
                            }
                        },
                        data: convertData(shopPoint[i])
                    },
                    {
                        type: "lines",
                        zlevel: 2,
                        effect: {
                            show: true,
                            period: fast, //箭头指向速度，值越小速度越快
                            trailLength: 0, //特效尾迹长度[0,1]值越大，尾迹越长重
                            symbol: "arrow", //箭头图标
                            symbolSize: 8, //图标大小
                            color: '#fc7e1d'
                        },
                        lineStyle: {
                            normal: {
                                width: 1, //尾迹线条宽度
                                opacity: 0.6, //尾迹线条透明度
                                curveness: 0.2, //尾迹线条曲直度
                                color: '#fc7e1d'
                            }
                        },
                        data: convertData(shopPoint[i])
                    }
                )
            }
        }

        mapBoxEchart.setOption(mapBoxOption);
    }


    var option = {
        tooltip : {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        legend: {
            orient : 'horizontal',
            left: 'center',
            data:['东北地区','中区','南区','东区','西南地区','西北地区'],
            textStyle:{
                color:"#fff"
            }
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
                name:'门店地区数量对比',
                type:'pie',
                radius : '55%',
                center: ['50%', '60%'],
                color:['#6787ed', '#00837e','#1b7099','#1ab1d8','#99d2dd','#2d69f2'],
                data:[
                    {value:335, name:'东北地区'},
                    {value:310, name:'中区'},
                    {value:234, name:'南区'},
                    {value:135, name:'东区'},
                    {value:1548, name:'西南地区'},
                    {value:888, name:'西北地区'}
                ]
            }
        ]
    };
    var pieChart = echarts.init(document.getElementById('pieBox'));

    //使用制定的配置项和数据显示图表
    pieChart.setOption(option);

    // echart图表自适应
    window.addEventListener("resize", function() {
        mapBoxEchart.resize();
        pieChart.resize();
    });

});
