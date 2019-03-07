package com.jingrui.jrap.activiti.dto;

import org.activiti.engine.repository.ProcessDefinition;

/**
 * @author shengyang.zhou@jingrui.com
 */
public class ProcessDefinitionDto {

    private String category;
    private String deploymentId;
    private String description;
    private String id;
    private String key;
    private String name;

    private int version;

    public ProcessDefinitionDto() {

    }

    public ProcessDefinitionDto(ProcessDefinition definition) {
        this.category = definition.getCategory();
        this.deploymentId = definition.getDeploymentId();
        this.description = definition.getDescription();
        this.id = definition.getId();
        this.key = definition.getKey();
        this.name = definition.getName();
        this.version = definition.getVersion();
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDeploymentId() {
        return deploymentId;
    }

    public void setDeploymentId(String deploymentId) {
        this.deploymentId = deploymentId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
