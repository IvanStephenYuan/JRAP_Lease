package com.jingrui.jrap.activiti.custom;

import org.activiti.bpmn.model.GraphicInfo;

import java.awt.*;

/**
 * @author njq.niu@jingrui.com
 */
public class NodeGraphicInfo extends GraphicInfo {

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    private Color color;

}
