/*
 * #{copyright}#
 */
package com.jingrui.jrap.attachment.mapper;

import java.util.Map;

import com.jingrui.jrap.attachment.dto.Attachment;
import com.jingrui.jrap.mybatis.common.Mapper;

/**
 * Created by xiaohua on 16/2/1.
 * @author hua.xiao@jingrui.com
 */
public interface  AttachmentMapper extends Mapper<Attachment> {


    /**
     * 根据Attachment对象查找单个Attachment.
     * 
     * @param attachment Attachment对象
     * @return  Attachment Attachment对象
     */
    Attachment selectAttachment(Attachment attachment);
    

    /**
     * 更新来源主键.
     * 
     * @param param 参数
     */
    int upgradeSourceKey(Map<String, Object> param);

}
