package com.jingrui.jrap.activiti.mapper;

import com.jingrui.jrap.activiti.dto.HiIdentitylink;
import com.jingrui.jrap.mybatis.common.Mapper;

import java.util.Map;

public interface HiIdentitylinkMapper extends Mapper<HiIdentitylink> {

    int updateReadFlag(HiIdentitylink hiIdentitylink);

    int insertCarbonCopy(Map<String, String> params);
}