package com.jingrui.jrap.db.excel;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.jingrui.jrap.db.excel.TableData.*;

public class ExcelSeedDataReader {
    private String filePath;
    private Workbook workBook;

    private Logger logger = LoggerFactory.getLogger(getClass());

    public ExcelSeedDataReader(String filePath) {
        this.filePath = filePath;
    }

    public List<TableData> load() {
        try (FileInputStream inStream = new FileInputStream(new File(filePath))) {
            workBook = WorkbookFactory.create(inStream);
            List<TableData> tablesAll = new ArrayList<>();
            for (int i = 1; i < workBook.getNumberOfSheets(); i++) {
                tablesAll.addAll(getSheetData(i));
            }
            return tablesAll;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String getCellValue(Cell cell) {
        String cellValue = "";
        DataFormatter formatter = new DataFormatter();
        if (cell != null) {
            switch (cell.getCellType()) {
            case Cell.CELL_TYPE_NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    cellValue = formatter.formatCellValue(cell);
                } else {
                    if(cell instanceof XSSFCell){
                        cellValue = ((XSSFCell)cell).getRawValue();
                        break;
                    }
                    double value = cell.getNumericCellValue();
                    int intValue = (int) value;
                    cellValue = value - intValue == 0 ? String.valueOf(intValue) : String.valueOf(value);

                }
                break;
            case Cell.CELL_TYPE_STRING:
                cellValue = cell.getStringCellValue();
                break;
            case Cell.CELL_TYPE_BOOLEAN:
                cellValue = String.valueOf(cell.getBooleanCellValue());
                break;
            case Cell.CELL_TYPE_FORMULA:
                cellValue = String.valueOf(cell.getCellFormula());
                break;
            case Cell.CELL_TYPE_BLANK:
                cellValue = "";
                break;
            case Cell.CELL_TYPE_ERROR:
                cellValue = "";
                break;
            default:
                cellValue = cell.toString().trim();
                break;
            }
        }
        return cellValue.trim();
    }

    private List<TableData> getSheetData(int sheetIdx) {

        int skipLine = 6;
        int skipCol = 3;

        Sheet sheet = workBook.getSheetAt(sheetIdx);

        List<TableData> tables = new ArrayList<>();
        int numOfRows = sheet.getLastRowNum() + 1;

        TableData currentTable = null;
        for (int rn = skipLine; rn < numOfRows; rn++) {
            Row row = sheet.getRow(rn);
            if (row == null && currentTable != null) {
                currentTable.makeReady();
                tables.add(currentTable);
                currentTable = null;
                continue;
            }
            if (row == null) {
                continue;
            }
            int cellNum = row.getLastCellNum();
            if (cellNum < skipCol) {
                continue;
            }

            Cell tableNameCell = row.getCell(skipCol);
            String tableName = getCellValue(tableNameCell);
            if (StringUtils.isNoneEmpty(tableName)) {
                if (currentTable != null) {
                    currentTable.makeReady();
                    tables.add(currentTable);
                }
                // table
                currentTable = new TableData();
                currentTable.sheet = sheet;
                currentTable.setStartLine(row.getRowNum() + 1);
                currentTable.setStartCol(tableNameCell.getColumnIndex() + 1);

                currentTable.setName(tableName);
                logger.info("found table:{} ,sheet:{}, begin row:{}", tableName, sheet.getSheetName(),
                        currentTable.getStartLine());
                // System.out.println("table:" + currentTable.getSummaryInfo());

                // column
                for (int c = skipCol + 1; c < cellNum; c++) {
                    Cell cell = row.getCell(c);
                    String colName = this.getCellValue(cell);
                    if (StringUtils.isEmpty(colName)) {
                        break;
                    }

                    currentTable.addCol(new COL(colName));
                }
                continue;
            } else {
                // data
                if (isAllEmpty(row, skipCol + 1)) {
                    continue;
                }
                TR tr = new TR();
                tr.table = currentTable;
                tr.lineNumber = row.getRowNum() + 1;
                boolean allBlank = true;
                for (int j = skipCol + 1; j < row.getLastCellNum(); j++) {
                    Cell cell = row.getCell(j);
                    TD td = new TD(cell, tr, currentTable.getCols().get(tr.tds.size()));
                    td.value = getCellValue(cell);
                    tr.tds.add(td);
                    if (StringUtils.isNoneEmpty(td.value)) {
                        allBlank = false;
                    }
                    if (tr.tds.size() == currentTable.getCols().size()) {
                        // 丢弃多余的数据(如果有，不继续读了)
                        break;
                    }
                }
                // System.out.println(" data row:" + tr);
                if (!allBlank) {
                    int delt = currentTable.getCols().size() - tr.tds.size();
                    // 不够的数据,补 null
                    for (int _i = 0; _i < delt; _i++) {
                        TD td = new TD(null, tr, currentTable.getCols().get(tr.tds.size()));
                        tr.tds.add(td);
                    }
                    currentTable.getTrs().add(tr);
                } else {
                    //System.out.println("skip all empty line:" + (row.getRowNum() + 1));
                }
            }
        }
        if (currentTable != null) {
            currentTable.makeReady();
            tables.add(currentTable);
        }
        // tables.get(0).getTrs().get(0).tds.get(0).updateValue("12");
        // workBook.getCreationHelper().createFormulaEvaluator().evaluateAll();
        // System.out.println(tables.get(1).getTrs().get(0).tds.get(0).cell.getRichStringCellValue());
        // System.out.println(tables.get(1).getTrs().get(0).tds.get(1).getRelatedCell());

        return tables;
    }

    private boolean isAllEmpty(Row row, int startCol) {
        for (int i = startCol; i < row.getLastCellNum(); i++) {
            Cell cell = row.getCell(i);
            if (cell != null && org.apache.commons.lang.StringUtils.isNotBlank(getCellValue(cell))) {
                return false;
            }
        }
        return true;
    }

}