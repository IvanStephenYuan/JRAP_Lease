package com.jingrui.jrap.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.jingrui.jrap.account.dto.Role;
import com.jingrui.jrap.account.dto.User;
import com.jingrui.jrap.account.service.IRole;
import com.jingrui.jrap.account.service.IRoleService;
import com.jingrui.jrap.core.BaseConstants;
import com.jingrui.jrap.core.components.SysConfigManager;
import com.jingrui.jrap.core.impl.RequestHelper;
import com.jingrui.jrap.message.profile.SystemConfigListener;
import com.jingrui.jrap.mybatis.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.web.servlet.ModelAndView;

/**
 * 自定义登录成功Handler.
 *
 * @author hailor
 * @date 16/6/12.
 */
public class CustomAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler implements SystemConfigListener {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    SysConfigManager sysConfigManager;

    private RequestCache requestCache = new HttpSessionRequestCache();

    private Logger logger = LoggerFactory.getLogger(getClass());

    private Map<String, IAuthenticationSuccessListener> listeners;

    public static final String DEFAULT_TARGET_URL = "DEFAULT_TARGET_URL";

    private final String loginOauthUrl = "/login?oauth";
    private final String loginUrl = "/login";
    private final String logoutUrl = "/logout";
    private final String indexUrl = "/index";
    private final String refererStr = "Referer";
    private final String loginCasUrl = "/login/cas";
    private final String functionCodeStr = "functionCode";
    private final String ROLE_URL = "/role";

    {
        setDefaultTargetUrl("/");
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        if (listeners == null) {
            listeners = applicationContext.getBeansOfType(IAuthenticationSuccessListener.class);
        }

        String referer = request.getHeader(refererStr);
        if (referer != null && referer.endsWith(loginOauthUrl)) {
            super.onAuthenticationSuccess(request, response, authentication);
            return;
        }

        clearAuthenticationAttributes(request);
        List<IAuthenticationSuccessListener> list = new ArrayList<>();
        list.addAll(listeners.values());
        Collections.sort(list);
        IAuthenticationSuccessListener successListener = null;
        String requestURI = request.getRequestURI();
        boolean isCas = requestURI.endsWith(loginCasUrl);
        try {
            for (IAuthenticationSuccessListener listener : list) {
                successListener = listener;
                successListener.onAuthenticationSuccess(request, response, authentication);
            }
            HttpSession session = request.getSession(false);
            session.setAttribute(User.LOGIN_CHANGE_INDEX, "CHANGE");
        } catch (Exception e) {
            logger.error("authentication success, but error occurred in " + successListener, e);
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.invalidate();
            }
            request.setAttribute("error", true);
            request.setAttribute("exception", e);
            if (isCas) {
                request.getRequestDispatcher("/casLoginFailure").forward(request, response);
            } else {
                request.getRequestDispatcher("/login").forward(request, response);
            }
            return;
        }
        // 增加了对于 standard security 支持
        //拿到登录以前的url
        SavedRequest savedRequest = this.requestCache.getRequest(request, response);
        if (savedRequest != null) {
            String targetUrl = savedRequest.getRedirectUrl();
            if(targetUrl.endsWith(loginUrl)){
                targetUrl = targetUrl.substring(0,targetUrl.lastIndexOf(loginUrl));
                targetUrl = targetUrl + "/";
            }else if(targetUrl.endsWith(logoutUrl)){
                targetUrl = targetUrl.substring(0,targetUrl.lastIndexOf(logoutUrl));
                targetUrl = targetUrl + "/";
            }
            String defaultTarget = getDefaultTargetUrl();
            if (!targetUrl.contains(functionCodeStr) && ! indexUrl.equalsIgnoreCase(defaultTarget) && ! "/".equalsIgnoreCase(defaultTarget) ) {
               targetUrl = getDefaultTargetUrl()+"?targetUrl="+targetUrl;
            }
            // 不合并角色
            HttpSession session = request.getSession(false);
            if (session != null ) {
                if (!sysConfigManager.getRoleMergeFlag()) {
                    Long roleId = (Long) session.getAttribute(Role.FIELD_ROLE_ID);
                    if (roleId == null) {
                        //return new ModelAndView(BaseConstants.VIEW_REDIRECT + "/role");
                        session.setAttribute("targetUrl", targetUrl);
                        this.getRedirectStrategy().sendRedirect(request, response, ROLE_URL);
                        return ;
                    }
                }
            }
            this.getRedirectStrategy().sendRedirect(request, response, targetUrl);
            return;
        }

        handle(request, response, authentication);
    }

    @Override
    public List<String> getAcceptedProfiles() {
        return Collections.singletonList(DEFAULT_TARGET_URL);
    }

    @Override
    public void updateProfile(String profileName, String profileValue) {
        if (StringUtil.isNotEmpty(profileValue)) {
            setDefaultTargetUrl(profileValue);
        }
    }


    private void selectRole(HttpServletRequest request, HttpServletResponse response, String targetUrl) {

    }
}
