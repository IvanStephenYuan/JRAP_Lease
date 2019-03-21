package com.jingrui.jrap.order.service.impl;

import com.jingrui.jrap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.jingrui.jrap.order.dto.Change;
import com.jingrui.jrap.order.service.IChangeService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class ChangeServiceImpl extends BaseServiceImpl<Change> implements IChangeService{

}