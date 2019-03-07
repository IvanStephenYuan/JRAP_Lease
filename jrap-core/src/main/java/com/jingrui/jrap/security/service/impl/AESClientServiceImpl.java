package com.jingrui.jrap.security.service.impl;

import com.jingrui.jrap.security.service.IAESClientService;
import org.springframework.stereotype.Service;

/**
 * @author shengyang.zhou@jingrui.com
 */
@Service
public class AESClientServiceImpl implements IAESClientService {

    @Override
    public String encrypt(String password) {
        return password;
    }

    @Override
    public String decrypt(String password) {
        return password;
    }
}
