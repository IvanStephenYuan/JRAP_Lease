package com.jingrui.jrap.activiti.custom;

/**
 * 实现该接口的类，将可以在 activiti 流程中调用
 *
 * @author shengyang.zhou@jingrui.com
 */
public interface IActivitiBean {

    /**
     * 默认返回 null，表示使用 bean 的默认名字<br>
     * 返回一个有效的值，则使用指定的名字
     *
     * @return bean 的名字
     */
    default String getBeanName() {
        return null;
    }
}
