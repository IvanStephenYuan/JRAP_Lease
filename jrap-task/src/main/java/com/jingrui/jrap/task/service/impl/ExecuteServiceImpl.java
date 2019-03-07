package com.jingrui.jrap.task.service.impl;

import com.github.pagehelper.StringUtil;
import com.jingrui.jrap.core.AppContextInitListener;
import com.jingrui.jrap.core.impl.RequestHelper;
import com.jingrui.jrap.message.TaskListenerContainer;
import com.jingrui.jrap.task.TaskConstants;
import com.jingrui.jrap.task.dto.TaskDetail;
import com.jingrui.jrap.task.dto.TaskExecution;
import com.jingrui.jrap.task.exception.TaskInterruptException;
import com.jingrui.jrap.task.info.ExecutionInfo;
import com.jingrui.jrap.task.info.ParameterInfo;
import com.jingrui.jrap.task.info.TaskDataInfo;
import com.jingrui.jrap.task.info.ThreadManageInfo;
import com.jingrui.jrap.task.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.*;
import java.util.concurrent.RejectedExecutionException;

/**
 * 执行任务service
 *
 * @author peng.jiang@jingrui.com
 * @date 2017/11/6.
 **/

@Service
public class ExecuteServiceImpl implements IExecuteService, AppContextInitListener {

    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private ITaskDetailService taskDetailService;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Autowired
    private ITaskExecutionService taskExecutionService;

    private List<IExecuteListener> executeListeners = Collections.emptyList();

    @Override
    public void taskExecute(TaskDataInfo taskDataInfo) {
        TaskThread taskThread = new TaskThread(taskDataInfo);
        taskThread.setName(TaskConstants.TASK_QUEUE + "-" + taskDataInfo.getExecutionId());
        try {
            taskExecutor.execute(taskThread);
        } catch (RejectedExecutionException e) {
            //任务被线程池拒绝，修改执行记录状态

            // 当前执行的任务组或任务状态改为未执行
            taskExecutionService.updateStatus(taskDataInfo.getExecutionId(), TaskConstants.EXECUTION_UN_EXECUTED);
            // 就绪的子任务状态改为未执行
            taskExecutionService.batchUpdateStatus(taskDataInfo.getExecutionId(), TaskConstants.EXECUTION_READY,
                    TaskConstants.EXECUTION_UN_EXECUTED);

            throw e;
        }
    }

    private class TaskThread extends Thread {

        private TaskDataInfo taskDataInfo = null;

        private boolean isSuccess = true;

        private TaskThread(TaskDataInfo taskDataInfo) {
            this.taskDataInfo = taskDataInfo;
        }

        @Override
        public void run() {

            DefaultTransactionDefinition def = new DefaultTransactionDefinition();
            def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
            TransactionStatus status = transactionManager.getTransaction(def);

            Thread thread = Thread.currentThread();
            // 设置当前线程执行name
            thread.setName(TaskConstants.TASK_TYPE_TASK + "-" + taskDataInfo.getExecutionId());
            ThreadManageInfo.threadHashMap.put(thread.getName(), thread);

            execute(taskDataInfo);

            //tomcat shutdown 不再执行以下代码
            if (!TaskListenerContainer.running) {
                if (isSuccess && !thread.isInterrupted()) {
                    transactionManager.commit(status);
                } else if (!isSuccess) {
                    transactionManager.rollback(status);
                } else if (thread.isInterrupted()) {
                    transactionManager.rollback(status);
                    updateStatus(taskDataInfo);
                }
                ThreadManageInfo.threadHashMap.remove(thread.getName());
            }

        }

        /**
         * 执行任务
         *
         * @param taskDataInfo 任务/任务组
         */
        private void execute(TaskDataInfo taskDataInfo) {
            if (taskDataInfo.getType().equals(TaskConstants.TASK_TYPE_TASK)) {
                executeTask(taskDataInfo);
            } else {
                for (int i = 0; i < taskDataInfo.getTaskDatas().size(); i++) {
                    if (!isSuccess || Thread.currentThread().isInterrupted()) {
                        return;
                    }
                    //设置当前执行的子任务序号
                    taskDataInfo.setCurrentExecution(i);
                    //设置当前执行的子任务
                    taskDataInfo.setCurrentTask(taskDataInfo.getTaskDatas().get(i));
                    executeTask(taskDataInfo);
                }
            }
        }

