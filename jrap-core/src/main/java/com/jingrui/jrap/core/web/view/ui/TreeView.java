package com.jingrui.jrap.core.web.view.ui;

import java.util.Map;

import com.jingrui.jrap.core.web.view.ReferenceType;
import com.jingrui.jrap.core.web.view.UITag;
import com.jingrui.jrap.core.web.view.ViewContext;
import com.jingrui.jrap.core.web.view.XMap;

/**
 * 
 * TreeView
 * 
 * @author hailin.xu@jingrui.com
 * 
 */
@UITag
public class TreeView extends Component {

    private static final String PROPERTITY_ANIMATION = "animation";
    private static final String PROPERTITY_AUTO_BIND = "autoBind";
    private static final String PROPERTITY_AUTO_SCROLL = "autoScroll";
    private static final String PROPERTITY_CHECKBOXES = "checkboxes";
    private static final String PROPERTITY_DATA_IMAGE_URL_FIELD = "dataImageUrlField";
    private static final String PROPERTITY_DATA_SOURCE = "dataSource";
    private static final String PROPERTITY_DATA_SPRITE_CSS_CLASS_FIELD = "dataSpriteCssClassField";
    private static final String PROPERTITY_DATA_TEXT_FIELD = "dataTextField";
    private static final String PROPERTITY_DATA_URL_FIELD = "dataUrlField";
    private static final String PROPERTITY_DRAG_AND_DROP = "dragAndDrop";
    private static final String PROPERTITY_LOAD_ON_DEMAND = "loadOnDemand";
    private static final String PROPERTITY_MESSAGES = "messages";
    private static final String PROPERTITY_TREE_LIST_VIEW = "treeListView";
    private static final String PROPERTITY_NAME = "name";
    private static final String PROPERTITY_TEMPLATE = "template";
    private static final String PROPERTITY_CHECK_CHILDREN = "checkChildren";

    private static final String PROPERTITY_CHANGE = "change";
    private static final String PROPERTITY_CHECK = "check";
    private static final String PROPERTITY_COLLAPSE = "collapse";
    private static final String PROPERTITY_DATA_BOUND = "dataBound";
    private static final String PROPERTITY_DRAGSTART = "dragstart";
    private static final String PROPERTITY_DRAG = "drag";
    private static final String PROPERTITY_DRAGEND = "dragend";
    private static final String PROPERTITY_DROP = "drop";
    private static final String PROPERTITY_EXPAND = "expand";
    private static final String PROPERTITY_NAVIGATE = "navigate";
    private static final String PROPERTITY_SELECT = "select";

    public static TreeView createInstance() {
        XMap view = new XMap(DEFAULT_TAG_PREFIX, DEFAULT_NAME_SPACE, PROPERTITY_TREE_LIST_VIEW);
        TreeView treeView = new TreeView();
        treeView.initPrototype(view);
        return treeView;
    }

    public Object getAnimation() {
        XMap animation = getPrototype().getChild(PROPERTITY_ANIMATION);
        if (animation != null) {
            return Animation.parseAnimation(animation);
        }
        if (getPrototype().getBoolean(PROPERTITY_ANIMATION) != null) {
            return getPrototype().getBoolean(PROPERTITY_ANIMATION);
        }
        return null;
    }

    public void setAnimation(Animation animation) {
        getPrototype().addChild(animation.toXMap());
    }

    public Boolean getAutoBind() {
        return getPrototype().getBoolean(PROPERTITY_AUTO_BIND);
    }

    public void setAutoBind(Boolean autoBind) {
        setPropertity(PROPERTITY_AUTO_BIND, autoBind);
    }

    public Boolean getAutoScroll() {
        return getPrototype().getBoolean(PROPERTITY_AUTO_SCROLL);
    }

    public void setAutoScroll(Boolean autoScroll) {
        setPropertity(PROPERTITY_AUTO_SCROLL, autoScroll);
    }

    public Object getCheckboxes() {
        Boolean checkboxes = getPrototype().getBoolean(PROPERTITY_CHECKBOXES);
        XMap checkboxesChild = getPrototype().getChild(PROPERTITY_CHECKBOXES);
        if (checkboxesChild != null) {
            Boolean checkChildren = checkboxesChild.getBoolean(PROPERTITY_CHECK_CHILDREN);
            String name = checkboxesChild.getString(PROPERTITY_NAME);
            Object template = checkboxesChild.getPropertity(PROPERTITY_TEMPLATE);
            if (checkChildren != null) {
                checkboxesChild.put(PROPERTITY_CHECK_CHILDREN, checkChildren);
            }
            if (name != null) {
                checkboxesChild.put(PROPERTITY_NAME, name);
            }
            if (template != null) {
                checkboxesChild.putPropertity(PROPERTITY_TEMPLATE, template);
            }
            return checkboxesChild;
        }
        if (checkboxes != null) {
            return getPrototype().getBoolean(PROPERTITY_CHECKBOXES);
        }
        return null;
    }

    public void setCheckboxes(Boolean checkboxes) {
        setPropertity(PROPERTITY_CHECKBOXES, checkboxes);
    }

    public void setCheckboxes(Map<String, Object> checkboxes) {
        XMap map = getPrototype().getChild(PROPERTITY_CHECKBOXES);
        if (map == null) {
            map = getPrototype().createChild(DEFAULT_TAG_PREFIX, DEFAULT_NAME_SPACE, PROPERTITY_CHECKBOXES);
        }
        if(checkboxes!=null){
            for (Map.Entry<String, Object> entry : checkboxes.entrySet()) {
                if (entry.getKey().equals(PROPERTITY_TEMPLATE)) {
                    if (entry.getValue() instanceof ReferenceType) {
                        map.putPropertity(entry.getKey(), entry.getValue());
                    } else {
                        map.put(entry.getKey(), entry.getValue());
                    }
                } else {
                    map.put(entry.getKey(), entry.getValue());
                }
            }
        }
        
    }

