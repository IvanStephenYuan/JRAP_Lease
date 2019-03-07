package com.jingrui.jrap.security.permission.mapper;

import com.jingrui.jrap.mybatis.common.Mapper;
import com.jingrui.jrap.security.permission.dto.DataPermissionRule;

import java.util.List;

/**
 * @author jialong.zuo@jingrui.com on 2017/12/8
 */
public interface DataPermissionRuleMapper extends Mapper<DataPermissionRule> {

    /** 获取未被选择的规则
     * @param dataPermissionRule
     * @return
     */
    List<DataPermissionRule> selectRuleWithoutTableSelect(DataPermissionRule dataPermissionRule);

}