package com.jingrui.jrap.core.web.view;

import java.util.Iterator;
import java.util.List;

import com.jingrui.jrap.core.web.view.ui.ViewTag;

/**
 * 
 * @author njq.niu@jingrui.com
 *
 */
public class XMLTag extends ViewTag {
    
    public static final String TAG_LEFT = "<";
    public static final String TAG_RIGHT = ">";
    public static final String TAG_RIGHT_CLOSE = "/>";
    public static final String TAG_LEFT_CLOSE = "</";

    public String execute(XMap view, ViewContext context) throws Exception {
        StringBuilder sb = new StringBuilder();
        List<XMap> children = view.getChildren();
        String name = view.getName();
        sb.append(TAG_LEFT).append(name);
        XMLOutputter.getAttributeXML(view, sb);
        String text = view.getText();
        if (children == null && text == null) {
            sb.append(TAG_RIGHT_CLOSE);
        } else {
            sb.append(TAG_RIGHT);
        }
        if (children != null) {
            Iterator<XMap> it = children.iterator();
            while (it.hasNext()) {
                XMap child = it.next();
                sb.append(ScreenBuilder.build(child, context));
            }
        } else if (text != null) {
            sb.append(text);
        }
        sb.append(TAG_LEFT_CLOSE).append(name).append(TAG_RIGHT);
        return sb.toString();
    }
}
