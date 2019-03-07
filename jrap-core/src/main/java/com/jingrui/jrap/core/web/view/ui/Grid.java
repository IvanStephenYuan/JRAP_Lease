package com.jingrui.jrap.core.web.view.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.jingrui.jrap.core.web.view.ReferenceType;
import com.jingrui.jrap.core.web.view.UITag;
import com.jingrui.jrap.core.web.view.ViewContext;
import com.jingrui.jrap.core.web.view.XMap;

/**
 * 
 * 表格
 * 
 * @author hailin.xu@jingrui.com
 * 
 */
@UITag
public class Grid extends Component {

    private static final String PROPERTITY_ALLOW_COPY = "allowCopy";
    private static final String PROPERTITY_ALT_ROW_TEMPLATE = "altRowTemplate";
    private static final String PROPERTITY_AUTO_BIND = "autoBind";
    private static final String PROPERTITY_COLUMN_RESIZE_HANDLE_WIDTH = "columnResizeHandleWidth";
    private static final String PROPERTITY_COLUMNS = "columns";
    private static final String PROPERTITY_COLUMN_MENU = "columnMenu";
    private static final String PROPERTITY_DATA_SOURCE = "dataSource";
    private static final String PROPERTITY_DETAIL_TEMPLATE = "detailTemplate";
    private static final String PROPERTITY_EDITABLE = "editable";
    private static final String PROPERTITY_EXCEL = "excel";
    private static final String PROPERTITY_FILTERABLE = "filterable";
    private static final String PROPERTITY_GROUPABLE = "groupable";
    private static final String PROPERTITY_HEIGHT = "height";
    private static final String PROPERTITY_MESSAGES = "messages";
    private static final String PROPERTITY_COMMANDS = "commands";
    private static final String PROPERTITY_MOBILE = "mobile";
    private static final String PROPERTITY_NAVIGATABLE = "navigatable";
    private static final String PROPERTITY_NO_RECORDS = "noRecords";
    private static final String PROPERTITY_PAGEABLE = "pageable";
    private static final String PROPERTITY_RESIZABLE = "resizable";
    private static final String PROPERTITY_REORDERABLE = "reorderable";
    private static final String PROPERTITY_ROW_TEMPLATE = "rowTemplate";
    private static final String PROPERTITY_SCROLLABLE = "scrollable";
    private static final String PROPERTITY_SELECTABLE = "selectable";
    private static final String PROPERTITY_SORTABLE = "sortable";
    private static final String PROPERTITY_TOOLBAR = "toolbar";
    private static final String PROPERTITY_CANCEL = "cancel";
    private static final String PROPERTITY_CHANGE = "change";
    private static final String PROPERTITY_COLUMN_HIDE = "columnHide";
    private static final String PROPERTITY_COLUMN_MENU_INIT = "columnMenuInit";
    private static final String PROPERTITY_COLUMN_REORDER = "columnReorder";
    private static final String PROPERTITY_COLUMN_RESIZE = "columnResize";
    private static final String PROPERTITY_COLUMN_SHOW = "columnShow";
    private static final String PROPERTITY_DATA_BINDING = "dataBinding";
    private static final String PROPERTITY_DATA_BOUND = "dataBound";
    private static final String PROPERTITY_DETAIL_COLLAPSE = "detailCollapse";
    private static final String PROPERTITY_DETAIL_EXPAND = "detailExpand";
    private static final String PROPERTITY_DETAIL_INIT = "detailInit";
    private static final String PROPERTITY_EDIT = "edit";
    private static final String PROPERTITY_EXCEL_EXPORT = "excelExport";
    private static final String PROPERTITY_PAGE = "page";
    private static final String PROPERTITY_GROUP = "group";
    private static final String PROPERTITY_FILTER = "filter";
    private static final String PROPERTITY_FILTER_MENU_INIT = "filterMenuInit";
    private static final String PROPERTITY_PDF_EXPORT = "pdfExport";
    private static final String PROPERTITY_REMOVE = "remove";
    private static final String PROPERTITY_SAVE = "save";
    private static final String PROPERTITY_SAVE_CHANGES = "saveChanges";
    private static final String PROPERTITY_SORT = "sort";
    private static final String PROPERTITY_NAVIGATE = "navigate";
    private static final String PROPERTITY_COLUMN_LOCK = "columnLock";
    private static final String PROPERTITY_COLUMN_UNLOCK = "columnUnlock";
    public static final String PROPERTITY_ATTRIBUTES = "attributes";
    public static final String PROPERTITY_COMMAND = "command";
    public static final String PROPERTITY_EDITOR = "editor";
    public static final String PROPERTITY_ENCODED = "encoded";
    public static final String PROPERTITY_EXPANDABLE = "expandable";
    public static final String PROPERTITY_FIELD = "field";
    public static final String PROPERTITY_FOORER_TEMPLATE = "footerTemplate";
    public static final String PROPERTITY_FORMAT = "format";
    public static final String PROPERTITY_HEADER_ATTRIBUTES = "headerAttributes";
    public static final String PROPERTITY_HEADER_TEMPLATE = "headerTemplate";
    public static final String PROPERTITY_MIN_SCREEN_WIDTH = "minScreenWidth";
    public static final String PROPERTITY_TEMPLATE = "template";
    public static final String PROPERTITY_TITLE = "title";
    public static final String PROPERTITY_WIDTH = "width";
    public static final String PROPERTITY_HIDDEN = "hidden";
    public static final String PROPERTITY_MENU = "menu";
    public static final String PROPERTITY_LOCKED = "locked";
    public static final String PROPERTITY_LOCKABLE = "lockable";
    public static final String PROPERTITY_ITEM = "item";
    public static final String PROPERTITY_GRID = "grid";
    public static final String PROPERTITY_CLICK = "click";
    public static final String PROPERTITY_MODE = "mode";
    public static final String PROPERTITY_ALLOW_UNSORT = "allowUnsort";
    public static final String PROPERTITY_VIRTUAL = "virtual";
    public static final String PROPERTITY_ENABLED = "enabled";
    public static final String PROPERTITY_SHOW_FOOTER = "showFooter";
    public static final String PROPERTITY_FORCE_PROXY = "forceProxy";
    public static final String PROPERTITY_ALL_PAGES = "allPages";
    public static final String PROPERTITY_LOCATE = "locate";
    public static final String PROPERTITY_ROWNUMBER = "rownumber";

