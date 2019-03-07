/*
 * #{copyright}#
 */

package com.jingrui.jrap.system.mapper;

import com.jingrui.jrap.system.dto.DocSequence;

/**
 * @author wuyichu
 */
public interface DocSequenceMapper {

    DocSequence lockDocSequence(DocSequence docSequence);

    int insert(DocSequence docSequence);

    int update(DocSequence docSequence);
}