package com.jingrui.jrap.report.mapper;

import com.jingrui.jrap.mybatis.common.Mapper;
import com.jingrui.jrap.report.dto.Report;

import java.util.List;

/**
 * 报表Mapper.
 *
 * @author qiang.zeng
 * @date 2017/9/21
 */
public interface ReportMapper extends Mapper<Report> {
    /**
     * 根据报表编码查询报表信息.
     *
     * @param reportCode 报表编码
     * @return 报表列表
     */
    List<Report> selectByReportCode(String reportCode);
}