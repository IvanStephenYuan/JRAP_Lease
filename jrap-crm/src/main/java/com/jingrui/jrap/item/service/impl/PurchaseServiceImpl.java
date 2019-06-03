package com.jingrui.jrap.item.service.impl;

import com.jingrui.jrap.activiti.service.IActivitiService;
import com.jingrui.jrap.code.rule.exception.CodeRuleException;
import com.jingrui.jrap.code.rule.service.ISysCodeRuleProcessService;
import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.item.dto.PurchaseDetail;
import com.jingrui.jrap.item.mapper.PurchaseDetailMapper;
import com.jingrui.jrap.item.mapper.PurchaseMapper;
import com.jingrui.jrap.system.dto.ResponseData;
import com.jingrui.jrap.system.service.impl.BaseServiceImpl;
import org.activiti.engine.impl.util.CollectionUtil;
import org.activiti.rest.service.api.runtime.process.ProcessInstanceCreateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jingrui.jrap.item.dto.Purchase;
import com.jingrui.jrap.item.service.IPurchaseService;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class PurchaseServiceImpl extends BaseServiceImpl<Purchase> implements IPurchaseService{
    @Autowired
    private IActivitiService activitiService;  //工作流相关Service
    private final static String PURCHASE_WORKFLOW_FLAG = "PURCHASE_APPLY";//采购申请流程标示
    private final static String PURCHASE_RULE_CODE = "PURCHASE";
    @Autowired
    private ISysCodeRuleProcessService codeRuleProcessService;
    @Autowired
    private PurchaseMapper purchaseMapper;
    @Autowired
    private PurchaseDetailMapper purchaseDetailMapper;


    @Override
    public ResponseData purchaseApply(IRequest iRequest, Purchase dto) {
        ResponseData responseData = new ResponseData(false);
        // 插入采购信息
        String purchaseCode = dto.getPurchaseCode();
        if (purchaseCode == null||"".equals(purchaseCode)) {
            try {
                purchaseCode = codeRuleProcessService.getRuleCode(PURCHASE_RULE_CODE);
                dto.setPurchaseCode(purchaseCode);
            } catch (CodeRuleException e) {
                e.printStackTrace();
            }
        }
        dto.setStatus("SUBMIT");
        dto.setCompanyId(iRequest.getCompanyId());
        dto.setSubmitDate(new Date());
        List<PurchaseDetail>  purchaseDetails =  dto.getPurchaseDetails();
        if (CollectionUtil.isEmpty(purchaseDetails)){
            return  responseData;
        }
        Long totalAmount = 0L;
        Long TotalNumber = 0L;
        // 计算采购金额及采购数量
        for (PurchaseDetail purchaseDetail : purchaseDetails) {
            totalAmount+=purchaseDetail.getUnitPrice()*purchaseDetail.getItemNumber();
            TotalNumber+=purchaseDetail.getItemNumber();
        }
        dto.setTotalAmount(totalAmount);
        dto.setTotalNumber(TotalNumber);
        purchaseMapper.insertSelective(dto);
        // 插入采购明细
        for (PurchaseDetail detail : purchaseDetails) {
            detail.setPurchaseId(dto.getPurchaseId());
            purchaseDetailMapper.insertSelective(detail);
        }
        // 启动工作流
        ProcessInstanceCreateRequest instanceCreateRequest = new ProcessInstanceCreateRequest();
        instanceCreateRequest.setBusinessKey(dto.getPurchaseId()+"");
        instanceCreateRequest.setProcessDefinitionKey(PURCHASE_WORKFLOW_FLAG);
        activitiService.startProcess(iRequest, instanceCreateRequest);
        responseData.setSuccess(true);
        return responseData;
    }
}