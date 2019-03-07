package com.jingrui.jrap.db.excel;

import static com.jingrui.jrap.db.excel.TableData.TD;
import static com.jingrui.jrap.db.excel.TableData.TR;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author shengyang.zhou@jingrui.com
 */
public class DbAdaptor {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private String userName;
    private String password;

    private String url;

    private Connection connection;

    private ExcelDataLoader dataProcessor;

    private boolean useSeq = false;

    private boolean override = true;

    private String dbType = "mysql";

    private String dbDriver;

    static Map<String, Integer> SQL_TYPE_MAP = new HashMap<>();

    static {
        SQL_TYPE_MAP.put("VARCHAR", Types.VARCHAR);
        SQL_TYPE_MAP.put("DATE", Types.DATE);
        SQL_TYPE_MAP.put("CLOB", Types.CLOB);
        SQL_TYPE_MAP.put("BLOB", Types.BLOB);
        SQL_TYPE_MAP.put("DECIMAL", Types.DECIMAL);
        SQL_TYPE_MAP.put("BIGINT", Types.BIGINT);
        SQL_TYPE_MAP.put("INT", Types.BIGINT);
    }

    Map<String, String> tableInsertSqlMap = new HashMap<>();
    Map<String, String> tableUpdateSqlMap = new HashMap<>();
    Map<String, String> tableUpdateTlSqlMap = new HashMap<>();

    public boolean isOverride() {
        return override;
    }

    public void setOverride(boolean override) {
        this.override = override;
    }