    public static final String PROPERTITY_AUTO_RESIZE = "autoResize";
    
    public static Grid createInstance() {
        XMap view = new XMap(DEFAULT_TAG_PREFIX, DEFAULT_NAME_SPACE, PROPERTITY_GRID);
        Grid grid = new Grid();
        grid.initPrototype(view);
        return grid;
    }

    public Object getAllowCopy() {
        Boolean allowCopy = getPrototype().getBoolean(PROPERTITY_ALLOW_COPY);
        XMap map = getPrototype().getChild(PROPERTITY_ALLOW_COPY);
        if (map != null) {
            return map;
        }
        if (allowCopy != null) {
            return allowCopy;
        }
        return null;
    }

    public void setAllowCopy(Boolean allowCopy) {
        setPropertity(PROPERTITY_ALLOW_COPY, allowCopy);
    }

    public void setAllowCopy(Map<String, Object> allowCopy) {
        XMap view = getPrototype().getChild(PROPERTITY_ALLOW_COPY);
        if (view == null) {
            view = getPrototype().createChild(DEFAULT_TAG_PREFIX, DEFAULT_NAME_SPACE, PROPERTITY_ALLOW_COPY);
        }
        if(allowCopy!=null){
            for (Map.Entry<String, Object> entry : allowCopy.entrySet()) {
                view.put(entry.getKey(), entry.getValue());
            }  
        }
    }

    public Object getAltRowTemplate() {
        return getPropertity(PROPERTITY_ALT_ROW_TEMPLATE);
    }

