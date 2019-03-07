package com.jingrui.jrap.core.web.view.ui;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jingrui.jrap.core.web.view.XMap;

/**
 * Grid - Filterable
 * 
 * @author hailin.xu@jingrui.com
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GridFilterable {

    private Boolean extra;
    private Object messages;
    private Object operators;
    private String mode;

    private static final String PROPERTITY_FILTERABLE = "filterable";
    private static final String PROPERTITY_EXTRA = "extra";
    private static final String PROPERTITY_MESSAGES = "messages";
    private static final String PROPERTITY_OPERATORS = "operators";
    private static final String PROPERTITY_MODE = "mode";

    public static GridFilterable parseFilterable(XMap view) {
        GridFilterable filterable = new GridFilterable();
        XMap map = view.getChild(PROPERTITY_FILTERABLE);
        if (map != null) {
            filterable.setExtra(map.getBoolean(PROPERTITY_EXTRA));
            filterable.setMessages(map.getChild(PROPERTITY_MESSAGES));
            filterable.setOperators(map.getChild(PROPERTITY_OPERATORS).getChildren());
            filterable.setMode(map.getString(PROPERTITY_MODE));
            return filterable;
        }
        return null;
    }

    public Boolean getExtra() {
        return extra;
    }

    public void setExtra(Boolean extra) {
        this.extra = extra;
    }

    public Object getMessages() {
        return messages;
    }

    public void setMessages(Object messages) {
        this.messages = messages;
    }

    public Object getOperators() {
        return operators;
    }

    public void setOperators(Object operators) {
        this.operators = operators;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }
}
