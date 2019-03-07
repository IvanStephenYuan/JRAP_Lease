package com.jingrui.jrap.core.web.view.ui;

import java.util.List;

import com.jingrui.jrap.core.web.view.ReferenceType;
import com.jingrui.jrap.core.web.view.ScreenBuilder;
import com.jingrui.jrap.core.web.view.UITag;
import com.jingrui.jrap.core.web.view.ViewContext;
import com.jingrui.jrap.core.web.view.XMap;

/**
 * 高级查询
 * 
 * @author hailin.xu@jingrui.com
 *
 */
@UITag
public class QueryPanel extends Component {

    private static final String PROPERTITY_QUERY_FUNCTION = "queryFunction";
    private static final String PROPERTITY_RESET_FUNCTION = "resetFunction";
    private static final String PROPERTITY_COMMON = "common";
    private static final String PROPERTITY_ADVANCE = "advance";

    public static QueryPanel createInstance() {
        XMap view = new XMap(DEFAULT_TAG_PREFIX, DEFAULT_NAME_SPACE, "queryPanel");
        QueryPanel queryPanel = new QueryPanel();
        queryPanel.initPrototype(view);
        return queryPanel;
    }

    public String getCommon(ViewContext context) throws Exception {
        StringBuffer sb = new StringBuffer();
        XMap common = getPrototype().getChild(PROPERTITY_COMMON);
        if (common != null) {
            List<XMap> list = common.getChildren();
            if(list!=null){
                for (XMap map : list) {
                    sb.append(ScreenBuilder.build(map, context));
                }  
            }
        }
        return sb.toString();
    }

    public void addCommon(List<XMap> children) {
        XMap map = getPrototype().getChild(PROPERTITY_COMMON);
        if (map == null) {
            map = getPrototype().createChild(DEFAULT_TAG_PREFIX, DEFAULT_NAME_SPACE, PROPERTITY_COMMON);
        }
        if(children!=null){
            for (XMap m : children) {
                map.addChild(m);
            } 
        }
    }

    public void addCommon(XMap child) {
        XMap map = getPrototype().getChild(PROPERTITY_COMMON);
        if (map == null) {
            map = getPrototype().createChild(DEFAULT_TAG_PREFIX, DEFAULT_NAME_SPACE, PROPERTITY_COMMON);
        }
        map.addChild(child);
    }

    public String getAdvance(ViewContext context) throws Exception {
        StringBuffer sb = new StringBuffer();
        XMap advance = getPrototype().getChild(PROPERTITY_ADVANCE);
        if (advance != null) {
            List<XMap> list = advance.getChildren();
            if(list!=null){
                for (XMap map : list) {
                    sb.append(ScreenBuilder.build(map, context));
                }   
            }
        }
        return sb.toString();
    }

    public void addAdvance(List<XMap> children) {
        XMap map = getPrototype().getChild(PROPERTITY_ADVANCE);
        if (map == null) {
            map = getPrototype().createChild(DEFAULT_TAG_PREFIX, DEFAULT_NAME_SPACE, PROPERTITY_ADVANCE);
        }
        for (XMap m : children) {
            map.addChild(m);
        }
    }

    public void addAdvance(XMap child) {
        XMap map = getPrototype().getChild(PROPERTITY_ADVANCE);
        if (map == null) {
            map = getPrototype().createChild(DEFAULT_TAG_PREFIX, DEFAULT_NAME_SPACE, PROPERTITY_ADVANCE);
        }
        map.addChild(child);
    }

    public ReferenceType getQueryFunction() {
        String queryFunction = getPrototype().getString(PROPERTITY_QUERY_FUNCTION);
        if (queryFunction != null) {
            return new ReferenceType(queryFunction);
        }
        return null;
    }

    public void setQueryFunction(ReferenceType queryFunction) {
        setPropertity(PROPERTITY_QUERY_FUNCTION, queryFunction);
    }

    public ReferenceType getResetFunction() {
        String resetFunction = getPrototype().getString(PROPERTITY_RESET_FUNCTION);
        if (resetFunction != null) {
            return new ReferenceType(resetFunction);
        }
        return null;
    }

    public void setResetFunction(ReferenceType resetFunction) {
        setPropertity(PROPERTITY_RESET_FUNCTION, resetFunction);
    }

    protected void initPrototype(XMap view) {
        super.initPrototype(view);
    }

    public void init(XMap view, ViewContext context) throws Exception {
        super.init(view, context);
        context.put(PROPERTITY_COMMON, getCommon(context));
        context.put(PROPERTITY_ADVANCE, getAdvance(context));
        context.addJsonConfig(PROPERTITY_QUERY_FUNCTION, getQueryFunction());
        context.addJsonConfig(PROPERTITY_RESET_FUNCTION, getResetFunction());
    }
}
