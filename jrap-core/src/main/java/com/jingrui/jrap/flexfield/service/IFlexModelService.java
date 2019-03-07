package com.jingrui.jrap.flexfield.service;

import com.jingrui.jrap.core.ProxySelf;
import com.jingrui.jrap.system.service.IBaseService;
import com.jingrui.jrap.flexfield.dto.FlexModel;

import java.util.List;

public interface IFlexModelService extends IBaseService<FlexModel>, ProxySelf<IFlexModelService> {

    /**删除弹性域模型
     * @param models 需要删除的FlexModel
     */
    void deleteFlexModel(List<FlexModel> models);

}