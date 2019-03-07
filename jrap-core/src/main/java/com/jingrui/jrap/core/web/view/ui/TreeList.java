package com.jingrui.jrap.core.web.view.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.jingrui.jrap.core.web.view.ReferenceType;
import com.jingrui.jrap.core.web.view.UITag;
import com.jingrui.jrap.core.web.view.ViewContext;
import com.jingrui.jrap.core.web.view.XMap;

/**
 * 
 * TreeList
 * 
 * @author hailin.xu@jingrui.com
 * 
 */
@UITag
public class TreeList extends Component {

    private static final String PROPERTITY_AUTO_BIND = "autoBind";
    private static final String PROPERTITY_COLUMNS = "columns";
    private static final String PROPERTITY_RESIZABLE = "resizable";
    private static final String PROPERTITY_REORDERABLE = "reorderable";
    private static final String PROPERTITY_COLUMN_MENU = "columnMenu";
    private static final String PROPERTITY_DATA_SOURCE = "dataSource";
    private static final String PROPERTITY_EDITABLE = "editable";
    private static final String PROPERTITY_EXCEL = "excel";
    private static final String PROPERTITY_FILTERABLE = "filterable";
    private static final String PROPERTITY_HEIGHT = "height";
    private static final String PROPERTITY_MESSAGES = "messages";
    private static final String PROPERTITY_PDF = "pdf";
    private static final String PROPERTITY_SCROLLABLE = "scrollable";
    private static final String PROPERTITY_SELECTABLE = "selectable";
    private static final String PROPERTITY_SORTABLE = "sortable";
    private static final String PROPERTITY_TOOLBAR = "toolbar";
    private static final String PROPERTITY_CANCEL = "cancel";
    private static final String PROPERTITY_CHANGE = "change";
    private static final String PROPERTITY_COLLAPSE = "collapse";
    private static final String PROPERTITY_DATA_BINDING = "dataBinding";
    private static final String PROPERTITY_DATA_BOUND = "dataBound";
    private static final String PROPERTITY_DRAGSTART = "dragstart";
    private static final String PROPERTITY_DRAG = "drag";
    private static final String PROPERTITY_DRAGEND = "dragend";
    private static final String PROPERTITY_DROP = "drop";
    private static final String PROPERTITY_EDIT = "edit";
    private static final String PROPERTITY_EXCEL_EXPORT = "excelExport";
    private static final String PROPERTITY_EXPAND = "expand";
    private static final String PROPERTITY_FILTER_MENU_INIT = "filterMenuInit";
    private static final String PROPERTITY_PDF_EXPORT = "pdfExport";
    private static final String PROPERTITY_REMOVE = "remove";
    private static final String PROPERTITY_SAVE = "save";
    private static final String PROPERTITY_COLUMN_SHOW = "columnShow";
    private static final String PROPERTITY_COLUMN_HIDE = "columnHide";
    private static final String PROPERTITY_COLUMN_REORDER = "columnReorder";
    private static final String PROPERTITY_COLUMN_RESIZE = "columnResize";
    private static final String PROPERTITY_COLUMN_MENU_INIT = "columnMenuInit";
    private static final String PROPERTITY_COLUMN_LOCK = "columnLock";
    private static final String PROPERTITY_COLUMN_UNLOCK = "columnUnlock";
    private static final String PROPERTITY_COMMANDS = "commands";
    private static final String PROPERTITY_ITEM = "item";
    private static final String PROPERTITY_TEMPLATE = "template";
    private static final String PROPERTITY_CLICK = "click";
    private static final String PROPERTITY_MOVE = "move";
    private static final String PROPERTITY_MODE = "mode";
    private static final String PROPERTITY_ALLOW_UNSORT = "allowUnsort";
    private static final String PROPERTITY_VIRTUAL = "virtual";
    private static final String PROPERTITY_FORCE_PROXY = "forceProxy";
    private static final String PROPERTITY_EXTRA = "extra";
    private static final String PROPERTITY_TREE_LIST = "treeList";

    public static TreeList createInstance() {
        XMap view = new XMap(DEFAULT_TAG_PREFIX, DEFAULT_NAME_SPACE, PROPERTITY_TREE_LIST);
        TreeList treeList = new TreeList();
        treeList.initPrototype(view);
        return treeList;
    }

    public Boolean getAutoBind() {
        return getPrototype().getBoolean(PROPERTITY_AUTO_BIND);
    }

    public void setAutoBind(Boolean autoBind) {
        setPropertity(PROPERTITY_AUTO_BIND, autoBind);
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
                for (XMap map : list) {
                    cList.add(TreeListColumn.parse(map));
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
            for(GridColumn column : cols){
                columns.addChild(column.toXMap());
            }  
        }
    }
    
    public void addColumn(TreeListColumn column) {
        XMap columns = getPrototype().getChild(PROPERTITY_COLUMNS);
        if (columns == null) {
            columns = getPrototype().createChild(DEFAULT_TAG_PREFIX, DEFAULT_NAME_SPACE, PROPERTITY_COLUMNS);
        }
        columns.addChild(column.toXMap());
    }