    public DbAdaptor(ExcelDataLoader dataProcessor) {
        this.dataProcessor = dataProcessor;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDbDriver() {
        return dbDriver;
    }

    public void setDbDriver(String dbDriver) {
        this.dbDriver = dbDriver;
    }

    public void initConnection() throws SQLException, ClassNotFoundException {
        String driver;
        if (url.contains("oracle")) {
            driver = "oracle.jdbc.driver.OracleDriver";
            dbType = "oracle";
            useSeq = true;
        } else if (url.contains("mysql")) {
            dbType = "mysql";
            driver = "com.mysql.jdbc.Driver";
        }  else if (url.contains("postgresql")) {
            dbType = "postgresql";
            useSeq = true;
            driver = "org.postgresql.Driver";
        } else {
            dbType = "mssql";
            driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
        }
        if (StringUtils.isNotEmpty(this.dbDriver)) {
            driver = this.dbDriver;
        }
        Class.forName(driver);
        connection = DriverManager.getConnection(url, userName, password);
        connection.setAutoCommit(false);
    }

    public void closeConnection(boolean commit) {
        if (connection != null) {
            try (Connection c = connection) {
                if (commit) {
                    c.commit();
                } else {
                    c.rollback();
                }
            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    protected Connection getConnection() {
        return connection;
    }

    /**
     * 新插入,或检测存在 则返回 1,否则0
     *
     * @return
     */
    public int processTR(TR tr) throws SQLException {
        if (tr.processFlag) {
            return 0;
        }
        Long cu = checkExists(tr);
        if (cu == null && tr.canInsert()) {
            doInsert(tr);
            doInsertTL(tr);
            return 1;
        } else if (cu != null && cu >= 0) {
            tr.processFlag = true;// 已经存在,不插入
            doInsertTL(tr);
            return 1;
        }

        return 0;
    }


    /**
     * 反回 -1: 不能检查唯一性（唯一性字段值不确定） <br>
     * 返回 0:存在，但没有自动生成的主键<br>
     * 返回 null:不存在<br>
     * 返回 大于0:存在，现有主键<br>
     *
     * @param tr
     * @return
     * @throws SQLException
     */
    protected Long checkExists(TR tr) throws SQLException {
        boolean uniquePresent = true;
        TD genTd = null;
        for (TD td : tr.tds) {
            if (td.col.gen) {
                genTd = td;
            }
            if (td.isFormula && !td.valuePresent) {
                dataProcessor.tryUpdateCell(td);
            }
            if (td.col.unique) {
                uniquePresent = uniquePresent && td.valuePresent;
            }
        }
        if (!uniquePresent) {
            // logger.info("[{}] check exists: ?? row:{} ,result :(-1)not
            // ready", tr.table.getName(), tr);
            return -1L;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("select ").append(genTd == null ? "0" : genTd.col.name).append(" from ").append(tr.table.getName());
        sb.append(" where ");
        List<String> list = new ArrayList<>();
        List<TD> params = new ArrayList<>();
        for (TD td : tr.tds) {
            if (td.col.unique) {
                list.add(td.col.name + "=?");
                params.add(td);
            }
        }
        sb.append(StringUtils.join(list, " AND "));
        try (PreparedStatement ps = connection.prepareStatement(sb.toString())) {
            int n = 1;
            for (TD td : params) {
                setParam(ps, td, n++);
            }
            try (ResultSet rs = ps.executeQuery()) {
                if (rs == null || !rs.next()) {
                    // logger.info("[{}] check exists: <> row:{} ,result :not
                    // exists", tr.table.getName(), tr);
                    return null;
                }
                Long pk = rs.getLong(1);
                if (rs.next()) {
                    throw new IllegalStateException("check unique found more than one,row:" + tr);
                }
                // logger.info("[{}] check exists: == row:{} ,result :{}",
                // tr.table.getName(), tr, pk);
                if (genTd != null) {
                    genTd.updateValue("" + pk);
                }
                tr.existsFlag = true;
                return pk;
            } catch (SQLException e) {
                logger.error("[{}]error check unique, row:{}, sql:{}", tr.table.getName(), tr, sb);
                throw e;
            }

        }

    }

    /**
     * 调用此方法之前,需要确保所有值已就绪
     *
     * @param tr
     * @return update count
     * @throws SQLException
     */
    protected int doUpdate(TR tr) throws SQLException {
        int updateCount = 0;
        TD genTd = null;
        List<TD> uniques = new ArrayList<>();
        List<TD> normals = new ArrayList<>();
        for (TD td : tr.tds) {
            if (td.isFormula && !td.valuePresent) {
                dataProcessor.tryUpdateCell(td);
            }
            if (td.col.gen) {
                genTd = td;
            } else if (td.col.unique) {
                uniques.add(td);
            } else {
                normals.add(td);
            }
        }
        if (genTd != null) {
            uniques.clear();
            uniques.add(genTd);
        }
        if (normals.isEmpty()) {
            return updateCount;
        }
        String sql = prepareTableUpdateSql(tr, uniques, normals);
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            int n = 1;
            for (TD td : normals) {
                if (td.col.lang == null || "zh_CN".equalsIgnoreCase(td.col.lang)) {
                    setParam(ps, td, n++);
                }
            }
            for (TD td : uniques) {
                setParam(ps, td, n++);
            }
            updateCount += ps.executeUpdate();
            tr.updateFlag = true;
        } catch (SQLException e) {
            logger.error("[{}]update error, row:{} ,sql:{}", tr.table.getName(), tr, sql);
            throw e;
        }

        if (genTd == null || tr.table.langs.isEmpty()) {
            return updateCount;
        }

        sql = prepareTableUpdateTlSql(tr, genTd, normals);

        for (String lang : tr.table.langs) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                int n = 1;
                for (TD td : normals) {
                    if (lang.equals(td.col.lang)) {
                        setParam(ps, td, n++);
                    }
                }
                setParam(ps, genTd, n++);
                ps.setString(n, lang);
                updateCount += ps.executeUpdate();
            } catch (SQLException e) {
                logger.error("[{}]error update tl ,row:{},sql:{}", tr.table.getName(), tr, sql);
                throw e;
            }
        }
        return updateCount;

    }

    private String prepareTableUpdateSql(TR tr, List<TD> uniques, List<TD> normals) {
        String sql = tableUpdateSqlMap.get(tr.table.getName());
        if (sql == null) {
            StringBuilder sb = new StringBuilder();
            sb.append("update ").append(tr.table.getName()).append(" set ");
            for (TD td : normals) {
                if (td.col.lang == null || "zh_CN".equalsIgnoreCase(td.col.lang)) {
                    sb.append(td.col.name).append("=?,");
                }
            }
            sb.deleteCharAt(sb.length() - 1);
            sb.append(" where ");
            for (TD td : uniques) {
                sb.append(td.col.name).append("=? AND ");
            }
            sb.delete(sb.length() - 4, sb.length());
            sql = sb.toString();
            tableUpdateSqlMap.put(tr.table.getName(), sql);
        }
        return sql;
    }

    private String prepareTableUpdateTlSql(TR tr, TD genTd, List<TD> normals) {
        String sql = tableUpdateTlSqlMap.get(tr.table.getName());
        if (sql == null) {
            StringBuilder sb = new StringBuilder();
            sb.append("update ").append(tlTableName(tr.table.getName())).append(" set ");

            for (TD td : normals) {
                if ("zh_CN".equalsIgnoreCase(td.col.lang)) {
                    sb.append(td.col.name).append("=?,");
                }
            }
            sb.deleteCharAt(sb.length() - 1);
            sb.append(" where ").append(genTd.col.name).append("=? and lang=?");
            sql = sb.toString();
            tableUpdateTlSqlMap.put(tr.table.getName(), sql);
        }
        return sql;
    }


    /**
     * 在调用本方法之前，需确保 该行记录具备插入条件
     *
     * @param tr
     * @return
     * @throws SQLException
     */
    protected Long doInsert(TR tr) throws SQLException {
        String sql = prepareInsertSql(tr);
        Long genVal = null;
        TD genTd = null;
        try (PreparedStatement ps = ("postgresql".equalsIgnoreCase(dbType))
                ? connection.prepareStatement(sql) : connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            // try (PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            int n = 1;
            for (TD td : tr.tds) {
                if (td.col.gen) {
                    genTd = td;
                    if (sequencePk()) {
                        genVal = getSeqNextVal(tr.table.getName());
                        ps.setLong(n++, genVal);
                    }
                    continue;
                }
                if (td.col.lang == null || "zh_CN".equals(td.col.lang)) {
                    if (!td.valuePresent && td.isFormula) {
                        ps.setLong(n++, -1L);// 为不确定的公式,插入-1(weakInsert
                        // 时会有不确定的值)
                        // ps.setNull(n++, Types.DECIMAL);
                    } else {
                        setParam(ps, td, n++);
                    }
                }
            }
            try {
                ps.executeUpdate();
                tr.insertFlag = true;
            } catch (SQLException sqle) {
                logger.error("[{}]error insert row:{}  sql:{}", tr.table.getName(), tr, sql);
                throw sqle;
            }
            logger.info("insert row:{}", tr);
            tr.table.insert++;
            if (!sequencePk() && genTd != null) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs != null && rs.next()) {
                        genVal = rs.getLong(1);
                    } else {
                        logger.warn("no gen pk,row:{}", tr);
                    }
                }
            }
        }
        if (genTd != null) {
            genTd.updateValue("" + genVal);
        }
        tr.processFlag = true;

