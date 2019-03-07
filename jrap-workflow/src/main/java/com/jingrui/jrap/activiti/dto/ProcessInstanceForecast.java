package com.jingrui.jrap.activiti.dto;

import com.jingrui.jrap.hr.dto.Employee;
import org.activiti.bpmn.model.GraphicInfo;

import java.util.List;
import java.util.Set;

/**
 * @author xiangyu.qi@jingrui.com on 2017/7/18.
 */
public class ProcessInstanceForecast {

    private boolean executed = false;

    private String taskId;

    private String taskName;

    private List<HistoricTaskInstanceResponseExt> history;

    private Set<Employee> forecast;

    private GraphicInfo graphicInfo;

    public boolean isExecuted() {
        return executed;
    }

    public void setExecuted(boolean executed) {
        this.executed = executed;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public List<HistoricTaskInstanceResponseExt> getHistory() {
        return history;
    }

    public void setHistory(List<HistoricTaskInstanceResponseExt> history) {
        this.history = history;
    }

    public Set<Employee> getForecast() {
        return forecast;
    }

    public void setForecast(Set<Employee> forecast) {
        this.forecast = forecast;
    }

    public GraphicInfo getGraphicInfo() {
        return graphicInfo;
    }

    public void setGraphicInfo(GraphicInfo graphicInfo) {
        this.graphicInfo = graphicInfo;
    }
}
