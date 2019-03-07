package com.jingrui.jrap.activiti.exception.mapper;

import com.jingrui.jrap.activiti.exception.dto.ActiviException;
import com.jingrui.jrap.mybatis.common.Mapper;

import java.util.List;

public interface ActiviExceptionMapper extends Mapper<ActiviException> {

    List<ActiviException> selectAllException(ActiviException exception);

}