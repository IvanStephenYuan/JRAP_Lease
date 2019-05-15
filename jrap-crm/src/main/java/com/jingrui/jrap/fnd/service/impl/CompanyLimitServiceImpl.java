/*
 * *
 *  @file com.maddyhome.idea.copyright.pattern.JavaCopyrightVariablesProvider$1@504cf8e2$
 *  @CopyRight (C) 2018 ZheJiangJingRui Co. Ltd.
 *  @brief JingRui Application Platform
 *  @author $name$
 *  @email yulong.yuan@jr-info.cn
 *  @date $date$
 * /
 */

package com.jingrui.jrap.fnd.service.impl;

import com.jingrui.jrap.activiti.service.IActivitiService;
import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.fnd.dto.Company;
import com.jingrui.jrap.fnd.dto.CompanyLimit;
import com.jingrui.jrap.fnd.dto.LimitChange;
import com.jingrui.jrap.fnd.mapper.CompanyLimitMapper;
import com.jingrui.jrap.fnd.mapper.CompanyMapper;
import com.jingrui.jrap.fnd.mapper.LimitChangeMapper;
import com.jingrui.jrap.fnd.service.ICompanyLimitService;
import com.jingrui.jrap.system.dto.ResponseData;
import com.jingrui.jrap.system.service.impl.BaseServiceImpl;
import org.activiti.engine.impl.util.CollectionUtil;
import org.activiti.rest.service.api.runtime.process.ProcessInstanceCreateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;


@Service
@Transactional(rollbackFor = Exception.class)
public class CompanyLimitServiceImpl extends BaseServiceImpl<CompanyLimit> implements ICompanyLimitService {
    @Autowired
    private IActivitiService activitiService;  //工作流相关Service
    @Autowired
    private CompanyMapper companyMapper;
    @Autowired
    private CompanyLimitMapper companyLimitMapper;
    @Autowired
    private LimitChangeMapper limitChangeMapper;

    private final String copanyInnetApplyKey = "COPANY_INNET_APPLY";//商户入网流程标示

    private final String limitChangeApplyKey = "LIMIT_CHANGE_APPLY";//额度调整流程标示

    /**
     * 创建商户入网工作流
     *
     * @param iRequest
     */
    @Override
    public void createVacationInstance(IRequest iRequest, String companyId) {
        // 工作流启动
        if (companyId != null && !"".equals(companyId)) {
            ProcessInstanceCreateRequest instanceCreateRequest = new ProcessInstanceCreateRequest();
            instanceCreateRequest.setBusinessKey(companyId);
            instanceCreateRequest.setProcessDefinitionKey(copanyInnetApplyKey);
            activitiService.startProcess(iRequest, instanceCreateRequest);
        }
    }

    /**
     * 商户信息变更
     *
     * @param iRequest
     * @param dto
     */
    @Override
    public void modify(IRequest iRequest, CompanyLimit dto) {
        // 商户基本信息变更
        Company company = new Company();
        company.setCompanyId(dto.getCompanyId());
        company.setCompanyCode(dto.getCompanyCode());
        company.setCompanyType(dto.getCompanyType());
        company.setAddress(dto.getAddress());
        company.setCompanyLevelId(dto.getCompanyLevelId());
        company.setParentCompanyId(dto.getParentCompanyId());
        company.setChiefPositionId(dto.getChiefPositionId());
        company.setStartDateActive(dto.getStartDateActive());
        company.setEndDateActive(dto.getEndDateActive());
        company.setCompanyShortName(dto.getCompanyShortName());
        company.setCompanyFullName(dto.getCompanyFullName());
        company.setZipcode(dto.getZipcode());
        company.setFax(dto.getFax());
        company.setPhone(dto.getPhone());
        company.setContactPerson(dto.getContactPerson());
        company.setParentCompanyName(dto.getParentCompanyName());
        company.setPositionName(dto.getPositionName());
        company.setStatus(dto.getCompanyClass());
        company.setCompanyClass(dto.getCompanyClass());
        companyMapper.updateByPrimaryKeySelective(company);
        // 额度信息变更
        List<CompanyLimit> companyLimits = dto.getCompanyLimits();
        if (CollectionUtil.isNotEmpty(companyLimits)) {
            for (CompanyLimit companyLimit : companyLimits) {
                companyLimitMapper.updateByPrimaryKeySelective(companyLimit);
            }
        }
    }

