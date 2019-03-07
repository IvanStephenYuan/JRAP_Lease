package com.jingrui.jrap.core.web.view.ui;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jingrui.jrap.core.web.view.ReferenceType;
import com.jingrui.jrap.core.web.view.XMap;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class GridColumnFilterable {

    private Map<String, Object> cell;
    private Boolean multi;
    private ReferenceType dataSource;
    private Boolean checkAll;
    private ReferenceType itemTemplate;
    private Map<Object, Object> operators;
    private Boolean search;
    private Boolean ignoreCase;
    private Object ui;

    private static final String PROPERTITY_CELL = "cell";
    private static final String PROPERTITY_DATA_TEXT_FIELD = "dataTextField";
    private static final String PROPERTITY_DELAY = "delay";
    private static final String PROPERTITY_INPUT_WIDTH = "inputWidth";
    private static final String PROPERTITY_SUGGESTION_OPERATOR = "suggestionOperator";
    private static final String PROPERTITY_MIN_LENGTH = "minLength";
    private static final String PROPERTITY_ENABLED = "enable";
    private static final String PROPERTITY_OPERATOR = "operator";
    private static final String PROPERTITY_SHOW_OPERATORS = "showOperators";
    private static final String PROPERTITY_TEMPLATE = "template";
    private static final String PROPERTITY_MULTI = "multi";
    private static final String PROPERTITY_DATA_SOURCE = "dataSource";
    private static final String PROPERTITY_CHECK_ALL = "checkAll";
    private static final String PROPERTITY_ITEM_TEMPLATE = "itemTemplate";
    private static final String PROPERTITY_OPERATORS = "operators";
    private static final String PROPERTITY_SEARCH = "search";
    private static final String PROPERTITY_IGNORE_CASE = "ignoreCase";
    private static final String PROPERTITY_UI = "ui";

    public static GridColumnFilterable parseFilterable(XMap view) {
        GridColumnFilterable filterable = new GridColumnFilterable();
        filterable.setCell(parseCell(view.getChild(PROPERTITY_CELL)));
        filterable.setMulti(view.getBoolean(PROPERTITY_MULTI));
        filterable.setDataSource(ReferenceType.create(view.getString(PROPERTITY_DATA_SOURCE)));
        filterable.setCheckAll(view.getBoolean(PROPERTITY_CHECK_ALL));
        filterable.setItemTemplate(ReferenceType.create(view.getString(PROPERTITY_ITEM_TEMPLATE)));
        filterable.setOperators(view.getChild(PROPERTITY_OPERATORS));
        filterable.setSearch(view.getBoolean(PROPERTITY_SEARCH));
        filterable.setIgnoreCase(view.getBoolean(PROPERTITY_IGNORE_CASE));
        filterable.setUi(view.getPropertity(PROPERTITY_UI));
        return filterable;
    }

    public XMap toXMap() {
        XMap filterable = new XMap(ViewTag.DEFAULT_TAG_PREFIX, ViewTag.DEFAULT_NAME_SPACE, "filterable");
        XMap cell = new XMap(ViewTag.DEFAULT_TAG_PREFIX, ViewTag.DEFAULT_NAME_SPACE, PROPERTITY_CELL);
        if (getCell() != null) {
            for (Map.Entry<String, Object> entry : getCell().entrySet()) {
                cell.put(entry.getKey(), entry.getValue());
            }
        }
        filterable.addChild(cell);
        filterable.put(PROPERTITY_MULTI, getMulti());
        filterable.put(PROPERTITY_DATA_SOURCE, getDataSource());
        filterable.put(PROPERTITY_CHECK_ALL, getCheckAll());
        filterable.put(PROPERTITY_ITEM_TEMPLATE, getItemTemplate());
        XMap operators = new XMap(ViewTag.DEFAULT_TAG_PREFIX, ViewTag.DEFAULT_NAME_SPACE, PROPERTITY_OPERATORS);
        if (getOperators() != null) {
            for (Map.Entry<Object, Object> entry : getOperators().entrySet()) {
                operators.put(entry.getKey(), entry.getValue());
            }
        }
        filterable.addChild(operators);
        filterable.put(PROPERTITY_SEARCH, getMulti());
        filterable.put(PROPERTITY_IGNORE_CASE, getMulti());
        filterable.put(PROPERTITY_UI, getUi());
        return filterable;
    }

    /**
     * @param view
     * @return
     */
    public static Map<String, Object> parseCell(XMap view) {
        Map<String, Object> map = new HashMap<>();
        if (view != null) {
            String dataSource1 = view.getString(PROPERTITY_DATA_SOURCE);
            if (dataSource1 != null) {
                map.put(PROPERTITY_DATA_SOURCE, new ReferenceType(dataSource1));
            }
            if (view.getDouble(PROPERTITY_DELAY) != null) {
                map.put(PROPERTITY_DELAY, view.getDouble(PROPERTITY_DELAY));
            }
            if (view.getDouble(PROPERTITY_INPUT_WIDTH) != null) {
                map.put(PROPERTITY_INPUT_WIDTH, view.getDouble(PROPERTITY_INPUT_WIDTH));
            }
            if (view.getDouble(PROPERTITY_MIN_LENGTH) != null) {
                map.put(PROPERTITY_MIN_LENGTH, view.getDouble(PROPERTITY_MIN_LENGTH));
            }
            if (view.getBoolean(PROPERTITY_ENABLED) != null) {
                map.put(PROPERTITY_ENABLED, view.getBoolean(PROPERTITY_ENABLED));
            }
            if (view.getBoolean(PROPERTITY_SHOW_OPERATORS) != null) {
                map.put(PROPERTITY_SHOW_OPERATORS, view.getBoolean(PROPERTITY_SHOW_OPERATORS));
            }
            if (view.getPropertity(PROPERTITY_TEMPLATE) != null) {
                map.put(PROPERTITY_TEMPLATE, view.getPropertity(PROPERTITY_TEMPLATE));
            }
            if (view.getString(PROPERTITY_DATA_TEXT_FIELD) != null) {
                map.put(PROPERTITY_DATA_TEXT_FIELD, view.getString(PROPERTITY_DATA_TEXT_FIELD));
            }
            if (view.getString(PROPERTITY_SUGGESTION_OPERATOR) != null) {
                map.put(PROPERTITY_SUGGESTION_OPERATOR, view.getString(PROPERTITY_SUGGESTION_OPERATOR));
            }
            if (view.getString(PROPERTITY_OPERATOR) != null) {
                map.put(PROPERTITY_OPERATOR, view.getString(PROPERTITY_OPERATOR));
            }
        }
        return map;
    }

    public Map<String, Object> getCell() {
        return cell;
    }

    public void setCell(Map<String, Object> cell) {
        this.cell = cell;
    }

    public Boolean getMulti() {
        return multi;
    }

    public void setMulti(Boolean multi) {
        this.multi = multi;
    }

    public ReferenceType getDataSource() {
        return dataSource;
    }

    public void setDataSource(ReferenceType dataSource) {
        this.dataSource = dataSource;
    }

    public Boolean getCheckAll() {
        return checkAll;
    }

    public void setCheckAll(Boolean checkAll) {
        this.checkAll = checkAll;
    }

    public ReferenceType getItemTemplate() {
        return itemTemplate;
    }

    public void setItemTemplate(ReferenceType itemTemplate) {
        this.itemTemplate = itemTemplate;
    }

    public Map<Object, Object> getOperators() {
        return operators;
    }

    /**
     * @param operators
     */
    public void setOperators(Map<Object, Object> operators) {
        this.operators = operators;
    }

    public Boolean getSearch() {
        return search;
    }

    public void setSearch(Boolean search) {
        this.search = search;
    }

    public Boolean getIgnoreCase() {
        return ignoreCase;
    }

    public void setIgnoreCase(Boolean ignoreCase) {
        this.ignoreCase = ignoreCase;
    }

    public Object getUi() {
        return ui;
    }

    public void setUi(Object ui) {
        this.ui = ui;
    }
}
