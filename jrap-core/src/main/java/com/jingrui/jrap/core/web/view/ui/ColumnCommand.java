package com.jingrui.jrap.core.web.view.ui;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.jingrui.jrap.core.web.view.ReferenceType;
import com.jingrui.jrap.core.web.view.XMap;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ColumnCommand {
    private String name;
    private Object text;
    private String className;
    private ReferenceType click;
    private Object template;
    
    private static final String PROPERTITY_NAME = "name";
    private static final String PROPERTITY_CLASS_NAME = "className";
    private static final String PROPERTITY_TEXT = "text";
    private static final String PROPERTITY_CLICK = "click";
    private static final String PROPERTITY_TEMPLATE = "template";
    
    public static ColumnCommand parseCommand(XMap view) {
        ColumnCommand command = new ColumnCommand();
        if (view != null) {
            command.setName(view.getString(PROPERTITY_NAME));
            command.setClassName(view.getString(PROPERTITY_CLASS_NAME));
            XMap text = view.getChild(PROPERTITY_TEXT);
            if(text!=null){
                Map<String, String> map = new HashMap<>();
                for(Object key:text.keySet()){
                    map.put(key.toString(),text.get(key).toString());
                }
                command.setText(map);
            }else{
                command.setText(view.getString(PROPERTITY_TEXT));
            }
            if(view.getString(PROPERTITY_CLICK)!=null){
                command.setClick(new ReferenceType(view.getString(PROPERTITY_CLICK)));
            }
            if (view.getString(PROPERTITY_TEMPLATE) != null) {
                command.setTemplate(new ReferenceType(PROPERTITY_TEMPLATE));
            }
            if(view.getChild(PROPERTITY_TEMPLATE)!=null){
                command.setTemplate(view.getChild(PROPERTITY_TEMPLATE).getText());
            }
            return command;
        }
        return null;
    }
    
    public XMap toXMap() {
        XMap map = new XMap(ViewTag.DEFAULT_TAG_PREFIX, ViewTag.DEFAULT_NAME_SPACE, "item");
        map.put(PROPERTITY_NAME,getName());
        map.put(PROPERTITY_CLASS_NAME, getClassName());
        if(getText() instanceof String){
            map.put(PROPERTITY_TEXT, getText());
        }else if(getText() instanceof Map){
            XMap text = new XMap(ViewTag.DEFAULT_TAG_PREFIX, ViewTag.DEFAULT_NAME_SPACE, PROPERTITY_TEXT);
            Map<String, String> child = (Map<String, String>) getText();
            if(child!=null){
                for(String s :child.keySet()){
                    text.put(s, child.get(s));
                }  
            }
            map.addChild(text);
        }
        map.put(PROPERTITY_CLICK, getClick());
        if(getTemplate() instanceof String){
            XMap template = new XMap(ViewTag.DEFAULT_TAG_PREFIX, ViewTag.DEFAULT_NAME_SPACE, PROPERTITY_TEMPLATE);
            template.setText(getTemplate().toString());
            map.addChild(template);
        }else if(getTemplate() instanceof ReferenceType){
            map.putPropertity(PROPERTITY_TEMPLATE, getTemplate());
        }
        return map;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public Object getText() {
        return text;
    }
    
    public void setText(String text) {
        this.text = text;
    }
    
    public void setText(Map<String, String> text) {
        this.text = text;
    }
    
    public String getClassName() {
        return className;
    }
    
    public void setClassName(String className) {
        this.className = className;
    }
    
    public ReferenceType getClick() {
        return click;
    }
    
    public void setClick(ReferenceType click) {
        this.click = click;
    }

    public Object getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }
    
    public void setTemplate(ReferenceType template) {
        this.template = template;
    }
}

