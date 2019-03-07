package com.jingrui.jrap.system.controllers.sys;

import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.system.controllers.BaseController;
import com.jingrui.jrap.system.dto.DashBoard;
import com.jingrui.jrap.system.dto.ResponseData;
import com.jingrui.jrap.system.dto.UserDashboard;
import com.jingrui.jrap.system.service.IDashBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * DashBoardController.
 *
 * @author zhizheng.yang@jingrui.com
 */

@Controller
public class DashBoardController extends BaseController {

    @Autowired
    private IDashBoardService dashBoardService;

    /**
     * home page.
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/home.html")
    public ModelAndView home(HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        ModelAndView view = new ModelAndView(getViewPath() + "/home");
        UserDashboard userDashboard = new UserDashboard();
        userDashboard.setEnabledFlag("Y");
        List<UserDashboard> dashboards = dashBoardService.selectMyDashboardConfig(requestContext, userDashboard);
        view.addObject("dashboards", dashboards);
        return view;
    }

    /**
     * home page.
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/dashboard.html")
    public ModelAndView dashboard(HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        ModelAndView view = new ModelAndView(getViewPath() + "/dashboard");
        List<UserDashboard> dashboards = dashBoardService.selectMyDashboardConfig(requestContext, new UserDashboard());
        view.addObject("dashboards", dashboards);
        return view;
    }

    /**
     * Add dashboard.
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/dashboard/add")
    @ResponseBody
    public ResponseData addMyDashboard(HttpServletRequest request, @RequestBody List<UserDashboard> userDashboards) {
        IRequest requestContext = createRequestContext(request);
        for (UserDashboard userDashboard : userDashboards) {
            userDashboard.setUserId(requestContext.getUserId());
            dashBoardService.insertMyDashboard(createRequestContext(request), userDashboard);
        }
        return new ResponseData(dashBoardService.selectMyDashboardConfig(requestContext, new UserDashboard()));
    }

    /**
     * Save dashboard order.
     *
     * @param request
     * @param userDashboards
     * @return
     */
    @RequestMapping(value = "/dashboard/update")
    @ResponseBody
    public ResponseData updateMyDashboardConfig(HttpServletRequest request, @RequestBody List<UserDashboard> userDashboards) {
        dashBoardService.updateMyDashboardConfig(createRequestContext(request), userDashboards);
        return new ResponseData();
    }

    /**
     * Remove a dashboard.
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/dashboard/remove")
    @ResponseBody
    public ResponseData removeDashboard(HttpServletRequest request, @RequestBody List<UserDashboard> userDashboards) {
        IRequest requestContext = createRequestContext(request);
        for (UserDashboard userDashboard : userDashboards) {
            dashBoardService.removeDashboard(requestContext, userDashboard);
        }
        return new ResponseData(dashBoardService.selectMyDashboardConfig(requestContext, new UserDashboard()));
    }

    /**
     * DASHBOARD数据展示
     *
     * @param request
     * @param dashBoard
     * @param page
     * @param pagesize
     * @return ResponseData
     */
    @RequestMapping(value = {"/sys/dashboard/query", "/api/sys/dashboard/query"})
    @ResponseBody
    public ResponseData query(final DashBoard dashBoard, @RequestParam(defaultValue = DEFAULT_PAGE) final int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) final int pagesize, final HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(dashBoardService.selectDashBoard(requestContext, dashBoard, page, pagesize));
    }

    /**
     * 个人DASHBOARD数据展示
     *
     * @param request
     * @return ResponseData
     */
    @RequestMapping(value = "/dashboard/query")
    @ResponseBody
    public ResponseData queryMyDashBoard(final HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(dashBoardService.selectMyDashboardConfig(requestContext, new UserDashboard()));
    }

    /**
     * 删除/批量删除功能
     *
     * @param dashBoards dashBoards
     * @param result     BindingResult
     * @param request    HttpServletRequest
     * @return ResponseData
     */
    @RequestMapping(value = {"/sys/dashboard/remove", "/api/sys/dashboard/remove"})
    @ResponseBody
    public ResponseData remove(@RequestBody final List<DashBoard> dashBoards, final BindingResult result,
                               final HttpServletRequest request) {
        dashBoardService.batchDelete(dashBoards);
        return new ResponseData();
    }

    /**
     * 批量更新功能
     *
     * @param dashBoards dashBoards
     * @param result     BindingResult
     * @param request    HttpServletRequest
     * @return ResponseData
     */
    @RequestMapping(value = {"/sys/dashboard/submit", "/api/sys/dashboard/submit"})
    @ResponseBody
    public ResponseData submit(@RequestBody final List<DashBoard> dashBoards, final BindingResult result,
                               final HttpServletRequest request) {
        getValidator().validate(dashBoards, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(dashBoardService.batchUpdate(requestContext, dashBoards));
    }

}
