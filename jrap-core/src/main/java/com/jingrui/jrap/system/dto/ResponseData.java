/*
 * #{copyright}#
 */
package com.jingrui.jrap.system.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.github.pagehelper.Page;

/**
 * 数据返回对象.
 *
 * @author njq.niu@jingrui.com
 */
public class ResponseData {

    // 返回状态编码
    @JsonInclude(Include.NON_NULL)
    private String code;

    // 返回信息
    @JsonInclude(Include.NON_NULL)
    private String message;

    //数据
    @JsonInclude(Include.NON_NULL)
    private List<?> rows;

    // 成功标识
    private boolean success = true;

    //总数
    @JsonInclude(Include.NON_NULL)
    private Long total;

    public ResponseData() {
    }

    public ResponseData(boolean success) {
        setSuccess(success);
    }

    public ResponseData(List<?> list) {
        this(true);
        setRows(list);
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public List<?> getRows() {
        return rows;
    }

    public Long getTotal() {
        return total;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setRows(List<?> rows) {
        this.rows = rows;
        if (rows instanceof Page) {
            setTotal(((Page<?>) rows).getTotal());
        } else {
            setTotal((long) rows.size());
        }
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setTotal(Long total) {
        this.total = total;
    }
}