        return genVal;

    }

    private List<TD> getUnpresentFormulaTds(TR tr) {
        List<TD> formulaTds = new ArrayList<>();
        for (TD td : tr.tds) {
            if (td.isFormula && !td.valuePresent) {
                formulaTds.add(td);
            }
        }
        return formulaTds;
    }

    private List<TD> getRequiredTds(TD td) {
        List<String> list = td.getRelatedCell();
        List<TD> tds = new ArrayList<>();
        for (String cellNum : list) {
            TD td0 = dataProcessor.findCell(cellNum, td);
            if (td0.valuePresent) {
                continue;
            }
            tds.add(td0);
        }
        return tds;
    }

    /**
     * 插入不存在的行,暂时不理会不能确定的值
     *
     * @param tables
     * @throws SQLException
     */
    public int weakInsert(List<TableData> tables) throws SQLException {
        List<TR> tempList = new ArrayList<>();
        for (TableData tableData : tables) {
            for (TR tr : tableData.getTrs()) {
                if (tr.processFlag) {
                    continue;
                }
                Long cu = checkExists(tr);
                if (cu == null) {
                    doInsert(tr);
                    tr.processFlag = false;
                    logger.info("weak insert row:{}", tr);
                    doInsertTL(tr);
                    tempList.add(tr);
                } else if (cu > 0) {
                    doInsertTL(tr);
                    tempList.add(tr);
                }
            }
        }
        int count;
        do {
            count = 0;
            for (TR tr : tempList) {
                if (tr.processFlag)
                    continue;
                List<TD> formulaTds = getUnpresentFormulaTds(tr);
                int c = 0;
                for (TD td : formulaTds) {
                    if (dataProcessor.tryUpdateCell(td)) {
                        c++;
                        doPostUpdate(tr, td, Long.parseLong("" + td.value));
                        count++;
                    }
                }
                if (c == formulaTds.size()) {
                    tr.processFlag = true;
                    tr.insertFlag = true;
                }
            }
        } while (count > 0);
        for (TR tr : tempList) {
            if (!tr.processFlag) {
                throw new RuntimeException("can not insert :" + tr);
            }
        }

        return tempList.size();
    }

    /**
     * 当执行 weakInsert 后,使用该方法修复数据
     *
     * @param tr
     * @param td
     * @param value
     * @throws SQLException
     */
    protected void doPostUpdate(TR tr, TD td, Long value) throws SQLException {
        TableData.TD genTD = null;
        for (TableData.TD d : tr.tds) {
            if (d.col.gen) {
                genTD = d;
                break;
            }
        }
        StringBuilder sb = new StringBuilder();
        sb.append("update ").append(tr.table.getName()).append(" set ").append(td.col.name).append("=? where ");
        sb.append(genTD.col.name).append("=?");
        try (PreparedStatement ps = connection.prepareStatement(sb.toString())) {
            ps.setLong(1, value);
            setParam(ps, genTD, 2);
            ps.executeUpdate();
            logger.debug("update col:{} value:{}  row:{}", td.col.name, value, tr);
        } catch (SQLException e) {
            logger.error("[{}]error post update col:{},row:{} ,sql:{}", tr.table.getName(), td.col.name, tr, sb);
            throw e;
        }

    }

    private void setParam(PreparedStatement ps, TD td, int n) throws SQLException {
        Object v = convertDataType(td.value, td.col.type);
        if (v == null) {
            ps.setNull(n, SQL_TYPE_MAP.get(td.col.type));
        } else if (v instanceof String) {
            ps.setString(n, (String) v);
        } else if (v instanceof Long) {
            ps.setLong(n, (Long) v);
        } else if (v instanceof Date) {
            ps.setDate(n, new java.sql.Date(((Date) v).getTime()));
        } else if (v instanceof Double) {
            ps.setDouble(n, (Double) v);
        } else if (v instanceof Boolean) {
            ps.setBoolean(n, (Boolean) v);
        } else {
            ps.setObject(n, v);
        }
    }

    protected boolean checkTlExists(TR tr, String lang) throws SQLException {
        StringBuilder sb = new StringBuilder();
        TD genTd = null;
        for (TD td : tr.tds) {
            if (td.col.gen) {
                genTd = td;
                break;
            }
        }
        if (genTd == null) {
            return true;
        }
        sb.append("select 1 from ").append(tlTableName(tr.table.getName()));
        sb.append(" where ").append(genTd.col.name).append("=").append(genTd.value);
        sb.append(" AND LANG='").append(lang).append("'");

        String sql = sb.toString();
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            } catch (SQLException e) {
                logger.error("[{}]error check tl exists, row:{} ,sql:{}", tr.table.getName(), tr, sql);
                throw e;
            }
        }

    }

    private String tlTableName(String str) {
        String tlTableName;
        if (str.toUpperCase().endsWith("_B")) {
            tlTableName = str.substring(0, str.length() - 2) + "_TL";
        } else {
            tlTableName = str + "_TL";
        }
        return tlTableName;
    }

    /**
     * 为支持的语言插入多语言数据.<br>
     * 不支持多语言的表,没有任何副作用<br>
     * 插入之前,会检查等价数据是否存在
     *
     * @param tr
     * @return
     * @throws SQLException
     */
    protected int doInsertTL(TR tr) throws SQLException {
        int c = 0;
        for (String lang : tr.table.langs) {
            // 没有多语言支持的话,这个循环不会执行
            // 当基表插入成功以后,tl 表肯定可以插入成功(如果不存在)
            c += doInsertTL(tr, lang);
        }
        return c;
    }

    protected int doInsertTL(TR tr, String lang) throws SQLException {
        if (checkTlExists(tr, lang)) {
            return 0;
        }
        StringBuilder sb = new StringBuilder();

        sb.append("INSERT INTO ").append(tlTableName(tr.table.getName())).append("(");
        for (TD td : tr.tds) {
            if (td.col.gen || lang.equals(td.col.lang)) {
                sb.append(td.col.name).append(",");
            }
        }
        sb.append("LANG)");
        sb.append("VALUES(");
        for (TD td : tr.tds) {
            if (td.col.gen || lang.equals(td.col.lang)) {
                sb.append("?").append(",");
            }
        }
        sb.append("?)");

        String sql = sb.toString();
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            int n = 1;
            for (TD td : tr.tds) {
                if (td.col.gen || lang.equals(td.col.lang)) {
                    setParam(ps, td, n++);
                }
            }
            ps.setString(n, lang);
            try {
                ps.executeUpdate();
            } catch (SQLException sqle) {
                logger.error("[{}]error insert tl row:{}  sql:{}", tr.table.getName(), tr, sql);
                throw sqle;
            }
        }

        logger.info("insert tl for row : {}  lang:{}", tr, lang);
        return 1;

    }

    public static final SimpleDateFormat sdf_l = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final SimpleDateFormat sdf_s = new java.text.SimpleDateFormat("yyyy-MM-dd");

    protected Object convertDataType(String value, String type) {
        if (StringUtils.isEmpty(value))
            return null;
        if ("DATE".equalsIgnoreCase(type)) {
            try {
                if (value.length() <= 10) {
                    return sdf_s.parse(value);
                }
                return sdf_l.parse(value);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
        if ("DECIMAL".equalsIgnoreCase(type) || "NUMBER".equalsIgnoreCase(type) || "BIGINT".equalsIgnoreCase(type)) {
            if (value.length() == 0) {
                return null;
            }
            if (value.contains(".")) {
                return Double.parseDouble(value);
            }
            return Long.parseLong(value);
        }
        if ("postgresql".equalsIgnoreCase(dbType) && "BOOLEAN".equalsIgnoreCase(type)) {
            if ("1".equalsIgnoreCase(value) || "T".equalsIgnoreCase(value) || "TRUE".equalsIgnoreCase(type)) {
                return Boolean.TRUE;
            } else {
                return Boolean.FALSE;
            }
        }
        return value;

    }

    protected String prepareInsertSql(TR tr) {
        String sql = tableInsertSqlMap.get(tr.table.getName());
        if (sql == null) {
            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO ").append(tr.table.getName()).append("(");
            int c = 0;
            for (TD td : tr.tds) {
                if (td.col.gen && !sequencePk())
                    continue;
                if (td.col.lang == null || "zh_CN".equals(td.col.lang)) {
                    c++;
                    sb.append(td.col.name).append(",");
                }
            }
            sb.deleteCharAt(sb.length() - 1);
            sb.append(") VALUES (");
            for (int i = 0; i < c; i++) {
                sb.append("?,");
            }
            sb.deleteCharAt(sb.length() - 1);
            sb.append(")");
            sql = sb.toString();
            tableInsertSqlMap.put(tr.table.getName(), sql);
        }
        return sql;
    }

    protected Long getSeqNextVal(String tableName) throws SQLException {
        String sql = "";
        if ("oracle".equalsIgnoreCase(dbType)) {
            sql = "select " + tableName + "_s.nextval from dual";
        }
        if ("postgresql".equalsIgnoreCase(dbType)) {
            sql = "select nextval('" + tableName + "_s')";
        }
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                return rs.getLong(1);
            } catch (SQLException e) {
                logger.error("error get sequence nextVal, tableName:{}" + tableName);
                throw e;
            }
        }
    }

    protected boolean sequencePk() {
        return useSeq;
    }

}
