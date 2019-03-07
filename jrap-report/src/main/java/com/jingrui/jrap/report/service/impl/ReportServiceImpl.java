package com.jingrui.jrap.report.service.impl;

import com.jingrui.jrap.report.dto.Report;
import com.jingrui.jrap.report.mapper.ReportMapper;
import com.jingrui.jrap.report.service.IReportService;
import com.jingrui.jrap.system.mapper.ParameterConfigMapper;
import com.jingrui.jrap.system.service.impl.BaseServiceImpl;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 报表服务接口实现.
 *
 * @author qiang.zeng
 * @date 2017/9/21
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ReportServiceImpl extends BaseServiceImpl<Report> implements IReportService {
    @Autowired
    private ReportMapper reportMapper;

    @Autowired
    private ParameterConfigMapper parameterConfigMapper;

    @Override
    public int batchDelete(List<Report> reports) {
        int count = 0;
        if (CollectionUtils.isNotEmpty(reports)) {
            for (Report report : reports) {
                int updateCount = reportMapper.deleteByPrimaryKey(report);
                checkOvn(updateCount, report);
                parameterConfigMapper.deleteByCodeAndTargetId("REPORT", report.getReportId());
                count++;
            }
        }
        return count;
    }

    @Override
    protected boolean useSelectiveUpdate() {
        return false;
    }

    @Override
    public List<Report> selectByReportCode(String reportCode) {
        return reportMapper.selectByReportCode(reportCode);
    }
}