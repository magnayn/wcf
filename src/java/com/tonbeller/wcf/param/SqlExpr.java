package com.tonbeller.wcf.param;

/**
 * @author av
 * @since 31.01.2005
 */
public interface SqlExpr extends Cloneable {
  void accept(SqlExprVisitor visitor);
  public Object clone() throws CloneNotSupportedException;
}
