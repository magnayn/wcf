package com.tonbeller.wcf.param;

/**
 * @author av
 * @since 31.01.2005
 */
public abstract class SqlColumnConstraint implements SqlExpr {
  String columnId;

  /**
   * the ID of the sql column that this constraint is for. The id may be the column name itself
   * or an identifier that is mapped to the actual column name
   */
  public String getColumnId() {
    return columnId;
  }
  /**
   * the ID of the sql column that this constraint is for. The id may be the column name itself
   * or an identifier that is mapped to the actual column name
   */
  public void setColumnId(String columnId) {
    this.columnId = columnId;
  }
  public Object clone() throws CloneNotSupportedException {
    return super.clone();
  }
}
