package com.jingrui.jrap.product.service;

import com.jingrui.jrap.core.ProxySelf;
import com.jingrui.jrap.system.service.IBaseService;
import com.jingrui.jrap.product.dto.DocumentType;

public interface IDocumentTypeService extends IBaseService<DocumentType>, ProxySelf<IDocumentTypeService>{
    /**
     * 获取编码规则
     * @param documentCategory
     * @param documentType
     * @return
     */
    public String getDocumentCodeRule(String documentCategory, String documentType);
}