    public void setAltRowTemplate(String altRowTemplate) {
        setPropertity(PROPERTITY_ALT_ROW_TEMPLATE, altRowTemplate);
    }

    public void setAltRowTemplate(ReferenceType altRowTemplate) {
        setPropertity(PROPERTITY_ALT_ROW_TEMPLATE, altRowTemplate.toString());
    }

    public Boolean getAutoBind() {
        return getPrototype().getBoolean(PROPERTITY_AUTO_BIND);
    }

    public void setAutoBind(Boolean autoBind) {
        setPropertity(PROPERTITY_AUTO_BIND, autoBind);
    }

    public Integer getColumnResizeHandleWidth() {
        return getPrototype().getInteger(PROPERTITY_COLUMN_RESIZE_HANDLE_WIDTH);
    }

    public void setColumnResizeHandleWidth(Integer columnResizeHandleWidth) {
        setPropertity(PROPERTITY_COLUMN_RESIZE_HANDLE_WIDTH, columnResizeHandleWidth);
    }

    public Object getColumns() {
        String columnsArray = getPrototype().getString(PROPERTITY_COLUMNS);
        XMap columnsChild = getPrototype().getChild(PROPERTITY_COLUMNS);
        List<Column> cList = new ArrayList<>();
        if (columnsArray != null) {
            String[] datas = columnsArray.split(",");
            for (int i = 0; i < datas.length; i++) {
                datas[i] = datas[i].trim();
            }
            return datas;
        }
        if (columnsChild != null) {
            List<XMap> list = columnsChild.getChildren();
            if (list != null) {
                for (XMap view : list) {
                    cList.add(GridColumn.parse(view));
                }
                return cList;
            }
        }
        return null;
    }

    public void addColumns(List<GridColumn> cols) {
        XMap columns = getPrototype().getChild(PROPERTITY_COLUMNS);
        if (columns == null) {
            columns = getPrototype().createChild(DEFAULT_TAG_PREFIX, DEFAULT_NAME_SPACE, PROPERTITY_COLUMNS);
        }
        if(cols!=null){
            for (GridColumn column : cols) {
                columns.addChild(column.toXMap());
            }  
        }
    }

    public void addColumn(GridColumn column) {
        XMap columns = getPrototype().getChild(PROPERTITY_COLUMNS);
        if (columns == null) {
            columns = getPrototype().createChild(DEFAULT_TAG_PREFIX, DEFAULT_NAME_SPACE, PROPERTITY_COLUMNS);
        }
        columns.addChild(column.toXMap());
    }

    public Object getColumnMenu() {
        Boolean columnMenu = getPrototype().getBoolean(PROPERTITY_COLUMN_MENU);
        if (getPrototype().getChild(PROPERTITY_COLUMN_MENU) != null) {
            ColumnMenu colMenu = new ColumnMenu();
            XMap map = getPrototype().getChild(PROPERTITY_COLUMN_MENU);
            if (map != null) {
                colMenu.setColumns(map.getBoolean(PROPERTITY_COLUMNS));
                colMenu.setFilterable(map.getBoolean(PROPERTITY_FILTERABLE));
                colMenu.setSortable(map.getBoolean(PROPERTITY_SORTABLE));
                colMenu.setMessages(map.getChild(PROPERTITY_MESSAGES));
                return colMenu;
            }
        }
        if (columnMenu != null) {
            return columnMenu;
        }
        return null;
    }

    public void setColumnMenu(Boolean columnMenu) {
        setPropertity(PROPERTITY_COLUMN_MENU, columnMenu);
    }

    public void setColumnMenu(ColumnMenu columnMenu) {
        getPrototype().addChild(columnMenu.toXMap());
    }

    public Object getDetailTemplate() {
        return getPropertity(PROPERTITY_DETAIL_TEMPLATE);
    }

