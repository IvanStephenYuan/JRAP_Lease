/*
 * *
 *  @file com.maddyhome.idea.copyright.pattern.JavaCopyrightVariablesProvider$1@729b553f$
 *  @CopyRight (C) 2018 ZheJiangJingRui Co. Ltd.
 *  @brief JingRui Application Platform
 *  @author $name$
 *  @email yulong.yuan@jr-info.cn
 *  @date $date$
 * /
 */

package com.jingrui.jrap.product.service.impl;

import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.core.impl.RequestHelper;
import com.jingrui.jrap.core.impl.ServiceRequest;
import com.jingrui.jrap.product.dto.DocumentType;
import com.jingrui.jrap.product.mapper.DocumentTypeMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.print.Doc;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.junit.Assert.*;
import static org.mockito.Mockito.times;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/spring/applicationContext.xml"})
@Transactional
@Rollback
public class DocumentTypeServiceImplTest {
    @Mock
    private DocumentTypeMapper documentTypeMapper;

    @InjectMocks
    private DocumentTypeServiceImpl documentTypeService;

    IRequest request;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        request= new ServiceRequest();
        request.setLocale("zh_CN");
        RequestHelper.setCurrentRequest(request);
    }

    @Test
    public void getDocumentCodeRule() {
        DocumentType documentType = new DocumentType();
        documentType.setCodingRule("CUSTOMER");

        DocumentType documentTypePara = new DocumentType();
        documentTypePara.setDocumentCategory("CUSTOMER");
        documentTypePara.setDocumentType("CUSTOMER");

        when(documentTypeMapper.selectByDocumentType(null,null)).thenReturn(documentType);

        String codeRule = documentTypeService.getDocumentCodeRule("CUSTOMER", "CUSTOMER");
        assertEquals(codeRule, "CUSTOMER");
        verify(documentTypeMapper, times(1)).selectByDocumentType(null,null);
    }
}