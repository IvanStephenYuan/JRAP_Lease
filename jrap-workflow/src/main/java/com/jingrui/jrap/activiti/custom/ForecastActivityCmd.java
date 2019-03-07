package com.jingrui.jrap.activiti.custom;

import com.jingrui.jrap.activiti.components.ApprovalRule;
import com.jingrui.jrap.activiti.core.IActivitiConstants;
import com.jingrui.jrap.activiti.dto.ApproveChainHeader;
import com.jingrui.jrap.activiti.dto.ApproveChainLine;
import com.jingrui.jrap.activiti.service.IApproveChainHeaderService;
import com.jingrui.jrap.activiti.service.IApproveChainLineService;
import com.jingrui.jrap.activiti.util.ActivitiUtils;
import com.jingrui.jrap.core.impl.RequestHelper;
import com.jingrui.jrap.hr.dto.Employee;
import com.jingrui.jrap.hr.service.IEmployeeService;
import org.activiti.bpmn.model.UserTask;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.el.ExpressionManager;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * get forecast usertask approve (typically a usertask node)
 *
 * @author xiangyu.qi@jingrui.com
 */
public class ForecastActivityCmd implements Command, Serializable, IActivitiConstants {

    Logger logger = org.slf4j.LoggerFactory.getLogger(ForecastActivityCmd.class);

    public static final ThreadLocal<UserTask> userTask = new ThreadLocal<>();

    public static final ThreadLocal<String> executionId = new ThreadLocal<>();

    public static final ThreadLocal<Integer> executedCount = new ThreadLocal<>();

    @Autowired
    private ApprovalRule approvalRule;

    @Autowired
    private IEmployeeService employeeService;

    @Autowired
    IApproveChainHeaderService approveChainHeaderService;

    @Autowired
    IApproveChainLineService approveChainLineService;

    public Object execute(CommandContext commandContext) {
        UserTask task = userTask.get();
        ProcessEngineConfigurationImpl processEngineConfiguration = commandContext.getProcessEngineConfiguration();
        ExpressionManager expressionManager = processEngineConfiguration.getExpressionManager();
        ExecutionEntity execution = commandContext.getExecutionEntityManager().findById(executionId.get());
        // execution.getCurrentFlowElement();
        Set<Employee> approve = new LinkedHashSet<>();
        boolean isAddApproveChain = ActivitiUtils.isAddApproveChain(task);
        // 判断是否使用新版设计器
        if (ActivitiUtils.isUseNewModelEditor(task) && !isAddApproveChain) {
            Set<String> apvCods = approvalRule.processCandidate(task, execution);
            apvCods.forEach(t -> {
                addEmp(approve, t);
            });
        } else {
            if (isAddApproveChain) {
                ApproveChainHeader approveChainHeader = approveChainHeaderService.selectByUserTask(
                        StringUtils.substringBefore(execution.getProcessDefinitionId(), ":"), task.getId());
                if (approveChainHeader == null) {
                    logger.debug("{} has no related approve chain", userTask);
                } else {
                    List<ApproveChainLine> chainLines = approveChainLineService.selectByHeaderId(
                            RequestHelper.getCurrentRequest(true), approveChainHeader.getApproveChainId());
                    if (chainLines.isEmpty()) {
                        logger.warn("{} has no active approve chain line.", userTask);
                    }
                    int count = executedCount.get();
                    int current = 0;
                    for (ApproveChainLine line : chainLines) {
                        if (current < count) {
                            current++;
                            continue;
                        }
                        String assignee = line.getAssignee();
                        Object assObj = null;
                        if (StringUtils.isNotEmpty(assignee)) {
                            assObj = getValueFromExpression(expressionManager, execution, assignee);
                            if (assObj != null) {
                                addEmp(approve, assObj.toString());
                            }
                        } else {
                            String assigneeGrop = line.getAssignGroup();
                            if (StringUtils.isNotEmpty(assigneeGrop)) {
                                assObj = getValueFromExpression(expressionManager, execution, assigneeGrop);
                                List<Employee> emps = employeeService.selectByPostionCode(assObj.toString());
                                approve.addAll(emps);
                            }
                        }
                    }
                    return approve;
                }
            }
            String assignee = task.getAssignee();
            Object assObj = null;
            if (StringUtils.isNotEmpty(assignee)) {
                assObj = getValueFromExpression(expressionManager, execution, assignee);
                if (assObj != null) {
                    addEmp(approve, assObj.toString());
                }
            } else {
                // 候选人 候选组
                if (task.getCandidateUsers() != null) {
                    task.getCandidateUsers().forEach(t -> {
                        Object value = getValueFromExpression(expressionManager, execution, t);
                        if (value != null) {
                            addEmp(approve, value.toString());
                        }
                    });
                }
                if (task.getCandidateGroups() != null) {
                    task.getCandidateGroups().forEach(t -> {
                        Object value = getValueFromExpression(expressionManager, execution, t);
                        List<Employee> emps = employeeService.selectByPostionCode(value.toString());
                        approve.addAll(emps);
                    });
                }
            }
        }

        return approve;
    }

    private void addEmp(Set<Employee> approve, String employeeCode) {
        Employee emp = employeeService.queryInfoByCode(employeeCode);
        if (emp != null) {
            approve.add(emp);
        }
    }

    private Object getValueFromExpression(ExpressionManager expressionManager, ExecutionEntity execution, String exp) {
        Object value = null;
        try {
            value = expressionManager.createExpression(exp).getValue(execution);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return value;
    }

}