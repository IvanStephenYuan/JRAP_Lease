package com.jingrui.jrap.plm.service.impl;

import com.jingrui.jrap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.jingrui.jrap.plm.dto.Dispose;
import com.jingrui.jrap.plm.service.IDisposeService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class DisposeServiceImpl extends BaseServiceImpl<Dispose> implements IDisposeService{

}