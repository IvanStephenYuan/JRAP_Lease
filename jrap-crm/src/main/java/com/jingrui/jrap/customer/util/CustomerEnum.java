/*
 * *
 *  @file com.maddyhome.idea.copyright.pattern.JavaCopyrightVariablesProvider$1@69577d6e$
 *  @CopyRight (C) 2018 ZheJiangJingRui Co. Ltd.
 *  @brief JingRui Application Platform
 *  @author $name$
 *  @email yulong.yuan@jr-info.cn
 *  @date $date$
 * /
 */

package com.jingrui.jrap.customer.util;

public enum CustomerEnum {
    ERROR001("SDK100", "image size error", "图片大小超限"),
    ERROR002("SDK101", "image length error", "图片边长不符合要求"),
    ERROR003("SDK102", "read image file error", "读取图片文件错误"),
    ERROR004("SDK108", "connection or read data time out", "连接超时或读取数据超时"),
    ERROR005("SDK109", "unsupported image format", "不支持的图片格式"),
    ERROR006("4", "Open api request limit reached", "集群超限额"),
    ERROR007("14", "IAM Certification failed", "IAM鉴权失败，建议用户参照文档自查生成sign的方式是否正确，或换用控制台中ak sk的方式调用"),
    ERROR008("17", "Open api daily request limit reached", "每天流量超限额"),
    ERROR009("18", "Open api qps request limit reached", "QPS超限额"),
    ERROR0010("19", "Open api total request limit reached", "请求总量超限额"),
    ERROR0012("100", "Invalid parameter", "无效参数"),
    ERROR0013("110", "Access token invalid or no longer valid", "Access Token失效"),
    ERROR0014("111", "Access token expired", "Access token过期"),
    ERROR0015("282000", "internal error", "服务器内部错误，如果您使用的是高精度接口，报这个错误码的原因可能是您上传的图片中文字过多，识别超时导致的，建议您对图片进行切割后再识别，其他情况请再次请求， 如果持续出现此类错误，请通过QQ群（631977213）或工单联系技术支持团队。"),
    ERROR0016("216100", "invalid param", "请求中包含非法参数，请检查后重新尝试"),
    ERROR0017("216101", "not enough param", "缺少必须的参数，请检查参数是否有遗漏"),
    ERROR0018("216102", "service not support", "请求了不支持的服务，请检查调用的url"),
    ERROR0019("216103", "param too long", "请求中某些参数过长，请检查后重新尝试"),
    ERROR0020("216110", "appid not exist", "appid不存在，请重新核对信息是否为后台应用列表中的appid"),
    ERROR0021("216200", "empty image", "图片为空，请检查后重新尝试"),
    ERROR0022("216201", "image format error", "上传的图片格式错误，现阶段我们支持的图片格式为：PNG、JPG、JPEG、BMP，请进行转码或更换图片"),
    ERROR0023("216202", "image size error", "上传的图片大小错误，现阶段我们支持的图片大小为：base64编码后小于4M，分辨率不高于4096*4096，请重新上传图片"),
    ERROR0024("216630", "recognize error", "识别错误，请再次请求，如果持续出现此类错误，请通过QQ群（631977213）或工单联系技术支持团队。"),
    ERROR0025("216631", "recognize bank card error", "识别银行卡错误，出现此问题的原因一般为：您上传的图片非银行卡正面，上传了异形卡的图片或上传的银行卡正品图片不完整"),
    ERROR0026("216633", "recognize idcard error", "识别身份证错误，出现此问题的原因一般为：您上传了非身份证图片或您上传的身份证图片不完整"),
    ERROR0027("216634", "detect error", "检测错误，请再次请求，如果持续出现此类错误，请通过QQ群（631977213）或工单联系技术支持团队。"),
    ERROR0028("282003", "missing parameters: {参数名}", "请求参数缺失"),
    ERROR0029("282005", "batch processing error", "处理批量任务时发生部分或全部错误，请根据具体错误码排查"),
    ERROR0030("282006", "batch task limit reached", "批量任务处理数量超出限制，请将任务数量减少到10或10以下"),
    ERROR0031("282110", "urls not exit", "URL参数不存在，请核对URL后再次提交"),
    ERROR0032("282111", "url format illegal", "URL格式非法，请检查url格式是否符合相应接口的入参要求"),
    ERROR0033("282112", "url download timeout", "url下载超时，请检查url对应的图床/图片无法下载或链路状况不好，您可以重新尝试一下，如果多次尝试后仍不行，建议更换图片地址"),
    ERROR0034("282113", "url response invalid", "URL返回无效参数"),
    ERROR0035("282114", "url size error", "URL长度超过1024字节或为0"),
    ERROR0036("282808", "request id: xxxxx not exist", "request id xxxxx 不存在"),
    ERROR0037("282809", "result type error", "返回结果请求错误（不属于excel或json）"),
    ERROR0038("282810", "image recognize error", "图像识别错误");


    private String code;
    private String name;
    private String descripton;

    private CustomerEnum(String code, String name, String descripton) {
        this.code = code;
        this.name = name;
        this.descripton = descripton;
    }

    public static String getErrorDescription(String code) {
        for (CustomerEnum c : CustomerEnum.values()) {
            if (code.equalsIgnoreCase(c.getCode())) {
                return c.getDescripton();
            }
        }

        return "未知错误";
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescripton() {
        return descripton;
    }

    public void setDescripton(String descripton) {
        this.descripton = descripton;
    }
}

