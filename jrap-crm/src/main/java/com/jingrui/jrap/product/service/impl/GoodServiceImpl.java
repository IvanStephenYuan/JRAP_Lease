package com.jingrui.jrap.product.service.impl;

import com.jingrui.jrap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.jingrui.jrap.product.dto.Good;
import com.jingrui.jrap.product.service.IGoodService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class GoodServiceImpl extends BaseServiceImpl<Good> implements IGoodService{

}