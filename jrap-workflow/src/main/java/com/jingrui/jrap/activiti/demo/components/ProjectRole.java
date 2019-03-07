package com.jingrui.jrap.activiti.demo.components;

import com.jingrui.jrap.activiti.custom.IActivitiBean;
import org.activiti.engine.delegate.DelegateExecution;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class ProjectRole implements IActivitiBean {


    public String role(String roleCode) {
        System.out.println(roleCode);
        return "Jessen";
    }

    public String c(DelegateExecution execution, String roleCode) {
        System.out.println(roleCode);
        return "Jessen";
    }

    public String a(DelegateExecution execution, String roleCode) {
        System.out.println(roleCode);
        return "Jessen";
    }

    public String e(DelegateExecution execution, String roleCode) {
        System.out.println(roleCode);
        return "Jessen";
    }

    public String d(DelegateExecution execution, String roleCode) {
        System.out.println(roleCode);
        return "Jessen";
    }


    /**
     * 获取会签的人
     *
     * @param execution
     * @return
     */
    public List<String> getCollection(DelegateExecution execution) {
        return Arrays.asList("rodgers", "jessen", "eric");
    }

}
