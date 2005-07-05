package com.tonbeller.wcf.param;

/**
 * @author av
 * @since 31.01.2005
 */
public class SqlBetweenExpr extends SqlColumnConstraint  {
  Object first, last;
  public Object getFirst() {
    return first;
  }
  public void setFirst(Object first) {
    this.first = first;
  }
  public Object getLast() {
    return last;
  }
  public void setLast(Object last) {
    this.last = last;
  }
  public void accept(SqlExprVisitor visitor) {
    visitor.visitSqlBetweenExpr(this);
  }
  public Object clone() throws CloneNotSupportedException {
    return super.clone();
  }
}
