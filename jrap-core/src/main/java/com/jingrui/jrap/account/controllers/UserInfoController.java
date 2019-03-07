package com.jingrui.jrap.account.controllers;

import com.jingrui.jrap.account.dto.User;
import com.jingrui.jrap.account.exception.UserException;
import com.jingrui.jrap.account.service.IUserInfoService;
import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.security.PasswordManager;
import com.jingrui.jrap.system.controllers.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户管理控制器.
 *
 * @author Zhaoqi
 * @author njq.niu@jingrui.com
 */
@RestController
public class UserInfoController extends BaseController {

    @Autowired
    private IUserInfoService userInfoService;

    @Autowired
    private PasswordManager passwordManager;

    @RequestMapping(value = "/sys/um/sys_user_info.html", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView userInfo(final HttpServletRequest request) throws UserException {
        ModelAndView mv = new ModelAndView(getViewPath() + "/sys/um/sys_user_info");
        IRequest requestContext = createRequestContext(request);
        User user = userInfoService.selectUserByPrimaryKey(requestContext, requestContext.getUserId());
        Integer length = passwordManager.getPasswordMinLength();
        String complexity = passwordManager.getPasswordComplexity();
        mv.addObject("user", user);
        mv.addObject("length", length);
        mv.addObject("complexity", complexity);
        return mv;
    }
}