    public void setDetailTemplate(String detailTemplate) {
        setPropertity(PROPERTITY_DETAIL_TEMPLATE, detailTemplate);
    }

    public void setDetailTemplate(ReferenceType detailTemplate) {
        setPropertity(PROPERTITY_DETAIL_TEMPLATE, detailTemplate.toString());
    }

    public Object getEditable() {
        String editable = getPrototype().getString(PROPERTITY_EDITABLE);
        XMap map = getPrototype().getChild(PROPERTITY_EDITABLE);
        if (map != null) {
            return GridEditable.parseEditable(map);
        }
        if (editable != null) {
            if ("true".equals(editable) || "false".equals(editable)) {
                return getPrototype().getBoolean(PROPERTITY_EDITABLE);
            }
            return getPrototype().getString(PROPERTITY_EDITABLE);
        }
        return null;
    }

    public void setEditable(Boolean editable) {
        setPropertity(PROPERTITY_EDITABLE, editable);
    }

    public void setEditable(String editable) {
        setPropertity(PROPERTITY_EDITABLE, editable);
    }

    public void setEditable(GridEditable editable) {
        getPrototype().addChild(editable.toXMap());
    }

    public Object getExcel() {
        XMap excel = getPrototype().getChild(PROPERTITY_EXCEL);
        if (excel != null) {
            Boolean allPages = excel.getBoolean(PROPERTITY_ALL_PAGES);
            Boolean filterable = excel.getBoolean(PROPERTITY_FILTERABLE);
            Boolean forceProxy = excel.getBoolean(PROPERTITY_FORCE_PROXY);
            if (allPages != null) {
                excel.put(PROPERTITY_ALL_PAGES, allPages);
            }
            if (filterable != null) {
                excel.put(PROPERTITY_FILTERABLE, filterable);
            }
            if (forceProxy != null) {
                excel.put(PROPERTITY_FORCE_PROXY, forceProxy);
            }
            return excel;
        }
        return null;
    }

    public void setExcel(Map<String, Object> excel) {
        XMap map = getPrototype().getChild(PROPERTITY_EXCEL);
        if (map == null) {
            map = getPrototype().createChild(DEFAULT_TAG_PREFIX, DEFAULT_NAME_SPACE, PROPERTITY_EXCEL);
        }
        if(excel!=null){
            for (String key : excel.keySet()) {
                map.put(key, excel.get(key));
            }  
        }
    }

    public Object getGroupable() {
        Boolean groupable = getPrototype().getBoolean(PROPERTITY_GROUPABLE);
        XMap map = getPrototype().getChild(PROPERTITY_GROUPABLE);
        if (map != null) {
            Boolean enabled = map.getBoolean(PROPERTITY_ENABLED);
            Boolean showFooter = map.getBoolean(PROPERTITY_SHOW_FOOTER);
            XMap messages = map.getChild(PROPERTITY_MESSAGES);
            if (enabled != null) {
                map.put(PROPERTITY_ENABLED, enabled);
            }
            if (showFooter != null) {
                map.put(PROPERTITY_SHOW_FOOTER, showFooter);
            }
            if (messages != null) {
                map.put(PROPERTITY_MESSAGES, messages);
            }
            return map;
        }
        if (groupable != null) {
            return groupable;
        }
        return null;
    }

    public void setGroupable(Map<String, Object> groupable) {
        XMap map = getPrototype().getChild(PROPERTITY_GROUPABLE);
        if (map == null) {
            map = getPrototype().createChild(DEFAULT_TAG_PREFIX, DEFAULT_NAME_SPACE, PROPERTITY_GROUPABLE);
        }
        if(groupable!=null){
            for (String key : groupable.keySet()) {
                if (key.equals(PROPERTITY_MESSAGES)) {
                    XMap gmessages = map.getChild(PROPERTITY_MESSAGES);
                    if (gmessages == null) {
                        gmessages = map.createChild(DEFAULT_TAG_PREFIX, DEFAULT_NAME_SPACE, PROPERTITY_MESSAGES);
                    }
                    Map<String, Object> messages = (Map<String, Object>) (groupable.get(key));
                    if(messages!=null){
                        for (String k : messages.keySet()) {
                            gmessages.put(k, messages.get(k));
                        } 
                    }
                } else {
                    map.put(key, groupable.get(key));
                }
            } 
        }
    }

