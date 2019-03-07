/*
 * #{copyright}#
 */
package com.jingrui.jrap.adaptor.impl;

import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.UUID;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.common.base.Throwables;
import com.jingrui.jrap.account.dto.Role;
import com.jingrui.jrap.account.dto.User;
import com.jingrui.jrap.account.exception.RoleException;
import com.jingrui.jrap.account.exception.UserException;
import com.jingrui.jrap.account.service.IRole;
import com.jingrui.jrap.account.service.IRoleService;
import com.jingrui.jrap.account.service.IUserService;
import com.jingrui.jrap.adaptor.ILoginAdaptor;
import com.jingrui.jrap.core.BaseConstants;
import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.core.components.CaptchaConfig;
import com.jingrui.jrap.core.components.SysConfigManager;
import com.jingrui.jrap.core.impl.RequestHelper;
import com.jingrui.jrap.core.util.TimeZoneUtil;
import com.jingrui.jrap.security.IUserSecurityStrategy;
import com.jingrui.jrap.security.TokenUtils;
import com.jingrui.jrap.security.captcha.ICaptchaManager;
import com.jingrui.jrap.security.service.impl.UserSecurityStrategyManager;
import com.jingrui.jrap.system.dto.ResponseData;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.util.HtmlUtils;
import org.springframework.web.util.WebUtils;

/**
 * 默认登陆代理类.
 *
 * @author njq.niu@jingrui.com
 * @author xiawang.liu@jingrui.com 2016年1月19日 TODO:URL和页面分开
 */
public class DefaultLoginAdaptor implements ILoginAdaptor {

    private static final boolean VALIDATE_CAPTCHA = true;

    /**
     * 校验码
     */
    private static final String KEY_VERIFICODE = "verifiCode";

    /**
     * 默认主页
     */
    private static final String VIEW_INDEX = "/";

    /**
     * 默认的登录页
     */
    private static final String VIEW_LOGIN = "/login";

    /**
     * 默认角色选择路径
     */
    private static final String VIEW_ROLE_SELECT = "/role";


    @Autowired
    private ICaptchaManager captchaManager;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    @Qualifier("roleServiceImpl")
    private IRoleService roleService;

    @Autowired
    private IUserService userService;

    @Autowired
    private CaptchaConfig captchaConfig;


    @Autowired
    private SysConfigManager sysConfigManager;

    @Autowired
    UserSecurityStrategyManager userSecurityStrategyManager;


    public ModelAndView doLogin(User user, HttpServletRequest request, HttpServletResponse response) {

        ModelAndView view = new ModelAndView();
        Locale locale = RequestContextUtils.getLocale(request);
        view.setViewName(getLoginView(request));
        try {
            beforeLogin(view, user, request, response);
            checkCaptcha(view, user, request, response);
            user = userService.login(user);
            HttpSession session = request.getSession(true);
            session.setAttribute(User.FIELD_USER_ID, user.getUserId());
            session.setAttribute(User.FIELD_USER_NAME, user.getUserName());
            session.setAttribute(IRequest.FIELD_LOCALE, locale.toString());
            setTimeZoneFromPreference(session, user.getUserId());
            generateSecurityKey(session);
            afterLogin(view, user, request, response);
        } catch (UserException e) {
            view.addObject("msg", messageSource.getMessage(e.getCode(), e.getParameters(), locale));
            view.addObject("code", e.getCode());
            processLoginException(view, user, e, request, response);
        }
        return view;
    }

    private void setTimeZoneFromPreference(HttpSession session, Long accountId) {
        String tz = "GMT+0800";
        if (StringUtils.isBlank(tz)) {
            tz = TimeZoneUtil.toGMTFormat(TimeZone.getDefault());
        }
        session.setAttribute(BaseConstants.PREFERENCE_TIME_ZONE, tz);
    }

    private String generateSecurityKey(HttpSession session) {
        return TokenUtils.setSecurityKey(session);
    }

    /**
     * 登陆前逻辑.
     *
     * @param view     视图
     * @param account  账号
     * @param request  请求
     * @param response 响应
     * @throws UserException 异常
     */
    protected void beforeLogin(ModelAndView view, User account, HttpServletRequest request,
                               HttpServletResponse response) throws UserException {

    }

    /**
     * 处理登陆异常.
     *
     * @param view
     * @param account
     * @param e
     * @param request
     * @param response
     */
    protected void processLoginException(ModelAndView view, User account, UserException e, HttpServletRequest request,
                                         HttpServletResponse response) {

    }

