package com.jingrui.jrap.task.mapper;

import com.jingrui.jrap.mybatis.common.Mapper;
import com.jingrui.jrap.task.dto.TaskExecutionDetail;
import org.apache.ibatis.annotations.Param;

/**
 * 任务/任务组执行记录详情 mapper.
 *
 * @author peng.jiang@jingrui.com
 * @date 2017/11/16.
 **/

public interface TaskExecutionDetailMapper extends Mapper<TaskExecutionDetail> {

    /**
     * 更新错误堆栈
     *
     * @param taskExecutionDetail 执行记录详情
     */
    void updateStacktrace(TaskExecutionDetail taskExecutionDetail);

    /**
     * 更新操作日志
     *
     * @param taskExecutionDetail 执行记录详情
     */
    void updateExecuteLog(TaskExecutionDetail taskExecutionDetail);

    /**
     * 获取操作日志
     *
     * @param taskExecutionDetail 执行记录详情
     * @return
     */
    TaskExecutionDetail getExecutionLog(TaskExecutionDetail taskExecutionDetail);

    /**
     * 通过执行记录ID删除执行记录详情.
     *
     * @param executionId 执行记录ID
     * @return 删除记录数量
     */
    int deleteByExecutionId(@Param("executionId") Long executionId);
}