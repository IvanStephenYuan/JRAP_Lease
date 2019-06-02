/**
 * @summary Jrap
 * @description 抽象通用函数
 * @version 3.0
 * @author njq.niu@jingrui.com
 * @author shengyang.zhou@jingrui.com
 * @copyright Copyright ZheJiang JingRui Co.,Ltd.
 *
 */
!(function ($) {

    Date.prototype.toJSON = function () {
        return kendo.toString(this, "yyyy-MM-dd HH:mm:ss")
    };

    $.extend({
        /**
         * isEmpty( Object value, [Boolean allowEmptyString] ) : Boolean
         * Returns true if the passed value is empty, false otherwise. The value is deemed to be empty if it is either:
         * null
         * undefined
         * a zero-length array
         * a zero-length string (Unless the allowEmptyString parameter is set to true)
         */
        isEmpty: function (value, allowEmptyString) {
            return (value === null) || (value === undefined)
                || (!allowEmptyString ? value === '' : false)
                || ($.isArray(value) && value.length === 0);
        }
    });

    /**
     * 获取或设置prompt信息
     * 函数描述
     * @param code
     * @param value
     * @returns
     */
    $l = function (code, value) {
        var al = arguments.length, p = Jrap.defaultPrompt;
        if (al == 1) {
            var v = p[code];
            return v ? v : code;
        } else if (al == 2) {
            p[code] = value;
        }
    };
    if (!window.Jrap) {
        Jrap = {
            version: '2.0',
            defaultPrompt: {}
        };

        /**
         * 根据系统编码获取对应的值的描述.
         *
         * <ul>
         * <li>data: 系统编码</li>
         * <li>v: 值</li>
         * </ul>
         * @param data
         * @param v
         */
        Jrap.getCodeMeaning = function (data, v) {
            $.each(data, function (i, n) {
                if ((n.value || '').toLowerCase() == (v || '').toLowerCase()) {
                    v = n.meaning;
                    return false;
                }
            });
            return v;
        };

        Jrap.showTip = function (msg) {
            if (!Jrap.systemNotification) {
                Jrap.systemNotification = $('<span id="systemNotification" style="display:none;"></span>').appendTo(document.body).kendoNotification({
                    stacking: "down",
                    show: function (e) {
                        if (e.sender.getNotifications().length == 1) {
                            var element = e.element.parent(),
                                eWidth = element.width(),
                                eHeight = element.height(),
                                wWidth = $(window).width(),
                                wHeight = $(window).height(),
                                newTop, newLeft;

                            newLeft = Math.floor(wWidth / 2 - eWidth / 2);
                            newTop = Math.floor(wHeight / 2 - eHeight / 2);

                            e.element.parent().css({top: newTop, left: newLeft});
                        }
                    },
                    autoHideAfter: 1500,
                    button: true
                }).data("kendoNotification")
            }
            Jrap.systemNotification.show(msg)
        };
        /**
         * 删除选中数据(grid).
         *
         * <ul>
         * <li>delSelection.grid: grid dom</li>
         * </ul>
         * @param opts
         */
        Jrap.deleteGridSelection = function (delSelection) {

            delSelection = delSelection || {};
            var grid = delSelection.grid.data("kendoGrid") || {};

            var checked = grid.selectedDataItems();
            if (grid.selectedDataItems().length) {
                kendo.ui.showConfirmDialog({
                    title: $l('jrap.tip.info'),
                    message: $l('jrap.tip.delete_confirm')
                }).done(function (event) {
                    if (event.button == 'OK') {
                        var destroyed = [];
                        $.each(checked, function (i, v) {
                            grid.dataSource.remove(v)
                        });
                        grid.dataSource.sync('destroy')
                    }
                })
            }
        };

        /**
         * websocket通用
         * 调用initWebsocket返回一个socket
         *
         */
        var webSocketListeners = [];
        var socket;
        Jrap.initWebSocket = function () {
            socket = new SockJS(_baseContext + '/websocket');
            socket.onmessage = function (event) {
                var data = $.parseJSON(event.data);
                var action = data.action;
                for (var i = 0; i < webSocketListeners.length; i++) {
                    var callback = webSocketListeners[i].callback;
                    if (action === webSocketListeners[i].action) {
                        callback(data, socket);
                    }
                }
            }
        };

        Jrap.onMessage = function (action, callback) {
            webSocketListeners.push({
                action: action,
                callback: callback
            })
        };



        /**
         * badge websocket 消息提示
         * type:
         * default primary info success danger warning
         * colorClass:
         * 自定义颜色，需要传一个class样式
         * http://keenthemes.com/preview/metronic/theme/admin_4/ui_colors.html
         */

        Jrap.showBadge = function(datas,options) {
            var opts = options ||{}, color = 'badge-' + (opts.type ||'danger'),colorClass = opts.colorClass || color;
            $.each(datas||{},function (code,count) {
                if(count) {
                    var badgeEmpty = '<span style="margin-left: 15px;" class="badge badge-empty ' + colorClass + '"></span>';
                    var element = $('#link_' + code), badge = element.find('.badge');
                    if (badge.length) {
                        badge.html(count);
                    } else {
                        element.append('<span class="badge ' + colorClass + '">' + count + '</span>');
                    }
                    var parents = element.parent().parents("li.nav-item");
                    $.each(parents, function (k, v) {
                        var title = $(v.firstChild).find(".title");
                        if (!title.find('.badge').length) {
                            title.append(badgeEmpty);
                        }
                    })
                }else{
                    Jrap.hideBadge(code);
                }
            });

        };


        Jrap.hideBadge = function(code){
            var element =$('#link_' + code);
            var badge = element.find('.badge');
            element.find('.badge').remove();
            var parents = element.parent().parents("li.nav-item");
            $.each(parents,function (i,v) {
                var title = $(v.firstChild).find(".title");
                if($(v).find(".badge").length <= 1){
                    title.find(".badge").remove();
                }
            });
        };

        /**
         * 表单提交遮罩
         * @param options:
         *          options.target 目标dom的id等标识
         *          options.message:提示信息内容
         *          options.textOnly 仅文字提示
         *          options.iconOnly 仅图标提示
         *          options.boxed 带边框的提示内容
         *          options.animate 提示动画
         *
         */
        Jrap.blockUI = function(options) {
            options = $.extend(true,{}, {boxed:true}, options);
            var html = '';

            if (options.animate) {
                html = '<div class="loading-message ' + (options.boxed ? 'loading-message-boxed' : '') + '">' + '<div class="block-spinner-bar"><div class="bounce1"></div><div class="bounce2"></div><div class="bounce3"></div></div>' + '</div>';
            } else if (options.iconOnly) {
                html = '<div class="loading-message ' + (options.boxed ? 'loading-message-boxed' : '') + '"><img src="' + _basePath + '/lib/assets/global/img/loading-spinner-grey.gif" align=""></div>';
            } else if (options.textOnly) {
                html = '<div class="loading-message ' + (options.boxed ? 'loading-message-boxed' : '') + '"><span>&nbsp;&nbsp;' + (options.message ? options.message : $l('jrap.loading')) + '</span></div>';
            } else {
                html = '<div class="loading-message ' + (options.boxed ? 'loading-message-boxed' : '') + '"><img src="' +  _basePath + '/lib/assets/global/img/loading-spinner-grey.gif" align=""><span>&nbsp;&nbsp;' + (options.message ? options.message : $l('jrap.loading')) + '</span></div>';
            }

            if (options.target) { // element blocking
                var el = $(options.target);
                if (el.height() <= ($(window).height())) {
                    options.cenrerY = true;
                }
                el.block({
                    message: html,
                    baseZ: options.zIndex ? options.zIndex : 1000,
                    centerY: options.centerY !== undefined ? options.centerY : false,
                    css: {
                        top: '10%',
                        border: '0',
                        padding: '0',
                        backgroundColor: 'none'
                    },
                    overlayCSS: {
                        backgroundColor: options.overlayColor ? options.overlayColor : '#555',
                        opacity: options.boxed ? 0.05 : 0.1,
                        cursor: 'wait'
                    }
                });
            } else { // page blocking
                $.blockUI({
                    message: html,
                    baseZ: options.zIndex ? options.zIndex : 1000,
                    css: {
                        border: '0',
                        padding: '0',
                        backgroundColor: 'none',
                    },
                    overlayCSS: {
                        backgroundColor: options.overlayColor ? options.overlayColor : '#555',
                        opacity: options.boxed ? 0.05 : 0.1,
                        cursor: 'wait'
                    }
                });
            }
        };

        /**
         * 取消遮罩
         * @param target
         */
        Jrap.unblockUI = function(target) {
            if (target) {
                $(target).unblock({
                    onUnblock: function() {
                        $(target).css('position', '');
                        $(target).css('zoom', '');
                    }
                });
            } else {
                $.unblockUI();
            }
        };

        /**
         * 保存头行数据(1个form,0个1个或多个 grid).
         *
         * <ul>
         * <li>opts.url: 提交的url</li>
         * <li>opts.type|method: 提交的 http method (默认 POST)</li>
         * <li>opts.formModel: form绑定的 model</li>
         * <li>opts.asArray: form 作为数组提交(默认 true)</li>
         * <li>opts.grid: grid name 和 dom</li>
         *      <ul>
         *          <li>key:bindName</li>
         *          <li>value:grid dom</li>
         *      </ul>
         * <li>opts.shadowMask: 遮罩效果的相关参数</li>
         * <li>opts.success: success回调函数</li>
         * <li>opts.failure: failure回调函数</li>
         * </ul>
         * @param opts
         */
        Jrap.submitForm = function (opts) {
            opts = opts || {};
            var shadowMask = opts.shadowMask ||{};
            var url = opts.url, button = opts.button||'';
            if (!opts.formModel || !url) {
                return;
            }
            opts.asArray = ('asArray' in opts) ? (!!opts.asArray) : true;
            var grids = opts.grid || {};

            var header = opts.formModel.toJSON();
            var changedDs = {};
            var isValid = true;
            $.each(grids, function (bindName, grid) {
                var ds = grid.data("kendoGrid").dataSource;
                if(!grid.data("kendoGrid").validate()){
                    isValid = false;
                    return;
                }
                if (!ds.hasChanges())return;
                changedDs[bindName] = ds;
                header[bindName] = [];
                $.each(ds.data(), function (idx, data) {
                    if (data.dirty === true) {
                        var d = data.toJSON();
                        d['__status'] = data.isNew() ? 'add' : 'update';
                        d['__id'] = data.uid;
                        header[bindName].push(d);
                    }
                });
            });

            if(!isValid){
                return;
            }
            Jrap.blockUI(shadowMask);
            $.ajax({
                url: url,
                type: opts.type || opts.method || 'POST',
                contentType: opts.contentType || 'application/json',
                data: kendo.stringify(opts.asArray ? [header] : header),
                success: function (result) {
                    if (result.success === true) {
                        var h = opts.asArray ? (result.rows[0] || {}) : result;
                        if (opts.formModel.set) {
                            $.each(h, function (k, v) {
                                opts.formModel.set(k, v);
                            })
                        } else
                            $.extend(opts.formModel, h);
                        delete opts.formModel['__status'];
                        $.each(changedDs, function (bindName, source) {
                            $.each(h[bindName] || [], function (i, r) {
                                var _r = source.getByUid(r.__id);
                                if (_r) {
                                    if (r.__status == 'delete') {
                                        //source.remove(_r)
                                    } else {
                                        delete r['__status'];
                                        delete r['__id'];
                                        _r.accept(r);
                                    }
                                }
                            });
                            grids[bindName].find(".k-dirty").removeClass("k-dirty");
                        });
                        Jrap.showToast({
                            type:'success',
                            message: $l('jrap.tip.success')
                        });
                        if (opts.success instanceof Function) {
                            opts.success(result)
                        }

                    } else {
                        if (opts.failure instanceof Function) {
                            opts.failure(result)
                        } else {
                            kendo.ui.showErrorDialog({
                                title: $l('jrap.error'),
                                width: 400,
                                message: result.message
                            })
                        }
                    }
                },
                complete:function () {
                    Jrap.unblockUI(shadowMask.target);
                }
            });
        };

        /**
         * outsizeGrid 表格随界面大小而变化
         * @param grid_id
         * 1.必须有个外层div
         * 2.传入一个grid的id值
         *
         */
        function resizeGrid(grid_id) {
            var grid = $(grid_id).data('kendoGrid');
            if (grid) {
                grid.autoResize();
            }
        }

        Jrap.autoResizeGrid = function (grid_id) {
            resizeGrid(grid_id);
            $(window).resize(function () {
                resizeGrid(grid_id);
            });
        };

        var tzOffSet = new Date().getTimezoneOffset();
        Jrap.timeZone = {
            getTimezoneOffset: function () {
                return tzOffSet;
            },
            set: function (tz) {
                if (!/GMT([+-]\d{4})?/.test(tz))return;
                if (tz.length > 3) {
                    var sign = tz.charAt(3) == '-' ? -1 : 1;
                    var h = +tz.substring(4, 6);
                    var m = +tz.substring(6);
                    tzOffSet = -sign * (h * 60 + m);
                } else tzOffSet = 0;//GMT
            }
        };

        /**
         * value:
         *   输入的值
         * temp:
         *     当前去匹配那个正则表达式(no_limit digits_and_letters digits_and_case_letters)
         *     no_limit:无限制
         *     digits_and_letters:混合数字和字母
         *     digits_and_case_letters:混合数字和大小写字母
         * 返回类型为布尔型
         *
         * */
        Jrap.passwordFormat = function (value, temp) {
            if (temp == 'no_limit') {
                return true;
            } else if (temp == "digits_and_letters") {
                return /[a-zA-Z]/.test(value) && /\d/.test(value);
            } else if (temp == "digits_and_case_letters") {
                return /[a-z]/.test(value) && /[A-Z]/.test(value) && /\d/.test(value);
            }
        };

        /**
         * 将日期字符串转换为 Date 对象。
         * 无效的值将返回 null
         * <ul>
         *     <li>value:日期字符串</li>
         * </ul>
         */
        Jrap.strToDate = function (value) {
            if (!value)return null;
            value = value.replace(/-/g, '/');
            value = value.replace('T', ' ');
            value = value.replace(/(\+[0-9]{2})(\:)([0-9]{2}$)/, ' UTC\$1\$3');
            value = value.replace(/\.[0-9]{1,3}/, '');
            return new Date(value);
        };

        Jrap.formatDate = function (value) {
            if (!value)return '';
            var d = (value instanceof Date) ? value : Jrap.strToDate(value);
            return kendo.toString(d, "yyyy-MM-dd")
        };
        //add by jinqin.ma@jingrui.com
        Jrap.bytestosize = function (bytes) {
            if (bytes === 0) return '0 B';
            var k = 1024, // or 1024
                sizes = ['B', 'KB', 'MB', 'GB', 'TB', 'PB', 'EB', 'ZB', 'YB'],
                i = Math.floor(Math.log(bytes) / Math.log(k));
            return (bytes / Math.pow(k, i)).toPrecision(3) + ' ' + sizes[i];
        };

        Jrap.formatDateTime = function (value) {
            if (!value)return '';
            var d = (value instanceof Date) ? value : Jrap.strToDate(value);
            return kendo.toString(d, "yyyy-MM-dd HH:mm:ss")
        };

        /**
         * 创建一个 A 标签,使用指定的 函数+参数 相应点击事件。
         * <ul>
         *     <li>text:a 标签内的元素,文本,也可以是其他的 html 元素</li>
         *     <li>func:点击相应函数,可以是函数名,也可以是引用</li>
         *     <li>其他参数:作为 func 的参数,类型可以是 boolean,string,number</li>
         * </ul>
         */
        Jrap.createAnchor = function (text, func) {
            if (typeof func == 'function')
                func = func.name || func.toString().match(/^function\s*([^\s(]+)/)[1];
            var arr = [];
            $.each(arguments, function (i, r) {
                if (i < 2)return;
                if (typeof r == 'string') arr.push("'" + r + "'");
                else arr.push(r);
            });
            var funcCall = func + '(' + arr.join(',') + ');return false';
            return '<a href="javascript:void(0);" onclick="' + funcCall + '">' + text + '</a>';
        };

        Jrap.prepareQueryParameter = function (obj, options) {
            obj = obj || {};
            if (options) {
                obj.page = options.page;
                obj.pagesize = options.pageSize;
                if (options.sort && options.sort.length > 0) {
                    obj.sortname = options.sort[0].field;
                    obj.sortorder = options.sort[0].dir;
                }
            }
            for (var k in obj) {
                if (obj[k] === '' || obj[k] === null || obj[k] === undefined)
                    delete obj[k];
                if (obj[k] instanceof Date) {
                    obj[k] = obj[k].toJSON()
                }
            }
            return obj;
        };

        Jrap.prepareSubmitParameter = function (options, type) {
            var datas = options.models;
            $.each(datas, function (i, r) {
                if (type == 'create')
                    r['__status'] = 'add';
                else if (type == 'update')
                    r['__status'] = 'update';
                else if (type == 'destroy')
                    r['__status'] = 'delete';
            });
            return datas;
        };


        Jrap.request = function (options) {
            $.ajax({
                type: options.type || 'POST',
                url: options.url,
                data: options.data,
                contentType: options.contentType || "application/json",
                dataType: options.dataType || 'json',
                success: function (data) {
                    if (data.success) {
                        Jrap.showToast({
                            type: 'success',
                            message: $l('jrap.tip.success')
                        });
                        if (options.success) options.success.call(window, data)

                    } else {
                        Jrap.resolveError(data, options.error);
                    }
                },
                error: function (res) {
                    Jrap.resolveError(res.responseJSON)
                }
            });
        };


        //处理ajax异常
        $(document).ajaxSuccess(function (event, xhr, options, json) {
            // 只处理系统级别异常，其他异常自行处理
            if (json && json.success === false && json.code && json.code.indexOf('sys_') != -1) {
                Jrap.resolveError(json)
            }
        }).ajaxError(function (event, xhr, ajaxOptions, thrownError) {
            // if(xhr.responseJSON)
            //     Jrap.resolveError(xhr.responseJSON)
        });


        Jrap.resolveError = function (json, callback) {
            switch (json.code) {
                case 'sys_session_timeout':
                    if (top.sessionExpiredLogin) {
                        top.sessionExpiredLogin();
                    } else {
                        top.location.href = _basePath + '/login';
                    }
                    break;
                case 'sys_access_denied':
                    kendo.ui.showErrorDialog({
                        title: $l('jrap.error'),
                        message: '无权访问!'
                    });
                    break;
                default:
                    kendo.ui.showErrorDialog({
                        title: $l('jrap.error'),
                        message: json.message
                    }).done(function () {
                        if (callback) callback.call(window, json)
                    });
            }
        };


        Jrap.escapeHtml = function (str) {
            if (!$.type(str) == 'string' || str.length == 0)
                return str;
            return String(str).replace(/&/gm, '&amp;').replace(/\"/gm, '&quot;').replace(/\(/gm, '&#40;').replace(/\)/gm, '&#41;').replace(/\+/gm, '&#43;').replace(/\%/gm, '&#37;')
                .replace(/</gm, '&lt;').replace(/>/gm, '&gt;').replace(/\'/gm, '&#39;');
        };
        Jrap.unescapeHtml = function (str) {
            if (!$.type(str) == 'string' || str.length == 0)
                return str;
            return String(str).replace(/&amp;/gm, '&').replace(/&quot;/gm, '"').replace(/&#40;/gm, '(').replace(/&#41;/gm, ')').replace(/&#43;/gm, '+').replace(/&#37;/gm, '%')
                .replace(/&lt;/gm, '<').replace(/&gt;/gm, '>').replace(/&#39;/gm, '\'');
        };

        Jrap.getDurationTime = function (durationTime) {
            var value = '', days = parseInt(durationTime / 86400), hours = parseInt(durationTime / 3600 % 24),
                minutes = parseInt(durationTime / 60 % 60);
            if (days > 0)
                value += (days + "天");
            if (hours > 0)
                value += (hours + "小时");
            if (minutes > 0) {
                value += (minutes + "分钟")
            }
            return value;
        };

        /**
         * 初始化form回车查询.
         *
         * @param selector
         * @param callback
         */
        Jrap.initEnterQuery = function (selector, callback) {
            $(selector).keydown(function (e) {
                if (e.keyCode == 13) {
                    e.target.blur();
                    callback.call(window)
                }
            });
        };

        /**
         * 保存成功后提示
         *
         */
        Jrap.showToast = function(options){
            var opts = $.extend({
                "closeButton":true,
                "debug": false,
                "positionClass": "toast-bottom-right",
                "onclick":null,
                "showDuration": "1000",
                "hideDuration": "1000",
                "timeOut": "1000",
                "extendedTimeOut":"1000",
                "showEasing": "swing",
                "hideEasing": "linear",
                "showMethod": "fadeIn",
                "hideMethod": "fadeOut"
            },options || {});

            var toastr = top.toastr;
            if (toastr && options.type) {
                var op = {
                    'success': toastr.success,
                    'info': toastr.info,
                    'warning': toastr.warning,
                    'error': toastr.error
                };
                op[options.type](options.message, options.title, opts);
            }
        };

        /**
         * 创建窗口
         * @param selector
         * @param options
         */
        Jrap.createWindow = function(selector, options){
            return $(selector).kendoWindow({
                width: options.width||900,
                height:  options.height||500,
                title: options.title||'window',
                content: options.url,
                scrollable: false,
                resizable: false,
                iframe: true,
                visible: false,
                modal: true
            }).data("kendoWindow");
        };

        /**
         * viewModel 加载数据
         */
        Jrap.loadViewModel = function(opt){
            if(opt.url){
                $.ajax({
                    url: opt.url,
                    success: function (args) {
                        if(opt.model){
                            var a0 = args.rows[0] || {};
                            for (var k in a0) {
                                opt.model.set(k, a0[k]);
                            }
                        }
                    }
                });
            }
        };

        /**
         * 常用查询模式下的ViewModel
         * @param selector
         * @param extension
         */
        Jrap.createGridViewModel = function(selector, extension){
            return kendo.observable($.extend({
                model: {},
                create: function () {
                    $(selector).data('kendoGrid').addRow();
                },
                save: function () {
                    $(selector).data('kendoGrid').saveChanges();
                },
                query: function (e) {
                    $(selector).data('kendoGrid').dataSource.page(1);
                },
                refresh: function(){
                    $(selector).data('kendoGrid').dataSource.read();
                },
                remove: function () {
                    Jrap.deleteGridSelection({
                        grid: $(selector)
                    });
                },
                reset: function(){
                    var formData = this.model.toJSON();
                    for (var k in formData) {
                        this.model.set(k, null);
                    }
                },
                cancel:function(){
                    $(selector).data('kendoGrid').cancelChanges();
                },
                exportExcel:function () {
                    $(selector).data('kendoGrid').saveAsExcel();
                },
                exportPDF:function(){
                    $(selector).data('kendoGrid').saveAsPDF();
                }
            }, extension));
        };


        /**
         * 锁定提交按钮，防止表格重复提交
         * @param options
         *      options.target:需要禁止的按钮标识
         */
        Jrap.lockBtn = function (options) {
            options = $.extend({
                "target":".jrap-submit-load",
                "message": ""
            },options || {});
            var dom = $(options.target);
            var message = options.message;
            if(message){
                dom.text(message);
            }

            $.each(dom,function (i,v) {
                var wrapper = "<div id='wrapper"+i+"' style='position: absolute;'></div>";
                $(this).parent().append(wrapper);
                var ele =$("#wrapper"+i);
                ele.outerWidth($(this).outerWidth());
                ele.outerHeight($(this).outerHeight());
                ele.offset($(this).offset());
                ele.bind("click",function (e) {
                    e.preventDefault();
                });
                ele.css("cursor","wait")

            });
            dom.attr("disabled",true);


        };

        /**
         * 解锁提交按钮
         * @param options
         */
        Jrap.unLockBtn = function (options) {
            options = $.extend({
                "target":".jrap-submit-load",
                "message": ""
            },options || {});
            var dom = $(options.target);
            var message = options.message;
            if(message){
                dom.text(message);
            }
            $.each(dom,function (i,v) {
                var ele =$("#wrapper"+i);
                ele.remove();

            });
            dom.attr("disabled",false);
        };


        /**
         * 创建Grid的DataSource
         * @param options
         */
        Jrap.createGridDataSource = function(options){
            return new kendo.data.DataSource({
                transport: {
                    read: {
                        url: options.url + "query",
                        type: "POST",
                        dataType: "json"
                    },
                    update: {
                        url: options.url + "submit",
                        type: "POST",
                        contentType: "application/json"
                    },
                    destroy: {
                        url: options.url + "remove",
                        type: "POST",
                        contentType: "application/json"
                    },
                    create: {
                        url: options.url + "submit",
                        type: "POST",
                        contentType: "application/json"
                    },
                    parameterMap: function (options, type) {
                        if (type !== "read" && options.models) {
                            return kendo.stringify(Jrap.prepareSubmitParameter(options, type));
                        } else if (type === "read") {
                            return Jrap.prepareQueryParameter(viewModel.model.toJSON(), options);
                        }
                    }
                },
                batch: true,
                serverPaging: options.serverPaging !== false,
                serverSorting: options.serverSorting !== false,
                pageSize: options.pageSize||20,
                sort: options.sort||{},
                schema: {
                    data: 'rows',
                    total: 'total',
                    model: options.model||{}
                },
            });
        }
    }

    //扩展Grid方法
    kendo.ui.Grid.prototype.removeRow = function (row) {
        var sf = this;
        kendo.ui.showConfirmDialog({
            title: $l('jrap.prompt'),
            message: $l('jrap.tip.delete_confirm')
        }).done(function (event) {
            if (event.button == 'OK') {
                sf._removeRow(row);
            }

        })
    };
    kendo.ui.Grid.prototype.showToast = function (e) {
        if (e.type != 'read' && e.response && e.response.success) {
            Jrap.showToast({
                type: 'success',
                message: $l('jrap.tip.success')
            });
        }
    };

    //扩展 Widget 的 init 方法
    kendo.ui.Widget.prototype.init = function (element, options) {
        var that = this;
        that.element = kendo.jQuery(element).handler(that);
        that.angular('init', options);
        kendo.Observable.fn.init.call(that);
        var dataSource = options ? options.dataSource : null;
        if (dataSource) {
            options = $.extend({}, options, {dataSource: {}});
        }
        options = that.options = $.extend(true, {}, that.options, options);
        if (dataSource) {
            options.dataSource = dataSource;
        }
        if (!that.element.attr(kendo.attr('role'))) {
            that.element.attr(kendo.attr('role'), (options.name

            || '').toLowerCase());
        }
        that.element.data('kendo' + options.prefix + options.name

            , that);
        that.bind(that.events, options);
        if (this instanceof kendo.ui.Grid) {
            if (options.dataSource instanceof kendo.data.DataSource) {
                options.dataSource.unbind('requestEnd', this.showToast);
                options.dataSource.bind('requestEnd', this.showToast);
            }
        }
    };



    /*********
     * *为请求添加token
     */
    var header = $('meta[name=_csrf_header]').attr('content');
    var token  = $('meta[name=_csrf]').attr('content');
    $(document).ajaxSend(function (e,xhr,options) {
        xhr.setRequestHeader(header,token);
    });

    /*
     *
     * 弹性域
     * */

    Jrap.createGridFlexField = function (ruleSetCode, viewModel, gridId,callback) {

        if(!window["flex"+gridId]){
            var grid = $("#"+gridId).data("kendoGrid");
            var options1 = grid.getOptions().columns;
            window["flex"+gridId]=options1;
        }

        $.ajax({
            type: "POST",
            url: _basePath + "/fnd/flex/rule/matching?ruleSetCode=" + ruleSetCode,
            contentType: "application/json",
            dataType: "json",
            data: kendo.stringify(viewModel),
            success: function (data) {
                //生成grid
                if(!data.rows){
                    return ;
                }
                if (gridId) {
                    var options = window["flex"+gridId].slice(0);
                    Jrap.createFlexGrid(data, gridId, options);
                }

                if(callback){
                    callback.call();
                }
            }
        });

    };

    Jrap.createFlexField = function (ruleSetCode, viewModel, divId,callback) {

        if (!viewModel._flexField) {
            viewModel._flexField = [];
        } else {
            Jrap.cleanFlexData(viewModel);
        }
        $("#" + divId).empty();
        $.ajax({
            type: "POST",
            url: _basePath + "/fnd/flex/rule/matching?ruleSetCode=" + ruleSetCode,
            contentType: "application/json",
            dataType: "json",
            data: kendo.stringify(viewModel),
            success: function (data) {
                if (data.rows) {
                    //生成form
                    if (divId) {
                        for (var i = 0; i < data.rows.length; i++) {
                            Jrap.createFlexRow(data.rows[i], $("#" + divId), viewModel);
                        }
                        kendo.bind($("#" + divId), viewModel);
                        if(callback){
                            callback.call();
                        }
                    }
                }
            }
        });
    };


    Jrap.createFlexGrid = function (data, gridId, option) {

        var grid = $("#" + gridId).data("kendoGrid");
        if (data.rows) {
            for (var i = 0; i < data.rows.length; i++) {
                for (var j = 0; j < data.rows[i].fields.length; j++) {

                    var column = data.rows[i].fields[j];
                    var columnName = Jrap.flexTocalme(column.columnName);
                    var style = $.parseJSON(column.fieldType);

                    var model = new Model();
                    if (column.readableFlag == 'Y') {
                        model.set("fields." + columnName, {});
                        model.set("fields." + columnName + ".editable", false);
                    }
                    if (column.requiredFlag == 'Y') {
                        model.set("fields." + columnName, {});
                        model.set("fields." + columnName + ".validation", {});
                        model.set("fields." + columnName + ".validation.required", true);

                    }

                    if (style.type == 'LOV') {
                        option.push({
                            field: columnName,
                            title: style.labelName,
                            width: column.fieldColumnWidth,
                            template: (function (fn) {
                                var conditionFieldSelectTf = fn;
                                return function (dataItem) {
                                    return dataItem[conditionFieldSelectTf] || ''
                                }
                            })(style.conditionFieldSelectTf.substring(style.conditionFieldSelectTf.indexOf('.')+1)),
                            editor: (function (cf, tf, cn,cr) {
                                return function (container, options) {
                                    var conditionFieldLovCode = cf;
                                    var conditionFieldSelectTf = tf;
                                    var colName = cn;

                                    var input= $('<input  name="' + options.field + '"/>');
                                    input.attr("required", cr=="Y" );
                                    input.appendTo(container)
                                        .kendoLov({
                                            contextPath: _basePath,
                                            locale: _locale,
                                            model: options.model,
                                            textField: conditionFieldSelectTf,
                                            code: conditionFieldLovCode,
                                        });
                                    // if (true) {
                                    //     container.html(options.model[colName])
                                    // } else {
                                    //     $('<input required name="' + options.field + '"/>')
                                    //         .appendTo(container)
                                    //         .kendoLov({
                                    //             contextPath: _basePath,
                                    //             locale: _locale,
                                    //             model: options.model,
                                    //             textField: conditionFieldSelectTf,
                                    //             code: conditionFieldLovCode,
                                    //         });
                                    // }

                                }

                            })(style.conditionFieldLovCode, style.conditionFieldSelectTf.substring(style.conditionFieldSelectTf.indexOf('.')+1), columnName,column.requiredFlag),
                            headerAttributes: {
                                style: "text-align:center"
                            },
                            attributes: {
                                style: "text-align:"+style.valueAlignType.toLowerCase()
                            }
                        });

                    } else if (style.type == 'SELECT') {
                        if (!style.conditionFieldSelectUrl == '') {
                            var dataSource = [];
                            $.ajax({
                                url: _basePath + '/' + style.conditionFieldSelectUrl,
                                dataType: "json",
                                async: false,
                                success: function (data) {
                                    dataSource = data.rows;
                                }
                            });

                            option.push({
                                field: columnName,
                                title: style.labelName,
                                width: column.fieldColumnWidth,
                                template: (function (ds, tf, vf, fn) {
                                    var dataSource = ds, textField = tf, valueField = vf, fieldName = fn;
                                    return function (dataItem) {
                                        var v = dataItem[fieldName];
                                        $.each(dataSource, function (i, n) {
                                            if ((n[valueField] || '') == (v || '')) {
                                                v = n[textField];
                                                return false;
                                            }
                                        });
                                        return v || '';
                                    }
                                })(dataSource, style.conditionFieldSelectTf, style.conditionFieldSelectVf, columnName),
                                editor: (function (ds, tf, vf,cr) {
                                    return function (container, options) {
                                        var dataSource = ds, textField = tf, valueField = vf;
                                        var input=$('<input  name="' + options.field + '"/>');
                                        input.attr("required", cr=="Y" );
                                        input.appendTo(container)
                                            .kendoComboBox({
                                                dataTextField: textField,
                                                dataValueField: valueField,
                                                valuePrimitive: true,
                                                dataSource: dataSource,
                                            })
                                    }

                                })(dataSource, style.conditionFieldSelectTf, style.conditionFieldSelectVf,column.requiredFlag),
                                headerAttributes: {
                                    style: "text-align:center"
                                },
                                attributes: {
                                    style: "text-align:"+style.valueAlignType.toLowerCase()
                                }
                            });
                        } else if (!style.conditionFieldSelectCode == '') {
                            var dataSource ;
                            $.ajax({
                                url: _basePath + '/common/code/' + style.conditionFieldSelectCode + '/',
                                dataType: "json",
                                async: false,
                                success: function (data) {
                                    dataSource = data;
                                }
                            });

                            option.push({
                                field: columnName,
                                title: style.labelName,
                                width: column.fieldColumnWidth,
                                template: (function (ds, tf, vf, fn) {
                                    var dataSource = ds, textField = tf, valueField = vf, fieldName = fn;
                                    return function (dataItem) {
                                        var v = dataItem[fieldName];
                                        $.each(dataSource, function (i, n) {
                                            if ((n[valueField] || '') == (v || '')) {
                                                v = n[textField];
                                                return false;
                                            }
                                        });
                                        return v || '';
                                    }
                                })(dataSource, style.conditionFieldSelectTf, style.conditionFieldSelectVf, columnName),
                                editor: (function (ds, tf, vf,cr) {
                                    return function (container, options) {
                                        var dataSource = ds, textField = tf, valueField = vf;
                                        var input=$('<input  name="' + options.field + '"/>');
                                        input.attr("required", cr=="Y" );
                                        input.appendTo(container)
                                            .kendoComboBox({
                                                dataTextField: textField,
                                                dataValueField: valueField,
                                                valuePrimitive: true,
                                                dataSource: dataSource,
                                            })
                                    }

                                })(dataSource, style.conditionFieldSelectTf, style.conditionFieldSelectVf,column.requiredFlag),
                                headerAttributes: {
                                    style: "text-align:center"
                                },
                                attributes: {
                                    style: "text-align:"+style.valueAlignType.toLowerCase()
                                }

                            });
                        }

                    } else if (style.type == 'MULTI') {

                        option.push({
                            field: columnName,
                            title: style.labelName,
                            width: column.fieldColumnWidth,
                            editor: (function (ds, tf, vf,cr) {
                                return function (container, options) {
                                    var conditionFieldSelectId = ds, conditionFieldSelectIdFiled = tf,
                                        conditionFieldSelectDto = vf;
                                    var input =$('<input  name="' + options.field + '"/>');
                                    input.attr("required", cr=="Y" );
                                    input.appendTo(container)
                                        .kendoTLEdit({
                                            idField: conditionFieldSelectId,
                                            field: conditionFieldSelectIdFiled,
                                            dto: conditionFieldSelectDto,
                                            model: options.model
                                        });
                                }

                            })(style.conditionFieldSelectId, style.conditionFieldSelectIdFiled, style.conditionFieldSelectDto,column.requiredFlag),
                            headerAttributes: {
                                style: "text-align:center"
                            },
                            attributes: {
                                style: "text-align:"+style.valueAlignType.toLowerCase()
                            }
                        });


                    } else if (style.type == 'INT') {
                        option.push({
                            field: columnName,
                            title: style.labelName,
                            width: column.fieldColumnWidth,
                            editor: (function (ds, tf, vf,cr) {
                                return function (container, options) {
                                    var conditionMinRange = ds, conditionMaxRange = tf, conditionPrecision = vf;
                                    var input=$('<input  name="' + options.field + '"/>');
                                    input.attr("required", cr=="Y" );
                                    input.appendTo(container)
                                        .kendoNumericTextBox({
                                            min: conditionMinRange,
                                            max: conditionMaxRange,
                                            decimals: conditionPrecision
                                        });
                                }

                            })(style.conditionMinRange, style.conditionMaxRange, style.conditionPrecision,column.requiredFlag),
                            headerAttributes: {
                                style: "text-align:center"
                            },
                            attributes: {
                                style: "text-align:"+style.valueAlignType.toLowerCase()
                            }
                        });
                    } else if (style.type == 'DATE') {
                        if (style.hasTime == 'Y') {
                            option.push({
                                field: columnName,
                                title: style.labelName,
                                width: column.fieldColumnWidth,
                                headerAttributes: {
                                    "class": "table-header-cell",
                                    style: "text-align: center"
                                },
                                format: "{0:yyyy-MM-dd HH:mm:ss}",
                                attributes: {
                                    style: "text-align:"+style.valueAlignType.toLowerCase()
                                },
                                editor:(function (cr) {
                                    return function (container, options) {
                                        var input = $('<input  name="' + options.field + '"/>');
                                        input.attr("required", cr=="Y" );
                                        input.appendTo(container)
                                            .kendoDateTimePicker();
                                    }

                                })(column.requiredFlag)
                                //editor: function (container, options) {
                                //
                                //     var input = $('<input  name="' + options.field + '"/>');
                                //     input.attr("required", cr=="Y" );
                                //
                                //     input.appendTo(container)
                                //         .kendoDateTimePicker();
                                // }
                            });

                        } else {
                            option.push({
                                field: columnName,
                                title: style.labelName,
                                width: column.fieldColumnWidth,
                                headerAttributes: {
                                    "class": "table-header-cell",
                                    style: "text-align: center"
                                },
                                format: "{0:yyyy-MM-dd}",
                                attributes: {
                                    style: "text-align:"+style.valueAlignType.toLowerCase()
                                },
                                editor:(function (cr) {
                                    return function (container, options) {
                                        var input=$('<input  name="' + options.field + '"/>');
                                        input.attr("required", cr=="Y" );
                                        input.appendTo(container)
                                            .kendoDatePicker();
                                    }
                                })(column.requiredFlag)
                            });
                        }

                    } else if (style.type == 'TEXT') {
                        option.push({
                            field: Jrap.flexTocalme(column.columnName),
                            title: style.labelName,
                            width: column.fieldColumnWidth,
                            headerAttributes: {
                                style: "text-align:center"
                            },
                            attributes: {
                                style: "text-align:"+style.valueAlignType.toLowerCase()
                            }
                        });
                    }
                }

            }
        }
        grid.setOptions({columns: option});
    };



    Jrap.cleanFlexData = function (viewModel) {
        var str1;
        while (viewModel._flexField.length > 0) {
            str1 = viewModel._flexField.pop();
            viewModel.set(str1, null);
        }
    };

    Jrap.createFlexRow = function (rows, parentDiv, model) {
        var row = Jrap.flexCe('div', parentDiv, {'class': 'row'});
        for (var i = 0; i < rows.fields.length; i++) {
            Jrap.createFlexColumn(rows.fields[i], row, model);
        }
    };
    Jrap.createFlexColumn = function (column, parentDiv, model) {
        var cloumnName = Jrap.flexTocalme(column.columnName);
        model._flexField.push(cloumnName);
        var style = $.parseJSON(column.fieldType);
        var div1 = Jrap.flexCe('div', parentDiv, {'class': 'col-xs-' + column.fieldColumnWidth});
        var form_group = Jrap.flexCe('div', div1, {'class': 'form-group'});
        var label = Jrap.flexCe('label', form_group, {
            'class': 'control-label col-xs-' + style.labelWidth,
            style: 'text-align: right'
        }).text(style.labelName);
        var div = Jrap.flexCe('div', form_group, {'class': 'col-xs-' + style.columnWidth});

        var ipt;
        var iptdata;

        if (style.type == 'LOV') {

            if (column.requiredFlag == 'Y') {
                ipt = Jrap.flexCe('input', div, {
                    'name': cloumnName,
                    'type': 'text',
                    'data-bind': 'value:' + style.conditionFieldSelectVf+",text:"+style.conditionFieldSelectTf+",",
                    //'data-bind': 'value:' + style.conditionFieldSelectVf+",text:"+style.conditionFieldSelectTf+",",
                    'required':true,
                    style: 'width:100%'
                });
                ipt.attr("data-label", style.labelName);

            }else{
                ipt = Jrap.flexCe('input', div, {
                    'name': cloumnName,
                    'type': 'text',
                    'data-bind': 'value:' + style.conditionFieldSelectVf+",text:"+style.conditionFieldSelectTf+",",
                    //'data-bind': 'value:' + style.conditionFieldSelectVf+",text:"+style.conditionFieldSelectTf+",",
                    style: 'width:100%'
                });
            }

            iptdata = ipt.kendoLov({
                contextPath: _basePath,
                locale: _locale,
                code: style.conditionFieldLovCode,
            }).data("kendoLov");

        } else if (style.type == 'SELECT') {
            if (column.requiredFlag == 'Y') {
                ipt = Jrap.flexCe('input', div, {
                    'name': cloumnName,
                    'required':true,
                    'data-value-primitive': true,
                    'data-text-field': style.conditionFieldSelectTf,
                    'data-value-field': style.conditionFieldSelectVf,
                    'data-bind': 'value:' + cloumnName,
                    'required':true,
                    style: "width: 100%;"
                });
                ipt.attr("data-label", style.labelName);

            }else{
                ipt = Jrap.flexCe('input', div, {
                    'name': cloumnName,
                    'data-value-primitive': true,
                    'data-text-field': style.conditionFieldSelectTf,
                    'data-value-field': style.conditionFieldSelectVf,
                    'data-bind': 'value:' + cloumnName,
                    style: "width: 100%;"
                });
            }

            if (!style.conditionFieldSelectUrl == '') {
                iptdata = ipt.kendoComboBox({
                    dataSource: {
                        transport: {
                            read: _basePath + '/' + style.conditionFieldSelectUrl,
                        },
                        schema: {
                            data: 'rows'
                        }
                    }
                }).data("kendoComboBox");
            } else if (!style.conditionFieldSelectCode == '') {
                iptdata = ipt.kendoComboBox({
                    dataTextField: style.conditionFieldSelectTf,
                    dataValueField: style.conditionFieldSelectVf,
                    dataSource: {
                        transport: {
                            read: {
                                url: _basePath + '/common/code/' + style.conditionFieldSelectCode + '/',
                                dataType: "json"
                            }
                        }
                    }
                }).data("kendoComboBox");
            }
        } else if (style.type == 'MULTI') {
            if (column.requiredFlag == 'Y') {
                ipt = Jrap.flexCe('input', div, {
                    'name': cloumnName,
                    'data-bind': 'value:' + cloumnName,
                    style: 'width:100%',
                    'required':true,
                });
                ipt.attr("data-label", style.labelName);

            }else{
                ipt = Jrap.flexCe('input', div, {
                    'name': cloumnName,
                    'data-bind': 'value:' + cloumnName,
                    style: 'width:100%'
                });
            }

            iptdata = ipt.kendoTLEdit({
                idField: style.conditionFieldSelectId,
                field: style.conditionFieldSelectIdFiled,
                dto: style.conditionFieldSelectDto,
                model: model
            }).data("kendoTLEdit");
        } else if (style.type == 'INT') {
            if (column.requiredFlag == 'Y') {
                ipt = Jrap.flexCe('input', div, {
                    'name': cloumnName,
                    'data-bind': 'value:' + cloumnName,
                    style: 'width:100%',
                    'required':true,
                });
                ipt.attr("data-label", style.labelName);
            }else{
                ipt = Jrap.flexCe('input', div, {
                    'name': cloumnName,
                    'data-bind': 'value:' + cloumnName,
                    style: 'width:100%'
                });
            }

            iptdata = ipt.kendoNumericTextBox({
                min: style.conditionMinRange,
                max: style.conditionMaxRange,
                decimals: style.conditionPrecision
            }).data("kendoNumericTextBox");
        } else if (style.type == 'DATE') {

            if (style.hasTime == 'Y') {
                if (column.requiredFlag == 'Y') {
                    ipt = Jrap.flexCe('input', div, {
                        'name': cloumnName,
                        'data-role': 'datetimepicker',
                        'class': 'k-datetimepicker',
                        'data-bind': 'value:' + cloumnName,
                        style: 'width:100%',
                        'required':true,
                    });
                    ipt.attr("data-label", style.labelName);
                }else{
                    ipt = Jrap.flexCe('input', div, {
                        'name': cloumnName,
                        'data-role': 'datetimepicker',
                        'class': 'k-datetimepicker',
                        'data-bind': 'value:' + cloumnName,
                        style: 'width:100%'
                    });
                }

            } else {
                if (column.requiredFlag == 'Y') {
                    ipt = Jrap.flexCe('input', div, {
                        'name': cloumnName,
                        'data-role': 'datepicker',
                        'class': 'k-datepicker',
                        'data-bind': 'value:' + cloumnName,
                        style: 'width:100%',
                        'required':true,
                    });
                    ipt.attr("data-label", style.labelName);
                }else{
                    ipt = Jrap.flexCe('input', div, {
                        'name': cloumnName,
                        'data-role': 'datepicker',
                        'class': 'k-datepicker',
                        'data-bind': 'value:' + cloumnName,
                        style: 'width:100%'
                    });
                }


            }

        } else if (style.type == 'TEXT') {
            type = 'text';
            cls = 'k-textbox';
            if (column.requiredFlag == 'Y') {
                ipt = Jrap.flexCe('input', div, {
                    'name': cloumnName,
                    'data-role': type,
                    'class': cls,
                    'data-bind': 'value:' + cloumnName,
                    style: 'width:100%',
                    'required':true,
                });
                ipt.attr("data-label", style.labelName);
            }else{
                ipt = Jrap.flexCe('input', div, {
                    'name': cloumnName,
                    'data-role': type,
                    'class': cls,
                    'data-bind': 'value:' + cloumnName,
                    style: 'width:100%'
                });
            }


        }
        if (column.readableFlag == 'Y') {
            if (style.type == 'DATE' || style.type == 'TEXT') {
                ipt.attr("readonly", true);
                ipt.attr("style", "background-color:#ededed !important;width: 100%");
            } else {
                iptdata.enable(false);
            }
        }
    };


    Jrap.flexCe = function (name, parent, attr) {
        var e = $('<' + name + '/>', attr);
        if (parent) {
            e.appendTo(parent);
        }
        return e;
    };

    Jrap.flexTocalme = function (str) {
        str = str.toLowerCase();
        var re = /_(\w)/g;
        return str.replace(re, function ($0, $1) {
            return $1.toUpperCase();
        });
    };

    /*
     *excel
     * */
    Jrap.exportExcel=function(tableName,url){
        var length = $(document.body).find("#_excelExportDynamicWin").length;
        if(!length){
            $(document.body).append("<div id='_excelExportDynamicWin'></div>");
        }
        Jrap.createWindow('#_excelExportDynamicWin',{
            width: '700',
            height: '500',
            title: '导出Excel',
            url: _basePath+"/excel/export_excel_template.html?tableName="+tableName+"&url="+url,
        }).center().open();

    };

    /**
     * kendo excel导出后台路径.
     * @returns {string} excel后台导出路径
     */
    Jrap.excelProxyURL = function () {
        return _basePath + '/kendo/export';
    };

    Jrap.importExcel=function(tableName){
        var length = $(document.body).find("#_excelImportDynamicWin").length;
        if(!length){
            $(document.body).append("<div id='_excelImportDynamicWin'></div>");
        }
        Jrap.createWindow('#_excelImportDynamicWin',{
            width: '500',
            height: '300',
            title: '导入Excel',
            url: _basePath+"/excel/import_excel_template.html?tableName="+tableName,
        }).center().open();

    };
    Jrap.checkAccess = function(id){
        var cfg = window.accessConfig,found = false;
        if(cfg){
            checkElement = function(elements, field){
                elements = elements||[];
                var checkFields = function(fields){
                    $.each(fields||[],function(i,n){
                        if(n.id == id) {
                            found = true;
                            return false;
                        }
                    });
                    return found;
                };
                if(field){
                    $.each(elements,function(i,n){
                        found = checkFields(n[field]);
                        if(found) return false;
                    });
                    return found;
                } else {
                    return checkFields(elements)
                }
            };
            found = checkElement(cfg.buttons) || checkElement(cfg.form, 'fields') || checkElement(cfg.form, 'buttons') || checkElement(cfg.grid, 'buttons')
        }
        return found == false;
    };

    Jrap.initForm = function(id){
        var generatorComponent =['dropdownlist','checkbox','radio','numerictextbox','datepicker','datetimepicker','timepicker','maskedtextbox','lov','combobox','tledit'];
        $.each(generatorComponent,function(i,roleName){
            $(id).find('[data-role="'+roleName+'"]').each(function () {
                var datas = $(this).data(),isInit=true;
                for(var data in datas){
                    if(data.toLowerCase() == "kendo"+roleName && typeof datas[data] == "object"){
                        isInit=false;
                        break;
                    }
                }
                if(isInit){
                    kendo.init($(this));
                }
            });
        });

    };
    $(document).keydown(function (e) {
        var keyValue = e.key.toUpperCase();
        $.each(hotKeys,function (i,v) {
            var length = $("[data-hotkey="+this.code+"]").length;
            if(this.hotkey.keyValue == keyValue && e.altKey == this.hotkey.altKey && e.shiftKey == this.hotkey.shiftKey && e.ctrlKey == this.hotkey.ctrlKey){
                if(length > 1){
                    e.preventDefault();
                    console.warn("页面中存在"+length+"个 hotkey="+this.code+" ，请检查!")
                }else if(length == 1 && !$("[data-hotkey="+this.code+"]").attr("disabled")){
                    e.preventDefault();
                    $("input").blur();
                    $("[data-hotkey="+this.code+"]").click()
                }
                return false;
            }
        })
    })
})(jQuery);
