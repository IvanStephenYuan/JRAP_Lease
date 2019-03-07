package com.jingrui.jrap.hr.controllers;

import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.hr.dto.HrOrgUnit;
import com.jingrui.jrap.hr.service.IOrgUnitService;
import com.jingrui.jrap.mybatis.common.Criteria;
import com.jingrui.jrap.mybatis.common.query.Comparison;
import com.jingrui.jrap.mybatis.common.query.WhereField;
import com.jingrui.jrap.system.controllers.BaseController;
import com.jingrui.jrap.system.dto.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 对部门的操作.
 *
 * @author jialong.zuo@jingrui.com
 * @date 2016/9/16.
 */
@RestController
@RequestMapping(value = {"/hr/unit", "/api/hr/unit"})
public class UnitController extends BaseController {

    @Autowired
    IOrgUnitService service;

    @RequestMapping(value = "/query", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseData query(HttpServletRequest request, HrOrgUnit unit,
                              @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize) {
        IRequest requestCtx = createRequestContext(request);
        Criteria criteria = new Criteria(unit);
        criteria.where(new WhereField(HrOrgUnit.FIELD_UNIT_CODE, Comparison.LIKE), HrOrgUnit.FIELD_UNIT_ID, HrOrgUnit.FIELD_PARENT_ID, HrOrgUnit.FIELD_NAME, HrOrgUnit.FIELD_UNIT_TYPE);
        return new ResponseData(service.selectOptions(requestCtx, unit, criteria, page, pagesize));
    }

    @RequestMapping(value = "/queryall", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseData queryAllUnits(HttpServletRequest request) {
        IRequest requestCtx = createRequestContext(request);
        Criteria criteria = new Criteria();
        criteria.select(HrOrgUnit.FIELD_PARENT_NAME, HrOrgUnit.FIELD_NAME, HrOrgUnit.FIELD_UNIT_CODE, HrOrgUnit.FIELD_DESCRIPTION, HrOrgUnit.FIELD_UNIT_ID, HrOrgUnit.FIELD_PARENT_ID, HrOrgUnit.FIELD_POSITION_NAME);
        criteria.selectExtensionAttribute();
        HrOrgUnit unit = new HrOrgUnit();
        unit.setEnabledFlag("Y");
        return new ResponseData(service.selectOptions(requestCtx, unit, criteria));
    }

    @PostMapping(value = "/submit")
    public ResponseData update(HttpServletRequest request, @RequestBody List<HrOrgUnit> units, final BindingResult result) {
        getValidator().validate(units, result);
        if (result.hasErrors()) {
            ResponseData rd = new ResponseData(false);
            rd.setMessage(getErrorMessage(result, request));
            return rd;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, units));
    }

    @PostMapping(value = "/remove")
    public ResponseData delete(HttpServletRequest request, @RequestBody List<HrOrgUnit> units) {
        service.batchDelete(units);
        return new ResponseData();
    }

}