    public Object getHeight() {
        String height = getPrototype().getString(PROPERTITY_HEIGHT);
        if (height != null) {
            try {
                return height + "px";
            } catch (Exception e) {
                return height;
            }
        }
        return null;
    }

    public void setHeight(Integer height) {
        setPropertity(PROPERTITY_HEIGHT, height);
    }

    public void setHeight(String height) {
        setPropertity(PROPERTITY_HEIGHT, height);
    }

    public Object getMessages() {
        XMap messages = getPrototype().getChild(PROPERTITY_MESSAGES);
        if (messages != null) {
            XMap commands = messages.getChild(PROPERTITY_COMMANDS);
            if (commands != null) {
                messages.put(PROPERTITY_COMMANDS, commands);
            }
            return messages;
        }
        return null;
    }

    public void setMessages(Map<String, Object> messages) {
        XMap map = getPrototype().getChild(PROPERTITY_MESSAGES);
        if (map == null) {
            map = getPrototype().createChild(DEFAULT_TAG_PREFIX, DEFAULT_NAME_SPACE, PROPERTITY_MESSAGES);
        }
        for (String key : messages.keySet()) {
            if (key.equals(PROPERTITY_COMMANDS)) {
                XMap mcommands = map.getChild(PROPERTITY_COMMANDS);
                if (mcommands == null) {
                    mcommands = map.createChild(DEFAULT_TAG_PREFIX, DEFAULT_NAME_SPACE, PROPERTITY_COMMANDS);
                }
                Map<String, Object> commands = (Map<String, Object>) (messages.get(key));
                if(commands!=null){
                    for (String k : commands.keySet()) {
                        mcommands.put(k, commands.get(k));
                    } 
                }
            } else {
                map.put(key, messages.get(key));
            }
        }
    }

    public Object getMobile() {
        String mobile = getPrototype().getString(PROPERTITY_MOBILE);
        if (mobile != null) {
            if ("true".equals(mobile) || "false".equals(mobile)) {
                return getPrototype().getBoolean(PROPERTITY_MOBILE);
            }
            return mobile;
        }
        return null;
    }

    public void setMobile(Boolean mobile) {
        setPropertity(PROPERTITY_MOBILE, mobile);
    }

    public void setMobile(String mobile) {
        setPropertity(PROPERTITY_MOBILE, mobile);
    }

    public Boolean getNavigatable() {
        return getPrototype().getBoolean(PROPERTITY_NAVIGATABLE);
    }

    public void setNavigatable(Boolean navigatable) {
        setPropertity(PROPERTITY_NAVIGATABLE, navigatable);
    }

    public Object getNoRecords() {
        Boolean noRecords = getPrototype().getBoolean(PROPERTITY_NO_RECORDS);
        XMap map = getPrototype().getChild(PROPERTITY_NO_RECORDS);
        if (map != null) {
            String template = map.getString(PROPERTITY_TEMPLATE);
            if (template != null) {
                map.put(PROPERTITY_TEMPLATE, map.getPropertity(template));
            }
            return map;
        }
        if (noRecords != null) {
            return noRecords;
        }
        return null;
    }

    public void setNoRecords(Boolean noRecords) {
        setPropertity(PROPERTITY_NO_RECORDS, noRecords);
    }

    public void setNoRecords(Map<String, Object> noRecords) {
        XMap map = getPrototype().getChild(PROPERTITY_NO_RECORDS);
        if (map == null) {
            map = getPrototype().createChild(DEFAULT_TAG_PREFIX, DEFAULT_NAME_SPACE, PROPERTITY_NO_RECORDS);
        }
        for (String key : noRecords.keySet()) {
            if (key.equals(PROPERTITY_TEMPLATE)) {
                if (noRecords.get(key) instanceof ReferenceType) {
                    map.putPropertity(key, noRecords.get(key));
                } else {
                    map.put(key, noRecords.get(key));
                }
            } else {
                map.put(key, noRecords.get(key));
            }
        }
    }

