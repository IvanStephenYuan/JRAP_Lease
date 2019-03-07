package com.jingrui.jrap.system.service;


import com.jingrui.jrap.core.ProxySelf;
import com.jingrui.jrap.system.dto.SysConfig;

/**
 * @author hailin.xu@jingrui.com
 */
public interface ISysConfigService extends IBaseService<SysConfig>, ProxySelf<ISysConfigService> {
    /**
     * 根据ConfigCode获取配置值.
     * 
     * @param configCode
     *            配置代码
     * @return 配置值
     */
            
    String getConfigValue(String configCode);

    /**
     * 更新系统图片时间戳.
     */
    String updateSystemImageVersion(String type);


    
}
