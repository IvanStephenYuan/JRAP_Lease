package com.jingrui.jrap.system.service;

import java.util.List;

import com.jingrui.jrap.system.dto.CodeValue;
import com.jingrui.jrap.core.annotation.StdWho;
import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.core.ProxySelf;
import com.jingrui.jrap.system.dto.Code;

/**
 * 快速编码Service
 *
 * @author runbai.chen@jingrui.com
 * @date 2016/6/9.
 */
public interface ICodeService extends ProxySelf<ICodeService> {

    /**
     * 查询Code列表
     * @param request
     * @param code
     * @param page
     * @param pagesize
     * @return  Code列表
     */
    List<Code> selectCodes(IRequest request, Code code, int page, int pagesize);

    /**
     * 查询CodeValue列表
     * @param request
     * @param value
     * @return
     */
    List<CodeValue> selectCodeValues(IRequest request, CodeValue value);

    /**
     * 插入Code
     * @param code
     * @return
     */
    Code createCode(Code code);

    /**
     * 批量删除头行及多语言数据
     * @param request
     * @param codes
     * @return
     */
    boolean batchDelete(IRequest request, List<Code> codes);

    /**
     * 批量删除CodeValue
     * @param request
     * @param values
     * @return
     */
    boolean batchDeleteValues(IRequest request, List<CodeValue> values);

    /**
     * 更新头行
     * @param code
     * @return
     */
    Code updateCode(Code code);

    /**
     * 批量更新Code
     * @param request
     * @param codes
     * @return
     */
    List<Code> batchUpdate(IRequest request, @StdWho List<Code> codes);

    /**
     * 根据code查询所有代码值.
     *
     * @param request
     * @param codeName
     * @return 代码值
     */
    List<CodeValue> selectCodeValuesByCodeName(IRequest request, String codeName);


    /**
     * 根据代码和值获取CodeValue.
     *
     * @param request  请求上下文
     * @param codeName 代码
     * @param value    代码值
     * @return codeValue 代码值DTO
     * @author frank.li
     */
    CodeValue getCodeValue(IRequest request, String codeName, String value);

    /**
     * 根据代码和含义获取代码值.
     * <p>
     * 从 cache 直接取值.
     *
     * @param request  请求上下文
     * @param codeName 代码
     * @param meaning  含义
     * @return value 代码值
     * @author frank.li
     */
    String getCodeValueByMeaning(IRequest request, String codeName, String meaning);

    /**
     * 根据代码和值获取Meaning.
     *
     * @param codeName 代码
     * @param value    代码值
     * @return meaning 含义
     */
    String getCodeMeaningByValue(IRequest request, String codeName, String value);

    /**
     * 根据代码和值获取描述.
     *
     * @param codeName 代码
     * @param value    代码值
     * @return description 描述
     */
    String getCodeDescByValue(IRequest request, String codeName, String value);

    /**
     * 根据code查询有效代码值.
     *
     * @param request
     * @param codeName
     * @return 代码值
     */
    List<CodeValue> getCodeValuesByCode(IRequest request, String codeName);


    /**
     * 根据代码和值获取子快码codeValue.
     *
     * @param request  请求上下文
     * @param codeName 代码
     * @param value    代码值
     * @return codeValue 代码值DTO
     * @author frank.li
     */
    List<CodeValue> getChildCodeValue(IRequest request, String codeName, String value);

    /**
     * 根据父代码、值和子代码获取子代码值.
     *
     * @param request        IRequest
     * @param parentCodeName 父代码
     * @param value          父代码值
     * @param childCodeName  子代码
     * @return 子代码值
     */
    List<CodeValue> getChildCodeValue(IRequest request, String parentCodeName, String value, String childCodeName);

    /**
     * 从缓存中获取Code
     * 缓存中为空，则从数据库中加载并更新缓存
     * @param request        IRequest
     * @param codeName
     * @return
     */
    Code getValue(IRequest request, String codeName);

    /**
     * 获取CodeValue
     * @param codeId
     * @return
     */
    CodeValue getCodeValueById(Long codeId);

}
