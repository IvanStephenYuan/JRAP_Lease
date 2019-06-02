package com.jingrui.jrap.account.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.jingrui.jrap.hr.dto.Employee;
import com.jingrui.jrap.mybatis.annotation.Condition;
import com.jingrui.jrap.mybatis.common.query.*;
import com.jingrui.jrap.mybatis.common.query.JoinColumn;
import com.jingrui.jrap.mybatis.common.query.JoinTable;
import com.jingrui.jrap.system.dto.BaseDTO;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.persistence.criteria.JoinType;
import java.util.Date;
import java.util.List;

/**
 * 用户DTO.
 *
 * @author njq.niu@jingrui.com
 * @date 2016/6/9
 */
@Table(name = "sys_user")
public class User extends BaseDTO {
    public static final String FIELD_USER_ID = "userId";
    public static final String FIELD_USER_NAME = "userName";
    public static final String FIELD_PASSWORD_ENCRYPTED = "passwordEncrypted";
    public static final String FIELD_PASSWORD = "password";
    public static final String FIELD_USER_TYPE = "userType";
    public static final String FIELD_EMAIL = "email";
    public static final String FIELD_PHONE = "phone";
    public static final String FIELD_START_ACTIVE_DATE = "startActiveDate";
    public static final String FIELD_END_ACTIVE_DATE = "endActiveDate";
    public static final String FIELD_STATUS = "status";
    public static final String FIELD_LAST_LOGIN_DATE = "lastLoginDate";
    public static final String FIELD_LAST_PASSWORD_UPDATE_DATE = "lastPasswordUpdateDate";
    public static final String FIELD_FIRST_LOGIN = "firstLogin";
    public static final String FIELD_DESCRIPTION = "description";
    public static final String FIELD_CUSTOMER_ID = "customerId";
    public static final String FIELD_SUPPLIER_ID = "supplierId";
    public static final String FIELD_EMPLOYEE_ID = "employeeId";
    public static final String FIELD_EMPLOYEE_CODE = "employeeCode";
    public static final String FIELD_EMPLOYEE_NAME = "employeeName";

    public static final String LOGIN_CHANGE_INDEX = "login_change_index";
    public static final String FIRST_LOGIN_STATUS = "Y";
    public static final String NOT_FIRST_LOGIN_STATUS = "N";

    public static final String STATUS_ACTV = "ACTV";
    public static final String STATUS_EXPR = "EXPR";
    public static final String STATUS_LOCK = "LOCK";


    @Id
    @GeneratedValue(generator = GENERATOR_TYPE)
    private Long userId;

    @NotEmpty
    @Length(max = 150)
    @JsonInclude(Include.NON_NULL)
    @Where
    @OrderBy("ASC")
    private String userName;

    @Condition(exclude = true)
    @Length(max = 80)
    private String passwordEncrypted;

    @Transient
    @JsonInclude(Include.NON_NULL)
    private String password;

    @Column
    @JsonInclude(Include.NON_NULL)
    private String userType;

    @Column
    @JsonInclude(Include.NON_NULL)
    @NotEmpty
    @Email
    @Length(max = 150)
    private String email;

    @Column
    @JsonInclude(Include.NON_NULL)
    @NotEmpty
    @Length(max = 40)
    private String phone;

    @Column
    @Condition(operator = ">=")
    @JsonInclude(Include.NON_NULL)
    private Date startActiveDate;

    @Column
    @Condition(operator = "<=")
    @JsonInclude(Include.NON_NULL)
    private Date endActiveDate;

    // 状态
    @JsonInclude(Include.NON_NULL)
    @Column
    @Where
    private String status;

    @Column
    @JsonInclude(Include.NON_NULL)
    private Date lastLoginDate;

    @Column
    @JsonInclude(Include.NON_NULL)
    private Date lastPasswordUpdateDate;

    @Column
    @JsonInclude(Include.NON_NULL)
    private String firstLogin;

    @Column
    @JsonInclude(Include.NON_NULL)
    @Length(max = 255)
    private String description;

    @Column
    @JsonInclude(Include.NON_NULL)
    private Long customerId;

    @Column
    @JsonInclude(Include.NON_NULL)
    private Long supplierId;

    @Column
    @JsonInclude(Include.NON_NULL)
    @JoinTable(name = "employeeJoin", target = Employee.class, type = JoinType.LEFT, on = {@JoinOn(joinField = Employee.FIELD_EMPLOYEE_ID)})
    private Long employeeId;

    @Transient
    @JsonInclude(Include.NON_NULL)
    @JoinColumn(joinName = "employeeJoin", field = Employee.FIELD_EMPLOYEE_CODE)
    @Where
    private String employeeCode;


    @Transient
    @JsonInclude(Include.NON_NULL)
    @JoinColumn(joinName = "employeeJoin", field = Employee.FIELD_NAME)
    @Where(comparison = Comparison.LIKE)
    private String employeeName;


    @Transient
    private List<String> roleCode;

    @Transient
    private Long roleId;

    @Transient
    private String roleName;
    @Transient
    private String unitName;
    @Transient
    private String orderNumber;
    @Transient
    private String unitRank;
    @Transient
    private String unitEmpTotal;
    @Transient
    private String groupRank;
    @Transient
    private String groupEmpTotal;

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getUnitRank() {
        return unitRank;
    }

    public void setUnitRank(String unitRank) {
        this.unitRank = unitRank;
    }

    public String getUnitEmpTotal() {
        return unitEmpTotal;
    }

    public void setUnitEmpTotal(String unitEmpTotal) {
        this.unitEmpTotal = unitEmpTotal;
    }

    public String getGroupRank() {
        return groupRank;
    }

    public void setGroupRank(String groupRank) {
        this.groupRank = groupRank;
    }

    public String getGroupEmpTotal() {
        return groupEmpTotal;
    }

    public void setGroupEmpTotal(String groupEmpTotal) {
        this.groupEmpTotal = groupEmpTotal;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getStatus() {
        return status;
    }

    public Long getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public void setEmail(String email) {
        this.email = StringUtils.trim(email);
    }

    public void setPhone(String phone) {
        this.phone = StringUtils.trim(phone);
    }

    public void setStatus(String status) {
        this.status = StringUtils.trim(status);
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setUserName(String userName) {
        this.userName = StringUtils.trim(userName);
    }

    public String getPasswordEncrypted() {
        return passwordEncrypted;
    }

    public void setPasswordEncrypted(String passwordEncrypted) {
        this.passwordEncrypted = passwordEncrypted;
    }

    public Date getStartActiveDate() {
        return startActiveDate;
    }

    public void setStartActiveDate(Date startActiveDate) {
        this.startActiveDate = startActiveDate;
    }

    public Date getEndActiveDate() {
        return endActiveDate;
    }

    public void setEndActiveDate(Date endActiveDate) {
        this.endActiveDate = endActiveDate;
    }

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    @JsonProperty
    public void setPassword(String password) {
        this.password = password;
    }

    public Date getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public Date getLastPasswordUpdateDate() {
        return lastPasswordUpdateDate;
    }

    public void setLastPasswordUpdateDate(Date lastPasswordUpdateDate) {
        this.lastPasswordUpdateDate = lastPasswordUpdateDate;
    }

    public String getFirstLogin() {
        return firstLogin;
    }

    public void setFirstLogin(String firstLogin) {
        this.firstLogin = firstLogin;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeeCode() {
        return employeeCode;
    }

    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(List<String> roleCode) {
        this.roleCode = roleCode;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
}