    public String getDataImageUrlField() {
        return getPrototype().getString(PROPERTITY_DATA_IMAGE_URL_FIELD);
    }

    public void setDataImageUrlField(String dataImageUrlField) {
        setPropertity(PROPERTITY_DATA_IMAGE_URL_FIELD, dataImageUrlField);
    }

    public String getDataSpriteCssClassField() {
        return getPrototype().getString(PROPERTITY_DATA_SPRITE_CSS_CLASS_FIELD);
    }

    public void setDataSpriteCssClassField(String dataSpriteCssClassField) {
        setPropertity(PROPERTITY_DATA_SPRITE_CSS_CLASS_FIELD, dataSpriteCssClassField);
    }

    public Object getDataTextField() {
        String dataTextField = getPrototype().getString(PROPERTITY_DATA_TEXT_FIELD);
        if (dataTextField != null) {
            String[] datas = dataTextField.split(",");
            for (int i = 0; i < datas.length; i++) {
                datas[i] = datas[i].trim();
            }
            return datas;
        }
        return null;
    }

    public void setDataTextField(String dataTextField) {
        setPropertity(PROPERTITY_DATA_TEXT_FIELD, dataTextField);
    }

    public String getDataUrlField() {
        return getPrototype().getString(PROPERTITY_DATA_URL_FIELD);
    }

    public void setDataUrlField(String dataUrlField) {
        setPropertity(PROPERTITY_DATA_URL_FIELD, dataUrlField);
    }

    public Boolean getDragAndDrop() {
        return getPrototype().getBoolean(PROPERTITY_DRAG_AND_DROP);
    }

    public void setDragAndDrop(Boolean dragAndDrop) {
        setPropertity(PROPERTITY_DRAG_AND_DROP, dragAndDrop);
    }

    public Boolean getLoadOnDemand() {
        return getPrototype().getBoolean(PROPERTITY_LOAD_ON_DEMAND);
    }

    public void setLoadOnDemand(Boolean loadOnDemand) {
        setPropertity(PROPERTITY_LOAD_ON_DEMAND, loadOnDemand);
    }

    public Object getMessages(XMap view) {
        if (view.getChild(PROPERTITY_MESSAGES) != null) {
            return view.getChild(PROPERTITY_MESSAGES);
        }
        return null;
    }

    public void setMessages(Map<String, String> messages) {
        XMap map = getPrototype().getChild(PROPERTITY_MESSAGES);
        if (map == null) {
            map = getPrototype().createChild(DEFAULT_TAG_PREFIX, DEFAULT_NAME_SPACE, PROPERTITY_MESSAGES);
        }
        if(messages!=null){
            for (Map.Entry<String, String> entry : messages.entrySet()) {
                map.put(entry.getKey(), entry.getValue());
            }  
        }
    }

    public Object getTemplate() {
        return getPropertity(PROPERTITY_TEMPLATE);
    }

    public void setTemplate(String template) {
        setPropertity(PROPERTITY_TEMPLATE, template);
    }

    public void setTemplate(ReferenceType template) {
        setPropertity(PROPERTITY_TEMPLATE, template);
    }

    public ReferenceType getDataSource() {
        return ReferenceType.create(getPrototype().getString(PROPERTITY_DATA_SOURCE));
    }

    public void setDataSource(ReferenceType dataSource) {
        setPropertity(PROPERTITY_DATA_SOURCE, dataSource);
    }

    protected void initPrototype(XMap view) {
        super.initPrototype(view);
        addEvents(PROPERTITY_CHANGE, PROPERTITY_CHECK, PROPERTITY_COLLAPSE, PROPERTITY_DATA_BOUND, PROPERTITY_DRAGSTART,
                PROPERTITY_DRAG, PROPERTITY_DRAGEND, PROPERTITY_DROP, PROPERTITY_NAVIGATE, PROPERTITY_SELECT,
                PROPERTITY_EXPAND);
    }

    public void init(XMap view, ViewContext context) throws Exception {
        super.init(view, context);
        context.addJsonConfig(PROPERTITY_ANIMATION, getAnimation());
        context.addJsonConfig(PROPERTITY_AUTO_BIND, getAutoBind());
        context.addJsonConfig(PROPERTITY_AUTO_SCROLL, getAutoScroll());
        context.addJsonConfig(PROPERTITY_CHECKBOXES, getCheckboxes());
        context.addJsonConfig(PROPERTITY_DATA_IMAGE_URL_FIELD, getDataImageUrlField());
        context.addJsonConfig(PROPERTITY_DATA_SPRITE_CSS_CLASS_FIELD, getDataSpriteCssClassField());
        context.addJsonConfig(PROPERTITY_DATA_TEXT_FIELD, getDataTextField());
        context.addJsonConfig(PROPERTITY_DATA_URL_FIELD, getDataUrlField());
        context.addJsonConfig(PROPERTITY_DRAG_AND_DROP, getDragAndDrop());
        context.addJsonConfig(PROPERTITY_LOAD_ON_DEMAND, getLoadOnDemand());
        context.addJsonConfig(PROPERTITY_MESSAGES, getMessages(view));
        context.addJsonConfig(PROPERTITY_TEMPLATE, getTemplate());
        context.addJsonConfig(PROPERTITY_DATA_SOURCE, getDataSource());
    }

}
