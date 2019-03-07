/*
 * Copyright ZheJiang JingRui Co.,Ltd.
 */

package com.jingrui.jrap.activiti.custom;

import com.jingrui.jrap.activiti.approval.dto.ApproveStrategy;
import com.jingrui.jrap.activiti.approval.mapper.ApproveStrategyMapper;
import com.jingrui.jrap.activiti.core.IActivitiConstants;
import com.jingrui.jrap.activiti.listeners.AutoDelegateListener;
import com.jingrui.jrap.activiti.listeners.NotificationListener;
import com.jingrui.jrap.activiti.listeners.TaskCreateNotificationListener;
import com.jingrui.jrap.activiti.listeners.TaskEndNotificationListener;
import com.jingrui.jrap.activiti.util.ActivitiUtils;
import com.jingrui.jrap.core.BaseConstants;
import com.jingrui.jrap.mybatis.util.StringUtil;
import org.activiti.bpmn.model.*;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.delegate.TransactionDependentTaskListener;
import org.activiti.engine.impl.bpmn.parser.BpmnParse;
import org.activiti.engine.impl.bpmn.parser.factory.ActivityBehaviorFactory;
import org.activiti.engine.impl.bpmn.parser.handler.UserTaskParseHandler;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * @author shengyang.zhou@jingrui.com
 */
public class CustomUserTaskParseHandler extends UserTaskParseHandler implements IActivitiConstants {

    @Autowired
    private NotificationListener notificationListener;

    @Autowired
    private AutoDelegateListener autoDelegateListener;

    @Autowired
    private TaskCreateNotificationListener taskCreateNotificationListener;

    @Autowired
    private TaskEndNotificationListener taskEndNotificationListener;

    @Autowired
    private ActivityBehaviorFactory activityBehaviorFactory;

    @Autowired
    private ApproveStrategyMapper strategyMapper;

    @Override
    protected void executeParse(BpmnParse bpmnParse, UserTask userTask) {
        super.executeParse(bpmnParse, userTask);

        ///// autoDeliver
        ActivitiListener deliverlistener = new ActivitiListener();
        deliverlistener.setEvent(TaskListener.EVENTNAME_CREATE);
        deliverlistener.setImplementationType(ImplementationType.IMPLEMENTATION_TYPE_INSTANCE);
        deliverlistener.setInstance(autoDelegateListener);
        userTask.getTaskListeners().add(0, deliverlistener);

        ///// user notify
        ActivitiListener listener = new ActivitiListener();
        //listener.setEvent(TaskListener.EVENTNAME_CREATE);
        listener.setEvent(TaskListener.EVENTNAME_ALL_EVENTS);
        listener.setImplementationType(ImplementationType.IMPLEMENTATION_TYPE_INSTANCE);
        listener.setInstance(notificationListener);
        userTask.getTaskListeners().add(listener);

        ///// user task start notify
        ActivitiListener listenerCreate = new ActivitiListener();
        listenerCreate.setEvent(TransactionDependentTaskListener.EVENTNAME_CREATE);
        listenerCreate.setImplementationType(ImplementationType.IMPLEMENTATION_TYPE_INSTANCE);
        listenerCreate.setOnTransaction(TransactionDependentTaskListener.ON_TRANSACTION_COMMITTED);
        listenerCreate.setInstance(taskCreateNotificationListener);
        userTask.getTaskListeners().add(listenerCreate);

        ///// user task end notify
        ActivitiListener listenerEnd = new ActivitiListener();
        listenerEnd.setEvent(TransactionDependentTaskListener.EVENTNAME_DELETE);
        listenerEnd.setImplementationType(ImplementationType.IMPLEMENTATION_TYPE_INSTANCE);
        listenerEnd.setOnTransaction(TransactionDependentTaskListener.ON_TRANSACTION_COMMITTED);
        listenerEnd.setInstance(taskEndNotificationListener);
        userTask.getTaskListeners().add(listenerEnd);

        /////// multiInstance or approveChain
        if (ActivitiUtils.isAddApproveChain(userTask)) {
            FlowNode flowNode = addApproveChain(bpmnParse, userTask);
            autoEnd(bpmnParse, flowNode);
        } else {
            multiInstance(userTask);
            autoEnd(bpmnParse, userTask);
        }
    }

