package com.jingrui.jrap.settle.service.impl;

import com.jingrui.jrap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.jingrui.jrap.settle.dto.DocumentDetail;
import com.jingrui.jrap.settle.service.IDocumentDetailService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class DocumentDetailServiceImpl extends BaseServiceImpl<DocumentDetail> implements IDocumentDetailService{

}