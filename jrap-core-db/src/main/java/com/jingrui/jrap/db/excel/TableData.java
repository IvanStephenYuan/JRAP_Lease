package com.jingrui.jrap.db.excel;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TableData {

    int startLine;

    int startCol;

    public Sheet sheet;

    public int insert = 0;// 记录插入了多少数据

    private String name;
    private List<COL> cols = new ArrayList<>();
    private List<COL> uniqueCols = new ArrayList<>();

    private List<TR> trs = new ArrayList<>();

    public int getStartLine() {
        return startLine;
    }

    public void setStartLine(int startLine) {
        this.startLine = startLine;
    }

    public int getStartCol() {
        return startCol;
    }

    public void setStartCol(int startCol) {
        this.startCol = startCol;
    }

    public List<TR> getTrs() {
        return trs;
    }

    public void setTrs(List<TR> trs) {
        this.trs = trs;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name.toUpperCase();
    }

    public List<COL> getUniqueCols() {
        return uniqueCols;
    }

    public List<COL> getCols() {
        return cols;
    }

    public void setCols(List<COL> cols) {
        this.cols = cols;
    }

    public Set<String> langs = new HashSet<>();

    public boolean complete() {
        for (TR tr : trs) {
            if (!tr.processFlag)
                return false;
        }
        return true;
    }

    public String processSummary() {
        int complete = 0;
        for (TR tr : trs) {
            if (tr.processFlag)
                complete++;
        }
        String str = String.format("total:%-4d  process:%-4d  insert:%-4d", trs.size(), complete, insert);
        if (complete == trs.size()) {
            str += "  complete";
        }
        return str;
    }

    public COL addCol(COL c) {
        cols.add(c);
        if (c.unique) {
            uniqueCols.add(c);
        }
        if (c.lang != null) {
            langs.add(c.lang);
        }
        return c;
    }

    public void makeReady() {
        int g = 0, u = 0;
        for (COL c : cols) {
            if (c.gen)
                g++;
            else if (c.unique)
                u++;
        }
        if (g > 1)
            throw new IllegalStateException("table has more than one generated column ," + getName());
        if (u == 0) {
            throw new IllegalStateException("table has no unique check column ," + getName());
        }
    }

    public String getSummaryInfo() {
        return String.format("%s[sheet:%s;startLine:%d]", name, sheet.getSheetName(), startLine);
    }

    public static class COL {
        public boolean gen = false;
        public String type = "VARCHAR";
        public String name;
        public boolean unique = false;

        public String lang = null;

        public COL(String name) {
            setName(name);
        }

        private void setName(String n) {
            this.name = n;
            if (name.startsWith("*")) {
                name = name.substring(1);
                gen = true;
            } else if (name.startsWith("#")) {
                unique = true;
                name = name.substring(1);
            }

            int sem = name.indexOf(":");
            if (sem > 0) {
                String lang = name.substring(sem + 1);
                name = name.substring(0, sem);
                if (StringUtils.isEmpty(lang)) {
                    throw new IllegalStateException("invalid TL language :" + n);
                }
                this.lang = lang;
            }
            name = name.toUpperCase();
            int lb = name.indexOf('(');
            if (lb > 0) {
                type = name.substring(lb + 1, name.indexOf(')')).trim().toUpperCase();
                name = name.substring(0, lb).trim();
            } else if (name.endsWith("_ID")) {
                type = "DECIMAL";
            } else if (name.endsWith("_DATE")) {
                type = "DATE";
            }
        }

        public String toString() {
            return (gen ? "*" : "") + name + ":" + type;
        }
    }

    public static class TR {
        public TableData table;
        public int lineNumber;
        public List<TD> tds = new ArrayList<>();

        public boolean processFlag = false;

        public boolean existsFlag=false;
        public boolean insertFlag=false;
        public boolean updateFlag=false;

        public boolean present() {
            for (TD td : tds) {
                if (!td.valuePresent)
                    return false;
            }
            return true;
        }

        public String toString() {
            return Arrays.toString(tds.toArray());
        }

        /**
         * 检查：是否有需要生成、但未生成的值（*主键）
         * 
         * @return
         */
        public boolean needGen() {
            for (TD td : tds) {
                if (td.col.gen && !td.valuePresent)
                    return true;
            }
            return false;
        }

        /**
         * 检查：是否除了需要自动生成的值，其他值均已具备
         * 
         * @return
         */
        public boolean canInsert() {
            for (TD td : tds) {
                if (td.col.gen || td.valuePresent)
                    continue;
                return false;
            }
            return true;
        }
    }

    public static class TD {
        public String value;
        public String formula;
        public boolean valuePresent = true;
        public boolean isFormula = false;
        public Cell cell;
        private static Pattern pattern = Pattern.compile("[A-Z]+[0-9]+");
        public TR tr;
        public COL col;

        public TD() {

        }

        public TD(Cell cell, TR tr, COL col) {
            this.tr = tr;
            this.col = col;
            this.cell = cell;
            if (cell != null && cell.getCellType() == Cell.CELL_TYPE_FORMULA) {
                isFormula = true;
                formula = cell.getCellFormula().replace("$", "");
                valuePresent = false;
            }
            if (col.gen) {
                valuePresent = false;
            }
        }

        public List<String> getRelatedCell() {
            if (!isFormula) {
                return Collections.emptyList();
            }
            if (formula.contains("!")) {
                return Collections.singletonList(formula);
            }
            List<String> list = new ArrayList<>();
            Matcher matcher = pattern.matcher(formula);
            while (matcher.find()) {
                list.add(matcher.group());
            }
            return list;
        }

        public void updateValue(String value) {
            cell.setCellValue(value);
            this.value = value;
            valuePresent = true;
        }

        public String toString() {
            if (cell == null)
                return null;
            StringBuilder sb = new StringBuilder();
            if (isFormula)
                sb.append('[').append("F:").append(formula).append(']');
            sb.append(value);
            return sb.toString();
        }

    }

}
