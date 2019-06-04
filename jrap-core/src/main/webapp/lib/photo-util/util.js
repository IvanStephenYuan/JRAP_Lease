/*
 * *
 *  @file com.maddyhome.idea.copyright.pattern.FileInfo@8f7f9ea$
 *  @CopyRight (C) 2018 ZheJiangJingRui Co. Ltd.
 *  @brief JingRui Application Platform
 *  @author $name$
 *  @email yulong.yuan@jr-info.cn
 *  @date $date$
 * /
 */

// 身份证校验
function IdentityCodeValid(code) {
    var city = {
        11: "北京",
        12: "天津",
        13: "河北",
        14: "山西",
        15: "内蒙古",
        21: "辽宁",
        22: "吉林",
        23: "黑龙江 ",
        31: "上海",
        32: "江苏",
        33: "浙江",
        34: "安徽",
        35: "福建",
        36: "江西",
        37: "山东",
        41: "河南",
        42: "湖北 ",
        43: "湖南",
        44: "广东",
        45: "广西",
        46: "海南",
        50: "重庆",
        51: "四川",
        52: "贵州",
        53: "云南",
        54: "西藏 ",
        61: "陕西",
        62: "甘肃",
        63: "青海",
        64: "宁夏",
        65: "新疆",
        71: "台湾",
        81: "香港",
        82: "澳门",
        91: "国外 "
    };
    var tip = true;
    var pass = true;
    if (!code
        || !/(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/.test(
            code)) {
        tip = "身份证号格式错误";
        pass = false;
        return tip;
    } else if (!city[code.substr(0, 2)]) {
        tip = "身份证号格式错误，地址码错";
        pass = false;
        return tip;
    } else {
        if (code.length == 18) {
            code = code.split('');
            var factor = [7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2];
            var parity = [1, 0, 'X', 9, 8, 7, 6, 5, 4, 3, 2];
            var sum = 0;
            var ai = 0;
            var wi = 0;
            var birthday = "";
            for (var i = 0; i < 17; i++) {
                ai = code[i];
                wi = factor[i];
                sum += ai * wi;
                if (i >= 6 && i <= 13) {
                    if (i == 9) {
                        birthday += code[i] + "/";
                    } else if (i == 11) {
                        birthday += code[i] + "/";
                    } else {
                        birthday += code[i]
                    }
                }
            }
            var last = parity[sum % 11];
            if (last != code[17]) {
                tip = "身份证号格式错误，校验位错";
                pass = false;
                return tip;
            }
            var d1 = new Date(birthday);
            var d2 = new Date();
            if (d1.getFullYear() + 16 < d2.getFullYear() || (d1.getFullYear() + 16 == d2.getFullYear() && d1.getMonth() < d2.getMonth()) || (d1.getFullYear() + 16 == d2.getFullYear() && d1.getMonth() == d2.getMonth() && d1.getDate() <= d2.getDate())) {
                tip = true;
            } else {
                tip = "未满16周岁";
                pass = false;
            }
        }
    }
    return tip;
};

// 校验发动(电)机号
function engineNumValid(engineNum) {
    var output;
    var code = 'REG_DJH';
    var reg;
    $.ajax({
        url: _basePath + '/er/configuration/validator?code=' + code,
        type: "POST",
        dataType: "json",
        contentType: "application/json;charset=UTF-8",
        async: false,
        success: function (args) {
            reg = new RegExp(args);
        },
        fail: function (args) {
            output = "发动(电)机号校验异常，请联系管理员";
        }
    });
    var reg1 = /^[*]$/;
    if (reg.test(engineNum)) {
        return true;
    } else if (reg1.test(engineNum)) {
        return false;
    } else {
        return "发动(电)机号校验异常，请联系管理员"
    }
}

// 校验车架号
function frameNumValid(frameNum) {
    var output;
    var code = 'REG_CJH';
    var reg;
    $.ajax({
        url: _basePath + '/er/configuration/validator?code=' + code,
        type: "POST",
        dataType: "json",
        contentType: "application/json;charset=UTF-8",
        async: false,
        success: function (args) {
            reg = new RegExp(args);
        },
        fail: function (args) {
            output = "车架号校验异常，请联系管理员";
        }
    });
    var reg1 = /^[*]$/;
    if (reg.test(frameNum)) {
        return true;
    } else if (reg1.test(frameNum)) {
        return false;
    } else {
        return "车架号校验异常，请联系管理员";
    }
}

// 校验车牌
function licenseNumValid(licenseNum) {
    var output;
    $.ajax({
        url: _basePath + '/er/electrombile/licenseNumValid?licenseNum=' + licenseNum,
        type: "POST",
        dataType: "json",
        contentType: "application/json;charset=UTF-8",
        async: false,
        success: function (args) {
            if (args > 0) {
                output = true;
            } else {
                output = "车牌号:" + licenseNum + " 未记载入车牌库，或已被上牌，请核实车牌号后录入。";
            }
        },
        fail: function (args) {
            output = "车牌号:" + licenseNum + " 未记载入车牌库，请核实车牌号后录入。";
        }
    });
    return output;
}

// 校验手机号码 返回true-准确 否则返回错误信息
function checkPhone(phone) {
    if (!(/^1(3|4|5|7|8|9)\d{9}$/.test(phone))) {
        return "手机号码格式错误";
    } else {
        return true;
    }
}
