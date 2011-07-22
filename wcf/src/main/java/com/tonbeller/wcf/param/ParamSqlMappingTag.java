package com.tonbeller.wcf.param;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * @author av
 * @since 01.02.2005
 */
public class ParamSqlMappingTag extends TagSupport {
  String column;
  String qname;
  
  public void setQname(String qname) {
    this.qname = qname;
  }
  public void setColumn(String column) {
    this.column = column;
  }
  
  public int doStartTag() throws JspException {
    ParamSqlTag tag = (ParamSqlTag) findAncestorWithClass(this, ParamSqlTag.class);
    if (tag == null)
      throw new JspException("paramSqlMapping tag must be nested in a paramSql tag");
    tag.setMapping(column, qname);
    return EVAL_BODY_INCLUDE;
  }

}