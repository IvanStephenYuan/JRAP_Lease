/*
 * #{copyright}#
 */

package com.jingrui.jrap.system.service;

import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.core.ProxySelf;
import com.jingrui.jrap.core.annotation.StdWho;
import com.jingrui.jrap.system.dto.Language;

import java.util.List;

/**
 * @author shengyang.zhou@jingrui.com
 */
public interface ILanguageService extends IBaseService<Language>, ProxySelf<ILanguageService> {

    /**
     * 更新Language
     * @param request requestContext
     * @param list dto list
     * @return 结果列表
     */
    List<Language> submit(IRequest request, @StdWho List<Language> list);

    /**
     *删除Language
     * @param list dto list
     * @return 影响行数
     */
    int remove(List<Language> list);
}
