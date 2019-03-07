package com.jingrui.jrap.activiti.demo.components;

import com.jingrui.jrap.activiti.custom.IActivitiBean;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * 实现接口JavaDelegate ,IActivitiBean. <br>
 * 定义在 spring context 中,可以 delegateExpression 中用
 *
 * @author shengyang.zhou@jingrui.com
 */
@Component // 自动扫描注册(components)
public class DemoServiceTaskDelegate implements JavaDelegate, IActivitiBean {

    private Logger logger = LoggerFactory.getLogger(DemoServiceTaskDelegate.class);

    @Autowired
    private ApplicationContext applicationContext;// 可以注入 bean

    @Override
    public void execute(DelegateExecution execution) {
        logger.debug("get variable :task1Output=" + execution.getVariable("task1Output"));
        logger.debug(":::" + execution.getCurrentFlowElement());
        execution.setVariable("task2Output", "value2");
    }

    /**
     * 该方法随便写的，和接口没关系
     *
     * @param delegateExecution
     */
    public void method1(DelegateExecution delegateExecution, String inputValue) {
        logger.debug("method1 called.");
        logger.debug("当前节点：" + delegateExecution.getCurrentFlowElement());
        logger.debug("参数值：" + inputValue);
    }
}
