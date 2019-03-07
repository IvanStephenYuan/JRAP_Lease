package com.jingrui.jrap.intergration.controllers;


import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.core.exception.TokenException;
import com.jingrui.jrap.intergration.dto.JrapInterfaceHeader;
import com.jingrui.jrap.intergration.service.IJrapInterfaceHeaderService;
import com.jingrui.jrap.intergration.service.IJrapInterfaceLineService;
import com.jingrui.jrap.system.controllers.BaseController;
import com.jingrui.jrap.system.dto.Code;
import com.jingrui.jrap.system.dto.ResponseData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * @author jiguang.sun@jingrui.com
 *         xiangyu.qi@jingrui.com 2016/11/01
 * @version 2016/7/21
 */

@Controller
@RequestMapping(value = {"/sys/api","/sys/interface","/api/sys/interface"})
public class JrapInterfaceHeaderController extends BaseController {

    private final Logger logger = LoggerFactory.getLogger(JrapInterfaceHeaderController.class);

    @Autowired
    private IJrapInterfaceHeaderService headerService;

    @Autowired
    private IJrapInterfaceLineService lineService;


    /**
     * 获取所有的系统路径
     */
    @RequestMapping(value = "/queryAllHeader", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData getHeaderList(HttpServletRequest request, JrapInterfaceHeader headerAndHeaderTlDTO
       ,@RequestParam(defaultValue = DEFAULT_PAGE) final int page, @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) final int pagesize) {

        IRequest iRequest = createRequestContext(request);
        List<JrapInterfaceHeader> list = headerService.getAllHeader(iRequest, headerAndHeaderTlDTO, page, pagesize);
        return new ResponseData(list);

    }



    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    public ResponseData submitCode(@RequestBody List<JrapInterfaceHeader> interfaces, BindingResult result, HttpServletRequest request) throws TokenException {
        checkToken(request, interfaces);
        getValidator().validate(interfaces, result);
        if (result.hasErrors()) {
            ResponseData rd = new ResponseData(false);
            rd.setMessage(getErrorMessage(result, request));
            return rd;
        }
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(headerService.batchUpdate(requestContext, interfaces));
    }



    /*
    * 新增 HmsInterfaceHeader
    * */
    @RequestMapping(value = "/addHeader", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData addHeader(HttpServletRequest request, @RequestBody JrapInterfaceHeader hapInterfaceHeader,BindingResult result) {

        getValidator().validate(hapInterfaceHeader, result);
        if (result.hasErrors()) {
            ResponseData rd = new ResponseData(false);
            rd.setMessage(getErrorMessage(result, request));
            return rd;
        }
        IRequest iRequest = createRequestContext(request);
        hapInterfaceHeader.setHeaderId(UUID.randomUUID().toString());
        hapInterfaceHeader.setDescription(hapInterfaceHeader.getName());
        JrapInterfaceHeader hapInterfaceHeaderNew = headerService.insertSelective(iRequest, hapInterfaceHeader);

        if (hapInterfaceHeaderNew != null) {
            return new ResponseData();
        } else {
            return new ResponseData(false);
        }

    }

    /*
    * 更新 HeaderAndHeaderTlDTO
    * */
    @RequestMapping(value = "/updateHeader", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData updateHeader(HttpServletRequest request, @RequestBody JrapInterfaceHeader hapInterfaceHeader,BindingResult result) {

        getValidator().validate(hapInterfaceHeader, result);
        if (result.hasErrors()) {
            ResponseData rd = new ResponseData(false);
            rd.setMessage(getErrorMessage(result, request));
            return rd;
        }

        IRequest iRequest = createRequestContext(request);

        hapInterfaceHeader.setDescription(hapInterfaceHeader.getName());

        int updateRow = headerService.updateHeader(iRequest, hapInterfaceHeader);

        if (updateRow > 0) {
            return new ResponseData(true);
        } else {
            return new ResponseData(false);
        }


    }

    /*
   * 删除 HmsInterfaceHeader
   * */
    @RequestMapping(value = "/deleteHeader", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData deleteHeader(HttpServletRequest request,@RequestBody  List<JrapInterfaceHeader> interfaceHeaders) {

        IRequest iRequest = createRequestContext(request);
        //删除行
        lineService.batchDeleteByHeaders(iRequest,interfaceHeaders);
        //删除头
        headerService.batchDelete(interfaceHeaders);

        return new ResponseData();

    }

    /*
    * 根据headerId 查询 header and line
    * */
    @RequestMapping(value = "/getHeaderAndLine", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData getHeaderAndLine(HttpServletRequest request, @RequestBody JrapInterfaceHeader headerAndHeaderTlDTO) {

        IRequest iRequest = createRequestContext(request);

        return new ResponseData(headerService.getHeaderAndLineList(iRequest,headerAndHeaderTlDTO));

    }

    /*
    * 根据headerId获取header
    * */
    @RequestMapping(value = "/getHeaderByHeaderId")
    @ResponseBody
    public ResponseData getHeaderByHeaderId(HttpServletRequest request,  JrapInterfaceHeader headerAndHeaderTlDTO) {

        IRequest iRequest = createRequestContext(request);

        return new ResponseData(headerService.getHeaderByHeaderId(iRequest,headerAndHeaderTlDTO));
    }


    /*
    * 根据lineId获取headerAndLine
    * */
    @RequestMapping(value = "/getHeaderAndLineByLineId", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData getHeaderAndLineByLineId(HttpServletRequest request, JrapInterfaceHeader headerAndLineDTO) {
        logger.info("getHeaderAndLineByLineId lineId:{}", headerAndLineDTO.getLineId());
        JrapInterfaceHeader headerAndLineDTO1 = headerService.getHeaderAndLineByLineId(headerAndLineDTO);

        return new ResponseData(Arrays.asList(headerAndLineDTO1));
    }

    /*
    * 所有有效的请求
    * */
    @RequestMapping(value = "/getAllHeaderAndLine", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData getAllHeaderAndLine(HttpServletRequest request,
                                            @RequestParam(defaultValue = DEFAULT_PAGE) final int page, @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) final int pagesize) {

        return new ResponseData(headerService.getAllHeaderAndLine(page,pagesize));
    }


}
