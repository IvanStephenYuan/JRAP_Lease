package com.jingrui.jrap.account.controllers;

import com.jingrui.jrap.account.dto.UserRole;
import com.jingrui.jrap.account.service.IUserRoleService;
import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.core.exception.BaseException;
import com.jingrui.jrap.system.controllers.BaseController;
import com.jingrui.jrap.system.dto.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 用户角色分配控制器.
 *
 * @author xiawang.liu@jingrui.com
 */

@RestController
public class UserRoleController extends BaseController {

    @Autowired
    private IUserRoleService userRoleService;

    @PostMapping(value = "/sys/userrole/submit")
    public ResponseData submitUserRole(HttpServletRequest request, @RequestBody List<UserRole> userRoles,
                                       BindingResult result) throws BaseException {
        getValidator().validate(userRoles, result);
        if (result.hasErrors()) {
            ResponseData rd = new ResponseData(false);
            rd.setMessage(getErrorMessage(result, request));
            return rd;
        }
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(userRoleService.batchUpdate(requestContext, userRoles));
    }

}
