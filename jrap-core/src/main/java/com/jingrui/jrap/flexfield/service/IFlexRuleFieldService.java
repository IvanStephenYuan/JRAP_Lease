package com.jingrui.jrap.flexfield.service;

import com.jingrui.jrap.core.ProxySelf;
import com.jingrui.jrap.system.service.IBaseService;
import com.jingrui.jrap.flexfield.dto.FlexRuleField;

import java.util.List;

public interface IFlexRuleFieldService extends IBaseService<FlexRuleField>, ProxySelf<IFlexRuleFieldService> {

    /**查询弹性域规则Field
     * @param flexRuleField 查询参数
     * @return 相应的FlexRuleField
     */
    List<FlexRuleField> queryFlexRuleField(FlexRuleField flexRuleField);

}