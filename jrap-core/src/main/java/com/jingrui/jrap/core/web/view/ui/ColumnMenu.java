package com.jingrui.jrap.core.web.view.ui;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jingrui.jrap.core.web.view.XMap;

/**
 * 
 * 共有类 行菜单columnMenu
 * 
 * @author hailin.xu@jingrui.com
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ColumnMenu {
    private Boolean columns;
    private Boolean filterable;
    private Boolean sortable;
    private Object messages;

    public XMap toXMap() {
        XMap map = new XMap(ViewTag.DEFAULT_TAG_PREFIX, ViewTag.DEFAULT_NAME_SPACE, "columnMenu");
        map.put("columns", getColumns());
        map.put("filterable", getFilterable());
        map.put("sortable", getSortable());
        XMap messages = new XMap(ViewTag.DEFAULT_TAG_PREFIX, ViewTag.DEFAULT_NAME_SPACE, "messages");
        Map<String, Object> message = (Map<String, Object>) getMessages();
        if(message!=null){
            for (String key : message.keySet()) {
                messages.put(key, message.get(key));
            }
        }
        map.addChild(messages);
        return map;
    }

    public Boolean getColumns() {
        return columns;
    }

    public void setColumns(Boolean columns) {
        this.columns = columns;
    }

    public Boolean getFilterable() {
        return filterable;
    }

    public void setFilterable(Boolean filterable) {
        this.filterable = filterable;
    }

    public Object getSortable() {
        return sortable;
    }

    public void setSortable(Boolean sortable) {
        this.sortable = sortable;
    }

    public Object getMessages() {
        return messages;
    }

    public void setMessages(Object messages) {
        this.messages = messages;
    }
}
