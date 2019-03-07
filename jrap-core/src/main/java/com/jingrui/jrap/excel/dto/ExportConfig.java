package com.jingrui.jrap.excel.dto;

import java.util.List;

/**
 * Created by jialong.zuo@jingrui.com on 2016/11/30.
 */
public class ExportConfig<T, E> {
    private String fileName;

    private List<E> columnsInfo;

    private T param = null;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public List<E> getColumnsInfo() {
        return columnsInfo;
    }

    public void setColumnsInfo(List<E> columnsInfo) {
        this.columnsInfo = columnsInfo;
    }

    public T getParam() {
        return param;
    }

    public void setParam(T param) {
        this.param = param;
    }

}
