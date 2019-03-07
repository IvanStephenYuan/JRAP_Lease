package com.jingrui.jrap.core.web.view.ui;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Locale;
import java.util.Map;

import org.springframework.web.servlet.support.RequestContextUtils;

import com.jingrui.jrap.core.web.view.ViewContext;
import com.jingrui.jrap.core.web.view.XMap;

import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * ViewTag.
 * 
 * @author njq.niu@jingrui.com
 *
 */
public abstract class ViewTag {

    public static final String DEFAULT_NAME_SPACE = "http://www.jingrui.com/jrap";
    public static final String DEFAULT_TAG_PREFIX = "h";

    public void init(XMap view, ViewContext context) throws Exception {
    }

    public abstract String execute(XMap view, ViewContext context) throws Exception;

    protected String build(ViewContext context, String templateName) throws IOException, TemplateException {
        String html = "";
        try (StringWriter out = new StringWriter()) {
            Locale locale = RequestContextUtils.getLocale(context.getRequest());
            Template template = context.getConfiguration().getTemplate(templateName, locale);
            Map<String, Object> map = context.getFreeMarkerModel();
            template.process(map, out);
            out.flush();
            html = out.toString();
        }
        return html;
    }
}
