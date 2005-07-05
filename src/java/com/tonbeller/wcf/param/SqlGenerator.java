package com.tonbeller.wcf.param;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.tonbeller.wcf.utils.SqlUtils;

/**
 * @author av
 * @since 31.01.2005
 */
public class SqlGenerator implements SqlExprVisitor {
  StringBuffer sb;
  Map columnMap;

  /**
   * if no columnId is set in an SqlExpr use this string to look up the column name
   */
  public static final String DEFAULT_COLUMN_ID = "DEFAULT_COLUMN_ID";

  public SqlGenerator() {
    this.columnMap = new HashMap();
  }

  /**
   * @param defaultColumn the name of the generated column if no column id is present in the SqlExpr
   */
  public SqlGenerator(String defaultColumn) {
    this.columnMap = new HashMap();
    columnMap.put(DEFAULT_COLUMN_ID, defaultColumn);
  }

  public SqlGenerator(Map columnMap) {
    this.columnMap = columnMap;
  }

  /**
   * returns a new SqlGenerator that shares the same settings as this.
   */
  protected SqlGenerator copyGenerator() {
    return new SqlGenerator(columnMap);
  }

  /**
   * generates SQL for opd in a new SqlGenerator
   */
  protected String generateOpd(SqlExpr opd) {
    return copyGenerator().generate(opd);
  }

  protected String getColumnName(SqlColumnConstraint scc) {
    String columnId = scc.getColumnId();
    if (columnId == null)
      columnId = DEFAULT_COLUMN_ID;
    String qname = (String) columnMap.get(columnId);
    if (qname != null)
      return qname;
    return columnId;
  }

  public void genAndOr(SqlExprWithOperands exp, String keyword) {
    Iterator it = exp.getOperands().iterator();
    SqlExpr opd = (SqlExpr) it.next();
    sb.append("(");
    sb.append(generateOpd(opd));
    while (it.hasNext()) {
      sb.append(" ");
      sb.append(keyword);
      sb.append(" ");
      opd = (SqlExpr) it.next();
      sb.append(generateOpd(opd));
    }
    sb.append(")");
  }

  public void visitSqlAndExpr(SqlAndExpr exp) {
    genAndOr(exp, "AND");
  }

  public void visitSqlOrExpr(SqlOrExpr exp) {
    genAndOr(exp, "OR");
  }

  public void visitSqlBetweenExpr(SqlBetweenExpr exp) {
    sb.append(getColumnName(exp));
    sb.append(" BETWEEN ");
    sb.append(SqlUtils.escapeSql(exp.getFirst()));
    sb.append(" AND ");
    sb.append(SqlUtils.escapeSql(exp.getLast()));
  }

  public void visitSqlEqualExpr(SqlEqualExpr exp) {
    generateEquals(exp, exp.getSqlValue());
  }

  private void generateEquals(SqlColumnConstraint exp, Object sqlValue) {
    sb.append(getColumnName(exp));
    if (sqlValue == null)
      sb.append(" IS NULL");
    else {
      sb.append(" = ");
      sb.append(SqlUtils.escapeSql(sqlValue));
    }
  }

  public void visitSqlDummyExpr(SqlDummyExpr exp) {
    sb.append("1 = 1");
  }

  public void visitSqlInExpr(SqlInExpr exp) {
    Object[] values = exp.getSqlValues();

    String cname = getColumnName(exp);
    boolean containsNull = containsNull(values);
    if (containsNull)
      values = removeNulls(values);

    if (values.length == 0) {
      // empty list
      if (containsNull)
        sb.append(cname).append(" IS NULL");
      else
        sb.append("1=0");
    } else if (values.length == 1) {
      // one single element
      if (containsNull) {
        sb.append("(");
        sb.append(cname);
        sb.append(" = ");
        sb.append(SqlUtils.escapeSql(values[0]));
        sb.append(" OR ");
        sb.append(cname);
        sb.append(" IS NULL)");
      } else {
        sb.append(cname);
        sb.append(" = ");
        sb.append(SqlUtils.escapeSql(values[0]));
      }
    } else {
      // multiple elements
      String list = generateList(values);
      if (containsNull) {
        sb.append("(");
        sb.append(cname);
        sb.append(" IN (");
        sb.append(list);
        sb.append(") OR ");
        sb.append(cname);
        sb.append(" IS NULL)");
      } else {
        sb.append(cname);
        sb.append(" IN (");
        sb.append(list);
        sb.append(")");
      }
    }
  }

  protected String generateList(Object[] values) {
    StringBuffer list = new StringBuffer();
    for (int i = 0; i < values.length; i++) {
      if (i > 0)
        list.append(", ");
      list.append(SqlUtils.escapeSql(values[i]));
    }
    return list.toString();
  }

  protected Object[] removeNulls(Object[] values) {
    List list = new ArrayList();
    for (int i = 0; i < values.length; i++) {
      if (values[i] != null)
        list.add(values[i]);
    }
    return list.toArray();
  }

  protected boolean containsNull(Object[] values) {
    for (int i = 0; i < values.length; i++)
      if (values[i] == null)
        return true;
    return false;
  }

  /**
   * generates SQL string from expression
   */
  public String generate(SqlExpr sqlExpr) {
    sb = new StringBuffer();
    sqlExpr.accept(this);
    return sb.toString();
  }

  /**
   * maps columnIds to column names. Example: "CUSTOMER" -> "CUSTOMER_TABLE.CUSTOMER_FK"
   */
  public Map getColumnMap() {
    return columnMap;
  }

  /**
   * maps columnIds to column names. Example: "CUSTOMER" -> "CUSTOMER_TABLE.CUSTOMER_FK"
   */
  public void setColumnMap(Map columnMap) {
    this.columnMap = columnMap;
  }

}