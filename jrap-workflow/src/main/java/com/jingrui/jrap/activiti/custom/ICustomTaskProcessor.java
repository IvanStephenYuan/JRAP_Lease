package com.jingrui.jrap.activiti.custom;

import org.activiti.engine.task.Task;

import java.util.Date;

/**
 * Created by Qixiangyu on 2017/4/26.
 */
public interface ICustomTaskProcessor extends Comparable<ICustomTaskProcessor> {


    /**
     * @param task 任务节点
     *             动态设置task的优先级，调用getDueTime，获取任务剩余时间
     *             建议不要改动其他属性，仅仅设置优先级
     * @return 一般情况返回参数task
     */
    Task processPriority(Task task);

    /**
     * @param startDate task的创建时间
     * @param dueTime   当前task的任务的限定时间
     *                  <p>
     *                  根据task的创建时间和限定时间，返回任务的剩余时间，单位秒
     * @return 返回经过计算后的任务剩余时间 ,负数表示超时时间
     */
    Long getDueTime(Date startDate, Long dueTime);

    /**
     * 是否继续处理，如果返回false，不会再继续执行其他实现类
     */
    boolean processorContinue();

    int getOrder();

    @Override
    default int compareTo(ICustomTaskProcessor o) {
        return getOrder() - o.getOrder();
    }

}
