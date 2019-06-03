package com.jingrui.jrap.fnd.service;

import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.core.ProxySelf;
import com.jingrui.jrap.system.service.IBaseService;
import com.jingrui.jrap.fnd.dto.Notice;

import java.util.List;

public interface INoticeService extends IBaseService<Notice>, ProxySelf<INoticeService>{

    List<Notice> limitQuery(IRequest request,Notice dto,int page,int pageSize);

}