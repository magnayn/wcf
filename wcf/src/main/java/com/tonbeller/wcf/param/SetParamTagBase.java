package com.tonbeller.wcf.param;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;

import com.tonbeller.wcf.expr.ExprUtils;

/**
 * @author av
 * @since 01.03.2005
 */
public class SetParamTagBase extends TagSupport {
  String displayName;
  String displayValue;
  String paramName;
  String sqlValue;
  String mdxValue;
  String textValue;

  private static final Logger logger = Logger.getLogger(SetParamTagBase.class);
  
  // the parameter in the pool before it was replaced by a new one
  SessionParam oldParam;
  SessionParam newParam;

  public int doStartTag() throws JspException {
    SessionParamPool pool = SessionParamPool.instance(pageContext);
    // remember the old parameter
    oldParam = pool.getParam(paramName);
    
    // create a new one, copies default values
    try {
      if (oldParam == null) {
        newParam = new SessionParam();
        newParam.setName(paramName);
      }
      else
        newParam = (SessionParam) oldParam.clone();
    } catch (CloneNotSupportedException e) {
      logger.error(null, e);
      throw new JspException(e);
    }

    // initialize + store
    initialize(newParam);
    pool.setParam(newParam);
    return EVAL_BODY_INCLUDE;
  }
  
  protected void initialize(SessionParam p) {
    if (displayName != null)
      p.setDisplayName(evalStr(displayName));
    if (displayValue != null)
      p.setDisplayValue(evalStr(displayValue));
    if (paramName != null)
      p.setName(evalStr(paramName));
    if (sqlValue != null)
      p.setSqlValue(evalObj(sqlValue));
    if (mdxValue != null)
      p.setMdxValue(evalStr(mdxValue));
    if (textValue != null)
      p.setMdxValue(evalStr(mdxValue));
  }
  
  protected Object evalObj(String s) {
    if (ExprUtils.isExpression(s))
      return ExprUtils.getModelReference(pageContext, s);
    return s;
  }

  protected String evalStr(String s) {
    if (ExprUtils.isExpression(s))
      return String.valueOf(ExprUtils.getModelReference(pageContext, s));
    return s;
  }

  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }

  public void setDisplayValue(String displayValue) {
    this.displayValue = displayValue;
  }

  public void setMdxValue(String mdxValue) {
    this.mdxValue = mdxValue;
  }

  public void setParamName(String paramName) {
    this.paramName = paramName;
  }

  public void setSqlValue(String sqlExpr) {
    this.sqlValue = sqlExpr;
  }

  public void setTextValue(String textValue) {
    this.textValue = textValue;
  }

}
