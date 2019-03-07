package com.jingrui.jrap.activiti.custom;

import com.jingrui.jrap.activiti.core.IActivitiConstants;
import org.activiti.bpmn.model.Activity;
import org.activiti.bpmn.model.SubProcess;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.ActivitiIllegalArgumentException;
import org.activiti.engine.delegate.BpmnError;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.impl.bpmn.behavior.AbstractBpmnActivityBehavior;
import org.activiti.engine.impl.bpmn.behavior.BpmnActivityBehavior;
import org.activiti.engine.impl.bpmn.behavior.SequentialMultiInstanceBehavior;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityManager;

/**
 * @author shengyang.zhou@jingrui.com
 */
public class CustomSequentialMultiInstanceBehavior extends SequentialMultiInstanceBehavior {
    public CustomSequentialMultiInstanceBehavior(Activity activity,
                                                 AbstractBpmnActivityBehavior innerActivityBehavior) {
        super(activity, innerActivityBehavior);
    }

    protected int createInstances(DelegateExecution multiInstanceExecution) {

        int nrOfInstances = resolveNrOfInstances(multiInstanceExecution);
        if (nrOfInstances == 0) {
            return nrOfInstances;
        } else if (nrOfInstances < 0) {
            throw new ActivitiIllegalArgumentException(
                    "Invalid number of instances: must be a non-negative integer value" + ", but was " + nrOfInstances);
        }

        // Create child execution that will execute the inner behavior
        ExecutionEntity execution = Context.getCommandContext().getExecutionEntityManager()
                .createChildExecution((ExecutionEntity) multiInstanceExecution);
        execution.setCurrentFlowElement(multiInstanceExecution.getCurrentFlowElement());
        multiInstanceExecution.setMultiInstanceRoot(true);
        multiInstanceExecution.setActive(false);

        // Set Multi-instance variables
        setLoopVariable(multiInstanceExecution, NUMBER_OF_INSTANCES, nrOfInstances);
        setLoopVariable(multiInstanceExecution, NUMBER_OF_COMPLETED_INSTANCES, 0);
        setLoopVariable(multiInstanceExecution, NUMBER_OF_ACTIVE_INSTANCES, 1);
        setLoopVariable(multiInstanceExecution, IActivitiConstants.NUMBER_OF_APPROVED, 0);// CUSTOM
        setLoopVariable(multiInstanceExecution, IActivitiConstants.NUMBER_OF_REJECTED, 0);// CUSTOM

        setLoopVariable(multiInstanceExecution, getCollectionElementIndexVariable(), 0);
        setLoopVariable(execution, NUMBER_OF_INSTANCES, nrOfInstances);
        setLoopVariable(execution, NUMBER_OF_COMPLETED_INSTANCES, 0);
        setLoopVariable(execution, NUMBER_OF_ACTIVE_INSTANCES, 1);
        setLoopVariable(execution, IActivitiConstants.NUMBER_OF_APPROVED, 0);// CUSTOM
        setLoopVariable(execution, IActivitiConstants.NUMBER_OF_REJECTED, 0);// CUSTOM
        setLoopVariable(execution, getCollectionElementIndexVariable(), 0);
        logLoopDetails(multiInstanceExecution, "initialized", 0, 0, 1, nrOfInstances);

        if (nrOfInstances > 0) {
            executeOriginalBehavior(execution, 0);
        }

        return nrOfInstances;
    }

