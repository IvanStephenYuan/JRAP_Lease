package com.jingrui.jrap.activiti.custom;

import com.jingrui.jrap.activiti.dto.WflRules;
import com.jingrui.jrap.activiti.mapper.RulesMapper;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.impl.bpmn.behavior.BusinessRuleTaskActivityBehavior;
import org.activiti.engine.impl.rules.RulesAgendaFilter;
import org.activiti.engine.impl.rules.RulesHelper;
import org.activiti.engine.impl.util.ProcessDefinitionUtil;
import org.activiti.engine.repository.ProcessDefinition;
import org.apache.commons.lang.StringUtils;
import org.drools.KnowledgeBase;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatefulKnowledgeSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * dynamic load drools rules from database
 *
 * @author shengyang.zhou@jingrui.com
 */
public class CustomBusinessRuleTaskActivityBehavior extends BusinessRuleTaskActivityBehavior {

    @Autowired
    private RulesMapper rulesMapper;

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void execute(DelegateExecution execution) {
        ProcessDefinition processDefinition = ProcessDefinitionUtil
                .getProcessDefinition(execution.getProcessDefinitionId());
        String deploymentId = processDefinition.getDeploymentId();


        KnowledgeBase knowledgeBase = null;
        try {
            knowledgeBase = RulesHelper.findKnowledgeBaseByDeploymentId(deploymentId);
        } catch (ActivitiException e) {
            logger.error(e.getMessage(), e);
            knowledgeBase = knowledgeBuilderFromDatabase(execution, processDefinition).newKnowledgeBase();
        }
        StatefulKnowledgeSession ksession = knowledgeBase.newStatefulKnowledgeSession();

        if (variablesInputExpressions != null) {
            Iterator<Expression> itVariable = variablesInputExpressions.iterator();
            while (itVariable.hasNext()) {
                Expression variable = itVariable.next();
                ksession.insert(variable.getValue(execution));
            }
        }
        if (!rulesExpressions.isEmpty()) {
            RulesAgendaFilter filter = new RulesAgendaFilter();
            for (Expression ruleName : rulesExpressions) {
                filter.addSuffic(ruleName.getValue(execution).toString());
            }
            filter.setAccept(!exclude);
            ksession.fireAllRules(filter);

        } else {
            ksession.fireAllRules();
        }

        Collection<Object> ruleOutputObjects = ksession.getObjects();
        if (ruleOutputObjects != null && !ruleOutputObjects.isEmpty()) {
            Collection<Object> outputVariables = new ArrayList<Object>();
            outputVariables.addAll(ruleOutputObjects);
            execution.setVariable(resultVariable, outputVariables);
        }
        ksession.dispose();
        leave(execution);
    }

    protected KnowledgeBuilder knowledgeBuilderFromDatabase(DelegateExecution execution, ProcessDefinition processDefinition) {
        String processKey = processDefinition.getKey();
        String nodeId = execution.getCurrentFlowElement().getId();

        WflRules rules = rulesMapper.selectByExecution(processKey, nodeId);
        if (rules == null) {
            rules = rulesMapper.selectByExecution(processKey, null);
            if (rules == null) {
                throw new RuntimeException("There is no rule associate with Process:" + processDefinition.getName()
                        + ",Node:" + execution.getCurrentFlowElement().getName());
            }
        }

        org.drools.io.Resource resource = null;
        try {
            resource = ResourceFactory
                    .newByteArrayResource(StringUtils.defaultIfEmpty(rules.getRuleContent(), "").getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
        kbuilder.add(resource, ResourceType.DRL);
        if (kbuilder.hasErrors()) {
            throw new RuntimeException("Error in drools: " + kbuilder.getErrors().toString());
        }
        return kbuilder;
    }
}