    public Object getPageable() {
        Boolean pageable = getPrototype().getBoolean(PROPERTITY_PAGEABLE);
        XMap map = getPrototype().getChild(PROPERTITY_PAGEABLE);
        if (map != null) {
            return GridPageable.parsePageable(map);
        }
        if (pageable != null) {
            return pageable;
        }
        return null;
    }

    public void setPageable(Boolean pageable) {
        setPropertity(PROPERTITY_PAGEABLE, pageable);
    }

    public void setPageable(GridPageable pageable) {
        getPrototype().addChild(pageable.toXMap());
    }

    public Boolean getReorderable() {
        return getPrototype().getBoolean(PROPERTITY_REORDERABLE);
    }

    public void setReorderable(Boolean reorderable) {
        setPropertity(PROPERTITY_REORDERABLE, reorderable);
    }

    public Boolean getResizable() {
        return getPrototype().getBoolean(PROPERTITY_RESIZABLE);
    }

    public void setResizable(Boolean resizable) {
        setPropertity(PROPERTITY_RESIZABLE, resizable);
    }

    public Object getRowTemplate() {
        return getPropertity(PROPERTITY_ROW_TEMPLATE);
    }

    public void setRowTemplate(String rowTemplate) {
        setPropertity(PROPERTITY_ROW_TEMPLATE, rowTemplate);
    }

    public void setRowTemplate(ReferenceType rowTemplate) {
        setPropertity(PROPERTITY_ROW_TEMPLATE, rowTemplate);

    }

    public Object getScrollable() {
        XMap map = getPrototype().getChild(PROPERTITY_SCROLLABLE);
        if (map != null) {
            Boolean virtual = map.getBoolean(PROPERTITY_VIRTUAL);
            if (virtual != null) {
                map.put(PROPERTITY_VIRTUAL, virtual);
            }
            return map;
        }
        return getPrototype().getBoolean(PROPERTITY_SCROLLABLE);
    }

    public void setScrollable(Boolean scrollable) {
        setPropertity(PROPERTITY_SCROLLABLE, scrollable);
    }

    public void setScrollable(Map<String, Object> scrollable) {
        XMap map = getPrototype().getChild(PROPERTITY_SCROLLABLE);
        if (map == null) {
            map = getPrototype().createChild(DEFAULT_TAG_PREFIX, DEFAULT_NAME_SPACE, PROPERTITY_SCROLLABLE);
        }
        for (String key : scrollable.keySet()) {
            map.put(key, scrollable.get(key));
        }
    }

    public Object getSelectable() {
        String selectable = getPrototype().getString(PROPERTITY_SELECTABLE);
        if (selectable != null) {
            if (selectable.equals("true") || selectable.equals("false")) {
                return getPrototype().getBoolean(PROPERTITY_SELECTABLE);
            }
            return selectable;
        }
        return null;
    }

    public void setSelectable(String selectable) {
        setPropertity(PROPERTITY_SELECTABLE, selectable);
    }

    public void setSelectable(Boolean selectable) {
        setPropertity(PROPERTITY_SELECTABLE, selectable);
    }

    public Object getSortable() {
        Boolean sortable = getPrototype().getBoolean(PROPERTITY_SORTABLE);
        XMap sortableChild = getPrototype().getChild(PROPERTITY_SORTABLE);
        if (sortableChild != null) {
            Boolean allowUnsort = sortableChild.getBoolean(PROPERTITY_ALLOW_UNSORT);
            String mode = sortableChild.getString(PROPERTITY_MODE);
            if (allowUnsort != null) {
                sortableChild.put(PROPERTITY_ALLOW_UNSORT, allowUnsort);
            }
            if (mode != null) {
                sortableChild.put(PROPERTITY_MODE, mode);
            }
            return sortableChild;
        }
        if (sortable != null) {
            return sortable;
        }
        return getPrototype().getBoolean(PROPERTITY_SORTABLE);
    }

