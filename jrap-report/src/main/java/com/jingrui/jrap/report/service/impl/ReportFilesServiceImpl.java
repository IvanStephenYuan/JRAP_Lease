package com.jingrui.jrap.report.service.impl;


import com.jingrui.jrap.report.dto.ReportFiles;
import com.jingrui.jrap.report.mapper.ReportFilesMapper;
import com.jingrui.jrap.report.service.IReportFilesService;
import com.jingrui.jrap.system.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 报表文件服务接口实现.
 *
 * @author qiang.zeng
 * @date 2017/9/21
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ReportFilesServiceImpl extends BaseServiceImpl<ReportFiles> implements IReportFilesService {
    @Autowired
    private ReportFilesMapper reportFilesMapper;

    @Override
    public ReportFiles selectByName(String name) {
        return reportFilesMapper.selectByName(name);
    }

    @Override
    public int deleteByName(String name) {
        return reportFilesMapper.deleteByName(name);
    }

    @Override
    public ReportFiles selectReportFileParams(String reportCode) {
        return reportFilesMapper.selectReportFileParams(reportCode);
    }
}