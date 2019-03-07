package com.jingrui.jrap.security.permission;

import com.github.pagehelper.SqlUtil;
import com.jingrui.jrap.core.IRequest;
import com.jingrui.jrap.core.components.ApplicationContextHelper;
import com.jingrui.jrap.security.permission.dto.Limit;
import com.jingrui.jrap.security.permission.service.impl.DataPermissionParameterMappingTokenHandler;
import com.jingrui.jrap.security.permission.service.impl.DataPermissionRangeFilter;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Alias;
import net.sf.jsqlparser.expression.BinaryExpression;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.operators.relational.InExpression;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.*;
import org.apache.ibatis.parsing.GenericTokenParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;

/**
 * @author jialong.zuo@jingrui.com on 2017/11/14.
 */
public class DataPermissionUtil {

    private static Logger logger = LoggerFactory.getLogger(DataPermissionUtil.class);

    static Map<String, DataPermissionRangeFilter> rangeFilter= ApplicationContextHelper.getApplicationContext().getBeansOfType(DataPermissionRangeFilter.class);

    public static String getNewSql (String oldSql,IRequest request) {
        if (null == request || request.getUserId().toString().equals("-1") || request.getAllRoleId().length == 0) {
            return oldSql;
        }
            Select select ;
        try {
            select=(Select) CCJSqlParserUtil.parse(oldSql);
        } catch (Exception e) {
            //跳过工具无法解析的sql
            logger.error("Parse SQL Error,Skip DATA_PERMISSION",e);
            return oldSql;
        }
        //解析oldSql
        try {
            analysisSql(select.getSelectBody(), request,oldSql);
        } catch (Exception e) {
            logger.error("Create New SQL Error,Skip DATA_PERMISSION",e);
            return oldSql;
        }
        //pageHelper所对应jsqlparser版本过低只有limit offest 需做转换。
        net.sf.jsqlparser.statement.select.Limit mit=null;

        if(select.getSelectBody() instanceof SetOperationList){
            mit = ((SetOperationList)select.getSelectBody()).getLimit();
            if (mit != null) {
                Limit limit = new Limit(mit);
                ((SetOperationList) select.getSelectBody()).setLimit(limit);

            }
        }else if(select.getSelectBody() instanceof PlainSelect){
            mit = ((PlainSelect)select.getSelectBody()).getLimit();
            if (mit != null) {
                Limit limit = new Limit(mit);
                ((PlainSelect) select.getSelectBody()).setLimit(limit);

            }
        }

        return select.toString();
    }

    private static void analysisSql(SelectBody selectBody, IRequest request,String oldSql) throws ExecutionException, JSQLParserException {
        if (selectBody instanceof PlainSelect) {
            parserSql((PlainSelect) selectBody, request,oldSql);
        } else if (selectBody instanceof SetOperationList) {
            SetOperationList list = (SetOperationList) selectBody;
            for (int i = 0; i < list.getSelects().size(); i++) {
                parserSql((PlainSelect) list.getSelects().get(i), request,oldSql);
            }
        }
    }

    private static void parserSql(PlainSelect select, IRequest request, String oldSql) throws ExecutionException, JSQLParserException {

        //List<SelectItem> lists = select.getSelectItems();
        //判断查询字段中是否有子查询
//        for (SelectItem selectItem : lists) {
//            if (selectItem instanceof SubSelect) {
//                analysisSql(((SubSelect) selectItem).getSelectBody(), request);
//            }
//        }

        //判断from中是否有子查询
        FromItem fromItem = select.getFromItem();
        if (fromItem instanceof SubSelect) {
            analysisSql(((SubSelect) fromItem).getSelectBody(), request,oldSql);
        }

        List<Join> joins = select.getJoins();
        if (null != joins) {
            for (Join join : joins) {
                if (join.getRightItem() instanceof SubSelect) {
                    analysisSql(((SubSelect) join.getRightItem()).getSelectBody(), request,oldSql);
                }
            }
        }
        // parseWhere(select.getWhere(), request);

        doParserSql(select, request,oldSql);

    }


