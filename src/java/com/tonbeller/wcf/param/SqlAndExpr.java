package com.tonbeller.wcf.param;

/**
 * @author av
 * @since 31.01.2005
 */
public class SqlAndExpr extends SqlExprWithOperands {
  public void accept(SqlExprVisitor visitor) {
    visitor.visitSqlAndExpr(this);
  }
}
