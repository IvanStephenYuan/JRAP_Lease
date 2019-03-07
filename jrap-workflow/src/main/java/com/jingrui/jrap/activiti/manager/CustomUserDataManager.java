package com.jingrui.jrap.activiti.manager;

import com.jingrui.jrap.activiti.util.ActivitiUtils;
import com.jingrui.jrap.hr.dto.Employee;
import com.jingrui.jrap.hr.dto.Position;
import com.jingrui.jrap.hr.mapper.EmployeeMapper;
import com.jingrui.jrap.hr.mapper.PositionMapper;
import com.jingrui.jrap.message.IMessageConsumer;
import com.jingrui.jrap.message.TopicMonitor;
import org.activiti.engine.identity.Group;
import org.activiti.engine.impl.persistence.entity.UserEntity;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisUserDataManager;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author shengyang.zhou@jingrui.com
 */
@TopicMonitor(channel = "employee.change")
public class CustomUserDataManager extends MybatisUserDataManager
        implements IMessageConsumer<Employee>, InitializingBean {

    @Autowired
    private PositionMapper positionMapper;

    @Autowired
    private EmployeeMapper employeeMapper;

    public Map<String, UserEntity> userCache = new HashMap<>();

    @Autowired
    private SpringProcessEngineConfiguration pec;

    public CustomUserDataManager() {
        super(null);
    }

    @Override
    public List<Group> findGroupsByUser(String userId) {
        List<Position> positions = positionMapper.getPositionByEmployeeCode(userId);
        List<Group> gs = new ArrayList<>();
        for (Position position : positions) {
            gs.add(ActivitiUtils.toActivitiGroup(position));
        }
        return gs;
    }

    /**
     * 这个方法使用非常频繁，做缓存支持
     *
     * @param entityId
     * @return
     */
    @Override
    public UserEntity findById(String entityId) {
        UserEntity userEntity = userCache.get(entityId);
        if (userEntity == null) {
            Employee employee = employeeMapper.queryByCode(entityId);
            userEntity = ActivitiUtils.toActivitiUser(employee);
            userCache.put(entityId, userEntity);
        }
        return userEntity;
    }

    @Override
    public void onMessage(Employee message, String pattern) {
        userCache.remove(message.getEmployeeCode());
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.processEngineConfiguration = pec;
    }
}
