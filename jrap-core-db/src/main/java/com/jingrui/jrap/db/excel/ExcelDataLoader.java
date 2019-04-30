package com.jingrui.jrap.db.excel;

import static com.jingrui.jrap.db.excel.TableData.TR;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import liquibase.change.custom.CustomTaskChange;
import liquibase.database.Database;
import liquibase.exception.CustomChangeException;
import liquibase.exception.SetupException;
import liquibase.exception.ValidationErrors;
import liquibase.resource.ResourceAccessor;

/**
 * @author shengyang.zhou@jingrui.com
 */
public class ExcelDataLoader implements CustomTaskChange {

    List<TableData> tables = null;

    DbAdaptor dbAdaptor;

    private Logger logger = LoggerFactory.getLogger(getClass());

    private String filePath;

    private Set<String> updateSkipTableNames = new HashSet<>();

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Set<String> getUpdateSkipTableNames() {
        return updateSkipTableNames;
    }

    public void setUpdateSkipTableNames(Set<String> updateSkipTableNames) {
        this.updateSkipTableNames = updateSkipTableNames;
    }

    public void processData() throws SQLException {
        try {
            dbAdaptor.initConnection();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        List<TableData> tablesCopy = new ArrayList<>(tables);
        int round = 0;

        long t0 = System.currentTimeMillis();

        try {
            while (true) {
                round++;
                logger.info("---- begin round {} ----", round);
                long t1 = System.currentTimeMillis();
                int roundProcessCount = 0;
                for (TableData tableData : tablesCopy) {
                    roundProcessCount += processTable(tableData);
                }

                logger.info("---- round {} summary ----", round);
                int mtnl = getMaxTableNameLength(tablesCopy);
                for (int i = 0; i < tablesCopy.size(); i++) {
                    String summary = String.format("%-" + mtnl + "s   %s", tablesCopy.get(i).getName(),
                            tablesCopy.get(i).processSummary());
                    logger.info(summary);
                    if (tablesCopy.get(i).complete()) {
                        tablesCopy.remove(i);
                        i--;
                    }
                }
                logger.info("---- total process {},time:{}ms ----", roundProcessCount,
                        (System.currentTimeMillis() - t1));
                if (tablesCopy.isEmpty()) {
                    break;
                }
                if (roundProcessCount == 0 && dbAdaptor.weakInsert(tablesCopy) == 0) {
                    logger.error("**** can not process rows below ****");
                    int c = 0;
                    for (TableData tableData : tablesCopy) {
                        logger.error(tableData.getName() + ":");
                        for (TR tr : tableData.getTrs()) {
                            if (tr.processFlag) {
                                logger.error("    " + tr);
                                c++;
                            }
                        }
                    }
                    throw new RuntimeException(c + " rows can not process.");
                }
            }
            if (dbAdaptor.isOverride()) {
                logger.info("begin update exists datas...");
                int uc = 0;
                for (TableData tableData : tables) {
                    if (updateSkipTableNames.contains(tableData.getName())) {
                        logger.info("skip update table : {}", tableData.getName());
                        continue;
                    }
                    for (TR tr : tableData.getTrs()) {
                        if (tr.existsFlag) {
                            uc += dbAdaptor.doUpdate(tr);
                        }
                    }
                }
                logger.info("update complete, update row:{} (include tl)", uc);
            }
            logger.info("SUCCESS");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw e;
        } finally {
            logger.info("data process finish, time:{}ms", (System.currentTimeMillis() - t0));
        }

    }

    private int getMaxTableNameLength(List<TableData> list) {
        int max = 10;
        for (TableData tableData : list) {
            max = Math.max(max, tableData.getName().length());
        }
        return max;
    }

    public int processTable(TableData tableData) throws SQLException {
        int c = 0;
        for (TR tr : tableData.getTrs()) {
            c += dbAdaptor.processTR(tr);
        }
        return c;
    }

    void updateCellFormula(TableData.TD td) {
        td.tr.table.sheet.getWorkbook().getCreationHelper().createFormulaEvaluator().evaluateFormulaCell(td.cell);
    }

    public boolean tryUpdateCell(TableData.TD td) {
        List<String> relatedCells = td.getRelatedCell();
        int ready = 0;
        for (String cellNum : relatedCells) {
            TableData.TD relatedTD = findCell(cellNum, td);
            if (relatedTD == null) {
                throw new IllegalStateException("invalid reference:" + cellNum);
            }
            if (relatedTD.valuePresent)
                ready++;
            else {
                // 先尝试插入依赖行？
            }
        }
        // 引用的所有 cell 值已就绪（一般只会引用 外键）
        if (ready == relatedCells.size()) {
            // 重新计算公式值
            updateCellFormula(td);
            // 标记未已有值 状态
            td.updateValue(td.cell.getRichStringCellValue().getString());
        } else {
            return false;
        }
        return true;
    }

    TableData.TD findCell(String cellNum, TableData.TD td) {
        String sheetName = null;
        int ii = cellNum.indexOf('!');
        if (ii > 0) {
            sheetName = cellNum.substring(0, ii);
            cellNum = cellNum.substring(ii + 1);
        }
        int idx = 0;
        for (int i = 0; i < cellNum.length(); i++) {
            if (Character.isDigit(cellNum.charAt(i))) {
                idx = i;
                break;
            }
        }
        if (idx == 0 || idx >= cellNum.length()) {
            throw new IllegalArgumentException(cellNum + " is not a value CellNum.");
        }
        int col = toColIndex(cellNum.substring(0, idx));
        int row = Integer.parseInt(cellNum.substring(idx));
        for (TableData tableData : tables) {
            if (sheetName == null && td.tr.table.sheet != tableData.sheet) {
                continue;
            }
            if (sheetName != null && !sheetName.equals(tableData.sheet.getSheetName())) {
                continue;
            }
            int sl = tableData.getStartLine(), sc = tableData.getStartCol();
            if (sl < row && sl + tableData.getTrs().size() >= row) {
                TR tr = tableData.getTrs().get(row - tableData.getStartLine() - 1);
                return tr.tds.get(col - sc - 1);
            }
        }
        return null;
    }

    /**
     * A=1,Z=26,AA=27,AB=28
     * 
     * @param str
     * @return
     */
    static int toColIndex(String str) {
        int value = 0, p = 1;
        for (int i = str.length() - 1; i >= 0; i--) {
            char c = str.charAt(i);
            value += (c - 'A' + 1) * p;
            p *= 26;
        }
        return value;
    }

    /**
     * 执行本地测试
     * 
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        System.setProperty("db.url", "jdbc:mysql://60.191.89.154:13306/jrap_dev");
        System.setProperty("db.user", "jrap_dev");
        System.setProperty("db.password", "jrap_dev");
        System.setProperty("db.override", "true");

        ExcelDataLoader processor = new ExcelDataLoader();
        URL url = ExcelDataLoader.class.getResource("/com/jingrui/jrap/db/data/20190426-init-data.xlsx");
        if (url == null) {
            throw new FileNotFoundException("excel not found.");
        }
        processor.setFilePath(new File(url.toURI()).getAbsolutePath());
        processor.execute(null);
    }

    @Override
    public void execute(Database database) throws CustomChangeException {
        logger.info("begin process excel :" + filePath);
        ExcelSeedDataReader dataReader = new ExcelSeedDataReader(filePath);
        tables = dataReader.load();
        dbAdaptor = new DbAdaptor(this);
        dbAdaptor.setUrl(System.getProperty("db.url"));
        dbAdaptor.setUserName(System.getProperty("db.user"));
        dbAdaptor.setPassword(System.getProperty("db.password"));
        dbAdaptor.setDbDriver(System.getProperty("db.driver"));
        dbAdaptor.setOverride(Boolean.valueOf(System.getProperty("db.override", "true")));
        String name = System.getProperty("db.skipUpdateTables");
        if (StringUtils.isNotEmpty(name)) {
            String[] names = name.toUpperCase().split(",");
            Collections.addAll(updateSkipTableNames, names);
        }
        try {
            processData();
            dbAdaptor.closeConnection(true);
        } catch (Exception e) {
            dbAdaptor.closeConnection(false);
            throw new CustomChangeException(e);
        }
    }

    @Override
    public String getConfirmationMessage() {
        return null;
    }

    @Override
    public void setUp() throws SetupException {

    }

    @Override
    public void setFileOpener(ResourceAccessor resourceAccessor) {

    }

    @Override
    public ValidationErrors validate(Database database) {
        return null;
    }
}
