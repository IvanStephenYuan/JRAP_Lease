package com.jingrui.jrap.system.service.impl;

import com.jingrui.jrap.system.dto.ParameterConfig;
import com.jingrui.jrap.system.mapper.ParameterConfigMapper;
import com.jingrui.jrap.system.service.IParameterConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author qiang.zeng
 * @date 2017/11/6
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ParameterConfigServiceImpl extends BaseServiceImpl<ParameterConfig> implements IParameterConfigService {
    @Autowired
    private ParameterConfigMapper parameterConfigMapper;

    @Override
    public List<ParameterConfig> selectByReportCode(String reportCode) {
        return parameterConfigMapper.selectByReportCode(reportCode);
    }

    @Override
    public List<ParameterConfig> selectByCodeAndTargetId(String code, Long targetId) {
        return parameterConfigMapper.selectByCodeAndTargetId(code, targetId);
    }

    @Override
    protected boolean useSelectiveUpdate() {
        return false;
    }
}
