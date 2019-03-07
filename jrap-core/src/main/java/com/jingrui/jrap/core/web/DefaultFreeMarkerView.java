/*
 * #{copyright}#
 */
package com.jingrui.jrap.core.web;

import com.jingrui.jrap.attachment.service.IAttachmentProvider;
import com.jingrui.jrap.system.service.IAccessService;
import com.jingrui.jrap.system.service.IKendoLovService;
import com.jingrui.jrap.system.service.ILovService;
import freemarker.ext.servlet.FreemarkerServlet;
import freemarker.ext.servlet.HttpRequestParametersHashModel;
import freemarker.template.SimpleHash;
import freemarker.template.SimpleScalar;
import freemarker.template.TemplateModel;
import org.springframework.beans.BeansException;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfig;
import org.springframework.web.servlet.view.freemarker.FreeMarkerView;
import org.springframework.web.util.HtmlUtils;
import org.springframework.web.util.JavaScriptUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author njq.niu@jingrui.com
 * @date 2016年1月31日
 */
public class DefaultFreeMarkerView extends FreeMarkerView {

    private IKendoLovService kendoLovService;
    private ILovService lovService;
    private IAccessService accessService;
    private IAttachmentProvider attachmentProvider;
    private FreeMarkerBeanProvider beanProvider;

    @Override
    protected FreeMarkerConfig autodetectConfiguration() throws BeansException {
        kendoLovService = getApplicationContext().getBean(IKendoLovService.class);
        lovService = getApplicationContext().getBean(ILovService.class);
        accessService = getApplicationContext().getBean(IAccessService.class);
        attachmentProvider = getApplicationContext().getBean(IAttachmentProvider.class);
        beanProvider = getApplicationContext().getBean(FreeMarkerBeanProvider.class);
        return super.autodetectConfiguration();
    }

    @Override
    protected SimpleHash buildTemplateModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) {
        SimpleHash fmModel = super.buildTemplateModel(model, request, response);
        fmModel.put(FreemarkerServlet.KEY_REQUEST_PARAMETERS, new XSSDefendHttpRequestParametersHashModel(request));
        accessService.setRequest(request);
        fmModel.put("lovProvider", kendoLovService);
        fmModel.put("lovService", lovService);
        fmModel.put("accessService", accessService);
        attachmentProvider.setConfiguration(getConfiguration());
        fmModel.put("attachmentProvider", attachmentProvider);
        Map<String, Object> beans = beanProvider.getAvailableBean();
        if (beans != null) {
            fmModel.putAll(beans);
        }
        return fmModel;
    }


    static class XSSDefendHttpRequestParametersHashModel extends HttpRequestParametersHashModel {

        private HttpServletRequest requestReference;

        public XSSDefendHttpRequestParametersHashModel(HttpServletRequest request) {
            super(request);
            this.requestReference = request;
        }

        @Override
        public TemplateModel get(String key) {
            String value = requestReference.getParameter(key);
            return value == null ? null : new SimpleScalar(JavaScriptUtils.javaScriptEscape((HtmlUtils.htmlEscape(value))));
        }
    }

}