    private static void doParserSql(PlainSelect select, IRequest request,String oldSql) throws ExecutionException, JSQLParserException {
        FromItem fromItem = select.getFromItem();

        if (fromItem instanceof Table) {
            SubSelect subSelect = getTableView((Table) fromItem, request,oldSql);
            if (null != subSelect) {
                select.setFromItem(subSelect);
            }
        }

        List<Join> joins = select.getJoins();
        if (null != joins) {
            for (Join join : joins) {
                if (join.getRightItem() instanceof Table) {
                    SubSelect subSelect = getTableView((Table) join.getRightItem(), request,oldSql);
                    if (null != subSelect) {
                        join.setRightItem(subSelect);
                    }
                }
            }
        }
    }

    private static void parseWhere(Expression expression, IRequest request,String oldSql) throws ExecutionException, JSQLParserException {
        if (null == expression) {
            return;
        }

        if (expression instanceof BinaryExpression) {
            Expression rightExp = ((BinaryExpression) expression).getRightExpression();
            Expression leftExp = ((BinaryExpression) expression).getLeftExpression();

            parseWhere(rightExp, request,oldSql);
            parseWhere(leftExp, request,oldSql);
        } else if (expression instanceof InExpression) {
            InExpression inExpression = (InExpression) expression;
            if (null != inExpression.getRightItemsList() && inExpression.getRightItemsList() instanceof SubSelect) {
                //parserSql((PlainSelect) ((SubSelect) inExpression.getRightItemsList()).getSelectBody(), request);
                parseWhere((SubSelect) inExpression.getRightItemsList(), request,oldSql);
            }
        } else if (expression instanceof SubSelect) {
            parserSql((PlainSelect) ((SubSelect) expression).getSelectBody(), request,oldSql);
        }
    }

    private static SubSelect getTableView(Table table, IRequest request,String oldSql) throws JSQLParserException, ExecutionException {
        Map<String, Set> map = new HashMap();
        SqlUtil.addPermissionTable(oldSql,table.getName().toUpperCase());
        for (Map.Entry<String, DataPermissionRangeFilter> entry : rangeFilter.entrySet()) {
            entry.getValue().doFilter(request, table.getName(), map);
        }

        if (map.size() == 0) {
            return null;
        }
        PlainSelect plainSelect = (PlainSelect) createTableView(map, table.getName(), request).getSelectBody();
        SubSelect tableView = new SubSelect();
        tableView.setSelectBody(plainSelect);
        if (null != table.getAlias()) {
            tableView.setAlias(table.getAlias());
        } else {
            Alias alias = new Alias(table.getName());
            alias.setUseAs(false);
            tableView.setAlias(alias);
        }
        return tableView;
    }

    private static Select createTableView(Map<String, Set> map, String tableName, IRequest iRequest) throws JSQLParserException {
        String newSql = "SELECT * FROM " + tableName + " WHERE ";
        for (Map.Entry<String, Set> mapEntry : map.entrySet()) {
            Set<String> inSet = mapEntry.getValue();
            String columnField = mapEntry.getKey();
            if (inSet.size() == 0) {
                continue;
            }
            if (!columnField.equals("_PERMISSION_CUSTOM_SQL")) {
                if (inSet.size() == 1) {
                    newSql += columnField + " = \'" + inSet.toArray()[0].toString()+"\'";
                } else {
                    String in = "";
                    String[] iSet = inSet.toArray(new String[inSet.size()]);
                    for (int i = 0; i < iSet.length; i++) {
                        in += "\'"+iSet[i]+"\'";
                        if (i != iSet.length - 1) {
                            in += ",";
                        }
                    }
                    newSql += columnField + " in ( " + in + " )";
                }
            } else {
                String in = "";
                String[] iSet = inSet.toArray(new String[inSet.size()]);
                for (int i = 0; i < iSet.length; i++) {
                    in += " ( " + addCriteriaIfDynamicSql(iSet[i], iRequest) + " ) ";
                    if (i != iSet.length - 1) {
                        in += " and ";
                    }
                }
                newSql += in;
            }
            newSql += " and ";
        }
        newSql = newSql.substring(0, newSql.length() - 5);
        return (Select) CCJSqlParserUtil.parse(newSql);
    }


    private static String addCriteriaIfDynamicSql(String oldSql, IRequest iRequest) {
        DataPermissionParameterMappingTokenHandler handler = new DataPermissionParameterMappingTokenHandler(iRequest);
        GenericTokenParser parser = new GenericTokenParser("#{", "}", handler);
        return parser.parse(oldSql);
    }
}
