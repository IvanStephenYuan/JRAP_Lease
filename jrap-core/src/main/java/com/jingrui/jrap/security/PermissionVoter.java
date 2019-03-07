package com.jingrui.jrap.security;

import com.jingrui.jrap.account.service.IRole;
import com.jingrui.jrap.account.service.IRoleService;
import com.jingrui.jrap.core.BaseConstants;
import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.core.impl.RequestHelper;
import com.jingrui.jrap.function.dto.Resource;
import com.jingrui.jrap.message.components.DefaultRoleResourceListener;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.web.FilterInvocation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * @author hailor
 * @date 16/6/12.
 */
public class PermissionVoter implements AccessDecisionVoter<FilterInvocation> {
    private static final Logger logger = LoggerFactory.getLogger(PermissionVoter.class);

    @Autowired
    @Qualifier("roleServiceImpl")
    private IRoleService roleService;

    @Autowired
    private DefaultRoleResourceListener roleResourceListener;

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return false;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

    @Override
    public int vote(Authentication authentication, FilterInvocation fi, Collection<ConfigAttribute> attributes) {
        int result = ACCESS_ABSTAIN;
        assert authentication != null;
        assert fi != null;
        assert attributes != null;

        // 已经 permitAll 的 url 不再过滤(主要是一些资源类 url,通用 url)
        for (ConfigAttribute attribute : attributes) {
            if ("permitAll".equals(attribute.toString())) {
                return result;
            }
        }

        HttpServletRequest request = fi.getRequest();
        String uri = StringUtils.substringAfter(request.getRequestURI(), request.getContextPath());

        if (uri.startsWith("/")) {
            uri = uri.substring(1);
        }
        if ("".equals(uri)) {
            return ACCESS_ABSTAIN;
        }

        Resource resource = ResourceAccessor.CURRENT_RESOURCE.get();
        ResourceAccessor.CURRENT_RESOURCE.remove();
        if (resource == null) {
            return ACCESS_ABSTAIN;
        }

        if (!BaseConstants.YES.equalsIgnoreCase(resource.getAccessCheck())) {
            if (logger.isDebugEnabled()) {
                logger.debug("url :'{}' need no access control.", uri);
            }
            return ACCESS_ABSTAIN;
        }

        if (authentication instanceof OAuth2Authentication) {
            OAuth2Authentication oAuth2Authentication = (OAuth2Authentication) authentication;
            Set<String> authrotities = AuthorityUtils.authorityListToSet(oAuth2Authentication.getAuthorities());
            IRole role = null;
            for (String roleCode : authrotities) {
                role = roleService.selectRoleByCode(roleCode);
                if (role != null) {
                    List<Long> ids = roleResourceListener.getRoleResource(role.getRoleId());
                    if (ids != null && ids.contains(resource.getResourceId())) {
                        return ACCESS_ABSTAIN;
                    }
                }
            }
        }

        HttpSession session = request.getSession(false);
        if (session == null) {
            return ACCESS_DENIED;
        }
        Long roleId = null;
        roleId = (Long) session.getAttribute(IRequest.FIELD_ROLE_ID);
        if (roleId == null) {
            return ACCESS_DENIED;
        }

        // 判断当前用户下的所有角色
        Long[] roleIds = RequestHelper.createServiceRequest(request).getAllRoleId();
        for (Long rid : roleIds) {
            List<Long> ids = roleResourceListener.getRoleResource(rid);
            if (ids != null && ids.contains(resource.getResourceId())) {
                return ACCESS_ABSTAIN;
            }
        }
        logger.debug("access to uri :'{}' denied.", uri);
        result = ACCESS_DENIED;

        return result;
    }

}
