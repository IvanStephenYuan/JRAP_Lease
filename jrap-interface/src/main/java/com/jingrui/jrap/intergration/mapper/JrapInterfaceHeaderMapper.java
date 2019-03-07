package com.jingrui.jrap.intergration.mapper;

import com.jingrui.jrap.mybatis.common.Mapper;

import com.jingrui.jrap.intergration.dto.JrapInterfaceHeader;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author jiguang.sun@jingrui.com
 * @version  2016/7/21.
 */
public interface JrapInterfaceHeaderMapper extends Mapper<JrapInterfaceHeader> {

    //查询所有的系统接口
   public  List<JrapInterfaceHeader> getAllHeader(JrapInterfaceHeader interfaceHeader);

    /*
    * 一对多关联查询 根据headerId 与语言
    * */
    public List<JrapInterfaceHeader> getHeaderAndLineList(JrapInterfaceHeader interfaceHeader);

    /*
    * 根据sysName 和 apiName 查询header 和 line
    * */
    public JrapInterfaceHeader getHeaderAndLineBySysNameAndApiName(@Param("sysName") String sysName, @Param("apiName") String apiName);

    /*
    * 获取所有的header和line 数据--->HeaderAndHeaderTlDTO
    * */
    List<JrapInterfaceHeader> getAllHeaderAndLine();

   /*
   * 根据headerId 获取header
   * */
   List<JrapInterfaceHeader> getHeaderByHeaderId(JrapInterfaceHeader interfaceHeader);

   /*
   * 根据lineId 获取数据  HeaderAndLineDTO
   * */
   JrapInterfaceHeader getHeaderAndLineBylineId(JrapInterfaceHeader interfaceHeader);


}
