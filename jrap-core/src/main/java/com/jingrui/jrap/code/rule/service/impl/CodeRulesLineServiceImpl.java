package com.jingrui.jrap.code.rule.service.impl;

import com.jingrui.jrap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.jingrui.jrap.code.rule.dto.CodeRulesLine;
import com.jingrui.jrap.code.rule.service.ICodeRulesLineService;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class CodeRulesLineServiceImpl extends BaseServiceImpl<CodeRulesLine> implements ICodeRulesLineService {

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW,rollbackFor = Exception.class)
    public CodeRulesLine updateRecord(CodeRulesLine record) {
        mapper.updateByPrimaryKey(record);
        return record;
    }
}