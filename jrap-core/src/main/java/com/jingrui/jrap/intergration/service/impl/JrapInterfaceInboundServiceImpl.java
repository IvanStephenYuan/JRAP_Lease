package com.jingrui.jrap.intergration.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.core.components.UserLoginInfoCollection;
import com.jingrui.jrap.intergration.beans.JrapInvokeInfo;
import com.jingrui.jrap.intergration.controllers.JrapInvokeRequestBodyAdvice;
import com.jingrui.jrap.intergration.dto.JrapInterfaceInbound;
import com.jingrui.jrap.intergration.mapper.JrapInterfaceInboundMapper;
import com.jingrui.jrap.intergration.service.IJrapInterfaceInboundService;
import com.jingrui.jrap.intergration.util.JrapInvokeLogUtils;
import com.jingrui.jrap.system.service.impl.BaseServiceImpl;

@Service
@Transactional
public class JrapInterfaceInboundServiceImpl extends BaseServiceImpl<JrapInterfaceInbound>
        implements IJrapInterfaceInboundService {

    @Autowired
    private JrapInterfaceInboundMapper inboundMapper;


    @Override
    @Deprecated
    public int inboundInvoke(HttpServletRequest request, JrapInterfaceInbound inbound, Throwable throwable) {

        // 处理一些请求信息
        String ipAddress = UserLoginInfoCollection.getIpAddress(request);
        inbound.setIp(ipAddress);
        if (inbound.getRequestMethod() == null)
            inbound.setRequestMethod(request.getMethod());
        if (inbound.getInterfaceUrl() == null)
            inbound.setInterfaceUrl(request.getServletPath());
        if (inbound.getRequestHeaderParameter() == null)
            inbound.setRequestHeaderParameter(request.getQueryString());
        if (inbound.getRequestBodyParameter() == null)
            inbound.setRequestBodyParameter(JrapInvokeRequestBodyAdvice.getAndRemoveBody());
        inbound.setReferer(StringUtils.abbreviate(request.getHeader("Referer"), 240));
        if (throwable != null) {
            // 获取异常堆栈
            inbound.setStackTrace(JrapInvokeLogUtils.getRootCauseStackTrace(throwable));
            inbound.setRequestStatus(JrapInvokeInfo.REQUEST_FAILURE);
        }
        return inboundInvoke(inbound);

    }

    @Override
    public int inboundInvoke(JrapInterfaceInbound inbound) {
        inbound.setReferer(StringUtils.abbreviate(inbound.getReferer(),240));
        return inboundMapper.insertSelective(inbound);
    }

    @Override
    public List<JrapInterfaceInbound> select(IRequest request, JrapInterfaceInbound condition, int pageNum,
            int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return inboundMapper.select(condition);
    }
}