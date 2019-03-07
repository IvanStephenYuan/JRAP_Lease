package com.jingrui.jrap.hr.service.impl;

import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.hr.dto.Position;
import com.jingrui.jrap.hr.mapper.EmployeeAssignMapper;
import com.jingrui.jrap.hr.service.IPositionService;
import com.jingrui.jrap.message.IMessagePublisher;
import com.jingrui.jrap.mybatis.common.Criteria;
import com.jingrui.jrap.system.dto.DTOStatus;
import com.jingrui.jrap.system.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 岗位服务接口实现.
 *
 * @author hailin.xu@jingrui.com
 */
@Service
public class PositionServiceImpl extends BaseServiceImpl<Position> implements IPositionService {
    @Autowired
    private IMessagePublisher messagePublisher;

    @Autowired
    private EmployeeAssignMapper employeeAssignMapper;

    @Override
    protected boolean useSelectiveUpdate() {
        return false;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<Position> batchUpdate(IRequest request, List<Position> list) {
        Criteria criteria = new Criteria();
        criteria.update(Position.FIELD_NAME, Position.FIELD_DESCRIPTION, Position.FIELD_UNIT_ID, Position.FIELD_PARENT_POSITION_ID);
        criteria.updateExtensionAttribute();
        for (Position position : list) {
            if (position.get__status().equalsIgnoreCase(DTOStatus.UPDATE)) {
                self().updateByPrimaryKeyOptions(request, position, criteria);
            } else {
                self().insertSelective(request, position);
            }
            messagePublisher.publish("position.change", position);
        }
        return list;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteByPrimaryKey(Position position) {
        int ret = super.deleteByPrimaryKey(position);
        employeeAssignMapper.deleteByPositionId(position.getPositionId());
        return ret;
    }
}