    public void setSortable(Boolean sortable) {
        setPropertity(PROPERTITY_SORTABLE, sortable);
    }

    public void setSortable(Map<String, Object> sortable) {
        XMap map = getPrototype().getChild(PROPERTITY_SORTABLE);
        if (map == null) {
            map = getPrototype().createChild(DEFAULT_TAG_PREFIX, DEFAULT_NAME_SPACE, PROPERTITY_SORTABLE);
        }
        if(sortable!=null){
            for (String key : sortable.keySet()) {
                map.put(key, sortable.get(key));
            } 
        }
    }

    public Object getToolBar() {
        String toolbar = getPrototype().getString(PROPERTITY_TOOLBAR);
        XMap map = getPrototype().getChild(PROPERTITY_TOOLBAR);
        List<XMap> list = new ArrayList<>();
        if (map != null) {
            List<XMap> toolbarChild = map.getChildren();
            if (toolbarChild != null) {
                for (XMap tool : toolbarChild) {
                    String click = tool.getString(PROPERTITY_CLICK);
                    Object template = tool.getPropertity(PROPERTITY_TEMPLATE);
                    if (click != null) {
                        tool.put(PROPERTITY_CLICK, new ReferenceType(PROPERTITY_CLICK));
                    }
                    if (template != null) {
                        tool.putPropertity(PROPERTITY_TEMPLATE, template);
                    }
                    if (tool.getChild(PROPERTITY_TEMPLATE) != null) {
                        tool.put(PROPERTITY_TEMPLATE, tool.getChild(PROPERTITY_TEMPLATE).getText());
                    }
                    list.add(tool);
                }
                return list;
            }
        }
        if (toolbar != null) {
            return getPropertity(toolbar);
        }
        return null;
    }

    public void setToolBar(List<Map<String, Object>> toolbar) {
        XMap map = getPrototype().getChild(PROPERTITY_TOOLBAR);
        if (map == null) {
            map = getPrototype().createChild(DEFAULT_TAG_PREFIX, DEFAULT_NAME_SPACE, PROPERTITY_TOOLBAR);
        }
        List<XMap> list = new ArrayList<>();
        for (Map<String, Object> m : toolbar) {
            XMap x = new XMap(DEFAULT_TAG_PREFIX, DEFAULT_NAME_SPACE, PROPERTITY_ITEM);
            if(m!=null){
                for (Map.Entry<String, Object> entry : m.entrySet()) {
                    if (entry.getKey().equals(PROPERTITY_TEMPLATE)) {
                        if (entry.getValue() instanceof ReferenceType) {
                            x.putPropertity(entry.getKey(), entry.getValue());
                        } else {
                            XMap template = new XMap(ViewTag.DEFAULT_TAG_PREFIX, ViewTag.DEFAULT_NAME_SPACE,
                                    PROPERTITY_TEMPLATE);
                            template.setText(entry.getValue().toString());
                            x.addChild(template);
                        }
                    } else {
                        x.put(entry.getKey(), entry.getValue());
                    }
                }
            }
            list.add(x);
        }
        map.addChild(list);
    }

    public ReferenceType getDataSource() {
        return ReferenceType.create(getPrototype().getString(PROPERTITY_DATA_SOURCE));
    }

    public void setDataSource(ReferenceType dataSource) {
        setPropertity(PROPERTITY_DATA_SOURCE, dataSource);
    }

    public ReferenceType getLocate() {
         return ReferenceType.create(getPrototype().getString(PROPERTITY_LOCATE)); 
    }

    public void setLocate(ReferenceType locate) {
        setPropertity(PROPERTITY_LOCATE, locate);
    }

