package com.jingrui.jrap.activiti.demo;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 配在流程里面,由流程初始化并调用<br>
 * 当第二次执行,或在其他节点,流程再次使用该类时,不再初始化.
 * 
 * @author shengyang.zhou@jingrui.com
 */
public class DemoServiceTask implements JavaDelegate {

    private Logger logger = LoggerFactory.getLogger(DemoServiceTask.class);

    {
        logger.debug("service init");
        // 只会初始化一次，类似 servlet 的模式。
        // 不要设置共享变量！！！！
    }

    @Override
    public void execute(DelegateExecution delegateExecution) {
        logger.debug(":::" + delegateExecution.getCurrentFlowElement());
        delegateExecution.setVariable("task1Output", "value1");
    }

}
