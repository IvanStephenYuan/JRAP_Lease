/*
 * *
 *  @file com.maddyhome.idea.copyright.pattern.JavaCopyrightVariablesProvider$1@798011c3$
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
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@ContextConfiguration(locations = {"classpath:/spring/applicationContext.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@Rollback
@Transactional
public class DocumentTypeMapperTest {

    @Autowired
    DocumentTypeMapper documentTypeMapper;

    IRequest request;

    DocumentType documentType;

    @Before
    public void setUp() {
        request = new ServiceRequest();
        request.setLocale("zh_CN");
        RequestHelper.setCurrentRequest(request);

        documentType = new DocumentType();
        documentType.setDocumentCategory("CUSTOMER");
        documentType.setDocumentType("CUSTOMER1");
        documentType.setEnabledFlag("Y");
        documentType.setCodingRule("CUSTOMER");

        documentType.setObjectVersionNumber((Long) 1L);
    }

    @Test
    public void testSelectByDocumentType() throws Exception {
        documentTypeMapper.insert(documentType);
        String documentCategory = "CUSTOMER";
        String documentType = "CUSTOMER1";

        DocumentType result = documentTypeMapper.selectByDocumentType(documentCategory, documentType);
        assertNotNull(result);
        assertEquals("CUSTOMER", result.getCodingRule());
    }

    @After
    public void tearDown() {

    }
}