    /**
     * 校验验证码是否正确.
     *
     * @param view     视图
     * @param user     账号
     * @param request  请求
     * @param response 响应
     * @throws UserException 异常
     */
    private void checkCaptcha(ModelAndView view, User user, HttpServletRequest request, HttpServletResponse response)
            throws UserException {
        if (VALIDATE_CAPTCHA) {
            Cookie cookie = WebUtils.getCookie(request, captchaManager.getCaptchaKeyName());
            String captchaCode = request.getParameter(KEY_VERIFICODE);
            if (cookie == null || StringUtils.isEmpty(captchaCode)
                    || !captchaManager.checkCaptcha(cookie.getValue(), captchaCode)) {
                throw new UserException(UserException.ERROR_INVALID_CAPTCHA, UserException.ERROR_INVALID_CAPTCHA, null);
            }
        }
    }

    /**
     * 账号登陆成功后处理逻辑.
     *
     * @param view     视图
     * @param user     账号
     * @param request  请求
     * @param response 响应
     * @throws UserException 异常
     */
    protected void afterLogin(ModelAndView view, User user, HttpServletRequest request, HttpServletResponse response)
            throws UserException {
        view.setViewName(BaseConstants.VIEW_REDIRECT + getRoleView(request));
        Cookie cookie = new Cookie(User.FIELD_USER_NAME, user.getUserName());
        cookie.setPath(StringUtils.defaultIfEmpty(request.getContextPath(), "/"));
        cookie.setMaxAge(-1);
        response.addCookie(cookie);
    }

    @Override
    public ModelAndView doSelectRole(IRole role, HttpServletRequest request, HttpServletResponse response)
            throws RoleException {
        ModelAndView result = new ModelAndView();
        // 选择角色
        HttpSession session = request.getSession(false);
        if (session != null && role != null && role.getRoleId() != null) {
            Long userId = (Long) session.getAttribute(User.FIELD_USER_ID);
            roleService.checkUserRoleExists(userId, role.getRoleId());
            if (!sysConfigManager.getRoleMergeFlag()) {
                Long[] ids = new Long[1];
                ids[0] = role.getRoleId();
                session.setAttribute(IRequest.FIELD_ALL_ROLE_ID, ids);
            }
            session.setAttribute(IRequest.FIELD_ROLE_ID, role.getRoleId());
            String targetUrl = (String) session.getAttribute("targetUrl");
            if (targetUrl != null) {
                result.setViewName(BaseConstants.VIEW_REDIRECT + targetUrl);
            } else {
                result.setViewName(BaseConstants.VIEW_REDIRECT + getIndexView(request));
            }
            session.removeAttribute("targetUrl");
        } else {
            result.setViewName(BaseConstants.VIEW_REDIRECT + getLoginView(request));
        }
        return result;
    }

    /**
     * 获取主界面.
     *
     * @param request
     * @return 视图
     */
    protected String getIndexView(HttpServletRequest request) {
        return VIEW_INDEX;
    }

    /**
     * 获取登陆界面.
     *
     * @param request
     * @return 视图
     */
    protected String getLoginView(HttpServletRequest request) {
        return VIEW_LOGIN;
    }


    /**
     * 获取角色选择界面.
     *
     * @param request
     * @return 视图
     */
    protected String getRoleView(HttpServletRequest request) {
        return VIEW_ROLE_SELECT;
    }

    /**
     * 集成类中可扩展此方法实现不同的userService.
     *
     * @return IUserService
     */
    public IUserService getUserService() {
        return userService;
    }

    @Override
    public ModelAndView indexView(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        ModelAndView mav = indexModelAndView(request, response);
        if (session != null) {
            // 获取user
            String userName = (String) session.getAttribute(User.FIELD_USER_NAME);
            Long userId = (Long) session.getAttribute(User.FIELD_USER_ID);
            if (userName != null) {
                if (session.getAttribute(User.LOGIN_CHANGE_INDEX) != null) {
                    User user = userService.selectByUserName(userName);
                    List<IUserSecurityStrategy> userSecurityStrategies = userSecurityStrategyManager.getUserSecurityStrategyList();
                    for (IUserSecurityStrategy userSecurityStrategy : userSecurityStrategies) {
                        ModelAndView mv = userSecurityStrategy.loginVerifyStrategy(user, request);
                        if (mv != null) {
                            return mv;
                        }
                    }
                    session.removeAttribute(User.LOGIN_CHANGE_INDEX);
                }
            } else {
                return new ModelAndView(BaseConstants.VIEW_REDIRECT + getLoginView(request));
            }
            // 角色选择
            if (!sysConfigManager.getRoleMergeFlag()) {
                Long roleId = (Long) session.getAttribute(Role.FIELD_ROLE_ID);
                if (roleId == null) {
                    return new ModelAndView(BaseConstants.VIEW_REDIRECT + getRoleView(request));
                }
                //查询用户角色为角色切换。
                User user = new User();
                user.setUserId(userId);
                user.setUserName(userName);
                List<IRole> roles = roleService.selectActiveRolesByUser(RequestHelper.createServiceRequest(request), user);
                mav.addObject("SYS_USER_ROLES", roles);
                mav.addObject("CURRENT_USER_ROLE", roleId);
            }
        }

        mav.addObject("SYS_TITLE", HtmlUtils.htmlEscape(sysConfigManager.getSysTitle()));
        return mav;
    }

