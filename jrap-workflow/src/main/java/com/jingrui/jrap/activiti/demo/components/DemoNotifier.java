/*

package com.jingrui.jrap.activiti.demo.components;

import com.jingrui.jrap.activiti.listeners.IUserTaskNotifier;
import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.core.impl.RequestHelper;
import com.jingrui.jrap.hr.dto.Employee;
import com.jingrui.jrap.hr.dto.EmployeeAssign;
import com.jingrui.jrap.hr.dto.Position;
import com.jingrui.jrap.hr.mapper.EmployeeAssignMapper;
import com.jingrui.jrap.hr.mapper.EmployeeMapper;
import com.jingrui.jrap.hr.mapper.PositionMapper;
import com.jingrui.jrap.mail.ReceiverTypeEnum;
import com.jingrui.jrap.mail.dto.MessageReceiver;
import com.jingrui.jrap.mail.mapper.MessageTemplateMapper;
import com.jingrui.jrap.mail.service.IMessageService;
import org.activiti.engine.impl.persistence.entity.GroupEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.persistence.entity.UserEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

*/
/**
 * @author shengyang.zhou@jingrui.com
 *//*


//@Component
public class DemoNotifier implements IUserTaskNotifier {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private final String activityEmailNotifierTemplateCode = "ACT_EMAIL_NOTIFIER";

    @Autowired
    MessageTemplateMapper templateMapper;

    @Autowired
    IMessageService messageService;

    @Autowired
    EmployeeAssignMapper employeeAssignMapper;

    @Autowired
    EmployeeMapper employeeMapper;

    @Autowired
    PositionMapper positionMapper;

    @Override
    public void onTaskCreate(TaskEntity task, UserEntity userEntity) {
        Map<String, Object> contentInfo = new HashMap();
        contentInfo.put("dom", task.getName());
        contentInfo.put("receiver", userEntity.getFirstName());
        contentInfo.put("content", task.getDescription());
        IRequest iRequest = RequestHelper.getCurrentRequest();

        List<MessageReceiver> receivers = new ArrayList<>();
        MessageReceiver receiver = new MessageReceiver();
        receiver.setMessageAddress(userEntity.getEmail());
        receiver.setMessageType(ReceiverTypeEnum.NORMAL.getCode());
        receivers.add(receiver);
        try {
            messageService.addEmailMessage(iRequest.getUserId(), "TEST", activityEmailNotifierTemplateCode, contentInfo,
                    null, receivers);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public void onTaskCreate(TaskEntity task, GroupEntity groupEntity) {
        Position position = positionMapper.getPositionByCode(groupEntity.getId());
        Long positionId = position.getPositionId();
        EmployeeAssign ass = new EmployeeAssign();
        ass.setPositionId(positionId);
        List<EmployeeAssign> employeeAssign = employeeAssignMapper.select(ass);

        if (employeeAssign.isEmpty()) {
            logger.error("NO EMPLOYEE");
        } else {
            for (EmployeeAssign employee : employeeAssign) {
                Employee employee1 = employeeMapper.selectByPrimaryKey(employee.getEmployeeId());

                Map<String, Object> contentInfo = new HashMap();
                contentInfo.put("dom", task.getName());
                contentInfo.put("receiver", employee1.getName());
                contentInfo.put("content", task.getDescription());
                IRequest iRequest = RequestHelper.getCurrentRequest();

                List<MessageReceiver> receivers = new ArrayList<>();
                MessageReceiver receiver = new MessageReceiver();
                receiver.setMessageAddress(employee1.getEmail());
                receiver.setMessageType(ReceiverTypeEnum.NORMAL.getCode());
                receivers.add(receiver);
                try {
                    messageService.addEmailMessage(iRequest.getUserId(), "TEST", activityEmailNotifierTemplateCode, contentInfo,
                            null, receivers);
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
    }
}
*/
