/*
 * *
 *  @file com.maddyhome.idea.copyright.pattern.JavaCopyrightVariablesProvider$1@6b69f842$
 *  @CopyRight (C) 2018 ZheJiangJingRui Co. Ltd.
 *  @brief JingRui Application Platform
 *  @author $name$
 *  @email yulong.yuan@jr-info.cn
 *  @date $date$
 * /
 */

package com.jingrui.jrap.db.excel;

import java.net.URL;
import java.io.FileNotFoundException;
import java.io.File;

public class ExcelUtil {
    public static void main(String[] args) throws Exception {
        System.setProperty("db.url", "jdbc:mysql://127.0.0.1:3306/hap_dev");
        System.setProperty("db.user", "hap_dev");
        System.setProperty("db.password", "hap_dev");
        System.setProperty("db.override", "true");

        ExcelDataLoader processor = new ExcelDataLoader();
        URL url = ExcelDataLoader.class.getResource("/com/jingrui/jrap/db/data/crm-init-data.xlsx");
        if (url == null) {
            throw new FileNotFoundException("excel not found.");
        }
        processor.setFilePath(new File(url.toURI()).getAbsolutePath());
        processor.execute(null);
    }
}
