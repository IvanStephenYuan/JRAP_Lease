package com.jingrui.jrap.mail.mapper;

import com.jingrui.jrap.mail.dto.MessageEmailConfig;
import com.jingrui.jrap.mybatis.common.Mapper;

import java.util.List;


/**
 * 邮件配置Mapper.
 *
 * @author qiang.zeng@jingrui.com
 */
public interface MessageEmailConfigMapper extends Mapper<MessageEmailConfig> {
    /**
     * 条件查询邮件配置.
     *
     * @param record 邮件配置
     * @return 邮件配置列表
     */
    List<MessageEmailConfig> selectMessageEmailConfigs(MessageEmailConfig record);
}