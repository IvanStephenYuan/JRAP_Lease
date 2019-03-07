package com.jingrui.jrap.report.service;

import com.jingrui.jrap.core.ProxySelf;
import com.jingrui.jrap.report.dto.Report;
import com.jingrui.jrap.system.service.IBaseService;

import java.util.List;

/**
 * 报表服务接口.
 *
 * @author qiang.zeng@jingrui.com
 * @date 2017/9/21
 */
public interface IReportService extends IBaseService<Report>, ProxySelf<IReportService> {
    /**
     * 根据报表编码查询报表信息.
     *
     * @param reportCode 报表编码
     * @return 报表列表
     */
    List<Report> selectByReportCode(String reportCode);
}