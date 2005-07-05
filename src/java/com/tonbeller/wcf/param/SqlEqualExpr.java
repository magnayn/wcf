package com.tonbeller.wcf.param;

/**
 * generates WHERE COLUMN = 'VALUE-LITERAL'
 * @author av
 * @since 31.01.2005
 */

public class SqlEqualExpr extends SqlColumnConstraint {
  Object sqlValue;
  public Object getSqlValue() {
    return sqlValue;
  }
  public void setSqlValue(Object sqlValue) {
    this.sqlValue = sqlValue;
  }
  public void accept(SqlExprVisitor visitor) {
    visitor.visitSqlEqualExpr(this);
  }
  public Object clone() throws CloneNotSupportedException {
    return super.clone();
  }
}
