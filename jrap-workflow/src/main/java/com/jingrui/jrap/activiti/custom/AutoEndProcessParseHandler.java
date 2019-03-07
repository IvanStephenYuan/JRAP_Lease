/*
 * Copyright ZheJiang JingRui Co.,Ltd.
 */

package com.jingrui.jrap.activiti.custom;

import org.activiti.bpmn.model.*;
import org.activiti.engine.impl.bpmn.behavior.ExclusiveGatewayActivityBehavior;
import org.activiti.engine.impl.bpmn.parser.BpmnParse;
import org.activiti.engine.impl.bpmn.parser.handler.UserTaskParseHandler;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class AutoEndProcessParseHandler extends UserTaskParseHandler {

    @Override
    protected void executeParse(BpmnParse bpmnParse, UserTask userTask) {
        super.executeParse(bpmnParse, userTask);
        List<SequenceFlow> outgoings = userTask.getOutgoingFlows();
        if (outgoings == null || outgoings.size() != 1) {
            return;
        }
        SequenceFlow s0 = outgoings.get(0);

        FlowElement nextNode = s0.getTargetFlowElement();
        if (nextNode instanceof Task) {
            ExclusiveGateway exclusiveGateway = new ExclusiveGateway();
            exclusiveGateway.setId("eg_auto_" + UUID.randomUUID().toString());
            exclusiveGateway.setParentContainer(userTask.getParentContainer());
            exclusiveGateway.setBehavior(new ExclusiveGatewayActivityBehavior());

            outgoings.remove(s0);
            SequenceFlow s1 = new SequenceFlow();
            outgoings.add(s1);
            s1.setValues(s0);
            s1.setSourceFlowElement(userTask);
            s1.setTargetFlowElement(exclusiveGateway);
            s1.setWaypoints(s0.getWaypoints());
            exclusiveGateway.setIncomingFlows(Arrays.asList(s1));

            SequenceFlow s2 = new SequenceFlow();
            s2.setId("sf_auto_" + UUID.randomUUID().toString());
            s2.setSourceFlowElement(exclusiveGateway);
            s2.setSourceRef(exclusiveGateway.getId());
            s2.setTargetFlowElement(nextNode);
            s2.setTargetRef(nextNode.getId());
            ((Task) nextNode).getIncomingFlows().remove(s0);
            ((Task) nextNode).getIncomingFlows().add(s2);

            SequenceFlow s3 = new SequenceFlow();
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
}