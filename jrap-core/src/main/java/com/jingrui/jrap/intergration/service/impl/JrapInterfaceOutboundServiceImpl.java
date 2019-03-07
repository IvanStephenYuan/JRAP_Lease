package com.jingrui.jrap.intergration.service.impl;

import com.github.pagehelper.PageHelper;
import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.intergration.dto.JrapInterfaceOutbound;
import com.jingrui.jrap.intergration.mapper.JrapInterfaceOutboundMapper;
import com.jingrui.jrap.intergration.service.IJrapInterfaceOutboundService;
import com.jingrui.jrap.system.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class JrapInterfaceOutboundServiceImpl extends BaseServiceImpl<JrapInterfaceOutbound>
        implements IJrapInterfaceOutboundService {

    @Autowired
    private JrapInterfaceOutboundMapper outboundMapper;

    @Override
    public List<JrapInterfaceOutbound> select(IRequest request, JrapInterfaceOutbound condition, int pageNum,
            int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return outboundMapper.select(condition);
    }

    @Override
    public int outboundInvoke(JrapInterfaceOutbound outbound) {
        return outboundMapper.insertSelective(outbound);
    }
}