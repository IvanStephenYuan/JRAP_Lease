(function (designer, $) {
    var registeredComponent = [], dragging, componentId, selectedComponent, currentProperties,currentFieldProperties,fieldProperties = $("#fieldproperties"), selectCount = 0,form={},viewModels={},vmDataSource,bindVMDataSource;

    /**
     * 获取注册组件配置.
     *
     * @param n
     * @returns {*}
     */
    function getRegisteredComponent(n) {
        for (var t = 0, len = registeredComponent.length; t < len; t++) {
            if (registeredComponent[t][0] == n)
                return registeredComponent[t][1];
        }
        return null;
    }

    /***
     * checkbox之类的选框一行 两行之类的布局配置
     */
    function spread() {
        var n, f = 0, o = false, columnCount = 0, controlType = currentProperties.controlType,
            s = $("#fieldproperties #" + controlType + 'properties select[data-gv-property="layout"] option:selected').val();
        switch (s) {
            case "Inline":
                n = "inline";
                controlType == "radio";
                break;
            case "One Column":
                n = "";
                break;
            case "Two Columns":
                n = "col-xs-6";
                columnCount = 2;
                break;
            case "Three Columns":
                n = "col-xs-4";
                columnCount = 3;
                break;
            case "Four Columns":
                n = "col-xs-3";
                columnCount = 4
        }
        $(".controls", selectedComponent).children().each(function () {
            for (f = f + 1, i = 0; i != 12; i++) {
                $(this).removeClass("col-xs-" + i).removeClass("field-layout-reset radio-inline checkbox-inline");
            }
            if (columnCount) {
                $(this).addClass(n);
                /**
                 * ?????
                 */
                if (o == true) {
                    $(this).addClass("field-layout-reset");
                    o = f % columnCount == 0 ? true : false;
                }
            } else if (n == "inline") {
                if (controlType == "checkbox") {
                    $(this).addClass("checkbox-inline").removeClass("checkbox")
                } else if (controlType == "radio") {
                    $(this).addClass("radio-inline").removeClass("radio")
                }
            }
        })
    }




    /**
     * 生成column组件
     *
     * @param event
     * @param ui
     * @param containerId
     */
    function createContainer(event, ui, containerId) {
        var html, f;
        if (arguments.length == 1) {
            (containerId = event);
        }
        html = generatePlaceholder(containerId, ui);
        $(".gv-droppable-grid", html).attr("data-alignment", "Left");
        arguments.length == 1 ? $("#design-canvas").append(html) : (f = $(ui.item).context, $(f).replaceWith(html));
        droppableGridInit(html)
    }

    function droppableGridInit(n) {
        $(".gv-droppable-grid", n).each(function (index, item) {
            initContainerSortable(index, item)
        })
    }


    /**
     * 生成tab页组件
     * @param n
     * @param ui
     */
    function createTab(n, ui) {
        var u = $(ui.item).context
            , renderEle = $(document.getElementById("tabTemplate")).clone();
        $(renderEle).attr("id", generateComponentName("tab"));
        $(".tab-content", renderEle).children().each(function (n) {
            var i = generateComponentName("");
            $(this).attr("id", "tabContent" + i);
            $("li:eq(" + n + ") a", renderEle).attr("href", "#tabContent" + i).attr("id", "tabLabel" + i)
        });
        $(u).replaceWith(renderEle);
        renderEle.wrap("<div class='form-group'></div>");
        tabEventInit(renderEle)

    }

    function tabEventInit(ele) {
        var tabConfigEventInit = function () {
            var getTabInfo, onTabNameUpdate, onConfigAddTabs, onConfigDeleteTabs;

            var ul = $("ul.nav-tabs:first", selectedComponent);

            getTabInfo = function () {
                $("#__tabItems", fieldProperties).children().remove();
                $("li", ul).each(function (n, i) {
                    var text = $("a", i).text().trim(),
                        id = $("a", i).attr("id"),
                        tanInfo = $('<li><div class="input-group" style="display: flex;"><div style="margin-bottom: 5px;float:left;width: 100%;"><input data-id="' + id + '" value="' + text + '" class="k-textbox" type="text"></div><span style="float:left;margin: auto" class="input-group-addon"><span class="icon-add"><i class="fa fa-plus-square-o" aria-hidden="true"><\/i><\/span><span class="icon-delete"><i class="fa fa-minus-square-o" aria-hidden="true"><\/i> <\/span><\/span><\/div><\/li>');
                    $("#__tabItems", tabproperties).append(tanInfo)
                })
            };
            getTabInfo();
            onTabNameUpdate = function () {
                $("#__tabItems input", fieldProperties).unbind("change");
                $("#__tabItems input", fieldProperties).change(function () {
                    var tabId = $(this).attr("data-id")
                        , tabs_canvas = $("#" + tabId);
                    $(tabs_canvas).text(this.value)
                })
            };
            onTabNameUpdate();

            onConfigAddTabs = function () {
                $("#__tabItems span.icon-add", fieldProperties).unbind("click");
                $("#__tabItems span.icon-add", fieldProperties).click(function () {
                    var tabName = generateComponentName(""), tabIndex = $(this).closest("li").index(),
                        tabEle = $(ul).children().eq(tabIndex),
                        newTabLabel = $('<li><a id="tabLabel' + tabName + '" href="#tabContent' + tabName + '" data-toggle="tab">Undefined<\/a><\/li>'),
                        newTabContent = $('<div class="tab-pane" id="tabContent' + tabName + '"><div class="tab-canvas" data-alignment="Left"><\/div><\/div>'),
                        tabContent;
                    $(newTabLabel).insertAfter(tabEle);
                    tabContent = $("div.tab-content", $(ul).parent()).children().eq(tabIndex);
                    newTabContent.insertAfter(tabContent);
                    getTabInfo();
                    onConfigAddTabs();
                    onConfigDeleteTabs();
                    onTabNameUpdate();
                    tabSortableInit()
                })
            };
            onConfigAddTabs();

            onConfigDeleteTabs = function () {
                $("#__tabItems span.icon-delete", fieldProperties).unbind("click");
                $("#__tabItems span.icon-delete", fieldProperties).click(function () {
                    var deleteConfirm = confirm("Delete");
                    if (deleteConfirm == true) {
                        var tabLabelId = $(this).closest("li").find("input:first").attr("data-id")
                            , tabLabel = $("#" + tabLabelId)
                            , tabContentId = $(tabLabel).attr("href").replace("#", "")
                            , tabContent = $("#" + tabContentId)
                            , tabWrapper = $(tabContent).parent().parent();
                        $(this).closest("li").remove();
                        $(tabLabel).hide("slow", function () {
                            $(tabLabel).parent().remove();
                            $(tabContent).remove();
                            if($("div.tab-content > div", tabWrapper).length == 0){
                                $(tabWrapper).remove()
                            }
                        });

                    }
                })
            };
            onConfigDeleteTabs()
        };


        $(ele).click(function (e) {
            selectCount ++;
            if(selectCount == 1){
                selectedComponent =  $(e.target).closest('[data-role="tab"]').parent();
                var hasFocus = $(selectedComponent).hasClass("hasFocus"),controlType;
                $(".hasFocus").removeClass("hasFocus");
                $(selectedComponent).addClass("hasFocus");
                if (!hasFocus) {
                    $(selectedComponent).addClass("hasFocus");
                    designer.selectedControlType = "tab";
                    controlType = "tab";
                    currentFieldProperties = $("#tabproperties");
                }

                if (controlType) {
                    fieldProperties.children().fadeOut(0);
                    currentFieldProperties.fadeIn(0);
                    tabConfigEventInit();
                }
            }

        })

        var tabSortableInit = function () {
            $(ele).find(".tab-canvas").each(function () {
                $(this).sortable({
                    appendTo: "#design-canvas",
                    delay: 200,
                    tolerance: "pointer",
                    connectWith: "#design-canvas, .gv-droppable-grid, .tab-canvas",
                    placeholder: "ui-state-highlight",
                    start: function (n, i) {
                        if(dragging == true){
                            var placeholder = generatePlaceholder(componentId, i);
                            i.placeholder.html($(placeholder).html())
                        }else {
                            i.placeholder.height(25);
                        }
                    },
                    helper: "clone",
                    stop: function (n, t) {
                        if(dragging == true){
                            switch (componentId) {
                                case "menu1Column":
                                case "menu2Columns":
                                case "menu3Columns":
                                case "menu4Columns":
                                case "twoandten":
                                case "custom":
                                    createContainer(n, t, componentId);
                                    break;
                                case "_tab":
                                    createTab(n, t)
                                    break;
                                default:
                                    if (componentId != null) {
                                        renderFormControl(n, t, componentId)
                                    }
                            }
                        }
                        dragging = false;
                    }
                })
            })
        };
        tabSortableInit();

    }

    function onColRemove(el) {
        var o = $(el).siblings(), colSpan = getColNumber(el);
        if (colSpan != 12) {
            var nextEl = $(el).next().length > 0 ? $(el).next() : $(el).prev(), nextSpan = getColNumber(nextEl);
            $(nextEl).removeClass("col-xs-" + nextSpan);
            $(nextEl).addClass("col-xs-" + (colSpan + nextSpan));
            //$(el).hide("slow", function () {
            $(el).remove()
            //})
        } else {
            //$(el).parent().hide("slow", function () {
            $(el).parent().remove()
            //});
        }
    }

    /**
     * 生成占位符组件.
     *
     * @param type
     * @param ui
     *
     * @returns {*}
     */
    function generatePlaceholder(type, ui) {
        var ui_new;
        switch (type) {
            case "twoandten":
                return $('<div class="row"><div id="'+generateComponentName("")+'" class="col-xs-2 gv-droppable-grid"><\/div><div class="col-xs-10 gv-droppable-grid" id="'+generateComponentName("")+'"><\/div><\/div>');
            case "menu1Column":
                return $('<div class="row"><div id="'+generateComponentName("")+'" class="col-xs-12  gv-droppable-grid"><\/div><\/div>');
            case "menu2Columns":
                return $('<div class="row"><div id="'+generateComponentName("")+'" class="col-xs-6 gv-droppable-grid"><\/div><div id="'+generateComponentName("")+'" class="col-xs-6 gv-droppable-grid"><\/div><\/div>');
            case "menu3Columns":
                return $('<div class="row"><div id="'+generateComponentName("")+'" class="col-xs-4 gv-droppable-grid"><\/div><div id="'+generateComponentName("")+'" class="col-xs-4 gv-droppable-grid"><\/div><div id="'+generateComponentName("")+'" class="col-xs-4 gv-droppable-grid"><\/div><\/div>');
            case "menu4Columns":
                return $('<div class="row"><div id="'+generateComponentName("")+'" class="col-xs-3 gv-droppable-grid"><\/div><div id="'+generateComponentName("")+'" class="col-xs-3 gv-droppable-grid"><\/div><div id="'+generateComponentName("")+'" class="col-xs-3 gv-droppable-grid"><\/div><div id="'+generateComponentName("")+'" class="col-xs-3 gv-droppable-grid"><\/div><\/div>');
            case "_tab":
                return $(document.getElementById("tabTemplate")).clone();
            case "_collapsible":
                return $(document.getElementById("panelTemplate")).clone();
            default:
                var template = type.replace("_", ""), html = $(ui.item).context, element = $(html).parent(),
                    options = getRegisteredComponent(template.replace("Template", "").toLowerCase());
                if (options) {
                    ui_new = $("#controlGroupTemplate").clone();
                    if (options.placeHolder) {
                        ui_new = options.placeHolder(ui_new)
                    }
                    $(ui_new).removeAttr("id");
                    if (componentId != "_LabelTemplate" && componentId != "_SeparatorTemplate" &&element.attr("data-alignment")) {
                        var options = {
                            alignment:element.attr("data-alignment")
                        }
                        componentLayout(ui_new,options);
                    }
                    return $('<div class="col-xs-12"><\/div>').append(ui_new);
                }
        }
        return;
    }


    function initContainerSortable(index, element) {

        $(element).sortable({
            delay: 200,
            connectWith: "#design-canvas, .gv-droppable-grid, .tab-canvas",
            tolerance: "pointer",
            appendTo: "#design-canvas",
            helper: "clone",
            start: function (event, ui) {
                if (dragging == true) {
                    var placeholder = generatePlaceholder(componentId, ui);
                    ui.placeholder.html($(placeholder).html())
                } else
                    ui.placeholder.height("25")
            },
            placeholder: "ui-state-highlight",
            stop: function (event, ui) {
                if(dragging ==true ){
                    switch (componentId) {
                        case "menu1Column":
                        case "menu2Columns":
                        case "menu3Columns":
                        case "menu4Columns":
                        case "twoandten":
                        case "custom":
                            createContainer(event, ui, componentId);
                            break;
                        case "_tab":
                            createTab(event, ui);
                            break;
                        default:
                            if (componentId != null) {
                                renderFormControl(event, ui, componentId)
                            }
                    }
                }
                componentId = null;
                dragging = false
            }
        });



        $(element).click(function () {
            selectCount++;
            if (selectCount == 1) {
                $(".hasFocus").removeClass("hasFocus")
                if (!$(this).hasClass("hasFocus")) {
                    $(this).addClass("hasFocus");
                    designer.selectedControlType = "column";
                }
                $("#fieldproperties").children().fadeOut(0);
                var columnProperties = $("#columnproperties"), focusEl = $(".hasFocus");

                var groupalignment = $("input[data-gv-property='groupalignment']",columnProperties).kendoDropDownList({
                    dataSource:[{text:"顶部对齐（Top）",value:"control-label-top"},{text:"左对齐（Left）",value:"control-label-left"},{text:"右对齐（Right）",value:"control-label-right"}],
                    dataTextField:"text",
                    dataValueField:"value",
                    select:function (e) {
                        var value = e.dataItem.value
                            ,formGroup = $(".hasFocus").find(".form-group");
                        formGroup.each(function () {
                            $(this).find("label:first").removeClass("control-label-left").removeClass("control-label-right");
                        })
                        if(value == "control-label-top"){
                            formGroup.each(function () {
                                var label = $(this).find("label:first")
                                    ,content = $(this).find("label:first").next();
                                label.removeClass("col-xs-"+getColNumber(label));
                                content.removeClass("col-xs-"+getColNumber(content));
                            });
                        }else{
                            formGroup.each(function () {
                                var label = $(this).find("label:first")
                                    ,content = $(this).find("label:first").next();
                                label.addClass(value).addClass("col-xs-"+$(label).attr("gv-alignment-col"))
                                content.addClass("col-xs-"+content.attr("gv-alignment-col"))
                            });
                        }

                        $(".hasFocus").attr("data-alignment",e.dataItem.text);
                        $(".hasFocus").find(".gv-droppable-grid.ui-sortable").attr("data-alignment",e.dataItem.text);
                    }
                }).data("kendoDropDownList");

                $('textarea[data-gv-property="columnCss"]',columnProperties).off("change").on("change",function () {
                    focusEl[0].style.cssText = this.value;
                })

                var initInputSortable = function (ele) {
                    $("#__cols").sortable({
                        axis: "y",
                        placeholder: "ui-state-highlight",
                        cursor: "move",
                        delay: 200,
                        helper: "clone",
                        start: function (n, t) {
                            dragging = false;
                            t.placeholder.height(t.item.height())
                        },
                        stop:function (n,t) {
                            var input = $("input",$(t.item).parent())
                                ,wrapper = $(ele).parent()
                                ,html = $(ele).parent().clone();
                            html.children().remove();
                            $.each(input,function (index,item) {
                                var col = $("#"+$(item).attr("data-id"));
                                html.append($(col,wrapper))
                            })
                            wrapper.children().remove();
                            wrapper.append(html.children());
                            droppableGridInit(wrapper);
                        }
                    })
                }
                var initPanel = function (ele) {
                    var ul = $("#__cols",columnProperties);
                    ul.children().remove();
                    var children = $(ele).parent().children(".gv-droppable-grid");
                    $.each(children,function (index,item) {
                        var li = $('<li><div><input data-gv-property="col" data-id="'+ $(item).attr("id")+'" value="' + getColNumber(item)+ '" type="text" style="width: 80%"><span class="input-group-addon"><span class="icon-add"><i class="fa fa-plus-square-o" aria-hidden="true"><\/i><\/span><span class="icon-delete"><i class="fa fa-minus-square-o" aria-hidden="true"><\/i> <\/span><span class="icon-reorder" title="拖拽"><i class="fa fa-arrows" aria-hidden="true"><\/i><\/span><\/span><\/div><\/li>');
                        ul.append(li);
                    });
                    var onChange = function () {
                        var input = $("li input[data-gv-property='col']",ul);
                        $.each(input,function (index,item) {
                            if(!$(item).data("kendoNumericTextBox")){
                                $(item).kendoNumericTextBox({
                                    max:12,
                                    min:0,
                                    format:"n0",
                                    change: function() {
                                        var col = 12;
                                        $.each(input,function (index,item) {
                                            var value = $(item).data("kendoNumericTextBox").value();
                                            if(!value){
                                                value = 0;
                                            }
                                            col= col - value;
                                        });

                                        if(col >= 0){
                                            $.each(input,function (i,v) {
                                                var col = $("#"+$(v).attr("data-id"))
                                                col.removeClass("col-xs-"+getColNumber(col));
                                                col.addClass("col-xs-"+parseInt(v.value));
                                            })
                                        }
                                    }
                                }).data("kendoNumericTextBox");
                            }

                        })

                    };
                    var onAdd = function () {
                        $(".icon-add",ul).unbind("click").bind("click",function(){
                            var input = $("li input[data-gv-property='col']",ul),col =12;

                            $.each(input,function (index,item) {
                                var value = $(item).data("kendoNumericTextBox").value();
                                if(!value){
                                    value = 0;
                                }
                                col= col - value;
                            });

                            if(col > 0){
                                var id = generateComponentName("")
                                    , wrapper = $(ele).parent()
                                    ,index =  $(this).closest("li").index();
                                $(this).closest("li").after('<li><div><input data-gv-property="col" data-id="'+ id +'" value="'+col+'" style="width: 80%" type="text"><span class="input-group-addon"><span class="icon-add"><i class="fa fa-plus-square-o" aria-hidden="true"><\/i><\/span><span class="icon-delete"><i class="fa fa-minus-square-o" aria-hidden="true"><\/i> <\/span><span class="icon-reorder" title="拖拽"><i class="fa fa-arrows" aria-hidden="true"><\/i><\/span><\/span><\/div><\/li>');
                                var html = wrapper.clone();
                                $(wrapper.children()[index]).after('<div id="'+generateComponentName("")+'" data-alignment="Left" class="col-xs-'+col+' gv-droppable-grid"><\/div>');
                                $.each($("input[data-gv-property='col']",ul),function (index,item) {
                                    $(wrapper.children()[index]).removeClass("col-xs-"+getColNumber(wrapper.children()[index]));
                                    $(wrapper.children()[index]).addClass("col-xs-"+parseInt(item.value));
                                });
                                // $(".gv-droppable-grid", wrapper).attr("data-alignment", "Left");
                                // wrapper.children().remove();
                                //wrapper.append(html.children());
                                droppableGridInit(wrapper);
                                //$(".form-group", wrapper).click(componentClick);
                                initPanel(wrapper.children()[0]);
                            }else alert("总和不小于12，无法新增");
                            onChange();
                        });
                    }
                    var onDelete = function () {
                        $(".icon-delete",ul).unbind("click");
                        $(".icon-delete",ul).click(function (){
                            var deleteConfirm = confirm("确定删除？");
                            if(deleteConfirm == true) {
                                var id = $(this).closest("li").find("input[data-gv-property='col']").attr("data-id")
                                    , parent = $("#" + id).parent();
                                $(this).closest("li").remove();
                                onColRemove("#" + id);
                                if ($(parent).length > 0) {
                                    initPanel(parent.children()[0]);
                                }
                            }
                        })
                    }
                    onAdd();
                    onDelete();
                    onChange();
                    initInputSortable(ele);
                }
                initPanel(this);
                columnProperties.fadeIn(0);
                var alignment = focusEl.attr("data-alignment"), dataSize = focusEl.attr("data-size");
                if (alignment) {
                    groupalignment.value("control-label-"+alignment.toLowerCase());
                }
                if (dataSize) $('select[data-gv-property="groupsize"]', columnProperties).val(dataSize).attr("selected", true);
                $('textarea[data-gv-property="columnCss"]', columnProperties).val(focusEl[0].style.cssText);
            }
        })



    }


    function generateComponentName(name) {
        var i = $("#design-canvas").attr("data-control-count");
        i = parseInt(i) + 1;
        $("#design-canvas").attr("data-control-count", i);
        return name + i.toString();
    }


    /**
     * 在画布上渲染组件.
     * @param event
     * @param ui
     * @param componentId
     */
    function renderFormControl(event, ui, componentId) {
        if (componentId) {
            componentId = componentId.replace("_", "");
            var element, name = generateComponentName("field"), context = $(ui.item).context,
                parent = $(context).parent(),
                controlElement = $(document.getElementById("controlGroupTemplate")).clone();

            if (componentId == "HorizontalTemplate") {
                controlElement = $(document.getElementById("horizontalGroupTemplate")).clone();
            } else {
                if (componentId == "EditorTemplate") {
                    element = $(document.getElementById(componentId)).clone();
                    $(context).replaceWith(element);
                    $(element).attr("name", name);
                    $(element).kendoEditor();
                    return
                }
                if (componentId.replace("Template", "").toLowerCase()) {
                    var options = getRegisteredComponent(componentId.replace("Template", "").toLowerCase());
                    if (options) {
                        element = options.add(controlElement, name);
                    }
                }
            }

            $(controlElement).removeAttr("id");
            $(controlElement).attr("data-control-type", componentId.replace("Template", "").toLowerCase());
            if (componentId != "LabelTemplate" && componentId != "SeparatorTemplate" && parent.attr("data-alignment")) {
                var options = {
                    alignment:element.attr("data-alignment")
                }
                componentLayout(controlElement,options);
            }


            $(context).replaceWith(controlElement);
            $(controlElement).hover(function () {
                $(this).hasClass("hasFocus") == false && $(this).addClass("hoverComponent")
            }, function () {
                $(this).removeClass("hoverComponent")
            });

            $(controlElement).click(componentClick);

            if (componentId == "HorizontalTemplate") {
                $(controlElement).sortable({
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
                        renderFormControl(n, i, componentId)
                    }
                });
            }
            componentId = null;
        }
        //

    }

    function getComponentElement(controlType){

        var componentElement,
            componentArr =['dropdownlist','numerictextbox','datepicker','datetimepicker','timepicker','maskedtextbox','lov','combobox','tledit'];
        if (controlType == "textarea") {
            componentElement = $(".controls textarea", selectedComponent);
        } else if( $.inArray(controlType,componentArr) != -1){
            componentElement = $('.controls input[data-role="'+controlType+'"]', selectedComponent);
        }else if(controlType == 'radio'){
            componentElement = $(".controls div[data-role='radio']", selectedComponent);
        }else if(controlType == 'checkbox'){
            componentElement = $(".controls input[data-role='checkbox']:first", selectedComponent);
        }else if(controlType == 'button'){
            componentElement = $(".controls button", selectedComponent);
        }else {
            componentElement = $(".controls input", selectedComponent);
        }

        return componentElement;
    }

    function fieldProperty(selectedComponent) {

        this.controlType = "";

        this.label = function () {
            var componentLabel = $(selectedComponent).find("label:first").clone();
            $('span[class*="req"]', componentLabel).text("");
            return componentLabel.text();
        };

        this.isRequired = function () {
            var isRequired = $("#fieldproperties #" + this.controlType + 'properties input[data-gv-property="required"]').is(":checked");
            return isRequired;
        };

        this.isAdmin = function () {
            return $("#fieldproperties #" + this.controlType + 'properties input[id="__a"]:radio').attr("checked") == "checked"
        }
        ;
        this.init = function () {
            var hasFocus = $(selectedComponent).hasClass("hasFocus");
            $(".hasFocus").removeClass("hasFocus");
            $(selectedComponent).addClass("hasFocus");

            if (!hasFocus) {
                $(selectedComponent).addClass("hasFocus");
                this.controlType = $(selectedComponent).attr("data-control-type");
                designer.selectedControlType = this.controlType;
                currentFieldProperties = $("#" + this.controlType.toLowerCase() + "properties");
            }

            if (this.controlType) {

                fieldProperties.children().fadeOut(0);
                currentFieldProperties.fadeIn(0);

                var label = $(selectedComponent).find("label:first"),
                    hasIconLock = $(":first", label).hasClass("icon-lock"),
                    isRequired = $(":last", label).hasClass("req"),
                    componentElement= getComponentElement(this.controlType);

                /**
                 * id和name读取
                 */
                $('input[data-gv-property="control-id"]', currentFieldProperties).val($(componentElement).attr("id"));
                $('input[data-gv-property="control-name"]', currentFieldProperties).val($(componentElement).attr("name"));

                /**
                 * disabled 读取
                 * @type {string}
                 */
                var disabled = $(componentElement).prop("disabled")?"Y":"N";
                if($('input[data-gv-property="disabled"]', currentFieldProperties).data("kendoCheckbox")){
                    $('input[data-gv-property="disabled"]', currentFieldProperties).data("kendoCheckbox").value(disabled);
                    if( $(componentElement).prop("disabled")){
                        componentElement.addClass("k-state-disabled")
                    }else{
                        componentElement.removeClass("k-state-disabled")
                    }
                }




                /**
                 * defaultValue placeholder处理
                 *
                 */

                $('input[data-gv-property="label"]', currentFieldProperties).val(this.label);
                var helpBlock = $(".help-block", selectedComponent), subLabel = "";
                if (helpBlock.length > 0) {
                    subLabel = $(helpBlock).text()
                }
                $('input[data-gv-property="sublabel"]', currentFieldProperties).val(subLabel);
                $('input[data-gv-property="value"]', currentFieldProperties).val(componentElement.val());
                $('input[data-gv-property="placeholder"]', currentFieldProperties).val(componentElement.attr("placeholder"));



                /**
                 * required选择框
                 */
                var required = $(componentElement).prop("required")?"Y":"N";
                if($('input[data-gv-property="required"]', currentFieldProperties).data("kendoCheckbox")){
                    $('input[data-gv-property="required"]', currentFieldProperties).data("kendoCheckbox").value(required);
                }


                var radioA = $("#" + this.controlType + 'properties input[id="__a"]:radio', fieldProperties),
                    radioE = $("#" + this.controlType + 'properties input[id="__e"]:radio', fieldProperties);
                $(radioA).attr("checked", hasIconLock);
                $(radioE).attr("checked", !hasIconLock);


                /**
                 * label和输入框摆放 top left right 默认为left
                 * @type {*}
                 */
                var alignment,alignmentElement = $("#" + this.controlType + 'properties input[data-gv-property="alignment"]', fieldProperties).data("kendoDropDownList");
                if(alignmentElement && this.controlType != "lable" && this.controlType != "separator"){
                    if($(label).hasClass("control-label-left")){
                        alignment = "Left";
                    }else if($(label).hasClass("control-label-right")){
                        alignment = "Right";
                    }else{
                        alignment = "Top";
                    }
                    alignmentElement.value(alignment);

                    var scaleLabelElement = $("#" + this.controlType + 'properties input[data-gv-property="scale-label"]', fieldProperties).data("kendoNumericTextBox"),
                        scaleComponentElement = $("#" + this.controlType + 'properties input[data-gv-property="scale-component"]', fieldProperties).data("kendoNumericTextBox");
                    if(scaleLabelElement && scaleComponentElement){
                        scaleLabelElement.enable(true);
                        scaleComponentElement.enable(true);
                        scaleLabelElement.max(12);
                        scaleComponentElement.max(12);
                        scaleLabelElement.value($(label).attr("gv-alignment-col")||getColNumber($(label))||3);
                        scaleComponentElement.value($(selectedComponent).find("div.controls").attr("gv-alignment-col")||getColNumber($(selectedComponent).find("div.controls"))||9);
                        if(alignmentElement.value() == "Top"){
                            scaleLabelElement.enable(false);
                            scaleComponentElement.enable(false);
                        }

                    }
                }

                /**
                 * labeL-css label样式自定义
                 * component-css 组件样式自定义
                 */
                $('textarea[ data-gv-property="label-css"]', currentFieldProperties).val(label[0] && label[0].style.cssText||"");
                $('textarea[ data-gv-property="component-css"]', currentFieldProperties).val(componentElement[0] &&componentElement[0].style.cssText|| "");


                /**
                 * 绑定
                 */
                initBindEvents(componentElement);

                var valuePrimitive = $('input[data-gv-property="valueprimitive"]', currentFieldProperties).data("kendoDropDownList"),defaultValuePrimitive=false;
                if(valuePrimitive){
                    if(this.controlType == "lov" || this.controlType == "checkbox" ){
                        defaultValuePrimitive = true;
                    }
                    valuePrimitive.value(componentElement.attr("data-value-primitive") || defaultValuePrimitive);
                }

            }
        };
        this.init();
        this.value = function () {
            return getComponentElement(this.controlType).val()
        }
    }


    /**
     *  组件点击时触发事件
     */
    function componentClick(event){
        selectCount += 1;
        var nodeName = $(event.target).context.nodeName;
        var nodeType = $(event.target).context.type;
        selectedComponent = $(this);
        if (!("__,SELECT,INPUT,OPTION,TEXTAREA".indexOf(nodeName) > 0 && selectedComponent.hasClass("hasFocus"))) {
            currentProperties = new fieldProperty(selectedComponent);
            var type = currentProperties.controlType;
            designer.selectedProperty = $("#fieldproperties");
            if (type) {
                var component = getRegisteredComponent(type);
                component.initPropertyPanel(selectedComponent, $("#fieldproperties"));
            }

        } else if (nodeType && "checkbox,radio,select-one".indexOf(nodeType) != -1) {
            var hasFocus = $(selectedComponent).hasClass("hasFocus");
            if (hasFocus) {
                var component = getRegisteredComponent(currentProperties.controlType);
                component.initPropertyPanel(selectedComponent, $("#fieldproperties"));
            }
        }
    }
    /**
     * 初始化画布.
     */
    designer.initDesignCanvas = function() {
        var designCanvas = $("#design-canvas"), o;

        if($(".hasFocus").length>0){
            selectedComponent = $(".hasFocus");
        }


        var generatorComponent =['dropdownlist','checkbox','radio','numerictextbox','datepicker','datetimepicker','timepicker','maskedtextbox','lov','combobox','tledit'];
        $.each(generatorComponent,function(i,roleName){
            $('[data-role="'+roleName+'"]', designCanvas).each(function () {
                if(roleName == "numerictextbox"){
                    var width = this.style.width;
                    kendo.init($(this));
                    this.style.width = width;
                }else{
                    kendo.init($(this));
                }
            });
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

        $(".form-group", designCanvas).click(componentClick);

        $("div.gv-droppable-grid", designCanvas).each(function (n, t) {
            initContainerSortable(n, t)
        });

        $('div[data-role="tab"]', designCanvas).each(function () {
            tabEventInit(this)
        });

    }


    /**
     * 组件布局.
     *
     * @param el
     * @param componentLayout
     * @returns {*}
     */
    function componentLayout(el, options) {
        var scaleLabel = options.scaleLabel||3;
        var alignment =  options.alignment ||'Left';
        var scaleComponent = options.scaleComponent || 9;
        var labelColLength =  $(el).find("label:first").attr("gv-alignment-col") || 3;
        var componentLength = $(el).find("div.controls").attr("gv-alignment-col") || 9;
        $(el).find("label:first").removeClass("control-label").removeClass("control-label-left").removeClass("control-label-right").removeClass("col-xs-"+labelColLength);
        $(el).find("div.controls").removeClass("control-inline").removeClass("col-xs-"+componentLength);
        switch (alignment) {
            case "Top":
                $(el).find("label:first").addClass("control-label");
                $(el).removeClass("form-horizontal");
                break;
            case "Right":
                $(el).find("label:first").addClass("control-label").addClass("control-label-right").addClass("col-xs-"+scaleLabel).attr("gv-alignment-col",scaleLabel);
                $(el).find("div.controls").addClass("col-xs-"+scaleComponent).attr("gv-alignment-col",scaleComponent);
                break;
            case "Left":
                $(el).find("label:first").addClass("control-label").addClass("control-label-left").addClass("col-xs-"+scaleLabel).attr("gv-alignment-col",scaleLabel);
                $(el).find("div.controls").addClass("col-xs-"+scaleComponent).attr("gv-alignment-col",scaleComponent);
        }
        return el
    }

    /**
     * tab页 布局 处理
     */
    function layoutPropertiesChange(){
        var alignment,scaleLabel,scaleComponent;
        $('input[data-gv-property="alignment"]', fieldProperties).kendoDropDownList({
            dataSource: [{text:"顶部对齐（Top）",value:"Top"},{text:"左对齐（Left）",value:"Left"},{text:"右对齐（Right）",value:"Right"}],
            value:"Left",
            dataTextField:"text",
            dataValueField:"value",
            change:function(){
                alignment = $('input[data-gv-property="alignment"]', currentFieldProperties).data("kendoDropDownList");
                scaleLabel =  $('input[data-gv-property="scale-label"]', currentFieldProperties).data("kendoNumericTextBox");
                scaleComponent = $('input[data-gv-property="scale-component"]', currentFieldProperties).data("kendoNumericTextBox");
                if(this.value()=="Top"){
                    scaleLabel.enable(false);
                    scaleComponent.enable(false);
                }else{
                    scaleLabel.enable(true);
                    scaleComponent.enable(true);
                }

                var options = {
                    alignment:alignment.value(),
                    scaleLabel:scaleLabel.value(),
                    scaleComponent:scaleComponent.value()
                }
                componentLayout(selectedComponent,options);

            }
        });

        $('input[data-gv-property="scale-label"]', fieldProperties).kendoNumericTextBox({
            max:12,
            min:0,
            step:1,
            value:3,
            format:"n0",
            change:function(){
                alignment = $('input[data-gv-property="alignment"]', currentFieldProperties).data("kendoDropDownList");
                scaleLabel =  $('input[data-gv-property="scale-label"]', currentFieldProperties).data("kendoNumericTextBox");
                scaleComponent = $('input[data-gv-property="scale-component"]', currentFieldProperties).data("kendoNumericTextBox");
                var value = this.value();
                if(!value){
                    value = 0;
                }
                var scaleValue = scaleComponent.value();
                if(scaleValue+value > 12){
                    scaleComponent.value(12-value);
                }
                scaleComponent.max(12-value);
                var options = {
                    alignment:alignment.value(),
                    scaleLabel:scaleLabel.value(),
                    scaleComponent:scaleComponent.value()
                }
                componentLayout(selectedComponent,options);
            }
        });

        $('input[data-gv-property="scale-component"]', fieldProperties).kendoNumericTextBox({
            max:12,
            min:0,
            step:1,
            value:9,
            format:"n0",
            change:function(){
                alignment = $('input[data-gv-property="alignment"]', currentFieldProperties).data("kendoDropDownList");
                scaleLabel =  $('input[data-gv-property="scale-label"]', currentFieldProperties).data("kendoNumericTextBox");
                scaleComponent = $('input[data-gv-property="scale-component"]', currentFieldProperties).data("kendoNumericTextBox");
                var value = this.value();
                if(!value){
                    value = 0;
                }
                var scaleValue = scaleLabel.value();
                if(scaleValue+value > 12){
                    scaleLabel.value(12-value);
                }
                scaleLabel.max(12-value);
                var options = {
                    alignment:alignment.value(),
                    scaleLabel:scaleLabel.value(),
                    scaleComponent:scaleComponent.value()
                }
                componentLayout(selectedComponent,options);

            }
        });


        $('textarea[data-gv-property="label-css"]', fieldProperties).bind("change",function(){
            var value = $(this).val();
            var labelElement = $(selectedComponent).find("label:first")[0];
            labelElement.style.cssText = value;
        })

        $('textarea[ data-gv-property="component-css"]', fieldProperties).bind("change",function(){
            var value = $(this).val();
            var componentElement = getComponentElement(designer.selectedControlType)[0];
            componentElement.style.cssText = value;
        })
    }

    /**
     *tab页  属性 处理
     *
     */
    function propertiesChange(){

        $('input[data-gv-property="disabled"]',fieldProperties).kendoCheckbox({
            checkedValue: 'Y',
            label: "禁用",
            uncheckedValue: 'N',
            change:function(e){
                var disabled = this.value()=='Y' ? true:false;
                var component = getComponentElement(designer.selectedControlType);
                component.prop("disabled",disabled);
                component.toggleClass("k-state-disabled");
            }
        });


        $('input[data-gv-property="required"]',fieldProperties).kendoCheckbox({
            checkedValue: 'Y',
            label: "必输",
            uncheckedValue: 'N',
            change:function(e){
                var required = this.value()=='Y' ? true:false;
                var component = getComponentElement(designer.selectedControlType);
                component.prop("required",required);
            }
        });



        $('input[data-gv-property="label"]', fieldProperties).change(function () {
            var value = $(this).val(),
                label = $(selectedComponent).find("label:first");
            label.text(value);
            var componentElement = getComponentElement(designer.selectedControlType);
            componentElement.attr("data-label",value);
        });

        $('input[data-gv-property="placeholder"]', fieldProperties).change(function () {
            var value = $(this).val();
            var componentElement = getComponentElement(designer.selectedControlType);
            componentElement.attr("placeholder", value);

        });

        $('input[data-gv-property="sublabel"]', fieldProperties).change(function () {
            var n = $(this).val(), i;
            n == "" ? $(".help-block", selectedComponent).remove() : (i = $(".help-block", selectedComponent),
                i.length == 0 ? $('<span class="help-block">' + n + "<\/span>").appendTo($(".controls", selectedComponent)) : $(i).text(n))
        });

        $('input[data-gv-property="control-name"]', fieldProperties).change(function () {
            var value = $(this).val(),element = getComponentElement(designer.selectedControlType);
            element.attr("name", value);
        });

        $('input[data-gv-property="control-id"]', fieldProperties).change(function () {
            var value = $(this).val(),element = getComponentElement(designer.selectedControlType);
            element.attr("id", value);
            $("label", selectedComponent).attr("for", value);
        });

        $('input[data-gv-property="valueprimitive"]', fieldProperties).kendoDropDownList({
            dataSource:["true","false"],
            change:function (e) {
                var value = this.value();
                var componentElement = getComponentElement(designer.selectedControlType);
                componentElement.attr("data-value-primitive", value);
            }
        });

    }

    function dataBindChangeStr(str,eName,value){
        var arr = str.split(",") || [],result,flag = false;
        for(var i=0;i<arr.length;i++){
            var key = arr[i].split(":")[0];
            if(key == eName)
            {
                flag = true;
                if(!value){
                    arr.splice(i--,1);
                }else{
                    arr[i] = eName+":"+value;
                }
                break;
            }
        }
        if(flag){
            result = arr.join(",");
        }else{
            result = str + "," +eName+":"+value;
        }
        return result;
    }

    function dataBindChange(eName,value,isEvent,inputElement){
        var dataBind = inputElement.attr("data-bind"),str;
        if(dataBind){
            var keyValueRegExp = /[A-Za-z0-9_\-]+:(\{([^}]*)\}|[^,}]+)/g;
            var arr = dataBind.match(keyValueRegExp) || [],flag=false;
            if(!isEvent){
                str = dataBindChangeStr(dataBind,eName,value);
            }else{
                for(var i=0;i<arr.length;i++){
                    var a = arr[i].split(":"),start,end,result;
                    var key = arr[i].split(":")[0];
                    if(key == "events")
                    {
                        flag = true;
                        start = arr[i].indexOf("{")+1;
                        end = arr[i].indexOf("}");
                        result = dataBindChangeStr(arr[i].substring(start,end),eName,value);
                        if(!result){
                            arr.splice(i--,1);
                        }else{
                            arr[i] ="events:{"+ result+"}";
                        }
                    }
                }
                if(flag){
                    str = arr.join(",");
                }else{
                    str = dataBind + ",events:{" +eName+":"+value+ "}";
                }
            }
        }else{
            if(!isEvent){
                str = eName+":"+value;
            }else{
                str = "events:{"+eName+":"+value+"}";
            }
        }
        return str;
    }

    function bindEventsChange(inputElement){
        function bindInput(name,isEvent){
            var str = name;
            if(!isEvent && name){
                str = "bind"+name;
            }
            $("#" + designer.selectedControlType + 'properties input[data-gv-property="'+str+'"]', fieldProperties).unbind("input propertychange").bind("input propertychange",function () {
                var value = $(this).val()
                    , input = selectedComponent.find(inputElement.selector)
                    ,dataBind = dataBindChange(name,value,isEvent,input);

                if(dataBind){
                    input.attr("data-bind",dataBind);
                }else{
                    input.removeAttr("data-bind");
                }
            });
        }
        bindInput("enabled");
        bindInput("source");
        bindInput("value");
        bindInput("text");
        bindInput("click");
        bindInput("change",true);
        bindInput("select",true);
        bindInput("open",true);
        bindInput("close",true);
        bindInput("query",true);
    }

    function initBindEvents(inputElement){
        var controlType = designer.selectedControlType;
        $("#" + controlType + 'properties input[data-gv-property="bindenabled"]', fieldProperties).val('');
        $("#" + controlType + 'properties input[data-gv-property="bindsource"]', fieldProperties).val('');
        $("#" + controlType + 'properties input[data-gv-property="bindvalue"]', fieldProperties).val('');
        $("#" + controlType + 'properties input[data-gv-property="bindtext"]', fieldProperties).val('');
        $("#" + controlType + 'properties input[data-gv-property="change"]', fieldProperties).val('');
        $("#" + controlType + 'properties input[data-gv-property="select"]', fieldProperties).val('');
        $("#" + controlType + 'properties input[data-gv-property="open"]', fieldProperties).val('');
        $("#" + controlType + 'properties input[data-gv-property="close"]', fieldProperties).val('');
        $("#" + controlType + 'properties input[data-gv-property="bindclick"]', fieldProperties).val('');
        $("#" + controlType + 'properties input[data-gv-property="query"]', fieldProperties).val('');

        if(inputElement.length > 0){
            var dataBind = inputElement.attr("data-bind");
            if(dataBind){
                var keyValueRegExp = /[A-Za-z0-9_\-]+:(\{([^}]*)\}|[^,}]+)/g;
                var arr = dataBind.match(keyValueRegExp)|| [];
                $.each(arr,function(i,v){
                    if(v.split(":")[0] == "events"){
                        var start,end,results,key,value;
                        start = v.indexOf("{")+1;
                        end = v.indexOf("}");
                        results = v.substring(start,end).split(",")||[];
                        $.each(results,function(j,result){
                            key = result.split(":")[0];
                            value = result.split(":")[1];
                            $("#" + controlType + 'properties input[data-gv-property="'+key+'"]', fieldProperties).val(value);
                        });
                    }else{
                        var key = "bind"+ v.split(":")[0];
                        var value = v.split(":")[1];
                        $("#" + controlType + 'properties input[data-gv-property="'+key+'"]', fieldProperties).val(value);
                    }
                });
            }
        }
        bindEventsChange(inputElement);
    }

    /**
     * 组件属性改变的事件绑定
     *
     */
    function initPropertiesEvent() {

        propertiesChange();
        layoutPropertiesChange();

        /**
         * checkbox radio之类的排序
         * 未实现
         */
        function optionsReorder() {
            var selectedValue = $("option:selected", selectedComponent).val();
            $("select", selectedComponent).children().remove();
            $("#__selectOptions > li").each(function () {
                var n = $('input[type="text"]', this).val();
                $("select", selectedComponent).append($('<option value="' + n + '">' + n + "<\/option>"))
            });
            $("select", selectedComponent).val(selectedValue).attr("selected", true);
        }

        $('#saveFormComfirm').kendoWindow({
            width: 500,
            modal: true,
            title: "保存"
        });

        $("#saveFormSave").click(function () {
            designer.saveData($('#saveFormComfirm').data("kendoWindow").status);
            $('#saveFormComfirm').data("kendoWindow").close();
        });

        $("#saveFormCancel").click(function () {
            $('#saveFormComfirm').data("kendoWindow").close();
        })


        /**
         * tab页的
         */
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
                if (currentProperties.controlType == "select") {
                    currentValue = $("option:selected", selectedComponent).val();
                    optionsReorder();
                    $('#__selectOptions input[type="text"]').each(function () {
                        if (this.value == currentValue) {
                            // $(this).siblings(".input-group-addon").attr("checked", true)
                            $(":input[type='radio']", $(this).prev()).prop("checked", true);
                        }
                    });
                }

                if (currentProperties.controlType == "checkbox") {
                    var canvasOptions = $(".controls", selectedComponent)
                        , html = $("<div><\/div>")
                        ,
                        propertyOptions = $("#fieldproperties #" + currentProperties.controlType + "properties  #__checkboxOptions")
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
                    spread();
                }

                if (currentProperties.controlType == "radio") {
                    var canvasOptions = $(".controls", selectedComponent)
                        , html = $("<div><\/div>")
                        ,
                        propertyOptions = $("#fieldproperties #" + currentProperties.controlType + "properties  #__radioItems")
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
                            $('input[type="radio"]', this).prop("checked", true)
                        }
                    });
                    a = $("#fieldproperties #allowother:checked");
                    if (a.length > 0) {
                        html.append($('.controls input[type="radio"]:last', selectedComponent).parent("div").clone());
                    }
                    canvasOptions.children().remove();
                    html.children().each(function () {
                        canvasOptions.append($(this).clone())
                    });
                    spread();
                }

            }
        });

    }




    /**
     * 获得当前column的比例
     * @param el
     * @returns {number}
     */
    function getColNumber(el) {
        for (var i = 1; i <= 12; i++)
            if ($(el).hasClass("col-xs-" + i))
                return i;
        return "";
    }



    /**
     * 加载保存后的数据
     */
    designer.loadData = function (){
        if(window.code != '0'){
            var url = _basePath + "/sys/form/builder/query?code="+window.code;
            $.ajax(url,{
                type: "POST",
                dataType: "json",
                contentType: "application/json",
                async:false,
                success:function (data) {
                    if(data.success){
                        form = data.rows[0];
                        if(form && form.content){
                            $("#design-canvas").children().remove();
                            var count;

                            $("#design-canvas")[0].innerHTML = form.content;
                            $("#design-canvas")[0].innerHTML = $("#formWrapper")[0].innerHTML
                            count = $("#controlCount").attr("data-control-count")
                            $("#design-canvas").attr("data-control-count",count);
                            $("#controlCount").remove();

                        };
                        $("#formDescriptionCode span:first").text(form.description);
                        $("#formDescriptionCode span:eq(1)").text(" "+form.code);
                    }else {
                        Jrap.resolveError(data);
                    }
                }
            })
        }
    }

    /**
     * 保存数据
     * @param n
     */


    designer.saveData = function(status) {

        var idNull = "";
         $("[data-role='checkbox']",$("#design-canvas")).each(function () {
            if(!$(this).attr("id")){
                idNull =idNull +$(this).attr("data-label")+"、";
            }
         })
         if(idNull != ""){
            idNull=idNull.substring(0,idNull.length-1);
             return kendo.ui.showErrorDialog({
                message: "checkbox:"+idNull+' ID 不能为空!'
            })
         }

        var formActiveTabSelector = $("#form-tabs li.active a").attr("href");
        if(formActiveTabSelector == "#form-tabs-design"){
            designer.handleComponentCode();
        }
        var r = $("#design-canvas").clone(false, false);
        $(".hasFocus",r).removeClass("hasFocus");
        var datas = [];

        if($("#design-canvas").find("div[data-role='tab']").length &&  !$(r).find("script[name='tabScript']").length){
            var bstr = $('<script name="tabScript" src="${base.contextPath}/lib/form-builder/js/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>');
            $(r).append(bstr);
        }

        if(status == "saveAs"){
            var saveAsFormData = {};
            saveAsFormData.__status='add';
            saveAsFormData.code = $("input[name=formCode]").val();
            saveAsFormData.description = $("input[name=formDescription]").val();
            saveAsFormData.content = $(r).html();
            datas.push(saveAsFormData);
        }else{
            if(!form.formId){
                form.__status='add';
            }else{
                form.__status='update';
            }
            form.content = $(r).html();
            datas.push(form);
        }


        if(formActiveTabSelector == "#form-tabs-code"){
            datas[0].content = window.editor.getValue();
        }

        var count = $(r).attr("data-control-count");
        datas[0].content = "<div id='formWrapper' class='form-horizontal col-xs-12'>"+datas[0].content+"<div id='controlCount' data-control-count='"+count+"'></div></div><script>Jrap.initForm('#formWrapper');</script>";

        window.isSubmiting = true;
        if(status == "preview"){
            preview();
        }

        $.ajax({
            type: 'POST',
            url:  _basePath +  "/sys/form/builder/submit",
            data: kendo.stringify(datas),
            contentType: "application/json",
            success: function (data) {
                window.isSubmiting = false;
                if(data.success){
                    Jrap.showToast({
                        type:'success',
                        message: $l('jrap.tip.success')
                    });
                    if(status != "saveAs"){
                        if(window.opener && window.opener.dataSource){
                            window.opener.dataSource.fetch();
                        }
                        form =  data.rows[0];
                        window.code = form.code;
                        designer.initDesignCanvas();
                    }

                }else {
                    Jrap.resolveError(data);
                }
            }
        }).then(function(data){
            if(data.success && status != "saveAs"){
                if(window.location.search==''){
                    window.location.search = "?code="+form.code;
                }
                $("#formDescriptionCode span:first").text(form.description);
                $("#formDescriptionCode span:eq(1)").text(" "+form.code);
            }
        });

    };

    $("#save").click(function () {
        if(!form.code){
            $('#saveFormComfirm').data('kendoWindow').status = 'save';
            $('#saveFormComfirm').data('kendoWindow').title("保存");
            $('#saveFormComfirm').data('kendoWindow').center().open();
        }else{
            designer.saveData('save');
        }
    });

    $("#saveAs").click(function () {
        $("input[name=formCode]").val("");
        $("input[name=formDescription]").val("");
        $('#saveFormComfirm').data('kendoWindow').status = 'saveAs';
        $('#saveFormComfirm').data('kendoWindow').title("另存为");
        $('#saveFormComfirm').data('kendoWindow').center().open();
    });



    $("#preview").click(function () {

        if(window.code == '0'){
            $('#saveFormComfirm').data('kendoWindow').status = 'preview';
            $('#saveFormComfirm').data('kendoWindow').title("保存");
            $('#saveFormComfirm').data('kendoWindow').center().open();
        }else{
            gvDesigner.saveData('preview');
        }

    })
    /**
     * 删除选中的数据
     */
    $("#removeMenu").click(function () {
        var el = $("#design-canvas .hasFocus"), type = designer.selectedControlType;
        if (type == "column") {
            onColRemove(el);
        } else if (type == "tab") {
            $(el).remove();
        } else if (type == "pagebreak") {
            // $(el).parent().remove();
            // $(".gv-page", $("#design-canvas")).length == 0 && $("#lastPage").remove(),
            //     showPager()
        } else if (type == "panel") {
            $(el).remove()
        } else {
            //$(el).hide("slow", function () {
            $(el).remove()
            //})
        }
    });

    designer.handleComponentCode = function() {
        function replace(roleName,componentName){
            var component = $("#design-canvas").find("[data-role="+roleName+"]");
            $.each(component,function(i,v){
                var template = $(v).clone(),wrapper = $(v).data(componentName).wrapper;
                var label = $(v).parents(".form-group:first").find("label:first");
                if(roleName == "radio"){
                    template.empty();
                    $(v).replaceWith(template);
                }else if(roleName == "checkbox"){
                    wrapper.next().remove();
                    wrapper.replaceWith(template);
                }else if($.inArray(roleName,["timepicker","datetimepicker","datepicker","tledit"]) != -1){
                    label.attr("for",$(v).attr("id")||"");
                    template.removeClass("k-input");
                    wrapper.replaceWith(template);
                }else{
                    label.attr("for",$(v).attr("id")||"");
                    wrapper.replaceWith(template);
                }

            })
        }
        replace("dropdownlist","kendoDropDownList");
        replace("checkbox","kendoCheckbox");
        replace("radio","kendoRadio");
        replace("numerictextbox","kendoNumericTextBox");
        replace("datepicker","kendoDatePicker");
        replace("datetimepicker","kendoDateTimePicker");
        replace("timepicker","kendoTimePicker");
        replace("lov","kendoLov");
        replace("combobox","kendoComboBox");
        replace("tledit","kendoTLEdit");
        /* $("#design-canvas").find(".k-tooltip.k-tooltip-validation").remove();*/
    }


    /**
     * 整个设计器的初始化函数
     * @constructor
     */
    designer.Initialize = function () {
        var u = $("#designform"), formName;
        $("[rel=tooltip]").tooltip();
        $("[rel=popover]").popover();
        if(window.opener && window.opener.dataSource){
            var dataSource = window.opener.dataSource;
            var requestEnd = function(e) {
                var response = e.response;
                var type = e.type;
                if(type == "update"){
                    if(form.code == response.rows[0].code){
                        form = response.rows[0];
                        $("#formDescriptionCode span:first").text(form.description);
                    }
                }
            }
            dataSource.bind("requestEnd",requestEnd);
        }

        $("#design-canvas").find("script[name='tabScript']").remove();
        $("#design-canvas").click(function () {
            selectCount = 0
        });

        $("#fieldproperties").children().fadeOut();

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
                    var r = generatePlaceholder(componentId, ui);
                    ui.placeholder.html($(r).html())
                } else {
                    ui.placeholder.height("25")
                }

            },
            stop: function (event, ui) {
                if (dragging == true)
                    switch (componentId) {
                        case "menu1Column":
                        case "menu2Columns":
                        case "menu3Columns":
                        case "menu4Columns":
                        case "twoandten":
                        case "custom":
                            createContainer(event, ui, componentId);
                            break;
                        case "_tab":
                            createTab(event, ui);
                            break;
                    }
                componentId = null;
                dragging = false
            }
        });


        $(".formControl").draggable({
            connectToSortable: ".gv-droppable-grid, .tab-canvas",
            cursor: "move",
            revert: false,
            helper: "clone",
            appendTo: "#design-canvas",
            placeholder: "formControl",
            start: function () {
                componentId = $(this).attr("id");
                dragging = true;
            },
            drag: function () {
            },
            stop: function () {

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
                dragging = true
            }
        });

        initPropertiesEvent();
        designer.initDesignCanvas();

    };

    designer.handleComponentShow =function(){
        /* 组件属性是否需要重新读取 */
    }

    /**
     * 组件的注册
     * @param name：组件名
     * @param options：
     */
    designer.register = function (name, options) {
        registeredComponent.push([name, options])
    };
    //------------------------ 组件注册 --------------------------------

    designer.register("text", function () {
        var textComponent = {};

        textComponent.initPropertyPanel = function (ele) {
            $('input[data-gv-property="value"]', currentFieldProperties).change(function () {
                var value = $(this).val(),
                    element = $(selectedComponent).find('.controls > input[type="text"]:first');
                element.attr("value",value);
            });
        };

        textComponent.placeHolder = function (ele) {
            var template = $("#TextTemplate").clone();
            $(ele).find(".controls").append(template);
            $(ele).find("label:first").text("Text");
            return ele;
        };

        textComponent.add = function (ele, id) {
            var template = $("#TextTemplate").clone();
            $(ele).find(".controls").append(template);
            $(template).attr("id", id);
            $(ele).find("label:first").attr("for", id);
            $(ele).find("label:first").text("Text");
            template.attr("data-label","Text");
            return template;
        };

        return textComponent;
    }());

    designer.register("dropdownlist", function () {
        var dropDownComponent = {};
        dropDownComponent.initPropertyPanel = function (ele) {

            var input = $(selectedComponent).find('.controls input[data-role="dropdownlist"]:first');
            var dropDownList = $(selectedComponent).find('.controls input[data-role="dropdownlist"]:first').data("kendoDropDownList");


            $('input[data-gv-property="value"]', currentFieldProperties).val(input.attr("data-value"));
            $('input[data-gv-property="value"]', currentFieldProperties).change(function () {
                var value = $(this).val();
                var input = $(selectedComponent).find('.controls input[data-role="dropdownlist"]:first');
                input.attr("data-value",value);
            });

            $('input[data-gv-property="text"]', currentFieldProperties).val(input.attr("data-text"));
            $('input[data-gv-property="text"]', currentFieldProperties).change(function () {
                var value = $(this).val();
                var input = $(selectedComponent).find('.controls input[data-role="dropdownlist"]:first');
                input.attr("data-text",value);
            });

            $('input[data-gv-property="valueprimitive"]', currentFieldProperties).val(input.attr("data-value-primitive"));
            $('input[data-gv-property="valueprimitive"]', currentFieldProperties).change(function () {
                var value = $(this).val();
                var input = $(selectedComponent).find('.controls input[data-role="dropdownlist"]:first');
                input.attr("data-value-primitive",value);
            });

            $('input[data-gv-property="datatextfield"]', currentFieldProperties).val(input.attr("data-text-field"));
            $('input[data-gv-property="datatextfield"]', currentFieldProperties).change(function () {
                var value = $(this).val();
                var input = $(selectedComponent).find('.controls input[data-role="dropdownlist"]:first');
                input.attr("data-text-field",value);
            });

            $('input[data-gv-property="datavaluefield"]', currentFieldProperties).val(input.attr("data-value-field"));
            $('input[data-gv-property="datavaluefield"]', currentFieldProperties).change(function () {
                var value = $(this).val();
                var input = $(selectedComponent).find('.controls input[data-role="dropdownlist"]:first');
                input.attr("data-value-field",value);
            });


            $('input[data-gv-property="optionlabel"]', currentFieldProperties).val(input.attr("data-option-label"));
            $('input[data-gv-property="optionlabel"]', currentFieldProperties).change(function () {
                var value = $(this).val();
                var input = $(selectedComponent).find('.controls input[data-role="dropdownlist"]:first');
                input.attr("data-option-label",value);
            });
        };

        dropDownComponent.placeHolder = function (ele) {
            var template = $("#DropDownListTemplate").clone();
            $(ele).find(".controls").append(template);
            $(ele).find("label:first").text("DropDown");
            kendo.init(template);
            return ele;
        };

        dropDownComponent.add = function (ele, id){
            var template = $("#DropDownListTemplate").clone();
            $(ele).find(".controls").append(template);
            template.attr("id", id);
            $(ele).find("label:first").attr("for", id);
            $(ele).find("label:first").text("DropDown");
            template.attr("data-label","DropDown");
            kendo.init(template);
            return template;
        };

        return dropDownComponent;
    }())

    designer.register("combobox", function () {
        var comboBoxComponent = {};
        comboBoxComponent.initPropertyPanel = function (ele) {

            var input = $(selectedComponent).find('.controls input[data-role="combobox"]:first');
            var comboBox = $(selectedComponent).find('.controls input[data-role="combobox"]:first').data("kendoComboBox");


            $('input[data-gv-property="value"]', currentFieldProperties).val(input.attr("data-value"));
            $('input[data-gv-property="value"]', currentFieldProperties).change(function () {
                var value = $(this).val();
                var input = $(selectedComponent).find('.controls input[data-role="combobox"]:first');
                input.attr("data-value",value);
            });

            $('input[data-gv-property="text"]', currentFieldProperties).val(input.attr("data-text"));
            $('input[data-gv-property="text"]', currentFieldProperties).change(function () {
                var value = $(this).val();
                var input = $(selectedComponent).find('.controls input[data-role="combobox"]:first');
                input.attr("data-text",value);
            });

            $('input[data-gv-property="valueprimitive"]', currentFieldProperties).val(input.attr("data-value-primitive"));
            $('input[data-gv-property="valueprimitive"]', currentFieldProperties).change(function () {
                var value = $(this).val();
                var input = $(selectedComponent).find('.controls input[data-role="combobox"]:first');
                input.attr("data-value-primitive",value);
            });

            $('input[data-gv-property="datatextfield"]', currentFieldProperties).val(input.attr("data-text-field"));
            $('input[data-gv-property="datatextfield"]', currentFieldProperties).change(function () {
                var value = $(this).val();
                var input = $(selectedComponent).find('.controls input[data-role="combobox"]:first');
                input.attr("data-text-field",value);
            });

            $('input[data-gv-property="datavaluefield"]', currentFieldProperties).val(input.attr("data-value-field"));
            $('input[data-gv-property="datavaluefield"]', currentFieldProperties).change(function () {
                var value = $(this).val();
                var input = $(selectedComponent).find('.controls input[data-role="combobox"]:first');
                input.attr("data-value-field",value);
            });


            $('input[data-gv-property="optionlabel"]', currentFieldProperties).val(input.attr("data-option-label"));
            $('input[data-gv-property="optionlabel"]', currentFieldProperties).change(function () {
                var value = $(this).val();
                var input = $(selectedComponent).find('.controls input[data-role="combobox"]:first');
                input.attr("data-option-label",value);
            });
        };

        comboBoxComponent.placeHolder = function (ele) {
            var template = $("#ComboBoxTemplate").clone();
            $(ele).find(".controls").append(template);
            $(ele).find("label:first").text("ComboBox");
            kendo.init(template);
            return ele;
        };

        comboBoxComponent.add = function (ele, id){
            var template = $("#ComboBoxTemplate").clone();
            $(ele).find(".controls").append(template);
            template.attr("id", id);
            $(ele).find("label:first").attr("for", id);
            $(ele).find("label:first").text("ComboBox");
            template.attr("data-label","ComboBox");
            kendo.init(template);
            return template;
        };

        return comboBoxComponent;
    }())

    designer.register("numerictextbox", function () {
        var numericComponent = {}, minvalue, maxValue, defaultValue;

        numericComponent.initPropertyPanel = function (ele, propertyEle) {
            var numeric = $('input[data-role="numerictextbox"]', selectedComponent)
                ,format = numeric.attr("format")||""
                , numMinValue
                ,numMaxValue
                ,numDefaultValue;
            $("#numdefaultvalue").val("");
            $("#nummaxvalue").val("");
            $("#numminvalue").val("");
            numDefaultValue = $("#numdefaultvalue", currentFieldProperties).kendoNumericTextBox({
                format:format,
                value:numeric.attr("value"),
                change:function(){
                    var value = this.value()
                        , numeric = $('input[data-role="numerictextbox"]', selectedComponent);
                    numeric.attr("value", value);
                }
            }).data("kendoNumericTextBox");

            numMaxValue = $("#nummaxvalue", currentFieldProperties).kendoNumericTextBox({
                format:format,
                value:numeric.attr("max"),
                change:function(){
                    var value = this.value()
                        , numeric = $('input[data-role="numerictextbox"]', selectedComponent);
                    numeric.attr("max", value);
                    numMinValue.max(value);
                    numDefaultValue.max(value);
                }
            }).data("kendoNumericTextBox");

            numMinValue = $("#numminvalue", currentFieldProperties).kendoNumericTextBox({
                format:format,
                value:numeric.attr("min"),
                change:function(){
                    var value = this.value()
                        , numeric = $('input[data-role="numerictextbox"]', selectedComponent);
                    numeric.attr("min", value);
                    numDefaultValue.min(value);
                    numMaxValue.min(value);
                }
            }).data("kendoNumericTextBox");


            $('input[data-gv-property="format"]', currentFieldProperties).val(numeric.attr("data-format"));
            $('input[data-gv-property="format"]', currentFieldProperties).change(function () {
                var value = $(this).val(),
                    numeric = $('input[data-role="numerictextbox"]', selectedComponent);
                numeric.attr("data-format",value);
            });
        };

        numericComponent.add = function (ele, id) {
            var template, error,width;
            template = $("#NumericTextBoxTemplate").clone();
            $(".controls", ele).append(template);
            template.attr("id", id);
            width = template[0].style.width;
            $(ele).find("label:first").attr("for", id);
            $(ele).find("label:first").text("Numeric");
            template.attr("data-label","Numeric");
            kendo.init(template);
            template[0].style.width = width;
            return template;
        };


        numericComponent.placeHolder = function (ele) {
            var template = $("#NumericTextBoxTemplate").clone();
            $(".controls", ele).append(template);
            $(ele).find("label:first").text("Numeric");
            kendo.init(template);
            return ele;
        };


        return numericComponent;
    }())

    designer.register("textarea", function () {

        var textareaComponent = {};

        $('input[data-gv-property="textareadisabled"]',fieldProperties).kendoCheckbox({
            checkedValue: 'Y',
            label: "禁用",
            uncheckedValue: 'N',
            change:function(e){
                var disabled = this.value()=='Y' ? true:false;
                $(selectedComponent).find('textarea:first').prop("disabled",disabled);
            }
        });

        $('input[data-gv-property="textarearequired"]',fieldProperties).kendoCheckbox({
            checkedValue: 'Y',
            label: "必输",
            uncheckedValue: 'N',
            change:function(e){
                var required = this.value()=='Y' ? true:false;
                $(selectedComponent).find('textarea:first').prop("required",required);
            }
        });

        textareaComponent.initPropertyPanel = function (ele) {

            $('input[data-gv-property="value"]', currentFieldProperties).change(function () {
                var value = $(this).val(),
                    element = $(selectedComponent).find(".controls > textarea");
                element.html(value);
            });

        }

        textareaComponent.add = function (ele, id) {
            var template = $("#TextAreaTemplate").clone();
            $(ele).find(".controls").append(template);
            template.attr("id", id);
            $(ele).find("label:first").attr("for", id);
            $(ele).find("label:first").text("TextArea");
            template.attr("data-label","TextArea");
            return template;
        };

        textareaComponent.placeHolder = function (ele) {
            var template = $("#TextAreaTemplate").clone();
            $(ele).find(".controls").append(template);
            $(ele).find("label:first").text("TextArea");
            return ele;
        };
        return textareaComponent;
    }())

    designer.register("radio", function () {
        var radioComponent = {};

        var isDisable =  $("#radiodisabled",currentFieldProperties).kendoCheckbox({
            checkedValue: 'Y',
            label: "禁用",
            uncheckedValue: 'N',
            change:function(e){
                var disabled = this.value()=='Y' ? true:false
                    ,radio = $(selectedComponent).find('div[data-role="radio"]');
                radio.attr("disabled",disabled);
                radio.data("kendoRadio").enable(!disabled);
            }
        }).data("kendoCheckbox");

        radioComponent.initPropertyPanel = function (ele, propertyEle) {



            var radio = $("div[data-role='radio']",selectedComponent).data("kendoRadio")
                ,radioDiv = $("div[data-role='radio']",selectedComponent);
            if(radioDiv.attr("disabled") == "disabled" || radioDiv.attr("disabled") == true){
                isDisable.value("Y")
            }else{
                isDisable.value("N")
            }

            function radioLabelKeyup(e) {
                var index = $(e).closest("li").index()
                    , value = $(e).val()
                    , radio = $("div[data-role='radio']",selectedComponent).data("kendoRadio")
                    ,items = radio.options.items
                    ,new_item = "["
                    ,radioDiv = $("div[data-role='radio']",selectedComponent);
                items.map(function (v,i) {
                    if(index == i){
                        v.label = value;
                        new_item =new_item+"{label:'"+value+"',value:'"+v.value+"'},"
                    }else {
                        new_item =new_item+"{label:'"+v.label+"',value:'"+v.value+"'},"
                    }
                })
                new_item =new_item+"]";
                radioDiv.attr("data-items",new_item);
                radioDiv.children().remove();
                if(radio){
                    radio.destroy();
                }
                kendo.init($("div[data-role='radio']",selectedComponent));

            }

            function radioValueKeyup(e) {
                var index = $(e).closest("li").index()
                    , value = $(e).val()
                    ,radio = $("div[data-role='radio']",selectedComponent).data("kendoRadio")
                    ,radioDiv = $("div[data-role='radio']",selectedComponent)
                    ,items = radio.options.items
                    ,new_item = "[";
                items.map(function (v,i) {
                    if(index == i){
                        new_item =new_item+"{label:'"+v.label+"',value:'"+value+"'},"
                    }else {
                        new_item =new_item+"{label:'"+v.label+"',value:'"+v.value+"'},"
                    }
                })
                new_item =new_item+"]";
                radioDiv.attr("data-items",new_item);
                radioDiv.children().remove();
                if(radio){
                    radio.destroy();
                }
                kendo.init($("div[data-role='radio']",selectedComponent));

            }

            function addRadio(e) {
                var parent = $(e).closest("li"), dataId = "", radioName, index = parent.index(),
                    itemTemplate
                    ,radio = $("div[data-role='radio']",selectedComponent).data("kendoRadio")
                    ,radioDiv = $("div[data-role='radio']",selectedComponent)
                    ,items = radio.options.items
                    ,new_item = "["
                    ,value = "&nbsp;";
                items.splice(index+1,0,{label:"",value:""});
                items.map(function (v,i) {
                    new_item =new_item+"{label:'"+v.label+"',value:'"+v.value+"'},"
                })
                new_item =new_item+"]";
                radioDiv.attr("data-items",new_item);
                radioDiv.children().remove();
                if(radio){
                    radio.destroy();
                }
                kendo.init($("div[data-role='radio']",selectedComponent));
                itemTemplate = $('<li id="' + dataId + '"><div class="input-group" style="display: flex"><div style="float:left;margin-bottom: 5px;width: 100%;"><input class="k-textbox" name="rdslabel" value="" type="text" /><input class="k-textbox" name="rdsvalue" value="" type="text" /></div><span style="float:left;margin: auto" class="input-group-addon"><span class="icon-add"><i class="fa fa-plus-square-o" aria-hidden="true"><\/i><\/span><span class="icon-delete"><i class="fa fa-minus-square-o" aria-hidden="true"><\/i> <\/span><\/span><\/div><\/li>');
                itemTemplate.insertAfter($(parent));
                $("input[name='rdsvalue']", itemTemplate).fadeOut(0);
                $('input[name="rdslabel"]', radioItems).change(function () {
                    radioLabelKeyup(this)
                });
                $('input[name="rdsvalue"]', radioItems).change(function () {
                    radioValueKeyup(this)
                });

                $("span.icon-add", itemTemplate).click(function () {
                    addRadio(this)
                });
                $("span.icon-delete", itemTemplate).click(function () {
                    var deleteConfirm = confirm("确定删除？");
                    if(deleteConfirm == true){
                        deleteRadio(this)
                    }
                });
                spread()
            }

            function deleteRadio(e) {
                var parent = $(e).closest("li")
                    , index = parent.index()
                    ,radio = $("div[data-role='radio']",selectedComponent).data("kendoRadio")
                    ,radioDiv = $("div[data-role='radio']",selectedComponent)
                    ,items = radio.options.items
                    ,new_item = "["
                    ,value = "&nbsp;";
                if (parent.siblings().length > 0) {
                    $(parent).remove();
                }
                items.splice(index,1);
                items.map(function (v,i) {
                    new_item =new_item+"{label:'"+v.label+"',value:'"+v.value+"'},"
                })
                new_item =new_item+"]";
                radioDiv.attr("data-items",new_item);
                radioDiv.children().remove();
                if(radio){
                    radio.destroy();
                }
                kendo.init($("div[data-role='radio']",selectedComponent));



                spread()
            }

            var radioItems, id, name, rdsItem, timeOut;
            radioItems = $("#__radioItems", currentFieldProperties);
            radioItems.children().remove();
            radio.options.items.map(function (v,i) {
                var label = v.label
                    ,value = v.value
                    ,template = $('<li><div class="input-group" style="display: flex"><div style="float:left;margin-bottom: 5px;width: 100%;"><input class="k-textbox" name="rdslabel" value="' + label + '" type="text" /><input class="k-textbox" name="rdsvalue" value="' + value + '" type="text" /></div><span style="float:left;margin: auto" class="input-group-addon"><span class="icon-add"><i class="fa fa-plus-square-o" aria-hidden="true"><\/i><\/span><span class="icon-delete"><i class="fa fa-minus-square-o" aria-hidden="true"><\/i> <\/span><\/span><\/div><\/li>');
                $(radioItems).append(template);

            })

            rdsItem = $("#rdsitem");
            $("input[name='rdsvalue']", rdsItem).fadeOut(0);
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

            $('input[name="rdslabel"]', radioItems).change(function () {
                radioLabelKeyup(this);
            });
            $('input[name="rdsvalue"]', radioItems).change(function () {
                radioValueKeyup(this);
            });
            $("span.icon-add", radioItems).click(function () {
                addRadio(this);
            });
            $("span.icon-delete", radioItems).click(function () {
                var deleteConfirm = confirm("确定删除？");
                if(deleteConfirm == true) {
                    deleteRadio(this);
                }
            });


            var controls = $(selectedComponent).children("div.controls").children("div"),layoutElement;
            if(!layoutElement){
                layoutElement = $('input[data-gv-property="layout"]', currentFieldProperties).kendoDropDownList({
                    dataSource:[{text:"1 列（One Column）",value:"vertical"},
                        {text:"2 列（Two Column）",value:"2"},
                        {text:"3 列（Three Column）",value:"3"},
                        {text:"4 列（Four Column）",value:"4"},
                        {text:"（内联）Inline",value:""}],
                    dataTextField:"text",
                    dataValueField:"value",
                    index:0,
                    change:function () {
                        var value = this.value()
                            , controls = $(selectedComponent).children("div.controls").children("div")
                            , radio = $(selectedComponent).find("div[data-role='radio']"),layoutElement;
                        $(controls).children("div").removeClass();
                        if(value == ""){
                            $(controls).children("div").addClass("radio-inline");
                        }else {
                            $(controls).children("div").addClass("col-xs-"+12/value);
                        }
                        if(value=="vertical"){
                            radio.attr("data-layout",value)
                        }else{
                            radio.removeAttr("data-layout");
                            radio.attr("data-cols",value);
                        }

                    }
                }).data("kendoDropDownList");
            }

            layoutElement.value("vertical");
            $(controls).children("div").hasClass("col-xs-6") && layoutElement.value("2");
            $(controls).children("div").hasClass("col-xs-4") && layoutElement.value("3");
            $(controls).children("div").hasClass("col-xs-3") &&layoutElement.value("4");
            $(controls).children("div").hasClass("radio-inline") && layoutElement.value("");



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
            template = $("#RadioTemplate").clone();
            $(ele).find("label:first").removeAttr("for");
            $(ele).find("label:first").text("Select a Choice");
            fielId = generateComponentName("field");
            $(".controls", ele).append(template.children());
            $("div[data-role='radio']", $(".controls", ele)).attr("id",fielId);
            kendo.init($("div[data-role='radio']", $(".controls", ele)));
            /* $(template).children().each(function () {
             id = generateComponentName("radio");
             cloneTemplate = $(this).clone();
             radio = $('input[type="radio"]', cloneTemplate);
             $(radio).attr("id", id);
             $(radio).attr("name", fielId);
             radio.parent().attr("for", id);
             $(".controls", ele).append(cloneTemplate);
             });*/
            return template;
        };


        radioComponent.placeHolder = function (ele) {
            var template = $("#RadioTemplate").clone();
            $(".controls", ele).append(template);
            kendo.init($("div[data-role='radio']",template));
            $(ele).find("label:first").text("Select an Option");
            return ele;
        };
        return radioComponent;
    }())

    designer.register("masktextbox", function () {
        var maskComponent = {};

        maskComponent.initPropertyPanel = function (ele, propertyEle) {
            var inputElement = $('input[data-role="maskedtextbox"]', selectedComponent),
                mask = inputElement.data("kendoMaskedTextBox");
            $('input[data-gv-property="value"]', currentFieldProperties).change(function () {
                var value = $(this).val(),
                    element = $(selectedComponent).find('.controls > input[type="text"]:first');
                mask.value(value);
                element.attr("data-value",value);
            });

            $('[data-gv-property="mask"]',currentFieldProperties).val(inputElement.attr("data-mask"));
            $('[data-gv-property="mask"]',currentFieldProperties).change(function () {
                var value = $(this).val(),inputElement = $('input[data-role="maskedtextbox"]', selectedComponent);
                mask = inputElement.data("kendoMaskedTextBox");
                mask.setOptions({
                    mask:value
                })
                inputElement.attr("data-mask",value);
            })

        };

        maskComponent.add = function (ele, id) {
            var template = $("#MaskTextBoxTemplate").clone();
            $(".controls", ele).append(template);
            template.attr("id", id);
            $(ele).find("label:first").attr("for", id);
            $(ele).find("label:first").text("Mask");
            template.attr("data-label","Mask");
            kendo.init(template);
            return template;
        };


        maskComponent.placeHolder = function (ele) {
            var template = $("#MaskTextBoxTemplate").clone();
            $(".controls", ele).append(template);
            $(ele).find("label:first").text("Mask");
            kendo.init(template);
            return ele;
        };

        return maskComponent;
    }())

    designer.register("lov", function () {
        var lov = {};
        lov.initPropertyPanel = function (ele) {
            var lov = $(selectedComponent).find('[data-role="lov"]').data("kendoLov");

            $('[data-gv-property="code"]',currentFieldProperties).change(function () {
                $("[data-role='lov']",selectedComponent).attr("data-code",this.value);
                lov.setLovCode(this.value);
            })
            $('[data-gv-property="code"]',currentFieldProperties).val(lov.options.code);

        };

        lov.placeHolder = function (ele) {
            var template = $("#LovTemplate").clone();
            $(".controls", ele).append(template);
            kendo.init($(template));
            $(ele).find("label:first").text("Lov");
            return ele;
        };

        lov.add = function (ele, id) {
            var template = $("#LovTemplate").clone();
            $(".controls", ele).append(template);
            $(template).attr("id", id);
            $(ele).find("label:first").attr("for", id);
            $(ele).find("label:first").text("LOV");
            template.attr("data-label","LOV");
            kendo.init($(template));
            return template;
        };

        return lov;
    }())

    designer.register("label", function () {
        var labelComponent = {};

        $('input[data-gv-property="labeltype"]',fieldProperties).kendoDropDownList({
            dataSource:[{
                'name':'标签-Label',
                'value':'label'
            },{
                'name':'标题-Header',
                'value':'header'
            },{
                'name':'链接-a',
                'value':'a'
            }],
            dataTextField:'name',
            dataValueField:'value',
            change:function(e){
                var value = this.value();
                var labelElement = $(".controls [data-control-type='label']",selectedComponent);
                $("#labelfor",fieldProperties).addClass('hidden');
                $("#headertype",fieldProperties).addClass('hidden');
                $("#alink",fieldProperties).addClass('hidden');

                if(value == 'label'){
                    $("#labelfor",fieldProperties).removeClass('hidden');
                    labelElement.replaceWith('<'+value+' data-control-type="label" >'+labelElement.text()+'</'+value+'>');
                    $('#labelfor').val(labelElement.attr("for")||'');
                }else if(value == 'header'){
                    $("#headertype",fieldProperties).removeClass('hidden');
                    var header = $('input[data-gv-property="headertype"]',fieldProperties).data("kendoDropDownList").value();
                    labelElement.replaceWith('<'+header+' data-control-type="label" >'+labelElement.text()+'</'+header+'>');
                }else{
                    $("#alink",fieldProperties).removeClass('hidden');
                    labelElement.replaceWith('<'+value+' data-control-type="label" >'+labelElement.text()+'</'+value+'>');
                    $('#alink').val(labelElement.attr("herf")||'');
                }
                $(".controls",selectedComponent).attr("data-type",value);

            }
        });

        $('input[data-gv-property="headertype"]',fieldProperties).kendoDropDownList({
            dataSource:[{
                name:"Header 1",
                value:"h1"
            },{
                name:"Header 2",
                value:"h2"
            },{
                name:"Header 3",
                value:"h3"
            },{
                name:"Header 4",
                value:"h4"
            },{
                name:"Header 5",
                value:"h5"
            }],
            dataTextField:"name",
            dataValueField:"value",
            change:function () {
                var labelElement = $(".controls [data-control-type='label']",selectedComponent);
                labelElement.replaceWith($("<" + this.value() + " data-control-type='label'>" + labelElement.text() + "<\/" + this.value() + ">"));
            }
        });


        $('input[data-gv-property="labelalignment"]',fieldProperties).kendoDropDownList({
            dataSource:[{
                name:'居左',
                value:'left'
            },{
                name:'居中',
                value:'center',
            },{
                name:'居右',
                value:'right'
            }],
            dataTextField:"name",
            dataValueField:"value",
            change:function () {
                var value = this.value();
                var labelElement =  $(".controls",selectedComponent);
                labelElement.css("text-align",value);
            }
        });

        labelComponent.initPropertyPanel = function (ele) {

            var labelElement =  $(".controls [data-control-type='label']",selectedComponent);
            $('input[data-gv-property="labelvalue"]',currentFieldProperties).val(labelElement.text());
            $('input[data-gv-property="labelvalue"]',currentFieldProperties).change(function () {
                var value = $(this).val();
                labelElement =  $(".controls [data-control-type='label']",selectedComponent);
                labelElement.text(value);
            });

            var labelType = $(".controls",selectedComponent).attr("data-type") || 'label';
            $('input[data-gv-property="labeltype"]',currentFieldProperties).data("kendoDropDownList").value(labelType);

            $('#labelfor',currentFieldProperties).addClass("hidden");
            $('#headertype',currentFieldProperties).addClass("hidden");
            $('#alink',currentFieldProperties).addClass("hidden");
            if(labelType == "label"){
                $('#labelfor',currentFieldProperties).removeClass("hidden");
                $('input[data-gv-property="labelfor"]',currentFieldProperties).val(labelElement.attr("for")||'');
            }else if(labelType == "header"){
                $('#headertype',currentFieldProperties).removeClass("hidden");
                var headerType = labelElement[0].nodeName.toLowerCase();
                $('input[data-gv-property="headertype"]',currentFieldProperties).data("kendoDropDownList").value(headerType||'h1');
            }else{
                $('#alink',currentFieldProperties).removeClass("hidden");
                $('input[data-gv-property="alink"]',currentFieldProperties).val(labelElement.attr("herf")||'');
            }

            $('input[data-gv-property="labelfor"]',currentFieldProperties).change(function(){
                var value = this.value;
                labelElement =  $(".controls [data-control-type='label']",selectedComponent);
                labelElement.attr("for",value);
            })

            $('input[data-gv-property="alink"]',currentFieldProperties).change(function(){
                var value = this.value;
                labelElement =  $(".controls [data-control-type='label']",selectedComponent);
                labelElement.attr("href",value);
            })

            $('textarea[data-gv-property="labelcss"]',currentFieldProperties).val(labelElement[0].style.cssText || '');
            $('textarea[data-gv-property="labelcss"]',currentFieldProperties).change(function () {
                var value = this.value;
                labelElement =  $(".controls [data-control-type='label']",selectedComponent);
                labelElement[0].style.cssText = value;
            })

        };

        labelComponent.add = function (ele, id) {
            var template, label;
            template = $("#LabelTemplate").clone();
            $(template).attr("id", id);
            label = $(ele).find("label:first").remove();
            $(".controls", ele).append(template);;
            return template;
        };

        labelComponent.placeHolder = function () {
            return $("<label>Label<\/label>");
        };

        return labelComponent;
    }())

    designer.register("separator", function () {
        var separatorComponent = {};

        $('input[data-gv-property="separatorborder"]', fieldProperties).kendoDropDownList({
            dataSource:[{
                name:"实线",
                value:"solid"
            },{
                name:"虚线",
                value:"dashed"
            },{
                name:"点状",
                value:"dotted"
            },{
                name:"双线",
                value:"double"
            }],
            dataTextField:'name',
            dataValueField:'value',
            change:function(){
                var value = this.value();
                var labelElement = $(selectedComponent).find('div[data-control-type="separator"]')[0];
                labelElement.style.border = '1px '+value+' #e7ecf1';
            }
        })

        separatorComponent.initPropertyPanel = function (ele) {

            var labelElement = $(selectedComponent).find('div[data-control-type="separator"]')[0];

            /* $('textarea[data-gv-property="separatorcss"]', currentFieldProperties).val(labelElement && labelElement.style.cssText||"");
             $('textarea[data-gv-property="separatorcss"]', currentFieldProperties).bind("change",function(){
             var value = $(this).val();
             var labelElement = $(selectedComponent).find('div[data-control-type="separator"]')[0];
             labelElement.style.cssText = value;
             })*/
            var borderStyle = labelElement.style.borderStyle;
            $('input[data-gv-property="separatorborder"]', fieldProperties).data("kendoDropDownList").value(borderStyle);

        };

        separatorComponent.add = function (ele, id) {
            var template, label;
            template = $("#SeparatorTemplate").clone();
            label = $(ele).find("label:first").remove();
            $(".controls", ele).append(template);
            return template;
        };

        separatorComponent.placeHolder = function () {
            return $("#SeparatorTemplate").clone();
        };

        return separatorComponent;
    }())

    designer.register("timepicker", function () {
        var timeComponent = {};

        timeComponent.initPropertyPanel = function (ele, propertyEle) {
            var timeInput = $('input[data-role="timepicker"]', selectedComponent), timePicker = timeInput.data("kendoTimePicker");
            $("#timepickermin").val("");
            $("#timepickermax").val("");
            $("#timedefaultvalue").val("");

            var timeMax = "00:00"
                ,timeMin = "00:00";

            if($("#timepickermin").data("kendoTimePicker")){
                $("#timepickermin").data("kendoTimePicker").wrapper.replaceWith('<input type="text" data-role="timepicker"  data-gv-property="timemin" data-property="min" id="timepickermin"/>')
            }
            if($("#timepickermax").data("kendoTimePicker")){
                $("#timepickermax").data("kendoTimePicker").wrapper.replaceWith('<input type="text" data-role="timepicker" data-gv-property="timemax" data-property="max" id="timepickermax" />')
            }
            if($("#timedefaultvalue").data("kendoTimePicker")){
                $("#timedefaultvalue").data("kendoTimePicker").wrapper.replaceWith('<input type="text" id="timedefaultvalue"  data-role="timepicker"  data-property="default" data-gv-property="datedefault">')
            }

            var timePickerMin = $("#timepickermin").kendoTimePicker({
                value:timeInput.attr("data-min")||"",
                format:timeInput.attr("data-format"),
                max:timeInput.attr("data-max")||timeMax
            }).data("kendoTimePicker");
            var timePickerMax = $("#timepickermax").kendoTimePicker({
                value:timeInput.attr("data-max")||"",
                format:timeInput.attr("data-format"),
                min:timeInput.attr("data-min")||timeMin
            }).data("kendoTimePicker");
            var timeDefaultValue = $("#timedefaultvalue").kendoTimePicker({
                value:timeInput.attr("data-value")||"",
                format:timeInput.attr("data-format"),
                max:timeInput.attr("data-max")||timeMax,
                min:timeInput.attr("data-min")||timeMin
            }).data("kendoTimePicker");



            timePickerMin.bind("change",function(){
                var value = this.value(),dataMin;
                timeInput = $('input[data-role="timepicker"]', selectedComponent);
                timePicker = timeInput.data("kendoTimePicker");
                if (!value) {
                    value = timeMin;
                }
                timePickerMax.min(value);
                timePicker.min(value)
                timeDefaultValue.min(value);

                dataMin = $("#timepickermin").val();
                timeInput.attr("data-min",dataMin);
            });

            timePickerMax.bind("change",function(){
                var value = this.value(),dataMax;
                timeInput = $('input[data-role="timepicker"]', selectedComponent);
                timePicker = timeInput.data("kendoTimePicker");
                if (!value) {
                    value = timeMax;
                }
                timePickerMin.max(value);
                timePicker.max(value);
                timeDefaultValue.max(value);
                dataMax = $("#timepickermax").val();
                timeInput.attr("data-max",dataMax);
            });


            timeDefaultValue.bind("change",function(){
                var value = this.value(),dataValue;
                timeInput = $('input[data-role="timepicker"]', selectedComponent);
                timePicker = timeInput.data("kendoTimePicker");
                timePicker.value(value);
                dataValue =$("#timedefaultvalue").val();
                timeInput.attr("data-value",dataValue);
            });

            $('input[data-gv-property="format"]', currentFieldProperties).val(timeInput.attr("data-format"));
            $('input[data-gv-property="format"]', currentFieldProperties).change(function () {
                var value = $(this).val();
                timeInput = $('input[data-role="timepicker"]', selectedComponent);
                timePicker = timeInput.data("kendoTimePicker");

                timePicker.setOptions({
                    format:value
                });
                timeInput.attr("data-format",value);
            });

        };

        timeComponent.add = function (ele, id) {
            var template, error;
            template = $("#TimePickerTemplate").clone();
            $(".controls", ele).append(template);
            template.attr("id", id);
            $(ele).find("label:first").attr("for", id);
            $(ele).find("label:first").text("Time");
            template.attr("data-label","Time");
            kendo.init(template);
            return template;
        };


        timeComponent.placeHolder = function (ele) {
            var template = $("#TimePickerTemplate").clone();
            $(".controls", ele).append(template);
            $(ele).find("label:first").text("Time");
            kendo.init(template);
            return ele;
        };

        return timeComponent;
    }());

    designer.register("datetimepicker", function () {
        var datetimeComponent = {};

        datetimeComponent.initPropertyPanel = function (ele, propertyEle) {
            var datetimeInput = $('input[data-role="datetimepicker"]', selectedComponent)
                , datetimePicker = datetimeInput.data("kendoDateTimePicker");
            $("#datetimepickermin").val("");
            $("#datetimepickermax").val("");
            $("#datetimedefaultvalue").val("");
            var dateTimeMax = new Date(2099, 11, 31)
                ,dateTimeMin =new Date(1900, 0, 1);

            if($("#datetimepickermin").data("kendoDateTimePicker")){
                $("#datetimepickermin").data("kendoDateTimePicker").wrapper.replaceWith('<input type="text" data-role="datetimepicker" data-gv-property="datetimemin" data-property="min" id="datetimepickermin" />')
            }

            if($("#datetimepickermax").data("kendoDateTimePicker")){
                $("#datetimepickermax").data("kendoDateTimePicker").wrapper.replaceWith('<input type="text" data-role="datetimepicker" data-gv-property="datetimemax" data-property="max" id="datetimepickermax" />')
            }

            if($("#datetimedefaultvalue").data("kendoDateTimePicker")){
                $("#datetimedefaultvalue").data("kendoDateTimePicker").wrapper.replaceWith('<input type="text" id="datetimedefaultvalue"  data-gv-property="datetimedefault" data-property="default" value="" data-role="datetimepicker">')
            }
            var datetimepickermin = $("#datetimepickermin").kendoDateTimePicker({
                value:datetimeInput.attr("data-min")||"",
                format:datetimeInput.attr("data-format"),
                max:datetimeInput.attr("data-max") || dateTimeMax
            }).data("kendoDateTimePicker")
                ,datetimepickermax = $("#datetimepickermax").kendoDateTimePicker({
                value:datetimeInput.attr("data-max")||"",
                format:datetimeInput.attr("data-format"),
                min:datetimeInput.attr("data-min") || dateTimeMin
            }).data("kendoDateTimePicker")
                ,datetimedefaultvalue = $("#datetimedefaultvalue").kendoDateTimePicker({
                value:datetimeInput.attr("data-min")||"",
                format:datetimeInput.attr("data-format"),
                max:datetimeInput.attr("data-max") || dateTimeMax,
                min:datetimeInput.attr("data-min") || dateTimeMin
            }).data("kendoDateTimePicker");



            datetimepickermin.bind("change",function(){
                var value = this.value(),datetimeInput =$('input[data-role="datetimepicker"]', selectedComponent),dataMin;
                var datetimePicker = datetimeInput.data("kendoDateTimePicker");
                if (!value) {
                    value = dateTimeMin;
                }
                datetimepickermax.min(value);
                datetimePicker.min(value);
                datetimedefaultvalue.min(value);
                dataMin = $("#datetimepickermin").val();
                datetimeInput.attr("data-min",dataMin);
            });

            datetimepickermax.bind("change",function(){
                var value = this.value(),datetimeInput = $('input[data-role="datetimepicker"]', selectedComponent),dataMax;
                var datetimePicker = datetimeInput.data("kendoDateTimePicker");
                if (!value) {
                    value = dateTimeMax;
                }
                datetimepickermin.max(value);
                datetimePicker.max(value);
                datetimedefaultvalue.max(value);
                dataMax = $("#datetimepickermax").val();
                datetimeInput.attr("data-max",dataMax);
            });

            datetimedefaultvalue.bind("change",function(){
                var value = this.value(), datetimeInput = $('input[data-role="datetimepicker"]', selectedComponent),dataValue;
                var datetimePicker = datetimeInput.data("kendoDateTimePicker");
                datetimePicker.value(value);
                dataValue = $("#datetimedefaultvalue").val();
                datetimeInput.attr("data-value",dataValue);
            });



            $('input[data-gv-property="format"]', currentFieldProperties).val(datetimeInput.attr("data-format"));
            $('input[data-gv-property="format"]', currentFieldProperties).change(function () {
                var value = $(this).val();
                datetimeInput = $('input[data-role="datetimepicker"]', selectedComponent);
                datetimePicker = datetimeInput.data("kendoDateTimePicker");
                datetimePicker.setOptions({
                    format:value
                });
                datetimeInput.attr("data-format",value);
            });

        };

        datetimeComponent.add = function (ele, id) {
            var template, error;
            template = $("#DateTimePickerTemplate").clone();
            $(".controls", ele).append(template);
            template.attr("id", id);
            $(ele).find("label:first").attr("for", id);
            $(ele).find("label:first").text("DateTime");
            template.attr("data-label","DateTime");
            kendo.init(template);
            return template;
        };

        datetimeComponent.placeHolder = function (ele) {
            var template = $("#DateTimePickerTemplate").clone();
            $(".controls", ele).append(template);
            $(ele).find("label:first").text("Date Time");
            $(template).kendoDateTimePicker();
            return ele;
        };


        return datetimeComponent;
    }())

    designer.register("datepicker", function () {
        var dateComponent = {};

        dateComponent.initPropertyPanel = function (ele, propertyEle) {

            var dateInput =$('input[data-role="datepicker"]', selectedComponent)
                , datePicker = dateInput.data("kendoDatePicker");
            $("#datepickermin").val("");
            $("#datepickermax").val("");
            $("#datedefaultvalue").val("");

            if($("#datepickermin").data("kendoDatePicker")){
                $("#datepickermin").data("kendoDatePicker").wrapper.replaceWith('<input type="text" data-role="datepicker"  data-gv-property="datemin" data-property="min" id="datepickermin" />')
            }
            if($("#datepickermax").data("kendoDatePicker")){
                $("#datepickermax").data("kendoDatePicker").wrapper.replaceWith('<input type="text" data-role="datepicker"  data-gv-property="datemax" data-property="max" id="datepickermax" />')
            }
            if($("#datedefaultvalue").data("kendoDatePicker")){
                $("#datedefaultvalue").data("kendoDatePicker").wrapper.replaceWith(' <input type="text" id="datedefaultvalue" data-gv-property="datedefault" data-property="default"  value="" data-role="datepicker">')
            }

            var dateMax = new Date(2099, 11, 31);
            var dateMin = new Date(1900, 0, 1);

            var datepickermin = $("#datepickermin").kendoDatePicker({
                value:dateInput.attr("data-min"),
                format:dateInput.attr("data-format"),
                max:dateInput.attr("data-max") || dateMax
            }).data("kendoDatePicker")
                ,datepickermax =  $("#datepickermax").kendoDatePicker({
                value:dateInput.attr("data-max"),
                format:dateInput.attr("data-format"),
                min:dateInput.attr("data-min") || dateMin
            }).data("kendoDatePicker")
                ,datedefaultvalue = $("#datedefaultvalue").kendoDatePicker({
                value:dateInput.attr("data-value"),
                format:dateInput.attr("data-format"),
                min:dateInput.attr("data-min")||dateMin,
                max:dateInput.attr("data-max")||dateMax
            }).data("kendoDatePicker");


            datepickermin.bind("change",function(){
                var value = this.value(),dateInput =$('input[data-role="datepicker"]', selectedComponent),dataMinValue;
                var datetimePicker = dateInput.data("kendoDatePicker");
                if (!value) {
                    value = dateMin;
                }
                datepickermax.min(value);
                datetimePicker.min(value)
                datedefaultvalue.min(value);
                dataMinValue = $("#datepickermin").val();
                dateInput.attr("data-min",dataMinValue);
            });

            datepickermax.bind("change",function(){
                var value = this.value(),dateInput = $('input[data-role="datepicker"]', selectedComponent),dataMaxValue;
                var datetimePicker = dateInput.data("kendoDatePicker");
                if (!value) {
                    value = dateMax;
                }
                datepickermin.max(value);
                datetimePicker.max(value);
                datedefaultvalue.max(value);
                dataMaxValue = $("#datepickermax").val();
                dateInput.attr("data-max",dataMaxValue);
            });

            datedefaultvalue.bind("change",function(){
                var value = this.value(), dateInput = $('input[data-role="datepicker"]', selectedComponent),dataValue;
                var datetimePicker = dateInput.data("kendoDatePicker");
                datetimePicker.value(value);
                dataValue = $("#datedefaultvalue").val();
                dateInput.attr("data-value",dataValue);

            });


            $('input[data-gv-property="format"]', currentFieldProperties).val(dateInput.attr("data-format"));
            $('input[data-gv-property="format"]', currentFieldProperties).change(function () {
                var value = $(this).val();
                dateInput = $('input[data-role="datepicker"]', selectedComponent);
                datePicker = dateInput.data("kendoDatePicker");

                datePicker.setOptions({
                    format:value
                });
                dateInput.attr("data-format",value);
            });
        };

        dateComponent.add = function (ele, id) {
            var template, error;
            template = $("#DatePickerTemplate").clone();
            $(".controls", ele).append(template);
            template.attr("id", id);
            $(ele).find("label:first").attr("for", id);
            $(ele).find("label:first").text("Date");
            template.attr("data-label","Date");
            kendo.init(template);
            return template;
        };

        dateComponent.placeHolder = function (ele) {
            var template = $("#DatePickerTemplate").clone();
            $(".controls", ele).append(template);
            $(ele).find("label:first").text("Date");
            $(template).kendoDatePicker();
            return ele;
        };

        return dateComponent;
    }())

    designer.register("checkbox", function () {
        var checkboxComponent = {};

        var isDisable = $('input[data-gv-property="checkboxdisabled"]',fieldProperties).kendoCheckbox({
            checkedValue: 'Y',
            label: "禁用",
            uncheckedValue: 'N',
            change:function(e){
                var disabled = this.value()=='Y' ? true:false;
                var seloption = $("input[name='seloption']", currentFieldProperties)
                    , index = seloption.index(seloption.filter(":checked"))
                    , id = seloption.filter(":checked").attr("data-id");
                $(".controls", selectedComponent).children().each(function (btnIndex, value) {
                    if (index == btnIndex) {
                        var checkbox = $('input[data-role="checkbox"]', this);
                        $(checkbox).prop("disabled",disabled);
                        checkbox.data("kendoCheckbox").enable(!disabled);
                    }
                })



            }
        }).data("kendoCheckbox");

        function  initBindOptions() {
            var bindOptionDataSource =[];
            $(".controls", selectedComponent).children().each(function (i,v) {
                var checkbox = $('input[data-role="checkbox"]', this).data("kendoCheckbox");
                if (checkbox) {
                    var label = checkbox.options.label;
                    var id = $('input[data-role="checkbox"]', this).attr("id")
                    bindOptionDataSource.push({text:label,value:i});
                }
            });
            return new kendo.data.DataSource({
                data:bindOptionDataSource
            });
        }

        var bindOptions = $('input[data-gv-property="bindoptions"]', fieldProperties).kendoDropDownList({
            index:0,
            dataTextField:"text",
            dataValueField:"value",
            change:function (e) {
                var index = this.value();
                var ele = $("input[data-role='checkbox']",selectedComponent)[index];
                $(".selected").removeClass("selected");
                $(ele).addClass("selected");
                initBindEvents($("input.selected"))
            }
        }).data("kendoDropDownList");

        checkboxComponent.initPropertyPanel = function (ele, propertyEle) {
            var selOption;


            bindOptions.setDataSource(initBindOptions());
            function checkboxChange(CheckboxEle) {
                var index = $(CheckboxEle).closest("li").index()
                    , isChecked = $(CheckboxEle).is(":checked");
                $(".controls", selectedComponent).children().each(function (i,v) {
                    if (i == index) {
                        $('input[type="checkbox"]', this).prop("checked", isChecked);
                    }
                })
            }

            function addCheckbox(CheckboxEle) {
                var parent = $(CheckboxEle).closest("li"), dataId = generateComponentName("__r"),
                    checkboxId = generateComponentName("checkbox"),
                    template = $('<li id="' + dataId + '"><div class="input-group" style="display: flex;margin-bottom: 5px"><input name="seloption" style="margin: auto" data-id="' + checkboxId + '" type="radio" /><div style="margin-left: 5px;float:left;width: 80%;"><input class="k-textbox"  name="rdchecklabel" type="text" /></div><span style="float:left;margin:auto" class="input-group-addon"><span class="icon-add"><i class="fa fa-plus-square-o" aria-hidden="true"><\/i><\/span><span class="icon-delete"><i class="fa fa-minus-square-o" aria-hidden="true"><\/i> <\/span><\/span><\/div><\/li>'),
                    index;
                template.insertAfter($(parent));
                index = parent.index();
                $(".controls", selectedComponent).children().each(function () {
                    if ($(this).index() == index) {
                        var name = $('input[data-role="checkbox"]', this).attr("name");
                        var tem = $('<div style="padding:0" class="'+$(this).attr("class")+'">' +
                            '<input data-role="checkbox" style="margin:5px 0px 0px;" data-label="&nbsp;" id="' + checkboxId + '" name="' + name + '" type="checkbox" ><\/div>');
                        $(tem).insertAfter($(this));
                        kendo.init($("#"+checkboxId));
                    }
                });
                bindOptions.setDataSource(initBindOptions())
                $('input[name="rdchecklabel"]', template).change(function () {
                    checkLabelKeyup(this)
                });
                $('input[name="rdchecklabel"]', template).focus(function () {
                    checkLabelFocus(this)
                });
                selectChange();
                $('input[name="rdcheckname"]', template).change(function () {
                    checkNameKeyup(this)
                });
                $('input[name="rdcheckid"]', template).change(function () {
                    checkIdKeyup(this)
                });
                $('input[type="checkbox"]', template).change(function () {
                    checkboxChange(this)
                });
                $("span.icon-add", template).click(function () {
                    addCheckbox(this)
                });
                $("span.icon-delete", template).click(function () {
                    var deleteConfirm = confirm("确定删除？");
                    if(deleteConfirm == true) {
                        deleteCheckbox(this)
                    }
                });
            }

            function deleteCheckbox(checkboxEle) {
                var parent = $(checkboxEle).closest("li")
                    , index = parent.index();
                if (parent.siblings().length > 0) {
                    $(parent).remove();
                }
                $(".controls", selectedComponent).children().each(function (i,v) {
                    if (i == index) {
                        $(this).remove();
                        index = -99;
                    }
                });
                spread();
                bindOptions.setDataSource(initBindOptions());
            }

            function checkLabelKeyup(checkboxEle) {
                var index = $(checkboxEle).closest("li").index()
                    , value = $(checkboxEle).val();
                $("#checkOptions").text(value);
                timeOut(function () {
                    $(".controls", selectedComponent).children().each(function (i,v) {
                        if (i == index) {
                            var checkbox = $('input[data-role="checkbox"]', this).data("kendoCheckbox");
                            checkbox.setOptions({label:value});
                            $("label",this).text(value);
                            $('input[data-role="checkbox"]', this).attr("data-label",value);
                        }
                    })
                    bindOptions.setDataSource(initBindOptions())
                }, 300)
            }

            function checkLabelFocus(checkboxEle){
                var seloption = $("input[name='seloption']", currentFieldProperties),selRadio;
                seloption.filter(":checked")[0].checked = false;
                selRadio = $(checkboxEle).closest("li").find("input[type='radio']");
                selRadio[0].checked = true;
                selRadio.change();
            }

            var checkboxs = $("#__checkboxOptions", propertyEle), id, rdcitems;
            checkboxs.children().remove();

            $(".controls", selectedComponent).children().each(function () {
                var checkbox = $('input[data-role="checkbox"]', this), template, checkboxId,li,label;
                if (checkbox.length > 0) {
                    checkboxId = checkbox.attr("id");
                    checkbox = $(checkbox).data("kendoCheckbox");
                    label = checkbox.options.label;
                    template = $('<li><div class="input-group" style="display: flex;margin-bottom: 5px"><input name="seloption" style="margin: auto" data-id="' + checkboxId + '" type="radio" /><div style="margin-left: 5px;float:left;width: 80%;"><input class="k-textbox" name="rdchecklabel" value="' + label + '" type="text" /></div><span style="float:left;margin: auto" class="input-group-addon"><span class="icon-add"><i class="fa fa-plus-square-o" aria-hidden="true"><\/i><\/span><span class="icon-delete"><i class="fa fa-minus-square-o" aria-hidden="true"><\/i> <\/span><\/span><\/div><\/li>');
                    $(checkbox).is(":checked") && $('input[type="checkbox"]', template).attr("checked", true);
                    $(checkboxs).append(template);
                }
            });


            function selectChange() {
                $("input[name='seloption']", currentFieldProperties).change(function () {
                    var seloption = $("input[name='seloption']", currentFieldProperties)
                        , index = seloption.index(seloption.filter(":checked"))
                        , id = seloption.filter(":checked").attr("data-id")
                        ,name,checkValue,unCheckValue,valuePrimitive;
                    $(".controls", selectedComponent).children().each(function (btnIndex, value) {
                        if (index == btnIndex) {
                            var checkbox = $('input[data-role="checkbox"]', this);
                            selOption = this;
                            name = checkbox.attr("name");
                            id = checkbox.attr("id");
                            checkValue = checkbox.attr("data-checked-value") === undefined ?checkbox.data("kendoCheckbox").options.checkedValue:checkbox.attr("data-checked-value");
                            unCheckValue = checkbox.attr("data-unchecked-value")=== undefined ?checkbox.data("kendoCheckbox").options.uncheckedValue:checkbox.attr("data-unchecked-value");
                            $("#checkOptions").text(checkbox.attr("data-label")+" 基本属性");
                            if(checkbox.attr("disabled") == "disabled" || checkbox.attr("disabled") == true){
                                isDisable.value("Y")
                            }else{
                                isDisable.value("N")
                            }
                        }
                    })

                    $('[data-gv-property="checkboxId"]',currentFieldProperties).val(id);
                    $('[data-gv-property="checkboxName"]',currentFieldProperties).val(name);
                    $('[data-gv-property="checkedValue"]',currentFieldProperties).val(checkValue);
                    $('[data-gv-property="uncheckedValue"]',currentFieldProperties).val(unCheckValue);

                });
            }
            selectChange();

            $("input[name='seloption']:first", currentFieldProperties).attr("checked", true).change();


            var inputElement =$("input[data-role='checkbox']",selOption);

            $('input[data-gv-property="checkedValue"]', currentFieldProperties).change(function () {
                $("input[data-role='checkbox']",selOption).attr("data-checked-value",this.value);
            });

            $('input[data-gv-property="checkboxName"]', currentFieldProperties).change(function () {
                $("input[data-role='checkbox']",selOption).attr("name",this.value);
            });
            var layoutElement;
            if(!layoutElement){
                layoutElement = $( 'input[data-gv-property="layout"]', currentFieldProperties).kendoDropDownList({
                    dataSource:[{text:"1 列（One Column）",value:""},
                        {text:"2 列（Two Column）",value:"col-xs-6"},
                        {text:"3 列（Three Column）",value:"col-xs-4"},
                        {text:"2 列（Four Column）",value:"col-xs-3"},
                        {text:"内联（Inline）",value:"checkbox-inline"}],
                    dataTextField:"text",
                    dataValueField:"value",
                    index:0,
                    change:function () {
                        var value = this.value();
                        var controls = $(selectedComponent).children("div.controls");
                        $(controls).children().removeClass();
                        $(controls).children().addClass(value);


                    }
                }).data("kendoDropDownList");
            }
            layoutElement.value("");
            $(selectedComponent).children("div.controls").children().hasClass("col-xs-6") && layoutElement.value("col-xs-6");
            $(selectedComponent).children("div.controls").children().hasClass("col-xs-4") && layoutElement.value("col-xs-4");
            $(selectedComponent).children("div.controls").children().hasClass("col-xs-3") &&layoutElement.value("col-xs-3");
            $(selectedComponent).children("div.controls").children().hasClass("checkbox-inline") && layoutElement.value("inline");




            $('input[data-gv-property="uncheckedValue"]', currentFieldProperties).change(function () {
                $("input[data-role='checkbox']",selOption).attr("data-unchecked-value",this.value);

            });
            $('input[data-gv-property="checkboxId"]', currentFieldProperties).change(function () {
                $("input[data-role='checkbox']",selOption).attr("id",this.value);

            });

            bindOptions.value("0");
            if(!bindOptions.dataSource.data().length){
                bindOptions.setDataSource(initBindOptions())
            }

            // $('input[data-gv-property="bindoptions"]', currentFieldProperties).data("kendoDropDownList").value();

            /* var ele = $("input[data-role='checkbox']",selectedComponent)[bindOptions.value()];
             $(".selected").removeClass("selected");
             $(ele).addClass("selected");
             initBindEvents($("input.selected"));*/

            $('input[type="checkbox"]', checkboxs).change(function () {
                checkboxChange(this)
            });
            $('input[name="rdchecklabel"]', checkboxs).change(function () {
                checkLabelKeyup(this)
            });

            $('input[name="rdchecklabel"]', checkboxs).focus(function () {
                checkLabelFocus(this)
            });

            $("span.icon-add", checkboxs).click(function () {
                addCheckbox(this)
            });
            $("span.icon-delete", checkboxs).click(function () {
                var deleteConfirm = confirm("确定删除？");
                if(deleteConfirm == true) {
                    deleteCheckbox(this)
                }
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
            var template = $("#CheckBoxTemplate").clone(), fieldName = generateComponentName("field"), checkboxName,
                eleClone, checkbox;
            $(template).attr("id", id);
            $(ele).find("label:first").attr("for", id);
            $(ele).find("label:first").text("Option");
            $(ele).find("label:first").removeAttr("for");
            $(ele).find("label:first").text("Select all that Apply");

            $(".controls", ele).append(template.children());
            $(".controls", ele).children().each(function () {
                var label = $("input",this).val()
                    ,id  = generateComponentName("checkbox");
                $("input",this).attr("id",id);
                $("input",this).attr("name", fieldName);
                $("input",this).kendoCheckbox({
                    label:label
                })
            });
            return template;
        };

        checkboxComponent.placeHolder = function (ele) {
            var template = $("#CheckBoxTemplate").clone()
                ,children = template.children();
            $(".controls", ele).append(template);
            $.each(children,function (i,v) {
                var label = $("input",v).val()
                    ,id = generateComponentName("__c");
                $("input",v).attr("id",id);
                $("input",v).kendoCheckbox({
                    label:label
                })
            })
            $(ele).find("label:first").text("Check all that apply");
            return ele;
        };
        return checkboxComponent;
    }())

    designer.register("button", function () {
        var buttonComponent = {},element;
        var properties = $("#fieldproperties");

        var btnInline = $("#__butgrpinline").kendoCheckbox({
            label: "Inline"
        }).data("kendoCheckbox");
        var btnGroup = $("#__butgrpit").kendoCheckbox({
            label: "Group"
        }).data("kendoCheckbox");
        var btnRightAlign = $("#__rightalign").kendoCheckbox({
            label: "Right Align"
        }).data("kendoCheckbox");




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





        function btnPropIdKeyUp() {
            $("#butpropId", properties).unbind("change");
            $("#butpropId", properties).change(function () {
                var id = $(this).val()
                    , seloption = $("input[name='seloption']", properties).filter(":checked")
                    , id_old = seloption.attr("data-id");
                seloption.attr("data-id",id);
                $("#"+id_old).attr("id",id);
            })
        };

        function btnPropNameKeyUp() {
            $("#butpropName", properties).unbind("change");
            $("#butpropName", properties).change(function () {
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



        $('input[data-gv-property="buttondisabled"]',fieldProperties).kendoCheckbox({
            checkedValue: 'Y',
            label: "禁用",
            uncheckedValue: 'N',
            change:function(e){
                var disabled = this.value()=='Y' ? true:false;
                var seloption = $("input[name='seloption']", properties)
                    , index = seloption.index(seloption.filter(":checked"))
                    , id = seloption.filter(":checked").attr("data-id");
                $("#"+id).prop("disabled",disabled);
            }
        });

        buttonComponent.initPropertyPanel = function (ele, propertyEle) {
            var buttons = $(propertyEle).find("#__buttons"), val, id, timeOut,selectedBtnForBind;
            buttons.children().remove();
            element = selectedComponent;

            function  initBtnSource() {
                var btnDataSource =[];
                $(selectedComponent).find("button").each(function (i,v) {
                    var text = $(this).text().trim()
                        ,id =$(this).attr("id");
                    btnDataSource.push({text:text,value:i});
                });
                return new kendo.data.DataSource({
                    data:btnDataSource
                });
            }

            var btnOptionsForBind =  $("#selectedBtnForBind", fieldProperties).kendoDropDownList({
                dataSource:initBtnSource(),
                index:0,
                dataTextField:"text",
                dataValueField:"value",
                change:function (e) {
                    var value = this.value();
                    selectedComponent.find("button").each(function (i,v) {
                        $(v).removeClass("selected");
                        if(i == value){
                            $(v).addClass("selected");
                        }
                    });
                    selectedBtnForBind = selectedComponent.find("button.selected");
                    selectedBtnForBind.selector = "button.selected";
                    initBindEvents(selectedBtnForBind);
                }
            }).data("kendoDropDownList");

            selectedBtnForBind = selectedComponent.find("button:first");
            initBindEvents(selectedBtnForBind);
            var checked = selectedComponent.find("button:first").prop("disabled")?'Y':'N';
            $('input[data-gv-property="buttondisabled"]',fieldProperties).data("kendoCheckbox").value(checked);


            var btnStyle = $("#butpropStyle",fieldProperties).kendoDropDownList({
                dataSource: [{
                    value:"btn-default",
                    text:"Default"
                },{
                    value:"btn-primary",
                    text:"Primary"
                },{
                    value:"btn-success",
                    text:"Success"
                },{
                    value:"btn-info",
                    text:"Info"
                },{
                    value:"btn-warning",
                    text:"Warning"
                },{
                    value:"btn-danger",
                    text:"Danger"
                },{
                    value:"btn-link",
                    text:"Link"
                }],
                valuePrimitive: true,
                dataTextField: "text",
                dataValueField: "value",
                optionLabel: "Select Style...",
                change:function () {
                    var seloption = $("input[name='seloption']", properties)
                        , index = seloption.index(seloption.filter(":checked"))
                        ,style = this.value();
                    $(selectedComponent).find("button").each(function(i,v){
                        if(i == index){
                            $(v).removeClass("btn-default").removeClass("btn-primary").removeClass("btn-success").removeClass("btn-info").removeClass("btn-warning").removeClass("btn-danger").removeClass("btn-link").addClass(style);
                        }
                    })
                }
            }).data("kendoDropDownList");


            $(selectedComponent).find("button").each(function () {
                val = "";
                id = generateComponentName("__o");
                var dataId = $(this).attr("id"),
                    text = $(this).text().trim(),
                    //template = $('<li><div class="input-group" style="display: flex"><input style="margin: auto" data-id="' + dataId + '" type="text" data-role="checkbox" /><div style="float:left;margin-left: 5px;width: 80%"><input class="k-textbox"  data-id="' + dataId + '" value="' + text + '" type="text" /></div><span style="float:right;margin: auto" class="input-group-addon"><span class="icon-add"><i class="fa fa-plus-square-o" aria-hidden="true"><\/i><\/span><span class="icon-delete"><i class="fa fa-minus-square-o" aria-hidden="true"><\/i> <\/span><\/span><\/div><\/li>');
                    template = $('<li><div class="input-group" style="display: flex"><input name="seloption" style="margin: auto" data-id="' + dataId + '" type="radio" /><div style="float:left;margin-left: 5px;width: 80%"><input class="k-textbox"  data-id="' + dataId + '" value="' + text + '" type="text" /></div><span style="float:right;margin: auto" class="input-group-addon"><span class="icon-add"><i class="fa fa-plus-square-o" aria-hidden="true"><\/i><\/span><span class="icon-delete"><i class="fa fa-minus-square-o" aria-hidden="true"><\/i> <\/span><\/span><\/div><\/li>');
                $("#fieldproperties #__buttons").append($(template));
                // kendo.init($('input[data-role="checkbox"]',template));
            });

            initBtnOpt();
            function btnKeyUp() {
                $('#__buttons input[type="text"]', properties).unbind("change");
                $('#__buttons input[type="text"]', properties).change(function () {
                    var  index = $(this).closest("li").index()
                        ,text = this.value;

                    $("button", selectedComponent).each(function (btnIndex, value) {
                        if (index == btnIndex) {
                            $(value).text(text)
                            btnOptionsForBind.setDataSource(initBtnSource());
                        }
                    })
                })
            };
            btnKeyUp();

            function buttonFocus(btnEle){
                var seloption = $("input[name='seloption']", currentFieldProperties),selRadio;
                seloption.filter(":checked")[0].checked = false;
                selRadio = $(btnEle).closest("li").find("input[type='radio']");
                selRadio[0].checked = true;
                selRadio.change();
            }

            $('#__buttons input[type="text"]', properties).focus(function () {
                buttonFocus(this);
            });

            function selectChange() {
                $("input[name='seloption']", properties).change(function () {
                    var seloption = $("input[name='seloption']", properties)
                        , index = seloption.index(seloption.filter(":checked"))
                        , id = seloption.filter(":checked").attr("data-id"),name;
                    var checked = $("#"+id).prop("disabled")?'Y':'N';
                    $('input[data-gv-property="buttondisabled"]',fieldProperties).data("kendoCheckbox").value(checked);

                    $("button", selectedComponent).each(function (btnIndex, value) {
                        if (index == btnIndex) {
                            btnStyle.dataSource.data().map(function (v, i) {
                                var cls = v.value;
                                if ($(value).hasClass(cls)) {
                                    btnStyle.value(cls);
                                }
                            })
                            name = $(value).attr("name");
                            $("#btnTitle").text($(value).text()+" 基本属性");
                        }
                    })

                    $("#butpropId").val(id);
                    $("#butpropName").val(name)
                });
            }
            selectChange();
            btnPropIdKeyUp();
            btnPropNameKeyUp();

            $("input[name='seloption']:first", properties).attr("checked", true).change();

            var btnFirstChild = $("button:first-child", selectedComponent);

            var isInline = $(selectedComponent).hasClass("group-inline")?"Y":"N";
            var isGroup = $(selectedComponent).hasClass("btn-group")?"Y":"N";
            var isRight = $(selectedComponent).hasClass("pull-right")?"Y":"N";

            btnInline.value(isInline);
            btnInline.unbind("change").bind("change",function(){
                var value = this.value();
                if(value == "Y"){
                    $(selectedComponent).addClass("group-inline");
                }else{
                    $(selectedComponent).removeClass("group-inline")
                }
            });

            btnGroup.value(isGroup);
            btnGroup.unbind("change").bind("change",function(){
                var value = this.value();
                if (value == "Y") {
                    $(selectedComponent).addClass("btn-group")
                } else {
                    $(selectedComponent).removeClass("btn-group")
                }
            });

            btnRightAlign.value(isRight);
            btnRightAlign.unbind("change").bind("change",function(){
                var value = this.value();
                if (value == "Y") {
                    $(selectedComponent).addClass("pull-right")
                } else {
                    $(selectedComponent).removeClass("pull-right")
                }
            });


            $("span.icon-add", buttons).click(function () {
                addBtn(this);
            });
            $("span.icon-delete", buttons).click(function () {
                var deleteConfirm = confirm("确定删除？");
                if(deleteConfirm == true) {
                    deleteBtn(this);
                }
            });

            function addBtn(e) {
                var item = $(e).closest("li"),
                    index = item.index(),
                    btnParent = $("button", selectedComponent).parent(),
                    dataId = generateComponentName("button"),
                    //addBtnTemplate = $('<li><div class="input-group" style="display: flex"><input data-id="' + dataId + '" style="margin: auto" type="text" data-role="checkbox" /><div style="float:left;margin-left: 5px;width: 80%"><input class="k-textbox"  data-id="' + dataId + '" value="Button"  type="text" /></div><span style="float:right;margin: auto" class="input-group-addon"><span class="icon-add"><i class="fa fa-plus-square-o" aria-hidden="true"><\/i><\/span><span class="icon-delete"><i class="fa fa-minus-square-o" aria-hidden="true"><\/i> <\/span><\/span><\/div><\/li>'),
                    addBtnTemplate = $('<li><div class="input-group" style="display: flex"><input data-id="' + dataId + '" name="seloption" style="margin: auto" type="radio" /><div style="float:left;margin-left: 5px;width: 80%"><input class="k-textbox"  data-id="' + dataId + '" value="Button"  type="text" /></div><span style="float:right;margin: auto" class="input-group-addon"><span class="icon-add"><i class="fa fa-plus-square-o" aria-hidden="true"><\/i><\/span><span class="icon-delete"><i class="fa fa-minus-square-o" aria-hidden="true"><\/i> <\/span><\/span><\/div><\/li>'),
                    template, btn;
                addBtnTemplate.insertAfter(item);
                // kendo.init($('input[data-role="checkbox"]',addBtnTemplate))
                template = $("#ButtonTemplate").clone();
                $(template).attr("id", dataId);
                btn = $(btnParent).children().eq(index);
                $(template).insertAfter(btn);
                template.before(" ").after(" ");
                btnOptionsForBind.setDataSource(initBtnSource());
                $(template).ready(function () {
                    btnKeyUp();
                    selectChange();
                    $('#__buttons input[type="text"]', properties).focus(function () {
                        buttonFocus(this);
                    });
                    $("span.icon-add", addBtnTemplate).click(function () {
                        addBtn(this)
                    });
                    $("span.icon-delete", addBtnTemplate).click(function () {
                        var deleteConfirm = confirm("确定删除？");
                        if(deleteConfirm == true) {
                            deleteBtn(this)
                        }
                    })
                })
            };
            function deleteBtn(e) {
                var item = $(e).closest("li"), dataId;
                if (item.siblings().length > 0) {
                    dataId = item.find("input").attr("data-id");
                    $("#" + dataId,selectedComponent).remove();
                    $(item).remove();
                    btnOptionsForBind.setDataSource(initBtnSource());
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
            var template = $("#ButtonTemplate").clone(), id = generateComponentName("button");
            $(ele).find(".controls").remove();
            $(ele).find("div.error").remove();
            $(ele).find("label:first").remove();
            $(template).attr("id", id).appendTo(ele);
            return template;
        };
        buttonComponent.placeHolder = function () {
            return $('<div class="form-group"><button type="button" class="btn btn-default">Button<\/button><div>')
        };
        return buttonComponent;
    }())

    designer.register("tledit", function () {
        var tlEditComponent = {};

        tlEditComponent.initPropertyPanel = function (ele, propertyEle) {
            var inputElement = $('input[data-role="tledit"]', selectedComponent),
                tlEdit = inputElement.data("kendoTLEdit");

            $('input[data-gv-property="value"]', currentFieldProperties).change(function () {
                var value = $(this).val();
                tlEdit.value(value);
                inputElement = $('input[data-role="tledit"]', selectedComponent);
                inputElement.attr("data-value",value);
            });

            $('input[data-gv-property="idField"]', currentFieldProperties).change(function () {
                var value = $(this).val();
                inputElement = $('input[data-role="tledit"]', selectedComponent);
                inputElement.attr("data-id-field",value);
            });
            $('input[data-gv-property="idField"]', currentFieldProperties).val(inputElement.attr("data-id-field"))

            $('input[data-gv-property="field"]', currentFieldProperties).change(function () {
                var value = $(this).val();
                inputElement = $('input[data-role="tledit"]', selectedComponent);
                inputElement.attr("data-field",value);
            });
            $('input[data-gv-property="field"]', currentFieldProperties).val(inputElement.attr("data-field"))


            $('input[data-gv-property="dto"]', currentFieldProperties).change(function () {
                var value = $(this).val();
                inputElement = $('input[data-role="tledit"]', selectedComponent);
                inputElement.attr("data-dto",value);
            });
            $('input[data-gv-property="dto"]', currentFieldProperties).val(inputElement.attr("data-dto"))

            $('input[data-gv-property="model"]', currentFieldProperties).change(function () {
                var value = $(this).val();
                inputElement = $('input[data-role="tledit"]', selectedComponent);
                inputElement.attr("data-model",value);
            });
            $('input[data-gv-property="model"]', currentFieldProperties).val(inputElement.attr("data-model"))


        };

        tlEditComponent.add = function (ele, id) {
            var template = $("#TleditTemplate").clone();
            $(".controls", ele).append(template);
            template.attr("id", id);
            $(ele).find("label:first").attr("for", id);
            $(ele).find("label:first").text("TLEdit");
            template.attr("data-label","TLEdit");
            kendo.init(template);
            return template;
        };


        tlEditComponent.placeHolder = function (ele) {
            var template = $("#TleditTemplate").clone();
            $(".controls", ele).append(template);
            $(ele).find("label:first").text("TLEdit");
            kendo.init(template);
            return ele;
        };

        return tlEditComponent;
    }())

})
(window.gvDesigner = window.gvDesigner || {}, jQuery)