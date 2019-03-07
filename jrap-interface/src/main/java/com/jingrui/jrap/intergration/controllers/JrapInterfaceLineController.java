package com.jingrui.jrap.intergration.controllers;

import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.intergration.dto.JrapInterfaceLine;
import com.jingrui.jrap.intergration.service.IJrapInterfaceLineService;
import com.jingrui.jrap.system.controllers.BaseController;
import com.jingrui.jrap.system.dto.ResponseData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.UUID;

/**
 * @author jiguang.sun@jingrui.com
 * @version 2016/7/26.
 */
@Controller
@RequestMapping(value = {"/sys/api","/sys/interface","/api/sys/interface"})
public class JrapInterfaceLineController extends BaseController {

    private final Logger logger = LoggerFactory.getLogger(JrapInterfaceLineController.class);

    @Autowired
    private IJrapInterfaceLineService lineService;


    /*
    * get line and lineTl by lineId and language
    * */
    @RequestMapping(value = "/queryLine", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData getLineList(HttpServletRequest request, @ModelAttribute JrapInterfaceLine lineAndLineTlDTO) {
        logger.info("query line by LineAndLineTlDTO  lineId:{}", lineAndLineTlDTO.getLineId());
        IRequest iRequest = createRequestContext(request);
        return new ResponseData(lineService.getLineAndLineTl(iRequest, lineAndLineTlDTO));
    }


    /*
    * 新增一个接口
    * */
    @RequestMapping(value = "/insertLine", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData insertLine(HttpServletRequest request, @RequestBody List<JrapInterfaceLine> hmsInterfaceLines,BindingResult result) {
        // logger.info("add line by LineAndLineTlDTO  headerId:{}", hmsInterfaceLine.getHeaderId());
        getValidator().validate(hmsInterfaceLines, result);
        if (result.hasErrors()) {
            ResponseData rd = new ResponseData(false);
            rd.setMessage(getErrorMessage(result, request));
            return rd;
        }
        IRequest iRequest = createRequestContext(request);
        for (JrapInterfaceLine hmsInterfaceLine : hmsInterfaceLines) {
            hmsInterfaceLine.setLineId(UUID.randomUUID().toString());
            hmsInterfaceLine.setLineDescription(hmsInterfaceLine.getLineName());
            int updateRow = lineService.insertLine(iRequest, hmsInterfaceLine);
            if (updateRow <= 0) {
                return new ResponseData(false);
            }
        }
        return new ResponseData(hmsInterfaceLines);

    }


    /*
    * 更新
    * */
    @RequestMapping(value = "/updateLine", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData updateLine(HttpServletRequest request, @RequestBody List<JrapInterfaceLine> hmsInterfaceLines,BindingResult result) {
        //logger.info("update line by hmsInterfaceLine  lineId:{}", hmsInterfaceLine.getLineId());
        getValidator().validate(hmsInterfaceLines, result);
        if (result.hasErrors()) {
            ResponseData rd = new ResponseData(false);
            rd.setMessage(getErrorMessage(result, request));
            return rd;
        }

        IRequest iRequest = createRequestContext(request);
        for (JrapInterfaceLine hmsInterfaceLine : hmsInterfaceLines) {
            hmsInterfaceLine.setLineDescription(hmsInterfaceLine.getLineName());
            int updateRow = lineService.updateLine(iRequest, hmsInterfaceLine);
            if (updateRow <= 0) {
                return new ResponseData(false);
            }
        }
        return new ResponseData(hmsInterfaceLines);
    }

    /*
   * 删除
   * */
    @RequestMapping(value = "/deleteLine", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData deleteLine(HttpServletRequest request, @RequestBody List<JrapInterfaceLine> interfaceLines) {
        logger.info("delete line by hmsInterfaceLine  size:", interfaceLines.size());
        lineService.batchDelete(interfaceLines);
        return new ResponseData();
    }

    /*
    * 根据headerId获取lines
    * */
    @RequestMapping(value = "/getLinesByHeaderId", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData getLinesByHeaderId(HttpServletRequest request, JrapInterfaceLine lineAndLineTlDTO,
                                           @RequestParam(defaultValue = DEFAULT_PAGE) final int page, @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) final int pagesize) {

        IRequest iRequest = createRequestContext(request);

        return new ResponseData(lineService.getLinesByHeaderId(iRequest, lineAndLineTlDTO, page, pagesize));
    }


}
