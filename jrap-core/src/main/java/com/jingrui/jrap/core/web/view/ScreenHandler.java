package com.jingrui.jrap.core.web.view;

import java.io.IOException;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/**
 * 
 * @author njq.niu@jingrui.com
 *
 */
public interface ScreenHandler {

    String TAG_SCREEN = "screen";

    void processDocument(XmlPullParser xpp) throws XmlPullParserException, IOException;

    XMap getRoot();
}