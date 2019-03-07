package com.jingrui.jrap.report;

import com.bstek.ureport.definition.datasource.BuildinDatasource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * 报表内置数据源.
 *
 * @author qiang.zeng
 * @date 2017/9/21
 */
public class BuildinDataSourceImpl implements BuildinDatasource {
    private DataSource dataSource;

    @Override
    public String name() {
        return "JRAP Built-in DataSource";
    }

    @Override
    public Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
