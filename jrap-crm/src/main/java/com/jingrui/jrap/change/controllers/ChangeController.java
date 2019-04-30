/*
 * *
 *  @file com.maddyhome.idea.copyright.pattern.JavaCopyrightVariablesProvider$1@38ebd38e$
 *  @CopyRight (C) 2018 ZheJiangJingRui Co. Ltd.
 *  @brief JingRui Application Platform
 *  @author $name$
 *  @email yulong.yuan@jr-info.cn
 *  @date $date$
 * /
 */

package com.jingrui.jrap.change.controllers;

import com.jingrui.jrap.change.dto.ChangeAssign;
import com.jingrui.jrap.change.dto.ChangeTrack;
import com.jingrui.jrap.change.dto.Contact;
import com.jingrui.jrap.change.mapper.ChangeAssignMapper;
import com.jingrui.jrap.change.mapper.ChangeTrackMapper;
import com.jingrui.jrap.change.service.IChangeAssignService;
import com.jingrui.jrap.change.service.IChangeTrackService;
import com.jingrui.jrap.change.service.IContactService;
import com.jingrui.jrap.code.rule.exception.CodeRuleException;
import com.jingrui.jrap.code.rule.service.ISysCodeRuleProcessService;
import com.jingrui.jrap.product.service.IDocumentTypeService;
import java.util.ArrayList;
import org.springframework.stereotype.Controller;
import com.jingrui.jrap.system.controllers.BaseController;
import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.system.dto.ResponseData;
import com.jingrui.jrap.change.service.IChangeService;
import com.jingrui.jrap.change.dto.Change;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import org.springframework.validation.BindingResult;
import java.util.List;

@Controller
public class ChangeController extends BaseController {

  private static final String CHANGE_RULE_CODE = "CHANGE";


  @Autowired
  private IChangeService service;
  @Autowired
  private ISysCodeRuleProcessService codeRuleProcessService;

  @Autowired
  private IDocumentTypeService documentTypeService;

  @Autowired
  ChangeTrackMapper changeTrackMapper;

  @Autowired
  ChangeAssignMapper changeAssignMapper;

  @Autowired
  private IChangeTrackService iChangeTrackService;

  @Autowired
  private IChangeAssignService iChangeAssignService;
  @Autowired
  private IContactService iContactService;

  @RequestMapping(value = "/con/change/query")
  @ResponseBody
  public ResponseData query(Change dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
      @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
    IRequest requestContext = createRequestContext(request);
    return new ResponseData(service.select(requestContext, dto, page, pageSize));
  }

  @RequestMapping(value = "/con/change/submit")
  @ResponseBody
  public ResponseData update(@RequestBody List<Change> dto, BindingResult result,
      HttpServletRequest request) {
    getValidator().validate(dto, result);
    if (result.hasErrors()) {
      ResponseData responseData = new ResponseData(false);
      responseData.setMessage(getErrorMessage(result, request));
      return responseData;
    }
    IRequest requestCtx = createRequestContext(request);
    for (int i = 0; i < dto.size(); i++) {
      dto.get(i).setCompanyId(requestCtx.getCompanyId());
      //设置商机编码
      String changecode = dto.get(i).getChangeCode();
      if (changecode == null || "".equalsIgnoreCase(changecode)) {
        try {
          String ruleCode = documentTypeService
              .getDocumentCodeRule(dto.get(i).getDocumentCategory(), dto.get(i).getDocumentType());
          if (ruleCode == null || "ERROR".equalsIgnoreCase(ruleCode)) {
            ruleCode = ChangeController.CHANGE_RULE_CODE;
          }
          String changeCode = codeRuleProcessService.getRuleCode(ruleCode);
          dto.get(i).setChangeCode(changeCode);
        } catch (CodeRuleException e) {
          e.printStackTrace();
        }
      }
    }
    return new ResponseData(service.batchUpdate(requestCtx, dto));
  }

  @RequestMapping(value = "/con/change/remove")
  @ResponseBody
  public ResponseData delete(HttpServletRequest request, @RequestBody List<Change> dto) {
    IRequest requestCtx = createRequestContext(request);
    service.batchDelete(dto);
    //循环遍历商机，并将其id查找出来
    List<ChangeTrack> lchangetrk = new ArrayList<>();
    List<ChangeAssign> lchangeasa = new ArrayList<>();
    for (Change change : dto) {
      //通过changeid查找出追踪信息的集合
      Long id = change.getChangeId();
      lchangetrk = changeTrackMapper.selectByChangeId(id);
      iChangeTrackService.batchDelete(lchangetrk);
      //通过changeid到分配表中找到联系人id,将分配表信息删除
      lchangeasa = changeAssignMapper.selectByCgeId(id);
      if (lchangeasa != null) {
        iChangeAssignService.batchDelete(lchangeasa);
      }
      Contact querycontact;
      Contact resultcontact;
      List<Contact> ulcontact = new ArrayList<>();
      //循环查找出联系人id
      for (ChangeAssign changeass : lchangeasa) {
        querycontact = new Contact();
        querycontact.setContactId(changeass.getContactId());
        resultcontact = iContactService.selectByPrimaryKey(requestCtx, querycontact);
        resultcontact.set__status("update");
        resultcontact.setStatus("new");
        ulcontact.add(resultcontact);
      }
      //回写联系人的表
      iContactService.batchUpdate(requestCtx, ulcontact);
    }
    return new ResponseData();
  }
}