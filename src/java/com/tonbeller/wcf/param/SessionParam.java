package com.tonbeller.wcf.param;

/**
 * @author av
 * @since 31.01.2005
 */
public class SessionParam implements Cloneable {

  String displayName;
  String displayValue;
  String name;
  SqlExpr sqlExpr;
  String mdxValue;
  String textValue;
  
  /**
   * returns a text value as entered in the jsp 
   */
  public String getTextValue() {
    return textValue;
  }
  
  /**
   * sets a text value as enterd in the jsp
   */
  public void setTextValue(String textValue) {
    this.textValue = textValue;
  }

  /**
   * returns the value returned by the expression parser. 
   * For example the unique name like "[Customers].[Name].[Andreas Voss]"
   * @see com.tonbeller.jpivot.olap.navi.ExpressionParser
   */
  public String getMdxValue() {
    return mdxValue;
  }

  /**
   * sets the value returned by the expression parser. 
   * For example the unique name like "[Customers].[Name].[Customer 287]"
   * @see com.tonbeller.jpivot.olap.navi.ExpressionParser
   */
  public void setMdxValue(String mdxValue) {
    this.mdxValue = mdxValue;
  }
  
  public SqlExpr getSqlExpr() {
    return sqlExpr;
  }
  
  public void setSqlExpr(SqlExpr sqlExpr) {
    this.sqlExpr = sqlExpr;
  }
  
  /**
   * shorthand for getting/setting the sqlValue of a SqlEqualExpr
   */
  public Object getSqlValue() {
    if (sqlExpr == null)
      return null;
    if (!(sqlExpr instanceof SqlEqualExpr))
      throw new IllegalStateException("SqlEqualExpr required");
    return ((SqlEqualExpr)sqlExpr).getSqlValue();
  }
  
  /**
   * shorthand for getting/setting the sqlValue of a SqlEqualExpr
   */
  public void setSqlValue(Object sqlValue) {
    SqlEqualExpr expr = new SqlEqualExpr();
    expr.setSqlValue(sqlValue);
    setSqlExpr(expr);
  }

  /**
   * returns the name of the parameter for display to the user. 
   * For example "Customer"
   */
  public String getDisplayName() {
    return displayName;
  }

  /**
   * sets the name of the parameter for display to the user
   * For example "Customer"
   */
  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }
  
  /**
   * returns the parameter value for display to the user
   * For example "Andreas Voss"
   */
  public String getDisplayValue() {
    return displayValue;
  }

  /**
   * sets the parameter value for display to the user
   * For example "Andreas Voss"
   */
  public void setDisplayValue(String displayValue) {
    this.displayValue = displayValue;
  }

  /**
   * returns the name that identifies this parameter within the {@link SessionParamPool}.
   * For example, the customer ID.
   */
  public String getName() {
    return name;
  }

  /**
   * sets the name that identifies this parameter within the {@link SessionParamPool}.
   * For example, the customer ID.
   */
  public void setName(String name) {
    this.name = name;
  }
  
  public Object clone() throws CloneNotSupportedException {
    SessionParam p = (SessionParam) super.clone();
    SqlExpr x = (SqlExpr) sqlExpr.clone();
    p.setSqlExpr(x);
    return p;
  }

}
