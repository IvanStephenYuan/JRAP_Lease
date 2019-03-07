/**
 * Created by ylj on 2017/9/14.
 */

/**
 * 单语言审计记录 处理 显示不同样式
 * @param data
 * @returns {string|*|string}
 */
function dealAuditSingleLanguageData(data) {
    if(data !== undefined) {
        var color = "";
        if(data.indexOf("&") >= 0) {
            data = data.substring(0, data.length - 1);
            color = "color: red;";
        }
        result = '<span style="'+ color + '">' + data + '</span>';
    } else result =  '';
    return result;
}

/**
 * 多语言审计记录 处理 显示不同样式
 * @param data
 * @param isShowMultiLanguage
 * @returns {string}
 */
function dealAuditMultiLanguageData(data, isShowMultiLanguage) {
    if(data !== undefined) {
        var content = "";
        var localData = "";
        var result = "";
        var color = "";
        var tooltip = "";
        var name = "";
        var localKey = "";
        if (isShowMultiLanguage) {
            data.forEach(function (value, key) {
                var k = key.indexOf("&") >= 0 ? key.substring(0, key.length - 1) : key;
                var v = value.indexOf("&") >= 0 ? value.substring(0, value.length - 1) : value;
                content += k + ": " + v + "&";
            });
            tooltip += "tooltip-content='" + content + "'";
            name = "name='target' ";
        }
        data.forEach(function (value, key) {
            if (key.indexOf("&") > 0) {
                localData = value;
            }
        });
        if (localData.indexOf("&") >= 0) {
            localData = localData.substring(0, localData.length - 1);
            color = "color: red;";
        }
        result = '<span style="'+ color + '"' + name + tooltip + '>'
            + localData
            + '</span>';

    } else result =  '';

    $(".target").kendoTooltip({
        animation: false
    });
    return result;
}