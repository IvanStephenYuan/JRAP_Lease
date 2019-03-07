/*
 * Copyright ZheJiang JingRui Co.,Ltd.
 */

package com.jingrui.jrap.activiti.service.impl;

import com.jingrui.jrap.activiti.core.IActivitiConstants;
import com.jingrui.jrap.activiti.custom.IActivitiBean;
import com.jingrui.jrap.activiti.dto.ApproveChainHeader;
import com.jingrui.jrap.activiti.dto.ApproveChainLine;
import com.jingrui.jrap.activiti.service.IApproveChainHeaderService;
import com.jingrui.jrap.activiti.service.IApproveChainLineService;
import com.jingrui.jrap.core.impl.RequestHelper;
import org.activiti.bpmn.model.UserTask;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.el.ExpressionManager;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author shengyang.zhou@jingrui.com
 */
@Component
public class JrapApproveChain implements IActivitiBean, IActivitiConstants {

    private Logger logger = LoggerFactory.getLogger(JrapApproveChain.class);

    private static String CURRENT_CHAIN_ID_PREFIX = "currentChainLineId_";
    private static String NO = "N";
    private static String YES = "Y";

    private static ThreadLocal<ApproveChainHolder> CHAIN_NEXT = new ThreadLocal<>();

    public static ThreadLocal<ApproveChainLine> CURRENT_LINE = new ThreadLocal<>();

    private boolean enableApproveChain = true;

    @Autowired
    IApproveChainHeaderService approveChainHeaderService;

    @Autowired
    IApproveChainLineService approveChainLineService;

    /**
     * 在选择网关中调用, 决定是不是要继续走审批链
     *
     * @param execution
     * @param userTaskId
     * @return
     */
    public String execute(DelegateExecution execution, String userTaskId) {
        if (!enableApproveChain) {
            return NO;
        }

        if (isRejected((String) execution.getVariable(PROP_APPROVE_RESULT))) {
            logger.debug("approve rejected, skip approve chain");
            return NO;
        }

        ApproveChainHeader approveChainHeader = approveChainHeaderService
                .selectByUserTask(StringUtils.substringBefore(execution.getProcessDefinitionId(), ":"), userTaskId);
        if (approveChainHeader == null) {
            logger.debug("UserTask has no related approve chain");
            return NO;
        }

        List<ApproveChainLine> chainLines = approveChainLineService
                .selectByHeaderId(RequestHelper.getCurrentRequest(true), approveChainHeader.getApproveChainId());
        if (chainLines.isEmpty()) {
            logger.warn("UserTask has no active approve chain line.");
            return NO;
        }

        Long currentLineId = (Long) execution.getVariable(CURRENT_CHAIN_ID_PREFIX + userTaskId);
        int nextIndex = 0;
        for (ApproveChainLine line : chainLines) {
            nextIndex++;
            if (line.getApproveChainLineId().equals(currentLineId)) {
                break;
            }
        }

        ApproveChainLine nextLine = getNextAvailableLine(execution, chainLines, nextIndex);

        if (nextLine != null) {
            ApproveChainHolder next = new ApproveChainHolder();
            next.chainLine = nextLine;
            CHAIN_NEXT.set(next);
            logger.debug("next approve chain line :" + next.chainLine);
            return YES;
        }
        logger.debug("no more approve chain line available");

        return NO;
    }

    private ApproveChainLine getNextAvailableLine(DelegateExecution execution, List<ApproveChainLine> chainLines,
                                                  int startIdx) {
        ProcessEngineConfigurationImpl processEngineConfiguration = Context.getProcessEngineConfiguration();
        ExpressionManager expressionManager = processEngineConfiguration.getExpressionManager();

        for (; startIdx < chainLines.size(); startIdx++) {
            ApproveChainLine line = chainLines.get(startIdx);
            if (StringUtils.isNotEmpty(line.getSkipExpression())) {
                Object result = expressionManager.createExpression(line.getSkipExpression()).getValue(execution);
                if (Boolean.TRUE.equals(result)) {
                    logger.debug("skip approve chain : " + line);
                    if ("Y".equalsIgnoreCase(line.getBreakOnSkip())) {
                        logger.info("chain loop stopped, due to previous skipped line.");
                        return null;
                    }
                } else {
                    return line;
                }
            } else {
                return line;
            }
        }
        return null;
    }

    /**
     * UserTask开始时, 首先检查有没有预设置的 审批链环节.
     *
     * @param execution
     */
    public void onTaskStart(DelegateExecution execution) {
        if (!enableApproveChain) {
            return;
        }
        UserTask userTask = (UserTask) execution.getCurrentFlowElement();

        ApproveChainHolder holder = CHAIN_NEXT.get();
        CHAIN_NEXT.remove(); // remove immediately
        ApproveChainLine currentLine;
        if (holder == null) {
            ApproveChainHeader approveChainHeader = approveChainHeaderService.selectByUserTask(
                    StringUtils.substringBefore(execution.getProcessDefinitionId(), ":"), userTask.getId());
            if (approveChainHeader == null) {
                logger.debug("{} has no related approve chain", userTask);
                return;
            }
            List<ApproveChainLine> chainLines = approveChainLineService
                    .selectByHeaderId(RequestHelper.getCurrentRequest(true), approveChainHeader.getApproveChainId());
            if (chainLines.isEmpty()) {
                logger.warn("{} has no active approve chain line.", userTask);
                return;
            }

            currentLine = getNextAvailableLine(execution, chainLines, 0);

            if (currentLine == null) {
                logger.warn("all approve chain are skipped");
                return;
            }
        } else {
            // other, come from exclusive gateway
            currentLine = holder.chainLine;
        }
        logger.debug("[{}] approve chain loop:{}", userTask.getName(), currentLine);

        CURRENT_LINE.set(currentLine);

        execution.setVariable(CURRENT_CHAIN_ID_PREFIX + userTask.getId(), currentLine.getApproveChainLineId());

    }

    public static class ApproveChainHolder {
        public ApproveChainLine chainLine;
    }
}
