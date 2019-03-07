package com.jingrui.jrap.hr.controllers;

import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.hr.dto.Position;
import com.jingrui.jrap.hr.service.IPositionService;
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
 * 对岗位的操作.
 *
 * @author hailin.xu@jingrui.com
 */

@RestController
@RequestMapping(value = {"/hr/position", "/api/hr/position"})
public class PositionController extends BaseController {
    @Autowired
    private IPositionService positionService;

    @RequestMapping(value = "/query", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseData getPosition(HttpServletRequest request, Position position, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                    @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pagesize) {
        IRequest iRequest = createRequestContext(request);
        Criteria criteria = new Criteria(position);
        criteria.where(new WhereField(Position.FIELD_POSITION_CODE, Comparison.LIKE), Position.FIELD_POSITION_ID, Position.FIELD_NAME, Position.FIELD_PARENT_POSITION_ID);
        return new ResponseData(positionService.selectOptions(iRequest, position, criteria, page, pagesize));

    }

    @PostMapping(value = "/submit")
    public ResponseData submitPosition(@RequestBody List<Position> positions, BindingResult result, HttpServletRequest request) {
        getValidator().validate(positions, result);
        if (result.hasErrors()) {
            ResponseData rd = new ResponseData(false);
            rd.setMessage(getErrorMessage(result, request));
            return rd;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(positionService.batchUpdate(requestCtx, positions));
    }

    @PostMapping(value = "/remove")
    public ResponseData delete(HttpServletRequest request, @RequestBody List<Position> positions) {
        positionService.batchDelete(positions);
        return new ResponseData();
    }
}
