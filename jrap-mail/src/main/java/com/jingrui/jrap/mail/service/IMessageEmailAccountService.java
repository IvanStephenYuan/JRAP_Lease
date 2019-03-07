package com.jingrui.jrap.mail.service;

import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.core.ProxySelf;
import com.jingrui.jrap.core.annotation.StdWho;
import com.jingrui.jrap.core.exception.BaseException;
import com.jingrui.jrap.mail.dto.MessageEmailAccount;

import java.util.List;

/**
 * 邮件账号服务接口.
 *
 * @author Clerifen Li
 */
public interface IMessageEmailAccountService extends ProxySelf<IMessageEmailAccountService> {
    /**
     * 新建邮件账户.
     *
     * @param request IRequest
     * @param obj     邮件账户
     * @return 邮件账户
     * @throws BaseException 基础异常
     */
    MessageEmailAccount createMessageEmailAccount(IRequest request, @StdWho MessageEmailAccount obj) throws BaseException;

    /**
     * 更新邮件账户,不处理账户密码.
     *
     * @param request IRequest
     * @param obj     邮件账户
     * @return 邮件账户
     * @throws BaseException 基础异常
     */
    MessageEmailAccount updateMessageEmailAccount(IRequest request, @StdWho MessageEmailAccount obj);

    /**
     * 只更新邮件账户密码.
     *
     * @param request IRequest
     * @param obj     邮件账户
     * @return 邮件账户
     * @throws BaseException 基础异常
     */
    MessageEmailAccount updateMessageEmailAccountPasswordOnly(IRequest request, MessageEmailAccount obj);

    /**
     * 根据邮件账户Id查询邮件账户.
     *
     * @param request IRequest
     * @param objId   邮件账户Id
     * @return 邮件账户
     */
    MessageEmailAccount selectMessageEmailAccountById(IRequest request, Long objId);

    /**
     * 条件查询邮件账户.
     *
     * @param request  IRequest
     * @param obj      邮件账户
     * @param page     页码
     * @param pageSize 页数
     * @return 邮件账户列表
     */
    List<MessageEmailAccount> selectMessageEmailAccounts(IRequest request, MessageEmailAccount obj, int page, int pageSize);

    /**
     * 删除单个邮件账户.
     *
     * @param request IRequest
     * @param obj     邮件账户
     * @return int
     */
    int deleteMessageEmailAccount(IRequest request, MessageEmailAccount obj);

    /**
     * 批量删除邮件账户.
     *
     * @param request                 IRequest
     * @param messageEmailAccountList 邮件账户列表
     * @return int
     * @throws BaseException 基础异常
     */
    int batchDelete(IRequest request, List<MessageEmailAccount> messageEmailAccountList) throws BaseException;

    /**
     * 根据账户密码查询邮件账户.
     *
     * @param request  IRequest
     * @param obj      邮件账户
     * @param page     页码
     * @param pageSize 页数
     * @return
     */
    List<MessageEmailAccount> selectMessageEmailAccountWithPassword(IRequest request, MessageEmailAccount obj, int page, int pageSize);

}