        /**
         * 执行任务 捕获执行异常
         *
         * @param taskDataInfo
         */
        private void executeTask(TaskDataInfo taskDataInfo) {
            String executeResultPath = "";
            try {
                executeListeners.forEach(executeListener -> {
                    executeListener.before(taskDataInfo);
                });
                executeResultPath = operate(taskDataInfo);
                if (!Thread.currentThread().isInterrupted()) {
                    executeListeners.forEach(executeListener -> {
                        executeListener.after(taskDataInfo);
                    });
                }

                //写入执行结果路径
                if (StringUtil.isNotEmpty(executeResultPath)){
                    TaskExecution taskExecution = new TaskExecution();
                    taskExecution.setExecuteResultPath(executeResultPath);
                    if (taskDataInfo.getType().equals(TaskConstants.TASK_TYPE_TASK)){
                        taskExecution.setExecutionId(taskDataInfo.getExecutionId());
                    }else {
                        taskExecution.setExecutionId(taskDataInfo.getCurrentTask().getExecutionId());
                    }
                    taskExecutionService.updateByPrimaryKeySelective(RequestHelper.newEmptyRequest(), taskExecution);
                }

            } catch (TaskInterruptException e) {
                //线程在非阻塞时被中断
            } catch (InterruptedException e) {
                //线程在阻塞时被中断，中断标志重置，这里需要恢复中断状态
                Thread.currentThread().interrupt();
            } catch (Exception e) {
                isSuccess = false;
                executeListeners.forEach(executeListener -> {
                    executeListener.doException(e, taskDataInfo);
                });
            } finally {
                //执行实现类重新排序，用户实现类先执行，系统默认实现最后执行
                executeListeners.sort((a, b) -> b.getOrder() - a.getOrder());
                executeListeners.forEach(executeListener -> {
                    executeListener.doFinally(taskDataInfo);
                });
            }
        }

        /**
         * 调用接口 执行任务
         *
         * @param taskDataInfo 数据传输类-任务/任务组
         */
        private String operate(TaskDataInfo taskDataInfo) throws Exception {
            String taskClass = "";
            if (taskDataInfo.getType().equals(TaskConstants.TASK_TYPE_TASK)) {
                taskClass = taskDataInfo.getTaskClass();
            } else {
                taskClass = taskDataInfo.getCurrentTask().getTaskClass();
            }

            ITask task = (ITask) Class.forName(taskClass.trim()).newInstance();
            applicationContext.getAutowireCapableBeanFactory().autowireBean(task);
            ExecutionInfo executionInfo = translateExecutionInfo(taskDataInfo);
            task.execute(executionInfo);
            return executionInfo.getExecuteResultPath();
        }

        /**
         * 封装执行接口参数
         *
         * @param taskDataInfo 数据传输类-任务/任务组
         * @return 任务执行记录
         */
        private ExecutionInfo translateExecutionInfo(TaskDataInfo taskDataInfo) {
            ExecutionInfo executionInfo = new ExecutionInfo();
            executionInfo.setUsername(taskDataInfo.getUsername());

            Map<String, Object> map = transformMap(taskDataInfo);
            executionInfo.setParam(map);

            TaskDetail taskDetail = new TaskDetail();
            taskDetail.setTaskId(taskDataInfo.getTaskId());
            taskDetail = taskDetailService.selectByPrimaryKey(RequestHelper.newEmptyRequest(), taskDetail);

            executionInfo.setTaskDetail(taskDetail);
            return executionInfo;
        }

        /**
         * 执行参数转为map
         *
         * @param taskDataInfo 数据传输类-任务/任务组
         * @return 执行参数
         */
        private Map<String, Object> transformMap(TaskDataInfo taskDataInfo) {
            Map<String, Object> parameterMap = new HashMap<>(16);
            List<ParameterInfo> parameterInfoList;
            if (taskDataInfo.getType().equals(TaskConstants.TASK_TYPE_TASK)) {
                parameterInfoList = taskDataInfo.getParam();
            } else {
                parameterInfoList = taskDataInfo.getCurrentTask().getParam();
            }
            for (ParameterInfo parameterInfo : parameterInfoList) {
                parameterMap.put(parameterInfo.getKey(), parameterInfo.getValue());
            }
            return parameterMap;
        }

        /**
         * 取消任务时修改执行记录状态
         *
         * @param taskDataInfo 数据传输类-任务/任务组
         */
        private void updateStatus(TaskDataInfo taskDataInfo) {

            // 当前执行的任务组或任务状态改为取消
            taskExecutionService.updateStatus(taskDataInfo.getExecutionId(), TaskConstants.EXECUTION_CANCEL);

            if (taskDataInfo.getType().equals(TaskConstants.TASK_TYPE_GROUP)) {
                // 成功的子任务状态改为回滚
                taskExecutionService.batchUpdateStatus(taskDataInfo.getExecutionId(), TaskConstants.EXECUTION_SUCCESS,
                        TaskConstants.EXECUTION_ROLLBACK);

                // 就绪的子任务状态改为未执行
                taskExecutionService.batchUpdateStatus(taskDataInfo.getExecutionId(), TaskConstants.EXECUTION_READY,
                        TaskConstants.EXECUTION_UN_EXECUTED);

                // 当前执行的子任务改为取消
                taskExecutionService.updateStatus(taskDataInfo.getCurrentTask().getExecutionId(),
                        TaskConstants.EXECUTION_CANCEL);
            }
        }
    }

    @Override
    public void contextInitialized(ApplicationContext applicationContext) {
        executeListeners = new ArrayList<>(applicationContext.getBeansOfType(IExecuteListener.class).values());
        executeListeners.sort(Comparator.comparingInt(IExecuteListener::getOrder));
    }


}
