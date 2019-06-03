package com.jingrui.jrap.fnd.mapper;

import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.mybatis.common.Mapper;
import com.jingrui.jrap.fnd.dto.Notice;

import java.util.List;

public interface NoticeMapper extends Mapper<Notice>{

    List<Notice> limitQuery(IRequest request, Notice dto);
}