package com.jingrui.jrap.activiti.components;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.jingrui.jrap.account.dto.Role;
import com.jingrui.jrap.account.mapper.RoleMapper;
import com.jingrui.jrap.activiti.approval.dto.ApproveCandidateRule;
import com.jingrui.jrap.activiti.approval.dto.ApproveStrategy;
import com.jingrui.jrap.activiti.approval.dto.BusinessRuleHeader;
import com.jingrui.jrap.activiti.approval.dto.BusinessRuleLine;
import com.jingrui.jrap.activiti.approval.mapper.ApproveCandidateRuleMapper;
import com.jingrui.jrap.activiti.approval.mapper.ApproveStrategyMapper;
import com.jingrui.jrap.activiti.approval.mapper.BusinessRuleHeaderMapper;
import com.jingrui.jrap.activiti.approval.mapper.BusinessRuleLineMapper;
import com.jingrui.jrap.activiti.core.IActivitiConstants;
import com.jingrui.jrap.activiti.custom.IActivitiBean;
import com.jingrui.jrap.activiti.util.ActivitiUtils;
import com.jingrui.jrap.core.BaseConstants;
import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.core.impl.RequestHelper;
import com.jingrui.jrap.hr.dto.Employee;
import com.jingrui.jrap.hr.dto.Position;
import com.jingrui.jrap.hr.mapper.EmployeeMapper;
import com.jingrui.jrap.hr.mapper.PositionMapper;
import org.activiti.bpmn.model.FormProperty;
import org.activiti.bpmn.model.FormValue;
import org.activiti.bpmn.model.UserTask;
import org.activiti.editor.constants.EditorJsonConstants;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.el.ExpressionManager;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;

/**
 * @author Qixiangyu on 2017/3/16.
 */
@Component
public class ApprovalRule implements IActivitiBean, IActivitiConstants, EditorJsonConstants {

    private final static String ID = "id";
    private final static String NAME = "name";
    private final static String CODE = "code";
    private final static String YES = "Y";
    private final static String ENUM_VALUES = "enumValues";
    private final static String ASSIGNEE = "assignee";
    private final static String ASSIGNEE_CODE = "assigneeCode";
    private final static String ASSIGNEE_NAME = "assigneeName";
    private final static String FORM_PROPERTIES = "formProperties";
    private final static String APPROVE_STRATEGY = "approve_strategy";
    private final static String APPROVE_RULE = "approve_rule";
    private final static String DESCRIPTION = "description";
    private final static String RULES = "rules";

    private static final Logger logger = LoggerFactory.getLogger(ApprovalRule.class);

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ApproveCandidateRuleMapper candidateRuleMapper;

    @Autowired
    private BusinessRuleHeaderMapper businessRuleHeaderMapper;

    @Autowired
    private BusinessRuleLineMapper businessRuleLineMapper;

    @Autowired
    private ApproveStrategyMapper strategyMapper;

    @Autowired
    private PositionMapper positionMapper;

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private ProcessEngineConfigurationImpl processEngineConfiguration;

    /**
     * 动态解析表单属性返回多实例的集合
     *
     * @param execution 获取流程信息
     * @return
     */
    public Set<String> getApprovalRule(DelegateExecution execution) {
        // 验证是否是userTask
        Object element = execution.getCurrentFlowElement();
        UserTask task;
        if (element instanceof UserTask) {
            task = (UserTask) element;
        } else {
            logger.error("must be a UserTask can use this expression");
            throw new ActivitiException("must be a UserTask can use this expression");
        }
        return processCandidate(task, execution);
    }

