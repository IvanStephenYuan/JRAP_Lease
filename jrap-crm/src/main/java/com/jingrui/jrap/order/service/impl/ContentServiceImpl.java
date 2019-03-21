package com.jingrui.jrap.order.service.impl;

import com.jingrui.jrap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.jingrui.jrap.order.dto.Content;
import com.jingrui.jrap.order.service.IContentService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class ContentServiceImpl extends BaseServiceImpl<Content> implements IContentService{

}