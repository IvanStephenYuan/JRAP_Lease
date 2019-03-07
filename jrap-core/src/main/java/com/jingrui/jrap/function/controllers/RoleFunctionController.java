package com.jingrui.jrap.function.controllers;

import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.core.exception.BaseException;
import com.jingrui.jrap.function.dto.MenuItem;
import com.jingrui.jrap.function.dto.ResourceItemAssign;
import com.jingrui.jrap.function.dto.RoleFunction;
import com.jingrui.jrap.function.service.IFunctionService;
import com.jingrui.jrap.function.service.IRoleFunctionService;
import com.jingrui.jrap.function.service.IRoleResourceItemService;
import com.jingrui.jrap.system.controllers.BaseController;
import com.jingrui.jrap.system.dto.ResponseData;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 对角色功能分配的操作.
 *
 * @author liuxiawang
 * @author njq.niu@jingrui.com
 * @author qiang.zeng@jingrui.com
 */
@RestController
@RequestMapping(value = {"/sys/rolefunction", "/api/sys/rolefunction"})
public class RoleFunctionController extends BaseController {

    @Autowired
    private IRoleFunctionService roleFunctionService;

    @Autowired
    private IFunctionService functionService;

    @Autowired
    private IRoleResourceItemService roleResourceItemService;

    @PostMapping(value = "/query")
    public ResponseData queryRoleFunction(HttpServletRequest request, @RequestParam(required = false) Long roleId)
            throws BaseException {
        IRequest requestContext = createRequestContext(request);
        List<MenuItem> menus = functionService.selectAllMenus(requestContext);
        if (roleId != null) {
            Long[] ids = roleFunctionService.getRoleFunctionById(roleId);
            updateMenuCheck(menus, ids);

        }
        return new ResponseData(menus);
    }

    @PostMapping(value = "/submit")
    public ResponseData submit(HttpServletRequest request, @RequestBody List<RoleFunction> records)
            throws BaseException {
        return new ResponseData(roleFunctionService.batchUpdate(createRequestContext(request), records));
    }

    @PostMapping("/queryResourceItems")
    public ResponseData queryResourceItems(HttpServletRequest request, @RequestParam(required = false) Long roleId,
                                           @RequestParam(required = false) Long functionId) {
        return new ResponseData(roleResourceItemService.queryResourceItems(createRequestContext(request), roleId, functionId));
    }

    @PostMapping("/submitResourceItems")
    public ResponseData submitResourceItems(HttpServletRequest request,
                                            @RequestBody List<ResourceItemAssign> resourceItemAssignList, @RequestParam(required = false) Long roleId,
                                            @RequestParam(required = false) Long functionId) {
        return new ResponseData(roleResourceItemService.updateResourceItemAssign(createRequestContext(request), resourceItemAssignList,
                roleId, functionId));
    }

    /**
     * 处理勾选状态.
     *
     * @param menus 菜单
     * @param ids   功能Id集合
     */
    private void updateMenuCheck(final List<MenuItem> menus, final Long[] ids) {
        if (CollectionUtils.isEmpty(menus) || ArrayUtils.isEmpty(ids)) {
            return;
        }
        for (MenuItem menuItem : menus) {
            if (menuItem.getChildren() != null && !menuItem.getChildren().isEmpty()) {
                updateMenuCheck(menuItem.getChildren(), ids);
            }
            for (Long id : ids) {
                if (menuItem.getId().equals(id)) {
                    menuItem.setIschecked(Boolean.TRUE);
                    break;
                }
            }
        }
    }

}
