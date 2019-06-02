/*
 * #{copyright}#
 */
package com.jingrui.jrap.core.interceptor;

import java.util.TimeZone;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.jingrui.jrap.core.impl.RequestHelper;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.jingrui.jrap.account.dto.User;
import com.jingrui.jrap.core.BaseConstants;
import com.jingrui.jrap.core.util.TimeZoneUtil;
import com.jingrui.jrap.security.TokenUtils;

/**
 * @author njq.niu@jingrui.com
 *
 *         2016年1月21日
 */
public class MonitorInterceptor extends HandlerInterceptorAdapter {

    private static ThreadLocal<Long> holder = new ThreadLocal<>();

    public static final ThreadLocal<Object> REST_INVOKE_HANDLER = new ThreadLocal<>();

    public static final String USER_ID = "userId";
    public static final String REQUEST_ID = "requestId";
    public static final String SESSION_ID = "sessionId";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        RequestHelper.clearCurrentRequest();
        fillMDC(request);
        holder.set(System.currentTimeMillis());
        HttpSession session = request.getSession(false);
        if (session != null) {
            String tz = (String) session.getAttribute(BaseConstants.PREFERENCE_TIME_ZONE);
            if (StringUtils.isNotEmpty(tz)) {
                TimeZoneUtil.setTimeZone(TimeZone.getTimeZone(tz));
            }
        }
        SecurityTokenInterceptor.LOCAL_SECURITY_KEY.set(TokenUtils.getSecurityKey(session));
        REST_INVOKE_HANDLER.set(handler);
        return true;
    }

    private void fillMDC(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            Long userId = (Long) session.getAttribute(User.FIELD_USER_ID);
            String uuid = UUID.randomUUID().toString().replace("-", "");
            if (userId != null) {
                MDC.put(USER_ID, userId.toString());
            }
            MDC.put(REQUEST_ID, uuid);
            MDC.put(SESSION_ID, session.getId());
        }
    }

    /**
     * This implementation is empty.
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
        long end = System.currentTimeMillis();
        if (handler instanceof HandlerMethod) {
            HandlerMethod method = (HandlerMethod) handler;
            Logger logger = LoggerFactory.getLogger(method.getBeanType());
            if (logger.isTraceEnabled()) {
                logger.trace(method.toString() + " - " + (end - holder.get()) + " ms");
            }
        }
    }

    /**
     * This implementation is empty.
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        RequestHelper.clearCurrentRequest();
        holder.remove();
        SecurityTokenInterceptor.LOCAL_SECURITY_KEY.remove();
        REST_INVOKE_HANDLER.remove();
        removeMDC(USER_ID);
        removeMDC(REQUEST_ID);
        removeMDC(SESSION_ID);
    }

    /**
     * This implementation is empty.
     */
    @Override
    public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
    }

    private void removeMDC(String key) {
        if (MDC.get(key) != null) {
            MDC.remove(key);
        }
    }
}
