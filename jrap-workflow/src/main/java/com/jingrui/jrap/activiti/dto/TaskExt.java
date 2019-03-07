package com.jingrui.jrap.activiti.dto;


import org.activiti.engine.form.FormData;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.persistence.entity.TaskEntityImpl;
import org.activiti.engine.task.Attachment;
import org.activiti.engine.task.Task;
import org.springframework.util.ReflectionUtils;

import java.util.List;

/**
 * @author shengyang.zhou@jingrui.com
 */
public class TaskExt extends TaskEntityImpl {

    private List<Attachment> attachments;

    private FormData formData;
    private List<HistoricTaskInstance> historicTaskList;
    private String businessKey;

    public TaskExt(Task task) {
        ReflectionUtils.doWithFields(task.getClass(), f -> {
            try {
                f.setAccessible(true);
                f.set(this, f.get(task));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }

    public FormData getFormData() {
        return formData;
    }

    public void setFormData(FormData formData) {
        this.formData = formData;
    }

    public void setHistoricTaskList(List<HistoricTaskInstance> historicTaskList) {
        this.historicTaskList = historicTaskList;
    }

    public List<HistoricTaskInstance> getHistoricTaskList() {
        return historicTaskList;
    }

    public void setBusinessKey(String businessKey) {
        this.businessKey = businessKey;
    }

    public String getBusinessKey() {
        return businessKey;
    }
}
