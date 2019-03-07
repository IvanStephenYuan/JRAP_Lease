package com.jingrui.jrap.activiti.custom;

import com.jingrui.jrap.activiti.custom.process.CustomHistoryService;
import com.jingrui.jrap.activiti.custom.task.CustomTaskService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.TaskService;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.apache.commons.lang.StringUtils;

import java.io.InputStream;

/**
 * @author njq.niu@jingrui.com
 */
public class CustomSpringProcessEngineConfiguration extends SpringProcessEngineConfiguration {

    private static final String DBTYPE_MYSQL = "mysql";
    private static final String DBTYPE_POSTGRESQL = "postgresql";
    private static final String DBTYPE_POSTGRES = "postgres";

    protected TaskService customTaskService = new CustomTaskService(this);
    protected HistoryService customHistoryService = new CustomHistoryService(this);

    public InputStream getMyBatisXmlConfigurationStream() {
        return getResourceAsStream("com/jingrui/jrap/activiti/custom/mappings.xml");
    }

    public void initServices() {
        initService(repositoryService);
        initService(runtimeService);
//        initService(historyService);
        initService(identityService);
//        initService(taskService);
        initService(formService);
        initService(managementService);
        initService(dynamicBpmnService);
//        super.initServices();
        initService(customTaskService);
        initService(customHistoryService);
    }

    public TaskService getTaskService() {
        return customTaskService;
    }

    public HistoryService getHistoryService() {
        return customHistoryService;
    }

    public ProcessEngineConfiguration setDatabaseType(String databaseType) {
        if (StringUtils.endsWithIgnoreCase(DBTYPE_POSTGRESQL, databaseType)){
            databaseType = DBTYPE_POSTGRES;
        }
        this.databaseType = databaseType;
        return this;
    }
}
