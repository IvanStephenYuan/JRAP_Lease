/*
 * #{copyright}#
 */
package com.jingrui.jrap.system.service;

import com.jingrui.jrap.core.annotation.StdWho;
import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.core.ProxySelf;
import com.jingrui.jrap.system.dto.DocSequence;

/**
 * 序列接口.
 * 
 * @author runbai.chen
 */
public interface IDocSequenceService extends ProxySelf<IDocSequenceService> {
    /**
     * 锁表,保证唯一.
     * 
     * @param request
     *            session信息
     * @param docSequence
     *            序列对象
     * @return 序列对象
     */
    DocSequence lockDocSequence(IRequest request, DocSequence docSequence);

    /**
     * 插入序列行信息.
     * 
     * @param request
     *            session信息
     * @param docSequence
     *            序列行信息
     * @return 序列对象
     */
    DocSequence insertDocSequence(IRequest request, @StdWho DocSequence docSequence);

    /**
     * 更新序列行信息.
     * 
     * @param request
     *            session信息
     * @param docSequence
     *            序列行信息
     * @return 序列对象
     */
    DocSequence updateDocSequence(IRequest request, @StdWho DocSequence docSequence);

    /**
     * 处理序列. 首先锁表，如果数据不为空，则更新数据序列+1，如果为空，则插入一行数据.
     * 
     * @param request
     *            session信息
     * @param docSequence
     *            序列对象
     * @param initValue
     *            初始值(插入时有效)
     * @return 序列对象
     */
    DocSequence processDocSequence(IRequest request, @StdWho DocSequence docSequence, Long initValue);

    /**
     * 生成流水号.
     * 
     * 不同业务流水类型
     * 
     * @param request
     *            session信息
     * @param docSequence
     *            docSequence
     * @param docPrefix
     *            流水号前缀
     * @param seqLength
     *            流水号长度
     * @param initValue
     *            初始化值
     * @return 生成的序号
     */
    String getSequence(IRequest request, @StdWho DocSequence docSequence, String docPrefix, int seqLength,
            Long initValue);

}
