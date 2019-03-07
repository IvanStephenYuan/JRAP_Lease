package com.jingrui.jrap.system.mapper;

import com.jingrui.jrap.mybatis.common.Mapper;
import com.jingrui.jrap.system.dto.CodeValue;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 快速编码行Mapper
 *
 * @author shengyang.zhou@jingrui.com
 * @date 2016/6/9.
 */
public interface CodeValueMapper extends Mapper<CodeValue> {

    /**
     * 删除CodeValue
     * @param key codeId
     * @return
     */
    int deleteByCodeId(CodeValue key);

    /**
     * 删除CodeValue 多语言表数据
     * @param key
     * @return
     */
    int deleteTlByCodeId(CodeValue key);

    /**
     * 根据codeName查询CodeValue
     * @param codeName
     * @return list
     */
    List<CodeValue> selectCodeValuesByCodeName(String codeName);

    /**
     * 查询消息模版编码LOV
     * @param value
     * @param meaning
     * @return
     */
    List<CodeValue> queryMsgTemCodeLov(@Param("value") String value, @Param("meaning") String meaning);

    /**
     * 查询邮箱帐号编码LOV
     * @param value
     * @param meaning
     * @return
     */
    List<CodeValue> queryEmlAccountCodeLov(@Param("value") String value, @Param("meaning") String meaning);

    /**
     * 根据CodeId查询CodeValue
     * @param codeValue
     * @return
     */
    List<CodeValue> selectCodeValuesByCodeId(CodeValue codeValue);

    /**
     * 查询CodeValue
     * @param parentId
     * @return
     */
    List<CodeValue> selectCodeValuesByParentId(Long parentId);

    /**
     * 获取codeValue
     * @param codeValueId
     * @return
     */
    CodeValue getCodeValueById(Long codeValueId);
}