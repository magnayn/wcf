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

import com.tonbeller.wcf.param.ParamLinkTag.InvokeMethodAction;

/**
 * calls a method on a bean when invoked.
 * 
 * @author av
 */
public class ParamLinkActionTag extends ParamLinkNestedTag {
  String target;
  String method;
  
  public int doStartTag() throws JspException {
    ParamLinkTag link = getParamLinkTag();
    InvokeMethodAction a = new InvokeMethodAction(target, method);
    link.addAction(a);
    return EVAL_BODY_INCLUDE;
  }

  public void setMethod(String method) {
    this.method = method;
  }
  public void setTarget(String target) {
    this.target = target;
  }
  
}
