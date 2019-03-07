package com.jingrui.jrap.core.web.view.ui;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jingrui.jrap.core.web.view.XMap;

/**
 * Grid - Pageable
 * 
 * @author hailin.xu@jingrui.com
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GridPageable {

    private Integer pageSize;
    private Boolean previousNext;
    private Boolean numeric;
    private Integer buttonCount;
    private Boolean input;
    private Object pageSizes;
    private Boolean refresh;
    private Boolean info;
    private Map<Object, Object> messages;

    private static final String PROPERTITY_PAGEABLE = "pageable";
    private static final String PROPERTITY_PAGE_SIZE = "pageSize";
    private static final String PROPERTITY_PREVIOUS_NEXT = "previousNext";
    private static final String PROPERTITY_NUMERIC = "numeric";
    private static final String PROPERTITY_BUTTON_COUNT = "buttonCount";
    private static final String PROPERTITY_INPUT = "input";
    private static final String PROPERTITY_PAGE_SIZES = "pageSizes";
    private static final String PROPERTITY_REFRESH = "refresh";
    private static final String PROPERTITY_INFO = "info";
    private static final String PROPERTITY_MESSAGES = "messages";

    public static GridPageable parsePageable(XMap map) {
        GridPageable pageable = new GridPageable();
        if (map != null) {
            pageable.setPageSize(map.getInteger(PROPERTITY_PAGE_SIZE));
            pageable.setPreviousNext(map.getBoolean(PROPERTITY_PREVIOUS_NEXT));
            pageable.setNumeric(map.getBoolean(PROPERTITY_NUMERIC));
            pageable.setButtonCount(map.getInteger(PROPERTITY_BUTTON_COUNT));
            pageable.setInput(map.getBoolean(PROPERTITY_INPUT));
            String pageSizes = map.getString(PROPERTITY_PAGE_SIZES);
            if (pageSizes != null) {
                if (pageSizes.equals("true") || pageSizes.equals("false")) {
                    pageable.setPageSizes(map.getBoolean(PROPERTITY_PAGE_SIZES));
                } else {
                    pageable.setPageSizes(map.getString(PROPERTITY_PAGE_SIZES));
                }
            }
            pageable.setRefresh(map.getBoolean(PROPERTITY_REFRESH));
            pageable.setInfo(map.getBoolean(PROPERTITY_INFO));
            pageable.setMessages(map.getChild(PROPERTITY_MESSAGES));
            return pageable;
        }
        return null;
    }

    public XMap toXMap() {
        XMap map = new XMap(ViewTag.DEFAULT_TAG_PREFIX, ViewTag.DEFAULT_NAME_SPACE, PROPERTITY_PAGEABLE);
        if (getPageSizes() != null) {
            if (getPageSizes() instanceof Boolean) {
                map.put(PROPERTITY_PAGE_SIZES, getPageSizes());
            } else {
                map.put(PROPERTITY_PAGE_SIZES, String.join(",", getPageSizes().toString()));
            }
        }
        map.put(PROPERTITY_PREVIOUS_NEXT, getPreviousNext());
        map.put(PROPERTITY_NUMERIC, getNumeric());
        map.put(PROPERTITY_BUTTON_COUNT, getButtonCount());
        map.put(PROPERTITY_INPUT, getInput());
        map.put(PROPERTITY_PAGE_SIZE, getPageSize());
        map.put(PROPERTITY_REFRESH, getRefresh());
        map.put(PROPERTITY_INFO, getInfo());
        XMap messages = new XMap(ViewTag.DEFAULT_TAG_PREFIX, ViewTag.DEFAULT_NAME_SPACE, PROPERTITY_MESSAGES);
        if (getMessages() != null) {
            for (Object key : getMessages().keySet()) {
                messages.put(key, getMessages().get(key));
            }
        }
        map.addChild(messages);
        return map;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Boolean getPreviousNext() {
        return previousNext;
    }

    public void setPreviousNext(Boolean previousNext) {
        this.previousNext = previousNext;
    }

    public Boolean getNumeric() {
        return numeric;
    }

    public void setNumeric(Boolean numeric) {
        this.numeric = numeric;
    }

    public Integer getButtonCount() {
        return buttonCount;
    }

    public void setButtonCount(Integer buttonCount) {
        this.buttonCount = buttonCount;
    }

    public Boolean getInput() {
        return input;
    }

    public void setInput(Boolean input) {
        this.input = input;
    }

    public Object getPageSizes() {
        return pageSizes;
    }

    public void setPageSizes(String pageSizes) {
        if (pageSizes != null) {
            String[] data = pageSizes.split(",");
            int length = data.length;
            Object[] datas = new Object[length];
            for (int i = 0; i < length; i++) {
                try {
                    datas[i] = Integer.parseInt(data[i].trim());
                } catch (Exception e) {
                    datas[i] = data[i].trim();
                }
            }
            this.pageSizes = datas;
        }
    }

    public void setPageSizes(Boolean pageSizes) {
        this.pageSizes = pageSizes;
    }

    public Boolean getRefresh() {
        return refresh;
    }

    public void setRefresh(Boolean refresh) {
        this.refresh = refresh;
    }

    public Boolean getInfo() {
        return info;
    }

    public void setInfo(Boolean info) {
        this.info = info;
    }

    public Map<Object, Object> getMessages() {
        return messages;
    }

    public void setMessages(Map<Object, Object> messages) {
        this.messages = messages;
    }

}
