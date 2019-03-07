package com.jingrui.jrap.task.service;

/**
 * @author peng.jiang@jingrui.com
 * @date 2017/12/1
 **/
public interface ITaskCallback<T> {
    /**
     * 关键代码执行.
     *
     * @return 返回值
     * @throws Exception 执行异常
     */
    T doInTask() throws Exception;
}
