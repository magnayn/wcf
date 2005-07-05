package com.tonbeller.wcf.param;

/**
 * Dummy SQL Expression like "1=1"
 */
public class SqlDummyExpr implements SqlExpr {

  public void accept(SqlExprVisitor visitor) {
    visitor.visitSqlDummyExpr(this);
  }

  public Object clone() throws CloneNotSupportedException {
    return super.clone();
  }
}
