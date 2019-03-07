package com.jingrui.jrap.report.dto;

import com.jingrui.jrap.mybatis.annotation.Condition;
import com.jingrui.jrap.mybatis.annotation.ExtensionAttribute;
import com.jingrui.jrap.system.dto.BaseDTO;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 报表文件.
 *
 * @author qiang.zeng
 * @date 2017/9/21
 */
@ExtensionAttribute(disable = true)
@Table(name = "sys_report_file")
public class ReportFiles extends BaseDTO {

    public static final String FIELD_FILE_ID = "fileId";
    public static final String FIELD_NAME = "name";
    public static final String FIELD_CONTENT = "content";
    public static final String FIELD_PARAMS = "params";
    public static final String FIELD_SOURCE_TYPE = "sourceType";

    @Id
    @GeneratedValue
    private Long fileId;

    @Column
    @NotEmpty
    @Condition(operator = LIKE)
    @Length(max = 50)
    private String name;

    @Column
    private String content;

    @Column
    @Length(max = 255)
    private String params;

    @Column
    @Length(max = 20)
    private String sourceType;

    public Long getFileId() {
        return fileId;
    }

    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }
}