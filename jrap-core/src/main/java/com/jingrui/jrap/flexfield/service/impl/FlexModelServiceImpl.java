package com.jingrui.jrap.flexfield.service.impl;

import com.jingrui.jrap.flexfield.dto.FlexModel;
import com.jingrui.jrap.flexfield.dto.FlexModelColumn;
import com.jingrui.jrap.flexfield.dto.FlexRuleSet;
import com.jingrui.jrap.flexfield.mapper.FlexModelColumnMapper;
import com.jingrui.jrap.flexfield.mapper.FlexModelMapper;
import com.jingrui.jrap.flexfield.mapper.FlexRuleSetMapper;
import com.jingrui.jrap.flexfield.service.IFlexModelService;
import com.jingrui.jrap.flexfield.service.IFlexRuleSetService;
import com.jingrui.jrap.system.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class FlexModelServiceImpl extends BaseServiceImpl<FlexModel> implements IFlexModelService {

    @Autowired
    private FlexModelColumnMapper modelColumnMapper;

    @Autowired
    private FlexModelMapper modelMapper;


    @Autowired
    private IFlexRuleSetService setService;

    @Autowired
    private FlexRuleSetMapper flexRuleSetMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteFlexModel(List<FlexModel> models) {
        for (FlexModel flexModel : models) {
            int updateCount = modelMapper.deleteByPrimaryKey(flexModel);
            checkOvn(updateCount,flexModel);
            modelColumnMapper.delete(new FlexModelColumn(flexModel.getModelId()));
        }

        models.stream().forEach(v -> {
            FlexRuleSet flexModelSet = new FlexRuleSet();
            flexModelSet.setModelId(v.getModelId());
            setService.deleteRuleSet(flexRuleSetMapper.select(flexModelSet));
        });

    }
}