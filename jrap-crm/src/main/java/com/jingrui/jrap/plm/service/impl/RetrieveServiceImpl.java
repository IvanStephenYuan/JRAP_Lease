package com.jingrui.jrap.plm.service.impl;

import com.jingrui.jrap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.jingrui.jrap.plm.dto.Retrieve;
import com.jingrui.jrap.plm.service.IRetrieveService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class RetrieveServiceImpl extends BaseServiceImpl<Retrieve> implements IRetrieveService{

}