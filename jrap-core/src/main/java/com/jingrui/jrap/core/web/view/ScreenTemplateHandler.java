package com.jingrui.jrap.core.web.view;

import java.io.IOException;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import com.jingrui.jrap.core.web.view.ui.ViewTag;

/**
 * 
 * @author njq.niu@jingrui.com
 *
 */
public class ScreenTemplateHandler implements ScreenHandler {

    private XMap screen;
    private boolean breakToken = false;
    
    
    public void processDocument(XmlPullParser xpp) throws XmlPullParserException, IOException {
        int eventType = xpp.getEventType();
        do {
            if (eventType == XmlPullParser.START_TAG) {
                processStartElement(xpp);
            } else if (eventType == XmlPullParser.END_TAG) {
                processEndElement(xpp);
            } 
            eventType = xpp.next();
        } while (!breakToken && eventType != XmlPullParser.END_DOCUMENT);
    }
    
    private void setAttribs(XMap node, XmlPullParser xpp) {
        int count = xpp.getAttributeCount();
        for (int i = 0, l = count; i < l; i++) {
            String attrib_name = xpp.getAttributeName(i);
            node.put(attrib_name, xpp.getAttributeValue(i));
        }
    }
    public void processStartElement(XmlPullParser xpp) {
        String name = xpp.getName();
        String uri = xpp.getNamespace();
        if(ViewTag.DEFAULT_NAME_SPACE.equals(uri)){
            if(TAG_SCREEN.equals(name)) {
                screen = new XMap(ViewTag.DEFAULT_TAG_PREFIX, uri, name);
                setAttribs(screen, xpp);
            } 
        }
    }
    
    public void processEndElement(XmlPullParser xpp) {
        
    }

    public XMap getRoot() {
        return screen;
    }
}
