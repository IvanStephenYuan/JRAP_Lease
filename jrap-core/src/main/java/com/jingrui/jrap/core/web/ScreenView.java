package com.jingrui.jrap.core.web;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContextException;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.xmlpull.v1.XmlPullParserException;

import com.jingrui.jrap.cache.Cache;
import com.jingrui.jrap.cache.CacheManager;
import com.jingrui.jrap.cache.impl.ResourceCustomizationCache;
import com.jingrui.jrap.core.BaseConstants;
import com.jingrui.jrap.core.web.view.ScreenBuilder;
import com.jingrui.jrap.core.web.view.ViewContext;
import com.jingrui.jrap.core.web.view.ViewTagFactory;
import com.jingrui.jrap.core.web.view.XMap;
import com.jingrui.jrap.core.web.view.XMapHandler;
import com.jingrui.jrap.core.web.view.XMapParser;
import com.jingrui.jrap.core.web.view.XMapUtil;
import com.jingrui.jrap.function.dto.Resource;

import freemarker.template.SimpleHash;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * 
 * @author njq.niu@jingrui.com
 *
 */
public class ScreenView extends DefaultFreeMarkerView {

    protected final Logger logger = LoggerFactory.getLogger(ScreenView.class);

    private ViewTagFactory screenTagFactory;

    private CacheManager cacheManager;

    private Cache<Resource> resourceCache;

    private ResourceCustomizationCache resourceCustomizationCache;

    public ViewTagFactory getScreenTagFactory() {
        return screenTagFactory;
    }

    @Override
    protected void initServletContext(ServletContext servletContext) throws BeansException {
        super.initServletContext(servletContext);
        this.screenTagFactory = (ViewTagFactory) autodetectScreenTagFactory(ViewTagFactory.class);
        this.cacheManager = (CacheManager) autodetectScreenTagFactory(CacheManager.class);
        this.resourceCustomizationCache = (ResourceCustomizationCache) autodetectScreenTagFactory(ResourceCustomizationCache.class);
        resourceCache = cacheManager.getCache(BaseConstants.CACHE_RESOURCE_URL);
    }

    protected Object autodetectScreenTagFactory(Class<?> clazz) throws BeansException {
        try {
            return BeanFactoryUtils.beanOfTypeIncludingAncestors(getApplicationContext(), clazz, true, false);
        } catch (NoSuchBeanDefinitionException ex) {
            throw new ApplicationContextException("Must define a single ViewTagFactory bean in this web application context!", ex);
        }
    }

    @SuppressWarnings("unchecked")
    protected void processTemplate(Template template, Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        SimpleHash fmModel = buildTemplateModel(model, request, response);
        try (Writer writer = response.getWriter()) {
            XMap xmap = parseView(fmModel, template);

            Resource resource = resourceCache.getValue(template.getName());
            if (resource != null) {
                String[] mergeList = resourceCustomizationCache.getValue(resource.getResourceId().toString());
                if (mergeList != null) {
                    List<String> list = Arrays.asList(mergeList);
                    mergeView(xmap, list, request, fmModel);
                }
            }

            String str = ScreenBuilder.build(xmap, new ViewContext(getConfiguration(), getScreenTagFactory(), fmModel.toMap(), request, response));
            if (str != null) {
                writer.write(str);
            }
        } catch (Exception e) {
            if(logger.isErrorEnabled()){
                logger.error("Failed to parse screen template for URL [" + getUrl() + "]", e);
            }
            throw e;
        }
    }

    private XMap parseView(SimpleHash model, Template template) throws IOException, TemplateException, XmlPullParserException {
        XMap xmap = null;
        try (StringWriter out = new StringWriter()) {
            template.process(model, out);
            out.flush();
            xmap = XMapParser.parseReader(new StringReader(out.toString()), new XMapHandler());
        }
        return xmap;
    }

    private void mergeView(XMap source, List<String> list, HttpServletRequest request, SimpleHash model) {
        if (list != null) {
            Locale locale = RequestContextUtils.getLocale(request);
            for (String url : list) {
                Template t;
                try {
                    t = getTemplate(url, locale);
                    XMap view = parseView(model, t);
                    XMapUtil.merge(source, view);
                } catch (Exception e) {
                    if (logger.isErrorEnabled()) {
                        logger.error("Failed to merge view " + url, e);
                    }
                }

            }
        }
    }

    protected void doRender(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        // Expose model to JSP tags (as request attributes).
        exposeModelAsRequestAttributes(model, request);
        // Expose all standard FreeMarker hash models.
        if (logger.isDebugEnabled()) {
            logger.debug("Rendering FreeMarker template [{}] in FreeMarkerView '{}'", getUrl(), getBeanName());
        }
        // Grab the locale-specific version of the template.
        Locale locale = RequestContextUtils.getLocale(request);
        processTemplate(getTemplate(locale), model, request, response);
    }
}
