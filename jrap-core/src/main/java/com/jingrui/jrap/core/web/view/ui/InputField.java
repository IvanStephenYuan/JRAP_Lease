package com.jingrui.jrap.core.web.view.ui;

import java.util.List;
import java.util.Map;

import com.jingrui.jrap.core.web.view.ReferenceType;
import com.jingrui.jrap.core.web.view.ViewContext;
import com.jingrui.jrap.core.web.view.XMap;

/**
 * 
 * InputField.
 * 
 * @author hailin.xu@jingrui.com
 * @author njq.niu@jingrui.com
 * 
 */
public class InputField extends Component {

    public static final String PROPERTITY_CULTURE = "culture";
    private static final String PROPERTITY_VALUE = "value";
    private static final String PROPERTITY_CLEAR_BUTTON = "clearButton";
    private static final String PROPERTITY_PLACEHOLDER = "placeholder";
    private static final String PROPERTITY_CHANGE = "change";
    private static final String PROPERTITY_BIND = "bind";
    private static final String PROPERTITY_BINDMODEL= "bindModel";
    private static final String PROPERTITY_REQUIRED = "required";
    private static final String PROPERTITY_VALIDATION_MESSAGE = "validationMessage";
    private static final String PROPERTITY_NAME = "name";
    private static final String PROPERTITY_DATA_MSG = "dataMsg";
    
    public Object getValue() throws Exception{
        return getPrototype().get(PROPERTITY_VALUE);
    }

    public void setValue(Object value) {
        setPropertity(PROPERTITY_VALUE, value);
    }

    public Boolean getClearButton() {
        return getPrototype().getBoolean(PROPERTITY_CLEAR_BUTTON);
    }

    public void setClearButton(Boolean clearButton) {
        setPropertity(PROPERTITY_CLEAR_BUTTON, clearButton);
    }

    public String getPlaceholder() {
        return getPrototype().getString(PROPERTITY_PLACEHOLDER);
    }

    public void setPlaceholder(String placeholder) {
        setPropertity(PROPERTITY_PLACEHOLDER, placeholder);
    }

    public String getCulture() {
        return getPrototype().getString(PROPERTITY_CULTURE);
    }

    public void setCulture(String culture) {
        setPropertity(PROPERTITY_CULTURE, culture);
    }

    public String getBind() {
        String bind = getPrototype().getString(PROPERTITY_BIND);
        return bind;
    }

    public void setBind(String required) {
        setPropertity(PROPERTITY_BIND, required);
    }

    public String getBindModel() {
        String bindModel  = getPrototype().getString(PROPERTITY_BINDMODEL);
        if(bindModel!=null){
            ReferenceType rt = new ReferenceType(bindModel);
            return rt.getReference();
        }
        return null;
    }

    public void setBindModel(ReferenceType bindModel) {
        setPropertity(PROPERTITY_BINDMODEL, bindModel);
    }
    public String getValidationMessage() {
        if(getPrototype().getString(PROPERTITY_VALIDATION_MESSAGE)!=null){
            return  "validationMessage=\""+getPrototype().getString(PROPERTITY_VALIDATION_MESSAGE)+"\"";
        }
        return "";
    }

    public void setValidationMessage(String validationMessage) {
        setPropertity(PROPERTITY_VALIDATION_MESSAGE, validationMessage);
    }

    public String getName() {
        return getPrototype().getString(PROPERTITY_NAME);
    }

    public void setName(String name) {
        setPropertity(PROPERTITY_NAME, name);
    }
    
    public String getRequired() {
        Boolean required = getPrototype().getBoolean(PROPERTITY_REQUIRED);
        if (required != null && required) {
            return PROPERTITY_REQUIRED;
        }
        return "";
    }

    public void setRequired(Boolean bind) {
        setPropertity(PROPERTITY_REQUIRED, bind);
    }
    
    public String getDataMsg(){
        String dataMsg = "";
        XMap validation = getPrototype().getChild("validation");
        if(validation!=null){
            List<XMap> item = validation.getChildren();
            if(item!=null){
                for(XMap map : item){
                   String name = map.getString("name");
                   String msg = map.getString("msg");
                   if(name!=null && msg!=null){
                       dataMsg += "data-"+ name+"-msg =\""+msg+"\"  ";
                   }
                }
            }
        }
        return dataMsg;
    }
    
    public void setDataMsg(List<Map<String, String>> msg){
        XMap view = getPrototype().getChild("validation");
        if (view == null) {
            view = getPrototype().createChild(DEFAULT_TAG_PREFIX, DEFAULT_NAME_SPACE, "validation");
        }
        if(msg!=null){
            for(Map<String, String> m : msg){
                XMap item = view.createChild(DEFAULT_TAG_PREFIX, DEFAULT_NAME_SPACE, "item");
                if(m!=null){
                    for (Map.Entry<String, String> entry : m.entrySet()) {
                        item.put(entry.getKey(), entry.getValue());
                    }  
                }
            }
        }
    }
    protected void initPrototype(XMap view) {
        super.initPrototype(view);
        addEvent(PROPERTITY_CHANGE);
    }

    public void init(XMap view, ViewContext context) throws Exception {
        super.init(view, context);
        context.put(PROPERTITY_BIND, getBind());
        context.put(PROPERTITY_BINDMODEL, getBindModel());
        context.put(PROPERTITY_REQUIRED, getRequired());
        context.put(PROPERTITY_NAME, getName());
        context.put(PROPERTITY_DATA_MSG, getDataMsg());
        context.put(PROPERTITY_VALIDATION_MESSAGE, getValidationMessage());
        context.put(PROPERTITY_PLACEHOLDER, getPlaceholder());
        context.addJsonConfig(PROPERTITY_CULTURE, getCulture());
        context.addJsonConfig(PROPERTITY_PLACEHOLDER, getPlaceholder());
        context.addJsonConfig(PROPERTITY_VALUE, getValue());
        context.addJsonConfig(PROPERTITY_CLEAR_BUTTON, getClearButton());
    }
}
