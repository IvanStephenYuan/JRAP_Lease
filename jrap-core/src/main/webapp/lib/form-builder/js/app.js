$.fn.serializeObject = function () {
    var serializeObj = {};
    var array = this.serializeArray();
    var str = this.serialize();
    $(array).each(function () {
        if (serializeObj[this.name]) {
            if ($.isArray(serializeObj[this.name])) {
                serializeObj[this.name].push(this.value);
            } else {
                serializeObj[this.name] = [serializeObj[this.name], this.value];
            }
        } else {
            serializeObj[this.name] = this.value;
        }
    });
    return serializeObj;
},
    function (n, t, r) {
        function getComponentOptions(n) {
            for (var u = componentBox.length, i, t = 0; t < u; t++)
                if (i = componentBox[t][0],
                    i == n)
                    return componentBox[t][1];
            return r
        }

        function tt(n) {
            for (var i = 1; i != 12; i++)
                if ($(n).hasClass("col-md-" + i))
                    break;
            return i
        }

        function getControlCount(name) {
            var i = $("#design-canvas").attr("data-control-count");
            i = parseInt(i) + 1;
            $("#design-canvas").attr("data-control-count", i);
            return name + i.toString();
        }

        function createTab(n, ui) {
            var u = $(ui.item).context
                , renderEle = $(document.getElementById("tabTemplate")).clone();
            $(renderEle).attr("id", getControlCount("tab"));
            $(".tab-content", renderEle).children().each(function (n) {
                var i = getControlCount("");
                $(this).attr("id", "tabContent" + i);
                $("li:eq(" + n + ") a", renderEle).attr("href", "#tabContent" + i).attr("id", "tabLabel" + i)
            });
            $(u).replaceWith(renderEle);
            $(renderEle).click(function () {
                a += 1
            });
            tabEventInit(renderEle)
        }

        function tabEventInit(ele) {
            var r = function (n) {
                $(n).unbind("hover");
                $(n).hover(function () {
                    var r = $('<div class="tab-toolbar"><span class="glyphicon glyphicon-cog"><\/span><\/div>');
                    $("ul.nav-tabs", n).append(r);
                    $(".glyphicon-cog", r).click(function (n) {
                        var f, e, o, s;
                        n.stopPropagation();
                        var a = $(this).hasClass("hasFocus")
                            , r = $("#fieldproperties")
                            , l = $("#tabproperties")
                            , u = $("ul.nav-tabs", $(this).parent().parent().parent());
                        $(".hasFocus").removeClass("hasFocus");
                        r.children().fadeOut(0);
                        l.fadeIn(0);
                        a == false && (c = "tab");
                        f = function () {
                            $("#__tabItems", r).children().remove();
                            $("li", u).each(function (n, i) {
                                var r = $("a", i).text().trim()
                                    , u = $("a", i).attr("id")
                                    ,
                                    f = $('<li><div class="input-group"><span class="input-group-addon">&nbsp;<span class="icon-reorder">&nbsp;<\/span>&nbsp;<\/span><input data-id="' + u + '" value="' + r + '" class="form-control" type="text"><span class="input-group-addon">&nbsp;<span class="icon-add">&nbsp;&nbsp;&nbsp;&nbsp;<\/span>&nbsp;<span class="icon-delete">&nbsp;&nbsp;&nbsp;&nbsp;<\/span>&nbsp;<\/span><\/div><\/li>');
                                $("#__tabItems", l).append(f)
                            })
                        }
                        ;
                        f();
                        e = function () {
                            $("#__tabItems input", r).unbind("keyup");
                            $("#__tabItems input", r).keyup(function () {
                                var n = $(this).attr("data-id")
                                    , i = $("#" + n);
                                $(i).text(this.value)
                            })
                        }
                        ;
                        e();
                        o = function () {
                            $("#__tabItems span.icon-add", r).unbind("click");
                            $("#__tabItems span.icon-add", r).click(function () {
                                var n = getControlCount(""), r = $(this).closest("li").index(), l = $(u).children().eq(r),
                                    a = $('<li><a id="tabLabel' + n + '" href="#tabContent' + n + '" data-toggle="tab">Undefined<\/a><\/li>'),
                                    v = $('<div class="tab-pane" id="tabContent' + n + '"><div class="tab-canvas"><\/div><\/div>'),
                                    c;
                                $(a).insertAfter(l);
                                c = $("div.tab-content", $(u).parent()).children().eq(r);
                                v.insertAfter(c);
                                f();
                                o();
                                s();
                                e();
                                i()
                            })
                        }
                        ;
                        o();
                        s = function () {
                            $("#__tabItems span.icon-delete", r).unbind("click");
                            $("#__tabItems span.icon-delete", r).click(function () {
                                var u = confirm("Delete");
                                if (u == true) {
                                    var f = $(this).parent().siblings("input").attr("data-id")
                                        , n = $("#" + f)
                                        , e = $(n).attr("href").replace("#", "")
                                        , i = $("#" + e)
                                        , r = $(i).parent().parent().parent();
                                    $(this).parent().parent().remove();
                                    $(n).hide("slow", function () {
                                        $(n).parent().remove();
                                        $(i).remove();
                                        $("div.tab-content > div", r).length == 0 && $(r).remove()
                                    })
                                }
                            })
                        }
                        ;
                        s()
                    })
                }, function () {
                    $(".tab-toolbar", this).remove()
                })
            }, i;
            r(ele);
            i = function () {
                $(ele).find(".tab-canvas").each(function () {
                    $(this).sortable({
                        appendTo: "#design-canvas",
                        delay: 200,
                        tolerance: "pointer",
                        connectWith: "#design-canvas, .gv-droppable-grid, .tab-canvas",
                        placeholder: "ui-state-highlight",
                        start: function (n, i) {
                            var r = generateEle(componentType, i);
                            i.placeholder.html($(r).html())
                        },
                        helper: "clone",
                        stop: function (n, t) {
                            switch (componentId) {
                                case "menu1Column":
                                case "menu2Columns":
                                case "menu3Columns":
                                case "menu4Columns":
                                case "twoandten":
                                    createContainer(n, t, componentId);
                                    break;
                                case "_collapsible":
                                    createAccordion(n, t);
                                    break;
                                case "_tab":
                                    createTab(n, t)
                            }
                            l = null;
                            componentId = null
                        }
                    })
                })
            }
            ;
            i()
        }

        function showPager() {
            $("#designform ul.pager li").fadeIn(0);
            $("#designform  ul.pager li:first").fadeOut(0);
            $("#designform  ul.pager li:last").fadeOut(0)
        }

        function createPageBreak(n, i) {
            var e = $(i.item).context, r = $(document.getElementById("pageBreakTemplate")).clone(),
                o = $("ul", r).parent(), u, f;
            $(r).removeAttr("id");
            $(e).replaceWith(r);
            o.click(function () {
                ct(this)
            });
            u = $("#designform");
            $('[data-role="pagebreak"]', u).length == 1 && (f = $(document.getElementById("pageBreakTemplate")).clone(),
                $(f).attr("id", "lastPage").addClass("container lp"),
                u.append(f));
            showPager()
        }

        function createPanel(n, i) {
            var u = $(i.item).context
                , r = $(document.getElementById("panel1Template")).clone()
                , f = getControlCount("panel");
            $(r).attr("id", f);
            $(u).replaceWith(r);
            panelEventInit(r, $(".panel-body", r))
        }

        function panelEventInit(n, i) {
            var e = function (n) {
                $(n).unbind("hover");
                $(n).hover(function () {
                    var n = $('<div class="panel-toolbar"><span class="glyphicon glyphicon-cog"><\/span><\/div>');
                    $(".accordion-toolbar", $(this)).remove();
                    $(this).append(n);
                    $(".glyphicon-cog", n).click(function (n) {
                        var o, i, r, e;
                        n.stopPropagation();
                        $(".hasFocus").removeClass("hasFocus");
                        c = "panel";
                        u = $(this).closest(".panel");
                        $(u).addClass("hasFocus");
                        f = $("#fieldproperties");
                        o = u;
                        i = $("#panelproperties");
                        f.children().fadeOut(0);
                        $(i).fadeIn(0);
                        $("#panelHeader", i).val($(".panel-heading", u).text());
                        $("#panelFooter", i).val($(".panel-footer", u).text());
                        r = function () {
                            $("#panelHeader", i).unbind("keyup");
                            $("#panelHeader", i).keyup(function () {
                                var n = $(".panel-heading", u)
                                    , i = this.value;
                                i === "" ? $(".panel-heading", u).remove() : ($(".panel-heading", u).length == 0 && (n = $('<div class="panel-heading">' + i + "<\/div>"),
                                    $(n).insertBefore($(".panel-body", u))),
                                    $(n).text(i))
                            })
                        }
                        ;
                        r();
                        e = function () {
                            $("#panelFooter", i).unbind("keyup");
                            $("#panelFooter", i).keyup(function () {
                                var n = $(".panel-footer", u)
                                    , i = this.value;
                                i === "" ? $(".panel-footer", u).remove() : ($(".panel-footer", u).length == 0 && (n = $('<div class="panel-footer">' + i + "<\/div>"),
                                    $(n).insertAfter($(".panel-body", u))),
                                    $(n).text(i))
                            })
                        }
                        ;
                        e()
                    })
                }, function () {
                    var n = $(this);
                    $(".panel-toolbar", n).remove()
                })
            }, r;
            e(n);
            r = function (n) {
                $(n).sortable({
                    appendTo: "#design-canvas",
                    delay: 200,
                    tolerance: "pointer",
                    connectWith: "#design-canvas, .gv-droppable-grid, .tab-canvas",
                    start: function (n, t) {
                        dragging = false;
                        t.placeholder.height("25")
                    },
                    helper: "clone",
                    placeholder: "ui-state-highlight",
                    stop: function (n, t) {
                        componentId = componentType;
                        switch (componentId) {
                            case "menu1Column":
                            case "menu2Columns":
                            case "menu3Columns":
                            case "menu4Columns":
                            case "twoandten":
                                createContainer(n, t, componentId);
                                break;
                            case "_collapsible":
                                createAccordion(n, t);
                                break;
                            case "_tab":
                                createTab(n, t);
                                break;
                            case "_panel":
                                createPanel(n, t)
                        }
                        l = null;
                        componentId = null
                    }
                })
            }
            ;
            r(i)
        }


        function createAccordion(n, i) {
            var f = $(i.item).context, e = $(document.getElementById("panelTemplate")).clone(),
                r = $(".panel-group", e), u;
            $(f).replaceWith(r);
            $(r).click(function () {
                a += 1
            });
            u = getControlCount("accordion");
            $(r).attr("id", u);
            $("#accTemplate", r).attr("id", getControlCount("acc"));
            $(".panel", r).each(function () {
                var n = getControlCount("");
                $(".panel-collapse", this).attr("id", "accBody" + n);
                $(".panel-title > a", this).attr("href", "#accBody" + n).attr("id", "accHeading" + n).attr("data-parent", "#" + u)
            });
            rt(r, $(".panel-body", r))
        }

        function rt(n, i) {
            var e = function (n) {
                $(".panel-heading", n).unbind("hover");
                $(".panel-heading", n).hover(function () {
                    var n = $('<div class="accordion-toolbar"><span class="glyphicon glyphicon-cog"><\/span><\/div>');
                    $(".accordion-toolbar", $("a", this)).remove();
                    $("a", this).append(n);
                    $(".glyphicon-cog", n).click(function (n) {
                        var i, y, o, s, l, a, v;
                        n.stopPropagation();
                        $(".hasFocus").removeClass("hasFocus");
                        c = "panel";
                        u = $(this).closest(".panel").parent();
                        f = $("#fieldproperties");
                        i = u;
                        o = $("#accordionproperties");
                        f.children().fadeOut(0);
                        $(o).fadeIn(0);
                        s = function () {
                            $("#__accordionItems", f).children().remove();
                            $("div.panel", i).each(function () {
                                var n = $(".panel-title", this), r;
                                if (n.length > 0) {
                                    var i = $("a", n)
                                        , u = i.text().trim()
                                        , f = i.attr("id");
                                    y = getControlCount("__a");
                                    r = $('<li><div class="input-group"><span class="input-group-addon">&nbsp;<span class="icon-reorder">&nbsp;<\/span>&nbsp;<\/span><input data-id="' + f + '" value="' + u + '" class="form-control" type="text"><span class="input-group-addon">&nbsp;<span class="icon-add">&nbsp;&nbsp;&nbsp;&nbsp;<\/span>&nbsp;<span class="icon-delete">&nbsp;&nbsp;&nbsp;&nbsp;<\/span>&nbsp;<\/span><\/div><\/li>');
                                    $("#__accordionItems", o).append(r)
                                }
                            })
                        }
                        ;
                        s();
                        l = function () {
                            $("#__accordionItems input", f).unbind("keyup");
                            $("#__accordionItems input", f).keyup(function () {
                                var n = $(this).attr("data-id")
                                    , i = $("#" + n, u);
                                $(i).text(this.value)
                            })
                        }
                        ;
                        l();
                        a = function () {
                            $("#__accordionItems span.icon-add", f).unbind("click");
                            $("#__accordionItems span.icon-add", f).click(function () {
                                var n = $("#panelTemplate  .panel").clone()
                                    , p = $(this).parent().siblings("input").attr("data-id")
                                    , u = getControlCount("")
                                    , f = $(i).attr("id")
                                    , o = $(this).parent().parent().index()
                                    , c = $(i).children().eq(o)
                                    , y = $(i).parent();
                                $(".panel-collapse", y).each(function (n, i) {
                                    $(i).removeClass("in", "slow")
                                });
                                $(".panel-collapse", n).attr("id", "accBody" + u);
                                $(".panel-title > a", n).attr("href", "#accBody" + u).attr("id", "accHeading" + u).attr("data-parent", "#" + f);
                                $(n).insertAfter(c);
                                s();
                                a();
                                v();
                                l();
                                rt(i, $(".panel-body", i));
                                e(i);
                                r($(".panel-body", i))
                            })
                        }
                        ;
                        a();
                        v = function () {
                            $("#__accordionItems .icon-delete", f).unbind("click");
                            $("#__accordionItems .icon-delete", f).click(function () {
                                var r = $(this).parent().siblings("input").attr("data-id"),
                                    n = $("#" + r).closest(".panel"), i;
                                $(this).closest("li").remove();
                                $(n).hide("slow", function () {
                                });
                                i = $(n).parent().parent();
                                $(n).remove();
                                $(".panel", i).length == 0 && $(i).remove()
                            })
                        }
                        ;
                        v()
                    })
                }, function () {
                    var n = $("a", this);
                    $(".accordion-toolbar", n).remove()
                })
            }, r;
            e(n);
            r = function (n) {
                $(n).sortable({
                    appendTo: "#design-canvas",
                    delay: 200,
                    tolerance: "pointer",
                    connectWith: "#design-canvas, .gv-droppable-grid, .tab-canvas",
                    placeholder: "ui-state-highlight",
                    start: function (n, t) {
                        dragging = false;
                        t.placeholder.height("25")
                    },
                    helper: "clone",
                    stop: function (n, t) {
                        componentId = componentType;
                        switch (componentId) {
                            case "menu1Column":
                            case "menu2Columns":
                            case "menu3Columns":
                            case "menu4Columns":
                            case "twoandten":
                                createContainer(n, t, componentId);
                                break;
                            case "_collapsible":
                                createAccordion(n, t);
                                break;
                            case "_tab":
                                createTab(n, t)
                        }
                        l = null;
                        componentId = null
                    }
                })
            }
            ;
            r(i)
        }

        function ut(event, ui, eleId) {
            var w, options, p;
            if (eleId != r && eleId !== r && eleId != null) {
                eleId = eleId.replace("_", "");
                var element
                    , eleCount = getControlCount("field")
                    , b = $(ui.item).context
                    , d = $(b).parent()
                    , y = $(document.getElementById("controlGroupTemplate")).clone();
                if (eleId == "HeroTemplate") {
                    $(b).replaceWith(element);
                    return
                }
                if (eleId == "HorizontalTemplate")
                    y = $(document.getElementById("horizontalGroupTemplate")).clone();
                else {
                    if (eleId == "EditorTemplate") {
                        element = $(document.getElementById(eleId)).clone();
                        $(b).replaceWith(element);
                        $(element).attr("name", eleCount);
                        $(element).kendoEditor();
                        return
                    }
                    if(eleId.replace("Template", "").toLowerCase() != r){
                        options = getComponentOptions(eleId.replace("Template", "").toLowerCase());
                        if(options != r){
                            element = options.add(y, eleCount);
                        }
                    }
                }

                $(y).removeAttr("id");
                $(y).attr("data-control-type", eleId.replace("Template", "").toLowerCase());
                if((eleId != "HeaderTemplate" || eleId != "ParagraphTemplate") && d.attr("data-alignment") != r){
                    alignment(y, d.attr("data-alignment"));
                }

                if (d.attr("data-size") != r) {
                    p = d.attr("data-size");
                    switch (p) {
                        case "Mini":
                            p = "input-mini";
                            break;
                        case "Small":
                            p = "input-small";
                            break;
                        case "Medium":
                            p = "input-medium";
                            break;
                        case "Large":
                            p = "input-large";
                            break;
                        case "Extra Large":
                            p = "input-xlarge";
                            break;
                        case "Stretched":
                            p = ""
                    }
                    if(p != ""){
                        $(".controls", y).removeClass("input-mini").removeClass("input-small").removeClass("input-medium").removeClass("input-large").removeClass("input-xlarge").addClass(p)
                    }
                }
                $(b).replaceWith(y);
                $(y).hover(function () {
                    $(this).hasClass("hasFocus") == false && $(this).addClass("hoverComponent")
                }, function () {
                    $(this).removeClass("hoverComponent")
                });
                $(y).click(function (i) {
                    var s, o;
                    a += 1;
                    s = $(i.target).context.nodeName;
                    u = $(this);
                    if(!("__,SELECT,INPUT,OPTION,TEXTAREA".indexOf(s) > 0 && $(u).hasClass("hasFocus"))){
                        e = new ht(u);
                        c = e.controlType;
                        n.selectedGroup = u;
                        n.selectedProperty = f;
                        n.selectedControlType = c;
                        if(e.controlType != r){
                            o = getComponentOptions(e.controlType);
                            if(o != r){
                                o.click(u, f);
                            }
                        }
                    }
                });

                if(eleId == "HorizontalTemplate"){
                    $(y).sortable({
                        connectWith: "#_Button",
                        tolerance: "pointer",
                        appendTo: ".gv-droppable-grid",
                        helper: "clone",
                        placeholder: "ui-state-highlight",
                        start: function (n, t) {
                            dragging = false;
                            t.placeholder.height(t.item.height())
                        },
                        stop: function (n, i) {
                            var r = $(i.item).context;
                            ut(n, i, eleId)
                        }
                    });
                }
                eleId = null;
                componentId = null
            }
        }

        /**
         * g函数更名为generateEle
         * @param type 拖拽对象的类型
         * @param ui 拖拽对象的dom对象
         * @returns 在dragTarget区域展示的新生成的dom
         */
        function generateEle(type, ui) {
            var ui_new;
            switch (type) {
                case "twoandten":
                    return $('<div class="row"><div class="col-md-2 gv-droppable-grid"><\/div><div class="col-md-10 gv-droppable-grid"><\/div><\/div>');
                case "menu1Column":
                    return $('<div class="row"><div class="col-md-12  gv-droppable-grid"><\/div><\/div>');
                case "menu2Columns":
                    return $('<div class="row"><div class="col-md-6 gv-droppable-grid"><\/div><div class="col-md-6 gv-droppable-grid"><\/div><\/div>');
                case "menu3Columns":
                    return $('<div class="row"><div class="col-md-4 gv-droppable-grid"><\/div><div class="col-md-4 gv-droppable-grid"><\/div><div class="col-md-4 gv-droppable-grid"><\/div><\/div>');
                case "menu4Columns":
                    return $('<div class="row"><div class="col-md-3 gv-droppable-grid"><\/div><div class="col-md-3 gv-droppable-grid"><\/div><div class="col-md-3 gv-droppable-grid"><\/div><div class="col-md-3 gv-droppable-grid"><\/div><\/div>');
                case "_tab":
                    return $(document.getElementById("tabTemplate")).clone();
                case "_collapsible":
                    return $(document.getElementById("panelTemplate")).clone();
                default:
                    var template = type.replace("_", "")
                        , html = $(ui.item).context
                        , element = $(html).parent()
                        , options = getComponentOptions(template.replace("Template", "").toLowerCase());
                    if (options) {
                        ui_new = $(document.getElementById("controlGroupTemplate")).clone();
                        if (options.placeHolder) {
                            ui_new = options.placeHolder(ui_new)
                        }
                        $(ui_new).removeAttr("id");
                        if (element.attr("data-alignment")) {
                            alignment(ui_new, element.attr("data-alignment"))
                        }
                        return $('<div class="row"><\/div>').append(ui_new);
                    }
            }
            return;
        }

        /**
         * form group的对齐方式
         * @param ele  选中的元素
         * @param value 对其方式对应的值
         * @returns 新的element
         */
        function alignment(ele, value) {
            $(ele).find("label:first").removeClass("control-label").removeClass("control-label-left").removeClass("control-label-right").removeClass("col-sm-3");
            $(ele).find("div.controls").removeClass("control-inline").removeClass("col-sm-9");
            switch (value) {
                case "Top":
                    $(ele).find("label:first").addClass("control-label");
                    $(ele).removeClass("form-horizontal");
                    break;
                case "Right":
                    $(ele).find("label:first").addClass("control-label").addClass("col-sm-3");
                    $(ele).find("div.controls").addClass("col-sm-9");
                    break;
                case "Left":
                    $(ele).find("label:first").addClass("control-label").addClass("control-label-left").addClass("col-sm-3");
                    $(ele).find("div.controls").addClass("col-sm-9")
            }
            return ele
        }

        function createContainer(event, ui, containerId) {
            var html, f;
            if(arguments.length == 1){
                (containerId = event);
            }
            html = generateEle(containerId); //原来是g函数
            $(".gv-droppable-grid", html).attr("data-alignment", "Left");
            arguments.length == 1 ? $("#design-canvas").append(html) : (f = $(ui.item).context,  $(f).replaceWith(html));
            droppableGridInit(html)
        }

        function containerSortableInit(n, ele) {
            var r = "";
            r = $(ele).hasClass("gv-droppable-grid") ? ".gv-droppable-grid, .tab-canvas" : "#design-canvas, .gv-droppable-grid, .tab-canvas";
            $(ele).sortable({
                delay: 200,
                connectWith: "#design-canvas, .gv-droppable-grid, .tab-canvas",
                tolerance: "pointer",
                appendTo: "#design-canvas",
                helper: "clone",
                start: function (n, i) {
                    if (dragging == true) {
                        var r = generateEle(componentType, i);
                        i.placeholder.html($(r).html())
                    } else
                        i.placeholder.height("25")
                },
                placeholder: "ui-state-highlight",
                stop: function (event, ui) {
                    switch (l) {
                        case "menu1Column":
                        case "menu2Columns":
                        case "menu3Columns":
                        case "menu4Columns":
                        case "twoandten":
                            createContainer(event, ui, l);
                            break;
                        case "_collapsible":
                            createAccordion(event, ui);
                            break;
                        case "_tab":
                            createTab(event, ui);
                            break;
                        case "_panel":
                            createPanel(event, ui);
                            break;
                        default:
                            if(l != null){
                                ut(event, ui, l)
                            }
                    }
                    l = null;
                    componentId = null;
                    dragging = false
                }
            });
            $(ele).click(function () {
                var e, i, n, r, u;
                (a += 1,
                a == 1) && (e = $(this).hasClass("hasFocus"),
                    $(".hasFocus").removeClass("hasFocus"),
                e == false && ($(this).addClass("hasFocus"),
                    c = "column"),
                    i = $(".hasFocus"),
                    f = $("#fieldproperties"),
                    f.children().fadeOut(0),
                    n = $("#columnproperties"),
                    $(n).fadeIn(0),
                    r = $(i).attr("data-alignment"),
                r != "undefined" && $('select[data-gv-property="groupalignment"]', n).val(r).attr("selected", true),
                    u = $(i).attr("data-size"),
                u != "undefined" && $('select[data-gv-property="groupsize"]', n).val(u).attr("selected", true))
            })
        }

        function droppableGridInit(n) {
            $(".gv-droppable-grid", n).each(function (index, item) {
                containerSortableInit(index, item)
            })
        }

        function domEventInit() {

            /**
             * 给select的options重新排序
             */
            function optionsReorder() {
                var selectedValue = $("option:selected", u).val();
                $("select", u).children().remove();
                $("#__selectOptions > li").each(function () {
                    var n = $('input[type="text"]', this).val();
                    $("select", u).append($('<option value="' + n + '">' + n + "<\/option>"))
                });
                $("select", u).val(selectedValue).attr("selected", true);
            }

            f = $("#fieldproperties");
            $('input[data-gv-property="control-name"]', f).keyup(function () {
                var n = $(this).val();
                $(".controls input", u).attr("name", n);
                $(".controls textarea", u).attr("name", n);
                $(".controls select", u).attr("name", n)
            });
            $('input[data-gv-property="control-id"]', f).keyup(function () {
                var n = $(this).val();
                $(".controls input", u).attr("id", n);
                $(".controls textarea", u).attr("id", n);
                $(".controls select", u).attr("id", n);
                $("label", u).attr("for", n)
            });
            $('textarea[data-gv-property="control-data-attribute"]', f).keyup(function () {
                var n = $(this).val();
                $(".controls input", u).attr("data-bind", n);
                $(".controls textarea", u).attr("data-bind", n);
                $(".controls select", u).attr("data-bind", n)
            });
            $('input[data-gv-property="label"]', f).keyup(function () {
                var o = this, i = $(this).val(), r = $(u).find("label:first"), f, n;
                r.text(i);
                $(r).removeClass("sr-only");
                e.isRequired() ? r.append($('<span class="req"> *<\/span>')) : i == "" && $(r).addClass("sr-only");
                e.isAdmin() && r.prepend($('<i class="icon-lock"><\/i>'));
                i !== "" && (f = $(o).parentsUntil(".row").parent().parent().find('input[data-gv-property="control-id"]'),
                f.length === 1 && (n = S(i).camelize(i).s,
                    f.val(n),
                    $(".controls input", u).attr("id", n),
                    $(".controls textarea", u).attr("id", n),
                    $(".controls select", u).attr("id", n),
                    $("label", u).attr("for", n)))
            });
            $('input[data-gv-property="value"]', f).keyup(function () {
                var n = $(this).val()
                    , i = $(u).find('.controls > input[type="text"]:first');
                i.length > 0 ? i.val(n) : $(u).find(".controls > textarea").val(n)
            });
            $('input[data-gv-property="placeholder"]', f).keyup(function () {
                var n = $(this).val();
                $(u).find('input[type="text"]:first').attr("placeholder", n)
            });
            $("#textminvalue, #textmaxvalue", f).keyup(function () {
                var i = parseInt($(this).attr("value")),
                    r = this.id == "textminvalue" ? "data-minwords" : "data-maxwords", n;
                i <= 0 || isNaN(i) ? (n = $(u).find('.controls > input[type="text"]:first'),
                    n.length > 0 ? n.removeAttr(r) : n = $(u).find(".controls > textarea").removeAttr(r)) : (n = $(u).find('.controls > input[type="text"]:first'),
                    n.length > 0 ? n.attr(r, i) : n = $(u).find(".controls > textarea").attr(r, i))
            });
            $("#prevPage", f).keyup(function () {
                var n = $("li.previous a", u);
                $(n).text(this.value)
            });
            $("#nextPage", f).keyup(function () {
                var n = $("li.next a", u);
                $(n).text(this.value)
            });
            $("#allowother", f).change(function () {
                if ($(this).is(":checked") == true) {
                    var i = $('.controls input[type="radio"]:first', u)
                        , n = getControlCount("field");
                    $(".controls", u).append($('<div class="radio"><input id="' + getControlCount("field") + '" type="radio" name="' + i.attr("name") + '"><input id="' + n + '" type="text" class="form-control"><\/div><\/div>'));
                    $("#" + n, u).keyup(function () {
                        $(this).siblings('input[type="radio"]').val(this.value)
                    })
                } else
                    $('.controls input[type="radio"]:last', u).parent("div").remove()
            });
            $('input[data-gv-property="required"]', f).change(function () {
                var f = $(u).find("label.control-label, label.control-label-left"), i,
                    o = $('<span class="req"> *<\/span>'), r, n;
                $(this).is(":checked") == true ? (f.append(o),
                    i = true) : ($(":last", f).remove(),
                    i = false);
                switch (e.controlType) {
                    case "text":
                        n = $('input[data-role="text"]', u);
                        i == true ? n.attr("required", "required") : n.removeAttr("required");
                        break;
                    case "lookup":
                        n = $('input[data-role="lookup"]', u);
                        i == true ? n.attr("required", "required") : n.removeAttr("required");
                        break;
                    case "checkbox":
                        i == true ? (r = $('input[type="checkbox"]', u),
                            r.attr("required", "required"),
                            labelFor(r[0].name)) : ($('input[type="checkbox"]', u).removeAttr("required"),
                            $("label.error", u).remove());
                        break;
                    case "radio":
                        i == true ? $('input[type="radio"]', u).attr("required", "required") : $('input[type="radio"]', u).removeAttr("required");
                        break;
                    case "select":
                        i == true ? $("select", u).attr("required", "required") : $("select", u).removeAttr("required");
                        break;
                    case "date":
                        n = $('input[data-role="date"]', u);
                        i == true ? n.attr("required", "required") : n.removeAttr("required");
                        break;
                    case "time":
                        n = $('input[data-role="time"]', u);
                        i == true ? n.attr("required", "required") : n.removeAttr("required");
                        break;
                    case "datetime":
                        n = $('input[data-role="datetime"]', u);
                        i == true ? n.attr("required", "required") : n.removeAttr("required");
                        break;
                    case "numeric":
                        n = $('input[data-role="numeric"]', u);
                        i == true ? n.attr("required", "required") : n.removeAttr("required");
                        break;
                    case "mask":
                        n = $('input[data-role="mask"]', u);
                        i == true ? n.attr("required", "required") : n.removeAttr("required");
                        break;
                    case "textarea":
                        n = $("textarea", u);
                        i == true ? n.attr("required", "required") : n.removeAttr("required")
                }
            });
            $("input[name=__role]:radio", f).change(function () {
                var n = $("input[name=__role]:radio").index($(this)), i;
                n > 1 && (n = n % 2);
                i = $(u).find("label:first");
                n == 0 ? $(i).find(".icon-lock:first").remove() : $(i).prepend($('<i class="icon-lock"><\/i>'))
            });
            $('input[data-gv-property="sublabel"]', f).keyup(function () {
                var n = $(this).val(), i;
                n == "" ? $(".help-block", u).remove() : (i = $(".help-block", u),
                    i.length == 0 ? $('<span class="help-block">' + n + "<\/span>").appendTo($(".controls", u)) : $(i).text(n))
            });
            $('#fieldproperties textarea[data-gv-property="hover"]').keyup(function () {
                var n = $(this).val();
                n == "" ? ($(u).removeAttr("rel").removeAttr("data-trigger").removeAttr("data-content").removeAttr("data-original-title"),
                    $(u).popover("destroy")) : ($(u).attr("rel", "popover").attr("data-trigger", "hover").attr("data-content", n).attr("data-original-title", ""),
                    $(u).popover())
            });
            $('select[data-gv-property="layout"]', f).change(function () {
                n.spread()
            });
            $('select[data-gv-property="alignment"]', f).change(function () {
                alignment(u, $(this).val())
            });
            $('select[data-gv-property="groupalignment"]', f).change(function () {
                var n = $(this).val()
                    , i = $(".hasFocus");
                $(i).attr("data-alignment", n);
                $(".form-group", i).each(function () {
                    alignment(this, n)
                })
            });
            $('select[data-gv-property="size"]', f).change(function () {
                var n = null;
                switch ($(this).val()) {
                    case "Mini":
                        n = "input-mini";
                        break;
                    case "Small":
                        n = "input-small";
                        break;
                    case "Medium":
                        n = "input-medium";
                        break;
                    case "Large":
                        n = "input-large";
                        break;
                    case "Extra Large":
                        n = "input-xlarge"
                }
                $(u).find(".controls").removeClass("input-mini").removeClass("input-small").removeClass("input-medium").removeClass("input-large").removeClass("input-xlarge");
                n != null && $(u).find(".controls").addClass(n)
            });
            $('select[data-gv-property="groupsize"]', f).change(function () {
                var n = $(this).val()
                    , i = $(".hasFocus");
                $(i).attr("data-size", n);
                $(".form-group", i).each(function () {
                    var i = null;
                    switch (n) {
                        case "Mini":
                            i = "input-mini";
                            break;
                        case "Small":
                            i = "input-small";
                            break;
                        case "Medium":
                            i = "input-medium";
                            break;
                        case "Large":
                            i = "input-large";
                            break;
                        case "Extra Large":
                            i = "input-xlarge"
                    }
                    $(this).find(".controls").removeClass("input-mini").removeClass("input-small").removeClass("input-medium").removeClass("input-large").removeClass("input-xlarge");
                    i != null && $(this).find(".controls").addClass(i)
                })
            });
            $("#__tabItems").sortable({
                axis: "y",
                placeholder: "ui-state-highlight",
                cursor: "move",
                delay: 200,
                helper: "clone",
                start: function (n, t) {
                    dragging = false;
                    t.placeholder.height(t.item.height())
                },
                stop: function (n, i) {
                    var e = $(i.item).context, r = $("input", e).attr("data-id"), o = $("#design-canvas"),
                        u = $("#" + r, o).parent().parent(), f;
                    $("#__tabItems li").each(function () {
                        r = $("input", this).attr("data-id");
                        f = $("#" + r, u).parent();
                        $(f).appendTo(u)
                    })
                }
            });
            $("#__accordionItems").sortable({
                axis: "y",
                placeholder: "ui-state-highlight",
                cursor: "move",
                delay: 200,
                helper: "clone",
                start: function (n, t) {
                    dragging = false;
                    t.placeholder.height(t.item.height())
                },
                stop: function (n, i) {
                    var f = $(i.item).context
                        , r = $("input", f).attr("data-id")
                        , u = $("#design-canvas")
                        , e = $("#" + r, u)
                        , o = $(e).closest(".panel-group");
                    $("#__accordionItems li").each(function () {
                        r = $("input", this).attr("data-id");
                        accordionGroup = $("#" + r, u).closest(".panel");
                        $(accordionGroup).appendTo(o)
                    })
                }
            });
            $("#__selectOptions, #__checkboxOptions, #__radioItems").sortable({
                axis: "y",
                placeholder: "ui-state-highlight",
                cursor: "move",
                delay: 200,
                helper: "clone",
                start: function (n, t) {
                    t.placeholder.height(t.item.height())
                },
                stop: function () {
                    var currentValue, a;
                    if (e.controlType == "select") {
                        currentValue = $("option:selected", u).val();
                        optionsReorder();
                        $('#__selectOptions input[type="text"]').each(function () {
                            if (this.value == currentValue) {
                                // $(this).siblings(".input-group-addon").attr("checked", true)
                                $(":input[type='radio']", $(this).prev()).attr("checked", true);
                            }
                        });
                    }

                    if (e.controlType == "checkbox") {
                        var canvasOptions = $(".controls", u)
                            , html = $("<div><\/div>")
                            ,
                            propertyOptions = $("#fieldproperties #" + e.controlType + "properties  #__checkboxOptions")
                            , opt_p, opt_c;
                        $(propertyOptions).children().each(function () {
                            opt_p = $('input[type="checkbox"]', this).attr("name");
                            $(canvasOptions).children().each(function () {
                                opt_c = $('input[type="checkbox"]', this).attr("id");
                                if (opt_p == opt_c) {
                                    $(html).append($(this).clone())
                                }
                            })
                        });
                        $(canvasOptions).children().remove();
                        $(html).children().each(function () {
                            $(canvasOptions).append($(this).clone())
                        });
                        n.spread();
                    }

                    if (e.controlType == "radio") {
                        var canvasOptions = $(".controls", u)
                            , html = $("<div><\/div>")
                            , propertyOptions = $("#fieldproperties #" + e.controlType + "properties  #__radioItems")
                            , opt_p, opt_c, opt_checked;
                        propertyOptions.children().each(function () {
                            opt_p = $('input[type="radio"]', this).attr("id");
                            canvasOptions.children().each(function () {
                                opt_c = "_" + $('input[type="radio"]', this).attr("id");
                                opt_p == opt_c && html.append($(this).clone());
                                if ($('input[type="radio"]:checked', this).length > 0) {
                                    opt_checked = opt_c
                                }
                            });
                            if (opt_checked == opt_p) {
                                $('input[type="radio"]', this).attr("checked", true)
                            }
                        });
                        a = $("#fieldproperties #allowother:checked");
                        if (a.length > 0) {
                            html.append($('.controls input[type="radio"]:last', u).parent("div").clone());
                        }
                        canvasOptions.children().remove();
                        html.children().each(function () {
                            canvasOptions.append($(this).clone())
                        });
                        n.spread();
                    }
                }
            });
            $("#borderLeft").click(function () {
            })
        }

        function ht(n) {
            this.controlType = "";
            this.label = function () {
                var i = $(n).find("label:first").clone();
                return $('span[class*="req"]', i).text(""),
                    i.text()
            }
            ;
            this.isRequired = function () {
                return $("#fieldproperties #" + this.controlType + 'properties input[data-gv-property="required"]').is(":checked")
            }
            ;
            this.isAdmin = function () {
                return $("#fieldproperties #" + this.controlType + 'properties input[id="__a"]:radio').attr("checked") == "checked"
            }
            ;
            this.init = function () {
                var b = $(n).hasClass("hasFocus"), a, h, v, y, p, r, u, e, w, c, o;
                if ($(".hasFocus").removeClass("hasFocus"),
                        b == false ? ($(n).removeClass("hoverComponent"),
                            $(n).addClass("hasFocus"),
                            this.controlType = $(n).attr("data-control-type")) : $(n).addClass("hoverComponent"),
                        f = $("#fieldproperties"),
                        f.children().fadeOut(0),
                    this.controlType != "") {
                    var s = $(n).find("label:first"), l = $(":first", s).hasClass("icon-lock"),
                        k = $(":last", s).hasClass("req"), i;
                    this.controlType == "textarea" ? (i = $(".controls textarea", n),
                        $('input[data-gv-property="control-id"]', f).val($(i).attr("id")),
                        $('input[data-gv-property="control-name"]', f).val($(i).attr("name")),
                        $('textarea[data-gv-property="control-data-attribute"]', f).val($(i).attr("data-bind"))) : this.controlType == "select" ? (i = $(".controls select", n),
                        $('input[data-gv-property="control-id"]', f).val($(i).attr("id")),
                        $('input[data-gv-property="control-name"]', f).val($(i).attr("name")),
                        $('textarea[data-gv-property="control-data-attribute"]', f).val($(i).attr("data-bind"))) : this.controlType == "numeric" ? (i = $('.controls > span > span > input[type="text"]:last', n),
                        $('input[data-gv-property="control-id"]', f).val($(i).attr("id")),
                        $('input[data-gv-property="control-name"]', f).val($(i).attr("name"))) : (i = $(".controls input", n),
                        $('input[data-gv-property="control-id"]', f).val($(i).attr("id")),
                        $('input[data-gv-property="control-name"]', f).val($(i).attr("name")),
                        $('textarea[data-gv-property="control-data-attribute"]', f).val($(i).attr("data-bind")));
                    a = $("#" + this.controlType.toLowerCase() + "properties");
                    $(a).fadeIn(0);
                    $("#" + this.controlType + 'properties input[data-gv-property="label"]', f).val(this.label);
                    val = $(n).attr("data-content");
                    $("#" + this.controlType + 'properties textarea[data-gv-property="hover"]', f).val(val);
                    h = $(".help-block", n);
                    val = "";
                    h.length > 0 && (val = $(h).text());
                    $("#" + this.controlType + 'properties input[data-gv-property="sublabel"]', f).val(val);
                    v = $("#" + this.controlType + 'properties input[id="__a"]:radio', f);
                    y = $("#" + this.controlType + 'properties input[id="__e"]:radio', f);
                    $(v).attr("checked", l);
                    $(y).attr("checked", !l);
                    p = $("#" + this.controlType + 'properties input[data-gv-property="required"]', f);
                    $(p).prop("checked", k);
                    r = $(n).children("div.controls");
                    u = $("#" + this.controlType + 'properties select[data-gv-property="size"]', f);
                    $(u).val("Stretched").attr("selected", true);
                    $(r).hasClass("input-mini") && $(u).val("Mini").attr("selected", true);
                    $(r).hasClass("input-small") && $(u).val("Small").attr("selected", true);
                    $(r).hasClass("input-medium") && $(u).val("Medium").attr("selected", true);
                    $(r).hasClass("input-large") && $(u).val("Large").attr("selected", true);
                    $(r).hasClass("input-xlarge") && $(u).val("Extra Large").attr("selected", true);
                    (this.controlType == "checkbox" || this.controlType == "radio") && (e = $("#" + this.controlType + 'properties select[data-gv-property="layout"]', f),
                        $(e).val("One Column").attr("selected", true),
                    $(r).children().hasClass("col-md-6") && $(e).val("Two Columns").attr("selected", true),
                    $(r).children().hasClass("col-md-3") && $(e).val("Four Columns").attr("selected", true),
                    $(r).children().hasClass("col-md-4") && $(e).val("Three Columns").attr("selected", true),
                    $(r).children().hasClass("inline") && $(e).val("Inline").attr("selected", true));
                    w = $("#" + this.controlType + 'properties select[data-gv-property="alignment"]', f);
                    c = "Top";
                    $(n).hasClass("form-horizontal") && (c = $(s).hasClass("control-label-left") ? "Left" : "Right");
                    $(w).val(c).attr("selected", true);
                    o = $('input[type="text"]:first', n);
                    o.length > 0 && ($("#" + this.controlType + 'properties input[data-gv-property="value"]', f).val(o.val()),
                        $("#" + this.controlType + 'properties input[data-gv-property="placeholder"]', f).val(o.attr("placeholder")))
                }
            }
            ;
            this.init();
            n: {
                this.value = function () {
                    return $(n).find(".controls > input:first").val()
                }
            }
        }

        function renderComponent() {
            console.log("bt函数");
            var designCanvas = $("#design-canvas"), o;
            $('[data-role="select"]', designCanvas).each(function () {
            });
            $('[data-role="date"]', designCanvas).each(function () {
                console.log("kendoDatePicker");
                $(this).kendoDatePicker();
                var n = $(this).data("kendoDatePicker")
            });
            $('[data-role="datetime"]', designCanvas).each(function () {
                console.log("datetime");
                var n = $(this).kendoDateTimePicker().data("kendoDateTimePicker")
            });
            $('[data-role="time"]', designCanvas).each(function () {
                $(this).kendoTimePicker();
                var n = $(this).data("kendoTimePicker")
            });
            $('[data-role="numeric"]', designCanvas).siblings(".k-select").remove();
            $('[data-role="numeric"]', designCanvas).each(function () {
                $(this).kendoNumericTextBox();
                var n = $(this).data("kendoNumericTextBox")
            });
            $('[data-role="mask"]', designCanvas).each(function () {
                $(this).inputmask($(this).attr("data-mask"))
            });
            o = function (n) {
                var u = n.attr("data-property-orientation"), f = n.attr("data-property-min"),
                    e = n.attr("data-property-max"), o = n.attr("data-property-showbutton"), t = n.clone(),
                    i = n.parentsUntil(".controls"), r;
                i.children().remove();
                t.css("display", "");
                i.append(t);
                r = t.kendoSlider().data("kendoSlider")
            }
            ;
            $('[data-role="slider"]', designCanvas).each(function () {
                o($(this))
            });
            $('[data-role="editor"]', designCanvas).each(function () {
                var n = $(this).kendoEditor()
            });
            $('[data-role="datatable"]', designCanvas).each(function (n, i) {
                $(i).bootstrapTable("destroy")
            });
            $('[data-role="datatable"]', designCanvas).each(function (n, i) {
                var isStriped = $(i).attr("data-striped") == "true"
                    , isHover = $(i).attr("data-hover") == "true"
                    , isPagination = $(i).attr("data-pagination") == "true"
                    , isShowRefresh = $(i).attr("data-show-refresh") == "true"
                    , isSearch = $(i).attr("data-search") == "true"
                    , isShowCol = $(i).attr("data-show-columns") == "true";
                $(i).bootstrapTable({
                    hover: isHover,
                    pagination: isPagination,
                    striped: isStriped,
                    search: isSearch,
                    showRefresh: isShowRefresh,
                    showColumns: isShowCol
                })
            });
            $(".form-group", designCanvas).hover(function () {
                if ($(this).hasClass("hasFocus") == false) {
                    $(this).addClass("hoverComponent")
                }
            }, function () {
                $(this).removeClass("hoverComponent")
            });
            $(".form-group", designCanvas).click(function (i) {
                var s, o;
                (a += 1,
                    s = $(i.target).context.nodeName,
                    u = $(this),
                "__,SELECT,INPUT,OPTION,TEXTAREA".indexOf(s) > 0 && $(u).hasClass("hasFocus")) || (e = new ht(u),
                    c = e.controlType,
                    n.selectedControlType = e.controlType,
                    n.selectedProperty = f,
                    n.selectedGroup = u,
                e.controlType != r && (o = getComponentOptions(e.controlType),
                o != r && o.click(u, f)))
            });
            $("div.gv-droppable-grid", designCanvas).each(function (n, t) {
                containerSortableInit(n, t)
            });
            $("div.panel-group", designCanvas).each(function () {
                rt(this, $(".panel-body", this))
            });
            $('div[data-role="panel"]', designCanvas).each(function () {
                panelEventInit(this, $(".panel-body", this))
            });
            $('div[data-role="tab"]', designCanvas).each(function () {
                tabEventInit(this)
            });
            $("div.gv-page", designCanvas).each(function () {
                $(this).click(function () {
                    ct(this)
                })
            });
            showPager()
        }

        function ct(n) {
            var e, i, r;
            u = n;
            e = $(n).hasClass("hasFocus");
            $(".hasFocus").removeClass("hasFocus");
            e == false && ($(n).addClass("hasFocus"),
                c = "pagebreak");
            f = $("#fieldproperties");
            pageProperties = $("#pageproperties");
            liPrev = $("li.previous", n);
            liNext = $("li.next", n);
            f.children().fadeOut(0);
            pageProperties.fadeIn(0);
            i = $("#nextPage", f);
            $(liNext).is(":hidden") == true ? $(i).parent().parent().parent().hide() : ($(i).parent().parent().parent().show(),
                $(i).val($("a", liNext).text()));
            r = $("#prevPage", f);
            $(liPrev).is(":hidden") == true ? $(r).parent().parent().parent().hide() : ($(r).parent().parent().parent().show(),
                $("#prevPage", f).val($("a", liPrev).text()))
        }


        var componentBox = [], u, f, e, c, l, componentId, componentType, dragging, a, at, yt, publish, downloadHtml;

        /**
         * 组件的注册
         * @param name：组件名
         * @param options：
         */
        n.register = function (name, options) {
            componentBox.push([name, options])
        };

        n.newName = function (n) {
            var i = $("#design-canvas").attr("data-control-count");
            return i = parseInt(i) + 1,
                $("#design-canvas").attr("data-control-count", i),
            n + i.toString()
        }
        ;
        a = 0;
        at = 0;
        n.selectedGroup = u;
        n.selectedProperty = f;
        n.selectedControlType = c;
        $("#removeMenu").click(function () {
            var n = $("#design-canvas .hasFocus"), i = 0, o, r, f, u, e, s;
            if (c == "column") {
                o = $(n).siblings();
                i = tt(n);
                i != 12 ? $(n).next().length > 0 ? (r = $(n).next(),
                    f = tt(r),
                    $(r).removeClass("col-md-" + f),
                    $(r).addClass("col-md-" + (i + f)),
                    $(n).hide("slow", function () {
                        $(n).remove()
                    })) : (u = $(n).prev(),
                    e = tt(u),
                    $(u).removeClass("col-md-" + e),
                    $(u).addClass("col-md-" + (i + e)),
                    $(n).hide("slow", function () {
                        $(n).remove()
                    })) : $(n).parent().hide("slow", function () {
                    $(n).parent().remove()
                });
                switch (o.length) {
                }
            } else
                c == "tab" ? $(n).remove() : c == "pagebreak" ? ($(n).parent().remove(),
                    s = $("#design-canvas"),
                $(".gv-page", s).length == 0 && $("#lastPage").remove(),
                    showPager()) : c == "panel" ? $(n).remove() : $(n).hide("slow", function () {
                    $(n).remove()
                })
        });


        $("#addTabs").click(function () {
            var n = $(document.getElementById("tabTemplate")).clone(), i;
            $(n).attr("id", getControlCount("tab"));
            $(".tab-content", n).children().each(function (i) {
                var r = getControlCount("");
                $(this).attr("id", "tabContent" + r);
                $("li:eq(" + i + ") a", n).attr("href", "#tabContent" + r).attr("id", "tabLabel" + r)
            });
            $("#design-canvas").append(n);
            $(n).find(".tab-canvas").each(function () {
                $(this).sortable({
                    appendTo: "#design-canvas",
                    delay: 200,
                    tolerance: "pointer",
                    connectWith: "#design-canvas, .gv-droppable-grid, .tab-canvas",
                    helper: "clone",
                    start: function (n, t) {
                        dragging = false;
                        t.placeholder.height("25")
                    },
                    placeholder: "ui-state-highlight",
                    stop: function () {
                    }
                })
            });
            $(n).click(function () {
                var n = $(this).hasClass("hasFocus");
                n == false
            });
            i = function (n) {
                $(n).unbind("hover");
                $(n).hover(function () {
                    var n = $('<div class="tab-toolbar"><i class="item icon-cog"><\/i><\/div>');
                    $(this).append(n);
                    $(".icon-cog", n).click(function () {
                        var s = $(this).hasClass("hasFocus"), n = $("#fieldproperties"), o = $("#tabproperties"),
                            i = $("ul.nav-tabs", $(this).parent().parent()), r, u, f, e;
                        $(".hasFocus").removeClass("hasFocus");
                        n.children().fadeOut(0);
                        o.fadeIn(0);
                        s == false && (c = "tab");
                        r = function () {
                            $("#__tabItems", n).children().remove();
                            $("li", i).each(function (n, i) {
                                var r = $("a", i).text().trim()
                                    , u = $("a", i).attr("id")
                                    ,
                                    f = $('<li"><div class="form-horizontal"><input data-id="' + u + '" class="input-medium prop-option" value="' + r + '" type="text" /> <i class="icon-add" ><\/i> <i class="icon-delete" ><\/i><i class="icon-reorder"><\/i><\/div><\/li>');
                                $("#__tabItems", o).append(f)
                            })
                        }
                        ;
                        r();
                        u = function () {
                            $("#__tabItems input", n).unbind("keyup");
                            $("#__tabItems input", n).keyup(function () {
                                var n = $(this).attr("data-id")
                                    , i = $("#" + n);
                                $(i).text(this.value)
                            })
                        }
                        ;
                        u();
                        f = function () {
                            $("#__tabItems .icon-add", n).unbind("click");
                            $("#__tabItems .icon-add", n).click(function () {
                                var n = getControlCount("")
                                    ,
                                    o = $('<li><a id="tabLabel"' + n + '" href="#tabContent' + n + '" data-toggle="tab">Undefined<\/a><\/li>')
                                    ,
                                    s = $('<div class="tab-pane" id="tabContent' + n + '"><div class="tab-canvas"><\/div><\/div>');
                                $("ul.nav-tabs", $(i).parent()).append(o);
                                $("div.tab-content", $(i).parent()).append(s);
                                r();
                                f();
                                e();
                                u()
                            })
                        }
                        ;
                        f();
                        e = function () {
                            $("#__tabItems .icon-delete", n).unbind("click");
                            $("#__tabItems .icon-delete", n).click(function () {
                                var i = $(this).siblings("input").attr("data-id")
                                    , n = $("#" + i)
                                    , r = $(n).attr("href").replace("#", "")
                                    , u = $("#" + r);
                                $(this).parent().parent().remove();
                                $(n).hide("slow", function () {
                                    $(n).parent().remove();
                                    $(u).remove()
                                })
                            })
                        }
                        ;
                        e()
                    })
                }, function () {
                    $(".tab-toolbar", this).remove()
                })
            }
            ;
            i(n);
            $(n).find(".gv-droppable-grid").each(function (n, i) {
                $(i).sortable({
                    connectWith: "#design-canvas, .gv-droppable-grid, .tab-canvas",
                    delay: 200,
                    tolerance: "pointer",
                    appendTo: "#design-canvas",
                    helper: "clone",
                    start: function (n, t) {
                        dragging = false;
                        t.placeholder.height("25")
                    },
                    placeholder: "ui-state-highlight",
                    stop: function (n, t) {
                        ut(n, t, l);
                        l = null
                    }
                });
                $(i).click(function () {
                    a += 1
                })
            })
        });
        $("#addPageBreak").click(function () {
            var n = $(document.getElementById("pageBreakTemplate")).clone();
            $("#design-canvas").append(n);
            $(n).attr("id", getControlCount("pb"));
            $("ul", n).parent().click(function () {
                var n = $(this).hasClass("hasFocus");
                $(".hasFocus").removeClass("hasFocus");
                n == false && ($(this).addClass("hasFocus"),
                    c = "pagebreak")
            })
        });
        yt = function () {
            var n = 0;
            return function (t, i) {
                clearTimeout(n);
                n = setTimeout(t, i)
            }
        }();

        n.spread = function () {
            var n, f = 0, o = false, r = 0,
                s = $("#fieldproperties #" + e.controlType + 'properties select[data-gv-property="layout"] option:selected').val();
            switch (s) {
                case "Inline":
                    n = "inline";
                    e.controlType == "radio";
                    break;
                case "One Column":
                    n = "";
                    break;
                case "Two Columns":
                    n = "col-md-6";
                    r = 2;
                    break;
                case "Three Columns":
                    n = "col-md-4";
                    r = 3;
                    break;
                case "Four Columns":
                    n = "col-md-3";
                    r = 4
            }
            $(".controls", u).children().each(function () {
                for (f = f + 1,
                         i = 0; i != 12; i++)
                    $(this).removeClass("col-md-" + i).removeClass("field-layout-reset radio-inline checkbox-inline");
                e.controlType == "checkbox" ? $(this).addClass("checkbox") : e.controlType == "radio" && $(this).addClass("radio");
                r != 0 ? ($(this).addClass(n),
                o == true && $(this).addClass("field-layout-reset"),
                    o = f % r == 0 ? true : false) : n == "inline" && (e.controlType == "checkbox" ? $(this).addClass("checkbox-inline").removeClass("checkbox") : e.controlType == "radio" && $(this).addClass("radio-inline").removeClass("radio"))
            })
        };

        n.Initialize = function () {
            var u = $("#designform"), menuSettings, saveData, formName;
            $("#fieldproperties").children().fadeOut();
            $("[rel=tooltip]").tooltip();
            $("[rel=popover]").popover();
            $("#design-canvas").click(function () {
                a = 0
            });
            $("#preview").click(function () {
                // var u = $(window).height() * .9, s = ($(window).height() - u) / 2, f = $(window).width() * .7,
                //     h = ($(window).width() - f) / 2, r = $("#modalPreview").clone(false).css({
                //         height: u,
                //         "max-height": u,
                //         "max-width": f,
                //         width: f,
                //         overflow: "hidden"
                //     }), o = $(".modal-body", r).empty().css({
                //         "max-height": u,
                //         height: u,
                //         "max-width": f,
                //         width: f,
                //         overflow: "hidden",
                //         padding: "20px"
                //     }), c = $("#design-canvas").html(), i = $('<div class="form-horizontal"><\/div>').append(c), e;
                // $("[id]", i).each(function (n, r) {
                //     var u = $(r).attr("id"), f, o, e, s;
                //     $(r).attr("id", u + "__prev");
                //     f = $('[href="#' + u + '"]', i);
                //     o = $(f).attr("href");
                //     $(f).attr("href", o + "__prev");
                //     e = $('[data-parent="#' + u + '"]', i);
                //     s = $(e).attr("data-parent");
                //     $(e).attr("data-parent", s + "__prev")
                // });
                // $("div", i).each(function () {
                //     $(this).before(" ").after(" ")
                // });
                // $('[data-role="pagebreak"]', i).length > 0 && ($(i).append($("#designform #lastPage").clone(false, false).removeClass("container").removeClass("lp")),
                //     e = 0,
                //     $(i).children().each(function () {
                //         var n, i;
                //         $(this).attr("data-role") == "pagebreak" ? e = e + 1 : (n = $(this).siblings('[data-role="pagebreak"]:eq(' + e + ")"),
                //             $(this).appendTo(n),
                //             i = $("div.gv-page", n),
                //             $(i).appendTo(n))
                //     }));
                // $("div", i).removeClass("mcanvas").removeClass("ui-sortable");
                // $("input select", i).removeAttr("id");
                // $("div", i).removeAttr("data-control-count").removeAttr("data-width");
                // $("div", i).removeAttr("data-control-type");
                // $("input", i).removeAttr("data-role");
                // $("div", i).removeAttr("data-alignment");
                // $(".ui-sortable", i).removeClass("ui-sortable");
                // $(".form-group-m", i).removeClass("form-group-m");
                // $(".hasFocus", i).removeClass("hasFocus");
                // $(".hoverComponent", i).removeClass("hoverComponent");
                // $(".gv-page", i).removeClass("gv-page");
                // $(i).find(".gv-droppable-grid").each(function () {
                //     $(this).removeClass("gv-droppable-grid")
                // });
                // $(i).find("readonly").each(function () {
                //     $(this).removeClass("readonly")
                // });
                // $(o).append(i);
                // $(r).css({
                //     "margin-top": function () {
                //         return s
                //     },
                //     "margin-left": function () {
                //         return h
                //     }
                // }).removeClass("hidden").on("hidden.bs.modal", function () {
                //     $(o).empty()
                // }).on("shown.bs.modal", function () {
                //     alert("D")
                // }).modal("show");
                // $(o).lionbars();
                // $('[data-role="pagebreak"]', i).fadeOut(0);
                // $('[data-role="pagebreak"]:first', i).fadeIn(0);
                // $("#fullsize", r).click(function () {
                //     $(r).css("height", "100%").css("max-height", "100%").css("margin-top", "0px").css("width", "100%").css("max-width", "100%").css("margin-left", "0px");
                //     $(".modal-body", r).css("height", "100%").css("max-height", "100%").css("margin-top", "0px").css("width", "100%").css("max-width", "100%").css("margin-left", "0px");
                //     $("#presize", r).css("visibility", "visible").css("width", "");
                //     $("#pfullsize", r).css("visibility", "hidden").css("width", "0px")
                // });
                // $("#presize", r).click(function () {
                //     $("#presize", r).css("visibility", "hidden").css("width", "0px");
                //     $("#pfullsize", r).css("visibility", "visible").css("width", "");
                //     var n = $(window).height() * .9
                //         , u = ($(window).height() - n) / 2
                //         , i = $(window).width() * .7
                //         , f = ($(window).width() - i) / 2;
                //     $(r).css("height", n).css("max-height", n).css("margin-top", u).css("width", i).css("max-width", i).css("margin-left", f);
                //     $(".modal-body", r).css("height", n).css("max-height", n).css("margin-top", u).css("width", i).css("max-width", i)
                // });
                // $("#previewDownload", r).click(function (t) {
                //     t.preventDefault();
                //     n(true)
                // });
                // $('[data-role="pagebreak"]', i).find("li.previous").click(function () {
                //     var n = $(this).closest('[data-role="pagebreak"]');
                //     n.fadeOut(0);
                //     n.prev().fadeIn(0)
                // });
                // $('[data-role="pagebreak"]', i).find("li.next").click(function () {
                //     var n = $(this).closest('[data-role="pagebreak"]');
                //     n.fadeOut(0);
                //     n.next().fadeIn(0)
                // })
            });
            $("#publish").click(function () {
                // publish(false)
            });
            $("#download").click(function (t) {
                // t.preventDefault();
                // saveData(true)
            });
            menuSettings = function () {
                // var id = $("#designform").attr("data-id");
                // $.ajax({
                //     type: "get",
                //     url: "../settings",
                //     data: {
                //         id: id
                //     },
                //     cache: false,
                //     success: function (n) {
                //         var i = $("#modalAction .modal-body").text("");
                //         $("#modalAction .modal-body").css({
                //             "max-height": function () {
                //                 return $("#wrap").height()
                //             }
                //         });
                //         $(i).append(n);
                //         $("#modalAction").css({
                //             width: function () {
                //                 return $(window).width() * .6
                //             },
                //             "margin-top": function () {
                //                 var n = $(window).height() * .9;
                //                 return ($(window).height() - n) / 2
                //             },
                //             "margin-left": function () {
                //                 var n = $(window).width() * .7;
                //                 return ($(window).width() - n) / 2
                //             }
                //         }).removeClass("hidden").modal("show")
                //     }
                // })
            }
            ;
            $("#menuFormSetting").click(function () {
                // menuSettings()
            });
            $("#save").click(function () {
                // saveData()
            });
            saveData = function (n) {
                // var s = JSON.stringify($("#designform").serializeObject()), r = $("#designform"), u, f, id, fieldLabel, url;
                // $('[data-role="datatable"]', r).each(function (n, i) {
                //     $(i).bootstrapTable("destroy")
                // });
                // u = $(r).clone().html();
                // f = escape(u);
                // $('[data-role="datatable"]', r).each(function (n, i) {
                //     var r = $(i).attr("data-striped") == "true"
                //         , u = $(i).attr("data-hover") == "true"
                //         , f = $(i).attr("data-pagination") == "true"
                //         , e = $(i).attr("data-show-refresh") == "true"
                //         , o = $(i).attr("data-search") == "true"
                //         , s = $(i).attr("data-show-columns") == "true"
                //         , h = $(i).attr("data-height")
                //         , c = $(i).attr("data-url");
                //     $(i).bootstrapTable({
                //         hover: u,
                //         pagination: f,
                //         striped: r,
                //         search: o,
                //         showRefresh: e,
                //         showColumns: s,
                //         height: h,
                //         url: c
                //     })
                // });
                // id = $("#designform").attr("data-id");
                // fieldLabel = $("#formid").val() == "" && (i = "Untitled");
                // url = $("#save").attr("data-url");
                // t.ajax({
                //     type: "post",
                //     url: url,
                //     data: {
                //         type: 1,
                //         id: id,
                //         data: f,
                //         fieldLabel: fieldLabel
                //     },
                //     cache: false,
                //     success: function (n) {
                //         $("#formId").val(n);
                //         $("#designform").attr("data-id") != n && $("#designform").attr("data-id", n)
                //     }
                // }).done(function () {
                //     n === true && downloadHtml(true)
                // })
            };

            $("#modalActionSave").click(function () {
                // var data = JSON.stringify($("#modalAction form").serializeObject()) , id = $("#designform").attr("data-id");
                // $.ajax({
                //     type: "post",
                //     url: "../save",
                //     data: {
                //         type: 2,
                //         id: id,
                //         data: data
                //     },
                //     cache: false,
                //     success: function () {
                //         $("#modalAction").modal("hide");
                //         var n = $("#modalAction .modal-body").text("")
                //     }
                // })
            });
            $("#design-canvas").sortable({
                connectWith: "#design-canvas, .gv-droppable-grid, .tab-canvas",
                delay: 200,
                cursor: "move",
                tolerance: "pointer",
                appendTo: "#design-canvas",
                placeholder: "ui-state-highlight",
                helper: "clone",
                start: function (event, ui) {
                    if (dragging == true) {
                        var r = generateEle(componentType, ui);
                        ui.placeholder.html($(r).html())
                    } else {
                        ui.placeholder.height("25")
                    }

                },
                stop: function (event, ui) {
                    if (dragging == true)
                        switch (componentId) {
                            case "_PageBreak":
                                createPageBreak(event, ui);
                                break;
                            case "menu1Column":
                            case "menu2Columns":
                            case "menu3Columns":
                            case "menu4Columns":
                            case "twoandten":
                                createContainer(event, ui, componentId);
                                break;
                            case "_collapsible":
                                createAccordion(event, ui);
                                break;
                            case "_tab":
                                createTab(event, ui);
                                break;
                            case "_panel":
                                createPanel(event, ui)
                        }
                    showPager();
                    componentId = null;
                    l = null;
                    dragging = false
                }
            });
            $(".formControl").draggable({
                connectToSortable: ".gv-droppable-grid",
                cursor: "move",
                revert: false,
                helper: "clone",
                appendTo: "#design-canvas",
                placeholder: "formControl",
                start: function () {
                    l = componentType = $(this).attr("id");
                    componentId = null;
                    dragging = true;
                },
                drag: function () {
                },
                stop: function () {
                }
            });
            $(".widgetContainer").draggable({
                connectToSortable: ".gv-droppable-grid",
                cursor: "move",
                revert: false,
                helper: "clone",
                appendTo: "#design-canvas",
                placeholder: "widgetContainer",
                start: function () {
                    l = $(this).attr("id");
                    componentId = null;
                    componentType = $(this).attr("id");
                    dragging = true
                }
            });
            $(".formContainer").draggable({
                connectToSortable: "#design-canvas, .gv-droppable-grid, .tab-canvas",
                cursor: "move",
                revert: false,
                helper: "clone",
                appendTo: "#design-canvas",
                placeholder: "formContainer",
                start: function () {
                    componentId = $(this).attr("id");
                    l = $(this).attr("id");
                    componentType = $(this).attr("id");
                    dragging = true
                }
            });
            $(".pageBreak").draggable({
                connectToSortable: "#design-canvas",
                cursor: "move",
                revert: false,
                helper: "clone",
                appendTo: "#design-canvas",
                placeholder: "formContainer",
                start: function () {
                    componentId = $(this).attr("id");
                    l = $(this).attr("id");
                    dragging = true
                },
                stop: function () {
                    $("#design-canvas").sortable({
                        connectWith: ".tab-canvas, .gv-droppable-grid"
                    })
                }
            });
            domEventInit();
            renderComponent();
            formName = $("#formId").val()
        }
        ;
        publish = function (n) {
            // $("#designform").attr("data-id") == "00000000-0000-0000-0000-000000000000" || downloadHtml(n)
        }
        ;
        downloadHtml = function (n) {
            // var r = $("#design-canvas").clone(false, false).attr("id", "designform"), u, e, s, h;
            // $('[data-role="pagebreak"]', r).length > 0 && ($(r).append($("#designform #lastPage").clone(false, false)),
            //     u = 0,
            //     $(r).children().each(function () {
            //         var n, i;
            //         $(this).attr("data-role") == "pagebreak" ? u = u + 1 : (n = $(this).siblings('[data-role="pagebreak"]:eq(' + u + ")"),
            //             $(this).appendTo(n),
            //             i = $("div.gv-page", n),
            //             $(i).appendTo(n))
            //     }));
            // $("div", r).removeClass("mcanvas").removeClass("ui-sortable");
            // $("#design-canvas", r).attr("id", "formentry");
            // $("div", r).removeAttr("data-control-count").removeAttr("data-width").removeAttr("data-alignment").removeAttr("data-control-type");
            // $(".ui-sortable", r).removeClass("ui-sortable");
            // $(".form-group-m", r).removeClass("form-group-m");
            // $(".hasFocus", r).removeClass("hasFocus");
            // $(".hoverComponent", r).removeClass("hoverComponent");
            // $(".gv-page", r).removeClass("gv-page");
            // $(".gv-container", r).removeClass("gv-container");
            // $(".tab-canvas", r).removeClass("tab-canvas");
            // $(r).find(".gv-droppable-grid").each(function () {
            //     $(this).removeClass("gv-droppable-grid")
            // });
            // $(r).find(".readonly").each(function () {
            //     $(this).removeClass("readonly")
            // });
            // i = 0;
            // $(r).find("div.controls").each(function () {
            //     i = i + 1;
            //     id = "errId" + i;
            //     $(this).append($('<span id="' + id + '" class="error"><\/span>'));
            //     $("select", this).attr("data-parsley-errors-container", "#" + id);
            //     $("input[type=text]", this).attr("data-parsley-errors-container", "#" + id);
            //     $("input[type=checkbox]:first-child", this).attr("data-parsley-errors-container", "#" + id);
            //     $("input[type=radio]:first-child", this).attr("data-parsley-errors-container", "#" + id);
            //     $("textarea", this).attr("data-parsley-errors-container", "#" + id)
            // });
            // var o = $("#designform").attr("data-id")
            //     , c = escape($(r).html())
            //     , f = $("#design-canvas").clone(false);
            // $(f).find("span.req").each(function () {
            //     $(this).remove("span.req")
            // });
            // $(f).find("i.icon-lock").each(function () {
            //     $(this).remove("i.icon-lock")
            // });
            // e = {};
            // $('label[for^="field"]', f).each(function () {
            //     e[this.htmlFor] = this.innerHTML
            // });
            // s = JSON.stringify(e);
            // h = $("#save").attr("data-url");
            // $.ajax({
            //     type: "post",
            //     url: h,
            //     data: {
            //         type: 3,
            //         id: o,
            //         data: c,
            //         fieldLabel: escape(s)
            //     },
            //     cache: false,
            //     success: function (n) {
            //         $("#formId").val(n);
            //         n == "00000000-0000-0000-0000-000000000000" && saveForm(true)
            //     }
            // }).done(function () {
            //     if (n === true && $("#designform").attr("data-id") != "00000000-0000-0000-0000-000000000000") {
            //         var i = $("#formid").val();
            //         i || (i = "Untitled");
            //         i = i.replace(/[^a-z0-9\s]/gi, "").replace(/[_\s]/g, "-");
            //         downloadFile("/Dashboard/form/index/" + o, i + ".html")
            //     }
            // })
        }
    }(window.gvDesigner = window.gvDesigner || {}, jQuery);