    public void leave(DelegateExecution execution) {
        DelegateExecution multiInstanceRootExecution = getMultiInstanceRootExecution(execution);
        int nrOfInstances = getLoopVariable(execution, NUMBER_OF_INSTANCES);
        int loopCounter = getLoopVariable(multiInstanceRootExecution, getCollectionElementIndexVariable()) + 1;
        int nrOfCompletedInstances = getLoopVariable(multiInstanceRootExecution, NUMBER_OF_COMPLETED_INSTANCES) + 1;
        int nrOfActiveInstances = getLoopVariable(execution, NUMBER_OF_ACTIVE_INSTANCES);

        setLoopVariable(multiInstanceRootExecution, NUMBER_OF_COMPLETED_INSTANCES, nrOfCompletedInstances);

        // CUSTOM
        String approveResult = String.valueOf(execution.getVariable(IActivitiConstants.PROP_APPROVE_RESULT));
        int nrOfApproved = getLoopVariable(multiInstanceRootExecution, IActivitiConstants.NUMBER_OF_APPROVED);
        int nrOfRejected = getLoopVariable(multiInstanceRootExecution, IActivitiConstants.NUMBER_OF_REJECTED);
        if (IActivitiConstants.APPROVED.equalsIgnoreCase(approveResult)) {
            nrOfApproved++;
            setLoopVariable(multiInstanceRootExecution, IActivitiConstants.NUMBER_OF_APPROVED, nrOfApproved);
        } else if (IActivitiConstants.REJECTED.equalsIgnoreCase(approveResult)) {
            nrOfRejected++;
            setLoopVariable(multiInstanceRootExecution, IActivitiConstants.NUMBER_OF_REJECTED, nrOfRejected);
        }
        // CUSTOM END

        setLoopVariable(multiInstanceRootExecution, getCollectionElementIndexVariable(), loopCounter);
        setLoopVariable(execution, getCollectionElementIndexVariable(), loopCounter);
        logLoopDetails(execution, "instance completed", loopCounter, nrOfCompletedInstances, nrOfActiveInstances,
                nrOfInstances);

        Context.getCommandContext().getHistoryManager().recordActivityEnd((ExecutionEntity) execution, null);
        callActivityEndListeners(execution);

        // executeCompensationBoundaryEvents(execution.getCurrentFlowElement(),
        // execution);

        if (loopCounter >= nrOfInstances || completionConditionSatisfied(multiInstanceRootExecution)) {
            removeLocalLoopVariable(multiInstanceRootExecution, getCollectionElementIndexVariable());
            removeLocalLoopVariable(execution, getCollectionElementIndexVariable());
            multiInstanceRootExecution.setMultiInstanceRoot(false);
            multiInstanceRootExecution.setScope(false);
            multiInstanceRootExecution.setCurrentFlowElement(execution.getCurrentFlowElement());
            Context.getCommandContext().getExecutionEntityManager()
                    .deleteChildExecutions((ExecutionEntity) multiInstanceRootExecution, "MI_END", false);
            //super.leave(multiInstanceRootExecution);
            new BpmnActivityBehavior().performDefaultOutgoingBehavior((ExecutionEntity) multiInstanceRootExecution);
        } else {
            try {

                if (execution.getCurrentFlowElement() instanceof SubProcess) {
                    ExecutionEntityManager executionEntityManager = Context.getCommandContext()
                            .getExecutionEntityManager();
                    ExecutionEntity executionToContinue = executionEntityManager
                            .createChildExecution((ExecutionEntity) multiInstanceRootExecution);
                    executionToContinue.setCurrentFlowElement(execution.getCurrentFlowElement());
                    executionToContinue.setScope(true);
                    setLoopVariable(executionToContinue, NUMBER_OF_INSTANCES, nrOfInstances);
                    setLoopVariable(executionToContinue, NUMBER_OF_COMPLETED_INSTANCES, nrOfCompletedInstances);
                    setLoopVariable(executionToContinue, NUMBER_OF_ACTIVE_INSTANCES, nrOfActiveInstances);
                    setLoopVariable(executionToContinue, IActivitiConstants.NUMBER_OF_APPROVED, nrOfApproved);//CUSTOM
                    setLoopVariable(executionToContinue, IActivitiConstants.NUMBER_OF_REJECTED, nrOfRejected);//CUSTOM

                    setLoopVariable(executionToContinue, getCollectionElementIndexVariable(), loopCounter);
                    executeOriginalBehavior(executionToContinue, loopCounter);
                } else {
                    executeOriginalBehavior(execution, loopCounter);
                }

            } catch (BpmnError error) {
                // re-throw business fault so that it can be caught by an Error
                // Intermediate Event or Error Event Sub-Process in the process
                throw error;
            } catch (Exception e) {
                throw new ActivitiException("Could not execute inner activity behavior of multi instance behavior", e);
            }
        }
    }
}
