package com.jingrui.jrap.activiti.interceptors;

import org.activiti.engine.impl.cmd.CompleteTaskCmd;
import org.activiti.engine.impl.interceptor.AbstractCommandInterceptor;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandConfig;

/**
 * @author shengyang.zhou@jingrui.com
 */
public class MyCommandInterceptor extends AbstractCommandInterceptor {
    @Override
    public <T> T execute(CommandConfig config, Command<T> command) {
        if (command instanceof CompleteTaskCmd) {
            //CompleteTaskCmd completeTaskCmd = (CompleteTaskCmd) command;
            System.out.println(command);
        }
        return next.execute(config, command);
    }

}
