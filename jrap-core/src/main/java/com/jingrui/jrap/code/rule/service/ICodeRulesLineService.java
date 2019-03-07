package com.jingrui.jrap.code.rule.service;

import com.jingrui.jrap.core.ProxySelf;
import com.jingrui.jrap.system.service.IBaseService;
import com.jingrui.jrap.code.rule.dto.CodeRulesLine;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public interface ICodeRulesLineService extends IBaseService<CodeRulesLine>, ProxySelf<ICodeRulesLineService> {

    CodeRulesLine updateRecord(CodeRulesLine record);
}