    /**
     * 根据表单属性，动态解析出集合，避免选人重复，返回set集合去重
     *
     * @return set集合，包含解析出来的所有employeeCode
     */
    public Set<String> processCandidate(UserTask task, DelegateExecution execution) {

        ExpressionManager expressionManager = processEngineConfiguration.getExpressionManager();
        Set<String> candidates = new LinkedHashSet<>();
        // 获取候选人规则对应的表单属性
        FormProperty property = ActivitiUtils.getFormPropertyById(task.getFormProperties(), APPROVAL_CANDIDATE);
        List<FormValue> values = property.getFormValues();
        if (values == null) {
            logger.warn("APPROVAL_CANDIDATE has no formValue check formProperty");
            return candidates;
        }
        String strategyCode, jsonVule;
        // 选人策略对象
        ApproveCandidateRule candidateRule;
        // 解析出的候选人
        Object user;
        // Map<String, List> ruleMap;
        JsonNode ruleJson = null;
        for (FormValue value : values) {
            strategyCode = value.getId();
            candidateRule = getCandidateRule(strategyCode);
            // 失效或者CODE不正确
            if (candidateRule == null) {
                logger.warn("code :" + strategyCode + " is disable or not exist");
                continue;
            }
            jsonVule = value.getName();
            try {
                ruleJson = objectMapper.readTree(jsonVule);
            } catch (IOException e) {
                logger.warn(e.getMessage());
            }
            if (!hasPermission(ruleJson, execution)) {
                continue;
            }
            ArrayNode parameters = (ArrayNode) ruleJson.get(APPROVAL_PARAMETER);
            // 指定人员
            if (APPROVAL_CANDIDATE_EMP.equalsIgnoreCase(strategyCode)) {
                for (JsonNode para : parameters) {
                    JsonNode codeObj = para.get(APPROVAL_CODE_EMPLOYEE);
                    if (codeObj == null) {
                        logger.warn("employee code is null");
                        continue;
                    }
                    String employeeCode = codeObj.textValue();
                    execution.setVariableLocal(APPROVAL_CODE_EMPLOYEE, employeeCode);
                    Object empCode = expressionManager.createExpression(candidateRule.getExpression())
                            .getValue(execution);
                    candidates.add(empCode.toString());
                }
            } else if (APPROVAL_CANDIDATE_POS.equalsIgnoreCase(strategyCode)) {
                // 指定岗位
                for (JsonNode para : parameters) {
                    JsonNode codeObj = para.get(APPROVAL_CODE_POSITION);
                    if (codeObj == null) {
                        logger.warn("position code is null");
                        continue;
                    }
                    String positionCode = codeObj.textValue();
                    execution.setVariableLocal(APPROVAL_CODE_POSITION, positionCode);
                    List<String> emps = (List<String>) expressionManager.createExpression(candidateRule.getExpression())
                            .getValue(execution);
                    candidates.addAll(emps);
                }
            } else if (APPROVAL_CANDIDATE_ROLE.equalsIgnoreCase(strategyCode)) {
                // 指定角色
                for (JsonNode para : parameters) {
                    JsonNode codeObj = para.get(APPROVAL_CODE_ROLE);
                    if (codeObj == null) {
                        logger.warn("role code is null");
                        continue;
                    }
                    String roleCode = codeObj.textValue();
                    execution.setVariableLocal(APPROVAL_CODE_ROLE, roleCode);
                    List<String> emps = (List<String>) expressionManager.createExpression(candidateRule.getExpression())
                            .getValue(execution);
                    candidates.addAll(emps);
                }
            } else {
                // 如果无法解析表达式，继续找下一个审批规则
                try {
                    user = expressionManager.createExpression(candidateRule.getExpression()).getValue(execution);
                    if (user != null) {
                        Set<String> users = null;
                        if (user instanceof String) {
                            users = org.springframework.util.StringUtils
                                    .commaDelimitedListToSet(user.toString());
                        } else if (user instanceof Set) {
                            users = (Set<String>) user;
                        } else if (user instanceof List) {
                            users = new HashSet<>((List) user);
                        } else {
                            logger.error("expression " + candidateRule.getExpression() + " result type must be String, " +
                                    "List or Set .not support " + user.getClass().toString());
                        }
                        if (users != null) {
                            candidates.addAll(users);
                        }
                    }
                } catch (ActivitiException e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        if (candidates.size() == 0) {
            logger.warn("The current node has no approver");
            candidates.add("");
        }
        return candidates;
    }

    /**
     * 导入xml流程定义文件时，处理扩展的自定义属性
     * 注意表单属性的key 与 properties里的key不一致
     * @param content 流程定义文件json对象
     * @return 处理后的json对象
     */
    public ObjectNode processCustomProperties(ObjectNode content) {
        List<ObjectNode> userTask = new ArrayList<>();
        processUserTaskNode(content, userTask);
        for (ObjectNode child : userTask) {
            ObjectNode prop = (ObjectNode) child.get(EDITOR_SHAPE_PROPERTIES);
            ObjectNode formProp = (ObjectNode) prop.get(FORM_PROPERTIES.toLowerCase());
            if (formProp != null && !formProp.isNull()) {
                ArrayNode formPropVaule = (ArrayNode) formProp.get(FORM_PROPERTIES);
                for (JsonNode value : formPropVaule) {
                    String valueId = value.get(ID).asText();
                    switch (valueId) {
                        case APPROVAL_STRATEGY: {
                            String code = value.get(NAME).asText();
                            ApproveStrategy record = new ApproveStrategy();
                            record.setCode(code);
                            record = strategyMapper.selectOne(record);
                            if (record != null) {
                                prop.put(APPROVE_STRATEGY, record.getDescription());
                            }
                            break;
                        }
                        case APPROVAL_WFL_NUM: {
                            JsonNode numObj = value.get(NAME);
                            if (numObj != null && !numObj.isNull()) {
                                Double num = numObj.asDouble();
                                prop.put(APPROVAL_WFL_NUM.toLowerCase(), num);
                            }
                            break;
                        }
                        case APPROVAL_ACTION: {
                            ArrayNode enumValues = (ArrayNode) value.get(ENUM_VALUES);
                            for (JsonNode node : enumValues) {
                                String actionType = node.get(ID).asText();
                                if (ADD_SIGN_FLAG.equalsIgnoreCase(actionType)
                                        || DELEGATE_FLAG.equalsIgnoreCase(actionType)) {
                                    prop.put(actionType.toLowerCase(), YES.equalsIgnoreCase(node.get(NAME).asText()));
                                }
                            }
                            break;
                        }
                        case APPROVAL_CANDIDATE: {
                            prop.set(APPROVE_RULE, processCadidateRuleNode((ObjectNode) value));
                            break;
                        }
                        case REVOKE_ENABLE_FLAG: {
                            JsonNode revokeFlag = value.get(NAME);
                            if (revokeFlag != null && !revokeFlag.isNull()) {
                                String flag = revokeFlag.asText();
                                prop.put(REVOKE_ENABLE_FLAG_WITHOUT_ACT.toLowerCase(), YES.equalsIgnoreCase(flag));
                            }
                        }
                        default:

                    }

                }
            }
        }
        return content;
    }

    private void processUserTaskNode(ObjectNode content, List<ObjectNode> userTask) {
        ArrayNode childShaps = (ArrayNode) content.get(EDITOR_CHILD_SHAPES);
        for (JsonNode key : childShaps) {
            ObjectNode child = (ObjectNode) key;
            String id = child.get(EDITOR_STENCIL).get(EDITOR_STENCIL_ID).asText();
            if ("userTask".equalsIgnoreCase(id)) {
                userTask.add(child);
            } else {
                processUserTaskNode(child, userTask);
            }
        }
    }

    private ObjectNode processCadidateRuleNode(ObjectNode value) {
        ArrayNode enumValues = (ArrayNode) value.get(ENUM_VALUES);
        ObjectNode ruleNode = objectMapper.createObjectNode();
        ArrayNode assignmentNode = objectMapper.createArrayNode();
        if (enumValues == null || enumValues.isNull()) {
            return ruleNode;
        }
        for (int i = 0; i < enumValues.size(); i++) {
            ObjectNode node = (ObjectNode) enumValues.get(i);
            String ruleCode = node.get(ID).asText();
            ApproveCandidateRule record = new ApproveCandidateRule();
            record.setCode(ruleCode);
            record = candidateRuleMapper.selectOne(record);

            ObjectNode index = objectMapper.createObjectNode();
            index.put(ID, i);
            index.put(CODE, ruleCode);

            String jsonMessage = node.get(NAME).textValue();
            JsonNode cadidate = null;
            try {
                cadidate = objectMapper.readTree(jsonMessage);
            } catch (IOException e) {
                e.printStackTrace();
            }

            // 指定岗位或者指定人
            if (APPOINTED_POSITION.equalsIgnoreCase(ruleCode)) {
                ArrayNode parameters = (ArrayNode) cadidate.get(APPROVAL_PARAMETER);
                String positionCode = null;
                for (JsonNode parameter : parameters) {
                    if (parameter.get(APPROVAL_CODE_POSITION) != null) {
                        positionCode = parameter.get(APPROVAL_CODE_POSITION).textValue();
                        break;
                    }
                }
                if (StringUtils.isNotEmpty(positionCode)) {
                    IRequest iRequest = RequestHelper.newEmptyRequest();
                    iRequest.setLocale("zh_CN");
                    RequestHelper.setCurrentRequest(iRequest);
                    Position position = positionMapper.getPositionByCode(positionCode);
                    RequestHelper.clearCurrentRequest();
                    index.put(ASSIGNEE_CODE, positionCode);
                    index.put(ASSIGNEE_NAME, positionCode);
                    if (position != null) {
                        index.put(ASSIGNEE, position.getPositionId());
                        index.put(ASSIGNEE_NAME, position.getName());
                    } else {
                        logger.error("no position with code:" + positionCode);
                    }
                }
            } else if (APPOINTED_EMPLOYEE.equalsIgnoreCase(ruleCode)) {
                String employeeCode = null;
                ArrayNode parameters = (ArrayNode) cadidate.get(APPROVAL_PARAMETER);
                for (JsonNode parameter : parameters) {
                    if (parameter.get(APPROVAL_CODE_EMPLOYEE) != null) {
                        employeeCode = parameter.get(APPROVAL_CODE_EMPLOYEE).textValue();
                        break;
                    }
                }
                if (StringUtils.isNotEmpty(employeeCode)) {
                    index.put(ASSIGNEE_CODE, employeeCode);
                    index.put(ASSIGNEE_NAME, employeeCode);
                    Employee employee = employeeMapper.queryByCode(employeeCode);
                    if (employee != null) {
                        index.put(ASSIGNEE, employee.getEmployeeCode());
                        index.put(ASSIGNEE_NAME, employee.getName());
                    } else {
                        logger.error("no employee with code:" + employeeCode);
                    }
                }
            } else if (APPROVAL_CANDIDATE_ROLE.equalsIgnoreCase(ruleCode)) {
                String roleCode = null;
                ArrayNode parameters = (ArrayNode) cadidate.get(APPROVAL_PARAMETER);
                for (JsonNode parameter : parameters) {
                    if (parameter.get(APPROVAL_CODE_ROLE) != null) {
                        roleCode = parameter.get(APPROVAL_CODE_ROLE).textValue();
                        break;
                    }
                }
                if (StringUtils.isNotEmpty(roleCode)) {
                    index.put(ASSIGNEE_CODE, roleCode);
                    index.put(ASSIGNEE_NAME, roleCode);
                    Role role = new Role();
                    role.setRoleCode(roleCode);
                    IRequest iRequest = RequestHelper.newEmptyRequest();
                    iRequest.setLocale("zh_CN");
                    RequestHelper.setCurrentRequest(iRequest);
                    role = roleMapper.selectOne(role);
                    RequestHelper.clearCurrentRequest();
                    if (role != null) {
                        index.put(ASSIGNEE, role.getRoleCode());
                        index.put(ASSIGNEE_NAME, role.getRoleName());
                    } else {
                        logger.error("no employee with code:" + roleCode);
                    }
                }
            }
            if (record != null) {
                index.put(DESCRIPTION, record.getDescription());
            }
            ArrayNode bussinessRuleNode = processBussinessRuleNode((ObjectNode) cadidate);
            if (bussinessRuleNode != null) {
                index.set(RULES, bussinessRuleNode);
            }
            assignmentNode.add(index);
        }
        ruleNode.set("assignment", assignmentNode);
        return ruleNode;
    }

    private ArrayNode processBussinessRuleNode(ObjectNode node) {
        ArrayNode businessRuleNode = null;
        ArrayNode businessRules = (ArrayNode) node.get(APPROVAL_BUSINESS_RULE);
        if (businessRules != null) {
            businessRuleNode = objectMapper.createArrayNode();
            for (JsonNode index : businessRules) {
                ObjectNode ruleNode = (ObjectNode) index;
                String ruleCode = ruleNode.get(CODE.toUpperCase()).textValue();
                ObjectNode objectNode = objectMapper.createObjectNode();
                objectNode.put(CODE, ruleCode);
                objectNode.put("businessRuleCode", ruleCode);
                BusinessRuleHeader rule = businessRuleHeaderMapper.selectByCode(ruleCode);
                if (rule != null) {
                    objectNode.put("businessRuleDescription", rule.getDescription());
                } else {
                    logger.error("no business rule  with code" + ruleCode);
                }
                businessRuleNode.add(objectNode);
            }

        }
        return businessRuleNode;
    }

    private ApproveCandidateRule getCandidateRule(String code) {
        // 根据code返回ApproveCandidateRule对象
        ApproveCandidateRule record = new ApproveCandidateRule();
        record.setCode(code);
        record.setEnableFlag(BaseConstants.YES);
        record = candidateRuleMapper.selectOne(record);
        return record;
    }

    private boolean hasPermission(JsonNode ruleMap, DelegateExecution execution) {
        if (ruleMap == null) {
            return true;
        }
        ExpressionManager expressionManager = processEngineConfiguration.getExpressionManager();

        // 取权限code
        ArrayNode rules = (ArrayNode) ruleMap.get(APPROVAL_BUSINESS_RULE);
        if (rules == null) {
            return true;
        }
        String code;
        BusinessRuleHeader header;
        BusinessRuleLine line = new BusinessRuleLine();
        List<BusinessRuleLine> lines;
        Date now = new Date();
        Date startDate, endDate;
        Object result;
        for (JsonNode rule : rules) {
            code = rule.get(CODE.toUpperCase()).textValue();
            if (StringUtils.isEmpty(code)) {
                continue;
            }
            header = businessRuleHeaderMapper.selectByCode(code);
            if (header == null) {
                logger.warn("businessRule is disable or not exist");
                continue;
            }
            startDate = header.getStartActiveDate();
            endDate = header.getEndActiveDate();
            if (startDate != null) {
                // 验证是否已到启用日期
                if (now.before(startDate)) {
                    logger.warn("now before startDate ,businessRule is disable");
                    continue;
                }
            }
            if (endDate != null) {
                // 验证是否已经过期
                if (now.after(endDate)) {
                    logger.warn("now after startDate ,businessRule is disable");
                    continue;
                }
            }
            line.setBusinessRuleId(header.getBusinessRuleId());
            line.setEnableFlag(BaseConstants.YES);
            lines = businessRuleLineMapper.select(line);
            for (BusinessRuleLine ruleLine : lines) {
                try {
                    result = expressionManager.createExpression(ruleLine.getConditions()).getValue(execution);
                    if (Boolean.FALSE.equals(result)) {
                        return false;
                    }
                } catch (Exception e) {
                    // 解析失败，认为有权限审批
                    logger.warn("condition fail", e);
                    return true;
                }
            }
        }
        return true;
    }
}
