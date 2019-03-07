package com.jingrui.jrap.activiti.dto;

/**
 * @author shengyang.zhou@jingrui.com
 */
public class ActivitiNode {
    private String nodeId;

    private String name;

    private String type;

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
