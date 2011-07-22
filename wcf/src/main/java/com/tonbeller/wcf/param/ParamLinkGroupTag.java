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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.tonbeller.wcf.component.RendererParameters;
import com.tonbeller.wcf.controller.Controller;
import com.tonbeller.wcf.controller.Dispatcher;
import com.tonbeller.wcf.controller.DispatcherSupport;
import com.tonbeller.wcf.expr.ExprUtils;

/**
 * Groups several ParamLink Tags. When the start tag is executed, 
 * the SqlSessionParam cache is cleared. Then new SqlSessionParam instances
 * are created by nested ParamLink tags. 
 * 
 * @author av
 */
public class ParamLinkGroupTag extends TagSupport {
  boolean renderActions;
  String hideIf;  

  public int doStartTag() throws JspException {
    getDispatcher().clear();
    HttpServletRequest hsr = (HttpServletRequest) pageContext.getRequest();
    renderActions = RendererParameters.isRenderActions(hsr);
    if (renderActions) 
      renderActions = !isHideLinks();
    return EVAL_BODY_INCLUDE;
  }

  private boolean isHideLinks() {
    if (hideIf == null)
      return false;
    Object obj = hideIf;
    if (ExprUtils.isExpression(hideIf))
      obj =ExprUtils.getModelReference(pageContext, hideIf);
    if (obj instanceof Boolean)
      return ((Boolean)obj).booleanValue();
    if (obj instanceof String)
      return Boolean.valueOf((String)obj).booleanValue();
    return false;
  }

  Dispatcher getDispatcher() {
    Dispatcher d = (Dispatcher) pageContext.getSession().getAttribute(id);
    if (d == null) {
      d = new DispatcherSupport();
      HttpSession session = pageContext.getSession();
      session.setAttribute(id, d);
      Controller.instance(session).addRequestListener(d);
    }
    return d;
  }

  public boolean isRenderActions() {
    return renderActions;
  }

  public void setHideIf(String hideIf) {
    this.hideIf = hideIf;
  }
}
