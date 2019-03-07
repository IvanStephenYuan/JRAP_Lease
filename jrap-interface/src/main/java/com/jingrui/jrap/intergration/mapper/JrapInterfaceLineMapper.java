package com.jingrui.jrap.intergration.mapper;

import com.jingrui.jrap.intergration.dto.JrapInterfaceHeader;
import com.jingrui.jrap.mybatis.common.Mapper;
import com.jingrui.jrap.intergration.dto.JrapInterfaceLine;

import java.util.List;

/**
 * Created by user on 2016/7/26.
 */
public interface JrapInterfaceLineMapper extends Mapper<JrapInterfaceLine> {

    /*
    * 根据lineId和语言 获取LineAndLineTl
    * */
    public List<JrapInterfaceLine> getLineAndLineTl(JrapInterfaceLine interfaceLine);


    /*
    * 根据lineId获取headerAndLine
    * */
    JrapInterfaceHeader getHeaderLineByLineId(String lineId);

    /*
    * 根据headerId 获取lines
    * */
    List<JrapInterfaceLine> getLinesByHeaderId(JrapInterfaceLine interfaceLine);

    int deleteByHeaderId(JrapInterfaceLine hapInterfaceLine);

    int deleteTlByHeaderId(JrapInterfaceLine hapInterfaceLine);

}
