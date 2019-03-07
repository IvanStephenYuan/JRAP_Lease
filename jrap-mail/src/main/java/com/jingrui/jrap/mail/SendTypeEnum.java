package com.jingrui.jrap.mail;

/**
 * 发送类型枚举.
 *
 * @author qiang.zeng@jingrui.com
 */
public enum SendTypeEnum {
    //直接发送
    DIRECT("DIRECT"),
    //定时任务
    JOB("JOB");

    private String code;

    SendTypeEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }
}