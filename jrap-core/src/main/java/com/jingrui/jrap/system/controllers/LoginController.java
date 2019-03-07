package com.jingrui.jrap.system.controllers;

import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.codahale.metrics.annotation.Timed;
import com.jingrui.jrap.account.dto.Role;
import com.jingrui.jrap.account.dto.User;
import com.jingrui.jrap.account.exception.RoleException;
import com.jingrui.jrap.account.exception.UserException;
import com.jingrui.jrap.account.service.IUserService;
import com.jingrui.jrap.adaptor.ILoginAdaptor;
import com.jingrui.jrap.adaptor.impl.DefaultLoginAdaptor;
import com.jingrui.jrap.core.BaseConstants;
import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.core.exception.BaseException;
import com.jingrui.jrap.core.exception.IBaseException;
import com.jingrui.jrap.security.service.impl.DefaultUserSecurityStrategy;
import com.jingrui.jrap.system.dto.ResponseData;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContextUtils;

/**
 * 用户登录控制层.
 *
 * @author wuyichu
 * @author njq.niu@jingrui.com
 */
@Controller
public class LoginController extends BaseController implements InitializingBean {

    @Autowired(required = false)
    private ILoginAdaptor loginAdaptor;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private IUserService userService;

    @RequestMapping(value = {"/login.html", "/login"})
    @Timed
    public ModelAndView loginView(final HttpServletRequest request, final HttpServletResponse response) {
        return getLoginAdaptor().loginView(request, response);
    }

    @GetMapping(value = {"/role.html", "/role"})
    public ModelAndView roleView(final HttpServletRequest request, final HttpServletResponse response)
            throws BaseException {
        return getLoginAdaptor().roleView(request, response);
    }

    @GetMapping(value = {"/", "/index.html"})
    public ModelAndView indexView(final HttpServletRequest request, final HttpServletResponse response) {
        return getLoginAdaptor().indexView(request, response);
    }

    @PostMapping(value = "/role")
    public ModelAndView selectRole(final Role role, final HttpServletRequest request,
                                   final HttpServletResponse response) throws RoleException {
        return getLoginAdaptor().doSelectRole(role, request, response);
    }

    @RequestMapping(value = "/sys/role/change")
    public ModelAndView changeRole(HttpServletRequest request, HttpServletResponse response, Long roleId) throws RoleException {
        Role role = new Role();
        role.setRoleId(roleId);
        return getLoginAdaptor().doSelectRole(role, request, response);
    }

    @PostMapping(value = "/sessionExpiredLogin")
    public ResponseData sessionExpiredLogin(final User account, final HttpServletRequest request,
                                            final HttpServletResponse response) throws BaseException {
        return getLoginAdaptor().sessionExpiredLogin(account, request, response);
    }

    @GetMapping(value = "/password/reset")
    public ModelAndView resetPassword(final HttpServletRequest request) {
        // 首次登录 密码过期跳转修改密码页面
        ModelAndView view = new ModelAndView("update_password");
        HttpSession session = request.getSession(false);
        if (session != null) {
            String reason = (String) session.getAttribute(DefaultUserSecurityStrategy.PASSWORD_UPDATE_REASON);
            Locale locale = RequestContextUtils.getLocale(request);
            view.addObject("update_password_title", updatePasswordTitle(reason, locale));
        }
        return view;
    }

    @PostMapping(value = "/password/reset")
    public ModelAndView resetPassword(HttpServletRequest request, String newPwd, String newPwdAgain) throws UserException {
        // 首次登录修改密码提交，密码过期提交
        ModelAndView view = new ModelAndView("update_password");
        IRequest iRequest = createRequestContext(request);
        HttpSession session = request.getSession(false);
        String reason = (String) session.getAttribute(DefaultUserSecurityStrategy.PASSWORD_UPDATE_REASON);
        try {
            if (reason != null) {
                request.getSession(false).removeAttribute(DefaultUserSecurityStrategy.PASSWORD_UPDATE_REASON);
                userService.firstAndExpiredLoginUpdatePassword(iRequest, newPwd, newPwdAgain);
                return new ModelAndView(BaseConstants.VIEW_REDIRECT + "/");
            }
        } catch (Exception e) {
            if (e instanceof IBaseException) {
                IBaseException be = (IBaseException) e;
                Locale locale = RequestContextUtils.getLocale(request);
                String messageKey = be.getDescriptionKey();
                String message = getMessageSource().getMessage(messageKey, be.getParameters(), messageKey, locale);
                view.addObject("update_password_title", updatePasswordTitle(reason, locale));
                view.addObject("message", message);
            } else {
                throw e;
            }
        }
        return view;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (loginAdaptor == null) {
            loginAdaptor = new DefaultLoginAdaptor();
            applicationContext.getAutowireCapableBeanFactory().autowireBean(loginAdaptor);
        }
    }

    @GetMapping(value = "/casLoginFailure")
    public ModelAndView casLoginError(HttpServletRequest request, HttpServletResponse response) throws UserException {
        return getLoginAdaptor().casLoginFailure(request, response);
    }

    private ILoginAdaptor getLoginAdaptor() {
        return loginAdaptor;
    }

    private String updatePasswordTitle(String reason, Locale locale) {
        String reasonTitle = "";
        switch (reason) {
            case DefaultUserSecurityStrategy.PASSWORD_UPDATE_REASON_EXPIRED:
                reasonTitle = getMessageSource().getMessage("error.user.update_password", null, locale);
                break;
            case DefaultUserSecurityStrategy.PASSWORD_UPDATE_REASON_RESET:
                reasonTitle = getMessageSource().getMessage("sys.config.resetpassword", null, locale);
                break;
            default:
                break;
        }
        return reasonTitle;
    }
}
