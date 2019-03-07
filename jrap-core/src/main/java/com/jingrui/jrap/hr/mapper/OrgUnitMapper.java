package com.jingrui.jrap.hr.mapper;

import com.jingrui.jrap.hr.dto.HrOrgUnit;
import com.jingrui.jrap.mybatis.common.Mapper;

import java.util.List;

/**
 * 部门组织Mapper.
 *
 * @author jialong.zuo@jingrui.com
 */
public interface OrgUnitMapper extends Mapper<HrOrgUnit> {
    /**
     * 条件查询部门组织.
     *
     * @param hrOrgUnit 部门组织
     * @return 部门组织列表
     */
    List<HrOrgUnit> selectUnit(HrOrgUnit hrOrgUnit);
}
