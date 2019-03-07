package com.jingrui.jrap.code.rule.mapper;

import com.jingrui.jrap.mybatis.common.Mapper;
import com.jingrui.jrap.code.rule.dto.CodeRulesLine;

public interface CodeRulesLineMapper extends Mapper<CodeRulesLine> {

    int deleteByHeaderId(CodeRulesLine line);
}