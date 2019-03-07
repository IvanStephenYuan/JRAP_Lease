package com.jingrui.jrap.activiti.custom;

import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.el.ExpressionManager;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Map;

/**
 * 获取表达式值
 *
 * @author xiangyu.qi@jingrui.com
 */
public class GetExpressionValueCmd implements Command, Serializable {
    Logger logger = LoggerFactory.getLogger(GetExpressionValueCmd.class);

    //private String taskId;
    private String expression;
    private String executionId;
    private Map<String, Object> variables;

  /*  public GetExpressionValueCmd(String taskId, String expression) {
        this.taskId = taskId;
        this.expression = expression;
    }*/

    public GetExpressionValueCmd(String executionId, String expression, Map<String, Object> variables) {
        this.executionId = executionId;
        this.expression = expression;
        this.variables = variables;
    }


    @Override
    public Object execute(CommandContext commandContext) {
        Object result = null;
        boolean createExecution = false;
        ExecutionEntity execution = null;
        try {
            ProcessEngineConfigurationImpl processEngineConfiguration = commandContext.getProcessEngineConfiguration();
            ExpressionManager expressionManager = processEngineConfiguration.getExpressionManager();
            if (StringUtils.isNotEmpty(executionId)) {
                execution = commandContext.getExecutionEntityManager().findById(executionId);
            } else {
                execution = commandContext.getExecutionEntityManager().create();
                createExecution = true;
                execution.setVariablesLocal(variables);
            }
            result = expressionManager.createExpression(expression).getValue(execution);
        } catch (Exception e) {
            result = expression;
            logger.warn("property not found in task description expression " + e.getMessage());
        } finally {
            if (execution != null && createExecution) {
                execution.removeVariablesLocal(variables.keySet());
            }
        }
        return result;
    }

}