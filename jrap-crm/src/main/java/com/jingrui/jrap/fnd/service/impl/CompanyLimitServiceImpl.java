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
import com.jingrui.jrap.fnd.mapper.CompanyLimitMapper;
import com.jingrui.jrap.fnd.mapper.CompanyMapper;
import com.jingrui.jrap.fnd.service.ICompanyLimitService;
import com.jingrui.jrap.system.service.impl.BaseServiceImpl;
import org.activiti.engine.impl.util.CollectionUtil;
import org.activiti.rest.service.api.runtime.process.ProcessInstanceCreateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    private final String processDefinitionKey = "COPANY_INNET_APPLY";//商户入网流程标示

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
            instanceCreateRequest.setProcessDefinitionKey(processDefinitionKey);
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
        if(CollectionUtil.isNotEmpty(companyLimits)){
            for (CompanyLimit companyLimit : companyLimits) {
                companyLimitMapper.updateByPrimaryKeySelective(companyLimit);
            }
        }
    }

    /**
     * 额度调整
     *
     * @param iRequest
     * @param dto
     */
    @Override
    public void adjust(IRequest iRequest, List<CompanyLimit> dto) {
        if(CollectionUtil.isNotEmpty(dto)){
            for (CompanyLimit companyLimit : dto) {
                // 原数据
                CompanyLimit old = companyLimitMapper.selectByPrimaryKey(companyLimit.getLimitId());
                // 额度增加
                if(old.getLimitAmount()<companyLimit.getLimitAmount()){
                    old.setBalance(old.getBalance()+companyLimit.getLimitAmount()-old.getLimitAmount());
                    old.setLimitAmount(companyLimit.getLimitAmount());
                    companyLimitMapper.updateByPrimaryKeySelective(old);
                }
                // 额度减少
                if(old.getLimitAmount()>companyLimit.getLimitAmount()){
                    // 减少的额度值不能大于当前余额
                    if(old.getBalance()>=(old.getLimitAmount()-companyLimit.getLimitAmount())){
                        //old.setBalance(old.getBalance()+companyLimit.getLimitAmount()-old.getLimitAmount());
                        old.setLimitAmount(companyLimit.getLimitAmount());
                        companyLimitMapper.updateByPrimaryKeySelective(old);
                    }
                }
            }
        }
    }
}