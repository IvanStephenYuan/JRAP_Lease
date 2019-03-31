package com.jingrui.jrap.security.service.impl;

import com.jingrui.jrap.account.dto.User;
import com.jingrui.jrap.account.exception.RoleException;
import com.jingrui.jrap.account.service.IRole;
import com.jingrui.jrap.account.service.IRoleService;
import com.jingrui.jrap.account.service.IUserService;
import com.jingrui.jrap.core.BaseConstants;
import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.core.components.CaptchaConfig;
import com.jingrui.jrap.core.components.SysConfigManager;
import com.jingrui.jrap.core.impl.RequestHelper;
import com.jingrui.jrap.core.util.TimeZoneUtil;
import com.jingrui.jrap.fnd.dto.Company;
import com.jingrui.jrap.hr.service.IEmployeeAssignService;
import com.jingrui.jrap.message.IMessagePublisher;
import com.jingrui.jrap.message.websocket.CommandMessage;
import com.jingrui.jrap.message.websocket.WebSocketSessionManager;
import com.jingrui.jrap.security.IAuthenticationSuccessListener;
import com.jingrui.jrap.security.TokenUtils;
import com.jingrui.jrap.system.dto.SysPreferences;
import com.jingrui.jrap.system.service.ISysPreferencesService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.FrameworkServlet;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

/**
 * @author shengyang.zhou@jingrui.com
 * @author njq.niu@jingrui.com
 */
@Component
public class DefaultAuthenticationSuccessListener implements IAuthenticationSuccessListener {

    @Autowired
    private IUserService userService;

    @Autowired
    @Qualifier("roleServiceImpl")
    private IRoleService roleService;

    @Autowired
    private ISysPreferencesService preferencesService;

    @Autowired
    private IEmployeeAssignService employeeAssignService;

    @Autowired
    private CaptchaConfig captchaConfig;

    @Autowired
    private SysConfigManager sysConfigManager;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private IMessagePublisher iMessagePublisher;

    @Value("${sys.user.security.generate.accesstoken:false}")
    private boolean generateAccessToken;

    private static final String HAP_LOGIN_USER = BaseConstants.JRAP_CACHE + "login:";

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        Locale locale = RequestContextUtils.getLocale(request);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userService.convertToUser(userDetails);

