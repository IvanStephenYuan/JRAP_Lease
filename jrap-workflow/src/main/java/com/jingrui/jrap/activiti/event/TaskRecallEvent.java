package com.jingrui.jrap.activiti.event;

import com.jingrui.jrap.core.IRequest;
import org.springframework.context.ApplicationEvent;

/**
 * @author xiangyu.qi@jingrui.com on 2017/11/7.
 */
public class TaskRecallEvent extends ApplicationEvent {
    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    private IRequest iRequest;

    public TaskRecallEvent(Object source) {
        super(source);
    }

    public IRequest getiRequest() {
        return iRequest;
    }

    public void setiRequest(IRequest iRequest) {
        this.iRequest = iRequest;
    }
}