$(function () {
    var element;
    gvDesigner.register("button", function () {
        var buttonComponent = {};
        var properties = $("#fieldproperties");

        function initBtnOpt() {
            $("#buttonOption > li").click(function () {
                var inputs = $(this).closest("div.input-group"),
                    inputId = $(inputs).find("input").attr("data-id"),
                    input = $(element).find("#" + inputId), aClass;
                $("#buttonOption > li").each(function () {
                    var className = $(this).find("a").attr("data-class");
                    if ($(input).hasClass(className)) {
                        $(input).removeClass(className)
                    }
                });
                aClass = $(this).find("a").attr("data-class");
                $(input).addClass(aClass);
            })
        };

        function btnKeyUp() {
            $('#__buttons input[type="text"]', properties).unbind("keyup");
            $('#__buttons input[type="text"]', properties).keyup(function () {
                var dataId = $(this).attr("data-id");
                $("#" + dataId).text(this.value)
            })
        };

        function selectChange() {
            $("input[name='seloption']", properties).change(function () {
                var seloption = $("input[name='seloption']", properties)
                    , index = seloption.index(seloption.filter(":checked"));
                $("button", element).each(function (i, v) {
                    if (index == i) {
                        $("#butpropId", properties).val($(v).attr("id"));
                        $("#butpropStyle > option").each(function () {
                            var cls = $(this).val();
                            if ($(v).hasClass(cls)) {
                                $(this).prop("selected", true);
                            }
                        })
                    }

                })
            })
        };

        function btnPropIdKeyUp() {
            $("#butpropId", properties).unbind("keyup");
            $("#butpropId", properties).keyup(function () {
                var id = $(this).val()
                    , seloption = $("input[name='seloption']", properties)
                    , index = seloption.index(seloption.filter(":checked"));
                $("button", element).each(function (i, v) {
                    if (index == i) {
                        $(v).attr("id", id)
                    }
                })
            })
        };

        function btnPropNameKeyUp() {
            $("#butpropName", properties).unbind("keyup");
            $("#butpropName", properties).keyup(function () {
                var name = $(this).val()
                    , seloption = $("input[name='seloption']", properties)
                    , index = seloption.index(seloption.filter(":checked"));
                $("button", element).each(function (i, v) {
                    if (index == i) {
                        $(v).attr("name", name)
                    }
                })
            })
        };

        function btnPropStyleChange() {
            $("#butpropStyle").change(function () {
                var style = $(this).val()
                    , seloption = $("input[name='seloption']", properties)
                    , index = seloption.index(seloption.filter(":checked"));
                $("button", element).each(function (i, v) {
                    if (index == i) {
                        $(v).removeClass("btn-default").removeClass("btn-primary").removeClass("btn-success").removeClass("btn-info").removeClass("btn-warning").removeClass("btn-danger").removeClass("btn-link").addClass(style);
                    }
                })
            })
        };

        function btnPropSizeChange() {
            $("#butpropSize").change(function () {
                var size = $(this).val();
                $("button", element).each(function (i, v) {
                    $(v).removeClass("btn-default").removeClass("btn-lg").removeClass("btn-sm").removeClass("btn-xs").addClass(size)
                })
            })
        };

        buttonComponent.click = function (ele, propertyEle) {
            element = ele;
            var buttons = $(propertyEle).find("#__buttons"), val, id, timeOut;
            buttons.children().remove();

            $(element).find("button").each(function () {
                val = "";
                id = gvDesigner.newName("__o");

                var dataId = $(this).attr("id"),
                    text = $(this).text().trim(),
                    ele = $('<li><div class="input-group"><span class="input-group-addon">&nbsp;<span class="icon-reorder">&nbsp;<\/span>&nbsp;<input id="_' + id + '" data-id="' + dataId + '" type="radio" name="seloption"' + val + '>&nbsp;<\/span><input data-id="' + dataId + '" value="' + text + '" class="form-control" type="text"><span class="input-group-addon">&nbsp;<span class="icon-add">&nbsp;&nbsp;&nbsp;&nbsp;<\/span>&nbsp;<span class="icon-delete">&nbsp;&nbsp;&nbsp;&nbsp;<\/span>&nbsp;<\/span><\/div><\/li>');
                $("#fieldproperties #__buttons").append($(ele));
            });
            initBtnOpt();
            btnKeyUp();
            selectChange();
            btnPropIdKeyUp();
            btnPropNameKeyUp();
            btnPropStyleChange();
            $("input[name='seloption']:first", properties).attr("checked", true).change();
            btnPropSizeChange();
            var btnFirstChild = $("button:first-child", element);
            $("#butpropSize> option").each(function () {
                var sizeClass = $(this).val();
                if ($(btnFirstChild).hasClass(sizeClass)) {
                    $(this).prop("selected", true);
                }
            });


            var isInline = $(element).hasClass("group-inline");
            var isGroup = $(element).hasClass("btn-group");
            var isRight = $(element).hasClass("pull-right");
            $("#__butgrpinline").prop("checked", isInline);
            $("#__butgrpinline").change(function () {
                if ($(this).is(":checked")) {
                    $(element).addClass("group-inline")
                } else {
                    $(element).removeClass("group-inline")
                }
            });
            $("#__rightalign").prop("checked", isRight);
            $("#__rightalign").change(function () {
                if ($(this).is(":checked")) {
                    $(element).addClass("pull-right")
                } else {
                    $(element).removeClass("pull-right")
                }
            });
            $("#__butgrpit").prop("checked", isGroup);
            $("#__butgrpit").change(function () {
                if ($(this).is(":checked")) {
                    $(element).addClass("btn-group")
                } else {
                    $(element).removeClass("btn-group")
                }
            });


            $("span.icon-add", buttons).click(function () {
                addBtn(this);
            });
            $("span.icon-delete", buttons).click(function () {
                deleteBtn(this);
            });
            function addBtn(e) {
                var item = $(e).closest("li"),
                    index = item.index(),
                    btnParent = $("button", element).parent(),
                    dataId = gvDesigner.newName("button"),
                    addBtnTemplate = $('<li><div class="input-group"><span class="input-group-addon">&nbsp;<span class="icon-reorder">&nbsp;<\/span>&nbsp;<input id="_' + id + '" type="radio" name="seloption"' + val + '>&nbsp;<\/span><input data-id="' + dataId + '" value="Button" class="form-control" type="text"><span class="input-group-addon">&nbsp;<span class="icon-add">&nbsp;&nbsp;&nbsp;&nbsp;<\/span>&nbsp;<span class="icon-delete">&nbsp;&nbsp;&nbsp;&nbsp;<\/span>&nbsp;<\/span><\/div><\/li>'),
                    template, btn;
                addBtnTemplate.insertAfter(item);
                template = $("#ButtonTemplate").clone();
                $(template).attr("id", dataId);
                btn = $(btnParent).children().eq(index);
                $(template).insertAfter(btn);
                template.before(" ").after(" ");
                $(template).ready(function () {
                    btnKeyUp();
                    selectChange();
                    $("span.icon-add", addBtnTemplate).click(function () {
                        addBtn(this)
                    });
                    $("span.icon-delete", addBtnTemplate).click(function () {
                        deleteBtn(this)
                    })
                })
            };
            function deleteBtn(e) {
                var item = $(e).closest("li"), dataId;
                if (item.siblings().length > 0) {
                    dataId = $(e).parent().siblings("input").attr("data-id");
                    $("#" + dataId, element).remove();
                    $(item).remove();
                }
            };
            $("#__buttons").sortable({
                axis: "y",
                placeholder: "ui-state-highlight",
                cursor: "move",
                delay: 200,
                helper: "ui-state-highlight",
                start: function (n, t) {
                    isNewWidget = false;
                    t.placeholder.height(t.item.height())
                },
                stop: function (n, t) {
                    var context = $(t.item).context
                        , dataId = $('input[type="text"]', context).attr("data-id")
                        , canvas = $("#design-canvas")
                        , parent = $("#" + dataId, canvas).parent();
                    $("#__buttons li").each(function () {
                        dataId = $('input[type="text"]', this).attr("data-id");
                        $(canvas).find("#" + dataId).appendTo(parent);
                        $(canvas).find("#" + dataId).before(" ").after(" ");
                    })
                }
            });
            timeOut = function () {
                var n = 0;
                return function (t, i) {
                    clearTimeout(n);
                    n = setTimeout(t, i)
                }
            }()
        };


        buttonComponent.add = function (ele) {
            element = ele;
            var template = $("#ButtonTemplate").clone(), id = gvDesigner.newName("button");
            $(ele).find(".controls").remove();
            $(ele).find("div.error").remove();
            $(ele).find("label:first").remove();
            $(template).attr("id", id).appendTo(ele);
            return template;
        };
        buttonComponent.delete = function () {
            alert("delete")
        };
        buttonComponent.placeHolder = function () {
            return $('<div class="form-group"><button type="button" class="btn btn-default">Button<\/button><div>')
        };
        return buttonComponent;
    }())
});
$(function () {
    var element;
    gvDesigner.register("checkbox", function () {
        function update() {
            var checkbox = $('input[type="checkbox"]', element).first(),
                min = $("#checkboxmin").data("kendoNumericTextBox"),
                max = $("#checkboxmax").data("kendoNumericTextBox"),
                minValue = min.value(),
                maxValue = max.value();
            checkbox.removeAttr("data-parsley-mincheck");
            checkbox.removeAttr("data-parsley-maxcheck");
            if (minValue > 0) {
                checkbox.attr("data-parsley-mincheck", minValue);
            }
            if (maxValue > 0) {
                checkbox.attr("data-parsley-maxcheck", maxValue);
            }
        }

        var checkboxComponent = {};
        $("#checkboxmin, #checkboxmax").kendoNumericTextBox({
            change: update,
            spin: update,
            min: 0
        });
        checkboxComponent.click = function (ele, propertyEle) {
            function initMaxLength() {
                var length = $('input[type="checkbox"]', element).length
                    , min = $("#checkboxmin").data("kendoNumericTextBox"),
                    max = $("#checkboxmax").data("kendoNumericTextBox");
                min.max(length);
                max.max(length);
            }

            function checkboxChange(ele) {
                var index = $(ele).parent().parent().index()
                    , isChecked = $(ele).is(":checked");
                $(".controls", element).children().each(function () {
                    if ($(this).index() == index) {
                        $('input[type="checkbox"]', this).attr("checked", isChecked);
                    }
                })
            }

            function addCheckbox(ele) {
                var parent = $(ele).parent().parent().parent(), dataId = gvDesigner.newName("__r"),
                    checkboxId = gvDesigner.newName("checkbox"),
                    template = $('<li id="' + dataId + '"><div class="input-group"><span class="input-group-addon">&nbsp;<span class="icon-reorder">&nbsp;<\/span>&nbsp;<input id="_' + dataId + '" name="' + id + '" type="checkbox">&nbsp;<\/span><input class="form-control prop-option"  name="rdchecklabel" type="text" /><input class="form-control prop-option"  name="rdcheckname" type="text" /><input class="form-control prop-option"  name="rdcheckid" type="text" /><span class="input-group-addon">&nbsp;<span class="icon-add">&nbsp;&nbsp;&nbsp;&nbsp;<\/span>&nbsp;<span class="icon-delete">&nbsp;&nbsp;&nbsp;&nbsp;<\/span>&nbsp;<\/span><\/div><\/li>'),
                    index;
                template.insertAfter($(parent));
                $("input[name='rdcheckname']", template).fadeOut(0);
                $("input[name='rdcheckid']", template).fadeOut(0);
                index = parent.index();
                $(".controls", element).children().each(function () {
                    if ($(this).index() == index) {
                        var name = $('input[type="checkbox"]', this).attr("name");
                        var tem = $('<label class="checkbox"><input id="' + checkboxId + '" name="' + name + '" type="checkbox" ><\/label>');
                        $(tem).insertAfter($(this))
                    }
                });
                $('input[name="rdchecklabel"]', template).keyup(function () {
                    checkLabelKeyup(this)
                });
                $('input[name="rdcheckname"]', template).keyup(function () {
                    checkNameKeyup(this)
                });
                $('input[name="rdcheckid"]', template).keyup(function () {
                    checkIdKeyup(this)
                });
                $('input[type="checkbox"]', template).change(function () {
                    checkboxChange(this)
                });
                $("span.icon-add", template).click(function () {
                    addCheckbox(this)
                });
                $("span.icon-delete", template).click(function () {
                    deleteCheckbox(this)
                });
                initMaxLength()
            }

            function deleteCheckbox(ele) {
                var parent = $(ele).parent().parent().parent()
                    , index = parent.index();
                if (parent.siblings().length > 0) {
                    $(parent).remove();
                }
                $(".controls", element).children().each(function () {
                    if ($(this).index() == index) {
                        $(this).remove();
                        index = -99;
                    }
                });
                gvDesigner.spread();
                initMaxLength()
            }

            function checkLabelKeyup(ele) {
                var index = $(ele).parent().parent().index()
                    , value = $(ele).val();
                timeOut(function () {
                    $(".controls", element).children().each(function () {
                        if ($(this).index() == index) {
                            var checkbox = $('input[type="checkbox"]', this);
                            $(this).text(value);
                            $(checkbox).attr("value", value);
                            $(this).prepend(checkbox)
                        }
                    })
                }, 300)
            }

            function checkNameKeyup(ele) {
                var index = $(ele).parent().parent().index()
                    , value = $(ele).val();
                timeOut(function () {
                    $(".controls", element).children().each(function () {
                        if ($(this).index() == index) {
                            var checkbox = $('input[type="checkbox"]', this);
                            $(checkbox).attr("value", value)
                        }
                    })
                }, 300)
            }

            function checkIdKeyup(ele) {
                var index = $(ele).parent().parent().index()
                    , value = $(ele).val();
                timeOut(function () {
                    $(".controls", element).children().each(function () {
                        if ($(this).index() == index) {
                            var checkbox = $('input[type="checkbox"]', this);
                            $(checkbox).attr("id", value);
                            $(checkbox).closest("label").attr("for", value)
                        }
                    })
                }, 300)
            }

            element = ele;
            var checkboxs = $("#__checkboxOptions", propertyEle), id, rdcitems;
            checkboxs.children().remove();

            $(".controls", element).children().each(function () {
                var checkbox = $('input[type="checkbox"]', this), tempalte, checkboxId;
                if (checkbox.length > 0) {
                    id = gvDesigner.newName("__c");
                    checkboxId = $('input[type="checkbox"]', this).attr("id");
                    tempalte = $('<li id="' + id + '"><div class="input-group"><span class="input-group-addon">&nbsp;<span class="icon-reorder">&nbsp;<\/span>&nbsp;<input id="_' + id + '" name="' + checkboxId + '" type="checkbox">&nbsp;<\/span><input class="form-control prop-option" name="rdchecklabel" value="' + $(this).text() + '" type="text" /><input class="form-control prop-option" name="rdcheckname" value="' + $(checkbox).attr("value") + '" type="text" /><input class="form-control prop-option" name="rdcheckid" value="' + $(checkbox).attr("id") + '" type="text" /><span class="input-group-addon">&nbsp;<span class="icon-add">&nbsp;&nbsp;&nbsp;&nbsp;<\/span>&nbsp;<span class="icon-delete">&nbsp;&nbsp;&nbsp;&nbsp;<\/span>&nbsp;<\/span><\/div><\/li>');
                    $(checkbox).is(":checked") && $('input[type="checkbox"]', tempalte).attr("checked", true);
                    $(checkboxs).append(tempalte);
                }
            });
            rdcitems = $("#rdcitems");
            $("input[name='rdcheckname']", rdcitems).fadeOut(0);
            $("input[name='rdcheckid']", rdcitems).fadeOut(0);
            $("#rdcheckLabel").click(function () {
                var items = $("#rdcitems");
                $("input[name='rdcheckid']", items).fadeOut(0);
                $("input[name='rdcheckname']", items).fadeOut(0);
                $("input[name='rdchecklabel']", items).fadeIn(0)
            });
            $("#rdcheckValue").click(function () {
                var items = $("#rdcitems");
                $("input[name='rdcheckid']", items).fadeOut(0);
                $("input[name='rdchecklabel']", items).fadeOut(0);
                $("input[name='rdcheckname']", items).fadeIn(0)
            });
            $("#rdcheckId").click(function () {
                var items = $("#rdcitems");
                $("input[name='rdchecklabel']", items).fadeOut(0);
                $("input[name='rdcheckname']", items).fadeOut(0);
                $("input[name='rdcheckid']", items).fadeIn(0)
            });

            initMaxLength();

            var checkboxFirst = $('input[type="checkbox"]', element).first();
            var min = $("#checkboxmin").data("kendoNumericTextBox"),
                max = $("#checkboxmax").data("kendoNumericTextBox");
            min.value(null);
            min.value(checkboxFirst.attr("data-parsley-mincheck"));
            max.value(null);
            max.value(checkboxFirst.attr("data-parsley-maxcheck"));

            $('input[type="checkbox"]', checkboxs).change(function () {
                checkboxChange(this)
            });
            $('input[name="rdchecklabel"]', checkboxs).keyup(function () {
                checkLabelKeyup(this)
            });
            $('input[name="rdcheckname"]', checkboxs).keyup(function () {
                checkNameKeyup(this)
            });
            $('input[name="rdcheckid"]', checkboxs).keyup(function () {
                checkIdKeyup(this)
            });
            $("span.icon-add", checkboxs).click(function () {
                addCheckbox(this)
            });
            $("span.icon-delete", checkboxs).click(function () {
                deleteCheckbox(this)
            });

            var timeOut = function () {
                var n = 0;
                return function (t, i) {
                    clearTimeout(n);
                    n = setTimeout(t, i)
                }
            }()
        };
        checkboxComponent.add = function (ele, id) {
            element = ele;
            var template = $("#CheckBoxTemplate").clone(), fieldName = gvDesigner.newName("field"), checkboxName,
                eleClone, checkbox;

            $(template).attr("id", id);
            $(ele).find("label:first").attr("for", id);
            $(ele).find("label:first").text("Option");
            $(ele).find("label:first").removeAttr("for");
            $(ele).find("label:first").text("Select all that Apply");

            $(template).children().each(function () {
                checkboxName = gvDesigner.newName("checkbox");
                eleClone = $(this).clone();
                checkbox = $('input[type="checkbox"]', eleClone);
                $(checkbox).attr("id", checkboxName);
                $(checkbox).attr("name", fieldName);
                checkbox.parent().attr("for", checkboxName);
                $(".controls", ele).append(eleClone)
            });
            return template;
        };
        checkboxComponent.delete = function () {
            alert("delete")
        };
        checkboxComponent.placeHolder = function (ele) {
            var template = $("#CheckBoxTemplate").clone();
            $(".controls", ele).append(template);
            $(ele).find("label:first").text("Check all that apply");
            return ele;
        };
        return checkboxComponent;
    }())
});
$(function () {
    var element;
    gvDesigner.register("datatable", function () {
        var datatableComponent = {};
        datatableComponent.click = function (ele, propertyEle) {
            var getDatatable, inputKeyup, heightKeyup, dataurlKeyup, optionChange, propIdKeyup, propNameKeyup, setProps,
                initBootstrapTable, timeOut, name;
            element = ele;
            var datatables = $('[data-role="datatable"]', element)
                , columns = $("#__columns", propertyEle)
                , properties = $("#fieldproperties");
            columns.children().remove();

            getDatatable = function () {
                var datatableId = $('[data-role="datatable"]', element).attr("id");
                return $("#" + datatableId);
            };

            $("th", datatables).each(function () {
                val = "";
                name = gvDesigner.newName("__o");
                var id = $(this).attr("id"),
                    template = $('<li><div class="input-group"><span class="input-group-addon">&nbsp;<span class="icon-reorder">&nbsp;<\/span>&nbsp;<input id="_' + name + '" data-id="' + id + '" type="radio" hidden name="seloption"' + val + '>&nbsp;<\/span><input data-id="' + id + '" value="' + $(this).text() + '" class="form-control" type="text"><span class="input-group-addon">&nbsp;<span class="icon-add">&nbsp;&nbsp;&nbsp;&nbsp;<\/span>&nbsp;<span class="icon-delete">&nbsp;&nbsp;&nbsp;&nbsp;<\/span>&nbsp;<\/span><\/div><\/li>');
                $("#fieldproperties #__columns").append($(template))
            });

            inputKeyup = function () {
                $('#__columns input[type="text"]', properties).unbind("keyup");
                $('#__columns input[type="text"]', properties).keyup(function () {
                    var item = $(this).closest("li"),
                        index = item.index(),
                        id = $('[data-role="datatable"]', element).attr("id"),
                        datatable = $("#" + id), child;
                    datatable.bootstrapTable("destroy");
                    child = $("th:nth-child(" + (index + 1) + ")", datatable);
                    $(child).text(this.value);
                    initBootstrapTable()
                })
            };
            inputKeyup();

            heightKeyup = function () {
                $("#__height", properties).unbind("keyup");
                $("#__height", properties).keyup(function () {
                    var height, id, datatable;
                    if ($.isNumeric(this.value)) {
                        height = parseInt(this.value);
                        if (height > 100) {
                            id = $('[data-role="datatable"]', element).attr("id");
                            datatable = $("#" + id);
                            $(datatable).attr("data-height", this.value);
                            initBootstrapTable();
                        }
                    }
                })
            };
            heightKeyup();

            dataurlKeyup = function () {
                $("#__dataurl", properties).unbind("keyup");
                $("#__dataurl", properties).keyup(function () {
                    var id = $('[data-role="datatable"]', element).attr("id")
                        , datatable = $("#" + id);
                    $(datatable).attr("data-url", this.value);
                    initBootstrapTable()
                })
            };
            dataurlKeyup();

            optionChange = function () {
                $("input[name='seloption']", properties).change(function () {
                    var option = $("input[name='seloption']", properties),
                        index = option.index(option.filter(":checked")),
                        datatable = $('[data-role="datatable"]', element), obj = $.extend({}, datatable), child;
                    obj.bootstrapTable("destroy");
                    child = $("th:nth-child(" + (index + 1) + ")", obj);
                    $("#dtdatafield").val($(child).attr("data-field"));
                    initBootstrapTable()
                })
            };
            optionChange();

            propIdKeyup = function () {
                $("#butpropId", properties).unbind("keyup");
                $("#butpropId", properties).keyup(function () {
                    var value = $(this).val()
                        , option = $("input[name='seloption']", properties)
                        , index = option.index(option.filter(":checked"));
                    $("button", element).each(function (i, v) {
                        if (i == index) {
                            $(v).attr("id", value);
                        }
                    })
                })
            };
            propIdKeyup();

            propNameKeyup = function () {
                $("#butpropName", properties).unbind("keyup");
                $("#butpropName", properties).keyup(function () {
                    var value = $(this).val()
                        , option = $("input[name='seloption']", properties)
                        , index = option.index(option.filter(":checked"));
                    $("button", element).each(function (i, v) {
                        if (i == index) {
                            $(v).attr("name", value);
                        }
                    })
                })
            };
            propNameKeyup();

            $("#__hover, #__striped, #__condensed, #__showrefresh, #__showcolumns, #__pagination, #__search", properties).click(function () {
                initBootstrapTable()
            });

            setProps = function () {
                var id = $('[data-role="datatable"]', element).attr("id")
                    , datatable = $("#" + id);
                $("#__striped").prop("checked", $(datatable).attr("data-striped") == "true");
                $("#__hover").prop("checked", $(datatable).attr("data-hover") == "true");
                $("#__pagination").prop("checked", $(datatable).attr("data-pagination") == "true");
                $("#__showrefresh").prop("checked", $(datatable).attr("data-show-refresh") == "true");
                $("#__search").prop("checked", $(datatable).attr("data-search") == "true");
                $("#__showcolumns").prop("checked", $(datatable).attr("data-show-columns") == "true");
                $("#__height").val($(datatable).attr("data-height"));
                $("#__dataurl").val($(datatable).attr("data-url"))
            };
            setProps();

            initBootstrapTable = function () {
                var id = $('[data-role="datatable"]', element).attr("id")
                    , datatable = $("#" + id);
                datatable.bootstrapTable("destroy");
                var striped = $("#__striped").prop("checked")
                    , hover = $("#__hover").prop("checked")
                    , pagination = $("#__pagination").prop("checked")
                    , showrefresh = $("#__showrefresh").prop("checked")
                    , search = $("#__search").prop("checked")
                    , showcolumn = $("#__showcolumns").prop("checked")
                    , height = $(datatable).attr("data-height")
                    , url = $(datatable).attr("data-url");
                $(datatable).attr("data-striped", striped).attr("data-hover", hover).attr("data-pagination", pagination).attr("data-search", search).attr("data-show-columns", showcolumn).attr("data-show-refresh", showrefresh).attr("data-height", height).attr("data-url", url);
                datatable.bootstrapTable({
                    method: "get",
                    url: url,
                    pagination: pagination,
                    striped: striped,
                    search: search,
                    showRefresh: showrefresh,
                    showColumns: showcolumn,
                    height: height
                })
            };

            $("span.icon-add", columns).click(function () {
                addColumn(this)
            });
            $("span.icon-delete", columns).click(function () {
                deleteColumn(this)
            });

            var addColumn = function (e) {
                var id = $('[data-role="datatable"]', element).attr("id"), datatable = $("#" + id), column, child;
                datatable.bootstrapTable("destroy");
                var item = $(e).closest("li")
                    , index = item.index()
                    , th = $("thead > tr", datatable)
                    , btnId = gvDesigner.newName("button")
                    ,
                    template = $('<li><div class="input-group"><span class="input-group-addon">&nbsp;<span class="icon-reorder">&nbsp;<\/span>&nbsp;<input id="_' + name + '" type="radio" name="seloption"' + val + '>&nbsp;<\/span><input data-id="' + btnId + '" value="Column" class="form-control" type="text"><span class="input-group-addon">&nbsp;<span class="icon-add">&nbsp;&nbsp;&nbsp;&nbsp;<\/span>&nbsp;<span class="icon-delete">&nbsp;&nbsp;&nbsp;&nbsp;<\/span>&nbsp;<\/span><\/div><\/li>');
                template.insertAfter(item);
                column = $('<th data-field="name">Column<\/th>');
                child = $("th:nth-child(" + (index + 1) + ")", datatable);
                $(column).insertAfter(child);
                datatable.bootstrapTable();
                $(column).ready(function () {
                    inputKeyup();
                    optionChange();
                    $("span.icon-add", template).click(function () {
                        addColumn(this)
                    });
                    $("span.icon-delete", template).click(function () {
                        deleteColumn(this)
                    })
                })
            };

            deleteColumn = function (e) {
                var item = $(e).closest("li"), index, id, datatable, child;
                if (item.siblings().length > 0) {
                    index = item.index();
                    $(item).remove();
                    id = $('[data-role="datatable"]', element).attr("id");
                    datatable = $("#" + id);
                    datatable.bootstrapTable("destroy");
                    child = $("th:nth-child(" + (index + 1) + ")", datatable);
                    $(child).remove();
                    datatable.bootstrapTable();
                }
            };
            var  h = -1,  index = -1;

            $("#__columns").sortable({
                axis: "propName",
                placeholder: "ui-state-highlight",
                cursor: "move",
                delay: 200,
                helper: "ui-state-highlight",
                start: function (n, t) {
                    isNewWidget = false;
                    t.placeholder.height(t.item.height());
                    var context = $(t.item).context;
                    index = $(context).index()
                },
                stop: function (n, i) {
                    var context = $(i.item).context, datatableId, datatable, child, html;
                    index = $(context).index();
                    datatableId = $('[data-role="datatable"]', element).attr("id");
                    datatable = $("#" + datatableId);
                    datatable.bootstrapTable("destroy");

                    child = $("th:nth-child(" + (h + 1) + ")", datatable);
                    html = $("th:nth-child(" + (index + 1) + ")", datatable);
                    if (h > index) {
                        $(child).insertBefore(html);
                    } else {
                        $(child).insertAfter(html);
                    }
                    initBootstrapTable();
                }
            });
            timeOut = function () {
                var n = 0;
                return function (t, i) {
                    clearTimeout(n);
                    n = setTimeout(t, i)
                }
            }()
        };
        datatableComponent.save = function () {
            refreshDataTable()
        };
        datatableComponent.add = function (ele) {
            element = ele;
            var template = $("#DataTableTemplate").clone()
                , id = gvDesigner.newName("datatable");
            $(template).attr("id", id);
            $(".controls", ele).remove();
            $("div.error", ele).remove();
            $("label:first", ele).remove();
            $(template).appendTo(ele);
            $(template).bootstrapTable();
            return template;
        };
        datatableComponent.delete = function () {
            alert("delete")
        };
        datatableComponent.placeHolder = function () {
        };
        return datatableComponent;
    }())
});
$(function () {
    var element;
    gvDesigner.register("date", function () {
        var dateComponent = {};
        $("#datepickermin, #datepickermax, #datedefaultvalue").kendoDatePicker();

        $("#datepickermin, #datepickermax, #datedefaultvalue").change(function () {
            var value = $(this).attr("value")
                , property = $(this).attr("data-property")
                , dateInput = $('input[data-role="date"]', element);
            dateInput.removeAttr("data-" + property);
            if (value) {
                dateInput.attr("data-" + property, value);
            }
        });

        dateComponent.click = function (ele, propertyEle) {
            var dateInput, dateInputId, canvas, beforeDate, afterDate;
            element = ele;
            dateInput = $('input[data-role="date"]', ele);
            dateInputId = dateInput.attr("id");
            $("#datepickermin", propertyEle).val(dateInput.attr("data-min"));
            $("#datepickermax", propertyEle).val(dateInput.attr("data-max"));
            $("#datedefaultvalue", propertyEle).val(dateInput.attr("data-default"));
            canvas = $("#design-canvas");

            $("#dateLessThan").find("option").remove().end().append('<option value=""><\/option>');
            $("#dateGreaterThan").find("option").remove().end().append('<option value=""><\/option>');
            $('[data-role="date"]', canvas).each(function () {
                var id = $(this).attr("id")
                    , value = $("label[for=" + $(this).attr("id") + "]", canvas).text();
                if (dateInputId != id) {
                    $("#dateLessThan").append($("<option><\/option>").attr("value", id).text(value));
                    $("#dateGreaterThan").append($("<option><\/option>").attr("value", id).text(value));
                }
            });
            beforeDate = dateInput.attr("data-beforedate");
            if (beforeDate) {
                $("#dateLessThan").val(beforeDate.replace("#", "")).attr("selected", true);
            }
            afterDate = dateInput.attr("data-afterdate");
            if (afterDate) {
                $("#dateGreaterThan").val(afterDate.replace("#", "")).attr("selected", true);
            }
        };

        dateComponent.add = function (ele, id) {
            var template, error;
            element = ele;
            template = $("#DateTemplate").clone();
            $(".controls", ele).append(template);
            $(template).attr("id", id);
            $(ele).find("label:first").attr("for", id);
            $(ele).find("label:first").text("Date");
            $(template).kendoDatePicker();
            error = $(".error", ele);
            error.attr("id", "err" + id);
            template.attr("data-error-container", "#err" + id);
            return template;
        };

        dateComponent.delete = function () {
            alert("delete");
        };

        dateComponent.placeHolder = function (ele) {
            var template = $("#DateTemplate").clone();
            $(".controls", ele).append(template);
            $(ele).find("label:first").text("Date");
            $(template).kendoDatePicker();
            return ele;
        };

        $("#dateLessThan, #dateGreaterThan").change(function () {
            var dateInput = $('input[data-role="date"]', element), property = $(this).attr("data-gv-property"), value;
            dateInput.removeAttr(property);
            dateInput.removeAttr("data-beforedate-message");
            dateInput.removeAttr("data-afterdate-message");
            value = $(this).attr("value");
            if (value) {
                dateInput.attr($(this).attr("data-gv-property"), "#" + value);
                if ($(this).attr("id") === "dateLessThan") {
                    dateInput.attr("data-beforedate-message", "This value should be less than '" + $(this).text() + "'");
                } else {
                    dateInput.attr("data-afterdate-message", "This value should be greater than '" + $(this).text() + "'");
                }
            }
        });
        return dateComponent;
    }())
});
$(function () {
    var element;
    gvDesigner.register("datetime", function () {
        var datetimeComponent = {};
        $("#datetimepickermin, #datetimepickermax, #datetimedefaultdatevalue").kendoDateTimePicker();

        datetimeComponent.click = function (ele, propertyEle) {
            var datatimeInput, datatimeInputId, canvas, beforeDate, afterDate;
            element = ele;
            datatimeInput = $('input[data-role="datetime"]', ele);
            datatimeInputId = datatimeInput.attr("id");
            $("#datetimepickermin", propertyEle).val(datatimeInput.attr("data-min"));
            $("#datetimepickermax", propertyEle).val(datatimeInput.attr("data-max"));
            $("#datetimedefaultdatevalue", propertyEle).val(datatimeInput.attr("data-default"));
            canvas = $("#design-canvas");
            $("#datetimeLessThan").find("option").remove().end().append('<option value=""><\/option>');
            $("#datetimeGreaterThan").find("option").remove().end().append('<option value=""><\/option>');
            $('[data-role="datetime"]', canvas).each(function () {
                var id = $(this).attr("id")
                    , value = $("label[for=" + $(this).attr("id") + "]", canvas).text();
                if (datatimeInputId != id) {
                    $("#datetimeLessThan").append($("<option><\/option>").attr("value", id).text(value));
                    $("#datetimeGreaterThan").append($("<option><\/option>").attr("value", id).text(value));
                }
            });
            beforeDate = datatimeInput.attr("data-beforedate");
            if (beforeDate) {
                $("#datetimeLessThan").val(beforeDate.replace("#", "")).attr("selected", true);
            }
            afterDate = datatimeInput.attr("data-afterdate");
            if (afterDate) {
                $("#datetimeGreaterThan").val(afterDate.replace("#", "")).attr("selected", true);
            }
        };

        datetimeComponent.add = function (ele, id) {
            var template, error;
            element = ele;
            template = $("#DateTimeTemplate").clone();
            $(".controls", ele).append(template);
            $(template).attr("id", id);
            $(ele).find("label:first").attr("for", id);
            $(ele).find("label:first").text("DateTime");
            $(template).kendoDateTimePicker();
            error = $(".error", ele);
            error.attr("id", "err" + id);
            template.attr("data-error-container", "#err" + id);
            return template;
        };

        datetimeComponent.delete = function () {
            alert("delete");
        };

        datetimeComponent.placeHolder = function (ele) {
            var template = $("#DateTimeTemplate").clone();
            $(".controls", ele).append(template);
            $(ele).find("label:first").text("Date Time");
            $(template).kendoDateTimePicker();
            return ele;
        };

        $("#datetimepickermin, #datetimepickermax, #datetimedefaultdatevalue").change(function () {
            var value = $(this).val()
                , property = $(this).attr("data-property")
                , datetimeInput = $('input[data-role="datetime"]', element);
            datetimeInput.removeAttr("data-" + property);
            if (value) {
                datetimeInput.attr("data-" + property, value);
            }
        });

        $("#datetimeLessThan, #datetimeGreaterThan").change(function () {
            var datetimeInput = $('input[data-role="datetime"]', element),
                property = $(this).attr("data-gv-property"), value;
            datetimeInput.removeAttr(property);
            datetimeInput.removeAttr("data-beforedate-message");
            datetimeInput.removeAttr("data-afterdate-message");
            value = $(this).val();
            if (value) {
                datetimeInput.attr($(this).attr("data-gv-property"), "#" + value);
                if ($(this).attr("id") === "datetimeLessThan") {
                    datetimeInput.attr("data-beforedate-message", "This value should be less than '" + $(this).text() + "'");
                } else {
                    datetimeInput.attr("data-afterdate-message", "This value should be greater than '" + $(this).text() + "'");
                }
            }
        });
        return datetimeComponent;
    }())
});
$(function () {
    var element;
    gvDesigner.register("time", function () {
        var timeComponent = {};
        $("#timepickermin, #timepickermax, #timedefaultvalue").kendoTimePicker();

        $("#timepickermin, #timepickermax, #timedefaultvalue").change(function () {
            var value = $(this).attr("value")
                , property = $(this).attr("data-property")
                , input = $('input[data-role="time"]', element);
            input.removeAttr("data-" + property);
            if (value) {
                input.attr("data-" + property, value);
            }
        });

        timeComponent.click = function (ele, propertyEle) {
            var timeInput, timeInputId, canvas, beforeDate, afterDate;
            element = ele;
            timeInput = $('input[data-role="time"]', ele);
            timeInputId = timeInput.attr("id");
            $("#timepickermin", propertyEle).val(null).val(timeInput.attr("data-min"));
            $("#timepickermax", propertyEle).val(null).val(timeInput.attr("data-max"));
            $("#timedefaultvalue", propertyEle).val(null).val(timeInput.attr("data-default"));

            canvas = $("#design-canvas");
            $("#timeLessThan").find("option").remove().end().append('<option value=""><\/option>');
            $("#timeGreaterThan").find("option").remove().end().append('<option value=""><\/option>');
            $('[data-role="time"]', canvas).each(function () {
                var id = $(this).attr("id")
                    , value = $("label[for=" + $(this).attr("id") + "]", canvas).text();
                if (timeInputId != id) {
                    $("#timeLessThan").append($("<option><\/option>").attr("value", id).text(value));
                    $("#timeGreaterThan").append($("<option><\/option>").attr("value", id).text(value));
                }
            });

            beforeDate = timeInput.attr("data-beforedate");
            if (beforeDate) {
                $("#timeLessThan").val(beforeDate.replace("#", "")).attr("selected", true);
            }
            afterDate = timeInput.attr("data-afterdate");
            if (afterDate) {
                $("#timeGreaterThan").val(afterDate.replace("#", "")).attr("selected", true);
            }

        };

        timeComponent.add = function (ele, id) {
            var template, error;
            element = ele;
            template = $("#TimeTemplate").clone();
            $(".controls", ele).append(template);
            $(template).attr("id", id);
            $(ele).find("label:first").attr("for", id);
            $(ele).find("label:first").text("Time");
            $(template).kendoTimePicker();
            error = $(".error", ele);
            error.attr("id", "err" + id);
            template.attr("data-error-container", "#err" + id);
            return template;
        };

        timeComponent.delete = function () {
            alert("delete");
        };

        timeComponent.placeHolder = function (ele) {
            var template = $("#TimeTemplate").clone();
            $(".controls", ele).append(template);
            $(ele).find("label:first").text("Time");
            $(template).kendoTimePicker();
            return ele;
        };

        $("#timeLessThan, #timeGreaterThan").change(function () {
            var time = $('input[data-role="time"]', element), property = $(this).attr("data-gv-property"), value;
            time.removeAttr(property);
            time.removeAttr("data-beforedate-message");
            time.removeAttr("data-afterdate-message");
            value = $(this).attr("value");
            if (value) {
                time.attr($(this).attr("data-gv-property"), "#" + value);
                if ($(this).attr("id") === "timeLessThan") {
                    time.attr("data-beforedate-message", "This value should be less than '" + $(this).text() + "'");
                } else {
                    time.attr("data-afterdate-message", "This value should be greater than '" + $(this).text() + "'");
                }
            }
        });
        return timeComponent;
    }())
});
$(function () {
    var element;
    gvDesigner.register("header", function () {
        var headerComponent = {};
        headerComponent.click = function (ele) {
            element = ele;
            $("#headerdefautvalue").val($(":header", element).text());
            $("#subheaderdefautvalue").val($("div.controls > p", element).text());
            var header = $(":header", element);
            $("#headeroption option").removeAttr("selected");
            $('#headeroption option[value="' + header.get(0).tagName.toLowerCase() + '"]').attr("selected", "selected")
        };

        headerComponent.add = function (ele, id) {
            var template, label;
            element = ele;
            $(ele).addClass("brdbot");
            template = $("#HeaderTemplate").clone();
            $(".controls", ele).append(template);
            $("div.error", ele).replaceWith("");
            $(template).attr("id", id);
            label = $(ele).find("label:first");
            label.replaceWith("<h3>Header<\/h3>");
            return template;
        };

        headerComponent.delete = function () {
            alert("delete");
        };

        headerComponent.placeHolder = function () {
            return $("<h3>Header<\/h3>");
        };

        $("#headerdefautvalue").keyup(function () {
            var value = this.value;
            $(":header", element).text(value);
        });

        $("#subheaderdefautvalue").keyup(function () {
            var value = this.value;
            $("div.controls > p", element).closest("div").removeClass("control-inline");
            $("div.controls > p", element).text(value);
        });

        $("#headeroption").change(function () {
            var header = $(":header", element);
            header.replaceWith($("<" + this.value + ">" + header.text() + "<\/" + this.value + ">"));
        });

        return headerComponent;
    }())
});
$(function () {
    var element;
    gvDesigner.register("lookup", function () {
        var lookupComponent = {};
        lookupComponent.click = function (ele) {
            element = ele;
            var text = $(element).find('.controls > input[type="text"]:first');
        };

        lookupComponent.placeHolder = function (ele) {
            var template = $("#LookupTemplate").clone();
            $(".controls", ele).append(template);
            $(ele).find("label:first").text("Untitled");
            return ele;
        };

        lookupComponent.add = function (ele, id) {
            element = ele;
            var template = $("#LookupTemplate").clone();
            $(".controls", ele).append(template);
            $(template).attr("id", id);
            $(ele).find("label:first").attr("for", id);
            $(ele).find("label:first").text("Untitled");
            return template;
        };

        lookupComponent.delete = function () {
            alert("delete");
        };

        return lookupComponent;
    }())
});
$(function () {
    var element;
    gvDesigner.register("mask", function () {
        var maskComponent = {};
        maskComponent.click = function (ele, propertyEle) {
            element = ele;
            var input = $('input[data-role="mask"]', ele);
            $("#maskman", propertyEle).val(input.attr("data-mask"))
        };

        maskComponent.add = function (ele, id) {
            element = ele;
            var template = $("#MaskTemplate").clone();
            $(".controls", ele).append(template);
            $(template).attr("id", id);
            $(ele).find("label:first").attr("for", id);
            $(ele).find("label:first").text("Mask");
            return template;
        };

        maskComponent.delete = function () {
            alert("delete");
        };

        maskComponent.placeHolder = function (ele) {
            var template = $("#MaskTemplate").clone();
            $(".controls", ele).append(template);
            $(ele).find("label:first").text("Mask");
            return ele;
        };

        $("#maskman").keyup(function () {
            var value = $(this).val()
                , property = $(this).attr("data-property");
            $('input[data-role="mask"]', element).attr("data-" + property, value)
        });

        return maskComponent;
    }())
});
$(function () {
    var element;
    gvDesigner.register("numeric", function () {
        var numericComponent = {}, numericAddAttr, minvalue, maxValue, defaultValue;
        $("#numdefaultvalue, #numminvalue, #nummaxvalue").kendoNumericTextBox();
        numericComponent.click = function (ele, propertyEle) {
            var numericId, numminvalue, nummaxvalue, numdefaultvalue, isAllowNegative, canvas, lessthan, greaterthan;
            element = ele;
            var numeric = $('input[data-role="numeric"]', ele)
                , numericFormat = $("#numericFormat", propertyEle)
                , l = numeric.attr("data-format");
            $(numericFormat).val(l).attr("selected", true);
            numericId = numeric.attr("id");

            numminvalue = $("#numminvalue", propertyEle).data("kendoNumericTextBox");
            numminvalue.value(null);
            if (numeric.attr("data-parsley-min")) {
                numminvalue.value(numeric.attr("data-parsley-min"));
            }


            nummaxvalue = $("#nummaxvalue", propertyEle).data("kendoNumericTextBox");
            nummaxvalue.value(null);
            if (numeric.attr("data-parsley-max")) {
                nummaxvalue.value(numeric.attr("data-parsley-max"));
            }

            numdefaultvalue = $("#numdefaultvalue", propertyEle).data("kendoNumericTextBox");
            numdefaultvalue.value(null);
            if (numeric.attr("data-default")) {
                numdefaultvalue.value(numeric.attr("data-default"));
            }

            isAllowNegative = false;
            if (numeric.attr("data-negative") == "true") {
                isAllowNegative = true
            }
            $("#numericAllowNegative", propertyEle).attr("checked", isAllowNegative);
            canvas = $("#design-canvas");
            $("#numlessThan").find("option").remove().end().append('<option value=""><\/option>');
            $("#numGreaterThan").find("option").remove().end().append('<option value=""><\/option>');
            $('[data-role="numeric"]', canvas).each(function () {
                var id = $(this).attr("id")
                    , text = $("label[for=" + $(this).attr("id") + "]", canvas).text();
                if (numericId != id) {
                    $("#numlessThan").append($("<option><\/option>").attr("value", id).text(text));
                    $("#numGreaterThan").append($("<option><\/option>").attr("value", id).text(text));
                }
            });
            lessthan = numeric.attr("data-lessthan");
            if (lessthan) {
                $("#numlessThan").val(lessthan.replace("#", "")).attr("selected", true);
            }
            greaterthan = numeric.attr("data-greaterthan");
            if (greaterthan) {
                $("#numGreaterThan").val(greaterthan.replace("#", "")).attr("selected", true);
            }
        };

        numericComponent.add = function (ele, id) {
            var template, error;
            element = ele;
            template = $("#NumericTemplate").clone();
            $(".controls", ele).append(template);
            $(template).attr("id", id);
            $(ele).find("label:first").attr("for", id);
            $(ele).find("label:first").text("Numeric");
            $(template).kendoNumericTextBox();
            error = $(".error", ele);
            error.attr("id", "err" + id);
            template.attr("data-error-container", "#err" + id);
            return template;
        };

        numericComponent.delete = function () {
            alert("delete")
        };

        numericComponent.placeHolder = function (ele) {
            var template = $("#NumericTemplate").clone();
            $(".controls", ele).append(template);
            $(ele).find("label:first").text("Numeric");
            $(template).kendoNumericTextBox();
            return ele;
        };

        $("#numlessThan, #numGreaterThan").change(function () {
            var numeric = $('input[data-role="numeric"]', element), property = $(this).attr("data-gv-property"), value;
            numeric.removeAttr(property);
            numeric.removeAttr("data-lessthan-message");
            numeric.removeAttr("data-greaterthan-message");
            value = $(this).attr("value");
            if (value != "") {
                numeric.attr($(this).attr("data-gv-property"), "#" + value);
                if ($(this).attr("id") === "numlessThan") {
                    numeric.attr("data-lessthan-message", "This value should be less than '" + $(this).text() + "'");
                } else {
                    numeric.attr("data-greaterthan-message", "This value should be greater than '" + $(this).text() + "'")
                }
            }
        });

        numericAddAttr = function (ele, attr) {
            var value = ele.value()
                , numeric = $('input[data-role="numeric"]', element);
            numeric.attr(attr, value);
        };

        minvalue = $("#numminvalue").data("kendoNumericTextBox"),
            minvalue.bind("change", function () {
                numericAddAttr(this, "data-parsley-min")
            });
        minvalue.bind("spin", function () {
            numericAddAttr(this, "data-parsley-min")
        });

        maxValue = $("#nummaxvalue").data("kendoNumericTextBox");
        maxValue.bind("change", function () {
            numericAddAttr(this, "data-parsley-max")
        });
        maxValue.bind("spin", function () {
            numericAddAttr(this, "data-parsley-max")
        });

        defaultValue = $("#numdefaultvalue").data("kendoNumericTextBox");
        defaultValue.bind("change", function () {
            numericAddAttr(this, "data-default");
        });
        defaultValue.bind("spin", function () {
            numericAddAttr(this, "data-default");
        });

        $("#numericFormat").change(function () {
            var numeric = $('input[data-role="numeric"]', element);
            numeric.attr("data-format", this.value)
        });

        $("#numericAllowNegative").change(function () {
            var numeric = $('input[data-role="numeric"]', element);
            numeric.attr("data-negative", $(this).is(":checked"))
        });
        return numericComponent;
    }())
});
$(function () {
    var element;
    gvDesigner.register("panel", function () {
        var panelComponent = {};
        panelComponent.click = function (ele) {
            element = ele
        };
        panelComponent.add = function (n, ele) {
            var context = $(ele.item).context
                , template = $(document.getElementById("panel1Template")).clone();
            $(context).replaceWith(template);
        };

        panelComponent.delete = function () {
            alert("delete");
        };

        panelComponent.placeHolder = function () {
            return $("#panel1Template").clone();
        };
        return panelComponent;
    }())
});
$(function () {
    var element;
    gvDesigner.register("paragraph", function () {
        var paragraphComponent = {};
        paragraphComponent.click = function (ele) {
            element = ele;
            $("#paragraphdefautvalue").val($("p", element).text());
            $("#paragraphdefautvalue").keyup(function () {
                var value = this.value;
                $("p", element).text(value);
            });

            $("#paragraphoption").change(function () {
                var header = $(":header", element);
                header.replaceWith($("<" + this.value + ">" + header.text() + "<\/" + this.value + ">"))
            });

        };

        paragraphComponent.add = function (ele, id) {
            var template, label, html;
            element = ele;
            template = $("#ParagraphTemplate").clone();
            $(".controls", ele).replaceWith("");
            $("div.error", ele).replaceWith("");
            $(template).attr("id", id);
            label = $(ele).find("label:first");
            html = "<p>Nullam quis risus eget urna mollis ornare vel eu leo. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Nullam id dolor id nibh ultricies vehicula.<\/p>";
            label.replaceWith(html);
            return template;
        };

        paragraphComponent.delete = function () {
            alert("delete");
        };

        paragraphComponent.placeHolder = function () {
            return $("<p>Nullam quis risus eget urna mollis ornare vel eu leo. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Nullam id dolor id nibh ultricies vehicula.<\/p>");
        };

        return paragraphComponent;
    }())
});
$(function () {
    var element;
    gvDesigner.register("pills", function () {
        var pillsComponent = {};
        pillsComponent.click = function (ele, propertyEle) {
            var pills, properties, pillInputKeyup, pillOptionChange, pillPropIdKeyup, pillPropNameKeyup, addPill,
                deletePill, isStacked, timeOut, val, id;
            element = ele;
            pills = $("#__pills", propertyEle);
            properties = $("#fieldproperties");
            pills.children().remove();
            $("ul > li", element).each(function () {
                val = "";
                id = gvDesigner.newName("__o");
                var dataId = $(this).attr("id"),
                    template = $('<li><div class="input-group"><span class="input-group-addon">&nbsp;<span class="icon-reorder">&nbsp;<\/span>&nbsp;<input id="_' + id + '" data-id="' + dataId + '" type="radio" name="selpilloption"' + val + '>&nbsp;<\/span><input data-id="' + dataId + '" value="' + $(this).text() + '" class="form-control" type="text"><span class="input-group-addon">&nbsp;<span class="icon-add">&nbsp;&nbsp;&nbsp;&nbsp;<\/span>&nbsp;<span class="icon-delete">&nbsp;&nbsp;&nbsp;&nbsp;<\/span>&nbsp;<\/span><\/div><\/li>');
                $("#fieldproperties #__pills").append($(template))
            });

            pillInputKeyup = function () {
                $('#__pills input[type="text"]', properties).unbind("keyup");
                $('#__pills input[type="text"]', properties).keyup(function () {
                    var dataId = $(this).attr("data-id")
                        , t = $("#" + dataId + " > a");
                    $(t).text(this.value)
                })
            };
            pillInputKeyup();

            pillOptionChange = function () {
                $("input[name='selpilloption']", properties).change(function () {
                    var pillOption = $("input[name='selpilloption']", properties)
                        , index = pillOption.index(pillOption.filter(":checked"));
                    $("ul > li", element).each(function (i, v) {
                        if (i == index) {
                            $("#pillpropId", properties).val($(v).attr("id"));
                            $("#pillpropName", properties).val($(v).attr("name"));
                        }
                    })
                })
            };
            pillOptionChange();

            pillPropIdKeyup = function () {
                $("#pillpropId", properties).unbind("keyup");
                $("#pillpropId", properties).keyup(function () {
                    var i = $(this).val()
                        , n = $("input[name='selpilloption']", properties)
                        , r = n.index(n.filter(":checked"));
                    $("ul > li", element).each(function (n, t) {
                        n == r && $(t).attr("id", i)
                    })
                })
            };
            pillPropIdKeyup();

            pillPropNameKeyup = function () {
                $("#pillpropName", properties).unbind("keyup");
                $("#pillpropName", properties).keyup(function () {
                    var value = $(this).val()
                        , pillOption = $("input[name='selpilloption']", properties)
                        , index = pillOption.index(pillOption.filter(":checked"));
                    $("ul > li", element).each(function (i, v) {
                        if (i == index) {
                            $(v).attr("name", value);
                        }
                    })
                })
            };
            pillPropNameKeyup();

            $("span.icon-add", pills).click(function () {
                addPill(this)
            });
            $("span.icon-delete", pills).click(function () {
                deletePill(this)
            });

            addPill = function (e) {
                var item = $(e).closest("li"), index = item.index(), parent = $("li", element).parent(),
                    dataId = gvDesigner.newName("pill"),
                    template = $('<li><div class="input-group"><span class="input-group-addon">&nbsp;<span class="icon-reorder">&nbsp;<\/span>&nbsp;<input id="_' + id + '" type="radio" name="selpilloption"' + val + '>&nbsp;<\/span><input data-id="' + dataId + '" value="Navigation" class="form-control" type="text"><span class="input-group-addon">&nbsp;<span class="icon-add">&nbsp;&nbsp;&nbsp;&nbsp;<\/span>&nbsp;<span class="icon-delete">&nbsp;&nbsp;&nbsp;&nbsp;<\/span>&nbsp;<\/span><\/div><\/li>'),
                    navigation, children;
                template.insertAfter(item);
                navigation = $("<li><a>Navigation<\/a><\/li>").clone();
                $(navigation).attr("id", dataId);
                children = $(parent).children().eq(index);
                $(navigation).insertAfter(children);
                navigation.before(" ").after(" ");
                $(navigation).ready(function () {
                    pillInputKeyup();
                    $("span.icon-add", template).click(function () {
                        addPill(this)
                    });
                    $("span.icon-delete", template).click(function () {
                        deletePill(this)
                    })
                })
            };
            deletePill = function (e) {
                var item = $(e).closest("li"), dataId;
                if (item.siblings().length > 0) {
                    dataId = $(e).parent().siblings("input").attr("data-id");
                    $("#" + dataId, element).remove();
                    $(item).remove();
                }
            };

            $("#__pills").sortable({
                axis: "y",
                placeholder: "ui-state-highlight",
                cursor: "move",
                delay: 200,
                helper: "ui-state-highlight",
                start: function (n, t) {
                    t.placeholder.height(t.item.height())
                },
                stop: function (n, t) {
                    var context = $(t.item).context
                        , dataId = $('input[type="text"]', context).attr("data-id")
                        , canvas = $("#design-canvas")
                        , parent = $("#" + dataId, canvas).parent();
                    $("#__pills li").each(function () {
                        dataId = $('input[type="text"]', this).attr("data-id");
                        var item = $("#" + dataId, canvas);
                        $(item).appendTo(parent)
                    })
                }
            });
            isStacked = $("ul", element).hasClass("nav-stacked");
            $("#__pillStacked").prop("checked", isStacked);
            $("#__pillStacked").change(function () {
                if ($(this).is(":checked")) {
                    $("ul", element).addClass("nav-stacked");
                } else {
                    $("ul", element).removeClass("nav-stacked");
                }
            });
            timeOut = function () {
                var n = 0;
                return function (t, i) {
                    clearTimeout(n);
                    n = setTimeout(t, i)
                }
            }()
        };

        pillsComponent.add = function (ele) {
            var template, id, pillId;
            element = ele;
            template = $("#PillsTemplate").clone();
            id = gvDesigner.newName("pill");
            $(".controls", ele).remove();
            $("div.error", ele).remove();
            $("label:first", ele).remove();
            $(template).children().each(function () {
                pillId = gvDesigner.newName("pill");
                $(this).attr("id", pillId);
            });
            $(template).attr("id", id).appendTo(ele);
            return template;
        };

        pillsComponent.delete = function () {
            alert("delete");
        };

        pillsComponent.placeHolder = function () {
            return $("#PillsTemplate").clone();
        };

        return pillsComponent;
    }())
});
$(function () {
    var element;
    gvDesigner.register("radio", function () {
        var radioComponent = {};
        radioComponent.click = function (ele, propertyEle) {
            function radioChange(e) {
                var index = $(e).parent().parent().index()
                    , isChecked = $(e).is(":checked");
                $(".controls", element).children().each(function () {
                    $('input[type="radio"]', this).removeAttr("checked");
                    if ($(this).index() == index) {
                        $('input[type="radio"]', this).attr("checked", isChecked);
                    }
                })
            }

            function radioLabelKeyup(e) {
                var index = $(e).parent().parent().index()
                    , value = $(e).val();
                timeOut(function () {
                    $(".controls", element).children().each(function () {
                        if ($(this).index() == index) {
                            var radio = $('input[type="radio"]', this);
                            $(this).text(value);
                            $(this).prepend(radio);
                        }
                    })
                }, 300)
            }

            function radioValueKeyup(e) {
                var index = $(e).parent().parent().index()
                    , value = $(e).val();
                timeOut(function () {
                    $(".controls", element).children().each(function () {
                        if ($(this).index() == index) {
                            var radio = $('input[type="radio"]', this);
                            $(radio).attr("value", value);
                        }
                    })
                }, 300)
            }

            function radioIdKeyup(e) {
                var index = $(e).parent().parent().index()
                    , value = $(e).val();
                timeOut(function () {
                    $(".controls", element).children().each(function () {
                        if ($(this).index() == index) {
                            var radio = $('input[type="radio"]', this);
                            $(radio).attr("id", value);
                            $(radio).closest("label").attr("for", value)
                        }
                    })
                }, 300)
            }

            function addRadio(e) {
                var parent = $(e).parent().parent().parent(), dataId = "", radioName, index = parent.index(),
                    itemTemplate;
                $(".controls", element).children().each(function () {
                    if ($(this).index() == index) {
                        dataId = gvDesigner.newName("radio");
                        radioName = $('input[type="radio"]', this).attr("name");
                        var template = $('<label class="radio"><input id="' + dataId + '" name="' + radioName + '" type="radio" ><\/label>');
                        $(template).insertAfter($(this))
                    }
                });
                itemTemplate = $('<li id="' + dataId + '"><div class="input-group"><span class="input-group-addon">&nbsp;<span class="icon-reorder">&nbsp;<\/span>&nbsp;<input id="_' + dataId + '" name=_"' + radioName + '" type="radio">&nbsp;<\/span><input class="prop-option form-control" name="rdslabel" value="" type="text" /><input class="prop-option form-control" name="rdsvalue" type="text" /><input class="prop-option form-control" name="rdsid" type="text" /><span class="input-group-addon">&nbsp;<span class="icon-add">&nbsp;&nbsp;&nbsp;&nbsp;<\/span>&nbsp;<span class="icon-delete">&nbsp;&nbsp;&nbsp;&nbsp;<\/span>&nbsp;<\/span><\/div><\/li>');
                itemTemplate.insertAfter($(parent));
                $("input[name='rdsvalue']", itemTemplate).fadeOut(0);
                $("input[name='rdsid']", itemTemplate).fadeOut(0);
                $('input[name="rdslabel"]', radioItems).keyup(function () {
                    radioLabelKeyup(this)
                });
                $('input[name="rdsvalue"]', radioItems).keyup(function () {
                    radioValueKeyup(this)
                });
                $('input[name="rdsid"]', radioItems).keyup(function () {
                    radioIdKeyup(this)
                });
                $('input[type="radio"]', itemTemplate).change(function () {
                    radioChange(this)
                });
                $("span.icon-add", itemTemplate).click(function () {
                    addRadio(this)
                });
                $("span.icon-delete", itemTemplate).click(function () {
                    deleteRadio(this)
                });
                gvDesigner.spread()
            }

            function deleteRadio(e) {
                var parent = $(e).parent().parent().parent()
                    , index = parent.index();
                if (parent.siblings().length > 0) {
                    $(parent).remove();
                }
                $(".controls", element).children().each(function () {
                    if ($(this).index() == index) {
                        $(this).remove();
                        index = -99;
                    }
                });
                gvDesigner.spread()
            }

            var radioItems, id, name, rdsItem, timeOut;
            element = ele;
            radioItems = $("#__radioItems", propertyEle);
            radioItems.children().remove();
            $(".controls", element).children().each(function () {
                var radio = $('input[type="radio"]', this), template;
                if (radio.length > 0) {
                    id = $('input[type="radio"]', this).attr("id");
                    name = $('input[type="radio"]', this).attr("name");
                    template = $('<li id="' + id + '"><div class="input-group"><span class="input-group-addon">&nbsp;<span class="icon-reorder">&nbsp;<\/span>&nbsp;<input id="_' + id + '" name=_"' + name + '" type="radio">&nbsp;<\/span><input class="prop-option form-control" name="rdslabel" value="' + $(this).text() + '" type="text" /><input class="prop-option form-control" name="rdsvalue" value="' + $(radio).attr("value") + '" type="text" /><input class="prop-option form-control" name="rdsid" value="' + $(radio).attr("id") + '" type="text" /><span class="input-group-addon">&nbsp;<span class="icon-add">&nbsp;&nbsp;&nbsp;&nbsp;<\/span>&nbsp;<span class="icon-delete">&nbsp;&nbsp;&nbsp;&nbsp;<\/span>&nbsp;<\/span><\/div><\/li>');
                    $(radio).is(":checked") && $('input[type="radio"]', template).attr("checked", true);
                    $(radioItems).append(template);
                }
            });
            rdsItem = $("#rdsitem");
            $("input[name='rdsvalue']", rdsItem).fadeOut(0);
            $("input[name='rdsid']", rdsItem).fadeOut(0);
            $("#rdselectLabel").click(function () {
                var item = $("#rdsitem");
                $("input[name='rdsid']", item).fadeOut(0);
                $("input[name='rdsvalue']", item).fadeOut(0);
                $("input[name='rdslabel']", item).fadeIn(0)
            });
            $("#rdselectValue").click(function () {
                var item = $("#rdsitem");
                $("input[name='rdslabel']", item).fadeOut(0);
                $("input[name='rdsid']", item).fadeOut(0);
                $("input[name='rdsvalue']", item).fadeIn(0)
            });
            $("#rdselectId").click(function () {
                var item = $("#rdsitem");
                $("input[name='rdslabel']", item).fadeOut(0);
                $("input[name='rdsvalue']", item).fadeOut(0);
                $("input[name='rdsid']", item).fadeIn(0)
            });
            $('input[type="radio"]', radioItems).change(function () {
                radioChange(this);
            });
            $('input[name="rdslabel"]', radioItems).keyup(function () {
                radioLabelKeyup(this);
            });
            $('input[name="rdsvalue"]', radioItems).keyup(function () {
                radioValueKeyup(this);
            });
            $('input[name="rdsid"]', radioItems).keyup(function () {
                radioIdKeyup(this);
            });
            $("span.icon-add", radioItems).click(function () {
                addRadio(this);
            });
            $("span.icon-delete", radioItems).click(function () {
                deleteRadio(this);
            });
            timeOut = function () {
                var n = 0;
                return function (t, i) {
                    clearTimeout(n);
                    n = setTimeout(t, i)
                }
            }()
        };

        radioComponent.add = function (ele) {
            var template, cloneTemplate, radio, id, fielId;
            element = ele;
            template = $("#RadioTemplate").clone();
            $(ele).find("label:first").removeAttr("for");
            $(ele).find("label:first").text("Select a Choice");
            fielId = gvDesigner.newName("field");
            $(template).children().each(function () {
                id = gvDesigner.newName("radio");
                cloneTemplate = $(this).clone();
                radio = $('input[type="radio"]', cloneTemplate);
                $(radio).attr("id", id);
                $(radio).attr("name", fielId);
                radio.parent().attr("for", id);
                $(".controls", ele).append(cloneTemplate);
            });
            return template;
        };

        radioComponent.delete = function () {
            alert("delete")
        };

        radioComponent.placeHolder = function (ele) {
            var template = $("#RadioTemplate").clone();
            $(".controls", ele).append(template);
            $(ele).find("label:first").text("Select an Option");
            return ele;
        };
        return radioComponent;
    }())
});
$(function () {
    var element;
    gvDesigner.register("select", function () {
        var selectComponent = {};
        selectComponent.click = function (ele, propertyEle) {
            function addSelect(e) {
                var parent = $(e).parent().parent().parent()
                    , dataId = gvDesigner.newName("__r")
                    ,
                    template = $('<li id="' + dataId + '"><div class="input-group"><span class="input-group-addon">&nbsp;<span class="icon-reorder">&nbsp;<\/span>&nbsp;<input id="_' + dataId + '" type="radio">&nbsp;<\/span><input class="form-control" value="" type="text" /><span class="input-group-addon">&nbsp;<span class="icon-add">&nbsp;&nbsp;&nbsp;&nbsp;<\/span>&nbsp;<span class="icon-delete">&nbsp;&nbsp;&nbsp;&nbsp;<\/span>&nbsp;<\/span><\/div><\/li>');
                template.insertAfter($(parent));
                changeSelected();
                $('input[type="text"]', template).keyup(function () {
                    onSelectKeyUp(this)
                });
                $('input[type="radio"]', template).change(function () {
                    radioChange(this)
                });
                $("span.icon-add", template).click(function () {
                    addSelect(this)
                });
                $("span.icon-delete", template).click(function () {
                    deleteSelect(this)
                })
            }

            function deleteSelect(e) {
                var parent = $(e).parent().parent().parent();
                if (parent.siblings().length > 0) {
                    $(parent).remove();
                    changeSelected();
                }
            }

            function selectLabelKeyup(e) {
                var index = $(e).parent().parent().index()
                    , value = $(e).val();
                timeOut(function () {
                    $("option", element).each(function () {
                        if ($(this).index() == index) {
                            $(selectItems).attr("id", value);
                            $(this).text(value);
                        }
                    })
                }, 300)
            }

            function selectValueKeyup(e) {
                var index = $(e).parent().parent().index()
                    , value = $(e).val();
                timeOut(function () {
                    $("option", element).each(function () {
                        if ($(this).index() == index) {
                            $(this).val(value)
                        }
                    })
                }, 300)
            }

            function radioChange(e) {
                var index = $(e).parent().parent().index();
                $("option", element).each(function () {
                    $(this).attr("selected", false);
                    $(this).removeAttr("selected");
                });
                $("option", element).each(function () {
                    if ($(this).index() == index) {
                        $(this).attr("selected", true);
                    }
                })
            }

            function changeSelected() {
                var value = $("option:selected", element).val();
                $("select", element).children().remove();
                $("#__selectOptions > li").each(function () {
                    var val = $('input[type="text"]', this).val();
                    $("select", element).append($('<option value="' + val + '">' + val + "<\/option>"))
                });
                $("select", element).val(value).attr("selected", true)
            }

            var selects, selected, str, id, selectItems, timeOut;
            element = ele;
            selects = $("#__selectOptions", propertyEle);
            selects.children().remove();
            selected = $("option:selected", element);
            $("option", element).each(function () {
                str = "";
                id = gvDesigner.newName("__o");
                if ($(selected).val() == $(this).val()) {
                    str = 'checked="true"';
                }
                var template = '<li id="' + id + '"><div class="input-group"><span class="input-group-addon">&nbsp;<span class="icon-reorder">&nbsp;<\/span>&nbsp;<input id="_' + id + '" type="radio" name="seloption"' + str + '>&nbsp;<\/span><input name="__rdselectlabel" class="form-control" value="' + $(this).text() + '" type="text" /><input  name="__rdselectvalue" class="form-control hidden" value="' + $(this).attr("value") + '" type="text" /><span class="input-group-addon">&nbsp;<span class="icon-add">&nbsp;&nbsp;&nbsp;&nbsp;<\/span>&nbsp;<span class="icon-delete">&nbsp;&nbsp;&nbsp;&nbsp;<\/span>&nbsp;<\/span><\/div><\/li>';
                $("#fieldproperties #__selectOptions").append($(template))
            });
            selectItems = $("#___rdselectitems");
            $("input[name='__rdselectvalue']", selectItems).addClass("hidden");
            $("input[name='__rdselectvalue']", selectItems).addClass("hidden");
            $("#___rdselectLabel").click(function () {
                var items = $("#___rdselectitems");
                $("input[name='__rdselectvalue']", selectItems).addClass("hidden");
                $("input[name='__rdselectlabel']", selectItems).removeClass("hidden")
            });
            $("#___rdselectValue").click(function () {
                var items = $("#___rdselectitems");
                $("input[name='__rdselectlabel']", selectItems).addClass("hidden");
                $("input[name='__rdselectvalue']", selectItems).removeClass("hidden")
            });
            $('input[name="__rdselectlabel"]', selects).keyup(function () {
                selectLabelKeyup(this)
            });
            $('input[name="__rdselectvalue"]', selects).keyup(function () {
                selectValueKeyup(this)
            });
            $('input[type="radio"]', selects).change(function () {
                radioChange(this)
            });
            $("span.icon-add", selects).click(function () {
                addSelect(this)
            });
            $("span.icon-delete", selects).click(function () {
                deleteSelect(this)
            });
            timeOut = function () {
                var n = 0;
                return function (t, i) {
                    clearTimeout(n);
                    n = setTimeout(t, i)
                }
            }()
        };
        selectComponent.placeHolder = function (ele) {
            element = ele;
            var template = $("#SelectTemplate").clone();
            $(".controls", ele).append(template);
            $(ele).find("label:first").text("Select");
            return ele;
        };
        selectComponent.add = function (ele, id) {
            element = ele;
            var template = $("#SelectTemplate").clone();
            $(".controls", ele).append(template);
            $(template).attr("id", id);
            $(ele).find("label:first").attr("for", id);
            $(ele).find("label:first").text("Select");
            return template;
        };
        selectComponent.delete = function () {
            alert("delete")
        };
        return selectComponent;
    }())
});
$(function () {
    var element, initSlider = function () {
        var slider = $('input[data-role="slider"]', gvDesigner.selectedGroup),
            orientation = slider.attr("data-property-orientation"),
            min = slider.attr("data-property-min"), max = slider.attr("data-property-max"),
            showButton = slider.attr("data-property-showbutton"),
            value = slider.attr("value"), cloneTemplate = slider.clone(), controls = slider.parentsUntil(".controls"),
            s;
        controls.children().remove();
        cloneTemplate.css("display", "");
        controls.append(cloneTemplate);
        s = cloneTemplate.kendoSlider({
            showButtons: showButton === "true",
            orientation: orientation,
            value: parseInt(value),
            min: parseInt(min),
            max: parseInt(max)
        }).data("kendoSlider")
    }, changeValue, numericTextBox;
    $("#sliderShowButtons").change(function () {
        var isChecked = $(this).is(":checked");
        $('input[data-role="slider"]', gvDesigner.selectedGroup).attr("data-property-showbutton", isChecked);
        initSlider()
    });
    $("#sliderOrientation").change(function () {
        var value = $(this).val();
        $('input[data-role="slider"]', gvDesigner.selectedGroup).attr("data-property-orientation", value)
    });
    changeValue = function (ele) {
        var value = ele.value(), slider = $('input[data-role="slider"]', element), textbox;
        slider.attr(ele.element.attr("data-property"), value);
        textbox = $(slider).data("kendoNumericTextBox");
        ele.value = value
    }
    ;
    numericTextBox = function (id) {
        var textbox = $("#" + id).kendoNumericTextBox({
            format: "n0"
        }).data("kendoNumericTextBox");
        textbox.bind("spin", function () {
            changeValue(this)
        });
        textbox.bind("change", function () {
            changeValue(this)
        })
    }
    ;
    numericTextBox("sliderdefaultvalue");
    numericTextBox("slidernumminvalue");
    numericTextBox("slidernummaxvalue");
    gvDesigner.register("slider", function () {
        var silderComponent = {};
        silderComponent.click = function (ele) {
            element = ele
        };
        silderComponent.add = function (ele, id) {
            element = ele;
            var template = $("#SliderTemplate").clone();
            $(".controls", ele).append(template);
            $(template).attr("id", id);
            $(ele).find("label:first").attr("for", id);
            $(ele).find("label:first").text("Slider");
            $(template).kendoSlider({
                orientation: "horizontal"
            });
            return template;
        };
        silderComponent.delete = function () {
            alert("delete")
        };
        return silderComponent;
    }())
});
$(function () {
    var element;
    gvDesigner.register("text", function () {
        function update() {
            var text = $(element).find('.controls > input[type="text"]:first')
                , limitType = $("#textLimitType :selected").text()
                , minValue = $("#textminvalue").val()
                , maxValue = $("#textmaxvalue").val();

            text.removeAttr("data-parsley-minwords");
            text.removeAttr("data-parsley-maxwords");
            text.removeAttr("data-parsley-minlength");
            text.removeAttr("data-parsley-maxlength");
            if (limitType == "String") {
                if (minValue > 0) {
                    text.attr("data-parsley-minlength", minValue);
                }
                if (maxValue > 0) {
                    $(text).attr("data-parsley-maxlength", maxValue)
                }
            } else {
                if (minValue > 0) {
                    text.attr("data-parsley-minwords", minValue);
                }
                if (maxValue > 0) {
                    $(text).attr("data-parsley-maxwords", maxValue)
                }
            }
        }

        var textComponent = {};
        $("#textminvalue, #textmaxvalue").kendoNumericTextBox({
            change: update,
            spin: update,
            min: 0,
            format: "#,###"
        });

        $("#textLimitType").change(function () {
            update();
        });
        textComponent.click = function (ele) {
            element = ele;
            var limitType = ""
                , text = $(element).find('.controls > input[type="text"]:first')
                , minValue = $("#textminvalue").data("kendoNumericTextBox")
                , maxValue = $("#textmaxvalue").data("kendoNumericTextBox")
                , minWords = text.attr("data-parsley-minwords")
                , maxWords = text.attr("data-parsley-maxwords")
                , minLength = text.attr("data-parsley-minlength")
                , maxLength = text.attr("data-parsley-maxlength");

            minValue.value(null);
            maxValue.value(null);
            if (minWords || maxWords) {
                minValue.value(minWords || null);
                maxValue.value(maxWords || null);
                limitType = "Word";
            }
            if (minLength || maxLength) {
                minValue.value(minLength || null);
                maxValue.value(maxLength || null);
                limitType = "String";
            }
            $("#textLimitType").val(limitType).attr("selected", true);
        };

        textComponent.placeHolder = function (ele) {
            var template = $("#TextTemplate").clone();
            $(ele).find(".controls").append(template);
            $(ele).find("label:first").text("Untitled");
            return ele;
        };

        textComponent.add = function (ele, id) {
            element = ele;
            var template = $("#TextTemplate").clone();
            $(ele).find(".controls").append(template);
            $(template).attr("id", id);
            $(ele).find("label:first").attr("for", id);
            $(ele).find("label:first").text("Untitled");
            return template;
        };
        textComponent.delete = function () {
            alert("delete")
        };
        return textComponent;
    }())
});
$(function () {
    var element;
    gvDesigner.register("textarea", function () {
        function update() {
            var textarea = $(element).find(".controls textarea")
                , limitType = $("#textareaLimitType :selected").text()
                , minValue = $("#textareaminvalue").val()
                , maxValue = $("#textareamaxvalue").val();
            element.removeAttr("data-minwords");
            element.removeAttr("data-maxwords");
            element.removeAttr("data-minlength");
            element.removeAttr("data-maxlength");
            if (limitType == "String") {
                if (minValue > 0) {
                    textarea.attr("data-minlength", minValue);
                }
                if (maxValue > 0) {
                    $(textarea).attr("data-maxlength", maxValue)
                }
            } else {
                if (minValue > 0) {
                    textarea.attr("data-minwords", minValue);
                }
                if (maxValue > 0) {
                    $(textarea).attr("data-maxwords", maxValue)
                }
            }
        }

        var textareaComponent = {};
        $("#textareaminvalue, #textareamaxvalue").kendoNumericTextBox({
            change: update,
            spin: update,
            min: 0,
            format: "#,###"
        });

        $("#textareaLimitType").change(function () {
            update()
        });

        textareaComponent.click = function (ele) {
            element = ele;
            var limitType = ""
                , textarea = $(".controls textarea", element)
                , minValue = $("#textareaminvalue").data("kendoNumericTextBox")
                , maxValue = $("#textareamaxvalue").data("kendoNumericTextBox")
                , minWords = textarea.attr("data-minwords")
                , maxWords = textarea.attr("data-maxwords")
                , minLength = textarea.attr("data-minlength")
                , maxLength = textarea.attr("data-maxlength");
            minValue.value(null);
            maxValue.value(null);
            if (minWords || maxWords) {
                minValue.value(minWords || null);
                maxValue.value(maxWords || null);
                limitType = "Word";
            }
            if (minLength || maxLength) {
                minValue.value(minLength || null);
                maxValue.value(maxLength || null);
                limitType = "String";
            }
            $("#textareaLimitType").val(limitType).attr("selected", true);
        }
        textareaComponent.add = function (ele, id) {
            element = ele;
            var template = $("#TextAreaTemplate").clone();
            $(ele).find(".controls").append(template);
            $(template).attr("id", id);
            $(ele).find("label:first").attr("for", id);
            $(ele).find("label:first").text("Untitled");
            return template;
        };
        textareaComponent.delete = function () {
            alert("delete")
        };
        textareaComponent.placeHolder = function (ele) {
            var template = $("#TextAreaTemplate").clone();
            $(ele).find(".controls").append(template);
            $(ele).find("label:first").text("Untitled");
            return ele;
        };
        return textareaComponent;
    }())
});
