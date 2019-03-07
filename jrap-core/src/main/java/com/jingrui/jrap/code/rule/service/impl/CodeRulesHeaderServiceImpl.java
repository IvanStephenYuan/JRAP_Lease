package com.jingrui.jrap.code.rule.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jingrui.jrap.cache.impl.SysCodeRuleCache;
import com.jingrui.jrap.code.rule.CodeRuleConstants;
import com.jingrui.jrap.code.rule.dto.CodeRulesHeader;
import com.jingrui.jrap.code.rule.dto.CodeRulesLine;
import com.jingrui.jrap.code.rule.mapper.CodeRulesHeaderMapper;
import com.jingrui.jrap.code.rule.mapper.CodeRulesLineMapper;
import com.jingrui.jrap.code.rule.service.ICodeRulesHeaderService;
import com.jingrui.jrap.core.BaseConstants;
import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.system.service.impl.BaseServiceImpl;

/**
 * @author xiangyu.qi@jingrui.com
 */

@Service
@Transactional(rollbackFor = Exception.class)
public class CodeRulesHeaderServiceImpl extends BaseServiceImpl<CodeRulesHeader> implements ICodeRulesHeaderService {

    @Autowired
    private SysCodeRuleCache ruleCache;

    @Autowired
    private CodeRulesHeaderMapper headerMapper;

    @Autowired
    private CodeRulesLineMapper lineMapper;

    public CodeRulesHeader updateCodeRule( CodeRulesHeader record) {
        headerMapper.updateByPrimaryKey(record);
        //是否删除序列缓存重新创建
        boolean deleteSeq = false;
        if(record.getLines() != null) {
            deleteSeq = processLines(record);
        }
        ruleCache.remove(record.getRuleCode());
        if(deleteSeq){
            ruleCache.removeSeq(record.getRuleCode());
        }
        if(BaseConstants.YES.equalsIgnoreCase(record.getEnableFlag())) {
            ruleCache.reload(record);
        }
        return record;
    }

    public CodeRulesHeader createCodeRule(CodeRulesHeader record) {
        headerMapper.insertSelective(record);
        // 判断如果行不为空，则迭代循环插入
        if (record.getLines() != null) {
            processLines(record);
        }
        if(BaseConstants.YES.equalsIgnoreCase(record.getEnableFlag())) {
            ruleCache.reload(record);
        }
        return record;
    }

    @Override
    public int deleteByPrimaryKey(CodeRulesHeader record) {
        //删除行
        CodeRulesLine line = new CodeRulesLine();
        line.setHeaderId(record.getHeaderId());
        lineMapper.deleteByHeaderId(line);
        int result = headerMapper.deleteByPrimaryKey(record);
        ruleCache.remove(record.getRuleCode());
        ruleCache.removeSeq(record.getRuleCode());
        return result;
    }

    @Override
    public List<CodeRulesHeader> batchUpdate(IRequest request, List<CodeRulesHeader> list) {
        for (CodeRulesHeader header : list) {
            if (header.getHeaderId() == null) {
                self().createCodeRule(header);
            } else {
                self().updateCodeRule(header);
            }
        }
        return list;
    }

    private boolean processLines(CodeRulesHeader header) {
        boolean updateSeq = false;
        for (CodeRulesLine line : header.getLines()) {
            if (line.getHeaderId() == null) {
                line.setHeaderId(header.getHeaderId()); // 设置头ID跟行ID一致
                lineMapper.insertSelective(line);
            } else {
                int count = lineMapper.updateByPrimaryKey(line);
                checkOvn(count,line);
                if(CodeRuleConstants.FIELD_TYPE_SEQUENCE.equalsIgnoreCase(line.getFiledType())){
                    updateSeq = true;
                }
            }
        }
        return updateSeq;
    }
}