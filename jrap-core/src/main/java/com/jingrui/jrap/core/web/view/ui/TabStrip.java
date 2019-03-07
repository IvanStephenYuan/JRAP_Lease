package com.jingrui.jrap.core.web.view.ui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.jingrui.jrap.core.web.view.ScreenBuilder;
import com.jingrui.jrap.core.web.view.UITag;
import com.jingrui.jrap.core.web.view.ViewContext;
import com.jingrui.jrap.core.web.view.XMap;

/**
 * TabStrip
 * 
 * @author zhizheng.yang@jingrui.com
 *
 */

@UITag
public class TabStrip extends Component {

    private static final String PROPERTITY_ANIMATION = "animation";
    private static final String PROPERTITY_NAVIGATABLE = "navigatable";
    private static final String PROPERTITY_SCROLLABLE = "scrollable";
    private static final String PROPERTITY_DISTANCE = " distance";
    private static final String PROPERTITY_TAB_POSITION = "tabPosition";
    private static final String PROPERTITY_VALUE = "value";
    private static final String PROPERTITY_COLLAPSIBLE = "collapsible";
    private static final String PROPERTITY_TABS = "tabs";
    private static final String PROPERTITY_TAB = "tab";
    private static final String PROPERTIY_TITLE = "title";
    private static final String PROPERTITY_TAB_CONTENT = "tabContent";
    private static final String PROPERTITY_CONTENT_URLS = "contentUrls";
    private static final String PROPERTITY_DATA_CONTENT_FIELD = "dataContentField";
    private static final String PROPERTITY_DATA_CONTENT_URL_FIELD = "dataContentUrlField";
    private static final String PROPERTITY_DATA_IMAGE_URL_FIELD = "dataImageUrlField";
    private static final String PROPERTITY_DATA_SPRITE_CSS_CLASS = "dataSpriteCssClass";
    private static final String PROPERTITY_DATA_TEXT_FIELD = "dataTextField";
    private static final String PROPERTITY_DATA_URL_FIELD = "dataUrlField";
    private static final String PROPERTITY_DATA_CONTENT_IFRAME = "dataContentIframe";
    private static final String PROPERTITY_DATA_IFRAME_ID_FIELD = "dataIframeIdField";
    private static final String PROPERTITY_DATA_CLOSE_ICON_FIELD = "dataCloseIconField";
    private static final String PROPERTITY_CLOSE_ICON = "closeIcon";
    private static final String PROPERTITY_CONTEXT_MENU = "contextMenu";

    private static final String PROPERTITY_ACTIVATE = "activate";
    private static final String PROPERTITY_CONTENT_LOAD = "contentLoad";
    private static final String PROPERTITY_ERROR = "error";
    private static final String PROPERTITY_SELECT = "select";
    private static final String PROPERTITY_CHANGE = "change";
    private static final String PROPERTITY_DATABINDING = "dataBinding";
    private static final String PROPERTITY_DATABOUND = "dataBound";
    private static final String PROPERTITY_SHOW = "show";
    private static final String PROPERTITY_DATASOURCE = "dataSource";

