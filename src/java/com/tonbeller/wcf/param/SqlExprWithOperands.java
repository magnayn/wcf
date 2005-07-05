package com.tonbeller.wcf.param;

import java.util.ArrayList;
import java.util.List;

/**
 * @author av
 * @since 31.01.2005
 */
abstract class SqlExprWithOperands implements SqlExpr {
  ArrayList opds = new ArrayList();
  public void addOperand(SqlExpr exp) {
    opds.add(exp);
  }
  public List getOperands() {
    return opds;
  }
  public Object clone() throws CloneNotSupportedException {
    SqlExprWithOperands p = (SqlExprWithOperands) super.clone();
    p.opds = (ArrayList) opds.clone();
    return p;
  }
}
