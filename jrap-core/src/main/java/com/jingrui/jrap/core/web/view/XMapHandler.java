package com.jingrui.jrap.core.web.view;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import com.jingrui.jrap.core.web.view.ui.ViewTag;

public class XMapHandler implements ScreenHandler {

    private XMap current = null;
    private Map<String, String> uriMapping = new HashMap<String, String>();
    private LinkedList<XMap> stack = new LinkedList<XMap>();

    public void processDocument(XmlPullParser xpp) throws XmlPullParserException, IOException {
        int eventType = xpp.getEventType();
        do {
            if (eventType == XmlPullParser.START_DOCUMENT) {
                startDocument();
            } else if (eventType == XmlPullParser.START_TAG) {
                processStartElement(xpp);
            } else if (eventType == XmlPullParser.END_TAG) {
                processEndElement(xpp);
            } else if (eventType == XmlPullParser.TEXT || eventType == XmlPullParser.CDSECT) {
                processText(xpp);
            }
            eventType = xpp.nextToken();
        } while (eventType != XmlPullParser.END_DOCUMENT);
    }

    public XMap getRoot() {
        return current;
    }

    public void startDocument() {
        current = null;
        uriMapping.clear();
        stack.clear();
    }

    public void endElement() {
        if (stack.size() > 0)
            current = pop();
    }

    private XMap pop() {
        XMap node = (XMap) stack.getFirst();
        stack.removeFirst();
        return node;
    }

    private void setAttribs(XMap node, XmlPullParser xpp) {
        int count = xpp.getAttributeCount();
        for (int i = 0, l = count; i < l; i++) {
            String attrib_name = xpp.getAttributeName(i);
            node.put(attrib_name, xpp.getAttributeValue(i));
        }
    }

    private void push(XMap node) {
        stack.addFirst(node);
    }

    public void processStartElement(XmlPullParser xpp) throws IOException, XmlPullParserException {
        String name = xpp.getName();
        String namespace = xpp.getNamespace();
        String prefix = xpp.getPrefix();

        prefix = prefix == null ? (ViewTag.DEFAULT_NAME_SPACE.equals(namespace) ? ViewTag.DEFAULT_TAG_PREFIX : "") : prefix;
        uriMapping.put(prefix, namespace);
        XMap node = new XMap(prefix, namespace, name);
        node.setNamespaceMapping(uriMapping);
        setAttribs(node, xpp);

        if (current == null) {
            current = node;
        } else {
            current.addChild(node);
            push(current);
            current = node;
        }

        // weblogic CDATA 问题
//        if("script".equalsIgnoreCase(xpp.getName()) && xpp.nextToken() == XmlPullParser.CDSECT){
//            node.setText(xpp.getText());
//        }

    }

    public void processEndElement(XmlPullParser xpp) {
        endElement();
    }

    int holderForStartAndLength[] = new int[2];

    public void processText(XmlPullParser xpp) throws XmlPullParserException {
        char ch[] = xpp.getTextCharacters(holderForStartAndLength);
        int start = holderForStartAndLength[0];
        int length = holderForStartAndLength[1];

        if (ch == null)
            return;
        if (start == length)
            return;
        if (current != null) {
            String t = current.getText();
            if (t != null)
                t += new String(ch, start, length);
            else
                t = new String(ch, start, length);
            current.setText(t);
        }
    }

}
