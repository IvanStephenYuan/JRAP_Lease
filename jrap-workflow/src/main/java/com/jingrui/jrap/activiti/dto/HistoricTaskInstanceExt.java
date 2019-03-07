package com.jingrui.jrap.activiti.dto;

import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.persistence.entity.HistoricTaskInstanceEntityImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ReflectionUtils;

/**
 * @author shengyang.zhou@jingrui.com
 */
public class HistoricTaskInstanceExt extends HistoricTaskInstanceEntityImpl {
    private String comment;

    private String assigneeName;

    private Logger logger = LoggerFactory.getLogger(getClass());

    public HistoricTaskInstanceExt() {

    }

    public HistoricTaskInstanceExt(HistoricTaskInstance response) {
        ReflectionUtils.doWithFields(response.getClass(), f -> {
            try {
                f.setAccessible(true);
                f.set(this, f.get(response));
            } catch (IllegalAccessException e) {
                logger.error(e.getMessage(), e);
            }
        });
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }


}