    public static TabStrip createInstance() {
        XMap view = new XMap(DEFAULT_TAG_PREFIX, DEFAULT_NAME_SPACE, "tabStrip");
        TabStrip tabStrip = new TabStrip();
        tabStrip.initPrototype(view);
        return tabStrip;
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

    public String getTab() {
        XMap tabs = getPrototype().getChild(PROPERTITY_TABS);
        StringBuilder sb = new StringBuilder();
        if (tabs != null) {
            List<XMap> maps = tabs.getChildren();
            if (maps != null) {
                for (XMap tab : maps) {
                    sb.append("<li>");
                    sb.append(tab.getString(PROPERTIY_TITLE));
                    sb.append("</li>");
                }
            }
        }
        return sb.toString();
    }

    /**
     * setTab
     * 
     * @param datas
     *            List<String>:List of tab's title
     */
    public void setTab(List<String> datas) {
        XMap tabs = getPrototype().getChild(PROPERTITY_TABS);
        if (tabs == null) {
            tabs = getPrototype().createChild(PROPERTITY_TABS);
        }
        List<XMap> tab = new ArrayList<XMap>();
        for (String value : datas) {
            XMap x = new XMap(DEFAULT_TAG_PREFIX, DEFAULT_NAME_SPACE, PROPERTITY_TAB);
            x.put(PROPERTIY_TITLE, value);
            tab.add(x);
        }
        tabs.addChild(tab);
    }

    public String getTabContent(ViewContext context) throws Exception {
        StringBuffer sb = new StringBuffer();
        XMap tabs = getPrototype().getChild(PROPERTITY_TABS);
        if (tabs != null) {
            List<XMap> maps = tabs.getChildren();
            if (maps != null) {
                for (XMap tab : maps) {
                    sb.append("<div>");
                    List<XMap> children = tab.getChildren();
                    if (children != null) {
                        Iterator<XMap> it = children.iterator();
                        while (it.hasNext()) {
                            XMap child = it.next();
                            sb.append(ScreenBuilder.build(child, context));
                        }
                    }
                    sb.append("</div>");
                }
            }
        }
        return sb.toString();
    }

    /**
     * setTabContent
     * 
     * @param Map<Object,List<XMap>>
     *            elements Object:the title of tab List<XMap>:Contents list of
     *            the tab
     */
    public void setTabContent(Map<Object, List<XMap>> elements) {
        XMap tabs = getPrototype().getChild(PROPERTITY_TABS);
        if (tabs != null) {
            List<XMap> tabList = tabs.getChildren();
            for (XMap xMap : tabList) {
                for (Map.Entry<Object, List<XMap>> entry : elements.entrySet()) {
                    if (entry.getKey() == xMap.get(PROPERTIY_TITLE)) {
                        for (XMap map : entry.getValue()) {
                            xMap.addChild(map);
                        }
                    }
                }
            }
        }
    }

    public Boolean getContextMenu() {
        return getPrototype().getBoolean(PROPERTITY_CONTEXT_MENU);
    }

    public void setContextMenu(Boolean contextMenu) {
        setPropertity(PROPERTITY_CONTEXT_MENU, contextMenu);
    }

    public Boolean getCloseIcon() {
        return getPrototype().getBoolean(PROPERTITY_CLOSE_ICON);
    }

    public void setCloseIcon(Boolean closeIcon) {
        setPropertity(PROPERTITY_CLOSE_ICON, closeIcon);
    }

    public String getDataCloseIconField() {
        return getPrototype().getString(PROPERTITY_DATA_CLOSE_ICON_FIELD);
    }

    public void setDataCloseIconField(String dataCloseIconField) {
        setPropertity(PROPERTITY_DATA_CLOSE_ICON_FIELD, dataCloseIconField);
    }

    public String getDataContentIframe() {
        return getPrototype().getString(PROPERTITY_DATA_CONTENT_IFRAME);
    }

    public void setDataContentIframe(String dataContentIframe) {
        setPropertity(PROPERTITY_DATA_CONTENT_IFRAME, dataContentIframe);
    }

    public String getDataIframeIdField() {
        return getPrototype().getString(PROPERTITY_DATA_IFRAME_ID_FIELD);
    }

    public void setdataIframeIdField(String dataIframeIdField) {
        setPropertity(PROPERTITY_DATA_IFRAME_ID_FIELD, dataIframeIdField);
    }

    public Boolean getCollapsible() {
        return getPrototype().getBoolean(PROPERTITY_COLLAPSIBLE);
    }

    public void setCollapsible(Boolean collapsible) {
        setPropertity(PROPERTITY_COLLAPSIBLE, collapsible);
    }

    public String[] getContentUrls() {
        String urls = getPrototype().getString(PROPERTITY_CONTENT_URLS);
        if (urls != null) {
            String[] contentUrls = urls.split(",");
            return contentUrls;
        }
        return null;
    }

    public void setContentUrls(String contentUrls) {
        setPropertity(PROPERTITY_CONTENT_URLS, contentUrls);
    }

    public String getDataContentField() {
        return getPrototype().getString(PROPERTITY_DATA_CONTENT_FIELD);
    }

    public void setDataContentField(String dataContentField) {
        setPropertity(PROPERTITY_DATA_CONTENT_FIELD, dataContentField);
    }

    public String getDataContentUrlField() {
        return getPrototype().getString(PROPERTITY_DATA_CONTENT_URL_FIELD);
    }

    public void setDataContentUrlField(String dataContentUrlField) {
        setPropertity(PROPERTITY_DATA_CONTENT_URL_FIELD, dataContentUrlField);
    }

    public String getDataImageUrlField() {
        return getPrototype().getString(PROPERTITY_DATA_IMAGE_URL_FIELD);
    }

    public void setDataImageUrlField(String dataImageUrlField) {
        setPropertity(PROPERTITY_DATA_IMAGE_URL_FIELD, dataImageUrlField);
    }

    public String getDataSpriteCssClass() {
        return getPrototype().getString(PROPERTITY_DATA_SPRITE_CSS_CLASS);
    }

    public void setDataSpriteCssClass(String dataSpriteCssClass) {
        setPropertity(PROPERTITY_DATA_SPRITE_CSS_CLASS, dataSpriteCssClass);
    }

    public String getDataTextField() {
        return getPrototype().getString(PROPERTITY_DATA_TEXT_FIELD);
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

    public Boolean getNavigatable() {
        return getPrototype().getBoolean(PROPERTITY_NAVIGATABLE);
    }

    public void setNavigatable(Boolean navigatable) {
        setPropertity(PROPERTITY_NAVIGATABLE, navigatable);
    }

    public Object getScrollable() {
        Boolean scrollable = getPrototype().getBoolean(PROPERTITY_SCROLLABLE);
        XMap distance = getPrototype().getChild(PROPERTITY_SCROLLABLE);
        if (distance != null) {
            return distance;
        }
        return scrollable;
    }

    public void setScrollable(Boolean scrollable) {
        setPropertity(PROPERTITY_SCROLLABLE, scrollable);
    }

    public void setScrollable(Integer distance) {
        XMap x = new XMap(DEFAULT_TAG_PREFIX, DEFAULT_NAME_SPACE, PROPERTITY_SCROLLABLE);
        x.put(PROPERTITY_DISTANCE, distance);
        getPrototype().addChild(x);
    }

    public String getTabPosition() {
        return getPrototype().getString(PROPERTITY_TAB_POSITION);
    }

    public void setTabPosition(String tabPosition) {
        setPropertity(PROPERTITY_TAB_POSITION, tabPosition);
    }

    public String getValue() {
        return getPrototype().getString(PROPERTITY_VALUE);
    }

    public void setValue(String value) {
        setPropertity(PROPERTITY_VALUE, value);
    }

    protected void initPrototype(XMap view) {
        super.initPrototype(view);
        addEvents(PROPERTITY_DATASOURCE, PROPERTITY_ACTIVATE, PROPERTITY_CONTENT_LOAD, PROPERTITY_SELECT,
                PROPERTITY_ERROR, PROPERTITY_SHOW, PROPERTITY_CHANGE, PROPERTITY_DATABINDING, PROPERTITY_DATABOUND);
    }

    public void init(XMap view, ViewContext context) throws Exception {
        super.init(view, context);
        context.put(PROPERTITY_TAB, getTab());
        context.put(PROPERTITY_TAB_CONTENT, getTabContent(context));
        context.addJsonConfig(PROPERTITY_ANIMATION, getAnimation());
        context.addJsonConfig(PROPERTITY_COLLAPSIBLE, getCollapsible());
        context.addJsonConfig(PROPERTITY_CONTENT_URLS, getContentUrls());
        context.addJsonConfig(PROPERTITY_DATA_CONTENT_FIELD, getDataContentField());
        context.addJsonConfig(PROPERTITY_DATA_CONTENT_URL_FIELD, getDataContentUrlField());
        context.addJsonConfig(PROPERTITY_DATA_IMAGE_URL_FIELD, getDataImageUrlField());
        context.addJsonConfig(PROPERTITY_DATA_SPRITE_CSS_CLASS, getDataSpriteCssClass());
        context.addJsonConfig(PROPERTITY_DATA_TEXT_FIELD, getDataTextField());
        context.addJsonConfig(PROPERTITY_DATA_URL_FIELD, getDataUrlField());
        context.addJsonConfig(PROPERTITY_NAVIGATABLE, getNavigatable());
        context.addJsonConfig(PROPERTITY_SCROLLABLE, getScrollable());
        context.addJsonConfig(PROPERTITY_TAB_POSITION, getTabPosition());
        context.addJsonConfig(PROPERTITY_VALUE, getValue());
    }
}
