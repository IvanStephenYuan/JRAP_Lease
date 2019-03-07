package com.jingrui.jrap.core.web.view;

public class XMLWritter {

    public static String getXMLDecl(String encoding) {
        if (encoding == null)
            encoding = "UTF-8";
        return "<?xml version=\"1.0\" encoding = \"" + encoding + "\"?>\r\n";
    }

    public static String startTag(String element) {
        return "<" + element + ">";
    }

    public static String endTag(String element) {
        return "</" + element + ">";
    }

    public static String cdata(String value) {
        return "<![CDATA[" + value + "]]>";
    }

    public static String getAttrib(String key, String value) {
        return key + '=' + '"' + escape(value) + '"';
    }

    public static String escape(String value) {
        StringBuilder dom = new StringBuilder();
        int len = value.length();
        for (int i = 0; i < len; i++) {
            char ch = value.charAt(i);
            if (ch == '<')
                dom.append("&lt;");
            else if (ch == '>')
                dom.append("&gt;");
            else if (ch == '&')
                dom.append("&amp;");
            else if (ch == '"')
                dom.append("&quot;");
            else if (ch == '\'')
                dom.append("&apos;");
            else
                dom.append(ch);
        }
        return dom.toString();
    }

}
