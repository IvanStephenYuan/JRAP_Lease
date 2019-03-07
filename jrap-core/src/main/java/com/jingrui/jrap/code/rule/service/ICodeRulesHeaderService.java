package com.jingrui.jrap.code.rule.service;

import com.jingrui.jrap.core.ProxySelf;
import com.jingrui.jrap.system.service.IBaseService;
import com.jingrui.jrap.code.rule.dto.CodeRulesHeader;

public interface ICodeRulesHeaderService extends IBaseService<CodeRulesHeader>, ProxySelf<ICodeRulesHeaderService> {

    CodeRulesHeader createCodeRule(CodeRulesHeader record);

    CodeRulesHeader updateCodeRule( CodeRulesHeader record);
}