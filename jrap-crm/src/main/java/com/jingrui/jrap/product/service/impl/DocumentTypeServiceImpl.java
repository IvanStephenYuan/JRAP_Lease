package com.jingrui.jrap.product.service.impl;

import com.jingrui.jrap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.jingrui.jrap.product.dto.DocumentType;
import com.jingrui.jrap.product.service.IDocumentTypeService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class DocumentTypeServiceImpl extends BaseServiceImpl<DocumentType> implements IDocumentTypeService{

}