    public Boolean getRownumber() {
        return getPrototype().getBoolean(PROPERTITY_ROWNUMBER);
    }

    public void setRownumber(Boolean rownumber) {
        setPropertity(PROPERTITY_ROWNUMBER, rownumber);
    }

    public Boolean getAutoResize() {
        return getPrototype().getBoolean(PROPERTITY_AUTO_RESIZE);
    }

    public void setAutoResize(Boolean autoResize) {
        setPropertity(PROPERTITY_AUTO_RESIZE, autoResize);
    }
    
    protected void initPrototype(XMap view) {
        super.initPrototype(view);
        addEvents(PROPERTITY_CANCEL, PROPERTITY_CHANGE, PROPERTITY_DETAIL_COLLAPSE, PROPERTITY_DATA_BINDING,
                PROPERTITY_DATA_BOUND, PROPERTITY_DETAIL_EXPAND, PROPERTITY_DETAIL_INIT, PROPERTITY_PAGE,
                PROPERTITY_GROUP, PROPERTITY_EDIT, PROPERTITY_EXCEL_EXPORT, PROPERTITY_FILTER, PROPERTITY_SAVE_CHANGES,
                PROPERTITY_SORT, PROPERTITY_NAVIGATE, PROPERTITY_FILTER_MENU_INIT, PROPERTITY_PDF_EXPORT,
                PROPERTITY_REMOVE, PROPERTITY_SAVE, PROPERTITY_COLUMN_SHOW, PROPERTITY_COLUMN_HIDE,
                PROPERTITY_COLUMN_REORDER, PROPERTITY_COLUMN_RESIZE, PROPERTITY_COLUMN_MENU_INIT,
                PROPERTITY_COLUMN_LOCK, PROPERTITY_COLUMN_UNLOCK);
    }

    public void init(XMap view, ViewContext context) throws Exception {
        super.init(view, context);
        context.addJsonConfig(PROPERTITY_ALLOW_COPY, getAllowCopy());
        context.addJsonConfig(PROPERTITY_ALT_ROW_TEMPLATE, getAltRowTemplate());
        context.addJsonConfig(PROPERTITY_AUTO_BIND, getAutoBind());
        context.addJsonConfig(PROPERTITY_COLUMN_RESIZE_HANDLE_WIDTH, getColumnResizeHandleWidth());
        context.addJsonConfig(PROPERTITY_COLUMNS, getColumns());
        context.addJsonConfig(PROPERTITY_COLUMN_MENU, getColumnMenu());
        context.addJsonConfig(PROPERTITY_RESIZABLE, getResizable());
        context.addJsonConfig(PROPERTITY_REORDERABLE, getReorderable());
        context.addJsonConfig(PROPERTITY_EDITABLE, getEditable());
        context.addJsonConfig(PROPERTITY_EXCEL, getExcel());
        // context.addJsonConfig(PROPERTITY_FILTERABLE,
        // Filterable.parseFilterable());
        context.addJsonConfig(PROPERTITY_HEIGHT, getHeight());
        context.addJsonConfig(PROPERTITY_PAGEABLE, getPageable());
        context.addJsonConfig(PROPERTITY_MESSAGES, getMessages());
        // context.addJsonConfig(PROPERTITY_PDF, GridPDF.parseGridPDF());
        context.addJsonConfig(PROPERTITY_SCROLLABLE, getScrollable());
        context.addJsonConfig(PROPERTITY_SELECTABLE, getSelectable());
        context.addJsonConfig(PROPERTITY_SORTABLE, getSortable());
        context.addJsonConfig(PROPERTITY_TOOLBAR, getToolBar());
        context.addJsonConfig(PROPERTITY_DATA_SOURCE, getDataSource());
        context.addJsonConfig(PROPERTITY_LOCATE, getLocate());
        context.addJsonConfig(PROPERTITY_ROWNUMBER, getRownumber());
        context.addJsonConfig(PROPERTITY_AUTO_RESIZE, getAutoResize());
    }

}
