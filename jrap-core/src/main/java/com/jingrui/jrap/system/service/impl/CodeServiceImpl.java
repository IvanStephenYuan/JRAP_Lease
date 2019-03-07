package com.jingrui.jrap.system.service.impl;

import com.github.pagehelper.PageHelper;
import com.jingrui.jrap.cache.impl.SysCodeCache;
import com.jingrui.jrap.core.BaseConstants;
import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.system.dto.Code;
import com.jingrui.jrap.system.dto.CodeValue;
import com.jingrui.jrap.system.mapper.CodeMapper;
import com.jingrui.jrap.system.mapper.CodeValueMapper;
import com.jingrui.jrap.system.service.ICodeService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 快速编码ServiceImpl
 *
 * @author shengyang.zhou@jingrui.com
 * @date 2016/6/9.
 */
@Service
public class CodeServiceImpl extends BaseServiceImpl<Code> implements ICodeService {

    @Autowired
    private CodeMapper codeMapper;

    @Autowired
    private CodeValueMapper codeValueMapper;

    @Autowired
    private SysCodeCache codeCache;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Code> selectCodes(IRequest request, Code code, int page, int pagesize) {
        PageHelper.startPage(page, pagesize);
        List<Code> codes = codeMapper.selectCodes(code);
        return codes;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<CodeValue> selectCodeValues(IRequest request, CodeValue value) {
        return codeValueMapper.selectCodeValuesByCodeId(value);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<CodeValue> selectCodeValuesByCodeName(IRequest request, String codeName) {
        Code code = getValue(request, codeName);
        if (code != null) {
            return code.getCodeValues();
        } else {
            return codeValueMapper.selectCodeValuesByCodeName(codeName);
        }
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public CodeValue getCodeValue(IRequest request, String codeName, String value) {
        Code code = getValue(request, codeName);
        if (code == null) {
            return null;
        }
        if (code.getCodeValues() == null) {
            return null;
        }
        for (CodeValue v : code.getCodeValues()) {
            if (v.getValue().equals(value)) {
                return v;
            }
        }
        return null;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public String getCodeValueByMeaning(IRequest request, String codeName, String meaning) {
        Code code = getValue(request, codeName);
        if (code == null) {
            return null;
        }
        if (code.getCodeValues() == null) {
            return null;
        }
        for (CodeValue v : code.getCodeValues()) {
            if (v.getMeaning().equals(meaning)) {
                return v.getValue();
            }
        }
        return null;
    }


    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public String getCodeMeaningByValue(IRequest request, String codeName, String value) {
        Code code = getValue(request, codeName);
        if (code == null) {
            return null;
        }
        if (code.getCodeValues() == null) {
            return null;
        }
        for (CodeValue v : code.getCodeValues()) {
            if (v.getValue().equals(value)) {
                return v.getMeaning();
            }
        }
        return null;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public String getCodeDescByValue(IRequest request, String codeName, String value) {
        Code code = getValue(request, codeName);
        if (code == null) {
            return null;
        }
        if (code.getCodeValues() == null) {
            return null;
        }
        for (CodeValue v : code.getCodeValues()) {
            if (v.getValue().equals(value)) {
                return v.getDescription();
            }
        }
        return null;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<CodeValue> getCodeValuesByCode(IRequest request, String codeName) {
        Code code = getValue(request, codeName);
        if (code != null && BaseConstants.YES.equals(code.getEnabledFlag())) {
            return getEnabledCodeValues(code);
        }
        return null;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<CodeValue> getChildCodeValue(IRequest request, String codeName, String value) {
        CodeValue codeValue = getCodeValue(request, codeName, value);
        if (codeValue != null) {
            return codeValueMapper.selectCodeValuesByParentId(codeValue.getCodeValueId());
        }
        return null;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<CodeValue> getChildCodeValue(IRequest request, String parentCodeName, String value,
            String childCodeName) {
        CodeValue parentCodeValue = getCodeValue(request, parentCodeName, value);
        List<CodeValue> childCodeValues = new ArrayList<>();
        if (parentCodeValue != null) {
            Code childCode = getValue(request, childCodeName);
            if (childCode != null && CollectionUtils.isNotEmpty(childCode.getCodeValues())) {
                Long parentCodeValueId = parentCodeValue.getCodeValueId();
                for (CodeValue codeValue : childCode.getCodeValues()) {
                    if (parentCodeValueId.equals(codeValue.getParentCodeValueId())) {
                        childCodeValues.add(codeValue);
                    }
                }
            }
        }
        return childCodeValues;
    }

    @Override
    public Code getValue(IRequest request, String codeName) {
        Code code = codeCache.getValue(codeName + "." + request.getLocale());
        if (code == null){
            code = codeMapper.getByCodeName(codeName);
            if (code != null){
                CodeValue codeValue = new CodeValue();
                codeValue.setCodeId(code.getCodeId());
                List<CodeValue> list = codeValueMapper.selectCodeValuesByCodeId(codeValue);
                code.setCodeValues(list);
                codeCache.setValue(codeName + "." + request.getLocale(), code);
            }
        }
        return code;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Code createCode(Code code) {
        // 插入头行
        codeMapper.insertSelective(code);
        // 判断如果行不为空，则迭代循环插入
        if (code.getCodeValues() != null) {
            processCodeValues(code);
        }
        codeCache.reload(code.getCodeId());
        return code;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean batchDelete(IRequest request, List<Code> codes) {
        // 删除头行
        for (Code code : codes) {
            CodeValue codeValue = new CodeValue();
            codeValue.setCodeId(code.getCodeId());
            // 首先删除行的多语言数据
            codeValueMapper.deleteTlByCodeId(codeValue);
            // 然后删除行
            codeValueMapper.deleteByCodeId(codeValue);
            // 最后删除头
            int updateCount = codeMapper.deleteByPrimaryKey(code);
            checkOvn(updateCount, code);
            codeCache.remove(code.getCode());
        }
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean batchDeleteValues(IRequest request, List<CodeValue> values) {
        Set<Long> codeIdSet = new HashSet<>();
        for (CodeValue value : values) {
            int updateCount = codeValueMapper.deleteByPrimaryKey(value);
            checkOvn(updateCount, value);
            codeIdSet.add(value.getCodeId());
        }
        for (Long codeId : codeIdSet) {
            codeCache.reload(codeId);
        }
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Code updateCode(Code code) {
        int count = codeMapper.updateByPrimaryKey(code);
        checkOvn(count, code);
        // 判断如果行不为空，则迭代循环插入
        if (code.getCodeValues() != null) {
            processCodeValues(code);
        }
        codeCache.remove(code.getCode());
        codeCache.reload(code.getCodeId());
        return code;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<Code> batchUpdate(IRequest request, List<Code> codes) {
        for (Code code : codes) {
            if (code.getCodeId() == null) {
                self().createCode(code);
            } else {
                self().updateCode(code);
            }
        }
        return codes;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public CodeValue getCodeValueById(Long codeId) {
        return codeValueMapper.getCodeValueById(codeId);
    }

    private List<CodeValue> getEnabledCodeValues(Code code) {
        List<CodeValue> enabledCodeValues = new ArrayList<>();
        List<CodeValue> allCodeValues = code.getCodeValues();
        if (allCodeValues != null) {
            for (CodeValue codevalue : allCodeValues) {
                if (BaseConstants.YES.equals(codevalue.getEnabledFlag())) {
                    enabledCodeValues.add(codevalue);
                }
            }
        }
        return enabledCodeValues;
    }

    /**
     * 批量操作快码行数据.
     *
     * @param code
     *            头行数据
     */
    private void processCodeValues(Code code) {
        for (CodeValue codeValue : code.getCodeValues()) {
            if (codeValue.getCodeValueId() == null) {
                // 设置头ID跟行ID一致
                codeValue.setCodeId(code.getCodeId());
                codeValueMapper.insertSelective(codeValue);
            } else {
                int count = codeValueMapper.updateByPrimaryKey(codeValue);
                checkOvn(count, codeValue);
            }
        }
    }

}
