package com.tonbeller.wcf.param;

/**
 * generates WHERE COLUMN IN ('VALUE-LITERAL-1', ...)
 * @author av
 * @since 31.01.2005
 */

public class SqlInExpr extends SqlColumnConstraint {
  Object[] sqlValues;
  public Object[] getSqlValues() {
    return sqlValues;
  }
  /** @deprecated use setSqlValues() instead */
  public void setSqlValue(Object[] sqlValues) {
    this.sqlValues = sqlValues;
  }
  public void setSqlValues(Object[] sqlValues) {
    this.sqlValues = sqlValues;
  }
  public void accept(SqlExprVisitor visitor) {
    visitor.visitSqlInExpr(this);
  }
  public Object clone() throws CloneNotSupportedException {
    SqlInExpr x = (SqlInExpr) super.clone();
    x.sqlValues = (Object[]) sqlValues.clone();
    return x;
  }
}
