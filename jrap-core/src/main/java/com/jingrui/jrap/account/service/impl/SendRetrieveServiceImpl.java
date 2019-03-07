package com.jingrui.jrap.account.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jingrui.jrap.account.constants.UserConstants;
import com.jingrui.jrap.account.dto.SendRetrieve;
import com.jingrui.jrap.account.exception.UserException;
import com.jingrui.jrap.account.mapper.SendRetrieveMapper;
import com.jingrui.jrap.account.service.ISendRetrieveService;
import com.jingrui.jrap.core.IRequest;


/**
 * 发送次数限制服务接口实现.
 *
 * @author shengyang.zhou@jingrui.com
 */
@Service
public class SendRetrieveServiceImpl implements ISendRetrieveService {

    @Autowired
    private SendRetrieveMapper sendRetrieveMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer insert(IRequest request, SendRetrieve sendRetrieve) throws UserException {
        // TODO Auto-generated method stub
        sendRetrieveMapper.insertRecord(sendRetrieve);
        Integer result = sendRetrieveMapper.query(sendRetrieve);
        if (result > UserConstants.NUMBER_2) {
            throw new UserException(UserConstants.SENT_LIMIT_ERROR, new Object[]{});
        }
        return result;
    }
}