    /**
     * 额度调整
     * 记录额度变更
     * 启动工作流
     * @param iRequest
     * @param dto
     */
    @Override
    public ResponseData adjust(IRequest iRequest, CompanyLimit dto) {
        ResponseData responseData = new ResponseData(false);
        CompanyLimit old = companyLimitMapper.selectByPrimaryKey(dto.getLimitId());
        // 调减额度，暂时减掉余额
        if (dto.getLimitAmount()<0) {
            if ((old.getBalance()+dto.getLimitAmount())<0) {
                responseData.setMessage("额度调减值不能大于当前余额：" + old.getBalance());
                return responseData;
            }else {
                old.setBalance(old.getBalance()+dto.getLimitAmount());
                companyLimitMapper.updateByPrimaryKeySelective(old);
            }
        }
        LimitChange limitChange = new LimitChange();
        limitChange.setLimitType(old.getLimitType());
        limitChange.setCompanyId(old.getCompanyId());
        limitChange.setLimitCompanyId(old.getLimitCompanyId());
        limitChange.setStatus("SUBMIT");
        limitChange.setGoodId(old.getGoodId());
        limitChange.setOrderId(old.getOrderId());
        limitChange.setLimitAmount(dto.getLimitAmount());
        limitChange.setSubmitDate(new Date());
        //limitChange.setApprovedDate();
        limitChangeMapper.insertSelective(limitChange);

        // 启动额度调整工作流
        ProcessInstanceCreateRequest instanceCreateRequest = new ProcessInstanceCreateRequest();
        instanceCreateRequest.setBusinessKey(dto.getCompanyId()+"");
        instanceCreateRequest.setProcessDefinitionKey(limitChangeApplyKey);
        activitiService.startProcess(iRequest, instanceCreateRequest);
        responseData.setSuccess(true);
        return responseData;
    }

    /**
     * 添加资方
     * 记录额度变更
     * 启动工作流
     *
     * @param iRequest
     * @param dto
     */
    @Override
    public void addCap(IRequest iRequest, CompanyLimit dto) {
        // 插入资方
        dto.setBalance(dto.getLimitAmount());
        dto.setLimitDate(new Date());
        dto.setEnabledFlag("N"); //审批通过后再设置为Y
        companyLimitMapper.insertSelective(dto);
        // 添加change
        LimitChange limitChange = new LimitChange();
        limitChange.setLimitType(dto.getLimitType());
        limitChange.setCompanyId(dto.getCompanyId());
        limitChange.setLimitCompanyId(dto.getLimitCompanyId());
        limitChange.setStatus("SUBMIT");// 审批通过后改为生效，拒绝后改为新建
        limitChange.setGoodId(dto.getGoodId());
        limitChange.setOrderId(dto.getOrderId());
        limitChange.setLimitAmount(dto.getLimitAmount());
        limitChange.setSubmitDate(new Date());
        //limitChange.setApprovedDate();
        limitChangeMapper.insertSelective(limitChange);
        // 启动额度调整工作流
        ProcessInstanceCreateRequest instanceCreateRequest = new ProcessInstanceCreateRequest();
        instanceCreateRequest.setBusinessKey(dto.getCompanyId()+"");
        instanceCreateRequest.setProcessDefinitionKey(limitChangeApplyKey);
        activitiService.startProcess(iRequest, instanceCreateRequest);
    }

    /**
     * 额度变更申请-工作流
     * @param iRequest
     * @param dto
     */
    @Override
    public ResponseData limitChangeApply(IRequest iRequest, List<CompanyLimit> dto) {
        ResponseData responseData = new ResponseData(true);
        if(CollectionUtil.isNotEmpty(dto)){
            Long companyId = dto.get(0).getCompanyId();
            for (CompanyLimit limit : dto) {
                // 查询
                CompanyLimit companyLimit = new CompanyLimit();
                companyLimit.setCompanyId(limit.getCompanyId());
                companyLimit.setLimitCompanyId(limit.getLimitCompanyId());
                List<CompanyLimit>  companyLimits = companyLimitMapper.select(companyLimit);
                // 调整额度
                if(CollectionUtil.isNotEmpty(companyLimits)){
                    CompanyLimit old =  companyLimits.get(0);
                    // 调减额度，暂时减掉余额
                    if (limit.getLimitAmount()<0) {
                        if ((old.getBalance()+limit.getLimitAmount())<0) {
                            responseData.setSuccess(false);
                            responseData.setMessage("额度调减值不能大于当前余额：" + old.getBalance());
                            return responseData;
                        }else {
                            old.setBalance(old.getBalance()+limit.getLimitAmount());
                            companyLimitMapper.updateByPrimaryKeySelective(old);
                        }
                    }
                // 添加资方
                }else {
                    limit.setLimitType("COMPANY");
                    limit.setBalance(0L);
                    limit.setEnabledFlag("N");
                    companyLimitMapper.insertSelective(limit);
                }
                // 插入change
                LimitChange limitChange = new LimitChange();
                limitChange.setLimitType("COMPANY");
                limitChange.setCompanyId(limit.getCompanyId());
                limitChange.setLimitCompanyId(limit.getLimitCompanyId());
                limitChange.setStatus("SUBMIT");// 审批通过后改为生效，拒绝后改为新建
                limitChange.setGoodId(limit.getGoodId());
                limitChange.setOrderId(limit.getOrderId());
                limitChange.setLimitAmount(limit.getLimitAmount());
                limitChange.setSubmitDate(new Date());
                limitChangeMapper.insertSelective(limitChange);
            }
            // 启动工作流
            ProcessInstanceCreateRequest instanceCreateRequest = new ProcessInstanceCreateRequest();
            instanceCreateRequest.setBusinessKey(companyId+"");
            instanceCreateRequest.setProcessDefinitionKey(limitChangeApplyKey);
            activitiService.startProcess(iRequest, instanceCreateRequest);
        }else {
            responseData.setSuccess(false);
            responseData.setMessage("提交数据为空！");
        }
        return responseData;
    }
}