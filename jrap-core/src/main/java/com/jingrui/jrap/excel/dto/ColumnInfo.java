package com.jingrui.jrap.excel.dto;

/**
 * Created by jialong.zuo@jingrui.com on 2016/11/30.
 */
public class ColumnInfo {

    private String title;

    private String name;

    private String type = "string";

    private int width = 80;

    private String align;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public String getAlign() {
        return align;
    }

    public void setAlign(String align) {
        this.align = align;
    }
}
