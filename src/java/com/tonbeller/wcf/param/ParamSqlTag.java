package com.tonbeller.wcf.param;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;

import com.tonbeller.wcf.expr.ExprUtils;

/**
 * outputs SQL for a {@link SessionParam}.
 * 
 * @author av
 * @since Dec 1, 2005
 */
public class ParamSqlTag extends TagSupport {
  String paramName;
  String qname;
  SqlGenerator sg;
  private static Logger logger = Logger.getLogger(ParamSqlTag.class);  
  
  public int doStartTag() throws JspException {
    sg = createSqlGenerator();
    if (qname != null)
      sg.getColumnMap().put(SqlGenerator.DEFAULT_COLUMN_ID, qname);
    return EVAL_BODY_INCLUDE;
  }
  
  public void setMapping(String columnId, String qname) {
    sg.getColumnMap().put(columnId, qname);
  }
  
  public int doEndTag() throws JspException {
    if (ExprUtils.isExpression(paramName)) {
      Object obj = ExprUtils.getModelReference(pageContext, paramName);
      paramName = String.valueOf(obj);
    }
      
    SessionParam param = SessionParamPool.instance(pageContext).getParam(paramName);
    if (param == null)
      throw new JspException("SessionParam " + paramName + " not found");
    SqlExpr ex = param.getSqlExpr();
    JspWriter out = pageContext.getOut();
    try {
      out.print(sg.generate(ex));
    } catch (IOException e) {
      logger.error(null, e);
    }
    return EVAL_PAGE;
  }

  /**
   * override to create your own DB specific SQL generator
   */
  protected SqlGenerator createSqlGenerator() {
    return new SqlGenerator();
  }
  public void setQname(String qname) {
    this.qname = qname;
  }
  public void setParam(String paramName) {
    this.paramName = paramName;
  }
}