    private FlowNode addApproveChain(BpmnParse bpmnParse, UserTask userTask) {
        List<SequenceFlow> outgoings = userTask.getOutgoingFlows();
        if (outgoings.size() != 1) {
            return userTask;
        }

        ActivitiListener executeListener = new ActivitiListener();
        executeListener.setEvent("start");
        executeListener.setImplementationType("expression");
        executeListener.setImplementation("#{hapApproveChain.onTaskStart(execution)}");
        userTask.getExecutionListeners().add(executeListener);

        SequenceFlow s0 = outgoings.get(0);

        ExclusiveGateway exclusiveGateway = new ExclusiveGateway();
        exclusiveGateway.setId("eg_auto_" + UUID.randomUUID().toString());
        exclusiveGateway.setParentContainer(userTask.getParentContainer());
        exclusiveGateway.setBehavior(activityBehaviorFactory.createExclusiveGatewayActivityBehavior(exclusiveGateway));

        SequenceFlow s1 = new SequenceFlow(); // usertask -> service

        s1.setValues(s0);
        s1.setWaypoints(s0.getWaypoints());
        s1.setSourceFlowElement(userTask);
        s1.setTargetFlowElement(exclusiveGateway);

        outgoings.remove(s0);
        outgoings.add(s1);

        exclusiveGateway.getIncomingFlows().add(s1);

        SequenceFlow s2 = new SequenceFlow(); // 选择网关指向 usertask
        s2.setId("sf_auto_" + UUID.randomUUID().toString());
        s2.setSourceRef(exclusiveGateway.getId());
        s2.setSourceFlowElement(exclusiveGateway);
        s2.setTargetRef(userTask.getId());
        s2.setTargetFlowElement(userTask);
        s2.setConditionExpression("${hapApproveChain.execute(execution,'" + userTask.getId() + "')=='Y'}");

        userTask.getIncomingFlows().add(s2);
        exclusiveGateway.getOutgoingFlows().add(s2);

        SequenceFlow defaultSeq = new SequenceFlow(); // continue;
        defaultSeq.setId("sf_auto_" + UUID.randomUUID().toString());

        if (s0.getTargetFlowElement() instanceof FlowNode) {
            FlowNode targetTskOfs0 = (FlowNode) s0.getTargetFlowElement();
            targetTskOfs0.getIncomingFlows().remove(s0);
            targetTskOfs0.getIncomingFlows().add(defaultSeq);
        }
        defaultSeq.setSourceRef(exclusiveGateway.getId());
        defaultSeq.setSourceFlowElement(exclusiveGateway);
        defaultSeq.setTargetFlowElement(s0.getTargetFlowElement());
        defaultSeq.setTargetRef(s0.getTargetRef());

        exclusiveGateway.getOutgoingFlows().add(0, defaultSeq);// add at 0
        exclusiveGateway.setDefaultFlow(defaultSeq.getId());

        bpmnParse.getSequenceFlows().put(s1.getId(), s1);
        bpmnParse.getSequenceFlows().put(s2.getId(), s2);
        bpmnParse.getSequenceFlows().put(defaultSeq.getId(), defaultSeq);

        return exclusiveGateway;
    }

