/*
 * #{copyright}#
 */

package com.jingrui.jrap.message.impl;

import com.jingrui.jrap.message.IMessageConsumer;

/**
 * 用于测试.
 * <p>
 * 直接打印收到的消息
 * 
 * @author shengyang.zhou@jingrui.com
 */
public class SimpleMessageConsumer implements IMessageConsumer<String> {
    @Override
    public void onMessage(String message, String pattern) {

    }
}