        HttpSession session = request.getSession(true);
        processRepeatLogin(user, session);
        session.setAttribute(User.FIELD_USER_ID, user.getUserId());
        session.setAttribute(User.FIELD_USER_NAME, user.getUserName());
        session.setAttribute(IRequest.FIELD_LOCALE, locale.toString());
        //查询首选项
        SysPreferences preference = new SysPreferences();
        preference.setUserId(user.getUserId());
        List<SysPreferences> preferences = preferencesService.querySysPreferences(RequestHelper.newEmptyRequest(), preference);
        setTimeZoneFromPreference(request, getPreferences(preferences, BaseConstants.PREFERENCE_TIME_ZONE));
        setNavFromPreference(session, getPreferences(preferences, BaseConstants.PREFERENCE_NAV));
        setLocalePreference(request, getPreferences(preferences, BaseConstants.PREFERENCE_LOCALE));
        setRoleInfo(request, session, user);
        setProcessUser(user, session);
        setCompany(user, session);
        generateSecurityKey(session);
        fetchSystemImageVersion(request);
        captchaConfig.resetLoginFailureInfo(request, response);
    }

    @Override
    public int getOrder() {
        return 0;
    }

    private void fetchSystemImageVersion(HttpServletRequest request) {
        WebUtils.setSessionAttribute(request, SysConfigManager.SYS_LOGO_VERSION, sysConfigManager.getSystemLogoVersion());
        WebUtils.setSessionAttribute(request, SysConfigManager.SYS_FAVICON_VERSION, sysConfigManager.getSystemFaviconVersion());
    }

    private void processRepeatLogin(User user, HttpSession session) {
        String existSessionId = redisTemplate.opsForValue().get(HAP_LOGIN_USER + user.getUserName());
        redisTemplate.opsForValue().set(HAP_LOGIN_USER + user.getUserName(), session.getId(), session.getMaxInactiveInterval(), TimeUnit.SECONDS);
        if (existSessionId != null && !existSessionId.equals(session.getId()) && sysConfigManager.isProhibitRepeatLogin()) {
            CommandMessage commandMessage = new CommandMessage();
            commandMessage.setSessionId(session.getId());
            commandMessage.setUserName(user.getUserName());
            commandMessage.setAction("SYS_REPEAT_LOGIN");
            iMessagePublisher.publish(WebSocketSessionManager.CHANNEL_WEB_SOCKET, commandMessage);
        }
    }

    private void setProcessUser(User user, HttpSession session) {
        if (user.getEmployeeId() != null) {
            session.setAttribute(User.FIELD_EMPLOYEE_ID, user.getEmployeeId());
            session.setAttribute(User.FIELD_EMPLOYEE_CODE, user.getEmployeeCode());
        }
    }

    private void setCompany(User user, HttpSession session) {
        if (user.getEmployeeId() != null) {
            Long companyId = employeeAssignService.getCompanyByEmployeeId(user.getEmployeeId());
            if (companyId != null) {
                session.setAttribute(Company.FIELD_COMPANY_ID, companyId);
            }

        }
    }

    private void setTimeZoneFromPreference(HttpServletRequest request, SysPreferences pref) {
        String tz = pref == null ? null : pref.getPreferencesValue();
        if (StringUtils.isBlank(tz)) {
            final String appServletContextKey = FrameworkServlet.SERVLET_CONTEXT_PREFIX + "appServlet";
            WebApplicationContext appContext = WebApplicationContextUtils.getWebApplicationContext(request.getServletContext(), appServletContextKey);
            TimeZone timeZone = appContext.getBean(SessionLocaleResolver.class).getDefaultTimeZone();
            tz = TimeZoneUtil.toGMTFormat(timeZone == null ? TimeZone.getDefault() : timeZone);
        }
        WebUtils.setSessionAttribute(request, SessionLocaleResolver.TIME_ZONE_SESSION_ATTRIBUTE_NAME,
                org.springframework.util.StringUtils.parseTimeZoneString(tz));
        request.getSession(true).setAttribute(BaseConstants.PREFERENCE_TIME_ZONE, tz);
    }

    private void setNavFromPreference(HttpSession session, SysPreferences pref) {
        String nav = pref == null ? null : pref.getPreferencesValue();
        session.setAttribute(BaseConstants.PREFERENCE_NAV, nav);
    }

    private void setLocalePreference(HttpServletRequest request, SysPreferences pref) {
        if (pref != null) {
            WebUtils.setSessionAttribute(request, SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME,
                    org.springframework.util.StringUtils.parseLocaleString(pref.getPreferencesValue()));
        }
    }

    private void setRoleInfo(HttpServletRequest request, HttpSession session, User user) {
        List<IRole> roles = roleService.selectActiveRolesByUser(RequestHelper.createServiceRequest(request), user);
        if (roles.isEmpty()) {
            request.setAttribute("code", "NO_ROLE");
            throw new RuntimeException(new RoleException(null, RoleException.MSG_NO_USER_ROLE, null));
        }
        if (sysConfigManager.getRoleMergeFlag()) {
            List<Long> roleIds = new ArrayList<Long>();
            for (IRole role : roles) {
                roleIds.add(role.getRoleId());
            }
            Long[] ids = roleIds.toArray(new Long[roleIds.size()]);

            session.setAttribute(IRequest.FIELD_ALL_ROLE_ID, ids);
            session.setAttribute(IRequest.FIELD_ROLE_ID, roles.get(0).getRoleId());
        }
    }

    private String generateSecurityKey(HttpSession session) {
        return TokenUtils.setSecurityKey(session);
    }

    private SysPreferences getPreferences(List<SysPreferences> preferencesList, String preferenceName) {
        for (SysPreferences pref : preferencesList) {
            if (preferenceName.equals(pref.getPreferences())) {
                return pref;
            }
        }
        return null;
    }


}
