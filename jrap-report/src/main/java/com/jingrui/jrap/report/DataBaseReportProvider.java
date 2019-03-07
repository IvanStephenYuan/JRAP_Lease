package com.jingrui.jrap.report;


import com.bstek.ureport.provider.report.ReportFile;
import com.bstek.ureport.provider.report.ReportProvider;
import com.jingrui.jrap.report.dto.ReportFiles;
import com.jingrui.jrap.report.service.IReportFilesService;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 报表数据库存储提供者.
 *
 * @author qiang.zeng
 * @date 2017/9/28
 */
public class DataBaseReportProvider implements ReportProvider {

    private String prefix = "database:";
    private boolean disabled;
    private String sourceType;

    private static String STORE_SYSTEM_NAME = "数据库存储系统";
    private static String REPORT = "ureport";
    private static String DATA_SOURCE = "datasource";
    private static String DATA_SET = "dataset";
    private static String PARAMETER = "parameter";
    private static String PARAMETER_NAME = "name";
    private static String SOURCE_TYPE = "type";
    private static String SOURCE_TYPE_SPRING_BEAN = "spring";

    @Autowired
    private IReportFilesService reportFilesService;

    @Override
    public InputStream loadReport(String name) {
        ReportFiles reportFile = reportFilesService.selectByName(removePrefix(name));
        if (reportFile != null) {
            try {
                return IOUtils.toInputStream(reportFile.getContent(), "utf-8");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public void deleteReport(String name) {
        reportFilesService.deleteByName(removePrefix(name));
    }

    @Override
    public List<ReportFile> getReportFiles() {
        List<ReportFile> list = new ArrayList<>();
        List<ReportFiles> reports = reportFilesService.selectOptions(null, new ReportFiles(), null);
        reports.forEach(report -> list.add(new ReportFile(report.getName(), report.getLastUpdateDate())));
        list.sort((ReportFile rf1, ReportFile rf2) -> rf2.getUpdateDate().compareTo(rf1.getUpdateDate()));
        return list;
    }

    @Override
    public void saveReport(String name, String content) {
        ReportFiles reportFiles = reportFilesService.selectByName(removePrefix(name));
        //初始化数据源类型
        this.sourceType = "";
        if (reportFiles != null) {
            reportFiles.setContent(content);
            reportFiles.setParams(getParams(content));
            reportFiles.setSourceType(this.sourceType);
            reportFilesService.updateByPrimaryKey(null, reportFiles);
        } else {
            reportFiles = new ReportFiles();
            reportFiles.setName(removePrefix(name));
            reportFiles.setContent(content);
            reportFiles.setParams(getParams(content));
            reportFiles.setSourceType(this.sourceType);
            reportFiles.setObjectVersionNumber(1L);
            reportFilesService.insertSelective(null, reportFiles);
        }
    }

    @Override
    public String getName() {
        return STORE_SYSTEM_NAME;
    }

    @Override
    public boolean disabled() {
        return this.disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    @Override
    public String getPrefix() {
        return this.prefix;
    }

    /**
     * 删除报表文件名前缀.
     *
     * @param name 报表文件名
     * @return String
     */
    private String removePrefix(String name) {
        if (name.startsWith(this.prefix)) {
            name = name.substring(this.prefix.length(), name.length());
        }
        name = name.split(".xml")[0] + ".xml";
        return name;
    }

    /**
     * 获取报表设置的参数.
     *
     * @param content 报表的XML内容
     * @return 报表参数（String）
     */
    private String getParams(String content) {
        StringBuilder params = new StringBuilder("");
        JSONObject report = XML.toJSONObject(content).getJSONObject(REPORT);
        if (report.has(DATA_SOURCE)) {
            obtainDatasource(report, params);
        }
        return params.toString();
    }

    /**
     * 获取报表设计中的数据源.
     *
     * @param report 报表
     * @param params 报表参数
     */
    private void obtainDatasource(JSONObject report, StringBuilder params) {
        Object dataSourceObj = report.get(DATA_SOURCE);
        if (dataSourceObj instanceof JSONObject) {
            JSONObject dataSource = (JSONObject) dataSourceObj;
            setSourceType(dataSource);
            obtainDataSet(dataSource, params);
        } else if (dataSourceObj instanceof JSONArray) {
            JSONArray dataSources = (JSONArray) dataSourceObj;
            for (int i = 0; i < dataSources.length(); i++) {
                JSONObject dataSource = (JSONObject) dataSources.get(i);
                setSourceType(dataSource);
                obtainDataSet(dataSource, params);
            }
        }
    }

    /**
     * 获取报表设计中的数据集.
     *
     * @param dataSource 报表数据源
     * @param params     报表参数
     */
    private void obtainDataSet(JSONObject dataSource, StringBuilder params) {
        if (dataSource.has(DATA_SET)) {
            Object dataSetObj = dataSource.get(DATA_SET);
            if (dataSetObj instanceof JSONObject) {
                JSONObject dataSet = (JSONObject) dataSetObj;
                obtainParameter(dataSet, params);
            } else if (dataSetObj instanceof JSONArray) {
                JSONArray dataSets = (JSONArray) dataSetObj;
                for (int i = 0; i < dataSets.length(); i++) {
                    JSONObject dataSet = (JSONObject) dataSets.get(i);
                    obtainParameter(dataSet, params);
                }
            }
        }
    }

    /**
     * 获取报表设计中的数据集参数.
     *
     * @param dataSet 报表数据集
     * @param params  报表参数
     */
    private void obtainParameter(JSONObject dataSet, StringBuilder params) {
        if (dataSet.has(PARAMETER)) {
            Object parameterObj = dataSet.get(PARAMETER);
            if (parameterObj instanceof JSONObject) {
                JSONObject parameter = (JSONObject) parameterObj;
                params.append(parameter.get(PARAMETER_NAME)).append(";");
            } else if (parameterObj instanceof JSONArray) {
                JSONArray parameters = (JSONArray) parameterObj;
                for (int i = 0; i < parameters.length(); i++) {
                    JSONObject parameter = (JSONObject) parameters.get(i);
                    params.append(parameter.get(PARAMETER_NAME)).append(";");
                }
            }
        }
    }

    /**
     * 设置报表文件的数据源类型.
     *
     * @param dataSource 数据源
     */
    private void setSourceType(JSONObject dataSource) {
        if (SOURCE_TYPE_SPRING_BEAN.equalsIgnoreCase(this.sourceType)) {
            return;
        }
        this.sourceType = (String) dataSource.get(SOURCE_TYPE);
    }
}
