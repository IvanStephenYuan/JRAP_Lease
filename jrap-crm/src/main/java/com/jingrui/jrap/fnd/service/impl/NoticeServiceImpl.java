package com.jingrui.jrap.fnd.service.impl;

import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.fnd.mapper.NoticeMapper;
import com.jingrui.jrap.system.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jingrui.jrap.fnd.dto.Notice;
import com.jingrui.jrap.fnd.service.INoticeService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class NoticeServiceImpl extends BaseServiceImpl<Notice> implements INoticeService{

    @Autowired
    private NoticeMapper noticeMapper;

    @Override
    public List<Notice> limitQuery(IRequest request, Notice dto, int page, int pageSize) {

        return noticeMapper.limitQuery(request,dto);
    }
}