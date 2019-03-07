package com.jingrui.jrap.activiti.custom.process;

import org.activiti.engine.history.HistoricProcessInstanceQuery;
import org.activiti.engine.history.HistoricTaskInstanceQuery;
import org.activiti.engine.impl.HistoricTaskInstanceQueryImpl;
import org.activiti.engine.impl.HistoryServiceImpl;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;

/**
 * @author njq.niu@jingrui.com
 */
public class CustomHistoryService extends HistoryServiceImpl {

    public CustomHistoryService(ProcessEngineConfigurationImpl processEngineConfiguration) {
        super(processEngineConfiguration);
    }

    public HistoricProcessInstanceQuery createHistoricProcessInstanceQuery() {
        return new CustomHistoricProcessInstanceQuery(commandExecutor);
    }


    public HistoricTaskInstanceQuery createHistoricTaskInstanceQuery() {
        return new HistoricTaskInstanceQueryImpl(commandExecutor, processEngineConfiguration.getDatabaseType());
    }
}
