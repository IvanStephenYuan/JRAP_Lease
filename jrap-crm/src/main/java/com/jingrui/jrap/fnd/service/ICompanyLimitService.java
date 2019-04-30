/*
 * *
 *  @file com.maddyhome.idea.copyright.pattern.JavaCopyrightVariablesProvider$1@28a8f185$
 *  @CopyRight (C) 2018 ZheJiangJingRui Co. Ltd.
 *  @brief JingRui Application Platform
 *  @author $name$
 *  @email yulong.yuan@jr-info.cn
 *  @date $date$
 * /
 */

package com.jingrui.jrap.fnd.service;

import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.core.ProxySelf;
import com.jingrui.jrap.fnd.dto.CompanyLimit;
import com.jingrui.jrap.system.service.IBaseService;

import java.util.List;


public interface ICompanyLimitService extends IBaseService<CompanyLimit>, ProxySelf<ICompanyLimitService> {

    void createVacationInstance(IRequest iRequest, String companyId);

    void modify(IRequest iRequest, CompanyLimit dto);

    void adjust(IRequest iRequest, List<CompanyLimit> dto);
}