package com.jingrui.jrap.product.service.impl;

import com.jingrui.jrap.system.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jingrui.jrap.product.dto.DocumentType;
import com.jingrui.jrap.product.mapper.DocumentTypeMapper;
import com.jingrui.jrap.product.service.IDocumentTypeService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class DocumentTypeServiceImpl extends BaseServiceImpl<DocumentType> implements IDocumentTypeService{
    @Autowired
    private DocumentTypeMapper documentTypeMapper;

    @Override
    public String getDocumentCodeRule(String documentCategory, String documentType) {
        String codeRule = "ERROR";
        DocumentType documentTypeData = documentTypeMapper.selectByDocumentType(documentCategory, documentType);

        if(documentTypeData != null){
            return documentTypeData.getCodingRule();
        }
        return codeRule;
    }
}