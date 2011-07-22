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

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.jstl.core.ConditionalTagSupport;

import org.apache.log4j.Logger;

import com.tonbeller.wcf.controller.RequestContext;
import com.tonbeller.wcf.expr.ExprUtils;

/**
 * Adds an XSL parameter for the transformation. If used inside a 
 * render tag, the parameter is local to that transformation. Otherwise
 * the scope attribute specifies the scope of the parameter, legal values
 * are "request" (default), "session" and "application".
 * <p></p>
 * The value may be provided as literal, or as expression (starting with "${").
 * 
 * @author av
 */
public class RendererParameterTag extends ConditionalTagSupport {
  private static Logger logger = Logger.getLogger(RendererParameterTag.class);

  String name;
  String value;
  String scope;
  String test;

  public void setTest(String test) {
    this.test = test;
  }

  /**
   * adds a parameter to the parent RendererTag (local) or to the
   * session-global xsl parameter map.
   */
  public int doStartTag() throws JspException {
    logger.info("enter");
    RequestContext context = RequestContext.instance();

    String paramName = name;
    String paramScope = (scope == null ? "request" : scope);
    Object paramValue;
    if (ExprUtils.isExpression(value))
      paramValue = context.getModelReference(value);
    else
      paramValue = value;

    RendererTag rt = (RendererTag) super.findAncestorWithClass(this, RendererTag.class);
    if (rt != null) {
      // parameter is local to this transformation
      if (paramValue == null)
        rt.removeParameter(paramName);
      else
        rt.addParameter(paramName, paramValue);
    } else {
      // scoped parameter
      if (paramValue == null)
        RendererParameters.removeParameter(context.getRequest(), paramName, paramValue, paramScope);
      else
        RendererParameters.setParameter(context.getRequest(), paramName, paramValue, paramScope);
    }
    logger.info("leave");
    return super.doStartTag();
  }

  /**
   * Sets the name.
   * @param name The name to set
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Sets the value.
   * @param value The value to set
   */
  public void setValue(String value) {
    this.value = value;
  }

  /**
   * @param string
   */
  public void setScope(String string) {
    scope = string;
  }

  protected boolean condition() throws JspTagException {
    if (test == null)
      return false;

    boolean b = true;
    String v = value;
    if (v != null && v.startsWith("!")) {
      v = v.substring(1);
      b = false;
    }

    HttpServletRequest hsr = (HttpServletRequest) pageContext.getRequest();
    Map map = RendererParameters.getParameterMap(hsr);
    String x = (String) map.get(test);
    if (x == null)
      return !b;
    if (v == null)
      return b;
    if (v.equals(x))
      return b;
    return !b;
  }

}
