/*
 * #{copyright}#
 */

package com.jingrui.jrap.system.mapper;

import java.util.List;
import java.util.Map;

import com.jingrui.jrap.system.dto.MultiLanguageField;

/**
 * 
 * @author njq.niu@jingrui.com
 *
 *         2016年3月30日
 */
public interface MultiLanguageMapper {

    List<MultiLanguageField> select(Map<String, String> map);
}