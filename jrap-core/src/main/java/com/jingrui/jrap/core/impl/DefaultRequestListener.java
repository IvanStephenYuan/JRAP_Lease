/*
 * #{copyright}#
 */

package com.jingrui.jrap.core.impl;

import javax.servlet.http.HttpServletRequest;

import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.core.IRequestListener;

/**
 * @author shengyang.zhou@jingrui.com
 */
public class DefaultRequestListener implements IRequestListener {
    
    @Override
    public IRequest newInstance() {
        return new ServiceRequest();
    }

    @Override
    public void afterInitialize(HttpServletRequest httpServletRequest, IRequest request) {

    }
}
