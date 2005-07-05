package com.tonbeller.wcf.param;

/**
 * @author av
 * @since 31.01.2005
 */
public class SqlOrExpr extends SqlExprWithOperands {

  public void accept(SqlExprVisitor visitor) {
    visitor.visitSqlOrExpr(this);
  }

}
