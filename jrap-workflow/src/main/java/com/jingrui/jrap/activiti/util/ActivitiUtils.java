package com.jingrui.jrap.activiti.util;

import com.jingrui.jrap.activiti.core.IActivitiConstants;
import com.jingrui.jrap.core.BaseConstants;
import com.jingrui.jrap.hr.dto.Employee;
import com.jingrui.jrap.hr.dto.Position;
import com.jingrui.jrap.mybatis.util.StringUtil;
import org.activiti.bpmn.model.FormProperty;
import org.activiti.bpmn.model.UserTask;
import org.activiti.engine.identity.Group;
import org.activiti.engine.impl.persistence.entity.GroupEntity;
import org.activiti.engine.impl.persistence.entity.GroupEntityImpl;
import org.activiti.engine.impl.persistence.entity.UserEntity;
import org.activiti.engine.impl.persistence.entity.UserEntityImpl;
import org.apache.commons.lang.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * @author shengyang.zhou@jingrui.com
 */
public class ActivitiUtils implements IActivitiConstants {
    public static UserEntity toActivitiUser(Employee emp) {
        UserEntityImpl entity = new UserEntityImpl();
        if (emp == null) {
            return entity;
        }
        entity.setId(emp.getEmployeeCode());
        String empName = emp.getName();
        entity.setFirstName(StringUtils.defaultIfEmpty(empName, "UNKNOWN"));
        entity.setLastName("");
        entity.setEmail(emp.getEmail());
        entity.setRevision(1);
        return entity;
    }

    public static GroupEntity toActivitiGroup(Position position) {
        GroupEntityImpl groupEntity = new GroupEntityImpl();
        if (position == null) {
            return groupEntity;
        }
        groupEntity.setRevision(1);
        groupEntity.setId(position.getPositionCode());
        groupEntity.setName(position.getName());
        groupEntity.setType("assignment");
        return groupEntity;
    }

    public static List<Group> toActivitiGroups(List<String> groupIds) {
        return null;
    }

    /**
     * 根据表单属性id获得表单属性FormProperty
     *
     * @param properties 表单属性
     * @param id         根据Id查找
     * @return 返回表单属性对象
     */
    public static FormProperty getFormPropertyById(List<FormProperty> properties, String id) {
        FormProperty formProperty = null;
        for (FormProperty property : properties) {
            if (id.equalsIgnoreCase(property.getId())) {
                formProperty = property;
                break;
            }
        }
        return formProperty;
    }

    /**
     * 是否添加审批链
     *
     * @return 如果返回true 表明添加审批链，不使用审批策略。
     */
    public static boolean isAddApproveChain(UserTask userTask) {
        FormProperty approvalStrategy = ActivitiUtils.getFormPropertyById(userTask.getFormProperties(),
                APPROVAL_STRATEGY);
        FormProperty approvalCandidate = ActivitiUtils.getFormPropertyById(userTask.getFormProperties(),
                APPROVAL_CANDIDATE);
        if (approvalStrategy != null && "WFL_CHAIN".equalsIgnoreCase(approvalStrategy.getName())) {
            return true;
        } else if (approvalStrategy == null && approvalCandidate == null) {
            return true;
        } else if (approvalStrategy != null && StringUtil.isEmpty(approvalStrategy.getName())
                && approvalCandidate != null && approvalCandidate.getFormValues() == null) {
            return true;
        }
        return false;
    }

    /**
     * 是否添加审批链
     *
     * @return 如果返回true 表明添加审批链，不使用审批策略。
     */
    public static boolean isEnabledRevoke(UserTask userTask) {
        FormProperty revokeEnableFlag = ActivitiUtils.getFormPropertyById(userTask.getFormProperties(),
                REVOKE_ENABLE_FLAG);
        if (revokeEnableFlag != null && BaseConstants.NO.equalsIgnoreCase(revokeEnableFlag.getName())) {
            return false;
        }
        return true;
    }


    public static boolean isUseNewModelEditor(UserTask task) {
        FormProperty approvalCandidate = ActivitiUtils.getFormPropertyById(task.getFormProperties(),
                APPROVAL_CANDIDATE);
        if (approvalCandidate != null && approvalCandidate.getFormValues() != null) {
            return true;
        }
        return false;
    }

    /**
     * 返回第二个日期与第一个日期之间相差的秒数
     *
     * @return 可能为负数，表明第二个日期在第一个日期之前
     */
    public static Long secondsBetweenDate(Date firstDate, Date secondDate) {
        Long interval = (Long) ((secondDate.getTime() - firstDate.getTime()) / 1000);
        return interval;
    }
}
