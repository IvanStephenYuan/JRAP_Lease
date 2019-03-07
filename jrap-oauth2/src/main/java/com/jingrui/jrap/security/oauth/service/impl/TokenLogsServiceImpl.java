package com.jingrui.jrap.security.oauth.service.impl;

import com.github.pagehelper.PageHelper;
import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.security.oauth.mapper.TokenLogsMapper;
import com.jingrui.jrap.system.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jingrui.jrap.security.oauth.dto.TokenLogs;
import com.jingrui.jrap.security.oauth.service.ITokenLogsService;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * oauth2日志服务 - 实现类.
 *
 * @author qixiangyu
 */
@Service
public class TokenLogsServiceImpl extends BaseServiceImpl<TokenLogs> implements ITokenLogsService {

    @Autowired
    private TokenLogsMapper tokenLogsMapper;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<TokenLogs> select(IRequest request, TokenLogs condition, int pageNum, int pageSize) {
        if (!"ADMIN".equalsIgnoreCase(request.getEmployeeCode())) {
            condition.setUserId(request.getUserId());
        }
        if ("valid".equalsIgnoreCase(condition.getTokenStatus())) {
            condition.setTokenExpiresTime(new Date());
            condition.setRevokeFlag("Y");
        } else if ("invalid".equalsIgnoreCase(condition.getTokenStatus())) {
            PageHelper.startPage(pageNum, pageSize);
            return processTokenStatus(tokenLogsMapper.selectInvalid(condition));
        }

        return processTokenStatus(super.select(request, condition, pageNum, pageSize));
    }

    @Override
    @Transactional
    public int revokeToken(String tokenValue) {
        return tokenLogsMapper.revokeToken(tokenValue);
    }

    private List<TokenLogs> processTokenStatus(List<TokenLogs> logs) {
        for (TokenLogs log : logs) {
            if ("N".equalsIgnoreCase(log.getRevokeFlag())) {
                log.setTokenStatus("invalid");
            } else if (log.getTokenExpiresTime().before(new Date())) {
                log.setTokenStatus("invalid");
            } else {
                log.setTokenStatus("valid");
            }
        }
        return logs;
    }
}