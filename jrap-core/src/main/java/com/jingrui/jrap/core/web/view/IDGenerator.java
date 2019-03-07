package com.jingrui.jrap.core.web.view;

import java.util.UUID;

/**
 * ID生成器.
 * 
 * @author njq.niu@jingrui.com
 *
 */
public class IDGenerator {

    // Singleton
    private static final IDGenerator instance = new IDGenerator();

    public static IDGenerator getInstance() {
        return instance;
    }

    public String generate() {
        String uuid = UUID.randomUUID().toString().replace("-", "").toUpperCase();
        return uuid;
    }

}
