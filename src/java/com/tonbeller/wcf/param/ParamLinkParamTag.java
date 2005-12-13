/*
 * ====================================================================
 * This software is subject to the terms of the Common Public License
 * Agreement, available at the following URL:
 *   http://www.opensource.org/licenses/cpl.html .
 * Copyright (C) 2003-2004 TONBELLER AG.
 * All Rights Reserved.
 * You must accept the terms of that agreement to use this software.
 * ====================================================================
 *
 * 
 */
package com.tonbeller.wcf.param;

import javax.servlet.jsp.JspException;

/**
 * Tag nested inside a ParamLinkTag. Adds a new SqlSessionParam to the parent tag.
 * 
 * @author av
 */
public class ParamLinkParamTag extends ParamLinkNestedTag {
  String paramName;
  String displayValue;
  String displayName;
  String sqlValue;
  String mdxValue;
  String textValue;

  //private static Logger logger = Logger.getLogger(ParamLinkParamTag.class);

  public int doStartTag() throws JspException {
    ParamLinkTag link = getParamLinkTag();
    link.addParam(paramName, displayName, displayValue, sqlValue, mdxValue, textValue);
    return EVAL_BODY_INCLUDE;
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

  public void setSqlValue(String sqlValue) {
    this.sqlValue = sqlValue;
  }
  
  public String getTextValue() {
    return textValue;
  }
  
  public void setTextValue(String textValue) {
    this.textValue = textValue;
  }
}