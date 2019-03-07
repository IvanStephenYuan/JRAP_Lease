package com.jingrui.jrap.system.service;

import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.core.ProxySelf;
import com.jingrui.jrap.system.dto.Prompt;

import java.util.List;

/**
 * 多语言描述.
 *
 * @author shengyang.zhou@jingrui.com
 * @date 2016/6/9.
 */
public interface IPromptService extends IBaseService<Prompt>, ProxySelf<IPromptService> {
    /**
     * 更新描述维护.
     *
     * @param request IRequest
     * @param list    描述维护列表
     * @return 描述维护列表
     */
    List<Prompt> submit(IRequest request, List<Prompt> list);

    /**
     * 根据语言和编码获取描述.
     * 首先从redis缓存取 如果没有 再尝试从数据库去读.
     *
     * @param locale     语言
     * @param promptCode 编码
     * @return 描述
     */
    String getPromptDescription(String locale, String promptCode);
}