    /**
     * 默认登陆页面.
     *
     * @param request
     * @param response
     * @return 视图
     */
    public ModelAndView indexModelAndView(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView("index");
    }

    @Override
    public ModelAndView loginView(HttpServletRequest request, HttpServletResponse response) {

        ModelAndView view = new ModelAndView(getLoginView(request));
        // 配置3次以后开启验证码
        Cookie cookie = WebUtils.getCookie(request, CaptchaConfig.LOGIN_KEY);
        if (captchaConfig.getWrongTimes() > 0) {
            if (cookie == null) {
                String uuid = UUID.randomUUID().toString();
                cookie = new Cookie(CaptchaConfig.LOGIN_KEY, uuid);
                cookie.setPath(StringUtils.defaultIfEmpty(request.getContextPath(), "/"));
                cookie.setMaxAge(captchaConfig.getExpire());
                cookie.setHttpOnly(true);
                if (SysConfigManager.useHttps) {
                    cookie.setSecure(true);
                }
                response.addCookie(cookie);
                captchaConfig.updateLoginFailureInfo(cookie);
            }
        }

        // 向前端传递是否开启验证码
        view.addObject("ENABLE_CAPTCHA", captchaConfig.isEnableCaptcha(cookie));

        view.addObject("SYS_TITLE", HtmlUtils.htmlEscape(sysConfigManager.getSysTitle()));

        Boolean error = (Boolean) request.getAttribute("error");
        Throwable exception = (Exception) request.getAttribute("exception");
        String code = UserException.ERROR_USER_PASSWORD;
        if (exception != null && !(exception instanceof BadCredentialsException)) {
            exception = Throwables.getRootCause(exception);
            code = exception.getMessage();
        }
        if (error != null && error) {
            String msg;
            Locale locale = RequestContextUtils.getLocale(request);
            msg = messageSource.getMessage(code, null, locale);
            view.addObject("msg", msg);
        }

        return view;
    }

    @Override
    public ModelAndView roleView(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView(getRoleView(request));
        HttpSession session = request.getSession(false);
        mv.addObject("SYS_TITLE", HtmlUtils.htmlEscape(sysConfigManager.getSysTitle()));
        if (session != null) {
            // 获取user
            Long userId = (Long) session.getAttribute(User.FIELD_USER_ID);
            if (userId != null) {
                User user = new User();
                user.setUserId(userId);
                user.setUserName((String) session.getAttribute(User.FIELD_USER_NAME));
                session.setAttribute(User.FIELD_USER_ID, userId);
                addCookie(User.FIELD_USER_ID, userId.toString(), request, response);
                List<IRole> roles = roleService.selectActiveRolesByUser(RequestHelper.createServiceRequest(request), user);
                mv.addObject("roles", roles);
            }
        }
        return mv;
    }

    @Override
    public ModelAndView casLoginFailure(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView view = new ModelAndView("cas_login_failure");
        Throwable exception = (Exception) request.getAttribute("exception");
        String code = UserException.ERROR_USER_PASSWORD;
        if (exception != null) {
            exception = Throwables.getRootCause(exception);
            code = exception.getMessage();
        }
        Locale locale = RequestContextUtils.getLocale(request);
        String errorMessage = messageSource.getMessage(code, null, locale);
        view.addObject("errorMessage", errorMessage);
        return view;
    }

    protected void addCookie(String cookieName, String cookieValue, HttpServletRequest request,
                             HttpServletResponse response) {
        Cookie cookie = new Cookie(cookieName, cookieValue);
        cookie.setPath(StringUtils.defaultIfEmpty(request.getContextPath(), "/"));
        cookie.setMaxAge(-1);
        response.addCookie(cookie);
    }

    @Override
    public ResponseData sessionExpiredLogin(User account, HttpServletRequest request, HttpServletResponse response)
            throws RoleException {
        ResponseData data = new ResponseData();
        ModelAndView view = this.doLogin(account, request, response);
        ModelMap mm = view.getModelMap();
        if (mm.containsAttribute("code")) {
            data.setSuccess(false);
            data.setCode((String) mm.get("code"));
            data.setMessage((String) mm.get("msg"));
        } else {
            Object userIdObj = request.getParameter(User.FIELD_USER_ID);
            Object roleIdObj = request.getParameter(IRequest.FIELD_ROLE_ID);
            if (userIdObj != null && roleIdObj != null) {
                Long userId = Long.valueOf(userIdObj.toString()), roleId = Long.valueOf(roleIdObj.toString());
                roleService.checkUserRoleExists(userId, roleId);
                HttpSession session = request.getSession();
                session.setAttribute(User.FIELD_USER_ID, userId);
                session.setAttribute(IRequest.FIELD_ROLE_ID, roleId);
            }
        }
        return data;
    }
}
