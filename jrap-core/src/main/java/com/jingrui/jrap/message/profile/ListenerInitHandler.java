/*
 * #{copyright}#
 */

package com.jingrui.jrap.message.profile;

/**
 * @author shengyang.zhou@jingrui.com
 */
public interface ListenerInitHandler {
    /**
     * 为 GlobalProfileListener 初始化加载 所关心的profile.
     * 
     * @param listener
     *            需要初始化的listener
     */
    void initLoad(GlobalProfileListener listener);
}