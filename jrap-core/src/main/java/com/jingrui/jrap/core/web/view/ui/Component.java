package com.jingrui.jrap.core.web.view.ui;

import java.util.ArrayList;
import java.util.List;

import com.jingrui.jrap.core.BaseConstants;
import com.jingrui.jrap.core.web.view.IDGenerator;
import com.jingrui.jrap.core.web.view.ReferenceType;
import com.jingrui.jrap.core.web.view.ViewContext;
import com.jingrui.jrap.core.web.view.XMap;

/**
 * 
 * 组件基础类.
 * 
 * @author njq.niu@jingrui.com
 *
 */
public class Component extends ViewTag {

    private static final String CONFIG = "config";

    public static final String PROPERTITY_ID = "id";
    
    public static final String PROPERTITY_EVENTS = "events";
    public static final String PROPERTITY_EVENT = "event";
    public static final String PROPERTITY_EVENT_NAME = "name";
    public static final String PROPERTITY_EVENT_HANDLER = "handler";
    
    private static final String PROPERTITY_STYLE = "style";
    private static final String PROPERTITY_CLASSNAME = "class";
    
    protected String getDefaultClass(XMap view, ViewContext context) {
        return "";
    }
    
    private XMap prototype;

    public XMap getPrototype() {
        return prototype;
    }

    protected void initPrototype(XMap obj) {
        this.prototype = obj;
    }
    
    protected Object getPropertity(String key){
        return getPrototype().getPropertity(key);
    }
    
//    protected void setPropertity(String key, ReferenceType referenceType){
//        setPropertity(key,  BaseConstants.xml_data_type_function + referenceType.getReference());
//    }
    
    
    protected void setPropertity(Object key, Object value) {
        XMap prototype = getPrototype();
        if (prototype != null) {
            prototype.putPropertity(key, value);
        }
    }

    public String getId() {
        String id = getPrototype().getString(PROPERTITY_ID);
        if (id == null) {
            id = "H" + IDGenerator.getInstance().generate();
        }
        return id;
    }

    public void setId(String id) {
        setPropertity(PROPERTITY_ID, id);
    }

    public List<Event> getEvents() {
        List<Event> events = new ArrayList<>();
        XMap evts = getPrototype().getChild(PROPERTITY_EVENTS);
        if (evts != null && evts.getChildren() != null)
            evts.getChildren().forEach(event -> {
                Event evt = new Event();
                evt.setName(event.getString(PROPERTITY_EVENT_NAME));
                evt.setHandler(event.getString(PROPERTITY_EVENT_HANDLER));
                events.add(evt);
            });
        return events;
    }

    public void addEvents(String...events){
        for (String event : events) {
            addEvent(event);
        }
    }
    
    public void addEvent(String name){
        String handler = getPrototype().getString(name);
        if (handler != null) {
            addEvent(name, handler);
        }
    }
    
    public void addEvent(String name, String handler) {
        XMap prototype = getPrototype();
        if (prototype != null) {
            XMap events = prototype.getChild(PROPERTITY_EVENTS);
            if (events == null) {
                events = new XMap(DEFAULT_TAG_PREFIX, DEFAULT_NAME_SPACE, PROPERTITY_EVENTS);
                prototype.addChild(events);
            } 
            boolean hasSameEvent = false;
            List<XMap> list = events.getChildren();
            if (list != null) {
                for (XMap evt : list) {
                    String eventName = evt.getString(PROPERTITY_EVENT_NAME, "");
                    if (eventName.equals(name)) {
                        hasSameEvent = true;
                        break;
                    }
                }
            }
            if(!hasSameEvent){
                XMap xevt = new XMap(DEFAULT_TAG_PREFIX, DEFAULT_NAME_SPACE, PROPERTITY_EVENT);
                xevt.put(PROPERTITY_EVENT_NAME, name);
                xevt.put(PROPERTITY_EVENT_HANDLER, handler);
                events.addChild(xevt);
            }
        }
    }

    public String getStyle(){
        return getPrototype().getString(PROPERTITY_STYLE);
    }
    
    public void setStyle(String style){
        setPropertity(PROPERTITY_STYLE, style);
    }
    
    public String getClassName(){
        return getPrototype().getString(PROPERTITY_CLASSNAME);
    }
    
    public void setClassName(String className){
        setPropertity(PROPERTITY_CLASSNAME, className);
    }
    
    public void init(XMap view, ViewContext context) throws Exception {
        initPrototype(view);
        
        /***************** ID属性 *****************/
        String id = getId();
        context.put(PROPERTITY_ID, id);
        
        
        context.put(PROPERTITY_STYLE, getStyle());
        context.put(PROPERTITY_CLASSNAME, getClassName());
        /***************** 注册事件 *****************/
        getEvents().forEach(event -> {
            context.addJsonConfig(event.getName(), event.getHandler());
        });
    }

    public String execute(XMap view, ViewContext context) throws Exception {
        context.put(Component.CONFIG, context.getJsonConfig());
        String template = getViewTemplate();
        return super.build(context, template == null ? (getClass().getSimpleName()+ ".ftl") : template);
    }

    public String getViewTemplate() {
        return null;
    }

    public static class Event {

        private String name;
        private ReferenceType handler;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public ReferenceType getHandler() {
            return handler;
        }

        public void setHandler(String handler) {
            this.handler = new ReferenceType(handler);
        }

    }
}
