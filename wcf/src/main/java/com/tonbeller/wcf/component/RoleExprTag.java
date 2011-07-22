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
package com.tonbeller.wcf.component;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.jstl.core.ConditionalTagSupport;

import com.tonbeller.wcf.controller.RequestContext;

/**
 * A conditional tag that evaluates its body if the roleExpr is successful
 * @author av
 */
public class RoleExprTag extends ConditionalTagSupport {
  String role;
  
  protected boolean condition() throws JspTagException {
    RequestContext context = RequestContext.instance();
    return context.isUserInRole(role);
  }

  public void setRole(String string) {
    role = string;
  }

}
