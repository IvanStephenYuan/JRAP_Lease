package com.jingrui.jrap.core.web.view.ui;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jingrui.jrap.core.web.view.XMap;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class GridEditable {

    private Object confirmation;
    private String cancelDelete;
    private String confirmDelete;
    private String createAt;
    private Boolean destroy;
    private String mode;
    private Object template;
    private Boolean update;
    /**
     * TODO window
     */
    private static final String PROPERTITY_CONFIRMATION = "confirmation";
    private static final String PROPERTITY_CANCE_DELETE = "cancelDelete";
    private static final String PROPERTITY_CONFIRM_DELETE = "confirmDelete";
    private static final String PROPERTITY_CREATE_AT = "createAt";
    private static final String PROPERTITY_DESTROY = "destroy";
    private static final String PROPERTITY_MODE = "mode";
    private static final String PROPERTITY_TEMPLATE = "template";
    private static final String PROPERTITY_UPDATE = "update";
    private static final String PROPERTITY_EDITABLE = "editable";

    public static GridEditable parseEditable(XMap view) {
        GridEditable editable = new GridEditable();
        if (view != null) {
            String confirmation = view.getString(PROPERTITY_CONFIRMATION);
            if (confirmation != null) {
                if (confirmation.equals("true") || confirmation.equals("false")) {
                    editable.setConfirmation(confirmation);
                }
            } else {
                editable.setConfirmation(view.getPropertity(PROPERTITY_CONFIRMATION));
            }
            editable.setCancelDelete(view.getString(PROPERTITY_CANCE_DELETE));
            editable.setConfirmDelete(view.getString(PROPERTITY_CONFIRM_DELETE));
            editable.setCreateAt(view.getString(PROPERTITY_CREATE_AT));
            editable.setDestroy(view.getBoolean(PROPERTITY_DESTROY));
            editable.setMode(view.getString(PROPERTITY_MODE));
            editable.setTemplate(view.getPropertity(PROPERTITY_TEMPLATE));
            editable.setUpdate(view.getBoolean(PROPERTITY_UPDATE));
        }
        return editable;
    }

    public XMap toXMap() {
        XMap map = new XMap(ViewTag.DEFAULT_TAG_PREFIX, ViewTag.DEFAULT_NAME_SPACE, PROPERTITY_EDITABLE);
        map.put(PROPERTITY_CANCE_DELETE, getCancelDelete());
        map.put(PROPERTITY_CONFIRM_DELETE, getConfirmDelete());
        map.put(PROPERTITY_CREATE_AT, getCreateAt());
        map.put(PROPERTITY_DESTROY, getDestroy());
        map.put(PROPERTITY_MODE, getMode());
        map.put(PROPERTITY_TEMPLATE, getTemplate());
        map.put(PROPERTITY_UPDATE, getUpdate());
        return map;
    }

    public Object getConfirmation() {
        return confirmation;
    }

    /**
     * 
     * ReferenceType类型和String类型
     * 
     * @param confirmation
     */
    public void setConfirmation(Object confirmation) {
        this.confirmation = confirmation;
    }

    public void setConfirmation(Boolean confirmation) {
        this.confirmation = confirmation;
    }

    public String getCancelDelete() {
        return cancelDelete;
    }

    public void setCancelDelete(String cancelDelete) {
        this.cancelDelete = cancelDelete;
    }

    public String getConfirmDelete() {
        return confirmDelete;
    }

    public void setConfirmDelete(String confirmDelete) {
        this.confirmDelete = confirmDelete;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public Boolean getDestroy() {
        return destroy;
    }

    public void setDestroy(Boolean destroy) {
        this.destroy = destroy;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public Object getTemplate() {
        return template;
    }

    public void setTemplate(Object template) {
        this.template = template;
    }

    public Boolean getUpdate() {
        return update;
    }

    public void setUpdate(Boolean update) {
        this.update = update;
    }
}
