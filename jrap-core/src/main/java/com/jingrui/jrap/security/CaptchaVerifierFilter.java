package com.jingrui.jrap.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

import com.jingrui.jrap.account.exception.UserException;
import com.jingrui.jrap.core.components.CaptchaConfig;
import com.jingrui.jrap.security.captcha.ICaptchaManager;

/**
 * @author hailor
 * @date 16/6/12.
 */
public class CaptchaVerifierFilter extends OncePerRequestFilter {
    @Autowired
    private ICaptchaManager captchaManager;

    @Autowired
    private CaptchaConfig captchaConfig;

    private RequestMatcher loginRequestMatcher;

    private String captchaField = "captcha";

    private String loginUrl = "/login";

    public CaptchaVerifierFilter() {
        setFilterProcessesUrl(this.loginUrl);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
            FilterChain filterChain) throws ServletException, IOException {
        if (captchaConfig.isEnableCaptcha(WebUtils.getCookie(httpServletRequest, CaptchaConfig.LOGIN_KEY))
                && requiresValidateCaptcha(httpServletRequest, httpServletResponse)) {
            Cookie cookie = WebUtils.getCookie(httpServletRequest, captchaManager.getCaptchaKeyName());
            String captchaCode = httpServletRequest.getParameter(getCaptchaField());
            if (cookie == null || StringUtils.isEmpty(captchaCode)
                    || !captchaManager.checkCaptcha(cookie.getValue(), captchaCode)) {
                httpServletRequest.setAttribute("error", true);
                httpServletRequest.setAttribute("code", "CAPTCHA_INVALID");
                httpServletRequest.setAttribute("exception",
                        new UserException(UserException.LOGIN_VERIFICATION_CODE_ERROR, null));
                httpServletRequest.getRequestDispatcher(loginUrl).forward(httpServletRequest, httpServletResponse);
                return;
            }
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);

    }

    public String getCaptchaField() {
        return captchaField;
    }

    public void setCaptchaField(String captchaField) {
        this.captchaField = captchaField;
    }

    public void setFilterProcessesUrl(String filterProcessesUrl) {
        this.loginRequestMatcher = new AntPathRequestMatcher(filterProcessesUrl);
    }

    protected boolean requiresValidateCaptcha(HttpServletRequest request, HttpServletResponse response) {
        return loginRequestMatcher.matches(request) && "POST".equalsIgnoreCase(request.getMethod());
    }
}
