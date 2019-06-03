package com.jingrui.jrap.settle.service.impl;

import com.jingrui.jrap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.jingrui.jrap.settle.dto.Operate;
import com.jingrui.jrap.settle.service.IOperateService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class OperateServiceImpl extends BaseServiceImpl<Operate> implements IOperateService{

}