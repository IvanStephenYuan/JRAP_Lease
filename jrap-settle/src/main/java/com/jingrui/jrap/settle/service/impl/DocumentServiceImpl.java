package com.jingrui.jrap.settle.service.impl;

import com.jingrui.jrap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.jingrui.jrap.settle.dto.Document;
import com.jingrui.jrap.settle.service.IDocumentService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class DocumentServiceImpl extends BaseServiceImpl<Document> implements IDocumentService{

}