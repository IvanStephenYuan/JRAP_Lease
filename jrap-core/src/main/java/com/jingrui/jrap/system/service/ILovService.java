package com.jingrui.jrap.system.service;

import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.core.ProxySelf;
import com.jingrui.jrap.system.dto.Lov;
import com.jingrui.jrap.system.dto.LovItem;

import java.util.List;
import java.util.Locale;

/**
 * 通用lov服务接口.
 * 
 * @author njq.niu@jingrui.com
 * @date 2016/1/31
 */
public interface ILovService extends IBaseService<Lov>, ProxySelf<ILovService> {

    /**
     * 删除lov配置.
     * 
     * @param items
     *            删除参数
     * @return 删除结果
     */
    boolean batchDeleteLov(List<Lov> items);

    /**
     * 批量删除item.
     * 
     * @param items
     *            参数
     * @return 结果
     */
    boolean batchDeleteItems(List<LovItem> items);

    /**
     * 删除LovItem.
     * 
     * @param item
     *            参数
     * @return 结果
     */
    boolean deleteLovItem(LovItem item);

    /**
     * 加载lov配置.
     * 
     * @param lovId
     * @return LOV
     */
    Lov loadLov(Long lovId);

    /**
     * 创建lov配置.
     * 
     * @param lov
     * @return LOV
     */
    Lov createLov(Lov lov);

    /**
     * 更新lov配置.
     * 
     * @param lov
     * @return LOV
     */
    Lov updateLov(Lov lov);

    /**
     * 条件查询lov配置.
     * 
     * @param request
     * @param lov
     * @param page
     *            页码
     * @param pagesize
     *            数量
     * @return lov列表
     */
    List<Lov> selectLovs(IRequest request, Lov lov, int page, int pagesize);

    /**
     * 查询LovItem配置.
     * 
     * @param request
     * @param lovItem
     *            lov配置
     * @return lov配置列表
     */
    List<LovItem> selectLovItems(IRequest request, LovItem lovItem);

    /**
     * 根据lov代码获取lov的配置.
     * 
     * @param contextPath
     *            contextPath
     * @param locale
     *            locale
     * @param lovCode
     *            lovCode
     * @return lov配置
     */
    String getLov(String contextPath, Locale locale, String lovCode);

    /**
     * 通用lov获取数据.
     * 
     * @param request
     *            session
     * @param code
     *            code
     * @param obj
     *            obj
     * @param page
     *            页码
     * @param pagesize
     *            数量
     * @return DTO数据
     */
    List<?> selectDatas(IRequest request, String code, Object obj, int page, int pagesize);
}
