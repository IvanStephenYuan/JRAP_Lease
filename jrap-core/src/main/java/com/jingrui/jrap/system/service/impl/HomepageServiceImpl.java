/*
 * *
 *  @file com.maddyhome.idea.copyright.pattern.JavaCopyrightVariablesProvider$1@31a28f3a$
 *  @CopyRight (C) 2018 ZheJiangJingRui Co. Ltd.
 *  @brief JingRui Application Platform
 *  @author $name$
 *  @email yulong.yuan@jr-info.cn
 *  @date $date$
 * /
 */

package com.jingrui.jrap.system.service.impl;

import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.system.dto.Homepage;
import com.jingrui.jrap.system.mapper.HomepageMapper;
import com.jingrui.jrap.system.service.IHomepageService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class HomepageServiceImpl extends BaseServiceImpl<Homepage> implements IHomepageService {

    @Autowired
    private HomepageMapper homepageMapper;

    @Override
    public Homepage queryPath(IRequest requestContext) {

        Long userId = requestContext.getUserId();
        Long roleId = requestContext.getRoleId();
        Long companyId = requestContext.getCompanyId();

        Homepage condition = new Homepage();
        condition.setUserId(userId);
        condition.setEnabledFlag("Y");
        List<Homepage> list1 = homepageMapper.select(condition);

        condition.setUserId(null);
        condition.setRoleId(roleId);
        List<Homepage> list2 = homepageMapper.select(condition);

        condition.setUserId(null);
        condition.setRoleId(null);
        condition.setCompanyId(companyId);
        List<Homepage> list3 = homepageMapper.select(condition);

        if (CollectionUtils.isNotEmpty(list1)) {
            return  list1.get(0);
        }
        if(CollectionUtils.isNotEmpty(list2)){
            return  list2.get(0);
        }
        if(CollectionUtils.isNotEmpty(list3)){
            return  list3.get(0);
        }
        return new Homepage();
    }
}