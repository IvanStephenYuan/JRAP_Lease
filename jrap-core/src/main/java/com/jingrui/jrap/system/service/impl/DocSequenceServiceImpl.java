/*
 * #{copyright}#
 */
package com.jingrui.jrap.system.service.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.system.dto.DocSequence;
import com.jingrui.jrap.system.mapper.DocSequenceMapper;
import com.jingrui.jrap.system.service.IDocSequenceService;

/**
 * @author runbai.chen
 */
@Service
@Transactional
public class DocSequenceServiceImpl implements IDocSequenceService {
    @Autowired
    private DocSequenceMapper docSequenceMapper;

    @Override
    public DocSequence lockDocSequence(IRequest iRequest, DocSequence docSequence) {
        DocSequence resultDocSequence = docSequenceMapper.lockDocSequence(docSequence);
        return resultDocSequence;
    }

    @Override
    public DocSequence insertDocSequence(IRequest iRequest, DocSequence docSequence) {
        docSequenceMapper.insert(docSequence);
        return docSequence;
    }

    @Override
    public DocSequence updateDocSequence(IRequest iRequest, DocSequence docSequence) {
        docSequenceMapper.update(docSequence);
        return docSequence;
    }

    @Override
    public DocSequence processDocSequence(IRequest iRequest, DocSequence docSequence, Long initValue) {
        DocSequence resultDocSequence = self().lockDocSequence(iRequest, docSequence);
        if (resultDocSequence != null && resultDocSequence.getNextSeqNumber() != null) {
            resultDocSequence.setNextSeqNumber(resultDocSequence.getNextSeqNumber() + 1);
            self().updateDocSequence(iRequest, resultDocSequence);
        } else {
            resultDocSequence = docSequence;
            resultDocSequence.setNextSeqNumber(initValue);
            self().insertDocSequence(iRequest, resultDocSequence);
        }
        return resultDocSequence;
    }

    @Override
    public String getSequence(IRequest iRequest, DocSequence docSequence, String docPrefix, int seqLength,
            Long initValue) {
        String sequence;
        // 数据库更新

        docSequence = self().processDocSequence(iRequest, docSequence, initValue);

        // 获取序列
        String seqNumber = docSequence.getNextSeqNumber().toString();

        // 如果输出序列长度小于0，或者数据库序列长度>0
        if (seqLength <= 0 || seqNumber.length() > seqLength) {
            // 将前缀+序列
            sequence = docPrefix + seqNumber;
        } else {
            // 前缀+000+序列
            // sequence = docPrefix + getFixLengthString(seqLength, "0",
            // seqNumber);
            sequence = docPrefix + StringUtils.leftPad(seqNumber, seqLength, '0');
        }
        return sequence;
    }
}
