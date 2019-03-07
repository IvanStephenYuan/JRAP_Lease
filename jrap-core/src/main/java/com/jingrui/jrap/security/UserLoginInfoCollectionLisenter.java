package com.jingrui.jrap.security;

import com.jingrui.jrap.message.IMessageConsumer;
import com.jingrui.jrap.message.QueueMonitor;
import com.jingrui.jrap.system.dto.UserLogin;
import com.jingrui.jrap.system.mapper.UserLoginMapper;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author jialong.zuo@jingrui.com
 * @date 2017/12/15.
 */
@QueueMonitor(queue = "jrap:queue:loginInfo")
public class UserLoginInfoCollectionLisenter implements IMessageConsumer<UserLogin> {

    @Autowired
    UserLoginMapper userLoginMapper;

    @Override
    public void onMessage(UserLogin userLogin, String pattern) {
        userLoginMapper.insertSelective(userLogin);

    }
}
