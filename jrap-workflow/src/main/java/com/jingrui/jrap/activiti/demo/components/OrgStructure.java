/*
 * Copyright ZheJiang JingRui Co.,Ltd.
 */

package com.jingrui.jrap.activiti.demo.components;

import com.jingrui.jrap.activiti.custom.IActivitiBean;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * @author shengyang.zhou@jingrui.com
 */
@Component
public class OrgStructure implements IActivitiBean {
    public String getDirector(String starter) {
        return "Jessen";
    }

    public String getDeptLeader(String starter) {
        return "Tony";
    }

    public List<String> getPers() {
        return Arrays.asList("ADMIN", "Tony");
    }
}
