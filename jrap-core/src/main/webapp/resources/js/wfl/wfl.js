/**
 * Created by jessen on 2016/9/23.
 */
(function () {
    if (!window.WFL) {
        window.WFL = {};
    }
    WFL.initTaskInfo=function(taskId){

    };
    WFL.ce = function (name, parent, attr) {
        var e = $('<' + name + '/>', attr);
        if (parent) {
            e.appendTo(parent);
        }
        return e;
    };

    //API for iframe
    var INTERFACE = WFL.INTERFACE = {};
    INTERFACE.setFormProperties = function (kv) {

    };
})
();