/*
 * #{copyright}#
 */

package com.jingrui.jrap.core.impl;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jingrui.jrap.core.IRequest;

import java.util.*;

/**
 * 默认的 IRequest 实现.
 * 
 * @author shengyang.zhou@jingrui.com
 */
public class ServiceRequest implements IRequest {



    private static final String ATTR_USER_ID = "_userId";

    private static final String ATTR_ROLE_ID = "_roleId";

    private static final String ATTR_COMPANY_ID = "_companyId";

    private static final String ATTR_LOCALE = "_locale";

    private static final long serialVersionUID = 3699668645012922404L;

    private Long userId = -1L;
    private Long roleId = -1L;
    private Long[] roleIds = {};
    private Long companyId = -1L;
    private String locale = Locale.getDefault().toString();
    private String employeeCode;
    private Long employeeId;
    private String userName;
    /******************** 用户的扩展字段 ********************/
    private Long positionId; // 岗位id
    private String positionCode; // 岗位代码
    private Long parentPositionId; // 上级岗位id
    private Long unitId; // 机构id
    private String unitCode; // 机构代码
    private String unitCategory; // 机构类别
    private String unitType; // 机构类型
    private Long parentId; // 上级机构id
    private String unitName; // 机构名称
    private String homePath; // 首页路径

    @Override
    public Long getPositionId() {
        return positionId;
    }

    @Override
    public void setPositionId(Long positionId) {
        this.positionId = positionId;
    }

    @Override
    public String getPositionCode() {
        return positionCode;
    }

    @Override
    public void setPositionCode(String positionCode) {
        this.positionCode = positionCode;
    }

    @Override
    public Long getParentPositionId() {
        return parentPositionId;
    }

    @Override
    public void setParentPositionId(Long parentPositionId) {
        this.parentPositionId = parentPositionId;
    }

    @Override
    public Long getUnitId() {
        return unitId;
    }

    @Override
    public void setUnitId(Long unitId) {
        this.unitId = unitId;
    }

    @Override
    public String getUnitCode() {
        return unitCode;
    }

    @Override
    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    @Override
    public String getUnitCategory() {
        return unitCategory;
    }

    @Override
    public void setUnitCategory(String unitCategory) {
        this.unitCategory = unitCategory;
    }

    @Override
    public String getUnitType() {
        return unitType;
    }

    @Override
    public void setUnitType(String unitType) {
        this.unitType = unitType;
    }

    @Override
    public Long getParentId() {
        return parentId;
    }

    @Override
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    @Override
    public String getUnitName() {
        return unitName;
    }

    @Override
    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    @Override
    public String getHomePath() {
        return homePath;
    }

    @Override
    public void setHomePath(String homePath) {
        this.homePath = homePath;
    }

    @JsonIgnore
    private Map<String, Object> attributeMap = new HashMap<>();

    @Override
    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String getUserName() {
        return userName;
    }

    @Override
    public Long getUserId() {
        return userId;
    }

    @Override
    public void setUserId(Long userId) {
        this.userId = userId;
        setAttribute(ATTR_USER_ID, userId);
    }

    @Override
    public String getEmployeeCode() {
        return employeeCode;
    }

    @Override
    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }

    @Override
    public Long getEmployeeId(){
        return employeeId;
    }

    @Override
    public void setEmployeeId(Long employeeId){
        this.employeeId = employeeId;
    }


    @Override
    public String getLocale() {
        return locale;
    }

    @Override
    public void setLocale(String locale) {
        this.locale = locale;
        setAttribute(ATTR_LOCALE, locale);
    }

    @Override
    public Long getRoleId() {
        return roleId;
    }

    @Override
    public Long[] getAllRoleId() {
        return roleIds;
    }

    @Override
    public void setAllRoleId(Long[] roleIds) {
        this.roleIds = roleIds;
    }

    @Override
    public void setRoleId(Long roleId) {
        this.roleId = roleId;
        setAttribute(ATTR_ROLE_ID, roleId);
    }

    @Override
    public Long getCompanyId() {
        return companyId;
    }

    @Override
    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
        setAttribute(ATTR_COMPANY_ID, companyId);
    }

    @Override
    @SuppressWarnings("unchecked")
    @JsonAnyGetter
    public <T> T getAttribute(String name) {
        return (T) attributeMap.get(name);
    }

    @Override
    @JsonAnySetter
    public void setAttribute(String name, Object value) {
        attributeMap.put(name, value);
    }

    @Override
    @JsonIgnore
    public Map<String, Object> getAttributeMap() {
        return attributeMap;
    }

    @Override
    @JsonIgnore
    public Set<String> getAttributeNames() {
        return attributeMap.keySet();
    }
}
