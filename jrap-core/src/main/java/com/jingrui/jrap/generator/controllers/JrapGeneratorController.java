package com.jingrui.jrap.generator.controllers;

import com.jingrui.jrap.generator.service.IJrapGeneratorService;
import com.jingrui.jrap.generator.dto.GeneratorInfo;
import com.jingrui.jrap.system.controllers.BaseController;
import com.jingrui.jrap.system.dto.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by jialong.zuo@jingrui.com on 2016/10/24.
 */
@Controller
@RequestMapping(value = "/generator")
public class JrapGeneratorController extends BaseController {
    @Autowired
    IJrapGeneratorService service;

    @RequestMapping(value = "/alltables", method = RequestMethod.GET)
    @ResponseBody
    public ResponseData showTables() {
        return new ResponseData(service.showTables());
    }

    @RequestMapping(value = "/newtables")
    @ResponseBody
    public int generatorTables(GeneratorInfo generatorInfo) {
        int rs = service.generatorFile(generatorInfo);
        return rs;
    }

}
