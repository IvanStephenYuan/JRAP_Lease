package com.jingrui.jrap.security.service;

/**
 * @author shengyang.zhou@jingrui.com
 */
public interface IAESClientService {

    /**
     * 加密.
     *
     * @param password 密码
     * @return 加密后密码
     */
    String encrypt(String password);

    /**
     * 解密.
     *
     * @param password 密码
     * @return 解密后密码
     */
    String decrypt(String password);
}
