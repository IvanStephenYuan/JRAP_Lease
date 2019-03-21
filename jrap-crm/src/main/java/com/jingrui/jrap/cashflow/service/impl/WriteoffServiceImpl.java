package com.jingrui.jrap.cashflow.service.impl;

import com.jingrui.jrap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.jingrui.jrap.cashflow.dto.Writeoff;
import com.jingrui.jrap.cashflow.service.IWriteoffService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class WriteoffServiceImpl extends BaseServiceImpl<Writeoff> implements IWriteoffService{

}