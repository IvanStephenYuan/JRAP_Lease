package com.jingrui.jrap.core.web.view.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jingrui.jrap.core.web.view.ReferenceType;
import com.jingrui.jrap.core.web.view.XMap;

/**
 * 
 * 行
 * 
 * @author hailin.xu@jingrui.com
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Column {
    
    private Map<Object, Object> attributes;
    private Object command;
    private ReferenceType editor;
    private Boolean encoded;
    private Boolean expandable;
    private String field;
    private Object footerTemplate;
    private String format;
    private Map<Object, Object> headerAttributes;
    private Object headerTemplate;
    private Integer minScreenWidth;
    private Object sortable;
    private Object template;
    private String title;
    private Object width;
    private Boolean hidden;
    private Boolean menu;
    private Boolean locked;
    private Boolean lockable;

    public static final String PROPERTITY_FILTERABLE = "filterable";
    public static final String PROPERTITY_SORTABLE = "sortable";
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

    public void parseColumn(XMap view) {
        if (view != null) {
            this.setAttributes(view.getChild(PROPERTITY_ATTRIBUTES));
            if (view.getChild(PROPERTITY_COMMAND) != null) {
                this.setCommand(parseCommand(view.getChild(PROPERTITY_COMMAND)));
            } else {
                this.setCommand(toArray(view.getString(PROPERTITY_COMMAND)));
            }
            if (view.getString(PROPERTITY_EDITOR) != null) {
                this.setEditor(new ReferenceType(view.getString(PROPERTITY_EDITOR)));
            }
            this.setEncoded(view.getBoolean(PROPERTITY_ENCODED));
            this.setExpandable(view.getBoolean(PROPERTITY_EXPANDABLE));
            this.setField(view.getString(PROPERTITY_FIELD));
            this.setFooterTemplate(view.getPropertity(PROPERTITY_FOORER_TEMPLATE));
            this.setFormat(view.getString(PROPERTITY_FORMAT));
            this.setHeaderAttributes(view.getChild(PROPERTITY_HEADER_ATTRIBUTES));
            this.setHeaderTemplate(view.getPropertity(PROPERTITY_HEADER_TEMPLATE));
            this.setMinScreenWidth(view.getInteger(PROPERTITY_MIN_SCREEN_WIDTH));
            if (view.getChild(PROPERTITY_SORTABLE) != null) {
                this.setSortable(view.getChild(PROPERTITY_SORTABLE));
            } else {
                this.setSortable(view.getBoolean(PROPERTITY_SORTABLE));
            }
            if(view.getString(PROPERTITY_TEMPLATE)!=null){
                this.setTemplate(new ReferenceType(view.getString(PROPERTITY_TEMPLATE)));
            }else if(view.getChild(PROPERTITY_TEMPLATE)!=null){
                this.setTemplate(view.getChild(PROPERTITY_TEMPLATE).getText());
            }
            this.setTitle(view.getString(PROPERTITY_TITLE));
            this.setWidth(view.getString(PROPERTITY_WIDTH));
            this.setHidden(view.getBoolean(PROPERTITY_HIDDEN));
            this.setMenu(view.getBoolean(PROPERTITY_MENU));
            this.setLocked(view.getBoolean(PROPERTITY_LOCKED));
            this.setLockable(view.getBoolean(PROPERTITY_LOCKABLE));
        }
    }

    /**
     * @param map
     */
    public void toXMap(XMap map) {
        XMap attributes = new XMap(ViewTag.DEFAULT_TAG_PREFIX, ViewTag.DEFAULT_NAME_SPACE,"attributes");
        if (getAttributes() != null) {
            for (Map.Entry<Object, Object> entry : getAttributes().entrySet()) {
                attributes.put(entry.getKey(), entry.getValue());
            }
            map.addChild(attributes);
        }
        XMap commands = new XMap(ViewTag.DEFAULT_TAG_PREFIX, ViewTag.DEFAULT_NAME_SPACE, PROPERTITY_COMMAND);
        if (getCommand() != null) {
            if (getCommand() instanceof String) {
                map.put(PROPERTITY_COMMAND, getCommand());
            } else if (getCommand() instanceof String[]) {
                map.put(PROPERTITY_COMMAND, String.join(",", getCommand().toString()));
            } else {
                List<ColumnCommand> list = (List<ColumnCommand>)getCommand();
                if(list!=null){
                    for (ColumnCommand command : list) {              
                        commands.addChild(command.toXMap());
                    }
                    map.addChild(commands);
                }
            }
        }
        map.put(PROPERTITY_EDITOR, getEditor());
        map.put(PROPERTITY_ENCODED, getEncoded());
        map.put(PROPERTITY_EXPANDABLE, getExpandable());
        map.put(PROPERTITY_FIELD, getField());
        map.put(PROPERTITY_FOORER_TEMPLATE, getFooterTemplate());
        map.put(PROPERTITY_FORMAT, getFormat());
        map.put(PROPERTITY_HEADER_ATTRIBUTES, getHeaderAttributes());
        map.put(PROPERTITY_HEADER_TEMPLATE, getHeaderTemplate());
        map.put(PROPERTITY_MIN_SCREEN_WIDTH, getMinScreenWidth());
        if (getSortable() instanceof Boolean) {
            map.put(PROPERTITY_SORTABLE, getSortable());
        } else {
            XMap sortable = new XMap(ViewTag.DEFAULT_TAG_PREFIX, ViewTag.DEFAULT_NAME_SPACE,PROPERTITY_SORTABLE);
            Map<Object, Object> s = (Map<Object, Object>) getSortable();
            if (s != null) {
                for (Map.Entry<Object, Object> entry : s.entrySet()) {
                    sortable.put(entry.getKey(), entry.getValue());
                }
                map.addChild(sortable);
            }
        }
        if(getTemplate() instanceof ReferenceType){
            map.putPropertity(PROPERTITY_TEMPLATE, getTemplate());
        }else if(getTemplate() instanceof String){
            XMap template = new XMap(ViewTag.DEFAULT_TAG_PREFIX, ViewTag.DEFAULT_NAME_SPACE,PROPERTITY_TEMPLATE);
            template.setText(getTemplate().toString());
            map.addChild(template);
        }   
        map.put(PROPERTITY_TITLE, getTitle());
        map.put(PROPERTITY_WIDTH, getWidth());
        map.put(PROPERTITY_HIDDEN, getHidden());
        map.put(PROPERTITY_MENU, getMenu());
        map.put(PROPERTITY_LOCKED, getLocked());
        map.put(PROPERTITY_LOCKABLE, getLockable());
    }

    public Map<Object, Object> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<Object, Object> attributes) {
        this.attributes = attributes;
    }

    public static List<ColumnCommand> parseCommand(XMap map) {
        List<ColumnCommand> list = new ArrayList<>();
        List<XMap> commands = map.getChildren();
        if(commands!=null){
            for (XMap m : commands) { 
                list.add(ColumnCommand.parseCommand(m));
            }
        }
        return list;
    }

    public Object getCommand() {
        return command;
    }

    public String[] toArray(String value) {
        if (value != null) {
            String[] datas = value.split(",");
            for (int i = 0; i < datas.length; i++) {
                datas[i] = datas[i].trim();
            }
            return datas;
        }
        return null;
    }

    public void setCommand(String[] command) {
        this.command = command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public void setCommand(List<ColumnCommand> command) {
        this.command = command;
    }

    public ReferenceType getEditor() {
        return editor;
    }

    public void setEditor(ReferenceType editor) {
        this.editor = editor;
    }

    public Boolean getEncoded() {
        return encoded;
    }

    public void setEncoded(Boolean encoded) {
        this.encoded = encoded;
    }

    public Boolean getExpandable() {
        return expandable;
    }

    public void setExpandable(Boolean expandable) {
        this.expandable = expandable;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public Object getFooterTemplate() {
        return footerTemplate;
    }

    public void setFooterTemplate(Object footerTemplate) {
        this.footerTemplate = footerTemplate;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public Map<Object, Object> getHeaderAttributes() {
        return headerAttributes;
    }

    public void setHeaderAttributes(Map<Object, Object> headerAttributes) {
        this.headerAttributes = headerAttributes;
    }

    public Object getHeaderTemplate() {
        return headerTemplate;
    }

    public void setHeaderTemplate(Object headerTemplate) {
        this.headerTemplate = headerTemplate;
    }

    public Integer getMinScreenWidth() {
        return minScreenWidth;
    }

    public void setMinScreenWidth(Integer minScreenWidth) {
        this.minScreenWidth = minScreenWidth;
    }

    public Object getSortable() {
        return sortable;
    }

    public void setSortable(Boolean sortable) {
        this.sortable = sortable;
    }

    public void setSortable(Map<Object, Object> sortable) {
        this.sortable = sortable;
    }

    public Object getTemplate() {
        return template;
    }

    public void setTemplate(ReferenceType template) {
        this.template = template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Object getWidth() {
        if (width != null) {
            try {
                width = Integer.parseInt(width.toString());
            } catch (Exception e) {
                return width;
            }
        }
        return width;
    }

    public void setWidth(Object width) {
        if (width != null) {
            try {
                Integer.parseInt(width.toString());
                setWidth(Integer.parseInt(width.toString()));
            } catch (Exception e) {
                setWidth(width.toString());
            }
        }
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public Boolean getHidden() {
        return hidden;
    }

    public void setHidden(Boolean hidden) {
        this.hidden = hidden;
    }

    public Boolean getMenu() {
        return menu;
    }

    public void setMenu(Boolean menu) {
        this.menu = menu;
    }

    public Boolean getLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    public Boolean getLockable() {
        return lockable;
    }

    public void setLockable(Boolean lockable) {
        this.lockable = lockable;
    }

}
