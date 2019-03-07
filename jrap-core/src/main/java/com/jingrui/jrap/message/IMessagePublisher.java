/*
 * #{copyright}#
 */

package com.jingrui.jrap.message;

/**
 * @author shengyang.zhou@jingrui.com
 * @author njq.niu@jingrui.com
 */
public interface IMessagePublisher {

    /**
     * publish message to a channel.
     * 
     * @param channel
     *            channel
     * @param message
     *            message :String ,Number, Map, Object...
     */
    void publish(String channel, Object message);

    /**
     * add message to a queue .
     * 
     * @param list
     *            destination
     * @param message
     *            message :String ,Number, Map, Object...
     *
     * @deprecated  use {@link #message(String,Object)
     */
    void rPush(String list, Object message);

    /**
     * add message to a queue .
     * @param name queue name.
     * @param message message obj.
     */
    void message(String name, Object message);
}
