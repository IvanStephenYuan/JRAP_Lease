package com.jingrui.jrap.hr.dto;

import com.jingrui.jrap.mybatis.common.query.Comparison;
import com.jingrui.jrap.mybatis.common.query.JoinCode;
import com.jingrui.jrap.mybatis.common.query.Where;
import com.jingrui.jrap.system.dto.BaseDTO;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.core.annotation.Order;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * 员工对象.
 *
 * @author yuliao.chen@jingrui.com
 */
/*@ExtensionAttribute(disable = true)*/
@Table(name = "hr_employee")
public class Employee extends BaseDTO {

    public static final String FIELD_EMPLOYEE_ID = "employeeId";
    public static final String FIELD_EMPLOYEE_CODE = "employeeCode";
    public static final String FIELD_NAME = "name";
    public static final String FIELD_BORN_DATE = "bornDate";
    public static final String FIELD_EMAIL = "email";
    public static final String FIELD_MOBIL = "mobil";
    public static final String FIELD_JOIN_DATE = "joinDate";
    public static final String FIELD_GENDER = "gender";
    public static final String FIELD_CERTIFICATE_ID = "certificateId";
    public static final String FIELD_STATUS = "status";
    public static final String FIELD_ENABLED_FLAG = "enabledFlag";
    public static final String FIELD_UNIT_ID = "unitId";
    public static final String FIELD_POSITION_ID = "positionId";
    public static final String FIELD_UNIT_NAME = "unitName";
    public static final String FIELD_POSITION_NAME = "positionName";
    public static final String FIELD_CERTIFICATE_TYPE = "certificateType";
    public static final String FIELD_EFFECTIVE_START_DATE = "effectiveStartDate";
    public static final String FIELD_EFFECTIVE_END_DATE = "effectiveEndDate";

    @Id
    @Column
    @Where
    @GeneratedValue(generator = GENERATOR_TYPE)
    private Long employeeId;

    /**
     * 员工编码
     */
    @NotEmpty
    @Column
    @Where
    @Length(max = 30)
    @OrderBy("ASC")
    private String employeeCode;

    /**
     * 员工姓名
     */
    @NotEmpty
    @Column
    @Where(comparison = Comparison.LIKE)
    @Length(max = 50)
    @OrderBy
    private String name;

    /**
     * 出生日期
     */
    @Column
    @OrderBy
    private Date bornDate;

    /**
     * 电子邮件
     */
    @Column
    @Email
    @Length(max = 50)
    @OrderBy
    private String email;

    /**
     * 移动电话
     */
    @Column
    @Length(max = 50)
    @OrderBy
    private String mobil;

    /**
     * 入职日期
     */
    @Column
    @OrderBy
    private Date joinDate;

    /**
     * 性别
     */
    @Column
    @Length(max = 1)
    @OrderBy
    private String gender;

    /**
     * ID
     */
    @NotEmpty
    @Column
    @Length(max = 100)
    private String certificateId;

    /**
     * 状态
     */
    @NotEmpty
    @Column
    @Length(max = 50)
    @OrderBy
    private String status;

    @Transient
    @JoinCode(code = "HR.EMPLOYEE_STATUS", joinKey = Employee.FIELD_STATUS)
    private String statusName;

    /**
     * 启用状态
     */
    @NotEmpty
    @Column
    @Length(max = 1)
    @OrderBy
    private String enabledFlag;

    /**
     * 部门Id
     */
    @Transient
    private Long unitId;
    /**
     * 岗位id
     */
    @Transient
    private Long positionId;

    /**
     * 部门名称
     */
    @Transient
    private String unitName;

    /**
     * 岗位名称
     */
    @Transient
    private String positionName;
    /**
     * 证件类型
     */
    @Column
    @Length(max = 240)
    private String certificateType;
    /**
     * 有效日期从
     */
    @Column
    private Date effectiveStartDate;
    /**
     * 有效日期至
     */
    @Column
    private Date effectiveEndDate;

    @Transient
    private List<Long> idList;

    public List<Long> getIdList() {
        return idList;
    }

    public void setIdList(List<Long> idList) {
        this.idList = idList;
    }

    public Long getUnitId() {
        return unitId;
    }

    public void setUnitId(Long unitId) {
        this.unitId = unitId;
    }

    public Long getPositionId() {
        return positionId;
    }

    public void setPositionId(Long positionId) {
        this.positionId = positionId;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeCode() {
        return employeeCode;
    }

    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = StringUtils.trim(employeeCode);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBornDate() {
        return bornDate;
    }

    public void setBornDate(Date bornDate) {
        this.bornDate = bornDate;
    }


    public String getMobil() {
        return mobil;
    }

    public void setMobil(String mobil) {
        this.mobil = mobil;
    }

    public Date getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(Date joinDate) {
        this.joinDate = joinDate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCertificateId() {
        return certificateId;
    }

    public void setCertificateId(String certificateId) {
        this.certificateId = StringUtils.trim(certificateId);
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEnabledFlag() {
        return enabledFlag;
    }

    public void setEnabledFlag(String enabledFlag) {
        this.enabledFlag = enabledFlag;
    }

    public String getCertificateType() {
        return certificateType;
    }

    public void setCertificateType(String certificateType) {
        this.certificateType = certificateType;
    }

    public Date getEffectiveStartDate() {
        return effectiveStartDate;
    }

    public void setEffectiveStartDate(Date effectiveStartDate) {
        this.effectiveStartDate = effectiveStartDate;
    }

    public Date getEffectiveEndDate() {
        return effectiveEndDate;
    }

    public void setEffectiveEndDate(Date effectiveEndDate) {
        this.effectiveEndDate = effectiveEndDate;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }
}
