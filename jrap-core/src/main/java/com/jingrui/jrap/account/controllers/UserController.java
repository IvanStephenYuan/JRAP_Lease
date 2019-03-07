package com.jingrui.jrap.account.controllers;

import com.jingrui.jrap.account.dto.User;
import com.jingrui.jrap.account.exception.UserException;
import com.jingrui.jrap.account.service.IRoleService;
import com.jingrui.jrap.account.service.IUserInfoService;
import com.jingrui.jrap.account.service.IUserService;
import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.core.exception.BaseException;
import com.jingrui.jrap.function.dto.ResourceItemAssign;
import com.jingrui.jrap.system.controllers.BaseController;
import com.jingrui.jrap.system.dto.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;

/**
 * 用户控制器.
 *
 * @author njq.niu@jingrui.com
 * @date 2016/1/29
 *
 */
@RestController
@RequestMapping(value = {"/sys/user", "/api/sys/user"})
public class UserController extends BaseController {

    @Autowired
    private IUserService userService;

    @Autowired
    private IRoleService roleService;

    @Autowired
    private IUserInfoService userInfoService;

    @PostMapping(value = "/submitResourceItems")
    public ResponseData submitResourceItems(HttpServletRequest request,
                                            @RequestBody List<ResourceItemAssign> resourceItemAssignList,
                                            @RequestParam(required = false) Long userId,
                                            @RequestParam(required = false) Long functionId) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(userService.updateResourceItemAssign(requestContext, resourceItemAssignList, userId, functionId));
    }

    @PostMapping(value = "/deleteResourceItems")
    public ResponseData removeResourceItems(@RequestParam(required = false) Long userId,
                                            @RequestParam(required = false) Long functionId) {
        userService.deleteResourceItems(userId, functionId);
        return new ResponseData();
    }

    @PostMapping(value = "/queryResourceItems")
    public ResponseData queryResourceItems(HttpServletRequest request, @RequestParam(required = false) Long userId,
                                           @RequestParam(required = false) Long functionId) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(userService.queryResourceItems(requestContext, userId, functionId));
    }

    @PostMapping(value = "/queryFunction")
    public ResponseData queryFunction(HttpServletRequest request, @RequestParam(required = false) Long userId) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(userService.queryFunction(requestContext, userId));
    }

    @PostMapping(value = "/query")
    public ResponseData queryUsers(HttpServletRequest request,
                                    @ModelAttribute User user,
                                    @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                    @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize) {
        IRequest iRequest = createRequestContext(request);
        return new ResponseData(userService.selectUsers(iRequest, user, page, pagesize));
    }

    @PostMapping(value = "/submit")
    public ResponseData submitUsers(@RequestBody List<User> users,
                                    BindingResult result,
                                    HttpServletRequest request) throws BaseException {
        getValidator().validate(users, result);
        if (result.hasErrors()) {
            ResponseData rd = new ResponseData(false);
            rd.setMessage(getErrorMessage(result, request));
            return rd;
        }
        return new ResponseData(userService.batchUpdate(createRequestContext(request), users));
    }

    @PostMapping(value = "/update")
    public ResponseData updateUserInfo(HttpServletRequest request, @RequestBody User user) throws BaseException {
        IRequest iRequest = createRequestContext(request);
        userInfoService.update(iRequest, user);
        return new ResponseData(Collections.singletonList(user));
    }

    @PostMapping(value = "/remove")
    public ResponseData remove(@RequestBody List<User> users) throws BaseException {
        userService.batchDelete(users);
        return new ResponseData(users);
    }

    @PostMapping(value = "/{userId}/roles")
    public ResponseData queryUserAndRoles(HttpServletRequest request, @PathVariable Long userId) {
        IRequest iRequest = createRequestContext(request);
        ResponseData rd = new ResponseData();
        User user = new User();
        user.setUserId(userId);
        rd.setRows(roleService.selectRolesByUser(iRequest, user));
        return rd;
    }

    @PostMapping(value = "/password/reset")
    public ResponseData updatePassword(HttpServletRequest request, String password,
                                       String passwordAgain, Long userId) throws UserException {
        IRequest iRequest = createRequestContext(request);
        User user = new User();
        user.setUserId(userId);
        user.setPassword(password);
        userService.resetPassword(iRequest, user, passwordAgain);
        return new ResponseData(true);
    }

    @PostMapping(value = "/password/update")
    public ResponseData updatePassword(HttpServletRequest request, String oldPwd,
                                       String newPwd, String newPwdAgain) throws UserException {
        IRequest iRequest = createRequestContext(request);
        userService.updateOwnerPassword(iRequest, oldPwd, newPwd, newPwdAgain);
        return new ResponseData(true);
    }

}