    private void autoEnd(BpmnParse bpmnParse, FlowNode userTask) {
        List<SequenceFlow> outgoings = userTask.getOutgoingFlows();
        if (outgoings == null || outgoings.isEmpty()) {
            return;
        }

        SequenceFlow s0 = outgoings.get(0); // userTask 原始连出线

        FlowElement nextNode = s0.getTargetFlowElement();
        if (nextNode instanceof Task) {
            ExclusiveGateway exclusiveGateway = new ExclusiveGateway();
            exclusiveGateway.setId("eg_auto_" + UUID.randomUUID().toString());
            exclusiveGateway.setParentContainer(userTask.getParentContainer());
            exclusiveGateway
                    .setBehavior(activityBehaviorFactory.createExclusiveGatewayActivityBehavior(exclusiveGateway));

            outgoings.remove(s0);
            SequenceFlow s1 = new SequenceFlow();
            outgoings.add(s1); // s1 替换 s0, (id 保持不变)
            s1.setValues(s0); // s1 copy s0 属性
            s1.setSourceFlowElement(userTask);
            s1.setTargetFlowElement(exclusiveGateway);
            s1.setWaypoints(s0.getWaypoints());
            exclusiveGateway.setIncomingFlows(Arrays.asList(s1));

            SequenceFlow s2 = new SequenceFlow(); // 选择网关默认连出线, 连向 s0 原来的 target
            s2.setId("sf_auto_" + UUID.randomUUID().toString());
            s2.setSourceFlowElement(exclusiveGateway);
            s2.setSourceRef(exclusiveGateway.getId());
            s2.setTargetFlowElement(nextNode);
            s2.setTargetRef(nextNode.getId());
            ((Task) nextNode).getIncomingFlows().remove(s0);
            ((Task) nextNode).getIncomingFlows().add(s2);

            SequenceFlow s3 = new SequenceFlow(); // 从选择网关 连向 endevent
            s3.setId("sf_auto_" + UUID.randomUUID().toString());
            s3.setSourceFlowElement(exclusiveGateway);
            s3.setSourceRef(exclusiveGateway.getId());

            EndEvent endEvent = new EndEvent();
            endEvent.setId("end_auto_" + UUID.randomUUID().toString());

            s3.setTargetFlowElement(endEvent);
            s3.setTargetRef(endEvent.getId());
            s3.setConditionExpression("${approveResult=='REJECTED'}");

            exclusiveGateway.setOutgoingFlows(Arrays.asList(s2, s3));

            exclusiveGateway.setDefaultFlow(s2.getId());

            bpmnParse.getSequenceFlows().put(s1.getId(), s1);
            bpmnParse.getSequenceFlows().put(s2.getId(), s2);
            bpmnParse.getSequenceFlows().put(s3.getId(), s3);

        }
    }

    private void multiInstance(UserTask userTask) {

        MultiInstanceLoopCharacteristics loopCharacteristics = new MultiInstanceLoopCharacteristics();
        loopCharacteristics.setElementVariable("assignee");
        // loopCharacteristics.setInputDataItem("${approvalRole.getApprovalRule(execution)}");
        loopCharacteristics.setInputDataItem(APPROVAL_COLLECTION);
        loopCharacteristics.setSequential(false);
        // 设置完成条件 默认全部审批
        String condition = "${nrOfRejected>0}";
        FormProperty property = ActivitiUtils.getFormPropertyById(userTask.getFormProperties(), APPROVAL_STRATEGY);
        if (property != null && StringUtil.isNotEmpty(property.getName())) {
            String code = property.getName();
            ApproveStrategy record = new ApproveStrategy();
            record.setCode(code);
            record.setEnableFlag(BaseConstants.YES);
            record = strategyMapper.selectOne(record);
            if (record != null) {
                condition = record.getCondition();
                FormProperty num = ActivitiUtils.getFormPropertyById(userTask.getFormProperties(), APPROVAL_WFL_NUM);
                String numValue = "1";
                if (num != null) {
                    if (StringUtils.isNotEmpty(num.getName())) {
                        numValue = num.getName();
                    }
                }
                condition = condition.replace(APPROVAL_WFL_NUM, numValue);
            }
        }
        loopCharacteristics.setCompletionCondition(condition);
        userTask.setAssignee("${assignee}");
        userTask.setLoopCharacteristics(loopCharacteristics);

        // userTask.setAsynchronous(true);

    }

}
