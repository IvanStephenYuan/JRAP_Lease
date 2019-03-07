package com.jingrui.jrap.task.service.impl;

import com.jingrui.jrap.account.dto.Role;
import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.system.service.impl.BaseServiceImpl;
import com.jingrui.jrap.task.dto.TaskAssign;
import com.jingrui.jrap.task.mapper.TaskAssignMapper;
import com.jingrui.jrap.task.service.ITaskAssignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 任务权限Service-实现类.
 *
 * @author lijian.yin@jingrui.com
 * @date 2017/11/15.
 **/

@Service
public class TaskAssignServiceImpl extends BaseServiceImpl<TaskAssign> implements ITaskAssignService {

    @Autowired
    private TaskAssignMapper taskAssignMapper;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    public List<TaskAssign> query(IRequest request, TaskAssign condition) {
        return taskAssignMapper.query(condition);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    public List<Role> queryUnbound(IRequest request, List<String> idList) {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = sdf.format(date);
        try {
            date = sdf.parse(dateStr);
        }catch (Exception e){
            e.printStackTrace();
        }
        return taskAssignMapper.queryUnbound(idList, date);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    public List<Long> queryTaskId(IRequest iRequest, boolean isAdmin) {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = sdf.format(date);
        try {
            date = sdf.parse(dateStr);
        }catch (Exception e){
            e.printStackTrace();
        }
        return taskAssignMapper.queryTaskId(iRequest.getAllRoleId(), date, isAdmin);
    }

    @Override
    protected boolean useSelectiveUpdate() {
        return false;
    }

    @Override
    public List<TaskAssign> batchUpdate(IRequest request, List<TaskAssign> list) {
        list.forEach(assign -> {
            if (assign.getTaskAssignId() != null) {
                updateByPrimaryKey(request, assign);
            } else {
                insertSelective(request, assign);
            }
        });
        return list;
    }
}