    public Boolean getResizable() {
        return getPrototype().getBoolean(PROPERTITY_RESIZABLE);
    }

    public void setResizable(Boolean resizable) {
        setPropertity(PROPERTITY_RESIZABLE, resizable);
    }

    public Boolean getReorderable() {
        return getPrototype().getBoolean(PROPERTITY_REORDERABLE);
    }

    public void setReorderable(Boolean reorderable) {
        setPropertity(PROPERTITY_REORDERABLE, reorderable);
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

    public void setColumnMenu(ColumnMenu columnMenu) {
        getPrototype().addChild(columnMenu.toXMap());
    }

    /*
     * TODO:XXX window
     */
    public Object getEditable() {
        String editable = getPrototype().getString(PROPERTITY_EDITABLE);
        XMap map = getPrototype().getChild(PROPERTITY_EDITABLE);
        if (map != null) {
            Boolean move = map.getBoolean(PROPERTITY_MOVE);
            String template = map.getString(PROPERTITY_TEMPLATE);
            if (move != null) {
                map.put(PROPERTITY_MOVE, move);
            }
            if (template != null) {
                map.put(PROPERTITY_TEMPLATE, map.getPropertity(template));
            }
            return map;
        }
        if (editable != null) {
            if (editable.equals("true") || editable.equals("false")) {
                return getPrototype().getBoolean(PROPERTITY_EDITABLE);
            }
            return getPrototype().getString(PROPERTITY_EDITABLE);
        }
        return null;
    }

    /***
     * 布尔类型或者字符串
     * 
     * @param editable
     */
    public void setEditable(Boolean editable) {
        setPropertity(PROPERTITY_EDITABLE, editable);
    }

    public void setEditable(String editable) {
        setPropertity(PROPERTITY_EDITABLE, editable);
    }

    public void setEditable(Map<String, Object> editable) {
        XMap map = getPrototype().getChild(PROPERTITY_EDITABLE);
        if (map == null) {
            map = getPrototype().createChild(DEFAULT_TAG_PREFIX, DEFAULT_NAME_SPACE, PROPERTITY_EDITABLE);
        }
        if(editable!=null){
            for (String key : editable.keySet()) {
                map.put(key, editable.get(key));
            }  
        }
    }

    public Object getExcel() {
        XMap excel = getPrototype().getChild(PROPERTITY_EXCEL);
        if (excel != null) {
            Boolean filterable = excel.getBoolean(PROPERTITY_FILTERABLE);
            Boolean forceProxy = excel.getBoolean(PROPERTITY_FORCE_PROXY);
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

    public Object getFilterable() {
        Boolean filterable = getPrototype().getBoolean(PROPERTITY_FILTERABLE);
        XMap map = getPrototype().getChild(PROPERTITY_FILTERABLE);
        if (map != null) {
            Boolean extra = map.getBoolean(PROPERTITY_EXTRA);
            XMap messages = map.getChild(PROPERTITY_MESSAGES);
            if (extra != null) {
                map.put(PROPERTITY_EXTRA, extra);
            }
            if (messages != null) {
                map.put(PROPERTITY_MESSAGES, messages);
            }
            return map;
        }
        if (filterable != null) {
            return filterable;
        }
        return null;
    }

    public void setFilterable(Boolean filterable) {
        setPropertity(PROPERTITY_FILTERABLE, filterable);
    }

    public void setFilterable(Boolean extra, Map<String, Object> messages) {
        XMap map = getPrototype().getChild(PROPERTITY_FILTERABLE);
        if (map == null) {
            map = getPrototype().createChild(DEFAULT_TAG_PREFIX, DEFAULT_NAME_SPACE, PROPERTITY_FILTERABLE);
        }
        map.put(PROPERTITY_EXTRA, extra);
        XMap m = map.getChild(PROPERTITY_MESSAGES);
        if (m == null) {
            m = map.createChild(DEFAULT_TAG_PREFIX, DEFAULT_NAME_SPACE, PROPERTITY_MESSAGES);
        }
        if(messages !=null){
            for (String key : messages.keySet()) {
                m.put(key, messages.get(key));
            } 
        }
    }

    public Object getHeight() {
        String height = getPrototype().getString(PROPERTITY_HEIGHT);
        if (height != null) {
            try {
                Integer.parseInt(height);
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
            messages.put(PROPERTITY_COMMANDS, commands);
            return messages;
        }
        return null;
    }

    public void setMessages(Map<String, Object> messages) {
        XMap map = getPrototype().getChild(PROPERTITY_MESSAGES);
        if (map == null) {
            map = getPrototype().createChild(DEFAULT_TAG_PREFIX, DEFAULT_NAME_SPACE, PROPERTITY_MESSAGES);
        }
        if(messages!=null){
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
    }

    public Object getPDF() {
        return PDF.parsePDF(getPrototype());
    }

    /**
     * TODO pdf未实现
     * 
     * @param pdf
     */
    /*
     * public void setPDF(PDF pdf) { setPropertity(PROPERTITY_PDF,pdf); }
     */
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
        if(scrollable != null ){
            for (String key : scrollable.keySet()) {
                map.put(key, scrollable.get(key));
            }  
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
        if(sortable != null){
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
            if(toolbarChild!=null){
                for (XMap tool : toolbarChild) {
                    Object click = tool.getPropertity(PROPERTITY_CLICK);
                    Object template = tool.getPropertity(PROPERTITY_TEMPLATE);
                    if (click != null) {
                       tool.put(PROPERTITY_CLICK,click);
                    }
                    if (template != null) {
                       tool.put(PROPERTITY_TEMPLATE, template);
                    }
                    list.add(tool);
                }
            }
            return list;
        }
        if (toolbar != null) {
            return getPropertity(toolbar);
        }
        return null;
    }

    public void setToolBar(ReferenceType toolBar) {
        setPropertity(PROPERTITY_TOOLBAR, toolBar);
    }

    public void setToolBar(List<Map<String, Object>> toolbar) {
        XMap map = getPrototype().getChild(PROPERTITY_TOOLBAR);
        if (map == null) {
            map = getPrototype().createChild(DEFAULT_TAG_PREFIX, DEFAULT_NAME_SPACE, PROPERTITY_TOOLBAR);
        }
        List<XMap> list = new ArrayList<>();
        if(toolbar!=null){
            for (Map<String, Object> m : toolbar) {
                if(m!=null){
                    XMap x = new XMap(DEFAULT_TAG_PREFIX, DEFAULT_NAME_SPACE, PROPERTITY_ITEM);
                    for (Map.Entry<String, Object> entry : m.entrySet()) {
                        x.put(entry.getKey(), entry.getValue());
                    }
                    list.add(x);
                }
            }  
        }
        map.addChild(list);
    }

    public ReferenceType getDataSource() {
        String dataSource = getPrototype().getString(PROPERTITY_DATA_SOURCE);
        if (dataSource != null) {
            return new ReferenceType(dataSource);
        }
        return null;
    }

    public void setDataSource(ReferenceType dataSource) {
        setPropertity(PROPERTITY_DATA_SOURCE, dataSource);
    }

    protected void initPrototype(XMap view) {
        super.initPrototype(view);
        addEvents(PROPERTITY_CANCEL, PROPERTITY_CHANGE, PROPERTITY_COLLAPSE, PROPERTITY_DATA_BINDING,
                PROPERTITY_DATA_BOUND, PROPERTITY_DRAGSTART, PROPERTITY_DRAG, PROPERTITY_DRAGEND, PROPERTITY_DROP,
                PROPERTITY_EDIT, PROPERTITY_EXCEL_EXPORT, PROPERTITY_EXPAND, PROPERTITY_FILTER_MENU_INIT,
                PROPERTITY_PDF_EXPORT, PROPERTITY_REMOVE, PROPERTITY_SAVE, PROPERTITY_COLUMN_SHOW,
                PROPERTITY_COLUMN_HIDE, PROPERTITY_COLUMN_REORDER, PROPERTITY_COLUMN_RESIZE,
                PROPERTITY_COLUMN_MENU_INIT, PROPERTITY_COLUMN_LOCK, PROPERTITY_COLUMN_UNLOCK);
    }

    public void init(XMap view, ViewContext context) throws Exception {
        super.init(view, context);
        context.addJsonConfig(PROPERTITY_COLUMNS, getColumns());
        context.addJsonConfig(PROPERTITY_COLUMN_MENU, getColumnMenu());
        context.addJsonConfig(PROPERTITY_AUTO_BIND, getAutoBind());
        context.addJsonConfig(PROPERTITY_RESIZABLE, getResizable());
        context.addJsonConfig(PROPERTITY_REORDERABLE, getReorderable());
        context.addJsonConfig(PROPERTITY_EDITABLE, getEditable());
        context.addJsonConfig(PROPERTITY_EXCEL, getExcel());
        context.addJsonConfig(PROPERTITY_FILTERABLE, getFilterable());
        context.addJsonConfig(PROPERTITY_HEIGHT, getHeight());
        context.addJsonConfig(PROPERTITY_MESSAGES, getMessages());
        context.addJsonConfig(PROPERTITY_PDF, getPDF());
        context.addJsonConfig(PROPERTITY_SCROLLABLE, getScrollable());
        context.addJsonConfig(PROPERTITY_SELECTABLE, getSelectable());
        context.addJsonConfig(PROPERTITY_SORTABLE, getSortable());
        context.addJsonConfig(PROPERTITY_TOOLBAR, getToolBar());
        context.addJsonConfig(PROPERTITY_DATA_SOURCE, getDataSource());
    }
}
