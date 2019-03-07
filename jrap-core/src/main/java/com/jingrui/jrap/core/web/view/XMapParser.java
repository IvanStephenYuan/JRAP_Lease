package com.jingrui.jrap.core.web.view;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

public class XMapParser {

    private static XmlPullParserFactory factory = null;

    static {
        try {
            factory = XmlPullParserFactory.newInstance();
            factory.setValidating(false);
            factory.setNamespaceAware(true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static XMap parseStream(InputStream stream) throws XmlPullParserException, IOException {
        return parseStream(stream, new XMapHandler());
    }

    public static XMap parseStream(InputStream stream, ScreenHandler handler) throws XmlPullParserException, IOException {
        XmlPullParser xpp = factory.newPullParser();
        xpp.setInput(stream, "UTF-8");
        handler.processDocument(xpp);
        XMap root = handler.getRoot();
        return root;
    }

    public static XMap parseReader(Reader reader) throws XmlPullParserException, IOException {
        return parseReader(reader, new XMapHandler());
    }

    public static XMap parseReader(Reader reader, ScreenHandler handler) throws XmlPullParserException, IOException {
        XmlPullParser xpp = factory.newPullParser();
        xpp.setInput(reader);
        handler.processDocument(xpp);
        XMap root = handler.getRoot();
        return root;
    }

}
