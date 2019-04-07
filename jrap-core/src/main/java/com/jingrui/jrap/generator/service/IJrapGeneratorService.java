package com.jingrui.jrap.generator.service;

import com.jingrui.jrap.generator.dto.DBTable;
import com.jingrui.jrap.generator.dto.GeneratorInfo;

import java.util.List;

/**
 * Created by jialong.zuo@jingrui.com on 2016/10/24.
 */
public interface IJrapGeneratorService {
    List<String> showTables();

    int generatorFile(GeneratorInfo info);

    public DBTable getTableInfo(String tableName);
}
