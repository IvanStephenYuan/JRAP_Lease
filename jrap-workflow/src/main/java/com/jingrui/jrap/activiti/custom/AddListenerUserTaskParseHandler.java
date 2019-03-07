package com.jingrui.jrap.activiti.custom;

import org.activiti.bpmn.model.ActivitiListener;
import org.activiti.bpmn.model.ImplementationType;
import org.activiti.bpmn.model.UserTask;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.impl.bpmn.parser.BpmnParse;
import org.activiti.engine.impl.bpmn.parser.handler.UserTaskParseHandler;

public class AddListenerUserTaskParseHandler extends UserTaskParseHandler {

    private final String eventName;
    private final TaskListener taskListener;

    public AddListenerUserTaskParseHandler(String eventName, TaskListener taskListener) {
        this.eventName = eventName;
        this.taskListener = taskListener;
    }

    @Override
    protected void executeParse(BpmnParse bpmnParse, UserTask userTask) {
        super.executeParse(bpmnParse, userTask);

        ActivitiListener listener = new ActivitiListener();
        listener.setEvent(eventName);
        listener.setImplementationType(ImplementationType.IMPLEMENTATION_TYPE_INSTANCE);
        listener.setInstance(taskListener);
        userTask.getTaskListeners().add(listener);

    }
}