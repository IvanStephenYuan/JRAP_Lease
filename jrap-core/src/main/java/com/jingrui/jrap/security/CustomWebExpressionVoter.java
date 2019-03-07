package com.jingrui.jrap.security;

import com.jingrui.jrap.core.BaseConstants;
import com.jingrui.jrap.function.dto.Resource;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.expression.WebExpressionVoter;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

/**
 * @author njq.niu@jingrui.com
 */
public class CustomWebExpressionVoter extends WebExpressionVoter {

    @Autowired
    private ResourceAccessor resourceAccessor;

    @Override
    public int vote(Authentication authentication, FilterInvocation fi, Collection<ConfigAttribute> attributes) {

        for (ConfigAttribute attribute : attributes) {
            if ("permitAll".equals(attribute.toString())) {
                return ACCESS_GRANTED;
            }
        }

        HttpServletRequest request = fi.getRequest();
        String uri = StringUtils.substringAfter(request.getRequestURI(), request.getContextPath());
        String uriOri = uri;

        if (uri.startsWith("/")) {
            uri = uri.substring(1);
        }

        Resource resource = resourceAccessor.getResourceOfUri(request, uriOri, uri);
        if (resource != null) {
            ResourceAccessor.CURRENT_RESOURCE.set(resource);
            if (!BaseConstants.YES.equalsIgnoreCase(resource.getLoginRequire())) {
                return ACCESS_GRANTED;
            }
        }
        return super.vote(authentication,fi,attributes);
    }


}
