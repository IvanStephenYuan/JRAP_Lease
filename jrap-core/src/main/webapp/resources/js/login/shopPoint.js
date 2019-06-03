
var shopPoint = [];
$.ajax({
    url: _baseContext + '/fnd/company/queryAll',
    type: "get",
    async: true,
    success: function (res) {
        //console.log(" shangpu success",res);   //fnd_company.attribute1 经度， attribute2 纬度
        var data = res.rows;
        for (var i = 0, len = data.length; i < len; i++) {
            if (data[i].attribute1 && data[i].attribute1) {
                shopPoint.push({
                    name: data[i].address,
                    value: [data[i].attribute1, data[i].attribute2]
                });
            }
        }

    },
    error: function (error) {

    }
});

