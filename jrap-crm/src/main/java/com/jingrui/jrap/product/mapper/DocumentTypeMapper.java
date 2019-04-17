package com.jingrui.jrap.product.mapper;

import com.jingrui.jrap.mybatis.common.Mapper;
import com.jingrui.jrap.product.dto.DocumentType;
import org.apache.ibatis.annotations.Param;

public interface DocumentTypeMapper extends Mapper<DocumentType>{
    /**
     * 获取单条记录通过单据类型
     * @param documentType
     * @return
     */
    DocumentType selectByDocumentType(@Param("documentCategory") String documentCategory, @Param("documentType") String documentType);
}