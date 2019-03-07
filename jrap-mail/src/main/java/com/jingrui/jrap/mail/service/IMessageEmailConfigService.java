package com.jingrui.jrap.mail.service;

import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.core.ProxySelf;
import com.jingrui.jrap.core.annotation.StdWho;
import com.jingrui.jrap.core.exception.BaseException;
import com.jingrui.jrap.core.exception.EmailException;
import com.jingrui.jrap.mail.dto.MessageEmailConfig;

import java.util.List;

/**
 * 邮件配置服务接口.
 *
 * @author Clerifen Li
 */
public interface IMessageEmailConfigService extends ProxySelf<IMessageEmailConfigService> {
    /**
     * 新建邮件配置.
     *
     * @param request IRequest
     * @param obj     邮件配置
     * @return 邮件配置
     * @throws BaseException 基础异常
     */
    MessageEmailConfig createMessageEmailConfig(IRequest request, @StdWho MessageEmailConfig obj) throws BaseException;

    /**
     * 更新邮件配置.
     *
     * @param request IRequest
     * @param obj     邮件配置
     * @return 邮件配置
     * @throws BaseException 基础异常
     */
    MessageEmailConfig updateMessageEmailConfig(IRequest request, @StdWho MessageEmailConfig obj);

    /**
     * 根据邮件配置Id查询邮件配置.
     *
     * @param request IRequest
     * @param objId   邮件配置Id
     * @return 邮件配置
     */
    MessageEmailConfig selectMessageEmailConfigById(IRequest request, Long objId);

    /**
     * 条件查询邮件配置.
     *
     * @param request  IRequest
     * @param obj      邮件配置
     * @param page     页码
     * @param pageSize 页数
     * @return 邮件配置列表
     */
    List<MessageEmailConfig> selectMessageEmailConfigs(IRequest request, MessageEmailConfig obj, int page, int pageSize);

    /**
     * 删除单个邮件配置.
     *
     * @param request IRequest
     * @param obj     邮件配置
     * @return int
     */
    int deleteMessageEmailConfig(IRequest request, MessageEmailConfig obj);

    /**
     * 批量删除邮件配置.
     *
     * @param request IRequest
     * @param objs    邮件配置列表
     * @return int
     * @throws BaseException 基础异常
     */
    int batchDelete(IRequest request, List<MessageEmailConfig> objs) throws BaseException;

    /**
     * 批量更新邮件配置.
     *
     * @param requestContext IRequest
     * @param obj            邮件配置
     * @throws EmailException 邮件异常
     */
    void batchUpdate(IRequest requestContext, @StdWho MessageEmailConfig obj) throws EmailException;

}
